package ru.greatstep.redislesson.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.greatstep.redislesson.exceptions.BadCredentialsException;
import ru.greatstep.redislesson.exceptions.UserNotFoundException;
import ru.greatstep.redislesson.models.dto.TokenDto;
import ru.greatstep.redislesson.models.dto.UserDto;
import ru.greatstep.redislesson.models.entities.UserEntity;
import ru.greatstep.redislesson.repo.UserRepo;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;
    private final TokenService tokenService;
    public TokenDto getToken(UserDto userDto) {
        UserEntity userEntity = userRepo.findByUsername(userDto.username())
                .orElseThrow(() -> new UserNotFoundException(userDto.username()));
        if (!Objects.equals(userDto.password(), userEntity.getPassword())) {
            throw new BadCredentialsException("Incorrect password");
        }

        return tokenService.generateToken(userEntity);
    }

}
