package io.github.mroncatto.itflow.application.config.constant;

import static io.github.mroncatto.itflow.application.config.constant.SecurityConstant.BASE_URL;

public class EndpointUrlConstant {
    public static final String ID = "/{id}";
    public static final String UUID = "/{uuid}";
    public static final String PAGE = "/page/{page}";
    public static final String ALL = "/all";
    public static final String FILTER_STAFF = "/filter/staff";
    public static final String AUTO_COMPLETE = "/autocomplete";
    public static final String STAFF_ID = "/staff/{id}";
    public static final String COMPUTER_ID = "/computer/{id}";
    public static final String DEVICE_COMPUTER_CPU = "/computer/{id}/cpu";
    public static final String DEVICE_COMPUTER_CPU_DELETE = "/computer/{id}/cpu/{cpuId}";
    public static final String DEVICE_COMPUTER_MEMORY = "/computer/{id}/memory";
    public static final String DEVICE_COMPUTER_MEMORY_DELETE = "/computer/{id}/memory/{memoryId}";
    public static final String DEVICE_COMPUTER_STORAGE = "/computer/{id}/storage";
    public static final String DEVICE_COMPUTER_STORAGE_DELETE = "/computer/{id}/storage/{storageId}";
    public static final String FILTER_DEVICE = "/filter/device";
    public static final String USERNAME = "/{username}";
    public static final String ROLE = "/role";
    public static final String USERNAME_ROLE = "/{username}/role";
    public static final String PROFILE = "/profile";
    public static final String UPDATE_PASSWORD = "/updatepassword";
    public static final String RESET_PASSWORD = "/resetpassword";
    public static final String LOCK_UNLOCK_USERNAME = "/lockunlock/{username}";

    public static final String UPDATE_SOFTWARE_LICENSE = ID + "/license";
    public static final String UPDATE_LICENSE_KEY = ID + "/key";

    // Base URL
    public static final String BRANCH = BASE_URL + "/branch";
    public static final String COMPANY = BASE_URL + "/company";
    public static final String DEPARTMENT = BASE_URL + "/department";
    public static final String DEVICE = BASE_URL + "/device";
    public static final String STAFF = BASE_URL + "/staff";
    public static final String OCCUPATION = BASE_URL + "/occupation";
    public static final String USER = BASE_URL + "/user";
    public static final String DEVICE_CATEGORY = BASE_URL + "/device/category";
    public static final String COMPUTER_CATEGORY = BASE_URL + "/computer/category";
    public static final String COMPUTER_CPU = BASE_URL + "/computer/cpu";
    public static final String COMPUTER_MEMORY = BASE_URL + "/computer/memory";
    public static final String COMPUTER_STORAGE = BASE_URL + "/computer/storage";
    public static final String COMPUTER_SOFTWARE = BASE_URL + "/computer/software";
    public static final String COMPUTER_LICENSE = BASE_URL + "/computer/license";

}
