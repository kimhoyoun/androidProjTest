package com.example.androidprojtest1.fragment;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.androidprojtest1.MyDatabaseHelper;
import com.example.androidprojtest1.R;
import com.example.androidprojtest1.model.CommunityItemDTO;
import com.example.androidprojtest1.model.CommunityItemLayout;

import java.util.ArrayList;
import java.util.List;


public class SearchFragment extends Fragment {
    Context context;
    SQLiteDatabase database;
    MyDatabaseHelper myHelper;
    SQLiteDatabase sqlDB;


    LinearLayout scrollViewInLayout;
    Button btnSearch;
    ArrayList<CommunityItemLayout> itemList = new ArrayList<>();
    LinearLayout.LayoutParams itemParams = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    EditText searchText;
    TextView resultText;

    public SearchFragment( Context context){
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        scrollViewInLayout = (LinearLayout) view.findViewById(R.id.scrollViewInLayout2);
        btnSearch = (Button) view.findViewById(R.id.btnSearch);
        searchText = (EditText) view.findViewById(R.id.searchText);
        resultText = (TextView) view.findViewById(R.id.resultText);

        myHelper = new MyDatabaseHelper(context);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userid = searchText.getText().toString();
                if(!userid.equals("")){
                    ArrayList<CommunityItemLayout> resultList = new ArrayList<>();

                    itemParams.setMargins(30,30,30,30);

                    resultList = searchList(userid);

                    if(resultList.size() == 0){

                        scrollViewInLayout.removeAllViews();

                        scrollViewInLayout.addView(resultText, new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                        resultText.setGravity(Gravity.CENTER);
                        resultText.setText("검색 결과 없음");
                    }else{
                        scrollViewInLayout.removeAllViews();
                         for(int i =0; i<resultList.size(); i++){
                            scrollViewInLayout.addView(resultList.get(i), itemParams);
                        }

                    }
                }
            }
        });

        return view;
    }


    public ArrayList searchList(String userid){

        ArrayList<CommunityItemLayout> list = new ArrayList<>();
        sqlDB = myHelper.getReadableDatabase();

        if(sqlDB != null){
            String query = "select * from communityItem where user_id = "+"'"+userid+"'";

            Cursor cursor = sqlDB.rawQuery(query,null);

            int count = cursor.getCount();

            if(count != 0) {
                for (int i = 0; i < count; i++) {
                    cursor.moveToNext();
                    int no = cursor.getInt(0);
                    String userID = cursor.getString(1);
                    String title = cursor.getString(2);
                    String mainText = cursor.getString(3);
                    int likeNum = cursor.getInt(4);
                    int cName = cursor.getInt(5);
                    String date = cursor.getString(6);

                    CommunityItemDTO dto = new CommunityItemDTO(no, userID, title, mainText, likeNum, cName, date);

                    CommunityItemLayout itemLayout = new CommunityItemLayout(context, dto);
                    list.add(itemLayout);
                }
            }

            cursor.close();
            sqlDB.close();
        } else{
            android.util.Log.i("결과", "실패");
        }
        return list;
    }
}