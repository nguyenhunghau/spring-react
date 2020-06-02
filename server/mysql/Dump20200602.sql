-- MySQL dump 10.13  Distrib 8.0.19, for Win64 (x86_64)
--
-- Host: localhost    Database: jobs_analytics
-- ------------------------------------------------------
-- Server version	8.0.19

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
-- Table structure for table `query_checker`
--

DROP TABLE IF EXISTS `query_checker`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `query_checker` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `WEB_ANALYTIC_ID` tinyint DEFAULT NULL,
  `QUERY_TYPE` enum('ITEM','TITLE','COMPANY','REQUIRE_YEAR','DATE_POST','DESCRIPTION','LINK','TAG','ADDRESS') DEFAULT NULL,
  `QUARY_VALUE` varchar(250) DEFAULT NULL,
  `IS_ACTIVE` tinyint DEFAULT NULL,
  `CREATED` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `query_checker`
--

LOCK TABLES `query_checker` WRITE;
/*!40000 ALTER TABLE `query_checker` DISABLE KEYS */;
INSERT INTO `query_checker` VALUES (1,1,'TITLE','.details .title',1,'2020-05-29 10:26:12'),(2,1,'COMPANY','.logo-wrapper img',1,'2020-05-29 10:26:12'),(3,1,'DATE_POST','.distance-time-job-posted',1,'2020-05-29 10:26:12'),(4,1,'DESCRIPTION','.details .description',1,'2020-05-29 10:26:12'),(5,1,'LINK','.details .title a',1,'2020-05-29 10:26:12'),(6,1,'TAG','.tag-list a span',1,'2020-05-29 10:26:12'),(7,1,'ADDRESS','.city .address',1,'2020-05-29 10:26:12'),(8,1,'ITEM','#jobs .job',1,'2020-05-29 10:28:52'),(9,2,'DESCRIPTION','$.results[0].hits[{item}].benefits[*].benefitValue',1,'2020-06-01 23:40:29'),(10,2,'TITLE','$.results[0].hits[{item}].jobTitle',1,'2020-06-01 23:41:45'),(11,2,'COMPANY','$.results[0].hits[{item}].company',1,'2020-06-01 23:42:57'),(12,2,'DATE_POST','$.results[0].hits[{item}].publishedDate',1,'2020-06-01 23:47:27'),(13,2,'LINK','$.results[0].hits[{item}].alias',1,'2020-06-01 23:50:37'),(14,2,'TAG','$.results[0].hits[{item}].skills',1,'2020-06-01 23:52:00'),(15,2,'ADDRESS','$.results[0].hits[{item}].locations',1,'2020-06-01 23:53:09');
/*!40000 ALTER TABLE `query_checker` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `web_analytic`
--

DROP TABLE IF EXISTS `web_analytic`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `web_analytic` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `NAME` varchar(250) DEFAULT NULL,
  `LINK` varchar(250) DEFAULT NULL,
  `IS_ACTIVE` tinyint DEFAULT NULL,
  `CREATED` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `web_analytic`
--

LOCK TABLES `web_analytic` WRITE;
/*!40000 ALTER TABLE `web_analytic` DISABLE KEYS */;
INSERT INTO `web_analytic` VALUES (1,'IT Viet','https://itviec.com/it-jobs/java/ho-chi-minh-hcm',1,'2020-05-30 00:53:12'),(2,'Vietnamwork','https://www.vietnamworks.com/java+nganh-it-phan-mem-tai-ho-chi-minh-i35v29-vn',1,'2020-06-01 23:55:01');
/*!40000 ALTER TABLE `web_analytic` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-06-02  7:43:52
