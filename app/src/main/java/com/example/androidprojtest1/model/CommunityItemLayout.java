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

public class CommunityItemLayout extends LinearLayout{
    ImageView profile;
    TextView title;
    LinearLayout profileLayout;
    LinearLayout innerLayout;
    ImageButton heart;
    ImageButton comment;
    CommunityItemDTO dto;
    TextView userid;
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
        this.dto = dto;

        setLayout();
    }

    public void setLayout(){
        profile.setImageResource(R.drawable.profile);
        userid.setText(dto.getUserID());

        params.setMargins(20,10,0,10);

        title.setText(dto.getTitle());
        title.setTextSize(15);
        title.setTextAlignment(TEXT_ALIGNMENT_CENTER);
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
}
