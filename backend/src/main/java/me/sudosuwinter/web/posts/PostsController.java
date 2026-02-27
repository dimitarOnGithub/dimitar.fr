package me.sudosuwinter.web.posts;

import me.sudosuwinter.web.posts.dto.PostRequest;
import me.sudosuwinter.web.posts.dto.PostResponse;
import me.sudosuwinter.web.posts.exceptions.PostNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import java.net.URI;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200/")
public class PostsController {

    private final PostService postService;

    @Autowired
    public PostsController(PostService postService){
        this.postService = postService;
    }

    @GetMapping("/posts")
    public PagedModel<PostResponse> getPageablePost(@RequestParam(name = "page", defaultValue = "0") int currentPage) {
        return new PagedModel<>(this.postService.getPostsByPage(currentPage));
    }

    @PostMapping("/posts")
    public ResponseEntity<?> publishPost(@RequestBody PostRequest newPost) {
        PostResponse postResponse = this.postService.publishPost(newPost);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{newPostId}").buildAndExpand(postResponse.id())
                .toUri();
        return ResponseEntity
                .status(201)
                .location(location)
                .build();
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<?> getPostById(@PathVariable Long id){
        Optional<PostResponse> post = this.postService.getPostById(id);
        if (post.isEmpty()) {
            throw new PostNotFoundException(id);
        }
        return ResponseEntity.of(post);
    }

}
