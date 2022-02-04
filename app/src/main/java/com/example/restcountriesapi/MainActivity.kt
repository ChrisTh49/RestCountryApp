package com.example.restcountriesapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Debug
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.restcountriesapi.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: CountryAdapter
    private val countryImages = mutableListOf<String>()
    private val countryNames = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.svCountries.setOnQueryTextListener(this)
        initRecyclerView()
        getAllCountries()
    }

    private fun initRecyclerView() {
        Log.i("Lalana", countryImages.toString() + countryNames)
        adapter = CountryAdapter(countryImages, countryNames)
        binding.rvCountries.layoutManager = GridLayoutManager(this, 2)
        binding.rvCountries.adapter = adapter
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://restcountries.com/v3.1/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun getAllCountries() {
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(ApiService::class.java).getAllCountries()
            val countries = call.body()
            runOnUiThread {
                if (call.isSuccessful) {
                    countryImages.clear()
                    countryNames.clear()
                    countries?.forEach { element ->
                        val countryName = element.name.countryNames
                        val countryImage = element.flags.flagImage
                        countryImages.addAll(listOf(countryImage))
                        countryNames.addAll(listOf(countryName))
                        adapter.notifyDataSetChanged()
                    }
                } else {
                    showError()
                }
            }
        }
    }

    private fun getCountryByName(countryNameQuery: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(ApiService::class.java).getCountryByName("name/$countryNameQuery")
            val countries = call.body()
            runOnUiThread {
                if (call.isSuccessful) {
                    countryImages.clear()
                    countryNames.clear()
                    countries?.forEach { element ->
                        val countryName = element.name.countryNames
                        val countryImage = element.flags.flagImage
                        countryImages.addAll(listOf(countryImage))
                        countryNames.addAll(listOf(countryName))
                        adapter.notifyDataSetChanged()
                    }
                } else {
                    showError()
                }
                hideKeyBoard()
            }
        }
    }

    private fun hideKeyBoard() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.viewRoot.windowToken, 0)
    }


    private fun showError() {
        Toast.makeText(this, "There was an error", Toast.LENGTH_SHORT).show()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if(!query.isNullOrEmpty()){
            getCountryByName(query.lowercase())
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if(newText.isNullOrEmpty()){
            getAllCountries()
        }
        return true
    }
}