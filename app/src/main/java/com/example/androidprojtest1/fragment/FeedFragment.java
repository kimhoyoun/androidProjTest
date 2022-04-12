package com.example.androidprojtest1.fragment;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidprojtest1.InsertActivity;
import com.example.androidprojtest1.MyDatabaseHelper;
import com.example.androidprojtest1.R;
import com.example.androidprojtest1.model.CommunityItemDTO;
import com.example.androidprojtest1.FeedItemAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class FeedFragment extends Fragment {
    Context context;

    ImageButton btnNewFeed;

    ArrayList<CommunityItemDTO> dtoList;

    MyDatabaseHelper myHelper;
    SQLiteDatabase sqlDB;

    RecyclerView recyclerView;
    FeedItemAdapter adapter;

    FirebaseDatabase database;
    DatabaseReference databaseReference;

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
        dtoList = new ArrayList<>();

//        dtoList = scrollViewInit();

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("communityItem");
        System.out.println("databaseReference : "+databaseReference.toString());

        databaseReference.setValue("Hello, World");
//        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                dtoList.clear();
//                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
//                    CommunityItemDTO item = snapshot.getValue(CommunityItemDTO.class);
//                    dtoList.add(item);
//                    System.out.println("sout: "+snapshot.getValue());
//                }
//                adapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Log.e("feedFragment", String.valueOf(error.toException()));
//            }
//        });

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Map<String, String> map = (Map<String,String>)snapshot.getValue();
                System.out.println(map.toString());

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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