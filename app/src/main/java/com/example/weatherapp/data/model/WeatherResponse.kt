package com.example.weatherapp.data.model// Ce fichier représente la structure exacte de la réponse JSON
// renvoyée par l'API OpenWeatherMap.
// Retrofit + Gson vont automatiquement "mapper" le JSON
// vers ces data classes — chaque champ correspond à une clé JSON.

data class WeatherResponse(
    val name: String,
    val main: Main,
    val weather: List<Weather>,
    val wind: Wind,
    val sys: Sys              // ← ajoute cette ligne
)

// Bloc "sys" — contient le code pays (ex: "FR", "US")
data class Sys(
    val country: String
)

// Bloc "main" dans le JSON OpenWeatherMap
data class Main(
    val temp: Double,         // Température actuelle en Kelvin (on convertira)
    val feels_like: Double,   // Température ressentie
    val humidity: Int         // Humidité en pourcentage
)

// Bloc "weather" — c'est une liste, on prendra toujours le premier élément
data class Weather(
    val main: String,         // Condition principale ex: "Rain", "Clear"
    val description: String,  // Description détaillée ex: "light rain"
    val icon: String          // Code icône ex: "10d" (on s'en servira plus tard)
)

// Bloc "wind" dans le JSON
data class Wind(
    val speed: Double         // Vitesse du vent en m/s
)
