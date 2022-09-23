package com.vikrant.task1;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SmallAdapter extends BaseAdapter {
    private Context mContext;
    ArrayList<String> list;
    public static final String MOVIE_BASE_URL="https://image.tmdb.org/t/p/w185";

    public SmallAdapter(Context context, ArrayList<String> movieList) {
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
        ImageView imageView;
        String path = list.get(position);

        if (convertView == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.small_cardview, parent, false);
        } else {
            view = convertView;
        }

        imageView = view.findViewById(R.id.card_img);


        //load data into the ImageView using Picasso
        Picasso.get().load(MOVIE_BASE_URL + path)
                .placeholder(R.drawable.mlogo)
                .into(imageView);


        return view;
    }
}
