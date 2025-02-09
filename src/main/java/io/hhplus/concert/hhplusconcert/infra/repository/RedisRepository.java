package io.hhplus.concert.hhplusconcert.infra.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.List;
import java.util.Set;

@Repository
@RequiredArgsConstructor
public class RedisRepository {

    private final RedisTemplate<String, Object> redisTemplate;

    public void put(String key, Object value, Duration ttl) {
        redisTemplate.opsForValue().set(key, value, ttl);
    }

    public void addSortedSet(String key, String value, double score) {
        redisTemplate.opsForZSet().add(key, value, score);
    }

    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public boolean keyExists(String key) {
        return redisTemplate.hasKey(key);
    }

    public Long getSize(String key) {
        Set<String> keys = redisTemplate.keys(key + ":*");
        return (long) (keys != null ? keys.size() : 0);
    }

    public Long getSortedSetSize(String key) {
        return redisTemplate.opsForZSet().zCard(key);
    }

    public Long getSortedSetRank(String key, String value) {
        return redisTemplate.opsForZSet().rank(key, value);
    }

    public Set<Object> getSortedSetRange(String key, long start, long end) {
        return redisTemplate.opsForZSet().range(key, start, end);
    }

    public void remove(String key) {
        redisTemplate.delete(key);
    }

    public void removeSortedSetMembers(String key, List<String> values) {
        redisTemplate.opsForZSet().remove(key, values.toArray());
    }
}
