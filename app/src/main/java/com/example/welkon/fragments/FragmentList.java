package com.example.welkon.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.near.ru.armysensor.Adapters.ArmyAdapter;
import com.near.ru.armysensor.R;
import com.near.ru.armysensor.Utils.MainDBHelper;
import com.near.ru.armysensor.interfaces.FragmentCommunication;
import com.near.ru.armysensor.models.Army;

import java.util.List;

import static com.near.ru.armysensor.MainActivity.KEY_FOR_TEXT_FROM_BUTTON;

public class FragmentList extends Fragment {
    private RecyclerView mArmyRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private MainDBHelper dbHelper;
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
        populaterecyclerView(fIntent);

        return view;
    }
    private void populaterecyclerView(String fromIntent){
        dbHelper = new MainDBHelper(getActivity());

        try {
            dbHelper.checkAndCopyDatabase();
            dbHelper.openDatabase();
        }catch (SQLiteException e){
            e.printStackTrace();
        }
        mainList = dbHelper.mainList(fromIntent);
        adapter = new ArmyAdapter(mainList, getActivity(), mArmyRecyclerView);
        mArmyRecyclerView.setAdapter(adapter);
    }
    /*
    public interface onSomeEventListener{
        public void someEvent(int uuid);
    }
    onSomeEventListener someEventListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity a;
        if(context instanceof Activity){
            a = (Activity) context;
            try {
                someEventListener = (onSomeEventListener) a;
            } catch (ClassCastException e){
                throw new ClassCastException(a.toString()+" must implement onSomeEventListener");
            }
        }
    }
    */

    FragmentCommunication communication = new FragmentCommunication() {
        @Override
        public void respond(int position, String UUID) {

        }
    };
}
