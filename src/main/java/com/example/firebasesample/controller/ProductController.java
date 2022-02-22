package com.example.firebasesample.controller;

import com.example.firebasesample.entity.Product;
import com.example.firebasesample.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping("/add-product")
    public String saveProduct(@RequestBody Product product) throws ExecutionException, InterruptedException {
        return productService.saveProduct(product);
    }

    @GetMapping("/products")
    public List<Product> getAllProducts() throws ExecutionException, InterruptedException {
        return productService.getProductDetails();
    }

    @GetMapping("/products/{id}")
    public Product getProduct(@PathVariable String id) throws ExecutionException, InterruptedException {
        return productService.getProductDetailsById(id);
    }

    @PutMapping("/update-product/{id}")
    public String updateProduct(@PathVariable String id, @RequestBody Product product) throws ExecutionException, InterruptedException {
        return productService.updateProduct(id, product);
    }

    @DeleteMapping("/delete-product/{id}")
    public String deleteProduct(@PathVariable String id) throws ExecutionException, InterruptedException {
        return productService.deleteProduct(id);
    }
}
