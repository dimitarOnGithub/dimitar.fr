package fr.dimitar.web.guestbook.mapper;

import fr.dimitar.web.guestbook.GuestbookEntry;
import fr.dimitar.web.guestbook.dto.GuestbookRequest;
import fr.dimitar.web.guestbook.dto.GuestbookResponse;

public interface GuestbookMapper {

    static GuestbookResponse fromEntityToResponse(GuestbookEntry entry) {
        return new GuestbookResponse(
                entry.getContent(),
                entry.getUsername(),
                entry.getUserWebsite(),
                entry.isApproved()
        );
    }

    static GuestbookEntry fromRequestToEntity(GuestbookRequest entryRequest) {
        return new GuestbookEntry(
                entryRequest.getContent(),
                entryRequest.getUsername(),
                entryRequest.getUserWebsite()
        );
    }
}
