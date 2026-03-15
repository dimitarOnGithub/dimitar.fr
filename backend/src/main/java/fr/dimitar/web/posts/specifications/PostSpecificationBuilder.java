package fr.dimitar.web.posts.specifications;

import fr.dimitar.web.posts.Post;
import fr.dimitar.web.posts.filters.PostsFilter;
import org.springframework.data.jpa.domain.Specification;


public class PostSpecificationBuilder {

    public static Specification<Post> fromFilter(PostsFilter filter) {
        Specification<Post> spec = Specification.allOf();

        spec = spec.and(PostSpecification.draftsOnly(filter.isDraftsOnly()));

        return spec;
    }
}
