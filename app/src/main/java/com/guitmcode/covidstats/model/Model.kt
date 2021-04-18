package com.guitmcode.covidstats.model

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.android.volley.Response
import com.guitmcode.covidstats.CovidData
import com.guitmcode.covidstats.DAO
import com.guitmcode.covidstats.Database
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate

class Model (context: Context){

	private val network = Network.getInstance(context)
	val dao : DAO
	public var db : Database

	init {

		db = Room.databaseBuilder(context, com.guitmcode.covidstats.Database::class.java, "database").fallbackToDestructiveMigration().build()
		dao = db.getDAO()

	}

	fun getCountries(listener: Response.Listener<List<Country>>, errorListener: Response.ErrorListener) = GlobalScope.launch(Dispatchers.Main)
		{
		val countries = withContext(Dispatchers.IO) {
			dao.getCountries()
		}

		if (countries.isEmpty()) {
			network.getCountries(Response.Listener {
				GlobalScope.launch {
					dao.insertCountries(it)
				}
				listener.onResponse(it)
			}, errorListener
			)
		}
		else {
			listener.onResponse(countries)
		}
		//network.getCountries(listener, errorListener)
	}

	fun getRegions(listener: Response.Listener<List<Region>>, errorListener: Response.ErrorListener, country: Country) = GlobalScope.launch(Dispatchers.Main) {
		val regions = withContext(Dispatchers.IO) {
			dao.getRegions(country.id)
		}

		if (regions.isEmpty()) {
			network.getRegions(Response.Listener {
				GlobalScope.launch {
					dao.insertRegions(it)
				}
				listener.onResponse(it)
			}, errorListener, country
			)
		}
		else {
			listener.onResponse(regions)
		}
		//network.getRegions(listener, errorListener, country)
	}

	fun getSubregions(listener: Response.Listener<List<Subregion>>, errorListener: Response.ErrorListener, country: Country, region: Region) = GlobalScope.launch(Dispatchers.Main) {
		val subregions = withContext(Dispatchers.IO) {
			dao.getSubegions(region.id)
		}

		if (subregions.isEmpty()) {
			network.getSubregions(Response.Listener {
				GlobalScope.launch {
					dao.insertSubregions(it)
				}
				listener.onResponse(it)
			}, errorListener, country, region
			)
		}
		else {
			listener.onResponse(subregions)
		}

		//network.getSubregions(listener, errorListener, country, region)
	}

	fun getCovidData(listener: Response.Listener<List<CovidData>>, errorListener: Response.ErrorListener, country: Country, region: Region?, subregion: Subregion?, from: LocalDate, to: LocalDate) {
		network.getCovidData(listener, errorListener, country, region, subregion, from, to)
	}
}