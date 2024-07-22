package com.example.api.RestController.Admin;

import com.example.api.Entity.Category;
import com.example.api.Entity.Order;
import com.example.api.Entity.Product;
import com.example.api.Service.Category_Service;
import com.example.api.Service.ProductRedis_Service;
import com.example.api.Service.Product_Service;
import com.example.api.Service.Serviceimpl.Category_impl;
import com.example.api.Service.Serviceimpl.Product_impl;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
@Slf4j
@RestController
@RequestMapping("/api-product")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class quanlyproductController {
    @Autowired
    private Product_Service productService;
    @Autowired
    private Category_Service categoryService;
    @Autowired
    ProductRedis_Service redisService;
    @PostMapping("/add")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Product> createProduct( @RequestParam("title") String title,
                                                  @RequestParam("price") int price,
                                                  @RequestParam("img") MultipartFile img,
                                                  @RequestParam("description") String description,
                                                  @RequestParam("category") int idcategory) throws IOException {

        // Kiểm tra xem có category nào tương ứng với categoryId hay không
        Category category = categoryService.findById(idcategory);
        if (category == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        // Chuyển đổi file ảnh thành byte array
        byte[] file = img.getBytes();

        // Tạo đối tượng Product và thiết lập các thuộc tính
        Product product = new Product();
        product.setTitle(title);
        product.setPrice(price);
        product.setDescription(description);
        product.setImg(file);
        product.setCategory(category);


        // Lưu Product vào cơ sở dữ liệu
        Product savedProduct = productService.createProduct(product);

        return ResponseEntity.ok(savedProduct);
    }
// phan trang product
    @GetMapping("/get/{idcategory}")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllProductsphantrang( @PathVariable("idcategory") int idcategory, @RequestParam(defaultValue = "0")int page,
                                            @RequestParam(defaultValue = "5") int size) {
        Category category = categoryService.findById(idcategory);
        Pageable pageable = PageRequest.of(page,size);
        Page<Product> list = productService.getAllbyCategory(category,pageable);

        return ResponseEntity.ok(list);
    }


@GetMapping("/category/{categoryname}")
public ResponseEntity<?> getProductByCategory(@PathVariable("categoryname") String name){
        Category category = categoryService.findByName(name);

  List<Product> list= productService.getAllProductByCategory(category);
  if(list.isEmpty()){

      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(" Not found by name"+name);
  }else {
  return  ResponseEntity.ok(list);}
}

    // Phương thức lấy sản phẩm theo ID
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable int id) {
        Optional<Product> product = productService.getProductbyId(id);
        return product.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    // Phương thức cập nhật sản phẩm
    @PutMapping("/{id}")
   // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") int id,
                                                 @RequestParam("title") String title,
                                                 @RequestParam("price") int price,
                                                 @RequestParam("img") MultipartFile img,
                                                 @RequestParam("description") String description,
                                                 @RequestParam("category") int idcategory
                                                ) {
        try {
            Product updatedProduct = new Product();
            updatedProduct.setTitle(title);
            updatedProduct.setPrice(price);
            updatedProduct.setImg(img.getBytes());
            updatedProduct.setDescription(description);
            Category category = categoryService.findById(idcategory);
            updatedProduct.setCategory(category);
            Product product = productService.updateProduct(id, updatedProduct);
            return ResponseEntity.ok(product);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Phương thức xóa sản phẩm
    @DeleteMapping("/{id}")
   // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteProduct(@PathVariable int id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok("delete success");
    }
    @GetMapping("/getlimit")
    public ResponseEntity<?> getlimit( )
    {
        return ResponseEntity.ok(productService.getAllProductlimit());


    }
    @GetMapping("/search")
    public ResponseEntity<List<Product>> search(@RequestParam("keyword")String keyword,@RequestParam("category") Category category){


        List<Product> searchResult = productService.search(keyword,category);
        return ResponseEntity.ok(searchResult);
    }
    @GetMapping("/getAll")
    public  ResponseEntity<?>  getAll() throws JsonProcessingException {
         ArrayList<Product> arr= redisService.getallProduct();
         if(arr!=null&&!arr.isEmpty()){
             return ResponseEntity.ok(arr);
         }
         else {
          ArrayList<Product> getall=  productService.getAllProducts() ;
           if(getall.isEmpty()){
               return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found products");
           }else {
               redisService.SaveAllproduct(getall);
               return  ResponseEntity.ok(getall);
           }
         }

    }
    @GetMapping("/category/cache/{categoryname}")
    public ResponseEntity<?> getProductByNameCategory(@PathVariable("categoryname") String name) throws JsonProcessingException {
        Category category = categoryService.findByName(name);



         ArrayList<Product> arr = redisService.getallProductbynamecategory(name);
            if(!arr.isEmpty()){
             return    ResponseEntity.ok(arr);
            }else {
                ArrayList<Product> list= productService.getAllProductByCategory(category);
                if(list.isEmpty()){
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found product of category"+name);
                }else {
                    //String key= redisService.getKeyFrom(name);
                redisService.SaveAllproductBycategory(name,list);
                return  ResponseEntity.ok(list);}
            }}



}
