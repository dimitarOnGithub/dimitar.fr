package me.sudosuwinter.web.posts.mapper;

import me.sudosuwinter.web.posts.Post;
import me.sudosuwinter.web.posts.dto.PostRequest;
import me.sudosuwinter.web.posts.dto.PostResponse;

public interface PostMapper {

    static PostResponse fromEntityToResponse(Post post) {
        return new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getPublishedDate(),
                post.isADraft()
        );
    }

    static Post fromRequestToEntity(PostRequest postRequest) {
        return new Post(
                postRequest.getTitle(),
                postRequest.getContent(),
                postRequest.isADraft()
        );
    }
}
