package fr.dimitar.web.posts;

import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PostsRepository extends JpaRepository<Post, Long>, JpaSpecificationExecutor<Post> {

    Optional<Post> findByTitle(String title);

    @Query("""
            SELECT post
            FROM Post post
            WHERE post.isDraft = false
            AND post.publishedDate < (SELECT p2.publishedDate FROM Post p2 WHERE p2.id=:id)
            ORDER BY post.publishedDate DESC
            """)
    Optional<Post> findPrevious(Long id, Limit limit);

    @Query("""
            SELECT post
            FROM Post post
            WHERE post.isDraft = false
            AND post.publishedDate > (SELECT p2.publishedDate FROM Post p2 WHERE p2.id=:id)
            ORDER BY post.publishedDate ASC
            """)
    Optional<Post> findNext(Long id, Limit limit);

}
