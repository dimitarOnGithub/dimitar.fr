package fr.dimitar.web.auth;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

@Service
@Profile("api")
public class AuthenticationService {

    Logger log = LoggerFactory.getLogger(AuthenticationService.class);

    @Value("${mail.recipient.address}")
    private String recipientAddress;

    @Value("${mail.sender.address}")
    private String senderAddress;

    @Value("${ui.login.path}")
    private String loginPath;

    private final JavaMailSender mailSender;
    private final Map<String, LinkAuthentication> authMap;

    @Autowired
    public AuthenticationService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
        this.authMap = new HashMap<>();
    }

    public void generateLinkToken(HttpServletRequest request) {
        var linkToken = LinkToken.generate();
        var authentication = new LinkAuthentication(
                linkToken,
                request.getRemoteAddr(),
                request.getHeader("User-Agent")
        );
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(UrlUtils.buildFullRequestUrl(request))
                .replacePath(request.getContextPath())
                .replaceQuery(null)
                .fragment(null)
                .path(this.loginPath)
                .queryParam("token", linkToken.getToken());
        String magicLink = builder.toUriString();
        var mail = new SimpleMailMessage();
        mail.setTo(this.recipientAddress);
        mail.setFrom(this.senderAddress);
        mail.setSubject("OTT Link");
        mail.setText("Login: " + magicLink);
        this.mailSender.send(mail);
        this.authMap.put(authentication.getIpAddress(), authentication);
    }

    public LinkAuthentication getAuthenticationForIpAddress(String ipAddress) {
        log.trace("Looking up saved authentication requests for ip: {}", ipAddress);
        return this.authMap.get(ipAddress);
    }

    public void invalidateTokenForIpAddress(String ipAddress) {
        this.authMap.remove(ipAddress);
    }

}
