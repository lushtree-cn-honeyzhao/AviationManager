-- MySQL dump 10.13  Distrib 5.6.16, for Linux (x86_64)
--
-- Host: localhost    Database: aviation_prd
-- ------------------------------------------------------
-- Server version	5.6.16

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
-- Table structure for table `t_aviation_buyers`
--

DROP TABLE IF EXISTS `t_aviation_buyers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_aviation_buyers` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `create_user` varchar(36) DEFAULT NULL,
  `status` int(11) DEFAULT '0',
  `update_time` datetime DEFAULT NULL,
  `update_user` varchar(36) DEFAULT NULL,
  `version` int(11) DEFAULT '1',
  `area` varchar(255) DEFAULT NULL,
  `auto_paying` int(11) DEFAULT NULL,
  `buyers_name` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `company_address` varchar(255) DEFAULT NULL,
  `company_name` varchar(255) DEFAULT NULL,
  `company_url` varchar(255) DEFAULT NULL,
  `confirm_password` varchar(255) DEFAULT NULL,
  `contact_person` varchar(255) DEFAULT NULL,
  `credit_balance` float DEFAULT NULL,
  `credit_certification` int(11) DEFAULT NULL,
  `customer_manager` varchar(255) DEFAULT NULL,
  `effected` tinyint(1) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `factorage_rate` float DEFAULT NULL,
  `fax_number` varchar(255) DEFAULT NULL,
  `freeze_status` int(11) DEFAULT NULL,
  `invalid_ticket_worktime` varchar(255) DEFAULT NULL,
  `liabilities` varchar(255) DEFAULT NULL,
  `max_factorage_rate` float DEFAULT NULL,
  `mobile` varchar(255) DEFAULT NULL,
  `office_sn` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `qq` varchar(255) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `server_time` varchar(255) DEFAULT NULL,
  `server_email` varchar(255) DEFAULT NULL,
  `service_ticket_begintime` varchar(255) DEFAULT NULL,
  `service_ticket_endtime` varchar(255) DEFAULT NULL,
  `settlement_type` int(11) DEFAULT NULL,
  `telephone` varchar(255) DEFAULT NULL,
  `temp_amount` float DEFAULT NULL,
  `ticket_rate` varchar(255) DEFAULT NULL,
  `ticket_remind_email` varchar(255) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  `work_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_aviation_buyers`
--

LOCK TABLES `t_aviation_buyers` WRITE;
/*!40000 ALTER TABLE `t_aviation_buyers` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_aviation_buyers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_aviation_flight_info`
--

DROP TABLE IF EXISTS `t_aviation_flight_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_aviation_flight_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `create_user` varchar(36) DEFAULT NULL,
  `status` int(11) DEFAULT '0',
  `update_time` datetime DEFAULT NULL,
  `update_user` varchar(36) DEFAULT NULL,
  `version` int(11) DEFAULT '1',
  `arrival` varchar(45) DEFAULT NULL,
  `arrival_airport_terminal` varchar(45) DEFAULT NULL,
  `carrier` varchar(45) DEFAULT NULL,
  `deleted` tinyint(4) DEFAULT '0',
  `end_time` datetime DEFAULT NULL,
  `number` varchar(45) DEFAULT NULL,
  `positions` varchar(45) DEFAULT NULL,
  `start_airport_terminal` varchar(45) DEFAULT NULL,
  `start_time` datetime DEFAULT NULL,
  `startaddress` varchar(45) DEFAULT NULL,
  `t_aviation_order_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_r44eg2eu4cdtak3tlsugsuv97` (`t_aviation_order_id`),
  CONSTRAINT `FK_r44eg2eu4cdtak3tlsugsuv97` FOREIGN KEY (`t_aviation_order_id`) REFERENCES `t_aviation_order` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_aviation_flight_info`
--

LOCK TABLES `t_aviation_flight_info` WRITE;
/*!40000 ALTER TABLE `t_aviation_flight_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_aviation_flight_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_aviation_order`
--

DROP TABLE IF EXISTS `t_aviation_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_aviation_order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `create_user` varchar(36) DEFAULT NULL,
  `status` int(11) DEFAULT '0',
  `update_time` datetime DEFAULT NULL,
  `update_user` varchar(36) DEFAULT NULL,
  `version` int(11) DEFAULT '1',
  `big_sn` varchar(255) DEFAULT NULL,
  `child_agency_fee` float DEFAULT NULL,
  `child_face_amount` float DEFAULT NULL,
  `child_inland_revenue` float DEFAULT NULL,
  `child_q_tax` float DEFAULT NULL,
  `child_return_fee` float DEFAULT NULL,
  `custom` varchar(255) DEFAULT NULL,
  `custom_real_receivable` float DEFAULT NULL,
  `custom_receivable` float DEFAULT NULL,
  `fare_match` int(11) DEFAULT NULL,
  `grown_agency_fee` float DEFAULT NULL,
  `grown_face_amount` float DEFAULT NULL,
  `grown_inland_revenue` float DEFAULT NULL,
  `grown_q_tax` float DEFAULT NULL,
  `grown_return_fee` float DEFAULT NULL,
  `handling_charges` float DEFAULT NULL,
  `order_sn` varchar(200) DEFAULT NULL,
  `order_type` int(11) DEFAULT NULL,
  `order_status` int(11) DEFAULT NULL,
  `payment_sn` varchar(255) DEFAULT NULL,
  `payment_type` varchar(255) DEFAULT NULL,
  `platform_real_receivable` float DEFAULT NULL,
  `platform_receivable` float DEFAULT NULL,
  `prn` text,
  `receivable_sn` varchar(255) DEFAULT NULL,
  `receivable_type` varchar(255) DEFAULT NULL,
  `remarks` varchar(255) DEFAULT NULL,
  `rtkt_remarks` varchar(255) DEFAULT NULL,
  `supplier` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_aviation_order`
--

LOCK TABLES `t_aviation_order` WRITE;
/*!40000 ALTER TABLE `t_aviation_order` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_aviation_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_aviation_passenger_info`
--

DROP TABLE IF EXISTS `t_aviation_passenger_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_aviation_passenger_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `create_user` varchar(36) DEFAULT NULL,
  `status` int(11) DEFAULT '0',
  `update_time` datetime DEFAULT NULL,
  `update_user` varchar(36) DEFAULT NULL,
  `version` int(11) DEFAULT '1',
  `birthday` varchar(255) DEFAULT NULL,
  `deleted` tinyint(4) DEFAULT '0',
  `idcard_expiry_date` datetime DEFAULT NULL,
  `idcard_sn` varchar(255) DEFAULT NULL,
  `passenger_type` varchar(255) DEFAULT NULL,
  `passenger_name` varchar(255) DEFAULT NULL,
  `payable_amount` float DEFAULT NULL,
  `receivable_amount` float DEFAULT NULL,
  `sex` varchar(255) DEFAULT NULL,
  `ticket_number` varchar(255) DEFAULT NULL,
  `t_aviation_order_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_pwtq1wh2s2pnpahif4rselft9` (`t_aviation_order_id`),
  CONSTRAINT `FK_pwtq1wh2s2pnpahif4rselft9` FOREIGN KEY (`t_aviation_order_id`) REFERENCES `t_aviation_order` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_aviation_passenger_info`
--

LOCK TABLES `t_aviation_passenger_info` WRITE;
/*!40000 ALTER TABLE `t_aviation_passenger_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_aviation_passenger_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_aviation_suppliers`
--

DROP TABLE IF EXISTS `t_aviation_suppliers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_aviation_suppliers` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `create_user` varchar(36) DEFAULT NULL,
  `status` int(11) DEFAULT '0',
  `update_time` datetime DEFAULT NULL,
  `update_user` varchar(36) DEFAULT NULL,
  `version` int(11) DEFAULT '1',
  `area` varchar(255) DEFAULT NULL,
  `auto_paying` int(11) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `compan_aaddress` varchar(255) DEFAULT NULL,
  `company_name` varchar(255) DEFAULT NULL,
  `company_url` varchar(255) DEFAULT NULL,
  `confirm_password` varchar(255) DEFAULT NULL,
  `contact_person` varchar(255) DEFAULT NULL,
  `custom_handle` varchar(255) DEFAULT NULL,
  `effected` tinyint(1) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `fax_number` varchar(255) DEFAULT NULL,
  `invalid_ticket_worktime` varchar(255) DEFAULT NULL,
  `liabilities` float NOT NULL,
  `mobile` varchar(255) DEFAULT NULL,
  `office_sn` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `qq` varchar(255) DEFAULT NULL,
  `service_email` varchar(255) DEFAULT NULL,
  `service_ticket_begintime` varchar(255) DEFAULT NULL,
  `service_ticket_endtime` varchar(255) DEFAULT NULL,
  `service_time` varchar(255) DEFAULT NULL,
  `suppliers_name` varchar(255) DEFAULT NULL,
  `suppliers_type` varchar(255) DEFAULT NULL,
  `telephone` varchar(255) DEFAULT NULL,
  `ticket_rate` varchar(255) DEFAULT NULL,
  `ticket_remind_email` varchar(255) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  `work_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_aviation_suppliers`
--

LOCK TABLES `t_aviation_suppliers` WRITE;
/*!40000 ALTER TABLE `t_aviation_suppliers` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_aviation_suppliers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_oil_prepaid_records`
--

DROP TABLE IF EXISTS `t_oil_prepaid_records`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_oil_prepaid_records` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `create_user` varchar(36) DEFAULT NULL,
  `status` int(11) DEFAULT '0',
  `update_time` datetime DEFAULT NULL,
  `update_user` varchar(36) DEFAULT NULL,
  `version` int(11) DEFAULT '1',
  `account_code` varchar(100) DEFAULT NULL,
  `after_amount` float DEFAULT '0',
  `after_rebate` float DEFAULT '0',
  `amount` float DEFAULT '0',
  `before_amount` float DEFAULT '0',
  `before_rebate` float DEFAULT '0',
  `note` varchar(1000) DEFAULT NULL,
  `prepaid_address` varchar(200) DEFAULT NULL,
  `prepaid_time` datetime DEFAULT NULL,
  `prepaid_type` varchar(50) DEFAULT NULL,
  `rebate` float DEFAULT '0',
  `record_type` int(11) DEFAULT '0',
  `trade_sn` varchar(200) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_7ejqjsm2ofw998ksn1vi41yh3` (`user_id`),
  CONSTRAINT `FK_7ejqjsm2ofw998ksn1vi41yh3` FOREIGN KEY (`user_id`) REFERENCES `t_sys_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_oil_prepaid_records`
--

LOCK TABLES `t_oil_prepaid_records` WRITE;
/*!40000 ALTER TABLE `t_oil_prepaid_records` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_oil_prepaid_records` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_sys_advert_info`
--

DROP TABLE IF EXISTS `t_sys_advert_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_sys_advert_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `create_user` varchar(36) DEFAULT NULL,
  `status` int(11) DEFAULT '0',
  `update_time` datetime DEFAULT NULL,
  `update_user` varchar(36) DEFAULT NULL,
  `version` int(11) DEFAULT '1',
  `appimg_url` varchar(50) NOT NULL,
  `begin_time` datetime DEFAULT NULL,
  `content_into_id` int(11) DEFAULT NULL,
  `description` varchar(200) NOT NULL,
  `end_time` datetime DEFAULT NULL,
  `hits` int(11) DEFAULT NULL,
  `img_url` varchar(50) NOT NULL,
  `insert_user` int(11) DEFAULT NULL,
  `key_value` varchar(255) DEFAULT NULL,
  `name` varchar(50) NOT NULL,
  `order_num` int(11) DEFAULT NULL,
  `publish` tinyint(4) DEFAULT '0',
  `website` varchar(200) DEFAULT NULL,
  `advert_site_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_h3u52jalhyyu58ec4i04lxx30` (`advert_site_id`),
  CONSTRAINT `FK_h3u52jalhyyu58ec4i04lxx30` FOREIGN KEY (`advert_site_id`) REFERENCES `t_sys_advert_site` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_sys_advert_info`
--

LOCK TABLES `t_sys_advert_info` WRITE;
/*!40000 ALTER TABLE `t_sys_advert_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_sys_advert_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_sys_advert_site`
--

DROP TABLE IF EXISTS `t_sys_advert_site`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_sys_advert_site` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `create_user` varchar(36) DEFAULT NULL,
  `status` int(11) DEFAULT '0',
  `update_time` datetime DEFAULT NULL,
  `update_user` varchar(36) DEFAULT NULL,
  `version` int(11) DEFAULT '1',
  `advert_number` int(11) DEFAULT NULL,
  `description` varchar(255) NOT NULL,
  `height` int(11) DEFAULT NULL,
  `insert_user` varchar(255) DEFAULT NULL,
  `key_value` varchar(255) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `width` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_sys_advert_site`
--

LOCK TABLES `t_sys_advert_site` WRITE;
/*!40000 ALTER TABLE `t_sys_advert_site` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_sys_advert_site` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_sys_bug`
--

DROP TABLE IF EXISTS `t_sys_bug`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_sys_bug` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CREATE_TIME` datetime DEFAULT NULL,
  `CREATE_USER` varchar(36) DEFAULT NULL,
  `STATUS` int(11) DEFAULT NULL,
  `UPDATE_TIME` datetime DEFAULT NULL,
  `UPDATE_USER` varchar(36) DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  `CONTENT` longtext,
  `TITLE` varchar(255) DEFAULT NULL,
  `TYPE` varchar(36) DEFAULT NULL,
  `COLOR` varchar(12) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_sys_bug`
--

LOCK TABLES `t_sys_bug` WRITE;
/*!40000 ALTER TABLE `t_sys_bug` DISABLE KEYS */;
INSERT INTO `t_sys_bug` VALUES (17,'2015-03-29 20:27:44','admin',0,NULL,NULL,0,'欢迎使用','欢迎使用','bug000','red');
/*!40000 ALTER TABLE `t_sys_bug` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_sys_dictionary`
--

DROP TABLE IF EXISTS `t_sys_dictionary`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_sys_dictionary` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CREATE_TIME` datetime DEFAULT NULL,
  `CREATE_USER` varchar(36) DEFAULT NULL,
  `STATUS` int(11) DEFAULT NULL,
  `UPDATE_TIME` datetime DEFAULT NULL,
  `UPDATE_USER` varchar(36) DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  `CODE` varchar(36) DEFAULT NULL,
  `NAME` varchar(100) DEFAULT NULL,
  `ORDER_NO` int(11) DEFAULT NULL,
  `REMAK` varchar(100) DEFAULT NULL,
  `VALUE` varchar(100) DEFAULT NULL,
  `DICTIONARYTYPE_CODE` varchar(20) DEFAULT NULL,
  `PARENT_CODE` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `CODE` (`CODE`),
  UNIQUE KEY `NAME` (`NAME`),
  UNIQUE KEY `CODE_2` (`CODE`),
  UNIQUE KEY `UK_o1tl2dpxs4ogjrihj5d0aetyy` (`CODE`),
  UNIQUE KEY `UK_a537d5dca8d3425ca9dda698d10` (`CODE`),
  UNIQUE KEY `UK_f5f933daadac4883853243aad12` (`CODE`),
  UNIQUE KEY `UK_e6c25999b7314e5181998f776ba` (`CODE`),
  UNIQUE KEY `UK_8f14db30926d45208b68602823a` (`CODE`),
  UNIQUE KEY `UK_60c6a856fe064bc68cfe0aee53b` (`CODE`),
  UNIQUE KEY `UK_oya5576xpt36m50jajdtf15k8` (`CODE`),
  KEY `FK79C52CB373CC8B3F` (`DICTIONARYTYPE_CODE`),
  KEY `FK79C52CB3BD49F8CB` (`PARENT_CODE`),
  CONSTRAINT `FK79C52CB373CC8B3F` FOREIGN KEY (`DICTIONARYTYPE_CODE`) REFERENCES `t_sys_dictionarytype` (`CODE`),
  CONSTRAINT `FK79C52CB3BD49F8CB` FOREIGN KEY (`PARENT_CODE`) REFERENCES `t_sys_dictionary` (`CODE`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_sys_dictionary`
--

LOCK TABLES `t_sys_dictionary` WRITE;
/*!40000 ALTER TABLE `t_sys_dictionary` DISABLE KEYS */;
INSERT INTO `t_sys_dictionary` VALUES (3,'2015-03-29 17:49:10','admin',0,NULL,NULL,0,'bug001','功能性',1,'','bug001','bug',NULL),(4,'2015-03-29 17:49:24','admin',0,NULL,NULL,0,'bug002','建议',2,'','bug002','bug',NULL),(5,'2015-03-29 20:27:09','admin',0,NULL,NULL,0,'bug000','内部新闻',3,'','bug000','bug',NULL);
/*!40000 ALTER TABLE `t_sys_dictionary` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_sys_dictionarytype`
--

DROP TABLE IF EXISTS `t_sys_dictionarytype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_sys_dictionarytype` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CREATE_TIME` datetime DEFAULT NULL,
  `CREATE_USER` varchar(36) DEFAULT NULL,
  `STATUS` int(11) DEFAULT NULL,
  `UPDATE_TIME` datetime DEFAULT NULL,
  `UPDATE_USER` varchar(36) DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  `CODE` varchar(20) DEFAULT NULL,
  `NAME` varchar(100) DEFAULT NULL,
  `ORDER_NO` int(11) DEFAULT NULL,
  `REMARK` varchar(255) DEFAULT NULL,
  `GROUP_CODE` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `CODE` (`CODE`),
  UNIQUE KEY `NAME` (`NAME`),
  UNIQUE KEY `CODE_2` (`CODE`),
  UNIQUE KEY `UK_9als5rp87ewbp9egotvj1j9rg` (`CODE`),
  UNIQUE KEY `UK_753186875d0243ee833be0fb462` (`CODE`),
  UNIQUE KEY `UK_2064ba05f4df4d2cb4dd86255b5` (`CODE`),
  UNIQUE KEY `UK_2281184778e548688543cc82330` (`CODE`),
  UNIQUE KEY `UK_551c03d742b847f8a4f1d44527e` (`CODE`),
  UNIQUE KEY `UK_02b721e6df5f42169f82ffab7bf` (`CODE`),
  UNIQUE KEY `UK_od88eya35c9mpgmpv80mhb2p7` (`CODE`),
  KEY `FK8551226D4DC80EF0` (`GROUP_CODE`),
  CONSTRAINT `FK8551226D4DC80EF0` FOREIGN KEY (`GROUP_CODE`) REFERENCES `t_sys_dictionarytype` (`CODE`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_sys_dictionarytype`
--

LOCK TABLES `t_sys_dictionarytype` WRITE;
/*!40000 ALTER TABLE `t_sys_dictionarytype` DISABLE KEYS */;
INSERT INTO `t_sys_dictionarytype` VALUES (5,'2015-03-29 17:48:24','admin',0,NULL,NULL,0,'system','系统字典',1,'',NULL),(6,'2015-03-29 17:48:52','admin',0,NULL,NULL,0,'bug','bug类型',2,'','system');
/*!40000 ALTER TABLE `t_sys_dictionarytype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_sys_log`
--

DROP TABLE IF EXISTS `t_sys_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_sys_log` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CREATE_TIME` datetime DEFAULT NULL,
  `CREATE_USER` varchar(36) DEFAULT NULL,
  `STATUS` int(11) DEFAULT NULL,
  `UPDATE_TIME` datetime DEFAULT NULL,
  `UPDATE_USER` varchar(36) DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  `ACTION` varchar(255) DEFAULT NULL,
  `ACTION_TIME` varchar(20) DEFAULT NULL,
  `IP` varchar(64) DEFAULT NULL,
  `LOGIN_NAME` varchar(36) DEFAULT NULL,
  `MODULE` varchar(36) DEFAULT NULL,
  `OPER_TIME` datetime DEFAULT NULL,
  `REMARK` varchar(255) DEFAULT NULL,
  `TYPE` int(11) DEFAULT NULL,
  `browser_type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=138 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_sys_log`
--

LOCK TABLES `t_sys_log` WRITE;
/*!40000 ALTER TABLE `t_sys_log` DISABLE KEYS */;
INSERT INTO `t_sys_log` VALUES (131,'2015-12-09 22:30:12','admin',0,NULL,NULL,0,'用户登录','0','0:0:0:0:0:0:0:1','admin','LoginController-login','2015-12-09 22:30:12',NULL,0,'Firefox'),(132,'2015-12-09 22:35:49','admin',0,NULL,NULL,0,'用户登录','0','0:0:0:0:0:0:0:1','admin','LoginController-login','2015-12-09 22:35:49',NULL,0,'Firefox'),(133,'2015-12-09 22:51:44','admin',0,NULL,NULL,0,'用户注销','0','0:0:0:0:0:0:0:1','admin','LoginController-logout','2015-12-09 22:51:44',NULL,0,'Firefox'),(134,'2015-12-09 22:51:50','admin',0,NULL,NULL,0,'用户登录','0','0:0:0:0:0:0:0:1','admin','LoginController-login','2015-12-09 22:51:50',NULL,0,'Firefox'),(135,'2015-12-09 22:55:13','admin',0,NULL,NULL,0,'用户注销','0','0:0:0:0:0:0:0:1','admin','LoginController-logout','2015-12-09 22:55:13',NULL,0,'Firefox'),(136,'2015-12-09 22:55:20','order',0,NULL,NULL,0,'用户登录','0','0:0:0:0:0:0:0:1','order','LoginController-login','2015-12-09 22:55:20',NULL,0,'Firefox'),(137,'2015-12-09 22:55:36','order',0,NULL,NULL,0,'用户注销','0','0:0:0:0:0:0:0:1','order','LoginController-logout','2015-12-09 22:55:36',NULL,0,'Firefox');
/*!40000 ALTER TABLE `t_sys_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_sys_message`
--

DROP TABLE IF EXISTS `t_sys_message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_sys_message` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `create_user` varchar(36) DEFAULT NULL,
  `status` int(11) DEFAULT '0',
  `update_time` datetime DEFAULT NULL,
  `update_user` varchar(36) DEFAULT NULL,
  `version` int(11) DEFAULT '1',
  `msg_box` int(11) DEFAULT NULL,
  `msg_content` varchar(2000) DEFAULT NULL,
  `msg_status` int(11) DEFAULT NULL,
  `msg_title` varchar(255) DEFAULT NULL,
  `msg_type` int(11) DEFAULT NULL,
  `send_time` datetime DEFAULT NULL,
  `msg_receiver_user` bigint(20) DEFAULT NULL,
  `msg_send_user` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_q87op5jqup2glejh1p6u0kyxs` (`msg_receiver_user`),
  KEY `FK_ki0w62gl5dpfok69o0yomal73` (`msg_send_user`),
  CONSTRAINT `FK_ki0w62gl5dpfok69o0yomal73` FOREIGN KEY (`msg_send_user`) REFERENCES `t_sys_user` (`id`),
  CONSTRAINT `FK_q87op5jqup2glejh1p6u0kyxs` FOREIGN KEY (`msg_receiver_user`) REFERENCES `t_sys_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_sys_message`
--

LOCK TABLES `t_sys_message` WRITE;
/*!40000 ALTER TABLE `t_sys_message` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_sys_message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_sys_organ`
--

DROP TABLE IF EXISTS `t_sys_organ`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_sys_organ` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CREATE_TIME` datetime DEFAULT NULL,
  `CREATE_USER` varchar(36) DEFAULT NULL,
  `STATUS` int(11) DEFAULT NULL,
  `UPDATE_TIME` datetime DEFAULT NULL,
  `UPDATE_USER` varchar(36) DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  `ADDRESS` varchar(255) DEFAULT NULL,
  `CODE` varchar(36) DEFAULT NULL,
  `FAX` varchar(64) DEFAULT NULL,
  `MANAGER_USER_ID` bigint(20) DEFAULT NULL,
  `NAME` varchar(255) NOT NULL,
  `ORDER_NO` int(11) DEFAULT NULL,
  `PHONE` varchar(64) DEFAULT NULL,
  `SYS_CODE` varchar(36) DEFAULT NULL,
  `TYPE` int(11) DEFAULT NULL,
  `PARENT_ID` bigint(20) DEFAULT NULL,
  `super_manager_user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `NAME` (`NAME`),
  KEY `FKFE2373CE3E535456` (`PARENT_ID`),
  KEY `FK_klg4pxi6cmbo6nu6tvj652x2` (`super_manager_user_id`),
  CONSTRAINT `FKFE2373CE3E535456` FOREIGN KEY (`PARENT_ID`) REFERENCES `t_sys_organ` (`ID`),
  CONSTRAINT `FK_klg4pxi6cmbo6nu6tvj652x2` FOREIGN KEY (`super_manager_user_id`) REFERENCES `t_sys_user` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_sys_organ`
--

LOCK TABLES `t_sys_organ` WRITE;
/*!40000 ALTER TABLE `t_sys_organ` DISABLE KEYS */;
INSERT INTO `t_sys_organ` VALUES (7,'2015-10-29 21:43:39','admin',0,NULL,NULL,0,'','','',NULL,'岚舒应用',1,'','00',0,NULL,NULL),(8,'2015-10-29 21:43:51','admin',0,'2015-12-09 22:53:32','admin',2,'','','',NULL,'出票组',2,'','001',2,7,NULL),(9,'2015-12-09 22:53:10','admin',0,'2015-12-09 22:53:21','admin',1,'','','',NULL,'订票组',3,'','002',2,7,NULL);
/*!40000 ALTER TABLE `t_sys_organ` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_sys_paymodel`
--

DROP TABLE IF EXISTS `t_sys_paymodel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_sys_paymodel` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `create_user` varchar(36) DEFAULT NULL,
  `status` int(11) DEFAULT '0',
  `update_time` datetime DEFAULT NULL,
  `update_user` varchar(36) DEFAULT NULL,
  `version` int(11) DEFAULT '1',
  `app_hand_charge` float DEFAULT NULL,
  `meta_keys` varchar(255) DEFAULT NULL,
  `model_name` varchar(255) DEFAULT NULL,
  `model_value` int(11) DEFAULT NULL,
  `pay_desc` varchar(255) DEFAULT NULL,
  `web_hand_charge` float DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_sys_paymodel`
--

LOCK TABLES `t_sys_paymodel` WRITE;
/*!40000 ALTER TABLE `t_sys_paymodel` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_sys_paymodel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_sys_post`
--

DROP TABLE IF EXISTS `t_sys_post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_sys_post` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `create_user` varchar(36) DEFAULT NULL,
  `status` int(11) DEFAULT '0',
  `update_time` datetime DEFAULT NULL,
  `update_user` varchar(36) DEFAULT NULL,
  `version` int(11) DEFAULT '1',
  `code` varchar(36) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `remark` varchar(36) DEFAULT NULL,
  `organ_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_do2moeabchk0jr90i3d7qjkhe` (`organ_id`),
  CONSTRAINT `FK_do2moeabchk0jr90i3d7qjkhe` FOREIGN KEY (`organ_id`) REFERENCES `t_sys_organ` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_sys_post`
--

LOCK TABLES `t_sys_post` WRITE;
/*!40000 ALTER TABLE `t_sys_post` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_sys_post` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_sys_resource`
--

DROP TABLE IF EXISTS `t_sys_resource`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_sys_resource` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CREATE_TIME` datetime DEFAULT NULL,
  `CREATE_USER` varchar(36) DEFAULT NULL,
  `STATUS` int(11) DEFAULT NULL,
  `UPDATE_TIME` datetime DEFAULT NULL,
  `UPDATE_USER` varchar(36) DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  `CODE` varchar(36) DEFAULT NULL,
  `ICON` varchar(255) DEFAULT NULL,
  `ICON_CLS` varchar(255) DEFAULT NULL,
  `MARK_URL` varchar(2000) DEFAULT NULL,
  `NAME` varchar(20) NOT NULL,
  `ORDER_NO` int(11) DEFAULT NULL,
  `TYPE` int(11) DEFAULT NULL,
  `URL` varchar(255) DEFAULT NULL,
  `PARENT_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FKFCD7B111A886F349` (`PARENT_ID`),
  CONSTRAINT `FKFCD7B111A886F349` FOREIGN KEY (`PARENT_ID`) REFERENCES `t_sys_resource` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_sys_resource`
--

LOCK TABLES `t_sys_resource` WRITE;
/*!40000 ALTER TABLE `t_sys_resource` DISABLE KEYS */;
INSERT INTO `t_sys_resource` VALUES (1,NULL,NULL,0,'2015-11-12 22:12:42','admin',1,'',NULL,'icon-application','','权限管理',1,0,'',NULL),(2,NULL,NULL,0,'2015-12-28 16:17:38','admin',6,'',NULL,'icon-folder','','资源管理',2,0,'/sys/resource',1),(3,'2015-11-12 22:13:42','admin',0,'2015-12-22 00:01:53','admin',3,'',NULL,'icon-group','','角色管理',3,0,'/sys/role',1),(4,'2015-11-12 22:14:10','admin',0,'2015-12-28 19:25:00','admin',3,'',NULL,'icon-group','','机构管理',4,0,'/sys/organ',1),(5,'2015-11-12 22:14:28','admin',0,'2015-12-28 19:19:03','admin',1,'',NULL,'icon-user','','用户管理',5,0,'/sys/user',1),(6,'2015-11-12 22:14:54','admin',0,'2015-11-12 22:15:18','admin',1,'',NULL,'icon-application','','系统配置',6,0,'',NULL),(7,'2015-11-12 22:15:13','admin',0,'2015-12-21 21:02:05','admin',2,'',NULL,'icon-book','','字典类型',7,0,'/sys/dictionary-type',6),(8,'2015-11-12 22:15:40','admin',0,'2015-12-28 19:25:41','admin',5,'',NULL,'icon-ipod','','数据字典',8,0,'/sys/dictionary',6),(9,'2015-11-12 22:15:57','admin',0,'2015-03-29 18:43:23','admin',2,'',NULL,'icon-bug','','内容管理',9,0,'/sys/bug',6),(11,'2015-11-16 23:45:54','admin',0,'2015-12-21 19:57:45','admin',5,'bug:add',NULL,'','','新增',10,1,'',9),(16,'2015-11-17 00:10:23','admin',0,'2015-11-17 20:16:33','admin',2,'bug:edit',NULL,'','','编辑',11,1,'',9),(17,'2015-11-17 00:11:05','admin',0,'2015-11-17 20:16:38','admin',2,'bug:importExcel',NULL,'','','Excel导入',12,1,'',9),(18,'2015-11-17 00:13:35','admin',0,'2015-11-17 20:16:41','admin',1,'bug:exportExcel',NULL,'','','Excel导出',13,1,'',9),(19,'2015-11-17 00:14:32','admin',0,'2015-11-17 20:16:45','admin',1,'bug:remove',NULL,'','','批量删除',14,1,'',9),(20,'2015-11-17 00:46:09','eryan',0,'2015-11-17 20:16:50','admin',1,'bug:view',NULL,'','','查看',15,1,'',9),(21,'2015-11-08 17:26:38','admin',0,'2015-11-08 17:27:03','admin',1,'',NULL,'icon-monitor','/sys/log*','日志管理',16,0,'/sys/log',6),(22,'2015-01-05 15:07:01','admin',0,'2015-12-09 22:41:32','admin',2,'',NULL,'icon-application','','财务管理',17,0,'',NULL),(26,'2015-12-09 22:40:44','admin',0,NULL,NULL,0,'',NULL,'icon-application','','订单管理',19,0,'',NULL),(27,'2015-12-09 22:42:00','admin',0,NULL,NULL,0,'',NULL,'','','订单创建',20,0,'',26),(28,'2015-12-09 22:42:28','admin',0,NULL,NULL,0,'',NULL,'','','我的预订',21,0,'',26),(29,'2015-12-09 22:42:45','admin',0,NULL,NULL,0,'',NULL,'','','待处理订单',22,0,'',26),(30,'2015-12-09 22:43:05','admin',0,NULL,NULL,0,'',NULL,'','','已确定订单',23,0,'',26),(31,'2015-12-09 22:43:25','admin',0,NULL,NULL,0,'',NULL,'','','订单清单',24,0,'',26),(32,'2015-12-09 22:43:49','admin',0,NULL,NULL,0,'',NULL,'','','欠收订单',25,0,'',26),(33,'2015-12-09 22:44:06','admin',0,NULL,NULL,0,'',NULL,'','','欠付订单',26,0,'',26),(34,'2015-12-09 22:44:31','admin',0,NULL,NULL,0,'',NULL,'','','订单清单',27,0,'',22),(35,'2015-12-09 22:44:58','admin',0,NULL,NULL,0,'',NULL,'','','批量付款',28,0,'',22),(36,'2015-12-09 22:45:10','admin',0,NULL,NULL,0,'',NULL,'','','批量付款对冲',29,0,'',22),(37,'2015-12-09 22:45:28','admin',0,NULL,NULL,0,'',NULL,'','','批量收款',30,0,'',22),(38,'2015-12-09 22:45:44','admin',0,NULL,NULL,0,'',NULL,'','','批量收款对冲',31,0,'',22),(39,'2015-12-09 22:46:16','admin',0,NULL,NULL,0,'',NULL,'','','批量付审',32,0,'',22),(40,'2015-12-09 22:46:32','admin',0,NULL,NULL,0,'',NULL,'','','批量收审',33,0,'',22),(41,'2015-12-09 22:46:50','admin',0,NULL,NULL,0,'',NULL,'','','批量终审',34,0,'',22),(42,'2015-12-09 22:47:28','admin',0,NULL,NULL,0,'',NULL,'icon-application','','供采管理',35,0,'',NULL),(43,'2015-12-09 22:48:01','admin',0,NULL,NULL,0,'',NULL,'','','供应商查询',36,0,'',42),(44,'2015-12-09 22:48:10','admin',0,NULL,NULL,0,'',NULL,'','','采购商查询',37,0,'',42),(45,'2015-12-09 22:48:42','admin',0,NULL,NULL,0,'',NULL,'icon-application','','报表管理',38,0,'',NULL),(46,'2015-12-09 22:49:18','admin',0,NULL,NULL,0,'',NULL,'','','机票总表',39,0,'',45),(47,'2015-12-09 22:49:34','admin',0,NULL,NULL,0,'',NULL,'','','订单总表',40,0,'',45),(48,'2015-12-09 22:49:49','admin',0,NULL,NULL,0,'',NULL,'','','付款总表',41,0,'',45),(49,'2015-12-09 22:50:06','admin',0,NULL,NULL,0,'',NULL,'','','收款总表',42,0,'',45),(50,'2015-12-09 22:50:21','admin',0,NULL,NULL,0,'',NULL,'','','欠收总表',43,0,'',45),(51,'2015-12-09 22:50:37','admin',0,NULL,NULL,0,'',NULL,'','','欠付总表',44,0,'',45),(52,'2015-12-09 22:50:50','admin',0,NULL,NULL,0,'',NULL,'','','月报表',45,0,'',45);
/*!40000 ALTER TABLE `t_sys_resource` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_sys_role`
--

DROP TABLE IF EXISTS `t_sys_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_sys_role` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CREATE_TIME` datetime DEFAULT NULL,
  `CREATE_USER` varchar(36) DEFAULT NULL,
  `STATUS` int(11) DEFAULT NULL,
  `UPDATE_TIME` datetime DEFAULT NULL,
  `UPDATE_USER` varchar(36) DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  `CODE` varchar(36) DEFAULT NULL,
  `NAME` varchar(100) NOT NULL,
  `REMARK` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `NAME` (`NAME`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_sys_role`
--

LOCK TABLES `t_sys_role` WRITE;
/*!40000 ALTER TABLE `t_sys_role` DISABLE KEYS */;
INSERT INTO `t_sys_role` VALUES (2,'2015-12-09 22:37:09','admin',0,'2015-12-09 22:55:01','admin',1,'order','order','订单管理员');
/*!40000 ALTER TABLE `t_sys_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_sys_role_resource`
--

DROP TABLE IF EXISTS `t_sys_role_resource`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_sys_role_resource` (
  `ROLE_ID` bigint(20) NOT NULL,
  `RESOURCE_ID` bigint(20) NOT NULL,
  KEY `FK99003E94CBF981E5` (`ROLE_ID`),
  KEY `FK99003E9476B5CD65` (`RESOURCE_ID`),
  CONSTRAINT `FK99003E9476B5CD65` FOREIGN KEY (`RESOURCE_ID`) REFERENCES `t_sys_resource` (`ID`),
  CONSTRAINT `FK99003E94CBF981E5` FOREIGN KEY (`ROLE_ID`) REFERENCES `t_sys_role` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_sys_role_resource`
--

LOCK TABLES `t_sys_role_resource` WRITE;
/*!40000 ALTER TABLE `t_sys_role_resource` DISABLE KEYS */;
INSERT INTO `t_sys_role_resource` VALUES (2,22),(2,34),(2,35),(2,36),(2,37),(2,38),(2,39),(2,40),(2,41),(2,26),(2,27),(2,28),(2,29),(2,30),(2,31),(2,32),(2,33),(2,42),(2,43),(2,44),(2,45),(2,46),(2,47),(2,48),(2,49),(2,50),(2,51),(2,52);
/*!40000 ALTER TABLE `t_sys_role_resource` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_sys_user`
--

DROP TABLE IF EXISTS `t_sys_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_sys_user` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CREATE_TIME` datetime DEFAULT NULL,
  `CREATE_USER` varchar(36) DEFAULT NULL,
  `STATUS` int(11) DEFAULT NULL,
  `UPDATE_TIME` datetime DEFAULT NULL,
  `UPDATE_USER` varchar(36) DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  `ADDRESS` varchar(255) DEFAULT NULL,
  `EMAIL` varchar(64) DEFAULT NULL,
  `LOGIN_NAME` varchar(36) NOT NULL,
  `MOBILEPHONE` varchar(36) DEFAULT NULL,
  `NAME` varchar(36) DEFAULT NULL,
  `PASSWORD` varchar(64) NOT NULL,
  `SEX` int(11) DEFAULT NULL,
  `TEL` varchar(36) DEFAULT NULL,
  `DEFAULT_ORGANID` bigint(20) DEFAULT NULL,
  `amount` float DEFAULT '0',
  `frozen_amount` float DEFAULT NULL,
  `user_type` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `LOGIN_NAME` (`LOGIN_NAME`),
  KEY `FKBDE2DA4E7AEFAE74` (`DEFAULT_ORGANID`),
  CONSTRAINT `FKBDE2DA4E7AEFAE74` FOREIGN KEY (`DEFAULT_ORGANID`) REFERENCES `t_sys_organ` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_sys_user`
--

LOCK TABLES `t_sys_user` WRITE;
/*!40000 ALTER TABLE `t_sys_user` DISABLE KEYS */;
INSERT INTO `t_sys_user` VALUES (1,NULL,NULL,0,'2015-11-13 08:02:20','admin',3,'','','admin',NULL,'','0192023a7bbd73250516f069df18b500',2,'',NULL,0,NULL,NULL),(3,'2015-12-09 22:38:12','admin',0,'2015-12-09 22:54:27','admin',3,'','','order','','订单管理员','71e905faf42f53707f84b29b709a4900',2,'',8,0,NULL,NULL);
/*!40000 ALTER TABLE `t_sys_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_sys_user_organ`
--

DROP TABLE IF EXISTS `t_sys_user_organ`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_sys_user_organ` (
  `USER_ID` bigint(20) NOT NULL,
  `ORGAN_ID` bigint(20) NOT NULL,
  KEY `FK1F9964C01162FD8F` (`ORGAN_ID`),
  KEY `FK1F9964C0712445C5` (`USER_ID`),
  CONSTRAINT `FK1F9964C01162FD8F` FOREIGN KEY (`ORGAN_ID`) REFERENCES `t_sys_organ` (`ID`),
  CONSTRAINT `FK1F9964C0712445C5` FOREIGN KEY (`USER_ID`) REFERENCES `t_sys_user` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_sys_user_organ`
--

LOCK TABLES `t_sys_user_organ` WRITE;
/*!40000 ALTER TABLE `t_sys_user_organ` DISABLE KEYS */;
INSERT INTO `t_sys_user_organ` VALUES (3,7),(3,8);
/*!40000 ALTER TABLE `t_sys_user_organ` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_sys_user_post`
--

DROP TABLE IF EXISTS `t_sys_user_post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_sys_user_post` (
  `user_id` bigint(20) NOT NULL,
  `post_id` bigint(20) NOT NULL,
  KEY `FK_6o9ao50lhoc3w2pveiy5ull8e` (`post_id`),
  KEY `FK_ifxlp9gug6x73orq3cliviugy` (`user_id`),
  CONSTRAINT `FK_6o9ao50lhoc3w2pveiy5ull8e` FOREIGN KEY (`post_id`) REFERENCES `t_sys_post` (`id`),
  CONSTRAINT `FK_ifxlp9gug6x73orq3cliviugy` FOREIGN KEY (`user_id`) REFERENCES `t_sys_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_sys_user_post`
--

LOCK TABLES `t_sys_user_post` WRITE;
/*!40000 ALTER TABLE `t_sys_user_post` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_sys_user_post` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_sys_user_resource`
--

DROP TABLE IF EXISTS `t_sys_user_resource`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_sys_user_resource` (
  `USER_ID` bigint(20) NOT NULL,
  `RESOURCE_ID` bigint(20) NOT NULL,
  KEY `FKD8C9C2DF712445C5` (`USER_ID`),
  KEY `FKD8C9C2DF76B5CD65` (`RESOURCE_ID`),
  CONSTRAINT `FKD8C9C2DF712445C5` FOREIGN KEY (`USER_ID`) REFERENCES `t_sys_user` (`ID`),
  CONSTRAINT `FKD8C9C2DF76B5CD65` FOREIGN KEY (`RESOURCE_ID`) REFERENCES `t_sys_resource` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_sys_user_resource`
--

LOCK TABLES `t_sys_user_resource` WRITE;
/*!40000 ALTER TABLE `t_sys_user_resource` DISABLE KEYS */;
INSERT INTO `t_sys_user_resource` VALUES (3,22),(3,34),(3,35),(3,36),(3,37),(3,38),(3,39),(3,40),(3,41),(3,26),(3,27),(3,28),(3,29),(3,30),(3,31),(3,32),(3,33),(3,42),(3,43),(3,44),(3,45),(3,46),(3,47),(3,48),(3,49),(3,50),(3,51),(3,52);
/*!40000 ALTER TABLE `t_sys_user_resource` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_sys_user_role`
--

DROP TABLE IF EXISTS `t_sys_user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_sys_user_role` (
  `USER_ID` bigint(20) NOT NULL,
  `ROLE_ID` bigint(20) NOT NULL,
  KEY `FK2A5097C7CBF981E5` (`ROLE_ID`),
  KEY `FK2A5097C7712445C5` (`USER_ID`),
  CONSTRAINT `FK2A5097C7712445C5` FOREIGN KEY (`USER_ID`) REFERENCES `t_sys_user` (`ID`),
  CONSTRAINT `FK2A5097C7CBF981E5` FOREIGN KEY (`ROLE_ID`) REFERENCES `t_sys_role` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_sys_user_role`
--

LOCK TABLES `t_sys_user_role` WRITE;
/*!40000 ALTER TABLE `t_sys_user_role` DISABLE KEYS */;
INSERT INTO `t_sys_user_role` VALUES (3,2);
/*!40000 ALTER TABLE `t_sys_user_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_validate_log`
--

DROP TABLE IF EXISTS `t_validate_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_validate_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `create_user` varchar(36) DEFAULT NULL,
  `status` int(11) DEFAULT '0',
  `update_time` datetime DEFAULT NULL,
  `update_user` varchar(36) DEFAULT NULL,
  `version` int(11) DEFAULT '1',
  `sendto` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `validate_code` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_validate_log`
--

LOCK TABLES `t_validate_log` WRITE;
/*!40000 ALTER TABLE `t_validate_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_validate_log` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-12-09 23:10:02
