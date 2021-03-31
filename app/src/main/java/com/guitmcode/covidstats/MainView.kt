package com.guitmcode.covidstats

import com.guitmcode.covidstats.model.Country
import com.guitmcode.covidstats.model.Region
import com.guitmcode.covidstats.model.Subregion

interface MainView {
    var countryVisible: Boolean
    var regionVisible: Boolean
    var subregionVisible: Boolean

    var progressBarVisible: Boolean

    fun showError(message: String)

    fun showCountries(countries: List<Country>)
    fun showRegions(countries: List<Region>)
    fun showSubregions(countries: List<Subregion>)

    fun showChosenPlace(countrie: Country, region: Region?, subregion: Subregion?)
}