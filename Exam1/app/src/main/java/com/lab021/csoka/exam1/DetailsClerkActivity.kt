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

class DetailsClerkActivity : AppCompatActivity() {

    private val TAG : String = "DetailsClerkActivity: "

    lateinit var etName : EditText
    lateinit var etDescription : EditText
    lateinit var etQuantity : EditText
    lateinit var etPrice : EditText

    lateinit var productRef : Product

    lateinit var deleteButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clerk_details)

        productRef = intent.extras.getParcelable<Product>(Codes.intent_msg_product_open)

        deleteButton = findViewById(R.id.buttonDelete)
        deleteButton.setOnClickListener { deleteActivity() }

        etName = findViewById(R.id.etName)
        etName.setText(productRef.name)

        etDescription = findViewById(R.id.etDescription)
        etDescription.setText(productRef.description)

        etQuantity = findViewById(R.id.etQuantity)
        etQuantity.setText(productRef.quantity.toString())

        etPrice = findViewById(R.id.etPrice)
        etPrice.setText(productRef.price.toString())
    }

    private fun deleteActivity() {
        deleteButton = findViewById(R.id.buttonDelete)
        val replyIntent : Intent = Intent()
        Log.d(TAG, "Deleting Product")
        replyIntent.putExtra(Codes.intent_msg_product_delete, productRef)
        setResult(Activity.RESULT_OK, replyIntent)
        finish()
    }
}