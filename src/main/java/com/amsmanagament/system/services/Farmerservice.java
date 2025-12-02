package com.amsmanagament.system.services;

import com.amsmanagament.system.model.Farmer;

import java.util.List;

public interface Farmerservice {

    public Farmer findById(Long id) throws Exception;
    public List<Farmer> findByUsername(String username) throws Exception;
    Farmer createFarmer(Farmer farmer) throws  Exception;
    Farmer updateFarmer(Farmer farmer) throws  Exception;
}
