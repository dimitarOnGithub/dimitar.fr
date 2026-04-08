package fr.dimitar.web.contact;

import fr.dimitar.web.contact.forms.ContactForm;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ContactController {

    private final ContactService service;

    @Autowired
    public ContactController(ContactService service) {
        this.service = service;
    }

    @GetMapping("/contact")
    public String contactPage(Model model, CsrfToken token, HttpServletRequest request){
        boolean alreadySubmitted = false;
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("contactFormSubmitted".equals(cookie.getName())) {
                    alreadySubmitted = true;
                    break;
                }
            }
        }
        if(alreadySubmitted) {
            model.addAttribute("contactForm", null);
        } else {
            model.addAttribute("csrfToken", token);
            model.addAttribute("contactForm", new ContactForm());
        }
        return "pages/contact";
    }

    @PostMapping("/contact")
    public String postContact(ContactForm contactForm, HttpServletResponse response) {
        if (contactForm.getName() != null) {
            return "redirect:http://localhost/";
        }
        this.service.processContactForm(contactForm);
        Cookie cookie = new Cookie("contactFormSubmitted", "true");
        cookie.setPath("/");
        response.addCookie(cookie);
        return "redirect:/contact";
    }

}
