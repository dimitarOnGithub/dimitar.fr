package fr.dimitar.web.guestbook.controllers;

import fr.dimitar.web.guestbook.GuestbookService;
import fr.dimitar.web.guestbook.dto.GuestbookModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("guestbookApiController")
public class APIController {

    private final GuestbookService guestbookService;

    @Autowired
    public APIController(GuestbookService guestbookService) {
        this.guestbookService = guestbookService;
    }

    @GetMapping("/api/guestbook")
    public List<GuestbookModel> getGuestbook() {
        return this.guestbookService.getAllEntries();
    }

}
