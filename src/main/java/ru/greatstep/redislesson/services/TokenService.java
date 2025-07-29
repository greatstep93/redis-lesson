package ru.greatstep.redislesson.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.greatstep.redislesson.exceptions.BadCredentialsException;
import ru.greatstep.redislesson.models.dto.TokenDto;
import ru.greatstep.redislesson.models.entities.TokenEntity;
import ru.greatstep.redislesson.models.entities.UserEntity;
import ru.greatstep.redislesson.repo.TokenRepo;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TokenService {

    private static final Integer TOKEN_EXPIRATION_TIME = 20;

    private final TokenRepo tokenRepo;

    public TokenDto generateToken(UserEntity userEntity) {
        String token = UUID.randomUUID().toString();
        LocalDateTime expireDate = LocalDateTime.now().plusSeconds(TOKEN_EXPIRATION_TIME);
        TokenEntity tokenEntity = new TokenEntity();
        tokenEntity.setToken(token);
        tokenEntity.setExpireDate(expireDate);
        tokenEntity.setUserId(userEntity);
        tokenRepo.save(tokenEntity);
        return new TokenDto(token);
    }

    public boolean validateToken(String token) {
        TokenEntity tokenEntity = tokenRepo.findByToken(token)
                .orElseThrow(() -> new BadCredentialsException("Invalid token"));

        if (tokenEntity.getExpireDate().isBefore(LocalDateTime.now())) {
            throw new BadCredentialsException("Token expired");
        }

        return true;
    }
}
