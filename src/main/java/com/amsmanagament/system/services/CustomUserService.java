package com.amsmanagament.system.services;

import com.amsmanagament.system.model.User;
import com.amsmanagament.system.model.User_Role;
import com.amsmanagament.system.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByPhoneNumber(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with number: " + username);
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        User_Role role = user.getRole();
        authorities.add(new SimpleGrantedAuthority( role.name()));

        return new org.springframework.security.core.userdetails.User(
                user.getPhoneNumber(),
                user.getPassword(),
                authorities
        );
    }
}
