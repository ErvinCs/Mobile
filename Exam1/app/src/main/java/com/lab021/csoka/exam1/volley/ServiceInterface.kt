package com.lab021.csoka.exam1.volley

import org.json.JSONObject

/**
 * When you add a request to the queue,
 * it is picked up by the cache thread and triaged:
 *  if the request can be serviced from cache,
 *    the cached response is parsed on the cache thread
 *    and the parsed response is delivered on the main thread.
 *  if the request cannot be serviced from cache,
 *    it is placed on the network queue.
 *    The first available network thread takes the request from the queue,
 *    performs the HTTP transaction, parses the response
 *    on the worker thread, writes the response to cache
 *    and posts the parsed response back to the main thread for delivery.
 */
 //requestQueue?.cancelAll(TAG) - cancel pending requests
interface ServiceInterface {
    fun post(path: String, params: JSONObject, completionHandler: (response: JSONObject?) -> Unit)

    fun delete(path: String, params: JSONObject, completionHandler: (response: JSONObject?) -> Unit)

    fun update(path: String, params: JSONObject, completionHandler: (response: JSONObject?) -> Unit)
}