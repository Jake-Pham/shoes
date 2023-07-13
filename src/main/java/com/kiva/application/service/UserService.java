package com.kiva.application.service;


import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.kiva.application.entity.User;
import com.kiva.application.model.dto.UserDTO;
import com.kiva.application.model.request.ChangePasswordRequest;
import com.kiva.application.model.request.CreateUserRequest;
import com.kiva.application.model.request.UpdateProfileRequest;
import com.kiva.application.model.request.UpdateStatusRequest;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.mail.MessagingException;

@Service
public interface UserService {
    List<UserDTO> getListUsers();

    Page<User> adminListUserPages(String fullName, String phone, String email, Integer page);

    User createUser(CreateUserRequest createUserRequest);

    void changePassword(User user, ChangePasswordRequest changePasswordRequest);

    User updateProfile(User user, UpdateProfileRequest updateProfileRequest);
    
    void sendVerificationEmail(User user, String siteURL) throws MessagingException, UnsupportedEncodingException;
    
    boolean verify(String verifycationCode);
    
    User updateStatus(User user, UpdateStatusRequest updateStatusRequest);
    
    User findByVerifyCode(String verifyCode);
}
