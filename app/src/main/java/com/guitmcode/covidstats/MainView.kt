package com.guitmcode.covidstats

interface MainView {
    var countryVisible: Boolean
    var progressBarVisible: Boolean

    fun showError(message: String)
    fun showCountries(countries: List<String>)
    fun showChosenCountry(country: String)
}