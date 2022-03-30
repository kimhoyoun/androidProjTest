package com.example.androidprojtest1;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.androidprojtest1.fragment.FeedFragment;
import com.example.androidprojtest1.fragment.MyPageFragment;
import com.example.androidprojtest1.fragment.NewFeedFragment;
import com.example.androidprojtest1.fragment.SearchFragment;
import com.example.androidprojtest1.fragment.VPAdapter;
import com.example.androidprojtest1.model.CommunityItemDTO;
import com.example.androidprojtest1.model.CommunityItemLayout;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class CommunityActivity extends AppCompatActivity{

    ViewPager viewPage;
    SQLiteDatabase database;

    Fragment fragment0, fragment1, fragment2, fragment3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.community);
        getSupportActionBar().hide();

        fragment0 = new FeedFragment(getDatabase(), CommunityActivity.this);

        getSupportFragmentManager().beginTransaction().add(R.id.frame, fragment0).commit();

        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.setTabGravity(tabs.GRAVITY_FILL);

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();

                Fragment selected = null;
                if(position == 0){
                    fragment0 = new FeedFragment(getDatabase(), CommunityActivity.this);
                    selected = fragment0;
                }else if(position == 1){
                    fragment1 = new SearchFragment();

                    selected = fragment1;
                }else if(position == 2){
                    fragment2 = new NewFeedFragment();
                    selected = fragment2;
                }else if(position == 3){
                    fragment3 = new MyPageFragment();
                    selected = fragment3;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.frame, selected).commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }


    public SQLiteDatabase getDatabase(){
        try{
            SQLiteDatabase database = openOrCreateDatabase("projectDB.db", Context.MODE_PRIVATE,null);
            return database;
        } catch(Exception e){
            e.printStackTrace();
        }

        return null;
    }
}


