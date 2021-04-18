package com.guitmcode.covidstats

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.guitmcode.covidstats.model.Country
import com.guitmcode.covidstats.model.Region
import com.guitmcode.covidstats.model.Subregion

@Dao
interface DAO {
/*
	@Insert
	fun insertCountry(country: Country)

	@Query("SELECT * FROM country ORDER BY name")
	fun getCountry() : Country
*/
	@Insert
	fun insertCountries(countries: List<Country>)

	@Insert
	fun insertRegions(regions: List<Region>)

	@Insert
	fun insertSubregions(subregions: List<Subregion>)



	@Query("SELECT * FROM countries ORDER BY name")
	fun getCountries() : List<Country>

	@Query("SELECT * FROM regions WHERE regions.country_id = :id ORDER BY name")
	fun getRegions(id: String) : List<Region>

	@Query("SELECT * FROM subregions WHERE subregions.region_id = :id ORDER BY name")
	fun getSubegions(id: String) : List<Subregion>

	/*
	@Query("SELECT * FROM subregions JOIN regions ON (regions.id = subregions.region_id) WHERE subregions.region_id = id ORDER BY name")
	fun getSubregions() : List<Subregions>
*/
	//"SELECT * FROM regions JOIN countries ON (regions.country_id = countries.id) WHERE regions.country_id = :id ORDER BY name"
}


