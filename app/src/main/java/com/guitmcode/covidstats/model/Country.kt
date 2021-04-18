package com.guitmcode.covidstats.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
class Country (@PrimaryKey val id: String, val name: String) : Comparable<Country> {
	override fun compareTo(other: Country): Int = this.name.compareTo(other.name)
}