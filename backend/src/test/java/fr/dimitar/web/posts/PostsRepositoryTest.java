package fr.dimitar.web.posts;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.test.context.jdbc.Sql;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts={"classpath:schemas/test_posts.sql"})
class PostsRepositoryTest {

    @Autowired
    private PostsRepository repository;

    @Test
    void save() {
        Post testPost = new Post("Test title", "Test content", false);
        this.repository.save(testPost);
        assertThat(testPost.getId()).isNotNull();
        assertThat(testPost.getId()).isEqualTo(4L);
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
        assertThat(testPost.get().getTitle()).isEqualTo("Test Post 1");
        assertThat(testPost.get().getContent()).isEqualTo("Test");
    }

    @Test
    void findAll() {
        List<Post> postsList = this.repository.findAll();
        assertThat(postsList.size()).isEqualTo(3);
    }

    @Test
    void findByTitle(){
        Optional<Post> post = this.repository.findByTitle("Test Post 2");
        assertThat(post.isPresent()).isEqualTo(true);
        assertThat(post.get().getId()).isEqualTo(2L);
        assertThat(post.get().isADraft()).isEqualTo(false);
    }

    @Test
    void verifyIsDraft(){
        Optional<Post> post = this.repository.findByTitle("Draft Post 1");
        assertThat(post.isPresent()).isEqualTo(true);
        assertThat(post.get().getId()).isEqualTo(3L);
        assertThat(post.get().getTitle()).isEqualTo("Draft Post 1");
        assertThat(post.get().isADraft()).isTrue();
    }

}