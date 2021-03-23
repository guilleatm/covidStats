package com.guitmcode.covidstats

import com.android.volley.Response
import com.android.volley.VolleyError
import com.guitmcode.covidstats.model.Model

class Presenter (val view : MainView, val model: Model) {

	private var country: String? = null

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
	}

}