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
    public static Context mContext;
    Toolbar toolbar;
    public static ActionBar actionBar;
    private RecyclerView rv;
    String memoCode;
    EditText L_title, L_content, L_noteName;
    TextView L_Date;
    Button L_Btn;
    ImageView L_branch;
    CheckBox L_chxbox;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewnote);
        mContext = this;

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
        L_branch = findViewById(R.id.L_image);
        L_chxbox = findViewById(R.id.saveCheckbox);

        rv = findViewById(R.id.rec);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        MainActivity.DBModel.loadNote(memoCode);
        String pre_title = L_title.getText().toString();

        L_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = L_title.getText().toString();
//                if(L_title.getText().toString().length() ==0 || L_title.getText().toString()==null){
//                    title = null;
//                }else{
//                    title = L_title.getText().toString();
//                }
                Date date = new Date(System.currentTimeMillis());
                MemoVo memo = new MemoVo(memoCode, title, L_content.getText().toString(), date);

                if(L_chxbox.isChecked()){
                    MainActivity.DBModel.updateMemo(memo);
                }else{
                    MainActivity.DBModel.insertMemo(memo);
                }
            }
        });

    }//end of onCreate

    public void getData(final List<MemoVo> memoVos) {
        class GetData extends AsyncTask<Void, Void, List<MemoVo>> {
            @Override
            protected List<MemoVo> doInBackground(Void... voids) {
                MemoVo last_memo = memoVos.remove(memoVos.size() - 1);
                L_noteName.setText(last_memo.getTitle());
                L_title.setText(last_memo.getTitle());
                L_content.setText(last_memo.getContentbody());
                L_Date.setText(MainActivity.date.format(last_memo.getDateval())+" "+MainActivity.time24.format(last_memo.getDateval()));
                if(memoVos.size()>0){
                    L_branch.setImageResource(R.drawable.finish);
                }else{
                    L_branch.setImageResource(R.drawable.circle);
                }
                return memoVos;
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
            .setPositiveButton(R.string.cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    //return;
                }
            })
            .setNegativeButton(R.string.delete, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    MainActivity.DBModel.deleteNote(memoCode);
                }
            });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}