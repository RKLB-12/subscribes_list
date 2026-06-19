package ru.lds.subscribes_list.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;

import ru.lds.subscribes_list.models.User;
import ru.lds.subscribes_list.services.UserService;

@Controller
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String register(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam Integer notificationDays,
            Model model
    ) {

        try {
            userService.registerUser(username, password, notificationDays);
            return "redirect:/login?registered";
        }
        catch (RuntimeException e) {
            model.addAttribute("error", "Аккаунт с таким именем уже существует");
            return "register";
        }
    }

    @GetMapping("/login")
    public String loginPage(@RequestParam(required = false) String registered, Model model) {

        if (registered != null) {
            model.addAttribute("success", "Аккаунт успешно создан");
        }

        return "login";
    }


}
