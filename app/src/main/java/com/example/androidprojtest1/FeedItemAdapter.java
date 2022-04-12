package com.example.androidprojtest1;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.androidprojtest1.model.CommunityItemDTO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class FeedItemAdapter extends RecyclerView.Adapter<FeedViewHolder> {
    private ArrayList<CommunityItemDTO> itemList;

    public FeedItemAdapter(ArrayList<CommunityItemDTO> list){
        itemList =list;
    }

    @NonNull
    @Override
    public FeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.feeditem_list,parent,false);

        FeedViewHolder viewHolder = new FeedViewHolder(context, view, itemList);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FeedViewHolder holder, int position) {

        if(getItemCount()!= 0) {
            CommunityItemDTO dto = itemList.get(position);
            holder.title.setText(dto.getTitle());
            holder.userid.setText(dto.getUserID());
            holder.feedTime.setText(holder.dateForm(dto.getDate()));
        }
    }

    @Override
    public int getItemCount() {
        if(itemList!= null) {
            return itemList.size();
        }else{
            return 0;
        }
    }

}

class FeedViewHolder extends RecyclerView.ViewHolder {

    public TextView title;
    public TextView userid;
    public TextView feedTime;


    public FeedViewHolder(Context context, @NonNull View itemView, ArrayList<CommunityItemDTO> itemList) {
        super(itemView);

        title = itemView.findViewById(R.id.titleText);
        userid = itemView.findViewById(R.id.useridText);
        feedTime = itemView.findViewById(R.id.dateText);

        title.setText("hello");
        userid.setText("userid");

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = getAdapterPosition();
                if(pos != RecyclerView.NO_POSITION){

                    Intent intent = new Intent(context, DetailActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("dto",itemList.get(pos));
                    context.startActivity(intent);
                }
            }
        });
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
