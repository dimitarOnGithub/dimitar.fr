package fr.dimitar.web.posts.controllers;

import fr.dimitar.web.posts.services.JtePostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class JtePostsController {

    private final JtePostService postService;

    @Autowired
    public JtePostsController(JtePostService jtePostService){
        this.postService = jtePostService;
    }

    @GetMapping("/posts/{postId}")
    public String getPost(@PathVariable("postId") Long postId, Model model){
        model.addAttribute("article",
                this.postService.getPostById(postId)
                        .orElseThrow(RuntimeException::new)
        );
        model.addAttribute("previous", this.postService.findPrevious(postId));
        model.addAttribute("next", this.postService.findNext(postId));
        return "article";
    }

}
