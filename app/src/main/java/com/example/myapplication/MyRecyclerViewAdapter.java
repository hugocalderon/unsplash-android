package com.example.myapplication;

import android.app.Activity;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.retrofit.models.PhotoJson;
import com.google.gson.Gson;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;


import java.util.List;



public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    private List<PhotoJson> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private  Activity context;
    SharedPreferences settings;
    SharedPreferences.Editor edit;

    // data is passed into the constructor
    MyRecyclerViewAdapter(Activity context, SharedPreferences settings, List<PhotoJson> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.context = context;
        this.settings = settings;
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
        final PhotoJson item = mData.get(position);

        Transformation transformation = new RoundedTransformationBuilder()
                //.borderColor(Color.BLACK)
                //.borderWidthDp(3)
                .cornerRadiusDp(6)
                .oval(false)
                .build();

        Picasso.get().load(item.getUrls().getSmall())
                .transform(transformation)
                .into(holder.myImage);

        holder.myImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(context,R.style.myDialogStyle);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.info_image);
               //dialog.setTitle("Información");
                Button btnClose = (Button) dialog.findViewById(R.id.btn);
                ImageView imageView = dialog.findViewById(R.id.image);
                TextView textView = dialog.findViewById(R.id.info_text);

                btnClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                Picasso.get().load(item.getUrls().getRegular())
                        .transform(transformation)
                        .into(imageView);
                dialog.show();


                textView.setText(
                        item.getDescription() != null ? item.getDescription() : item.getAlt_description() + " \n\n\n\nAutor: " + item.getUser().getName() +
                        "\nLikes: " + item.getLikes() +
                        "\nInstagram: " + item.getUser().getInstagram_username()
                );



            }
        });

        Picasso.get().load(item.getUser().getProfile_image().getMedium())
                .transform(transformation)
                .into(holder.imageViewUser);

        //holder.myTextView.setText(animal);
        holder.tvLikes.setText(item.getLikes());
        holder.tvUsername.setText(item.getUser().getUsername());

        String strJson = settings.getString(item.getId(),"0");
        /*Gson gson = new Gson();
        PhotoJson photoJson = gson.fromJson(strJson, PhotoJson.class);*/
        if(strJson != null && strJson != "0"){
            holder.btnFavorite.setChecked(true);
        }

        holder.btnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //edit = settings.edit();
                SharedPreferences.Editor edit = settings.edit();
                if(holder.btnFavorite.isChecked()){
                        ///Guardar
                    edit.putString(item.getId(),new Gson().toJson(item));
                    edit.apply();
                    Toast.makeText(context, "Guardado a favoritos " , Toast.LENGTH_SHORT).show();
                }else {
                       // Borrar
                    edit.remove(item.getId());
                    edit.apply();
                }

            }
        });
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
        ToggleButton btnFavorite;


        ViewHolder(View itemView) {
            super(itemView);
            myImage = itemView.findViewById(R.id.item_photo_iv);
            imageViewUser = itemView.findViewById(R.id.stat_2);
            tvLikes = itemView.findViewById(R.id.stat_1);
            tvUsername = itemView.findViewById(R.id.stat_detail_2);
            btnFavorite = itemView.findViewById(R.id.stat_3);
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