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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.androidprojtest1.CommunityActivity;
import com.example.androidprojtest1.MainActivity;
import com.example.androidprojtest1.MyDatabaseHelper;
import com.example.androidprojtest1.R;
import com.example.androidprojtest1.model.CommunityItemDTO;
import com.example.androidprojtest1.model.CommunityItemLayout;

import java.util.ArrayList;


public class NewFeedFragment extends Fragment {

    Context context;
    EditText newTitle;
    EditText newMainText;
    ImageButton btnImgAdd;
    Button btnNewFeedInsert;
    Button btnNewFeedCancel;

    MyDatabaseHelper myHelper;
    SQLiteDatabase sqlDB;

    CommunityActivity comAc;
    public NewFeedFragment(){

    }

    public NewFeedFragment(Context context){
        this.context = context;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        myHelper = new MyDatabaseHelper(context);

        View view = inflater.inflate(R.layout.fragment_new_feed, container, false);
        newTitle = (EditText) view.findViewById(R.id.newTitle);
        newMainText = (EditText) view.findViewById(R.id.newMainText);
        btnImgAdd = (ImageButton) view.findViewById(R.id.btnImgAdd);
        btnNewFeedInsert = (Button) view.findViewById(R.id.btnNewFeedInsert);
        btnNewFeedCancel = (Button) view.findViewById(R.id.btnNewFeedCancel);


        comAc = (CommunityActivity) getActivity();

        btnNewFeedInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String query = "insert into communityItem (user_id, title, main_text) values('user6','"+newTitle.getText().toString()
                        +"','"+newMainText.getText().toString()+"')";

                sqlDB = myHelper.getWritableDatabase();
                sqlDB.execSQL(query);
                sqlDB.close();
                Toast.makeText(context, "입력됨",Toast.LENGTH_SHORT).show();

                comAc.feedgo();
            }


        });

        return view;
    }
}