package com.guitmcode.covidstats

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley


class MainActivity : AppCompatActivity() {

    lateinit var textView : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById(R.id.pp)

        //println("WE ARE HERE")
        Log.d("debug", "WE ARE HERE")

        prueba()
    }




    private fun prueba() : Unit {
        val url = "https://api.covid19tracking.narrativa.com/api/countries" //"http://my-json-feed" // "https://reqres.in/api/users"


        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
                Response.Listener { response ->
                    //print("Response: %s".format(response.toString()))
                    Log.d("debug", "Response: %s".format(response.toString()))
                    textView.text = "Response: %s".format(response.toString())
                },
                Response.ErrorListener { error ->
                    Log.d("debug", error.toString())
                    textView.text = "ERROOOOOR"
                    // TODO: Handle error
                }
        )

        val queue = Volley.newRequestQueue(this)
        queue.add(jsonObjectRequest)
        //Log.d("debug", queue().toString())

        //Log.d("debug", jsonObjectRequest.toString())
        // Access the RequestQueue through your singleton class.
        //MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }
}