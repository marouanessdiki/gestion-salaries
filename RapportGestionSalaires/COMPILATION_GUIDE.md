# Guide de Compilation - Rapport Gestion Salaires

## 🎯 Objectif
Ce guide vous aide à compiler le rapport LaTeX pour le projet "Système de Gestion des Salaires et Attestations RH".

## 📋 Prérequis

### 1. Installation LaTeX
**Windows :**
- Télécharger et installer MiKTeX : https://miktex.org/download
- Ou TeX Live : https://www.tug.org/texlive/

**macOS :**
- Installer MacTeX : https://www.tug.org/mactex/

**Linux (Ubuntu/Debian) :**
```bash
sudo apt-get update
sudo apt-get install texlive-full
```

### 2. Éditeur recommandé
- **TeXstudio** (gratuit, multiplateforme) : https://www.texstudio.org/
- **Overleaf** (en ligne) : https://www.overleaf.com/
- **VS Code** avec extension LaTeX Workshop

## 🚀 Compilation

### Méthode 1 : Makefile (Linux/Mac/Windows avec WSL)
```bash
# Compilation complète
make

# Compilation rapide
make quick

# Nettoyer les fichiers temporaires
make clean

# Voir le PDF
make view
```

### Méthode 2 : Ligne de commande
```bash
# Première compilation
pdflatex rapportGestionSalaires.tex

# Seconde compilation (pour les références croisées)
pdflatex rapportGestionSalaires.tex
```

### Méthode 3 : Éditeur LaTeX
1. Ouvrir `rapportGestionSalaires.tex` dans votre éditeur
2. Cliquer sur "Build" ou appuyer sur F5
3. Le PDF sera généré automatiquement

## 📁 Assets Requis

Pour une compilation complète, téléchargez ces logos et placez-les dans `assets/` :

1. **spring-boot-logo.png** - Logo Spring Boot
   - Source : https://spring.io/projects/spring-boot

2. **react-logo.png** - Logo React  
   - Source : https://react.dev/

3. **mysql-logo.png** - Logo MySQL
   - Source : https://www.mysql.com/

4. **jasper-logo.png** - Logo JasperReports
   - Source : https://community.jaspersoft.com/

5. **mui-logo.png** - Logo Material-UI
   - Source : https://mui.com/

6. **maven-logo.png** - Logo Maven
   - Source : https://maven.apache.org/

7. **axios-logo.png** - Logo Axios
   - Source : https://axios-http.com/

8. **intellij-logo.png** - Logo IntelliJ IDEA
   - Source : https://www.jetbrains.com/idea/

9. **vscode-logo.png** - Logo VS Code
   - Source : https://code.visualstudio.com/

## 🔧 Résolution des Problèmes

### Erreur : "Package not found"
```bash
# Installer les packages manquants (MiKTeX)
miktex packages install <nom-du-package>

# Ou mettre à jour la distribution
miktex packages update
```

### Erreur : "File not found" pour les images
- Vérifiez que tous les logos sont dans le dossier `assets/`
- Les noms de fichiers doivent correspondre exactement
- Format PNG recommandé

### Problème d'encodage
- Assurez-vous que le fichier est encodé en UTF-8
- Sur Windows, évitez les accents dans les chemins de fichiers

### Compilation lente
- Utilisez `make quick` pour une compilation rapide
- Commentez `\listoffigures` si non nécessaire

## 📊 Structure du Rapport Généré

Le rapport final contiendra :
- **Pages** : ~50-60 pages
- **Chapitres** : 4 + Introduction/Conclusion
- **Table des matières** automatique
- **Liste des figures** automatique
- **Code source** avec coloration syntaxique
- **Bibliographie** avec liens cliquables

## ✅ Vérification Finale

Après compilation, vérifiez :
- [ ] PDF généré sans erreurs
- [ ] Table des matières complète
- [ ] Toutes les images affichées
- [ ] Liens hypertexte fonctionnels
- [ ] Numérotation des pages correcte
- [ ] Code source bien formaté

## 📞 Support

En cas de problème :
1. Vérifiez les logs de compilation (fichier .log)
2. Consultez la documentation LaTeX
3. Recherchez l'erreur sur Stack Overflow
4. Utilisez la communauté LaTeX : https://tex.stackexchange.com/

---

**Bonne compilation !** 🎓
