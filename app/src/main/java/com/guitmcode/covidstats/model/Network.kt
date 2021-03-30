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
private const val REGIONS = "regions"
private  const val NAME_LABEL = "name"

class Network private constructor (context : Context) {

	companion object: SingletonHolder<Network, Context>(:: Network)

	val queue = Volley.newRequestQueue(context)

	fun getCountries(listener: Response.Listener<List<String>>, errorListener: Response.ErrorListener) {
		val url = "$BASE_URL/$COUNTRIES"
		Log.d("covidStats", "url: $url")
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
			Log.d("covidStats", "Algo falla (NETWORK)")
			listener.onResponse(null)
		}

		countries.sort()
		Log.d("covidStats", "País 1: ${countries[0]}")

		listener.onResponse(countries)
	}

	fun getRegion(listener: Response.Listener<List<String>>, errorListener: Response.ErrorListener, country: String) {
		val url = "$BASE_URL/$COUNTRIES/$country/$REGIONS"

		val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
			{ response -> processRegions(response, listener, country) },
			{ error -> errorListener.onErrorResponse(error) }
		)
		queue.add(jsonObjectRequest)
	}

	private fun processRegions(
		response: JSONObject,
		listener: Response.Listener<List<String>>,
		country: String
	) {
		val regions = ArrayList<String>()

		try {
			val regionsArray = response.getJSONArray(country)
			for (i in 0 until regionsArray.length()) {
				val regionObject : JSONObject = regionsArray[i] as JSONObject
				val name = regionObject.getString(NAME_LABEL)
				regions.add(name)
			}
		} catch (e: JSONException) {
			Log.d("covidStats", "Algo falla (NETWORK)")
			listener.onResponse(null)
		}

		regions.sort()
		Log.d("covidStats", "Region 1: ${regions[0]}")

		listener.onResponse(regions)
	}

}