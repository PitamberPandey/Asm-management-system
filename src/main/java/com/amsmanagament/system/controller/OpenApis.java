package com.amsmanagament.system.controller;


import com.amsmanagament.system.model.Category;
import com.amsmanagament.system.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/catagories")
public class OpenApis {

    @Autowired
    CategoryService categoryService;

    @GetMapping("/get")
    public List<Category> getAllCategories() throws Exception {
        return categoryService.getAllCategory();
    }



}
