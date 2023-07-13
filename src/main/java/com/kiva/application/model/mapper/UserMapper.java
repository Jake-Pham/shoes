package com.kiva.application.model.mapper;

import org.springframework.security.crypto.bcrypt.BCrypt;

import com.kiva.application.entity.User;
import com.kiva.application.model.dto.UserDTO;
import com.kiva.application.model.request.CreateUserRequest;

import net.bytebuddy.utility.RandomString;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;

public class UserMapper {
    public static UserDTO toUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setFullName(user.getFullName());
        userDTO.setEmail(user.getEmail());
        userDTO.setCity(user.getCity());
        userDTO.setDistrict(user.getDistrict());
        userDTO.setWard(user.getWard());
        userDTO.setAddress(user.getAddress());
        userDTO.setPhone(user.getPhone());
        userDTO.setAvatar(user.getAvatar());
        userDTO.setRoles(user.getRoles());

        return userDTO;
    }

    public static User toUser(CreateUserRequest createUserRequest) {
        User user = new User();
        user.setFullName(createUserRequest.getFullName());
        user.setEmail(createUserRequest.getEmail());
        // Hash password using BCrypt
        String hash = BCrypt.hashpw(createUserRequest.getPassword(), BCrypt.gensalt(12));
        user.setPassword(hash);
        user.setPhone(createUserRequest.getPhone());
        user.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        user.setEnabled(false);
        //user.setStatus(true);
        user.setRoles(new ArrayList<>(Arrays.asList("USER")));
        String randomCode = RandomString.make(64);
        user.setVerificationCode(randomCode);

        return user;
    }
}
