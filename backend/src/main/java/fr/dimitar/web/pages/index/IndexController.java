package fr.dimitar.web.pages.index;

import fr.dimitar.web.posts.dto.PostModel;
import fr.dimitar.web.posts.services.JtePostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class IndexController {

    private final JtePostService postService;

    @Autowired
    public IndexController(JtePostService readService) {
        this.postService = readService;
    }

    @GetMapping("/")
    public String indexPage(Model model){
        List<PostModel> posts = this.postService.getRecentPosts(6);
        model.addAttribute("posts", posts);
        return "index";
    }
}
