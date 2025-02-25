package com.bayaniact.common.constant;

/**
 * This class contains constant strings used for defining request mapping paths.
 * These constants provide a centralized place for all controller URLs and help prevent
 * hardcoding of paths throughout the application. It ensures consistency and ease of maintenance.
 */
public class RequestMappingConst {

    /*
     * RESIDENT USER CONTROLLER
     *
     * These constants are used for the paths related to the resident user controller.
     * They handle resident-related functionalities like form submission and listing.
     */

    // Path for displaying the form to create or edit a resident
    public static final String RESIDENT_FORM_PATH = "/form";

    // Path for displaying the list of residents
    public static final String RESIDENT_LIST_PATH = "/list";

    // Path for saving resident data (both creation and updates)
    public static final String RESIDENT_SAVE_PATH = "/save";

    /*
     * RESIDENT ADMIN CONTROLLER
     *
     * These constants are used for the paths related to the resident admin controller.
     * They handle administrative tasks such as managing resident records, searching, and exporting data.
     */

    // Path for displaying the form to add or edit a resident (admin version)
    public static final String RESIDENT_ADMIN_FORM_PATH = "/form";

    // Path for saving changes to a resident (admin version)
    public static final String RESIDENT_ADMIN_SAVE_PATH = "/save";

    // Path for displaying the list of residents (admin version)
    public static final String RESIDENT_ADMIN_LIST_PATH = "/list";

    // Path for updating the status of a resident (admin version)
    public static final String RESIDENT_ADMIN_UPDATE_STATUS_PATH = "/update-status";

    // Path for searching residents (admin version)
    public static final String RESIDENT_ADMIN_SEARCH_PATH = "/search";

    // Path for deleting resident records (admin version)
    public static final String RESIDENT_ADMIN_DELETE_PATH = "/delete";

    // Path for exporting resident data (admin version)
    public static final String RESIDENT_ADMIN_EXPORT_PATH = "/export";

    /*
     * AUTHENTICATION CONTROLLER
     *
     * These constants are used for the paths related to the authentication controller.
     * They handle user registration, login, password resets, and access control.
     */

    // Path for displaying the resident registration page
    public static final String RESIDENT_REGISTER_GET_PATH = "/register";

    // Path for processing the resident registration form (POST)
    public static final String RESIDENT_REGISTER_POST_PATH = "/register";

    // Path for displaying the resident login page
    public static final String RESIDENT_LOGIN_GET_PATH = "/login";

    // Path for displaying the access denied page (when user doesn't have permission)
    public static final String RESIDENT_ACCESS_DENIED_PATH = "/access-denied";

    // Path for displaying the forgot password page
    public static final String RESIDENT_FORGOT_PASSWORD_GET_PATH = "/forgot-password";

    // Path for processing the forgot password form (POST)
    public static final String RESIDENT_FORGOT_PASSWORD_POST_PATH = "/forgot-password";

    // Path for displaying the reset password page
    public static final String RESIDENT_RESET_PASSWORD_GET_PATH = "/reset-password";

    // Path for processing the reset password form (POST)
    public static final String RESIDENT_RESET_PASSWORD_POST_PATH = "/reset-password";

    // Path for displaying the list of users on the dashboard (admin version)
    public static final String DASHBOARD_USER_LIST_GET_PATH = "/dashboard/user/list";

    // Path for updating user status on the dashboard (admin version)
    public static final String DASHBOARD_USER_UPDATE_STATUS_POST_PATH = "/dashboard/user/update-status";

    public static final String RESIDENT_PROFILE_UPDATE_POST_PATH = "/account-update";
}

