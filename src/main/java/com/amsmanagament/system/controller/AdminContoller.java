package com.amsmanagament.system.controller;



import com.amsmanagament.system.Response.ApiCreateResponse;
import com.amsmanagament.system.Response.ApiResponse;
import com.amsmanagament.system.Response.ApiResponseCategory;
import com.amsmanagament.system.Response.SellerResponse;
import com.amsmanagament.system.exception.ResourceNotFoundException;
import com.amsmanagament.system.model.*;
import com.amsmanagament.system.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminContoller {

    @Autowired
    private UserService userService;

    @Autowired
    Farmerservice farmerservice;

    @Autowired
    BuyerServices buyerServices;

    @Autowired
    CategoryService categoryService;

    @Autowired
    ProductService productService;


    @Autowired
    SellerService sellerService;

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) throws Exception {
        User user = userService.findByUserId(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/phone/{number}")
    public ResponseEntity<User> getUserByPhoneNumber(@PathVariable String number) throws Exception {
        User user = userService.findUserByNumber(number);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/user/update")
    public ResponseEntity<User> updateUser(@RequestBody User user) throws Exception {
        User updatedUser = userService.updateUser(user);
        return ResponseEntity.ok(updatedUser);

    }

    @DeleteMapping("/user/delete/{id}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long id) throws Exception {
        userService.deleteUser(id);
        return ResponseEntity.ok(new ApiResponse("User deleted successfully!", true));
    }

    @GetMapping("/farmer")
    public List<Farmer> getAllFarmer() throws Exception {
        return farmerservice.getallfarmer();
    }

    @GetMapping("/farmer/{id}")
    public ResponseEntity<Farmer> getFarmerById(@PathVariable Long id) throws Exception {
        Farmer user = farmerservice.findById(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping("farmer/verify/{id}")
    public ResponseEntity<ApiCreateResponse> verifyFarmer(@PathVariable Long id) throws Exception {

        Farmer farmer = farmerservice.verifyFarmer(id);

        ApiCreateResponse apiResponse = new ApiCreateResponse(
                "Farmer verified successfully",
                true,
                farmer
        );

        return ResponseEntity.ok(apiResponse);
    }
    @PostMapping("farmer/reject/{id}")
    public ResponseEntity<ApiCreateResponse> rejectFarmer(@PathVariable Long id) throws Exception {

        Farmer farmer = farmerservice.rejectFarmer(id);

        ApiCreateResponse apiResponse = new ApiCreateResponse(
                "Farmer is rejected for improper document",
                false,
                farmer
        );

        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/buyer/{id}")
    public ResponseEntity<Buyer> getBuyerById(@PathVariable Long id) throws Exception {
        Buyer user = buyerServices.getBuyerById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/buyer")
    public  List<Buyer> getAllBuyer() throws Exception {
        return buyerServices.getAllBuyers();
    }

    @DeleteMapping("/buyer/delete/{id}")
    public ResponseEntity<ApiResponse> deleteBuyer(@PathVariable Long id) throws Exception {
        buyerServices.deleteBuyer(id);
        return ResponseEntity.ok(new ApiResponse("Buyer deleted successfully!", true));
    }

    @GetMapping("/buyer/search")
    public List<Buyer> searchBuyersByName(@RequestParam String name) throws Exception {
        return buyerServices.searchBuyersByName(name);
    }

    @PostMapping("/create/category")
    public ResponseEntity<ApiResponseCategory> createCategory(@RequestBody Category category) throws Exception {
        Category createdCategory = categoryService.createCategory(category);
        ApiResponseCategory apiResponse = new ApiResponseCategory("Category created successfully", true, createdCategory);
        return ResponseEntity.ok(apiResponse);
    }
    @PutMapping("/update/category")
    public ResponseEntity<ApiResponseCategory> updateCategory(@RequestBody Category category) throws Exception {
        Category updatedCategory = categoryService.updateCatogory(category);
        ApiResponseCategory apiResponse = new ApiResponseCategory("Category updated successfully", true, updatedCategory);
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/delete/category/{id}")
    public ResponseEntity<ApiResponseCategory> deleteCategory(@PathVariable Long id) throws Exception {
        Category deletedCategory = categoryService.deleteByCategoryid(id);
        ApiResponseCategory apiResponse = new ApiResponseCategory("Category deleted successfully", true, deletedCategory);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/search/category")
    public ResponseEntity<ApiResponseCategory> searchCategoryByName(@RequestParam String name) throws Exception {
        Category category = categoryService.searchbycatorgoryname(name);
        ApiResponseCategory apiResponse = new ApiResponseCategory("Category found successfully", true, category);
        return ResponseEntity.ok(apiResponse);

    }


    @GetMapping("/farmer/product/{id}")
    public ResponseEntity<Product> getProductsByfarmer(@PathVariable Long id) throws Exception {
        List<Product> products = productService.getProductsBySeller(id);
        return ResponseEntity.ok().body((products.get(0)));
    }


    @PostMapping("/delete/{id}")
    public ResponseEntity<SellerResponse> deleteSeller(@PathVariable("id") Long id) throws Exception {
        try {
            Seller seller1 = sellerService.deleteSeller(id);
            SellerResponse sellerResponse = new SellerResponse("seller delete successfully", true, seller1);
            return ResponseEntity.ok(sellerResponse);
        } catch (Exception e) {
            SellerResponse sellerResponse = new SellerResponse(" failed  for deleteUser seller", false, null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(sellerResponse);
        }


    }

    @GetMapping("/seller")
    public List<Seller> sellers() throws Exception {
        List<Seller> sellers=sellerService.getAllSellers();
        return sellers;
    }


    @GetMapping("seller/{id}")
        public Seller getSeller(@PathVariable("id") Long id) throws Exception {
        try {
            Seller seller = sellerService.getSellerById(id);
            return seller;
        } catch (Exception e) {
            throw new ResourceNotFoundException("seller not found this id" + id);
        }

    }

}
