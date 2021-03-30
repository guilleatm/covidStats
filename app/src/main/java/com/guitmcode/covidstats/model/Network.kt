package com.guitmcode.covidstats.model

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

private const val BASE_URL = "https://api.covid19tracking.narrativa.com/api"
private const val COUNTRIES = "countries"
private const val REGIONS = "regions"
private const val SUBREGIONS = "sub_regions"
private  const val NAME_LABEL = "name"
private const val ID_LABEL = "id"

class Network private constructor (context : Context) {

	companion object: SingletonHolder<Network, Context>(:: Network)

	val queue = Volley.newRequestQueue(context)

	fun getCountries(listener: Response.Listener<List<Country>>, errorListener: Response.ErrorListener) {
		val url = "$BASE_URL/$COUNTRIES"
		Log.d("covidStats", "url countries: $url")
		val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
			{ response -> processCountries(response, listener) },
			{ error -> errorListener.onErrorResponse(error) }
		)
		queue.add(jsonObjectRequest)
	}

	private fun processCountries(
		response: JSONObject,
		listener: Response.Listener<List<Country>>
	) {
		val countries = ArrayList<Country>()

		try {
			val countryArray = response.getJSONArray(COUNTRIES)
			for (i in 0 until countryArray.length()) {
				val countryObject : JSONObject = countryArray[i] as JSONObject
				val id = countryObject.getString(ID_LABEL)
				val name = countryObject.getString(NAME_LABEL)
				countries.add(Country(id, name))
			}
		} catch (e: JSONException) {
			Log.d("covidStats", "Algo falla (NETWORK)")
			listener.onResponse(null)
		}

		countries.sort()

		Log.d("covidStats", "País 1: ${countries[0].name}")

		listener.onResponse(countries)
	}

	fun getRegions(listener: Response.Listener<List<String>>, errorListener: Response.ErrorListener, country: String) {
		val url = "$BASE_URL/$COUNTRIES/$country/$REGIONS"
		Log.d("covidStats", "url regions: $url")

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
			val countryArray : JSONArray = response.getJSONArray(COUNTRIES)

			val intermediateObject : JSONObject = countryArray.getJSONObject(0)
			val regionsArray : JSONArray = intermediateObject.getJSONArray("Spain") // PONER LA COUNTRIE
			//val pp : JSONObject = regionsArray[0]

			Log.d("covidStats", regionsArray.toString())

			for (i in 0 until regionsArray.length()) {
				val regionObject : JSONObject = regionsArray[i] as JSONObject
				val name = regionObject.getString(NAME_LABEL)
				regions.add(name)
			}
		} catch (e: JSONException) {
			Log.d("covidStats", "Algo falla (NETWORK) regions")
			listener.onResponse(null)
		}

		regions.sort()
		Log.d("covidStats", "Region 1: ${regions[0]}")

		listener.onResponse(regions)
	}

	fun getSubRegions(listener: Response.Listener<List<String>>, errorListener: Response.ErrorListener, country: String, region: String) {
		//val url = "$BASE_URL/$COUNTRIES/$country/$REGIONS/$region/$SUBREGIONS" // posar url bona
		val url = "$BASE_URL/$COUNTRIES/$country/$REGIONS/c_valenciana/$SUBREGIONS"

		Log.d("covidStats", "url subregions: $url")

		val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
			{ response -> processSubRegions(response, listener, country, region) },
			{ error -> errorListener.onErrorResponse(error) }
		)
		queue.add(jsonObjectRequest)
	}

	private fun processSubRegions(
		response: JSONObject,
		listener: Response.Listener<List<String>>,
		country: String,
		region: String
	) {
		val subRegions = ArrayList<String>()

		try {
			val countryArray : JSONArray = response.getJSONArray(COUNTRIES)

			val intermediateObject : JSONObject = countryArray.getJSONObject(0)
			val intermediateObject2 : JSONObject = intermediateObject.getJSONObject("Spain") // PONER LA COUNTRIE
			val subRegionsArray : JSONArray = intermediateObject2.getJSONArray("c_valenciana") // PONER REGION

			Log.d("covidStats", subRegionsArray.toString())

			for (i in 0 until subRegionsArray.length()) {
				val subRegionObject : JSONObject = subRegionsArray[i] as JSONObject
				val name = subRegionObject.getString(NAME_LABEL)
				subRegions.add(name)
			}
		} catch (e: JSONException) {
			Log.d("covidStats", "Algo falla (NETWORK) subregions")
			listener.onResponse(null)
		}

		subRegions.sort()
		Log.d("covidStats", "Subregion 1: ${subRegions[0]}")

		listener.onResponse(subRegions)
	}

}