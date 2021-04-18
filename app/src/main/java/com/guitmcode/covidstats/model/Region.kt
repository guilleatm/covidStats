package com.guitmcode.covidstats.model

import androidx.room.Entity
import androidx.room.ColumnInfo
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(foreignKeys = [ForeignKey(entity = Country::class, parentColumns = ["id"], childColumns = ["country_id"])], tableName = "regions")
class Region (@PrimaryKey val id: String, val name: String, @ColumnInfo(name = "country_id") val country_id: String?) : Comparable<Region>{
	override fun compareTo(other: Region): Int = this.name.compareTo(other.name)
}