package com.amsmanagament.system.services;

import com.amsmanagament.system.model.Category;

import java.util.List;

public interface CategoryService {
    public  Category createCategory(Category category) throws  Exception;
    Category updateCatogory(Category category) throws  Exception;
    Category deleteByCategoryid(Long id) throws  Exception;
    List<Category> getAllCategory()throws  Exception;
    Category searchbycatorgoryname(String name) throws  Exception;


}
