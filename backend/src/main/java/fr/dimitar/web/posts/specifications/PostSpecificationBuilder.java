package fr.dimitar.web.posts.specifications;

import fr.dimitar.web.posts.Post;
import org.springframework.data.jpa.domain.Specification;


public class PostSpecificationBuilder {

    private Specification<Post> spec = Specification.allOf();

    public Specification<Post> build() {
        return this.spec;
    }

    public PostSpecificationBuilder draftsOnly() {
        this.spec = this.spec.and(PostSpecification.draftsOnly(true));
        return this;
    }

    public PostSpecificationBuilder publishedOnly() {
        this.spec = this.spec.and(PostSpecification.draftsOnly(false));
        return this;
    }

    public PostSpecificationBuilder byId(Long postId) {
        this.spec = this.spec.and(PostSpecification.specificId(postId));
        return this;
    }

}
