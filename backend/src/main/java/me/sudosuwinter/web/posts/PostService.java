package me.sudosuwinter.web.posts;

import me.sudosuwinter.web.posts.dto.PostRequest;
import me.sudosuwinter.web.posts.dto.PostResponse;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface PostService {

    PostResponse publishPost(PostRequest post);

    void deletePost(Long postId);

    List<PostResponse> getAllPosts();

    Optional<PostResponse> getPostById(Long postId);

    Page<PostResponse> getPostsByPage(int pageNumber, int maxSize);

    Page<PostResponse>getPostsByPage(int pageNumber);

}
