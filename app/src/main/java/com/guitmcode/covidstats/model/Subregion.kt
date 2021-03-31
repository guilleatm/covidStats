package com.guitmcode.covidstats.model

class Subregion (val id: String, val name: String) : Comparable<Subregion>{
	override fun compareTo(other: Subregion): Int = this.name.compareTo(other.name)
}