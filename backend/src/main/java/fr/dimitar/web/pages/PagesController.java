package fr.dimitar.web.pages;

import fr.dimitar.web.posts.PostService;
import fr.dimitar.web.posts.dto.PostModel;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

@Controller
public class PagesController implements ErrorController {

    private final PostService postService;

    @Autowired
    public PagesController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/")
    public String indexPage(Model model){
        List<PostModel> posts = this.postService.getPostsWithLimit(6);
        model.addAttribute("posts", posts);
        return "pages/index";
    }

    @GetMapping("/archive")
    public String archivePage(Model model) {
        List<PostModel> posts = this.postService.getPosts();
        SortedMap<Integer, List<PostModel>> postsMap = new TreeMap<>(java.util.Collections.reverseOrder());
        for(PostModel postModel: posts) {
            int postYear = postModel.getPublishedDateTime().getYear();
            var yearlyList = postsMap.getOrDefault(postYear, new ArrayList<>());
            yearlyList.add(postModel);
            postsMap.put(postYear, yearlyList);
        }
        model.addAttribute("postsMap", postsMap);
        return "pages/archive";
    }

    @GetMapping("/now")
    public String nowPage() {
        return "pages/now";
    }

    @GetMapping("/about")
    public String aboutPage() {
        return "pages/about";
    }

    @RequestMapping("/error")
    public String errorPage(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("jakarta.servlet.error.status_code");
        Throwable exception = (Throwable) request.getAttribute("jakarta.servlet.error.exception");

        return switch (statusCode) {
            case 400 -> "errors/400";
            case 403 -> "errors/403";
            case 404 -> "errors/404";
            default -> "errors/500";
        };
    }

}
