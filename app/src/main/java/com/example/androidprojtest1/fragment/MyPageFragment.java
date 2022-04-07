package com.example.androidprojtest1.fragment;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.androidprojtest1.CommunityActivity;
import com.example.androidprojtest1.MyDatabaseHelper;
import com.example.androidprojtest1.R;
import com.example.androidprojtest1.model.CommentDTO;
import com.example.androidprojtest1.model.CommentItemLayout;
import com.example.androidprojtest1.model.CommunityItemDTO;
import com.example.androidprojtest1.model.CommunityItemLayout;

import java.util.ArrayList;

public class MyPageFragment extends Fragment {
    Context context;

    MyDatabaseHelper myHelper;
    SQLiteDatabase sqlDB;

    TextView profileText;
    Button btnProfileFeed;
    Button btnProfileComment;
    LinearLayout profileInnerLayout;
    TextView profileTabName;

    ArrayList<CommentItemLayout> commentList;
    ArrayList<CommunityItemLayout> itemList = new ArrayList<>();
    LinearLayout.LayoutParams itemParams = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

    public MyPageFragment(Context context){
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_page, container, false);
        myHelper = new MyDatabaseHelper(context);

        profileText = (TextView) view.findViewById(R.id.profileIdText);
        btnProfileFeed = (Button) view.findViewById(R.id.btnProfileFeed);
        btnProfileComment = (Button) view.findViewById(R.id.btnProfileComment);
        profileInnerLayout = (LinearLayout) view.findViewById(R.id.profileInnerLayout);
        profileTabName = (TextView) view.findViewById(R.id.profileTabName);


        btnProfileFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profileTabName.setText("내가 쓴 게시글");
                scrollViewInit();
            }
        });

        btnProfileComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profileTabName.setText("내가 쓴 댓글");
                setComment();
            }
        });

        for(int i =0; i<itemList.size(); i++) {
            final int index;
            index = i;

            itemList.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    CommunityActivity act = (CommunityActivity)getActivity();
                    act.detailPage(itemList.get(index).getDto());
                }
            });
        }


        return view;
    }

    public void scrollViewInit(){
        CommunityItemDTO dto;
        profileInnerLayout.removeAllViews();
        sqlDB = myHelper.getReadableDatabase();


        itemParams.setMargins(30,30,30,30);
        try{
            if(sqlDB != null){
                String query = getString(R.string.myselectAllQuery);
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

                    CommunityItemLayout itemLayout = new CommunityItemLayout(context,dto);
                    itemList.add(itemLayout);
                    profileInnerLayout.addView(itemLayout, itemParams);

                }
            } else{
                android.util.Log.i("결과", "실패");
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public void setComment(){
        CommentDTO dto;
        commentList = new ArrayList<>();
        profileInnerLayout.removeAllViews();
        sqlDB = myHelper.getReadableDatabase();

        try{
            if(sqlDB != null){
                Cursor cursor = sqlDB.rawQuery("select * from comment where comment_user = 'user1'",null);
                int count = cursor.getCount();

                android.util.Log.i("결과", count+"");
                for(int i =0; i<count; i++){
                    cursor.moveToNext();
                    int no = cursor.getInt(0);
                    String comment_user = cursor.getString(1);
                    String comment_text = cursor.getString(2);
                    String feed_user = cursor.getString(3);
                    int feed_no = cursor.getInt(4);

                    dto = new CommentDTO(no, comment_user, comment_text, feed_user, feed_no);
                    CommentItemLayout itemLayout = new CommentItemLayout(context,dto, itemList.get(0).getDto());

                    commentList.add(itemLayout);
                    profileInnerLayout.addView(itemLayout, itemParams);

                }
            } else{
                android.util.Log.i("결과", "실패");
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }

}