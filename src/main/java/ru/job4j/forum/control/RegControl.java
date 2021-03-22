package ru.job4j.forum.control;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.forum.model.User;
import ru.job4j.forum.service.ForumService;

@Controller
public class RegControl {
    private final ForumService service;

    public RegControl(ForumService service) {
        this.service = service;
    }

    @GetMapping("/reg")
    public String registration() {
        return "reg";
    }

    @PostMapping("/reg")
    public String registration(@ModelAttribute User user) {
        service.saveUser(user);
        return "redirect:/login";
    }
}
