package com.example.csoka.lab_02_android

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.csoka.lab_02_android.model.Post
import com.example.csoka.lab_02_android.repository.PostRepository
import com.example.csoka.lab_02_android.ui.PostListAdapter
import com.example.csoka.lab_02_android.ui.PostViewModel
import com.example.csoka.lab_02_android.volley.APIController
import com.example.csoka.lab_02_android.volley.ServiceVolley
import com.google.gson.GsonBuilder

import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class MainActivity : AppCompatActivity() {
    final val NEW_POST_ACTIVITY_CODE = 1
    final val UPDATE_POST_CODE = 2

    final val EXTRA_POST_REPLY = "com.example.csoka.lab_02_android.POST_REPLY"
    final val DELETE_POST_REPLY = "com.example.csoka.lab_02_android.DELETE_REPLY"

    final val postsUrl = "http://10.0.2.2:8080/posts" //"http://127.0.0.1:8080/posts"

    lateinit var postViewModel : PostViewModel
    lateinit var adapter : PostListAdapter

//    var queue: RequestQueue = Volley.newRequestQueue(this)
//    var volley = VolleyHttp(queue, postsUrl)

    val service = ServiceVolley()
    val apiController = APIController(service)

    public fun activeInternetConnection(context: Context) : Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
        return isConnected
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        var recyclerView : RecyclerView = findViewById(R.id.main_recyclerview)
        adapter = PostListAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        //Get the ViewModel
        postViewModel = ViewModelProviders.of(this).get(PostViewModel::class.java)

        //Add an observer for the LiveData
        postViewModel.allPosts.observe(this, object : Observer<List<Post>> {
            override fun onChanged(t: List<Post>?) {
                adapter.setPosts(t)
            }
        })

        fetchJson()
    }

    fun fetchJson() {
        if (!activeInternetConnection(this)) {
            Toast.makeText(this, R.string.no_internet, Toast.LENGTH_LONG).show()
            Log.d("MainActivity: ", "No active internet connection found")
            return
        }
        Log.d("MainActivity: ","Fetching Json Posts")

        val request = Request
            .Builder()
            .url(postsUrl)
            .build()

        val thisContext: Context = this
        val client = OkHttpClient()
        //Sync with the db on a bg thread
        client.newCall(request)
            .enqueue(object: Callback {
                override fun onResponse(call: Call, response: Response) {
                    val body = response
                        .body()
                        ?.string()
                    Log.d("MainActivity: ", "Fetched body: " + body)

                    val gson = GsonBuilder().create()
                    val posts : Array<Post> = gson.fromJson(body, Array<Post>::class.java)

                    runOnUiThread {
                        for(p: Post in posts) {
                            postViewModel.insert(p)
                            Log.d("MainActivity: ", "Inserted post: " + p.toString())
                        }
                    }
                }
                override fun onFailure(call: Call, e: IOException) {
                    Log.d("MainActivity: ","Failed Http Request; Could not connect: " + e.toString())
                    Toast.makeText(thisContext, R.string.could_not_connect_db, Toast.LENGTH_LONG).show()
                }
            })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            R.id.action_refresh -> {
                fetchJson()
                true
            }
            R.id.action_new_activity -> {
                Toast.makeText(this, "New Activity", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, NewPostActivity::class.java)
                startActivityForResult(intent, NEW_POST_ACTIVITY_CODE)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == NEW_POST_ACTIVITY_CODE) {
            if(resultCode == Activity.RESULT_OK) {
                if (data!!.hasExtra(EXTRA_POST_REPLY)) {
                    var post = data.extras!!.getParcelable<Post>(EXTRA_POST_REPLY)
                    postViewModel.insert(post)

                    val path = "posts"
                    val p = JSONObject()

                    p.put("id", post.id)
                    p.put("userName", post.userName)
                    p.put("activityName", post.activityName)
                    p.put("memberLimit", post.memberLimit)
                    p.put("date", post.date)
                    p.put("time", post.time)
                    p.put("location", post.location)
                    p.put("description", post.description)

                    Log.d("Added: ", p.toString())
                    apiController.post(path, p) { response ->
                        Log.d("Post: Response - ", response.toString())
                    }

                    Toast.makeText(this, R.string.post_added, Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(this, R.string.post_add_failed, Toast.LENGTH_LONG).show()
            }
        } else if(requestCode == UPDATE_POST_CODE) {
            if(!this.activeInternetConnection(this)) {
                Toast.makeText(this, R.string.no_internet, Toast.LENGTH_LONG).show()
            } else {
                if (resultCode == Activity.RESULT_OK) {
                    if (data!!.hasExtra(EXTRA_POST_REPLY)) {
                        var post = data.extras!!.getParcelable<Post>(EXTRA_POST_REPLY)
                        postViewModel.updateOne(post)

                        val id: Long = post.id
                        val path = "posts/" + id.toString()
                        val p = JSONObject()

                        p.put("id", post.id)
                        p.put("userName", post.userName)
                        p.put("activityName", post.activityName)
                        p.put("memberLimit", post.memberLimit)
                        p.put("date", post.date)
                        p.put("time", post.time)
                        p.put("location", post.location)
                        p.put("description", post.description)

                        Log.d("Updated: ", p.toString())
                        apiController.update(path, p) { response ->
                            Log.d("Post: Response - ", response.toString())
                        }

                        Toast.makeText(this, R.string.post_updated, Toast.LENGTH_LONG).show()
                    } else if (data!!.hasExtra(DELETE_POST_REPLY)) {
                        var post = data.extras!!.getParcelable<Post>(DELETE_POST_REPLY)
                        postViewModel.deleteOne(post)

                        val id: Long = post.id
                        val path = "posts/" + id.toString()
                        val p = JSONObject()

                        p.put("id", id)
                        p.put("userName", post.userName)
                        p.put("activityName", post.activityName)
                        p.put("memberLimit", post.memberLimit)
                        p.put("date", post.date)
                        p.put("time", post.time)
                        p.put("location", post.location)
                        p.put("description", post.description)

                        Log.d("Deleted: ", p.toString())
                        apiController.delete(path, p) { response ->
                            Log.d("Delete: Response - ", response.toString())
                        }

                        Toast.makeText(this, R.string.post_deleted, Toast.LENGTH_LONG).show()
                    }
                } else {
                    Toast.makeText(this, R.string.post_update_failed, Toast.LENGTH_LONG).show()
                }
            }
        } else {
            Toast.makeText(this, R.string.invalid_input, Toast.LENGTH_LONG).show()
        }
    }
}

