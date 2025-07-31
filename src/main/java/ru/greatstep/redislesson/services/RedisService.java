package ru.greatstep.redislesson.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import ru.greatstep.redislesson.models.entities.UserEntity;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final RedisTemplate<String, UserEntity> userEntityRedisTemplate;

    public void saveData(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public void saveDataWithExpiration(String key, Object value, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    public Object getData(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void saveUserEntity(UserEntity userEntity) {
        userEntityRedisTemplate.opsForValue().set(userEntity.getUsername(), userEntity);
    }

    public void saveUserEntityWithExpiration(UserEntity userEntity, Duration duration) {
        userEntityRedisTemplate.opsForValue().set(userEntity.getUsername(), userEntity, duration);
    }

    public UserEntity getUserEntity(String username) {
        return userEntityRedisTemplate.opsForValue().get(username);
    }

    public void deleteUserEntity(String username) {
        userEntityRedisTemplate.delete(username);
    }

    public void deleteKey(String key) {
        redisTemplate.delete(key);
    }
}
