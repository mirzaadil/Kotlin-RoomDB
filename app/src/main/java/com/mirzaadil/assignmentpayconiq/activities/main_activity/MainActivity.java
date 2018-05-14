package com.mirzaadil.assignmentpayconiq.activities.main_activity;


import android.graphics.Bitmap;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.mirzaadil.assignmentpayconiq.R;
import com.mirzaadil.assignmentpayconiq.adapters.MainRecyclerAdapter;
import com.mirzaadil.assignmentpayconiq.adapters.MainRecyclerAdapterDb;
import com.mirzaadil.assignmentpayconiq.database.db.RepositoriesDao;
import com.mirzaadil.assignmentpayconiq.database.db.RepositoryDatabase;
import com.mirzaadil.assignmentpayconiq.models.HomeAPIResponseModel;
import com.mirzaadil.assignmentpayconiq.database.entity.Model;
import com.mirzaadil.assignmentpayconiq.utils.Utils;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements MainActivityView {

    private int currentItems;
    private int totalItems;
    private int scrollOutItems;
    private int mainProductsPage = 1;
    Boolean isScrolling = false;

    private RecyclerView mainRecycler;
    private RecyclerView mainRecyclerViewOffline;

    private RepositoriesDao repositoriesDao;
    private LottieAnimationView progressBar;
    private LottieAnimationView progressBar_bottom;
    private LottieAnimationView refresh_buttom;
    private TextView textView_bottomMessage;
    private RelativeLayout relativeLayout_bottom;

    MainActivityPresenter mainActivityPresenter;
    MainRecyclerAdapter mainRecyclerAdapter;
    MainRecyclerAdapterDb mainRecyclerAdapterdb;
    List<Model.RepositoryModel> repositoryModelList = new ArrayList<Model.RepositoryModel>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Initializing the presenter for this activity.
        initView();


        // The following method call is responsible of setting up all of the configuration related
        // to recycler view.
        configureRecycler();

        // The following method call is responsible to perform the series of tasks that this activity
        // is supposed to perform.
        setupActivityTasks();
    }

    /**
     * In this method we are Objects.
     */
    private void initView() {

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        mainRecycler = (RecyclerView) findViewById(R.id.main_recycler);
        mainRecyclerViewOffline = (RecyclerView) findViewById(R.id.rv_repos_offline);

        textView_bottomMessage = (TextView) findViewById(R.id.textviewBottom);
        relativeLayout_bottom = (RelativeLayout) findViewById(R.id.bottom_layout);
        repositoriesDao = RepositoryDatabase.getInstance(this).getRepsositoriesDao();
        progressBar = (LottieAnimationView) findViewById(R.id.progress_bar);
        progressBar_bottom = (LottieAnimationView) findViewById(R.id.progress_bar_bottom);
        refresh_buttom = (LottieAnimationView) findViewById(R.id.button_refresh);
        mainActivityPresenter = new MainActivityPresenter(this);
        refresh_buttom = (LottieAnimationView) findViewById(R.id.button_refresh);

        //Refresh Button
        refresh_buttom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Call Refresh Api
                mainProductsPage = 1;
                mainActivityPresenter.fetchProducts();

            }
        });


    }


    /**
     * In this method we are configuring mainRecycler according to our needs.
     */
    private void configureRecycler() {

        // Create a grid layout with two columns
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);


        // Setting up the layout manager for recycler
        mainRecycler.setLayoutManager(layoutManager);
        mainRecyclerViewOffline.setLayoutManager(linearLayoutManager);

        // Setting up onScrollListener for recycler. Which will be used for pagination.
        mainRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                currentItems = layoutManager.getChildCount();
                totalItems = layoutManager.getItemCount();
                scrollOutItems = layoutManager.findFirstVisibleItemPosition();

                if (isScrolling && (currentItems + scrollOutItems == totalItems)) {
                    isScrolling = false;

                    ++mainProductsPage;
                    progressBar_bottom.setVisibility(View.VISIBLE);
                    mainActivityPresenter.fetchHomeProducts(mainProductsPage);

                }

            }
        });
    }

    private void setupActivityTasks() {
        // Setting the visibility of the progress bar.
        // progressBar.setVisibility(View.VISIBLE);

        // Calling the presenter method to fetch flash and home products.
        mainActivityPresenter.fetchProducts();
    }

    /**
     * This is the CallBack method from MainActivityPresenter which will be called when flash and
     * home products are fetched successfully.
     * I am using RxJava to combine the response from different Async Retrofit Calls.
     *
     * @param repositeryResponse contains the combined response flash and home API
     */
    @Override
    public void onCombinedProductsFetched(List<HomeAPIResponseModel> repositeryResponse) {

        Log.d("MIRZA", "repositeryResponse:>>>>>>>>>>>>>> " + repositeryResponse.size());

        progressBar.setVisibility(View.GONE);
        progressBar_bottom.setVisibility(View.GONE);
        refresh_buttom.setVisibility(View.GONE);
        mainRecycler.setVisibility(View.VISIBLE);
        mainRecyclerViewOffline.setVisibility(View.GONE);

        mainRecyclerAdapter = new MainRecyclerAdapter(this, repositeryResponse);
        mainRecycler.setAdapter(mainRecyclerAdapter);
        mainRecyclerAdapter.notifyDataSetChanged();

        //Save DataBase
        if (mainProductsPage == 1) {
            repositoriesDao.deleteAll();
            saveOfflineData(repositeryResponse);
        }

        //Hide Visibility for check internet Connection
        relativeLayout_bottom.setBackgroundResource(R.drawable.bottom_background_transparet);
        textView_bottomMessage.setVisibility(View.GONE);
    }






    /**
     * This is the CallBack method from MainActivityPresenter which will be called if there is some
     * problem in fetching the products.
     * The reason of failure can be following:
     * - Session TimeOut
     * - Connection Reset
     * - Server Not Responding
     * - Any other http response than 202,404 and any error etc.
     */
    @Override
    public void onProductsFetchFailed() {
        progressBar.setVisibility(View.GONE);
        progressBar_bottom.setVisibility(View.GONE);
        refresh_buttom.setVisibility(View.VISIBLE);

        relativeLayout_bottom.setBackgroundResource(R.drawable.bottom_background);
        textView_bottomMessage.setVisibility(View.VISIBLE);
        textView_bottomMessage.setText(getResources().getString(R.string.unable_to_fetch_data));
    }

    /**
     * This is the CallBack method from MainActivityPresenter which will be called if the network is
     * not available while the presenter tries to call the APIs.
     * The reason of failure can be following:
     * - Wifi is turned off
     * - Wifi is turned on but for some reason network is not available
     * - Wifi is turned off call local DataBase and show data
     */
    @Override
    public void onNetworkFailure() {

        progressBar.setVisibility(View.GONE);
        refresh_buttom.setVisibility(View.VISIBLE);
        progressBar_bottom.setVisibility(View.GONE);
        mainRecycler.setVisibility(View.GONE);

        relativeLayout_bottom.setBackgroundResource(R.drawable.bottom_background);
        textView_bottomMessage.setVisibility(View.VISIBLE);
        textView_bottomMessage.setText(getResources().getString(R.string.unable_to_connect_to_the_internet));

        mainRecyclerAdapterdb = new MainRecyclerAdapterDb(this, repositoriesDao.getAllRepositories());
        mainRecyclerViewOffline.setVisibility(View.VISIBLE);
        mainRecyclerViewOffline.setAdapter(mainRecyclerAdapterdb);
        mainRecyclerAdapterdb.notifyDataSetChanged();

    }


    /**
     * In this method we are save Objects on Database.
     */

    public void saveOfflineData(List<HomeAPIResponseModel> homeList) {
        if (homeList.size() != 0) {
            for (int i = 0; i < homeList.size(); i++) {
                Model.RepositoryModel repositoryModel = new Model.RepositoryModel();
                repositoryModel.setName(homeList.get(i).getName());
                repositoryModel.setDesceiption(homeList.get(i).getDescription());
                repositoryModel.setPostId(String.valueOf(homeList.get(i).getId()));
                Bitmap bitmap = Utils.getBitmapFromURL(homeList.get(i).getOwner().getAvatarUrl());
                repositoryModel.setImage_url(Utils.encodeImage(bitmap));

                repositoryModelList.add(repositoryModel);
            }
            repositoriesDao.addRepositories(repositoryModelList);

        }
    }


}
