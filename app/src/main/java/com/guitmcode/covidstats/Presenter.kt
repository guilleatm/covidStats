package com.guitmcode.covidstats


import android.content.Intent
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat.startActivity
import com.android.volley.Response
import com.android.volley.VolleyError
import com.guitmcode.covidstats.model.Country
import com.guitmcode.covidstats.model.Model
import com.guitmcode.covidstats.model.Region
import com.guitmcode.covidstats.model.Subregion
import java.time.LocalDate


class Presenter (val view : MainView, val model: Model) {

	private var country: Country? = null
	private var region: Region? = null
	private var subregion: Subregion? = null

	init {
		view.progressBarVisible = true

		view.countryVisible = false
		view.regionVisible = false
		view.subregionVisible = false

		view.countryButton.isEnabled = false
		view.regionButton.isEnabled = false
		view.subregionButton.isEnabled = false

		model.getCountries(object : Response.Listener<List<Country>> { // Se puede convertir a lambda
			override fun onResponse(countries: List<Country>?) {
				if (countries != null) {
					view.showCountries(countries)
					view.progressBarVisible = false
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

		view.progressBarVisible = true
		model.getRegions(object : Response.Listener<List<Region>> { // Se puede convertir a lambda
			override fun onResponse(regions: List<Region>?) {
				if (!regions.isNullOrEmpty()) {
					view.showRegions(regions)
					view.progressBarVisible = false
					view.regionVisible = true
				} else {
					view.progressBarVisible = false
					view.showError("No hay regiones")
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

		view.progressBarVisible = true
		model.getSubregions(object : Response.Listener<List<Subregion>> { // Se puede convertir a lambda
			override fun onResponse(subregions: List<Subregion>?) {
				if (!subregions.isNullOrEmpty()) {
					view.showSubregions(subregions)
					view.progressBarVisible = false
					view.subregionVisible = true
				} else {
					view.progressBarVisible = false
					view.showError("No hay subregiones")

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




	fun goCovidData(from: LocalDate, to: LocalDate, case: Int) {

		var regionAux = region; var subregionAux = subregion;

		when (case) {
			0 -> {
				regionAux = null
				subregionAux = null
			}
			1 -> {
				subregionAux = null
			}

		}

		model.getCovidData(object : Response.Listener<List<CovidData>> { // Se puede convertir a lambda
			override fun onResponse(data: List<CovidData>?) {
				view.progressBarVisible = false
				if (data != null) {
					view.goCountry(data)
				} else {
				}


			}
		}, object : Response.ErrorListener { // Se puede convertir a lambda
			override fun onErrorResponse(error: VolleyError?) {
				view.showError(error.toString())
			}

		}, this.country!!, regionAux, subregionAux, from, to)
	}

	fun checkRechosenField(country: Country?, region: Region?, subregion: Subregion?) {
		if (this.country != null && country != null) {
			if (this.country != country) {
				this.region = null
				this.subregion = null

				view.regionButton.isEnabled = false
				view.subregionVisible = false
				return
			}
		}
		if (this.region != null && region != null) {
			if (this.region != region) {
				this.subregion = null

				view.subregionButton.isEnabled = false
				return
			}
		}
	}
}