package fr.dimitar.web.posts.specifications;

import fr.dimitar.web.posts.Post;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class PostSpecification {

    private PostSpecification() {}

    public static Specification<Post> postIdsIn(List<Integer> ids) {
        return (root, query, cb) -> root.get("id").in(ids);
    }



}
