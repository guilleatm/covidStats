package com.guitmcode.covidstats.model

class Region (val id: String, val name: String) : Comparable<Region>{
	override fun compareTo(other: Region): Int = this.name.compareTo(other.name)
}