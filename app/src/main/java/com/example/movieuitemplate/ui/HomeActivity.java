package com.example.movieuitemplate.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.ActivityOptions;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.movieuitemplate.adapters.MovieItemClickListener;
import com.example.movieuitemplate.R;
import com.example.movieuitemplate.adapters.MovieAdapter;
import com.example.movieuitemplate.adapters.SliderPageAdapter;
import com.example.movieuitemplate.models.Movie;
import com.example.movieuitemplate.models.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
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
import java.util.Timer;
import java.util.TimerTask;

public class HomeActivity extends AppCompatActivity implements MovieItemClickListener {

    private ViewPager sliderPager;
    private TabLayout indicator;
    private RecyclerView moviesRV, moviesRVWeek;

    private TextView username;
    private TextView email;

    FirebaseUser firebaseUser;
    DatabaseReference reference;

    private NavigationView navigationView;
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private BottomNavigationView bottomNavigationView;

    private List<Movie> popularMovies = new ArrayList<>();
    private List<Movie> slideMovies = new ArrayList<>();
    private List<Movie> topRatedMovies = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity_with_nav);

        iniViews();


        PopularMoviesData popularMoviesData = new PopularMoviesData();
        SlideMoviesData slideMoviesData = new SlideMoviesData();
        TopRatedMoviesData topRatedMoviesData = new TopRatedMoviesData();
        popularMoviesData.execute();
        slideMoviesData.execute();
        topRatedMoviesData.execute();

        setBottomNav_NavDraView();


    }

    private void setBottomNav_NavDraView(){


        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorAccent));
        getSupportActionBar().setTitle("Home");

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.colorAccent));
        toggle.syncState();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:

                        //Toast.makeText(HomeActivity.this, "You are already in home", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.search:
                        //Toast.makeText(HomeActivity.this, "we would go to search page ", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(HomeActivity.this, SearchActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.discover:
                        //Toast.makeText(HomeActivity.this, "we would go to discover page ", Toast.LENGTH_SHORT).show();
                        Intent intent1 = new Intent(HomeActivity.this, DiscoverActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.more:
                        Toast.makeText(HomeActivity.this, "we would go to more page", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.watchlists:
                        Intent intent = new Intent(HomeActivity.this, WatchlistsActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.mainHome:
                        drawer.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.logout:

                        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case DialogInterface.BUTTON_POSITIVE:
                                       // Toast.makeText(HomeActivity.this, "Have a nice day and goodbye ", Toast.LENGTH_SHORT).show();
                                        FirebaseAuth.getInstance().signOut();
                                        Intent intent = new Intent(HomeActivity.this, StartActivity.class);
                                        startActivity(intent);
                                        finish();
                                        break;
                                }
                            }
                        };
                        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                        builder.setMessage("Are you sure you want to logout ?").setPositiveButton("Yes", dialogClickListener).setNegativeButton("No", dialogClickListener).show();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });

    }

    private void setUpRVTopRatedMovies() {

        MovieAdapter movieAdapter = new MovieAdapter(this, topRatedMovies, this);
        moviesRVWeek.setAdapter(movieAdapter);
        moviesRVWeek.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }

    private void setUpRVPopularMovies() {
        //setup Recview

        MovieAdapter movieAdapter = new MovieAdapter(this, popularMovies, this);
        moviesRV.setAdapter(movieAdapter);
        moviesRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

    }

    private void setUpRVSLideMovies() {

        SliderPageAdapter adapter = new SliderPageAdapter(this, slideMovies, this);
        sliderPager.setAdapter(adapter);

        //setup the timer
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new SliderTime(), 4000, 6000);

        indicator.setupWithViewPager(sliderPager, true);
    }

    private void iniViews() {
        sliderPager = findViewById(R.id.sliderPager);
        indicator = findViewById(R.id.indicator);
        moviesRV = findViewById(R.id.RvMovies);
        moviesRVWeek = findViewById(R.id.RvMoviesWeek);

        navigationView = findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);
        username = header.findViewById(R.id.navUsername);
        email = header.findViewById(R.id.navEmail);


        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if (user!= null) {
                    username.setText(user.getUsername());
                    email.setText(user.getEmail());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        toolbar = findViewById(R.id.toolBar);
        drawer = findViewById(R.id.drawerLayout);
        bottomNavigationView = findViewById(R.id.bottomNavigation);

    }

    @Override
    public void onMovieClick(Movie movie, ImageView movieImageView) {
        //here we will send movie info to the detail activity
        //also the transition animation
        //Toast.makeText(this, "item clicked " + movie.getTitle(), Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, MovieDetailActivity.class);
        intent.putExtra("title", movie.getTitle());
        intent.putExtra("imgURL", movie.getThumbnail());
        intent.putExtra("rating",movie.getRating());
        intent.putExtra("imgCover", movie.getCoverPhoto());
        intent.putExtra("description", movie.getDescription());
        intent.putExtra("id", movie.getId());

        //Toast.makeText(this, String.valueOf(movie.getId()), Toast.LENGTH_SHORT).show();

        //Animation

        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(HomeActivity.this, movieImageView, "sharedName");
        startActivity(intent, options.toBundle());

    }

    class SliderTime extends TimerTask {

        @Override
        public void run() {
            HomeActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (sliderPager.getCurrentItem() < slideMovies.size() - 1) {
                        sliderPager.setCurrentItem(sliderPager.getCurrentItem() + 1);
                    } else
                        sliderPager.setCurrentItem(0);
                }
            });
        }
    }


    class PopularMoviesData extends AsyncTask<String, String, String> {
        private String JSON_URL = "https://api.themoviedb.org/3/movie/popular?api_key=5cc9ceb908ad9216a127b5d99cbdd8e5";


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
                    movie.setRating(String.valueOf(jsonObject1.getDouble("vote_average")));
                    movie.setId(jsonObject1.getInt("id"));
                    movie.setDescription(jsonObject1.getString("overview"));

                    popularMovies.add(movie);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            setUpRVPopularMovies();
        }


    }
    class SlideMoviesData extends AsyncTask<String, String, String> {
        private String JSON_URL = "https://api.themoviedb.org/3/movie/upcoming?api_key=5cc9ceb908ad9216a127b5d99cbdd8e5";


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
                    movie.setRating(String.valueOf(jsonObject1.getDouble("vote_average")));
                    movie.setCoverPhoto(jsonObject1.getString("poster_path"));
                    movie.setId(jsonObject1.getInt("id"));
                    movie.setDescription(jsonObject1.getString("overview"));

                    slideMovies.add(movie);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            setUpRVSLideMovies();

        }


    }
    class TopRatedMoviesData extends AsyncTask<String, String, String> {
        private String JSON_URL = "https://api.themoviedb.org/3/movie/top_rated?api_key=5cc9ceb908ad9216a127b5d99cbdd8e5";

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
                    movie.setRating(String.valueOf(jsonObject1.getDouble("vote_average")));
                    movie.setCoverPhoto(jsonObject1.getString("poster_path"));
                    movie.setId(jsonObject1.getInt("id"));
                    movie.setDescription(jsonObject1.getString("overview"));

                    topRatedMovies.add(movie);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            setUpRVTopRatedMovies();
        }


    }
}