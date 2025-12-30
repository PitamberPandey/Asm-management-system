package com.amsmanagament.system.controller;

import com.amsmanagament.system.Response.ApiCreateBuyer;
import com.amsmanagament.system.exception.ResourceNotFoundException;
import com.amsmanagament.system.model.Buyer;
import com.amsmanagament.system.model.Seller;
import com.amsmanagament.system.services.BuyerServices;
import com.amsmanagament.system.services.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/buyer")
public class BuyerController {

    @Autowired
    private BuyerServices buyerServices;


    @Autowired
    private SellerService sellerService;

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

    @GetMapping("/search/seller")
    public List<Seller> search(@RequestParam String keyword) throws Exception {
        List<Seller> sellers=sellerService.searchSellersByName(keyword);
        if(sellers==null){
            throw  new ResourceNotFoundException("Empty seller");
        }
        return sellers;
    }
}
