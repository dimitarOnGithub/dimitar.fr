#!/bin/bash

ROOT_DIR="" # where do certificates live? path like /etc/letsencrypt/live/example.com
DEST_DIR="" # where do they need to go? path like /home/foobar/certs
WEBROOT="" # where do HTTP challenges go? path used by the webserver - /var/www/public
CERT_NAME="" # name of the certificate
CERT_FILE="$DEST_DIR/cert.pem" # location of the certificate that is being loaded by the proxy container
KEY_FILE="$DEST_DIR/key.pem" # location of the key that is being loaded by the proxy container
OWNER="user:group" # who will own the certs/dir post-copy? The user running the docker daemon
# Email configs
EMAIL_TO="" # who to send mail with the result?
EMAIL_FROM="" # who is the sender?
EMAIL_SERVER="" # SMTP server
EMAIL_USER="" # auth
EMAIL_PASSWORD="" # auth
# Script logfile
LOGFILE=/tmp/cert-renewal-script

log() {
  local message=$1
  echo "[$(date +"%Y-%m-%d %H:%M:%S")] $message" >> $LOGFILE
}

email () {
  local esubject=$1
  local ebody=$2
  swaks \
    --to "$EMAIL_TO" \
    --from "$EMAIL_FROM" \
    --server "$EMAIL_SERVER" \
    --port 587 \
    --auth LOGIN \
    --auth-user "$EMAIL_USER" \
    --auth-password "$EMAIL_PASSWORD" \
    --tls \
    --header "Subject: $esubject" \
    --body "$ebody"
}

log "Starting renewal script"
log "Validating current certificate expiry"

if [[ ! -f $CERT_FILE ]]; then
  log "Certificate file not found: $CERT_FILE"
  email "Certificate Renewal - Cert does not exist" "Failed to locate certificate at $CERT_FILE"
  exit 1
fi

CURRENT_EPOCH=$(date +"%s") # Timestamp
# notAfter=Jul  1 05:55:12 2026 GMT -> Wed Jul  1 05:55:12 UTC 2026
CERT_EXPIRY_DATE=$(openssl x509 -in "$CERT_FILE" -noout -enddate | cut -d '=' -f2-)
# Turn into timestamp too
CERT_EXPIRY_EPOCH=$(date -d "$CERT_EXPIRY_DATE" +"%s")
DAYS_DIFFERENCE=$(( ( CERT_EXPIRY_EPOCH - CURRENT_EPOCH ) / 86400 ))

log "Certificate expiry date is: $CERT_EXPIRY_DATE || Days: $DAYS_DIFFERENCE"

if [[ $DAYS_DIFFERENCE -gt 14 ]]; then
  email "Certificate Renewal - No Action Taken" "Certificate: $CERT_FILE; expires in $DAYS_DIFFERENCE days."
  exit 0
fi

log "Expiry date is in less than $DAYS_DIFFERENCE; commencing renewal process"

# Execute cert renewal
OUTPUT=$(certbot certonly --webroot -q -n --cert-name "$CERT_NAME" -w "$WEBROOT" && echo "success" || echo "fail")

if [[ $OUTPUT == "fail" ]]; then
  log "Certbot renewal failed"
  mail "Certificate Renewal - Failure" "Certbot command failed to renew certificate: $CERT_FILE; certificate expires in $DAYS_DIFFERENCE days"
  exit 1
fi

log "Certificate renewal finished successfully, copying certificate to: $DEST_DIR"

# This ain't great since if at any point certbot renewed but did NOT replace the old certificate
# (which is the default behaviour - https://eff-certbot.readthedocs.io/en/latest/using.html#re-creating-and-updating-existing-certificates )
# the newly generated one will increment thus making these lines copy the old certificates
cp "$ROOT_DIR/cert1.pem" "$CERT_FILE"
cp "$ROOT_DIR/privkey1.pem" "$KEY_FILE"

log "Certificate and key copied, updating permissions"
chown "$OWNER" "$DEST_DIR/"*.pem

log "Certificates copied and ownership updated"

NEW_EXPIRY_DATE=$(openssl x509 -in "$CERT_FILE" -noout -enddate | cut -d '=' -f2-)
NEW_EXPIRY_EPOCH=$(date -d "$NEW_EXPIRY_DATE" +"%s")
DAYS_DIFFERENCE=$(( ( NEW_EXPIRY_EPOCH - CURRENT_EPOCH ) / 86400 ))

email "Certificate Renewal - Success" "Certificate: $CERT_NAME renewed successfully, new expiration date: $NEW_EXPIRY_DATE ($DAYS_DIFFERENCE days)"

log "Email notification sent, exiting"
