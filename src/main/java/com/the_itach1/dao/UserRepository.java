package com.the_itach1.dao;

import com.the_itach1.po.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    //根据username和password来查询用户
    User findByUsernameAndPassword(String username,String password);
}
