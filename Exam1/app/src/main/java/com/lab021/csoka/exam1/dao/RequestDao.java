package com.lab021.csoka.exam1.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.*;
import com.lab021.csoka.exam1.model.Request;

import java.util.List;

@Dao
public interface RequestDao {
    @Query("SELECT * FROM requests")
    LiveData<List<Request>> getAllRequests();

    @Query("SELECT * FROM requests WHERE id == :id")
    LiveData<List<Request>> getRequestById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOne(Request request);

    @Delete
    void deleteOne(Request request);

    @Update
    void updateOne(Request newRequest);

    @Query("DELETE FROM requests")
    void deleteAll();


}
