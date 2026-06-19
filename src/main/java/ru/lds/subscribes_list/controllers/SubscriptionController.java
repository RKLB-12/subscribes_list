package ru.lds.subscribes_list.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.lds.subscribes_list.models.Subscription;
import ru.lds.subscribes_list.models.User;
import ru.lds.subscribes_list.services.SubscriptionService;
import ru.lds.subscribes_list.services.UserService;

import java.math.BigDecimal;
import java.time.LocalDate;

@Controller
public class SubscriptionController {
    @GetMapping("/subscriptions/add")
    public String addPage() {
        return "subscriptions/add_subscription";
    }

    private final SubscriptionService subscriptionService;
    private final UserService userService;
    public SubscriptionController(SubscriptionService subscriptionService, UserService userService) {
        this.subscriptionService = subscriptionService;
        this.userService = userService;
    }

    @PostMapping("/subscriptions/add")
    public String addSubscription(
            Authentication authentication,
            @RequestParam String name,
            @RequestParam String company,
            @RequestParam String website,
            @RequestParam BigDecimal price,
            @RequestParam LocalDate startDate,
            @RequestParam Integer periodDays
    ) {
        String username = authentication.getName();
        User currentUser = userService.getByUsername(username);
        Subscription subscription = new Subscription();

        if (!website.startsWith("http://") && !website.startsWith("https://")) {
            website = "https://" + website;
        }

        subscription.setName(name);
        subscription.setCompany(company);
        subscription.setWebsite(website);
        subscription.setPrice(price);
        subscription.setStartDate(startDate);
        subscription.setPeriodDays(periodDays);
        subscription.setUser(currentUser);
        subscriptionService.saveSubscription(subscription);

        return "redirect:/";
    }
    @PostMapping("/subscriptions/delete/{id}")
    public String deleteSubscription(@PathVariable Long id, Authentication authentication) {
        String username = authentication.getName();
        User currentUser = userService.getByUsername(username);

        Subscription subscription = subscriptionService.findById(id);

        if (!subscription.getUser().getId().equals(currentUser.getId())) {
            throw new RuntimeException("Access denied");
        }

        subscriptionService.deleteById(id);
        return "redirect:/";
    }

    @GetMapping("/subscriptions/edit/{id}")
    public String editPage(@PathVariable Long id, Authentication authentication, Model model) {

        String username = authentication.getName();
        User currentUser = userService.getByUsername(username);

        Subscription subscription = subscriptionService.findById(id);

        if (!subscription.getUser().getId().equals(currentUser.getId())) {
            throw new RuntimeException("Access denied");
        }

        model.addAttribute("subscription", subscription);
        return "subscriptions/edit_subscription";
    }

    @PostMapping("/subscriptions/edit/{id}")
    public String editSubscription(@PathVariable Long id,
                                   Authentication authentication,
                                   @RequestParam String name,
                                   @RequestParam String company,
                                   @RequestParam String website,
                                   @RequestParam BigDecimal price,
                                   @RequestParam LocalDate startDate,
                                   @RequestParam Integer periodDays) {

        String username = authentication.getName();
        User currentUser = userService.getByUsername(username);
        if (!website.startsWith("http://") && !website.startsWith("https://")) {
            website = "https://" + website;
        }

        Subscription subscription = subscriptionService.findById(id);

        if (!subscription.getUser().getId().equals(currentUser.getId())) {
            throw new RuntimeException("Access denied");
        }

        subscription.setName(name);
        subscription.setCompany(company);
        subscription.setWebsite(website);
        subscription.setPrice(price);
        subscription.setStartDate(startDate);
        subscription.setPeriodDays(periodDays);

        subscriptionService.saveSubscription(subscription);

        return "redirect:/";
    }
}