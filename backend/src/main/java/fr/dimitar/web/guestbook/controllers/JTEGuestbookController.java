package fr.dimitar.web.guestbook.controllers;

import fr.dimitar.web.guestbook.dto.GuestbookModel;
import fr.dimitar.web.guestbook.forms.GuestbookForm;
import fr.dimitar.web.guestbook.services.JTEGuestbookService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class JTEGuestbookController {

    private final JTEGuestbookService guestbookService;

    @Autowired
    public JTEGuestbookController(JTEGuestbookService guestbookService) {
        this.guestbookService = guestbookService;
    }

    @GetMapping("/guestbook")
    public String guestbookPage(Model model, CsrfToken token, HttpServletRequest request) {

        String remoteIpAddress = request.getRemoteAddr();
        System.out.println("Request received for ip: " + remoteIpAddress);

        var entry = this.guestbookService.findByIpAddress(remoteIpAddress);
        if (entry.isEmpty()) {
            model.addAttribute("guestbookForm", new GuestbookForm());
        } else if (!entry.get().isApproved()) {
            model.addAttribute("formSaved", true);
        }
        model.addAttribute("csrfToken", token);

        List<GuestbookModel> listOfEntries = this.guestbookService.getGuestbook();
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
        List<GuestbookModel> listOfEntries = this.guestbookService.getGuestbook();
        model.addAttribute("listOfEntries", listOfEntries);
        return "redirect:/guestbook";
    }
}
