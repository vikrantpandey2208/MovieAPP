package com.vikrant.task1;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import java.util.Timer;
import java.util.TimerTask;

public class a1_splash extends AppCompatActivity {
    ImageView logo;
    ProgressBar progressBar;
    // for counting the progress
    int counter = 0;
    String TAG = "a1_splash";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a1_splash);
        setStatusColor();

        logo = findViewById(R.id.a1_app_logo);

        //Setting essentials
        FirebaseAuth fAuth = FirebaseAuth.getInstance();
        FirebaseUser user = fAuth.getCurrentUser();

        // user is logged in
        // Setting the credentials to app scope variables
        if (user != null) {
            String userId = user.getUid();
            Universal.userID = userId;
            String userName = user.getDisplayName();
            userName = toTitleCase(userName);
            Universal.userName = userName;

            final Handler handler = new Handler();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(getApplicationContext(), a2_movies.class);
                    startActivity(intent);

                    a1_splash.this.finish();
                }
            }, 2000);
        }
        else {
            final Handler handler = new Handler();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(a1_splash.this, a3_login.class);
                    startActivity(intent);

                    finish();
                }
            }, 2000);
        }


        // loading the animation to image of logo
        Animation newanimation = AnimationUtils.loadAnimation(a1_splash.this, R.anim.a1_logo);
        logo.startAnimation(newanimation);
        progressbarRun();

    }
    // method ends

    public static String toTitleCase(String givenString) {
        String[] arr = givenString.split(" ");
        StringBuffer sb = new StringBuffer();

        // adjusting the case of string
        for (int i = 0; i < arr.length; i++) {
            sb.append(Character.toUpperCase(arr[i].charAt(0)))
                    .append(arr[i].substring(1)).append(" ");
        }
        return sb.toString().trim();
    }
    // method ends

    private void setStatusColor(){
        //setting status bar color
        Window window;
        window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getColor(R.color.prime));
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }

    public void progressbarRun() {
        progressBar = (ProgressBar) findViewById(R.id.a1_progressbar);
        final Timer t = new Timer();

        // running the timer
        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                counter = counter + 1;
                progressBar.setProgress(counter);
                if (counter == 100) {
                    t.cancel();
                }
            }
        };
        t.schedule(tt, 0, 10);
    }
    // method ends
}