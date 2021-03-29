package ru.job4j.forum.control;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.forum.model.Post;
import ru.job4j.forum.service.ForumService;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PostControl {
    private final ForumService service;

    public PostControl(ForumService service) {
        this.service = service;
    }

    @GetMapping("/post")
    public String post(@RequestParam("topicId") int topicId, Model model) {
        model.addAttribute("topic", service.findById(topicId));
        return "post";
    }

    @GetMapping("/create")
    public String createPost(@RequestParam("topicId") int topicId, Model model) {
        model.addAttribute("topic_id", topicId);
        return "post/create";
    }

    @GetMapping("/update")
    public String updatePost(@RequestParam("postId") int postId, Model model) {
        model.addAttribute("post", service.postFindById(postId));
        return "post/edit";
    }

    @PostMapping("/save")
    public String savePost(@ModelAttribute Post post, HttpServletRequest req) {
        String topicId = req.getParameter("topic_id");
        service.addPost(Integer.parseInt(topicId), post);
        return "redirect:/";
    }
}
