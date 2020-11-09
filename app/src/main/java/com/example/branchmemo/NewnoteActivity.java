package com.example.branchmemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.icu.util.Calendar;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Date;

public class NewnoteActivity extends AppCompatActivity {
    Toolbar toolbar;
    public static ActionBar actionBar;
    EditText memoTitle, titleTxt, contentTxt;
    Button btn_toSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newnote);

        toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);//기본 제목
        actionBar.setDisplayHomeAsUpEnabled(true); //툴바의 뒤로가기 버튼

        memoTitle = findViewById(R.id.Totaltitle);
        titleTxt = findViewById(R.id.TitleView);
        contentTxt = findViewById(R.id.ContentView);
        btn_toSave = findViewById(R.id.saveBtn);

        btn_toSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

                    MemoVo memo = new MemoVo(code, title, content, date);
                    MemoListVo memolist = new MemoListVo(memo.getCode(), memoTitle.getText().toString(), memo.getDateval());

                    insertData(memo, memolist);

                    startActivity(new Intent(NewnoteActivity.this, MainActivity.class));
                    finish();
                }//end of if
            }//end of onClick
        }); //end of onClickListener
    }//end of onCreate

    private void insertData(MemoVo memo, MemoListVo memolist) {
        MainActivity.DBModel.insertMemo(memo);
        MainActivity.DBModel.insertMemoList(memolist);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {//toolbar의 back키 눌렀을 때 동작
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}