/*
SQLyog Ultimate v10.00 Beta1
MySQL - 5.7.25 : Database - stock_db_2024
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`stock_db_2024` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `stock_db_2024`;

/*Table structure for table `stock_block_rt_info` */

CREATE TABLE `stock_block_rt_info` (
                                       `id` bigint(20) NOT NULL COMMENT '板块主键ID（业务无关）',
                                       `label` varchar(20) DEFAULT NULL COMMENT '表示，如：new_blhy-玻璃行业',
                                       `block_name` varchar(20) DEFAULT NULL COMMENT '板块名称',
                                       `company_num` int(11) DEFAULT NULL COMMENT '公司数量',
                                       `avg_price` decimal(10,3) DEFAULT NULL COMMENT '平均价格',
                                       `updown_rate` decimal(10,3) DEFAULT NULL COMMENT '涨跌幅',
                                       `trade_amount` bigint(20) DEFAULT NULL COMMENT '交易量',
                                       `trade_volume` decimal(18,3) DEFAULT NULL COMMENT '交易金额',
                                       `cur_time` datetime DEFAULT NULL COMMENT '当前日期（精确到秒）',
                                       PRIMARY KEY (`id`) USING BTREE,
                                       UNIQUE KEY `unique_name_time` (`cur_time`,`label`) USING BTREE COMMENT '板块表示与时间组成唯一索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT COMMENT='股票板块详情信息表';

/*Data for the table `stock_block_rt_info` */

/*Table structure for table `stock_business` */

CREATE TABLE `stock_business` (
                                  `stock_code` char(6) NOT NULL DEFAULT '' COMMENT ' 股票编码',
                                  `stock_name` varchar(20) DEFAULT '' COMMENT '股票名称',
                                  `block_label` varchar(10) DEFAULT NULL COMMENT '股票所属行业|板块标识',
                                  `block_name` varchar(20) DEFAULT NULL COMMENT '行业板块名称',
                                  `business` varchar(300) DEFAULT NULL COMMENT '主营业务',
                                  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                  PRIMARY KEY (`stock_code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='主营业务表';

/*Data for the table `stock_business` */

insert  into `stock_business`(`stock_code`,`stock_name`,`block_label`,`block_name`,`business`,`update_time`) values ('000001','平安银行','856024','银行 ','经有关监管机构批准的各项商业银行业务','2020-08-10 07:35:30');
insert  into `stock_business`(`stock_code`,`stock_name`,`block_label`,`block_name`,`business`,`update_time`) values ('000002','万科Ａ','856004','房地产  ','房地产开发和物业服务','2020-06-05 19:39:53');
insert  into `stock_business`(`stock_code`,`stock_name`,`block_label`,`block_name`,`business`,`update_time`) values ('000004','国农科技','856011','计算机	 ','通讯、计算机、软件、新材料、生物技术和生物特征识别技术、新药、生物制品、医用检测试剂和设备的研究与开发；信息咨询；计算机软件及生物技术的培训（以上各项不含限制项目）、兴办实业（具体项目另行申报）、房地产开发与经营。','2020-06-05 19:39:53');
insert  into `stock_business`(`stock_code`,`stock_name`,`block_label`,`block_name`,`business`,`update_time`) values ('000005','世纪星源','856004','房地产  ','绿色低碳城市社区建设相关的服务业务','2020-06-05 19:39:53');
insert  into `stock_business`(`stock_code`,`stock_name`,`block_label`,`block_name`,`business`,`update_time`) values ('000006','深振业Ａ','856004','房地产  ','土地开发、房产销售及租赁、物业管理。','2020-06-05 19:39:53');
insert  into `stock_business`(`stock_code`,`stock_name`,`block_label`,`block_name`,`business`,`update_time`) values ('000007','全新好','856004','房地产  ','物业管理和房屋租赁业等','2020-06-05 19:39:53');
insert  into `stock_business`(`stock_code`,`stock_name`,`block_label`,`block_name`,`business`,`update_time`) values ('000008','神州高铁','856014','交通运输','专业致力于为轨道交通安全运营提供监测、检测、维修、保养设备和服务及整体解决方案','2020-06-05 19:39:53');
insert  into `stock_business`(`stock_code`,`stock_name`,`block_label`,`block_name`,`business`,`update_time`) values ('000009','中国宝安','856004','房地产  ','新能源、新材料及其它高新技术产业、生物医药业、房地产业以及其他行业','2020-06-05 19:39:53');
insert  into `stock_business`(`stock_code`,`stock_name`,`block_label`,`block_name`,`business`,`update_time`) values ('000011','深物业A','856004','房地产	 ','房地产开发及商品房销售,商品楼宇的建筑、管理,房屋租赁,建设监理。国内商业、物资供销业(不含专营专卖专控商品)。兼营:物业租赁及管理、商业、运输业、饮食旅游业等。','2020-06-05 19:39:53');
insert  into `stock_business`(`stock_code`,`stock_name`,`block_label`,`block_name`,`business`,`update_time`) values ('000012','南玻Ａ','856013','建筑材料','平板玻璃、工程玻璃等节能建筑材料,硅材料、光伏组件等可再生能源产品及超薄电子玻璃等新型材料和高科技产品的生产、制造和销售','2020-06-05 19:39:53');
insert  into `stock_business`(`stock_code`,`stock_name`,`block_label`,`block_name`,`business`,`update_time`) values ('000014','沙河股份','856004','房地产  ','房地产开发和销售','2020-06-05 19:39:53');
insert  into `stock_business`(`stock_code`,`stock_name`,`block_label`,`block_name`,`business`,`update_time`) values ('000016','深康佳Ａ','856012','家用电器','研究开发、生产经营电视机、冰箱、洗衣机、日用小家电等家用电器产品,家庭视听设备,IPTV机顶盒,数字电视接收器(含卫星电视广播地面接收设备),数码产品,移动通信设备及终端产品,日用电子产品,汽车电子产品,卫星导航系统,智能交通系统,防火防盗报警系统,办公设备,电子计算机,显示器,大屏幕显示设备的制造和应用服务','2020-06-05 19:39:53');
insert  into `stock_business`(`stock_code`,`stock_name`,`block_label`,`block_name`,`business`,`update_time`) values ('000017','深中华A','856010','机械设备','生产装配各种类型的自行车及自行车零件、部件、配件、机械产品、运动器械、精细化工、碳纤维复合材料、家用小电器及配套原件等。','2020-06-05 19:39:53');
insert  into `stock_business`(`stock_code`,`stock_name`,`block_label`,`block_name`,`business`,`update_time`) values ('000019','深粮控股','856020','食品饮料','茶及天然植物精深加工为主的食品原料(配料)生产研发和销售业务;粮油储备粮油贸易粮油加工等粮油流通及粮油储备服务业','2020-06-05 19:39:53');
insert  into `stock_business`(`stock_code`,`stock_name`,`block_label`,`block_name`,`business`,`update_time`) values ('000020','深华发Ａ','856012','家用电器','生产经营各种彩色电视机、液晶显示器、液晶显示屏(在分支机构生产经营)、收录机、音响设备、电子表、电子游戏机、电脑等各类电子产品及配套的印刷线路板、精密注塑件、轻型包装材料(在武汉生产经营)、五金件(含工模具),电镀及表面处理、焊锡丝,房地产开发经营(深房地字第7226760号),物业管理。','2020-06-05 19:39:53');
insert  into `stock_business`(`stock_code`,`stock_name`,`block_label`,`block_name`,`business`,`update_time`) values ('000021','深科技','856003','电子    ','开发、生产、经营计算机软、硬件系统及其外部设备、通讯设备、电子仪器仪表及其零部件、原器件、接插件和原材料,生产、经营家用商品电脑及电子玩具(以上生产项目均不含限制项目);金融计算机软件模型的制作和设计、精密模具CAD/CAM 技术、节能型自动化机电产品和智能自动化控制系统','2020-06-05 19:39:53');
insert  into `stock_business`(`stock_code`,`stock_name`,`block_label`,`block_name`,`business`,`update_time`) values ('000023','深天地Ａ','856026','综合    ','商品混凝土及其原材料的生产、销售(具体生产场地执照另行申办);水泥制品的生产、销售(具体生产场地执照另行申办);在合法取得土地使用权的地块上从事房地产开发;物流服务;普通货运,货物专用运输(罐式)(道路运输经营许可证有效期至2014年12月31);机电设备维修;物业管理;投资兴办实业(具体项目另行申报);国内商业、物资供销业(不含专营、专控、专卖商品及限制项目);经营进出口业务(按深府办函[1994]278号文执行)。','2020-06-05 19:39:53');
insert  into `stock_business`(`stock_code`,`stock_name`,`block_label`,`block_name`,`business`,`update_time`) values ('000025','特力Ａ','856017','汽车    ','投资兴办实业(具体项目另行申报);经营在已合法取得的土地使用权范围内的房地产开发。国内商业、物资供销业(不含专营、专控、专卖商品)、物业租赁与管理。自营本公司及所属企业自产产品、自用生产材料、金属加工机械、通用零件的进出口业务。进出口业务按深贸管证字第098号外贸审查证书办理。','2020-06-05 19:39:53');
insert  into `stock_business`(`stock_code`,`stock_name`,`block_label`,`block_name`,`business`,`update_time`) values ('000026','飞亚达','856003','电子    ','生产经营各种指针式石英表及其机芯、零部件、各种计时仪器、加工批发Ｋ金手饰表（生产场地另行申报）；国内商业、物资供销业（不含专营、专控、专卖商品）；物业管理、物业租赁；自营进出口业务（按深贸管登证字第2000-072号文执行）。','2020-06-05 19:39:53');
insert  into `stock_business`(`stock_code`,`stock_name`,`block_label`,`block_name`,`business`,`update_time`) values ('000027','深圳能源','856009','化工    ','各种常规能源和新能源的开发、生产、购销；投资和经营能提高能源使用效益的高科技产业；投资和经营与能源相关的化工原材料的开发和运输、港口、码头和仓储工业等；','2020-06-05 19:39:53');
insert  into `stock_business`(`stock_code`,`stock_name`,`block_label`,`block_name`,`business`,`update_time`) values ('600000','浦发银行','856024','银行','提供银行及相关金融服务','2020-06-06 20:24:11');
insert  into `stock_business`(`stock_code`,`stock_name`,`block_label`,`block_name`,`business`,`update_time`) values ('600004','白云机场','856014','交通运输','以航空器、旅客和货物、邮件为对象，提供飞机起降与停场、旅客综合服务、安全检查以及航空地面保障等航空服务业务，和货邮代理服务、航站楼内商业场地租赁服务、特许经营服务、地面运输服务和广告服务等航空性延伸服务业务。','2020-06-05 19:39:53');
insert  into `stock_business`(`stock_code`,`stock_name`,`block_label`,`block_name`,`business`,`update_time`) values ('600006','东风汽车','856017','汽车    ','全系列轻型商用车整车和动力总成的设计、制造和销售。','2020-06-05 19:39:53');
insert  into `stock_business`(`stock_code`,`stock_name`,`block_label`,`block_name`,`business`,`update_time`) values ('600007','中国国贸','856019','商业贸易','从事商务服务设施的投资、经营和管理','2020-06-05 19:39:53');
insert  into `stock_business`(`stock_code`,`stock_name`,`block_label`,`block_name`,`business`,`update_time`) values ('600008','首创股份','856026','综合    ','水务固废等环保业务','2020-06-05 19:39:53');
insert  into `stock_business`(`stock_code`,`stock_name`,`block_label`,`block_name`,`business`,`update_time`) values ('600009','上海机场','856014','交通运输','航空地面保障服务及其配套服务','2020-06-05 19:39:53');
insert  into `stock_business`(`stock_code`,`stock_name`,`block_label`,`block_name`,`business`,`update_time`) values ('600010','包钢股份','856006','钢铁    ','矿产资源开发利用、钢铁产品的生产与销售','2020-06-05 19:39:53');
insert  into `stock_business`(`stock_code`,`stock_name`,`block_label`,`block_name`,`business`,`update_time`) values ('600011','华能国际','856002','电气设备','电力及热力销售、港口服务及运输服务等','2020-06-05 19:39:53');
insert  into `stock_business`(`stock_code`,`stock_name`,`block_label`,`block_name`,`business`,`update_time`) values ('600012','皖通高速','856014','交通运输','投资、建设、运营及管理安徽省境内外的收费公路','2020-06-05 19:39:53');
insert  into `stock_business`(`stock_code`,`stock_name`,`block_label`,`block_name`,`business`,`update_time`) values ('600015','华夏银行','856024','银行    ','吸收存款;发放贷款;办理国内外结算;发行金融债券;同业拆借等业务','2020-06-05 19:39:53');
insert  into `stock_business`(`stock_code`,`stock_name`,`block_label`,`block_name`,`business`,`update_time`) values ('600016','民生银行','856024','银行    ','从事公司及个人银行业务、资金业务、融资租赁业务、资产管理业务及提供其他相关金融服务。','2020-06-05 19:39:53');
insert  into `stock_business`(`stock_code`,`stock_name`,`block_label`,`block_name`,`business`,`update_time`) values ('600017','日照港','856014','交通运输','装卸业务、堆存业务和港务管理业务','2020-06-05 19:39:53');
insert  into `stock_business`(`stock_code`,`stock_name`,`block_label`,`block_name`,`business`,`update_time`) values ('600018','上港集团','856014','交通运输','集装箱装卸业务、散杂货装卸业务、港口服务业务和港口物流业务。','2020-06-05 19:39:53');
insert  into `stock_business`(`stock_code`,`stock_name`,`block_label`,`block_name`,`business`,`update_time`) values ('600019','宝钢股份','856006','钢铁    ','钢铁产品的制造和销售以及钢铁产销过程中产生的副产品的销售与服务。','2020-06-05 19:39:53');
insert  into `stock_business`(`stock_code`,`stock_name`,`block_label`,`block_name`,`business`,`update_time`) values ('600020','中原高速','856014','交通运输','经营性收费公路的投资、建设、运营管理等。','2020-06-05 19:39:53');
insert  into `stock_business`(`stock_code`,`stock_name`,`block_label`,`block_name`,`business`,`update_time`) values ('600021','上海电力','856002','电气设备','从事电力、热力产品的生产和销售','2020-06-05 19:39:53');
insert  into `stock_business`(`stock_code`,`stock_name`,`block_label`,`block_name`,`business`,`update_time`) values ('600022','山东钢铁','856006','钢铁    ','以生产、销售钢材、钢坯等钢铁产品为主。','2020-06-05 19:39:53');
insert  into `stock_business`(`stock_code`,`stock_name`,`block_label`,`block_name`,`business`,`update_time`) values ('600023','浙能电力','856002','电气设备','从事火力发电业务','2020-06-05 19:39:53');
insert  into `stock_business`(`stock_code`,`stock_name`,`block_label`,`block_name`,`business`,`update_time`) values ('600025','华能水电','856002','电气设备','水力发电项目的开发、投资、建设、运营与管理','2020-06-05 19:39:53');
insert  into `stock_business`(`stock_code`,`stock_name`,`block_label`,`block_name`,`business`,`update_time`) values ('600026','中远海能','856014','交通运输','中国沿海地区和全球的成品油及原油运输，中国沿海地区和全球的煤炭和铁矿石运输。','2020-06-05 19:39:53');

/*Table structure for table `stock_market_index_info` */

CREATE TABLE `stock_market_index_info` (
                                           `id` bigint(20) NOT NULL COMMENT '主键字段（无业务意义）',
                                           `market_code` char(12) NOT NULL COMMENT '大盘编码',
                                           `market_name` varchar(20) DEFAULT NULL COMMENT '指数名称',
                                           `pre_close_point` decimal(18,2) DEFAULT NULL COMMENT '前收盘点',
                                           `open_point` decimal(18,2) DEFAULT NULL COMMENT '开盘点',
                                           `cur_point` decimal(18,2) DEFAULT NULL COMMENT '当前点数',
                                           `min_point` decimal(18,2) DEFAULT NULL COMMENT '最低点',
                                           `max_point` decimal(18,2) DEFAULT NULL COMMENT '最高点',
                                           `trade_amount` bigint(20) DEFAULT NULL COMMENT '成交量（手）',
                                           `trade_volume` bigint(18) DEFAULT NULL COMMENT '成交额(元)',
                                           `cur_time` datetime NOT NULL COMMENT '当前时间',
                                           PRIMARY KEY (`id`) USING BTREE,
                                           UNIQUE KEY `unique_id_time` (`cur_time`,`market_code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='股票大盘数据详情表';

/*Data for the table `stock_market_index_info` */

/*Table structure for table `stock_outer_market_index_info` */

CREATE TABLE `stock_outer_market_index_info` (
                                                 `id` bigint(20) NOT NULL COMMENT '主键ID',
                                                 `market_code` char(12) NOT NULL COMMENT '大盘编码',
                                                 `market_name` varchar(20) NOT NULL COMMENT '大盘名称',
                                                 `cur_point` decimal(10,2) DEFAULT NULL COMMENT '大盘当前点',
                                                 `updown` decimal(10,2) DEFAULT NULL COMMENT '大盘涨跌值',
                                                 `rose` decimal(10,2) DEFAULT NULL COMMENT '大盘涨幅',
                                                 `cur_time` datetime NOT NULL COMMENT '当前时间',
                                                 PRIMARY KEY (`id`) USING BTREE,
                                                 UNIQUE KEY `unique_mcode_date` (`cur_time`,`market_code`) USING BTREE COMMENT '大盘id与日期组成唯一索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT COMMENT='股票大盘 开盘价与前收盘价流水表';

/*Data for the table `stock_outer_market_index_info` */

/*Table structure for table `stock_rt_info_202401` */

CREATE TABLE `stock_rt_info_202401` (
                                        `id` bigint(20) NOT NULL COMMENT '主键字段（无业务意义）',
                                        `stock_code` char(6) NOT NULL COMMENT '股票代码',
                                        `stock_name` varchar(20) NOT NULL COMMENT '股票名称',
                                        `pre_close_price` decimal(8,2) DEFAULT NULL COMMENT '前收盘价| 昨日收盘价',
                                        `open_price` decimal(8,2) DEFAULT NULL COMMENT '开盘价',
                                        `cur_price` decimal(8,2) NOT NULL COMMENT '当前价格',
                                        `min_price` decimal(8,2) DEFAULT NULL COMMENT '今日最低价',
                                        `max_price` decimal(8,2) DEFAULT NULL COMMENT '今日最高价',
                                        `trade_amount` bigint(20) DEFAULT NULL COMMENT '成交量',
                                        `trade_volume` decimal(18,3) DEFAULT NULL COMMENT '成交总金额(单位元)',
                                        `cur_time` datetime NOT NULL COMMENT '当前时间',
                                        PRIMARY KEY (`id`) USING BTREE,
                                        UNIQUE KEY `unique_code_date` (`stock_code`,`cur_time`) USING BTREE COMMENT '股票编码时间唯一索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='个股详情信息表';

/*Data for the table `stock_rt_info_202401` */

/*Table structure for table `stock_rt_info_202402` */

CREATE TABLE `stock_rt_info_202402` (
                                        `id` bigint(20) NOT NULL COMMENT '主键字段（无业务意义）',
                                        `stock_code` char(6) NOT NULL COMMENT '股票代码',
                                        `stock_name` varchar(20) NOT NULL COMMENT '股票名称',
                                        `pre_close_price` decimal(8,2) DEFAULT NULL COMMENT '前收盘价| 昨日收盘价',
                                        `open_price` decimal(8,2) DEFAULT NULL COMMENT '开盘价',
                                        `cur_price` decimal(8,2) NOT NULL COMMENT '当前价格',
                                        `min_price` decimal(8,2) DEFAULT NULL COMMENT '今日最低价',
                                        `max_price` decimal(8,2) DEFAULT NULL COMMENT '今日最高价',
                                        `trade_amount` bigint(20) DEFAULT NULL COMMENT '成交量',
                                        `trade_volume` decimal(18,3) DEFAULT NULL COMMENT '成交总金额(单位元)',
                                        `cur_time` datetime NOT NULL COMMENT '当前时间',
                                        PRIMARY KEY (`id`) USING BTREE,
                                        UNIQUE KEY `unique_code_time` (`cur_time`,`stock_code`) USING BTREE COMMENT '股票编码和日期构成唯一索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='个股详情信息表';

/*Data for the table `stock_rt_info_202402` */

/*Table structure for table `stock_rt_info_202403` */

CREATE TABLE `stock_rt_info_202403` (
                                        `id` bigint(20) NOT NULL COMMENT '主键字段（无业务意义）',
                                        `stock_code` char(6) NOT NULL COMMENT '股票代码',
                                        `stock_name` varchar(20) NOT NULL COMMENT '股票名称',
                                        `pre_close_price` decimal(8,2) DEFAULT NULL COMMENT '前收盘价| 昨日收盘价',
                                        `open_price` decimal(8,2) DEFAULT NULL COMMENT '开盘价',
                                        `cur_price` decimal(8,2) NOT NULL COMMENT '当前价格',
                                        `min_price` decimal(8,2) DEFAULT NULL COMMENT '今日最低价',
                                        `max_price` decimal(8,2) DEFAULT NULL COMMENT '今日最高价',
                                        `trade_amount` bigint(20) DEFAULT NULL COMMENT '成交量',
                                        `trade_volume` decimal(18,3) DEFAULT NULL COMMENT '成交总金额(单位元)',
                                        `cur_time` datetime NOT NULL COMMENT '当前时间',
                                        PRIMARY KEY (`id`) USING BTREE,
                                        UNIQUE KEY `unique_code_time` (`cur_time`,`stock_code`) USING BTREE COMMENT '股票编码和日期构成唯一索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='个股详情信息表';

/*Data for the table `stock_rt_info_202403` */

/*Table structure for table `stock_rt_info_202404` */

CREATE TABLE `stock_rt_info_202404` (
                                        `id` bigint(20) NOT NULL COMMENT '主键字段（无业务意义）',
                                        `stock_code` char(6) NOT NULL COMMENT '股票代码',
                                        `stock_name` varchar(20) NOT NULL COMMENT '股票名称',
                                        `pre_close_price` decimal(8,2) DEFAULT NULL COMMENT '前收盘价| 昨日收盘价',
                                        `open_price` decimal(8,2) DEFAULT NULL COMMENT '开盘价',
                                        `cur_price` decimal(8,2) NOT NULL COMMENT '当前价格',
                                        `min_price` decimal(8,2) DEFAULT NULL COMMENT '今日最低价',
                                        `max_price` decimal(8,2) DEFAULT NULL COMMENT '今日最高价',
                                        `trade_amount` bigint(20) DEFAULT NULL COMMENT '成交量',
                                        `trade_volume` decimal(18,3) DEFAULT NULL COMMENT '成交总金额(单位元)',
                                        `cur_time` datetime NOT NULL COMMENT '当前时间',
                                        PRIMARY KEY (`id`) USING BTREE,
                                        UNIQUE KEY `unique_code_time` (`cur_time`,`stock_code`) USING BTREE COMMENT '股票编码和日期构成唯一索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='个股详情信息表';

/*Data for the table `stock_rt_info_202404` */

/*Table structure for table `stock_rt_info_202405` */

CREATE TABLE `stock_rt_info_202405` (
                                        `id` bigint(20) NOT NULL COMMENT '主键字段（无业务意义）',
                                        `stock_code` char(6) NOT NULL COMMENT '股票代码',
                                        `stock_name` varchar(20) NOT NULL COMMENT '股票名称',
                                        `pre_close_price` decimal(8,2) DEFAULT NULL COMMENT '前收盘价| 昨日收盘价',
                                        `open_price` decimal(8,2) DEFAULT NULL COMMENT '开盘价',
                                        `cur_price` decimal(8,2) NOT NULL COMMENT '当前价格',
                                        `min_price` decimal(8,2) DEFAULT NULL COMMENT '今日最低价',
                                        `max_price` decimal(8,2) DEFAULT NULL COMMENT '今日最高价',
                                        `trade_amount` bigint(20) DEFAULT NULL COMMENT '成交量',
                                        `trade_volume` decimal(18,3) DEFAULT NULL COMMENT '成交总金额(单位元)',
                                        `cur_time` datetime NOT NULL COMMENT '当前时间',
                                        PRIMARY KEY (`id`) USING BTREE,
                                        UNIQUE KEY `unique_code_time` (`cur_time`,`stock_code`) USING BTREE COMMENT '股票编码和日期构成唯一索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='个股详情信息表';

/*Data for the table `stock_rt_info_202405` */

/*Table structure for table `stock_rt_info_202406` */

CREATE TABLE `stock_rt_info_202406` (
                                        `id` bigint(20) NOT NULL COMMENT '主键字段（无业务意义）',
                                        `stock_code` char(6) NOT NULL COMMENT '股票代码',
                                        `stock_name` varchar(20) NOT NULL COMMENT '股票名称',
                                        `pre_close_price` decimal(8,2) DEFAULT NULL COMMENT '前收盘价| 昨日收盘价',
                                        `open_price` decimal(8,2) DEFAULT NULL COMMENT '开盘价',
                                        `cur_price` decimal(8,2) NOT NULL COMMENT '当前价格',
                                        `min_price` decimal(8,2) DEFAULT NULL COMMENT '今日最低价',
                                        `max_price` decimal(8,2) DEFAULT NULL COMMENT '今日最高价',
                                        `trade_amount` bigint(20) DEFAULT NULL COMMENT '成交量',
                                        `trade_volume` decimal(18,3) DEFAULT NULL COMMENT '成交总金额(单位元)',
                                        `cur_time` datetime NOT NULL COMMENT '当前时间',
                                        PRIMARY KEY (`id`) USING BTREE,
                                        UNIQUE KEY `unique_code_time` (`cur_time`,`stock_code`) USING BTREE COMMENT '股票编码和日期构成唯一索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='个股详情信息表';

/*Data for the table `stock_rt_info_202406` */

/*Table structure for table `stock_rt_info_202407` */

CREATE TABLE `stock_rt_info_202407` (
                                        `id` bigint(20) NOT NULL COMMENT '主键字段（无业务意义）',
                                        `stock_code` char(6) NOT NULL COMMENT '股票代码',
                                        `stock_name` varchar(20) NOT NULL COMMENT '股票名称',
                                        `pre_close_price` decimal(8,2) DEFAULT NULL COMMENT '前收盘价| 昨日收盘价',
                                        `open_price` decimal(8,2) DEFAULT NULL COMMENT '开盘价',
                                        `cur_price` decimal(8,2) NOT NULL COMMENT '当前价格',
                                        `min_price` decimal(8,2) DEFAULT NULL COMMENT '今日最低价',
                                        `max_price` decimal(8,2) DEFAULT NULL COMMENT '今日最高价',
                                        `trade_amount` bigint(20) DEFAULT NULL COMMENT '成交量',
                                        `trade_volume` decimal(18,3) DEFAULT NULL COMMENT '成交总金额(单位元)',
                                        `cur_time` datetime NOT NULL COMMENT '当前时间',
                                        PRIMARY KEY (`id`) USING BTREE,
                                        UNIQUE KEY `unique_code_time` (`cur_time`,`stock_code`) USING BTREE COMMENT '股票编码和日期构成唯一索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='个股详情信息表';

/*Data for the table `stock_rt_info_202407` */

/*Table structure for table `stock_rt_info_202408` */

CREATE TABLE `stock_rt_info_202408` (
                                        `id` bigint(20) NOT NULL COMMENT '主键字段（无业务意义）',
                                        `stock_code` char(6) NOT NULL COMMENT '股票代码',
                                        `stock_name` varchar(20) NOT NULL COMMENT '股票名称',
                                        `pre_close_price` decimal(8,2) DEFAULT NULL COMMENT '前收盘价| 昨日收盘价',
                                        `open_price` decimal(8,2) DEFAULT NULL COMMENT '开盘价',
                                        `cur_price` decimal(8,2) NOT NULL COMMENT '当前价格',
                                        `min_price` decimal(8,2) DEFAULT NULL COMMENT '今日最低价',
                                        `max_price` decimal(8,2) DEFAULT NULL COMMENT '今日最高价',
                                        `trade_amount` bigint(20) DEFAULT NULL COMMENT '成交量',
                                        `trade_volume` decimal(18,3) DEFAULT NULL COMMENT '成交总金额(单位元)',
                                        `cur_time` datetime NOT NULL COMMENT '当前时间',
                                        PRIMARY KEY (`id`) USING BTREE,
                                        UNIQUE KEY `unique_code_time` (`cur_time`,`stock_code`) USING BTREE COMMENT '股票编码和日期构成唯一索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='个股详情信息表';

/*Data for the table `stock_rt_info_202408` */

/*Table structure for table `stock_rt_info_202409` */

CREATE TABLE `stock_rt_info_202409` (
                                        `id` bigint(20) NOT NULL COMMENT '主键字段（无业务意义）',
                                        `stock_code` char(6) NOT NULL COMMENT '股票代码',
                                        `stock_name` varchar(20) NOT NULL COMMENT '股票名称',
                                        `pre_close_price` decimal(8,2) DEFAULT NULL COMMENT '前收盘价| 昨日收盘价',
                                        `open_price` decimal(8,2) DEFAULT NULL COMMENT '开盘价',
                                        `cur_price` decimal(8,2) NOT NULL COMMENT '当前价格',
                                        `min_price` decimal(8,2) DEFAULT NULL COMMENT '今日最低价',
                                        `max_price` decimal(8,2) DEFAULT NULL COMMENT '今日最高价',
                                        `trade_amount` bigint(20) DEFAULT NULL COMMENT '成交量',
                                        `trade_volume` decimal(18,3) DEFAULT NULL COMMENT '成交总金额(单位元)',
                                        `cur_time` datetime NOT NULL COMMENT '当前时间',
                                        PRIMARY KEY (`id`) USING BTREE,
                                        UNIQUE KEY `unique_code_time` (`cur_time`,`stock_code`) USING BTREE COMMENT '股票编码和日期构成唯一索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='个股详情信息表';

/*Data for the table `stock_rt_info_202409` */

/*Table structure for table `stock_rt_info_202410` */

CREATE TABLE `stock_rt_info_202410` (
                                        `id` bigint(20) NOT NULL COMMENT '主键字段（无业务意义）',
                                        `stock_code` char(6) NOT NULL COMMENT '股票代码',
                                        `stock_name` varchar(20) NOT NULL COMMENT '股票名称',
                                        `pre_close_price` decimal(8,2) DEFAULT NULL COMMENT '前收盘价| 昨日收盘价',
                                        `open_price` decimal(8,2) DEFAULT NULL COMMENT '开盘价',
                                        `cur_price` decimal(8,2) NOT NULL COMMENT '当前价格',
                                        `min_price` decimal(8,2) DEFAULT NULL COMMENT '今日最低价',
                                        `max_price` decimal(8,2) DEFAULT NULL COMMENT '今日最高价',
                                        `trade_amount` bigint(20) DEFAULT NULL COMMENT '成交量',
                                        `trade_volume` decimal(18,3) DEFAULT NULL COMMENT '成交总金额(单位元)',
                                        `cur_time` datetime NOT NULL COMMENT '当前时间',
                                        PRIMARY KEY (`id`) USING BTREE,
                                        UNIQUE KEY `unique_code_time` (`cur_time`,`stock_code`) USING BTREE COMMENT '股票编码和日期构成唯一索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='个股详情信息表';

/*Data for the table `stock_rt_info_202410` */

/*Table structure for table `stock_rt_info_202411` */

CREATE TABLE `stock_rt_info_202411` (
                                        `id` bigint(20) NOT NULL COMMENT '主键字段（无业务意义）',
                                        `stock_code` char(6) NOT NULL COMMENT '股票代码',
                                        `stock_name` varchar(20) NOT NULL COMMENT '股票名称',
                                        `pre_close_price` decimal(8,2) DEFAULT NULL COMMENT '前收盘价| 昨日收盘价',
                                        `open_price` decimal(8,2) DEFAULT NULL COMMENT '开盘价',
                                        `cur_price` decimal(8,2) NOT NULL COMMENT '当前价格',
                                        `min_price` decimal(8,2) DEFAULT NULL COMMENT '今日最低价',
                                        `max_price` decimal(8,2) DEFAULT NULL COMMENT '今日最高价',
                                        `trade_amount` bigint(20) DEFAULT NULL COMMENT '成交量',
                                        `trade_volume` decimal(18,3) DEFAULT NULL COMMENT '成交总金额(单位元)',
                                        `cur_time` datetime NOT NULL COMMENT '当前时间',
                                        PRIMARY KEY (`id`) USING BTREE,
                                        UNIQUE KEY `unique_code_time` (`cur_time`,`stock_code`) USING BTREE COMMENT '股票编码和日期构成唯一索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='个股详情信息表';

/*Data for the table `stock_rt_info_202411` */

/*Table structure for table `stock_rt_info_202412` */

CREATE TABLE `stock_rt_info_202412` (
                                        `id` bigint(20) NOT NULL COMMENT '主键字段（无业务意义）',
                                        `stock_code` char(6) NOT NULL COMMENT '股票代码',
                                        `stock_name` varchar(20) NOT NULL COMMENT '股票名称',
                                        `pre_close_price` decimal(8,2) DEFAULT NULL COMMENT '前收盘价| 昨日收盘价',
                                        `open_price` decimal(8,2) DEFAULT NULL COMMENT '开盘价',
                                        `cur_price` decimal(8,2) NOT NULL COMMENT '当前价格',
                                        `min_price` decimal(8,2) DEFAULT NULL COMMENT '今日最低价',
                                        `max_price` decimal(8,2) DEFAULT NULL COMMENT '今日最高价',
                                        `trade_amount` bigint(20) DEFAULT NULL COMMENT '成交量',
                                        `trade_volume` decimal(18,3) DEFAULT NULL COMMENT '成交总金额(单位元)',
                                        `cur_time` datetime NOT NULL COMMENT '当前时间',
                                        PRIMARY KEY (`id`) USING BTREE,
                                        UNIQUE KEY `unique_code_time` (`cur_time`,`stock_code`) USING BTREE COMMENT '股票编码和日期构成唯一索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='个股详情信息表';

/*Data for the table `stock_rt_info_202412` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
