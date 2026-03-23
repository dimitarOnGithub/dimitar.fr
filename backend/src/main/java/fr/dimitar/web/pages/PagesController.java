package fr.dimitar.web.pages;

import fr.dimitar.web.guestbook.forms.GuestbookForm;
import fr.dimitar.web.guestbook.services.JteGuestbookService;
import fr.dimitar.web.posts.dto.PostModel;
import fr.dimitar.web.posts.services.JtePostService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class PagesController {

    private final JtePostService postService;
    private final JteGuestbookService guestbookService;

    @Autowired
    public PagesController(JtePostService postService, JteGuestbookService guestbookService) {
        this.postService = postService;
        this.guestbookService = guestbookService;
    }

    @GetMapping("/")
    public String indexPage(Model model){
        List<PostModel> posts = this.postService.getRecentPosts(6);
        model.addAttribute("posts", posts);
        return "index";
    }

    @GetMapping("/about")
    public String aboutPage() {
        return "about";
    }

    @GetMapping("/guestbook")
    public String guestbookPage(Model model, CsrfToken token, HttpServletRequest request) {

        String remoteIpAddress = request.getRemoteAddr();
        System.out.println("Request received for ip: " + remoteIpAddress);

        var entry = this.guestbookService.findByIpAddress(remoteIpAddress);
        if (entry.isEmpty()) {
            model.addAttribute("guestbookForm", new GuestbookForm());
        } else {
            model.addAttribute("formSaved", true);
        }
        model.addAttribute("csrfToken", token);

        List<GuestbookForm> listOfEntries = this.guestbookService.getGuestbook();
        model.addAttribute("listOfEntries", listOfEntries);

        return "guestbook";
    }

    @PostMapping("/guestbook")
    public String postGuestbook(GuestbookForm guestbookForm, Model model, HttpServletRequest request) {
        if (guestbookForm.getAlias() != null) {
            System.out.println("Alias has been filled out, returning a redirect");
            return "redirect:http://localhost";
        }
        String remoteIpAddress = request.getRemoteAddr();
        guestbookForm.setIpAddress(remoteIpAddress);
        this.guestbookService.postGuestbook(guestbookForm);
        model.addAttribute("guestbookForm", null);
        List<GuestbookForm> listOfEntries = this.guestbookService.getGuestbook();
        model.addAttribute("listOfEntries", listOfEntries);
        return "redirect:/guestbook";
    }
}
