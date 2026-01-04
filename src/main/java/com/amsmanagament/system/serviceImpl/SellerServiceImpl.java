package com.amsmanagament.system.serviceImpl;

import com.amsmanagament.system.model.Seller;
import com.amsmanagament.system.model.User;
import com.amsmanagament.system.repo.SellerRepo;
import com.amsmanagament.system.services.SellerService;
import com.amsmanagament.system.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
        newSeller.setShopAddress(seller.getShopAddress());
        newSeller.setContactNumber(seller.getContactNumber());

        newSeller.setLatitude(seller.getLatitude());
        newSeller.setLongitude(seller.getLongitude());
        newSeller.setCreatedAt(LocalDateTime.now());
        newSeller.setUpdatedAt(LocalDateTime.now());
        newSeller.setUser(user);  // Link User to Seller

        // Save to database using repository
        return sellerRepo.save(newSeller);
    }


    @Override
    public Seller updateSeller(Seller seller) throws Exception {
        Seller seller1 = sellerRepo.findById(seller.getId()).orElseThrow(() -> new RuntimeException("not exits"));
        seller1.setFarmername(seller.getFarmername());

        seller1.setLatitude(seller.getLatitude());
        seller1.setLongitude(seller.getLongitude());
        seller1.setShopAddress(seller.getShopAddress());
        seller1.setContactNumber(seller.getContactNumber());


        seller1.setCreatedAt(LocalDateTime.now());
        // Link User to Seller


        return sellerRepo.save(seller1);
    }

    @Override
    public Seller getSellerById(Long id) throws Exception {
        Seller user = sellerRepo.findById(id).orElseThrow(() -> new RuntimeException("seller not found this id"));
        return user;
    }

    @Override
    public Seller deleteSeller(Long id) throws Exception {
        Seller seller = sellerRepo.findById(id).orElseThrow(() -> new RuntimeException("Seller not found this id"));
        sellerRepo.delete(seller);
        return seller;
    }

    @Override
    public List<Seller> getAllSellers() throws Exception {
        return sellerRepo.findAll();
    }

    @Override
    public List<Seller> searchSellersByName(String name) throws Exception {
        List<Seller> users = sellerRepo.findByFarmernameContainingIgnoreCase(name);
        if (users == null) {
            throw new RuntimeException("User not found");

        }
        return users;
    }

    @Override
    public List<Seller> findSellersInRange(double latitude, double longitude, double radiusKm) {

        // Get all sellers from DB (however you load them)
        List<Seller> allSellers = sellerRepo.findAll();

        List<Seller> sellersInRange = new ArrayList<>();

        for (Seller seller : allSellers) {

            double sellerLat = seller.getLatitude();
            double sellerLon = seller.getLongitude();

            double distance = distanceInKm(latitude, longitude, sellerLat, sellerLon);

            if (distance <= radiusKm) {
                sellersInRange.add(seller);
            }
        }

        return sellersInRange;
    }

    // Haversine formula
    private double distanceInKm(double lat1, double lon1, double lat2, double lon2) {
        final double EARTH_RADIUS = 6371.0; // in km

        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS * c;
    }
}
