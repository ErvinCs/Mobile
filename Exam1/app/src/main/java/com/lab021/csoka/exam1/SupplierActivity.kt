package com.lab021.csoka.exam1

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.gson.GsonBuilder
import com.lab021.csoka.exam1.model.Codes
import com.lab021.csoka.exam1.model.Request
import com.lab021.csoka.exam1.ui.ProductAdapter
import com.lab021.csoka.exam1.ui.ProductViewModel
import com.lab021.csoka.exam1.volley.APIController
import com.lab021.csoka.exam1.volley.ServiceVolley
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class SupplierActivity : AppCompatActivity() {

    private val TAG : String = "SupplierActivity: "

    public fun activeInternetConnection(context: Context) : Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
        return isConnected
    }

    lateinit var viewModel : ProductViewModel
    lateinit var adapter : ProductAdapter
    lateinit var recyclerView: RecyclerView

    val service = ServiceVolley()
    val apiController = APIController(service)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fab.hide()  //Available only for the Clerk


        recyclerView = findViewById(R.id.main_view)
        adapter = ProductAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        viewModel = ViewModelProviders.of(this).get(ProductViewModel::class.java)
        viewModel.allProducts.observe(this, object : Observer<List<Request>> {
            override fun onChanged(t: List<Request>?) {
                adapter.setProducts(t)
            }
        })

        fetchJson()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_client, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item!!.itemId) {
            R.id.action_filter -> {
                //View bought products
                true
            } R.id.action_all -> {
                //View the list of all products
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    //TODO - fill request by specfying the request ID
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == Codes.PRODUCT_DETAILS_CODE) {
            if(resultCode == Activity.RESULT_OK) {
                if(data!!.hasExtra(Codes.intent_msg_product_details)) {
                    var product = data.extras!!.getParcelable<Request>(Codes.intent_msg_product_details)

                    val path = "fill"
                    val json = buildJsonObject(product)

                    apiController.post(path, json) { response ->
                        //Toast.makeText(this, response.toString(), Toast.LENGTH_LONG).show()
                        Log.d(TAG, "buyProduct: Response - " + response.toString())
                    }

                }
            }
        }
    }

    private fun buildJsonObject(p : Request) : JSONObject {
        val obj = JSONObject()

        obj.put("id", p.id)
        obj.put("name", p.name)
        obj.put("product", p.product)
        obj.put("quantity", p.quantity)
        obj.put("status", p.status)

        return obj
    }

    fun fetchJson() {
        if (!activeInternetConnection(this)) {
            Toast.makeText(this, "No internet!", Toast.LENGTH_LONG).show()
            Log.d(TAG, "No active internet connection found")
            return
        }
        Log.d(TAG,"Fetching Json Posts")

        val request = okhttp3.Request
            .Builder()
            .url(Codes.base_url + "requests")
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
                    val requests : Array<Request> = gson.fromJson(body, Array<Request>::class.java)

                    runOnUiThread {
                        for(p: Request in requests) {
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