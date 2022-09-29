package com.zerdasoftware.internetnotification


import android.content.Context
import android.os.StrictMode
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.zerdasoftware.internetnotification.Constants.BASE_URL
import com.zerdasoftware.internetnotification.Constants.SERVER_KEY
import org.json.JSONObject


class FCMSend {

    fun pushNotification(context:Context, token:String, title:String, message:String) {
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        val queue = Volley.newRequestQueue(context)

        try {
            val json = JSONObject()
            json.put("to", token)

            val notification = JSONObject()
            notification.put("title", title)
            notification.put("body", message)

            json.put("notification", notification)

            val jsonObjectRequest: JsonObjectRequest = object :
                JsonObjectRequest(Method.POST, BASE_URL, json,
                    Response.Listener<JSONObject?> {  response -> println("FCM$response") },
                    Response.ErrorListener { }) {
                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String> {
                    val params: MutableMap<String, String> = HashMap()
                    params["Content-Type"] = "application/json"
                    params["Authorization"] = SERVER_KEY
                    return params
                }
            }
            queue.add(jsonObjectRequest)
        }catch (e:Exception) { e.printStackTrace() }
    }
}