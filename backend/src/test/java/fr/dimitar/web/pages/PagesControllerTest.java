package fr.dimitar.web.pages;

import fr.dimitar.web.posts.PostService;
import fr.dimitar.web.posts.dto.PostModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PagesController.class)
@AutoConfigureMockMvc(addFilters = false)
public class PagesControllerTest {

    private final ZonedDateTime YEAR_2025 = ZonedDateTime.of(
            2025, 1, 12, 13, 14, 15, 16,
            ZoneId.of("Europe/Paris"));
    private final ZonedDateTime YEAR_2024 = ZonedDateTime.of(
            2024, 1, 12, 13, 14, 15, 16,
            ZoneId.of("Europe/Paris"));
    private final ZonedDateTime YEAR_2023 = ZonedDateTime.of(
            2023, 1, 12, 13, 14, 15, 16,
            ZoneId.of("Europe/Paris"));

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PostService postService;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(new PagesController(this.postService))
                .build();
    }

    @Test
    void shouldRenderIndexPage() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("pages/index"))
                .andExpect(model().attributeExists("posts"));
    }

    @Test
    void shouldRenderAboutPage() throws Exception {
        mockMvc.perform(get("/about"))
                .andExpect(status().isOk())
                .andExpect(view().name("pages/about"));
    }

    @Test
    void shouldRenderNowPage() throws Exception {
        mockMvc.perform(get("/now"))
                .andExpect(status().isOk())
                .andExpect(view().name("pages/now"));
    }

    @Test
    void shouldRenderArchivePage() throws Exception {
        List<PostModel> list = List.of(
                new PostModel(1L, "Test - 2023", "test", YEAR_2023, false),
                new PostModel(2L, "Test - 2024", "test", YEAR_2024, false),
                new PostModel(3L, "Test - 2025", "test", YEAR_2025, false)
        );

        when(this.postService.getPosts()).thenReturn(list);

        MvcResult result = mockMvc.perform(get("/archive"))
                .andExpect(status().isOk())
                .andReturn();

        Map<String, Object> model = result.getModelAndView().getModel();

        SortedMap<Integer, List<PostModel>> postsMap =
                (SortedMap<Integer, List<PostModel>>) model.get("postsMap");

        assertThat(postsMap).containsKeys(2025, 2024, 2023);

        // Keys need to be in descending order
        assertThat(postsMap.firstKey()).isEqualTo(2025);
        assertThat(postsMap.lastKey()).isEqualTo(2023);
    }

}
