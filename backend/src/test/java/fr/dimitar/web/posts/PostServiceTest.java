package fr.dimitar.web.posts;

import fr.dimitar.web.posts.exceptions.PostNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@Import(PostService.class)
@Sql(statements = "TRUNCATE TABLE posts RESTART IDENTITY", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts={"classpath:data.sql"})
public class PostServiceTest {

    @Autowired
    private PostsRepository postsRepository;

    @Autowired
    private PostService postService;

    @Test
    void getPostsShouldReturnPublishedPostsOnly() throws Exception {
        var postsList = this.postService.getPosts();
        for(var post: postsList) {
            assertThat(post.isDraft()).isFalse();
        }
    }

    @Test
    void getPostsWithLimit() throws Exception {
        var postsList = this.postService.getPostsWithLimit(1);
        assertThat(postsList.size()).isEqualTo(1);
        assertThat(postsList.get(0).isDraft()).isFalse();
    }

    @Test
    void getDraftsShouldReturnDraftsOnly() throws Exception {
        var draftsList = this.postService.getDrafts();
        for(var post: draftsList) {
            assertThat(post.isDraft()).isTrue();
        }
    }

    @Test
    void getPostByIdReturnsPublishedAndDrafts() throws Exception {
        var publishedPost = this.postService.getPostById(1L);
        assertThat(publishedPost.isDraft()).isFalse();

        var draftPost = this.postService.getPostById(2L);
        assertThat(draftPost.isDraft()).isTrue();
    }

    @Test
    void getPublishedPostByIdReturnsNoDrafts() throws Exception {
        var publishedPost = this.postService.getPublishedPostById(1L);
        assertThat(publishedPost.isDraft()).isFalse();

        assertThrows(PostNotFoundException.class, () -> {this.postService.getPublishedPostById(2L);});
    }
}
