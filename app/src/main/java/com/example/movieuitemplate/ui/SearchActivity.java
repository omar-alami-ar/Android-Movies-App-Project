package com.example.movieuitemplate.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.movieuitemplate.R;
import com.example.movieuitemplate.adapters.MovieAdapter;
import com.example.movieuitemplate.adapters.MovieItemClickListener;
import com.example.movieuitemplate.models.Movie;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class SearchActivity extends AppCompatActivity implements MovieItemClickListener {

    private SearchView search;
    private RecyclerView rvMovies;

    private List<Movie> movies = new ArrayList<>();

    private MovieAdapter movieAdapter;

    private static boolean firstTime = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        search = findViewById(R.id.searchView);
        rvMovies = findViewById(R.id.rvSearchMovies);




        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                movies.clear();
                MovieSearchData movieSearchData = new MovieSearchData();
                movieSearchData.JSON_URL = MovieSearchData.LIEN + query;
                try {
                    movieSearchData.execute().get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                /*movies.clear();
                MovieSearchData movieSearchData = new MovieSearchData();
                movieSearchData.JSON_URL = MovieSearchData.LIEN + newText;
                movieSearchData.execute();*/
                return false;
            }
        });
    }



    private void setUpRVMovies() {

        if (firstTime) {
            firstTime = false;
            movieAdapter = new MovieAdapter(this, movies, this);
            rvMovies.setAdapter(movieAdapter);
            rvMovies.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        }else{
            movieAdapter.updateData(movies);
        }
    }




    @Override
    public void onMovieClick(Movie movie, ImageView movieImageView) {
        Intent intent = new Intent(this, MovieDetailActivity.class);
        intent.putExtra("title", movie.getTitle());
        intent.putExtra("imgURL", movie.getThumbnail());
        intent.putExtra("imgCover", movie.getCoverPhoto());
        intent.putExtra("description", movie.getDescription());
        intent.putExtra("id", movie.getId());

        //Toast.makeText(this, String.valueOf(movie.getId()), Toast.LENGTH_SHORT).show();

        //Animation

        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SearchActivity.this, movieImageView, "sharedName");
        startActivity(intent, options.toBundle());
    }

    class MovieSearchData extends AsyncTask<String, String, String> {
        private static final String LIEN = "https://api.themoviedb.org/3/search/movie?api_key=5cc9ceb908ad9216a127b5d99cbdd8e5&query=";
        private String JSON_URL = "";
        //https://api.themoviedb.org/3/movie/top_rated?api_key=5cc9ceb908ad9216a127b5d99cbdd8e5

        //JSON_URL = "https://api.themoviedb.org/3/search/movie?api_key=5cc9ceb908ad9216a127b5d99cbdd8e5&query=venom";
        @Override
        protected String doInBackground(String... strings) {
            String current = "";

            try {
                URL url;
                HttpURLConnection urlConnection = null;

                try {
                    url = new URL(JSON_URL);
                    urlConnection = (HttpURLConnection) url.openConnection();

                    InputStream is = urlConnection.getInputStream();
                    //InputStreamReader isr = new InputStreamReader(is);
                    BufferedReader isr = new BufferedReader(new InputStreamReader(is));

                    int data = isr.read();
                    while (data != -1) {
                        current += (char) data;
                        data = isr.read();
                    }
                    return current;

                } catch (MalformedURLException e) {
                    e.printStackTrace();

                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (urlConnection != null)
                        urlConnection.disconnect();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return current;
        }

        @Override
        protected void onPostExecute(String s) {

            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("results");

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                    Movie movie = new Movie();
                    movie.setTitle(jsonObject1.getString("original_title"));
                    movie.setThumbnail(jsonObject1.getString("backdrop_path"));
                    movie.setCoverPhoto(jsonObject1.getString("poster_path"));
                    movie.setId(jsonObject1.getInt("id"));
                    movie.setDescription(jsonObject1.getString("overview"));

                    movies.add(movie);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            Toast.makeText(SearchActivity.this, String.valueOf(movies.size()), Toast.LENGTH_SHORT).show();
            setUpRVMovies();
        }


    }
}