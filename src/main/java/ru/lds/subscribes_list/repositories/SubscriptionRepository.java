package ru.lds.subscribes_list.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.lds.subscribes_list.models.Subscription;
import ru.lds.subscribes_list.models.User;

import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    List<Subscription> findByUser(User user);
    void deleteByUser(User user);
}