package com.example.csoka.lab_02_android.volley

import android.util.Log
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.VolleyLog
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONObject

/**
 * Formulate the request and handle the response
 * JSON Object Requests
 * The request queue is accessed through the singleton class
 */
class ServiceVolley : ServiceInterface {
    val TAG = ServiceVolley::class.java.simpleName
    val basePath = "http://10.0.2.2:8080/" ///posts"

    override fun post(path: String, params: JSONObject, completionHandler: (response: JSONObject?) -> Unit) {
        val jsonObjReq = object : JsonObjectRequest(Method.POST, basePath + path, params,
            Response.Listener<JSONObject> { response ->
                Log.d(TAG, "/post request OK! Response: $response")
                completionHandler(response)
            },
            Response.ErrorListener { error ->
                VolleyLog.e(TAG, "/post request fail! Error: ${error.message}")
                completionHandler(null)
            }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers.put("Content-Type", "application/json")
                return headers
            }
        }

        BackendVolley.instance?.addToRequestQueue(jsonObjReq, TAG)
    }

    override fun delete(path: String, params: JSONObject, completionHandler: (response: JSONObject?) -> Unit) {
        val jsonObjReq = object : JsonObjectRequest(Method.DELETE, basePath + path, params,
            Response.Listener<JSONObject> { response ->
                Log.d(TAG, "/delete request OK! Response: $response")
                completionHandler(response)
            },
            Response.ErrorListener { error ->
                VolleyLog.e(TAG, "/delete request fail! Error: ${error.message}")
                completionHandler(null)
            }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers.put("Content-Type", "application/json")
                return headers
            }
        }

        BackendVolley.instance?.addToRequestQueue(jsonObjReq, TAG)
    }

    override fun update(path: String, params: JSONObject, completionHandler: (response: JSONObject?) -> Unit) {
        val jsonObjReq = object : JsonObjectRequest(Method.PUT, basePath + path, params,
            Response.Listener<JSONObject> { response ->
                Log.d(TAG, "/update request OK! Response: $response")
                completionHandler(response)
            },
            Response.ErrorListener { error ->
                VolleyLog.e(TAG, "/update request fail! Error: ${error.message}")
                completionHandler(null)
            }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers.put("Content-Type", "application/json")
                return headers
            }
        }

        BackendVolley.instance?.addToRequestQueue(jsonObjReq, TAG)
    }

}