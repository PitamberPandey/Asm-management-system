package com.amsmanagament.system.repo;

import com.amsmanagament.system.model.OptVerification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OtpRepo extends JpaRepository<OptVerification,Long> {

    public OptVerification findByPhoneNumber(String phoneNumber);

    void deleteByPhoneNumber(String phoneNumber);


}
