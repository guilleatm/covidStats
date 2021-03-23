package com.guitmcode.covidstats

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.guitmcode.covidstats.model.Model
import javax.xml.transform.ErrorListener


class MainActivity : AppCompatActivity(), MainView {

	lateinit var countryText : TextView
	lateinit var countryTextView: AutoCompleteTextView
	lateinit var progressBar : ProgressBar
	lateinit var chosenCountry : TextView

	lateinit var presenter : Presenter

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)


		countryText = findViewById(R.id.countryText)
		countryTextView = findViewById(R.id.countryTextView)
		progressBar = findViewById(R.id.progressBar)
		chosenCountry = findViewById(R.id.chosenCountry)

		val model = Model(applicationContext)
		presenter = Presenter(this, model)
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

	override var countryVisible: Boolean
		get() = countryText.visibility == View.VISIBLE
		set(value) {
			val v = if (value) View.VISIBLE else View.GONE
			countryText.visibility = v
			countryTextView.visibility = v
		}
	override var progressBarVisible: Boolean
		get() = progressBar.visibility == View.VISIBLE
		set(value) {
			progressBar.visibility = if (value) View.VISIBLE else View.GONE
		}

	override fun showError(message: String) {
		Toast.makeText(this, message, Toast.LENGTH_LONG).show()
	}

	override fun showCountries(countries: List<String>) {
		val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, countries)
		countryTextView.apply{
			setAdapter(adapter)
			setText("")

			addTextChangedListener(object : TextWatcher {
				override fun afterTextChanged(p0: Editable?) {
					val country = p0.toString()
					countries.binarySearch { it.compareTo(country) }.let {
						if (it >= 0)
							presenter.setChosenCountry(countries[it])
					}
				}

				override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
					// Do nothing
				}

				override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
					// Do nothing
				}

			})
		}
	}

	override fun showChosenCountry(country: String) {
		chosenCountry.setText(country)
	}


/* from google
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

    */



}