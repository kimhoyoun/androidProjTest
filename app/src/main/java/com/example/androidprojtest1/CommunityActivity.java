package com.example.androidprojtest1;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.androidprojtest1.model.CommunityItemDTO;
import com.example.androidprojtest1.model.CommunityItemLayout;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class CommunityActivity extends AppCompatActivity{

    Fragment feedFragment;
    Fragment searchFragment;
    Fragment newfeedFragment;
    Fragment mypageFragment;

    SQLiteDatabase database;
    LinearLayout scrollViewInLayout;
    ArrayList<CommunityItemLayout> itemList = new ArrayList<>();
    LinearLayout.LayoutParams itemParams = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.community);

        Button btnSearch = (Button) findViewById(R.id.btnSearch);
        scrollViewInLayout = (LinearLayout)findViewById(R.id.scrollViewInLayout);
        EditText searchText = (EditText) findViewById(R.id.searchText);
        ImageButton btnNewFeed = (ImageButton) findViewById(R.id.btnNewFeed);

        feedFragment = new Fragment();
        searchFragment = new Fragment();
        newfeedFragment = new Fragment();
        mypageFragment = new Fragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.container, feedFragment).commit();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch(item.getItemId()){
                            case R.id.menuFeed:
                                getSupportFragmentManager().beginTransaction().replace(R.id.container, feedFragment).commit();

                                return true;

                            case R.id.menuSerch:
                                getSupportFragmentManager().beginTransaction().replace(R.id.container, searchFragment).commit();

                                return true;

                            case R.id.menuNew:
                                getSupportFragmentManager().beginTransaction().replace(R.id.container, newfeedFragment).commit();

                                return true;

                            case R.id.menuMyFeed:
                                getSupportFragmentManager().beginTransaction().replace(R.id.container, mypageFragment).commit();

                                return true;
                        }
                        return false;

                    }
                }
        );
//        try{
//        database = openOrCreateDatabase("projectDB.db", Context.MODE_PRIVATE,null);
//        android.util.Log.i("접속","데이터베이스 연결");
//        } catch(Exception e){
//            e.printStackTrace();
//        }
//        scrollViewInit();
//
//
//        btnSearch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String userid = searchText.getText().toString();
//                android.util.Log.i("결과",userid);
//
//                scrollViewInLayout.removeAllViews();
//                ArrayList<CommunityItemLayout> tempList = new ArrayList<>();
//                for(int i =0; i<itemList.size(); i++){
//                    if(userid.equals(itemList.get(i).getDto().getUserID())){
//                        tempList.add(itemList.get(i));
//                    }
//                }
//
//                for(int i =0; i<tempList.size(); i++){
//                    scrollViewInLayout.addView(tempList.get(i), itemParams);
//                }
//            }
//        });
//
//        btnNewFeed.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });



    }


    public void scrollViewInit(){
        CommunityItemDTO dto;


        itemParams.setMargins(30,30,30,30);
        try{
            if(database != null){
                Cursor cursor = database.rawQuery("select * from communityItem",null);

                int count = cursor.getCount();
                android.util.Log.i("결과 수","레코드 수 : "+count);

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

                    CommunityItemLayout itemLayout = new CommunityItemLayout(CommunityActivity.this,dto);
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
