package com.vikrant.task1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.util.ArrayList;

//import butterknife.ButterKnife;
import utilities.Network;

public class a2_movies extends AppCompatActivity {
    String TAG = "a2_movies";
    BottomNavigationView bottomNavigationView;
    GridView gridView;
    ArrayList<Movie> mPopularList;
    ArrayList<Movie> mTopTopRatedList;
    ImageView searchBtn;
    String query = "http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=";
    Dialog dialog;
    Universal universal = new Universal();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.top_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.pop_movies) {
            refreshList(mPopularList);
        }
        if (id == R.id.top_movies) {
            refreshList(mTopTopRatedList);
        }
        return super.onOptionsItemSelected(item);
    }

    private void refreshList(ArrayList<Movie> list) {
        MovieAdapter adapter = new MovieAdapter(a2_movies.this, list);
        gridView.invalidateViews();
        gridView.setAdapter(adapter);
    }

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a2_movies);
        setStatusColor();
        setListenerToTablayout();
        Log.d(TAG, "onCreate: ");

        gridView = findViewById(R.id.a2_grid);
        searchBtn = findViewById(R.id.a2_search);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: search btn" + onSearchRequested());
                onSearchRequested();
            }
        });



        // Get the intent, verify the action and get the query
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String queryI = intent.getStringExtra(SearchManager.QUERY);
            Log.d(TAG, "onCreate: " + queryI);
            if(queryI != null){
                query = "http://api.themoviedb.org/3/search/movie?&query="+queryI+"&api_key=";
            }
        }

        new AsynTask().execute();
    }


    public class AsynTask extends AsyncTask<Void, Void, Void> {

        String popularMovies;
        String topRatedMovies;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = universal.SetLoadingBar(a2_movies.this);
        }

        @Override
        protected void onPostExecute(Void  s) {
            super.onPostExecute(s);
            dialog.cancel();
            MovieAdapter adapter = new MovieAdapter(a2_movies.this,mPopularList);
            gridView.setAdapter(adapter);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> view, View view1, int position, long l) {
                    Movie movie = (Movie) view.getAdapter().getItem(position);
                    Intent intent = new Intent(a2_movies.this, a6_movie_detail.class);
                    intent.putExtra("detail", movie);
                    startActivity(intent);
                }
            });
        }

        @Override
        protected Void doInBackground(Void... voids) {
            final String KEY = "88f8409be9878a26970f4c0a31b02e74";

            String popularMoviesURL = query + KEY;

            //String topRatedMoviesURL = "http://api.themoviedb.org/3/discover/movie?sort_by=vote_average.desc&api_key="+ KEY;

            mPopularList = new ArrayList<>();
            mTopTopRatedList = new ArrayList<>();

            try {
                if(Network.networkStatus(a2_movies.this)){
                    mPopularList = Network.fetchData(popularMoviesURL); //Get popular movies
                    Network.registerUser();
                }else{
                    Toast.makeText(a2_movies.this,"No Internet Connection",Toast.LENGTH_LONG).show();
                }
            } catch (IOException e){
                e.printStackTrace();
            }

            Log.d(TAG, mPopularList.toString());
            Log.d(TAG, mTopTopRatedList.toString());



            return null;
        }
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
        bottomNavigationView = findViewById(R.id.a2_navigation);
        bottomNavigationView.getMenu().findItem(R.id.menu_movie).setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_movie: {
                        break;
                    }
                    case R.id.menu_favorite: {
                        universal.OpenGivenActivity(a2_movies.this, a4_favorite.class);
                        break;
                    }
                    case R.id.menu_wlist: {
                        universal.OpenGivenActivity(a2_movies.this, a5_watchlist.class);
                        break;
                    }
                    case R.id.menu_logout: {
                        AuthUI.getInstance()
                                .signOut(a2_movies.this)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(a2_movies.this,"Logged Out", Toast.LENGTH_SHORT).show();
                                    }
                                });
                        universal.OpenGivenActivity(a2_movies.this, a3_login.class);
                        finishAffinity();
                        break;
                    }
                }
                return false;
            }
        });
    }
}