package fr.dimitar.web.posts.mapper;

import fr.dimitar.web.posts.Post;
import fr.dimitar.web.posts.dto.PostRequest;
import fr.dimitar.web.posts.dto.PostResponse;

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
