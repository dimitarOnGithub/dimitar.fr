package fr.dimitar.web.posts;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.data.domain.Limit;
import org.springframework.test.context.jdbc.Sql;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Sql(statements = "TRUNCATE TABLE posts RESTART IDENTITY", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts={"classpath:data.sql"})
class PostsRepositoryTest {

    @Autowired
    private PostsRepository repository;

    @Test
    void save() {
        Post testPost = new Post("Test title", "Test content", false);
        this.repository.save(testPost);
        assertThat(testPost.getId()).isNotNull();
        assertThat(testPost.getId()).isEqualTo(7L);
        assertThat(testPost.getTitle()).isEqualTo("Test title");
        assertThat(testPost.getContent()).isEqualTo("Test content");
        assertThat(testPost.getPublishedDate()).isInstanceOf(ZonedDateTime.class);
        this.repository.delete(testPost);
    }

    @Test
    void findById() {
        Optional<Post> testPost = this.repository.findById(1L);
        assertThat(testPost.isPresent()).isEqualTo(true);
        assertThat(testPost.get().getId()).isNotNull();
        assertThat(testPost.get().getTitle()).isEqualTo("Test Post ID: 1");
        assertThat(testPost.get().getContent()).isEqualTo("Test");
    }

    @Test
    void findAll() {
        List<Post> postsList = this.repository.findAll();
        assertThat(postsList.size()).isEqualTo(6);
    }

    @Test
    void findByTitle(){
        Optional<Post> post = this.repository.findByTitle("Test Post ID: 3");
        assertThat(post.isPresent()).isEqualTo(true);
        assertThat(post.get().getId()).isEqualTo(3L);
        assertThat(post.get().isADraft()).isEqualTo(false);
    }

    @Test
    void verifyIsDraft(){
        Optional<Post> post = this.repository.findByTitle("Draft Post ID: 2");
        assertThat(post.isPresent()).isEqualTo(true);
        assertThat(post.get().getId()).isEqualTo(2L);
        assertThat(post.get().getTitle()).isEqualTo("Draft Post ID: 2");
        assertThat(post.get().isADraft()).isTrue();
    }

    @Test
    void findPrevious() {
        Optional<Post> post = this.repository.findPrevious(3L, Limit.of(1));
        assertThat(post.isPresent()).isEqualTo(true);
        assertThat(post.get().getId()).isEqualTo(1L);
        assertThat(post.get().getTitle()).isEqualTo("Test Post ID: 1");
        assertThat(post.get().isADraft()).isFalse();
    }

    @Test
    void findNext() {
        Optional<Post> post = this.repository.findNext(3L, Limit.of(1));
        assertThat(post.isPresent()).isEqualTo(true);
        assertThat(post.get().getId()).isEqualTo(5L);
        assertThat(post.get().getTitle()).isEqualTo("Test Post ID: 5");
        assertThat(post.get().isADraft()).isFalse();
    }

}