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
import android.util.TypedValue;
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
    String L_preTitle;
    TextView L_Date;
    Button L_Btn;
    ImageView L_branch;
    CheckBox L_chxbox;
    Date L_VosDate;

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
        MainActivity.DBModel.loadNote(memoCode); //getData
        L_preTitle = L_title.getText().toString();

        L_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title;
                String input_title = L_title.getText().toString().replace(" ", "");
                if(input_title.length()<=0 || input_title==null){
                    title = input_title;
                }else{
                    title = L_title.getText().toString();
                }
                Date date = new Date(System.currentTimeMillis());
                MemoVo memo = new MemoVo(memoCode, title, L_content.getText().toString(), date);

                String notename = L_noteName.getText().toString();
                MainActivity.DBModel.updateNote(memoCode, notename, L_VosDate, 1);

                if(L_chxbox.isChecked()){
                    MainActivity.DBModel.updateMemo(memo, notename);
                }else{
                    MainActivity.DBModel.insertMemo(memo, notename);
                }

            }
        });

    }//end of onCreate

    public void getData(final List<MemoVo> memoVos, final String Notename) {

            MemoVo last_memo = memoVos.remove(memoVos.size() - 1);
            L_VosDate = last_memo.getDateval();
            L_noteName.setText(Notename);
            L_title.setText(last_memo.getTitle());
            L_content.setText(last_memo.getContentbody());
            L_Date.setText(MainActivity.date.format(last_memo.getDateval())+" "+MainActivity.time24.format(last_memo.getDateval()));
            if(memoVos.size()>0){
                L_branch.setImageResource(R.drawable.finish);
            }else{
                L_branch.setImageResource(R.drawable.circle);
            }

            MemoAdapter adapter = new MemoAdapter(memoVos);
            rv.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.viewnote_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                MainActivity.DBModel.updateNote(memoCode, L_noteName.getText().toString(), L_VosDate, 0);
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