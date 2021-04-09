package com.guitmcode.covidstats

import java.io.Serializable

class CovidData (val date: String,
				 val confirmedCases: Int, val deaths: Int, val hospitalized: Int, val ICU: Int, val openCases: Int, val recovered: Int,
				 val tt_confirmedCases: Int, val tt_deaths: Int, val tt_hospitalized: Int, val tt_ICU: Int, val tt_openCases: Int, val tt_recovered: Int
				) : Serializable {}