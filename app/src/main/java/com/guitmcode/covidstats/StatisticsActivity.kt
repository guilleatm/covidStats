package com.guitmcode.covidstats

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_statistics.*


// Hemos tomado la decisión ejecutiva de no utilizar la estructura MVP

class StatisticsActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_statistics)

		Log.d("covidStats", "esto va flama")


		val intent = intent
		val list = intent.getSerializableExtra("data") as ArrayList<CovidData>




		var myAdapter = RecyclerViewAdapter(list)

		wifiList.apply {
			// vertical layout
			layoutManager = LinearLayoutManager(applicationContext)
			// set adapter
			adapter = myAdapter

			// Touch handling

		}
	}
}