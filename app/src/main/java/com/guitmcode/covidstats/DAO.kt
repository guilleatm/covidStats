package com.guitmcode.covidstats

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.guitmcode.covidstats.model.Country

@Dao
interface DAO {
	@Insert
	fun insertCountry(country: Country)

	@Insert
	fun insertCountries(countries: ArrayList<Country>)

	@Query("SELECT * FROM country ORDER BY name")
	fun getCountry() : Country

}