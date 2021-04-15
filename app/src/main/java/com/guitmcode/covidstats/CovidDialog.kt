package com.guitmcode.covidstats

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment


class CovidDialog (private val marselo : CovidData) : DialogFragment() {
	override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
		if (activity == null)
			throw IllegalStateException("Activity cannot be null")
		val builder = AlertDialog.Builder(activity)
		val gigamarselo = arrayOf("                                           Today      Total",
			"Confirmed positives:     ${marselo.confirmedCases}   ${marselo.tt_confirmedCases}",
			"Confirmed deaths:         ${marselo.deaths}          ${marselo.tt_deaths}",
			"Hospitalised:                 ${marselo.hospitalized}          ${marselo.tt_hospitalized}",
			"Intensive Care Unit:      ${marselo.ICU}                ${marselo.tt_ICU}",
			"Open cases:                  ${marselo.openCases}   ${marselo.tt_openCases}",
			"Recovered:                    ${marselo.recovered}                   ${marselo.tt_recovered}")
		builder.setTitle(marselo.date)
			.setItems(gigamarselo) { dialog, which ->
				
			}
			.setNegativeButton("OK") { dialog, which
				-> dialog.cancel() }
		return builder.create()
	}
}