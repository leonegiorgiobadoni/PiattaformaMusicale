-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Versione server:              10.4.32-MariaDB - mariadb.org binary distribution
-- S.O. server:                  Win64
-- HeidiSQL Versione:            12.10.0.7000
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dump della struttura del database piattaformamusicale
DROP DATABASE IF EXISTS `piattaformamusicale`;
CREATE DATABASE IF NOT EXISTS `piattaformamusicale` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */;
USE `piattaformamusicale`;

-- Dump della struttura di tabella piattaformamusicale.autori
DROP TABLE IF EXISTS `autori`;
CREATE TABLE IF NOT EXISTS `autori` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(100) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `nome` (`nome`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- L’esportazione dei dati non era selezionata.

-- Dump della struttura di tabella piattaformamusicale.brani
DROP TABLE IF EXISTS `brani`;
CREATE TABLE IF NOT EXISTS `brani` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `idGenere` int(11) NOT NULL,
  `titolo` varchar(255) DEFAULT NULL,
  `durata_secondi` int(11) NOT NULL,
  `lingua` varchar(20) DEFAULT NULL,
  `data_pubblicazione` date DEFAULT NULL,
  `link_audio_mp4` varchar(255) DEFAULT NULL,
  `link_youtube` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `idGenere` (`idGenere`),
  CONSTRAINT `brani_ibfk_1` FOREIGN KEY (`idGenere`) REFERENCES `generi` (`ID`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- L’esportazione dei dati non era selezionata.

-- Dump della struttura di tabella piattaformamusicale.brani_autori
DROP TABLE IF EXISTS `brani_autori`;
CREATE TABLE IF NOT EXISTS `brani_autori` (
  `idBrano` int(11) NOT NULL,
  `idAutore` int(11) NOT NULL,
  PRIMARY KEY (`idBrano`,`idAutore`),
  KEY `idAutore` (`idAutore`),
  CONSTRAINT `brani_autori_ibfk_1` FOREIGN KEY (`idBrano`) REFERENCES `brani` (`ID`) ON DELETE CASCADE,
  CONSTRAINT `brani_autori_ibfk_2` FOREIGN KEY (`idAutore`) REFERENCES `autori` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- L’esportazione dei dati non era selezionata.

-- Dump della struttura di tabella piattaformamusicale.elementi_playlist
DROP TABLE IF EXISTS `elementi_playlist`;
CREATE TABLE IF NOT EXISTS `elementi_playlist` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `idPlaylist` int(11) NOT NULL,
  `idBrano` int(11) NOT NULL,
  `posizione` int(11) NOT NULL,
  `data_aggiunta` timestamp NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UKk5uuj814ukdwe86wcejes8ntg` (`idPlaylist`,`posizione`),
  UNIQUE KEY `UKoulqmkhftx2ffboo29nt8jb87` (`idPlaylist`,`idBrano`),
  KEY `idBrano` (`idBrano`),
  CONSTRAINT `elementi_playlist_ibfk_1` FOREIGN KEY (`idPlaylist`) REFERENCES `playlists` (`ID`) ON DELETE CASCADE,
  CONSTRAINT `elementi_playlist_ibfk_2` FOREIGN KEY (`idBrano`) REFERENCES `brani` (`ID`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- L’esportazione dei dati non era selezionata.

-- Dump della struttura di tabella piattaformamusicale.generi
DROP TABLE IF EXISTS `generi`;
CREATE TABLE IF NOT EXISTS `generi` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(50) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `nome` (`nome`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- L’esportazione dei dati non era selezionata.

-- Dump della struttura di tabella piattaformamusicale.playlists
DROP TABLE IF EXISTS `playlists`;
CREATE TABLE IF NOT EXISTS `playlists` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `idUtente` int(11) NOT NULL,
  `nome` varchar(100) NOT NULL,
  `data_creazione` timestamp NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`ID`),
  KEY `idUtente` (`idUtente`),
  CONSTRAINT `playlists_ibfk_1` FOREIGN KEY (`idUtente`) REFERENCES `utenti` (`ID`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- L’esportazione dei dati non era selezionata.

-- Dump della struttura di tabella piattaformamusicale.utenti
DROP TABLE IF EXISTS `utenti`;
CREATE TABLE IF NOT EXISTS `utenti` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `ruolo` enum('USER','SUPER_ADMIN') NOT NULL DEFAULT 'USER',
  `autenticato` tinyint(1) NOT NULL DEFAULT 0,
  `data_registrazione` timestamp NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`ID`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- L’esportazione dei dati non era selezionata.

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
