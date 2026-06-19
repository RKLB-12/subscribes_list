package ru.lds.subscribes_list.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.lds.subscribes_list.models.User;
import ru.lds.subscribes_list.services.SubscriptionService;
import ru.lds.subscribes_list.services.UserService;


@Controller
public class UserController {
    private final UserService userService;
    private final SubscriptionService subscriptionService;

    public UserController(UserService userService, SubscriptionService subscriptionService) {

        this.userService = userService;
        this.subscriptionService = subscriptionService;
    }

    @GetMapping("/user/edit-days")
    public String editDaysPage(Authentication authentication, Model model) {
        User user = userService.getByUsername(authentication.getName());
        model.addAttribute("user", user);
        return "user/edit_days";
    }

    @PostMapping("/user/edit-days")
    public String updateDays(Authentication authentication, @RequestParam Integer notificationDays) {
        User user = userService.getByUsername(authentication.getName());
        user.setNotificationDays(notificationDays);
        userService.save(user);
        return "redirect:/";
    }

    @PostMapping("/user/delete")
    public String deleteAccount(Authentication authentication, HttpServletRequest request) {

        User currentUser = userService.getByUsername(authentication.getName());

        subscriptionService.deleteByUser(currentUser);
        userService.delete(currentUser);

        request.getSession().invalidate();
        SecurityContextHolder.clearContext();
        return "redirect:/login";
    }



}
