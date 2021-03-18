package com.example.covidstats

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException

class MainActivity : AppCompatActivity() {
    private lateinit var textView: TextView
    private var requestQueue: RequestQueue? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = "KotlinApp"
        textView = findViewById(R.id.textViewResult)
        val button: Button = findViewById(R.id.btnParse)
        requestQueue = Volley.newRequestQueue(this)
        button.setOnClickListener {
            jsonParse()
        }
    }
    private fun jsonParse() {
        val url = "https://api.myjson.com/bins/xbspb"
        val request = JsonObjectRequest(Request.Method.GET, url, null, Response.Listener {
            response ->try {
            val jsonArray = response.getJSONArray("employees")
            for (i in 0 until jsonArray.length()) {
                val employee = jsonArray.getJSONObject(i)
                val firstName = employee.getString("firstname")
                val age = employee.getInt("age")
                val mail = employee.getString("mail")
                textView.append("$firstName, $age, $mail\n\n")
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        }, Response.ErrorListener { error -> error.printStackTrace() })
        requestQueue?.add(request)
    }
}