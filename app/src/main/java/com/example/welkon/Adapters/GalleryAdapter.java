package com.example.welkon.Adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.example.welkon.R;
import com.example.welkon.interfaces.IRecyclerViewClickListener;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {

    private List<String> mLinks;
    private Context mContext;
    private RecyclerView mRecyclerV;
    IRecyclerViewClickListener clickListener;

    public GalleryAdapter(List<String> mLinks, Context mContext, RecyclerView mRecyclerV,IRecyclerViewClickListener clickListener) {
        this.mLinks = mLinks;
        this.mContext = mContext;
        this.mRecyclerV = mRecyclerV;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.single_item_photo,parent,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        loadImageFromData(mLinks.get(position),holder.galleryImage, mContext);

    }

    @Override
    public int getItemCount() {
        return mLinks.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public ImageView galleryImage;
        public View layout;

        public ViewHolder(View itemView) {
            super(itemView);
            galleryImage= (ImageView)itemView.findViewById(R.id.allImage);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.onClick(v,getAdapterPosition());
        }
    }

    public void loadImageFromData(String namePhoto, ImageView imageView1, Context context) {
        String path = Environment.getExternalStorageDirectory().toString();
        String imagePath = path + "/AudioArmy/PhotoForDB/"+namePhoto+".jpg";
        imageView1.setImageURI(Uri.parse(imagePath));
    }


}
