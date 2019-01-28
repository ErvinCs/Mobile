package com.lab021.csoka.exam1.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.*;
import com.lab021.csoka.exam1.model.Product;

import java.util.List;

@Dao
public interface ProductDao {
    @Query("SELECT * FROM products")
    LiveData<List<Product>> getAllProducts();

    @Query("SELECT * FROM products WHERE id == :id")
    LiveData<List<Product>> getProductById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOne(Product product);

    @Delete
    void deleteOne(Product product);

    @Update
    void updateOne(Product newProduct);

    @Query("DELETE FROM products")
    void deleteAll();


}
