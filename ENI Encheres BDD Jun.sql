-- phpMyAdmin SQL Dump
-- version 5.1.1deb5ubuntu1
-- https://www.phpmyadmin.net/
--
-- Hôte : localhost:3306
-- Généré le : ven. 09 déc. 2022 à 10:38
-- Version du serveur : 10.6.11-MariaDB-0ubuntu0.22.04.1
-- Version de PHP : 8.1.2-1ubuntu2.9

--SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
--START TRANSACTION;
--SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `encheres`
--

-- --------------------------------------------------------

--
-- Structure de la table `ARTICLES_VENDUS`
--

CREATE TABLE ARTICLES_VENDUS (
  no_article INTEGER IDENTITY(1,1) NOT NULL,
  nom_article varchar(30) NOT NULL,
  description varchar(300) NOT NULL,
  date_debut_encheres date NOT NULL,
  date_fin_encheres date NOT NULL,
  prix_initial INTEGER NOT NULL,
  prix_vente INTEGER NOT NULL,
  etatVente varchar(30) NOT NULL,
  no_utilisateur INTEGER NOT NULL,
  no_categorie INTEGER NOT NULL,
  retrait INTEGER NOT NULL,
)
-- --------------------------------------------------------

--
-- Structure de la table `CATEGORIES`
--

CREATE TABLE CATEGORIES (
  no_categorie INTEGER IDENTITY(1,1) NOT NULL,
  libelle varchar(30) NOT NULL
) 

--
-- Déchargement des données de la table `CATEGORIES`
--

INSERT INTO CATEGORIES (libelle) VALUES ('Informatique');
INSERT INTO CATEGORIES (libelle) VALUES ('Ameublement');
INSERT INTO CATEGORIES (libelle) VALUES ('Vêtement');
INSERT INTO CATEGORIES (libelle) VALUES ('Sport&Loisirs');

-- --------------------------------------------------------

--
-- Structure de la table `ENCHERES`
--

CREATE TABLE ENCHERES (
  id INTEGER IDENTITY(1,1) NOT NULL,
  no_utilisateur INTEGER NOT NULL,
  no_article INTEGER NOT NULL,
  date_enchere datetime NOT NULL,
  montant_enchere INTEGER NOT NULL
) 
-- --------------------------------------------------------

--
-- Structure de la table `RETRAITS`
--

CREATE TABLE RETRAITS (
  no_article INTEGER NOT NULL,
  rue varchar(30) NOT NULL,
  code_postal varchar(15) NOT NULL,
  ville varchar(30) NOT NULL
) 

-- --------------------------------------------------------

--
-- Structure de la table `UTILISATEURS`
--

CREATE TABLE UTILISATEURS (
  id  INTEGER IDENTITY(1,1) NOT NULL,
  no_utilisateur INTEGER NOT NULL,
  pseudo varchar(30) NOT NULL,
  nom varchar(30) NOT NULL,
  prenom varchar(30) NOT NULL,
  email varchar(40) NOT NULL,
  telephone varchar(15) NOT NULL,
  rue varchar(30) NOT NULL,
  code_postal varchar(10) NOT NULL,
  ville varchar(30) NOT NULL,
  mot_de_passe varchar(40) NOT NULL,
  credit bigint NOT NULL,
  administrateur bit NOT NULL,
  date_de_naissance datetime NOT NULL
) 

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `ARTICLES_VENDUS`
--
ALTER TABLE ARTICLES_VENDUS
  ADD PRIMARY KEY (no_article);

ALTER TABLE ARTICLES_VENDUS
  ADD CONSTRAINT no_utilisateur FOREIGN KEY (no_utilisateur) REFERENCES utilisateurs ( no_utilisateur );

ALTER TABLE ARTICLES_VENDUS
ADD CONSTRAINT articles_vendus_categories_fk FOREIGN KEY (no_categorie) REFERENCES categories ( no_categorie );

--
-- Index pour la table `CATEGORIES`
--
ALTER TABLE CATEGORIES
  ADD PRIMARY KEY (no_categorie);

--
-- Index pour la table `ENCHERES`
--
ALTER TABLE ENCHERES
  ADD PRIMARY KEY (no_utilisateur,no_article);

ALTER TABLE ENCHERES
  ADD CONSTAINT encheres_articles_vendus_fk FOREIGN KEY(no_article)REFERENCES ARTICLES_VENDUS ( no_article );

--
-- Index pour la table `RETRAITS`
--
ALTER TABLE RETRAITS
  ADD PRIMARY KEY (no_article);

--
-- Index pour la table `UTILISATEURS`
--
ALTER TABLE UTILISATEURS
  ADD PRIMARY KEY (no_utilisateur);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `CATEGORIES`
--
--ALTER TABLE CATEGORIES
--  MODIFY no_categorie INTEGER NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT pour la table `RETRAITS`
--
--ALTER TABLE RETRAITS
--  MODIFY no_article INTEGER NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `UTILISATEURS`
--
--ALTER TABLE UTILISATEURS
--  MODIFY no_utilisateur INTEGER NOT NULL AUTO_INCREMENT;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `ARTICLES_VENDUS`
--
ALTER TABLE ARTICLES_VENDUS
  ADD CONSTRAINT articles_vendus_categories_fk FOREIGN KEY (no_categorie) REFERENCES CATEGORIES (no_categorie);

ALTER TABLE ARTICLES_VENDUS
  ADD CONSTRAINT encheres_utilisateur_fk FOREIGN KEY (no_utilisateur) REFERENCES UTILISATEURS (no_utilisateur);

--
-- Contraintes pour la table `ENCHERES`
--
ALTER TABLE ENCHERES
  ADD CONSTRAINT encheres_articles_vendus_fk FOREIGN KEY (no_article) REFERENCES ARTICLES_VENDUS (no_article);

--
-- Contraintes pour la table `RETRAITS`
--
ALTER TABLE RETRAITS
  ADD CONSTRAINT retraits_articles_vendus_fk FOREIGN KEY (no_article) REFERENCES ARTICLES_VENDUS (no_article);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;