package ru.job4j.forum.control;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.forum.service.ForumService;

@Controller
public class IndexControl {
    private final ForumService fService;

    public IndexControl(ForumService fService) {
        this.fService = fService;
    }

    @GetMapping({"/", "/index"})
    public String index(Model model) {
        model.addAttribute("topics", fService.getAllTopics());
        model.addAttribute("user", fService.getCurrentUser());
        return "index";
    }
}