package fr.dimitar.web.guestbook;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface GuestbookRepository extends JpaRepository<GuestbookEntry, Long>, JpaSpecificationExecutor<GuestbookEntry> {

    @Query("""
            SELECT entry
            FROM GuestbookEntry entry
            WHERE entry.ipAddress = :ipAddress
            """)
    public Optional<GuestbookEntry> findByIpAddress(String ipAddress);

}
