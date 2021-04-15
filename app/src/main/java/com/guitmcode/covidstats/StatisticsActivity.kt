package com.guitmcode.covidstats

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_statistics.*


// Hemos tomado la decisión ejecutiva de no utilizar la estructura MVP

class StatisticsActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_statistics)

		Log.d("covidStats", "esto va flama")

		val myIntent = intent
		val list = myIntent.getSerializableExtra("data") as ArrayList<CovidData>
		var myAdapter = RecyclerViewAdapter(list, onClickListener = { view, CovidData -> showDataDialog(view, CovidData) })

		listID.apply {
			// vertical layout
			layoutManager = LinearLayoutManager(applicationContext)
			// set adapter
			adapter = myAdapter

			// Touch handling

			/*listID.addOnItemTouchListener(object : RecyclerView.OnItemTouchListener {
				override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
					showFruitDialog(rv)
				}

				override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
					return true
				}

				override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
				}

			})*/
		}
	}

	fun showDataDialog(view: View?, covidData: CovidData) {
		val dialog = CovidDialog(covidData)
		dialog.show(supportFragmentManager, "marselo videogames")
	}
}