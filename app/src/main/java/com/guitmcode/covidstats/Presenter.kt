package com.guitmcode.covidstats

import com.android.volley.Response
import com.android.volley.VolleyError
import com.guitmcode.covidstats.model.Model

class Presenter (val view : MainView, val model: Model) {

	private var country: String? = null

	init {
		view.progressBarVisible = true
		view.countryVisible = false

		model.getCountries(object : Response.Listener<List<String>> {
			override fun onResponse(countries: List<String>?) {
				if (countries != null) {
					view.showCountries(countries)
					view.progressBarVisible = true
					view.countryVisible = true
				} else {
					view.showError("Posible JSON malformado")
				}
			}
		}, object : Response.ErrorListener {
				override fun onErrorResponse(error: VolleyError?) {
					view.showError(error.toString())
				}

		})
	}

	fun setChosenCountry(country: String) {
		TODO("Not yet implemented")
	}

}