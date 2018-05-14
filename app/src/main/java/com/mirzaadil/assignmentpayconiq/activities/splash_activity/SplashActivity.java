package com.mirzaadil.assignmentpayconiq.activities.splash_activity;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.airbnb.lottie.LottieAnimationView;
import com.mirzaadil.assignmentpayconiq.R;
import com.mirzaadil.assignmentpayconiq.activities.main_activity.MainActivity;
import com.mirzaadil.assignmentpayconiq.utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity {

    /**
     * Created by Mirza Adil on 5/11/2018.
     * <p>
     * This is the first activity which is launched upon application startup.
     * This activity will just wait for 3 seconds and will call the navigation controller method to start
     * MainActivity.
     */


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent_main = new Intent(SplashActivity.this, MainActivity.class);
                intent_main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent_main);
                finish();

            }
        }, Constants.SPLASH_LENGTH);
    }
}
