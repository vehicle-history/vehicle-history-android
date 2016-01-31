package io.vehiclehistory.data.api.caller;

import io.vehiclehistory.api.consts.Settings;
import io.vehiclehistory.api.model.ApiError;
import io.vehiclehistory.api.model.Auth;
import io.vehiclehistory.data.DataManager;
import io.vehiclehistory.data.api.ErrorUtils;
import io.vehiclehistory.data.api.auth.OnAuthCallback;
import io.vehiclehistory.data.api.view.MvpView;
import retrofit2.HttpException;
import retrofit2.Retrofit;
import rx.Subscription;
import timber.log.Timber;

/**
 * Base class that implements the Caller interface and provides a base implementation for
 * attachView() and detachView(). It also handles keeping a reference to the mvpView that
 * can be accessed from the children classes by calling getMvpView().
 */
public abstract class BaseCaller<T extends MvpView> implements Caller<T> {

    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;

    private final DataManager dataManager;

    private final Retrofit retrofit;

    private int retry = 0;

    private Subscription subscription;

    public BaseCaller(
            DataManager dataManager,
            Retrofit retrofit
    ) {
        this.dataManager = dataManager;
        this.retrofit = retrofit;
    }

    private T mMvpView;


    protected void resetRetry() {
        retry = 0;
    }

    @Override
    public void attachView(T mvpView) {
        mMvpView = mvpView;
    }

    @Override
    public void detachView() {
        mMvpView = null;

        if (subscription != null) {
            subscription.unsubscribe();
        }
    }

    public boolean isViewAttached() {
        return mMvpView != null;
    }

    public T getMvpView() {
        return mMvpView;
    }

    public DataManager getDataManager() {
        return dataManager;
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    public void checkViewAttached() {
        if (!isViewAttached()) throw new MvpViewNotAttachedException();
    }

    public static class MvpViewNotAttachedException extends RuntimeException {
        public MvpViewNotAttachedException() {
            super("Please preCall Caller.attachView(MvpView) before" +
                    " requesting data to the Caller");
        }
    }

    protected void preCall() {
        ++retry;

        if (retry > Settings.RETRY_COUNT) {
            Timber.e("Reached max retries: %d", retry);
            mMvpView.onRetryError();
            return;
        }

        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }

        subscription = call();
    }

    protected void handleApiError(Throwable e) {
        checkViewAttached();
        getMvpView().finishedLoadingData();

        Timber.e(e, "There was an api error");
        ApiError exception = ApiError.internalApiError();

        if (e instanceof HttpException) {
            HttpException ex = (HttpException) e;
            int code = ex.code();

            if (ex.response() != null) {
                exception = ErrorUtils.parseError(ex.response(), retrofit);
            }

            exception.setStatusCode(code);

            switch (exception.getStatusCode()) {
                case UNAUTHORIZED:
                    authorizeAndRetry();
                    break;
                case FORBIDDEN:
                    handleForbiddenError(exception);
                    break;
                default:
                    getMvpView().onErrorResponse(exception.getUserMessage());
            }
        } else {
            getMvpView().onErrorResponse(exception.getUserMessage());
        }
    }

    protected void handleForbiddenError(ApiError exception) {
        //nop
    }

    private void authorizeAndRetry() {
        dataManager.getNewSession(new OnAuthCallback() {

            @Override
            public void onSuccess(Auth response) {
                Timber.e("authorizeAndRetry.onSuccess");
                resetRetry();
                preCall();
            }

            @Override
            public void onError() {
                Timber.e("authorizeAndRetry.onError");
                mMvpView.onNoConnectionError();
            }
        });
    }
}

