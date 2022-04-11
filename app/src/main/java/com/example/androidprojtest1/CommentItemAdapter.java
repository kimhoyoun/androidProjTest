package com.example.androidprojtest1;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidprojtest1.model.CommentDTO;
import com.example.androidprojtest1.model.CommunityItemDTO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CommentItemAdapter extends RecyclerView.Adapter<CommentViewHolder> {
    private ArrayList<CommentDTO> commentList;

    public CommentItemAdapter(ArrayList<CommentDTO> list){
        commentList =list;

    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.comment,parent,false);

        String str = context.getClass().toString();

        String className = str.substring(str.lastIndexOf('.')+1);

        CommentViewHolder viewHolder = new CommentViewHolder(context, view, commentList, className);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        if(getItemCount()!= 0) {
            CommentDTO dto = commentList.get(position);
            holder.commentUserID.setText(dto.getComment_user());
            holder.commentText.setText(dto.getComment_text());
            holder.commentTime.setText(dto.getDate().substring(0,16));
        }
    }

    @Override
    public int getItemCount() {
        if(commentList!= null) {
            return commentList.size();
        }else{
            return 0;
        }
    }

}

class CommentViewHolder extends RecyclerView.ViewHolder {

    public TextView commentUserID;
    public TextView commentText;
    public TextView commentTime;
    public ImageButton btnCommentMenu;

    MyDatabaseHelper myHelper;
    SQLiteDatabase sqlDB;

    String className;


    public CommentViewHolder(Context context, @NonNull View itemView, ArrayList<CommentDTO> itemList, String className) {
        super(itemView);

        commentUserID = itemView.findViewById(R.id.commentUserID);
        commentText = itemView.findViewById(R.id.commentText);
        commentTime = itemView.findViewById(R.id.commentTime);
        btnCommentMenu = itemView.findViewById(R.id.btnCommentMenu);

        myHelper = new MyDatabaseHelper(context);

        this.className = className;

        if(className.equals("CommunityActivity")) {
            btnCommentMenu.setVisibility(View.INVISIBLE);
        }else{
            btnCommentMenu.setVisibility(View.VISIBLE);
        }

        btnCommentMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                int pos = getAdapterPosition();
                if(pos != RecyclerView.NO_POSITION){

                    final PopupMenu popupMenu = new PopupMenu(context.getApplicationContext(), view);
                    ((Activity)view.getContext()).getMenuInflater().inflate(R.menu.comment_menu, popupMenu.getMenu());


                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            if(item.getItemId() == R.id.comment_update){

                                CommentDTO editDTO = itemList.get(pos);

                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setTitle("댓글 수정");
                                builder.setMessage("수정할 메세지를 입력해주세요!");
                                final EditText comment = new EditText(context);
                                comment.setText(editDTO.getComment_text());

                                builder.setView(comment);

                                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        String newText = comment.getText().toString();
                                        editDTO.setComment_text(newText);

                                        boolean result  = commentUpdate(editDTO);

                                        if(result){
                                            redirect(context);

                                        }

                                    }
                                });

                                builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast.makeText(context, "수정 취소되었습니다.",Toast.LENGTH_SHORT).show();
                                    }
                                });

                                builder.show();

                            }else if(item.getItemId() == R.id.comment_delete){

                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setTitle("삭제");
                                builder.setMessage("삭제하시겠습니까?");

                                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        boolean result;
                                        Toast.makeText(context, "삭제됨",Toast.LENGTH_SHORT).show();

                                        result = commentDelete(itemList.get(pos));

                                        if(result){
                                            redirect(context);
                                        }
                                    }
                                });

                                builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast.makeText(context, "취소되었습니다.",Toast.LENGTH_SHORT).show();
                                    }
                                });

                                builder.show();

                            }
                            return false;
                        }
                    });
                    popupMenu.show();
            }
            }
        });


        if(className.equals("CommunityActivity")) {
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = getAdapterPosition();
                if(pos != RecyclerView.NO_POSITION){


                        CommentDTO dto = itemList.get(pos);
                        CommunityItemDTO feeddto = null;
                        int feedNo = dto.getFeed_no();
                        MyDatabaseHelper myHelper = new MyDatabaseHelper(context);
                        SQLiteDatabase sqlDB = myHelper.getReadableDatabase();

                        if(sqlDB != null){
                            String query = "select * from communityItem where no = "+feedNo;
                            Cursor cursor = sqlDB.rawQuery(query, null);

                            int count = cursor.getCount();

                            if(count != 0){
                                for(int i =0; i<count; i++){
                                    cursor.moveToNext();
                                    int no = cursor.getInt(0);
                                    String userID = cursor.getString(1);
                                    String title = cursor.getString(2);
                                    String mainText = cursor.getString(3);
                                    int likeNum = cursor.getInt(4);
                                    int cName = cursor.getInt(5);
                                    String date = cursor.getString(6);
                                    feeddto = new CommunityItemDTO(no, userID, title, mainText, likeNum, cName, date);
                                }
                            }
                        }
                        if(feeddto != null) {
                            Intent intent = new Intent(context, DetailActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra("dto", feeddto);
                            context.startActivity(intent);
                        }
                    }
                }

        });
        }
    }

    public void redirect(Context context){
        Intent intent = ((Activity)context).getIntent();
        ((Activity)context).finish(); //현재 액티비티 종료 실시
        ((Activity)context).overridePendingTransition(0, 0); //효과 없애기
        ((Activity)context).startActivity(intent); //현재 액티비티 재실행 실시
        ((Activity)context).overridePendingTransition(0, 0); //효과 없애기
        Toast.makeText(context, "수정되었습니다.",Toast.LENGTH_SHORT).show();
    }

    public boolean commentDelete(CommentDTO item){
        int num = item.getNo();

        String query = "delete from comment where no = "+num;
        sqlDB = myHelper.getWritableDatabase();
        sqlDB.execSQL(query);
        sqlDB.close();

        return true;
    }

    public boolean commentUpdate(CommentDTO item){
        String query = "update comment set comment_text = '"+item.getComment_text()+"' where no ="+item.getNo();
        sqlDB = myHelper.getWritableDatabase();
        sqlDB.execSQL(query);
        sqlDB.close();

        return true;
    }


    public String dateForm(String date){
        long mNow = System.currentTimeMillis();
        Date format = null;
        String result = "방금 전";

        try {
            format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(format != null) {
            long diffSec = (mNow - format.getTime()) / 1000; //초 차이
            long diffMin = (mNow - format.getTime()) / 60000; //분 차이
            long diffHor = (mNow - format.getTime()) / 3600000; //시 차이
            long diffDays = (mNow / (24 * 60 * 60)/1000) - (format.getTime()/(24*60*60)/1000);

            if(diffDays != 0){
                result = diffDays+"일 전";
                return result;
            }

            if(diffDays==0 && diffHor != 0){
                result = diffHor + "시간 전";
                return result;
            }

            if(diffDays==0 && diffHor == 0 && diffMin != 0){
                result = diffMin + "분 전";
                return result;
            }
        }
        return result;
    }
}
