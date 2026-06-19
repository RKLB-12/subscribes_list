package ru.lds.subscribes_list.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lds.subscribes_list.models.Subscription;
import ru.lds.subscribes_list.models.User;
import ru.lds.subscribes_list.repositories.SubscriptionRepository;

import java.util.List;

@Service
public class SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;

    public SubscriptionService(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    public List<Subscription> getSubscriptions(User user) {
        return subscriptionRepository.findByUser(user);
    }

    public void saveSubscription(Subscription subscription) {
        subscriptionRepository.save(subscription);
    }

    public Subscription findById(Long id) {
        return subscriptionRepository.findById(id).orElseThrow(() -> new RuntimeException("Subscription not found"));
    }

    @Transactional
    public void deleteById(Long id) {
        subscriptionRepository.deleteById(id);
    }

    @Transactional
    public void deleteByUser(User user) {
        subscriptionRepository.deleteByUser(user);
    }
}