package ru.job4j.forum.control;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.forum.model.User;
import ru.job4j.forum.service.ForumService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginControl {
    private final ForumService fService;

    public LoginControl(ForumService fService) {
        this.fService = fService;
    }

    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "logout", required = false) String logout,
                            Model model) {
        String errorMessage = null;
        if (error != null) {
            errorMessage = "Username or Password is incorrect !!";
        }
        if (logout != null) {
            errorMessage = "You have been successfully logged out !!";
        }
        model.addAttribute("errorMessage", errorMessage);
        return "login";
    }

    @PostMapping("/login")
    public String loginPage(@ModelAttribute User user, Model model) {
        String errorMessage = null;
        String res = null;
        User u = fService.findUserByName(user.getUsername());
        if (u == null) {
            errorMessage = "Пользователь с таким именем не найден в системе !!";
            model.addAttribute("errorMessage", errorMessage);
            res = "login";
        } else if (u.getPassword().equals(user.getPassword())) {
            fService.setCurrentUser(u);
            res = "redirect:/";
        } else {
            errorMessage = "Username or Password is incorrect !!";
            model.addAttribute("errorMessage", errorMessage);
            res = "login";
        }
        return res;
    }

    @GetMapping(value = "/logout")
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        return "redirect:/login?logout=true";
    }
}
