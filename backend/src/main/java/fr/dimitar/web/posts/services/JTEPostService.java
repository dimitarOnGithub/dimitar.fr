package fr.dimitar.web.posts.services;

import fr.dimitar.web.posts.Post;
import fr.dimitar.web.posts.PostsRepository;
import fr.dimitar.web.posts.dto.PostModel;
import fr.dimitar.web.posts.dto.PostResponse;
import fr.dimitar.web.posts.filters.PostsFilter;
import fr.dimitar.web.posts.mapper.PostMapper;
import fr.dimitar.web.posts.specifications.PostSpecification;
import fr.dimitar.web.posts.specifications.PostSpecificationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JTEPostService {

    private final PostsRepository postsRepository;

    @Autowired
    public JTEPostService(PostsRepository postsRepository) {
        this.postsRepository = postsRepository;
    }

    public List<PostModel> getRecentPosts(int limit) {
        Sort sort = Sort.by(Sort.Direction.DESC, "publishedDate");
        Pageable pageable = PageRequest.of(0, limit, sort);
        PostsFilter filter = new PostsFilter();
        filter.setDraftsOnly(false);
        filter.setPage(0);
        filter.setPageSize(6);
        Specification<Post> spec = PostSpecificationBuilder.fromFilter(filter);
        var a = this.postsRepository.findAll(spec, pageable);
        return a.stream()
                .map(PostMapper::fromEntityToModel)
                .toList();
    }

    public List<PostModel> getPosts() {
        Sort sort = Sort.by(Sort.Direction.DESC, "publishedDate");
        PostsFilter filter = new PostsFilter();
        filter.setDraftsOnly(false);
        Specification<Post> spec = PostSpecificationBuilder.fromFilter(filter);
        var a = this.postsRepository.findAll(spec, sort);
        return a.stream()
                .map(PostMapper::fromEntityToModel)
                .toList();
    }

    public Optional<PostModel> getPostById(Long postId) {
        var spec = PostSpecification
                .specificId(postId)
                .and(PostSpecification.draftsOnly(false));
        return this.postsRepository.findOne(spec)
                .map(PostMapper::fromEntityToModel);

    }

    public Optional<PostModel> findPrevious(Long postId) {
        Optional<Post> post = this.postsRepository.findPrevious(postId, Limit.of(1));
        return post.map(PostMapper::fromEntityToModel);
    }

    public Optional<PostModel> findNext(Long postId) {
        Optional<Post> post = this.postsRepository.findNext(postId, Limit.of(1));
        return post.map(PostMapper::fromEntityToModel);
    }
}
