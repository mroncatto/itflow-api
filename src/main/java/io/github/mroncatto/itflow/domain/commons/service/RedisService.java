package io.github.mroncatto.itflow.domain.commons.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final RedisTemplate<String, String> redisTemplate;


    public void saveValue(String key, String value, int timeout) {
        BoundValueOperations<String, String> boundValueOperations = redisTemplate.boundValueOps(key);
        boundValueOperations.set(value);
        boundValueOperations.expire(timeout, TimeUnit.MINUTES);
    }

    public String getValue(String key) {
        BoundValueOperations<String, String> boundValueOperations = redisTemplate.boundValueOps(key);
        return boundValueOperations.get();
    }
}
