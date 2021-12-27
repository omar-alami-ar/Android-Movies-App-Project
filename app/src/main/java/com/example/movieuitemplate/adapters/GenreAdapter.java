package com.example.movieuitemplate.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieuitemplate.R;
import com.example.movieuitemplate.models.Genre;
import com.example.movieuitemplate.ui.GenreMoviesActivity;

import java.util.List;

public class GenreAdapter extends RecyclerView.Adapter<GenreAdapter.GenreViewHolder> {

    private Context mContext;
    private List<Genre> mData;

    public GenreAdapter(Context mContext, List<Genre> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public GenreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_discover_genre, parent, false);
        GenreViewHolder holder = new GenreViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull GenreViewHolder holder, int position) {
        holder.genreImg.setImageResource(mData.get(position).getImg());
        holder.genreTitle.setText(mData.get(position).getTitle());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, GenreMoviesActivity.class);

                switch (holder.getAdapterPosition()) {
                    case 0:
                        intent.putExtra("genre", 28);
                        break;
                    case 1:
                        intent.putExtra("genre", 16);
                        break;
                    case 2:
                        intent.putExtra("genre", 99);
                        break;
                    case 3:
                        intent.putExtra("genre", 18);
                        break;
                    case 4:
                        intent.putExtra("genre", 10751);
                        break;
                    case 5:
                        intent.putExtra("genre", 14);
                        break;
                    case 6:
                        intent.putExtra("genre", 36);
                        break;
                    case 7:
                        intent.putExtra("genre", 35);
                        break;
                    case 8:
                        intent.putExtra("genre", 10752);
                        break;
                    case 9:
                        intent.putExtra("genre", 80);
                        break;
                    case 10:
                        intent.putExtra("genre", 10402);
                        break;
                    case 11:
                        intent.putExtra("genre", 9648);
                        break;
                    case 12:
                        intent.putExtra("genre", 10749);
                        break;
                    case 13:
                        intent.putExtra("genre", 878);
                        break;
                    case 14:
                        intent.putExtra("genre", 27);
                        break;
                    case 15:
                        intent.putExtra("genre", 53);
                        break;
                    case 16:
                        intent.putExtra("genre", 37);
                        break;
                    case 17:
                        intent.putExtra("genre", 12);
                        break;
                }
                intent.putExtra("title", mData.get(holder.getAdapterPosition()).getTitle());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class GenreViewHolder extends RecyclerView.ViewHolder{

        ImageView genreImg;
        TextView genreTitle;

        public GenreViewHolder(@NonNull View itemView) {
            super(itemView);

            genreImg = itemView.findViewById(R.id.genreImg);
            genreTitle = itemView.findViewById(R.id.genreTitle);


        }
    }
}
