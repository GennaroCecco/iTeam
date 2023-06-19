-- MySQL dump 10.13  Distrib 8.0.29, for Win64 (x86_64)
--
-- Host: localhost    Database: af_db
-- ------------------------------------------------------
-- Server version	8.0.29

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `candidature`
--

DROP TABLE IF EXISTS `candidature`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `candidature` (
  `IdCandidatura` int NOT NULL AUTO_INCREMENT,
  `Curriculum` varchar(512) NOT NULL,
  `DocumentiAggiuntivi` varchar(512) DEFAULT NULL,
  `Stato` varchar(32) NOT NULL,
  `DataCandidatura` date NOT NULL,
  `DataOraColloquio` datetime DEFAULT NULL,
  `IdCandidato` int NOT NULL,
  `IdHR` int DEFAULT NULL,
  PRIMARY KEY (`IdCandidatura`),
  KEY `IdCandidato` (`IdCandidato`),
  KEY `IdHR` (`IdHR`),
  CONSTRAINT `candidature_ibfk_1` FOREIGN KEY (`IdCandidato`) REFERENCES `utenti` (`IdUtente`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `candidature_ibfk_2` FOREIGN KEY (`IdHR`) REFERENCES `utenti` (`IdUtente`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `candidature`
--

LOCK TABLES `candidature` WRITE;
/*!40000 ALTER TABLE `candidature` DISABLE KEYS */;
/*!40000 ALTER TABLE `candidature` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dipendenti`
--

DROP TABLE IF EXISTS `dipendenti`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dipendenti` (
  `IdDipendente` int NOT NULL,
  `Residenza` varchar(128) DEFAULT NULL,
  `Telefono` varchar(20) DEFAULT NULL,
  `Stato` tinyint(1) NOT NULL,
  `AnnoDiNascita` int NOT NULL,
  `IdTeam` int DEFAULT NULL,
  PRIMARY KEY (`IdDipendente`),
  KEY `IdTeam` (`IdTeam`),
  CONSTRAINT `dipendenti_ibfk_1` FOREIGN KEY (`IdTeam`) REFERENCES `team` (`IdTeam`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `dipendenti_ibfk_2` FOREIGN KEY (`IdDipendente`) REFERENCES `utenti` (`IdUtente`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dipendenti`
--

LOCK TABLES `dipendenti` WRITE;
/*!40000 ALTER TABLE `dipendenti` DISABLE KEYS */;
INSERT INTO `dipendenti` VALUES (2,'Fisciano','118',1,2000,NULL);
/*!40000 ALTER TABLE `dipendenti` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `documenti`
--

DROP TABLE IF EXISTS `documenti`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `documenti` (
  `IdDocumento` int NOT NULL AUTO_INCREMENT,
  `MaterialeDiFormazione` varchar(512) NOT NULL,
  `IdHR` int NOT NULL,
  `IdTeam` int NOT NULL,
  PRIMARY KEY (`IdDocumento`),
  KEY `IdHR` (`IdHR`),
  KEY `IdTeam` (`IdTeam`),
  CONSTRAINT `documenti_ibfk_1` FOREIGN KEY (`IdHR`) REFERENCES `utenti` (`IdUtente`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `documenti_ibfk_2` FOREIGN KEY (`IdTeam`) REFERENCES `team` (`IdTeam`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `documenti`
--

LOCK TABLES `documenti` WRITE;
/*!40000 ALTER TABLE `documenti` DISABLE KEYS */;
/*!40000 ALTER TABLE `documenti` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `skill`
--

DROP TABLE IF EXISTS `skill`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `skill` (
  `IdSkill` int NOT NULL AUTO_INCREMENT,
  `NomeSkill` varchar(64) NOT NULL,
  `DescrizioneSkill` varchar(512) NOT NULL,
  PRIMARY KEY (`IdSkill`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `skill`
--

LOCK TABLES `skill` WRITE;
/*!40000 ALTER TABLE `skill` DISABLE KEYS */;
INSERT INTO `skill` VALUES (1,'HTML','HTML'),(2,'CSS','CSS'),(3,'Java','Java'),(4,'C','C'),(5,'Python','Python'),(6,'React','React'),(7,'Node','Node'),(8,'C++','C++'),(9,'javascript','javascript'),(10,'Ruby','Ruby'),(11,'C#','C#'),(12,'Android','Android'),(13,'SQL','SQL'),(14,'PHP','PHP');
/*!40000 ALTER TABLE `skill` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `skillsdipendenti`
--

DROP TABLE IF EXISTS `skillsdipendenti`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `skillsdipendenti` (
  `IdDipendente` int NOT NULL,
  `IdSkill` int NOT NULL,
  `Livello` int NOT NULL,
  PRIMARY KEY (`IdDipendente`,`IdSkill`),
  KEY `IdSkill` (`IdSkill`),
  CONSTRAINT `skillsdipendenti_ibfk_1` FOREIGN KEY (`IdDipendente`) REFERENCES `dipendenti` (`IdDipendente`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `skillsdipendenti_ibfk_2` FOREIGN KEY (`IdSkill`) REFERENCES `skill` (`IdSkill`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `skillsdipendenti`
--

LOCK TABLES `skillsdipendenti` WRITE;
/*!40000 ALTER TABLE `skillsdipendenti` DISABLE KEYS */;
INSERT INTO `skillsdipendenti` VALUES (2,1,5),(2,2,3);
/*!40000 ALTER TABLE `skillsdipendenti` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `team`
--

DROP TABLE IF EXISTS `team`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `team` (
  `IdTeam` int NOT NULL AUTO_INCREMENT,
  `NomeProgetto` varchar(32) NOT NULL,
  `NumeroDipendenti` int NOT NULL,
  `NomeTeam` varchar(32) NOT NULL,
  `Descrizione` varchar(512) NOT NULL,
  `Competenza` varchar(512) DEFAULT NULL,
  `IdTM` int NOT NULL,
  PRIMARY KEY (`IdTeam`),
  KEY `IdTM` (`IdTM`),
  CONSTRAINT `team_ibfk_1` FOREIGN KEY (`IdTM`) REFERENCES `utenti` (`IdUtente`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `team`
--

LOCK TABLES `team` WRITE;
/*!40000 ALTER TABLE `team` DISABLE KEYS */;
INSERT INTO `team` VALUES (1,'TechAll',4,'NuoveTecnologie','Ricerchiamo nuove tecnologie',NULL,3);
/*!40000 ALTER TABLE `team` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `utenti`
--

DROP TABLE IF EXISTS `utenti`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `utenti` (
  `IdUtente` int NOT NULL AUTO_INCREMENT,
  `Nome` varchar(32) NOT NULL,
  `Cognome` varchar(32) NOT NULL,
  `Pwd` varchar(16) NOT NULL,
  `Mail` varchar(32) NOT NULL,
  `Ruolo` int NOT NULL,
  PRIMARY KEY (`IdUtente`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `utenti`
--

LOCK TABLES `utenti` WRITE;
/*!40000 ALTER TABLE `utenti` DISABLE KEYS */;
INSERT INTO `utenti` VALUES (1,'Luigi','Giacchetti','lol','l.giacchetti@studenti.unisa.it',1),(2,'Pasquale','Severino','lol','p.severino@studenti.unisa.it',2),(3,'Manuel','Nocerino','lol','m.nocerino@studenti.unisa.it',3),(4,'Domenico','Pagliuca','lol','d.pagliuca@studenti.unisa.it',4),(5,'Mario','Rossi','lol','MarioDraghi@libero.it',2);
/*!40000 ALTER TABLE `utenti` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-06-19 21:26:49
