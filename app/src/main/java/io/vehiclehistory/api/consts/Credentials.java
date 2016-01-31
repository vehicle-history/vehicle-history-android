package io.vehiclehistory.api.consts;

import io.vehiclehistory.BuildConfig;

/**
 * Created by dudvar on 2015-03-13.
 */
public class Credentials {
    public static final String LOGIN = BuildConfig.API_LOGIN;
    public static final String PASSWORD = BuildConfig.API_PASSWORD;
    public static final String CLIENT = BuildConfig.API_CLIENT;
    public static final String CLIENT_PASSWORD = BuildConfig.API_CLIENT_PASSWORD;

    public static final String REFRESH_TOKEN_GRANT_TYPE = "refresh_token";
    public static final String PASSWORD_GRANT_TYPE = "password";

    public static final String BEARER_AUTHORIZATION = "Bearer";
    public static final String BASIC_AUTHORIZATION = "Basic";
}
