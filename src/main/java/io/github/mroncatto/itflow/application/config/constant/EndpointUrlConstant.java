package io.github.mroncatto.itflow.application.config.constant;

import static io.github.mroncatto.itflow.application.config.constant.SecurityConstant.BASE_URL;

public class EndpointUrlConstant {
    public static final String id = "/{id}";
    public static final String uuid = "/{uuid}";
    public static final String page = "/page/{page}";
    public static final String all = "/all";
    public static final String filterStaff = "/filter/staff";
    public static final String autoComplete = "/autocomplete";
    public static final String staffId = "/staff/{id}";
    public static final String computerId = "/computer/{id}";
    public static final String deviceComputerCpu = "/computer/{id}/cpu";
    public static final String filterDevice = "/filter/device";
    public static final String username = "/{username}";
    public static final String role = "/role";
    public static final String usernameRole = "/{username}/role";
    public static final String profile = "/profile";
    public static final String updatePassword = "/updatepassword";
    public static final String resetPassword = "/resetpassword";
    public static final String lockUnlockUsername = "/lockunlock/{username}";

    public static final String updateSoftwareLicense = id + "/license";
    public static final String updateLicenseKey = id + "/key";

    // Base URL
    public static final String branch = BASE_URL + "/branch";
    public static final String company = BASE_URL + "/company";
    public static final String department = BASE_URL + "/department";
    public static final String device = BASE_URL + "/device";
    public static final String staff = BASE_URL + "/staff";
    public static final String occupation = BASE_URL + "/occupation";
    public static final String user = BASE_URL + "/user";
    public static final String deviceCategory = BASE_URL + "/device/category";
    public static final String computerCategory = BASE_URL + "/computer/category";
    public static final String computerCpu = BASE_URL + "/computer/cpu";
    public static final String computerMemory = BASE_URL + "/computer/memory";
    public static final String computerStorage = BASE_URL + "/computer/storage";
    public static final String computerSoftware = BASE_URL + "/computer/software";
    public static final String computerLicense = BASE_URL + "/computer/license";

}
