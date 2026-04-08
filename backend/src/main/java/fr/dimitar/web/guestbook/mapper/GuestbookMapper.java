package fr.dimitar.web.guestbook.mapper;

import fr.dimitar.web.guestbook.GuestbookEntry;
import fr.dimitar.web.guestbook.dto.GuestbookModel;

public interface GuestbookMapper {

    static GuestbookModel fromEntityToModel(GuestbookEntry entry) {
        var model = new GuestbookModel();
        model.setContent(entry.getContent());
        model.setUsername(entry.getUsername());
        model.setUserWebsite(entry.getUserWebsite());
        model.setIpAddress(entry.getIpAddress());
        model.setApproved(entry.isApproved());
        return model;
    }

    static GuestbookEntry fromModelToEntity(GuestbookModel model) {
        return new GuestbookEntry(
                model.getContent(),
                model.getUsername(),
                model.getUserWebsite(),
                model.getIpAddress()
        );
    }
}
