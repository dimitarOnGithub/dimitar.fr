package fr.dimitar.web.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
@Profile("api")
public class LinkAuthenticationProvider implements AuthenticationProvider {

    Logger log = LoggerFactory.getLogger(LinkAuthenticationProvider.class);

    private final AuthenticationService authenticationService;

    @Autowired
    public LinkAuthenticationProvider(AuthenticationService authenticationService){
        this.authenticationService = authenticationService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        LinkAuthentication linkAuthentication = (LinkAuthentication) authentication;
        log.debug("Attempting authentication for {}", linkAuthentication);
        LinkAuthentication savedAuthentication = this.authenticationService.getAuthenticationForIpAddress(linkAuthentication.getIpAddress());
        if (savedAuthentication == null) {
            log.debug("No authentication found for ip address={}", linkAuthentication.getIpAddress());
            throw new BadCredentialsException("Invalid token provided");
        }
        if (! savedAuthentication.getUserAgent().equals(linkAuthentication.getUserAgent())) {
            log.debug("User agent mismatch for {}", linkAuthentication);
            throw new BadCredentialsException("Invalid token provided");
        }
        LinkToken providedToken = (LinkToken) linkAuthentication.getCredentials();
        LinkToken savedToken = (LinkToken) savedAuthentication.getCredentials();
        if (! providedToken.equals(savedToken) ) {
            log.debug("Provided token: {}; does not match saved token: {}", providedToken.getScrubbed(), savedToken.getScrubbed());
            throw new BadCredentialsException("Invalid token provided");
        }
        if (savedToken.hasExpired()) {
            log.debug("Token has expired: {}", savedToken.getScrubbed());
            throw new BadCredentialsException("Token has expired");
        }
        log.debug("Authentication successful: {}", linkAuthentication);
        authentication.setAuthenticated(true);
        this.authenticationService.invalidateTokenForIpAddress(linkAuthentication.getIpAddress());
        return authentication;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return LinkAuthentication.class.isAssignableFrom(authentication);
    }
}
