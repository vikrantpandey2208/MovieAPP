package com.vikrant.task1;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class MovieAdapter extends BaseAdapter {
    private Context mContext;
    ArrayList<Movie> list;
    public static final String MOVIE_BASE_URL="https://image.tmdb.org/t/p/w185";

    public MovieAdapter(Context context, ArrayList<Movie> movieList) {
        this.mContext = context;
        this.list = movieList;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        Movie movies = (Movie) getItem(position);
        ImageView imageView;
        TextView name, date;
        ImageButton fav, wish;

        if (convertView == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_cardview, parent, false);
        } else {
            view = convertView;
        }

        imageView = view.findViewById(R.id.card_img);
        name = view.findViewById(R.id.card_name);
        date = view.findViewById(R.id.date);
        fav = view.findViewById(R.id.fav);
        wish = view.findViewById(R.id.wish);

        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseFirestore firestore= FirebaseFirestore.getInstance();
                Map<String, Object> docData = new HashMap<>();
                docData.put(String.valueOf(System.currentTimeMillis()), movies.getPosterPath());
                firestore.collection("data").document(Universal.userID + "F")
                        .set(docData, SetOptions.merge());
            }
        });

        wish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseFirestore firestore= FirebaseFirestore.getInstance();
                Map<String, Object> docData = new HashMap<>();
                docData.put(String.valueOf(System.currentTimeMillis()), movies.getPosterPath());
                firestore.collection("data").document(Universal.userID + "W")
                        .set(docData, SetOptions.merge());
            }
        });

        //load data into the ImageView using Picasso
        Picasso.get().load(MOVIE_BASE_URL + movies.getPosterPath())
                .placeholder(R.drawable.mlogo)
                .into(imageView);
        name.setText("Title: "+ movies.getTitle());
        date.setText( movies.getReleaseDate());

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, a6_movie_detail.class);
                intent.putExtra("detail", movies);
                mContext.startActivity(intent);
            }
        });

        return view;
    }
}
