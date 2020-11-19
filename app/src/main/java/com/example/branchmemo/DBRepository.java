package com.example.branchmemo;

import android.app.Activity;
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

    //----------------------------------------------------------------------------------------------
    //For User Methods

    public void viewNote(int pos){
        ViewMemoAsyncTask task = new ViewMemoAsyncTask(memoListDao);
        task.execute(pos);
    }
    public void loadNote(String code){
        LoadMemoAsyncTask task = new LoadMemoAsyncTask(memoDao);
        task.execute(code);
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


    public void deleteMemo(MemoVo memo){
        DeleteMemoAsyncTask task = new DeleteMemoAsyncTask(memoDao);
        task.execute(memo);

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

    public void createNew(MemoVo memo, MemoListVo memolist) {
        CreateNewNoteAsyncTask task = new CreateNewNoteAsyncTask(memoDao, memoListDao, memo, memolist);
        task.execute();
    }

    public void deleteNote(String memoCode) {
        DeleteNoteAsyncTask task = new DeleteNoteAsyncTask(memoDao, memoListDao);
        task.execute(memoCode);
    }



    //----------------------------------------------------------------------------------------------
    //AsyncTask Classes

    private static class DeleteNoteAsyncTask extends AsyncTask<String, Void, Void>{
        private MemoDao memoDao;
        private MemoListDao memoListDao;
        public DeleteNoteAsyncTask(MemoDao memoDao, MemoListDao memoListDao){
            this.memoDao = memoDao;
            this.memoListDao = memoListDao;
        }
        @Override
        protected Void doInBackground(String... code) {
            memoDao.delete(code[0]);
            memoListDao.delete(code[0]);
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            Intent close = new Intent(viewnoteActivity.mContext, MainActivity.class);
            close.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            viewnoteActivity.mContext.startActivity(close);
            ((Activity)viewnoteActivity.mContext).finish();
        }
    }

    private static class CreateNewNoteAsyncTask extends AsyncTask<Void, Void, Void> {
        private MemoDao memoDao;
        private MemoListDao memoListDao;
        private MemoVo memoVo;
        private MemoListVo memoListVo;
        public CreateNewNoteAsyncTask(MemoDao memoDao, MemoListDao memoListDao, MemoVo memo, MemoListVo memoList) {
            this.memoDao = memoDao;
            this.memoListDao = memoListDao;
            this.memoVo = memo;
            this.memoListVo = memoList;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            memoDao.insert(memoVo);
            memoListDao.insert(memoListVo);
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            Intent intent = new Intent(NewnoteActivity.mContext, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            NewnoteActivity.mContext.startActivity(intent);
            ((Activity)NewnoteActivity.mContext).finish();
        }
    }//end of InsertMemoAsyncTask

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

    private static class LoadMemoAsyncTask extends AsyncTask<String, Void, Void>{
        private MemoDao memoDao;
        public LoadMemoAsyncTask(MemoDao memoDao){
            this.memoDao = memoDao;
        }
        @Override
        protected Void doInBackground(String... code) {
            ((viewnoteActivity)viewnoteActivity.mContext).getData(memoDao.getAll(code[0]));
            return null;
        }

    }

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
        @Override
        protected void onPostExecute(Void aVoid) {
            Intent intent = ((Activity)viewnoteActivity.mContext).getIntent();
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            ((Activity)viewnoteActivity.mContext).finish();
            viewnoteActivity.mContext.startActivity(intent);
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
        @Override
        protected void onPostExecute(Void aVoid) {
            Intent intent = ((Activity)viewnoteActivity.mContext).getIntent();
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            ((Activity)viewnoteActivity.mContext).finish();
            viewnoteActivity.mContext.startActivity(intent);
        }
    }

    private static class DeleteMemoAsyncTask extends AsyncTask<MemoVo, Void, Void> {
        private MemoDao memoDao;
        public DeleteMemoAsyncTask(MemoDao memoDao) {
            this.memoDao = memoDao;
        }
        @Override
        protected Void doInBackground(MemoVo... memo) {
            memoDao.delete(memo[0].getId());
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            Intent intent = ((Activity)viewnoteActivity.mContext).getIntent();
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            ((Activity)viewnoteActivity.mContext).finish();
            viewnoteActivity.mContext.startActivity(intent);
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
