package com.example.androidprojtest1;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private FirebaseDatabase database = FirebaseDatabase.getInstance("https://androidproj-ab6fe-default-rtdb.firebaseio.com/");
    private DatabaseReference databaseReference = database.getReference();


    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference conditionRef = mRootRef.child("text");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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


        btnFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println("database : "+databaseReference.toString());

                databaseReference.setValue("Test");
                DatabaseReference connectedRef = FirebaseDatabase.getInstance().getReference(".info/connected");

                connectedRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        boolean connected = snapshot.getValue(Boolean.class);
                        if (connected) {
                            System.out.println("connect");
                        }else{
                            System.out.println("not connected");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        System.out.println("cancelled");
                    }
                });

//                databaseReference.child("item").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<DataSnapshot> task) {
//                        if(!task.isSuccessful()) {
//                            System.out.println("Error getting data : " + task.getException());
//                        }else{
//                            System.out.println("result : " + String.valueOf(task.getResult().getValue()));
//                        }
//                    }
//                });
//
//                databaseReference.child("item").setValue("ㄷㄱㄷㄴㅇㄹㅈ")
//                        .addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void unused) {
//                                System.out.println("입력완료");
//                            }
//                        })
//                        .addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                System.out.println("실패");
//                            }
//                        });
            }
        });
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        conditionRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                String text = snapshot.getValue(String.class);
//                Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(MainActivity.this, "에러", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        btnFood.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                conditionRef.setValue("hi");
//            }
//        });
//    }
}