package com.example.demoweatherapp.view

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.demoweatherapp.R
import com.example.demoweatherapp.databinding.ActivityMainBinding
import com.example.demoweatherapp.model.WeatherModel

import com.example.demoweatherapp.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel

    private lateinit var GET: SharedPreferences
    private lateinit var SET: SharedPreferences.Editor


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        GET = getSharedPreferences(packageName, MODE_PRIVATE)
        SET = GET.edit()

        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        var cName = GET.getString("cityName", "ha noi")
        binding.edtCityName.setText(cName)
        mainViewModel.refreshData(cName!!)

        getLiveData()

        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.llData.visibility = View.GONE
            binding.tvError.visibility = View.GONE
            binding.pbLoading.visibility = View.GONE

            var cityName = GET.getString("cityName", cName)?.toLowerCase()
            binding.edtCityName.setText(cityName)
            mainViewModel.refreshData(cityName!!)
            binding.swipeRefreshLayout.isRefreshing = false
        }

        binding.imgSearchCity.setOnClickListener {
            val cityName = binding.edtCityName.text.toString()
            SET.putString("cityName", cityName)
            SET.apply()
            mainViewModel.refreshData(cityName)
            getLiveData()
            Log.i("TAG", "onCreate: " + cityName)
        }

    }


    private fun getLiveData() {

        mainViewModel.weather_data.observe(this, Observer { data ->
            data?.let {
                binding.llData.visibility = View.VISIBLE
                binding.tvCityName.text = data.name

                binding.tvDegree.text = data.main.temp.toString() + "°C"

                binding.tvHumidity.text = data.main.humidity.toString() + "%"
                binding.tvWindSpeed.text = data.wind.speed.toString()
                binding.tvLat.text = data.coord.lat.toString()
                binding.tvLon.text = data.coord.lon.toString()

            }
        })

        mainViewModel.weather_error.observe(this, Observer { error ->
            error?.let {
                if (error) {
                    binding.tvError.visibility = View.VISIBLE
                    binding.pbLoading.visibility = View.GONE
                    binding.llData.visibility = View.GONE
                } else {
                    binding.tvError.visibility = View.GONE
                }
            }
        })

        mainViewModel.weather_loading.observe(this, Observer { loading ->
            loading?.let {
                if (loading) {
                    binding.pbLoading.visibility = View.VISIBLE
                    binding.tvError.visibility = View.GONE
                    binding.llData.visibility = View.GONE
                } else {
                    binding.pbLoading.visibility = View.GONE
                }
            }
        })

    }

}