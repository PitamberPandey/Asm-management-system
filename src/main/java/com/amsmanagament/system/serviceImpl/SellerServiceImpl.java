package com.amsmanagament.system.serviceImpl;

import com.amsmanagament.system.model.Seller;
import com.amsmanagament.system.model.User;
import com.amsmanagament.system.repo.SellerRepo;
import com.amsmanagament.system.services.SellerService;
import com.amsmanagament.system.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SellerServiceImpl implements SellerService {

    @Autowired
    UserService userService;

    @Autowired
    SellerRepo sellerRepo;
    @Override
    public Seller createSeller(Seller seller) throws Exception {
        // Get authenticated user's phone number
        String phonenumber = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        // Find the User object
        User user = userService.findUserByNumber(phonenumber);
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        // Create a new Seller and set fields
        Seller newSeller = new Seller();
        newSeller.setFarmername(seller.getFarmername());

        newSeller.setLatitude(seller.getLatitude());
        newSeller.setLongitude(seller.getLongitude());
        newSeller.setUser(user);  // Link User to Seller

        // Save to database using repository
        return sellerRepo.save(newSeller);
    }


    @Override
    public Seller updateSeller(Seller seller) throws Exception {
        Seller seller1=sellerRepo.findById(seller.getId()).orElseThrow(()->new RuntimeException("not exits"));
        seller1.setFarmername(seller.getFarmername());

        seller1.setLatitude(seller.getLatitude());
        seller1.setLongitude(seller.getLongitude());
         // Link User to Seller


        return sellerRepo.save(seller1);
    }

    @Override
    public Seller getSellerById(Long id) throws Exception {
        Seller user=sellerRepo.findById(id).orElseThrow(()->new RuntimeException("seller not found this id"));
        return user;
    }

    @Override
    public Seller deleteSeller(Long id) throws Exception {
        Seller seller=sellerRepo.findById(id).orElseThrow(()->new RuntimeException("Seller not found this id"));
        sellerRepo.delete(seller);
        return seller ;
    }

    @Override
    public List<Seller> getAllSellers() throws Exception {
        return sellerRepo.findAll();
    }

    @Override
    public List<Seller> searchSellersByName(String name) throws Exception {
        List<Seller> users=sellerRepo.findByFarmernameContainingIgnoreCase(name);
        if (users==null){
            throw  new RuntimeException("User not found");

        }
        return users;
    }

    @Override
    public List<Seller> findSellersInRange(double latitude, double longitude, double radiusKm) {
        return List.of();
    }
}
