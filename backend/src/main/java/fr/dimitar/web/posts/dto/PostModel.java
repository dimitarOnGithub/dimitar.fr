package fr.dimitar.web.posts.dto;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class PostModel {

    private final Long id;
    private final String title;
    private final String content;
    private final ZonedDateTime publishedDate;
    private final boolean isADraft;

    public PostModel(Long id, String title, String content, ZonedDateTime publishedDate, boolean isADraft) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.publishedDate = publishedDate;
        this.isADraft = isADraft;
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

    public String getPublishedDate() {
        return publishedDate.format(DateTimeFormatter.ofPattern("dd MMM, yyyy"));
    }

    public boolean isADraft() {
        return isADraft;
    }
}
