package me.sudosuwinter.web.posts;

import me.sudosuwinter.web.posts.dto.PostRequest;
import me.sudosuwinter.web.posts.dto.PostResponse;
import me.sudosuwinter.web.posts.mapper.PostMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Profile("!test")
public class PostServiceImpl implements PostService {

    private final PostsRepository postsRepository;

    @Autowired
    public PostServiceImpl(PostsRepository postsRepository){
        this.postsRepository = postsRepository;
    }

    @Override
    public PostResponse publishPost(PostRequest post) {
        Post createdPost = this.postsRepository.saveAndFlush(PostMapper.fromRequestToEntity(post));
        return PostMapper.fromEntityToResponse(createdPost);
    }

    @Override
    public void deletePost(Long postId) {
        this.postsRepository.deleteById(postId);
    }

    @Override
    public List<PostResponse> getAllPosts() {
        return this.postsRepository.findAll().stream()
                .map(PostMapper::fromEntityToResponse)
                .toList();
    }

    @Override
    public Optional<PostResponse> getPostById(Long postId) {
        Optional<Post> post = this.postsRepository.findById(postId);
        return post.map(PostMapper::fromEntityToResponse);
    }

    @Override
    public Page<PostResponse> getPostsByPage(int pageNumber, int maxSize) {
        Sort sort = Sort.by(Sort.Direction.ASC, "publishedDate");
        Pageable pageable = PageRequest.of(pageNumber, maxSize, sort);
        return this.postsRepository.findAll(pageable).map(PostMapper::fromEntityToResponse);
    }

    @Override
    public Page<PostResponse> getPostsByPage(int pageNumber) {
        return this.getPostsByPage(pageNumber, 5);
    }

}
