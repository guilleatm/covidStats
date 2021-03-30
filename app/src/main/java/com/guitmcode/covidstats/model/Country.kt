package com.guitmcode.covidstats.model

class Country (val id: String, val name: String) : Comparable<Country>{
	override fun compareTo(other: Country): Int = this.name.compareTo(other.name)


}