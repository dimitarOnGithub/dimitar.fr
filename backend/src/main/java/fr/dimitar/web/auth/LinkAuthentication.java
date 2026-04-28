package fr.dimitar.web.auth;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

public class LinkAuthentication implements Authentication {

    // Spring Security defaults
    private boolean authenticated = false;
    private final Collection<? extends GrantedAuthority> authorities = List.of();

    // Custom token auth data
    private final LinkToken token;
    private final String ipAddress;
    private final String userAgent;

    public LinkAuthentication(LinkToken token, String ipAddress, String userAgent) {
        this.token = token;
        this.ipAddress = ipAddress;
        this.userAgent = userAgent;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public @Nullable Object getCredentials() {
        return this.token;
    }

    @Override
    public @Nullable Object getDetails() {
        return null;
    }

    @Override
    public @Nullable Object getPrincipal() {
        return null;
    }

    @Override
    public boolean isAuthenticated() {
        return this.authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.authenticated = true;
    }

    @Override
    public String getName() {
        return "Link Authentication: ip=" + this.ipAddress + "; user_agent=" + this.userAgent + ";";
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public String toString() {
        return "<LinkAuthentication: token=" + this.token.getScrubbed() + "; ipAddress=" + this.ipAddress +
                "; userAgent=" + this.userAgent + ";>";
    }
}
