package com.example.branchmemo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
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
    public static MemoDatabase memoDatabase;
    public static MemoListDatabase memoListDatabase;

    Toolbar toolbar;
    public static ActionBar actionBar;
    private static Handler mHandler ;
    TextView Date_top_1, Date_top_2, Date_bottom;
    private RecyclerView rv;

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Database
//        memoDatabase = Room.databaseBuilder(getApplicationContext(), MemoDatabase.class, "memo-db").allowMainThreadQueries().build();
//        memoListDatabase = Room.databaseBuilder(getApplicationContext(), MemoListDatabase.class, "memolist-db").allowMainThreadQueries().build();
        memoDatabase = MemoDatabase.getAppDatabase(getApplicationContext());
        memoListDatabase = MemoListDatabase.getAppDatabase(getApplicationContext());

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
                SimpleDateFormat ap = new SimpleDateFormat("a", Locale.ENGLISH);
                SimpleDateFormat time = new SimpleDateFormat("hh:mm");
                SimpleDateFormat date = new SimpleDateFormat("yyyy/MM/dd");
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
                Intent intent = new Intent(getApplicationContext(), NewnoteActivity.class);
                startActivity(intent);
            }
        });

//        RoomExplorer.show(getApplicationContext(), MemoListDatabase.class, "memolist");
//        RoomExplorer.show(getApplicationContext(), MemoDatabase.class, "memolist");

        rv = findViewById(R.id.MemoListRecyclerView);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        getData();
    }//end of onCreate

    private void getData() {
        class GetData extends AsyncTask<Void, Void, List<MemoListVo>> {
            @Override
            protected List<MemoListVo> doInBackground(Void... voids) {
                List<MemoListVo> memoList_lists = (List<MemoListVo>) memoListDatabase.memoListDao().getData();
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
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}