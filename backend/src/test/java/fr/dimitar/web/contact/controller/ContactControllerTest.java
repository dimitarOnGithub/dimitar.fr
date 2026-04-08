package fr.dimitar.web.contact.controller;

import fr.dimitar.web.contact.ContactController;
import fr.dimitar.web.contact.ContactService;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ContactController.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("mvc")
class ContactControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ContactService contactService;

    // -------------------------
    // GET /contact
    // -------------------------

    @Test
    void shouldShowFormWhenNotSubmitted() throws Exception {
        mockMvc.perform(get("/contact"))
                .andExpect(status().isOk())
                .andExpect(view().name("pages/contact"))
                .andExpect(model().attributeExists("contactForm"));
    }

    @Test
    void shouldHideFormWhenAlreadySubmitted() throws Exception {
        Cookie cookie = new Cookie("contactFormSubmitted", "true");

        mockMvc.perform(get("/contact").cookie(cookie))
                .andExpect(status().isOk())
                .andExpect(view().name("pages/contact"))
                .andExpect(model().attributeDoesNotExist("contactForm"))
                .andExpect(model().attributeDoesNotExist("csrfToken"));
    }

    @Test
    void shouldSubmitFormAndSetCookie() throws Exception {
        mockMvc.perform(post("/contact").with(csrf())
                        .param("name", "") // important: must be empty (honeypot)
                        .param("email", "test@test.com")
                        .param("message", "Hello"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/contact"))
                .andExpect(cookie().exists("contactFormSubmitted"));

        verify(contactService).processContactForm(any());
    }

    @Test
    void shouldRedirectWhenHoneypotTriggered() throws Exception {
        mockMvc.perform(post("/contact").with(csrf())
                        .param("name", "bot") // honeypot triggered
                        .param("email", "test@test.com")
                        .param("message", "Hello"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/"));

        verify(contactService, never()).processContactForm(any());
    }

}