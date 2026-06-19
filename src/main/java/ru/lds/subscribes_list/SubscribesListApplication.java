package ru.lds.subscribes_list;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import ru.lds.subscribes_list.models.User;
import ru.lds.subscribes_list.repositories.UserRepository;

@SpringBootApplication
public class SubscribesListApplication {

    public static void main(String[] args) {
        SpringApplication.run(SubscribesListApplication.class, args);
    }

}
