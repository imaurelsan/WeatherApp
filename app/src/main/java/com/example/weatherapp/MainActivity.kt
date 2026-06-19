package com.example.weatherapp

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.weatherapp.ui.WeatherViewModel
import com.example.weatherapp.BuildConfig


class MainActivity : AppCompatActivity() {

    // Déclaration des vues — on les récupère depuis le layout XML
    private lateinit var etCityName: EditText
    private lateinit var btnSearch: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var cardWeather: LinearLayout
    private lateinit var tvCityName: TextView
    private lateinit var tvWeatherDescription: TextView
    private lateinit var tvTemperature: TextView
    private lateinit var tvFeelsLike: TextView
    private lateinit var tvHumidity: TextView
    private lateinit var tvWind: TextView
    private lateinit var tvError: TextView

    // viewModels() = délégation Android qui crée et gère le cycle de vie du ViewModel
    // Si l'écran tourne, le même ViewModel est réutilisé — pas de perte de données
    private val viewModel: WeatherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // On connecte cette Activity à son layout XML
        setContentView(R.layout.activity_main)

        // Initialisation de toutes les vues via leurs IDs définis dans le XML
        etCityName = findViewById(R.id.etCityName)
        btnSearch = findViewById(R.id.btnSearch)
        progressBar = findViewById(R.id.progressBar)
        cardWeather = findViewById(R.id.cardWeather)
        tvCityName = findViewById(R.id.tvCityName)
        tvWeatherDescription = findViewById(R.id.tvWeatherDescription)
        tvTemperature = findViewById(R.id.tvTemperature)
        tvFeelsLike = findViewById(R.id.tvFeelsLike)
        tvHumidity = findViewById(R.id.tvHumidity)
        tvWind = findViewById(R.id.tvWind)
        tvError = findViewById(R.id.tvError)

        setupListeners()
        observeViewModel()
    }

    private fun setupListeners() {
        // Clic sur le bouton Rechercher
        btnSearch.setOnClickListener {
            launchSearch()
        }

        // Permet aussi de lancer la recherche depuis le clavier
        // quand l'utilisateur appuie sur "Entrée" / "Rechercher"
        etCityName.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                actionId == EditorInfo.IME_ACTION_DONE) {
                launchSearch()
                true
            } else false
        }
    }

    private fun launchSearch() {
        val city = etCityName.text.toString().trim()

        // Validation basique — on n'appelle pas l'API si le champ est vide
        if (city.isEmpty()) {
            Toast.makeText(this, "Veuillez entrer un nom de ville", Toast.LENGTH_SHORT).show()
            return
        }

        // On lance la recherche dans le ViewModel
        viewModel.fetchWeather(
            cityName = city,
            apiKey = BuildConfig.OPENWEATHER_API_KEY // ✅ Clé récupérée depuis local.properties
        )
    }

    private fun observeViewModel() {
        // On observe le LiveData isLoading — à chaque changement, ce bloc s'exécute
        viewModel.isLoading.observe(this) { isLoading ->
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            btnSearch.isEnabled = !isLoading
        }

        // On observe les données météo — déclenché uniquement en cas de succès
        viewModel.weatherData.observe(this) { weather ->
            // On cache le message d'erreur s'il était visible
            tvError.visibility = View.GONE
            // On affiche la carte de résultats
            cardWeather.visibility = View.VISIBLE

            // Remplissage de chaque TextView avec les données de l'API
            tvCityName.text = "${weather.name}, ${weather.sys.country}"
            tvWeatherDescription.text = weather.weather.firstOrNull()?.description
                ?.replaceFirstChar { it.uppercase() } ?: ""

            // L'API OpenWeather retourne la température en Kelvin par défaut
            // On convertit via la fonction utilitaire du ViewModel
            val tempCelsius = weather.main.temp.toInt()
            val feelsLike = weather.main.feels_like.toInt()

            tvTemperature.text = "$tempCelsius°C"
            tvFeelsLike.text = "Ressenti : $feelsLike°C"
            tvHumidity.text = "💧 Humidité : ${weather.main.humidity}%"

            // La vitesse du vent est en m/s dans l'API — conversion en km/h
            val windKmh = (weather.wind.speed * 3.6).toInt()
            tvWind.text = "💨 Vent : $windKmh km/h"
        }

        // On observe les erreurs — déclenché uniquement en cas d'échec
        viewModel.errorMessage.observe(this) { error ->
            cardWeather.visibility = View.GONE
            tvError.visibility = View.VISIBLE
            tvError.text = error
        }
    }
}
