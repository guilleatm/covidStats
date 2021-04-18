package com.guitmcode.covidstats

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.guitmcode.covidstats.model.Country
import com.guitmcode.covidstats.model.Region

@Dao
interface DAO {
	@Insert
	fun insertCountry(country: Country)

	@Insert
	fun insertCountries(countries: List<Country>)

	@Insert
	fun insertRegions(regions: List<Region>)

	/*@Query("SELECT * FROM country ORDER BY name")
	fun getCountry() : Country*/

	@Query("SELECT * FROM countries ORDER BY name")
	fun getCountries() : List<Country>

	@Query("SELECT * FROM regions JOIN countries ON (countries.id = regions.country_id) WHERE regions.country_id = :id ORDER BY name")
	fun getRegions(id: String) : List<Region>

	/*
	@Query("SELECT * FROM subregions JOIN regions ON (regions.id = subregions.region_id) WHERE subregions.region_id = id ORDER BY name")
	fun getSubregions() : List<Subregions>
*/
}


