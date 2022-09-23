package com.vikrant.task1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class a5_watchlist extends AppCompatActivity {
    String TAG = "a5_watchlist";
    GridView gridView ;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a5_watchlist);
        Log.d(TAG, "onCreate: ");
        setStatusColor();
        setListenerToTablayout();
        gridView = findViewById(R.id.a5_grid);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("data").document(Universal.userID+ "W");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete( Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        ArrayList<String> list = new ArrayList<>();

                        Map<String, Object> map = document.getData();
                        if (map != null) {
                            for (Map.Entry<String, Object> entry : map.entrySet()) {
                                list.add(entry.getValue().toString());
                            }
                        }

                        SmallAdapter adapter= new SmallAdapter(a5_watchlist.this,list);
                        gridView.setAdapter(adapter);
                    }


                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
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
    private void setListenerToTablayout() {
        //Tablayout events
        Universal universal = new Universal();
        bottomNavigationView = findViewById(R.id.a5_navigation);
        bottomNavigationView.getMenu().findItem(R.id.menu_wlist).setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_movie: {
                        universal.OpenGivenActivity(a5_watchlist.this, a2_movies.class);
                        break;
                    }
                    case R.id.menu_favorite: {
                        universal.OpenGivenActivity(a5_watchlist.this, a4_favorite.class);
                        break;
                    }
                    case R.id.menu_wlist: {
                        break;
                    }
                    case R.id.menu_logout: {
                        AuthUI.getInstance()
                                .signOut(a5_watchlist.this)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(a5_watchlist.this,"Logged Out", Toast.LENGTH_SHORT).show();
                                    }
                                });
                        universal.OpenGivenActivity(a5_watchlist.this, a3_login.class);
                        finishAffinity();
                        break;
                    }
                }
                return false;
            }
        });
    }
}