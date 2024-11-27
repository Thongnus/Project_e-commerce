package com.example.api.Service;

import com.example.api.Entity.Product;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.awt.*;
import java.util.ArrayList;

public interface ProductRedis_Service {

    String getKeyFrom(String name_category);
    ArrayList<Product> getallProductbynamecategory( String name_category ) throws JsonProcessingException;
    void SaveAllproductBycategory(String namecategory,ArrayList<Product> list) throws JsonProcessingException;
    void delete(String key);
    public <T> ArrayList<Product> getallProduct() throws JsonProcessingException;
    void SaveAllproduct(ArrayList<Product> arr) throws JsonProcessingException;
    void clear(String namecategory);
}
