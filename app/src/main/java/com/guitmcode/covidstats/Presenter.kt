package com.guitmcode.covidstats

import com.guitmcode.covidstats.model.Model

class Presenter (val view : MainView, val model: Model) {

	private var country: String? = null

	init {
		view.progressBarVisible = true
		view.countryVisible = false


	}

	fun setChosenCountry(country: String) {
		TODO("Not yet implemented")
	}

}