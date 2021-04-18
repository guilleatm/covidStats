package com.guitmcode.covidstats

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment


class CovidDialog (private val data : CovidData) : DialogFragment() {
	override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
		if (activity == null)
			throw IllegalStateException("Activity cannot be null")
		val builder = AlertDialog.Builder(activity)
		val gigamarselo = arrayOf("                                           Today      Total",
			"Confirmed positives:     ${data.confirmedCases}   ${data.tt_confirmedCases}",
			"Confirmed deaths:         ${data.deaths}          ${data.tt_deaths}",
			"Hospitalised:                 ${data.hospitalized}          ${data.tt_hospitalized}",
			"Intensive Care Unit:      ${data.ICU}                ${data.tt_ICU}",
			"Open cases:                  ${data.openCases}   ${data.tt_openCases}",
			"Recovered:                    ${data.recovered}                   ${data.tt_recovered}")
		builder.setTitle(data.date)
			.setItems(gigamarselo) { dialog, which ->
				
			}
			.setNegativeButton("OK") { dialog, which
				-> dialog.cancel() }
		return builder.create()
	}
}