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
import com.example.movieuitemplate.models.Cast;

import java.util.List;

public class CastAdapter extends RecyclerView.Adapter<CastAdapter.CastViewHolder> {

    Context mContext;
    List<Cast> mData;

    public CastAdapter(Context context, List<Cast> mData) {
        this.mContext = context;
        this.mData = mData;

    }

    @NonNull
    @Override
    public CastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_cast, parent, false);
        CastViewHolder castViewHolder = new CastViewHolder(view);
        return castViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CastViewHolder holder, int position) {

        Glide.with(mContext).load("https://image.tmdb.org/t/p/original" + mData.get(position).getImgLink()).into(holder.image);
        //holder.image.setImageResource(mData.get(position).getImgLink());
        holder.castName.setText(mData.get(position).getName());
        holder.characterName.setText(mData.get(position).getCharacter());


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class CastViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView castName;
        TextView characterName;
         public CastViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.imgCast);
            castName = itemView.findViewById(R.id.castName);
            characterName = itemView.findViewById(R.id.characterName);

        }
    }
}
