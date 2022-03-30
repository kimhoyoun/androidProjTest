package com.example.androidprojtest1.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.androidprojtest1.R;

public class VPAdapter extends PagerAdapter {
    private Context mContext = null;

    public VPAdapter(){}

    public VPAdapter(Context context){
        mContext = context;
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = null;

        if(mContext!=null){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if(position == 1){
            view = inflater.inflate(R.layout.fragment_feed, container, false);
            }else if(position == 2){
                view = inflater.inflate(R.layout.fragment_search, container, false);
            }else if(position ==3){
                view = inflater.inflate(R.layout.fragment_my_page, container, false);
            }


        }

        container.addView(view);


        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view == (View)object);
    }
}
