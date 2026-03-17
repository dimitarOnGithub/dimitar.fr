package fr.dimitar.web.guestbook;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "guestbook")
public class GuestbookEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Long id;

    @Column(nullable = false, length = 1000)
    private String content;

    @Column(name = "username")
    private String username;

    @Column(name = "website")
    private String userWebsite;

    @Column(nullable = false, name = "approved", columnDefinition = "INT", length = 1)
    @ColumnDefault(value = "0")
    private boolean approved;

    public GuestbookEntry() {}

    public GuestbookEntry(String content) {
        this(content, null, null);
    }

    public GuestbookEntry(String content, String username){
        this(content, username, null);
    }

    public GuestbookEntry(String content, String username, String userWebsite) {
        this.content = content;
        this.username = username;
        this.userWebsite = userWebsite;
    }

    public Long getId() {
        return id;
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

    public boolean isApproved() {
        return approved;
    }

}
