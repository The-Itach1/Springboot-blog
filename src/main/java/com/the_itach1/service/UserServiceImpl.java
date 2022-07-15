package com.the_itach1.service;

import com.the_itach1.dao.UserRepository;
import com.the_itach1.po.User;
import com.the_itach1.util.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User checkUser(String username,String password)
    {
        User user = userRepository.findByUsernameAndPassword(username, Md5Util.code(password));
        return user;
    }
}
