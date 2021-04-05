package com.guitmcode.covidstats

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

// Hemos tomado la decisión ejecutiva de no utilizar la estructura MVP

class StatisticsActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_statistics)
	}
}