package com.example.movieuitemplate.adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.movieuitemplate.R;
import com.example.movieuitemplate.models.WatchListItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.HashMap;

public class WatchlistLVadapter extends ArrayAdapter<WatchListItem> {

    FirebaseUser firebaseUser;
    DatabaseReference reference;
    DialogInterface dialog;
    public  String movieName,movieID;
    // constructor for our list view adapter.
    public WatchlistLVadapter(@NonNull Context context, ArrayList<WatchListItem> dataModalArrayList) {
        super(context, 0, dataModalArrayList);
    }

    public WatchlistLVadapter(@NonNull Context context, ArrayList<WatchListItem> dataModalArrayList, DialogInterface dialog) {
        super(context, 0, dataModalArrayList);
        this.dialog = dialog;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // below line is use to inflate the
        // layout for our item of list view.
        View listitemView = convertView;
        if (listitemView == null) {
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.watchlist_item, parent, false);
        }

        // after inflating an item of listview item
        // we are getting data from array list inside
        // our modal class.
        WatchListItem dataModal = getItem(position);

        // initializing our UI components of list view item.
        TextView nameTV = listitemView.findViewById(R.id.tvWatchlistName);


        // after initializing our items we are
        // setting data to our view.
        // below line is use to set data to our text view.
        nameTV.setText(dataModal.getName());



        // below line is use to add item click listener
        // for our item of list view.
        listitemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on the item click on our list view.
                // we are displaying a toast message.
                firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid())
                        .child("Watchlists").child(dataModal.getId());
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("movieID","123");
                hashMap.put("movieName","Spiderman");

                reference.child("Movies").push().setValue(hashMap);


                dialog.dismiss();
                Toast.makeText(getContext(), "Item clicked is : " + dataModal.getId(), Toast.LENGTH_SHORT).show();

            }
        });
        return listitemView;
    }
}
