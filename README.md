CREATE DATABASE IF NOT EXISTS gestion_salaries
CHARACTER SET utf8mb4
COLLATE utf8mb4_general_ci;

USE gestion_salaries;

-- Table employe
CREATE TABLE IF NOT EXISTS employe (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    cin VARCHAR(20) NOT NULL UNIQUE,
    poste VARCHAR(100) NOT NULL,
    service VARCHAR(100) NOT NULL,
    date_embauche DATE NOT NULL
);

-- Table attestation
CREATE TABLE IF NOT EXISTS attestation (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    employe_id BIGINT NOT NULL,
    type_attestation VARCHAR(20) NOT NULL,
    date_generation DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    chemin_fichier VARCHAR(255),

    CONSTRAINT fk_employe FOREIGN KEY (employe_id) REFERENCES employe(id)
    ON DELETE CASCADE
);
