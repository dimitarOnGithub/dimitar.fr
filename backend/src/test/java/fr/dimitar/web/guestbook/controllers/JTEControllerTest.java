package fr.dimitar.web.guestbook.controllers;

import fr.dimitar.web.guestbook.GuestbookService;
import fr.dimitar.web.guestbook.dto.GuestbookModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Optional;

@WebMvcTest(JTEController.class)
@AutoConfigureMockMvc(addFilters = false)
class JTEControllerTest {

    private static final String TEST_IP = "127.0.0.1";

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private GuestbookService guestbookService;

    @Test
    void shouldShowEmptyFormWhenNoEntryExists() throws Exception {
        when(guestbookService.findByIpAddress(TEST_IP)).thenReturn(Optional.empty());
        when(guestbookService.getPublishedEntries()).thenReturn(List.of());

        mockMvc.perform(get("/guestbook")
                        .with(request -> { request.setRemoteAddr(TEST_IP); return request; }))
                .andExpect(status().isOk())
                .andExpect(view().name("pages/guestbook"))
                .andExpect(model().attributeExists("guestbookForm"))
                .andExpect(model().attributeExists("listOfEntries"));
    }

    @Test
    void shouldShowFormSavedWhenEntryNotApproved() throws Exception {
        GuestbookModel entry = new GuestbookModel();
        entry.setApproved(false);

        when(guestbookService.findByIpAddress(TEST_IP)).thenReturn(Optional.of(entry));
        when(guestbookService.getPublishedEntries()).thenReturn(List.of());

        mockMvc.perform(get("/guestbook")
                        .with(request -> { request.setRemoteAddr(TEST_IP); return request; }))
                .andExpect(status().isOk())
                .andExpect(view().name("pages/guestbook"))
                .andExpect(model().attribute("formSaved", true));
    }

    @Test
    void shouldNotShowFormOrFormSavedWhenEntryApproved() throws Exception {
        GuestbookModel entry = new GuestbookModel();
        entry.setApproved(true);

        when(guestbookService.findByIpAddress(TEST_IP)).thenReturn(Optional.of(entry));
        when(guestbookService.getPublishedEntries()).thenReturn(List.of());

        mockMvc.perform(get("/guestbook")
                        .with(request -> { request.setRemoteAddr(TEST_IP); return request; }))
                .andExpect(status().isOk())
                .andExpect(view().name("pages/guestbook"))
                .andExpect(model().attributeDoesNotExist("guestbookForm"))
                .andExpect(model().attributeDoesNotExist("formSaved"));
    }

    @Test
    void shouldAlwaysAddEntriesToModel() throws Exception {
        List<GuestbookModel> entries = List.of(new GuestbookModel());

        when(guestbookService.findByIpAddress(TEST_IP)).thenReturn(Optional.empty());
        when(guestbookService.getPublishedEntries()).thenReturn(entries);

        mockMvc.perform(get("/guestbook")
                        .with(request -> { request.setRemoteAddr(TEST_IP); return request; }))
                .andExpect(status().isOk())
                .andExpect(model().attribute("listOfEntries", entries));
    }

    @Test
    void shouldRedirectToExternalWhenAliasFilled() throws Exception {
        mockMvc.perform(post("/guestbook")
                        .param("alias", "bot-field")) // honeypot triggered
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost"));
    }

    @Test
    void shouldSaveEntryAndRedirect() throws Exception {
        when(guestbookService.getPublishedEntries()).thenReturn(List.of());

        mockMvc.perform(post("/guestbook")
                        .param("alias", "") // honeypot field, must be empty
                        .param("name", "John")
                        .param("message", "Hello")
                        .with(request -> { request.setRemoteAddr(TEST_IP); return request; }))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/guestbook"));

        verify(guestbookService).postEntry(any());
    }

}