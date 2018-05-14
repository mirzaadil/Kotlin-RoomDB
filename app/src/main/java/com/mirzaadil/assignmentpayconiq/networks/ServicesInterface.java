package com.mirzaadil.assignmentpayconiq.networks;


import com.mirzaadil.assignmentpayconiq.models.HomeAPIResponseModel;
import com.mirzaadil.assignmentpayconiq.networks.api.Endpoints;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Mirza Adil on 5/10/2018.
 * <p>
 * This is the ServicesInterface for our application.
 */

public interface ServicesInterface {


    @GET(Endpoints.HOME)
    Observable<Response<List<HomeAPIResponseModel>>> getHomeProducts();


    @GET(Endpoints.HOMEE)
    Observable<Response<List<HomeAPIResponseModel>>> getHomeProductsPaged(@Query("page") int page, @Query("per_page") int perPage);


}
