package com.example.branchmemo;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DBRepository {

    private MemoDao memoDao;
    private MemoListDao memoListDao;


    public DBRepository(Context context) {
        MemoDatabase memoDatabase = MemoDatabase.getAppDatabase(context);
        MemoListDatabase memoListDatabase = MemoListDatabase.getAppDatabase(context);
        memoDao = memoDatabase.memeDao();
        memoListDao = memoListDatabase.memoListDao();
    }

    public MemoDao getMemoDao() {
        return memoDao;
    }

    public MemoListDao getMemoListDao() {
        return memoListDao;
    }

    //이 리스트객체들을 제대로 초기화하는 법을 알아내야 함.
    private List<MemoVo> MemoVoReturnVal = new ArrayList<>();
    private List<MemoListVo> MemoListVoReturnVal = new ArrayList<>();

    private void setMemoVoReturnVal(List<MemoVo> memoVoReturnVal) {
        MemoVoReturnVal = memoVoReturnVal;
    }
    public List<MemoVo> getMemoVoReturnVal() {
        return MemoVoReturnVal;
    }


    private void setMemoListVoReturnVal(List<MemoListVo> memoListVoReturnVal) {
        MemoListVoReturnVal = memoListVoReturnVal;
    }
    public List<MemoListVo> getMemoListVoReturnVal() {

        return MemoListVoReturnVal;
    }

    private String returnValCode;
    public void setCode(String code){
        returnValCode = code;
    }

    public String getCode(int pos) {
        GetCodeAsyncTask task = new GetCodeAsyncTask(memoListDao);
        task.repository = this;
        task.execute(pos);
        return returnValCode;
    }


    private int codeCount;
    public void setCodeCount(int codeCount) { this.codeCount = codeCount; }
    public int getCodeCount() { return codeCount; }

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
        task.execute(memoList);
    }

    public void deleteMemoList(String code){
        DeleteMemoListAsyncTask task = new DeleteMemoListAsyncTask(code);
        task.execute(code);
    }

    public int selectCode(String code){
        GetCodeExistAsyncTask task = new GetCodeExistAsyncTask(memoListDao);
        task.repository = this;
        task.execute(code);
        return getCodeCount();
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
    }//end of GetAllMemoListAsyncTask

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
            repository.setCodeCount((int)val);
        }
    }//end of GetCodeExistAsyncTask

    private static class GetCodeAsyncTask extends AsyncTask<Integer, Void, String>{
        private DBRepository repository = null;
        private MemoListDao memoListDao;
        public GetCodeAsyncTask(MemoListDao memoListDao){
            this.memoListDao = memoListDao;
        }
        @Override
        protected String doInBackground(Integer... pos) {
            return memoListDao.getAll().get((int)pos[0]).getCode();
        }
        @Override
        protected void onPostExecute(String code) {
            repository.setCode(code);
        }
    }//end of GetCodeAsyncTask

    private static class InsertMemoAsyncTask extends AsyncTask<MemoVo, Void, Void> {
        private MemoDao memoDao;
        public InsertMemoAsyncTask(MemoDao memoDao) {
            this.memoDao = memoDao;
        }
        @Override
        protected Void doInBackground(MemoVo... memoVos) {
            memoDao.insert(memoVos[0]);
            Log.d("DBRepository", "정국 Memo insert "+memoDao.getAll(memoVos[0].getCode()).get(0).getTitle());
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
            Log.d("DBRepository", "정국 MemoList insert: "+memoListVos[0].getTitle());
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
