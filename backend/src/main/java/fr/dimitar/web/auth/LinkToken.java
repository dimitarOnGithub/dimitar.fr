package fr.dimitar.web.auth;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

public class LinkToken {

    private UUID uuid;
    private Instant instant;

    public LinkToken(UUID token) {
        this.uuid = token;
        this.instant = Instant.now();
    }

    public UUID getToken() {
        return uuid;
    }

    public boolean hasExpired() {
        Duration timeElapsed = Duration.between(this.instant, Instant.now());
        return timeElapsed.toMinutes() > 5;
    }

    public String getScrubbed() {
        var stringToken = this.uuid.toString();
        var start = stringToken.substring(0, 5);
        var end = stringToken.substring(stringToken.length() - 5);
        return start + "..." + end;
    }

    public boolean equals(Object other) {
        if (! (other instanceof LinkToken otherToken)) {
            return false;
        }
        return this.uuid.equals(otherToken.getToken());
    }

    public static LinkToken generate() {
        return new LinkToken(UUID.randomUUID());
    }

    public static LinkToken fromString(String stringValue) {
        return new LinkToken(UUID.fromString(stringValue));
    }

}
