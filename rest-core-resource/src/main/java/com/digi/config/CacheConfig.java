package com.digi.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * Created by tymoshenkol on 23-Nov-16.
 */
@Configuration
@EnableCaching
@PropertySource(value = "classpath:application-aws-cache.properties")
@Slf4j
public class CacheConfig extends CachingConfigurerSupport {

    private @Value("${redis.host-name}") String redisHostName;
    private @Value("${redis.port}") int redisPort;
    private @Value("${redis.password}") String password;

    @Bean
    public JedisConnectionFactory redisConnectionFactory() {
        log.debug("JedisConnectionFactory: {}: {}", redisHostName, redisPort);
        JedisConnectionFactory redisConnectionFactory = new JedisConnectionFactory();

        redisConnectionFactory.setHostName(redisHostName);
        redisConnectionFactory.setPort(redisPort);
        redisConnectionFactory.setPassword(password);
        //redisConnectionFactory.setUsePool(true);
        return redisConnectionFactory;
    }

    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory cf) {
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(cf);
        return redisTemplate;
    }

    @Bean
    public CacheManager cacheManager(RedisTemplate redisTemplate) {
        RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);

        // Number of seconds before expiration. Set to 30 minutes, otherwise defaults to unlimited (0)
        cacheManager.setDefaultExpiration(30 * 60);
        return cacheManager;
    }

}
