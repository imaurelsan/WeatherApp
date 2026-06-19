# 🌤️ WeatherApp

Application Android native développée en Kotlin permettant de consulter
la météo en temps réel pour n'importe quelle ville dans le monde.

---

## 📱 Fonctionnalités

- Recherche météo par nom de ville
- Affichage de la température, conditions météo et icône dynamique
- Interface simple et intuitive
- Gestion des erreurs réseau et saisie invalide

---

## 🛠️ Stack technique

| Technologie | Usage |
|---|---|
| Kotlin | Langage principal |
| Android SDK | Framework mobile |
| Retrofit | Appels API REST |
| ViewModel + LiveData | Architecture MVVM |
| OpenWeatherMap API | Source des données météo |

---

## 🚀 Lancer le projet

### Prérequis

- Android Studio Hedgehog ou version supérieure
- JDK 17+
- Un compte [OpenWeatherMap](https://openweathermap.org/api) (gratuit)

### Installation

1. Clone le repo

```bash
git clone https://github.com/imaurelsan/weatherapp.git
cd weatherapp
```

2. Crée un fichier `local.properties` à la racine du projet

```properties
OPENWEATHER_API_KEY=ta_clé_api_ici
```

3. Ouvre le projet dans Android Studio et lance **`Sync Now`**

4. Lance l'application sur un émulateur ou un appareil physique



## 🔑 Obtenir une clé API
1. Crée un compte sur https://openweathermap.org/
2. Va dans `**My API Keys**`
3. Copie ta clé et colle-la dans `local.properties`

⚠️ Ne commite jamais ton fichier local.properties — il est dans le .gitignore

## 🏗️ Architecture
app/
├── data/
│   ├── api/          # Interface Retrofit + modèles JSON
│   └── repository/   # Couche d'accès aux données
├── ui/
│   ├── viewmodel/    # ViewModel MVVM
│   └── view/         # Activities / Fragments
└── utils/            # Extensions et helpers


## 📸 Screenshots
À venir

## 📄 Licence
Ce projet est distribué sous licence MIT — utilisation libre, modification 
libre, distribution libre. Voir le fichier `[LICENSE](./LICENSE)` pour les détails.
```