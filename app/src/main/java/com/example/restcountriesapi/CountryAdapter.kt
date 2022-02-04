package com.example.restcountriesapi

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class CountryAdapter(val countryImages:List<String>, val countryNames:List<String>):RecyclerView.Adapter<CountryViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CountryViewHolder(layoutInflater.inflate(R.layout.item_countries, parent, false))
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        val countriesImages = countryImages[position]
        val countriesNames = countryNames[position]

        holder.bind(countriesImages, countriesNames)
    }

    override fun getItemCount(): Int = countryImages.size
}