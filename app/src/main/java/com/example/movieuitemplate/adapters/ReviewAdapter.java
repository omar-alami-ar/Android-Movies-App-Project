package com.example.movieuitemplate.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.movieuitemplate.R;
import com.example.movieuitemplate.models.Review;
import com.example.movieuitemplate.models.WatchListItem;
import com.example.movieuitemplate.ui.HomeActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

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
            holder.userImg = convertView.findViewById(R.id.userImg);
            convertView.setTag(holder);
        } else {
            holder = (ReviewAdapter.ViewHolder) convertView.getTag();
        }

        Review review = this.listData.get(position);
        //holder.watchlistImg =;
        // Glide.with(context).load("https://image.tmdb.org/t/p/original" +listData.get(position).getImage()).into(holder.watchlistImg);
        holder.userName.setText(review.getUsername());
        holder.reviewText.setText(review.getReviewText());

        
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("images/"+firebaseUser.getUid());
        try {
            File localFile = File.createTempFile(firebaseUser.getUid(),"jpg");
            storageReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(context, "Picture retrieved", Toast.LENGTH_SHORT).show();
                    Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                    holder.userImg.setImageBitmap(bitmap);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(context, "Error Occurred", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (IOException e){
            e.printStackTrace();
        }


        //int imageId = this.getMipmapResIdByName(wa.getFlagName());
        //holder.flagView.setImageResource(imageId);
        return convertView;
    }


    static class ViewHolder {
        CircleImageView userImg;
        TextView userName;
        TextView reviewText;
    }
}
