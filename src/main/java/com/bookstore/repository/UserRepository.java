package com.bookstore.repository;

import com.bookstore.entity.User;
import java.math.BigDecimal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface UserRepository extends JpaRepository<User, String> {
    @Query(value = "from User where userId = :userId")
    User getUserById(@Param("userId") Integer userId);

    @Query(value="from User where name= :name")
    User getUserByName(@Param("name") String name);

    @Modifying
    @Query(value="insert into `user`(name,email) values(?1,?2)",nativeQuery = true)
    void addUser(String name, String email);

    @Query(value="select * from `user` where user_id in (select user_id from `user_auth` where user_type=0)",nativeQuery = true)
    List<User> getAllUsers();

    @Modifying
    @Query(value="update `user` set enabled=?2 where `user_id`=?1",nativeQuery = true)
    void updateUserStatus(Integer userId,Boolean enabled);
}
