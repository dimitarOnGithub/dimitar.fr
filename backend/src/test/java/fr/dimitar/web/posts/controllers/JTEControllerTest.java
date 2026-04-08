package fr.dimitar.web.posts.controllers;

import fr.dimitar.web.posts.PostService;
import fr.dimitar.web.posts.dto.PostModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.ZonedDateTime;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(JTEController.class)
@AutoConfigureMockMvc(addFilters = false)
public class JTEControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PostService postService;

    @Test
    void shouldRenderArticle() throws Exception {
        PostModel postModel = new PostModel(1L, "Test Post", "Test Content", ZonedDateTime.now(), false);
        when(this.postService.getPublishedPostById(any(Long.class))).thenReturn(postModel);

        mockMvc.perform(get("/posts/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("pages/article"))
                .andExpect(model().attributeExists("article"))
                .andExpect(model().attribute(
                        "article", hasProperty("title", is("Test Post")
                        )
                ));
    }
}
