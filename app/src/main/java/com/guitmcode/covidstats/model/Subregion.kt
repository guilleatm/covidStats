package com.guitmcode.covidstats.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(foreignKeys = [ForeignKey(entity = Region::class, parentColumns = ["id"], childColumns = ["region_id"])], tableName = "subregions")
class Subregion (@PrimaryKey val id: String, val name: String, @ColumnInfo(name = "region_id") val region_id: String?) : Comparable<Subregion>{
	override fun compareTo(other: Subregion): Int = this.name.compareTo(other.name)
}