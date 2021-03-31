package com.guitmcode.covidstats

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.guitmcode.covidstats.model.Country
import com.guitmcode.covidstats.model.Model
import com.guitmcode.covidstats.model.Region
import com.guitmcode.covidstats.model.Subregion
import kotlinx.android.synthetic.main.activity_main.*
import javax.xml.transform.ErrorListener


class MainActivity : AppCompatActivity(), MainView {

	lateinit var countryText : TextView
	lateinit var countryTextView: AutoCompleteTextView

	lateinit var regionText : TextView
	lateinit var regionTextView: AutoCompleteTextView

	lateinit var subregionText : TextView
	lateinit var subregionTextView : AutoCompleteTextView

	lateinit var progressBar : ProgressBar
	lateinit var chosenPlace : TextView

	lateinit var presenter : Presenter

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)


		countryText = findViewById(R.id.countryText)
		countryTextView = findViewById(R.id.countryTextView)
		progressBar = findViewById(R.id.progressBar)
		chosenPlace = findViewById(R.id.chosenPlace)

		regionText = findViewById(R.id.regionText)
		regionTextView = findViewById(R.id.regionTextView)

		subregionText = findViewById(R.id.subregionText)
		subregionTextView = findViewById(R.id.subregionTextView)

		val model = Model(applicationContext)
		presenter = Presenter(this, model)
	}



	override var countryVisible: Boolean
		get() = countryText.visibility == View.VISIBLE
		set(value) {
			val v = if (value) View.VISIBLE else View.GONE
			countryText.visibility = v
			countryTextView.visibility = v
		}

	override var regionVisible: Boolean
		get() = regionText.visibility == View.VISIBLE
		set(value) {
			val v = if (value) View.VISIBLE else View.GONE
			regionText.visibility = v
			regionTextView.visibility = v
		}

	override var subregionVisible: Boolean
		get() = subregionText.visibility == View.VISIBLE
		set(value) {
			val v = if (value) View.VISIBLE else View.GONE
			subregionText.visibility = v
			subregionTextView.visibility = v
		}

	override var progressBarVisible: Boolean
		get() = progressBar.visibility == View.VISIBLE
		set(value) {
			progressBar.visibility = if (value) View.VISIBLE else View.GONE
		}

	override fun showError(message: String) {
		Toast.makeText(this, message, Toast.LENGTH_LONG).show()
	}

	override fun showCountries(countries: List<Country>) {
		val countriesStringList: List<String> = countries.map { it.name }
		val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, countriesStringList)
		countryTextView.apply{
			setAdapter(adapter)
			setText("")

			addTextChangedListener(object : TextWatcher {
				override fun afterTextChanged(p0: Editable?) {
					val textWrote = p0.toString()
					val countryWrote = Country("null_id", textWrote)

					countries.binarySearch { it.compareTo(countryWrote) }.let {
						if (it >= 0)
							presenter.setChosenCountry(countries[it])
					}
				}

				override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
					// Do nothing
				}

				override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
					// Do nothing
				}

			})
		}
	}


	override fun showRegions(regions: List<Region>) {
		val regionsStringList: List<String> = regions.map { it.name }
		val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, regionsStringList)
		regionTextView.apply{
			setAdapter(adapter)
			setText("")

			addTextChangedListener(object : TextWatcher {
				override fun afterTextChanged(p0: Editable?) {
					val textWrote = p0.toString()
					val regionWrote = Region("null_id", textWrote)
					regions.binarySearch { it.compareTo(regionWrote) }.let {
						if (it >= 0)
							presenter.setChosenRegion(regions[it])
					}
				}

				override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
					// Do nothing
				}

				override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
					// Do nothing
				}

			})
		}
	}


	override fun showSubregions(subregions: List<Subregion>) {
		val subregionsStringList: List<String> = subregions.map { it.name }
		val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, subregionsStringList)
		subregionTextView.apply{
			setAdapter(adapter)
			setText("")

			addTextChangedListener(object : TextWatcher {
				override fun afterTextChanged(p0: Editable?) {
					val textWrote = p0.toString()
					val subregionWrote = Subregion("null_id", textWrote)
					subregions.binarySearch { it.compareTo(subregionWrote) }.let {
						if (it >= 0)
							presenter.setChosenSubregion(subregions[it])
					}
				}

				override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
					// Do nothing
				}

				override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
					// Do nothing
				}

			})
		}
	}


	override fun showChosenPlace(countrie: Country, region: Region?, subregion: Subregion?) {

		var place = countrie.name

		if (region != null) {
			place = "$place, ${region.name}"

			if (subregion != null)
				place = "$place, ${subregion.name}"
		}

		chosenPlace.setText(place)
	}

}