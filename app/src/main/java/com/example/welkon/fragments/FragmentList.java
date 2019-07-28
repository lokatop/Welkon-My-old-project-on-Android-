package com.example.welkon.fragments;

import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.welkon.Adapters.ArmyAdapter;
import com.example.welkon.R;
import com.example.welkon.Utils.MainDBHelper2;
import com.example.welkon.models.Army;

import java.util.List;

import static com.example.welkon.MainActivity.KEY_FOR_TEXT_FROM_BUTTON;

public class FragmentList extends Fragment {
    private RecyclerView mArmyRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private MainDBHelper2 dbHelper2;
    private ArmyAdapter adapter;
    // В этом листе хранится
    public static List<Army> mainList;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list,container,false);
        mArmyRecyclerView = (RecyclerView) view .findViewById(R.id.army_recycler_view);
        mArmyRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        Intent intent = getActivity().getIntent();
        String fIntent = intent.getStringExtra(KEY_FOR_TEXT_FROM_BUTTON);
        populaterecyclerView2(fIntent);

        return view;
    }
    private void populaterecyclerView2(String fromIntent){
        dbHelper2 = new MainDBHelper2(getActivity());

        try {
            dbHelper2.checkAndCopyDatabase();
            dbHelper2.openDatabase();
        }catch (SQLiteException e){
            e.printStackTrace();
        }
        mainList = dbHelper2.mainList(fromIntent);
        adapter = new ArmyAdapter(mainList, getActivity(), mArmyRecyclerView);
        mArmyRecyclerView.setAdapter(adapter);
        dbHelper2.close();
    }
}
