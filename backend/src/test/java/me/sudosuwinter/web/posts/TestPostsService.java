package me.sudosuwinter.web.posts;

import me.sudosuwinter.web.posts.dto.PostRequest;
import me.sudosuwinter.web.posts.dto.PostResponse;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.util.*;

@Service
@Profile("test")
public class TestPostsService implements PostService {

    private final List<PostResponse> postsRepo = new ArrayList<>();

    public TestPostsService() {
        this.postsRepo.add(
                new PostResponse(
                        1L, "Test Post 1", "Test",
                        Instant.now().atZone(ZoneId.of("Europe/Paris")),
                        false)
        );
        this.postsRepo.add(
                new PostResponse(
                2L, "Test Post 2", "Test",
                        Instant.now().atZone(ZoneId.of("Europe/Paris")),
                false)
        );
    }

    @Override
    public PostResponse publishPost(PostRequest post) {
        Long lastId = (long) this.postsRepo.size();
        PostResponse postResponse = new PostResponse(
                lastId + 1,
                post.getTitle(),
                post.getContent(),
                Instant.now().atZone(ZoneId.of("Europe/Paris")),
                post.isADraft()
        );
        this.postsRepo.add(postResponse);
        return postResponse;
    }

    @Override
    public void deletePost(Long postId) {
        this.postsRepo.removeIf(postResponse -> postResponse.id().equals(postId));
    }

    @Override
    public List<PostResponse> getAllPosts() {
        return new ArrayList<>(this.postsRepo);
    }

    @Override
    public Optional<PostResponse> getPostById(Long postId) {
        for(var p: this.postsRepo){
            if(p.id().equals(postId)){
                return Optional.of(p);
            }
        }
        return Optional.empty();
    }

    @Override
    public Page<PostResponse> getPostsByPage(int pageNumber, int maxSize) {
        List<PostResponse> slice = this.postsRepo.subList(pageNumber, pageNumber + maxSize);
        return new PageImpl<>(slice);
    }

    @Override
    public Page<PostResponse> getPostsByPage(int pageNumber) {
        return this.getPostsByPage(pageNumber, 1);
    }
}
