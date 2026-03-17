package fr.dimitar.web.guestbook.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class GuestbookRequest {

    @NotNull(message = "Content cannot be null")
    @NotBlank(message = "Content cannot be empty")
    @Max(value = 1000, message = "Content cannot be longer than 1000 characters")
    private final String content;
    private final String username;
    private final String userWebsite;

    public GuestbookRequest(String content, String username, String userWebsite) {
        this.content = content;
        this.username = username;
        this.userWebsite = userWebsite;
    }

    public String getContent() {
        return content;
    }

    public String getUsername() {
        return username;
    }

    public String getUserWebsite() {
        return userWebsite;
    }

}
