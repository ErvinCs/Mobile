package com.lab021.csoka.exam1

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.lab021.csoka.exam1.model.Request
import com.lab021.csoka.exam1.ui.ProductAdapter
import com.lab021.csoka.exam1.ui.ProductClerkAdapter
import com.lab021.csoka.exam1.ui.ProductFillAdapter
import com.lab021.csoka.exam1.ui.ProductViewModel

class FilterBigActivity : AppCompatActivity() {
    lateinit var viewModel : ProductViewModel
    lateinit var adapter : ProductFillAdapter
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        recyclerView = findViewById(R.id.main_view)
        adapter = ProductFillAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        viewModel = ViewModelProviders.of(this).get(ProductViewModel::class.java)
    }
}