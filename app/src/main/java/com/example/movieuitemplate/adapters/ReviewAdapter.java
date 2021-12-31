package com.example.movieuitemplate.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.movieuitemplate.R;
import com.example.movieuitemplate.models.Review;
import com.example.movieuitemplate.models.WatchListItem;

import java.util.List;

public class ReviewAdapter extends BaseAdapter {
    private List<Review> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    public ReviewAdapter() {
    }

    public ReviewAdapter(Context aContext, List<Review> listData) {
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
        ReviewAdapter.ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.review_item, null);
            holder = new ReviewAdapter.ViewHolder();
            //holder.watchlistImg = (ImageView) convertView.findViewById(R.id.watchlistImg);
            holder.userName = (TextView) convertView.findViewById(R.id.userName);
            holder.reviewText = (TextView) convertView.findViewById(R.id.reviewText);
            convertView.setTag(holder);
        } else {
            holder = (ReviewAdapter.ViewHolder) convertView.getTag();
        }

        Review review = this.listData.get(position);
        //holder.watchlistImg =;
        // Glide.with(context).load("https://image.tmdb.org/t/p/original" +listData.get(position).getImage()).into(holder.watchlistImg);
        holder.userName.setText(review.getUsername());
        holder.reviewText.setText(review.getReviewText());

        //int imageId = this.getMipmapResIdByName(wa.getFlagName());
        //holder.flagView.setImageResource(imageId);
        return convertView;
    }


    static class ViewHolder {
        //ImageView watchlistImg;
        TextView userName;
        TextView reviewText;
    }
}
