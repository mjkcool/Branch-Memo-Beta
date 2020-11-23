package com.example.branchmemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Date;

public class NewnoteActivity extends AppCompatActivity {
    public static Context mContext;
    Toolbar toolbar;
    public static ActionBar actionBar;
    EditText notename, titleTxt, contentTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);
        mContext = this;

        toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);//기본 제목
        actionBar.setDisplayHomeAsUpEnabled(true); //툴바의 뒤로가기 버튼

        notename = findViewById(R.id.Totaltitle);
        titleTxt = findViewById(R.id.TitleView);
        contentTxt = findViewById(R.id.ContentView);

    }//end of onCreate



    public static void insertData(EditText notename, EditText titleTxt,EditText contentTxt, Context context) {
        String code, title, content;
        Date date = new Date(System.currentTimeMillis());

        //content
        content = contentTxt.getText().toString().replace("\n", " ");
        //title
        title = titleTxt.getText().toString();
        if (content == null || content.length() == 0) { //검열
            Toast.makeText(context, "Empty", Toast.LENGTH_SHORT).show();
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

            String notenameTxt;
            if(notename.getText().toString().length() ==0 || notename.getText().toString()==null){
                notenameTxt = title;
            }else notenameTxt = notename.getText().toString();

            MemoVo memo = new MemoVo(code, title, content, date);
            MemoListVo memolist = new MemoListVo(memo.getCode(), notenameTxt, memo.getDateval());

            MainActivity.DBModel.creatNewMemo(memo, memolist); //finish
        }//end of if
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.new_note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_save:
                insertData(notename, titleTxt,  contentTxt, NewnoteActivity.mContext);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}