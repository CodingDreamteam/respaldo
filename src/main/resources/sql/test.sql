CREATE DATABASE  IF NOT EXISTS `test` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `test`;
-- MySQL dump 10.13  Distrib 5.7.12, for Win32 (AMD64)
--
-- Host: 127.0.0.1    Database: test
-- ------------------------------------------------------
-- Server version	5.7.17-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `tbloperator`
--

DROP TABLE IF EXISTS `tbloperator`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbloperator` (
  `ID` varchar(40) NOT NULL,
  `Name` varchar(75) NOT NULL,
  `Role` varchar(45) NOT NULL,
  `Password` varchar(150) NOT NULL,
  `Comment` varchar(255) DEFAULT NULL,
  `CreatedBy` varchar(40) NOT NULL,
  `CreatedAtDate` date NOT NULL,
  `CreatedAtTime` time NOT NULL,
  `UpdatedBy` varchar(40) DEFAULT NULL,
  `UpdatedAtDate` date DEFAULT NULL,
  `UpdatedAtTime` time DEFAULT NULL,
  `DisabledBy` varchar(40) DEFAULT NULL,
  `DisabledAtDate` date DEFAULT NULL,
  `DisabledAtTime` time DEFAULT NULL,
  `LastLoginAtDate` date DEFAULT NULL,
  `LastLoginAtTime` time DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='store the operator system';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbloperator`
--

LOCK TABLES `tbloperator` WRITE;
/*!40000 ALTER TABLE `tbloperator` DISABLE KEYS */;
INSERT INTO `tbloperator` VALUES ('111','test01','admin','12345678','123','god','2017-01-06','08:41:00',NULL,NULL,NULL,NULL,NULL,NULL,'2017-01-21','22:22:08');
/*!40000 ALTER TABLE `tbloperator` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblperson`
--

DROP TABLE IF EXISTS `tblperson`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblperson` (
  `ID` varchar(40) COLLATE utf8_unicode_ci NOT NULL,
  `FirstName` varchar(150) COLLATE utf8_unicode_ci NOT NULL,
  `LastName` varchar(150) COLLATE utf8_unicode_ci NOT NULL,
  `Gender` smallint(6) NOT NULL COMMENT '0 female\n1 male',
  `BirthDate` date NOT NULL,
  `Comment` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `CreatedBy` varchar(40) COLLATE utf8_unicode_ci NOT NULL,
  `CreatedAtDate` date NOT NULL,
  `CreatedAtTime` time NOT NULL,
  `UpdatedBy` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL,
  `UpdatedAtDate` date DEFAULT NULL,
  `UpdatedAtTime` time DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='people table';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblperson`
--

LOCK TABLES `tblperson` WRITE;
/*!40000 ALTER TABLE `tblperson` DISABLE KEYS */;
INSERT INTO `tblperson` VALUES ('12312345555','lul',' lal',1,'2016-11-29','lol','root','2017-01-21','19:43:41',NULL,NULL,NULL),('223123','adsadas',' asdasdfffgg',1,'2016-11-28','YO','root','2017-01-21','17:30:00',NULL,NULL,NULL),('23213555778','adsa',' sad',0,'2016-11-29','lel','root','2017-01-21','17:30:17',NULL,NULL,NULL);
/*!40000 ALTER TABLE `tblperson` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-01-22 13:20:38
