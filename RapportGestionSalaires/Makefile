# Makefile pour la compilation du rapport LaTeX
# Système de Gestion des Salaires - ENSIAS 2024-2025

# Variables
MAIN_FILE = rapportGestionSalaires
TEX_FILE = $(MAIN_FILE).tex
PDF_FILE = $(MAIN_FILE).pdf

# Commandes LaTeX
LATEX = pdflatex
LATEX_FLAGS = -interaction=nonstopmode -halt-on-error

# Cible par défaut
all: $(PDF_FILE)

# Compilation du PDF (deux passes pour les références)
$(PDF_FILE): $(TEX_FILE)
	@echo "=== Première compilation LaTeX ==="
	$(LATEX) $(LATEX_FLAGS) $(TEX_FILE)
	@echo "=== Seconde compilation LaTeX (pour les références) ==="
	$(LATEX) $(LATEX_FLAGS) $(TEX_FILE)
	@echo "=== Compilation terminée avec succès ==="
	@echo "Le fichier $(PDF_FILE) a été généré."

# Compilation rapide (une seule passe)
quick: $(TEX_FILE)
	@echo "=== Compilation rapide ==="
	$(LATEX) $(LATEX_FLAGS) $(TEX_FILE)
	@echo "=== Compilation rapide terminée ==="

# Nettoyage des fichiers auxiliaires
clean:
	@echo "=== Nettoyage des fichiers auxiliaires ==="
	rm -f *.aux *.log *.toc *.out *.lof *.lot *.fls *.fdb_latexmk *.synctex.gz
	@echo "=== Nettoyage terminé ==="

# Nettoyage complet (inclut le PDF)
cleanall: clean
	@echo "=== Suppression du PDF ==="
	rm -f $(PDF_FILE)
	@echo "=== Nettoyage complet terminé ==="

# Affichage du PDF (Linux/Mac)
view: $(PDF_FILE)
	@if command -v xdg-open > /dev/null; then \
		xdg-open $(PDF_FILE); \
	elif command -v open > /dev/null; then \
		open $(PDF_FILE); \
	else \
		echo "Impossible d'ouvrir automatiquement le PDF. Ouvrez manuellement $(PDF_FILE)"; \
	fi

# Vérification de la syntaxe LaTeX
check:
	@echo "=== Vérification de la syntaxe LaTeX ==="
	$(LATEX) -draftmode -interaction=nonstopmode $(TEX_FILE)
	@echo "=== Vérification terminée ==="

# Installation des dépendances (Ubuntu/Debian)
install-deps:
	@echo "=== Installation des dépendances LaTeX (Ubuntu/Debian) ==="
	sudo apt-get update
	sudo apt-get install texlive-full
	@echo "=== Installation terminée ==="

# Aide
help:
	@echo "Makefile pour la compilation du rapport LaTeX"
	@echo ""
	@echo "Cibles disponibles :"
	@echo "  all         - Compilation complète du PDF (défaut)"
	@echo "  quick       - Compilation rapide (une passe)"
	@echo "  clean       - Supprime les fichiers auxiliaires"
	@echo "  cleanall    - Supprime tous les fichiers générés"
	@echo "  view        - Ouvre le PDF généré"
	@echo "  check       - Vérifie la syntaxe LaTeX"
	@echo "  install-deps- Installe les dépendances LaTeX (Ubuntu/Debian)"
	@echo "  help        - Affiche cette aide"
	@echo ""
	@echo "Exemples d'utilisation :"
	@echo "  make              # Compile le rapport"
	@echo "  make clean        # Nettoie les fichiers temporaires"
	@echo "  make view         # Ouvre le PDF"

# Cibles sans fichiers correspondants
.PHONY: all quick clean cleanall view check install-deps help
