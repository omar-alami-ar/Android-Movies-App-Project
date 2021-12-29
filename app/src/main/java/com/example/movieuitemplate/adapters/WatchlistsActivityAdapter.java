package com.example.movieuitemplate.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.movieuitemplate.R;
import com.example.movieuitemplate.models.WatchListItem;

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
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        WatchListItem watchlist = this.listData.get(position);
        //holder.watchlistImg =;
       // Glide.with(context).load("https://image.tmdb.org/t/p/original" +listData.get(position).getImage()).into(holder.watchlistImg);
        holder.watchlistName.setText(watchlist.getName());
        holder.watchlistCount.setText("Movies: " + watchlist.getCount());

        //int imageId = this.getMipmapResIdByName(wa.getFlagName());
        //holder.flagView.setImageResource(imageId);
        return convertView;
    }


    static class ViewHolder {
        ImageView watchlistImg;
        TextView watchlistName;
        TextView watchlistCount;
    }

}