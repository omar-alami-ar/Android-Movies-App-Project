package com.example.movieuitemplate.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.example.movieuitemplate.R;
import com.example.movieuitemplate.adapters.MoviesListAdapter;
import com.example.movieuitemplate.adapters.WatchlistsActivityAdapter;
import com.example.movieuitemplate.models.Movie;
import com.example.movieuitemplate.models.WatchListItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class WatchlistDetails extends AppCompatActivity {

    TextView watchlistName;
    ListView moviesList;
    MoviesListAdapter adapter;
    FirebaseUser firebaseUser;
    DatabaseReference reference;
    ArrayList<Movie> movies = new ArrayList<Movie>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watchlist_details);

        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Manage watchlist");
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorAccent));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        watchlistName = findViewById(R.id.watchlistDetailsName);
        watchlistName.setText(getIntent().getStringExtra("name"));
        String watchlistId = getIntent().getStringExtra("id");

        moviesList = findViewById(R.id.moviesList);





        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());




        reference.child("Watchlists").child(getIntent().getStringExtra("id")).child("Movies").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot movieSnapshot: snapshot.getChildren()) {
                    Movie movie = movieSnapshot.getValue(Movie.class);
                    //Long count = watchListSnapshot.child("Movies").getChildrenCount();
                    //String image = watchListSnapshot.child("Movies").
                    //watchListItem.setCount(String.valueOf(count));
                    //watchListItem.setImage("https://image.tmdb.org/t/p/w1280//6Y9fl8tD1xtyUrOHV2MkCYTpzgi.jpg");
                    movies.add(movie);


                }
                adapter = new MoviesListAdapter(WatchlistDetails.this, movies);
                adapter.watchlistId = getIntent().getStringExtra("id");
                moviesList.setAdapter(adapter);

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

    }
}