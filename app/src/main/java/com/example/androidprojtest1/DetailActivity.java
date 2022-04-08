package com.example.androidprojtest1;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.androidprojtest1.R;
import com.example.androidprojtest1.model.CommentDTO;
import com.example.androidprojtest1.model.CommentItemLayout;
import com.example.androidprojtest1.model.CommunityItemDTO;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    CommunityItemDTO dto;

    MyDatabaseHelper myHelper;
    SQLiteDatabase sqlDB;

    LinearLayout detailMyLayout;
    InputMethodManager imm;

    Button btnNewComment;
    Button btnCommentImg;
    EditText newCommnetText;
    LinearLayout commentLayout;

    ArrayList<LinearLayout> commentList;

    LinearLayout.LayoutParams itemParams = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailpage);

        myHelper = new MyDatabaseHelper(this);

        imm = (InputMethodManager) this.getSystemService(INPUT_METHOD_SERVICE);
        Intent intent = getIntent();
        dto = (CommunityItemDTO)intent.getSerializableExtra("dto");

        btnNewComment = (Button)findViewById(R.id.btnNewComment);
        newCommnetText = (EditText)findViewById(R.id.newCommnetText);

        Toolbar toolbar = findViewById(R.id.detailToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Detail Page");

        TextView title = (TextView) findViewById(R.id.detalTitle);
        TextView mainText = (TextView) findViewById(R.id.detailMainText);
        TextView dateText = (TextView) findViewById(R.id.detailDateText);
        commentLayout = findViewById(R.id.commentLayout);

        title.setText(dto.getTitle());
        mainText.setText(dto.getMainText());
        dateText.setText(dto.getDate().substring(0,10));

        setComment();




        btnNewComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = "insert into comment (comment_user, comment_text, feed_user, feed_no) values('user6','"+newCommnetText.getText().toString()
                     +"','"+dto.getUserID().toString()+"',"+dto.getNo()+")";

                sqlDB = myHelper.getWritableDatabase();
                sqlDB.execSQL(query);
                sqlDB.close();

                // 키보드 내리기
                imm.hideSoftInputFromWindow(newCommnetText.getWindowToken(), 0);
                newCommnetText.setText("");
                // 댓글 갱신
                setComment();

                }
            });


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detailmenu, menu);

       return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            case R.id.detail_menuUpdate:
                update(dto);
                finish();
                return true;
            case R.id.detail_menuDelete:


                AlertDialog.Builder builder = new AlertDialog.Builder(DetailActivity.this);
                builder.setTitle("삭제");
                builder.setMessage("삭제하시겠습니까?");
                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        boolean result;
                        result = delete(dto);
                        Toast.makeText(DetailActivity.this, "삭제됨",Toast.LENGTH_SHORT).show();

                        if(result){
                            finish();
                        }else{

                        }

                    }
                });
                builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(DetailActivity.this, "취소되었습니다.",Toast.LENGTH_SHORT).show();
                    }
                });

                builder.create().show();

                return true;
        }

        return super.onOptionsItemSelected(item);
    }



    public void update(CommunityItemDTO item){
        Intent intent = new Intent(DetailActivity.this, InsertActivity.class);
        intent.putExtra("dto",item);
        startActivity(intent);
    }

    public boolean delete(CommunityItemDTO item){
        int no = item.getNo();

        String query = "delete from communityItem where no = "+no;

        sqlDB = myHelper.getWritableDatabase();
        sqlDB.execSQL(query);
        sqlDB.close();



        return true;
    }

    public void setComment(){
        CommentDTO commentDTO;
        commentList = new ArrayList<>();
        commentLayout.removeAllViews();
        sqlDB = myHelper.getReadableDatabase();

        try{
            if(sqlDB != null){
                Cursor cursor = sqlDB.rawQuery("select * from comment where feed_no = "+dto.getNo(),null);
                int count = cursor.getCount();

                android.util.Log.i("결과", count+"");
                for(int i =0; i<count; i++){
                    cursor.moveToNext();
                    int no = cursor.getInt(0);
                    String comment_user = cursor.getString(1);
                    String comment_text = cursor.getString(2);
                    String feed_user = cursor.getString(3);
                    int feed_no = cursor.getInt(4);

                    commentDTO = new CommentDTO(no, comment_user, comment_text, feed_user, feed_no);
                    CommentItemLayout itemLayout = new CommentItemLayout(this,commentDTO, dto);
                    commentList.add(itemLayout);
                    commentLayout.addView(itemLayout, itemParams);

                }
            } else{
                android.util.Log.i("결과", "실패");
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
