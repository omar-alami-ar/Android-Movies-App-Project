package com.example.movieuitemplate.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityOptions;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.example.movieuitemplate.R;
import com.example.movieuitemplate.adapters.CastAdapter;
import com.example.movieuitemplate.adapters.MovieAdapter;
import com.example.movieuitemplate.adapters.MovieItemClickListener;
import com.example.movieuitemplate.adapters.ReviewAdapter;
import com.example.movieuitemplate.adapters.ReviewsAdapter;
import com.example.movieuitemplate.adapters.WatchlistLVadapter;
import com.example.movieuitemplate.adapters.WatchlistsActivityAdapter;
import com.example.movieuitemplate.models.Cast;
import com.example.movieuitemplate.models.Like;
import com.example.movieuitemplate.models.Movie;
import com.example.movieuitemplate.models.MovieCompanie;
import com.example.movieuitemplate.models.Review;
import com.example.movieuitemplate.models.User;
import com.example.movieuitemplate.models.WatchListItem;
import com.example.movieuitemplate.models.Watchlist;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
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
import java.util.HashMap;
import java.util.List;

public class MovieDetailActivity extends AppCompatActivity implements MovieItemClickListener {

    private ImageView movieThumbnailImg, movieCoverImg;
    private TextView tvTitle, tvDescription,tvRating;
    private RecyclerView rvCast, rvCompanies,rvSimilar;
    private CastAdapter castAdapter;
    private MovieAdapter    movieAdapter;
    private MaterialButton addToWatchlist,addToLikes;

    private boolean liked;

    TextInputLayout tbReview;

    ListView reviewsListView;
    ReviewAdapter reviewAdapter;
    ArrayList<Review> reviews = new ArrayList<Review>();

    ListView watchlistLV;
    //ArrayList<WatchListItem>  wlArrayList;

    FirebaseUser firebaseUser;
    DatabaseReference reference,reviewsReference;

    private final List<Cast> castData = new ArrayList<>();
    private final List<Movie> similarMovies = new ArrayList<>();
    private final List<MovieCompanie> movieCompanies = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        reviewsListView = findViewById(R.id.lvReviews);
        iniViews();

        reviewsReference = FirebaseDatabase.getInstance().getReference("Reviews");
        reviewsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot reviewSnapshot: snapshot.getChildren()) {
                    Review review = reviewSnapshot.getValue(Review.class);
                    if (review.getMovieId().equals(String.valueOf(getIntent().getIntExtra("id", 0)))) {
                        reviews.add(review);
                    }

                }
                setUpReviewsListView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        addToWatchlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String movieTitle = getIntent().getStringExtra("title");
                String movieID = String.valueOf(getIntent().getIntExtra("id",0));
                String movieImg = getIntent().getStringExtra("imgURL");
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
                }).setPositiveButton("Create new list", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(MovieDetailActivity.this, WatchlistsActivity.class);
                        startActivity(intent);
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
                        adapter.movieImg = movieImg;
                        watchlistLV.setAdapter(adapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }


                });




            }
        });



        addToLikes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if(liked == false) {
                        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                    reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid()).child("Likes");

                        HashMap<String, String> hashMap = new HashMap<>();
                        hashMap.put("id", String.valueOf(getIntent().getIntExtra("id", 0)));
                        hashMap.put("name", getIntent().getStringExtra("title"));
                        reference.child(String.valueOf(getIntent().getIntExtra("id", 0))).setValue(hashMap);
                        addToLikes.setBackgroundColor(getResources().getColor(R.color.liked));
                        addToLikes.setText("Added to likes");
                        liked = true;
                    } else {
                        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                        reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid()).child("Likes");
                        reference.child(String.valueOf(getIntent().getIntExtra("id", 0))).removeValue();
                        addToLikes.setBackgroundColor(getResources().getColor(R.color.notLiked));
                        addToLikes.setText("Add to likes");
                        liked = false;
                    }

                //addToLikes.setIconTint(getResources().getColor(R.color.liked));
            }
        });


        CastMovieData castMovieData = new CastMovieData();
        castMovieData.execute();
        SimilarMoviesData similarMoviesData = new SimilarMoviesData();
        similarMoviesData.execute();
    }

    private void setUpReviewsListView() {
        reviewAdapter = new ReviewAdapter(MovieDetailActivity.this, reviews);
        reviewsListView.setAdapter(reviewAdapter);
        //reviewsListView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    private void setupRvCast() {

        castAdapter = new CastAdapter(this, castData);
        rvCast.setAdapter(castAdapter);
        rvCast.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }

    private void setUpRVSimilarMovies() {
        movieAdapter = new MovieAdapter(this, similarMovies, this);
        rvSimilar.setAdapter(movieAdapter);
        rvSimilar.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }

    void iniViews(){
        String movieTitle = getIntent().getStringExtra("title");
        //String movieID = String.valueOf(getIntent().getIntExtra("id",0));
        String movieRating = getIntent().getStringExtra("rating");
        String imageResourceId = getIntent().getStringExtra("imgURL");
        String imageCover = getIntent().getStringExtra("imgCover");

        rvCast = findViewById(R.id.rvCast);
        rvSimilar = findViewById(R.id.rvSimilar);
       // rvCompanies = findViewById(R.id.rvComp);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid()).child("Likes");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild(String.valueOf(getIntent().getIntExtra("id", 0)))) {
                    addToLikes.setBackgroundColor(getResources().getColor(R.color.liked));
                    addToLikes.setText("Added to likes");
                    liked = true;
                } else {
                    addToLikes.setBackgroundColor(getResources().getColor(R.color.notLiked));
                    addToLikes.setText("Add to likes");
                    liked = false;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        tbReview = findViewById(R.id.tbReview);
        tbReview.setEndIconOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                if(tbReview.getEditText().getText()!=null) {
                    //Toast.makeText(MovieDetailActivity.this, tbReview.getEditText().getText().toString(), Toast.LENGTH_SHORT).show();
                    firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                    reference = FirebaseDatabase.getInstance().getReference("Reviews");
                    DatabaseReference usernameReference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

                    usernameReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                             User user = snapshot.getValue(User.class);
                             String username = user.getUsername();
                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("username", username);
                            hashMap.put("userId", firebaseUser.getUid());
                            hashMap.put("movieId", String.valueOf(getIntent().getIntExtra("id", 0)));
                            hashMap.put("reviewText", tbReview.getEditText().getText().toString());

                            reference.push().setValue(hashMap);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }
        });



        movieThumbnailImg = findViewById(R.id.detailMovieCover);
        Glide.with(this).load("https://image.tmdb.org/t/p/w500" +imageResourceId).into(movieThumbnailImg);

        movieCoverImg = findViewById(R.id.detailMovieImg);
        Glide.with(this).load("https://image.tmdb.org/t/p/w500" +imageCover).into(movieCoverImg);

        tvTitle = findViewById(R.id.detailMovieTitle);
        tvTitle.setText(movieTitle);

        tvRating = findViewById(R.id.movieRating);
        tvRating.setText(movieRating);

        addToWatchlist = findViewById(R.id.btnAddToWatchlist);
        addToLikes = findViewById(R.id.btnAddToLikes);


       // getSupportActionBar().setTitle(movieTitle);

        tvDescription = findViewById(R.id.detailMovieDesc);
        tvDescription.setText(getIntent().getStringExtra("description"));


        //setup Animation
        movieCoverImg.setAnimation(AnimationUtils.loadAnimation(this, R.anim.scale_animation));

    }

    @Override
    public void onMovieClick(Movie movie, ImageView movieImageView) {
        Intent intent = new Intent(this, MovieDetailActivity.class);
        intent.putExtra("title", movie.getTitle());
        intent.putExtra("imgURL", movie.getThumbnail());
        intent.putExtra("rating",movie.getRating());
        intent.putExtra("imgCover", movie.getCoverPhoto());
        intent.putExtra("description", movie.getDescription());
        intent.putExtra("id", movie.getId());

        //Toast.makeText(this, String.valueOf(movie.getId()), Toast.LENGTH_SHORT).show();

        //Animation

        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MovieDetailActivity.this, movieImageView, "sharedName");
        startActivity(intent, options.toBundle());
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
                //Log.e("cast", "onPostExecute: cast found" );
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

    class SimilarMoviesData extends AsyncTask<String, String, String> {
        private String JSON_URL = "https://api.themoviedb.org/3/movie/"+ String.valueOf(getIntent().getIntExtra("id", 0)).trim() +"/similar?api_key=eaccbc4d46ac90d1613d46f6937c1f4b&page=1";



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
                //Log.e("similar", "onPostExecute: similar found" );

                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("results");

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                    Movie movie = new Movie();
                    movie.setTitle(jsonObject1.getString("original_title"));
                    movie.setThumbnail(jsonObject1.getString("backdrop_path"));
                    movie.setCoverPhoto(jsonObject1.getString("poster_path"));
                    movie.setRating(String.valueOf(jsonObject1.getDouble("vote_average")));
                    movie.setId(jsonObject1.getInt("id"));
                    movie.setDescription(jsonObject1.getString("overview"));

                    similarMovies.add(movie);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            setUpRVSimilarMovies();
        }




    }
}
