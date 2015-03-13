package pl.vehicle_history.api.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dawid.mackowiak on 2015-03-13.
 */
public class Auth {
    @SerializedName("access_token")
    private String accessToken;

    @SerializedName("token_type")
    private String tokenType;

    @SerializedName("refresh_token")
    private String refreshToken;

    @SerializedName("expires_in")
    private long expiresIn;

    public String getAccessToken() {
        return accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public String getScope() {
        return scope;
    }

    public String getJti() {
        return jti;
    }

    @SerializedName("scope")
    private String scope;

    @SerializedName("jti")
    private String jti;
}
