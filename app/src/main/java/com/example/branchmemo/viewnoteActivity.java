package com.example.branchmemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

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
                List<MemoVo> memo_lists = MainActivity.DBModel.getMemoDao().getAll(memoCode);
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
//        MemoAdapter adapter = new MemoAdapter(MainActivity.DBModel.getMemo(memoCode));
//        rv.setAdapter(adapter);
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
                FragmentManager manager=getFragmentManager();
                //(new FireMissilesDialogFragment(memoCode)).show(manager, "tag");
                //Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
                //startActivity(new Intent(viewnoteActivity.this, MainActivity.class));
                return true;

        }
        return super.onOptionsItemSelected(item);
//        if (item.getItemId() == android.R.id.home) {//toolbar의 back키 눌렀을 때 동작
//            finish();
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
    }
}

class FireMissilesDialogFragment extends DialogFragment {
    String memoCode;
    public FireMissilesDialogFragment(String code){
        this.memoCode = code;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.dialog_message)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                })
                .setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
//                        MainActivity.DBModel.deleteMemo(memoCode);
//                        MainActivity.DBModel.deleteMemoList(memoCode);
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}