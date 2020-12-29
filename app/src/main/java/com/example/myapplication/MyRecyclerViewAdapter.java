package com.example.myapplication;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.retrofit.models.PhotoJson;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;


import java.util.List;



public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    private List<PhotoJson> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    MyRecyclerViewAdapter(Activity context, List<PhotoJson> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_photo, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PhotoJson item = mData.get(position);

        Transformation transformation = new RoundedTransformationBuilder()
                //.borderColor(Color.BLACK)
                //.borderWidthDp(3)
                .cornerRadiusDp(6)
                .oval(false)
                .build();


        Picasso.get().load(item.getUrls().getSmall())
                .transform(transformation)
                .into(holder.myImage);

        Picasso.get().load(item.getUser().getProfile_image().getMedium())
                .transform(transformation)
                .into(holder.imageViewUser);

        //holder.myTextView.setText(animal);
        holder.tvLikes.setText(item.getLikes());
        holder.tvUsername.setText(item.getUser().getUsername());
    }

    // total number of rows
    @Override
    public int getItemCount() {
        Log.v("","");
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView myImage;
        ImageView imageViewUser;
        TextView tvLikes,tvUsername;


        ViewHolder(View itemView) {
            super(itemView);
            myImage = itemView.findViewById(R.id.item_photo_iv);
            imageViewUser = itemView.findViewById(R.id.stat_2);
            tvLikes = itemView.findViewById(R.id.stat_1);
            tvUsername = itemView.findViewById(R.id.stat_detail_2);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    /*String getItem(int id) {
        return mData.get(id);
    }*/

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}