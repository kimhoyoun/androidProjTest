package com.example.androidprojtest1.fragment;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidprojtest1.InsertActivity;
import com.example.androidprojtest1.MyDatabaseHelper;
import com.example.androidprojtest1.R;
import com.example.androidprojtest1.model.CommunityItemDTO;
import com.example.androidprojtest1.FeedItemAdapter;

import java.util.ArrayList;

public class FeedFragment extends Fragment {
    Context context;

    ImageButton btnNewFeed;

    ArrayList<CommunityItemDTO> dtoList = new ArrayList<>();

    MyDatabaseHelper myHelper;
    SQLiteDatabase sqlDB;

    RecyclerView recyclerView;
    FeedItemAdapter adapter;

    public FeedFragment(){

    }

    public FeedFragment(Context context){
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        myHelper = new MyDatabaseHelper(context);

        View view = inflater.inflate(R.layout.fragment_feed, container, false);

        recyclerView = (RecyclerView)view.findViewById(R.id.feed_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
        btnNewFeed = (ImageButton)view.findViewById(R.id.btnNewFeed);

        dtoList = scrollViewInit();

        adapter = new FeedItemAdapter(dtoList);

        recyclerView.setAdapter(adapter);

        btnNewFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), InsertActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }


    public ArrayList<CommunityItemDTO> scrollViewInit(){
        CommunityItemDTO dto;
        ArrayList <CommunityItemDTO> list = new ArrayList<>();
        sqlDB = myHelper.getReadableDatabase();

        try{
            if(sqlDB != null){

                String query = getString(R.string.selectAllQuery);
                Cursor cursor = sqlDB.rawQuery(query,null);
                int count = cursor.getCount();

                for(int i =0; i<count; i++){
                    cursor.moveToNext();
                    int no = cursor.getInt(0);
                    String userID = cursor.getString(1);
                    String title = cursor.getString(2);
                    String mainText = cursor.getString(3);
                    int likeNum = cursor.getInt(4);
                    int cName = cursor.getInt(5);
                    String date = cursor.getString(6);

                    dto = new CommunityItemDTO(no, userID, title, mainText, likeNum, cName, date);

                    list.add(dto);
                }
            } else{
                android.util.Log.i("결과", "실패");
            }
        } catch(Exception e){
            e.printStackTrace();
        }

        return list;
    }

}