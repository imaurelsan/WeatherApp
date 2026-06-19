# 🤝 Guide de contribution

Merci de vouloir contribuer à WeatherApp ! Ce guide explique comment organiser ton travail pour faciliter les reviews et les merges.

---

## 1. Fork & Clone

```bash
# Fork sur GitHub (bouton "Fork" en haut à droite)
# Puis clone ton fork en local :
git clone https://github.com/TON_USERNAME/WeatherApp.git
cd WeatherApp

# Ajoute le repo original comme remote "upstream"
git remote add upstream https://github.com/original_owner/WeatherApp.git
```

---

## 2. Créer une branche

Utilise une branche par fonctionnalité ou correction. Le nom doit suivre cette convention :

| Type | Convention | Exemple |
|---|---|---|
| 🌟 Nouvelle fonctionnalité | `feature/nom-court` | `feature/add-5days-forecast` |
| 🐛 Correction de bug | `fix/nom-du-bug` | `fix/resolve-null-crash` |
| 🧹 Amélioration/refactor | `chore/nom-court` | `chore/update-gradle-plugin` |
| 📚 Documentation | `docs/nom-court` | `docs/update-readme` |
| 🔧 Config / CI | `ci/nom-court` | `ci/add-github-actions` |

```bash
git checkout -b feature/add-geolocation
```

---

## 3. Conventional Commits

On utilise le format [Conventional Commits](https://www.conventionalcommits.org/) pour les messages de commit :

```
<type>(<scope>): <description courte>

[corps optionnel]

[pied-de-page optionnel]
```

**Types autorisés :**
- `feat` — nouvelle fonctionnalité
- `fix` — correction de bug
- `chore` — maintenance (deps, config, build)
- `docs` — documentation
- `refactor` — refactoring sans changement de comportement
- `test` — ajout ou modification de tests
- `style` — formattage, indent, etc. (pas de logique)

**Exemples :**

```bash
git commit -m "feat(search): add city search with debounce"
git commit -m "fix(api): handle 404 when city not found"
git commit -m "chore(deps): update Retrofit to 2.9.0"
git commit -m "docs(readme): add installation guide"
```

⚠️ Le titre du commit doit :
- Être en anglais
- Être court (max 72 caractères)
- Ne pas terminer par un point
- Décrire **ce qui change**, pas ce qui existe

---

## 4. Tests & qualité du code

Avant de commiter, vérifie que :

- Le projet compile sans erreur (`./gradlew assembleDebug`)
- Le code respecte les conventions Kotlin (indentation, nommage)
- Les ViewModel/Repository sont testés si applicable
- Aucune clé API ou secret n'a été commité (voir [SECURITY.md](SECURITY.md))

---

## 5. Pull Request

### Avant de ouvrir la PR

```bash
# Synchronise avec upstream
git fetch upstream
git rebase upstream/main

# Push sur ton fork
git push origin feature/ta-fonctionnalite
```

### Ouverture de la PR

1. Va sur GitHub → **Compare & pull request**
2. **Titre** : utilise le même format Conventional Commits que ton commit
3. **Description** : explique le quoi et le pourquoi (pas le comment)
   - Résume les changements
   - Référence les issues liées (`Fixes #12`, `Closes #5`)
   - Ajoute une capture d'écran si c'est une UI change
4. Assigne des reviewers
5. Coche les cases dans la checklist si tu en as une

### Checklist PR

```
- [ ] Compilation OK
- [ ] Tests ajoutés/mis à jour
- [ ] Documentation mise à jour (si applicable)
- [ ] Aucune clé API dans le diff
- [ ] Code style respecté
```

---

## 6. Code style

**Kotlin — règles appliquées :**

- Indentation : 4 espaces (pas de tabulation)
- Classes/functions en `camelCase` ou `PascalCase` selon le type
- Constantes en `SCREAMING_SNAKE_CASE`
- Imports triés alphabétiquement, regroupés par type (`android.*`, `kotlin.*`, `com.example.*`)
- Une seule classe par fichier
- Documentation KDoc pour les fonctions publiques et les classes publiques

**XML layouts :**

- Attributs triés alphabétiquement dans chaque balise
- `ConstraintLayout` préféré pour les mises en page performantes

---

## 7. Ressources

- [Conventional Commits](https://www.conventionalcommits.org/) — spécification des messages de commit
- [Kotlin Coding Conventions](https://kotlinlang.org/docs/coding-conventions.html)
- [Android Developer Guide](https://developer.android.com/)

Merci pour ta contribution ! 🚀