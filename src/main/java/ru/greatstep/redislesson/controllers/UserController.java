package ru.greatstep.redislesson.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import ru.greatstep.redislesson.models.dto.TokenDto;
import ru.greatstep.redislesson.models.dto.UserDto;
import ru.greatstep.redislesson.services.TokenService;
import ru.greatstep.redislesson.services.UserService;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final TokenService tokenService;

    @PostMapping("/login")
    public TokenDto login(@RequestBody UserDto userDto) {
        return userService.getToken(userDto);
    }

    @PostMapping("/validate")
    public boolean validate(@RequestHeader(name = "token") String token) {
        return tokenService.validateToken(token);
    }
}
