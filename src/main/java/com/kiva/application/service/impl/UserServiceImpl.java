package com.kiva.application.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

import com.kiva.application.entity.User;
import com.kiva.application.exception.BadRequestException;
import com.kiva.application.model.dto.UserDTO;
import com.kiva.application.model.mapper.UserMapper;
import com.kiva.application.model.request.ChangePasswordRequest;
import com.kiva.application.model.request.CreateUserRequest;
import com.kiva.application.model.request.UpdateProfileRequest;
import com.kiva.application.model.request.UpdateStatusRequest;
import com.kiva.application.repository.UserRepository;
import com.kiva.application.service.UserService;

import net.bytebuddy.utility.RandomString;

import static com.kiva.application.config.Contant.LIMIT_USER;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private JavaMailSender mailSender;

    @Override
    public List<UserDTO> getListUsers() {
        List<User> users = userRepository.findAll();
        List<UserDTO> userDTOS = new ArrayList<>();
        for (User user : users) {
            userDTOS.add(UserMapper.toUserDTO(user));
        }
        return userDTOS;
    }

    @Override
    public Page<User> adminListUserPages(String fullName, String phone, String email, Integer page) {
        page--;
        if (page < 0) {
            page = 0;
        }
        Pageable pageable = PageRequest.of(page, LIMIT_USER, Sort.by("created_at").descending());
        return userRepository.adminListUserPages(fullName, phone, email, pageable);
    }

    @Override
    public User createUser(CreateUserRequest createUserRequest) {
        User user = userRepository.findByEmail(createUserRequest.getEmail());
        if (user != null) {
            throw new BadRequestException("Email đã tồn tại trong hệ thống. Vui lòng sử dụng email khác!");
        }
        user = UserMapper.toUser(createUserRequest);
        userRepository.save(user);
        return user;
    } 

    @Override
    public void changePassword(User user, ChangePasswordRequest changePasswordRequest) {
        //Kiểm tra mật khẩu
        if (!BCrypt.checkpw(changePasswordRequest.getOldPassword(), user.getPassword())) {
            throw new BadRequestException("Mật khẩu cũ không chính xác");
        }

        String hash = BCrypt.hashpw(changePasswordRequest.getNewPassword(), BCrypt.gensalt(12));
        user.setPassword(hash);
        userRepository.save(user);
    }

    @Override
    public User updateProfile(User user, UpdateProfileRequest updateProfileRequest) {
        user.setFullName(updateProfileRequest.getFullName());
        user.setPhone(updateProfileRequest.getPhone());
        user.setCity(updateProfileRequest.getCity());
        user.setDistrict(updateProfileRequest.getDistrict());
        user.setWard(updateProfileRequest.getWard());
        user.setAddress(updateProfileRequest.getAddress());

        return userRepository.save(user);
    }

	@Override
	public void sendVerificationEmail(User user, String siteURL) throws MessagingException, UnsupportedEncodingException {
	    String subject = "Please Verify Your Registration";
	    String senderName = "Sneaker Shop";
	    String mailContent = "<p>Dear" + user.getFullName() + ",</p>";
	    mailContent += "<p>Please click the link below to verify your registration: </p>";
	    String verifyURL = siteURL + "/verify?code=" + user.getVerificationCode();
	    mailContent += "<h3><a href=\"" + verifyURL + "\" target=\"_self\">VERIFY</a></h3>";
	    mailContent += "<p>The Sneaker Shop</P>";
	    MimeMessage message = mailSender.createMimeMessage();
	    MimeMessageHelper helper = new MimeMessageHelper(message);
	    
	    helper.setFrom("phamk8875@gmail.com", senderName);
	    helper.setTo(user.getEmail());
	    helper.setSubject(subject);
	    helper.setText(mailContent, true);
	    mailSender.send(message);
	}

	@Override
	public boolean verify(String verifycationCode) {
		User user = userRepository.findByVerificationCode(verifycationCode);
		if (user == null || user.isEnabled()) {
	        return false;
	    } else {
	        user.setVerificationCode(null);
	        user.setEnabled(true);
	        userRepository.save(user);	         
	        return true;
	    }
	}

	@Override
	public User updateStatus(User user, UpdateStatusRequest updateStatusRequest) {
		user.setEnabled(updateStatusRequest.isEnabled());
        return userRepository.save(user);		
	}

	@Override
	public User findByVerifyCode(String verifyCode) {
		return userRepository.findByVerificationCode(verifyCode);
	}
}
