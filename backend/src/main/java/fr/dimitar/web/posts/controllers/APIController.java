package fr.dimitar.web.posts.controllers;

import fr.dimitar.web.posts.dto.PostModel;
import fr.dimitar.web.posts.dto.PostRequest;
import fr.dimitar.web.posts.mapper.PostMapper;
import fr.dimitar.web.posts.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController("postsApiController")
@Profile("api")
public class APIController {

    private final PostService postService;

    @Autowired
    public APIController(PostService postService){
        this.postService = postService;
    }

    @GetMapping("/posts")
    public List<PostModel> getPosts() {
        return this.postService.getPosts();
    }

    @PostMapping("/posts")
    public ResponseEntity<PostModel> publishPost(@Valid @RequestBody PostRequest newPost) {
        PostModel publishPost = this.postService.publishPost(PostMapper.fromRequestToModel(newPost));
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{newPostId}").buildAndExpand(publishPost.getId())
                .toUri();
        return ResponseEntity
                .status(201)
                .location(location)
                .build();
    }

    @GetMapping("/drafts")
    public List<PostModel> getDrafts(){
        return this.postService.getDrafts();
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<PostModel> getPostById(@PathVariable Long id){
        PostModel post = this.postService.getPostById(id);
        return ResponseEntity.ok(post);
    }

    @PutMapping("/posts/{id}")
    public ResponseEntity<PostModel> updatePostById(@PathVariable Long id, @RequestBody PostRequest postData){
        PostModel updatedPost = this.postService.updatePost(id, PostMapper.fromRequestToModel(postData));
        return ResponseEntity.ok(updatedPost);
    }

    @GetMapping("/posts/{id}/previous")
    public ResponseEntity<PostModel> getPreviousPost(@PathVariable Long id){
        PostModel post = this.postService.findPrevious(id);
        return ResponseEntity.ok(post);
    }

    @GetMapping("/posts/{id}/next")
    public ResponseEntity<PostModel> getNextPost(@PathVariable Long id){
        PostModel post = this.postService.findNext(id);
        return ResponseEntity.ok(post);
    }

}
