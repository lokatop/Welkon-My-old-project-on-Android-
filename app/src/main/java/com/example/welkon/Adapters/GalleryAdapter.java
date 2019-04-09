package com.example.welkon.Adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.example.welkon.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {

    private List<String> mLinks;
    private Context mContext;
    private RecyclerView mRecyclerV;

    public GalleryAdapter(List<String> mLinks, Context mContext, RecyclerView mRecyclerV) {
        this.mLinks = mLinks;
        this.mContext = mContext;
        this.mRecyclerV = mRecyclerV;
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
        loadImageFromAsset(mLinks.get(position),holder.galleryImage, mContext);

/*
        //listen to single view layout click
        holder.galleryImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                mContext.startActivity(intent);
            }
        });
*/
    }

    @Override
    public int getItemCount() {
        return mLinks.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView galleryImage;
        public View layout;

        public ViewHolder(View itemView) {
            super(itemView);
            galleryImage= (ImageView)itemView.findViewById(R.id.allImage);
        }
    }

    public void loadImageFromAsset(String namePhoto, ImageView imageView1, Context context) {
        try {
            InputStream ims = context.getAssets().open(namePhoto+".jpg");
            Drawable d = Drawable.createFromStream(ims, null);
            imageView1.setImageDrawable(d);
        }
        catch(IOException ex) {
            return;
        }
    }


}
