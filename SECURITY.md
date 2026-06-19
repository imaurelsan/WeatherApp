# 🔒 Politique de sécurité — WeatherApp

La sécurité de ce projet repose sur une règle simple et non négociable : **les secrets n'ont rien à faire dans le code source**.

---

## 🚨 Règle d'or : Ne JAMAIS commiter de clé API

La clé OpenWeatherMap ou toute autre clé secrète **ne doit jamais** être commité dans le dépôt Git, même "juste pour tester" ou "juste un instant".

Si une clé est exposée sur un dépôt public (GitHub, GitLab…), elle peut être récupérée par des bots qui scannent en permanence les plateformes Git à la recherche de credentials. En moins de quelques minutes, ta clé gratuite sera épuisée par des tiers.

---

## ✅ Configuration sécurisée

### Utiliser `local.properties`

Le fichier `local.properties` (déjà dans le `.gitignore`) est le seul endroit où ta clé doit être stockée :

```properties
WEATHER_API_KEY=votre_cle_api_ici
```

**Structure du projet :**

```
WeatherApp/
├── local.properties         ← ta clé ici (NON commité)
├── .gitignore               ← exclut local.properties
├── gradle.properties        ← optionnel aussi
└── app/
    └── build.gradle.kts     ← lis la clé depuis les propriétés Gradle
```

### Exposer la clé via BuildConfig

Dans `app/build.gradle.kts`, expose la clé en tant que champ `BuildConfig` :

```kotlin
android {
    buildConfigField(
        "String",
        "WEATHER_API_KEY",
        "\"${project.findProperty("WEATHER_API_KEY") ?: ""}\""
    )
}
```

Puis dans ton code, utilise uniquement `BuildConfig.WEATHER_API_KEY`.

---

## ⚠️ Si tu as commité une clé par erreur

1. **Révoke immédiatement** la clé sur le dashboard OpenWeatherMap
2. Génère une nouvelle clé
3. Supprime le commit problématique de l'historique Git :

```bash
git filter-branch --force --index-filter \
  "git rm --cached --ignore-unmatch local.properties" \
  --prune-empty --tag-name-filter cat -- --all

git push origin --force --all
```

> Pour les gros history, utilise `git filter-repo` (plus robuste) ou contacte le support GitHub si le repo est public.

4. Mets à jour `local.properties` avec la nouvelle clé

---

## 📋 Checklist avant chaque commit

```
[ ] Je n'ai pas ajouté de clé API dans le code
[ ] Je n'ai pas ajouté de fichier secrets (.env, credentials.properties…)
[ ] local.properties est dans .gitignore ✓
[ ] google-services.json est dans .gitignore ✓
[ ] Aucune URL d'API en dur autre part dans le code
```

---

## 📞 Signaler une faille de sécurité

Si tu découvres un problème de sécurité dans ce projet (clé exposée, vulnérabilité…), ouvre une **issue privée** ou contacte directement le mainteneur. Ne crée pas de PR publique pour corriger un problème de sécurité.

Merci pour ta vigilance ! 🛡️