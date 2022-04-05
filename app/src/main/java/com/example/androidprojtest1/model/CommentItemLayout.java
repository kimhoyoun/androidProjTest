package com.example.androidprojtest1.model;

import android.content.Context;
import android.view.Gravity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.androidprojtest1.R;

public class CommentItemLayout extends LinearLayout {

    CommentDTO dto;
    CommunityItemDTO cdto;

    ImageView profile;
    TextView comment_userID;
    LinearLayout profileLayout;

    TextView comment_text;

    LinearLayout btnLayout;
    Button btnCommentUpdate;
    Button btnCommentDelete;

    LinearLayout.LayoutParams profileLayoutParams = new LinearLayout.LayoutParams(
            LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

    LinearLayout.LayoutParams btnLayoutParams = new LinearLayout.LayoutParams(
            LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);


    public CommentItemLayout(Context context, CommentDTO dto, CommunityItemDTO cdto ){
        super(context);
        this.dto = dto;
        this.cdto = cdto;

        profile = new ImageView(context);
        comment_userID = new TextView(context);
        profileLayout = new LinearLayout(context);
        comment_text = new TextView(context);
        btnCommentUpdate = new Button(context);
        btnCommentDelete = new Button(context);
        btnLayout = new LinearLayout(context);


        setLayout();
    }


    public void setLayout(){
        profile.setImageResource(R.drawable.profile);
        comment_userID.setText(dto.getComment_user());

        comment_text.setText(dto.getComment_text());

        this.setOrientation(LinearLayout.VERTICAL);
        profileLayout.addView(profile,new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        profileLayout.addView(comment_userID, new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        profileLayout.setGravity(Gravity.CENTER);

        this.addView(profileLayout, new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

        this.addView(comment_text, new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

        comment_text.setTextAlignment(TEXT_ALIGNMENT_CENTER);
        android.util.Log.i("결과", "feed_user : "+ dto.getComment_user());
        android.util.Log.i("결과", "userID : "+cdto.getUserID());


        // 로그인한 userid와 댓글을 작성한 userID가 일치하면 수정&삭제 버튼 보이기
        // cdto.getUserID -> 로그인 userID로 수정 예정
       if(dto.getComment_user().equals(cdto.getUserID())){
           btnCommentUpdate.setText("수정");
           btnCommentDelete.setText("삭제");
           btnLayout.addView(btnCommentUpdate, new LinearLayout.LayoutParams(
                   LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
           btnLayout.addView(btnCommentDelete, new LinearLayout.LayoutParams(
                   LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

           btnLayout.setGravity(Gravity.RIGHT);

           this.addView(btnLayout, new LinearLayout.LayoutParams(
                   LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
       }

    }

    public CommentDTO getDto(){
        return dto;
    }
}
