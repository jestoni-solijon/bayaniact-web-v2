package com.bayaniact.common.security;


import com.bayaniact.common.entity.Event;
import com.bayaniact.common.entity.Role;
import com.bayaniact.common.entity.User;
import jakarta.mail.MessagingException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

	public User findByUserName(String userName);

	void save(User user) throws MessagingException;

	User findByEmail(String email); // New method

	User findByResetToken(String token);

	Page<User> findAll(Pageable pageable);

	Page<User> findByEmail(String email, Pageable pageable);

	Page<User> findByUsername(String username, Pageable pageable);

	void updateUserRoles(String userUUID, List<Role> roles);

	User findByUserUuid(String userUUID);

	User findByVerificationToken(String verificationToken);

	void deleteUserByUuid(String userUUID);

}
