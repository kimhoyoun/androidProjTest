package com.example.androidprojtest1.fragment;

import static android.content.Context.INPUT_METHOD_SERVICE;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.androidprojtest1.MyDatabaseHelper;
import com.example.androidprojtest1.R;
import com.example.androidprojtest1.model.CommentDTO;
import com.example.androidprojtest1.model.CommentItemLayout;
import com.example.androidprojtest1.model.CommunityItemDTO;
import com.example.androidprojtest1.model.CommunityItemLayout;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

public class FeedFragment extends Fragment {
    Context context;
    LinearLayout scrollViewInLayout;
    LinearLayout feedLayout;
    FrameLayout detailFrame;
    FrameLayout feedFrame;

    ImageButton btnNewFeed;

    ArrayList<CommunityItemLayout> itemList = new ArrayList<>();
    LinearLayout.LayoutParams itemParams = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);


    MyDatabaseHelper myHelper;
    SQLiteDatabase sqlDB;

    Button btnPrev;
    LinearLayout detailLayout;
    View detailView;

    InputMethodManager imm;

    // detail page view

    LinearLayout detailMyLayout;
    Button btndetailPrev;
    Button btnNewComment;
    Button btnCommentImg;
    EditText newCommnetText;
    LinearLayout commentLayout;
    Button btnFeedUpdate;
    Button btnFeedDelete;
    Button btndetailMenu;

    // newFeedView
    View newFeedView;
    Button btnNewFeedInsert;
    Button btnNewFeedCancel;
    EditText newTitle;
    EditText newMainText;
    ImageButton btnImgAdd;


    ArrayList<LinearLayout> commentList;

    int feedNo = -1;

    public FeedFragment(){

    }

    public FeedFragment(Context context){
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        myHelper = new MyDatabaseHelper(context);
        imm = (InputMethodManager) context.getSystemService(INPUT_METHOD_SERVICE);

        View view = inflater.inflate(R.layout.fragment_feed, container, false);


        scrollViewInLayout = (LinearLayout) view.findViewById(R.id.scrollViewInLayout);
//        ImageButton btnNewFeed = (ImageButton) view.findViewById(R.id.btnNewFeed);
        feedLayout = (LinearLayout)view.findViewById(R.id.feedLayout);
        btnNewFeed = (ImageButton)view.findViewById(R.id.btnNewFeed);

        scrollViewInit();

        feedFrame = (FrameLayout) view.findViewById(R.id.feedFrame);
        detailFrame = (FrameLayout) view.findViewById(R.id.detailFrame);
        detailLayout = (LinearLayout) view.findViewById(R.id.detailLayout);



        // detailView init
        detailView = getMyLayout(inflater, container);

        btnFeedUpdate = (Button) detailView.findViewById(R.id.btnFeedUpdate);
        btnFeedDelete = (Button) detailView.findViewById(R.id.btnFeedDelete);
        detailMyLayout = (LinearLayout) detailView.findViewById(R.id.detailMyLayout);
        btndetailPrev = (Button) detailView.findViewById(R.id.btndetailPrev);
        btnNewComment = (Button) detailView.findViewById(R.id.btnNewComment);
        btnCommentImg = (Button) detailView.findViewById(R.id.btnCommentImg);
        newCommnetText = (EditText) detailView.findViewById(R.id.newCommnetText);
        commentLayout = (LinearLayout) detailView.findViewById(R.id.commentLayout);
        btndetailMenu = (Button) detailView.findViewById(R.id.btndetailMenu);

        registerForContextMenu(btndetailMenu);

        // newFeed init
        newFeedView = inflater.inflate(R.layout.fragment_new_feed, container, false);
        btnNewFeedInsert = (Button) newFeedView.findViewById(R.id.btnNewFeedInsert);
        btnNewFeedCancel = (Button) newFeedView.findViewById(R.id.btnNewFeedCancel);
        newTitle = (EditText) newFeedView.findViewById(R.id.newTitle);
        newMainText = (EditText) newFeedView.findViewById(R.id.newMainText);
        btnImgAdd = (ImageButton) newFeedView.findViewById(R.id.btnImgAdd);


        for(int i =0; i<itemList.size(); i++) {
            final int index;
            index = i;

            itemList.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    feedNo = index;

                    TextView title = (TextView) detailView.findViewById(R.id.detalTitle);
                    TextView mainText = (TextView) detailView.findViewById(R.id.detailMainText);
                    TextView dateText = (TextView) detailView.findViewById(R.id.detailDateText);




                    title.setText(itemList.get(index).getDto().getTitle());
                    mainText.setText(itemList.get(index).getDto().getMainText());
                    dateText.setText(itemList.get(index).getDto().getDate().substring(0,10));

                    if(itemList.get(index).getDto().getUserID().equals("user1")){
                        detailMyLayout.setVisibility(View.VISIBLE);
                    }else{
                        detailMyLayout.setVisibility(View.INVISIBLE);
                    }


                    setComment(index);

                    feedFrame.setVisibility(View.INVISIBLE);

                    detailFrame.removeAllViews();

                    detailFrame.addView(detailView);
                    detailFrame.setVisibility(View.VISIBLE);






                }


            });






            btnNewComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String query = "insert into comment (comment_user, comment_text, feed_user, feed_no) values('user6','"+newCommnetText.getText().toString()
                            +"','"+itemList.get(feedNo).getDto().getUserID().toString()+"',"+itemList.get(feedNo).getDto().getNo()+")";

                    sqlDB = myHelper.getWritableDatabase();
                    sqlDB.execSQL(query);
                    sqlDB.close();

                    imm.hideSoftInputFromWindow(newCommnetText.getWindowToken(), 0);
                    newCommnetText.setText("");
                    setComment(feedNo);

                }
            });

//            btnNewFeed.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//
//                    btnPrev = (Button) newFeedView.findViewById(R.id.btnNewFeedCancel);
//
//                    feedFrame.setVisibility(View.INVISIBLE);
//
//                    detailFrame.removeAllViews();
//                    detailFrame.addView(newFeedView);
//                    detailFrame.setVisibility(view.VISIBLE);
//
//                }
//            });

            // 새 프래그먼트로 이동
            btnNewFeed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle(); // 무언가를 담을 준비를 할 수 있는 꾸러미
                    bundle.putString("fromFrag1","프래그먼트1 data");
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    NewFeedFragment fragment2 = new NewFeedFragment(context);
                    fragment2.setArguments(bundle);
                    transaction.replace(R.id.frame,fragment2);
                    transaction.commit(); // 저장
                }
            });

            btnNewFeedCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    detailFrame.setVisibility(View.INVISIBLE);
                    feedFrame.setVisibility(View.VISIBLE);
                }
            });

            btnNewFeedInsert.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String query = "insert into communityItem (user_id, title, main_text) values('user6','"+newTitle.getText().toString()
                            +"','"+newMainText.getText().toString()+"')";

                    sqlDB = myHelper.getWritableDatabase();
                    sqlDB.execSQL(query);
                    sqlDB.close();
                    Toast.makeText(context, "입력됨",Toast.LENGTH_SHORT).show();

                    fragmentRefresh();

                    detailFrame.setVisibility(View.INVISIBLE);
                    feedFrame.setVisibility(View.VISIBLE);

                }
            });




            btndetailPrev.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    detailFrame.setVisibility(View.INVISIBLE);
                    feedFrame.setVisibility(View.VISIBLE);
                }
            });



            if (btnPrev != null) {
                btnPrev.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        detailFrame.setVisibility(View.INVISIBLE);
                        feedFrame.setVisibility(View.VISIBLE);
                    }
                });
            }
        }
        return view;
    }



    public View getMyLayout(LayoutInflater inflater, ViewGroup container){
        View view;

        view = inflater.inflate(R.layout.detailpage, container, false);

        return view;
    }



    public void scrollViewInit(){
        CommunityItemDTO dto;

        sqlDB = myHelper.getReadableDatabase();


        itemParams.setMargins(10,10,10,10);
        try{
            if(sqlDB != null){

                String query = getString(R.string.selectAllQuery);
                Cursor cursor = sqlDB.rawQuery(query,null);
                int count = cursor.getCount();

                for(int i =0; i<count; i++){
                    cursor.moveToNext();
                    int no = cursor.getInt(0);
                    String userID = cursor.getString(1);
                    String title = cursor.getString(2);
                    String mainText = cursor.getString(3);
                    int likeNum = cursor.getInt(4);
                    int cName = cursor.getInt(5);
                    String date = cursor.getString(6);

                    dto = new CommunityItemDTO(no, userID, title, mainText, likeNum, cName, date);

                    CommunityItemLayout itemLayout = new CommunityItemLayout(context,dto);
                    itemList.add(itemLayout);
                    scrollViewInLayout.addView(itemLayout, itemParams);

                             }
            } else{
                android.util.Log.i("결과", "실패");
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }


//    @Override
//    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);
//    }
//
//    @Override
//    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
//        super.onCreateContextMenu(menu, v, menuInfo);
//
//        MenuInflater menuInflater = getActivity().getMenuInflater();
//
//        if (v == btndetailMenu){
//            menu.setHeaderTitle("detail menu");
//
//            menuInflater.inflate(R.menu.feedmenu, menu);
//        }
//    }

    public void fragmentRefresh(){
        FragmentTransaction ft = getFragmentManager().beginTransaction();

        ft.detach(this).attach(this).commit();

    }
    public ArrayList<CommunityItemLayout> getList(){
        return itemList;
    }

    public void setComment(int index){
        CommentDTO dto;
        commentList = new ArrayList<>();
        commentLayout.removeAllViews();
        sqlDB = myHelper.getReadableDatabase();

        try{
            if(sqlDB != null){
                Cursor cursor = sqlDB.rawQuery("select * from comment where feed_no = "+itemList.get(index).getDto().getNo(),null);
                int count = cursor.getCount();

                android.util.Log.i("결과", count+"");
                for(int i =0; i<count; i++){
                    cursor.moveToNext();
                    int no = cursor.getInt(0);
                    String comment_user = cursor.getString(1);
                    String comment_text = cursor.getString(2);
                    String feed_user = cursor.getString(3);
                    int feed_no = cursor.getInt(4);

//                                String date = cursor.getString(6);

                    dto = new CommentDTO(no, comment_user, comment_text, feed_user, feed_no);
                    android.util.Log.i("결과", dto.toString());
                    CommentItemLayout itemLayout = new CommentItemLayout(context,dto, itemList.get(index).getDto());

                    commentList.add(itemLayout);
                    android.util.Log.i("결과", commentList.size()+"");
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