package fr.dimitar.web.guestbook;

import fr.dimitar.web.guestbook.dto.GuestbookRequest;
import fr.dimitar.web.guestbook.dto.GuestbookResponse;
import fr.dimitar.web.guestbook.filters.GuestbookFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200/")
public class GuestbookController {

    private final APIGuestbookService guestbookService;

    @Autowired
    public GuestbookController(APIGuestbookService guestbookService) {
        this.guestbookService = guestbookService;
    }

    @GetMapping("/guestbook")
    public PagedModel<GuestbookResponse> getGuestbook(GuestbookFilter guestbookFilter) {
        return new PagedModel<>(this.guestbookService.getGuestbook(guestbookFilter));
    }

    @PostMapping("/guestbook")
    public ResponseEntity<?> postGuestbook(@RequestBody GuestbookRequest guestbookRequest){
        this.guestbookService.postGuestbook(guestbookRequest);
        return ResponseEntity.status(201).build();
    }

}
