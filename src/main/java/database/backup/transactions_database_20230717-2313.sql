-- MySQL dump 10.13  Distrib 8.0.33, for Win64 (x86_64)
--
-- Host: localhost    Database: transactions_database
-- ------------------------------------------------------
-- Server version	8.0.33

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `transactions_table`
--

DROP TABLE IF EXISTS `transactions_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transactions_table` (
  `Date` char(10) NOT NULL,
  `Description` varchar(24) NOT NULL,
  `Amount` decimal(9,2) NOT NULL,
  `Type` varchar(19) NOT NULL,
  `Account_Name` varchar(24) NOT NULL,
  `ID` int NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transactions_table`
--

LOCK TABLES `transactions_table` WRITE;
/*!40000 ALTER TABLE `transactions_table` DISABLE KEYS */;
INSERT INTO `transactions_table` VALUES ('11.11.11','asdf',-4.00,'Unavoidable Expense','aa',1),('11.11.11','fdsa',2.00,'Deposit','bb',2),('11.11.11','hsdj',34.00,'Luxury Expense','cc',3),('12.12.12.','asdfh',252.00,'Luxury Expense','cc',4),('05.23.11','adha',54.00,'Deposit','bb',5),('02.02.11','asdfav',43.00,'Deposit','aa',6),('02.05.11','oiug',500.00,'Deposit','ee',7),('2.05.12','qwerh',225.00,'Deposit','ee',8);
/*!40000 ALTER TABLE `transactions_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `view_accounts_table`
--

DROP TABLE IF EXISTS `view_accounts_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `view_accounts_table` (
  `Account_Name` varchar(24) NOT NULL,
  `Type` varchar(16) NOT NULL,
  `ID` int NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `view_accounts_table`
--

LOCK TABLES `view_accounts_table` WRITE;
/*!40000 ALTER TABLE `view_accounts_table` DISABLE KEYS */;
INSERT INTO `view_accounts_table` VALUES ('aa','Checking',1),('bb','Savings',2),('cc','Car Loan',3),('ee','Investment',4);
/*!40000 ALTER TABLE `view_accounts_table` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-07-17 23:13:36
