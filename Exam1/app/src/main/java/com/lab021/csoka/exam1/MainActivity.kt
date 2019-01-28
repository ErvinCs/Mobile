package com.lab021.csoka.exam1

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    public fun activeInternetConnection(context: Context) : Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
        return isConnected
    }

    private val TAG : String = "MainActivity: "

    lateinit var buttonClient : Button
    lateinit var buttonClerk : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.choose_section)

        buttonClient = findViewById(R.id.buttonClient)
        buttonClerk = findViewById(R.id.buttonClerk)

        buttonClient.setOnClickListener {
            startActivity(Intent(this@MainActivity, ClientActivity::class.java))
        }

        buttonClerk.setOnClickListener {
            if (!activeInternetConnection(this))
                Toast.makeText(this, "No internet connection!", Toast.LENGTH_LONG).show()
            else
                startActivity(Intent(this@MainActivity, ClerkActivity::class.java))
        }

    }
}
