package io.vehiclehistory.data.api.caller;

import javax.inject.Inject;

import io.vehiclehistory.api.model.VehicleInput;
import io.vehiclehistory.api.model.VehicleResponse;
import io.vehiclehistory.data.DataManager;
import io.vehiclehistory.data.api.view.VehicleHistoryMvpView;
import retrofit2.Retrofit;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

public class GetVehicleHistoryCaller extends BaseCaller<VehicleHistoryMvpView> {

    private VehicleInput request;

    @Inject
    public GetVehicleHistoryCaller(
            DataManager dataManager,
            Retrofit retrofit
    ) {
        super(dataManager, retrofit);
    }

    public void getVehicleHistory(VehicleInput request) {
        this.request = request;
        resetRetry();
        checkViewAttached();
        preCall();
    }

    @Override
    public Subscription call() {
        return getDataManager().getVehicleHistory(request)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        //nop
                    }
                })
                .subscribe(new Subscriber<VehicleResponse>() {
                    @Override
                    public void onCompleted() {
                        //nop
                    }

                    @Override
                    public void onError(Throwable e) {
                        handleApiError(e);
                    }

                    @Override
                    public void onNext(VehicleResponse response) {
                        getMvpView().onGetVehicleHistoryFinished(request, response);
                    }
                });
    }

}
