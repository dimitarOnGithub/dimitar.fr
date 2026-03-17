package fr.dimitar.web.guestbook.specifications;

import fr.dimitar.web.guestbook.GuestbookEntry;
import fr.dimitar.web.guestbook.filters.GuestbookFilter;
import org.springframework.data.jpa.domain.Specification;


public class GuestbookSpecificationBuilder {

    public static Specification<GuestbookEntry> fromFilter(GuestbookFilter filter) {
        Specification<GuestbookEntry> spec = Specification.allOf();

        spec = spec.and(GuestbookSpecification.approvedOnly(filter.isApprovedOnly()));

        return spec;
    }
}
