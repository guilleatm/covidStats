package com.guitmcode.covidstats

import android.util.Log
import com.android.volley.Response
import com.android.volley.VolleyError
import com.guitmcode.covidstats.model.Country
import com.guitmcode.covidstats.model.Model
import com.guitmcode.covidstats.model.Region
import com.guitmcode.covidstats.model.Subregion

class Presenter (val view : MainView, val model: Model) {

	private var country: Country? = null
	private var region: Region? = null
	private var subregion: Subregion? = null

	init {
		view.progressBarVisible = true
		view.countryVisible = false

		model.getCountries(object : Response.Listener<List<Country>> { // Se puede convertir a lambda
			override fun onResponse(countries: List<Country>?) {
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

	fun setChosenCountry(country: Country) {
		this.country = country
		view.showChosenPlace(this.country!!, this.region, this.subregion)

		// Improvisaçao

		model.getRegions(object : Response.Listener<List<Region>> { // Se puede convertir a lambda
			override fun onResponse(regions: List<Region>?) {
				if (regions != null) {
					Log.d("covidStats", regions[0].name)
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


	fun setChosenRegion(region: Region) {
		this.region = region
		view.showChosenPlace(this.country!!, this.region, this.subregion)

		// Improvisaçao

		model.getSubregions(object : Response.Listener<List<Subregion>> { // Se puede convertir a lambda
			override fun onResponse(subregions: List<Subregion>?) {
				if (subregions != null) {
					Log.d("covidStats", subregions[0].name)
					view.showSubregions(subregions)
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


	fun setChosenSubregion(subregion: Subregion) {
		this.subregion = subregion
		view.showChosenPlace(this.country!!, this.region, this.subregion)
	}



}