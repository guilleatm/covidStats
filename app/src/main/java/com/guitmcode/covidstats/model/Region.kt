package com.guitmcode.covidstats.model

import androidx.room.Entity
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

@Entity
class Region (@PrimaryKey val id: String, val name: String) : Comparable<Region>{
	override fun compareTo(other: Region): Int = this.name.compareTo(other.name)
}