package com.lab021.csoka.exam1.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import com.lab021.csoka.exam1.dao.ProductDao;
import com.lab021.csoka.exam1.model.Product;
import okhttp3.MediaType;

import java.util.List;

public class ProductRepository {
    private ProductDao mProductDao;
    private LiveData<List<Product>> mAllProducts;

    public ProductRepository(Application application) {
        ProductRoomDatabase db = ProductRoomDatabase.getDatabase(application);
        mProductDao = db.productDao();
        mAllProducts = mProductDao.getAllProducts();
    }

    public LiveData<List<Product>> getmAllProducts() {
        return mAllProducts;
    }

    public void insert(Product product) {
        new insertAsyncTask(mProductDao).execute(product);
    }

    public void deleteOne(Product product) {
        new removeAsyncTask(mProductDao).execute(product);
    }

    public void updateOne(Product newProduct) {
        new updateAsyncTask(mProductDao).execute(newProduct);
    }

    public List<Product> getProductList() {
        return getmAllProducts().getValue();
    }

    private static class insertAsyncTask extends AsyncTask<Product, Void, Void> {

        private ProductDao mAsyncTaskDao;

        insertAsyncTask(ProductDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Product... params) {
            mAsyncTaskDao.insertOne(params[0]);
            return null;
        }
    }

    private static class removeAsyncTask extends AsyncTask<Product, Void, Void> {
        private ProductDao mAsyncTaskDao;

        removeAsyncTask(ProductDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Product... params) {
            mAsyncTaskDao.deleteOne(params[0]);
            return null;
        }
    }

    private static class updateAsyncTask extends AsyncTask<Product, Void, Void> {

        private ProductDao mAsyncTaskDao;

        updateAsyncTask(ProductDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Product... params) {
            mAsyncTaskDao.updateOne(params[0]);
            return null;
        }
    }
}
