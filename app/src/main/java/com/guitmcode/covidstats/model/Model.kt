package com.guitmcode.covidstats.model

import android.content.Context
import com.android.volley.Response

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
}