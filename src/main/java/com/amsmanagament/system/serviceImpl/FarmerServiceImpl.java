package com.amsmanagament.system.serviceImpl;

import com.amsmanagament.system.exception.ResourceNotFoundException;
import com.amsmanagament.system.model.Farmer;
import com.amsmanagament.system.model.Farmer_Status;
import com.amsmanagament.system.repo.FarmerRepo;
import com.amsmanagament.system.services.Farmerservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FarmerServiceImpl implements Farmerservice {

    @Autowired
    FarmerRepo farmerRepo;


    @Override
    public Farmer findById(Long id) throws Exception {
        return farmerRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("user is not present in this" + id));
    }

    @Override
    public List<Farmer> findByUsername(String username) {
        return farmerRepo.findByUsername(username);
    }
    @Override
    public Farmer createFarmer(Farmer farmer) throws Exception {
        if (farmerRepo.findByFarmerName(farmer.getFarmerName()).isPresent()) {
            throw new Exception("Farmer with this name already exists");
        }

        Farmer farmerToSave = new Farmer();
        farmerToSave.setFarmerName(farmer.getFarmerName());
        farmerToSave.setFarmerAddress(farmer.getFarmerAddress());
        farmerToSave.setWardNo(farmer.getWardNo());
        farmerToSave.setUser(farmer.getUser());
        farmerToSave.setDocument(farmer.getDocument());
       farmerToSave.setStatus(Farmer_Status.STATUS_PENDING);
        farmerToSave.setCreatedAt(LocalDateTime.now());
        farmerToSave.setUpdatedAt(LocalDateTime.now());

        // Save the correct object
        return farmerRepo.save(farmer);
    }

    @Override
    public Farmer updateFarmer(Farmer farmer) throws Exception {
        Farmer exitinguser=farmerRepo.findById(farmer.getId()).orElseThrow(()-> new ResourceNotFoundException("user not found this "+farmer.getId()));
        exitinguser.setFarmerName(farmer.getFarmerName());
        exitinguser.setFarmerAddress(farmer.getFarmerAddress());
        exitinguser.setDocument(farmer.getDocument());
        exitinguser.setWardNo(farmer.getWardNo());
        return farmerRepo.save(exitinguser);
    }


}
