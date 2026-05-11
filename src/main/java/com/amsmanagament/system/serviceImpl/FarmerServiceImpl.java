package com.amsmanagament.system.serviceImpl;

import com.amsmanagament.system.exception.ResourceNotFoundException;
import com.amsmanagament.system.model.*;
import com.amsmanagament.system.repo.FarmerRepo;
import com.amsmanagament.system.repo.UserRepo;
import com.amsmanagament.system.services.Farmerservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class FarmerServiceImpl implements Farmerservice {

    @Autowired
    FarmerRepo farmerRepo;

    @Autowired
    UserRepo userRepo;
    @Autowired
    private NotificationEventPublisher eventPublisher;


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
        // Get logged-in user's phone number from Spring Security
        String phoneNumber = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Fetch the full User entity from the database
        User loggedInUser = userRepo.findByPhoneNumber(phoneNumber);
        if (loggedInUser == null) {
            throw new Exception("Logged-in user not found");
        }

        // Check if the user already has a farmer profile
        if (farmerRepo.existsByUser(loggedInUser)) {
            throw new Exception("You have already created a farmer profile.");
        }

        // Create and save new Farmer
        Farmer farmerToSave = new Farmer();
        farmerToSave.setFarmerName(farmer.getFarmerName());
        farmerToSave.setFarmerAddress(farmer.getFarmerAddress());
        farmerToSave.setWardNo(farmer.getWardNo());
        farmerToSave.setFarmerlogitute(farmer.getFarmerlogitute());
        farmerToSave.setFarmerlatitude(farmer.getFarmerlatitude());
        farmerToSave.setUser(loggedInUser); // important to avoid null user
        farmerToSave.setDocument(farmer.getDocument());
        farmerToSave.setStatus(Farmer_Status.STATUS_PENDING);

        Farmer savedFarmer = farmerRepo.save(farmerToSave);

        // 🔔 PUSH NOTIFICATION HERE
        eventPublisher.publish(
                new NotificationEvent(
                        loggedInUser,   // receiver
                        null,           // sender (system)
                        NotificationAction.SYSTEM_ALERT,
                        "🌾 Farmer profile submitted successfully (ID: " + savedFarmer.getId() + ")",
                        savedFarmer.getId()
                )
        );

        return savedFarmer;
    }



    @Override
    public Farmer updateFarmer(Farmer farmer) throws Exception {
        Farmer exitinguser=farmerRepo.findById(farmer.getId()).orElseThrow(()-> new ResourceNotFoundException("user not found this "+farmer.getId()));
        exitinguser.setFarmerName(farmer.getFarmerName());
        exitinguser.setFarmerAddress(farmer.getFarmerAddress());
        exitinguser.setDocument(farmer.getDocument());
        exitinguser.setWardNo(farmer.getWardNo());
        exitinguser.setUpdatedAt(LocalDateTime.now());


        return farmerRepo.save(exitinguser);
    }



    @Override
    public List<Farmer> getallfarmer() throws Exception {
        return farmerRepo.findAll();
    }

    @Override
    public Farmer finduser(User user) throws Exception {
        return farmerRepo.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Farmer not found for user with ID " + user.getId()));
    }

    @Override
    public Farmer verifyFarmer(Long id) throws Exception {
        Farmer findFarmer=farmerRepo.findById(id).orElseThrow(()->new RuntimeException("farmer not found this id"+id));
        findFarmer.setStatus(Farmer_Status.STATUS_ACCEPTED);

        return farmerRepo.save(findFarmer);
    }

    @Override
    public Farmer rejectFarmer(Long id) throws Exception {
        Farmer findFarmer=farmerRepo.findById(id).orElseThrow(()->new RuntimeException("farmer not found this id"+id));
        findFarmer.setStatus(Farmer_Status.STATUS_NOT_ACCEPTED);

        return farmerRepo.save(findFarmer);
    }

    @Override
    public void deleteFarmer(Long id) {
        Farmer farmer = farmerRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Farmer not found with id " + id));

        farmerRepo.delete(farmer);
    }

}
