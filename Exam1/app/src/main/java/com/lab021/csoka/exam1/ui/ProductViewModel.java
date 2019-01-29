package com.lab021.csoka.exam1.ui;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import com.lab021.csoka.exam1.model.Request;
import com.lab021.csoka.exam1.repository.ProductRepository;

import java.util.List;

public class ProductViewModel extends AndroidViewModel {
    private ProductRepository mProductRepository;
    private LiveData<List<Request>> mAllProducts;

    public ProductViewModel(Application application) {
        super(application);
        mProductRepository = new ProductRepository(application);
        mAllProducts = mProductRepository.getmAllRequests();
    }

    public LiveData<List<Request>> getAllProducts() {
        return mAllProducts;
    }

    public List<Request> getProductList() { return mAllProducts.getValue(); }

    public boolean contains(Request request) {
        if (mAllProducts.getValue() != null)
            return mAllProducts.getValue().contains((Request) request);
        else
            return false;
    }

    public void insert(Request request) {
        mProductRepository.insert(request);
    }

    public void deleteOne(Request request) {
        mProductRepository.deleteOne(request);
    }

    public void updateOne(Request newRequest) {
        mProductRepository.updateOne(newRequest);
    }
}
