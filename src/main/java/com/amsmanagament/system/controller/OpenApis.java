package com.amsmanagament.system.controller;


import com.amsmanagament.system.exception.ResourceNotFoundException;
import com.amsmanagament.system.model.Category;
import com.amsmanagament.system.model.Product;
import com.amsmanagament.system.repo.CatogoriesRepo;
import com.amsmanagament.system.services.CategoryService;
import com.amsmanagament.system.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/catagories")
public class OpenApis {

    @Autowired
    CategoryService categoryService;

    @Autowired
    CatogoriesRepo catogoriesRepo;

    @Autowired
    ProductService productService;

    @GetMapping("/get")
    public List<Category> getAllCategories() throws Exception {
        return categoryService.getAllCategory();
    }

    @GetMapping("/searchproducts")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String keyword) {
        List<Product> products = productService.searchProducts(keyword);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/products/category/{id}")
    public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable Long id) throws Exception {
        Category category = catogoriesRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        List<Product> products = productService.getProductsByCategory(category.getId());
        return ResponseEntity.ok(products);


    }
    @GetMapping("/products/all")
    public ResponseEntity<List<Product>> getAllProducts() {
            List<Product> products= productService.getAllProducts();
            return ResponseEntity.ok(products);
    }


}
