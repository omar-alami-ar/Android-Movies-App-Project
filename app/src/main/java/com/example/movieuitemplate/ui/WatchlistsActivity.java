package com.example.movieuitemplate.ui;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.movieuitemplate.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class WatchlistsActivity extends AppCompatActivity {

    EditText name;
    Button add;


    FirebaseUser firebaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watchlists);


        add=findViewById(R.id.btnAdd);
        name = findViewById(R.id.watchlistName);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid()).child("Watchlists");
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("name",name.getText().toString());
                String random = String.valueOf((int )(Math.random() * 99999 + 1));
                String watchlistID = name.getText().toString()+random;
                hashMap.put("id",watchlistID);

                reference.child(watchlistID).setValue(hashMap);

            }
        });
    }
}