package ru.greatstep.redislesson.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.greatstep.redislesson.models.dto.TokenDto;
import ru.greatstep.redislesson.models.dto.UserDto;
import ru.greatstep.redislesson.services.TokenService;
import ru.greatstep.redislesson.services.UserService;

import java.util.List;

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

    @PostMapping("/create-user")
    public UserDto createUser(@RequestBody @Valid UserDto userDto) {
        return userService.createUser(userDto);
    }

    @GetMapping("/user")
    public UserDto getUser(@RequestParam String username) {
        return userService.getUser(username);
    }

    @GetMapping("/users")
    public List<UserDto> getUsers() {
        return userService.getUsers();
    }
}
