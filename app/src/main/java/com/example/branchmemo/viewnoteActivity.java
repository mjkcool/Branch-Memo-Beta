package com.example.branchmemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;

import java.util.List;

public class viewnoteActivity extends AppCompatActivity {
    Toolbar toolbar;
    public static ActionBar actionBar;
    private RecyclerView rv;
    String memoCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewnote);

        toolbar = findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);//기본 제목
        actionBar.setDisplayHomeAsUpEnabled(true); //툴바의 뒤로가기 버튼


        Intent memoIntent = getIntent();
        memoCode = memoIntent.getStringExtra("code");


        rv = findViewById(R.id.rec);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        getData();

    }//end of onCreate

    private void getData() {
        class GetData extends AsyncTask<Void, Void, List<MemoVo>> {
            @Override
            protected List<MemoVo> doInBackground(Void... voids) {
                List<MemoVo> memo_lists
                        = (List<MemoVo>) MainActivity.memoDatabase.memeDao().getAll(memoCode);
                return memo_lists;
            }

            @Override
            protected void onPostExecute(List<MemoVo> memoVo) {
                MemoAdapter adapter = new MemoAdapter(memoVo);
                rv.setAdapter(adapter);
                super.onPostExecute(memoVo);
            }
        }
        GetData gd = new GetData();
        gd.execute();
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