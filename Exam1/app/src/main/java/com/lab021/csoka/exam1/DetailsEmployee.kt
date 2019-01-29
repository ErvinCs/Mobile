package com.lab021.csoka.exam1

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.lab021.csoka.exam1.model.Codes
import com.lab021.csoka.exam1.model.Request

class DetailsEmployee : AppCompatActivity() {

    private val TAG : String = "DetailsEmployee: "

    lateinit var etName : EditText
    lateinit var etProduct : EditText
    lateinit var etQuantity : EditText
    lateinit var etStatus : EditText

    lateinit var productRef : Request

    lateinit var deleteButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clerk_details)

        productRef = intent.extras.getParcelable<Request>(Codes.intent_msg_product_open)

        deleteButton = findViewById(R.id.buttonDelete)
        deleteButton.setOnClickListener { deleteActivity() }

        etName = findViewById(R.id.etName)
        etName.setText(productRef.name)

        etProduct = findViewById(R.id.etProduct)
        etProduct.setText(productRef.product)

        etQuantity = findViewById(R.id.etQuantity)
        etQuantity.setText(productRef.quantity.toString())

    }

    private fun deleteActivity() {
        deleteButton = findViewById(R.id.buttonDelete)
        val replyIntent : Intent = Intent()
        Log.d(TAG, "Deleting Request")
        replyIntent.putExtra(Codes.intent_msg_product_delete, productRef)
        setResult(Activity.RESULT_OK, replyIntent)
        finish()
    }
}