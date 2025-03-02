package com.bayaniact.common.controller;

import com.bayaniact.common.constant.ApplicationMessageConst;
import com.bayaniact.common.constant.RequestMappingConst;
import com.bayaniact.common.email.EmailService;
import com.bayaniact.common.entity.Role;
import com.bayaniact.common.entity.User;
import com.bayaniact.common.security.RoleDao;
import com.bayaniact.common.security.RoleDaoImpl;
import com.bayaniact.common.security.UserService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.logging.Logger;

@Controller
public class AuthenticationController {

    private Logger logger = Logger.getLogger(getClass().getName());

    @Autowired private UserService userService; // Service for user-related operations
    @Autowired private BCryptPasswordEncoder passwordEncoder; // Encoder for securing passwords
    @Autowired private EmailService emailService; // Service for sending emails
    @Autowired private RoleDao roleDao; // DAO for managing roles

    /**
     * Displays the registration page.
     * Redirects to the home page if the user is already logged in.
     *
     * @param model      Model object to pass attributes to the view.
     * @param principal  The currently authenticated user (null if not authenticated).
     * @return The registration page view name.
     */
    @GetMapping(value = RequestMappingConst.RESIDENT_REGISTER_GET_PATH)
    public String getRegisterPage(Model model, Principal principal) {
        if (principal != null) {
            return "redirect:/"; // Redirect logged-in users to the home page
        }
        model.addAttribute("user", new User()); // Add a new user object for form binding
        return "resident/register"; // Return the registration view
    }

    /**
     * Processes the user registration form.
     * Validates input, checks for existing users, and saves the new user.
     *
     * @param user             The user object populated from the form.
     * @param theBindingResult Binding result for validation errors.
     * @param model            Model object to pass attributes to the view.
     * @param principal        The currently authenticated user (null if not authenticated).
     * @return Redirect to the login page or registration page based on the result.
     */
    @PostMapping(value = RequestMappingConst.RESIDENT_REGISTER_POST_PATH)
    public String processRegistrationForm(@Valid @ModelAttribute("user") User user, BindingResult theBindingResult,
                                          Model model, Principal principal) throws MessagingException {
        String userName = user.getUserName();
        logger.info("Processing registration form for: " + userName);
        logger.info(getClass().getName());

        if (theBindingResult.hasErrors()) {
            return "resident/register"; // Return to registration page if validation fails
        }

        // Check if email already exists
        if (userService.findByEmail(user.getEmail()) != null) {
            model.addAttribute("message", ApplicationMessageConst.EMAIL_ADDRESS_ALREADY_EXIST);
            return "redirect:/register";
        }

        // Check if username already exists
        if (userService.findByUserName(userName) != null) {
            model.addAttribute("user", new User());
            model.addAttribute("registrationError", ApplicationMessageConst.USERNAME_ALREADY_EXIST);
            logger.warning("User name already exists.");
            return "redirect:/register";
        }

        // Save the new user
        userService.save(user);
        logger.info("Successfully created user: " + userName);
        model.addAttribute("message", ApplicationMessageConst.USER_SUCCESSFULLY_CREATED);
        return "resident/login"; // Redirect to login page after successful registration
    }

    /**
     * Displays the login page.
     * Redirects logged-in users to the home page.
     *
     * @param model      Model object to pass attributes to the view.
     * @param request    The current HTTP request.
     * @param principal  The currently authenticated user (null if not authenticated).
     * @return The login page view name or home page if already logged in.
     */
    @GetMapping(value = RequestMappingConst.RESIDENT_LOGIN_GET_PATH)
    public String getLoginPage(Model model, HttpServletRequest request, Principal principal) {
        if (principal != null) {
            return "redirect:/";
        }
        return "resident/login";
    }

    /**
     * Displays the access denied page.
     *
     * @return The access denied page view name.
     */
    @GetMapping(value = RequestMappingConst.RESIDENT_ACCESS_DENIED_PATH)
    public String getAccessDeniedPage() {
        return "access-denied";
    }

    /**
     * Displays the forgot password page.
     *
     * @return The forgot password page view name.
     */
    @GetMapping(value = RequestMappingConst.RESIDENT_FORGOT_PASSWORD_GET_PATH)
    public String getForgotPasswordPage() {
        return "resident/forgot-password";
    }

    /**
     * Processes the forgot password request.
     * Generates a reset token and sends a password reset link to the user's email if the email exists.
     *
     * @param email The email address entered by the user for password recovery.
     * @param model Model object to pass attributes to the view.
     * @return The forgot password view with a success or error message.
     * @throws MessagingException If sending the reset email fails.
     */
    @PostMapping(value = RequestMappingConst.RESIDENT_FORGOT_PASSWORD_POST_PATH)
    public String processForgotPassword(@RequestParam("email") String email,
                                        Model model) throws MessagingException {
        Optional<User> userOptional = Optional.ofNullable(userService.findByEmail(email));

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            // Generate a reset token
            String token = UUID.randomUUID().toString();
            user.setResetToken(token);
            user.setTokenExpiry(LocalDateTime.now().plusHours(1)); // Token expires in 1 hour
            userService.save(user);

            // Build the reset password link dynamically
            String resetLink = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/reset-password")
                    .queryParam("token", token)
                    .toUriString();

            // Send the reset password email
            emailService.sendMail(user.getEmail(), user.getFirstName(),
                    ApplicationMessageConst.PASSWORD_RESET_REQUEST, resetLink);

            model.addAttribute("message", ApplicationMessageConst.PASSWORD_RESET_LINK_SENT);
        } else {
            // Email not found in the system
            model.addAttribute("message", ApplicationMessageConst.EMAIL_ADDRESS_NOT_FOUND);
        }

        return "resident/forgot-password"; // Return to the forgot password view
    }

    /**
     * Displays the reset password form.
     * Validates the reset token and allows the user to set a new password if the token is valid.
     *
     * @param token The reset token provided in the password reset link.
     * @param model Model object to pass attributes to the view.
     * @return The reset password view if the token is valid, otherwise redirects to the forgot password view.
     */
    @GetMapping(value = RequestMappingConst.RESIDENT_RESET_PASSWORD_GET_PATH)
    public String resetPasswordForm(@RequestParam("token") String token, Model model) {
        Optional<User> userOptional = Optional.ofNullable(userService.findByResetToken(token));

        if (userOptional.isPresent() && userOptional.get().getTokenExpiry().isAfter(LocalDateTime.now())) {
            // Token is valid and has not expired
            model.addAttribute("token", token); // Pass the token to the view
            return "resident/reset-password"; // Return the reset password form
        } else {
            // Token is invalid or expired
            model.addAttribute("message", ApplicationMessageConst.INVALID_TOKEN);
            return "resident/forgot-password"; // Redirect to the forgot password page
        }
    }

    /**
     * Processes the reset password form submission.
     * Updates the user's password if the reset token is valid and not expired.
     *
     * @param token    The reset token provided in the password reset link.
     * @param password The new password entered by the user.
     * @param model    Model object to pass attributes to the view.
     * @return Redirects to the login page if successful, or the reset password view if the token is invalid.
     */
    @PostMapping(value = RequestMappingConst.RESIDENT_RESET_PASSWORD_POST_PATH)
    public String processResetPassword(@RequestParam("token") String token,
                                       @RequestParam("password") String password, Model model) throws MessagingException {
        Optional<User> userOptional = Optional.ofNullable(userService.findByResetToken(token));

        if (userOptional.isPresent() && userOptional.get().getTokenExpiry().isAfter(LocalDateTime.now())) {
            User user = userOptional.get();

            // Update the password with the encoded version
            user.setPassword(passwordEncoder.encode(password));
            // Clear the reset token and expiry
            user.setResetToken(null);
            user.setTokenExpiry(null);
            userService.save(user); // Save the updated user object

            model.addAttribute("message", ApplicationMessageConst.PASSWORD_SUCCESSFULLY_UPDATED);
            return "resident/login"; // Redirect to the login page
        } else {
            // Token is invalid or expired
            model.addAttribute("message", ApplicationMessageConst.INVALID_TOKEN);
            return "resident/reset-password"; // Stay on the reset password page
        }
    }

    /**
     * Displays the paginated list of user accounts in the dashboard.
     *
     * @param page  The page number for pagination (default is 0, the first page).
     * @param size  The number of records per page (default is 10).
     * @param model Model object to pass attributes to the view.
     * @return The view name for the user list page.
     */
    @GetMapping(value = RequestMappingConst.DASHBOARD_USER_LIST_GET_PATH)
    public String getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(name = "filter", required = false) String filter,
            @RequestParam(name = "query", required = false) String query,
            Model model) {

        Pageable pageable = PageRequest.of(page, size);
        Page<User> userPage;

        if (query != null && !query.isEmpty()) {
            if ("email".equals(filter)) {
                userPage = userService.findByEmail(query, pageable);
            } else if ("userName".equals(filter)) {
                userPage = userService.findByUsername(query, pageable);
            } else {
                userPage = userService.findAll(pageable);
            }
        } else {
            userPage = userService.findAll(pageable);
        }
        model.addAttribute("userObj", new User());
        model.addAttribute("userPage", userPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", userPage.getTotalPages());
        model.addAttribute("pageSize", size);

        return "admin/user-list";
    }

    // ✅ METHOD 2: AJAX request (returns JSON)
    @GetMapping("/dashboard/user/list/data")
    public ResponseEntity<Map<String, Object>> getUserData(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(name = "filter", required = false) String filter,
            @RequestParam(name = "query", required = false) String query) {

        Pageable pageable = PageRequest.of(page, size);
        Page<User> userPage;

        if (query != null && !query.isEmpty()) {
            if ("email".equals(filter)) {
                userPage = userService.findByEmail(query, pageable);
            } else if ("userName".equals(filter)) {
                userPage = userService.findByUsername(query, pageable);
            } else {
                userPage = userService.findAll(pageable);
            }
        } else {
            userPage = userService.findAll(pageable);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("users", userPage.getContent());
        response.put("currentPage", userPage.getNumber());
        response.put("totalPages", userPage.getTotalPages());
        response.put("pageSize", userPage.getSize());
        response.put("totalItems", userPage.getTotalElements());

        return ResponseEntity.ok(response); // Return JSON response for AJAX
    }

    /**
     * Updates the roles of a user based on their UUID.
     * Removes roles that are no longer assigned and adds new roles to the user.
     *
     * @param userUUID The UUID of the user whose roles are being updated.
     * @param roles    A list of role names to be assigned to the user.
     * @return A redirection to the user list page.
     */
    @PostMapping(value = RequestMappingConst.DASHBOARD_USER_UPDATE_STATUS_POST_PATH)
    public String updateUserRoles(@RequestParam("userUuid") String userUUID,
                                  @RequestParam(value = "roles", required = false) List<String> roles) {
        // Initialize roles as an empty list if no roles are provided
        if (roles == null) { roles = new ArrayList<>(); }

        // Fetch the existing user by UUID
        User user = userService.findByUserUuid(userUUID);

        if (user != null) {
            // Convert role names to Role objects
            List<Role> newRoles = new ArrayList<>();
            for (String roleName : roles) {
                Role role = roleDao.findRoleByName(roleName);
                if (role != null) { newRoles.add(role); } // Add the corresponding Role to the list
            }

            // Get the user's current roles
            Collection<Role> currentRoles = user.getRoles();

            // Identify roles to remove (present in current roles but not in new roles)
            List<Role> rolesToRemove = new ArrayList<>();
            for (Role currentRole : currentRoles) {
                if (!newRoles.contains(currentRole)) { rolesToRemove.add(currentRole); }
            }

            // Identify roles to add (present in new roles but not in current roles)
            List<Role> rolesToAdd = new ArrayList<>();
            for (Role newRole : newRoles) {
                if (!currentRoles.contains(newRole)) { rolesToAdd.add(newRole); }
            }

            // Update the user's roles
            currentRoles.removeAll(rolesToRemove); // Remove roles no longer assigned
            currentRoles.addAll(rolesToAdd); // Add new roles
            userService.updateUserRoles(userUUID, newRoles); // Save the updated user roles

            // Redirect to the user list page
            return "redirect:/dashboard/user/list";
        }

        // If the user is not found, redirect to the user list page
        return "redirect:/dashboard/user/list";
    }

    /**
     * Processes the user profile update form without requiring a password.
     * Validates input and updates the user's information (name, email, etc.).
     *
     * @param user             The user object populated from the form.
     * @param theBindingResult Binding result for validation errors.
     * @param model            Model object to pass attributes to the view.
     * @param principal        The currently authenticated user (null if not authenticated).
     * @return Redirect to the profile page or the same page based on the result.
     */
    @PostMapping(value = RequestMappingConst.RESIDENT_PROFILE_UPDATE_POST_PATH)
    public String processProfileUpdateForm(@Valid @ModelAttribute("user") User user, BindingResult theBindingResult,
                                           Model model, Principal principal) throws MessagingException {
        // Get the currently authenticated user
        String username = principal.getName();
        logger.info("Processing profile update for: " + username);

        // If validation fails, return the form with error messages
        /*if (theBindingResult.hasErrors()) {
            return "resident/account"; // Return to profile page if validation fails
        }*/

        System.out.println(username);
        // Retrieve the existing user based on the username
        User existingUser = userService.findByUserUuid(user.getUserUUID());
        if (existingUser == null) {
            model.addAttribute("message", "username not found");
            return "resident/account"; // Return to profile page if the user was not found
        }

        // Check if email has changed and if the new email already exists (if applicable)
        if (!existingUser.getEmail().equals(user.getEmail()) && userService.findByEmail(user.getEmail()) != null) {
            model.addAttribute("message", ApplicationMessageConst.EMAIL_ADDRESS_ALREADY_EXIST);
            return "resident/account"; // Return to profile page if email already exists
        }

        // Check if the username is the same as the current one, or allow username change
        if (!existingUser.getUserName().equals(user.getUserName()) && userService.findByUserName(user.getUserName()) != null) {
            model.addAttribute("message", ApplicationMessageConst.USERNAME_ALREADY_EXIST);
            return "resident/account"; // Return to profile page if username already exists
        }

        existingUser.setUserName(user.getUserName());
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setMiddleName(user.getMiddleName());
        existingUser.setEmail(user.getEmail());
        existingUser.setPhoneNumber(user.getPhoneNumber());
        existingUser.setAddress(user.getAddress());
        // Save the updated user
        userService.save(existingUser);
        logger.info("Successfully updated user: " + username);

        // Add success message and redirect to the profile page
        //model.addAttribute("message", ApplicationMessageConst.PROFILE_UPDATED_SUCCESSFULLY);
        return "resident/account"; // Redirect to the profile page after successful update
    }

    @GetMapping("/verify")
    public String verifyUserEmail(@RequestParam("token") String token, Model model) throws MessagingException {

        Optional<User> userOptional = Optional.ofNullable(userService.findByVerificationToken(token));
        if (userOptional.isEmpty()) {
            model.addAttribute("message", "Invalid verification link.");
            return "resident/login";
        }

        User user = userOptional.get();

        if (isTokenExpired(user)) {
            model.addAttribute("message", "Invalid verification link.");
            return "resident/login";
        }
        user.setEnabled(true); // Activate user
        user.setVerificationToken(null); // Remove token after successful verification
        userService.save(user);

        model.addAttribute("message", "Your email has been verified! You can now log in.");
        return "resident/login";
    }

    public boolean isTokenExpired(User user) {
        return user.getTokenExpiry().isBefore(LocalDateTime.now());
    }

    @PostMapping("/dashboard/user/delete")
    public String deleteUser(@RequestParam("userUUID") String userUUID, RedirectAttributes redirectAttributes) {
        try {
            userService.deleteUserByUuid(userUUID);
            redirectAttributes.addFlashAttribute("successMessage", "User deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting user.");
        }
        return "redirect:/dashboard/user/list"; // Redirect back to user list
    }


}
