package me.sudosuwinter.web.posts.dto;

import java.time.ZonedDateTime;

public record PostResponse (
        Long id,
        String title,
        String content,
        ZonedDateTime publishedDate,
        boolean isADraft){}
