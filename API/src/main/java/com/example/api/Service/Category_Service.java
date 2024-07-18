package com.example.api.Service;


import com.example.api.Dto.CategoryDto;
import com.example.api.Entity.Category;
import org.springframework.cache.annotation.Cacheable;

import java.util.ArrayList;

public interface Category_Service {

  ArrayList<Category> getALL();


  ArrayList<CategoryDto> getALLdto();

  Category findById(int id);
  Category save(Category category);
  void delete(int id);
  Category findByName(String name);
  ArrayList<Category> searchbyName(String name);
}
