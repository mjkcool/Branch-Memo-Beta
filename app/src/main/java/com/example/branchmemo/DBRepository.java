package com.example.branchmemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;


import java.sql.Date;
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

    synchronized public void viewNote(int pos){
        ViewMemoAsyncTask task = new ViewMemoAsyncTask(memoListDao);
        task.execute(pos);
    }
    synchronized public void loadNote(String code){
        LoadMemoAsyncTask task = new LoadMemoAsyncTask(memoDao, memoListDao);
        task.execute(code);
    }

    synchronized public void insertMemoList(MemoListVo memoList){
        InsertMemoListAsyncTask task = new InsertMemoListAsyncTask(memoListDao);
        task.execute(memoList);
    }

    synchronized public void deleteMemoList(String code){
        DeleteMemoListAsyncTask task = new DeleteMemoListAsyncTask(memoListDao);
        task.execute(code);
    }

    synchronized public void insertMemo(MemoVo memo, String notename){
        InsertMemoAsyncTask task = new InsertMemoAsyncTask(memoDao, memoListDao, notename);
        task.execute(memo);
    }

    synchronized public void deleteMemo(MemoVo memo){
        DeleteMemoAsyncTask task = new DeleteMemoAsyncTask(memoDao);
        task.execute(memo);

    }

    synchronized public void updateMemo(MemoVo memo, String notename){
        UpdateMemoAsyncTask task = new UpdateMemoAsyncTask(memoDao, memoListDao, notename);
        task.execute(memo);
    }

    synchronized public int selectCode(String code){
        GetCodeExistAsyncTask task = new GetCodeExistAsyncTask(memoListDao);
        task.repository = this;
        task.execute(code);
        return codeCount;
    }

    synchronized public void createNew(MemoVo memo, MemoListVo memolist) {
        CreateNewNoteAsyncTask task = new CreateNewNoteAsyncTask(memoDao, memoListDao, memo, memolist);
        task.execute();
    }

    synchronized public void deleteNote(String memoCode) {
        DeleteNoteAsyncTask task = new DeleteNoteAsyncTask(memoDao, memoListDao);
        task.execute(memoCode);
    }

    synchronized public void loadNoteList() {
        LoadNoteListAsyncTask task = new LoadNoteListAsyncTask(memoListDao);
        task.execute();
    }

    synchronized public void updateNote(String memoCode, String title, Date date, int flg) {
        UpdateNoteAsyncTask task = new UpdateNoteAsyncTask(memoListDao, title, date, flg);
        task.execute(memoCode);
    }

    //----------------------------------------------------------------------------------------------
    //AsyncTask Classes

    private static class UpdateNoteAsyncTask extends AsyncTask<String, Void, List<MemoListVo>>{
        private MemoListDao memoListDao;
        private String title;
        private Date date;
        private int flg;
        public UpdateNoteAsyncTask(MemoListDao memoListDao, String title, Date date, int flg){
            this.memoListDao = memoListDao;
            this.title = title;
            this.date = date;
            this.flg = flg;
        }
        @Override
        protected List<MemoListVo> doInBackground(String... code) {
            memoListDao.updateNote(code[0], title, date);
            return memoListDao.getAll();

        }
        @Override
        protected void onPostExecute(List<MemoListVo> list) {
            ((MainActivity)MainActivity.mContext).getData(list);
            if(flg==0) ((Activity) ViewBranchActivity.mContext).finish();//백버튼 동작시
        }
    }

    private static class LoadNoteListAsyncTask extends AsyncTask<Void, Void, List<MemoListVo>>{
        private MemoListDao memoListDao;
        public LoadNoteListAsyncTask(MemoListDao memoListDao){
            this.memoListDao = memoListDao;
        }
        @Override
        protected List<MemoListVo> doInBackground(Void... Void) {
            return memoListDao.getAll();
        }
        @Override
        protected void onPostExecute(List<MemoListVo> list) {
            ((MainActivity)MainActivity.mContext).getData(list);
        }
    }

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
            Intent close = new Intent(ViewBranchActivity.mContext, MainActivity.class);
            close.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            ViewBranchActivity.mContext.startActivity(close);
            ((Activity) ViewBranchActivity.mContext).finish();
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
            ViewMemoAsyncTask task = new ViewMemoAsyncTask(memoListDao);
            task.onPostExecute(this.memoListVo.getCode());
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
            Intent intent = new Intent(MainActivity.mContext, ViewBranchActivity.class);
            intent.putExtra("code", code);
            MainActivity.mContext.startActivity(intent);
        }
    }//end of ViewMemoAsyncTask

    private static class LoadMemoAsyncTask extends AsyncTask<String, Void, List<MemoVo>>{
        private MemoDao memoDao;
        private MemoListDao memoListDao;
        private String notename;
        public LoadMemoAsyncTask(MemoDao memoDao, MemoListDao memoListDao){
            this.memoDao = memoDao;
            this.memoListDao = memoListDao;
        }
        @Override
        protected List<MemoVo> doInBackground(String... code) {
            notename = memoListDao.get(code[0]).getTitle();
            return memoDao.getAll(code[0]);
        }
        @Override
        protected void onPostExecute(List<MemoVo> list) {
            ((ViewBranchActivity) ViewBranchActivity.mContext).getData(list, notename);
        }
    }

    private static class InsertMemoAsyncTask extends AsyncTask<MemoVo, Void, List<MemoVo>> {
        private MemoDao memoDao;
        private MemoListDao memoListDao;
        private String notename;
        public InsertMemoAsyncTask(MemoDao memoDao, MemoListDao memoListDao, String notename) {
            this.memoDao = memoDao;
            this.memoListDao = memoListDao;
            this.notename = notename;
        }
        @Override
        protected List<MemoVo> doInBackground(MemoVo... memo) {
            memoDao.insert(memo[0]);
            return memoDao.getAll(memo[0].getCode());
        }
        @Override
        protected void onPostExecute(List<MemoVo> list) {
            ((ViewBranchActivity) ViewBranchActivity.mContext).getData(list, notename);
        }
    }//end of InsertMemoAsyncTask

    private static class UpdateMemoAsyncTask extends AsyncTask<MemoVo, Void, List<MemoVo>>{
        private MemoDao memoDao;
        private MemoListDao memoListDao;
        private String notename;
        public UpdateMemoAsyncTask(MemoDao memoDao, MemoListDao memoListDao, String notename){
            this.memoDao = memoDao;
            this.memoListDao = memoListDao;
            this.notename = notename;
        }
        @Override
        protected List<MemoVo> doInBackground(MemoVo... memo) {
            memo[0].setId(memoDao.getThisId(memo[0].getCode()));
            memoDao.update(memo[0]);
            return memoDao.getAll(memo[0].getCode());
        }
        @Override
        protected void onPostExecute(List<MemoVo> list) {
            ((ViewBranchActivity) ViewBranchActivity.mContext).getData(list, notename);
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
            Intent intent = ((Activity) ViewBranchActivity.mContext).getIntent();
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            ((Activity) ViewBranchActivity.mContext).finish();
            ViewBranchActivity.mContext.startActivity(intent);
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
