package com.example.weatherapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.api.RetrofitInstance
import com.example.weatherapp.data.model.WeatherResponse
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {

    // --- LiveData exposés à la MainActivity ---

    // Données météo — mises à jour uniquement en cas de succès
    private val _weatherData = MutableLiveData<WeatherResponse>()
    val weatherData: LiveData<WeatherResponse> = _weatherData

    // État de chargement — true pendant l'appel réseau, false sinon
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    // Message d'erreur — mis à jour uniquement en cas d'échec
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    // --- Appel réseau ---

    // fetchWeather() est appelé depuis MainActivity quand l'utilisateur clique sur "Rechercher"
    fun fetchWeather(cityName: String, apiKey: String) {
        viewModelScope.launch {
            // On signale le début du chargement
            _isLoading.value = true

            try {
                // Appel direct à Retrofit — suspend function, exécutée dans la coroutine
                val response = RetrofitInstance.api.getWeatherByCity(
                    cityName = cityName,
                    apiKey = apiKey
                )
                // Succès — on met à jour le LiveData des données
                _weatherData.value = response

            } catch (e: Exception) {
                // Échec — ville introuvable, pas de réseau, clé invalide...
                _errorMessage.value = "Erreur : ${e.message ?: "Problème inconnu"}"

            } finally {
                // Dans tous les cas, on arrête l'indicateur de chargement
                _isLoading.value = false
            }
        }
    }

    // --- Utilitaire de conversion ---

    // L'API retourne les températures en Kelvin quand units n'est pas "metric"
    // Ici on force "metric" dans le service donc c'est déjà en °C,
    // mais on garde la fonction pour usage futur ou si tu changes les units
    fun kelvinToCelsius(kelvin: Double): Int {
        return (kelvin - 273.15).toInt()
    }
}
