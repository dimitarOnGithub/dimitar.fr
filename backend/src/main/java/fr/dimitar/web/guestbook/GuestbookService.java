package fr.dimitar.web.guestbook;

import fr.dimitar.web.guestbook.dto.GuestbookRequest;
import fr.dimitar.web.guestbook.dto.GuestbookResponse;
import fr.dimitar.web.guestbook.filters.GuestbookFilter;
import org.springframework.data.domain.Page;

public interface GuestbookService {

    Page<GuestbookResponse> getGuestbook(GuestbookFilter guestbookFilter);

    void postGuestbook(GuestbookRequest guestbookRequest);
}
