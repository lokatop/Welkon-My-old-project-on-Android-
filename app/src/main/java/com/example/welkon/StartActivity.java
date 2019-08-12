package com.example.welkon;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.welkon.Adapters.StartAdapter;
import com.example.welkon.interfaces.IRecyclerViewClickListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.welkon.Utils.SomeVoidsFromData.GetLinkImages;

public class StartActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private StartAdapter adapter;
    //final private String NAME_OF_SCHEME = "sheme3,sheme1,sheme2,sheme4";
    final public List<Integer> SCHEMES = new ArrayList<Integer>();
    final public List<String> SCHEMES_TEXT = new ArrayList<String>();
    final static public int[] layouts = {R.layout.activity_main,R.layout.activity_main2,R.layout.activity_main3,R.layout.activity_main4};

    // В этом листе хранится
    TextView title;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        SCHEMES.add(R.drawable.shm3);SCHEMES_TEXT.add("макет УС КП армии и мбср");
        SCHEMES.add(R.drawable.shm1);SCHEMES_TEXT.add("макет УС КП армии 1943 г.");
        SCHEMES.add(R.drawable.shm2);SCHEMES_TEXT.add("макет модульного ПППУ");
        SCHEMES.add(R.drawable.shm4);SCHEMES_TEXT.add("макет УС КНП мсб");

        //---------------------------------------
        //initialize the variables
        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerView2);
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        //--------------------------------------------
        //------------------------------------
        //title = (TextView)findViewById(R.id.tvStartActivity);
        //------------------------------------
        StartAdapter();
    }


    public void StartAdapter(){

        //List<String> photoLinks = GetLinkImages(NAME_OF_SCHEME);
        //final String photoLinksStr[] = photoLinks.toArray(new String[0]);

//-----------------------------------------------------------------------------
        final IRecyclerViewClickListener listener = new IRecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);

                i.putExtra("POSITION",position);
                i.putExtra("layout",layouts[position]);
                startActivity(i);
            }
        };
//-----------------------------------------------------------------------------
        adapter = new StartAdapter(SCHEMES,SCHEMES_TEXT, this, listener);
        mRecyclerView.setAdapter(adapter);
        //mRecyclerView.scrollToPosition(1);
    }




}
