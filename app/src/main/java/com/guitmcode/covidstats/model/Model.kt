package com.guitmcode.covidstats.model

import android.content.Context
import com.android.volley.Response

class Model (context: Context){

	private val network = Network.getInstance(context)

	fun getCountries(listener: Response.Listener<List<String>>, errorListener: Response.ErrorListener) {
		network.getCountries(listener, errorListener)
	}
}