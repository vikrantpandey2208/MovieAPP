package com.vikrant.task1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import presentation.BackFragment;

public class a6_movie_detail extends AppCompatActivity {

    String TAG = "a6_movie_detail";
    public static final String BASE_URL ="https://image.tmdb.org/t/p/w185";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a6_movie_detail);
        setStatusColor();

        ImageView i1, i2;
        TextView t1, t2, t3;
        Button b;

        i1 = findViewById(R.id.detail_img);
        i2 = findViewById(R.id.detail_img2);
        t1 = findViewById(R.id.mname);
        t2 = findViewById(R.id.overview);
        t3 = findViewById(R.id.date);
        b = findViewById(R.id.playnow);

        Intent intent = getIntent();
        Movie mov_intent = (Movie) intent.getSerializableExtra("detail");
        String url = mov_intent.getBackdropPath();

        t1.setText(mov_intent.getTitle());
        t2.setText(mov_intent.getOverview());
        t3.setText(mov_intent.getReleaseDate());
        b.setClickable(true);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(a6_movie_detail.this);
                builder.setTitle(mov_intent.getTitle());
                builder.setMessage("Movie is Playing");
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Do close the quiz
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        Picasso.get()
                .load(BASE_URL+url)
                .into(i1);
        url = mov_intent.getPosterPath();
        Picasso.get()
                .load(BASE_URL+url)
                .into(i2);
    }

    private void setStatusColor(){
        //setting status bar color
        Window window;
        window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getColor(R.color.azure));
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }
}