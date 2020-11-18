package com.example.branchmemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Date;

public class NewnoteActivity extends AppCompatActivity {
    public static Context mContext;
    Toolbar toolbar;
    public static ActionBar actionBar;
    EditText memoTitle, titleTxt, contentTxt;
    Button btn_toSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newnote);
        mContext = this;

        toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);//기본 제목
        actionBar.setDisplayHomeAsUpEnabled(true); //툴바의 뒤로가기 버튼

        memoTitle = findViewById(R.id.Totaltitle);
        titleTxt = findViewById(R.id.TitleView);
        contentTxt = findViewById(R.id.ContentView);

    }//end of onCreate



    private void insertData() {
        String code, title, content;
        Date date = new Date(System.currentTimeMillis());

        //content
        content = contentTxt.getText().toString().replace("\n", " ");
        //title
        title = titleTxt.getText().toString();
        if (content == null || content.length() == 0) { //검열
            Toast.makeText(getApplicationContext(), "Empty", Toast.LENGTH_SHORT).show();
        } else {
            if (title == null || title.length() == 0) { //대체 여부 결정
                String temp_title;
                if (content.length()>25) {
                    temp_title = content.substring(25);
                }else{
                    temp_title = content;
                }
                title = temp_title;
            }
            content = contentTxt.getText().toString();
            code = (new CodeCreater()).getNewCode();

            String memoTitleTxt;
            if(memoTitle.getText().toString().length() ==0 || memoTitle.getText().toString()==null){
                memoTitleTxt = title;
            }else memoTitleTxt = memoTitle.getText().toString();

            MemoVo memo = new MemoVo(code, title, content, date);
            MemoListVo memolist = new MemoListVo(memo.getCode(), memoTitleTxt, memo.getDateval());

            MainActivity.DBModel.creatNewMemo(memo, memolist); //finish
        }//end of if
    }

    private float display_height_to_dp(){
        float display = getResources().getDisplayMetrics().heightPixels;
        float density = getApplicationContext().getResources().getDisplayMetrics().density;
        if(density == 1.0) density*=4.0;
        else if(density == 1.5) density*=(8/3);
        else if(density == 2.0) density*=2.0;
        return display/density;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.newnote_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_save:
                insertData();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}