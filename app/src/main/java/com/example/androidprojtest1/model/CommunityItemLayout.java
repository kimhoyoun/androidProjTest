package com.example.androidprojtest1.model;

import android.content.Context;
import android.graphics.Color;
import android.text.Layout;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.androidprojtest1.R;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CommunityItemLayout extends LinearLayout{
    ImageView profile;
    TextView title;
    LinearLayout profileLayout;
    LinearLayout innerLayout;
    ImageButton heart;
    ImageButton comment;
    CommunityItemDTO dto;
    TextView userid;
    TextView feedTime;

    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);


    public CommunityItemLayout(Context context, CommunityItemDTO dto) {
        super(context);

        profile = new ImageView(context);
        userid = new TextView(context);
        profileLayout = new LinearLayout(context);

        title = new TextView(context);
        innerLayout = new LinearLayout(context);
        heart = new ImageButton(context);
        comment = new ImageButton(context);
        feedTime = new TextView(context);

        this.dto = dto;
        this.setBackgroundColor(Color.WHITE);
        setLayout();
    }

    public void setLayout(){
        profile.setImageResource(R.drawable.profile);
        userid.setText(dto.getUserID());

        params.setMargins(20,10,0,10);

        title.setText(dto.getTitle());
        title.setTextSize(15);
        title.setTextAlignment(TEXT_ALIGNMENT_CENTER);


        feedTime.setText(dateForm(dto.getDate()));
        feedTime.setGravity(Gravity.RIGHT);

        heart.setImageResource(R.drawable.heart);
        heart.setBackgroundColor(Color.WHITE);
        comment.setImageResource(R.drawable.comment);
        comment.setBackgroundColor(Color.WHITE);

        innerLayout.setGravity(Gravity.RIGHT);


        this.setOrientation(LinearLayout.VERTICAL);
        profileLayout.addView(profile, new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        );
        profileLayout.addView(userid, params);

        this.addView(profileLayout, new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

        this.addView(title, new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));


        this.addView(feedTime, new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));


        innerLayout.addView(heart, new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        innerLayout.addView(comment, new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        this.addView(innerLayout, new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
    }

    public CommunityItemDTO getDto(){
        return dto;
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
            long diffDays = diffSec / (24 * 60 * 60);

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
