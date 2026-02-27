package me.sudosuwinter.web.posts;

import me.sudosuwinter.web.config.TestConfig;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.net.URI;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PostsController.class)
@Import(TestConfig.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
class PostsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAllPosts() throws Exception {
        mockMvc.perform(get("/posts"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void publishPost() throws Exception {
        JSONObject testPost = new JSONObject();
        testPost.put("title", "Test Post 3");
        testPost.put("content", "Test post");
        testPost.put("isADraft", false);
        MvcResult result = mockMvc.perform(
                post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                               testPost.toString()
                        )
                )
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andReturn();
        String newPostPath = URI.create(result.getResponse().getHeader("Location")).toURL().getPath();
        mockMvc.perform(get(newPostPath))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("title").exists())
                .andExpect(jsonPath("title").value("Test Post 3"));
    }

    @Test
    @WithMockUser
    void getPostById() throws Exception {
        mockMvc.perform(get("/posts/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("title").exists())
                .andExpect(jsonPath("title").value("Test Post 1"));

    }

    @Test
    void getPageablePost() throws Exception {
        mockMvc.perform(get("/posts").queryParam("page", "0"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("content.length()").value(1))
                .andExpect(jsonPath("content[0].title").value("Test Post 1"));
    }

    @Test
    void getNonexistentPost() throws Exception {
        mockMvc.perform(get("/posts/99999"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON));
    }
}