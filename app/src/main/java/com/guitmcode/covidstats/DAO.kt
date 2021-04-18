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

	@Query("SELECT * FROM country ORDER BY name")
	fun getCountries() : List<Country>

	@Query("SELECT * FROM region ORDER BY name")
	fun getRegions() : List<Region>

}