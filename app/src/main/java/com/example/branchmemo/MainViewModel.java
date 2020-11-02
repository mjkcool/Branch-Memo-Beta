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
    public void insertMemo(MemoVo memo){
        repository.insertMemo(memo);
    }
    public void deleteMemo(String code){
        repository.deleteMemo(code);
    }
    public MemoDao getMemoDao(){ return repository.getMemoDao(); }

    //MemoList
    public List<MemoListVo> getMemoList(){
        return repository.getAllMemoList();
    }
    public void insertMemoList(MemoListVo memoList){
        repository.insertMemoList(memoList);
    }
    public void deleteMemoList(String code) { repository.deleteMemoList(code); }
    public MemoListDao getMemoListDao(){ return repository.getMemoListDao(); }

    //Other functions
    public int selectCode(String code) { return repository.selectCode(code); }
    public void viewMemo(int pos){  repository.viewMemo(pos); }


}
