package com.lab021.csoka.exam1.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import com.lab021.csoka.exam1.dao.RequestDao;
import com.lab021.csoka.exam1.model.Request;

import java.util.List;

public class ProductRepository {
    private RequestDao mRequestDao;
    private LiveData<List<Request>> mAllRequests;

    public ProductRepository(Application application) {
        ProductRoomDatabase db = ProductRoomDatabase.getDatabase(application);
        mRequestDao = db.productDao();
        mAllRequests = mRequestDao.getAllRequests();
    }

    public LiveData<List<Request>> getmAllRequests() {
        return mAllRequests;
    }

    public void insert(Request request) {
        new insertAsyncTask(mRequestDao).execute(request);
    }

    public void deleteOne(Request request) {
        new removeAsyncTask(mRequestDao).execute(request);
    }

    public void updateOne(Request newRequest) {
        new updateAsyncTask(mRequestDao).execute(newRequest);
    }

    public List<Request> getProductList() {
        return getmAllRequests().getValue();
    }

    private static class insertAsyncTask extends AsyncTask<Request, Void, Void> {

        private RequestDao mAsyncTaskDao;

        insertAsyncTask(RequestDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Request... params) {
            mAsyncTaskDao.insertOne(params[0]);
            return null;
        }
    }

    private static class removeAsyncTask extends AsyncTask<Request, Void, Void> {
        private RequestDao mAsyncTaskDao;

        removeAsyncTask(RequestDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Request... params) {
            mAsyncTaskDao.deleteOne(params[0]);
            return null;
        }
    }

    private static class updateAsyncTask extends AsyncTask<Request, Void, Void> {

        private RequestDao mAsyncTaskDao;

        updateAsyncTask(RequestDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Request... params) {
            mAsyncTaskDao.updateOne(params[0]);
            return null;
        }
    }
}
