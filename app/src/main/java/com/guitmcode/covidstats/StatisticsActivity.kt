package com.guitmcode.covidstats

import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

		listID.apply {
			// vertical layout
			layoutManager = LinearLayoutManager(applicationContext)
			// set adapter
			adapter = myAdapter

			// Touch handling

			listID.addOnItemTouchListener(object : RecyclerView.OnItemTouchListener {
				override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
					Toast.makeText(applicationContext, "Que pasa locoo", Toast.LENGTH_SHORT).show()
				}

				override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
					return true
				}

				override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
				}

			})

		}


	}
}