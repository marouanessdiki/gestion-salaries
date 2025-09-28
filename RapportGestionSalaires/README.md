# Rapport de Projet - Système de Gestion des Salaires

## 📋 Description

Ce dossier contient le rapport LaTeX complet pour le projet de système de gestion des salaires et attestations RH développé chez Digital Data Service.

## 🚀 Compilation

### Prérequis
- Distribution LaTeX complète (TeX Live, MiKTeX, ou MacTeX)
- Éditeur LaTeX (TeXstudio, Overleaf, ou VS Code avec extension LaTeX)

### Instructions de compilation

1. **Via ligne de commande :**
```bash
pdflatex rapportGestionSalaires.tex
pdflatex rapportGestionSalaires.tex  # Seconde compilation pour les références
```

2. **Via éditeur LaTeX :**
   - Ouvrir `rapportGestionSalaires.tex` dans votre éditeur
   - Compiler avec pdfLaTeX (généralement F5 ou bouton Build)

### Fichiers générés
- `rapportGestionSalaires.pdf` - Le rapport final
- Fichiers auxiliaires (.aux, .toc, .lof, etc.) - Peuvent être supprimés après compilation

## 📁 Structure

```
RapportGestionSalaires/
├── rapportGestionSalaires.tex    # Fichier LaTeX principal
├── assets/                       # Images et logos
│   ├── ensias.png               # Logo ENSIAS
│   ├── um5.png                  # Logo UM5
│   ├── DDS.png                  # Logo Digital Data Service
│   └── [autres logos...]        # Logos des technologies
└── README.md                    # Ce fichier
```

## 🎯 Contenu du Rapport

Le rapport est structuré en 4 chapitres principaux :

1. **Contexte Général du Projet** - Présentation de l'environnement et de la problématique
2. **Analyse des Besoins** - Spécification des exigences fonctionnelles et non-fonctionnelles
3. **Conception et Architecture** - Modélisation et architecture du système
4. **Implémentation** - Détails techniques et résultats

## 📝 Notes importantes

- Les logos des technologies (Spring Boot, React, MySQL, etc.) doivent être téléchargés séparément depuis leurs sites officiels
- Le rapport utilise des packages LaTeX standards disponibles dans la plupart des distributions
- La compilation peut nécessiter 2 passes pour générer correctement la table des matières et les références

## 🔧 Personnalisation

Pour adapter ce rapport à votre projet :

1. Modifier les informations personnelles dans la page de titre
2. Adapter le contenu des chapitres selon votre implémentation
3. Ajouter vos propres captures d'écran dans le dossier assets/
4. Mettre à jour la bibliographie avec vos sources

## 📊 Statistiques

- **Pages** : ~50-60 pages
- **Chapitres** : 4 + Introduction/Conclusion
- **Figures** : Diagrammes d'architecture et captures d'écran
- **Code** : Extraits de code avec syntaxe colorée

---

**Auteur** : TAMASNA Anouar  
**Projet** : Système de Gestion des Salaires  
**Institution** : ENSIAS - École Nationale Supérieure d'Informatique et d'Analyse des Systèmes  
**Année** : 2024-2025
