package fr.dimitar.web.guestbook.controllers;

import fr.dimitar.web.guestbook.GuestbookService;
import fr.dimitar.web.guestbook.dto.GuestbookModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

@WebMvcTest(controllers = APIController.class)
@ActiveProfiles("api")
class APIControllerTest {

    private final List<GuestbookModel> ENTRIES = List.of(
            new GuestbookModel("Hello", "google.com", "John", true),
            new GuestbookModel("Hi", "foobar.com", "Jane", true)
    );

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private GuestbookService guestbookService;

    @Test
    @WithMockUser
    void getGuestbook() throws Exception {
        when(guestbookService.getAllEntries()).thenReturn(ENTRIES);

        mockMvc.perform(get("/guestbook"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].username").value("John"))
                .andExpect(jsonPath("$[0].content").value("Hello"))
                .andExpect(jsonPath("$[1].username").value("Jane"));
    }

    @Test
    void getGuestbook401Unauthorized() throws Exception {
        when(guestbookService.getAllEntries()).thenReturn(ENTRIES);

        mockMvc.perform(get("/guestbook"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void getGuestbookEmptyList() throws Exception {
        when(guestbookService.getAllEntries()).thenReturn(List.of());

        mockMvc.perform(get("/guestbook"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(0));
    }

}