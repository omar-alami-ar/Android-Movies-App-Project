package com.example.movieuitemplate.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.movieuitemplate.R;
import com.example.movieuitemplate.models.Movie;

import java.util.List;

public class SliderPageAdapter extends PagerAdapter {

    private Context mContext;
    private List<Movie> mList;
    private MovieItemClickListener movieItemClickListener;

    public SliderPageAdapter(Context mContext, List<Movie> mList, MovieItemClickListener movieItemClickListener) {
        this.mContext = mContext;
        this.mList = mList;
        this.movieItemClickListener = movieItemClickListener;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        //LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //LayoutInflater inflater = LayoutInflater.from(container.getContext());
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View slideLayout = inflater.inflate(R.layout.slider_item,null);

        ImageView slideImg = slideLayout.findViewById(R.id.slideImg);
        TextView slideText = slideLayout.findViewById(R.id.slideTitle);

        Glide.with(mContext).load("https://image.tmdb.org/t/p/original" +mList.get(position).getThumbnail()).into(slideImg);
        slideText.setText(mList.get(position).getTitle());

        slideLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movieItemClickListener.onMovieClick(mList.get(position),slideImg);
            }
        });

        container.addView(slideLayout);

        return slideLayout;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
