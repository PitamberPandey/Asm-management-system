package com.amsmanagament.system.serviceImpl;

import com.amsmanagament.system.exception.ResourceNotFoundException;
import com.amsmanagament.system.model.Category;
import com.amsmanagament.system.model.User;
import com.amsmanagament.system.repo.CatogoriesRepo;
import com.amsmanagament.system.repo.UserRepo;
import com.amsmanagament.system.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CatogoryServiceImpl implements CategoryService {

    @Autowired
    CatogoriesRepo catogoriesRepo;

    @Autowired
    UserRepo userRepo;
    @Override
    public Category createCategory(Category category) throws Exception {
        String phoneNumber= SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        User loggedInUser=userRepo.findByPhoneNumber(phoneNumber);


        Category newCategory=new Category();
        newCategory.setCategoryName(category.getCategoryName());
        newCategory.setDescription(category.getDescription());
        newCategory.setCreatedBy(loggedInUser);



        return  catogoriesRepo.save(category);
    }

    @Override
    public Category updateCatogory(Category category) throws Exception {
        Category category1= catogoriesRepo.findById(category.getId()).orElseThrow(()->new Exception("Category not found"));
        category1.setCategoryName(category.getCategoryName());
        category1.setDescription(category.getDescription());
        return catogoriesRepo.save(category1);
    }

    @Override
    public Category deleteByCategoryid(Long id) throws Exception {
        Category deletedCategory = catogoriesRepo.findById(id).orElseThrow(() -> new Exception("Category not found"));
        catogoriesRepo.delete(deletedCategory);
        return deletedCategory;
    }

    @Override
    public List<Category> getAllCategory() throws Exception {
        return catogoriesRepo.findAll();
    }

    @Override
    public Category searchbycatorgoryname(String name) throws Exception {
        return catogoriesRepo.findByCategoryName(name).orElseThrow(()->new Exception("Category not found"));
    }
}
