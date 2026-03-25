package fr.dimitar.web.guestbook.mapper;

import fr.dimitar.web.guestbook.GuestbookEntry;
import fr.dimitar.web.guestbook.dto.GuestbookModel;
import fr.dimitar.web.guestbook.forms.GuestbookForm;
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

    static GuestbookModel fromEntityToModel(GuestbookEntry entry) {
        var model = new GuestbookModel();
        model.setContent(entry.getContent());
        model.setUsername(entry.getUsername());
        model.setUserWebsite(entry.getUserWebsite());
        model.setIpAddress(entry.getIpAddress());
        model.setApproved(entry.isApproved());
        return model;
    }

    static GuestbookEntry fromModelToEntry(GuestbookForm model) {
        return new GuestbookEntry(
                model.getContent(),
                model.getUsername(),
                model.getUserWebsite(),
                model.getIpAddress()
        );
    }
}
