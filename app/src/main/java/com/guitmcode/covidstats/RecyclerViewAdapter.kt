package com.guitmcode.covidstats

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class RecyclerViewAdapter(private val list: ArrayList<CovidData>) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

	// holder class to hold reference
	inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
		//get view reference
		var title: TextView = view.findViewById(R.id.cardTitle) as TextView
		var confirmed: TextView = view.findViewById(R.id.cardConfirmed) as TextView
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		// create view holder to hold reference
		return ViewHolder( LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false))
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		//set values
		holder.title.text =  list[position].date
		holder.confirmed.text = list[position].confirmedCases.toString()
	}

	override fun getItemCount(): Int {
		return list.size
	}
	// update your data
	fun updateData(data: ArrayList<CovidData>) {
		list.clear()
		notifyDataSetChanged()
		list.addAll(data)
		notifyDataSetChanged()

	}
}