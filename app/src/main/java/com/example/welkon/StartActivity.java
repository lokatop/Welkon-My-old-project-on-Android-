package com.example.welkon;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.welkon.Adapters.GalleryAdapter;
import com.example.welkon.Utils.MainDBHelper2;
import com.example.welkon.interfaces.IRecyclerViewClickListener;
import com.example.welkon.models.Army;

import java.util.List;

import static com.example.welkon.Utils.SomeVoidsFromData.GetLinkImages;

public class StartActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private GalleryAdapter adapter;

    // В этом листе хранится
    TextView title;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);


        //---------------------------------------
        //initialize the variables
        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerView2);
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        //--------------------------------------------
        //------------------------------------
        title = (TextView)findViewById(R.id.tvStartActivity);
        //------------------------------------
    }


    public void populaterecyclerView(int id){

        title.setText(mainArmy.getTitle());
        String linksPhoto = mainArmy.getAllImage();
        List<String> photoLinks = GetLinkImages(linksPhoto);


//-----------------------------------------------------------------------------
        final IRecyclerViewClickListener listener = new IRecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
               // Intent i = new Intent(getApplicationContext(), FullScreenActivity.class);
               // i.putExtra("POSITION",position);
                startActivity(i);
            }
        };
//-----------------------------------------------------------------------------
        adapter = new GalleryAdapter(photoLinks, this, listener);
        mRecyclerView.setAdapter(adapter);

        dbHelper2.close();

    }

}
