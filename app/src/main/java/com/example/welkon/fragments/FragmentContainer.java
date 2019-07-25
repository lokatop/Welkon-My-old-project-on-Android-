package com.example.welkon.fragments;

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
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.welkon.Adapters.ArmyAdapter;
import com.example.welkon.Adapters.GalleryAdapter;
import com.example.welkon.R;
import com.example.welkon.Utils.DBHelper;
import com.example.welkon.Utils.MainDBHelper;
import com.example.welkon.Utils.MainDBHelper2;
import com.example.welkon.models.Army;

import java.util.ArrayList;
import java.util.List;

import static com.example.welkon.Particular.UUID_INT;

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

        adapter = new GalleryAdapter(photoLinks, getActivity(), mRecyclerView);
        mRecyclerView.setAdapter(adapter);

        dbHelper2.close();

    }

    public static List<String> GetLinkImages(String links){
        List<String> temp = new ArrayList<String>();
        char[] charLinks = links.toCharArray();
        char findСomma = 44;
        int start =0;
        for (int i = 0; i < charLinks.length; i++){
            if (charLinks[i] == findСomma || i+1 == charLinks.length){
                char[] dst;
                if(i+1 == charLinks.length){
                    dst=new char[i+1 - start];
                    links.getChars(start, i+1, dst, 0);
                }else {
                    dst=new char[i - start];
                    links.getChars(start, i, dst, 0);
                }
                links.getChars(start, i, dst, 0);
                temp.add(String.valueOf(dst));
                start = i+1;
            }
        }
        return temp;
    }

}
