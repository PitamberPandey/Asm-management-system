package com.amsmanagament.system.controller;

import com.amsmanagament.system.Response.ApiCreateBuyer;
import com.amsmanagament.system.model.Buyer;
import com.amsmanagament.system.services.BuyerServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/buyer")
public class BuyerController {

    @Autowired
    private BuyerServices buyerServices;

    // Create buyer profile
    @PostMapping("/create")
    public ResponseEntity<ApiCreateBuyer> createProfileBuyer(@RequestBody Buyer buyer) throws Exception {
        Buyer created = buyerServices.createProfile(buyer);
        ApiCreateBuyer apiResponse = new ApiCreateBuyer("Profile created successfully", true, created);
        return ResponseEntity.ok(apiResponse);
    }

    // Update buyer profile
    @PutMapping("/update")
    public ResponseEntity<ApiCreateBuyer> updateProfile(@RequestBody Buyer buyer) throws Exception {
        Buyer updated = buyerServices.updateProfile(buyer);
        ApiCreateBuyer apiResponse = new ApiCreateBuyer("Profile updated successfully", true, updated);
        return ResponseEntity.ok(apiResponse);
    }
}
