package com.example.branchmemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewnoteActivity extends AppCompatActivity {
    Toolbar toolbar2;
    public static ActionBar actionBar;
    EditText titleTxt, contentTxt;
    private Button btn_toSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newnote);

        toolbar2 = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar2);
        actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);//기본 제목
        actionBar.setDisplayHomeAsUpEnabled(true); //툴바의 뒤로가기 버튼

        titleTxt = (EditText)findViewById(R.id.TitleView);
        contentTxt = (EditText)findViewById(R.id.ContentView);
        btn_toSave = (Button)findViewById(R.id.saveBtn);
        btn_toSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code, title, content;
                content = contentTxt.getText().toString().replace("\n", " ");
                if(content==null || content.length()==0){
                    Toast.makeText(getApplicationContext(), "Empty", Toast.LENGTH_LONG).show();
                    return;
                }
                content = contentTxt.getText().toString();
                title = titleTxt.getText().toString();
                if(content==null || content.length()==0){
                    String temp_title = content.replace("\n", " ").substring(0, 25);
                    title = temp_title;
                }

                code = (new CodeCreater()).getNewCode();
                MemoVo memo = new MemoVo(code, title, content);
                MainActivity.memoDatabase.memeDao().insert(memo);
                MemoListVo memolist = new MemoListVo(memo.code, title, memo.dateval);
                MainActivity.memoListDatabase.memoListDao().insert(memolist);

                Toast.makeText(getApplicationContext(), "Saved!", Toast.LENGTH_LONG).show();

                titleTxt.setText("");
                contentTxt.setText("");

                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}