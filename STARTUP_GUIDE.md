# Guide de Démarrage des Applications

## 🚀 Démarrage des Applications

### Frontend (React)
```powershell
cd gestion-salaries-frontend
npm start
```
- **URL** : http://localhost:3000
- **Status** : ✅ En cours de démarrage

### Backend (Spring Boot)
```powershell
cd gestion-salaries-backend
mvnw.cmd spring-boot:run
```
- **URL** : http://localhost:8080
- **Status** : ✅ En cours de démarrage

## 🔍 Vérification du Fonctionnement

### 1. Vérifier que les applications démarrent
- **Frontend** : Ouvrir http://localhost:3000 dans votre navigateur
- **Backend** : Vérifier les logs dans le terminal

### 2. Tester la génération de PDF
1. Se connecter à l'application via le frontend
2. Aller dans la section "Attestations"
3. Créer une nouvelle attestation (Travail ou Salaire)
4. Vérifier que le PDF est généré sans erreur

### 3. Vérifier les logs
Les logs du backend doivent montrer :
```
INFO - PDF generated successfully: pdfs/attestation_X.pdf
```

## 🛠️ Commandes PowerShell Utiles

### Navigation
```powershell
# Aller au répertoire racine
cd "C:\Users\aniac\IdeaProjects\gestion-salaries (1)"

# Aller au frontend
cd gestion-salaries-frontend

# Aller au backend
cd gestion-salaries-backend
```

### Démarrage des Applications
```powershell
# Frontend
cd gestion-salaries-frontend
npm start

# Backend (dans un autre terminal)
cd gestion-salaries-backend
mvnw.cmd spring-boot:run
```

### Arrêt des Applications
- **Frontend** : `Ctrl + C` dans le terminal
- **Backend** : `Ctrl + C` dans le terminal

## 🔧 Résolution de Problèmes

### Frontend ne démarre pas
1. Vérifier que Node.js est installé : `node --version`
2. Installer les dépendances : `npm install`
3. Redémarrer : `npm start`

### Backend ne démarre pas
1. Vérifier que Java 17+ est installé : `java --version`
2. Vérifier que Maven est disponible : `mvnw.cmd --version`
3. Redémarrer : `mvnw.cmd spring-boot:run`

### Erreur de port déjà utilisé
1. Identifier le processus : `netstat -ano | findstr :8080`
2. Tuer le processus : `taskkill /PID <PID> /F`
3. Redémarrer l'application

## 📊 Status Actuel

- ✅ **Migration OpenPDF → Jasper Reports** : Terminée
- ✅ **Images déplacées** : Dans `src/main/resources/images/`
- ✅ **Template Jasper Reports** : Créé et configuré
- ✅ **Tests unitaires** : Passent
- 🔄 **Frontend** : En cours de démarrage
- 🔄 **Backend** : En cours de démarrage

## 🎯 Prochaines Étapes

1. **Tester la génération de PDF** via l'interface utilisateur
2. **Vérifier la qualité** des PDFs générés
3. **Comparer** avec les anciens PDFs OpenPDF
4. **Optimiser** si nécessaire

## 📞 Support

En cas de problème :
1. Vérifier les logs dans les terminaux
2. Consulter le fichier `test-pdf-generation.md`
3. Vérifier que toutes les dépendances sont installées
