package fr.dimitar.web.guestbook.dto;

public record GuestbookResponse (
        String content,
        String username,
        String userWebsite,
        boolean isApproved
){}
