package com.lab021.csoka.exam1.ui;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import com.lab021.csoka.exam1.model.Product;
import com.lab021.csoka.exam1.repository.ProductRepository;

import java.util.List;

public class ProductViewModel extends AndroidViewModel {
    private ProductRepository mProductRepository;
    private LiveData<List<Product>> mAllProducts;

    public ProductViewModel(Application application) {
        super(application);
        mProductRepository = new ProductRepository(application);
        mAllProducts = mProductRepository.getmAllProducts();
    }

    public LiveData<List<Product>> getAllProducts() {
        return mAllProducts;
    }

    public List<Product> getProductList() { return mAllProducts.getValue(); }

    public boolean contains(Product product) {
        if (mAllProducts.getValue() != null)
            return mAllProducts.getValue().contains((Product) product);
        else
            return false;
    }

    public void insert(Product product) {
        mProductRepository.insert(product);
    }

    public void deleteOne(Product product) {
        mProductRepository.deleteOne(product);
    }

    public void updateOne(Product newProduct) {
        mProductRepository.updateOne(newProduct);
    }
}
