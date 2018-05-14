package com.mirzaadil.assignmentpayconiq.activities.main_activity;


import com.mirzaadil.assignmentpayconiq.models.HomeAPIResponseModel;
import com.mirzaadil.assignmentpayconiq.networks.GeneralNetworkInterface;

import java.util.List;

/**
 * Created by Mirza Adil on 5/10/2018.
 * <p>
 * This Interface is being implemented by MainActivity.
 * MainActivity Implements this interface to receive CallBacks on different events completed by
 * MainActivityPresenter.
 */

public interface MainActivityView extends GeneralNetworkInterface {




    void onCombinedProductsFetched(List<HomeAPIResponseModel> homeAPIResponse);

    void onProductsFetchFailed();
}
