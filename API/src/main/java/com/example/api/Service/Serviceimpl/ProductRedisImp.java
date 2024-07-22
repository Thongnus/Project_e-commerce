package com.example.api.Service.Serviceimpl;

import com.example.api.Entity.Product;
import com.example.api.Service.ProductRedis_Service;
import com.example.api.Service.Redis_service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.ss.formula.functions.DProduct;
import org.apache.poi.ss.formula.functions.FinanceLib;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
@Service
public class ProductRedisImp implements ProductRedis_Service {
    @Autowired
    RedisTemplate<String,Object> redisTemplate;

    @Autowired
    Redis_service redisService;
    @Autowired
    ObjectMapper objectMapper;
     public static   String Allproduct="ALlproduct";
    @Override
    public String getKeyFrom(String id_category) {
        String Key = String.format("productsbyNamecategory:%s",id_category);

        return Key;
    }

    @Override
    public ArrayList<Product> getallProductbynamecategory(String name_category) throws JsonProcessingException {
        String key = this.getKeyFrom(name_category);
        String json = (String) redisService.getkey(key);
        ArrayList<Product> list = new ArrayList<>();
        if (json != null && !json.isEmpty()) {
            list = objectMapper.readValue(json, new TypeReference<ArrayList<Product>>() {});
        }
        return list;
    }


    @Override
    public void SaveAllproductBycategory(String namecategory,ArrayList<Product> list) throws JsonProcessingException {
        String key = this.getKeyFrom(namecategory);
        String json = objectMapper.writeValueAsString(list);
        redisService.setkey(key,json);
    }

    @Override
    public void delete(String key) {
        redisService.delete_key(key);
    }

    @Override
    public ArrayList<Product> getallProduct() throws JsonProcessingException {
            String json = (String) redisService.getkey(Allproduct);
            if(json!=null&&!json.isEmpty())
            {
                return objectMapper.readValue
                    (json, new TypeReference<ArrayList<Product>>() {
            });
            }
            return new ArrayList<Product>();
    }

    @Override
    public void SaveAllproduct(ArrayList<Product> arr) throws JsonProcessingException {

        String json = objectMapper.writeValueAsString(arr);
        redisService.setkey(Allproduct,json);
    }

    @Override
    public void clear(String namecategory) {
        String Key = String.format("productsbyNamecategory:%s",namecategory);
            redisService.delete_key(Key);
            redisService.delete_key(Allproduct);
    }
}
