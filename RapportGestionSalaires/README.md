# Rapport de Projet - SystÃ¨me de Gestion des Salaires

## ðŸ“‹ Description

Ce dossier contient le rapport LaTeX complet pour le projet de systÃ¨me de gestion des salaires et attestations RH dÃ©veloppÃ© chez Digital Data Service.

## ðŸš€ Compilation

### PrÃ©requis
- Distribution LaTeX complÃ¨te (TeX Live, MiKTeX, ou MacTeX)
- Ã‰diteur LaTeX (TeXstudio, Overleaf, ou VS Code avec extension LaTeX)

### Instructions de compilation

1. **Via ligne de commande :**
```bash
pdflatex rapportGestionSalaires.tex
pdflatex rapportGestionSalaires.tex  # Seconde compilation pour les rÃ©fÃ©rences
```

2. **Via Ã©diteur LaTeX :**
   - Ouvrir `rapportGestionSalaires.tex` dans votre Ã©diteur
   - Compiler avec pdfLaTeX (gÃ©nÃ©ralement F5 ou bouton Build)

### Fichiers gÃ©nÃ©rÃ©s
- `rapportGestionSalaires.pdf` - Le rapport final
- Fichiers auxiliaires (.aux, .toc, .lof, etc.) - Peuvent Ãªtre supprimÃ©s aprÃ¨s compilation

## ðŸ“ Structure

```
RapportGestionSalaires/
â”œâ”€â”€ rapportGestionSalaires.tex    # Fichier LaTeX principal
â”œâ”€â”€ assets/                       # Images et logos
â”‚   â”œâ”€â”€ ensias.png               # Logo ENSIAS
â”‚   â”œâ”€â”€ um5.png                  # Logo UM5
â”‚   â”œâ”€â”€ DDS.png                  # Logo Digital Data Service
â”‚   â””â”€â”€ [autres logos...]        # Logos des technologies
â””â”€â”€ README.md                    # Ce fichier
```

## ðŸŽ¯ Contenu du Rapport

Le rapport est structurÃ© en 4 chapitres principaux :

1. **Contexte GÃ©nÃ©ral du Projet** - PrÃ©sentation de l'environnement et de la problÃ©matique
2. **Analyse des Besoins** - SpÃ©cification des exigences fonctionnelles et non-fonctionnelles
3. **Conception et Architecture** - ModÃ©lisation et architecture du systÃ¨me
4. **ImplÃ©mentation** - DÃ©tails techniques et rÃ©sultats

## ðŸ“ Notes importantes

- Les logos des technologies (Spring Boot, React, MySQL, etc.) doivent Ãªtre tÃ©lÃ©chargÃ©s sÃ©parÃ©ment depuis leurs sites officiels
- Le rapport utilise des packages LaTeX standards disponibles dans la plupart des distributions
- La compilation peut nÃ©cessiter 2 passes pour gÃ©nÃ©rer correctement la table des matiÃ¨res et les rÃ©fÃ©rences

## ðŸ”§ Personnalisation

Pour adapter ce rapport Ã  votre projet :

1. Modifier les informations personnelles dans la page de titre
2. Adapter le contenu des chapitres selon votre implÃ©mentation
3. Ajouter vos propres captures d'Ã©cran dans le dossier assets/
4. Mettre Ã  jour la bibliographie avec vos sources

## ðŸ“Š Statistiques

- **Pages** : ~50-60 pages
- **Chapitres** : 4 + Introduction/Conclusion
- **Figures** : Diagrammes d'architecture et captures d'Ã©cran
- **Code** : Extraits de code avec syntaxe colorÃ©e

---

**Auteur** : TAMASNA Anouar  
**Projet** : SystÃ¨me de Gestion des Salaires  
**Institution** : ENSIAS - Ã‰cole Nationale SupÃ©rieure d'Informatique et d'Analyse des SystÃ¨mes  
**AnnÃ©e** : 2024-2025

## Diagrams as Code (Mermaid / Eraser)

Des versions « diagrammes en code » ont été ajoutées dans RapportGestionSalaires/diagrams/.

- Ouvrez un fichier .mmd, copiez le contenu et collez-le dans un diagramme Mermaid sur eraser.io, ou utilisez tout autre moteur Mermaid.
- Fichiers fournis: use-case, sequences (attestation/employés), class-model, workflow-attestation, architecture (backend/frontend), waterfall.

