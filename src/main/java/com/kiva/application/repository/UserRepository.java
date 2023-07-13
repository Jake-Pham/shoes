package com.kiva.application.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.kiva.application.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findByEmail(String email);

    @Query(value = "SELECT * " +
            "FROM users u WHERE u.full_name LIKE CONCAT('%',?1,'%') " +
            "AND u.phone LIKE CONCAT('%',?2,'%') " +
            "AND u.email LIKE CONCAT('%',?3,'%') ",nativeQuery = true)
    Page<User> adminListUserPages(String fullName, String phone, String email, Pageable pageable);
    
    @Query( value = "SELECT * FROM users u WHERE u.verification_code = ?1", nativeQuery = true)
    User findByVerificationCode(String code);
    
    //@Query(value = "UPDATE users u SET u.enabled = true WHERE c.id LIEK CONCAT ('%',?1,'%')", nativeQuery = true)
    //void enable(Long id);
}
