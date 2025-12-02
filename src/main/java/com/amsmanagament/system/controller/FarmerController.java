package com.amsmanagament.system.controller;


import com.amsmanagament.system.Response.ApiCreateResponse;
import com.amsmanagament.system.Response.ApiResponse;
import com.amsmanagament.system.model.Farmer;

import com.amsmanagament.system.services.Farmerservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/farmer")
public class FarmerController {

    @Autowired
    Farmerservice farmerservice;

    @GetMapping("/user/{id}")
    public ResponseEntity<Farmer> getUserById(@PathVariable Long id) throws Exception {
        Farmer user = farmerservice.findById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/user")
    public ResponseEntity<List<Farmer>> getUsersByUsername(@RequestParam String username) throws Exception {
        return ResponseEntity.ok(farmerservice.findByUsername(username));
    }

    @PostMapping("/create/farmer")
    public ResponseEntity<ApiCreateResponse> createFarmer(@RequestBody Farmer farmer) {
        try {
            Farmer createdFarmer = farmerservice.createFarmer(farmer);
            ApiCreateResponse response = new ApiCreateResponse( "Farmer created successfully",true,createdFarmer);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiCreateResponse response = new ApiCreateResponse( "Failed to create farmer: " + e.getMessage(), false,null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

    }

    @PutMapping("/update/farmer")
    public ResponseEntity<ApiCreateResponse> updateFarmer(@RequestBody Farmer farmer) {
        try {
            // Call the update method in your service
            Farmer updatedFarmer = farmerservice.updateFarmer(farmer);

            ApiCreateResponse response = new ApiCreateResponse(
                    "Farmer updated successfully",
                    true,
                    updatedFarmer
            );
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            ApiCreateResponse response = new ApiCreateResponse(
                    "Failed to update farmer: " + e.getMessage(),
                    false,
                    null // use null because update failed
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

}






