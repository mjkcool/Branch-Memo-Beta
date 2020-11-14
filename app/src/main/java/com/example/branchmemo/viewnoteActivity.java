package com.example.branchmemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Date;
import java.util.List;

public class viewnoteActivity extends AppCompatActivity {
    Toolbar toolbar;
    public static ActionBar actionBar;
    private RecyclerView rv;
    String memoCode;
    EditText L_title, L_content, L_noteName;
    TextView L_Date;
    Button L_Btn;
    ImageView L_image;
    CheckBox L_chxbox;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewnote);

        Intent memoIntent = getIntent();
        memoCode = memoIntent.getStringExtra("code");

        toolbar = findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);//기본 제목
        actionBar.setDisplayHomeAsUpEnabled(true); //툴바의 뒤로가기 버튼

        L_noteName = findViewById(R.id.notename);
        L_title = findViewById(R.id.L_title);
        L_content = findViewById(R.id.L_content);
        L_Date = findViewById(R.id.L_Date);
        L_Btn = findViewById(R.id.L_savebtn);
        L_image = findViewById(R.id.L_image);
        L_chxbox = findViewById(R.id.saveCheckbox);

        rv = findViewById(R.id.rec);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        getData();

        L_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date date = new Date(System.currentTimeMillis());
                MemoVo memo = new MemoVo(memoCode, L_title.getText().toString(), L_content.getText().toString(), date);
                if(L_chxbox.isChecked()){
                    MainActivity.DBModel.updateMemo(memo);
                }else{
                    MainActivity.DBModel.insertMemo(memo);
                }
                //MainActivity.DBModel

                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });

    }//end of onCreate

    private void getData() {
        class GetData extends AsyncTask<Void, Void, List<MemoVo>> {
            @Override
            protected List<MemoVo> doInBackground(Void... voids) {
                List<MemoVo> memo_lists = MainActivity.DBModel.getMemoDao().getAll(memoCode);
                //마지막 메모는 수정이 가능하게
                MemoVo last_memo = memo_lists.remove(memo_lists.size() - 1);
                L_noteName.setText(MainActivity.DBModel.getMemoListDao().get(memoCode).getTitle());
                L_title.setText(last_memo.getTitle());
                L_content.setText(last_memo.getContentbody());
                L_Date.setText(MainActivity.date.format(last_memo.getDateval())+" "+MainActivity.time24.format(last_memo.getDateval()));
                if(memo_lists.size()>0){
                    L_image.setImageResource(R.drawable.finish);
                }else{
                    L_image.setImageResource(R.drawable.circle);
                }
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.viewnote_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_delete:
                deleteThis();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteThis(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.dialog_title).setMessage(R.string.dialog_message)
            .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    //return;
                }
            })
            .setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    MainActivity.DBModel.deleteMemo(memoCode);
                    MainActivity.DBModel.deleteMemoList(memoCode);
                    Intent close = new Intent(viewnoteActivity.this, MainActivity.class);
                    close.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(close);
                    finish();
                }
            });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}