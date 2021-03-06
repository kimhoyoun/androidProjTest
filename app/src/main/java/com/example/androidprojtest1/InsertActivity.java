package com.example.androidprojtest1;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.androidprojtest1.model.CommunityItemDTO;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class InsertActivity extends AppCompatActivity {

    CommunityItemDTO dto;
    EditText newTitle;
    EditText newMainText;
    ImageButton btnImgAdd;
    Button btnNewFeedOK;
    Button btnNewFeedCancel;

    MyDatabaseHelper myHelper;
    SQLiteDatabase sqlDB;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        Toolbar toolbar = findViewById(R.id.insertToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("InsertPage");


        newTitle = (EditText)findViewById(R.id.newTitle);
        newMainText = (EditText) findViewById(R.id.newMainText);
        btnImgAdd = (ImageButton) findViewById(R.id.btnImgAdd);

        myHelper = new MyDatabaseHelper(this);

        Intent intent = getIntent();
        if(intent.getSerializableExtra("dto")!=null){
            dto = (CommunityItemDTO) intent.getSerializableExtra("dto");

            setUpdateLayout();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.insertmenu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            case R.id.btnInsertOK:{
                if(dto != null){
                    noticeMessage("??????");

                }else{
                    noticeMessage("??????");
                }
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public void setUpdateLayout(){
        getSupportActionBar().setTitle("UpdatePage");
        newTitle.setText(dto.getTitle());
        newMainText.setText(dto.getMainText());

    }

    public void insert(){
//        String query = "insert into communityItem (user_id, title, main_text) values('user6','"+newTitle.getText().toString()
//                +"','"+newMainText.getText().toString()+"')";
//
//        sqlDB = myHelper.getWritableDatabase();
//        sqlDB.execSQL(query);
//        sqlDB.close();
//        Toast.makeText(InsertActivity.this, "?????????",Toast.LENGTH_SHORT).show();

        databaseReference = database.getReference("communityItem");
        CommunityItemDTO item = new CommunityItemDTO(1, "user2", "????????? ??????", "??????????????????.", 0, 1, "2022-04-11 10:32:30");

        databaseReference.child("item2").push().setValue(item);


        finish();
    }

    public void update(){
        dto.setTitle(newTitle.getText().toString());
        dto.setMainText(newMainText.getText().toString());

        String query = "update communityItem set title = '"+dto.getTitle()+"', main_text='"+dto.getMainText()+
                "' where no = "+dto.getNo()+" and user_id = '"+dto.getUserID()+"'";

        sqlDB = myHelper.getWritableDatabase();
        sqlDB.execSQL(query);
        sqlDB.close();
        Toast.makeText(InsertActivity.this, "?????????",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(InsertActivity.this, DetailActivity.class);
        intent.putExtra("dto", dto);
        startActivity(intent);
        finish();
    }

    public void noticeMessage(String type){
        AlertDialog.Builder builder = new AlertDialog.Builder(InsertActivity.this);
        builder.setTitle(type);
        builder.setMessage(type+"???????????????????");
        builder.setPositiveButton("??????", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

               if(type.equals("??????")){
                   update();
               }else if(type.equals("??????")){
                   insert();
               }

                Toast.makeText(InsertActivity.this, type+"???",Toast.LENGTH_SHORT).show();

            }
        });
        builder.setNegativeButton("??????", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(InsertActivity.this, "?????????????????????.",Toast.LENGTH_SHORT).show();
            }
        });

        builder.create().show();
    }
}
