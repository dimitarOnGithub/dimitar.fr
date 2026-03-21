package fr.dimitar.web.posts.services;

import fr.dimitar.web.posts.dto.PostResponse;
import fr.dimitar.web.posts.filters.PostsFilter;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface ReadService {

    Page<PostResponse> getPosts(PostsFilter postsFilter);

    Optional<PostResponse> getPostById(Long postId);

    Optional<PostResponse> findPrevious(Long postId);

    Optional<PostResponse> findNext(Long postId);
}
