package com.guitmcode.covidstats

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_statistics.*

// Hemos tomado la decisión ejecutiva de no utilizar la estructura MVP

class StatisticsActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_statistics)

		Log.d("covidStats", "esto va flama")


		var names = ArrayList<String>()
		names.add("Hola")
		names.add("Que tal")

		var wifiAdapter = WifiAdapter(names)

		wifiList.apply {
			// vertical layout
			layoutManager = LinearLayoutManager(applicationContext)
			// set adapter
			adapter = wifiAdapter

			// Touch handling

		}
	}
}