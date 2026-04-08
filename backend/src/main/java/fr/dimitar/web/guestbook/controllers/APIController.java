package fr.dimitar.web.guestbook.controllers;

import fr.dimitar.web.guestbook.GuestbookService;
import fr.dimitar.web.guestbook.dto.GuestbookModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("guestbookApiController")
@Profile("api")
public class APIController {

    private final GuestbookService guestbookService;

    @Autowired
    public APIController(GuestbookService guestbookService) {
        this.guestbookService = guestbookService;
    }

    @GetMapping("/guestbook")
    public List<GuestbookModel> getGuestbook() {
        return this.guestbookService.getAllEntries();
    }

}
