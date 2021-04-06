package com.guitmcode.covidstats

import android.R
import android.content.Context
import android.net.wifi.ScanResult
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder


class WifiAdapter(private val wifiList: ArrayList<ScanResult>) : RecyclerView.Adapter<WifiAdapter.ViewHolder>() {

	// holder class to hold reference
	inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
		//get view reference
		var ssid: TextView = view.findViewById(R.id.ssid) as TextView
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		// create view holder to hold reference
		return ViewHolder( LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false))
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		//set values
		holder.ssid.text =  wifiList[position].SSID
	}

	override fun getItemCount(): Int {
		return wifiList.size
	}
	// update your data
	fun updateData(scanResult: ArrayList<ScanResult>) {
		wifiList.clear()
		notifyDataSetChanged()
		wifiList.addAll(scanResult)
		notifyDataSetChanged()

	}
}