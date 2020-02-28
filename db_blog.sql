-- MySQL dump 10.13  Distrib 8.0.16, for Win64 (x86_64)
--
-- Host: localhost    Database: db_blog
-- ------------------------------------------------------
-- Server version	8.0.13

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `roletopermission`
--

DROP TABLE IF EXISTS `roletopermission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `roletopermission` (
  `idrole` int(11) NOT NULL,
  `idpermission` int(11) NOT NULL,
  PRIMARY KEY (`idrole`,`idpermission`),
  KEY `fk_permissionid_idx` (`idpermission`),
  CONSTRAINT `fk_permissionid` FOREIGN KEY (`idpermission`) REFERENCES `tb_permission` (`idpermission`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_roleid` FOREIGN KEY (`idrole`) REFERENCES `tb_role` (`idrole`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roletopermission`
--

LOCK TABLES `roletopermission` WRITE;
/*!40000 ALTER TABLE `roletopermission` DISABLE KEYS */;
INSERT INTO `roletopermission` VALUES (1,1),(1,2),(1,3),(2,3);
/*!40000 ALTER TABLE `roletopermission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tagtoarticle`
--

DROP TABLE IF EXISTS `tagtoarticle`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tagtoarticle` (
  `tagid` int(11) NOT NULL,
  `articleid` int(11) NOT NULL,
  PRIMARY KEY (`tagid`,`articleid`),
  KEY `fk_articleid_idx` (`articleid`),
  CONSTRAINT `fk_articleid` FOREIGN KEY (`articleid`) REFERENCES `tb_article` (`idarticle`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_tagid` FOREIGN KEY (`tagid`) REFERENCES `tb_tag` (`idtag`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tagtoarticle`
--

LOCK TABLES `tagtoarticle` WRITE;
/*!40000 ALTER TABLE `tagtoarticle` DISABLE KEYS */;
/*!40000 ALTER TABLE `tagtoarticle` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_article`
--

DROP TABLE IF EXISTS `tb_article`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tb_article` (
  `idarticle` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(128) NOT NULL COMMENT '使用BLOB数据类型来存储标题，以支持标题中带表情。',
  `content` text NOT NULL COMMENT '可带emoji表情的文章存储，这里使用MEDIUMBLOB数据类型来存储相关数据。',
  `top` tinyint(1) NOT NULL DEFAULT '0',
  `postTime` datetime NOT NULL,
  `watchedNum` int(11) NOT NULL DEFAULT '0',
  `likeNum` int(11) NOT NULL DEFAULT '0',
  `belongedCategoryId` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`idarticle`),
  UNIQUE KEY `idarticle_UNIQUE` (`idarticle`),
  KEY `fk_article_category_idx` (`belongedCategoryId`),
  CONSTRAINT `fk_article_category` FOREIGN KEY (`belongedCategoryId`) REFERENCES `tb_category` (`idcategory`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_article`
--

LOCK TABLES `tb_article` WRITE;
/*!40000 ALTER TABLE `tb_article` DISABLE KEYS */;
INSERT INTO `tb_article` VALUES (8,'springboot全套配置（thymeleaf，数据库，日志系统，缓存系统）','<a>test\\n111</a></br>',0,'2020-02-19 00:00:00',0,0,2),(9,'战地五通关技巧','test',0,'2020-02-19 00:00:00',0,0,8),(10,'如何弹奏吉他泛音','test',0,'2020-02-19 00:00:00',0,0,7),(11,'cnn中卷积的理解与实现','test',0,'2020-02-19 00:00:00',0,0,4),(12,'关于西红柿炒蛋先放什么的讨论','test',0,'2020-02-19 00:00:00',0,0,5),(13,'hadoop简介','test',0,'2020-02-19 00:00:00',0,0,3),(14,'鸡汤','test',0,'2020-02-19 00:00:00',0,0,6),(15,'iPad pro使用体验','test',0,'2020-02-19 00:00:00',0,0,9),(16,'testInsert','#一级标题\r\n\r\n##二级标题\r\n\r\n###三级标题\r\n\r\n####四级标题\r\n\r\nemoji：:relaxed: :kissing_heart: :expressionless: :rage:\r\n2020-02-28 19:00:16 星期五\r\n\r\n| 1  |  2 |\r\n| ------------ | ------------ |\r\n| 3  | 4  |\r\n| 5  | 6  |\r\n\r\n**~~*加粗的斜体删除*~~**\r\n> 引用\r\n- 无序1\r\n- 无序2\r\n\r\n1. 有序1\r\n2. 有序2\r\n\r\n\r\n------------\r\n\r\n[百度](http://baidu.com \"百度\")',0,'2020-02-28 11:28:54',0,0,1);
/*!40000 ALTER TABLE `tb_article` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_category`
--

DROP TABLE IF EXISTS `tb_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tb_category` (
  `idcategory` int(11) NOT NULL AUTO_INCREMENT,
  `categoryName` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`idcategory`),
  UNIQUE KEY `idcategory_UNIQUE` (`idcategory`),
  UNIQUE KEY `categoryname_UNIQUE` (`categoryName`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_category`
--

LOCK TABLES `tb_category` WRITE;
/*!40000 ALTER TABLE `tb_category` DISABLE KEYS */;
INSERT INTO `tb_category` VALUES (2,'javaweb'),(3,'java大数据'),(4,'python与机器学习'),(6,'动人鸡汤'),(5,'厨艺初修'),(7,'吉他'),(1,'无分类'),(8,'游戏'),(9,'电子产品');
/*!40000 ALTER TABLE `tb_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_comment`
--

DROP TABLE IF EXISTS `tb_comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tb_comment` (
  `idcomment` int(11) NOT NULL AUTO_INCREMENT,
  `content` varchar(256) NOT NULL,
  `postDate` datetime NOT NULL,
  `belongedPosterId` int(11) NOT NULL DEFAULT '0',
  `belongedArticleId` int(11) DEFAULT NULL,
  `belongedQuestionId` int(11) DEFAULT NULL,
  `receiveUserId` int(11) DEFAULT NULL,
  PRIMARY KEY (`idcomment`),
  UNIQUE KEY `idcomment_UNIQUE` (`idcomment`),
  KEY `fk_comment_poster_idx` (`belongedPosterId`),
  KEY `fk_comment_article_idx` (`belongedArticleId`),
  KEY `fk_comment_question_idx` (`belongedQuestionId`),
  CONSTRAINT `fk_comment_article` FOREIGN KEY (`belongedArticleId`) REFERENCES `tb_article` (`idarticle`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_comment_poster` FOREIGN KEY (`belongedPosterId`) REFERENCES `tb_user` (`iduser`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_comment_question` FOREIGN KEY (`belongedQuestionId`) REFERENCES `tb_question` (`idquestion`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_comment`
--

LOCK TABLES `tb_comment` WRITE;
/*!40000 ALTER TABLE `tb_comment` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_permission`
--

DROP TABLE IF EXISTS `tb_permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tb_permission` (
  `idpermission` int(11) NOT NULL AUTO_INCREMENT,
  `permissionName` varchar(32) NOT NULL,
  `url` varchar(255) NOT NULL DEFAULT '/index',
  `description` varchar(255) NOT NULL DEFAULT 'no any description',
  `createTime` datetime NOT NULL,
  PRIMARY KEY (`idpermission`),
  UNIQUE KEY `idpermission_UNIQUE` (`idpermission`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_permission`
--

LOCK TABLES `tb_permission` WRITE;
/*!40000 ALTER TABLE `tb_permission` DISABLE KEYS */;
INSERT INTO `tb_permission` VALUES (1,'addUser','/user/addUser*','no any description','2020-02-19 00:00:00'),(2,'deleteUser','/user/delete*','no any description','2020-02-19 00:00:00'),(3,'queryUser','/user/query*','no any description','2020-02-19 00:00:00');
/*!40000 ALTER TABLE `tb_permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_question`
--

DROP TABLE IF EXISTS `tb_question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tb_question` (
  `idquestion` int(11) NOT NULL AUTO_INCREMENT,
  `content` varchar(256) NOT NULL,
  `postDate` datetime NOT NULL,
  `isChecked` tinyint(1) NOT NULL DEFAULT '0',
  `belongedPosterId` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`idquestion`),
  UNIQUE KEY `idquestion_UNIQUE` (`idquestion`),
  KEY `fk_question_poster_idx` (`belongedPosterId`),
  CONSTRAINT `fk_question_poster` FOREIGN KEY (`belongedPosterId`) REFERENCES `tb_user` (`iduser`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_question`
--

LOCK TABLES `tb_question` WRITE;
/*!40000 ALTER TABLE `tb_question` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_question` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_role`
--

DROP TABLE IF EXISTS `tb_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tb_role` (
  `idrole` int(11) NOT NULL AUTO_INCREMENT,
  `roleName` varchar(32) NOT NULL,
  `description` varchar(255) NOT NULL DEFAULT 'no any description',
  `createTime` datetime NOT NULL,
  PRIMARY KEY (`idrole`),
  UNIQUE KEY `idrole_UNIQUE` (`idrole`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_role`
--

LOCK TABLES `tb_role` WRITE;
/*!40000 ALTER TABLE `tb_role` DISABLE KEYS */;
INSERT INTO `tb_role` VALUES (1,'admin','admin role','2020-02-19 00:00:00'),(2,'normal','normal user, which can add,query,delete comment','2020-02-23 00:00:00');
/*!40000 ALTER TABLE `tb_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_tag`
--

DROP TABLE IF EXISTS `tb_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tb_tag` (
  `idtag` int(11) NOT NULL AUTO_INCREMENT,
  `tagName` varchar(64) NOT NULL,
  PRIMARY KEY (`idtag`),
  UNIQUE KEY `idquestion_UNIQUE` (`idtag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_tag`
--

LOCK TABLES `tb_tag` WRITE;
/*!40000 ALTER TABLE `tb_tag` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_user`
--

DROP TABLE IF EXISTS `tb_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tb_user` (
  `iduser` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) CHARACTER SET utf8 NOT NULL,
  `password` varchar(256) CHARACTER SET utf8 NOT NULL,
  `gender` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'UNKNOWN',
  `email` varchar(128) CHARACTER SET utf8 NOT NULL,
  `phoneNumber` varchar(13) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `registerTime` datetime NOT NULL,
  `latestLoginDate` datetime DEFAULT NULL,
  `isadmin` tinyint(1) NOT NULL DEFAULT '0',
  `ischecked` tinyint(1) NOT NULL DEFAULT '0',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '0冻结1正常2删除',
  `salt` varchar(32) NOT NULL DEFAULT 'ausheygvbmloiuhhtgfr5wgjj8t6g3a',
  PRIMARY KEY (`iduser`),
  UNIQUE KEY `iduser_UNIQUE` (`iduser`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_user`
--

LOCK TABLES `tb_user` WRITE;
/*!40000 ALTER TABLE `tb_user` DISABLE KEYS */;
INSERT INTO `tb_user` VALUES (1,'yuwenxin','84676fea6498b70f66d0866ef7ce836c','MALE','1351819147@qq.com','18770503006','2020-02-15 00:00:00','2020-02-28 12:25:06',1,1,1,'ausheygvbmloiuhhtgfr5wgjj8t6g3a'),(3,'test2','d71605a01256dabfe08b9cb02a66775c','UNKNOWN','test@test.test',NULL,'2020-02-19 11:10:37',NULL,0,0,1,'7c0da3ef2bfda7430350eb73505d56a9'),(4,'test3','dc3783f288e064d233419ca180e0b0c8','UNKNOWN','yuwenxin980214@gmail.com','18770503006','2020-02-19 12:48:19',NULL,0,0,1,'89c47a7739ff5f3ea984657aed2527c5'),(8,'update','test','MALE','test@test.test',NULL,'2020-02-19 11:10:37','2020-02-26 02:36:29',0,0,1,'ausheygvbmloiuhhtgfr5wgjj8t6g3a');
/*!40000 ALTER TABLE `tb_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usertorole`
--

DROP TABLE IF EXISTS `usertorole`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `usertorole` (
  `iduser` int(11) NOT NULL,
  `idrole` int(11) NOT NULL,
  PRIMARY KEY (`iduser`,`idrole`),
  KEY `fk_roleid_idx` (`idrole`),
  CONSTRAINT `fk_roleid2` FOREIGN KEY (`idrole`) REFERENCES `tb_role` (`idrole`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_userid` FOREIGN KEY (`iduser`) REFERENCES `tb_user` (`iduser`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usertorole`
--

LOCK TABLES `usertorole` WRITE;
/*!40000 ALTER TABLE `usertorole` DISABLE KEYS */;
INSERT INTO `usertorole` VALUES (1,1),(3,2),(4,2);
/*!40000 ALTER TABLE `usertorole` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-02-28 22:58:16
