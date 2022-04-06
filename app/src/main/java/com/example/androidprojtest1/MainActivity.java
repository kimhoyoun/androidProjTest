package com.example.androidprojtest1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 타이틀바 숨기기

        Button btnEdit = (Button) findViewById(R.id.btnEdit);
        Button btnExercise = (Button) findViewById(R.id.btnExercise);
        Button btnFood = (Button) findViewById(R.id.btnFood);
        Button btnCommunity = (Button) findViewById(R.id.btnCommunity);

        btnCommunity.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CommunityActivity.class);
                startActivity(intent);
            }
        });
    }
}