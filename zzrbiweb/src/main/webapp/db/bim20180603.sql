/*
SQLyog Enterprise v12.09 (64 bit)
MySQL - 5.6.21-log : Database - bim
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`bim` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `bim`;

/*Table structure for table `attachment` */

DROP TABLE IF EXISTS `attachment`;

CREATE TABLE `attachment` (
  `id` int(11) DEFAULT NULL,
  `atype` varchar(5) DEFAULT NULL COMMENT '附件类型1=培训2=不良记录3=奖励记录',
  `name` varchar(100) DEFAULT NULL COMMENT '附件名称',
  `url` varchar(200) DEFAULT NULL COMMENT '附件地址',
  `mainId` int(11) DEFAULT NULL COMMENT '关联id'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `attachment` */

/*Table structure for table `bed` */

DROP TABLE IF EXISTS `bed`;

CREATE TABLE `bed` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `roomId` int(11) DEFAULT NULL,
  `state` varchar(5) DEFAULT NULL COMMENT '0=未入住 1=已入住',
  `bed` varchar(50) DEFAULT NULL COMMENT '床位号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `bed` */

/*Table structure for table `black_record` */

DROP TABLE IF EXISTS `black_record`;

CREATE TABLE `black_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `reason` varchar(1000) DEFAULT NULL COMMENT '加入黑名单原因',
  `creatTime` datetime DEFAULT NULL COMMENT '加入时间',
  `source` varchar(50) DEFAULT NULL COMMENT '来源',
  `remark` varchar(1000) DEFAULT NULL COMMENT '备注',
  `status` varchar(5) DEFAULT NULL COMMENT '0=未上报1=已上报',
  `realName` varchar(50) DEFAULT NULL COMMENT '姓名',
  `sex` varchar(5) DEFAULT NULL COMMENT '性别',
  `age` int(11) DEFAULT NULL COMMENT '年龄',
  `idCard` varchar(50) DEFAULT NULL COMMENT '身份证号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `black_record` */

/*Table structure for table `company_team_worker` */

DROP TABLE IF EXISTS `company_team_worker`;

CREATE TABLE `company_team_worker` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `companyId` int(11) DEFAULT NULL COMMENT '所属公司id',
  `teamId` int(11) DEFAULT NULL COMMENT '所属班组id',
  `workerId` int(11) DEFAULT NULL COMMENT '工人id',
  `floorId` int(11) DEFAULT NULL COMMENT '住宿楼宇',
  `layerId` int(11) DEFAULT NULL COMMENT '住宿楼层',
  `roomId` int(11) DEFAULT NULL COMMENT '住宿房间',
  `bedId` int(11) DEFAULT NULL COMMENT '住宿床位',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `company_team_worker` */

/*Table structure for table `cultivate_record` */

DROP TABLE IF EXISTS `cultivate_record`;

CREATE TABLE `cultivate_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(200) DEFAULT NULL COMMENT '培训标题',
  `teacher` varchar(50) DEFAULT NULL COMMENT '讲师',
  `studyDate` datetime DEFAULT NULL COMMENT '培训日期',
  `studylong` double DEFAULT NULL COMMENT '培训时长',
  `studyType` varchar(5) DEFAULT NULL COMMENT '培训类型',
  `students` int(11) DEFAULT NULL COMMENT '人数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `cultivate_record` */

/*Table structure for table `directory` */

DROP TABLE IF EXISTS `directory`;

CREATE TABLE `directory` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '目录id',
  `directoryName` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '目录名称',
  `directoryCode` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '目录编码',
  `directoryPriority` int(10) unsigned DEFAULT NULL COMMENT '目录优先级',
  `directoryType` smallint(5) unsigned DEFAULT '1' COMMENT '0 功能导航 1 功能菜单  3 文件夹  4 按钮',
  `directoryIcon` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '目录图标',
  `parentNavigator` int(10) unsigned DEFAULT NULL COMMENT '目录父导航',
  `consoleUrl` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '目录控制台url',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8;

/*Data for the table `directory` */

insert  into `directory`(`id`,`directoryName`,`directoryCode`,`directoryPriority`,`directoryType`,`directoryIcon`,`parentNavigator`,`consoleUrl`) values (1,'系统管理',NULL,NULL,1,NULL,0,NULL),(2,'角色管理',NULL,NULL,1,NULL,1,'/roleIndex.shtml'),(3,'用户管理',NULL,NULL,1,NULL,1,'/staffIndex.shtml'),(14,'人员管理',NULL,NULL,1,NULL,0,NULL),(15,'施工队伍',NULL,NULL,1,NULL,14,'/workTeam.shtml'),(16,'管理人员',NULL,NULL,1,NULL,14,'/worker.shtml'),(17,'授权中心',NULL,NULL,1,NULL,0,NULL),(18,'正式授权',NULL,NULL,1,NULL,17,NULL),(19,'临时授权',NULL,NULL,1,NULL,17,NULL),(20,'门区管理',NULL,NULL,1,NULL,17,NULL),(21,'发卡统计',NULL,NULL,1,NULL,17,NULL),(22,'培训管理',NULL,NULL,1,NULL,0,NULL),(23,'安全培训',NULL,NULL,1,NULL,22,NULL),(24,'劳务记录',NULL,NULL,1,NULL,0,NULL),(25,'不良记录',NULL,NULL,1,NULL,24,NULL),(26,'奖励记录',NULL,NULL,1,NULL,24,NULL),(27,'工时管理',NULL,NULL,1,NULL,0,NULL),(28,'队伍工时',NULL,NULL,1,NULL,27,NULL),(29,'黑名单',NULL,NULL,1,NULL,24,NULL),(30,'工资发放',NULL,NULL,1,NULL,27,NULL),(31,'宿舍管理',NULL,NULL,1,NULL,0,NULL),(32,'楼栋展示',NULL,NULL,1,NULL,31,NULL),(33,'房间展示',NULL,NULL,1,NULL,31,NULL),(34,'床位展示',NULL,NULL,1,NULL,31,NULL);

/*Table structure for table `floor` */

DROP TABLE IF EXISTS `floor`;

CREATE TABLE `floor` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `floor` varchar(100) DEFAULT NULL COMMENT '楼宇名称',
  `layer` varchar(20) DEFAULT NULL COMMENT '层数',
  `room` int(11) DEFAULT NULL COMMENT '房间数',
  `bed` int(11) DEFAULT NULL COMMENT '床数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `floor` */

/*Table structure for table `layer` */

DROP TABLE IF EXISTS `layer`;

CREATE TABLE `layer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `floorId` int(11) DEFAULT NULL COMMENT '楼宇id',
  `layerName` varchar(50) DEFAULT NULL COMMENT '楼层',
  `room` int(11) DEFAULT NULL COMMENT '房间数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf32;

/*Data for the table `layer` */

/*Table structure for table `role_directory_rel` */

DROP TABLE IF EXISTS `role_directory_rel`;

CREATE TABLE `role_directory_rel` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '角色目录id',
  `directoryId` int(10) unsigned NOT NULL COMMENT '目录id',
  `roleId` int(10) unsigned NOT NULL COMMENT '角色id',
  PRIMARY KEY (`id`),
  KEY `fk_role_directory_rel_roleId` (`roleId`) USING BTREE,
  KEY `fk_role_directory_rel_directoryId` (`directoryId`) USING BTREE,
  CONSTRAINT `role_directory_rel_ibfk_1` FOREIGN KEY (`directoryId`) REFERENCES `directory` (`id`),
  CONSTRAINT `role_directory_rel_ibfk_2` FOREIGN KEY (`roleId`) REFERENCES `user_role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8;

/*Data for the table `role_directory_rel` */

insert  into `role_directory_rel`(`id`,`directoryId`,`roleId`) values (18,1,80),(19,2,80),(20,3,80),(21,14,80),(22,15,80),(23,16,80),(24,17,80),(25,18,80),(26,19,80),(27,20,80),(28,21,80),(29,22,80),(30,23,80),(31,24,80),(32,25,80),(33,26,80),(34,27,80),(35,28,80),(36,29,80),(37,30,80),(38,31,80),(39,32,80),(40,33,80),(41,34,80);

/*Table structure for table `room` */

DROP TABLE IF EXISTS `room`;

CREATE TABLE `room` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `floorId` int(11) DEFAULT NULL,
  `layerId` int(11) DEFAULT NULL,
  `roomName` varchar(50) DEFAULT NULL COMMENT '房间名称',
  `bed` varchar(50) DEFAULT NULL COMMENT '床位数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `room` */

/*Table structure for table `staff` */

DROP TABLE IF EXISTS `staff`;

CREATE TABLE `staff` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '员工id',
  `userRoleId` int(10) unsigned DEFAULT NULL COMMENT '用户角色id',
  `userId` int(10) unsigned DEFAULT NULL COMMENT '用户uid',
  `passwd` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '密码',
  `staffName` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '员工名称',
  `sex` smallint(5) unsigned DEFAULT NULL COMMENT '0 男 1 女',
  `email` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '电子邮件',
  `mobile` varchar(11) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '移动电话',
  `phone` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '固定电话',
  `age` int(10) unsigned DEFAULT NULL COMMENT '年龄',
  `birthday` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '生日',
  `idCard` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '身份证号',
  `qq` varchar(12) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'QQ号码',
  `lowest` int(10) unsigned DEFAULT NULL COMMENT '最低审批额度',
  `highest` int(10) unsigned DEFAULT NULL COMMENT '最高审批额度',
  `firstTaskNo` int(10) unsigned DEFAULT '0' COMMENT '当前初审任务数',
  `secondTaskNo` int(10) unsigned DEFAULT '0' COMMENT '当前复审任务数',
  `x509SKeyId` varchar(40) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'X.509 数字证书使用者密钥标识符',
  `remark` varchar(1000) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `platformType` smallint(5) DEFAULT NULL COMMENT '平台类型 1:管理平台 2:商户平台',
  `companyID` int(11) DEFAULT NULL COMMENT '商户企业ID',
  `createMethod` int(11) DEFAULT NULL COMMENT '银客网账户创建方式  0:新建  1:关联',
  `stepNO` smallint(6) DEFAULT NULL COMMENT '创建步骤顺序号',
  `status` smallint(6) DEFAULT NULL COMMENT '帐户状态 0:新建  1:生效',
  `realname` varchar(50) DEFAULT NULL,
  `companyKey` varchar(50) DEFAULT NULL,
  `QRCode` varchar(200) DEFAULT NULL COMMENT '二维码',
  `securityCode` varchar(200) DEFAULT NULL COMMENT '安全码',
  PRIMARY KEY (`id`),
  UNIQUE KEY `staff_userId` (`userId`) USING BTREE,
  KEY `fk_staff_userRoleId` (`userRoleId`) USING BTREE,
  CONSTRAINT `staff_ibfk_1` FOREIGN KEY (`userRoleId`) REFERENCES `user_role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2435 DEFAULT CHARSET=utf8;

/*Data for the table `staff` */

insert  into `staff`(`id`,`userRoleId`,`userId`,`passwd`,`staffName`,`sex`,`email`,`mobile`,`phone`,`age`,`birthday`,`idCard`,`qq`,`lowest`,`highest`,`firstTaskNo`,`secondTaskNo`,`x509SKeyId`,`remark`,`platformType`,`companyID`,`createMethod`,`stepNO`,`status`,`realname`,`companyKey`,`QRCode`,`securityCode`) values (2362,80,NULL,'E1EF39E56C4E088B4014C61509A6CE88','admin',NULL,'hr@zhongzairong.com','13888888888',NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,NULL,'otpauth://totp/admin@zhongzairong.cn?secret=Q6BPNBNZQ2IJV4U6','Q6BPNBNZQ2IJV4U6'),(2370,105,NULL,'E1EF39E56C4E088B4014C61509A6CE88','chenyanfei',NULL,'123456@163.com','18810330646',NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,NULL,'otpauth://totp/chenyanfei@zhongzairong.cn?secret=WPTJOWVKM5MKALQP','WPTJOWVKM5MKALQP'),(2372,105,NULL,'E1EF39E56C4E088B4014C61509A6CE88','lulu',NULL,'lujinhui@zhongzairong.cn','13681270735',NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,NULL,'otpauth://totp/lulu@zhongzairong.cn?secret=WZ3OCJSRSUDPNHLR','WZ3OCJSRSUDPNHLR'),(2373,106,NULL,'E1EF39E56C4E088B4014C61509A6CE88','hello',NULL,'1232323@163.com','14423434343',NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,NULL,'otpauth://totp/hello@zhongzairong.cn?secret=5PMK2LL2M6DS256E','5PMK2LL2M6DS256E'),(2374,106,NULL,'E1EF39E56C4E088B4014C61509A6CE88','songsong',NULL,'1232323@163.com','15232323232',NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,NULL,'otpauth://totp/songsong@zhongzairong.cn?secret=TQO27M26XGTUX4NL','TQO27M26XGTUX4NL'),(2375,105,NULL,'E1EF39E56C4E088B4014C61509A6CE88','xiaoming',NULL,'1232323@163.com','14423232222',NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,NULL,'otpauth://totp/xiaoming@zhongzairong.cn?secret=Q5EQAE3NQT25U7IO','Q5EQAE3NQT25U7IO'),(2376,105,NULL,'E1EF39E56C4E088B4014C61509A6CE88','xinxin',NULL,'1232323@163.com','15232322111',NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,NULL,'otpauth://totp/xinxin@zhongzairong.cn?secret=YGQIUY4VX6S5TOXX','YGQIUY4VX6S5TOXX'),(2377,80,NULL,'E1EF39E56C4E088B4014C61509A6CE88','shanshan',NULL,'1232323@163.com','14423423232',NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,NULL,'otpauth://totp/shanshan@zhongzairong.cn?secret=GSQVNZXNOH6QLN42','GSQVNZXNOH6QLN42'),(2383,107,NULL,'E1EF39E56C4E088B4014C61509A6CE88','jinjin',NULL,'14400001111@qq.com','14400001111',NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,NULL,'otpauth://totp/jinjin@zhongzairong.cn?secret=U43HJO4HMLKATEFZ','U43HJO4HMLKATEFZ'),(2385,105,NULL,'748D0232AC2C824F8DEF7369BD079AA3','juyouqian',NULL,'31212@qq.com','14434343434',NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,NULL,'otpauth://totp/juyouqian@zhongzairong.cn?secret=AY2EQAVE3VCHW5R5','AY2EQAVE3VCHW5R5'),(2388,80,NULL,'748D0232AC2C824F8DEF7369BD079AA3','juju',NULL,'31212@qq.com','14434343434',NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,NULL,'otpauth://totp/juju@zhongzairong.cn?secret=NSTGGGOEF6L3FOY6','NSTGGGOEF6L3FOY6'),(2391,105,NULL,'748D0232AC2C824F8DEF7369BD079AA3','jaja',NULL,'31212@qq.com','14434343434',NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,NULL,'otpauth://totp/jaja@zhongzairong.cn?secret=BHOWHUUDUA7NTDAF','BHOWHUUDUA7NTDAF'),(2394,80,NULL,'E1EF39E56C4E088B4014C61509A6CE88','long',NULL,'yll@qq.com','15822935054',NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,NULL,'otpauth://totp/long@zhongzairong.cn?secret=TCIQU3XWEHLGFDS5','TCIQU3XWEHLGFDS5'),(2401,80,NULL,'437D6979B7C0F041FB72893037DEE5DC','zzbb',NULL,'1231313@qe.com','18686454650',NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,NULL,'otpauth://totp/zzbb@zhongzairong.cn?secret=B5P62VI4QXJAMFXN','B5P62VI4QXJAMFXN'),(2405,105,NULL,'748D0232AC2C824F8DEF7369BD079AA3','xiaoke',NULL,'31212@qq.com','14434343434',NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,NULL,'otpauth://totp/xiaoke@zhongzairong.cn?secret=6OKNG442ECLVM7KZ','6OKNG442ECLVM7KZ'),(2407,105,NULL,'748D0232AC2C824F8DEF7369BD079AA3','aaaa',NULL,'121212@qq.com','14423232323',NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,NULL,'otpauth://totp/aaaa@zhongzairong.cn?secret=SA5MDGGZPPJ2TPGN','SA5MDGGZPPJ2TPGN'),(2409,80,NULL,'E1EF39E56C4E088B4014C61509A6CE88','aaaaa',NULL,'1510011@qq.com','15822935022',NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,NULL,'otpauth://totp/aaaaa@zhongzairong.cn?secret=U3BJFINJ4IIKMSCH','U3BJFINJ4IIKMSCH'),(2412,105,NULL,'748D0232AC2C824F8DEF7369BD079AA3','kekeke',NULL,'31212@qq.com','14434343434',NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,NULL,'otpauth://totp/kekeke@zhongzairong.cn?secret=3YIUR47Z2ROXF5DX','3YIUR47Z2ROXF5DX'),(2413,105,NULL,'E1EF39E56C4E088B4014C61509A6CE88','wwwA',NULL,'151@qq.com','15112311231',NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,NULL,'otpauth://totp/wwwA@zhongzairong.cn?secret=L4IOH2GTJVBPKOBS','L4IOH2GTJVBPKOBS'),(2418,80,NULL,'437D6979B7C0F041FB72893037DEE5DC','aa123',NULL,'15100@qq.com','14400001111',NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,NULL,'otpauth://totp/aa123@zhongzairong.cn?secret=AKDQSGLMQMYAFZDM','AKDQSGLMQMYAFZDM'),(2421,80,NULL,'437D6979B7C0F041FB72893037DEE5DC','cc23',NULL,'15100@qq.com','15822935000',NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,NULL,'otpauth://totp/cc23@zhongzairong.cn?secret=LWNM7UX76IUITZKT','LWNM7UX76IUITZKT'),(2422,105,NULL,'748D0232AC2C824F8DEF7369BD079AA3','ss123',NULL,'121212@qq.com','14423232323',NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,NULL,'otpauth://totp/ss123@zhongzairong.cn?secret=ZOGLDKRKUWCTZCQR','ZOGLDKRKUWCTZCQR'),(2423,105,NULL,'748D0232AC2C824F8DEF7369BD079AA3','ss0820',NULL,'121212@qq.com','14423232323',NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,NULL,'otpauth://totp/ss0820@zhongzairong.cn?secret=CLKD36LOGG2BMLSX','CLKD36LOGG2BMLSX'),(2424,80,NULL,'748D0232AC2C824F8DEF7369BD079AA3','feiF',NULL,'121212@qq.com','14423232323',NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,NULL,'otpauth://totp/feiF@zhongzairong.cn?secret=VZEXCSIQPAN2NVBI','VZEXCSIQPAN2NVBI'),(2425,105,NULL,'748D0232AC2C824F8DEF7369BD079AA3','运营02',NULL,'121212@qq.com','14423232323',NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,NULL,'otpauth://totp/运营02@zhongzairong.cn?secret=AWSPQNBQIBQPMIK7','AWSPQNBQIBQPMIK7'),(2427,105,NULL,'E1EF39E56C4E088B4014C61509A6CE88','运营03',NULL,'12344@qq.com','13681270735',NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,NULL,'otpauth://totp/运营03@zhongzairong.cn?secret=NYUCY5QMXXMYJ5DN','NYUCY5QMXXMYJ5DN'),(2428,106,NULL,'E1EF39E56C4E088B4014C61509A6CE88','运营For',NULL,'455@44.com','13888888888',NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,NULL,'otpauth://totp/运营For@zhongzairong.cn?secret=DPGEZZS7OXUZ3BOU','DPGEZZS7OXUZ3BOU'),(2429,80,NULL,'E1EF39E56C4E088B4014C61509A6CE88','main_12',NULL,'12344@qq.com','14434545334',NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,NULL,'otpauth://totp/main_12@zhongzairong.cn?secret=NJJETEL3R457IL6E','NJJETEL3R457IL6E'),(2430,80,NULL,'E1EF39E56C4E088B4014C61509A6CE88','maIN',NULL,'33@qq.com','14434545334',NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,NULL,'otpauth://totp/maIN@zhongzairong.cn?secret=UZQ2SJUCB4GT4KWG','UZQ2SJUCB4GT4KWG'),(2431,105,NULL,'E1EF39E56C4E088B4014C61509A6CE88','main',NULL,'33@qq.com','14434545334',NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,NULL,'otpauth://totp/main@zhongzairong.cn?secret=D42DTKUMGMZSWDLA','D42DTKUMGMZSWDLA'),(2432,106,NULL,'E1EF39E56C4E088B4014C61509A6CE88','运营人员账号01',NULL,'33@qq.com','14434545334',NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,NULL,'otpauth://totp/运营人员账号01@zhongzairong.cn?secret=BOHNID55LPKGCLSF','BOHNID55LPKGCLSF'),(2433,106,NULL,'E1EF39E56C4E088B4014C61509A6CE88','运营001',NULL,'33@qq.com','14434545334',NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,NULL,'otpauth://totp/运营001@zhongzairong.cn?secret=LDR53NRD4HWAX3BZ','LDR53NRD4HWAX3BZ'),(2434,105,NULL,'E1EF39E56C4E088B4014C61509A6CE88','huihui',NULL,'12344@qq.com','14434545334',NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,NULL,'otpauth://totp/huihui@zhongzairong.cn?secret=V2HEQDKUH7HFRSGJ','V2HEQDKUH7HFRSGJ');

/*Table structure for table `user_role` */

DROP TABLE IF EXISTS `user_role`;

CREATE TABLE `user_role` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `roleName` varchar(50) DEFAULT NULL COMMENT '角色名称id',
  `roleType` smallint(5) unsigned DEFAULT NULL COMMENT '\n            0=业务类型,\n            1=管理类型\n            ',
  `remark` varchar(1000) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `platformType` smallint(5) DEFAULT NULL COMMENT '平台类型 1:管理平台 2:商户平台',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=108 DEFAULT CHARSET=utf8;

/*Data for the table `user_role` */

insert  into `user_role`(`id`,`roleName`,`roleType`,`remark`,`platformType`) values (80,'系统管理员',1,'系统管理,角色管理,用户管理,运营管理,用户流量,资金流量,产品详情,复投留存,新老占比',1),(105,'测试',NULL,'系统管理,用户管理,用户流量',1),(106,'新年快乐角色',NULL,'系统管理,角色管理,用户管理,运营管理,用户流量,资金流量,产品详情,复投留存,新老占比',1),(107,'2月角色',NULL,'系统管理,角色管理,用户管理',1);

/*Table structure for table `wages` */

DROP TABLE IF EXISTS `wages`;

CREATE TABLE `wages` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `companyId` int(11) DEFAULT NULL COMMENT '公司id',
  `teamId` int(11) DEFAULT NULL COMMENT '班组id',
  `wagesTime` datetime DEFAULT NULL COMMENT '工资发放年月',
  `remark` varchar(1000) DEFAULT NULL COMMENT '备注',
  `creatTime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `wages` */

/*Table structure for table `work_company` */

DROP TABLE IF EXISTS `work_company`;

CREATE TABLE `work_company` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL COMMENT '公司名称',
  `creatTime` datetime DEFAULT NULL COMMENT '创建时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `manager` varchar(50) DEFAULT NULL COMMENT '法人',
  `mobile` varchar(11) DEFAULT NULL COMMENT '法人电话',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `work_company` */

insert  into `work_company`(`id`,`name`,`creatTime`,`remark`,`manager`,`mobile`) values (1,'中国铁路总公司主数据中心工程','2018-06-02 11:12:55',NULL,'张三','13245678900');

/*Table structure for table `work_team` */

DROP TABLE IF EXISTS `work_team`;

CREATE TABLE `work_team` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `teamName` varchar(200) DEFAULT NULL COMMENT '班组名称',
  `creatTime` datetime DEFAULT NULL COMMENT '创建时间',
  `teamManager` varchar(50) DEFAULT NULL COMMENT '班长',
  `mobile` varchar(11) DEFAULT NULL COMMENT '班长电话',
  `companyId` int(11) DEFAULT NULL COMMENT '公司id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `work_team` */

insert  into `work_team`(`id`,`teamName`,`creatTime`,`teamManager`,`mobile`,`companyId`) values (1,'李刚-民工食堂','2018-06-02 17:21:00','李刚','13211144587',1),(2,'尹春景-齐河县三建筑','2018-06-02 17:28:02','尹春景','13211144564',1);

/*Table structure for table `worker` */

DROP TABLE IF EXISTS `worker`;

CREATE TABLE `worker` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `realName` varchar(50) DEFAULT NULL COMMENT '姓名',
  `sex` int(11) DEFAULT NULL COMMENT '性别0=男1=女',
  `age` int(11) DEFAULT NULL COMMENT '年龄',
  `nation` varchar(20) DEFAULT NULL COMMENT '民族',
  `idCard` varchar(20) DEFAULT NULL COMMENT '身份证',
  `contractStatus` varchar(5) DEFAULT NULL COMMENT '合同状态1=已签合同0=未签合同',
  `type` varchar(5) DEFAULT NULL COMMENT '1=管理员',
  `creatTime` datetime DEFAULT NULL COMMENT '创建时间',
  `station` varchar(50) DEFAULT NULL COMMENT '岗位',
  `department` varchar(50) DEFAULT NULL COMMENT '部门',
  `teamId` int(11) DEFAULT NULL COMMENT '所属班组',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `worker` */

/*Table structure for table `worker_card_record` */

DROP TABLE IF EXISTS `worker_card_record`;

CREATE TABLE `worker_card_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `workerId` int(11) DEFAULT NULL COMMENT '工人Id',
  `cardTime` datetime DEFAULT NULL COMMENT '打卡时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `worker_card_record` */

/*Table structure for table `worker_record` */

DROP TABLE IF EXISTS `worker_record`;

CREATE TABLE `worker_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `rtype` varbinary(5) DEFAULT NULL COMMENT '0=不良记录1=奖励记录',
  `happenTime` datetime DEFAULT NULL COMMENT '发生时间',
  `reason` varchar(1000) DEFAULT NULL COMMENT '时间发生原因',
  `level` varchar(500) DEFAULT NULL COMMENT '严重程度、奖项',
  `remark` varchar(1000) DEFAULT NULL COMMENT '备注',
  `workerId` int(11) DEFAULT NULL COMMENT '工人id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `worker_record` */

/*Table structure for table `worker_role` */

DROP TABLE IF EXISTS `worker_role`;

CREATE TABLE `worker_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `department` varchar(50) DEFAULT NULL COMMENT '部门',
  `creatTime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `worker_role` */

/*Table structure for table `worker_station` */

DROP TABLE IF EXISTS `worker_station`;

CREATE TABLE `worker_station` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `station` varchar(100) DEFAULT NULL COMMENT '岗位',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `worker_station` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
