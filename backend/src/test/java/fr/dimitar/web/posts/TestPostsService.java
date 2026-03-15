package fr.dimitar.web.posts;

import fr.dimitar.web.posts.dto.PostRequest;
import fr.dimitar.web.posts.dto.PostResponse;
import fr.dimitar.web.posts.filters.PostsFilter;
import fr.dimitar.web.posts.mapper.PostMapper;
import fr.dimitar.web.posts.specifications.PostSpecificationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Profile("test")
public class TestPostsService implements PostService {

    @Autowired
    private PostsRepository repository;

    @Override
    public PostResponse publishPost(PostRequest post) {
        Post createdPost = this.repository.saveAndFlush(PostMapper.fromRequestToEntity(post));
        return PostMapper.fromEntityToResponse(createdPost);
    }

    @Override
    public void deletePost(Long postId) {
        this.repository.deleteById(postId);
    }

    @Override
    public Page<PostResponse> getPosts(PostsFilter postsFilter) {
        Sort sort = Sort.by(Sort.Direction.DESC, "publishedDate");
        Pageable pageable = PageRequest.of(postsFilter.getPage(), postsFilter.getPageSize(), sort);

        postsFilter.setDraftsOnly(false);
        Specification<Post> specification = PostSpecificationBuilder.fromFilter(postsFilter);

        return this.repository.findAll(specification, pageable).map(PostMapper::fromEntityToResponse);
    }

    @Override
    public Page<PostResponse> getDrafts(PostsFilter postsFilter) {
        Sort sort = Sort.by(Sort.Direction.DESC, "publishedDate");
        Pageable pageable = PageRequest.of(postsFilter.getPage(), postsFilter.getPageSize(), sort);

        postsFilter.setDraftsOnly(true);
        Specification<Post> specification = PostSpecificationBuilder.fromFilter(postsFilter);

        return this.repository.findAll(specification, pageable).map(PostMapper::fromEntityToResponse);
    }

    @Override
    public Optional<PostResponse> getPostById(Long postId) {
        Optional<Post> post = this.repository.findById(postId);
        return post.map(PostMapper::fromEntityToResponse);
    }

    @Override
    public Optional<PostResponse> findPrevious(Long postId) {
        Optional<Post> post = this.repository.findPrevious(postId, Limit.of(1));
        return post.map(PostMapper::fromEntityToResponse);
    }

    @Override
    public Optional<PostResponse> findNext(Long postId) {
        Optional<Post> post = this.repository.findNext(postId, Limit.of(1));
        return post.map(PostMapper::fromEntityToResponse);
    }

}
