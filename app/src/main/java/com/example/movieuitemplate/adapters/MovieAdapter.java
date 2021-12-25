package com.example.movieuitemplate.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movieuitemplate.R;
import com.example.movieuitemplate.models.Movie;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyViewHolder> {

    Context context;
    List<Movie> mData;
    MovieItemClickListener movieItemClickListener;

    public MovieAdapter(Context context, List<Movie> mData, MovieItemClickListener listener) {
        this.context = context;
        this.mData = mData;
        this.movieItemClickListener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.TvTitle.setText(mData.get(position).getTitle());
        //holder.ImgMovie.setImageResource(mData.get(position).getThumbnail());
        Glide.with(context).load("https://image.tmdb.org/t/p/original" + mData.get(position).getCoverPhoto()).into(holder.ImgMovie);

    }

    public void updateData(List<Movie> movies){
        mData = movies;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView TvTitle;
        private ImageView ImgMovie;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            TvTitle = itemView.findViewById(R.id.textMovie);
            ImgMovie = itemView.findViewById(R.id.imgMovie);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    movieItemClickListener.onMovieClick(mData.get(getAdapterPosition()), ImgMovie);

                }
            });
        }
    }
}
