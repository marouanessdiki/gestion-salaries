# Test de Génération de PDF avec Jasper Reports

## Problème Résolu ✅

Le problème était que les images (`logo.png` et `cachet.png`) n'étaient pas dans le classpath de l'application.

### Solution Appliquée

1. **Images déplacées** : Les images ont été copiées de `images/` vers `src/main/resources/images/`
2. **Code modifié** : Le service charge maintenant les images via `ClassPathResource`
3. **Gestion d'erreur** : Ajout de fallback si les images ne peuvent pas être chargées

## Test de Fonctionnement

### 1. Vérifier que l'application démarre
```bash
./mvnw spring-boot:run
```

### 2. Tester la génération d'attestation
- Ouvrir le frontend : `http://localhost:3000`
- Se connecter à l'application
- Aller dans la section des attestations
- Créer une nouvelle attestation (Travail ou Salaire)
- Vérifier que le PDF est généré sans erreur

### 3. Vérifier les logs
Les logs doivent montrer :
```
INFO - PDF generated successfully: pdfs/attestation_X.pdf
```

### 4. Vérifier le fichier généré
- Le fichier PDF doit être créé dans le répertoire `pdfs/`
- Le PDF doit contenir le logo et le cachet de l'entreprise
- Le contenu doit être correctement formaté

## Structure des Ressources

```
src/main/resources/
├── images/
│   ├── logo.png
│   └── cachet.png
├── reports/
│   └── attestation_template.jrxml
└── application.properties
```

## En Cas de Problème

### Erreur : "Template not found"
- Vérifier que `attestation_template.jrxml` est dans `src/main/resources/reports/`

### Erreur : "Image not found"
- Vérifier que les images sont dans `src/main/resources/images/`
- Redémarrer l'application après modification des ressources

### Erreur : "Compilation failed"
- Vérifier la syntaxe du template Jasper Reports
- Vérifier que toutes les dépendances sont installées

## Logs de Debug

Pour activer les logs de debug Jasper Reports, ajouter dans `application.properties` :
```properties
logging.level.net.sf.jasperreports=DEBUG
logging.level.com.netcon.gestion_salaries.service=DEBUG
```
