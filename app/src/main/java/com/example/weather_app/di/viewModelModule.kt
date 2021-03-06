package com.example.weatherapp.di

import com.example.weather_app.ui.weather_screen.WeatherViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule=module{
    viewModel{ WeatherViewModel(get()) }
}