package com.guitmcode.covidstats

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.guitmcode.covidstats.model.Country
import com.guitmcode.covidstats.model.Region
import com.guitmcode.covidstats.model.SingletonHolder
import com.guitmcode.covidstats.model.Subregion

@Database(entities = [Country::class, Region::class], version = 2, exportSchema = false)



abstract class Database : RoomDatabase() {

	abstract fun getDAO(): DAO
}


/*
abstract class Database private constructor(context: Context) : RoomDatabase() {

	companion object: SingletonHolder<Database, Context>(androidx.room.Database())

	abstract fun getDAO(): DAO
}
*/