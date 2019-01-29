package com.lab021.csoka.exam1

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    public fun activeInternetConnection(context: Context) : Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
        return isConnected
    }

    private val TAG : String = "MainActivity: "

    lateinit var buttonSupplier : Button
    lateinit var buttonEmployee : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.choose_section)

        buttonSupplier = findViewById(R.id.buttonSupplier)
        buttonEmployee = findViewById(R.id.buttonEmployee)

        buttonSupplier.setOnClickListener {
            startActivity(Intent(this@MainActivity, SupplierActivity::class.java))
        }

        buttonEmployee.setOnClickListener {
            if (!activeInternetConnection(this))
                Toast.makeText(this, "No internet connection!", Toast.LENGTH_LONG).show()
            else
                startActivity(Intent(this@MainActivity, EmployeeActivity::class.java))
        }

    }
}
