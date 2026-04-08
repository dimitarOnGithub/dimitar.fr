package fr.dimitar.web.guestbook.forms;

import fr.dimitar.web.guestbook.dto.GuestbookModel;

public class GuestbookForm {

    private String content;
    private String username;
    private String userWebsite;
    private String alias;
    private String ipAddress;

    public GuestbookForm() {
        this.content = null;
        this.username = null;
        this.userWebsite = null;
        this.alias = null;
        this.ipAddress = null;
    }

    public String getContent() {
        if (content.isBlank()) {
            return null;
        }
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUsername() {
        if ( username == null || username.isBlank()) {
            return null;
        }
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserWebsite() {
        if (userWebsite == null || userWebsite.isBlank()) {
            return null;
        }
        return userWebsite;
    }

    public void setUserWebsite(String userWebsite) {
        this.userWebsite = userWebsite;
    }

    public String getAlias() {
        if (alias == null || alias.isBlank()) {
            return null;
        }
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public GuestbookModel toModel() {
        var model = new GuestbookModel();
        model.setContent(this.content);
        model.setUsername(this.username);
        model.setUserWebsite(this.userWebsite);
        model.setIpAddress(this.ipAddress);
        return model;
    }

}
