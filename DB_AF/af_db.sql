-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               10.4.11-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win64
-- HeidiSQL Version:             11.3.0.6390
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping database structure for af_db
CREATE DATABASE IF NOT EXISTS `af_db` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;
USE `af_db`;

-- Dumping structure for table af_db.candidature
CREATE TABLE IF NOT EXISTS `candidature` (
  `IdCandidatura` int(11) NOT NULL AUTO_INCREMENT,
  `Curriculum` varchar(512) NOT NULL,
  `DocumentiAggiuntivi` varchar(512) DEFAULT NULL,
  `Stato` varchar(32) NOT NULL,
  `DataCandidatura` date NOT NULL,
  `DataOraColloquio` datetime DEFAULT NULL,
  `IdCandidato` int(11) NOT NULL,
  `IdHR` int(11) DEFAULT NULL,
  PRIMARY KEY (`IdCandidatura`),
  KEY `IdCandidato` (`IdCandidato`),
  KEY `IdHR` (`IdHR`),
  CONSTRAINT `candidature_ibfk_1` FOREIGN KEY (`IdCandidato`) REFERENCES `utenti` (`IdUtente`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `candidature_ibfk_2` FOREIGN KEY (`IdHR`) REFERENCES `utenti` (`IdUtente`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Dumping data for table af_db.candidature: ~0 rows (approximately)
DELETE FROM `candidature`;

-- Dumping structure for table af_db.dipendenti
CREATE TABLE IF NOT EXISTS `dipendenti` (
  `IdDipendente` int(11) NOT NULL,
  `Residenza` varchar(128) DEFAULT NULL,
  `Telefono` varchar(20) DEFAULT NULL,
  `Stato` tinyint(1) NOT NULL,
  `AnnoDiNascita` int(11) NOT NULL,
  `IdTeam` int(11) DEFAULT NULL,
  PRIMARY KEY (`IdDipendente`),
  KEY `IdTeam` (`IdTeam`),
  CONSTRAINT `dipendenti_ibfk_1` FOREIGN KEY (`IdTeam`) REFERENCES `team` (`IdTeam`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `dipendenti_ibfk_2` FOREIGN KEY (`IdDipendente`) REFERENCES `utenti` (`IdUtente`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Dumping data for table af_db.dipendenti: ~2 rows (approximately)
DELETE FROM `dipendenti`;
INSERT INTO `dipendenti` (`IdDipendente`, `Residenza`, `Telefono`, `Stato`, `AnnoDiNascita`, `IdTeam`) VALUES
	(2, 'Fisciano', '118', 0, 2000, 1),
	(5, 'Fisciano', '777', 0, 2000, 2);

-- Dumping structure for table af_db.documenti
CREATE TABLE IF NOT EXISTS `documenti` (
  `IdDocumento` int(11) NOT NULL AUTO_INCREMENT,
  `MaterialeDiFormazione` varchar(512) NOT NULL,
  `IdHR` int(11) NOT NULL,
  `IdTeam` int(11) NOT NULL,
  PRIMARY KEY (`IdDocumento`),
  KEY `IdHR` (`IdHR`),
  KEY `IdTeam` (`IdTeam`),
  CONSTRAINT `documenti_ibfk_1` FOREIGN KEY (`IdHR`) REFERENCES `utenti` (`IdUtente`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `documenti_ibfk_2` FOREIGN KEY (`IdTeam`) REFERENCES `team` (`IdTeam`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Dumping data for table af_db.documenti: ~0 rows (approximately)
DELETE FROM `documenti`;

-- Dumping structure for table af_db.skill
CREATE TABLE IF NOT EXISTS `skill` (
  `IdSkill` int(11) NOT NULL AUTO_INCREMENT,
  `NomeSkill` varchar(64) NOT NULL,
  `DescrizioneSkill` varchar(512) NOT NULL,
  PRIMARY KEY (`IdSkill`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

-- Dumping data for table af_db.skill: ~2 rows (approximately)
DELETE FROM `skill`;
INSERT INTO `skill` (`IdSkill`, `NomeSkill`, `DescrizioneSkill`) VALUES
	(1, 'HTML', 'HTML'),
	(2, 'CSS', 'CSS');

-- Dumping structure for table af_db.skillsdipendenti
CREATE TABLE IF NOT EXISTS `skillsdipendenti` (
  `IdDipendente` int(11) NOT NULL,
  `IdSkill` int(11) NOT NULL,
  `Livello` int(11) NOT NULL,
  PRIMARY KEY (`IdDipendente`,`IdSkill`),
  KEY `IdSkill` (`IdSkill`),
  CONSTRAINT `skillsdipendenti_ibfk_1` FOREIGN KEY (`IdDipendente`) REFERENCES `dipendenti` (`IdDipendente`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `skillsdipendenti_ibfk_2` FOREIGN KEY (`IdSkill`) REFERENCES `skill` (`IdSkill`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Dumping data for table af_db.skillsdipendenti: ~2 rows (approximately)
DELETE FROM `skillsdipendenti`;
INSERT INTO `skillsdipendenti` (`IdDipendente`, `IdSkill`, `Livello`) VALUES
	(2, 1, 5),
	(2, 2, 3);

-- Dumping structure for table af_db.team
CREATE TABLE IF NOT EXISTS `team` (
  `IdTeam` int(11) NOT NULL AUTO_INCREMENT,
  `NomeProgetto` varchar(32) NOT NULL,
  `NumeroDipendenti` int(11) NOT NULL,
  `NomeTeam` varchar(32) NOT NULL,
  `Descrizione` varchar(512) NOT NULL,
  `Competenza` varchar(512) DEFAULT NULL,
  `IdTM` int(11) NOT NULL,
  PRIMARY KEY (`IdTeam`),
  KEY `IdTM` (`IdTM`),
  CONSTRAINT `team_ibfk_1` FOREIGN KEY (`IdTM`) REFERENCES `utenti` (`IdUtente`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

-- Dumping data for table af_db.team: ~2 rows (approximately)
DELETE FROM `team`;
INSERT INTO `team` (`IdTeam`, `NomeProgetto`, `NumeroDipendenti`, `NomeTeam`, `Descrizione`, `Competenza`, `IdTM`) VALUES
	(1, 'TechAll', 8, 'NuoveTecnologie', 'Ricerchiamo nuove tecnologie', NULL, 3),
	(2, 'NewTech', 1, 'Arte', 'Nuove tecnologie biomeccaniche', 'Basi di Ingegneria Meccanica', 3);

-- Dumping structure for table af_db.utenti
CREATE TABLE IF NOT EXISTS `utenti` (
  `IdUtente` int(11) NOT NULL AUTO_INCREMENT,
  `Nome` varchar(32) NOT NULL,
  `Cognome` varchar(32) NOT NULL,
  `Pwd` varchar(16) NOT NULL,
  `Mail` varchar(32) NOT NULL,
  `Ruolo` int(11) NOT NULL,
  PRIMARY KEY (`IdUtente`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;

-- Dumping data for table af_db.utenti: ~5 rows (approximately)
DELETE FROM `utenti`;
INSERT INTO `utenti` (`IdUtente`, `Nome`, `Cognome`, `Pwd`, `Mail`, `Ruolo`) VALUES
	(1, 'Luigi', 'Giacchetti', 'lol', 'l.giacchetti@studenti.unisa.it', 1),
	(2, 'Pasquale', 'Severino', 'lol', 'p.severino@studenti.unisa.it', 2),
	(3, 'Manuel', 'Nocerino', 'lol', 'm.nocerino@studenti.unisa.it', 3),
	(4, 'Domenico', 'Pagliuca', 'lol', 'd.pagliuca@studenti.unisa.it', 4),
	(5, 'Mario', 'Rossi', 'lol', 'MarioDraghi@libero.it', 2);


-- Dumping database structure for af_db_test
CREATE DATABASE IF NOT EXISTS `af_db_test` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;
USE `af_db_test`;

-- Dumping structure for table af_db_test.candidature
CREATE TABLE IF NOT EXISTS `candidature` (
  `IdCandidatura` int(11) NOT NULL AUTO_INCREMENT,
  `Curriculum` varchar(512) NOT NULL,
  `DocumentiAggiuntivi` varchar(512) DEFAULT NULL,
  `Stato` varchar(32) NOT NULL,
  `DataCandidatura` date NOT NULL,
  `DataOraColloquio` datetime DEFAULT NULL,
  `IdCandidato` int(11) NOT NULL,
  `IdHR` int(11) DEFAULT NULL,
  PRIMARY KEY (`IdCandidatura`),
  KEY `IdCandidato` (`IdCandidato`),
  KEY `IdHR` (`IdHR`),
  CONSTRAINT `candidature_ibfk_1` FOREIGN KEY (`IdCandidato`) REFERENCES `utenti` (`IdUtente`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `candidature_ibfk_2` FOREIGN KEY (`IdHR`) REFERENCES `utenti` (`IdUtente`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Dumping data for table af_db_test.candidature: ~0 rows (approximately)
DELETE FROM `candidature`;

-- Dumping structure for table af_db_test.dipendenti
CREATE TABLE IF NOT EXISTS `dipendenti` (
  `IdDipendente` int(11) NOT NULL,
  `Residenza` varchar(128) DEFAULT NULL,
  `Telefono` varchar(20) DEFAULT NULL,
  `Stato` tinyint(1) NOT NULL,
  `AnnoDiNascita` int(11) NOT NULL,
  `IdTeam` int(11) DEFAULT NULL,
  PRIMARY KEY (`IdDipendente`),
  KEY `IdTeam` (`IdTeam`),
  CONSTRAINT `dipendenti_ibfk_1` FOREIGN KEY (`IdTeam`) REFERENCES `team` (`IdTeam`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `dipendenti_ibfk_2` FOREIGN KEY (`IdDipendente`) REFERENCES `utenti` (`IdUtente`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Dumping data for table af_db_test.dipendenti: ~1 rows (approximately)
DELETE FROM `dipendenti`;
INSERT INTO `dipendenti` (`IdDipendente`, `Residenza`, `Telefono`, `Stato`, `AnnoDiNascita`, `IdTeam`) VALUES
	(2, 'Fisciano', '118', 0, 2000, 1);

-- Dumping structure for table af_db_test.documenti
CREATE TABLE IF NOT EXISTS `documenti` (
  `IdDocumento` int(11) NOT NULL AUTO_INCREMENT,
  `MaterialeDiFormazione` varchar(512) NOT NULL,
  `IdHR` int(11) NOT NULL,
  `IdTeam` int(11) NOT NULL,
  PRIMARY KEY (`IdDocumento`),
  KEY `IdHR` (`IdHR`),
  KEY `IdTeam` (`IdTeam`),
  CONSTRAINT `documenti_ibfk_1` FOREIGN KEY (`IdHR`) REFERENCES `utenti` (`IdUtente`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `documenti_ibfk_2` FOREIGN KEY (`IdTeam`) REFERENCES `team` (`IdTeam`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Dumping data for table af_db_test.documenti: ~0 rows (approximately)
DELETE FROM `documenti`;

-- Dumping structure for table af_db_test.skill
CREATE TABLE IF NOT EXISTS `skill` (
  `IdSkill` int(11) NOT NULL AUTO_INCREMENT,
  `NomeSkill` varchar(64) NOT NULL,
  `DescrizioneSkill` varchar(512) NOT NULL,
  PRIMARY KEY (`IdSkill`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

-- Dumping data for table af_db_test.skill: ~2 rows (approximately)
DELETE FROM `skill`;
INSERT INTO `skill` (`IdSkill`, `NomeSkill`, `DescrizioneSkill`) VALUES
	(1, 'HTML', 'Conoscenze generali di HTML'),
	(2, 'CSS', 'Conoscenze basilari di CSS');

-- Dumping structure for table af_db_test.skillsdipendenti
CREATE TABLE IF NOT EXISTS `skillsdipendenti` (
  `IdDipendente` int(11) NOT NULL,
  `IdSkill` int(11) NOT NULL,
  `Livello` int(11) NOT NULL,
  PRIMARY KEY (`IdDipendente`,`IdSkill`),
  KEY `IdSkill` (`IdSkill`),
  CONSTRAINT `skillsdipendenti_ibfk_1` FOREIGN KEY (`IdDipendente`) REFERENCES `dipendenti` (`IdDipendente`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `skillsdipendenti_ibfk_2` FOREIGN KEY (`IdSkill`) REFERENCES `skill` (`IdSkill`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Dumping data for table af_db_test.skillsdipendenti: ~2 rows (approximately)
DELETE FROM `skillsdipendenti`;
INSERT INTO `skillsdipendenti` (`IdDipendente`, `IdSkill`, `Livello`) VALUES
	(2, 1, 5),
	(2, 2, 3);

-- Dumping structure for table af_db_test.team
CREATE TABLE IF NOT EXISTS `team` (
  `IdTeam` int(11) NOT NULL AUTO_INCREMENT,
  `NomeProgetto` varchar(32) NOT NULL,
  `NumeroDipendenti` int(11) NOT NULL,
  `NomeTeam` varchar(32) NOT NULL,
  `Descrizione` varchar(512) NOT NULL,
  `Competenza` varchar(512) DEFAULT NULL,
  `IdTM` int(11) NOT NULL,
  PRIMARY KEY (`IdTeam`),
  KEY `IdTM` (`IdTM`),
  CONSTRAINT `team_ibfk_1` FOREIGN KEY (`IdTM`) REFERENCES `utenti` (`IdUtente`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

-- Dumping data for table af_db_test.team: ~1 rows (approximately)
DELETE FROM `team`;
INSERT INTO `team` (`IdTeam`, `NomeProgetto`, `NumeroDipendenti`, `NomeTeam`, `Descrizione`, `Competenza`, `IdTM`) VALUES
	(1, 'TechAll', 8, 'NuoveTecnologie', 'Ricerchiamo nuove tecnologie', 'Basi di Ingegneria Meccanica', 3);

-- Dumping structure for table af_db_test.utenti
CREATE TABLE IF NOT EXISTS `utenti` (
  `IdUtente` int(11) NOT NULL AUTO_INCREMENT,
  `Nome` varchar(32) NOT NULL,
  `Cognome` varchar(32) NOT NULL,
  `Pwd` varchar(16) NOT NULL,
  `Mail` varchar(32) NOT NULL,
  `Ruolo` int(11) NOT NULL,
  PRIMARY KEY (`IdUtente`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;

-- Dumping data for table af_db_test.utenti: ~4 rows (approximately)
DELETE FROM `utenti`;
INSERT INTO `utenti` (`IdUtente`, `Nome`, `Cognome`, `Pwd`, `Mail`, `Ruolo`) VALUES
	(1, 'Luigi', 'Giacchetti', 'lol', 'l.giacchetti@studenti.unisa.it', 1),
	(2, 'Pasquale', 'Severino', 'lol', 'p.severino@studenti.unisa.it', 2),
	(3, 'Manuel', 'Nocerino', 'lol', 'm.nocerino@studenti.unisa.it', 3),
	(4, 'Domenico', 'Pagliuca', 'lol', 'd.pagliuca@studenti.unisa.it', 4);

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
