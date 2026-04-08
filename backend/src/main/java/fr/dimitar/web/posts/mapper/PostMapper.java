package fr.dimitar.web.posts.mapper;

import fr.dimitar.web.posts.Post;
import fr.dimitar.web.posts.dto.PostModel;
import fr.dimitar.web.posts.dto.PostRequest;

public interface PostMapper {

    static PostModel fromRequestToModel(PostRequest postRequest) {
        return new PostModel(
                postRequest.getTitle(),
                postRequest.getContent(),
                postRequest.isDraft()
        );
    }

    static PostModel fromEntityToModel(Post post){
        return new PostModel(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getPublishedDate(),
                post.isADraft()
        );
    }

    static Post fromModelToEntity(PostModel model) {
        return new Post(
                model.getTitle(),
                model.getContent(),
                model.isDraft()
        );
    }
}
