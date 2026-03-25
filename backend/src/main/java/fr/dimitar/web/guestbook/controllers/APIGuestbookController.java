package fr.dimitar.web.guestbook.controllers;

import fr.dimitar.web.guestbook.dto.GuestbookRequest;
import fr.dimitar.web.guestbook.dto.GuestbookResponse;
import fr.dimitar.web.guestbook.filters.GuestbookFilter;
import fr.dimitar.web.guestbook.services.APIGuestbookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class APIGuestbookController {

    private final APIGuestbookService guestbookService;

    @Autowired
    public APIGuestbookController(APIGuestbookService guestbookService) {
        this.guestbookService = guestbookService;
    }

    @GetMapping("/api/guestbook")
    public PagedModel<GuestbookResponse> getGuestbook(GuestbookFilter guestbookFilter) {
        return new PagedModel<>(this.guestbookService.getGuestbook(guestbookFilter));
    }

    @PostMapping("/api/guestbook")
    public ResponseEntity<?> postGuestbook(@RequestBody GuestbookRequest guestbookRequest){
        this.guestbookService.postGuestbook(guestbookRequest);
        return ResponseEntity.status(201).build();
    }

}
