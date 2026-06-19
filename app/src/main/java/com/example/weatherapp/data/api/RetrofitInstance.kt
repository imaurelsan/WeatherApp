package com.example.weatherapp.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Object = Singleton en Kotlin — une seule instance partagée dans toute l'appli.
// Bonne pratique : on ne crée pas Retrofit à chaque appel réseau,
// c'est coûteux. On le crée une fois, on le réutilise partout.
object RetrofitInstance {

    // URL de base de l'API OpenWeatherMap
    // Toutes les requêtes partiront de cette adresse
    private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"

    // "by lazy" = l'instance n'est créée qu'au premier accès,
    // pas au démarrage de l'appli — optimisation mémoire
    val api: WeatherApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            // Gson va convertir automatiquement le JSON en data classes Kotlin
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            // Génère l'implémentation concrète de notre interface
            .create(WeatherApiService::class.java)
    }
}
