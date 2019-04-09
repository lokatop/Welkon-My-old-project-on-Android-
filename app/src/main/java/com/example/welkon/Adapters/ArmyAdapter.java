package com.example.welkon.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.welkon.interfaces.FragmentCommunication;
import com.example.welkon.models.Army;
import com.example.welkon.MainActivity;
import com.example.welkon.R;
import com.example.welkon.fragments.FragmentContainer;
import com.example.welkon.interfaces.FragmentCommunication;
import com.example.welkon.models.Army;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class ArmyAdapter extends RecyclerView.Adapter<ArmyAdapter.ViewHolder> {

    public static final String FOR_UUID = "ID";

    private List<Army> mMainList;
    private Context mContext;
    private RecyclerView mRecyclerV;
    private FragmentCommunication mCommicator;


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.list_item_army, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v,mCommicator);
        return vh;
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
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        final Army mainList = mMainList.get(position);
        holder.mainTitleTxtV.setText("Title: " + mainList.getTitle());
        holder.mainSubtitleTxtV.setText("Subtitle: " + mainList.getSubtitle());

        String namePhoto = mainList.getImage();
        loadImageFromAsset(namePhoto,holder.mainImageImgV, mContext);


        FragmentContainer fragmentB=new FragmentContainer();
        Bundle bundle=new Bundle();
        String uuid = String.valueOf(mainList.getId());
        bundle.putString(FOR_UUID,uuid);
        fragmentB.setArguments(bundle);

        /*
        //listen to single view layout click
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              // int UUID = mainList.getId();

            }
        });
        */
    }

    @Override
    public int getItemCount() {
        return mMainList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        // each data item is just a string in this case
        public TextView mainTitleTxtV;
        public TextView mainSubtitleTxtV;
        public ImageView mainImageImgV;
        FragmentCommunication mComminication;

        public View layout;

        public ViewHolder(View v, FragmentCommunication Communicator) {
            super(v);
            layout = v;
            mainTitleTxtV = (TextView) v.findViewById(R.id.title);
            mainSubtitleTxtV = (TextView) v.findViewById(R.id.subTitle);
            mainImageImgV = (ImageView) v.findViewById(R.id.image);

            mComminication=Communicator;
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mComminication.respond(getAdapterPosition(), String.valueOf(mMainList.get(getAdapterPosition()).getId()));
        }
    }
    // Provide a suitable constructor (depends on the kind of dataset)
    public ArmyAdapter(List<Army> myDataset, Context context, RecyclerView recyclerView) {
        mMainList = myDataset;
        mContext = context;
        mRecyclerV = recyclerView;
    }

}
