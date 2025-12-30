package com.amsmanagament.system.services;

import com.amsmanagament.system.model.Seller;
import java.util.List;

public interface SellerService {

    Seller createSeller(Seller seller) throws Exception;

    Seller updateSeller(Seller seller) throws Exception;

    Seller getSellerById(Long id) throws Exception;

    Seller deleteSeller(Long id) throws Exception;

    List<Seller> getAllSellers() throws Exception;

    List<Seller> searchSellersByName(String name) throws Exception;
    List<Seller> findSellersInRange(double latitude, double longitude, double radiusKm);
}

