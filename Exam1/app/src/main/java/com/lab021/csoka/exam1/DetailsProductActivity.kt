package com.lab021.csoka.exam1

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.lab021.csoka.exam1.model.Codes
import com.lab021.csoka.exam1.model.Product
import com.lab021.csoka.exam1.ui.ProductAdapter

class DetailsProductActivity : AppCompatActivity() {

    private val TAG : String = "DetailsProductActivity: "

    lateinit var tvName : TextView
    lateinit var tvDescription : TextView
    lateinit var tvQuantity : TextView
    lateinit var tvPrice : TextView
    lateinit var tvStatus : TextView

    lateinit var buttonConfirm : Button

    lateinit var productRef : Product
    //TODO - Check for active internet connection

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        productRef = intent.extras.getParcelable<Product>(Codes.intent_msg_product_details)

        tvName = findViewById(R.id.tvName)
        tvName.text = productRef.name

        tvDescription = findViewById(R.id.tvDescription)
        tvDescription.text = productRef.description

        tvQuantity = findViewById(R.id.tvQuantity)
        tvQuantity.text = productRef.quantity.toString()

        tvPrice = findViewById(R.id.tvPrice)
        tvPrice.text = productRef.price.toString()

        tvStatus = findViewById(R.id.tvStatus)
        tvStatus.text = productRef.status

        buttonConfirm = findViewById(R.id.buttonConfirm)
        buttonConfirm.setOnClickListener { confirm() }
    }

    fun confirm() {
        buttonConfirm = findViewById(R.id.buttonConfirm)
        val replyIntent : Intent = Intent()
        Log.d(TAG, "Confirming Purchase")
        replyIntent.putExtra(Codes.intent_msg_product_details, productRef)
        setResult(Activity.RESULT_OK, replyIntent)
        finish()
    }
}