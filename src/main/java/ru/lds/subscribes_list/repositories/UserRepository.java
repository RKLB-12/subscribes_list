package ru.lds.subscribes_list.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.lds.subscribes_list.models.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
