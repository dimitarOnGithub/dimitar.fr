package fr.dimitar.web.posts;

import fr.dimitar.web.posts.dto.PostModel;
import fr.dimitar.web.posts.exceptions.PostNotFoundException;
import fr.dimitar.web.posts.mapper.PostMapper;
import fr.dimitar.web.posts.specifications.PostSpecificationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PostService {

    private final PostsRepository postsRepository;

    @Autowired
    public PostService(PostsRepository postsRepository){
        this.postsRepository = postsRepository;
    }

    public PostModel publishPost(PostModel post) {
        Post createdPost = this.postsRepository.saveAndFlush(PostMapper.fromModelToEntity(post));
        return PostMapper.fromEntityToModel(createdPost);
    }

    public void deletePost(Long postId) {
        this.postsRepository.deleteById(postId);
    }

    public List<PostModel> getPostsWithLimit(int limit) {
        Sort sort = Sort.by(Sort.Direction.DESC, "publishedDate");
        Pageable page = PageRequest.of(0, limit, sort);
        Specification<Post> specification = new PostSpecificationBuilder()
                .publishedOnly()
                .build();

        return this.postsRepository.findAll(specification, page)
                .map(PostMapper::fromEntityToModel)
                .toList();
    }

    public List<PostModel> getPosts() {
        Sort sort = Sort.by(Sort.Direction.DESC, "publishedDate");
        Specification<Post> specification = new PostSpecificationBuilder()
                .publishedOnly()
                .build();

        return this.postsRepository.findAll(specification, sort)
                .stream()
                .map(PostMapper::fromEntityToModel)
                .toList();
    }

    public List<PostModel> getDrafts() {
        Sort sort = Sort.by(Sort.Direction.DESC, "publishedDate");
        Specification<Post> specification = new PostSpecificationBuilder()
                .draftsOnly()
                .build();

        return this.postsRepository.findAll(specification, sort)
                .stream()
                .map(PostMapper::fromEntityToModel)
                .toList();
    }

    public PostModel getPostById(Long postId) {
        Optional<Post> post = this.postsRepository.findById(postId);
        return post.map(PostMapper::fromEntityToModel).orElseThrow(() -> new PostNotFoundException(postId));
    }

    public PostModel getPublishedPostById(Long postId) {
        Specification<Post> spec = new PostSpecificationBuilder()
                .publishedOnly()
                .byId(postId)
                .build();
        Optional<Post> post = this.postsRepository.findOne(spec);
        return post.map(PostMapper::fromEntityToModel).orElseThrow(() -> new PostNotFoundException(postId));
    }

    public PostModel updatePost(Long postId, PostModel postData) {
        Post post = this.postsRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId));
        post.setTitle(postData.getTitle());
        post.setContent(postData.getContent());
        post.setDraft(postData.isDraft());
        this.postsRepository.saveAndFlush(post);
        return PostMapper.fromEntityToModel(post);
    }

    public PostModel findPrevious(Long postId) {
        Optional<Post> post = this.postsRepository.findPrevious(postId, Limit.of(1));
        return post.map(PostMapper::fromEntityToModel).orElse(null);
    }

    public PostModel findNext(Long postId) {
        Optional<Post> post = this.postsRepository.findNext(postId, Limit.of(1));
        return post.map(PostMapper::fromEntityToModel).orElse(null);
    }

}
