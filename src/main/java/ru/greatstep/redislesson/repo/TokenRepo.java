package ru.greatstep.redislesson.repo;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.greatstep.redislesson.models.entities.TokenEntity;

import java.util.Optional;

@Repository
public interface TokenRepo extends JpaRepository<TokenEntity, Long> {

    @Cacheable(cacheNames = "tokens")
    Optional<TokenEntity> findByToken(String token);
}
