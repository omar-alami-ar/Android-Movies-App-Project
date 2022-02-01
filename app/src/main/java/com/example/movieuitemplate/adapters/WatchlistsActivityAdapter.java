package com.example.movieuitemplate.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.movieuitemplate.R;
import com.example.movieuitemplate.models.Movie;
import com.example.movieuitemplate.models.WatchListItem;
import com.example.movieuitemplate.models.Watchlist;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class WatchlistsActivityAdapter extends BaseAdapter {

    private List<WatchListItem> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    public WatchlistsActivityAdapter() {
    }

    public WatchlistsActivityAdapter(Context aContext, List<WatchListItem> listData) {
        this.context = aContext;
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.watchlist_activity_item, null);
            holder = new ViewHolder();
            holder.watchlistImg = (ImageView) convertView.findViewById(R.id.watchlistImg);
            holder.watchlistName = (TextView) convertView.findViewById(R.id.watchlistTitle);
            holder.watchlistCount = (TextView) convertView.findViewById(R.id.watchlistCount);
           // holder.removeBtn = (ImageButton) convertView.findViewById(R.id.removeBtn);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        WatchListItem watchlist = this.listData.get(position);
        //holder.watchlistImg =;
       // Glide.with(context).load("https://image.tmdb.org/t/p/original" +listData.get(position).getImage()).into(holder.watchlistImg);
        holder.watchlistName.setText(watchlist.getName());
        holder.watchlistCount.setText("Movies: " + watchlist.getCount());
      /*  holder.removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context,watchlistId, Toast.LENGTH_SHORT).show();
                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
                //Log.e("ref", reference.toString());
                reference.child("Watchlists").child(String.valueOf(watchlist.getId())).removeValue();


                WatchListItem watchlisttoremove = listData.get(position);
                listData.remove(watchlisttoremove);
                notifyDataSetChanged();
            }
        });*/


        //int imageId = this.getMipmapResIdByName(wa.getFlagName());
        //holder.flagView.setImageResource(imageId);
        return convertView;
    }


    static class ViewHolder {
        ImageView watchlistImg;
        TextView watchlistName;
        TextView watchlistCount;
       // ImageButton removeBtn;
    }

}