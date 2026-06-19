package com.example.weatherapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.api.RetrofitInstance
import com.example.weatherapp.data.model.WeatherResponse
import kotlinx.coroutines.launch

// WeatherViewModel hérite de ViewModel — Android gère son cycle de vie.
// Il survit aux rotations d'écran, contrairement à une variable dans MainActivity.
// Règle d'or : jamais de référence à un Context ou une View ici.
class WeatherViewModel : ViewModel() {

    // MutableLiveData = version "privée" modifiable uniquement depuis ce ViewModel
    // On l'expose en LiveData "publique" en lecture seule vers l'UI
    private val _weatherData = MutableLiveData<WeatherResponse>()
    val weatherData: LiveData<WeatherResponse> = _weatherData

    // LiveData dédié aux erreurs — message texte à afficher à l'utilisateur
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    // LiveData qui indique si un chargement est en cours
    // Permet d'afficher/cacher un indicateur de chargement dans l'UI
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    // Remplace cette valeur par ta propre clé API OpenWeatherMap
    // Inscription gratuite sur : https://openweathermap.org/api
    private val apiKey = "VOTRE_CLE_API_ICI"

    // Fonction principale appelée depuis MainActivity quand l'utilisateur
    // tape un nom de ville et valide sa recherche
    fun fetchWeather(cityName: String) {

        // Sécurité basique — on n'envoie pas de requête si le champ est vide
        if (cityName.isBlank()) {
            _errorMessage.value = "Veuillez entrer un nom de ville"
            return
        }

        // viewModelScope = coroutine liée au cycle de vie du ViewModel
        // Elle sera automatiquement annulée si le ViewModel est détruit
        viewModelScope.launch {

            // On signale à l'UI que le chargement commence
            _isLoading.value = true

            // try/catch indispensable pour toute requête réseau —
            // pas de connexion, ville introuvable, timeout... tout peut arriver
            try {
                val response = RetrofitInstance.api.getWeatherByCity(
                    cityName = cityName,
                    apiKey = apiKey
                )
                // Succès — on pousse les données vers l'UI via LiveData
                _weatherData.value = response

            } catch (e: Exception) {
                // Échec — on pousse un message d'erreur lisible
                // e.message contient le détail technique de l'erreur
                _errorMessage.value = "Ville introuvable ou erreur réseau : ${e.message}"
            } finally {
                // finally est TOUJOURS exécuté, succès ou échec —
                // on arrête l'indicateur de chargement dans tous les cas
                _isLoading.value = false
            }
        }
    }

    // Fonction utilitaire — conversion Kelvin vers Celsius
    // Utile si on bascule en mode "imperial" plus tard
    fun kelvinToCelsius(kelvin: Double): Int {
        return (kelvin - 273.15).toInt()
    }
}
