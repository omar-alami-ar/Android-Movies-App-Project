package com.example.movieuitemplate.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.movieuitemplate.R;
import com.example.movieuitemplate.adapters.CastAdapter;
import com.example.movieuitemplate.adapters.MovieCompanieAdapter;
import com.example.movieuitemplate.models.Cast;
import com.example.movieuitemplate.models.MovieCompanie;

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

public class MovieDetailActivity extends AppCompatActivity {

    private ImageView movieThumbnailImg, movieCoverImg;
    private TextView tvTitle, tvDescription;
    private RecyclerView rvCast, rvCompanies;
    private CastAdapter castAdapter;

    private final List<Cast> castData = new ArrayList<>();
    private final List<MovieCompanie> movieCompanies = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        iniViews();

        CastMovieData castMovieData = new CastMovieData();
        ProductionCompaniesMovieData productionCompaniesMovieData = new ProductionCompaniesMovieData();
        castMovieData.execute();
        productionCompaniesMovieData.execute();

    }

    void iniViews(){
        String movieTitle = getIntent().getStringExtra("title");
        String imageResourceId = getIntent().getStringExtra("imgURL");
        String imageCover = getIntent().getStringExtra("imgCover");

        rvCast = findViewById(R.id.rvCast);
        rvCompanies = findViewById(R.id.rvComp);

        movieThumbnailImg = findViewById(R.id.detailMovieCover);
        Glide.with(this).load("https://image.tmdb.org/t/p/w500" +imageResourceId).into(movieThumbnailImg);

        movieCoverImg = findViewById(R.id.detailMovieImg);
        Glide.with(this).load("https://image.tmdb.org/t/p/w500" +imageCover).into(movieCoverImg);

        tvTitle = findViewById(R.id.detailMovieTitle);
        tvTitle.setText(movieTitle);

       // getSupportActionBar().setTitle(movieTitle);

        tvDescription = findViewById(R.id.detailMovieDesc);
        tvDescription.setText(getIntent().getStringExtra("description"));


        //setup Animation
        movieCoverImg.setAnimation(AnimationUtils.loadAnimation(this, R.anim.scale_animation));

    }

    private void setupRvCast() {

        castAdapter = new CastAdapter(this, castData);
        rvCast.setAdapter(castAdapter);
        rvCast.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }

    private void setUpRvMovieCompanies(){
        MovieCompanieAdapter movieCompanieAdapter = new MovieCompanieAdapter(this, movieCompanies);
        rvCompanies.setAdapter(movieCompanieAdapter);
        rvCompanies.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
    }

    class CastMovieData extends AsyncTask<String, String, String> {
        private final String JSON_URL = "https://api.themoviedb.org/3/movie/"+ String.valueOf(getIntent().getIntExtra("id", 0)).trim() +"/credits?api_key=5cc9ceb908ad9216a127b5d99cbdd8e5";


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
                JSONArray jsonArray = jsonObject.getJSONArray("cast");

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                    Cast cast = new Cast();
                    cast.setName(jsonObject1.getString("original_name"));
                    cast.setImgLink(jsonObject1.getString("profile_path"));
                    cast.setCharacter(jsonObject1.getString("character"));

                    castData.add(cast);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            setupRvCast();


        }


    }
    class ProductionCompaniesMovieData extends AsyncTask<String, String, String> {
        private final String JSON_URL = "https://api.themoviedb.org/3/movie/"+ String.valueOf(getIntent().getIntExtra("id", 0)).trim() +"?api_key=5cc9ceb908ad9216a127b5d99cbdd8e5";


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
                JSONArray jsonArray = jsonObject.getJSONArray("production_companies");

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                    MovieCompanie movieCompanie = new MovieCompanie();
                    movieCompanie.setImg(jsonObject1.getString("logo_path"));
                    movieCompanie.setName(jsonObject1.getString("name"));
                    movieCompanie.setCountry(jsonObject1.getString("origin_country"));

                    movieCompanies.add(movieCompanie);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            setUpRvMovieCompanies();
            //Toast.makeText(MovieDetailActivity.this, String.valueOf(movieCompanies.get(4).getImg()), Toast.LENGTH_SHORT).show();
        }


    }
}