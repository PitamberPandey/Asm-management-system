package com.amsmanagament.system.serviceImpl;

import com.amsmanagament.system.config.JwtProvider;
import com.amsmanagament.system.exception.ResourceNotFoundException;
import com.amsmanagament.system.model.Farmer;
import com.amsmanagament.system.model.User;
import com.amsmanagament.system.model.User_Role;
import com.amsmanagament.system.repo.FarmerRepo;
import com.amsmanagament.system.repo.UserRepo;
import com.amsmanagament.system.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.rsocket.server.RSocketServerException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private JwtProvider provider;
    @Autowired
    FarmerRepo farmerRepo;

    @Override
    @Transactional
    public User findUserByJwtToken(String jwtToken) throws Exception {
        // Remove "Bearer " if present
        if (jwtToken.startsWith("Bearer ")) {
            jwtToken = jwtToken.substring(7);
        }

        String number = provider.getNumberFromToken(jwtToken);
        User user = userRepo.findByPhoneNumber(number);

        if (user == null) {
            throw new Exception("User not found with number: " + number);
        }

        return user;
    }

    @Override
    public User findUserByNumber(String number) throws Exception {
        User user = userRepo.findByPhoneNumber(number);
        if (user == null) {
            throw new Exception("User not found with number: " + number);
        }
        return user;
    }

    @Override
    public User findByUserId(Long id) throws Exception {
        return userRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    @Override
    public User updateUser(User user) throws Exception {

        User existingUser = userRepo.findById(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + user.getId()));
        existingUser.setFullName(user.getFullName());
        existingUser.setPhoneNumber(user.getPhoneNumber());
        existingUser.setPassword(user.getPassword());

        if(user.getRole() == null) {
            user.setRole(User_Role.ROLE_FARM);
        }

        existingUser.setAddress(user.getAddress());
        existingUser.setProfileImage(user.getProfileImage());
        existingUser.setGender(user.getGender());
        existingUser.setDob(user.getDob());
return  userRepo.save(user);

    }

    @Override
    public User deleteUser(Long id) {

        User existingUser = userRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        userRepo.delete(existingUser);
        return existingUser;
    }


}
