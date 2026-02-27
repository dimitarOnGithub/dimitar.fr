package me.sudosuwinter.web.posts;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostsRepository extends JpaRepository<Post, Long> {

    Optional<Post> findByTitle(String title);

}
