package com.lab021.csoka.exam1

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.lab021.csoka.exam1.model.Codes
import com.lab021.csoka.exam1.model.Request

class DetailsFulfillActivity : AppCompatActivity() {

    public fun activeInternetConnection(context: Context) : Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
        return isConnected
    }

    private val TAG : String = "DetailsFulfillActivity: "

    lateinit var tvName : TextView
    lateinit var tvProduct : TextView
    lateinit var tvQuantity : TextView
    lateinit var tvStatus : TextView

    lateinit var buttonConfirm : Button

    lateinit var productRef : Request
    //TODO - Check for active internet connection

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(!activeInternetConnection(this)) {
            Log.d(TAG,"No Internet")
            Toast.makeText(this, "No active internet connection!",Toast.LENGTH_SHORT).show();
            finish()
        }
        setContentView(R.layout.activity_details)

        productRef = intent.extras.getParcelable<Request>(Codes.intent_msg_product_details)

        tvName = findViewById(R.id.tvName)
        tvName.text = productRef.name

        tvProduct = findViewById(R.id.tvProduct)
        tvProduct.text = productRef.product

        tvQuantity = findViewById(R.id.tvQuantity)
        tvQuantity.text = productRef.quantity.toString()

        tvStatus = findViewById(R.id.tvStatus)
        tvStatus.text = productRef.status

        buttonConfirm = findViewById(R.id.buttonConfirm)
        buttonConfirm.setOnClickListener { confirm() }
    }

    fun confirm() {
        buttonConfirm = findViewById(R.id.buttonConfirm)
        val replyIntent : Intent = Intent()
        Log.d(TAG, "Fulfilling Request")
        replyIntent.putExtra(Codes.intent_msg_product_details, productRef)
        setResult(Activity.RESULT_OK, replyIntent)
        finish()
    }
}