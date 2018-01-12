-- MySQL dump 10.13  Distrib 5.6.27, for Win64 (x86_64)
--
-- Host: localhost    Database: new_fort
-- ------------------------------------------------------
-- Server version	5.6.27

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
-- Current Database: `new_fort`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `new_fort` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci */;

USE `new_fort`;

--
-- Table structure for table `account`
--

DROP TABLE IF EXISTS `account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `account` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `password` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `res_id` int(11) DEFAULT NULL,
  `type` int(11) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `res_id` (`res_id`),
  CONSTRAINT `account_ibfk_1` FOREIGN KEY (`res_id`) REFERENCES `resource` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account`
--

LOCK TABLES `account` WRITE;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
INSERT INTO `account` VALUES (10,'root','',3,1),(11,'nemo','',3,0),(12,'fort','',3,0);
/*!40000 ALTER TABLE `account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `employee` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ä¸»é”®',
  `name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'å§“å',
  `login_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'ç™»å½•å',
  `password` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'å¯†ç ',
  `create_time` datetime DEFAULT NULL COMMENT 'åˆ›å»ºæ—¶é—´',
  `update_time` datetime DEFAULT NULL COMMENT 'ä¿®æ”¹æ—¶é—´',
  `memo` text COLLATE utf8_unicode_ci COMMENT 'å¤‡æ³¨ä¿¡æ¯',
  `mobile` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'æ‰‹æœºå·ç ',
  `tel` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'å›ºå®šç”µè¯',
  `organization` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'ç»„ç»‡',
  `update_pwd` int(11) DEFAULT NULL COMMENT 'ä¸‹æ¬¡ç™»å½•æ˜¯å¦ä¿®æ”¹å¯†ç ï¼Œ1ï¼šæ˜¯ï¼Œ0ï¼šå¦',
  `auth_type` int(11) DEFAULT NULL COMMENT 'ç™»å½•è®¤è¯æ–¹å¼ï¼Œ0ï¼šå¯†ç ç™»å½•',
  `status` int(11) DEFAULT NULL COMMENT 'ç”¨æˆ·çŠ¶æ€ï¼Œ0:é”å®šï¼Œ1ï¼šæ¿€æ´»',
  `start_week` int(11) DEFAULT NULL COMMENT 'å·¥ä½œæ—¥èµ·å§‹æ—¶é—´',
  `end_week` int(11) DEFAULT NULL COMMENT 'å·¥ä½œæ—¥ç»“æŸæ—¶é—´',
  `pwd_timeout` datetime DEFAULT NULL COMMENT 'å¯†ç æœŸé™',
  `email` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'é‚®ç®±åœ°å€',
  `role_id` int(11) DEFAULT NULL COMMENT 'è§’è‰²ID',
  `group_id` int(11) DEFAULT NULL COMMENT 'åˆ†ç»„ID',
  PRIMARY KEY (`id`),
  KEY `role_id` (`role_id`),
  KEY `group_id` (`group_id`),
  CONSTRAINT `employee_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE SET NULL,
  CONSTRAINT `employee_ibfk_2` FOREIGN KEY (`group_id`) REFERENCES `employee_group` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee`
--

LOCK TABLES `employee` WRITE;
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
INSERT INTO `employee` VALUES (1,'admin','admin','123456','2017-08-23 17:18:08','2017-09-15 15:16:26',NULL,NULL,NULL,NULL,0,0,1,0,4,NULL,'bugbycode@gmail.com',1,1),(2,'å¼ å¿—åŠŸ','zhangzhigong','123456',NULL,'2018-01-10 13:19:35',NULL,NULL,NULL,NULL,0,0,1,1,4,NULL,'bugbycode@gmail.com',1,1),(3,'å¼ å°å°','zhangxiaoxiao','123',NULL,NULL,NULL,NULL,NULL,NULL,0,0,1,0,4,'2017-09-08 00:00:00','',NULL,NULL),(5,'æå››','lisi','123456',NULL,'2017-10-11 17:31:01',NULL,NULL,NULL,NULL,0,0,1,0,4,'2017-09-08 00:00:00','',1,1),(6,'å¼ ä¸‰','zhangsan','123456',NULL,'2018-01-04 14:26:51',NULL,NULL,NULL,NULL,0,0,1,0,4,NULL,'',NULL,NULL),(9,'test','bugbycode@gmail.com','123456',NULL,NULL,NULL,NULL,NULL,NULL,0,0,1,0,4,NULL,'',1,NULL);
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employee_group`
--

DROP TABLE IF EXISTS `employee_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `employee_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ä¸»é”®',
  `name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'åˆ†ç»„åç§°',
  `memo` text COLLATE utf8_unicode_ci COMMENT 'å¤‡æ³¨',
  `create_time` datetime DEFAULT NULL COMMENT 'åˆ›å»ºæ—¶é—´',
  `update_time` datetime DEFAULT NULL COMMENT 'ä¿®æ”¹æ—¶é—´',
  `role_id` int(11) DEFAULT NULL COMMENT 'è§’è‰²ID',
  PRIMARY KEY (`id`),
  KEY `role_id` (`role_id`),
  CONSTRAINT `employee_group_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee_group`
--

LOCK TABLES `employee_group` WRITE;
/*!40000 ALTER TABLE `employee_group` DISABLE KEYS */;
INSERT INTO `employee_group` VALUES (1,'ç®¡ç†å‘˜','æ‹¥æœ‰ç³»ç»Ÿæœ€é«˜æƒé™ï¼Œå¯ä»¥ç®¡ç†æ‰€æœ‰ä¿¡æ¯ã€‚','2017-09-12 15:28:30',NULL,1),(4,'æ™®é€šç”¨æˆ·','åªæœ‰ç™»å½•çš„æƒé™ã€‚','2017-09-15 13:46:35',NULL,NULL);
/*!40000 ALTER TABLE `employee_group` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `oauth_access_token`
--

DROP TABLE IF EXISTS `oauth_access_token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `oauth_access_token` (
  `token_id` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `token` blob,
  `authentication_id` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `user_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `client_id` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `authentication` blob,
  `refresh_token` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `oauth_access_token`
--

LOCK TABLES `oauth_access_token` WRITE;
/*!40000 ALTER TABLE `oauth_access_token` DISABLE KEYS */;
INSERT INTO `oauth_access_token` VALUES ('0de41d80001c038564ec82242d5330d2','¬í\0sr\0Corg.springframework.security.oauth2.common.DefaultOAuth2AccessToken²6$úÎ\0L\0additionalInformationt\0Ljava/util/Map;L\0\nexpirationt\0Ljava/util/Date;L\0refreshTokent\0?Lorg/springframework/security/oauth2/common/OAuth2RefreshToken;L\0scopet\0Ljava/util/Set;L\0	tokenTypet\0Ljava/lang/String;L\0valueq\0~\0xpsr\0java.util.Collections$EmptyMapY6…ZÜçĞ\0\0xpsr\0java.util.DatehjKYt\0\0xpw\0\0_å$Ñ*xsr\0Lorg.springframework.security.oauth2.common.DefaultExpiringOAuth2RefreshToken/ßGcĞÉ·\0L\0\nexpirationq\0~\0xr\0Dorg.springframework.security.oauth2.common.DefaultOAuth2RefreshTokensá\ncTÔ^\0L\0valueq\0~\0xpt\0$83944e87-955c-4e38-a26c-54ed045d7b41sq\0~\0	w\0\0`N¨¯xsr\0%java.util.Collections$UnmodifiableSet€’Ñ›€U\0\0xr\0,java.util.Collections$UnmodifiableCollectionB\0€Ë^÷\0L\0ct\0Ljava/util/Collection;xpsr\0java.util.LinkedHashSetØl×Z•İ*\0\0xr\0java.util.HashSetºD…•–¸·4\0\0xpw\0\0\0?@\0\0\0\0\0t\0appxt\0bearert\0$0454c9d9-e4ac-4548-a6f6-b79b88443d84','74bf6906e64b0de98af25facda24dd0a',NULL,'client','¬í\0sr\0Aorg.springframework.security.oauth2.provider.OAuth2Authentication½@bR\0L\0\rstoredRequestt\0<Lorg/springframework/security/oauth2/provider/OAuth2Request;L\0userAuthenticationt\02Lorg/springframework/security/core/Authentication;xr\0Gorg.springframework.security.authentication.AbstractAuthenticationTokenÓª(~nGd\0Z\0\rauthenticatedL\0authoritiest\0Ljava/util/Collection;L\0detailst\0Ljava/lang/Object;xp\0sr\0&java.util.Collections$UnmodifiableListü%1µì\0L\0listt\0Ljava/util/List;xr\0,java.util.Collections$UnmodifiableCollectionB\0€Ë^÷\0L\0cq\0~\0xpsr\0java.util.ArrayListxÒ™Ça\0I\0sizexp\0\0\0\0w\0\0\0\0xq\0~\0psr\0:org.springframework.security.oauth2.provider.OAuth2Request\0\0\0\0\0\0\0\0Z\0approvedL\0authoritiesq\0~\0L\0\nextensionst\0Ljava/util/Map;L\0redirectUrit\0Ljava/lang/String;L\0refresht\0;Lorg/springframework/security/oauth2/provider/TokenRequest;L\0resourceIdst\0Ljava/util/Set;L\0\rresponseTypesq\0~\0xr\08org.springframework.security.oauth2.provider.BaseRequest6(z>£qi½\0L\0clientIdq\0~\0L\0requestParametersq\0~\0L\0scopeq\0~\0xpt\0clientsr\0%java.util.Collections$UnmodifiableMapñ¥¨ştõB\0L\0mq\0~\0xpsr\0java.util.HashMapÚÁÃ`Ñ\0F\0\nloadFactorI\0	thresholdxp?@\0\0\0\0\0w\0\0\0\0\0\0t\0\ngrant_typet\0client_credentialst\0	client_idt\0clientt\0scopet\0appxsr\0%java.util.Collections$UnmodifiableSet€’Ñ›€U\0\0xq\0~\0	sr\0java.util.LinkedHashSetØl×Z•İ*\0\0xr\0java.util.HashSetºD…•–¸·4\0\0xpw\0\0\0?@\0\0\0\0\0q\0~\0xsq\0~\0\"w\0\0\0?@\0\0\0\0\0\0xsq\0~\0?@\0\0\0\0\0\0w\0\0\0\0\0\0\0xppsq\0~\0\"w\0\0\0?@\0\0\0\0\0\0xsq\0~\0\"w\0\0\0?@\0\0\0\0\0\0xp','1fa97b38a8cf64357adb60200b8dba4c'),('64df0cfb5dc611359071b16be84f5989','¬í\0sr\0Corg.springframework.security.oauth2.common.DefaultOAuth2AccessToken²6$úÎ\0L\0additionalInformationt\0Ljava/util/Map;L\0\nexpirationt\0Ljava/util/Date;L\0refreshTokent\0?Lorg/springframework/security/oauth2/common/OAuth2RefreshToken;L\0scopet\0Ljava/util/Set;L\0	tokenTypet\0Ljava/lang/String;L\0valueq\0~\0xpsr\0java.util.Collections$EmptyMapY6…ZÜçĞ\0\0xpsr\0java.util.DatehjKYt\0\0xpw\0\0_å$Ï…xsr\0Lorg.springframework.security.oauth2.common.DefaultExpiringOAuth2RefreshToken/ßGcĞÉ·\0L\0\nexpirationq\0~\0xr\0Dorg.springframework.security.oauth2.common.DefaultOAuth2RefreshTokensá\ncTÔ^\0L\0valueq\0~\0xpt\0$9fba821d-cbea-40c1-bb48-e173878eb6a1sq\0~\0	w\0\0`N¹R3xsr\0%java.util.Collections$UnmodifiableSet€’Ñ›€U\0\0xr\0,java.util.Collections$UnmodifiableCollectionB\0€Ë^÷\0L\0ct\0Ljava/util/Collection;xpsr\0java.util.LinkedHashSetØl×Z•İ*\0\0xr\0java.util.HashSetºD…•–¸·4\0\0xpw\0\0\0?@\0\0\0\0\0t\0appxt\0bearert\0$bfefca2f-ba58-424a-8a3a-9e89ef2349c2','287b1b4095d75bc94942ea499ad78a0c','user','client','¬í\0sr\0Aorg.springframework.security.oauth2.provider.OAuth2Authentication½@bR\0L\0\rstoredRequestt\0<Lorg/springframework/security/oauth2/provider/OAuth2Request;L\0userAuthenticationt\02Lorg/springframework/security/core/Authentication;xr\0Gorg.springframework.security.authentication.AbstractAuthenticationTokenÓª(~nGd\0Z\0\rauthenticatedL\0authoritiest\0Ljava/util/Collection;L\0detailst\0Ljava/lang/Object;xp\0sr\0&java.util.Collections$UnmodifiableListü%1µì\0L\0listt\0Ljava/util/List;xr\0,java.util.Collections$UnmodifiableCollectionB\0€Ë^÷\0L\0cq\0~\0xpsr\0java.util.ArrayListxÒ™Ça\0I\0sizexp\0\0\0w\0\0\0sr\0Borg.springframework.security.core.authority.SimpleGrantedAuthority\0\0\0\0\0\0¤\0L\0rolet\0Ljava/lang/String;xpt\0	ROLE_USERxq\0~\0psr\0:org.springframework.security.oauth2.provider.OAuth2Request\0\0\0\0\0\0\0\0Z\0approvedL\0authoritiesq\0~\0L\0\nextensionst\0Ljava/util/Map;L\0redirectUriq\0~\0L\0refresht\0;Lorg/springframework/security/oauth2/provider/TokenRequest;L\0resourceIdst\0Ljava/util/Set;L\0\rresponseTypesq\0~\0xr\08org.springframework.security.oauth2.provider.BaseRequest6(z>£qi½\0L\0clientIdq\0~\0L\0requestParametersq\0~\0L\0scopeq\0~\0xpt\0clientsr\0%java.util.Collections$UnmodifiableMapñ¥¨ştõB\0L\0mq\0~\0xpsr\0java.util.HashMapÚÁÃ`Ñ\0F\0\nloadFactorI\0	thresholdxp?@\0\0\0\0\0w\0\0\0\0\0\0t\0\ngrant_typet\0passwordt\0	client_idt\0clientt\0scopet\0appt\0usernamet\0userxsr\0%java.util.Collections$UnmodifiableSet€’Ñ›€U\0\0xq\0~\0	sr\0java.util.LinkedHashSetØl×Z•İ*\0\0xr\0java.util.HashSetºD…•–¸·4\0\0xpw\0\0\0?@\0\0\0\0\0q\0~\0!xsq\0~\0\'w\0\0\0?@\0\0\0\0\0\0xsq\0~\0\Z?@\0\0\0\0\0\0w\0\0\0\0\0\0\0xppsq\0~\0\'w\0\0\0?@\0\0\0\0\0\0xsq\0~\0\'w\0\0\0?@\0\0\0\0\0\0xsr\0Oorg.springframework.security.authentication.UsernamePasswordAuthenticationToken\0\0\0\0\0\0¤\0L\0credentialsq\0~\0L\0	principalq\0~\0xq\0~\0sq\0~\0sq\0~\0\0\0\0w\0\0\0q\0~\0xq\0~\00sr\0java.util.LinkedHashMap4ÀN\\lÀû\0Z\0accessOrderxq\0~\0\Z?@\0\0\0\0\0w\0\0\0\0\0\0q\0~\0q\0~\0q\0~\0 q\0~\0!t\0\rclient_secrett\0secretq\0~\0q\0~\0q\0~\0\"q\0~\0#x\0psr\02org.springframework.security.core.userdetails.User\0\0\0\0\0\0¤\0Z\0accountNonExpiredZ\0accountNonLockedZ\0credentialsNonExpiredZ\0enabledL\0authoritiesq\0~\0L\0passwordq\0~\0L\0usernameq\0~\0xpsq\0~\0$sr\0java.util.TreeSetİ˜P“•í‡[\0\0xpsr\0Forg.springframework.security.core.userdetails.User$AuthorityComparator\0\0\0\0\0\0¤\0\0xpw\0\0\0q\0~\0xpt\0user','38c11a607939d9e9a396a86afe0c89d5');
/*!40000 ALTER TABLE `oauth_access_token` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `oauth_client_details`
--

DROP TABLE IF EXISTS `oauth_client_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `oauth_client_details` (
  `client_id` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `client_secret` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `resource_ids` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `scope` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `authorized_grant_types` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `web_server_redirect_uri` text COLLATE utf8_unicode_ci,
  `authorities` text COLLATE utf8_unicode_ci,
  `access_token_validity` int(11) DEFAULT NULL,
  `refresh_token_validity` int(11) DEFAULT NULL,
  `additional_information` text COLLATE utf8_unicode_ci,
  `autoapprove` text COLLATE utf8_unicode_ci
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `oauth_client_details`
--

LOCK TABLES `oauth_client_details` WRITE;
/*!40000 ALTER TABLE `oauth_client_details` DISABLE KEYS */;
INSERT INTO `oauth_client_details` VALUES ('client','secret',NULL,'app','authorization_code,password,client_credentials,refresh_token','http://www.baidu.com',NULL,NULL,NULL,NULL,NULL),('zzg','123',NULL,'bbs','authorization_code,password','http://localhost',NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `oauth_client_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `oauth_refresh_token`
--

DROP TABLE IF EXISTS `oauth_refresh_token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `oauth_refresh_token` (
  `token_id` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `token` blob,
  `authentication` blob
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `oauth_refresh_token`
--

LOCK TABLES `oauth_refresh_token` WRITE;
/*!40000 ALTER TABLE `oauth_refresh_token` DISABLE KEYS */;
INSERT INTO `oauth_refresh_token` VALUES ('38c11a607939d9e9a396a86afe0c89d5','¬í\0sr\0Lorg.springframework.security.oauth2.common.DefaultExpiringOAuth2RefreshToken/ßGcĞÉ·\0L\0\nexpirationt\0Ljava/util/Date;xr\0Dorg.springframework.security.oauth2.common.DefaultOAuth2RefreshTokensá\ncTÔ^\0L\0valuet\0Ljava/lang/String;xpt\0$9fba821d-cbea-40c1-bb48-e173878eb6a1sr\0java.util.DatehjKYt\0\0xpw\0\0`N¹R3x','¬í\0sr\0Aorg.springframework.security.oauth2.provider.OAuth2Authentication½@bR\0L\0\rstoredRequestt\0<Lorg/springframework/security/oauth2/provider/OAuth2Request;L\0userAuthenticationt\02Lorg/springframework/security/core/Authentication;xr\0Gorg.springframework.security.authentication.AbstractAuthenticationTokenÓª(~nGd\0Z\0\rauthenticatedL\0authoritiest\0Ljava/util/Collection;L\0detailst\0Ljava/lang/Object;xp\0sr\0&java.util.Collections$UnmodifiableListü%1µì\0L\0listt\0Ljava/util/List;xr\0,java.util.Collections$UnmodifiableCollectionB\0€Ë^÷\0L\0cq\0~\0xpsr\0java.util.ArrayListxÒ™Ça\0I\0sizexp\0\0\0w\0\0\0sr\0Borg.springframework.security.core.authority.SimpleGrantedAuthority\0\0\0\0\0\0¤\0L\0rolet\0Ljava/lang/String;xpt\0	ROLE_USERxq\0~\0psr\0:org.springframework.security.oauth2.provider.OAuth2Request\0\0\0\0\0\0\0\0Z\0approvedL\0authoritiesq\0~\0L\0\nextensionst\0Ljava/util/Map;L\0redirectUriq\0~\0L\0refresht\0;Lorg/springframework/security/oauth2/provider/TokenRequest;L\0resourceIdst\0Ljava/util/Set;L\0\rresponseTypesq\0~\0xr\08org.springframework.security.oauth2.provider.BaseRequest6(z>£qi½\0L\0clientIdq\0~\0L\0requestParametersq\0~\0L\0scopeq\0~\0xpt\0clientsr\0%java.util.Collections$UnmodifiableMapñ¥¨ştõB\0L\0mq\0~\0xpsr\0java.util.HashMapÚÁÃ`Ñ\0F\0\nloadFactorI\0	thresholdxp?@\0\0\0\0\0w\0\0\0\0\0\0t\0\ngrant_typet\0passwordt\0	client_idt\0clientt\0scopet\0appt\0usernamet\0userxsr\0%java.util.Collections$UnmodifiableSet€’Ñ›€U\0\0xq\0~\0	sr\0java.util.LinkedHashSetØl×Z•İ*\0\0xr\0java.util.HashSetºD…•–¸·4\0\0xpw\0\0\0?@\0\0\0\0\0q\0~\0!xsq\0~\0\'w\0\0\0?@\0\0\0\0\0\0xsq\0~\0\Z?@\0\0\0\0\0\0w\0\0\0\0\0\0\0xppsq\0~\0\'w\0\0\0?@\0\0\0\0\0\0xsq\0~\0\'w\0\0\0?@\0\0\0\0\0\0xsr\0Oorg.springframework.security.authentication.UsernamePasswordAuthenticationToken\0\0\0\0\0\0¤\0L\0credentialsq\0~\0L\0	principalq\0~\0xq\0~\0sq\0~\0sq\0~\0\0\0\0w\0\0\0q\0~\0xq\0~\00sr\0java.util.LinkedHashMap4ÀN\\lÀû\0Z\0accessOrderxq\0~\0\Z?@\0\0\0\0\0w\0\0\0\0\0\0q\0~\0q\0~\0q\0~\0 q\0~\0!t\0\rclient_secrett\0secretq\0~\0q\0~\0q\0~\0\"q\0~\0#x\0psr\02org.springframework.security.core.userdetails.User\0\0\0\0\0\0¤\0Z\0accountNonExpiredZ\0accountNonLockedZ\0credentialsNonExpiredZ\0enabledL\0authoritiesq\0~\0L\0passwordq\0~\0L\0usernameq\0~\0xpsq\0~\0$sr\0java.util.TreeSetİ˜P“•í‡[\0\0xpsr\0Forg.springframework.security.core.userdetails.User$AuthorityComparator\0\0\0\0\0\0¤\0\0xpw\0\0\0q\0~\0xpt\0user'),('1fa97b38a8cf64357adb60200b8dba4c','¬í\0sr\0Lorg.springframework.security.oauth2.common.DefaultExpiringOAuth2RefreshToken/ßGcĞÉ·\0L\0\nexpirationt\0Ljava/util/Date;xr\0Dorg.springframework.security.oauth2.common.DefaultOAuth2RefreshTokensá\ncTÔ^\0L\0valuet\0Ljava/lang/String;xpt\0$83944e87-955c-4e38-a26c-54ed045d7b41sr\0java.util.DatehjKYt\0\0xpw\0\0`N¨¯x','¬í\0sr\0Aorg.springframework.security.oauth2.provider.OAuth2Authentication½@bR\0L\0\rstoredRequestt\0<Lorg/springframework/security/oauth2/provider/OAuth2Request;L\0userAuthenticationt\02Lorg/springframework/security/core/Authentication;xr\0Gorg.springframework.security.authentication.AbstractAuthenticationTokenÓª(~nGd\0Z\0\rauthenticatedL\0authoritiest\0Ljava/util/Collection;L\0detailst\0Ljava/lang/Object;xp\0sr\0&java.util.Collections$UnmodifiableListü%1µì\0L\0listt\0Ljava/util/List;xr\0,java.util.Collections$UnmodifiableCollectionB\0€Ë^÷\0L\0cq\0~\0xpsr\0java.util.ArrayListxÒ™Ça\0I\0sizexp\0\0\0\0w\0\0\0\0xq\0~\0psr\0:org.springframework.security.oauth2.provider.OAuth2Request\0\0\0\0\0\0\0\0Z\0approvedL\0authoritiesq\0~\0L\0\nextensionst\0Ljava/util/Map;L\0redirectUrit\0Ljava/lang/String;L\0refresht\0;Lorg/springframework/security/oauth2/provider/TokenRequest;L\0resourceIdst\0Ljava/util/Set;L\0\rresponseTypesq\0~\0xr\08org.springframework.security.oauth2.provider.BaseRequest6(z>£qi½\0L\0clientIdq\0~\0L\0requestParametersq\0~\0L\0scopeq\0~\0xpt\0clientsr\0%java.util.Collections$UnmodifiableMapñ¥¨ştõB\0L\0mq\0~\0xpsr\0java.util.HashMapÚÁÃ`Ñ\0F\0\nloadFactorI\0	thresholdxp?@\0\0\0\0\0w\0\0\0\0\0\0t\0\ngrant_typet\0client_credentialst\0	client_idt\0clientt\0scopet\0appxsr\0%java.util.Collections$UnmodifiableSet€’Ñ›€U\0\0xq\0~\0	sr\0java.util.LinkedHashSetØl×Z•İ*\0\0xr\0java.util.HashSetºD…•–¸·4\0\0xpw\0\0\0?@\0\0\0\0\0q\0~\0xsq\0~\0\"w\0\0\0?@\0\0\0\0\0\0xsq\0~\0?@\0\0\0\0\0\0w\0\0\0\0\0\0\0xppsq\0~\0\"w\0\0\0?@\0\0\0\0\0\0xsq\0~\0\"w\0\0\0?@\0\0\0\0\0\0xp');
/*!40000 ALTER TABLE `oauth_refresh_token` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `os`
--

DROP TABLE IF EXISTS `os`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `os` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'æ“ä½œç³»ç»Ÿåç§°',
  `type_id` int(11) DEFAULT NULL COMMENT 'æ“ä½œç³»ç»Ÿç±»å‹ID',
  PRIMARY KEY (`id`),
  KEY `type_id` (`type_id`),
  CONSTRAINT `os_ibfk_1` FOREIGN KEY (`type_id`) REFERENCES `res_type` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `os`
--

LOCK TABLES `os` WRITE;
/*!40000 ALTER TABLE `os` DISABLE KEYS */;
INSERT INTO `os` VALUES (1,'Debian',1),(2,'CentOs',1),(3,'Windows 7',2),(4,'Windows Server 2003',2);
/*!40000 ALTER TABLE `os` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `res_type`
--

DROP TABLE IF EXISTS `res_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `res_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'è®¾å¤‡ç±»å‹',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `res_type`
--

LOCK TABLES `res_type` WRITE;
/*!40000 ALTER TABLE `res_type` DISABLE KEYS */;
INSERT INTO `res_type` VALUES (1,'Linux'),(2,'Windows');
/*!40000 ALTER TABLE `res_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `resource`
--

DROP TABLE IF EXISTS `resource`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `resource` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `ip` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `type_id` int(11) DEFAULT NULL,
  `os_id` int(11) DEFAULT NULL,
  `use_ssh` int(11) DEFAULT '0',
  `ssh_port` int(11) DEFAULT '22',
  `use_sftp` int(11) DEFAULT '0',
  `sftp_port` int(11) DEFAULT '22',
  `use_rdp` int(11) DEFAULT '0',
  `rdp_port` int(11) DEFAULT '3389',
  `status` int(11) DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `type_id` (`type_id`),
  KEY `os_id` (`os_id`),
  CONSTRAINT `resource_ibfk_1` FOREIGN KEY (`type_id`) REFERENCES `res_type` (`id`) ON DELETE SET NULL,
  CONSTRAINT `resource_ibfk_2` FOREIGN KEY (`os_id`) REFERENCES `os` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `resource`
--

LOCK TABLES `resource` WRITE;
/*!40000 ALTER TABLE `resource` DISABLE KEYS */;
INSERT INTO `resource` VALUES (3,'è®¡ç®—æœº','192.168.1.101',1,1,1,22,0,22,0,3389,1,'2018-01-12 15:53:00','2018-01-12 16:41:49');
/*!40000 ALTER TABLE `resource` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'è§’è‰²ID',
  `name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'è§’è‰²åç§°',
  `memo` text COLLATE utf8_unicode_ci COMMENT 'å¤‡æ³¨ä¿¡æ¯',
  `create_time` datetime DEFAULT NULL COMMENT 'åˆ›å»ºæ—¶é—´',
  `update_time` datetime DEFAULT NULL COMMENT 'ä¿®æ”¹æ—¶é—´',
  `new_emp` tinyint(4) DEFAULT '0' COMMENT 'æ–°å»ºç”¨æˆ·æƒé™',
  `update_emp` tinyint(4) DEFAULT '0' COMMENT 'ä¿®æ”¹ç”¨æˆ·æƒé™',
  `del_emp` tinyint(4) DEFAULT '0' COMMENT 'åˆ é™¤ç”¨æˆ·æƒé™',
  `view_emp` tinyint(4) DEFAULT '0' COMMENT 'æŸ¥çœ‹ç”¨æˆ·æƒé™',
  `import_emp` tinyint(4) DEFAULT '0' COMMENT 'å¯¼å…¥ç”¨æˆ·æƒé™',
  `export_emp` tinyint(4) DEFAULT '0' COMMENT 'å¯¼å‡ºç”¨æˆ·æƒé™',
  `new_role` tinyint(4) DEFAULT '0' COMMENT 'æ–°å»ºè§’è‰²æƒé™',
  `update_role` tinyint(4) DEFAULT '0' COMMENT 'ä¿®æ”¹è§’è‰²æƒé™',
  `del_role` tinyint(4) DEFAULT '0' COMMENT 'åˆ é™¤è§’è‰²æƒé™',
  `view_role` tinyint(4) DEFAULT '0' COMMENT 'æŸ¥çœ‹è§’è‰²æƒé™',
  `new_emp_group` tinyint(4) DEFAULT '0' COMMENT 'æ–°å»ºç”¨æˆ·ç»„æƒé™',
  `update_emp_group` tinyint(4) DEFAULT '0' COMMENT 'ä¿®æ”¹ç”¨æˆ·ç»„æƒé™',
  `del_emp_group` tinyint(4) DEFAULT '0' COMMENT 'åˆ é™¤ç”¨æˆ·ç»„æƒé™',
  `view_emp_group` tinyint(4) DEFAULT '0' COMMENT 'æŸ¥çœ‹ç”¨æˆ·ç»„æƒé™',
  `new_res` tinyint(4) DEFAULT '0' COMMENT 'æ–°å»ºèµ„æºæƒé™',
  `update_res` tinyint(4) DEFAULT '0' COMMENT 'ä¿®æ”¹èµ„æºæƒé™',
  `del_res` tinyint(4) DEFAULT '0' COMMENT 'åˆ é™¤èµ„æºæƒé™',
  `view_res` tinyint(4) DEFAULT '0' COMMENT 'æŸ¥çœ‹èµ„æºæƒé™',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'ç³»ç»Ÿç®¡ç†å‘˜','å…·æœ‰ç³»ç»Ÿæœ€é«˜æƒé™ï¼Œå¯ç®¡ç†ç³»ç»Ÿæ‰€æœ‰ä¿¡æ¯ã€‚','2017-08-30 13:25:52',NULL,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1),(2,'è®¾å¤‡ç®¡ç†å‘˜','','2018-01-12 16:54:16',NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1);
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-01-12 17:12:25
