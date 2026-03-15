package fr.dimitar.web.posts;

import fr.dimitar.web.posts.dto.PostRequest;
import fr.dimitar.web.posts.dto.PostResponse;
import fr.dimitar.web.posts.exceptions.PostNotFoundException;
import fr.dimitar.web.posts.filters.PostsFilter;
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
    public PagedModel<PostResponse> getPosts(PostsFilter postsFilter) {
        return new PagedModel<>(this.postService.getPosts(postsFilter));
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

    @GetMapping("/drafts")
    public PagedModel<PostResponse> getDrafts(PostsFilter postsFilter){
        return new PagedModel<>(this.postService.getDrafts(postsFilter));
    }


    @GetMapping("/posts/{id}")
    public ResponseEntity<?> getPostById(@PathVariable Long id){
        Optional<PostResponse> post = this.postService.getPostById(id);
        if (post.isEmpty()) {
            throw new PostNotFoundException(id);
        }
        return ResponseEntity.of(post);
    }

    @GetMapping("/posts/{id}/previous")
    public ResponseEntity<?> getPreviousPost(@PathVariable Long id){
        Optional<PostResponse> post = this.postService.findPrevious(id);
        return ResponseEntity.ok(
                post.orElseThrow(() -> new PostNotFoundException(id))
        );
    }

    @GetMapping("/posts/{id}/next")
    public ResponseEntity<?> getNextPost(@PathVariable Long id){
        Optional<PostResponse> post = this.postService.findNext(id);
        return ResponseEntity.ok(
                post.orElseThrow(() -> new PostNotFoundException(id))
        );
    }

}
