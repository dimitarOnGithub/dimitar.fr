package fr.dimitar.web.posts.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@JsonPropertyOrder({ "id", "title", "content", "publishedDateTime", "draft" })
public class PostModel {

    private final Long id;
    private final String title;
    private final String content;
    private final ZonedDateTime publishedDate;
    private final boolean draft;

    public PostModel(String title, String content, boolean draft) {
        this(null, title, content, null, draft);
    }

    public PostModel(Long id, String title, String content, ZonedDateTime publishedDate, boolean draft) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.publishedDate = publishedDate;
        this.draft = draft;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public ZonedDateTime getPublishedDateTime() {
        return this.publishedDate;
    }

    public String formatPublishedDate(String pattern) {
        return publishedDate.format(DateTimeFormatter.ofPattern(pattern));
    }

    public boolean isDraft() {
        return this.draft;
    }
}
