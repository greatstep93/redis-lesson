package ru.greatstep.redislesson.models.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Size;

public record UserDto(

        @Size(min = 1, max = 100, message = "Длинна username должна быть не меньше 1 и не более 10 символов")
        String username,

        @Size(min = 10, max = 200, message = "Длинна пароля должна быть не меньше 10 и не более 200 символов")
        String password) {
}
