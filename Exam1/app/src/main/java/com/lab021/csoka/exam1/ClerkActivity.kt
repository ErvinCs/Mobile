package com.lab021.csoka.exam1

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.gson.GsonBuilder
import com.lab021.csoka.exam1.model.Codes
import com.lab021.csoka.exam1.model.Product
import com.lab021.csoka.exam1.ui.ProductAdapter
import com.lab021.csoka.exam1.ui.ProductClerkAdapter
import com.lab021.csoka.exam1.ui.ProductViewModel
import com.lab021.csoka.exam1.volley.APIController
import com.lab021.csoka.exam1.volley.ServiceVolley
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class ClerkActivity : AppCompatActivity() {

    private val TAG : String = "ClerkActivity:"

    public fun activeInternetConnection(context: Context) : Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
        return isConnected
    }

    lateinit var viewModel : ProductViewModel
    lateinit var adapter : ProductClerkAdapter
    lateinit var  recyclerView: RecyclerView

    val service = ServiceVolley()
    val apiController = APIController(service)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //setSupportActionBar(toolbar)

        recyclerView = findViewById(R.id.main_view)
        adapter = ProductClerkAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        viewModel = ViewModelProviders.of(this).get(ProductViewModel::class.java)

        viewModel.allProducts.observe(this, object : Observer<List<Product>> {
            override fun onChanged(t: List<Product>?) {
                adapter.setProducts(t)
            }
        })

        fetchJson()

        //FAB Post
        fab.show()
        fab.setOnClickListener { _ ->
            Toast.makeText(this, "Add Product", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, PostProductActivity::class.java)
            startActivityForResult(intent, Codes.PRODUCT_POST_CODE)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item!!.itemId) {
            R.id.action_post -> {
                Log.d(TAG, "Started Add Product")
                Toast.makeText(this, "Add Product", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, PostProductActivity::class.java)
                startActivityForResult(intent, Codes.PRODUCT_POST_CODE)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun buildJsonObject(p : Product) : JSONObject {
        val obj = JSONObject()

        obj.put("id", p.id)
        obj.put("name", p.name)
        obj.put("description", p.description)
        obj.put("quantity", p.quantity)
        obj.put("price", p.price)
        obj.put("status", p.status)

        return obj
    }

    //TODO - Post & Delete
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == Codes.PRODUCT_POST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                if (data!!.hasExtra(Codes.intent_msg_product_post)) {
                    var product = data.extras!!.getParcelable<Product>(Codes.intent_msg_product_post)

                    val path = "product"
                    val json = buildJsonObject(product)

                    apiController.post(path, json) { response ->
                        Log.d(TAG, "Post: Response - " + response.toString())
                    }

                    viewModel.insert(product)
                    fetchJson()
                }
            }
        } else if (requestCode == Codes.PRODUCT_DELETE_CODE) {
            if(resultCode == Activity.RESULT_OK) {
                if(data!!.hasExtra(Codes.intent_msg_product_delete)) {
                    var product = data.extras!!.getParcelable<Product>(Codes.intent_msg_product_delete)

                    val path = "product/" + product.id.toString()
                    val json = buildJsonObject(product)

                    apiController.delete(path, json) { response ->
                        Log.d(TAG, "Delete: Response - " + response.toString())
                    }

                    viewModel.deleteOne(product)
                    fetchJson()
                }
            }
        }
    }

    fun fetchJson() {
        if (!activeInternetConnection(this)) {
            Toast.makeText(this, "No internet!", Toast.LENGTH_LONG).show()
            Log.d(TAG, "No active internet connection found")
            return
        }
        Log.d(TAG,"Fetching Json Posts")

        val request = Request
            .Builder()
            .url(Codes.base_url + "all")
            .build()

        val thisContext: Context = this
        val client = OkHttpClient()
        client.newCall(request)
            .enqueue(object: Callback {
                override fun onResponse(call: Call, response: Response) {
                    val body = response
                        .body()
                        ?.string()
                    Log.d(TAG, "Fetched body: " + body)

                    val gson = GsonBuilder().create()
                    val products : Array<Product> = gson.fromJson(body, Array<Product>::class.java)

                    runOnUiThread {
                        for(p: Product in products) {
                            viewModel.insert(p)
                            Log.d(TAG, "Inserted product: " + p.toString())
                        }
                    }
                }
                override fun onFailure(call: Call, e: IOException) {
                    Log.d(TAG,"Failed Http Request; Could not connect: " + e.toString())
                    runOnUiThread {
                        Toast.makeText(thisContext, e.toString(), Toast.LENGTH_LONG).show()
                    }
                }
            })
    }
}