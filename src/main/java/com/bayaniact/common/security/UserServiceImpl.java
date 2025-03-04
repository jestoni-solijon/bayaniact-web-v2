package com.bayaniact.common.security;

import java.time.LocalDateTime;
import java.util.*;

import com.bayaniact.common.email.EmailService;
import com.bayaniact.common.entity.Role;
import com.bayaniact.common.entity.User;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Service
public class UserServiceImpl implements UserService {

	@Autowired private UserDao userDao;
	@Autowired private RoleDao roleDao;
	@Autowired private BCryptPasswordEncoder passwordEncoder;
	@Autowired private SessionFactory sessionFactory;
	@Autowired private EntityManager entityManager;
	@Autowired private EmailService emailService;

	@Override
	public User findByUserName(String userName) {
		// check the database if the user already exists
		return userDao.findByUserName(userName);
	}

	@Override
	public void save(User user) throws MessagingException {

		// If user is already present, you can skip creating a new one and directly update their password
		if (user.getUserUUID() != null) {

			userDao.save(user); // This will update the existing user
		} else {
			// If user does not exist, create a new one
			User users = new User();
			// assign user details to the user object
			users.setUserName(user.getUserName());
			users.setPassword(passwordEncoder.encode(user.getPassword()));
			users.setFirstName(user.getFirstName());
			users.setLastName(user.getLastName());
			users.setMiddleName(user.getMiddleName());
			users.setEmail(user.getEmail());
			users.setPhoneNumber(user.getPhoneNumber());
			users.setAddress(user.getAddress());
			users.setEnabled(false);
			users.setRoles(Arrays.asList(roleDao.findRoleByName("ROLE_FOR_APPROVAL_RESIDENT")));
			String token = UUID.randomUUID().toString();
			users.setVerificationToken(token);
			users.setTokenExpiry(LocalDateTime.now().plusHours(1));
			//users.setTokenExpiry(LocalDateTime.now().plusMinutes(15)); // Expires in 15 minutes
			userDao.save(users);

			emailService.sendEmailVerification(users);
		}
	}

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		User user = userDao.findByUserName(userName);

		if (user == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}

		Collection<SimpleGrantedAuthority> authorities = mapRolesToAuthorities(user.getRoles());

		return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),
				authorities);
	}

	private Collection<SimpleGrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

		for (Role tempRole : roles) {
			SimpleGrantedAuthority tempAuthority = new SimpleGrantedAuthority(tempRole.getName());
			authorities.add(tempAuthority);
		}

		return authorities;
	}

	@Override
	public User findByEmail(String email) {
		try {
			return entityManager.createQuery("FROM User WHERE email = :email", User.class)
					.setParameter("email", email)
					.getSingleResult();
		} catch (NoResultException e) {
			return null; // Return null instead of throwing an exception
		}
	}


	@Override
	@Transactional
	public User findByResetToken(String token) {
		String hql = "FROM User WHERE resetToken = :token";
		TypedQuery<User> query = entityManager.createQuery(hql, User.class);
		query.setParameter("token", token);

		List<User> users = query.getResultList();
		return users.isEmpty() ? null : users.get(0);  // Return the first user or null if none found
	}

	@Override
	public Page<User> findAll(Pageable pageable) {
		// Define the JPQL query to fetch all users
		String hql = "FROM User";

		// Create a TypedQuery with pagination
		TypedQuery<User> query = entityManager.createQuery(hql, User.class);
		query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize()); // Set the starting index
		query.setMaxResults(pageable.getPageSize()); // Set the number of results per page

		// Get the results
		List<User> users = query.getResultList();

		// Get the total count for pagination
		String countHql = "SELECT COUNT(u) FROM User u";
		TypedQuery<Long> countQuery = entityManager.createQuery(countHql, Long.class);
		Long totalCount = countQuery.getSingleResult();

		// Convert roles to a string list for each user
		for (User user : users) {
			user.setRoles(new ArrayList<>(user.getRoles())); // Convert PersistentBag to ArrayList
		}

		// Return the paginated result as a Page
		return new PageImpl<>(users, pageable, totalCount);
	}

	@Override
	public void updateUserRoles(String userUUID, List<Role> newRoles) {
		// Fetch the user by UUID
		User user = userDao.findByUserUuid(userUUID);
		if (user != null) {
			// Update the roles
			user.setRoles(newRoles);

			// Save the updated user
			userDao.save(user);
		}
	}

	@Override
	public User findByUserUuid(String userUUID) {
		// Call the UserDao to find the user by UUID
		return userDao.findByUserUuid(userUUID);
	}

	@Override
	public User findByVerificationToken(String verificationToken) {
		String hql = "FROM User WHERE verificationToken = :verificationToken";
		TypedQuery<User> query = entityManager.createQuery(hql, User.class);
		query.setParameter("verificationToken", verificationToken);

		List<User> users = query.getResultList();
		return users.isEmpty() ? null : users.get(0);
	}

}

	/*	UUID uuid = UUID.randomUUID();
		ByteBuffer byteBuffer = ByteBuffer.wrap(new byte[20]);
		byteBuffer.putLong(uuid.getMostSignificantBits());
		byteBuffer.putLong(uuid.getLeastSignificantBits());
		users.setUserUUID(Base64.getEncoder().encodeToString(byteBuffer.array()));*/