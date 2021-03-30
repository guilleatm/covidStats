package com.guitmcode.covidstats

import com.guitmcode.covidstats.model.Country

interface MainView {
    var countryVisible: Boolean
    var progressBarVisible: Boolean

    fun showError(message: String)

    fun showCountries(countries: List<Country>)
    fun showChosenCountry(country: Country)

    fun showRegions(countries: List<String>)
    fun showChosenRegion(country: String)
}