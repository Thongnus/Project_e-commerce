package com.example.api.Entity;

import com.example.api.Service.ProductRedis_Service;
import jakarta.persistence.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Productlistener {
    @Autowired
    private ProductRedis_Service productRedisService;
    @Autowired
    RedisTemplate<String,Object> redisTemplate;
    @PrePersist
    public void prePersist(Product product) {
        log.info("prePersist");
    }

    @PostPersist //save = persis
    public void postPersist(Product product) {
        // Update Redis cache
        log.info("postPersist");
     //   productRedisService.delete(product.getCategory().nameCategory);
        productRedisService.clear(product.getCategory().nameCategory);
    }

    @PreUpdate
    public void preUpdate(Product product) {
        //ApplicationEventPublisher.instance().publishEvent(event);
        log.info("preUpdate");
    }

    @PostUpdate
   // @CachePut(value =)
    public void postUpdate(Product product) {
        // Update Redis cache
        log.info("postUpdate");
        productRedisService.clear(product.getCategory().nameCategory);
    }

    @PreRemove
    public void preRemove(Product product) {
        //ApplicationEventPublisher.instance().publishEvent(event);
        log.info("preRemove");
    }

    @PostRemove
    public void postRemove(Product product) {
        // Update Redis cache
        log.info("postRemove");
        productRedisService.clear(product.getCategory().nameCategory);
    }
}
