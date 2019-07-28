package com.example.welkon.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.welkon.R;
import com.example.welkon.interfaces.IRecyclerViewClickListener;

import java.util.List;

import static com.example.welkon.Utils.SomeVoidsFromData.loadImageFromData;

public class StartAdapter extends RecyclerView.Adapter<StartAdapter.ViewHolder> {


    private List<String> mLinks;
    private Context mContext;
    IRecyclerViewClickListener clickListener;

    public StartAdapter(List<String> mLinks, Context mContext,IRecyclerViewClickListener clickListener){
        this.mLinks = mLinks;
        this.mContext = mContext;
        this.clickListener = clickListener;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View v = inflater.inflate(R.layout.start_item,viewGroup,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        loadImageFromData(mLinks.get(position),holder.galleryImage);
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
}
