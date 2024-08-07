package com.example.api.Service;

import com.example.api.Entity.Category;
import com.example.api.Entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface Product_Service {
    Product createProduct(Product product);
    Optional<Product> getProductbyId(int id);

    ArrayList<Product> getAllProducts();
    Product updateProduct(int id, Product newProduct);
    public List<Product> search(String keyword, Category category);
    void deleteProduct(int id);
    Optional<Product> findbyId(int id);
    Page<Product> getAllbyCategory(Category category,Pageable pageable);
    ArrayList<Product> getAllProductByCategory(Category category);
    ArrayList<Product> getAllProductlimit();
}
