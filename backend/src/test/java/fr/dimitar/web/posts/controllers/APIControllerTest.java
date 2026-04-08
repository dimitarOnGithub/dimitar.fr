package fr.dimitar.web.posts.controllers;

import fr.dimitar.web.posts.PostService;
import fr.dimitar.web.posts.dto.PostModel;
import fr.dimitar.web.posts.exceptions.PostNotFoundException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.ZonedDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(APIController.class)
@AutoConfigureMockMvc
@ActiveProfiles("api")
class APIControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PostService postService;

    @Test
    @WithMockUser
    void getAllPosts() throws Exception {
        when(postService.getPosts()).thenReturn(
                List.of(
                        new PostModel(1L, "Test Post 1", "Test Content", ZonedDateTime.now(), false),
                        new PostModel(2L, "Test Post 2", "Test Content", ZonedDateTime.now(), false)
                )
        );

        mockMvc.perform(get("/posts"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].title").value("Test Post 1"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].title").value("Test Post 2"));
    }

    @Test
    void getAllPosts401Unauthorized() throws Exception {
        when(postService.getPosts()).thenReturn(
                List.of(
                        new PostModel(1L, "Test Post 1", "Test Content", ZonedDateTime.now(), false),
                        new PostModel(2L, "Test Post 2", "Test Content", ZonedDateTime.now(), false)
                )
        );

        mockMvc.perform(get("/posts"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void publishPost() throws Exception {
        var post = new PostModel("Test Post 3", "Test post", false);
        when(postService.publishPost(any(PostModel.class))).thenReturn(post);

        JSONObject testPost = new JSONObject();
        testPost.put("title", "Test Post 3");
        testPost.put("content", "Test post");
        testPost.put("draft", false);
        mockMvc.perform(
                post("/posts").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                               testPost.toString()
                        )
                )
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    @WithMockUser
    void publishPost400BadRequest() throws Exception {
        var post = new PostModel("Test Post 3", "Test post", false);
        when(postService.publishPost(any(PostModel.class))).thenReturn(post);

        JSONObject testPost = new JSONObject();
        testPost.put("title", "Test Post 3");
        testPost.put("foobar", "Test post");
        testPost.put("draft", false);
        mockMvc.perform(
                        post("/posts").with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        testPost.toString()
                                )
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void publishPost403Forbidden() throws Exception {
        var post = new PostModel("Test Post 3", "Test post", false);
        when(postService.publishPost(any(PostModel.class))).thenReturn(post);

        JSONObject testPost = new JSONObject();
        testPost.put("title", "Test Post 3");
        testPost.put("content", "Test post");
        testPost.put("draft", false);
        mockMvc.perform(
                        post("/posts")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        testPost.toString()
                                )
                )
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser
    void getPostById() throws Exception {
        var post = new PostModel(1L, "Test Post 1", "Test Content", ZonedDateTime.now(), false);

        when(postService.getPostById(any(Long.class))).thenReturn(post);

        mockMvc.perform(get("/posts/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("title").exists())
                .andExpect(jsonPath("title").value("Test Post 1"));

    }

    @Test
    void getPostById401Unauthorized() throws Exception {
        var post = new PostModel(1L, "Test Post 1", "Test Content", ZonedDateTime.now(), false);

        when(postService.getPostById(any(Long.class))).thenReturn(post);

        mockMvc.perform(get("/posts/1"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void getNonexistentPost() throws Exception {
        when(postService.getPostById(any(Long.class))).thenThrow(PostNotFoundException.class);

        mockMvc.perform(get("/posts/99999"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON));
    }

    @Test
    @WithMockUser
    void getDraftPosts() throws Exception {
        when(postService.getDrafts()).thenReturn(
                List.of(
                        new PostModel(1L, "Test Post 1", "Test Content", ZonedDateTime.now(), true),
                        new PostModel(2L, "Test Post 2", "Test Content", ZonedDateTime.now(), true)
                )
        );

        mockMvc.perform(get("/drafts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void getDrafts401Unauthorized() throws Exception {
        when(postService.getDrafts()).thenReturn(
                List.of(
                        new PostModel(1L, "Test Post 1", "Test Content", ZonedDateTime.now(), true),
                        new PostModel(2L, "Test Post 2", "Test Content", ZonedDateTime.now(), true)
                )
        );

        mockMvc.perform(get("/drafts"))
                .andExpect(status().isUnauthorized());
    }

}