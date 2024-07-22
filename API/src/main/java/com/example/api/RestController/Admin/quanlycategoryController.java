package com.example.api.RestController.Admin;

import com.example.api.Dto.CategoryDto;
import com.example.api.Entity.Category;
import com.example.api.Service.Category_Service;
import com.example.api.Service.Redis_service;
import com.example.api.Service.Serviceimpl.Category_impl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api-category")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@EnableCaching
public class quanlycategoryController {
    @Autowired
    Category_Service categoryImpl;
    @Autowired
    Category_impl c;
    @Autowired
    RedisTemplate<String,Object> redisTemplate;
    //list ALL
    @Autowired
    Redis_service redisService;
    @Autowired
    ObjectMapper objectMapper;
    @GetMapping("")
    public ResponseEntity<ArrayList<?>> getALl()  {


        return  ResponseEntity.ok(c.getALLdto());

    }
    @GetMapping("/{id}")
    public ResponseEntity<Category> getById(@PathVariable("id") int id){
        Category category = categoryImpl.findById(id);
        if(category==null) {
            return ResponseEntity.notFound().build();
        }else{

            return ResponseEntity.ok(category);
        }

    }
    @PostMapping("/add")
   // @PreAuthorize("hasRole('ADMIN')")
    @CacheEvict("categorys")
    public ResponseEntity<?> add(@RequestBody Category category) {
      Category isexist= categoryImpl.findByName(category.getNameCategory());
        if(isexist!=null){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Đã tồn tại "+isexist.getNameCategory());

        }else{
            categoryImpl.save(category);
            return ResponseEntity.status(HttpStatus.CREATED).body(category);
        }


    }
    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> update(@PathVariable("id") int id, @RequestBody Category updatedCategory){
        Category existingCategory = categoryImpl.findById(id);
        if(existingCategory == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy category với id: " + id);
        } else {
            existingCategory.setNameCategory(updatedCategory.getNameCategory());

            categoryImpl.save(existingCategory);
            return ResponseEntity.ok(existingCategory);
        }
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable("id") int id){
       Category category= categoryImpl.findById(id);
        if(category==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("không tìm thấy với id:"+id);

        }else{
            categoryImpl.delete(id);
            return ResponseEntity.ok().body("xóa thành công");
        }

    }
    //search
    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam("keyword") String key){
        ArrayList<Category> categories = categoryImpl.searchbyName(key);
        return ResponseEntity.ok(categories);
    }

}
