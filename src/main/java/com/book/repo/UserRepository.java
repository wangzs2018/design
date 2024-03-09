package com.book.repo;

import com.book.pojo.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserInfo, Integer> {
    //根据用户 userName查询信息
    UserInfo findByUserName(String userName);
    //根据用 户名称 和 密码 查询用户信息
    UserInfo findByUserNameAndUserPassword(String account, String password);
}
