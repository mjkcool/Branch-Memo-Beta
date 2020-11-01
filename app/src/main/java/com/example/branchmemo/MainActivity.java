package com.example.branchmemo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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
//    public static MemoDatabase memoDatabase;
//    public static MemoListDatabase memoListDatabase;
    public static MainViewModel DBModel;

    Toolbar toolbar;
    public static ActionBar actionBar;
    private static Handler mHandler ;
    TextView Date_top_1, Date_top_2, Date_bottom;
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
//        memoDatabase = MemoDatabase.getAppDatabase(getApplicationContext());
//        memoListDatabase = MemoListDatabase.getAppDatabase(getApplicationContext());

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
            Calendar cal = Calendar.getInstance();
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

        //RoomExplorer.show(getApplicationContext(), MemoListDatabase.class, "memolist_data");
        //RoomExplorer.show(getApplicationContext(), MemoDatabase.class, "memo_data");

        rv = findViewById(R.id.MemoListRecyclerView);
        rv.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        rv.setLayoutManager(mLayoutManager);
        getData();


    }//end of onCreate

    private void getData() {
        class GetData extends AsyncTask<Void, Void, List<MemoListVo>> {
            @Override
            protected List<MemoListVo> doInBackground(Void... voids) {
                List<MemoListVo> memoList_lists = DBModel.getMemoListDao().getAll();
                return memoList_lists;
            }
            @Override
            protected void onPostExecute(List<MemoListVo> memoListVo) {
                MemoListAdapter adapter = new MemoListAdapter(memoListVo);
                rv.setAdapter(adapter);
                super.onPostExecute(memoListVo);
            }
        }
        GetData gd = new GetData();
        gd.execute();
//        Log.d("MainActivity", "메모 개수 정국: "+Integer.toString(DBModel.getMemoList().size()));
//        MemoListAdapter adapter = new MemoListAdapter(DBModel.getMemoList());
//        rv.setAdapter(adapter);
    }

    public void viewMemo(int pos) {
        Log.d("뷰메모 pos", Integer.toString(pos));
        /*AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                String code = DBModel.getMemoListDao().getAll().get(pos).getCode();
                Intent intent = new Intent(mContext, viewnoteActivity.class);
                intent.putExtra("code", code);
                startActivity(intent);
            }
        });*/
        String code = DBModel.getPosCode(pos);
        Intent intent = new Intent(mContext, viewnoteActivity.class);
        intent.putExtra("code", code);
        startActivity(intent);
    }
    public void deleteMemo(String memoCode) {
//        memoListDatabase.memoListDao().delete(memoCode);
//        memoDatabase.memeDao().delete(memoCode);
        DBModel.deleteMemo(memoCode);
        DBModel.deleteMemoList(memoCode);
    }
}