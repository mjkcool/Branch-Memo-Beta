//package com.example.branchmemo;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.ActionBar;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.Toolbar;
//
//import android.annotation.SuppressLint;
//import android.content.Context;
//import android.os.Bundle;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.EditText;
//
//import java.util.List;
//
//public class ViewNoteActivity extends AppCompatActivity {
//    public static Context mContext;
//    Toolbar toolbar;
//    public static ActionBar actionBar;
//    EditText notename, titleTxt, contentTxt;
//
//    @SuppressLint("WrongViewCast")
//    @Overrides
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_view_note);
//        mContext = this;
//
//        toolbar = findViewById(R.id.toolbar_viewnote);
//        setSupportActionBar(toolbar);
//        actionBar = getSupportActionBar();
//        actionBar.setDisplayShowCustomEnabled(true);
//        actionBar.setDisplayShowTitleEnabled(false);//기본 제목
//        actionBar.setDisplayHomeAsUpEnabled(true); //툴바의 뒤로가기 버튼
//
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.view_note_menu, menu);
//        return true;
//    }
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                MainActivity.DBModel.updateNote(memoCode, L_noteName.getText().toString(), L_VosDate, 0);
//                return true;
//            case R.id.action_branch:
//
//                return true;
//            case R.id.action_save:
//                NewnoteActivity.insertData(notename, titleTxt,  contentTxt, NewnoteActivity.mContext);
//                return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
//}