package com.lab021.csoka.exam1.volley

import org.json.JSONObject

class APIController constructor(serviceInjection: ServiceInterface): ServiceInterface {
    private val service: ServiceInterface = serviceInjection

    override fun post(path: String, params: JSONObject, completionHandler: (response: JSONObject?) -> Unit) {
        service.post(path, params, completionHandler)
    }

    override fun delete(path: String, params: JSONObject, completionHandler: (response: JSONObject?) -> Unit) {
        service.delete(path, params, completionHandler)
    }

    override fun update(path: String, params: JSONObject, completionHandler: (response: JSONObject?) -> Unit) {
        service.update(path, params, completionHandler)
    }
}