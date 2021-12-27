package com.example.movieuitemplate.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.movieuitemplate.R;
import com.example.movieuitemplate.adapters.GenreAdapter;
import com.example.movieuitemplate.models.Genre;

import java.util.ArrayList;
import java.util.List;

public class DiscoverActivity extends AppCompatActivity {

    private RecyclerView rvGenres;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover_movies);

        init();

    }

    private void init() {
        rvGenres = findViewById(R.id.genresRV);

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
}