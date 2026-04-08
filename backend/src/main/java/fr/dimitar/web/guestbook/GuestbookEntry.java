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
    @ColumnDefault(value = "null")
    private String username;

    @Column(name = "website")
    private String userWebsite;

    @Column(nullable = false, name = "approved")
    @ColumnDefault(value = "0")
    private boolean approved;

    @Column(nullable = false, name="ip_address")
    private String ipAddress;

    public GuestbookEntry() {}

    public GuestbookEntry(String content, String ipAddress) {
        this(content, null, null, ipAddress);
    }

    public GuestbookEntry(String content, String username, String ipAddress){
        this(content, username, null, ipAddress);
    }

    public GuestbookEntry(String content, String username, String userWebsite, String ipAddress) {
        this.content = content;
        this.username = username;
        this.userWebsite = userWebsite;
        this.ipAddress = ipAddress;
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

    public String getIpAddress() {
        return ipAddress;
    }

}
