package com.amsmanagament.system.services;

import com.amsmanagament.system.model.Buyer;

import java.util.List;

public interface BuyerServices {

    Buyer createProfile(Buyer buyer) throws Exception;

    Buyer updateProfile(Buyer buyer) throws Exception;

    Buyer getBuyerById(Long id) throws Exception;

    Buyer deleteBuyer(Long id) throws Exception;

    List<Buyer> getAllBuyers() throws Exception;

  List<Buyer> searchBuyersByName(String name) throws Exception;
}
