package ru.greatstep.redislesson.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.greatstep.redislesson.exceptions.BadCredentialsException;
import ru.greatstep.redislesson.exceptions.UserNotFoundException;
import ru.greatstep.redislesson.models.dto.TokenDto;
import ru.greatstep.redislesson.models.dto.UserDto;
import ru.greatstep.redislesson.models.entities.UserEntity;
import ru.greatstep.redislesson.repo.UserRepo;

import java.time.Duration;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;
    private final TokenService tokenService;
    private final RedisService redisService;

    public TokenDto getToken(UserDto userDto) {
        UserEntity userEntity = userRepo.findByUsername(userDto.username())
                .orElseThrow(() -> new UserNotFoundException(userDto.username()));
        if (!Objects.equals(userDto.password(), userEntity.getPassword())) {
            throw new BadCredentialsException("Incorrect password");
        }

        return tokenService.generateToken(userEntity);
    }

    public UserDto createUser(UserDto userDto) {
        var entity = userRepo.save(new UserEntity(null, userDto.username(), userDto.password()));
        redisService.saveUserEntityWithExpiration(entity, Duration.ofSeconds(30));
        return userDto;
    }

    public UserDto getUser(String username) {
        var userEntity = redisService.getUserEntity(username);
        return new UserDto(userEntity.getUsername(), userEntity.getPassword());
    }
}
