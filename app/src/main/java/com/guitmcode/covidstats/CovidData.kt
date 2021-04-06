package com.guitmcode.covidstats

import java.io.Serializable

class CovidData (val date: String, val confirmedCases: Int, val deaths: Int, val hospitalized: Int, val ICU: Int) : Serializable {}