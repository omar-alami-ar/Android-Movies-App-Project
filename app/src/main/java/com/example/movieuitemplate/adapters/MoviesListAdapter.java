package com.example.movieuitemplate.adapters;

import android.app.BackgroundServiceStartNotAllowedException;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.movieuitemplate.R;
import com.example.movieuitemplate.models.Movie;
import com.example.movieuitemplate.models.WatchListItem;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class MoviesListAdapter extends BaseAdapter {
    private List<Movie> listData;
    private LayoutInflater layoutInflater;
    private Context context;
    public String watchlistId;

    public MoviesListAdapter() {
    }

    public MoviesListAdapter(Context aContext, List<Movie> listData) {
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
        MoviesListAdapter.ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.movie_item, null);
            holder = new MoviesListAdapter.ViewHolder();
            holder.movieImg = (ImageView) convertView.findViewById(R.id.movieImg);
            holder.movieTitle = (TextView) convertView.findViewById(R.id.movieTitle);
            holder.removeBtn = convertView.findViewById(R.id.removeBtn);

            convertView.setTag(holder);
        } else {
            holder = (MoviesListAdapter.ViewHolder) convertView.getTag();
        }

        Movie movie = this.listData.get(position);
        //holder.watchlistImg =;
        Glide.with(context).load("https://image.tmdb.org/t/p/original" +movie.getCoverPhoto()).into(holder.movieImg);
        //holder.movieImg.setText(watchlist.getName());
        holder.movieTitle.setText(movie.getTitle());
        holder.removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //Toast.makeText(context,watchlistId, Toast.LENGTH_SHORT).show();
               FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid())
                        .child("Watchlists").child(watchlistId);
                //Log.e("ref", reference.toString());
                reference.child("Movies").child(String.valueOf(movie.getId())).removeValue();
                notifyDataSetChanged();
            }
        });

        //int imageId = this.getMipmapResIdByName(wa.getFlagName());
        //holder.flagView.setImageResource(imageId);
        return convertView;
    }


    static class ViewHolder {
        ImageView movieImg;
        TextView movieTitle;
        ImageButton removeBtn;

    }

}
