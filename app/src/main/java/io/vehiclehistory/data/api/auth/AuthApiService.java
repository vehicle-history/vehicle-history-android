package io.vehiclehistory.data.api.auth;

import io.vehiclehistory.api.model.Auth;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;

public interface AuthApiService {
    public static final String OAUTH_TOKEN = "/oauth/token";

    @Headers({
            "Accept: application/json"
    })
    @FormUrlEncoded
    @POST(OAUTH_TOKEN)
    Observable<Auth> getSessionFromPassword(
            @Field("username") String username,
            @Field("password") String password,
            @Field("client_id") String clientId,
            @Field("grant_type") String grantType
    );


    @Headers({
            "Accept: application/json"
    })
    @FormUrlEncoded
    @POST(OAUTH_TOKEN)
    Observable<Auth> getSessionFromRefreshToken(
            @Field("refresh_token") String refreshToken,
            @Field("client_id") String clientId,
            @Field("grant_type") String grantType
    );
}
