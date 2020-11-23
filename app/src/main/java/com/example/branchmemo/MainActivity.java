package com.example.branchmemo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    public static Context mContext;
    public static MainViewModel DBModel;

    Toolbar toolbar;
    public static ActionBar actionBar;
    private static Handler mHandler ;
    TextView Date_top_1, Date_top_2, Date_bottom, NoneText;
    private RecyclerView rv;

    static SimpleDateFormat ap = new SimpleDateFormat("a", Locale.ENGLISH);
    static SimpleDateFormat time = new SimpleDateFormat("hh:mm");
    static SimpleDateFormat time24 = new SimpleDateFormat("HH:mm");
    static SimpleDateFormat date = new SimpleDateFormat("yyyy/MM/dd");

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;

        DBModel = new MainViewModel(getApplication());

        //bar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);//기본 제목을 없애줍니다.
        actionBar.setDisplayHomeAsUpEnabled(false); //툴바의 뒤로가기 버튼

        //Clock
        Date_top_1 = findViewById(R.id.a_view);
        Date_top_2 = findViewById(R.id.time_view);
        Date_bottom = findViewById(R.id.date_view);
        NoneText = findViewById(R.id.NoneText);
        NoneText.setVisibility(View.GONE);

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                Calendar cal = Calendar.getInstance();
                Date_top_1.setText(ap.format(cal.getTime()));
                Date_top_2.setText(time.format(cal.getTime()));
                Date_bottom.setText(date.format(cal.getTime()));
            }
        };

        class NewRunnable implements Runnable {
            @Override
            public void run() {
                while (true) {
                    mHandler.sendEmptyMessage(0) ;
                    try {
                        Thread.sleep(1000) ;
                    } catch (Exception e) {
                        e.printStackTrace() ;
                    }
                }
            }
        }
        NewRunnable nr = new NewRunnable() ;
        Thread t = new Thread(nr) ;
        t.start() ;

        findViewById(R.id.newBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            startActivity(new Intent(mContext, NewnoteActivity.class));
            }
        });

        rv = findViewById(R.id.MemoListRecyclerView);
        rv.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        rv.setLayoutManager(mLayoutManager);
        MainActivity.DBModel.loadNoteList(); //getData

    }//end of onCreate

    public void getData(final List<MemoListVo> memoListVos) {
        MemoListAdapter adapter = new MemoListAdapter(memoListVos);
        rv.setAdapter(adapter);
        if(adapter.getItemCount()==0){
            NoneText.setVisibility(View.VISIBLE);
        }else NoneText.setVisibility(View.GONE);
    }

    public static int DPtoPX(Context context, float dp){
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
        return px;
    }
}