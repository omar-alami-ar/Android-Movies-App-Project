package com.example.movieuitemplate.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.movieuitemplate.R;
import com.example.movieuitemplate.adapters.GenreAdapter;
import com.example.movieuitemplate.models.Genre;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

public class DiscoverActivity extends AppCompatActivity {

    private RecyclerView rvGenres;
    private BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover_movies);

        init();

        setBottomNavView();

    }

    private void init() {
        rvGenres = findViewById(R.id.genresRV);
        bottomNavigationView = findViewById(R.id.bottomNavigation);


        List<Genre> genres = new ArrayList<>();
        genres.add(new Genre("Action", R.mipmap.action));
        genres.add(new Genre("Animated", R.mipmap.animated));
        genres.add(new Genre("Documentary", R.mipmap.documentary));
        genres.add(new Genre("drama", R.mipmap.drama));
        genres.add(new Genre("family", R.mipmap.family));
        genres.add(new Genre("fantasy", R.mipmap.fantasy));
        genres.add(new Genre("history", R.mipmap.history));
        genres.add(new Genre("comedy", R.mipmap.comedy));
        genres.add(new Genre("war", R.mipmap.war));
        genres.add(new Genre("crime", R.mipmap.crime));
        genres.add(new Genre("music", R.mipmap.music));
        genres.add(new Genre("mystery", R.mipmap.mystery));
        genres.add(new Genre("romance", R.mipmap.romance));
        genres.add(new Genre("sci-fi ", R.mipmap.sci_fi));
        genres.add(new Genre("horror", R.mipmap.horror));
        genres.add(new Genre("thriller", R.mipmap.thriller));
        genres.add(new Genre("western", R.mipmap.western));
        genres.add(new Genre("adventure", R.mipmap.adventure));

        GenreAdapter adapter = new GenreAdapter(this, genres);


        rvGenres.setAdapter(adapter);
        rvGenres.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }


    private void setBottomNavView(){

        bottomNavigationView.setSelectedItemId(R.id.discover);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:

                        //Toast.makeText(HomeActivity.this, "You are already in home", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(DiscoverActivity.this, HomeActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.search:
                        //Toast.makeText(HomeActivity.this, "we would go to search page ", Toast.LENGTH_SHORT).show();
                        Intent intent1 = new Intent(DiscoverActivity.this, SearchActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.discover:
                        //Toast.makeText(HomeActivity.this, "we would go to discover page ", Toast.LENGTH_SHORT).show();

                        break;
                    case R.id.more:
                        Toast.makeText(DiscoverActivity.this, "we would go to more page", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });
    }
}