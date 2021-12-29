package com.example.movieuitemplate.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.movieuitemplate.R;
import com.example.movieuitemplate.adapters.CastAdapter;
import com.example.movieuitemplate.adapters.WatchlistLVadapter;
import com.example.movieuitemplate.models.Cast;
import com.example.movieuitemplate.models.MovieCompanie;
import com.example.movieuitemplate.models.WatchListItem;
import com.example.movieuitemplate.models.Watchlist;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
    private TextView tvTitle, tvDescription,tvRating;
    private RecyclerView rvCast, rvCompanies;
    private CastAdapter castAdapter;
    private MaterialButton addToWatchlist;

    ListView watchlistLV;
    //ArrayList<WatchListItem>  wlArrayList;

    FirebaseUser firebaseUser;
    DatabaseReference reference;

    private final List<Cast> castData = new ArrayList<>();
    private final List<MovieCompanie> movieCompanies = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        iniViews();

        addToWatchlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String movieTitle = getIntent().getStringExtra("title");
                String movieID = String.valueOf(getIntent().getIntExtra("id",0));
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(MovieDetailActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.watchlistlv_activity,null);
                mBuilder.setTitle("Choose a watchlist");
                watchlistLV = mView.findViewById(R.id.listWatchlists);
                firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                mBuilder.setView(mView);
                mBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = mBuilder.create();
                dialog.show();

                reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
                final ArrayList<WatchListItem> watchlists = new ArrayList<WatchListItem>();
                reference.child("Watchlists").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot watchListSnapshot: snapshot.getChildren()) {
                            WatchListItem watchListItem = watchListSnapshot.getValue(WatchListItem.class);
                            watchlists.add(watchListItem);
                        }
                        WatchlistLVadapter adapter = new WatchlistLVadapter(MovieDetailActivity.this,watchlists,dialog);
                        adapter.movieID = movieID;
                        adapter.movieName = movieTitle;
                        watchlistLV.setAdapter(adapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }


                });




            }
        });


        CastMovieData castMovieData = new CastMovieData();
        castMovieData.execute();
    }

    void iniViews(){
        String movieTitle = getIntent().getStringExtra("title");
        //String movieID = String.valueOf(getIntent().getIntExtra("id",0));
        String movieRating = getIntent().getStringExtra("rating");
        String imageResourceId = getIntent().getStringExtra("imgURL");
        String imageCover = getIntent().getStringExtra("imgCover");

        rvCast = findViewById(R.id.rvCast);
       // rvCompanies = findViewById(R.id.rvComp);

        movieThumbnailImg = findViewById(R.id.detailMovieCover);
        Glide.with(this).load("https://image.tmdb.org/t/p/w500" +imageResourceId).into(movieThumbnailImg);

        movieCoverImg = findViewById(R.id.detailMovieImg);
        Glide.with(this).load("https://image.tmdb.org/t/p/w500" +imageCover).into(movieCoverImg);

        tvTitle = findViewById(R.id.detailMovieTitle);
        tvTitle.setText(movieTitle);

        tvRating = findViewById(R.id.movieRating);
        tvRating.setText(movieRating);

        addToWatchlist = findViewById(R.id.btnAddToWatchlist);


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

}
