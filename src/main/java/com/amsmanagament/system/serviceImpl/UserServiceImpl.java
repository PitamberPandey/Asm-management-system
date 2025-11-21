package com.amsmanagament.system.serviceImpl;

import com.amsmanagament.system.config.JwtProvider;
import com.amsmanagament.system.model.User;
import com.amsmanagament.system.repo.UserRepo;
import com.amsmanagament.system.services.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private JwtProvider provider;

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
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + id));
    }

    @Override
    public User updateUser(User user) throws Exception {
        return null;
    }
}
