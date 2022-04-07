package com.example.androidprojtest1;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.androidprojtest1.model.CommunityItemDTO;

public class InsertActivity extends AppCompatActivity {

    CommunityItemDTO dto;
    EditText newTitle;
    EditText newMainText;
    ImageButton btnImgAdd;
    Button btnNewFeedOK;
    Button btnNewFeedCancel;

    MyDatabaseHelper myHelper;
    SQLiteDatabase sqlDB;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_new_feed);

        Toolbar toolbar = findViewById(R.id.insertToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("InsertPage");


        newTitle = (EditText)findViewById(R.id.newTitle);
        newMainText = (EditText) findViewById(R.id.newMainText);
        btnImgAdd = (ImageButton) findViewById(R.id.btnImgAdd);
        btnNewFeedOK = (Button) findViewById(R.id.btnNewFeedInsert);
        btnNewFeedCancel = (Button) findViewById(R.id.btnNewFeedCancel);

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
                    update();
                }else{
                    insert();
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
        String query = "insert into communityItem (user_id, title, main_text) values('user6','"+newTitle.getText().toString()
                +"','"+newMainText.getText().toString()+"')";

        sqlDB = myHelper.getWritableDatabase();
        sqlDB.execSQL(query);
        sqlDB.close();
        Toast.makeText(InsertActivity.this, "입력됨",Toast.LENGTH_SHORT).show();

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
        Toast.makeText(InsertActivity.this, "수정됨",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(InsertActivity.this, DetailActivity.class);
        intent.putExtra("dto", dto);
        startActivity(intent);
        finish();
    }

}
