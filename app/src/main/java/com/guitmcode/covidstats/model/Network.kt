package com.guitmcode.covidstats.model

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.guitmcode.covidstats.CovidData
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList
import java.time.format.DateTimeFormatter

private const val BASE_URL = "https://api.covid19tracking.narrativa.com/api"
private const val COUNTRIES = "countries"
private const val REGIONS = "regions"
private const val SUBREGIONS = "sub_regions"
private  const val NAME_LABEL = "name"
private const val ID_LABEL = "id"

//private const val "https://api.covid19tracking.narrativa.com/api/country/spain/region/c_valenciana/sub_region/castellon?date_from=2021-03-01&date_to=2021-03-07"
private const val DATA_URL = "https://api.covid19tracking.narrativa.com/api?date_from=2021-02-10&date_to=2021-02-17"



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

	fun getRegions(listener: Response.Listener<List<Region>>, errorListener: Response.ErrorListener, country: Country) {
		val url = "$BASE_URL/$COUNTRIES/${country.id}/$REGIONS"
		Log.d("covidStats", "url regions: $url")

		val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
			{ response -> processRegions(response, listener, country) },
			{ error -> errorListener.onErrorResponse(error) }
		)
		queue.add(jsonObjectRequest)
	}

	private fun processRegions(
		response: JSONObject,
		listener: Response.Listener<List<Region>>,
		country: Country
	) {
		val regions = ArrayList<Region>()

		try {
			val countryArray : JSONArray = response.getJSONArray(COUNTRIES)

			val intermediateObject : JSONObject = countryArray.getJSONObject(0)
			val regionsArray : JSONArray = intermediateObject.getJSONArray(country.id)

			if (regionsArray.length() == 0) {
				listener.onResponse(regions)
				return
			}

			for (i in 0 until regionsArray.length()) {
				val regionObject : JSONObject = regionsArray[i] as JSONObject
				val id = regionObject.getString(ID_LABEL)
				val name = regionObject.getString(NAME_LABEL)

				regions.add(Region(id, name))
			}
		} catch (e: JSONException) {
			Log.d("covidStats", "Algo falla (NETWORK) regions")
			listener.onResponse(null)
		}

		regions.sort()
		Log.d("covidStats", "Region 1: ${regions[0].name}")

		listener.onResponse(regions)
	}

	fun getSubregions(listener: Response.Listener<List<Subregion>>, errorListener: Response.ErrorListener, country: Country, region: Region) {
		val url = "$BASE_URL/$COUNTRIES/${country.id}/$REGIONS/${region.id}/$SUBREGIONS"

		Log.d("covidStats", "url subregions: $url")

		val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
			{ response -> processSubregions(response, listener, country, region) },
			{ error -> errorListener.onErrorResponse(error) }
		)
		queue.add(jsonObjectRequest)
	}

	private fun processSubregions(
		response: JSONObject,
		listener: Response.Listener<List<Subregion>>,
		country: Country,
		region: Region
	) {
		val subregions = ArrayList<Subregion>()

		try {
			val countryArray : JSONArray = response.getJSONArray(COUNTRIES)

			val intermediateObject : JSONObject = countryArray.getJSONObject(0)
			val intermediateObject2 : JSONObject = intermediateObject.getJSONObject(country.id)
			val subregionsArray : JSONArray = intermediateObject2.getJSONArray(region.id)

			if (subregionsArray.length() == 0) {
				listener.onResponse(subregions)
				return
			}

			for (i in 0 until subregionsArray.length()) {
				val subregionObject : JSONObject = subregionsArray[i] as JSONObject
				val id = subregionObject.getString(ID_LABEL)
				val name = subregionObject.getString(NAME_LABEL)
				subregions.add(Subregion(id, name))
			}
		} catch (e: JSONException) {
			Log.d("covidStats", "Algo falla (NETWORK) subregions")
			listener.onResponse(null)
		}

		subregions.sort()
		Log.d("covidStats", "Subregion 1: ${subregions[0].name}")

		listener.onResponse(subregions)
	}


	fun getCovidData(listener: Response.Listener<List<CovidData>>, errorListener: Response.ErrorListener, from: LocalDate, to: LocalDate) {
		val url = "$DATA_URL"

		val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
			{ response -> processCovidData(response, listener, from, to) },
			{ error -> errorListener.onErrorResponse(error) }
		)
		queue.add(jsonObjectRequest)
	}

	private fun processCovidData(
		response: JSONObject,
		listener: Response.Listener<List<CovidData>>,
		from: LocalDate,
		to: LocalDate
	) {
		val data = ArrayList<CovidData>()

		try {
			// Preferible con constantes
			val dataObject : JSONObject = response.getJSONObject("dates")


			var aux: LocalDate = from
			val toPlus: LocalDate = to.plusDays(1)
			while (aux != toPlus) {

				val dateObject : JSONObject = dataObject.getJSONObject(aux.toString())
				val countriesObject : JSONObject = dateObject.getJSONObject("countries")
				val countryObject : JSONObject = countriesObject.getJSONObject("Spain")


				val confirmed = countryObject.getInt("today_confirmed")
				val deaths = countryObject.getInt("today_deaths")
				val hospitalized = countryObject.getInt("today_new_total_hospitalised_patients")
				val ICU = countryObject.getInt("today_new_intensive_care")

				data.add(CovidData(aux.toString(), confirmed, deaths, hospitalized, ICU))


				aux = aux.plusDays(1)
			}

		} catch (e: JSONException) {
			Log.d("covidStats", "Algo falla (NETWORK)")
			listener.onResponse(null)
		}

		//data.sort()

		Log.d("covidStats", "País 1: ${data[0].confirmedCases}")

		listener.onResponse(data)
	}

}