package com.example.branchmemo;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.List;

public class MainViewModel extends AndroidViewModel {
    private DBRepository repository;
    public MainViewModel(@NonNull Application application) {
        super(application);
        repository = new DBRepository(application);
    }

    public void insertMemo(MemoVo memo){
        repository.insertMemo(memo);
    }

    public void deleteMemo(String code){
        repository.deleteMemo(code);
    }

    public List<MemoVo> getMemo(String code){
        return repository.getAllMemo(code);
    }

    public void insertMemoList(MemoListVo memoList){
        repository.insertMemoList(memoList);
    }

    public List<MemoListVo> getMemoList(){
        return repository.getAllMemoList();
    }

    public void deleteMemoList(String code){
        repository.deleteMemoList(code);
    }


}
