package com.example.branchmemo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.os.Message;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    public static ActionBar actionBar;
    private static Handler mHandler ;
    TextView Date_top_1, Date_top_2, Date_bottom;

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);//기본 제목을 없애줍니다.
        actionBar.setDisplayHomeAsUpEnabled(false); //툴바의 뒤로가기 버튼

        Date_top_1 = findViewById(R.id.a_view);
        Date_top_2 = findViewById(R.id.time_view);
        Date_bottom = findViewById(R.id.date_view);

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                Calendar cal = Calendar.getInstance() ;
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}