package com.example.api.Service.Serviceimpl;

import com.example.api.Service.Redis_service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class RedisServiceimpl implements Redis_service {
    @Autowired
    RedisTemplate<String,Object> redisTemplate;

    // redistemplate có bao gồm hashOperations có thể gọi trực tiếp hoặc
    //sử dụng hashOperations riêng
    @Autowired
    HashOperations<String ,String,Object>  hashOperations ;
 public void setkey(String key, Object object){
     redisTemplate.opsForValue().set(key,object);

 }

 public Object getkey(String key){

    return redisTemplate.opsForValue().get(key);

 }
 public  void delete_key(String key){
     redisTemplate.delete(key);

 }
 public void settimetolive(String key,long time){
     redisTemplate.expire(key,time, TimeUnit.DAYS);

 }




 //check hashkey
    public Boolean hasKey(String key, Object hashKey) {
        return hashOperations.hasKey(key, hashKey);
    }

//Lấy giá trị từ Redis dựa trên hashKey trong hash có key
    public Object get(String key, Object hashKey) {
        return hashOperations.get(key, hashKey);
    }

//ấy nhiều giá trị từ Redis dựa trên một danh sách hashKeys trong hash có key
    public List<Object> multiGet(String key, Collection<String> hashKeys) {
        return hashOperations.multiGet(key, hashKeys);
    }



//Lấy một cặp key-value ngẫu nhiên từ hash có key tương ứng
    public Map.Entry<String, Object> randomEntry(String key) {
        return hashOperations.randomEntry(key);
    }

//Lấy một danh sách các key ngẫu nhiên từ hash có
// key tương ứng trong Redis với số lượng là count
    public List<String> randomKeys(String key, long count) {
        return hashOperations.randomKeys(key, count);
    }

//Lấy một danh sách các cặp key-value ngẫu nhiên
// từ hash có key tương ứng trong Redis với số lượng là count,
    public Map<String, Object> randomEntries(String key, long count) {
        return hashOperations.randomEntries(key, count);
    }

//Lấy tất cả các key của hash có key tương ứng
    public Set<String> keys(String key) {
        return hashOperations.keys(key);
    }

//
    public Long lengthOfValue(String key, String hashKey) {
        return hashOperations.lengthOfValue(key, hashKey);
    }


    public Long size(String key) {
        return hashOperations.size(key);
    }


    public void putAll(String key, Map<? extends String,Object> m) {
        hashOperations.putAll(key, m);
    }


    public void put(String key, String hashKey, Object value) {
        hashOperations.put(key,hashKey,value);
    }



//Lấy tất cả các giá trị trong hash có key tương ứng
    public List<Object> values(String key) {
        return hashOperations.values(key);
    }

//Lấy tất cả các cặp key-value trong hash có key tương ứng
    public Map<String, Object> entries(String key) {
        return hashOperations.entries(key);
    }




}
