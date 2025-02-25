package com.bayaniact.common.constant;

/**
 * This class contains constant message strings used throughout the application.
 * These messages are intended to provide feedback to users regarding various actions,
 * such as authentication, password resets, and deletion of resident records.
 */
public class ApplicationMessageConst {

    /*
     * RESIDENT ADMIN MESSAGES
     *
     * These constants are used for messages related to resident administration actions.
     */

    // Message when no resident is selected for deletion
    public static final String NO_RESIDENT_SELECTED_FOR_DELETION = "No residents selected for deletion.";

    // Message when selected residents are successfully deleted
    public static final String SELECTED_RESIDENT_DELETED_SUCCESSFULLY = "Selected residents deleted successfully.";

    /*
     * AUTHENTICATION MESSAGES
     *
     * These constants are used for messages related to user authentication and password management.
     */

    // Message when the email address already exists in the system
    public static final String EMAIL_ADDRESS_ALREADY_EXIST = "This email address is already in use. Please provide a different one.";

    // Message when the username already exists in the system
    public static final String USERNAME_ALREADY_EXIST = "User name already exists.";

    // Subject line for the email sent to the user requesting a password reset
    public static final String PASSWORD_RESET_REQUEST = "Password reset request";

    // Message confirming that a password reset link has been sent to the user's email
    public static final String PASSWORD_RESET_LINK_SENT = "Password reset link has been sent to your email.";

    // Message when the provided email address is not found in the system
    public static final String EMAIL_ADDRESS_NOT_FOUND = "Email address not found";

    // Message when the token provided for password reset is invalid or expired
    public static final String INVALID_TOKEN = "Invalid or expired token.";

    // Message confirming that the password was successfully updated
    public static final String PASSWORD_SUCCESSFULLY_UPDATED = "Password successfully updated.";

    // Message displayed when a new user account is successfully created
    public static final String USER_SUCCESSFULLY_CREATED = "Account has been successfully created. Please verify your email to continue.";


}

