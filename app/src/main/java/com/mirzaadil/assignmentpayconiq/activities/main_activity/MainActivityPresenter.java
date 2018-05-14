package com.mirzaadil.assignmentpayconiq.activities.main_activity;

import android.util.Log;
import android.widget.Toast;

import com.mirzaadil.assignmentpayconiq.models.HomeAPIResponseModel;
import com.mirzaadil.assignmentpayconiq.networks.RestClient;
import com.mirzaadil.assignmentpayconiq.networks.ServicesInterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Mirza Adil on 5/10/2018.
 * <p>
 * This is a presenter class for MainActivity.
 */

class MainActivityPresenter {

    private List<HomeAPIResponseModel> listAddAll = new ArrayList<HomeAPIResponseModel>();
    private MainActivityView mainActivityViewListener;

    MainActivityPresenter(MainActivityView mainActivityViewListener) {
        this.mainActivityViewListener = mainActivityViewListener;
    }

    /**
     * The following method is responsible of fetching flash and home products from the respective APIs.
     * RxJava with Retrofit Client is being used in this method to call the APIs.
     */
    void fetchProducts() {

        // Defining the observable for flash products API Call.

        Observable<Response<List<HomeAPIResponseModel>>> homeAPIResponseObservable = RestClient.getRetrofitClient()
                .create(ServicesInterface.class)
                .getHomeProducts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());


        homeAPIResponseObservable.subscribe(new Observer<Response<List<HomeAPIResponseModel>>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Response<List<HomeAPIResponseModel>> flashHomeCombined) {

                List<HomeAPIResponseModel> homeAPIResponseModels = flashHomeCombined.body();

                // On successful response from home and flash api and combining the responses of both
                // Call the respective interface method which is being implemented by HomeActivity.

                if (flashHomeCombined.code() == 200) {

                    if (flashHomeCombined.body().size() <= 0) {

                    } else {
                        mainActivityViewListener.onCombinedProductsFetched(homeAPIResponseModels);
                        listAddAll.addAll(homeAPIResponseModels);
                    }
                } else {
                    mainActivityViewListener.onProductsFetchFailed();
                }

                //
                //mainActivityViewListener.onHomeProductsFetched(homeAPIResponseModels);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();

                // On error check the type of error.
                if (e instanceof IOException)
                    // If IOException it means that the network is not available.
                    // Call the respective interface method which is being implemented by HomeActivity.
                    mainActivityViewListener.onNetworkFailure();
                else
                    // If some other Exception.
                    // Call the respective interface method which is being implemented by HomeActivity.
                    mainActivityViewListener.onProductsFetchFailed();

            }

            @Override
            public void onComplete() {

            }
        });
    }

    /**
     * The following method is responsible fro fetching only the home products on pagination.
     *
     * @param page
     */
    void fetchHomeProducts(int page) {
        // Defining the observable for flash products API Call.

        Observable<Response<List<HomeAPIResponseModel>>> homeAPIResponseObservable = RestClient.getRetrofitClient()
                .create(ServicesInterface.class)
                .getHomeProductsPaged(page, 15)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        homeAPIResponseObservable.subscribe(new Observer<Response<List<HomeAPIResponseModel>>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Response<List<HomeAPIResponseModel>> flashHomeCombined) {

                if (flashHomeCombined.code() == 200) {

                    if (flashHomeCombined.body().size() <= 0) {

                        mainActivityViewListener.onProductsFetchFailed();

                    } else {

                        List<HomeAPIResponseModel> homeAPIResponseModels = flashHomeCombined.body();

                        listAddAll.addAll(homeAPIResponseModels);

                        // On successful response from home and flash api and combining the responses of both
                        // Call the respective interface method which is being implemented by HomeActivity.

                        mainActivityViewListener.onCombinedProductsFetched(listAddAll);
                    }
                } else {
                    mainActivityViewListener.onProductsFetchFailed();
                }


            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();

                // On error check the type of error.
                if (e instanceof IOException)
                    // If IOException it means that the network is not available.
                    // Call the respective interface method which is being implemented by HomeActivity.
                    mainActivityViewListener.onNetworkFailure();
                else
                    // If some other Exception.
                    // Call the respective interface method which is being implemented by HomeActivity.
                    mainActivityViewListener.onProductsFetchFailed();

            }

            @Override
            public void onComplete() {

            }
        });

    }

}
