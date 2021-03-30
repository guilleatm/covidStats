package com.guitmcode.covidstats

import android.util.Log
import com.android.volley.Response
import com.android.volley.VolleyError
import com.guitmcode.covidstats.model.Model

class Presenter (val view : MainView, val model: Model) {

	private var country: String? = null
	private var region: String? = null

	init {
		view.progressBarVisible = true
		view.countryVisible = false

		model.getCountries(object : Response.Listener<List<String>> { // Se puede convertir a lambda
			override fun onResponse(countries: List<String>?) {
				if (countries != null) {
					view.showCountries(countries)
					view.progressBarVisible = true
					view.countryVisible = true
				} else {
					view.showError("Posible JSON malformado")
				}
			}
		}, object : Response.ErrorListener { // Se puede convertir a lambda
				override fun onErrorResponse(error: VolleyError?) {
					view.showError(error.toString())
				}

		})
	}

	fun setChosenCountry(country: String) {
		this.country = country
		view.showChosenCountry(this.country!!)

		// Improvisaçao

		model.getRegions(object : Response.Listener<List<String>> { // Se puede convertir a lambda
			override fun onResponse(regions: List<String>?) {
				if (regions != null) {
					Log.d("covidStats", regions[0])
					view.showRegions(regions)
					//view.progressBarVisible = true
					//view.countryVisible = true
				} else {
					view.showError("Posible JSON malformado")
				}
			}
		}, object : Response.ErrorListener { // Se puede convertir a lambda
			override fun onErrorResponse(error: VolleyError?) {
				view.showError(error.toString())
			}

		}, this.country!!)
	}


	fun setChosenRegion(region: String) {
		this.region = region
		view.showChosenRegion(this.region!!)

		// Improvisaçao

		model.getSubRegions(object : Response.Listener<List<String>> { // Se puede convertir a lambda
			override fun onResponse(subregions: List<String>?) {
				if (subregions != null) {
					Log.d("covidStats", subregions[0])
					view.showRegions(subregions)
					//view.progressBarVisible = true
					//view.countryVisible = true
				} else {
					view.showError("Posible JSON malformado")
				}
			}
		}, object : Response.ErrorListener { // Se puede convertir a lambda
			override fun onErrorResponse(error: VolleyError?) {
				view.showError(error.toString())
			}

		}, this.country!!, this.region!!)
	}



}