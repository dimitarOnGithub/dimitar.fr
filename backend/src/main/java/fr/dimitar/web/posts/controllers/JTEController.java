package fr.dimitar.web.posts.controllers;

import fr.dimitar.web.posts.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class JTEController {

    private final PostService postService;

    @Autowired
    public JTEController(PostService postService){
        this.postService = postService;
    }

    @GetMapping("/posts/{postId}")
    public String getPost(@PathVariable("postId") Long postId, Model model){
        model.addAttribute("article", this.postService.getPublishedPostById(postId));
        model.addAttribute("previous", this.postService.findPrevious(postId));
        model.addAttribute("next", this.postService.findNext(postId));
        return "pages/article";
    }

}
