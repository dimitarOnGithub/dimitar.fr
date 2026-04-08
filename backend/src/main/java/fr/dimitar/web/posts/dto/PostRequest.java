package fr.dimitar.web.posts.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class PostRequest {

    @NotNull(message = "Title cannot be null")
    @NotBlank(message = "Title cannot be empty")
    @Size(max = 100, message = "Title cannot be longer than 100 characters")
    private final String title;

    @NotNull(message = "Content cannot be null")
    @NotBlank(message = "Content cannot be empty")
    private final String content;

    @NotNull(message = "Draft status must be set to either true or false")
    private final Boolean draft;

    public PostRequest(String title, String content, boolean draft) {
        this.title = title;
        this.content = content;
        this.draft = draft;
    }

    public String getTitle() {
        return this.title;
    }

    public String getContent() {
        return this.content;
    }

    public boolean isDraft() {
        return this.draft;
    }
}
