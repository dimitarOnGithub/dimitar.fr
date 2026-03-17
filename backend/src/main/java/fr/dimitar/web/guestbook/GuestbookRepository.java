package fr.dimitar.web.guestbook;

import fr.dimitar.web.guestbook.specifications.GuestbookSpecification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface GuestbookRepository extends JpaRepository<GuestbookEntry, Long>, JpaSpecificationExecutor<GuestbookEntry> {

}
