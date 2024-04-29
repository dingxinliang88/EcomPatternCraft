package com.youyi.ecom.repo;

import com.youyi.ecom.pojo.po.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByName(String username);

    User findByNameAndPwd(String username, String password);
}
