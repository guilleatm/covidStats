package com.guitmcode.covidstats

import android.widget.Button
import com.guitmcode.covidstats.model.Country
import com.guitmcode.covidstats.model.Region
import com.guitmcode.covidstats.model.Subregion

interface MainView {
    var countryVisible: Boolean
    var regionVisible: Boolean
    var subregionVisible: Boolean

    var progressBarVisible: Boolean

    var countryButton: Button
    var regionButton: Button
    var subregionButton: Button

    fun showError(message: String)

    fun showCountries(countries: List<Country>)
    fun showRegions(countries: List<Region>)
    fun showSubregions(countries: List<Subregion>)

    fun showChosenPlace(countrie: Country, region: Region?, subregion: Subregion?)

    fun goCountry(data: List<CovidData>)
}
