package presentation;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.squareup.picasso.Picasso;
import com.vikrant.task1.R;

public class BackFragment extends Fragment {

    String TAG = "BackFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.back_fragment, container, false);
        return view;
    }

    public void set_backdrop(String url) {
        Log.d(TAG, "set_backdrop: parsing " + url);
        ImageView view = (ImageView) getView().findViewById(R.id.movie_backdrop);
        Picasso.get()
                .load(url)
                .into(view);
    }
}
