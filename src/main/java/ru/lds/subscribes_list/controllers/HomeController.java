package ru.lds.subscribes_list.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.lds.subscribes_list.models.Subscription;
import ru.lds.subscribes_list.models.User;
import ru.lds.subscribes_list.services.SubscriptionService;
import ru.lds.subscribes_list.services.UserService;

import java.util.Comparator;
import java.util.List;

@Controller
public class HomeController {
    private final UserService userService;
    private final SubscriptionService subscriptionService;

    public HomeController(UserService userService, SubscriptionService subscriptionService) {
        this.userService = userService;
        this.subscriptionService = subscriptionService;
    }

    @GetMapping("/")
    public String home(Authentication authentication, Model model) {
        String username = authentication.getName();
        User currentUser = userService.getByUsername(username);

        model.addAttribute("username", username);
        model.addAttribute(
                "subscriptions",
                subscriptionService.getSubscriptions(currentUser)
        );

        List<Subscription> subscriptions = subscriptionService.getSubscriptions(currentUser);
        subscriptions.sort(Comparator.comparing(Subscription::getNextPaymentDate));

        subscriptions = subscriptions.stream()
                .filter(subscription -> subscription.getDaysUntilPayment() <= currentUser.getNotificationDays())
                .sorted(Comparator.comparing(Subscription::getNextPaymentDate))
                .toList();
        model.addAttribute("notifications", subscriptions);
        return "home";
    }

}