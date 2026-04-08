package fr.dimitar.web.posts;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, length = 1000000)
    private String content;

    @Column(nullable = false, name = "published_date")
    @CreationTimestamp
    private Instant publishedDate;

    @Column(nullable = false, name = "is_draft")
    private boolean isDraft;

    public Post(){};

    public Post(String title, String content, boolean isDraft){
        this.title = title;
        this.content = content;
        this.isDraft = isDraft;
    }

    public Long getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getContent() {
        return this.content;
    }

    public ZonedDateTime getPublishedDate() {
        return this.publishedDate.atZone(ZoneId.of("UTC"));
    }

    public boolean isADraft() {
        return this.isDraft;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDraft(boolean draft) {
        isDraft = draft;
    }
}
