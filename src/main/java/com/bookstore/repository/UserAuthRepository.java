package com.bookstore.repository;

import com.bookstore.entity.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserAuthRepository extends JpaRepository<UserAuth, String> {
    @Query(value = "from UserAuth where username = :username and userPassword = :userPassword")
    UserAuth checkAuth(@Param("username") String userAccount, @Param("userPassword") String userPassword);

    @Modifying
    @Query(value="insert into `user_auth`(user_id,username,user_password,user_type) values(?1,?2,?3,?4)",nativeQuery = true)
    void addUserAuth(Integer userId, String username, String userPassword, Integer userType);

    @Query(value="from UserAuth where username= :username")
    UserAuth getUserAuthByUsername(@Param("username") String username);

    @Query(value="select count(*) from `user_auth` where `username`=?1",nativeQuery = true)
    Integer registerCheck(@Param("username") String username);
}