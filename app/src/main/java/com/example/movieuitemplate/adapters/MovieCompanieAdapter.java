package com.example.movieuitemplate.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movieuitemplate.R;
import com.example.movieuitemplate.models.MovieCompanie;

import java.util.List;

public class MovieCompanieAdapter extends RecyclerView.Adapter<MovieCompanieAdapter.CompanieViewHolder> {
    Context mContext;
    List<MovieCompanie> mData;

    public MovieCompanieAdapter(Context mContext, List<MovieCompanie> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public CompanieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_comp, parent, false);
        CompanieViewHolder companieViewHolder = new CompanieViewHolder(view);
        return companieViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CompanieViewHolder holder, int position) {


        if(mData.get(position).getImg().isEmpty() || mData.get(position).getImg() == null || mData.get(position).getImg() == "null") {
            holder.companieImg.setImageResource(R.drawable.noimageavailable);

        }
        else
            Glide.with(mContext).load("https://image.tmdb.org/t/p/original" + mData.get(position).getImg()).into(holder.companieImg);

        String text = mData.get(position).getName() + " (" +mData.get(position).getCountry()+")";
        holder.companieName.setText(text);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class CompanieViewHolder extends RecyclerView.ViewHolder {

        ImageView companieImg;
        TextView companieName;

        public CompanieViewHolder(@NonNull View itemView) {
            super(itemView);

            companieImg = itemView.findViewById(R.id.companieImg);
            companieName = itemView.findViewById(R.id.companieName);
        }
    }
}
