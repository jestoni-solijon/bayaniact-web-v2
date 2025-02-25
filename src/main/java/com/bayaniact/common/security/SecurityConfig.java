package com.bayaniact.common.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * Security configuration class to configure HTTP security for the application.
 * This class defines the security measures such as authentication, authorization,
 * password encoding, and handling login/logout mechanisms.
 */
@Configuration
public class SecurityConfig {

	/**
	 * Bean for password encoder using BCrypt.
	 * BCrypt is a strong hashing function that adds a salt automatically to the password.
	 * This is used to securely store passwords in the database.
	 *
	 * @return an instance of BCryptPasswordEncoder
	 */
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * Bean for DaoAuthenticationProvider, which uses a custom UserService to load user details
	 * and validate passwords. The DaoAuthenticationProvider is part of Spring Security's
	 * authentication mechanism.
	 *
	 * @param userService the custom service used to load user details
	 * @return an instance of DaoAuthenticationProvider
	 */
	@Bean
	public DaoAuthenticationProvider authenticationProvider(UserService userService) {
		DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
		auth.setUserDetailsService(userService);  // Set the service to load user details
		auth.setPasswordEncoder(passwordEncoder());  // Set the password encoder to bcrypt
		return auth;
	}

	/**
	 * Configures the security filter chain for HTTP requests.
	 * This method defines which URLs are permitted, which roles are required,
	 * and how login and logout should behave.
	 *
	 * @param http the HttpSecurity object to configure security for HTTP requests
	 * @param customAuthenticationSuccessHandler the custom handler for successful authentication
	 * @return the configured SecurityFilterChain
	 * @throws Exception if there are any errors during the configuration
	 */
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http,
										   AuthenticationSuccessHandler customAuthenticationSuccessHandler) throws Exception {
		http.csrf().disable() // Disabling CSRF protection for REST APIs
			.authorizeHttpRequests(configure -> configure

						// Allow unrestricted access to certain paths
						.requestMatchers(
								"/",
								"/api/**",
								"/admin/**",
								"/request/**",
								"/forgot-password",
								"/reset-password",
								"/access-denied",
								"/account-update",
								"/verify",
								"/register",
								"/brgy-official/**",
								"/event/**",
								"/privacy",
								"/about",
								"/incident/**",
								"/account/**",
								"/contact",
								"/css/**",
								"/js/**",
								"/images/**")
						.permitAll()  // Allow all users to access the above resources
						// Restrict access to resident-related pages based on roles
						.requestMatchers("/resident/**").hasAnyRole("FOR_APPROVAL_RESIDENT", "ADMIN")
						.requestMatchers("/resident/**", "/form/**").hasAnyRole("RESIDENT", "ADMIN")
						// Only allow ADMIN role to access certain dashboard and user update paths
						.requestMatchers("/**",
								"/dashboard/user/update-status",
								"/dashboard/incident/**").hasRole("ADMIN")
						.anyRequest().authenticated())  // Require authentication for all other requests
				// Configure the login settings
				.formLogin(form -> form
						.loginPage("/login")  // Custom login page URL
						.loginProcessingUrl("/authenticate")  // URL for processing login form
						.successHandler(customAuthenticationSuccessHandler)  // Custom success handler on login
						.defaultSuccessUrl("/", true)  // Redirect to the homepage on successful login
						.permitAll())  // Allow everyone to access the login page
				// Configure logout settings
				.logout(LogoutConfigurer::permitAll)
				// Configure access denied page for unauthorized access
				.exceptionHandling(configure -> configure.accessDeniedPage("/access-denied"));

		return http.build();  // Build and return the configured HTTP security filter chain
	}
}
