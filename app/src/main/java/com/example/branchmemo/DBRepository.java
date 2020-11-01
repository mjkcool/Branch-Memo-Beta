package com.example.branchmemo;

import android.app.Application;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

public class DBRepository {

    private MemoDao memoDao;
    private MemoListDao memoListDao;

    public DBRepository(Application application) {
        MemoDatabase memoDatabase = MemoDatabase.getAppDatabase(application);
        MemoListDatabase memoListDatabase = MemoListDatabase.getAppDatabase(application);
        memoDao = memoDatabase.memeDao();
        memoListDao = memoListDatabase.memoListDao();
    }

    private List<MemoVo> MemoVoReturnVal = new ArrayList<>();
    private void setMemoVoReturnVal(List<MemoVo> memoVoReturnVal) {
        MemoVoReturnVal = memoVoReturnVal;
    }
    public List<MemoVo> getMemoVoReturnVal() {
        return MemoVoReturnVal;
    }

    private List<MemoListVo> MemoListVoReturnVal = new ArrayList<>();
    private void setMemoListVoReturnVal(List<MemoListVo> memoListVoReturnVal) {
        MemoListVoReturnVal = memoListVoReturnVal;
    }
    public List<MemoListVo> getMemoListVoReturnVal() {
        return MemoListVoReturnVal;
    }

    public void insertMemo(MemoVo memo){
        InsertMemoAsyncTask task = new InsertMemoAsyncTask(memoDao);
        task.execute(memo);
    }

    public void deleteMemo(String code){
        DeleteMemoAsyncTask task = new DeleteMemoAsyncTask(memoDao);
        task.execute(code);
    }

    //사용자
    public List<MemoVo> getAllMemo(String code){
        GetAllMemoAsyncTask task = new GetAllMemoAsyncTask(memoDao);
        task.repository = this;
        task.execute(code);
        return getMemoVoReturnVal();
    }
    //사용자
    public List<MemoListVo> getAllMemoList(){
        GetAllMemoListAsyncTask task = new GetAllMemoListAsyncTask(memoListDao);
        task.repository = this;
        task.execute();
        return getMemoListVoReturnVal();
    }

    public void insertMemoList(MemoListVo memoList){
        InsertMemoListAsyncTask task = new InsertMemoListAsyncTask(memoListDao);

    }

    public void deleteMemoList(String code){
        DeleteMemoListAsyncTask task = new DeleteMemoListAsyncTask(code);
    }

    private static class GetAllMemoAsyncTask extends AsyncTask<String, Void, List<MemoVo>>{
        private DBRepository repository = null;
        private MemoDao memoDao;
        public GetAllMemoAsyncTask(MemoDao memoDao) {
            this.memoDao = memoDao;
        }
        @Override
        protected List<MemoVo> doInBackground(String... code) {
            return memoDao.getAll(code[0]);
        }
        @Override
        protected void onPostExecute(List<MemoVo> memoVos) {
            repository.setMemoVoReturnVal(memoVos);
        }
    }//end of GetAllMemoAsyncTask

    private static class GetAllMemoListAsyncTask extends AsyncTask<Void, Void, List<MemoListVo>>{
        private DBRepository repository = null;
        private MemoListDao memoListDao;
        public GetAllMemoListAsyncTask(MemoListDao memoListDao) {
            this.memoListDao = memoListDao;
        }
        @Override
        protected List<MemoListVo> doInBackground(Void... voids) {
            return memoListDao.getAll();
        }
        @Override
        protected void onPostExecute(List<MemoListVo> memoListVos) {
            repository.setMemoListVoReturnVal(memoListVos);
        }
    }//end of getAllMemoListAsyncTask


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
        public DeleteMemoListAsyncTask(String code) {
            this.memoListDao = memoListDao;
        }
        @Override
        protected Void doInBackground(String... code) {
            memoListDao.delete(code[0]);
            return null;
        }
    }//end of DeleteMemoListAsyncTask
}
