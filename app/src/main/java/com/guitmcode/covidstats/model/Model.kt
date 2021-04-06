package com.guitmcode.covidstats.model

import android.content.Context
import com.android.volley.Response
import com.guitmcode.covidstats.CovidData
import java.time.LocalDate

class Model (context: Context){

	private val network = Network.getInstance(context)

	fun getCountries(listener: Response.Listener<List<Country>>, errorListener: Response.ErrorListener) {
		network.getCountries(listener, errorListener)
	}

	fun getRegions(listener: Response.Listener<List<Region>>, errorListener: Response.ErrorListener, country: Country) {
		network.getRegions(listener, errorListener, country)
	}

	fun getSubregions(listener: Response.Listener<List<Subregion>>, errorListener: Response.ErrorListener, country: Country, region: Region) {
		network.getSubregions(listener, errorListener, country, region)
	}

	fun getCovidData(listener: Response.Listener<List<CovidData>>, errorListener: Response.ErrorListener, from: LocalDate, to: LocalDate) {
		network.getCovidData(listener, errorListener, from, to)
	}
}