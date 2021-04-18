package com.guitmcode.covidstats.model

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.guitmcode.covidstats.CovidData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.time.LocalDate
import kotlin.collections.ArrayList
import java.time.format.DateTimeFormatter

private const val BASE_URL = "https://api.covid19tracking.narrativa.com/api"
private const val COUNTRIES = "countries"
private const val REGIONS = "regions"
private const val SUBREGIONS = "sub_regions"
private  const val NAME_LABEL = "name"
private const val ID_LABEL = "id"

//private const val "https://api.covid19tracking.narrativa.com/api/country/spain/region/c_valenciana/sub_region/castellon?date_from=2021-03-01&date_to=2021-03-07"
//private const val DATA_URL = "https://api.covid19tracking.narrativa.com/api?date_to=2021-02-10&date_from=2021-02-17"
//https://api.covid19tracking.narrativa.com/api?date_to=2021-02-17&date_from=2021-02-10
private const val DATA_URL = "https://api.covid19tracking.narrativa.com/api"
private const val COUNTRY_DATA = "country"
private const val REGION_DATA = "region"
private const val SUBREGION_DATA = "sub_region"
private const val FROM = "date_from="
private const val TO = "date_to="


//?date_from=2021-02-10&date_to=2021-02-17"



class Network private constructor (context : Context) {

	companion object: SingletonHolder<Network, Context>(:: Network)

	val queue = Volley.newRequestQueue(context)



	fun getCountries(listener: Response.Listener<List<Country>>, errorListener: Response.ErrorListener) = GlobalScope.launch(Dispatchers.Main) {


		val countries = withContext(Dispatchers.IO) {
			db.dao.getCountries()
		}



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


	fun getCovidData(listener: Response.Listener<List<CovidData>>, errorListener: Response.ErrorListener, country: Country, region: Region?, subregion: Subregion?, from: LocalDate, to: LocalDate) {

		val url = getURL(country, region, subregion, from, to)

		val hayregion = region != null
		val haysubregion = subregion != null
		var pais = country.name

		Log.d("covidStats", "HayRegion: ${hayregion}")
		Log.d("covidStats", "HaySubregion: ${haysubregion}")

		val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
			{ response -> processCovidData(response, listener, from, to, hayregion, haysubregion, pais) },
			{ error -> errorListener.onErrorResponse(error) }
		)
		queue.add(jsonObjectRequest)
	}

	private fun processCovidData(
		response: JSONObject,
		listener: Response.Listener<List<CovidData>>,
		from: LocalDate,
		to: LocalDate,
		hayregion: Boolean,
		haysubregion: Boolean,
		pais: String
	) {
		val data = ArrayList<CovidData>()

		try {
			var confirmed = 0
			var deaths = 0
			var tt_confirmed = 0
			var tt_deaths = 0
			var hospitalized = 0
			var ICU = 0
			var openCases = 0
			var recovered = 0
			var tt_hospitalized = 0
			var tt_ICU = 0
			var tt_openCases = 0
			var tt_recovered = 0

			// Preferible con constantes
			val dataObject : JSONObject = response.getJSONObject("dates")

			var aux: LocalDate = from
			val toPlus: LocalDate = to.plusDays(1)
			while (aux != toPlus) {

				val dateObject : JSONObject = dataObject.getJSONObject(aux.toString())
				val countriesObject : JSONObject = dateObject.getJSONObject("countries")
				var countryObject : JSONObject = countriesObject.getJSONObject(pais)

				if (hayregion) {
					val regionsArray : JSONArray = countryObject.getJSONArray("regions")
					val regionsObject : JSONObject = regionsArray.getJSONObject(0)
					countryObject = regionsObject
					if (haysubregion) {
						val subregionsArray : JSONArray = regionsObject.getJSONArray("sub_regions")
						val subregionsObject : JSONObject = subregionsArray.getJSONObject(0)
						countryObject = subregionsObject
					}
				}
				if (countryObject.has("today_new_confirmed")) {
					confirmed = countryObject.getInt("today_new_confirmed")
				}
				if (countryObject.has("today_new_deaths")) {
					deaths = countryObject.getInt("today_new_deaths")
				}
				if (countryObject.has("today_confirmed")) {
					tt_confirmed = countryObject.getInt("today_confirmed")
				}
				if (countryObject.has("today_deaths")) {
					tt_deaths = countryObject.getInt("today_deaths")
				}
				if (countryObject.has("today_new_total_hospitalised_patients")) {
					hospitalized = countryObject.getInt("today_new_total_hospitalised_patients")
				}
				if (countryObject.has("today_new_intensive_care")) {
					ICU = countryObject.getInt("today_new_intensive_care")
				}
				if (countryObject.has("today_new_open_cases")) {
					openCases = countryObject.getInt("today_new_open_cases")
				}
				if (countryObject.has("today_new_recovered")) {
					recovered = countryObject.getInt("today_new_recovered")
				}
				if (countryObject.has("today_total_hospitalised_patients")) {
					tt_hospitalized = countryObject.getInt("today_total_hospitalised_patients")
				}
				if (countryObject.has("today_intensive_care")) {
					tt_ICU = countryObject.getInt("today_intensive_care")
				}
				if (countryObject.has("today_open_cases")) {
					tt_openCases = countryObject.getInt("today_open_cases")
				}
				if (countryObject.has("today_recovered")) {
					tt_recovered = countryObject.getInt("today_recovered")
				}

				data.add(CovidData(aux.toString(), confirmed, deaths, hospitalized, ICU, openCases, recovered, tt_confirmed, tt_deaths, tt_hospitalized, tt_ICU, tt_openCases, tt_recovered))


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


	private fun getURL(country: Country, region: Region?, subregion: Subregion?, from: LocalDate, to: LocalDate): String {
		//https://api.covid19tracking.narrativa.com/api/country/spain/region/c_valenciana/sub_region/castellon?date_from=2021-03-01&date_to=2021-03-07

		if (subregion != null)
			return "$BASE_URL/$COUNTRY_DATA/${country.id}/$REGION_DATA/${region!!.id}/$SUBREGION_DATA/${subregion.id}?$FROM${from.toString()}&$TO${to.toString()}"

		if (region != null)
			return "$BASE_URL/$COUNTRY_DATA/${country.id}/$REGION_DATA/${region.id}?$FROM${from.toString()}&$TO${to.toString()}"

		return "$BASE_URL/$COUNTRY_DATA/${country.id}?$FROM${from.toString()}&$TO${to.toString()}"

	}

}