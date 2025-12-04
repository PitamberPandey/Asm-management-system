package com.amsmanagament.system.controller;



import com.amsmanagament.system.Response.ApiCreateResponse;
import com.amsmanagament.system.Response.ApiResponse;
import com.amsmanagament.system.model.Buyer;
import com.amsmanagament.system.model.Farmer;
import com.amsmanagament.system.model.User;
import com.amsmanagament.system.services.BuyerServices;
import com.amsmanagament.system.services.Farmerservice;
import com.amsmanagament.system.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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

}
