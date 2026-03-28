package fr.dimitar.web.posts.specifications;

import fr.dimitar.web.posts.Post;
import org.springframework.data.jpa.domain.Specification;

public class PostSpecification {

    private PostSpecification() {}

    public static Specification<Post> draftsOnly(boolean onlyDraftPosts) {
        return (
                (root, query, criteriaBuilder)
                        -> root.get("isDraft").equalTo(onlyDraftPosts)
        );
    }

    public static Specification<Post> specificId(Long id){
        return (
                (root, query, criteriaBuilder)
                    -> root.get("id").equalTo(id)
                );
    }

}
