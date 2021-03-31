package com.guitmcode.covidstats

import com.guitmcode.covidstats.model.Country
import com.guitmcode.covidstats.model.Region
import com.guitmcode.covidstats.model.Subregion

interface MainView {
    var countryVisible: Boolean
    var progressBarVisible: Boolean

    fun showError(message: String)

    fun showCountries(countries: List<Country>)
    fun showChosenCountry(country: Country)

    fun showRegions(countries: List<Region>)
    fun showChosenRegion(country: Region)

    fun showSubregions(countries: List<Subregion>)
    fun showChosenSubregion(country: Subregion)
}