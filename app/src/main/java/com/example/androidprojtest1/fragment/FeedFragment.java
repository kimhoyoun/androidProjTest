package com.example.androidprojtest1.fragment;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.androidprojtest1.CommunityActivity;
import com.example.androidprojtest1.MyDatabaseHelper;
import com.example.androidprojtest1.R;
import com.example.androidprojtest1.model.CommunityItemDTO;
import com.example.androidprojtest1.model.CommunityItemLayout;

import java.util.ArrayList;

public class FeedFragment extends Fragment {
    Context context;

    LinearLayout scrollViewInLayout;
    ArrayList<CommunityItemLayout> itemList = new ArrayList<>();
    LinearLayout.LayoutParams itemParams = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);


    MyDatabaseHelper myHelper;
    SQLiteDatabase sqlDB;


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

        scrollViewInLayout = (LinearLayout) view.findViewById(R.id.scrollViewInLayout);
        ImageButton btnNewFeed = (ImageButton) view.findViewById(R.id.btnNewFeed);

        scrollViewInit();


        return view;
    }

    public void scrollViewInit(){
        CommunityItemDTO dto;

        sqlDB = myHelper.getReadableDatabase();


        itemParams.setMargins(30,30,30,30);
        try{
            if(sqlDB != null){
                 Cursor cursor = sqlDB.rawQuery("select * from communityItem",null);
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

                    CommunityItemLayout itemLayout = new CommunityItemLayout(context,dto);
                    itemList.add(itemLayout);
                    scrollViewInLayout.addView(itemLayout, itemParams);

                }
            } else{
                android.util.Log.i("결과", "실패");
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}