package com.example.branchmemo;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.sql.Date;
import java.util.List;

public class MainViewModel extends AndroidViewModel {

    //**[IMPORTANT] Instance of DBRepository
    private DBRepository repository;

    //Constructor
    public MainViewModel(@NonNull Application application) {
        super(application);
        repository = new DBRepository(application);
    }

    //Memo
    synchronized public void insertMemo(MemoVo memo, String notename){
        repository.insertMemo(memo, notename);
    }
    synchronized public void deleteMemo(MemoVo memo) { repository.deleteMemo(memo); }
    public MemoDao getMemoDao(){ return repository.getMemoDao(); }
    synchronized public void updateMemo(MemoVo memo, String notename){
        repository.updateMemo(memo, notename);
    }

    synchronized public void insertMemoList(MemoListVo memoList){
        repository.insertMemoList(memoList);
    }
    synchronized public void deleteMemoList(String code) { repository.deleteMemoList(code); }
    public MemoListDao getMemoListDao(){ return repository.getMemoListDao(); }

    //Other functions
    synchronized public int selectCode(String code) { return repository.selectCode(code); }
    synchronized public void viewNote(int pos){  repository.viewNote(pos); }
    synchronized public void loadNote(String code){ repository.loadNote(code);}

    synchronized public void creatNewMemo(MemoVo memo, MemoListVo memolist) {
        repository.createNew(memo, memolist);
    }

    synchronized public void deleteNote(String memoCode) {
        repository.deleteNote(memoCode);
    }

    synchronized public void loadNoteList() { repository.loadNoteList(); }

    synchronized public void updateNote(String memoCode, String title, Date date, int flg) {
        repository.updateNote(memoCode, title, date, flg);
    }
}
