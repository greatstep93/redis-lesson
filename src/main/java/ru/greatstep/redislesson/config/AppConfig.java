package ru.greatstep.redislesson.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.greatstep.redislesson.models.entities.UserEntity;
import ru.greatstep.redislesson.repo.UserRepo;

@Component
@RequiredArgsConstructor
public class AppConfig {

    private final UserRepo userRepo;

    @PostConstruct
    public void init() {
        userRepo.save(new UserEntity(null, "test", "test"));
    }
}
