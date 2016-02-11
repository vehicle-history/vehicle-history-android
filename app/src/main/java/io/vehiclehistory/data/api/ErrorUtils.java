package io.vehiclehistory.data.api;

import java.io.IOException;
import java.lang.annotation.Annotation;

import io.vehiclehistory.api.model.ApiError;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import timber.log.Timber;

public class ErrorUtils {

    public static ApiError parseError(Response<?> response, Retrofit retrofit) {
        Converter<ResponseBody, ApiError> converter =
                retrofit.responseBodyConverter(ApiError.class, new Annotation[0]);

        try {
            return converter.convert(response.errorBody());
        } catch (IOException e) {
            Timber.e(e, "Unable to get ApiError from error response");
            return ApiError.internalApiError();
        }
    }
}
