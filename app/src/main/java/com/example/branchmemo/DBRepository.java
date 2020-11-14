package com.example.branchmemo;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

public class DBRepository {
    //**[IMPORTANT] Instance of DAO
    private MemoDao memoDao;
    private MemoListDao memoListDao;
    //DAO Getter
    public MemoDao getMemoDao() {
        return memoDao;
    }
    public MemoListDao getMemoListDao() {
        return memoListDao;
    }

    //Constructor
    public DBRepository(Context context) {
        MemoDatabase memoDatabase = MemoDatabase.getAppDatabase(context);
        MemoListDatabase memoListDatabase = MemoListDatabase.getAppDatabase(context);
        memoDao = memoDatabase.memeDao();
        memoListDao = memoListDatabase.memoListDao();
    }

    //----------------------------------------------------------------------------------------------


    private int codeCount;//GetCodeExistAsyncTask
    private int lastId;//GetLastIdAsyncTask

    //----------------------------------------------------------------------------------------------
    //For User Methods

    public void viewMemo(int pos){
        ViewMemoAsyncTask task = new ViewMemoAsyncTask(memoListDao);
        task.execute(pos);
    }

    public void insertMemoList(MemoListVo memoList){
        InsertMemoListAsyncTask task = new InsertMemoListAsyncTask(memoListDao);
        task.execute(memoList);
    }

    public void deleteMemoList(String code){
        DeleteMemoListAsyncTask task = new DeleteMemoListAsyncTask(memoListDao);
        task.execute(code);
    }

    public void insertMemo(MemoVo memo){
        InsertMemoAsyncTask task = new InsertMemoAsyncTask(memoDao);
        task.execute(memo);
    }

    public void deleteMemo(String code){
        DeleteMemoAsyncTask task = new DeleteMemoAsyncTask(memoDao);
        task.execute(code);
    }

    public void updateMemo(MemoVo memo){
        UpdateMemoAsyncTask task = new UpdateMemoAsyncTask(memoDao);
        task.execute(memo);
    }

    public int selectCode(String code){
        GetCodeExistAsyncTask task = new GetCodeExistAsyncTask(memoListDao);
        task.repository = this;
        task.execute(code);
        return codeCount;
    }


    //----------------------------------------------------------------------------------------------
    //AsyncTask Classes

    private static class GetLastIdAsyncTask extends AsyncTask<String, Void, Integer>{
        private DBRepository repository = null;
        private MemoDao memoDao;
        public GetLastIdAsyncTask(MemoDao memoDao) {
            this.memoDao = memoDao;
        }
        @Override
        protected Integer doInBackground(String... code) {
            return memoDao.getThisId(code[0]);
        }
        @Override
        protected void onPostExecute(Integer val) {
            repository.lastId = (int)val;
        }
    }

    private static class GetCodeExistAsyncTask extends AsyncTask<String, Void, Integer>{
        private DBRepository repository = null;
        private MemoListDao memoListDao;
        public GetCodeExistAsyncTask(MemoListDao memoListDao) {
            this.memoListDao = memoListDao;
        }
        @Override
        protected Integer doInBackground(String... code) {
            return (Integer)memoListDao.selectCode(code[0]);
        }
        @Override
        protected void onPostExecute(Integer val) {
            repository.codeCount = (int)val;
        }
    }//end of GetCodeExistAsyncTask

    private static class ViewMemoAsyncTask extends AsyncTask<Integer, Void, String>{
        private MemoListDao memoListDao;
        public ViewMemoAsyncTask(MemoListDao memoListDao){
            this.memoListDao = memoListDao;
        }
        @Override
        protected String doInBackground(Integer... pos) {
            return memoListDao.getAll().get((int)pos[0]).getCode();
        }
        @Override
        protected void onPostExecute(String code) {
            Intent intent = new Intent(MainActivity.mContext, viewnoteActivity.class);
            intent.putExtra("code", code);
            MainActivity.mContext.startActivity(intent);
        }
    }//end of ViewMemoAsyncTask

    private static class InsertMemoAsyncTask extends AsyncTask<MemoVo, Void, Void> {
        private MemoDao memoDao;
        public InsertMemoAsyncTask(MemoDao memoDao) {
            this.memoDao = memoDao;
        }
        @Override
        protected Void doInBackground(MemoVo... memoVos) {
            memoDao.insert(memoVos[0]);
            return null;
        }
    }//end of InsertMemoAsyncTask

    private static class UpdateMemoAsyncTask extends AsyncTask<MemoVo, Void, Void>{
        private MemoDao memoDao;
        public UpdateMemoAsyncTask(MemoDao memoDao){ this.memoDao = memoDao; }
        @Override
        protected Void doInBackground(MemoVo... memo) {
            memo[0].setId(memoDao.getThisId(memo[0].getCode()));
            memoDao.update(memo[0]);
            return null;
        }
    }

    private static class DeleteMemoAsyncTask extends AsyncTask<String, Void, Void> {
        private MemoDao memoDao;
        public DeleteMemoAsyncTask(MemoDao memoDao) {
            this.memoDao = memoDao;
        }
        @Override
        protected Void doInBackground(String... code) {
            memoDao.delete(code[0]);
            return null;
        }
    }//end of DeleteMemoAsyncTask

    private static class InsertMemoListAsyncTask extends AsyncTask<MemoListVo, Void, Void> {
        private MemoListDao memoListDao;
        public InsertMemoListAsyncTask(MemoListDao memoListDao) {
            this.memoListDao = memoListDao;
        }
        @Override
        protected Void doInBackground(MemoListVo... memoListVos) {
            memoListDao.insert(memoListVos[0]);
            return null;
        }
    }//end of InsertMemoListAsyncTask

    private static class DeleteMemoListAsyncTask extends AsyncTask<String, Void, Void> {
        private MemoListDao memoListDao;
        public DeleteMemoListAsyncTask(MemoListDao memoListDao) {
            this.memoListDao = memoListDao;
        }
        @Override
        protected Void doInBackground(String... code) {
            memoListDao.delete(code[0]);
            return null;
        }
    }//end of DeleteMemoListAsyncTask


}//end of DBRepository
