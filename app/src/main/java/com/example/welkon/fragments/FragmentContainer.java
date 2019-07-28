package com.example.welkon.fragments;

import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.welkon.Adapters.GalleryAdapter;
import com.example.welkon.FullScreenActivity;
import com.example.welkon.R;
import com.example.welkon.Utils.MainDBHelper2;
import com.example.welkon.interfaces.IRecyclerViewClickListener;
import com.example.welkon.models.Army;

import java.util.ArrayList;
import java.util.List;

import static com.example.welkon.Particular.UUID_INT;
import static com.example.welkon.Utils.SomeVoidsFromData.GetLinkImages;

public class FragmentContainer extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private MainDBHelper2 dbHelper2;
    private GalleryAdapter adapter;
    // В этом листе хранится
    public static List<Army> mainList;
    public Army mainArmy;
    TextView title;
    TextView subTitle;
    TextView description;
    //ImageView imageView;


    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_container,null);

        ScrollView scrollView = (ScrollView)v.findViewById(R.id.scrlContainer);
        //LinearLayout linearLayout = (LinearLayout) v.findViewById(R.id.LinLoy);


        Drawable myImg = ContextCompat.getDrawable(getContext().getApplicationContext(), R.drawable.embllem22);
        myImg.setAlpha(60);
        scrollView.setBackground(myImg);
        //linearLayout.setBackground(myImg);

        //---------------------------------------
        //initialize the variables
        mRecyclerView = (RecyclerView)v.findViewById(R.id.recyclerView2);
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        //--------------------------------------------

        //------------------------------------
        title = (TextView)v.findViewById(R.id.title2);
        subTitle = (TextView)v.findViewById(R.id.subtitle2);
        description = (TextView)v.findViewById(R.id.desc2);
        //------------------------------------
        if(UUID_INT != 0 ){
            populaterecyclerView(UUID_INT);
        }

        return v;
    }

    public void populaterecyclerView(int id){
        dbHelper2 = new MainDBHelper2(getActivity());

        try {
            dbHelper2.checkAndCopyDatabase();
            dbHelper2.openDatabase();
        }catch (SQLiteException e){
            e.printStackTrace();
        }
        mainArmy = dbHelper2.exMainList(id);

        title.setText(mainArmy.getTitle());
        subTitle.setText(mainArmy.getSubtitle());
        description.setText(mainArmy.getDescription());
        String linksPhoto = mainArmy.getAllImage();
        List<String> photoLinks = GetLinkImages(linksPhoto);
        final String photoLinksStr[] = photoLinks.toArray(new String[0]);
//-----------------------------------------------------------------------------
        final IRecyclerViewClickListener listener = new IRecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                //Toast.makeText(getActivity(),"click",Toast.LENGTH_SHORT).show();


                Intent i = new Intent(getActivity(), FullScreenActivity.class);
                i.putExtra("IMAGES",photoLinksStr);
                i.putExtra("POSITION",position);
                startActivity(i);
            }
        };
//-----------------------------------------------------------------------------
        adapter = new GalleryAdapter(photoLinks, getActivity(), listener);
        mRecyclerView.setAdapter(adapter);

        dbHelper2.close();

    }


}
