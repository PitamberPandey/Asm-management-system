package com.amsmanagament.system.services;

import com.amsmanagament.system.model.User;
public interface UserService {

        public User findUserByJwtToken(String jwt) throws Exception;

        public User findUserByNumber(String number)throws Exception;

        public  User findByUserId(Long id)throws Exception;

        public  User updateUser(User user)throws Exception;


}
