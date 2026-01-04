package com.amsmanagament.system.serviceImpl;

import com.amsmanagament.system.exception.ResourceNotFoundException;
import com.amsmanagament.system.model.Buyer;
import com.amsmanagament.system.model.User;
import com.amsmanagament.system.repo.ByerRepo;
import com.amsmanagament.system.repo.FarmerRepo;
import com.amsmanagament.system.repo.UserRepo;
import com.amsmanagament.system.services.BuyerServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BuyerServiceImpl implements BuyerServices {

    @Autowired
    ByerRepo buyerRepository;

    @Autowired
    UserRepo userRepo;

    @Autowired
    FarmerRepo farmerRepo;


    @Override
    public Buyer createProfile(Buyer buyer) throws Exception {
        // Get logged-in user's phone number from Spring Security
        String phoneNumber = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Fetch the full User entity from the database
        User loggedInUser = userRepo.findByPhoneNumber(phoneNumber);
        if (loggedInUser == null) {
            throw new Exception("Logged-in user not found");
        }


        if (buyerRepository.existsByUser(loggedInUser)) {
            throw new ResourceNotFoundException("you have already crated a buyer profile");
        }

        Buyer newBuyer = new Buyer();
        newBuyer.setFullName(buyer.getFullName());
        newBuyer.setAddress(buyer.getAddress());
        newBuyer.setUser(buyer.getUser());
        newBuyer.setContactNumber(buyer.getContactNumber());
        newBuyer.setUser(loggedInUser);
        newBuyer.setUpdatedAt(LocalDateTime.now());

        return buyerRepository.save(newBuyer); // ✅ Save the new object
    }


    @Override
    public Buyer updateProfile(Buyer buyer) throws Exception {
        Buyer existingBuyer = buyerRepository.findById(buyer.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Buyer not found with ID: " + buyer.getId()));

        try {
            // Update fields
            existingBuyer.setFullName(buyer.getFullName());
            existingBuyer.setAddress(buyer.getAddress());
            existingBuyer.setContactNumber(buyer.getContactNumber());
            existingBuyer.setUpdatedAt(java.time.LocalDateTime.now());

            return buyerRepository.save(existingBuyer);
        } catch (Exception e) {
            throw new ResourceNotFoundException("Failed to update buyer profile: " );
        }
    }

    @Override
    public Buyer getBuyerById(Long id) throws Exception {
        return buyerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Buyer not found with ID: " + id));
    }

    @Override
    public Buyer deleteBuyer(Long id) throws Exception {
        Buyer existingBuyer = buyerRepository.findById(id)
                .orElseThrow(() -> new Exception("Buyer not found with ID: " + id));

        try {
            buyerRepository.delete(existingBuyer); // Hard delete
            return existingBuyer;
        } catch (Exception e) {
            throw new Exception("Failed to delete buyer: " + e.getMessage());
        }
    }

    @Override
    public List<Buyer> getAllBuyers() throws Exception {
        try {
            return buyerRepository.findAll();
        } catch (Exception e) {
            throw new Exception("Failed to fetch buyers: " + e.getMessage());
        }
    }

    @Override
    public List<Buyer> searchBuyersByName(String name) throws Exception {
        try {
            return buyerRepository.findByUsername(name);
        } catch (Exception e) {
            throw new Exception("Failed to search buyers by name: " + e.getMessage());
        }
    }


}
