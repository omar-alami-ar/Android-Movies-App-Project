package com.example.movieuitemplate.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.movieuitemplate.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MoreActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    TextView about;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_more);
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        setBottomNavView();


        about = findViewById(R.id.about);
        String text = "This application has been created by \n Aboutajedyne Moad \n Alami Aroussi Omar \nYou can Contact us at Our mails Anytime \n moad.aboutajedyne@usmba.ac.ma \n omar.alamiaroussi@usmba.ac.ma";
        about.setText(text);
    }

    private void setBottomNavView() {


        bottomNavigationView.setSelectedItemId(R.id.more);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        //Toast.makeText(HomeActivity.this, "You are already in home", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MoreActivity.this, HomeActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.search:
                        //Toast.makeText(HomeActivity.this, "we would go to search page ", Toast.LENGTH_SHORT).show();
                        Intent intent1 = new Intent(MoreActivity.this, SearchActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.discover:
                        //Toast.makeText(HomeActivity.this, "we would go to discover page ", Toast.LENGTH_SHORT).show();
                        Intent intent2 = new Intent(MoreActivity.this, DiscoverActivity.class);
                        startActivity(intent2);
                        break;
                    case R.id.more:
                        //Toast.makeText(SearchActivity.this, "we would go to more page", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });


    }
}
