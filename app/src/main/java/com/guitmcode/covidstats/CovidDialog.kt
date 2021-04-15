package com.guitmcode.covidstats

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class CovidDialog : DialogFragment() {
	override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
		if (activity == null)
			throw IllegalStateException("Activity cannot be null")
		val builder = AlertDialog.Builder(activity)
		builder.setTitle("Esto tira pero como una bestia")
			.setMessage("Quien necesita teoria cuando tengo stackoverflow")
			.setPositiveButton("NO", null)
			.setNegativeButton("OK") { dialog, which
				-> dialog.cancel() }
		return builder.create()
	}
}