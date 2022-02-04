package com.example.restcountriesapi

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.restcountriesapi.databinding.ItemCountriesBinding
import com.squareup.picasso.Picasso

class CountryViewHolder(view:View):RecyclerView.ViewHolder(view) {
    private val binding = ItemCountriesBinding.bind(view)
    fun bind(flagImages: String, countryNames:String){
        Picasso.get().load(flagImages).into(binding.ivFlag)
        binding.tvCountryName.text = countryNames
    }
}