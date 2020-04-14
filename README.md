## Overview

This is a simple app that interacts with a database containing information about students, courses, and who's enrolled in which course.
The server is implemented using Service - DAO - Model pattern.

## DB scheme

I used a MySQL database, which you can recreate using this SQL script:

```
SET NAMES utf8 ;

--
-- Table structure for table `disciplines`
--

DROP TABLE IF EXISTS `disciplines`;
SET character_set_client = utf8mb4 ;
CREATE TABLE `disciplines` (
  `id` bigint(20) NOT NULL,
  `name` varchar(45) NOT NULL,
  `credits` decimal(3,1) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `disciplines`
--

LOCK TABLES `disciplines` WRITE;
INSERT INTO `disciplines` VALUES (0,'ProgrammingInJava',3.0);
UNLOCK TABLES;

--
-- Table structure for table `student`
--

DROP TABLE IF EXISTS `student`;
SET character_set_client = utf8mb4 ;
CREATE TABLE `student` (
  `id` bigint(20) NOT NULL,
  `name` varchar(90) NOT NULL,
  `course` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `student`
--

LOCK TABLES `student` WRITE;
INSERT INTO `student` VALUES (0,'The Mighty Admin',3);
UNLOCK TABLES;

--
-- Table structure for table `enrollment`
--

DROP TABLE IF EXISTS `enrollment`;
SET character_set_client = utf8mb4 ;
CREATE TABLE `enrollment` (
  `id` bigint(20) NOT NULL,
  `id_discipline` bigint(20) NOT NULL,
  `id_student` bigint(20) NOT NULL,
  `grade` tinyint(3) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_disciplines_idx` (`id_discipline`),
  KEY `id_student_idx` (`id_student`),
  CONSTRAINT `id_disciplines` FOREIGN KEY (`id_discipline`) REFERENCES `disciplines` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `id_student` FOREIGN KEY (`id_student`) REFERENCES `student` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `enrollment`
--

LOCK TABLES `enrollment` WRITE;
INSERT INTO `enrollment` VALUES (0,0,0,96);
UNLOCK TABLES;
```

Enjoy!