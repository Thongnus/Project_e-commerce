package com.example.api.Service.Serviceimpl;

import com.example.api.Entity.Category;
import com.example.api.Entity.Order;
import com.example.api.Entity.Product;
import com.example.api.Reponsitory.Product_Reponsitory;
import com.example.api.Service.Product_Service;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.NaturalIdCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service

public class Product_impl implements Product_Service {
    @Autowired
    Product_Reponsitory productReponsitory;
    @Override
   // @CacheEvict(value = "products",allEntries = true)
        public Product createProduct(Product product)   {
            return productReponsitory.save(product);
        }

    @Override
//    @Cacheable(value = "product",key = "#id")
    public Optional<Product> getProductbyId(int id) {
        return productReponsitory.findById(id);
    }


    @Override
   // @Cacheable(value = "products" )
    public ArrayList<Product> getAllProducts() {
        System.out.println("Get Data");
        return (ArrayList<Product>) productReponsitory.findAll();
    }

    @Override
//@Caching(put = @CachePut(value = "product",key = "#id"),
  //         evict = @CacheEvict(value = "products",allEntries = true)
 //       )
    public Product updateProduct(int id, Product newProduct) {
        Optional<Product> optionalProduct = productReponsitory.findById(id);
        if (optionalProduct.isPresent()) {
            Product existingProduct = optionalProduct.get();
            existingProduct.setTitle(newProduct.getTitle());
            existingProduct.setPrice(newProduct.getPrice());
            existingProduct.setImg(newProduct.getImg()); // Cập nhật ảnh
            existingProduct.setDescription(newProduct.getDescription());
            existingProduct.setCategory(newProduct.getCategory());

            return productReponsitory.save(existingProduct);
        } else {
            return null;
        }
    }
    @Override
    public List<Product> search(String keyword,Category category) {
        return productReponsitory.findByTitleContaining(keyword,category);
    }

    @Override
   // @Caching(evict ={ @CacheEvict(value = "products",allEntries = true),
  //      @CacheEvict(value = "product",key = "#id")})
    public void deleteProduct(int id) {
        productReponsitory.deleteById(id);
    }

    @Override
   //@Cacheable(value = "product",key = "#id")
    public Optional<Product> findbyId(int id) {
        return productReponsitory.findById(id);
    }

    @Override
    public Page<Product> getAllbyCategory(Category category,Pageable pageable) {
        return  productReponsitory.findAllByCategory(category,pageable);
    }

    @Override
    public ArrayList<Product> getAllProductByCategory(Category category) {

        return  productReponsitory.findAllByCategory(category);
    }

    @Override
    public ArrayList<Product> getAllProductlimit() {
        return productReponsitory.findFirst6By();
    }
}
