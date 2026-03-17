package fr.dimitar.web.guestbook.specifications;

import fr.dimitar.web.guestbook.GuestbookEntry;
import org.springframework.data.jpa.domain.Specification;

public class GuestbookSpecification {

    private GuestbookSpecification() {}

    public static Specification<GuestbookEntry> approvedOnly(boolean approvedOnly) {
        return (
                (root, query, criteriaBuilder)
                        -> root.get("approved").equalTo(approvedOnly)
        );
    }

}
