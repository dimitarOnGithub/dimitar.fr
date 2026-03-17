package fr.dimitar.web.posts.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Max;

public class PostRequest {

    @NotNull(message = "Title cannot be null")
    @NotBlank(message = "Title cannot be empty")
    @Max(value = 100, message = "Title cannot be longer than 100 characters")
    private final String title;

    @NotNull(message = "Content cannot be null")
    @NotBlank(message = "Content cannot be empty")
    private final String content;

    @NotNull(message = "Username cannot be null")
    @NotBlank(message = "Username cannot be empty")
    private final String username;

    @NotNull(message = "Draft status must be set to either true or false")
    private final boolean isADraft;

    public PostRequest(String title, String content, String username, boolean isADraft) {
        this.title = title;
        this.content = content;
        this.username = username;
        this.isADraft = isADraft;
    }

    public String getTitle() {
        return this.title;
    }

    public String getContent() {
        return this.content;
    }

    public String getUsername() {
        return this.username;
    }

    public boolean isADraft() {
        return this.isADraft;
    }
}
