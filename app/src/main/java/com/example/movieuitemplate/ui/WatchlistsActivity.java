package com.example.movieuitemplate.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.movieuitemplate.R;
import com.example.movieuitemplate.adapters.WatchlistLVadapter;
import com.example.movieuitemplate.adapters.WatchlistsActivityAdapter;
import com.example.movieuitemplate.models.WatchListItem;
import com.example.movieuitemplate.models.Watchlist;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.CountDownLatch;

public class WatchlistsActivity extends AppCompatActivity {

    EditText name;
    Button add;

    FirebaseUser firebaseUser;
    ListView watchlistsListView;
    DatabaseReference reference;

    WatchlistsActivityAdapter adapter;

    ArrayList<WatchListItem> watchlists = new ArrayList<WatchListItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watchlists);


        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Browse Watchlists");
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorAccent));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        watchlistsListView = findViewById(R.id.watchlistsListView);

        add=findViewById(R.id.btnAdd);
        name = findViewById(R.id.watchlistName);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());




        reference.child("Watchlists").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot watchListSnapshot: snapshot.getChildren()) {
                    WatchListItem watchListItem = watchListSnapshot.getValue(WatchListItem.class);
                    Long count = watchListSnapshot.child("Movies").getChildrenCount();
                    //String image = watchListSnapshot.child("Movies").
                    watchListItem.setCount(String.valueOf(count));
                    //watchListItem.setImage("https://image.tmdb.org/t/p/w1280//6Y9fl8tD1xtyUrOHV2MkCYTpzgi.jpg");
                    watchlists.add(watchListItem);

                    //Toast.makeText(WatchlistsActivity.this, String.valueOf(watchlists.size()), Toast.LENGTH_SHORT).show();
                }
               // Toast.makeText(WatchlistsActivity.this, String.valueOf(watchlists.size()), Toast.LENGTH_SHORT).show();
                setUpWatchListView();
                //name.setText("");

//                WatchlistsActivityAdapter adapter = new WatchlistsActivityAdapter(WatchlistsActivity.this, watchlists);
//                watchlistsListView.setAdapter(adapter);
//                adapter.notifyDataSetChanged();

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });




        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(name.getText())) {
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid()).child("Watchlists");
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("name", name.getText().toString());
                    String random = String.valueOf((int) (Math.random() * 99999 + 1));
                    String watchlistID = name.getText().toString() + random;
                    hashMap.put("id", watchlistID);

                    reference.child(watchlistID).setValue(hashMap);
                    WatchListItem watchlist = new WatchListItem(watchlistID, name.getText().toString(), "0");
                    watchlists.add(watchlist);
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(WatchlistsActivity.this, "Watchlist name cannot be empty", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void setUpWatchListView(){
        adapter = new WatchlistsActivityAdapter(WatchlistsActivity.this, watchlists);
        watchlistsListView.setAdapter(adapter);
    }





}