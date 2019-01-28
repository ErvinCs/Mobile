package com.lab021.csoka.exam1

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.lab021.csoka.exam1.model.Codes
import com.lab021.csoka.exam1.model.Product
import com.lab021.csoka.exam1.model.Status

class PostProductActivity : AppCompatActivity() {
    private val TAG : String = "PostProductActivity: "

    lateinit var editName : EditText
    lateinit var editDescription : EditText
    lateinit var editQuantity : EditText
    lateinit var editPrice : EditText
    lateinit var buttonPostProduct : Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)
        buttonPostProduct = findViewById(R.id.buttonPostProduct)
        buttonPostProduct.setOnClickListener { postProduct()}
    }

    private fun postProduct() {
        editName = findViewById(R.id.editName)
        editDescription = findViewById(R.id.editDescription)
        editQuantity = findViewById(R.id.editQuantity)
        editPrice = findViewById(R.id.editPrice)

        val replyIntent : Intent = Intent()
        if (editName.text.isEmpty() || editDescription.text.isEmpty() ||
            editQuantity.text.isEmpty() || editPrice.text.isEmpty()) {
            Log.d(TAG, "Result Canceled")
            setResult(Activity.RESULT_CANCELED, replyIntent)
        } else {
            val post = Product(
                editName.text.toString(),
                editDescription.text.toString(), editQuantity.text.toString().toInt(),
                editPrice.text.toString().toInt(), Status.NEW.toString()
            )
            post.id = 0
            Log.d(TAG, "Posting Product")
            replyIntent.putExtra(Codes.intent_msg_product_post, post)
            setResult(Activity.RESULT_OK, replyIntent)
        }
        finish()
    }
}