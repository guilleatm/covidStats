package com.guitmcode.covidstats.model

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject

private const val BASE_URL = "https://api.covid19tracking.narrativa.com/api"
private const val COUNTRIES = "countries"
private  const val NAME_LABEL = "name"

class Network private constructor (context : Context) {

	companion object: SingletonHolder<Network, Context>(:: Network)

	val queue = Volley.newRequestQueue(context)

	fun getCountries(listener: Response.Listener<List<String>>, errorListener: Response.ErrorListener) {
		val url = "$BASE_URL/$COUNTRIES"
		Log.d("debug", "url: $url")
		val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
			{ response -> processCountries(response, listener) },
			{ error -> errorListener.onErrorResponse(error) }
		)
		queue.add(jsonObjectRequest)
	}

	private fun processCountries(
		response: JSONObject,
		listener: Response.Listener<List<String>>
	) {
		val countries = ArrayList<String>()

		try {
			val countryArray = response.getJSONArray(COUNTRIES)
			for (i in 0 until countryArray.length()) {
				val countryObject : JSONObject = countryArray[i] as JSONObject
				val name = countryObject.getString(NAME_LABEL)
				countries.add(name)
			}
		} catch (e: JSONException) {
			Log.d("debug", "Algo falla (NETWORK)")
		}

		countries.sort()
		Log.d("debug", "País 1: ${countries[0]}")

		listener.onResponse(countries)
	}

}