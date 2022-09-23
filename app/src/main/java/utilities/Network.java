package utilities;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.google.android.gms.common.util.IOUtils;
import com.vikrant.task1.Movie;

//import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

public class Network {
    static String TAG = "Network";

    public static Boolean networkStatus(Context context){
        ConnectivityManager manager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()){
            return true;
        }
        return false;
    }
    public static boolean registerUser(){
        final String KEY = "88f8409be9878a26970f4c0a31b02e74";
        String url = "https://api.themoviedb.org/3/authentication/token/new?api_key=";
        Log.d(TAG, "registerUser: url "+ url);

        String token = findToken();

        if(token != null){

        }

        try {
            URL new_url = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) new_url.openConnection(); //Opening a http connection  to the remote object

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "*/*");

            connection.connect();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static String findToken() {
        final String KEY = "88f8409be9878a26970f4c0a31b02e74";
        String url = "https://api.themoviedb.org/3/authentication/token/new?api_key=" + KEY;
        Log.d(TAG, "findToken: "+ url);
        try {

            URL new_url = new URL(url); //create a url from a String
            Log.d(TAG, "findToken: url "+ url);
            HttpURLConnection connection = (HttpURLConnection) new_url.openConnection(); //Opening a http connection  to the remote object
            connection.connect();
            InputStream inputStream = connection.getInputStream(); //reading from the object

            Scanner s = new Scanner(inputStream).useDelimiter("\\A");
            String results = s.hasNext() ? s.next() : "";

            Log.d(TAG, "findToken: result " + results);

            JSONObject mainObject = new JSONObject(results);
            String token = mainObject.getString("request_token");
            Log.d(TAG, "findToken: is " + token);
            return token;

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<Movie> fetchData(String url) throws IOException {
        ArrayList<Movie> movies = new ArrayList<Movie>();
        try {

            URL new_url = new URL(url); //create a url from a String
            Log.d(TAG, "fetchData: url "+ url);
            HttpURLConnection connection = (HttpURLConnection) new_url.openConnection(); //Opening a http connection  to the remote object
            connection.connect();
            InputStream inputStream = connection.getInputStream(); //reading from the object

            Scanner s = new Scanner(inputStream).useDelimiter("\\A");
            String results = s.hasNext() ? s.next() : "";

            //String results = new String(inputStream.read());  //IOUtils to convert inputstream objects into Strings type
            parseJson(results,movies);
            inputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return movies;
    }

    public static void parseJson(String data, ArrayList<Movie> list){

        try {
            JSONObject mainObject = new JSONObject(data);

            JSONArray resArray = mainObject.getJSONArray("results"); //Getting the results object
            for (int i = 0; i < resArray.length(); i++) {
                JSONObject jsonObject = resArray.getJSONObject(i);
                Movie movie = new Movie(); //New Movie object
                movie.setId(jsonObject.getInt("id"));
                movie.setVoteAverage(jsonObject.getInt("vote_average"));
                movie.setVoteCount(jsonObject.getInt("vote_count"));
                movie.setOriginalTitle(jsonObject.getString("original_title"));
                movie.setTitle(jsonObject.getString("title"));
                movie.setPopularity(jsonObject.getDouble("popularity"));
                movie.setBackdropPath(jsonObject.getString("backdrop_path"));
                movie.setOverview(jsonObject.getString("overview"));
                movie.setReleaseDate(jsonObject.getString("release_date"));
                movie.setPosterPath(jsonObject.getString("poster_path"));
                //Adding a new movie object into ArrayList
                list.add(movie);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, "Erro occurred during JSON Parsing", e);
        }

    }
}
