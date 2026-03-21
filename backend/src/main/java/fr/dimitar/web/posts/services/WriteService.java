package fr.dimitar.web.posts.services;

import fr.dimitar.web.posts.dto.PostRequest;
import fr.dimitar.web.posts.dto.PostResponse;
import fr.dimitar.web.posts.filters.PostsFilter;
import org.springframework.data.domain.Page;

public interface WriteService {

    PostResponse publishPost(PostRequest post);

    void deletePost(Long postId);

    Page<PostResponse> getDrafts(PostsFilter postsFilter);

}
