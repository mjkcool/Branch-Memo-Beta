package com.example.branchmemo;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

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
    synchronized public void insertMemo(MemoVo memo){
        repository.insertMemo(memo);
    }
    synchronized public void deleteMemo(String code){
        repository.deleteMemo(code);
    }
    public MemoDao getMemoDao(){ return repository.getMemoDao(); }
    synchronized public void updateMemo(MemoVo memo){
        repository.updateMemo(memo);
    }

    synchronized public void insertMemoList(MemoListVo memoList){
        repository.insertMemoList(memoList);
    }
    synchronized public void deleteMemoList(String code) { repository.deleteMemoList(code); }
    public MemoListDao getMemoListDao(){ return repository.getMemoListDao(); }

    //Other functions
    public int selectCode(String code) { return repository.selectCode(code); }
    synchronized public void viewMemo(int pos){  repository.viewMemo(pos); }


}
