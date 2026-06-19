package com.example.weatherapp.data.api

import com.example.weatherapp.data.model.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

// Interface Retrofit — chaque fonction correspond à un endpoint de l'API.
// Retrofit va générer automatiquement l'implémentation à partir
// de ces annotations au moment de la compilation.
interface WeatherApiService {

    // Endpoint GET /weather — récupère la météo actuelle d'une ville
    // @Query traduit les paramètres Kotlin en paramètres d'URL
    // URL finale exemple : /weather?q=Paris&appid=TACLÉ&units=metric
    @GET("weather")
    suspend fun getWeatherByCity(
        @Query("q") cityName: String,       // Nom de la ville
        @Query("appid") apiKey: String,     // Clé API OpenWeatherMap
        @Query("units") units: String = "metric" // "metric" = °C, "imperial" = °F
    ): WeatherResponse
    // "suspend" = fonction asynchrone, doit être appelée depuis une coroutine
}
