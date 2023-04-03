/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1
Source Server Version : 80019
Source Host           : 127.0.0.1:3306
Source Database       : jbos

Target Server Type    : MYSQL
Target Server Version : 80019
File Encoding         : 65001

Date: 2023-04-01 15:32:31
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for comm_id_generator
-- ----------------------------
DROP TABLE IF EXISTS `comm_id_generator`;
CREATE TABLE `comm_id_generator` (
  `id` int NOT NULL,
  `max_id` bigint NOT NULL COMMENT '当前最大id',
  `step` int NOT NULL COMMENT '号段的步长',
  `biz_type` int NOT NULL COMMENT '业务类型',
  `version` int NOT NULL COMMENT '版本号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of comm_id_generator
-- ----------------------------
INSERT INTO `comm_id_generator` VALUES ('1', '200000', '10000', '100', '20');

-- ----------------------------
-- Table structure for comm_pic_repo
-- ----------------------------
DROP TABLE IF EXISTS `comm_pic_repo`;
CREATE TABLE `comm_pic_repo` (
  `seq_id` varchar(128) NOT NULL COMMENT '序列号',
  `file_repo` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '文件目录',
  `file_name` varchar(128) DEFAULT NULL COMMENT '文件名称',
  `file_as_name` varchar(32) DEFAULT NULL COMMENT '文件别名',
  PRIMARY KEY (`seq_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='图片存储表';

-- ----------------------------
-- Records of comm_pic_repo
-- ----------------------------
INSERT INTO `comm_pic_repo` VALUES ('141a69c1-1171-4941-a97b-b748340da4c4', '/nas/upload', '9.jpg', '1677032760944.jpeg');
INSERT INTO `comm_pic_repo` VALUES ('6ec911ca-3f52-4973-bad6-d2c6567209e3', '/nas/upload', '5.jpg', '1677032754886.jpeg');
INSERT INTO `comm_pic_repo` VALUES ('73fa821d-fc04-4917-ae68-05af858d3657', '/nas/upload', 'O1CN01Ldl1s61bYyCf2oVCU_!!20003478-0-picasso.jpg', '1677032749170.jpeg');
INSERT INTO `comm_pic_repo` VALUES ('781bbbf6-9125-4236-978d-bad2fb7e8b29', '/nas/upload', '2.jpg', '1647831995267.jpeg');
INSERT INTO `comm_pic_repo` VALUES ('7f764bb5-53c4-4f80-9b09-51431473357d', '/nas/upload', '1.jpg', '1640243169339.jpeg');
INSERT INTO `comm_pic_repo` VALUES ('afae050b-dea4-43d1-803d-28eb2a1db45e', '/nas/upload', '11.jpg', '1677032765309.jpeg');
INSERT INTO `comm_pic_repo` VALUES ('bcf09c87-d98c-4bb1-8a97-66cf3bc5272f', '/nas/upload', '2.jpg', '1640243172541.jpeg');
INSERT INTO `comm_pic_repo` VALUES ('c944580a-85df-4dfa-8361-a03e3aac42d1', '/nas/upload', '3.jpg', '1640243175839.jpeg');
INSERT INTO `comm_pic_repo` VALUES ('d67d2d18-8baf-4043-a1d3-6f67f6d899b4', '/nas/upload', '2.jpg', '1677032777023.jpeg');

-- ----------------------------
-- Table structure for comm_region
-- ----------------------------
DROP TABLE IF EXISTS `comm_region`;
CREATE TABLE `comm_region` (
  `rgon_code` varchar(32) DEFAULT NULL COMMENT '地区编码',
  `rgon_nm` varchar(32) DEFAULT NULL COMMENT '地区名称',
  `city_nm` varchar(32) DEFAULT NULL COMMENT '所属地区',
  `prov_nm` varchar(32) DEFAULT NULL COMMENT '所属省份',
  `level` varchar(32) DEFAULT NULL COMMENT '级别',
  `is_valid` int DEFAULT NULL COMMENT '是否有效(1:有效;0:无效)',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='地区信息表';

-- ----------------------------
-- Records of comm_region
-- ----------------------------

-- ----------------------------
-- Table structure for im_material_buy
-- ----------------------------
DROP TABLE IF EXISTS `im_material_buy`;
CREATE TABLE `im_material_buy` (
  `ID` varchar(64) NOT NULL COMMENT 'ID',
  `BIZNO` varchar(64) DEFAULT NULL COMMENT '业务编号',
  `INSTID` varchar(64) DEFAULT NULL COMMENT '实例ID',
  `APPLYUSERID` varchar(64) DEFAULT NULL COMMENT '申请人',
  `APPLYDEPID` varchar(64) DEFAULT NULL COMMENT '申请部门',
  `FEETYPE` varchar(16) DEFAULT NULL COMMENT '费用类型',
  `APPLYTIME` timestamp NULL DEFAULT NULL COMMENT '申请时间',
  `TOTALAMT` decimal(19,4) DEFAULT NULL COMMENT '总金额',
  `GMOTIME` timestamp NULL DEFAULT NULL COMMENT '总办会议时间',
  `PURPOSE` varchar(512) DEFAULT NULL COMMENT '采购用途',
  `BIZSTATE` varchar(16) DEFAULT NULL COMMENT '状态',
  `ISVALID` int DEFAULT '1' COMMENT '是否有效(1：是；0：否)',
  `CREATEUSERID` varchar(64) DEFAULT NULL COMMENT '创建用户',
  `CREATETIME` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `UPDATEUSERID` varchar(64) DEFAULT NULL COMMENT '更新用户',
  `UPDATETIME` varchar(20) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='物品采购表';

-- ----------------------------
-- Records of im_material_buy
-- ----------------------------
INSERT INTO `im_material_buy` VALUES ('65b6bf79-c115-49a7-a84f-56bd6d6babe6', 'BIZ_BUY_20211026150915', '09434f45-3c7c-11ec-9ddd-005056c00001', 'k0091', '2', null, '2021-10-26 00:00:00', '100.0000', '2021-10-26 00:00:00', 'A4纸2箱', '20', '1', null, null, null, null);
INSERT INTO `im_material_buy` VALUES ('851c3bba-0c4a-43fe-b4fc-c2a2fa208280', 'BIZ_BUY_20211027182902', '0eef90cc-3713-11ec-8fbf-005056c00001', 'k0091', '2', null, '2021-10-27 00:00:00', '200.0000', '2021-10-27 00:00:00', '茶叶一盒', '20', '1', null, null, null, null);

-- ----------------------------
-- Table structure for im_material_info
-- ----------------------------
DROP TABLE IF EXISTS `im_material_info`;
CREATE TABLE `im_material_info` (
  `ID` varchar(64) NOT NULL COMMENT 'ID',
  `MATERIAL_NO` varchar(32) DEFAULT NULL COMMENT '物品编号',
  `MATERIAL_NAME` varchar(64) DEFAULT NULL COMMENT '物品名称',
  `PARENTID` varchar(64) DEFAULT NULL COMMENT '上级ID',
  `MATERIAL_DESC` varchar(512) DEFAULT NULL COMMENT '物品描述',
  `ISVALID` int DEFAULT '1' COMMENT '是否有效(1：是；0：否)',
  `CREATEUSERID` varchar(64) DEFAULT NULL COMMENT '创建用户',
  `CREATETIME` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `UPDATEUSERID` varchar(64) DEFAULT NULL COMMENT '更新用户',
  `UPDATETIME` varchar(20) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='物品信息表';

-- ----------------------------
-- Records of im_material_info
-- ----------------------------

-- ----------------------------
-- Table structure for im_material_list
-- ----------------------------
DROP TABLE IF EXISTS `im_material_list`;
CREATE TABLE `im_material_list` (
  `ID` varchar(64) NOT NULL COMMENT 'ID',
  `BIZID` varchar(64) DEFAULT NULL COMMENT '业务ID',
  `BIZNO` varchar(64) DEFAULT NULL COMMENT '业务编号',
  `BIZTYPE` varchar(64) DEFAULT NULL COMMENT '业务类型',
  `MATERIALNAME` varchar(64) DEFAULT NULL COMMENT '物品名称',
  `AMOUNT` decimal(8,2) DEFAULT NULL COMMENT '数量',
  `PRICE` decimal(19,4) DEFAULT NULL COMMENT '单价',
  `ISVALID` int DEFAULT '1' COMMENT '是否有效(1：是；0：否)',
  `CREATEUSERID` varchar(64) DEFAULT NULL COMMENT '创建用户',
  `CREATETIME` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `UPDATEUSERID` varchar(64) DEFAULT NULL COMMENT '更新用户',
  `UPDATETIME` varchar(20) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='物品清单表';

-- ----------------------------
-- Records of im_material_list
-- ----------------------------
INSERT INTO `im_material_list` VALUES ('1401c8ed-a720-49c1-b11e-55ffbee2b799', '65b6bf79-c115-49a7-a84f-56bd6d6babe6', null, 'BUY', 'A4纸', '2.00', '50.0000', '1', null, null, null, null);
INSERT INTO `im_material_list` VALUES ('35169ff5-3481-43df-9f26-cb75e492f3a5', '851c3bba-0c4a-43fe-b4fc-c2a2fa208280', null, 'BUY', '茶叶', '1.00', '200.0000', '1', null, null, null, null);

-- ----------------------------
-- Table structure for jbos_captcha
-- ----------------------------
DROP TABLE IF EXISTS `jbos_captcha`;
CREATE TABLE `jbos_captcha` (
  `TOKEN` varchar(64) NOT NULL,
  `TEXT` varchar(16) DEFAULT NULL,
  PRIMARY KEY (`TOKEN`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='验证码表';

-- ----------------------------
-- Records of jbos_captcha
-- ----------------------------
INSERT INTO `jbos_captcha` VALUES ('0008fb3e-7161-4666-a9c9-cb7d92b75670', '852de');
INSERT INTO `jbos_captcha` VALUES ('003a889e-61b3-47df-a9af-702c15eb1e6c', 'c7a8n');
INSERT INTO `jbos_captcha` VALUES ('007635f0-d032-4425-9eca-4afb3905ada1', 'wbcdp');
INSERT INTO `jbos_captcha` VALUES ('011b0a7a-aa1a-4878-8479-e5e486cc86ec', '2m5wm');
INSERT INTO `jbos_captcha` VALUES ('01368f65-4579-4409-917b-43df659959f9', 'dd5ww');
INSERT INTO `jbos_captcha` VALUES ('0151fd1d-d8b9-4507-92e7-7867fdf78379', 'pg2bm');
INSERT INTO `jbos_captcha` VALUES ('016cc026-be03-491e-a35c-b763caa30057', 'f6day');
INSERT INTO `jbos_captcha` VALUES ('019ad1b9-9001-4788-89ca-773bd65306fb', 'n7x7x');
INSERT INTO `jbos_captcha` VALUES ('0249fe10-57e4-44c3-8d79-a971faba6fe1', '2ngmd');
INSERT INTO `jbos_captcha` VALUES ('026eb1c4-e0ae-4d18-ab9f-2035d3490c1f', 'wncwn');
INSERT INTO `jbos_captcha` VALUES ('02e8126e-500e-46ee-aea6-29f12c184250', 'mc8pf');
INSERT INTO `jbos_captcha` VALUES ('030f8147-3deb-4eb7-ad74-c0d4bcf9d69d', '6ne7n');
INSERT INTO `jbos_captcha` VALUES ('034530d5-69fc-4f4f-8cb2-58944ea1fa5a', '2p5x5');
INSERT INTO `jbos_captcha` VALUES ('03465126-e78f-49c0-bb5e-25411879012c', 'd7xcw');
INSERT INTO `jbos_captcha` VALUES ('0350410c-2ffe-479d-b0a6-c72a72cb29b5', '27222');
INSERT INTO `jbos_captcha` VALUES ('039d5099-ce1e-4b44-8d8b-eb949c564527', 'gbe28');
INSERT INTO `jbos_captcha` VALUES ('03cfef78-57fa-486c-9b2d-d8081695e09f', 'n6wb8');
INSERT INTO `jbos_captcha` VALUES ('04cf0486-949f-41a0-90c5-2cd8bb6640ff', 'dg2d5');
INSERT INTO `jbos_captcha` VALUES ('051c1551-96cb-4327-9b64-1f28e7f5aa54', 'gamf3');
INSERT INTO `jbos_captcha` VALUES ('056989b3-3ccd-4fe9-afe9-a827c4159ae0', '4a38n');
INSERT INTO `jbos_captcha` VALUES ('0608a4ab-665b-495d-a56a-4921c3481321', 'gygey');
INSERT INTO `jbos_captcha` VALUES ('06be663e-82c8-44b7-bc88-23618bee5df2', 'bgmxa');
INSERT INTO `jbos_captcha` VALUES ('07955cb2-6e5e-40f6-87d8-1f142600ef7c', 'ndne4');
INSERT INTO `jbos_captcha` VALUES ('07afc17e-669f-4406-b8c5-4bf25ed8f1c8', '7nyma');
INSERT INTO `jbos_captcha` VALUES ('08273abe-cd52-4b0f-8b7c-f13d2445defd', 'g5dwx');
INSERT INTO `jbos_captcha` VALUES ('087e595c-1b60-4006-94e2-afafd5ba180c', 'bdn24');
INSERT INTO `jbos_captcha` VALUES ('094c6a03-411a-4ced-8f3b-23a4aeb7a5dc', '2cgym');
INSERT INTO `jbos_captcha` VALUES ('096ae358-4a65-423d-8bec-cb1ff9c39123', 'eemce');
INSERT INTO `jbos_captcha` VALUES ('09979e15-6d74-4f68-ae73-4f0ddbd51875', '8c6g2');
INSERT INTO `jbos_captcha` VALUES ('0a2f17fd-263c-4469-ab3d-942a19b716f3', '5yw2d');
INSERT INTO `jbos_captcha` VALUES ('0aa384cd-0efa-4795-83b0-65263359326f', 'xgmwy');
INSERT INTO `jbos_captcha` VALUES ('0b28bf9d-fb25-4b27-bb9e-2c3cd6396ca7', 'gec24');
INSERT INTO `jbos_captcha` VALUES ('0c1caec0-99fa-4099-aefa-19ace617ebe8', 'fd2c8');
INSERT INTO `jbos_captcha` VALUES ('0cf15c14-1540-47c4-8cff-5371eefa2b4d', '7d562');
INSERT INTO `jbos_captcha` VALUES ('0d16d348-e8aa-490d-8675-1458e81ca0e2', '275yx');
INSERT INTO `jbos_captcha` VALUES ('0e5e8993-5f6f-402e-ade9-2c1f4146e74e', '88p66');
INSERT INTO `jbos_captcha` VALUES ('0e651380-f6fa-4ff2-89b8-63c9d37ce099', '3e4wp');
INSERT INTO `jbos_captcha` VALUES ('0ecfadbd-bceb-493a-a825-e5eca0241da6', '752cy');
INSERT INTO `jbos_captcha` VALUES ('0f527fc3-814f-4df6-bef5-3fae1a5230ab', '4mbnm');
INSERT INTO `jbos_captcha` VALUES ('0fac91a2-fe8e-41a7-9b85-e05052703964', '8nn4n');
INSERT INTO `jbos_captcha` VALUES ('0fb48e84-2789-4e97-92dc-d34fbc07f0b2', 'fp6f5');
INSERT INTO `jbos_captcha` VALUES ('0fdbb365-d1bc-4aa1-a2b9-07e089377d12', 'p7wxx');
INSERT INTO `jbos_captcha` VALUES ('10ba757e-3752-4d5f-a493-790449b3db7d', '2xxfm');
INSERT INTO `jbos_captcha` VALUES ('10ce0c08-06d5-4dea-b42d-542d671913f9', 'xf5x7');
INSERT INTO `jbos_captcha` VALUES ('10e5a7a0-adb9-4d1e-a5e6-c51c8d9c8912', 'agy6x');
INSERT INTO `jbos_captcha` VALUES ('1174e60e-ab09-4100-974b-e5563e6f29ac', 'yf7ff');
INSERT INTO `jbos_captcha` VALUES ('12083ecf-2e15-4e2c-a3ea-88805cd1add6', 'w57mw');
INSERT INTO `jbos_captcha` VALUES ('12e83cd7-30a9-4f2f-8e31-47d3a2afc6c1', '2a3ba');
INSERT INTO `jbos_captcha` VALUES ('135465e7-0d54-4108-8794-406b6471ff2c', 'beegc');
INSERT INTO `jbos_captcha` VALUES ('13be0c34-026f-44eb-a9e7-aa05e6619d5a', '88a7a');
INSERT INTO `jbos_captcha` VALUES ('14967aa3-d489-4e95-beb8-77ba1293bd26', '33x6b');
INSERT INTO `jbos_captcha` VALUES ('14c51add-11e1-47fd-9ed6-74fa8c138a1a', 'ag68b');
INSERT INTO `jbos_captcha` VALUES ('14ce67a3-9840-4841-b346-dc34ff7f477a', '5fbga');
INSERT INTO `jbos_captcha` VALUES ('151a8f5d-9006-4633-96e2-5a8d1376f5c9', 'pp7wc');
INSERT INTO `jbos_captcha` VALUES ('153ed233-ffa7-488b-97d5-4ed6686881dd', 'nb4ff');
INSERT INTO `jbos_captcha` VALUES ('154d21ee-2cde-4641-acdb-161da7a862bf', 'nedaa');
INSERT INTO `jbos_captcha` VALUES ('15a1881f-3dbc-4a7a-8cff-15015fe96719', 'nwcnn');
INSERT INTO `jbos_captcha` VALUES ('16e1639d-d8ad-40cf-84ec-ae00867e5c1c', 'pp8n4');
INSERT INTO `jbos_captcha` VALUES ('1747fc4e-4d9a-41b2-a6da-4dd3e57fa567', 'an8ge');
INSERT INTO `jbos_captcha` VALUES ('17e2488a-cbdc-4317-b9b1-a70942d578ed', 'm6g8c');
INSERT INTO `jbos_captcha` VALUES ('1807474d-9d23-4a63-97fd-73b5d92bcff3', 'png6e');
INSERT INTO `jbos_captcha` VALUES ('18325663-0272-4686-8adb-46282236217f', 'yg5nd');
INSERT INTO `jbos_captcha` VALUES ('1945463d-4ebc-44c0-9a24-01fec90d537a', 'wa7dp');
INSERT INTO `jbos_captcha` VALUES ('196e23fc-fc16-4f42-86ad-9a5aef168871', '6fcnx');
INSERT INTO `jbos_captcha` VALUES ('19713901-602b-4e37-86bd-1858869db664', 'g64bg');
INSERT INTO `jbos_captcha` VALUES ('19bb2fcf-6acc-4b54-a6e9-42c90b95609e', 'ycncy');
INSERT INTO `jbos_captcha` VALUES ('1ab88fd6-28bd-4c75-8241-6dccb7417f72', '8mp5y');
INSERT INTO `jbos_captcha` VALUES ('1b2c3e8c-d130-4136-80e0-2a313ae5b5e7', '6ff3b');
INSERT INTO `jbos_captcha` VALUES ('1b6932d0-9fc4-4f4f-8290-f93a0df647b9', 'mfapn');
INSERT INTO `jbos_captcha` VALUES ('1b7186f2-9a12-4b0b-8adc-65ce93826296', 'yyanp');
INSERT INTO `jbos_captcha` VALUES ('1be53442-eff8-4801-af46-d15f474c9d2c', '3nbcn');
INSERT INTO `jbos_captcha` VALUES ('1ced158d-9541-4b81-82bb-51d7a64c776f', 'nm6pa');
INSERT INTO `jbos_captcha` VALUES ('1db48027-8eb0-4c18-b8da-5d9a9a77ca09', '8aw3w');
INSERT INTO `jbos_captcha` VALUES ('1db8ac67-7ef3-4c4f-9391-ab3d1a746b71', 'n4w64');
INSERT INTO `jbos_captcha` VALUES ('1dc42283-8255-4673-af48-debf552b26e4', 'we2yc');
INSERT INTO `jbos_captcha` VALUES ('1dd853c7-d679-4f46-aeee-e23f8d7e1a27', '2my4x');
INSERT INTO `jbos_captcha` VALUES ('1eb74786-68ce-4b6a-82c4-713c326fcf8b', '744pg');
INSERT INTO `jbos_captcha` VALUES ('1eeefbf0-d5be-4734-8274-efb56207d79f', 'f6nx8');
INSERT INTO `jbos_captcha` VALUES ('1efbaf61-f7ad-4d56-a91d-1000947e34b2', 'bnd7n');
INSERT INTO `jbos_captcha` VALUES ('1f3f15ab-c016-4bd2-b73b-3462be50a497', 'dwcx7');
INSERT INTO `jbos_captcha` VALUES ('1f83eb7d-36d9-4026-8414-122f1759afed', '64bwn');
INSERT INTO `jbos_captcha` VALUES ('1f8f84a5-ded8-4084-82cf-d62ca2c31459', 'yyayc');
INSERT INTO `jbos_captcha` VALUES ('1fcd9bbb-a155-4735-acb8-78bf5a958456', '25ee8');
INSERT INTO `jbos_captcha` VALUES ('207b8325-7ad7-4a97-989e-7c63937c576b', 'm3f24');
INSERT INTO `jbos_captcha` VALUES ('208baa31-9ed1-4965-a183-fd801c26a28c', '2f7x2');
INSERT INTO `jbos_captcha` VALUES ('20b5a1a6-4a94-4e53-801d-933263db80f1', '5wefy');
INSERT INTO `jbos_captcha` VALUES ('20d4c3ad-ff67-4d57-9c7a-70e75f5a07d3', '4cmeb');
INSERT INTO `jbos_captcha` VALUES ('20e9b51d-e4da-4293-bd4a-336bf6adbe28', '8n4dp');
INSERT INTO `jbos_captcha` VALUES ('2133f327-9bb9-42ae-8325-a014694693ea', '67a5d');
INSERT INTO `jbos_captcha` VALUES ('21424b7f-af62-45f6-8d36-cc144e07f100', 'n6ebw');
INSERT INTO `jbos_captcha` VALUES ('21541d3b-1cab-46d1-8999-f261ad4dfe0c', 'ndg2b');
INSERT INTO `jbos_captcha` VALUES ('219964ba-f783-44c7-b723-2c748b79095d', 'c56wg');
INSERT INTO `jbos_captcha` VALUES ('21ea6ce0-0fa4-4524-b200-24c24234fc3b', 'g6857');
INSERT INTO `jbos_captcha` VALUES ('224f4e69-451f-43df-9ef0-a5f4b6820103', 'nmb8n');
INSERT INTO `jbos_captcha` VALUES ('228f9551-2397-4636-9634-200aef327506', 'f383n');
INSERT INTO `jbos_captcha` VALUES ('22c7ef7d-c105-4244-8fe5-37c9885757be', 'wn8xd');
INSERT INTO `jbos_captcha` VALUES ('22f4b43a-e7c7-4558-bf79-bfc74c315805', 'cxya7');
INSERT INTO `jbos_captcha` VALUES ('244f5d3d-fe1a-44bd-aa70-be93fd94865e', 'fynmg');
INSERT INTO `jbos_captcha` VALUES ('24af64d9-921e-4536-8255-0e24eec22f05', 'fmea7');
INSERT INTO `jbos_captcha` VALUES ('26007e8e-d145-4994-8d0f-9c4d715c217d', 'enwma');
INSERT INTO `jbos_captcha` VALUES ('26975ffd-ef58-43fb-8804-2e72f28a992c', '28b4g');
INSERT INTO `jbos_captcha` VALUES ('26c4e4b6-6881-4dbc-b0e3-5630fb41c039', 'febya');
INSERT INTO `jbos_captcha` VALUES ('26c9c928-647a-4d08-ae4e-04ac9505257e', 'bcxde');
INSERT INTO `jbos_captcha` VALUES ('2703e16f-3218-4ba8-9943-d42690f91c3f', '3ddmy');
INSERT INTO `jbos_captcha` VALUES ('270508af-dfe3-4a0d-a179-4f638669246f', '7ecg8');
INSERT INTO `jbos_captcha` VALUES ('27738329-8993-4116-977c-f5bc3f9c37c5', '4573e');
INSERT INTO `jbos_captcha` VALUES ('27eb6578-6e65-4760-9775-3823ae5c8af5', '88ad3');
INSERT INTO `jbos_captcha` VALUES ('283b9342-3581-4beb-a48c-36ca91c664c0', 'dw37a');
INSERT INTO `jbos_captcha` VALUES ('2862d568-d4d8-427e-a45f-f6f6aeb9ec7a', 'c8pf6');
INSERT INTO `jbos_captcha` VALUES ('2867bc22-eb63-4a78-8cf5-77241f1e8365', 'xcy2n');
INSERT INTO `jbos_captcha` VALUES ('28768069-ced9-4069-a9b2-394ad61dc1c9', '84c6a');
INSERT INTO `jbos_captcha` VALUES ('28d05558-eea3-4993-912b-c864508164e8', '2y55c');
INSERT INTO `jbos_captcha` VALUES ('2931ebd9-f789-4999-9631-7a904db7d966', '5a43f');
INSERT INTO `jbos_captcha` VALUES ('2974ee68-54f2-4f44-b74e-f6292c9e9b65', '8bg3n');
INSERT INTO `jbos_captcha` VALUES ('299d858c-99c8-46fd-ad58-46e9e71a7517', 'wwg76');
INSERT INTO `jbos_captcha` VALUES ('29ad6ef6-90f3-4aeb-9068-0099dc9bbe07', '24y2n');
INSERT INTO `jbos_captcha` VALUES ('2b1f967e-925b-4425-bcb1-368f26fb1c91', '8xp5f');
INSERT INTO `jbos_captcha` VALUES ('2b8e4dfb-d7a6-4f23-8401-f13b062515e0', 'dd4d5');
INSERT INTO `jbos_captcha` VALUES ('2bb97495-42d5-44f5-a78a-232f4fc975e6', '55pgx');
INSERT INTO `jbos_captcha` VALUES ('2c21440b-6605-412d-ae50-596fc09feed6', '76gnp');
INSERT INTO `jbos_captcha` VALUES ('2c8e810a-1c79-4aa7-a24e-8d9aceb94584', 'wefgc');
INSERT INTO `jbos_captcha` VALUES ('2d0ed2be-7283-49fc-9123-2dc6efc8183c', '8by5f');
INSERT INTO `jbos_captcha` VALUES ('2d21757e-b08d-4b52-976b-7701a73793ad', '7x3fm');
INSERT INTO `jbos_captcha` VALUES ('2de51793-965e-4640-837f-56a2d1d97da3', 'n23na');
INSERT INTO `jbos_captcha` VALUES ('2e8398b7-ab2b-49d1-bad2-1a28b0df203c', 'b6xcx');
INSERT INTO `jbos_captcha` VALUES ('2e8f59a6-d875-4fb7-adef-c31b63c5441f', '7ef4a');
INSERT INTO `jbos_captcha` VALUES ('2ecb83b6-ca92-4d70-a5db-195690dfa56b', 'ebf7a');
INSERT INTO `jbos_captcha` VALUES ('2f2f689d-a477-4a85-bc90-b431a0cd0e65', 'gc84w');
INSERT INTO `jbos_captcha` VALUES ('2ff5c114-b471-4463-8237-360abe3ddca5', 'dnwbx');
INSERT INTO `jbos_captcha` VALUES ('301b020f-83c1-4903-a825-6598c833ef9e', 'p6m3y');
INSERT INTO `jbos_captcha` VALUES ('30aa0ff8-8132-48ab-90e2-840724c1b564', 'yd26g');
INSERT INTO `jbos_captcha` VALUES ('31067534-b0c7-4e53-9175-7fb7e77ddb7d', 'y4p84');
INSERT INTO `jbos_captcha` VALUES ('31ab1c38-340e-4c07-a24c-ea2096a3dcbb', '7m27f');
INSERT INTO `jbos_captcha` VALUES ('32531613-7d55-41aa-aba2-e25a9f0f89fc', '6wy5y');
INSERT INTO `jbos_captcha` VALUES ('3292f4d9-2f73-4841-8a91-77e1e94c2eb8', 'padc7');
INSERT INTO `jbos_captcha` VALUES ('32e49400-7427-4171-a7e6-4d4a7dbd7263', '2a4an');
INSERT INTO `jbos_captcha` VALUES ('32e6912e-03f6-4687-9588-30116a19d974', 'wn43e');
INSERT INTO `jbos_captcha` VALUES ('343dcdb8-e969-4530-89f1-99c149b95e8b', 'weyxd');
INSERT INTO `jbos_captcha` VALUES ('3484e668-5335-46c1-abf0-b8c0556e0f13', '8ygcx');
INSERT INTO `jbos_captcha` VALUES ('34ba4a0a-35fc-40ef-8a2e-babd7cb75368', 'wc6n4');
INSERT INTO `jbos_captcha` VALUES ('35ae810d-3fe0-46be-a47e-5133fc6d76c5', '2fwdf');
INSERT INTO `jbos_captcha` VALUES ('366e0652-55f4-4b74-9c25-11245904165e', 'edenc');
INSERT INTO `jbos_captcha` VALUES ('36a8b7fa-f662-4e85-be99-8e43aa454c85', 'wwgmn');
INSERT INTO `jbos_captcha` VALUES ('37911d9f-7444-4cbe-844b-16dd7bd4eed6', '4e3n7');
INSERT INTO `jbos_captcha` VALUES ('37cdb6be-de24-41b8-8409-b1ef73f40845', 'nafe4');
INSERT INTO `jbos_captcha` VALUES ('37d182ab-2b84-459b-ae93-c9bba567c91e', '35epa');
INSERT INTO `jbos_captcha` VALUES ('37d78db6-5029-4a95-8a02-3a22771c5ed7', 'y4d52');
INSERT INTO `jbos_captcha` VALUES ('37eecd47-2e5d-423b-af0f-a853c8a7d51c', 'nmeng');
INSERT INTO `jbos_captcha` VALUES ('37fb9d07-da56-478a-ad71-a9c1ac134960', 'm7n68');
INSERT INTO `jbos_captcha` VALUES ('38b861e3-e4f0-44e7-9442-efb4e34cf20d', '4cf7e');
INSERT INTO `jbos_captcha` VALUES ('38fde098-4d78-4590-a62b-a3fb950e1cfe', 'b4mf8');
INSERT INTO `jbos_captcha` VALUES ('39fc8db4-2ed8-45a9-848b-b471f00dffec', '6fbb2');
INSERT INTO `jbos_captcha` VALUES ('3a18d9c4-31a0-4964-8416-697915b749de', 'e26fb');
INSERT INTO `jbos_captcha` VALUES ('3a20e3cf-e77e-4d14-8417-6ff7c8e67714', 'bc3n3');
INSERT INTO `jbos_captcha` VALUES ('3a349d2c-f211-4203-9494-76b175cee5c0', '5gmnb');
INSERT INTO `jbos_captcha` VALUES ('3b7c1dc2-7aa1-4c0d-868e-3e41a3c2ca34', '2cc6p');
INSERT INTO `jbos_captcha` VALUES ('3bc21b44-c6cc-47a8-b0c1-1d81f40660a6', '4d7a5');
INSERT INTO `jbos_captcha` VALUES ('3c486447-af7b-4f27-b5c4-6decf670047c', 'gc4ae');
INSERT INTO `jbos_captcha` VALUES ('3d11a27f-6736-40c1-96e2-1c0af1adaee0', '5b6xn');
INSERT INTO `jbos_captcha` VALUES ('3f0ec61b-8588-493f-9b08-8877cfced6d6', 'cf4gn');
INSERT INTO `jbos_captcha` VALUES ('3f4cdafb-2f03-4cf1-9dbf-4a31b01dcbe5', 'mfnye');
INSERT INTO `jbos_captcha` VALUES ('3ffcea4a-c2fc-4c51-8b66-a6eb795d5e21', 'mpcxw');
INSERT INTO `jbos_captcha` VALUES ('4067b1ec-42c4-465a-b7ea-b59f688db1a5', '8dn5f');
INSERT INTO `jbos_captcha` VALUES ('40859924-9053-41a7-b4e6-0dc2bd3326b2', '6y45c');
INSERT INTO `jbos_captcha` VALUES ('416beea4-f23f-4640-87e7-3a0a0f19df0c', 'nx2ed');
INSERT INTO `jbos_captcha` VALUES ('42882f08-c0b8-43c9-acc2-7cbf4c1e7241', 'nnw7x');
INSERT INTO `jbos_captcha` VALUES ('42c84031-a248-492c-b223-78072ed1dafb', 'a66w6');
INSERT INTO `jbos_captcha` VALUES ('43f1e9ea-4ede-4d70-94e3-7fb0ee060e31', 'ep4bx');
INSERT INTO `jbos_captcha` VALUES ('4419cd7a-935c-4589-8dbe-5de948735990', '724mx');
INSERT INTO `jbos_captcha` VALUES ('44ab34d9-c40e-4310-b5a9-0b2ad7844930', 'm6xfe');
INSERT INTO `jbos_captcha` VALUES ('450b1d6e-260e-492f-acf6-0032ccd3ac9e', '8755c');
INSERT INTO `jbos_captcha` VALUES ('452c6ce1-170b-4c09-a11c-837c63a6f23c', 'edpda');
INSERT INTO `jbos_captcha` VALUES ('45d095d8-2eb1-4a4f-9541-9fcc9089f836', 'gc6xx');
INSERT INTO `jbos_captcha` VALUES ('45e593a8-576a-472b-ac8c-a11acd655ece', 'cn87p');
INSERT INTO `jbos_captcha` VALUES ('4656aedf-d2af-413c-b9e5-1d8ef409426c', 'my56y');
INSERT INTO `jbos_captcha` VALUES ('4687d1e0-17f8-48f6-9f08-b270a890ae91', 'wnn32');
INSERT INTO `jbos_captcha` VALUES ('46d8a67e-55a9-4777-b9ec-d9d9a32aaea5', '32wgc');
INSERT INTO `jbos_captcha` VALUES ('4701971a-7003-44f9-b80f-5b086f460ac5', 'fab3p');
INSERT INTO `jbos_captcha` VALUES ('47d69f24-486d-48b9-af48-01439d355e85', '8g485');
INSERT INTO `jbos_captcha` VALUES ('4803b77c-6326-4480-a068-c756cf52ae17', 'ynefn');
INSERT INTO `jbos_captcha` VALUES ('4830e71c-ec36-4409-b2f8-2170c097e43c', '6na88');
INSERT INTO `jbos_captcha` VALUES ('48473682-2559-4ddd-86db-93727944634b', 'wf4yx');
INSERT INTO `jbos_captcha` VALUES ('48c101c2-c3e1-485f-a31f-2b9c73cfc776', 'w44ny');
INSERT INTO `jbos_captcha` VALUES ('499556e1-6a2f-4b7e-ac9d-398c27e5da52', 'm45w5');
INSERT INTO `jbos_captcha` VALUES ('49ada408-1b2b-42c1-b2d5-ce39823584fd', 'wbecm');
INSERT INTO `jbos_captcha` VALUES ('49bfbbd9-7d8e-4f53-907e-5ce6d260b656', '7n74n');
INSERT INTO `jbos_captcha` VALUES ('49ca5e8d-5bec-45f1-aff9-d238c7c98e55', 'g2b7a');
INSERT INTO `jbos_captcha` VALUES ('4a5e1379-f25c-4727-b614-db7d63e2e729', '2n6w6');
INSERT INTO `jbos_captcha` VALUES ('4a68ef08-c321-4164-b1c8-ddf01ae752a8', 'f5b4n');
INSERT INTO `jbos_captcha` VALUES ('4a756844-11ca-4f6e-9cdb-4c87ec494828', 'pn64d');
INSERT INTO `jbos_captcha` VALUES ('4ae4830f-53a8-4b49-9fc2-d49b55bd0232', 'a62m7');
INSERT INTO `jbos_captcha` VALUES ('4b2afa66-d0ec-42fb-9dc8-12fa0f085547', '7dfnx');
INSERT INTO `jbos_captcha` VALUES ('4b5045a2-77e4-49d0-a66a-3ecfa38d80a9', 'bnfd6');
INSERT INTO `jbos_captcha` VALUES ('4be4d6cd-894b-4358-97c3-e9f60e760dfd', '2cbgf');
INSERT INTO `jbos_captcha` VALUES ('4c1dfdb3-8d57-4298-a532-d667b58017e5', 'c37p4');
INSERT INTO `jbos_captcha` VALUES ('4c417bcb-0ee4-444a-801e-869abd771da4', 'aby3n');
INSERT INTO `jbos_captcha` VALUES ('4c832e22-26b8-4c5b-8d69-4b560523e297', '3ac3m');
INSERT INTO `jbos_captcha` VALUES ('4c896b8c-32d1-408c-8605-817764124718', '2cw2b');
INSERT INTO `jbos_captcha` VALUES ('4d001917-f2db-43a1-bff3-4a15e6987cd3', 'dg863');
INSERT INTO `jbos_captcha` VALUES ('4d428527-16a8-44ed-84bb-f0426f1a7fa0', 'm6p3c');
INSERT INTO `jbos_captcha` VALUES ('4d90415d-c0c9-48a3-85e3-ebd3a5a78429', 'd254c');
INSERT INTO `jbos_captcha` VALUES ('4e52b6bc-6f0a-42c2-9fb8-4226305d00cb', 'xwn2e');
INSERT INTO `jbos_captcha` VALUES ('4f07c622-6977-4d44-bd18-7f840f6abc17', 'x757f');
INSERT INTO `jbos_captcha` VALUES ('4f44d8c7-02e2-44da-9378-9502ace2aa3f', '2a4wy');
INSERT INTO `jbos_captcha` VALUES ('4fcb3409-439c-4f81-a523-13232d96cb38', '6gan3');
INSERT INTO `jbos_captcha` VALUES ('4fd0435b-c1dd-4299-a820-3918521c6d0f', 'cxpfe');
INSERT INTO `jbos_captcha` VALUES ('5024bbd7-bd7e-4858-98d7-9c6798900961', 'f3fw6');
INSERT INTO `jbos_captcha` VALUES ('51413fd5-dc77-43d3-ad79-4d7dd71a1e52', '58mnf');
INSERT INTO `jbos_captcha` VALUES ('519ed0cd-4aa7-41ce-b541-dbb8417f66c1', 'fn3wa');
INSERT INTO `jbos_captcha` VALUES ('51b814ec-da5a-49a3-897a-5ba4d88000f2', '728xb');
INSERT INTO `jbos_captcha` VALUES ('51bd49e9-793c-4e0c-a723-a1dd97bfe6f7', 'p2556');
INSERT INTO `jbos_captcha` VALUES ('51ff0a9b-faa9-4d85-87d8-469251fec2e8', '6f6be');
INSERT INTO `jbos_captcha` VALUES ('52551a30-1726-46d6-b0a1-85d9c7bf1cf3', 'a6bn2');
INSERT INTO `jbos_captcha` VALUES ('52f069eb-c85d-4ded-a5d1-ef79aee3b90a', 'bp3d4');
INSERT INTO `jbos_captcha` VALUES ('532aeff6-4a08-4044-95be-16779ede2529', '62py3');
INSERT INTO `jbos_captcha` VALUES ('56109d18-b6b6-4a78-b1cd-27bead7597e1', 'ce7gb');
INSERT INTO `jbos_captcha` VALUES ('562f0c3a-d7b2-4533-94a7-c243ab1db337', 'c3ex6');
INSERT INTO `jbos_captcha` VALUES ('563e7dea-9921-4538-a850-8b26f8ef236b', '55wbb');
INSERT INTO `jbos_captcha` VALUES ('564cfcbe-7903-4f38-87ac-6caa58489179', 'bmenf');
INSERT INTO `jbos_captcha` VALUES ('5743bc76-d7ad-4fbf-9cfd-84d3d93ebcf0', 'w2dxd');
INSERT INTO `jbos_captcha` VALUES ('579d3d0d-8f70-4a9b-8e06-2df8d89c530f', 'm6fp8');
INSERT INTO `jbos_captcha` VALUES ('5839fb38-fe50-4486-860d-196c24d01967', 'ey8e3');
INSERT INTO `jbos_captcha` VALUES ('584933f8-1b41-4685-9cfa-5bdf50dc65f6', 'gg23n');
INSERT INTO `jbos_captcha` VALUES ('584adb56-dc02-41a9-a4cb-caa584efb0c7', 'ygga4');
INSERT INTO `jbos_captcha` VALUES ('59400ee9-fa85-41a1-8a0c-ed062295c5e3', '6386w');
INSERT INTO `jbos_captcha` VALUES ('59633693-6ff0-4bb1-addf-c0b15824d7fc', '88xea');
INSERT INTO `jbos_captcha` VALUES ('5a7e0af3-ea58-486b-9ec8-8aa2a6cb6aa6', 'c4gcd');
INSERT INTO `jbos_captcha` VALUES ('5aa71fe8-c4a0-4d06-a292-6ceb9ad480ad', 'bdxe6');
INSERT INTO `jbos_captcha` VALUES ('5b44af54-c1ad-4fcd-ab3d-2ec9eea04d54', 'nbdf4');
INSERT INTO `jbos_captcha` VALUES ('5b451a6b-f2b9-44c7-ab70-8e9780ec881d', '24edx');
INSERT INTO `jbos_captcha` VALUES ('5b47a008-254f-48c4-ad89-7b5d056f8dd3', 'dpnnd');
INSERT INTO `jbos_captcha` VALUES ('5b6882b4-73ea-4f6b-9b2e-47fb8c36fed6', 'mfam5');
INSERT INTO `jbos_captcha` VALUES ('5b95dd0e-af20-43a9-93f2-8d6cb9eba8ef', '8n3n6');
INSERT INTO `jbos_captcha` VALUES ('5bcbcd0d-830f-4389-8dff-8a940ad2db18', 'e8n2m');
INSERT INTO `jbos_captcha` VALUES ('5c484932-0322-419c-acce-705611c7c6c6', 'y38n8');
INSERT INTO `jbos_captcha` VALUES ('5c7a84b9-9048-4dda-ac2e-f2e3daf57ec2', 'g2yn6');
INSERT INTO `jbos_captcha` VALUES ('5c908236-c754-4805-8367-077b467a15e8', 'gg7be');
INSERT INTO `jbos_captcha` VALUES ('5ca5eb61-3978-4ac3-b6dd-620f2c68a6b8', 'ya24p');
INSERT INTO `jbos_captcha` VALUES ('5cb83767-0949-4a7e-a927-6f30ae3d8224', 'c2awd');
INSERT INTO `jbos_captcha` VALUES ('5ccc2e61-7706-40d4-bead-e8f4d268844a', 'fg7c7');
INSERT INTO `jbos_captcha` VALUES ('5cf1357b-4881-4744-ac0f-5aeadd465d89', 'nmb3n');
INSERT INTO `jbos_captcha` VALUES ('5cf15e85-7866-4312-b80c-3c2ae56dbc0c', '5gy3m');
INSERT INTO `jbos_captcha` VALUES ('5d0582e6-59ab-45d8-b7e6-e61e8f76704e', '72eg2');
INSERT INTO `jbos_captcha` VALUES ('5d18b9c4-3dd7-4921-8c06-be61e37b59ab', '7naam');
INSERT INTO `jbos_captcha` VALUES ('5d471690-32cb-4366-a938-8ffb7afbb643', '66wmf');
INSERT INTO `jbos_captcha` VALUES ('5e01a75e-b228-4521-a739-48d79fde354a', '66x2w');
INSERT INTO `jbos_captcha` VALUES ('5eedc479-41f5-4d7b-937f-f1726bc49724', 'e5gf4');
INSERT INTO `jbos_captcha` VALUES ('5fe3a385-9597-458d-8cc4-c408b69462ac', 'w3p53');
INSERT INTO `jbos_captcha` VALUES ('602a1297-232a-4f2c-8322-842a232489e9', 'dxged');
INSERT INTO `jbos_captcha` VALUES ('604b556f-34ec-4d0c-a393-24dcbb98ba4b', '2537c');
INSERT INTO `jbos_captcha` VALUES ('60d3e2b2-a0f5-4075-8a70-f3f9a99e461a', '6n6nd');
INSERT INTO `jbos_captcha` VALUES ('60fcee53-cbc0-49be-bb74-c46f7a0adeaa', '52388');
INSERT INTO `jbos_captcha` VALUES ('636c8359-e2f4-48c8-a504-eeb7b5e3ca5e', '24a3a');
INSERT INTO `jbos_captcha` VALUES ('63c0bd10-5d26-4970-b54b-b2b9c26e096d', '4bbbm');
INSERT INTO `jbos_captcha` VALUES ('63f8a490-7aff-429c-aeaf-1871ad18682a', 'ay23n');
INSERT INTO `jbos_captcha` VALUES ('64feb35c-5c0c-49f1-8db1-985ea56caa44', 'mxgnp');
INSERT INTO `jbos_captcha` VALUES ('654b1b1f-2a3b-4826-b670-ac38e609b90c', '22db2');
INSERT INTO `jbos_captcha` VALUES ('65898f61-01cb-4ad8-9544-927acd6602bc', 'ax6np');
INSERT INTO `jbos_captcha` VALUES ('65acbdb8-3e1c-4090-9b9e-3f8279cde3fb', 'wnaxp');
INSERT INTO `jbos_captcha` VALUES ('65fd95ce-eda7-41a5-9222-b9daa320f437', 'n2fna');
INSERT INTO `jbos_captcha` VALUES ('666c10f0-6773-4f02-b0f6-6565a7f4ab20', '4wm2w');
INSERT INTO `jbos_captcha` VALUES ('6702ac9f-f28a-453c-be8a-a5e4cde6820c', 'd5mdg');
INSERT INTO `jbos_captcha` VALUES ('671e7f5d-3149-41b8-9800-70bce54a24c6', 'nf6fy');
INSERT INTO `jbos_captcha` VALUES ('67221548-904c-457c-a0c4-b332a6f27014', 'n8578');
INSERT INTO `jbos_captcha` VALUES ('6860e8ad-9946-489d-a64d-d9bd17bdeaf5', 'gewma');
INSERT INTO `jbos_captcha` VALUES ('692fac28-505a-4640-8502-9489e6cfdbfc', '8de7g');
INSERT INTO `jbos_captcha` VALUES ('69cd738f-50bd-4fa9-a252-351d01a69f73', '27bff');
INSERT INTO `jbos_captcha` VALUES ('6a2d9873-e634-4b2c-8289-1f2453da50ae', 'nmm33');
INSERT INTO `jbos_captcha` VALUES ('6ab81fc2-405b-4abe-915b-e55597969800', '5fgwa');
INSERT INTO `jbos_captcha` VALUES ('6bb17542-2bac-430f-9ff8-58c686d01cda', '5634g');
INSERT INTO `jbos_captcha` VALUES ('6be2d758-0000-4f0f-99be-52670583461b', 'n8cnb');
INSERT INTO `jbos_captcha` VALUES ('6be4cee4-4651-4d6e-8dc8-4083f6e48232', 'fgnd3');
INSERT INTO `jbos_captcha` VALUES ('6be949a5-7f41-436b-b475-d7cc3517b3f5', '6ae3g');
INSERT INTO `jbos_captcha` VALUES ('6c309866-9191-4ae7-8068-ba6b23ac057a', 'ndp34');
INSERT INTO `jbos_captcha` VALUES ('6c5af481-e019-429a-a22d-333e5a53f13a', 'd225e');
INSERT INTO `jbos_captcha` VALUES ('6cd5c85b-de9d-4fde-a2b6-570373c1315e', 'wffbw');
INSERT INTO `jbos_captcha` VALUES ('6ce1f524-d7aa-4900-90ac-e207831d488f', 'xd4yg');
INSERT INTO `jbos_captcha` VALUES ('6ceb0ab5-3dba-465a-906e-cd055046360e', 'cnn3x');
INSERT INTO `jbos_captcha` VALUES ('6cf9af96-862a-4891-aa0d-b6866f15e946', '65wag');
INSERT INTO `jbos_captcha` VALUES ('6d432a88-1828-4b1a-b988-7f4480a91901', 'edyaf');
INSERT INTO `jbos_captcha` VALUES ('6da24f19-538d-48e9-ada3-c0ca07f46389', '4y24d');
INSERT INTO `jbos_captcha` VALUES ('6f4a91ef-6f49-4b1b-96e4-063cd1438907', 'gxedm');
INSERT INTO `jbos_captcha` VALUES ('6f54b6fa-88ea-44c3-89f4-e848cb5ee02e', 'c6dcw');
INSERT INTO `jbos_captcha` VALUES ('708d372c-d0ed-4a5d-b311-81ca5dde3f43', '7xbf7');
INSERT INTO `jbos_captcha` VALUES ('709b5847-0517-4a45-b767-3053c46b40e7', '4cgn5');
INSERT INTO `jbos_captcha` VALUES ('713511ad-e8e7-46df-83a6-b7f7a3d606e3', '35g54');
INSERT INTO `jbos_captcha` VALUES ('716a5155-778c-4009-b7f0-c2877ed9ed5e', 'wpnng');
INSERT INTO `jbos_captcha` VALUES ('725cdcca-0c61-40af-8507-87be6978a677', 'ym888');
INSERT INTO `jbos_captcha` VALUES ('72dc8c33-0bde-48ac-826d-9665175ccde4', 'pnp5f');
INSERT INTO `jbos_captcha` VALUES ('72edd1e7-b7b7-4d9b-9b09-1656fb560ba4', 'f7m4y');
INSERT INTO `jbos_captcha` VALUES ('72f213d1-4288-4c84-b82d-1b0004c9541e', 'ynxcm');
INSERT INTO `jbos_captcha` VALUES ('731f3a59-b248-4c09-97d8-9f51d6e94355', 'n7m8m');
INSERT INTO `jbos_captcha` VALUES ('74281b88-8405-4324-9bd4-78359bf72789', '5eebd');
INSERT INTO `jbos_captcha` VALUES ('749510d4-98dc-4150-a27a-ff8e1158113e', '2pynw');
INSERT INTO `jbos_captcha` VALUES ('7507f7f7-2baa-4cd4-9458-9ffb688c91ff', '48bg4');
INSERT INTO `jbos_captcha` VALUES ('759f3dc1-993b-4d4c-a6ba-1cd686f10b6f', 'ew24g');
INSERT INTO `jbos_captcha` VALUES ('75fb8f7f-b7fc-48ed-a512-0c5013b91e89', 'eydpw');
INSERT INTO `jbos_captcha` VALUES ('7625837c-d2bc-4640-bfeb-d07119a803a7', '3pyb2');
INSERT INTO `jbos_captcha` VALUES ('767ca6d9-c171-4be1-abac-67dd602d9518', 'fmnem');
INSERT INTO `jbos_captcha` VALUES ('7717721f-5498-4a88-b0cc-197ee6b24d9c', 'nxmgn');
INSERT INTO `jbos_captcha` VALUES ('77bed722-0c96-455c-975f-0cf0554a35d6', '875ya');
INSERT INTO `jbos_captcha` VALUES ('77ee60b8-d06e-4036-8671-ea9b446d639b', '4dgg2');
INSERT INTO `jbos_captcha` VALUES ('789c438a-0860-4b42-a18d-39064fdb8a3e', '3p6n7');
INSERT INTO `jbos_captcha` VALUES ('78b61c0f-1047-4144-8c66-575e2aa2db65', 'xdx7n');
INSERT INTO `jbos_captcha` VALUES ('79fe2fa2-dca8-416a-9266-8a5cd56a5401', '6e2yc');
INSERT INTO `jbos_captcha` VALUES ('7a6e3519-c91d-4478-8cbf-0618dd7d8bc8', 'ywn83');
INSERT INTO `jbos_captcha` VALUES ('7a72a373-679c-4728-bbcf-e971fb68acda', 'xb8ba');
INSERT INTO `jbos_captcha` VALUES ('7b01046d-e796-4959-b243-c0af73c97bdc', 'm355d');
INSERT INTO `jbos_captcha` VALUES ('7b52e6c4-84fd-4061-969d-2693b115c8f5', '2dydx');
INSERT INTO `jbos_captcha` VALUES ('7cffe020-3a70-444e-b13a-cce027b1cbfc', 'ma54m');
INSERT INTO `jbos_captcha` VALUES ('7e659c46-e79e-45fa-8f38-1546d9a4693a', 'bbn3y');
INSERT INTO `jbos_captcha` VALUES ('7ed7b9cc-816a-4f6b-896d-6662e5583661', 'eewpn');
INSERT INTO `jbos_captcha` VALUES ('7f22181a-d975-4b24-861a-90be4ca10652', '4gn6n');
INSERT INTO `jbos_captcha` VALUES ('7f542039-61d5-4117-aaf1-db26b693650d', 'e3eyy');
INSERT INTO `jbos_captcha` VALUES ('8059bf57-930c-4cf4-b92b-418af4f91c83', 'n38pn');
INSERT INTO `jbos_captcha` VALUES ('807b1be2-5d37-4dc4-936a-420ed129fa2d', '4a7ng');
INSERT INTO `jbos_captcha` VALUES ('808d72ed-3551-47cc-b539-fe5261909a43', 'wpyde');
INSERT INTO `jbos_captcha` VALUES ('8097360e-6928-4710-a954-313d8a8fb58c', '6fcn5');
INSERT INTO `jbos_captcha` VALUES ('80cdcd02-a23a-41cd-a898-7aa1a932a58b', 'b6xxm');
INSERT INTO `jbos_captcha` VALUES ('81eba1ee-f3b4-4af9-9d28-11e2f163cdd0', 'ycn2y');
INSERT INTO `jbos_captcha` VALUES ('826120f2-6a28-467c-9d96-0971badf608b', 'agm6n');
INSERT INTO `jbos_captcha` VALUES ('82c8a24f-db04-4413-832a-97af2803c45c', 'w8fpa');
INSERT INTO `jbos_captcha` VALUES ('836db5ef-b0e2-4322-8984-2e9850fb8903', 'xbn4w');
INSERT INTO `jbos_captcha` VALUES ('8371a83f-707e-43ef-b3d4-6acb36d423b1', '6cmda');
INSERT INTO `jbos_captcha` VALUES ('842d85d7-76ed-42ce-85b1-ee9474262d95', '6cw8a');
INSERT INTO `jbos_captcha` VALUES ('847934e3-8a1a-428c-ad33-29fd32feafda', '6ag3c');
INSERT INTO `jbos_captcha` VALUES ('847e4915-74ed-4fb2-b5fd-4a25ccf7dcd2', 'nyaam');
INSERT INTO `jbos_captcha` VALUES ('84ed59ca-9d24-478f-9d42-41886ec3a15a', 'bafb5');
INSERT INTO `jbos_captcha` VALUES ('8584eb1c-8448-497a-9de5-bd80fdcc1df6', '4b423');
INSERT INTO `jbos_captcha` VALUES ('85baa0c6-4f2b-4c92-950b-e5aa36cedafc', 'cgppa');
INSERT INTO `jbos_captcha` VALUES ('8615cbbd-f4ab-4812-af04-024183ab3b81', 'fx2e3');
INSERT INTO `jbos_captcha` VALUES ('861730e3-90fd-4658-8dcf-1ea2a2e39174', '5ypy2');
INSERT INTO `jbos_captcha` VALUES ('866a2569-635d-43a9-be77-d1473c88d56d', '4n4n8');
INSERT INTO `jbos_captcha` VALUES ('868f8e3d-b874-4756-824c-0e75587fe671', 'w8wgw');
INSERT INTO `jbos_captcha` VALUES ('8724fa9c-5c8a-4a3a-b256-643b88c73051', 'yeg6e');
INSERT INTO `jbos_captcha` VALUES ('87867c4b-0d8b-433d-a207-742a3ab401d9', '62nad');
INSERT INTO `jbos_captcha` VALUES ('87aa1e95-65c1-479e-a18e-d64e7bee30db', '4ww5b');
INSERT INTO `jbos_captcha` VALUES ('87d1b751-de6a-4fab-bae2-54b3d2f048ea', 'n25f8');
INSERT INTO `jbos_captcha` VALUES ('880915b5-81e8-47e9-8408-88985e46b0a1', 'ae378');
INSERT INTO `jbos_captcha` VALUES ('88aa93cd-05ae-453a-b208-31257415513a', '25acn');
INSERT INTO `jbos_captcha` VALUES ('89118dbe-76a6-4ff6-8500-8a0845857a16', 'np6wb');
INSERT INTO `jbos_captcha` VALUES ('892a0338-6aef-4deb-8cdf-d1cb5f1d54d3', 'cn4p8');
INSERT INTO `jbos_captcha` VALUES ('8950f646-8c5a-429b-8ffe-dc26371c0678', '388d7');
INSERT INTO `jbos_captcha` VALUES ('8986eb43-acb2-4331-9c8b-6807de03b56d', '5n876');
INSERT INTO `jbos_captcha` VALUES ('89e1ee44-7055-404e-b307-057e67cbda17', '7g67b');
INSERT INTO `jbos_captcha` VALUES ('8a3fd261-f654-4596-ae4f-4fb9bf3bd876', '5gmyb');
INSERT INTO `jbos_captcha` VALUES ('8b2a21d7-6627-41d6-97fa-6ace75966510', 'ga88w');
INSERT INTO `jbos_captcha` VALUES ('8b76c5cd-2fdf-4d75-b5ed-a758b067b376', 'adep2');
INSERT INTO `jbos_captcha` VALUES ('8ba48ce3-3914-49c0-aa92-bb34cc6531ab', 'ngmn5');
INSERT INTO `jbos_captcha` VALUES ('8c0e831f-e39f-4e09-a8c2-6239dadcd7df', 'cmnax');
INSERT INTO `jbos_captcha` VALUES ('8c9bd08d-127b-403f-a731-ed494bd89d2a', 'wfxbw');
INSERT INTO `jbos_captcha` VALUES ('8cf666b0-cdd5-4ac3-a37e-b5ca3978625e', '43x34');
INSERT INTO `jbos_captcha` VALUES ('8d488bf7-3f19-41e1-a7df-8b71ea378f7a', 'xxp8g');
INSERT INTO `jbos_captcha` VALUES ('8d5a48be-58dc-4f2c-bb4b-ebd7883d56af', 'xnad3');
INSERT INTO `jbos_captcha` VALUES ('8e039e81-31a3-46f8-968a-82caa5865c7f', 'pfebw');
INSERT INTO `jbos_captcha` VALUES ('8e2ea81a-4e6e-4ef0-8432-fd32cf7c758d', 'eedne');
INSERT INTO `jbos_captcha` VALUES ('8f9451ff-515f-4b5c-b9fc-8c66200b4af2', 'y8g7c');
INSERT INTO `jbos_captcha` VALUES ('906f834b-4974-41da-86de-bf95c3ee55cd', 'eepd4');
INSERT INTO `jbos_captcha` VALUES ('910f873d-5cc3-4d95-8388-b5ea46e6582c', 'w5a2a');
INSERT INTO `jbos_captcha` VALUES ('91385d7a-4bc4-4580-82d5-8865ef2201a2', '2pyx5');
INSERT INTO `jbos_captcha` VALUES ('929fecd7-af57-48be-aee4-979aa454d1cf', 'x3yyw');
INSERT INTO `jbos_captcha` VALUES ('92abe206-7368-48a7-ad6b-9e00ff43c271', '5n7gd');
INSERT INTO `jbos_captcha` VALUES ('92f8c090-deab-4bb3-9191-f98447f26a6a', 'f226p');
INSERT INTO `jbos_captcha` VALUES ('93c1e837-ff0e-4be7-b942-118ed56be48b', 'pdpan');
INSERT INTO `jbos_captcha` VALUES ('949b1f3b-7232-4832-a52a-77f9ec4df1b8', '882fw');
INSERT INTO `jbos_captcha` VALUES ('94f5b88b-bc6b-4d65-bddc-74727c5d00e4', 'fagmn');
INSERT INTO `jbos_captcha` VALUES ('95353147-422a-44ed-84b9-ae6792117f7e', 'g7fg2');
INSERT INTO `jbos_captcha` VALUES ('956b83d5-ec74-42d4-ba5d-b02ded42536d', '2cxfy');
INSERT INTO `jbos_captcha` VALUES ('95933a08-90d6-441e-8ec5-e1de1e9a0a9e', 'w476g');
INSERT INTO `jbos_captcha` VALUES ('95adbb20-f16b-4f6d-8e4a-7d04d9623c13', '832cn');
INSERT INTO `jbos_captcha` VALUES ('95d997cf-80a2-4d3a-bb65-772d226c6d66', 'nb37c');
INSERT INTO `jbos_captcha` VALUES ('9766bf33-1b04-4114-84fa-0797b37743f8', 'nb2y6');
INSERT INTO `jbos_captcha` VALUES ('97b3c68e-0402-4d55-9289-4e65afe4866b', 'n8xed');
INSERT INTO `jbos_captcha` VALUES ('97b858df-76f5-4042-953e-58385e396c59', 'emna8');
INSERT INTO `jbos_captcha` VALUES ('980f4c99-9616-4b8d-8351-085c479e6e52', 'p5xny');
INSERT INTO `jbos_captcha` VALUES ('98bc79f9-9b11-42df-90cc-f13fbaa8d5e3', 'fcgn5');
INSERT INTO `jbos_captcha` VALUES ('98ded84a-1367-49b4-922e-ef23579862a0', '2y5dn');
INSERT INTO `jbos_captcha` VALUES ('992455d9-32af-4fc4-8f70-eeafc5c36345', 'fc8eg');
INSERT INTO `jbos_captcha` VALUES ('9994d227-4cb4-4c5d-8992-2c54e00ce3a3', 'wnppg');
INSERT INTO `jbos_captcha` VALUES ('9a333fa3-29b9-4876-8fd3-4b750c26c066', 'w62gb');
INSERT INTO `jbos_captcha` VALUES ('9ad74f50-63e8-4304-8fa7-6cbbde0d4fff', 'w7n4f');
INSERT INTO `jbos_captcha` VALUES ('9b12d8ca-911a-43f8-bcc6-dca5bab8e9e7', '6wm64');
INSERT INTO `jbos_captcha` VALUES ('9b205778-306d-478a-ad54-6255662ecfd5', 'wg2c6');
INSERT INTO `jbos_captcha` VALUES ('9b4c313b-ff06-43ca-8ccd-81c7b38c144a', 'p2nad');
INSERT INTO `jbos_captcha` VALUES ('9bb24841-1bef-4453-9f5f-3bf00d48781b', '6wd22');
INSERT INTO `jbos_captcha` VALUES ('9c1b7ad4-e93c-4f3f-9387-d9ce57bd4647', 'f33e5');
INSERT INTO `jbos_captcha` VALUES ('9c3ce338-9240-4b9a-9cc4-4211ff7a454c', 'b4f8b');
INSERT INTO `jbos_captcha` VALUES ('9cc0d316-55fe-4142-97a1-629a8056122c', 'xww2n');
INSERT INTO `jbos_captcha` VALUES ('9cfc8d20-9cd4-4b58-9737-1026951d5790', '5x8ex');
INSERT INTO `jbos_captcha` VALUES ('9d6ff72a-d539-4fa0-9e5e-9453e4cfa8b5', '44f2p');
INSERT INTO `jbos_captcha` VALUES ('9d9957b3-9b3d-445b-bab3-1c6bceaec977', 'f257n');
INSERT INTO `jbos_captcha` VALUES ('9e3780f1-3e32-4a54-a3d8-4142bf3561b3', '34mf4');
INSERT INTO `jbos_captcha` VALUES ('9e7958af-fc35-48a0-bcf0-d35424493625', '6gpbw');
INSERT INTO `jbos_captcha` VALUES ('9efc8141-aca1-49d4-8ce8-31b3ec3d575f', 'gwf3p');
INSERT INTO `jbos_captcha` VALUES ('9fb68f1d-77cb-4285-a675-588118d8a4ba', 'cbc7d');
INSERT INTO `jbos_captcha` VALUES ('a0725e0d-bd11-4080-8f7f-d28a5c7cbfb8', 'be66w');
INSERT INTO `jbos_captcha` VALUES ('a1217898-73dc-472c-92f4-a15ed4fc14a7', 'nnfg3');
INSERT INTO `jbos_captcha` VALUES ('a1717ec3-33c4-44a1-9b91-bd72697368b3', '445bn');
INSERT INTO `jbos_captcha` VALUES ('a1c8b1ca-ddb5-4c99-99d7-36e6dd0df552', '8da6e');
INSERT INTO `jbos_captcha` VALUES ('a1f3ae4f-6053-4a18-a4dd-bab7f7e6bafa', 'e747f');
INSERT INTO `jbos_captcha` VALUES ('a2032bd2-d06d-467a-9684-2e72ca5b65ce', '4b2en');
INSERT INTO `jbos_captcha` VALUES ('a209dcf4-ea39-46f3-b758-839bd81db879', 'cwpnw');
INSERT INTO `jbos_captcha` VALUES ('a2bf5b17-d166-4c07-bc8b-ee23c3dcf724', 'f5gyw');
INSERT INTO `jbos_captcha` VALUES ('a2d70680-1c83-4b9c-8d13-b1903fa425d7', 'nm35m');
INSERT INTO `jbos_captcha` VALUES ('a2de93ac-8d4c-49c3-b080-2709961f0d49', 'nyy5x');
INSERT INTO `jbos_captcha` VALUES ('a3997fde-2412-415b-b905-a0e7318c57cc', '6n2fc');
INSERT INTO `jbos_captcha` VALUES ('a3ad7e7f-d4fc-4c3b-8edb-5457f1c6f13c', '3mn3e');
INSERT INTO `jbos_captcha` VALUES ('a5950fbb-e12b-4cb1-977a-62433d52a573', 'p2by3');
INSERT INTO `jbos_captcha` VALUES ('a62705e6-b71a-4570-9f58-1c9286de8248', 'pbdme');
INSERT INTO `jbos_captcha` VALUES ('a63aa2a5-4d2a-4871-9873-ec7f4673eb8c', 'ag4mb');
INSERT INTO `jbos_captcha` VALUES ('a6a04071-b657-465b-8a6d-101af009b988', 'f44ew');
INSERT INTO `jbos_captcha` VALUES ('a8403e4a-86c2-43a5-8e3a-10b792be00ea', '428cx');
INSERT INTO `jbos_captcha` VALUES ('a95e66e1-bc2e-4c59-a413-d0915ce7771f', 'pcna8');
INSERT INTO `jbos_captcha` VALUES ('a9635570-ca3c-4c3f-8460-490c332ff10a', 'gwnnp');
INSERT INTO `jbos_captcha` VALUES ('a987a2e2-596c-46b3-908c-10bc9beebdb5', 'newye');
INSERT INTO `jbos_captcha` VALUES ('aa1bf9c5-762a-46d2-b025-295e6a49d860', '54e8p');
INSERT INTO `jbos_captcha` VALUES ('aa4d1ba3-8fa0-430d-be02-37a918919554', '3nwnc');
INSERT INTO `jbos_captcha` VALUES ('aa520d18-ff30-4d60-aa06-f850d04d7553', '225dp');
INSERT INTO `jbos_captcha` VALUES ('aa73a0ff-d655-46e9-9854-9c3db7f13753', 'edxn7');
INSERT INTO `jbos_captcha` VALUES ('aad80692-9aaf-49b5-a7e7-8cf7e641da7f', 'cnfn8');
INSERT INTO `jbos_captcha` VALUES ('ab717770-6b67-4b33-8688-77535ce2fd5d', '2pbyd');
INSERT INTO `jbos_captcha` VALUES ('aba3801b-0c42-4b89-95c3-c8520f6e3a62', '7gaaa');
INSERT INTO `jbos_captcha` VALUES ('acc140e3-190c-4a18-a2de-428cba56460f', 'g568w');
INSERT INTO `jbos_captcha` VALUES ('acef5931-aca2-49bb-86e8-c4b526e33349', 'amfng');
INSERT INTO `jbos_captcha` VALUES ('ad0186f7-904f-4672-8d57-2157373417ee', 'db4g4');
INSERT INTO `jbos_captcha` VALUES ('ad2e35c3-3a09-4f7b-aaa8-62e3137dcdb2', 'd6fn3');
INSERT INTO `jbos_captcha` VALUES ('aead4ce2-2d09-4340-b5cc-f8add8effb12', '5afff');
INSERT INTO `jbos_captcha` VALUES ('af25e3f7-4a75-495e-a6ee-60e08f5e1630', '6b8e8');
INSERT INTO `jbos_captcha` VALUES ('af545a3c-d95a-4c34-b57e-f1a6e3351656', '55n76');
INSERT INTO `jbos_captcha` VALUES ('b1168627-ab91-433d-a5a0-77a3e2e8d834', '5f28e');
INSERT INTO `jbos_captcha` VALUES ('b12f5eac-c1ad-4a2e-baae-3646b9ffd896', '2dnnn');
INSERT INTO `jbos_captcha` VALUES ('b148ede3-6a02-41ab-9a06-6801da745665', '5nfye');
INSERT INTO `jbos_captcha` VALUES ('b1ec3b2b-1be6-41c4-bfe8-26a1aad10718', '7pnmp');
INSERT INTO `jbos_captcha` VALUES ('b386c465-799b-4bd8-95f0-ddf8fd8d7e5f', '27e62');
INSERT INTO `jbos_captcha` VALUES ('b3d7c24e-cdcb-44e5-b8da-cfb4097dab37', 'g3xbm');
INSERT INTO `jbos_captcha` VALUES ('b42b68cf-3ae9-4030-b2aa-676bd13aa890', 'yc3dn');
INSERT INTO `jbos_captcha` VALUES ('b4318342-ebc0-45dc-8346-32510717c4e3', 'xwbx2');
INSERT INTO `jbos_captcha` VALUES ('b446f5ff-af2f-4e40-ab6e-61501212405e', 'ed4x8');
INSERT INTO `jbos_captcha` VALUES ('b475c976-172e-4926-a8ef-18cb899abf8e', 'b3pfg');
INSERT INTO `jbos_captcha` VALUES ('b492fad3-b249-40f3-b8f2-ce704ff1720a', 'dyp8e');
INSERT INTO `jbos_captcha` VALUES ('b4b88cdd-8984-4c8d-a250-eca88eecd2ad', 'e2nam');
INSERT INTO `jbos_captcha` VALUES ('b4c83fad-21b0-4eee-8ced-d95fcb5449e5', '8nw6g');
INSERT INTO `jbos_captcha` VALUES ('b4d4818a-da9b-4b38-ac90-503a7972d6ca', 'xeexp');
INSERT INTO `jbos_captcha` VALUES ('b54ec9e4-ae3d-460b-a5f7-3d6dd698a41a', 'wwgxc');
INSERT INTO `jbos_captcha` VALUES ('b685c8b0-b4c5-4b7e-9945-44d628db83c3', '3nnnn');
INSERT INTO `jbos_captcha` VALUES ('b6c4da08-3c60-4d18-927a-96ee68703546', '5468f');
INSERT INTO `jbos_captcha` VALUES ('b6e61389-957b-4912-89ed-7ffd1a88019e', '52neb');
INSERT INTO `jbos_captcha` VALUES ('b737b558-6617-428f-b432-feb00c993610', 'pwfp5');
INSERT INTO `jbos_captcha` VALUES ('b74139ae-b3e4-4875-8d41-7e82e9cc7605', 'fnmp4');
INSERT INTO `jbos_captcha` VALUES ('b82304f4-4ba6-463a-8229-7a8d2ef77567', '7nnwg');
INSERT INTO `jbos_captcha` VALUES ('b89f11b9-ecdf-4ca4-a27c-df596b97623d', 'nxygw');
INSERT INTO `jbos_captcha` VALUES ('b8bb2ba0-3158-454c-860d-c0d35769b839', 'p67bm');
INSERT INTO `jbos_captcha` VALUES ('b8ffa2b3-a68e-46c4-85ee-4f13fbc64c5c', '7ppg7');
INSERT INTO `jbos_captcha` VALUES ('b9985eea-4e3c-4ef5-978c-fc7d81773525', '8fw8d');
INSERT INTO `jbos_captcha` VALUES ('b9ce3a4a-8e7b-405e-8f30-103c7c423386', '4nfcn');
INSERT INTO `jbos_captcha` VALUES ('b9f483ed-b814-4f55-9a87-b8bb7182585b', 'n8gwd');
INSERT INTO `jbos_captcha` VALUES ('baa99215-90d8-4657-8d16-5941dd950075', 'bf8x4');
INSERT INTO `jbos_captcha` VALUES ('babca41b-28dd-4cab-8b48-ce203d8763af', '7xen4');
INSERT INTO `jbos_captcha` VALUES ('bb294634-4da0-4a08-a8d6-01dcb4a132c2', 'wwxya');
INSERT INTO `jbos_captcha` VALUES ('bc75c315-0fb7-4eaf-98f8-5353cee176fc', 'f8fn8');
INSERT INTO `jbos_captcha` VALUES ('bcf48d46-4d26-43d2-b64d-7bac37624fb9', 'awyfx');
INSERT INTO `jbos_captcha` VALUES ('bcfe2807-e7c2-41e9-8c89-913839be3ae9', 'pxpf4');
INSERT INTO `jbos_captcha` VALUES ('bd75b4ed-d511-40fb-b865-2ccb89130ed4', 'ygmbe');
INSERT INTO `jbos_captcha` VALUES ('bd8cb7eb-ffad-4693-b785-592c39817ceb', 'w586n');
INSERT INTO `jbos_captcha` VALUES ('be318313-5e3a-42c0-b234-f144ea1bf48a', 'cxn3w');
INSERT INTO `jbos_captcha` VALUES ('bef6ce40-0fe3-41ac-bae6-ab50fb83c683', 'bd386');
INSERT INTO `jbos_captcha` VALUES ('bf19c0d9-91b3-4660-881b-13aa7735a950', 'bpnb3');
INSERT INTO `jbos_captcha` VALUES ('c0323478-1554-44d2-a523-49bd3dee3816', 'cn7n7');
INSERT INTO `jbos_captcha` VALUES ('c054f11e-6b76-4505-89a8-a1f9b6157f42', 'wg2ep');
INSERT INTO `jbos_captcha` VALUES ('c0e57872-4aa5-453c-abc6-2253840bdf00', 'gfa7c');
INSERT INTO `jbos_captcha` VALUES ('c0fa2711-1952-4f02-b8ff-e0c18e877f0b', '6xa8y');
INSERT INTO `jbos_captcha` VALUES ('c15dd9c6-6ed1-4c9e-a29d-6414c74278dc', 'gb36f');
INSERT INTO `jbos_captcha` VALUES ('c242acda-66ed-44a3-8641-2c25cf3ef9f2', '6xegf');
INSERT INTO `jbos_captcha` VALUES ('c26d3ed8-7c37-40e3-a952-76eaba85c615', '54axd');
INSERT INTO `jbos_captcha` VALUES ('c2e2af39-6a12-4471-9782-aa3a71a4e7c5', 'ybmwg');
INSERT INTO `jbos_captcha` VALUES ('c36a3236-d785-4c88-8c75-a8dbd7aecb43', '4g77b');
INSERT INTO `jbos_captcha` VALUES ('c446f6b2-b4e0-48a4-9a01-e0590b176690', 'y4g3g');
INSERT INTO `jbos_captcha` VALUES ('c50ba149-7be6-45b4-8d75-a0ffe39c753e', 'c6d6e');
INSERT INTO `jbos_captcha` VALUES ('c5349d49-d921-4683-b607-b03637dae424', 'e8n88');
INSERT INTO `jbos_captcha` VALUES ('c54bfca7-5360-4901-ae33-46662768de91', '2nffn');
INSERT INTO `jbos_captcha` VALUES ('c5c945a9-0110-4885-9b15-4ea5a658c6ff', 'ncnc3');
INSERT INTO `jbos_captcha` VALUES ('c5ce1a0b-a240-4bd1-8b96-0a11766ffef6', '2bnmn');
INSERT INTO `jbos_captcha` VALUES ('c6458257-d11f-4a6b-ae91-07a3ac41aa10', 'm8m56');
INSERT INTO `jbos_captcha` VALUES ('c695e35c-afa7-4d92-afe8-3001b66ff94f', 'x8e2b');
INSERT INTO `jbos_captcha` VALUES ('c6b1ac47-e656-4962-936f-1aea40d491e5', '7y3y8');
INSERT INTO `jbos_captcha` VALUES ('c82388f7-b3ca-48a6-aa0c-ea1afc379e2e', 'xm38x');
INSERT INTO `jbos_captcha` VALUES ('c831654e-5fae-43d9-91cb-f1ab743ffb4c', '6cw4c');
INSERT INTO `jbos_captcha` VALUES ('c8406ec9-2e48-41d9-848c-d7fb1a4babfd', '8bad2');
INSERT INTO `jbos_captcha` VALUES ('c8666435-bea4-4d8b-bb7f-3d9400b8928d', '4ynby');
INSERT INTO `jbos_captcha` VALUES ('c86abf98-0792-4d1a-927d-4f1d434ab8bd', 'ge5md');
INSERT INTO `jbos_captcha` VALUES ('c9436db7-4073-41ce-ba62-57c39611218d', '2depb');
INSERT INTO `jbos_captcha` VALUES ('c9e87c40-d81c-4e0e-957c-472740345633', 'xcmb2');
INSERT INTO `jbos_captcha` VALUES ('ca028f25-e82f-4d4c-b770-277fae8ee04b', '2wpgp');
INSERT INTO `jbos_captcha` VALUES ('ca234442-29c7-40f1-b38e-9a1423951431', 'nmcbx');
INSERT INTO `jbos_captcha` VALUES ('ca2418a8-7e8e-4614-8773-cdf8e2ee5e3b', 'dge3p');
INSERT INTO `jbos_captcha` VALUES ('ca61cbfb-6333-43d6-995c-1a51fd0f2b45', 'nam53');
INSERT INTO `jbos_captcha` VALUES ('ca824064-c6ce-4333-8885-cc5fc4b95133', 'nnf3m');
INSERT INTO `jbos_captcha` VALUES ('caa83cfc-5126-4850-89a7-c3e109c651e5', 'wgw53');
INSERT INTO `jbos_captcha` VALUES ('cb0c38c2-3a3c-4ee5-a782-1df9a4bf45e8', 'by3d6');
INSERT INTO `jbos_captcha` VALUES ('cb56757f-82e9-45d1-94d2-ae55a898898b', '5p7bg');
INSERT INTO `jbos_captcha` VALUES ('cb943006-087a-405c-bc93-4ca2d94600d5', 'a6n6y');
INSERT INTO `jbos_captcha` VALUES ('cb9b6249-0eb0-4479-aa36-0978ad77f7bb', '52e54');
INSERT INTO `jbos_captcha` VALUES ('cbe464c2-6690-41b0-9f9c-4eb8e6d9ae0a', 'bxnbb');
INSERT INTO `jbos_captcha` VALUES ('cc83b912-0d33-4a2c-b325-3d4c97e8c90f', 'dyg45');
INSERT INTO `jbos_captcha` VALUES ('cc868bc8-3179-4b42-963f-610bddb29e6b', 'mn437');
INSERT INTO `jbos_captcha` VALUES ('cd40a8d5-2d27-4aca-88e3-832dd0631931', '6mfp2');
INSERT INTO `jbos_captcha` VALUES ('cdf72c5a-ea14-4dee-b788-1efe27cf64ea', '4547b');
INSERT INTO `jbos_captcha` VALUES ('cf46ee0b-b792-4fac-a9f6-cd35bd3cba62', 'nfe3w');
INSERT INTO `jbos_captcha` VALUES ('cf4f7681-dc94-42cb-b0b6-0a109e10bdc3', '25g77');
INSERT INTO `jbos_captcha` VALUES ('cf8ead2a-4fce-4086-8a1a-db4cf4c48f2a', 'w74p6');
INSERT INTO `jbos_captcha` VALUES ('cf9d3737-1bf4-4ed5-b547-21eac9993a79', '2xa3b');
INSERT INTO `jbos_captcha` VALUES ('d0acf09b-a45f-43da-8624-83fd79a528a6', 'nnab4');
INSERT INTO `jbos_captcha` VALUES ('d0c16549-5c3d-4c80-a07b-31b779041aa0', 'wf8mn');
INSERT INTO `jbos_captcha` VALUES ('d1168af8-8b78-4acb-bcdc-2337948082e9', 'c7452');
INSERT INTO `jbos_captcha` VALUES ('d1333740-44ba-47f8-8758-e6926874fe23', '3bn5e');
INSERT INTO `jbos_captcha` VALUES ('d20e38f3-b632-421a-81ee-1b84b9bfd29a', 'n6fm8');
INSERT INTO `jbos_captcha` VALUES ('d314dd05-ee35-4e3a-922b-68938cef6f28', '3pn56');
INSERT INTO `jbos_captcha` VALUES ('d34f3a12-e887-41bf-9168-887f23f74fb6', '8c5c6');
INSERT INTO `jbos_captcha` VALUES ('d3c3806e-57a8-41d7-9a3d-1deb36eaf382', 'n6885');
INSERT INTO `jbos_captcha` VALUES ('d3cd550a-6d42-4294-ab79-431a0f7b2e47', 'pady5');
INSERT INTO `jbos_captcha` VALUES ('d3ef4003-8835-4251-9ec3-fb280c5515ea', 'b24yn');
INSERT INTO `jbos_captcha` VALUES ('d43dce00-a3cb-4b7b-b837-9e6012a367dd', '8wd32');
INSERT INTO `jbos_captcha` VALUES ('d4608f9e-ab80-45cc-aaed-74a6fb2e9980', 'cnwbx');
INSERT INTO `jbos_captcha` VALUES ('d5598f37-54b6-46be-8b4f-9eb3239219cb', '88fbc');
INSERT INTO `jbos_captcha` VALUES ('d568d3c5-d832-4668-bf51-c3dc956566be', 'f4nbp');
INSERT INTO `jbos_captcha` VALUES ('d63746f6-cb60-4f4f-bbc3-5b0b4b0fe745', 'dbpc2');
INSERT INTO `jbos_captcha` VALUES ('d72fb5da-0fb1-4108-86ef-c29e7f5c05fe', '6e3m3');
INSERT INTO `jbos_captcha` VALUES ('d7c5759e-3436-4f39-ab1a-5d718a51e50d', 'p5dw8');
INSERT INTO `jbos_captcha` VALUES ('d870b0cf-2195-4d54-9b2b-a34418789d00', 'cnwnc');
INSERT INTO `jbos_captcha` VALUES ('d90d69d8-a319-4a9b-bc12-0f242cbe06a5', '26b4w');
INSERT INTO `jbos_captcha` VALUES ('d9709a73-5f40-415f-bb68-ed9ce8582e28', 'y5y5f');
INSERT INTO `jbos_captcha` VALUES ('d97ceb15-b18a-44ea-b607-6457e8ad108a', 'bnbyy');
INSERT INTO `jbos_captcha` VALUES ('db611e84-4c03-400c-ba58-7994d549d9e9', 'a56db');
INSERT INTO `jbos_captcha` VALUES ('dc6861a2-4732-4d2d-9203-f90f25901581', 'fc7cp');
INSERT INTO `jbos_captcha` VALUES ('dd3188c3-ceb9-4cf6-b695-0f675f8c77c3', 'd7y5c');
INSERT INTO `jbos_captcha` VALUES ('ddaa5a37-9915-45da-845a-4a8e1977d589', 'ab8ya');
INSERT INTO `jbos_captcha` VALUES ('dddb2dc4-f296-4aa5-8875-33a34555b0fa', 'g57a8');
INSERT INTO `jbos_captcha` VALUES ('dde6ff57-376a-4820-a276-0a6429696fe3', 'b7yc5');
INSERT INTO `jbos_captcha` VALUES ('de6ea8d7-9068-4cfb-bd31-0498aeb19db8', '3c3cp');
INSERT INTO `jbos_captcha` VALUES ('dea9aeac-f294-4332-98d7-43125e67c000', '2b86w');
INSERT INTO `jbos_captcha` VALUES ('df818d4f-34a7-47aa-abcb-e560605b6aa5', 'ffxpf');
INSERT INTO `jbos_captcha` VALUES ('e0fc2883-10a5-4af4-830e-b5f87dd7c0e9', 'mp7ea');
INSERT INTO `jbos_captcha` VALUES ('e1640e52-24c7-4889-a344-a857b6905556', '3cpxe');
INSERT INTO `jbos_captcha` VALUES ('e2809ea2-7ba6-464f-9472-963b923066df', '8mypg');
INSERT INTO `jbos_captcha` VALUES ('e2839009-c05e-432e-a23b-2aabed73c14c', '623n4');
INSERT INTO `jbos_captcha` VALUES ('e3726743-96ad-447d-9e78-24a75a0a8805', 'g6npb');
INSERT INTO `jbos_captcha` VALUES ('e4348480-aeff-44ef-a5dc-80a41eae15cd', '663bw');
INSERT INTO `jbos_captcha` VALUES ('e48c6fa8-1002-45da-9cb2-43eeb5fe14f7', 'nng3g');
INSERT INTO `jbos_captcha` VALUES ('e502e750-7a52-4e27-9434-eabea6c4927e', '2p7dm');
INSERT INTO `jbos_captcha` VALUES ('e567714e-d744-4293-be38-90fa67076f85', 'g7gdg');
INSERT INTO `jbos_captcha` VALUES ('e5787dd6-d926-40ca-a2f8-91c5a7ed8415', '82mm2');
INSERT INTO `jbos_captcha` VALUES ('e63820b5-8724-4eba-9e54-9bac7793327c', 'bn3mf');
INSERT INTO `jbos_captcha` VALUES ('e6592961-843d-4a88-a215-b38f1f10e834', 'yac2c');
INSERT INTO `jbos_captcha` VALUES ('e681c053-dc27-4d87-b444-9ee419de12f5', 'dfemm');
INSERT INTO `jbos_captcha` VALUES ('e75054d4-6d8d-444e-9097-c3878e902814', 'y5md5');
INSERT INTO `jbos_captcha` VALUES ('e8c57b16-019f-48dd-9bf7-b877faf56443', 'b4cax');
INSERT INTO `jbos_captcha` VALUES ('e8c83205-6118-46dd-830d-727c27bdb2d3', 'ffp5g');
INSERT INTO `jbos_captcha` VALUES ('e9d6da08-aaf9-43ef-a32d-96d225eaf893', '76yn2');
INSERT INTO `jbos_captcha` VALUES ('ea5e3d8e-ab11-478f-a357-675b7f2a3ff0', 'wwaw7');
INSERT INTO `jbos_captcha` VALUES ('ea9bf15f-96ea-495d-844e-8de6065e4bd8', '2b8n3');
INSERT INTO `jbos_captcha` VALUES ('eaaa3129-88b6-4342-a97d-9eb256b60d6f', '46cxx');
INSERT INTO `jbos_captcha` VALUES ('eafb63b7-42bb-4852-81d9-1624ad919044', 'mw5f2');
INSERT INTO `jbos_captcha` VALUES ('eb7bc5f3-1d36-4ff9-94a5-5a1d9e57c90a', 'n2n7e');
INSERT INTO `jbos_captcha` VALUES ('ec22f069-b5ca-4813-af5a-6d37056ce09d', 'mbe2m');
INSERT INTO `jbos_captcha` VALUES ('ec54b097-9951-42e3-9a59-1cc083f02ed1', '8g773');
INSERT INTO `jbos_captcha` VALUES ('ec592775-2c19-4f27-a07e-b46e2479a389', 'wf8nc');
INSERT INTO `jbos_captcha` VALUES ('ece60cca-2b38-4330-a4e8-f9cc225d44cc', 'ne5x3');
INSERT INTO `jbos_captcha` VALUES ('ed20a725-1007-4e8f-bf4a-3344f43b037c', 'nppfg');
INSERT INTO `jbos_captcha` VALUES ('ed639892-72f3-4f2d-ba31-c1e18ad66276', 'nf5a7');
INSERT INTO `jbos_captcha` VALUES ('edfbf677-cf66-4a6f-bcb0-1051375e359f', 'dwn56');
INSERT INTO `jbos_captcha` VALUES ('ee0aa684-7117-4372-846d-1bcf508c1c2d', 'mmegy');
INSERT INTO `jbos_captcha` VALUES ('ee3709f6-b700-4c6e-a42d-caee36008e13', 'n2ex6');
INSERT INTO `jbos_captcha` VALUES ('eef72e79-913d-4301-8140-61b85bb1317c', '5aex6');
INSERT INTO `jbos_captcha` VALUES ('ef0ac063-330f-4db1-b703-2d679421ae80', 'ncg8x');
INSERT INTO `jbos_captcha` VALUES ('f0a86380-9be4-476f-b7bc-8358a55427cc', 'adb84');
INSERT INTO `jbos_captcha` VALUES ('f2043e44-a8ae-4d07-8d20-47c7a0567c18', '5fbax');
INSERT INTO `jbos_captcha` VALUES ('f250f694-0ae2-445a-a2ed-6465cf4b3c6e', 'd7267');
INSERT INTO `jbos_captcha` VALUES ('f376c5e1-ff15-40c5-bad5-9530e093b217', 'mn38a');
INSERT INTO `jbos_captcha` VALUES ('f3a24055-c7f1-4485-a137-f3c7dda33bad', '74c84');
INSERT INTO `jbos_captcha` VALUES ('f4064ee7-99a0-4d77-a198-328caffb04f3', '8a6w4');
INSERT INTO `jbos_captcha` VALUES ('f44e42b2-d353-4a5f-9da7-8bc0ce5038bf', 'x3n52');
INSERT INTO `jbos_captcha` VALUES ('f497d637-b274-4344-8a82-a2fecada66e5', 'x45fc');
INSERT INTO `jbos_captcha` VALUES ('f53728b7-5ef8-4db1-a905-32f344e8d287', 'n3aca');
INSERT INTO `jbos_captcha` VALUES ('f6e1b0d3-040a-4e58-b782-c22a0f82173a', '328yw');
INSERT INTO `jbos_captcha` VALUES ('f7969937-6864-4532-94e7-afe55be75e71', 'bpbdc');
INSERT INTO `jbos_captcha` VALUES ('f806b291-bbd2-4547-9eed-5dc1cb6dbfd0', 'nxfa3');
INSERT INTO `jbos_captcha` VALUES ('f80ed098-d316-4b88-860e-23189a38842d', '3fnnn');
INSERT INTO `jbos_captcha` VALUES ('f87e4263-57c8-4658-bfa8-b2dd3f3ef64b', 'wyx6b');
INSERT INTO `jbos_captcha` VALUES ('f8e4951d-95d5-44f3-9c45-457172f2cae8', 'pdwxg');
INSERT INTO `jbos_captcha` VALUES ('f8fc34ee-c223-4a2e-826b-f51d42a5c195', 'yaf46');
INSERT INTO `jbos_captcha` VALUES ('f9286f63-e319-4c35-b5ec-4620daf7a6cb', '6aay2');
INSERT INTO `jbos_captcha` VALUES ('f9b1b6ae-52ad-4b16-9ca3-54d03d9ebb49', 'f6p63');
INSERT INTO `jbos_captcha` VALUES ('f9b417bf-5fc5-40cc-9277-62f72f09127f', 'n47gn');
INSERT INTO `jbos_captcha` VALUES ('fa2c2b7b-e386-441f-9dc9-dfe81e16d387', 'e6na5');
INSERT INTO `jbos_captcha` VALUES ('fa332c58-2eab-4122-b98a-681489bd1aa8', '5mcpg');
INSERT INTO `jbos_captcha` VALUES ('fa4725b6-e9f9-4fbc-9a6f-0e571f36e41a', '8xa62');
INSERT INTO `jbos_captcha` VALUES ('faeaec6d-bd70-4743-a3c3-a3e2d37f1b56', 'x55ef');
INSERT INTO `jbos_captcha` VALUES ('fb63516d-e451-4f37-8740-1b79bc8f8626', '5npn6');
INSERT INTO `jbos_captcha` VALUES ('fb7a5266-704d-4815-bd3b-4e61e7a58fc5', 'w32x7');
INSERT INTO `jbos_captcha` VALUES ('fc06627d-e4d6-4edb-9387-7696be0bc79d', '8x36c');
INSERT INTO `jbos_captcha` VALUES ('fc928f2a-605e-4c7d-9e45-512757e25d66', '4n8dn');
INSERT INTO `jbos_captcha` VALUES ('fce04725-d557-4c9b-b9a5-70656fe23d0f', 'm4b6w');
INSERT INTO `jbos_captcha` VALUES ('fd604a76-0d97-4aae-91cd-192e6ab8b27c', 'yngg5');
INSERT INTO `jbos_captcha` VALUES ('fdd5a879-77d2-46ae-83d1-dec4b69cf8f3', 'bf8p4');
INSERT INTO `jbos_captcha` VALUES ('fe0f299a-ed31-4812-a08f-712f4474025e', '8ange');
INSERT INTO `jbos_captcha` VALUES ('fe9f263e-05ca-4794-986f-85599d9dc770', '4bw8g');
INSERT INTO `jbos_captcha` VALUES ('feea2ce4-07b9-412f-b5ff-df8a2b837f0c', '38dac');
INSERT INTO `jbos_captcha` VALUES ('ff82ebce-85f1-4791-9b17-0273d151365f', 'mea48');
INSERT INTO `jbos_captcha` VALUES ('ffb5e1b5-6691-4347-bcce-80743e050d08', 'cwn2n');
INSERT INTO `jbos_captcha` VALUES ('ffc74fb9-e065-405b-bdaa-7e150a493121', '88gey');

-- ----------------------------
-- Table structure for jbos_dep
-- ----------------------------
DROP TABLE IF EXISTS `jbos_dep`;
CREATE TABLE `jbos_dep` (
  `ID` varchar(64) NOT NULL COMMENT 'ID',
  `PARENTID` varchar(64) DEFAULT NULL COMMENT '父ID',
  `DEPCODE` varchar(32) DEFAULT NULL COMMENT '部门编码',
  `DEPNAME` varchar(128) DEFAULT NULL COMMENT '部门名称',
  `DEPLEVEL` varchar(16) DEFAULT NULL COMMENT '部门级别',
  `ORGID` varchar(64) DEFAULT NULL COMMENT '所属机构',
  `DEPCHARGE` varchar(64) DEFAULT NULL COMMENT '部门负责人',
  `DEPDIRECTOR` varchar(64) DEFAULT NULL COMMENT '部门分管领导',
  `EFFECTDATE` timestamp NULL DEFAULT NULL COMMENT '生效日期',
  `DISABLEDDATE` timestamp NULL DEFAULT NULL COMMENT '失效日期',
  `ORDERNO` int DEFAULT NULL COMMENT '机构排序',
  `REMARK` varchar(256) DEFAULT NULL COMMENT '备注',
  `ISVALID` int DEFAULT '1' COMMENT '是否有效(1：是；0：否)',
  `CREATEUSERID` varchar(64) DEFAULT NULL COMMENT '创建用户',
  `CREATETIME` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `UPDATEUSERID` varchar(64) DEFAULT NULL COMMENT '更新用户',
  `UPDATETIME` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='部门信息表';

-- ----------------------------
-- Records of jbos_dep
-- ----------------------------
INSERT INTO `jbos_dep` VALUES ('2', null, '10101002', '总行科技部', '100', '2', 'k0094', 'k0010', null, null, null, null, '1', null, null, null, null);
INSERT INTO `jbos_dep` VALUES ('d24e41da-5b6c-41c3-9cf4-963e070944ab', null, '10101003', '测试部', '100', '2', 'k0091', 'k0013', null, null, null, null, '1', null, null, null, null);

-- ----------------------------
-- Table structure for jbos_dict_code
-- ----------------------------
DROP TABLE IF EXISTS `jbos_dict_code`;
CREATE TABLE `jbos_dict_code` (
  `TYPEID` varchar(64) NOT NULL COMMENT '类型ID',
  `DICTID` varchar(64) NOT NULL COMMENT '字典编码',
  `DICTNAME` varchar(64) DEFAULT NULL COMMENT '字典编码',
  `ORDERNO` int DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`TYPEID`,`DICTID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='字典码值表';

-- ----------------------------
-- Records of jbos_dict_code
-- ----------------------------
INSERT INTO `jbos_dict_code` VALUES ('JBOS_DEP_LEVEL', '100', '一级部门', '1');
INSERT INTO `jbos_dict_code` VALUES ('JBOS_DEP_LEVEL', '101', '二级部门', '2');
INSERT INTO `jbos_dict_code` VALUES ('JBOS_EMP_STATUS', 'locked', '锁定', '2');
INSERT INTO `jbos_dict_code` VALUES ('JBOS_EMP_STATUS', 'running', '正常', '1');
INSERT INTO `jbos_dict_code` VALUES ('JBOS_HEADSHIP', '100', '系统开发', '1');
INSERT INTO `jbos_dict_code` VALUES ('JBOS_HEADSHIP', '102', '项目经理', '3');

-- ----------------------------
-- Table structure for jbos_dict_type
-- ----------------------------
DROP TABLE IF EXISTS `jbos_dict_type`;
CREATE TABLE `jbos_dict_type` (
  `TYPEID` varchar(64) NOT NULL COMMENT '类型ID',
  `TYPENAME` varchar(64) DEFAULT NULL COMMENT '类型名称',
  PRIMARY KEY (`TYPEID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='字典类型表';

-- ----------------------------
-- Records of jbos_dict_type
-- ----------------------------
INSERT INTO `jbos_dict_type` VALUES ('JBOS_DEP_LEVEL', '部门级别');
INSERT INTO `jbos_dict_type` VALUES ('JBOS_EMP_STATUS', '员工状态');
INSERT INTO `jbos_dict_type` VALUES ('JBOS_HEADSHIP', '员工职务');

-- ----------------------------
-- Table structure for jbos_emp
-- ----------------------------
DROP TABLE IF EXISTS `jbos_emp`;
CREATE TABLE `jbos_emp` (
  `ID` varchar(64) NOT NULL COMMENT 'ID',
  `BADGE` varchar(16) DEFAULT NULL COMMENT '工号',
  `EMPNAME` varchar(16) DEFAULT NULL COMMENT '姓名',
  `ORGID` varchar(64) DEFAULT NULL COMMENT '所属机构',
  `DEPID` varchar(64) DEFAULT NULL COMMENT '所属部门',
  `HEADSHIP` varchar(16) DEFAULT NULL COMMENT '职务',
  `EMPSTATUS` varchar(16) DEFAULT NULL COMMENT '员工状态',
  `GENDER` varchar(16) DEFAULT NULL COMMENT '性别',
  `BIRTHDAY` varchar(10) DEFAULT NULL COMMENT '出生日期',
  `OFFICEPHONE` varchar(16) DEFAULT NULL COMMENT '办公电话',
  `MOBILEPHONE` varchar(11) DEFAULT NULL COMMENT '手机号码',
  `ORDERNO` int DEFAULT NULL COMMENT '排序',
  `REMARK` varchar(256) DEFAULT NULL COMMENT '备注',
  `ISVALID` int DEFAULT '1' COMMENT '是否有效(1：是；0：否)',
  `CREATEUSERID` varchar(64) DEFAULT NULL COMMENT '创建用户',
  `CREATETIME` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `UPDATEUSERID` varchar(64) DEFAULT NULL COMMENT '更新用户',
  `UPDATETIME` varchar(20) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='员工信息表';

-- ----------------------------
-- Records of jbos_emp
-- ----------------------------
INSERT INTO `jbos_emp` VALUES ('3e3b17c2-32ff-4d38-8cf0-a627fffaae70', 'k0100', 'k0100', '2', '2', '100', 'running', null, null, null, null, null, null, '1', null, null, null, null);
INSERT INTO `jbos_emp` VALUES ('56381b33-7cae-4125-9f84-6d5944cc0748', 'k0092', 'k0092', '2', '2', '100', 'running', null, null, null, null, null, null, '1', null, null, null, null);
INSERT INTO `jbos_emp` VALUES ('57c3cb7f-bf05-4660-858d-50b1246582f2', 'k0097', 'k0097', '2', '2', '100', 'running', null, null, null, null, null, null, '1', null, null, null, null);
INSERT INTO `jbos_emp` VALUES ('82470e24-4f71-4c75-8b7e-ff228d58e0f5', 'k0013', 'k0013', '2', '2', '', 'running', null, null, null, null, null, null, '1', null, null, null, null);
INSERT INTO `jbos_emp` VALUES ('885ad986-af51-4b7d-ad49-f48a38d6ed09', 'l0091', 'l0091', '2', '2', '', 'running', null, null, null, null, null, null, '1', null, null, null, null);
INSERT INTO `jbos_emp` VALUES ('917782f7-82d4-4510-9e5e-b5de2d25f89c', 'k0010', 'k0010', '2', '2', '', 'running', null, null, null, null, null, null, '1', null, null, null, null);
INSERT INTO `jbos_emp` VALUES ('9f887008-0c79-4efa-b518-9bd56e934434', 'l0091', 'l0091', '2', '2', '', 'running', null, null, null, null, null, null, '1', null, null, null, null);
INSERT INTO `jbos_emp` VALUES ('a76393f9-7624-4b75-8130-29da1cdd276b', 'k0096', 'k0096', '2', '2', '', 'running', null, null, null, null, null, null, '1', null, null, null, null);
INSERT INTO `jbos_emp` VALUES ('bd388b87-7181-43b8-86f0-869e49107105', 'k0094', 'k0094', '2', '2', '', 'running', null, null, null, null, null, null, '1', null, null, null, null);
INSERT INTO `jbos_emp` VALUES ('caef9062-581f-43d0-9cbe-0f89a6fac03f', 'k0095', 'k0095', '2', '2', '', 'running', null, null, null, null, null, null, '1', null, null, null, null);
INSERT INTO `jbos_emp` VALUES ('cc79fb2a-f081-48bc-9ad1-5272fc2a064d', 'k0011', 'k0011', '2', '2', '', 'running', null, null, null, null, null, null, '1', null, null, null, null);
INSERT INTO `jbos_emp` VALUES ('cd611e49-d622-488b-a6ee-3afa5606f931', 'k0091', '张三', '2', '2', '100', 'running', null, null, null, null, null, null, '1', null, null, null, null);
INSERT INTO `jbos_emp` VALUES ('f4662ed0-b84c-4b12-ac3d-ffa5421b6df0', 'k0093', '王二', '2', '2', '100', 'running', null, null, null, null, null, null, '1', null, null, null, null);

-- ----------------------------
-- Table structure for jbos_func
-- ----------------------------
DROP TABLE IF EXISTS `jbos_func`;
CREATE TABLE `jbos_func` (
  `ID` varchar(64) NOT NULL COMMENT 'ID',
  `PARENTID` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '父ID',
  `FUNCCODE` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '功能编码',
  `FUNCNAME` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '功能名称',
  `FUNCTYPE` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '功能类型(01：目录；02：功能)',
  `FUNCPATH` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '功能路径',
  `FUNCURL` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '功能URL',
  `ORDERNO` decimal(3,0) DEFAULT NULL COMMENT '排序号',
  `ISVALID` decimal(1,0) DEFAULT '1' COMMENT '是否有效(1：是；0：否)',
  `CREATEUSERID` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建用户',
  `CREATETIME` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `UPDATEUSERID` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '更新用户',
  `UPDATETIME` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='功能权限表';

-- ----------------------------
-- Records of jbos_func
-- ----------------------------
INSERT INTO `jbos_func` VALUES ('0006ba5a-4f38-4369-aaa6-86ab9227af97', '265cfe1f-71e3-4a0f-883b-c8b146ff06bd', 'memberList', '会员列表', '1', null, 'mm/memberMgr', '1', '1', null, null, null, null);
INSERT INTO `jbos_func` VALUES ('1', '0', 'sysManage', '系统管理', '0', '', null, '1', '1', null, null, null, null);
INSERT INTO `jbos_func` VALUES ('19855a0f-f067-4370-812b-8ae84a80ce3b', '0', 'productMgr', '商品管理', '0', null, '', '3', '1', null, null, null, null);
INSERT INTO `jbos_func` VALUES ('265cfe1f-71e3-4a0f-883b-c8b146ff06bd', '0', 'memberMgr', '会员管理', '0', null, '', '2', '1', null, null, null, null);
INSERT INTO `jbos_func` VALUES ('3', '1', 'orgManage', '机构管理', '0', '', 'sm/orgMgr', '1', '1', null, null, null, null);
INSERT INTO `jbos_func` VALUES ('4', '1', 'funcManage', '功能管理', '1', '', 'sm/funcMgr', '2', '1', null, null, null, null);
INSERT INTO `jbos_func` VALUES ('4b0626f3-b6f6-412d-8335-752a67c9e5a7', '619fe121-1bbe-473c-9f54-181afb594288', 'orderAfterSale', '售后订单', '1', null, '', '2', '1', null, null, null, null);
INSERT INTO `jbos_func` VALUES ('5', '1', 'roleManage', '角色管理', '1', '', 'sm/roleMgr', '3', '1', null, null, null, null);
INSERT INTO `jbos_func` VALUES ('5c110bba-8965-4f69-82eb-89fe2bf4f83c', '8', 'picStorage', '图片空间', '1', null, '', '1', '1', null, null, null, null);
INSERT INTO `jbos_func` VALUES ('6', '1', 'dataDict', '数据字典', '1', '', 'sm/dictMgr', '4', '1', null, null, null, null);
INSERT INTO `jbos_func` VALUES ('619fe121-1bbe-473c-9f54-181afb594288', '0', 'orderMgr', '订单管理', '0', null, '', '4', '1', null, null, null, null);
INSERT INTO `jbos_func` VALUES ('7b373d32-4531-4ff1-a849-543aa8e12115', '8', 'materialMgr', '物品管理', '1', '', 'im/materialMgr/index', '3', '1', null, null, null, null);
INSERT INTO `jbos_func` VALUES ('8', '0', 'unionMgr', '综合管理', '0', '', null, '99', '1', null, null, null, null);
INSERT INTO `jbos_func` VALUES ('87dba888-6aa3-43a9-8452-4f812f6a912e', '19855a0f-f067-4370-812b-8ae84a80ce3b', 'propertyCategory', '属性规格', '1', null, '', '3', '1', null, null, null, null);
INSERT INTO `jbos_func` VALUES ('92b6529e-9cc7-4097-bd9c-dc61275d6d4c', '8', 'regionInfo', '地区信息', '1', null, '', '2', '1', null, null, null, null);
INSERT INTO `jbos_func` VALUES ('95642962-c91f-4ea3-8ede-0cb739f45239', '19855a0f-f067-4370-812b-8ae84a80ce3b', 'productList', '商品列表', '1', null, 'pm/productMgr', '1', '1', null, null, null, null);
INSERT INTO `jbos_func` VALUES ('9839ae20-586b-4843-a100-f80e084e5ddd', '265cfe1f-71e3-4a0f-883b-c8b146ff06bd', 'intergalMgr', '积分管理', '1', null, '', '2', '1', null, null, null, null);
INSERT INTO `jbos_func` VALUES ('a991bf73-ea2e-45e0-9171-db88caf2da6f', '19855a0f-f067-4370-812b-8ae84a80ce3b', 'productCategory', '商品分类', '1', null, 'pm/categoryMgr', '2', '1', null, null, null, null);
INSERT INTO `jbos_func` VALUES ('dc5894bc-1483-4d62-83c7-2022d03e93b2', '7b373d32-4531-4ff1-a849-543aa8e12115', 'materialInStore', '物品领用', '1', null, 'sm/dictMgr/index', '2', '1', null, null, null, null);
INSERT INTO `jbos_func` VALUES ('e902b29a-5f43-4531-8c26-8bdeb379a887', '7b373d32-4531-4ff1-a849-543aa8e12115', 'materialBuy', '物品采购', '1', '', 'im/materialMgr/materialBuy/list', '1', '1', null, null, null, null);
INSERT INTO `jbos_func` VALUES ('eb6e72f2-6faf-45c8-9141-42820c9a6cb1', '619fe121-1bbe-473c-9f54-181afb594288', 'orderList', '订单列表', '1', null, '', '1', '1', null, null, null, null);

-- ----------------------------
-- Table structure for jbos_org
-- ----------------------------
DROP TABLE IF EXISTS `jbos_org`;
CREATE TABLE `jbos_org` (
  `ID` varchar(64) NOT NULL COMMENT 'ID',
  `PARENTID` varchar(64) DEFAULT NULL COMMENT '父ID',
  `ORGCODE` varchar(32) DEFAULT NULL COMMENT '机构编码',
  `ORGNAME` varchar(128) DEFAULT NULL COMMENT '机构名称',
  `ORGLEVEL` varchar(16) DEFAULT NULL COMMENT '机构级别',
  `ORGCHARGE` varchar(64) DEFAULT NULL COMMENT '机构负责人',
  `DIRECTOR` varchar(64) DEFAULT NULL COMMENT '分管领导',
  `EFFECTDATE` timestamp NULL DEFAULT NULL COMMENT '生效日期',
  `DISABLEDDATE` timestamp NULL DEFAULT NULL COMMENT '失效日期',
  `ORDERNO` int DEFAULT NULL COMMENT '机构排序',
  `REMARK` varchar(256) DEFAULT NULL COMMENT '备注',
  `ISVALID` int DEFAULT '1' COMMENT '是否有效(1：是；0：否)',
  `CREATEUSERID` varchar(64) DEFAULT NULL COMMENT '创建用户',
  `CREATETIME` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `UPDATEUSERID` varchar(64) DEFAULT NULL COMMENT '更新用户',
  `UPDATETIME` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='组织机构表';

-- ----------------------------
-- Records of jbos_org
-- ----------------------------
INSERT INTO `jbos_org` VALUES ('1', '0', '10000', '宁波银行', null, null, null, null, null, '1', null, '1', null, null, null, null);
INSERT INTO `jbos_org` VALUES ('2', '1', '10101', '总行', null, null, null, null, null, '1', null, '1', null, null, null, null);
INSERT INTO `jbos_org` VALUES ('5', '1', '10102', '宁波地区支行', null, null, null, null, null, '2', null, '1', null, null, null, null);

-- ----------------------------
-- Table structure for jbos_process_detail
-- ----------------------------
DROP TABLE IF EXISTS `jbos_process_detail`;
CREATE TABLE `jbos_process_detail` (
  `ID` varchar(64) NOT NULL COMMENT 'ID',
  `PROC_DEF_KEY` varchar(256) DEFAULT NULL COMMENT '流程定义名称',
  `BUSSINESS_KEY` varchar(256) DEFAULT NULL COMMENT '业务名称',
  `PROC_INST_ID` varchar(64) DEFAULT NULL COMMENT '实例ID',
  `TASK_ID` varchar(64) DEFAULT NULL COMMENT '任务ID',
  `ACT_ID` varchar(64) DEFAULT NULL COMMENT '活动ID',
  `ACT_NAME` varchar(64) DEFAULT NULL COMMENT '活动名称',
  `ACT_INST_ID` varchar(64) DEFAULT NULL COMMENT '活动实例ID',
  `ASSIGNEE` varchar(32) DEFAULT NULL COMMENT '领取人',
  `ROUTE_URL` varchar(256) DEFAULT NULL COMMENT '路由URL',
  `IS_ACTIVE` int DEFAULT NULL COMMENT '是否活动',
  `START_TIME` timestamp NULL DEFAULT NULL COMMENT '开始时间',
  `END_TIME` timestamp NULL DEFAULT NULL COMMENT '结束时间',
  `APPROVE_OPINION` varchar(512) DEFAULT NULL COMMENT '审批意见',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='流程工作项表';

-- ----------------------------
-- Records of jbos_process_detail
-- ----------------------------

-- ----------------------------
-- Table structure for jbos_role
-- ----------------------------
DROP TABLE IF EXISTS `jbos_role`;
CREATE TABLE `jbos_role` (
  `ID` varchar(64) NOT NULL COMMENT 'ID',
  `PARENTID` varchar(64) DEFAULT NULL COMMENT '父ID',
  `ROLECODE` varchar(64) DEFAULT NULL COMMENT '角色编码',
  `ROLENAME` varchar(64) DEFAULT NULL COMMENT '角色名称',
  `ROLETYPE` varchar(16) DEFAULT NULL COMMENT '角色类型',
  `ISVALID` decimal(1,0) DEFAULT '1' COMMENT '是否有效(1：是；0：否)',
  `CREATEUSERID` varchar(64) DEFAULT NULL COMMENT '创建用户',
  `CREATETIME` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `UPDATEUSERID` varchar(64) DEFAULT NULL COMMENT '更新用户',
  `UPDATETIME` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统角色表';

-- ----------------------------
-- Records of jbos_role
-- ----------------------------
INSERT INTO `jbos_role` VALUES ('0fc09c65-7667-450c-bcb3-a5a9f2350e7e', '3', 'ROLE_SUPERADMIN', '超级管理员', '100', '1', null, null, null, null);
INSERT INTO `jbos_role` VALUES ('1a9a03cc-4313-4e88-8b30-1dd3c1817e7c', '0fc09c65-7667-450c-bcb3-a5a9f2350e7e', 'ROLE_DEFAULT', '默认角色', '100', '1', null, null, null, null);

-- ----------------------------
-- Table structure for jbos_role_func
-- ----------------------------
DROP TABLE IF EXISTS `jbos_role_func`;
CREATE TABLE `jbos_role_func` (
  `ROLEID` varchar(64) NOT NULL COMMENT '角色ID',
  `FUNCID` varchar(64) NOT NULL COMMENT '功能ID',
  PRIMARY KEY (`ROLEID`,`FUNCID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色功能表';

-- ----------------------------
-- Records of jbos_role_func
-- ----------------------------
INSERT INTO `jbos_role_func` VALUES ('0fc09c65-7667-450c-bcb3-a5a9f2350e7e', '1');
INSERT INTO `jbos_role_func` VALUES ('0fc09c65-7667-450c-bcb3-a5a9f2350e7e', '3');
INSERT INTO `jbos_role_func` VALUES ('0fc09c65-7667-450c-bcb3-a5a9f2350e7e', '4');
INSERT INTO `jbos_role_func` VALUES ('0fc09c65-7667-450c-bcb3-a5a9f2350e7e', '444635b2-1e97-4928-b785-ffc89b756541');
INSERT INTO `jbos_role_func` VALUES ('0fc09c65-7667-450c-bcb3-a5a9f2350e7e', '4b57002b-0f5b-4012-b0b6-bddd27e709eb');
INSERT INTO `jbos_role_func` VALUES ('0fc09c65-7667-450c-bcb3-a5a9f2350e7e', '5');
INSERT INTO `jbos_role_func` VALUES ('0fc09c65-7667-450c-bcb3-a5a9f2350e7e', '6');
INSERT INTO `jbos_role_func` VALUES ('0fc09c65-7667-450c-bcb3-a5a9f2350e7e', '7');
INSERT INTO `jbos_role_func` VALUES ('0fc09c65-7667-450c-bcb3-a5a9f2350e7e', '7b373d32-4531-4ff1-a849-543aa8e12115');
INSERT INTO `jbos_role_func` VALUES ('0fc09c65-7667-450c-bcb3-a5a9f2350e7e', '8');
INSERT INTO `jbos_role_func` VALUES ('0fc09c65-7667-450c-bcb3-a5a9f2350e7e', 'bc9f33f0-c8b8-4532-809c-15a2c9547a63');
INSERT INTO `jbos_role_func` VALUES ('0fc09c65-7667-450c-bcb3-a5a9f2350e7e', 'dc5894bc-1483-4d62-83c7-2022d03e93b2');
INSERT INTO `jbos_role_func` VALUES ('0fc09c65-7667-450c-bcb3-a5a9f2350e7e', 'e902b29a-5f43-4531-8c26-8bdeb379a887');
INSERT INTO `jbos_role_func` VALUES ('1a9a03cc-4313-4e88-8b30-1dd3c1817e7c', '19855a0f-f067-4370-812b-8ae84a80ce3b');
INSERT INTO `jbos_role_func` VALUES ('1a9a03cc-4313-4e88-8b30-1dd3c1817e7c', '265cfe1f-71e3-4a0f-883b-c8b146ff06bd');
INSERT INTO `jbos_role_func` VALUES ('1a9a03cc-4313-4e88-8b30-1dd3c1817e7c', '4b57002b-0f5b-4012-b0b6-bddd27e709eb');
INSERT INTO `jbos_role_func` VALUES ('1a9a03cc-4313-4e88-8b30-1dd3c1817e7c', '7b373d32-4531-4ff1-a849-543aa8e12115');
INSERT INTO `jbos_role_func` VALUES ('1a9a03cc-4313-4e88-8b30-1dd3c1817e7c', '8');
INSERT INTO `jbos_role_func` VALUES ('1a9a03cc-4313-4e88-8b30-1dd3c1817e7c', 'dc5894bc-1483-4d62-83c7-2022d03e93b2');
INSERT INTO `jbos_role_func` VALUES ('1a9a03cc-4313-4e88-8b30-1dd3c1817e7c', 'e902b29a-5f43-4531-8c26-8bdeb379a887');

-- ----------------------------
-- Table structure for jbos_user
-- ----------------------------
DROP TABLE IF EXISTS `jbos_user`;
CREATE TABLE `jbos_user` (
  `ID` varchar(64) NOT NULL COMMENT 'ID',
  `USERNAME` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '登录名称',
  `NICKNAME` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用户名称',
  `PASSWORD` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '登录密码',
  `SALT` varchar(128) DEFAULT NULL COMMENT '密码盐值',
  `USERSTATUS` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '01' COMMENT '用户状态(01：启用：02：锁定)',
  `ISVALID` decimal(1,0) DEFAULT '1' COMMENT '是否有效(1：是；0：否)',
  `CREATEUSERID` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建用户',
  `CREATETIME` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `UPDATEUSERID` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '更新用户',
  `UPDATETIME` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of jbos_user
-- ----------------------------
INSERT INTO `jbos_user` VALUES ('1', 'admin', ' 超级管理员', '$2a$10$VgsT9WTpLtoyJIaH21aIEe5EK5AHvGEoJmYUJM7S0lstdeMjjUCjG', '7TL5Lu1ylNVuZh3Ynmus', '01', '1', null, null, null, null);
INSERT INTO `jbos_user` VALUES ('1b15b830-e01e-46b0-9f77-cbedb5745e0a', '152581605641', '1', '$2a$10$6eUnUqjegd3E4L8kwlBZjO2w3rimD0LwZYc2GQSE3V/AUT4nzMdkS', 'TTMuIcn5zElzTfUZOQHD', 'running', '1', null, null, null, null);
INSERT INTO `jbos_user` VALUES ('26cb451b-c835-44a7-82a9-a1f1edb58f22', null, null, '$2a$10$ey8y6atYxWk.3TC4SjWR/undXl68re1a9jC8SPBYSWlNS9wtzqJFm', 'YcOoLWRDKva3RvN0phvE', 'running', '1', null, null, null, null);
INSERT INTO `jbos_user` VALUES ('3e3b17c2-32ff-4d38-8cf0-a627fffaae70', 'k0100', 'k0100', '$2a$10$VgsT9WTpLtoyJIaH21aIEe5EK5AHvGEoJmYUJM7S0lstdeMjjUCjG', 'gHFDNf6kmvmfFNITGODc', 'running', '1', null, null, null, null);
INSERT INTO `jbos_user` VALUES ('57c3cb7f-bf05-4660-858d-50b1246582f2', 'k0097', 'k0097', '$2a$10$VgsT9WTpLtoyJIaH21aIEe5EK5AHvGEoJmYUJM7S0lstdeMjjUCjG', 'UuTKz7EJXk3CwAguPelU', 'running', '1', null, null, null, null);
INSERT INTO `jbos_user` VALUES ('82470e24-4f71-4c75-8b7e-ff228d58e0f5', 'k0013', 'k0013', '$2a$10$VgsT9WTpLtoyJIaH21aIEe5EK5AHvGEoJmYUJM7S0lstdeMjjUCjG', 'Mkbwjakz05G3TqXBdAjM', 'running', '1', null, null, null, null);
INSERT INTO `jbos_user` VALUES ('885ad986-af51-4b7d-ad49-f48a38d6ed09', 'l0091', 'l0091', '$2a$10$ryvsWPYDmJemMEqC4DcL3ec8Z2ZiVUh1gCg3DgoIZcHP9DxeR5CIi', 'beY9Ea7RMlochJgST0Iz', 'running', '1', null, null, null, null);
INSERT INTO `jbos_user` VALUES ('917782f7-82d4-4510-9e5e-b5de2d25f89c', 'k0010', 'k0010', '$2a$10$VgsT9WTpLtoyJIaH21aIEe5EK5AHvGEoJmYUJM7S0lstdeMjjUCjG', 'yHMUc7TvVl0s8kLuchxn', 'running', '1', null, null, null, null);
INSERT INTO `jbos_user` VALUES ('a76393f9-7624-4b75-8130-29da1cdd276b', 'k0096', 'k0096', '$2a$10$VgsT9WTpLtoyJIaH21aIEe5EK5AHvGEoJmYUJM7S0lstdeMjjUCjG', 'OsxfNsDwHhpmy5tWtBOy', 'running', '1', null, null, null, null);
INSERT INTO `jbos_user` VALUES ('bd388b87-7181-43b8-86f0-869e49107105', 'k0093', '王二', '$2a$10$VgsT9WTpLtoyJIaH21aIEe5EK5AHvGEoJmYUJM7S0lstdeMjjUCjG', 'sCNaFmqinBOmFzFvYXQR', 'running', '1', null, null, null, null);
INSERT INTO `jbos_user` VALUES ('c7e6cf50-70c0-45cc-8ff0-7d2b727ab355', '18968365610', '18968365610', '$2a$10$NHNxNMesURUZpZHFD0c5QuYRSL1CWSZK7URXncsdERRKauooXbJHe', '3ZCkyYEQppLZZgNaW6rW', 'running', '1', null, null, null, null);
INSERT INTO `jbos_user` VALUES ('f3fb305b-bed2-4cf0-8a3e-8db7dc26230c', '15258160564', '15258160564', '$2a$10$XNONv2Vyr2.OZp2.KnB4/.rWGsVmbNuTxXRfQmGcU9QJWGltdl4B2', 'P9vCIAEjeXf15T41Q94w', 'running', '1', null, null, null, null);

-- ----------------------------
-- Table structure for jbos_user_role
-- ----------------------------
DROP TABLE IF EXISTS `jbos_user_role`;
CREATE TABLE `jbos_user_role` (
  `USERID` varchar(64) NOT NULL COMMENT '用户ID',
  `ROLEID` varchar(64) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`ROLEID`,`USERID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色表';

-- ----------------------------
-- Records of jbos_user_role
-- ----------------------------
INSERT INTO `jbos_user_role` VALUES ('0d6e89af-737a-4dbe-bead-9d20e5db3d55', '1a9a03cc-4313-4e88-8b30-1dd3c1817e7c');
INSERT INTO `jbos_user_role` VALUES ('1b15b830-e01e-46b0-9f77-cbedb5745e0a', '1a9a03cc-4313-4e88-8b30-1dd3c1817e7c');
INSERT INTO `jbos_user_role` VALUES ('26cb451b-c835-44a7-82a9-a1f1edb58f22', '1a9a03cc-4313-4e88-8b30-1dd3c1817e7c');
INSERT INTO `jbos_user_role` VALUES ('56381b33-7cae-4125-9f84-6d5944cc0748', '1a9a03cc-4313-4e88-8b30-1dd3c1817e7c');
INSERT INTO `jbos_user_role` VALUES ('57c3cb7f-bf05-4660-858d-50b1246582f2', '1a9a03cc-4313-4e88-8b30-1dd3c1817e7c');
INSERT INTO `jbos_user_role` VALUES ('82470e24-4f71-4c75-8b7e-ff228d58e0f5', '1a9a03cc-4313-4e88-8b30-1dd3c1817e7c');
INSERT INTO `jbos_user_role` VALUES ('917782f7-82d4-4510-9e5e-b5de2d25f89c', '1a9a03cc-4313-4e88-8b30-1dd3c1817e7c');
INSERT INTO `jbos_user_role` VALUES ('a76393f9-7624-4b75-8130-29da1cdd276b', '1a9a03cc-4313-4e88-8b30-1dd3c1817e7c');
INSERT INTO `jbos_user_role` VALUES ('bd388b87-7181-43b8-86f0-869e49107105', '1a9a03cc-4313-4e88-8b30-1dd3c1817e7c');
INSERT INTO `jbos_user_role` VALUES ('c7e6cf50-70c0-45cc-8ff0-7d2b727ab355', '1a9a03cc-4313-4e88-8b30-1dd3c1817e7c');
INSERT INTO `jbos_user_role` VALUES ('caef9062-581f-43d0-9cbe-0f89a6fac03f', '1a9a03cc-4313-4e88-8b30-1dd3c1817e7c');
INSERT INTO `jbos_user_role` VALUES ('cc79fb2a-f081-48bc-9ad1-5272fc2a064d', '1a9a03cc-4313-4e88-8b30-1dd3c1817e7c');
INSERT INTO `jbos_user_role` VALUES ('cd611e49-d622-488b-a6ee-3afa5606f931', '1a9a03cc-4313-4e88-8b30-1dd3c1817e7c');
INSERT INTO `jbos_user_role` VALUES ('f3fb305b-bed2-4cf0-8a3e-8db7dc26230c', '1a9a03cc-4313-4e88-8b30-1dd3c1817e7c');
INSERT INTO `jbos_user_role` VALUES ('f4662ed0-b84c-4b12-ac3d-ffa5421b6df0', '1a9a03cc-4313-4e88-8b30-1dd3c1817e7c');

-- ----------------------------
-- Table structure for jbos_user_token
-- ----------------------------
DROP TABLE IF EXISTS `jbos_user_token`;
CREATE TABLE `jbos_user_token` (
  `USERID` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户ID',
  `TOKEN` varchar(128) DEFAULT NULL COMMENT '令牌',
  `EXPIRETIME` timestamp NULL DEFAULT NULL COMMENT '到期时间',
  `UPDATETIME` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`USERID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of jbos_user_token
-- ----------------------------
INSERT INTO `jbos_user_token` VALUES ('1', 'be3db088cd8d9cea1a81d22d0eb9dab26d367a0d8ede822f7aba4cf89dc3c961', '2022-12-29 19:58:29', '2022-12-29 07:58:29');

-- ----------------------------
-- Table structure for mm_account
-- ----------------------------
DROP TABLE IF EXISTS `mm_account`;
CREATE TABLE `mm_account` (
  `seq_id` varchar(128) NOT NULL COMMENT '序列号',
  `account` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '账号',
  `password` varchar(64) DEFAULT NULL COMMENT '密码',
  `status` varchar(8) DEFAULT NULL COMMENT '状态(10：启用；20：锁定)',
  `is_valid` int DEFAULT '1' COMMENT '是否有效(1:有效;0:无效)',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`seq_id`,`account`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='会员账号表';

-- ----------------------------
-- Records of mm_account
-- ----------------------------
INSERT INTO `mm_account` VALUES ('05a9fab1-9b21-4351-8122-a044a30d5f2c', 'admin', '$2a$10$jeGUXJav9/QSUlDuej3B3O2XJCKGejNsBtvjM8psfRhQp4CHOfemO', '10', '1', null, null);
INSERT INTO `mm_account` VALUES ('132ac1e8-b3a6-4fa6-bff7-d429f8bda84d', '15258160564', '$2a$10$T8Kd9GXpVJKPpfPLI8Ffw.u2ExZSx2wKTXAel6KrpHbVsPY2zqHcq', '10', '1', null, null);
INSERT INTO `mm_account` VALUES ('3f22eada-39d5-4916-9904-a5c4de9e0190', '152581605641', '$2a$10$nHySSFkm13gvuHhc7JNfZ.T5DXCXjPFt60gBC6SIyka7sC2a2sIp.', '10', '1', null, null);
INSERT INTO `mm_account` VALUES ('f26d567c-a23c-4df2-b1a4-1004426508dd', '18968365610', '$2a$10$ioat6AK2fHhhU9MrP3gRSuWuscM001K62cO5wRXcgjy9rT8eCMDCq', '10', '1', null, null);

-- ----------------------------
-- Table structure for mm_member_base
-- ----------------------------
DROP TABLE IF EXISTS `mm_member_base`;
CREATE TABLE `mm_member_base` (
  `seq_id` varchar(128) NOT NULL COMMENT '序列号',
  `account` varchar(128) DEFAULT NULL COMMENT '账号',
  `nick_name` varchar(128) DEFAULT NULL COMMENT '昵称',
  `full_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '姓名',
  `sex` int DEFAULT NULL COMMENT '性别',
  `mobile_phone` varchar(16) DEFAULT NULL COMMENT '手机号码',
  `email` varchar(64) DEFAULT NULL COMMENT '邮箱',
  `grade` varchar(8) DEFAULT NULL COMMENT '会员等级',
  `integral` int DEFAULT NULL COMMENT '会员积分',
  `registry_time` timestamp NULL DEFAULT NULL COMMENT '注册时间',
  `is_valid` int DEFAULT '1' COMMENT '是否有效(1:有效;0:无效)',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`seq_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='会员信息表';

-- ----------------------------
-- Records of mm_member_base
-- ----------------------------
INSERT INTO `mm_member_base` VALUES ('05a9fab1-9b21-4351-8122-a044a30d5f2c', 'admin', 'admin', '管理员', '1', '15258160564', 'admin@gmail.com', '99', null, '2023-02-22 13:39:49', '1', null, null);
INSERT INTO `mm_member_base` VALUES ('132ac1e8-b3a6-4fa6-bff7-d429f8bda84d', '15258160564', 'GIF', '汪有富', '1', '15258160564', 'youfu.wang@gmail.com', '10', null, '2023-02-22 13:39:52', '1', null, null);
INSERT INTO `mm_member_base` VALUES ('207e5021-0489-4127-bc9f-d92e6f4b3ea4', '18968365610', 'zhanshang', '张三', '1', '18968365610', null, '10', null, '2021-11-18 16:48:47', '1', '2021-11-18 16:48:47', null);
INSERT INTO `mm_member_base` VALUES ('92091176-8193-4b29-b8ca-93cd60dfc5d9', '152581605641', 'lishi', '李四', '1', '152581605641', null, '10', null, '2021-11-18 16:55:02', '1', '2021-11-18 16:55:02', null);

-- ----------------------------
-- Table structure for mm_receipt_address
-- ----------------------------
DROP TABLE IF EXISTS `mm_receipt_address`;
CREATE TABLE `mm_receipt_address` (
  `seq_id` varchar(128) NOT NULL COMMENT '序列号',
  `receipt_name` varchar(128) DEFAULT NULL COMMENT '收货人',
  `mobile_phone` varchar(32) DEFAULT NULL COMMENT '手机号码',
  `rgon_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '地区',
  `address` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '详细地址',
  `post_code` varchar(16) DEFAULT NULL COMMENT '邮政编码',
  `is_default` int DEFAULT NULL COMMENT '默认地址',
  `is_valid` int DEFAULT '1' COMMENT '是否有效(1:有效;0:无效)',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`seq_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='收货地址表';

-- ----------------------------
-- Records of mm_receipt_address
-- ----------------------------

-- ----------------------------
-- Table structure for oauth_access_token
-- ----------------------------
DROP TABLE IF EXISTS `oauth_access_token`;
CREATE TABLE `oauth_access_token` (
  `token_id` varchar(256) DEFAULT NULL,
  `token` blob NOT NULL,
  `authentication_id` varchar(256) NOT NULL,
  `user_name` varchar(256) DEFAULT NULL,
  `client_id` varchar(256) DEFAULT NULL,
  `authentication` blob,
  `refresh_token` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`authentication_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of oauth_access_token
-- ----------------------------
INSERT INTO `oauth_access_token` VALUES ('09003633a526c32580e75d80efc5465f', 0xACED0005737200436F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E636F6D6D6F6E2E44656661756C744F4175746832416363657373546F6B656E0CB29E361B24FACE0200064C00156164646974696F6E616C496E666F726D6174696F6E74000F4C6A6176612F7574696C2F4D61703B4C000A65787069726174696F6E7400104C6A6176612F7574696C2F446174653B4C000C72656672657368546F6B656E74003F4C6F72672F737072696E676672616D65776F726B2F73656375726974792F6F61757468322F636F6D6D6F6E2F4F417574683252656672657368546F6B656E3B4C000573636F706574000F4C6A6176612F7574696C2F5365743B4C0009746F6B656E547970657400124C6A6176612F6C616E672F537472696E673B4C000576616C756571007E00057870737200176A6176612E7574696C2E4C696E6B6564486173684D617034C04E5C106CC0FB0200015A000B6163636573734F72646572787200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C770800000010000000017400036A746974002433646161363665622D666665632D343962642D613666372D34353431653236633864326478007372000E6A6176612E7574696C2E44617465686A81014B597419030000787077080000017B1A3566F9787372004C6F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E636F6D6D6F6E2E44656661756C744578706972696E674F417574683252656672657368546F6B656E2FDF47639DD0C9B70200014C000A65787069726174696F6E71007E0002787200446F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E636F6D6D6F6E2E44656661756C744F417574683252656672657368546F6B656E73E10E0A6354D45E0200014C000576616C756571007E0005787074011065794A68624763694F694A49557A49314E694973496E523563434936496B705856434A392E65794A7A593239775A53493657794A68624777695853776959585270496A6F694D325268595459325A5749745A6D5A6C597930304F574A6B4C5745325A6A63744E4455304D5755794E6D4D345A444A6B496977695A586877496A6F784E6A49344F444D7A4E5445794C434A7164476B694F694A6B4F54637A595451784E43316A4F4445324C54526B596A5574595749774E5330354E7A59344F47526B4F44686D5A5467694C434A6A62476C6C626E5266615751694F694A71596D397A496E302E6E616D785F67396657706B77376B433178686B765A4A573148434F567A7757366462584E497472533573637371007E000C77080000017B3E0AFC7478737200256A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C65536574801D92D18F9B80550200007872002C6A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C65436F6C6C656374696F6E19420080CB5EF71E0200014C0001637400164C6A6176612F7574696C2F436F6C6C656374696F6E3B7870737200176A6176612E7574696C2E4C696E6B656448617368536574D86CD75A95DD2A1E020000787200116A6176612E7574696C2E48617368536574BA44859596B8B7340300007870770C000000023F40000000000001740003616C6C787400066265617265727400D465794A68624763694F694A49557A49314E694973496E523563434936496B705856434A392E65794A7A593239775A53493657794A6862477769585377695A586877496A6F784E6A49344D6A4D794D7A45794C434A7164476B694F69497A5A4746684E6A5A6C5969316D5A6D566A4C545135596D517459545A6D4E7930304E5451785A544932597A686B4D6D51694C434A6A62476C6C626E5266615751694F694A71596D397A496E302E6A657767714A7A546F434433566C737A4931704D73303048734E344D53715A50766D49454A5338666D6734, '8b0afe3f162a05f80b07a120d54e51e8', null, 'jbos', 0xACED0005737200416F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E70726F76696465722E4F417574683241757468656E7469636174696F6EBD400B02166252130200024C000D73746F7265645265717565737474003C4C6F72672F737072696E676672616D65776F726B2F73656375726974792F6F61757468322F70726F76696465722F4F4175746832526571756573743B4C00127573657241757468656E7469636174696F6E7400324C6F72672F737072696E676672616D65776F726B2F73656375726974792F636F72652F41757468656E7469636174696F6E3B787200476F72672E737072696E676672616D65776F726B2E73656375726974792E61757468656E7469636174696F6E2E416273747261637441757468656E7469636174696F6E546F6B656ED3AA287E6E47640E0200035A000D61757468656E746963617465644C000B617574686F7269746965737400164C6A6176612F7574696C2F436F6C6C656374696F6E3B4C000764657461696C737400124C6A6176612F6C616E672F4F626A6563743B787000737200266A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C654C697374FC0F2531B5EC8E100200014C00046C6973747400104C6A6176612F7574696C2F4C6973743B7872002C6A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C65436F6C6C656374696F6E19420080CB5EF71E0200014C00016371007E00047870737200136A6176612E7574696C2E41727261794C6973747881D21D99C7619D03000149000473697A657870000000007704000000007871007E000C707372003A6F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E70726F76696465722E4F41757468325265717565737400000000000000010200075A0008617070726F7665644C000B617574686F72697469657371007E00044C000A657874656E73696F6E7374000F4C6A6176612F7574696C2F4D61703B4C000B72656469726563745572697400124C6A6176612F6C616E672F537472696E673B4C00077265667265736874003B4C6F72672F737072696E676672616D65776F726B2F73656375726974792F6F61757468322F70726F76696465722F546F6B656E526571756573743B4C000B7265736F7572636549647374000F4C6A6176612F7574696C2F5365743B4C000D726573706F6E7365547970657371007E0011787200386F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E70726F76696465722E426173655265717565737436287A3EA37169BD0200034C0008636C69656E74496471007E000F4C001172657175657374506172616D657465727371007E000E4C000573636F706571007E001178707400046A626F73737200256A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C654D6170F1A5A8FE74F507420200014C00016D71007E000E7870737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F400000000000067708000000080000000374000A6772616E745F74797065740012636C69656E745F63726564656E7469616C73740009636C69656E745F69647400046A626F7374000573636F7065740003616C6C78737200256A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C65536574801D92D18F9B80550200007871007E0009737200176A6176612E7574696C2E4C696E6B656448617368536574D86CD75A95DD2A1E020000787200116A6176612E7574696C2E48617368536574BA44859596B8B7340300007870770C000000103F4000000000000171007E001E78017371007E0022770C000000103F40000000000000787371007E00173F40000000000000770800000010000000007870707371007E0022770C000000103F40000000000000787371007E0022770C000000103F400000000000007870, '5de2148d4f2c92c7056b0c8d3ac2b0ac');

-- ----------------------------
-- Table structure for oauth_client_details
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client_details`;
CREATE TABLE `oauth_client_details` (
  `CLIENT_ID` varchar(128) NOT NULL,
  `CLIENT_SECRET` varchar(256) DEFAULT NULL COMMENT '客户端密钥',
  `RESOURCE_IDS` varchar(256) DEFAULT NULL COMMENT '资源ID',
  `AUTHORIZED_GRANT_TYPES` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '授权类型',
  `AUTHORITIES` varchar(256) DEFAULT NULL COMMENT '权限标识',
  `ACCESS_TOKEN_VALIDITY` int DEFAULT NULL COMMENT '读取令牌有效期',
  `REFRESH_TOKEN_VALIDITY` int DEFAULT NULL COMMENT '刷新令牌有效期',
  `WEB_SERVER_REDIRECT_URI` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '跳转URI',
  `AUTOAPPROVE` varchar(32) DEFAULT NULL COMMENT '自动许可',
  `SCOPE` varchar(32) DEFAULT NULL COMMENT '范围',
  `additional_information` varchar(2048) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`CLIENT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of oauth_client_details
-- ----------------------------
INSERT INTO `oauth_client_details` VALUES ('jbos', '$2a$10$jXHuLSZBHWY7n.PxiwixmuSW7JTs920tJ3avcA0UUl6ssaHTPB1ka', null, 'client_credentials,refresh_token', null, '3600', '604800', 'http://baidu.com', 'true', 'all', null);
INSERT INTO `oauth_client_details` VALUES ('k0091', '$2a$10$jXHuLSZBHWY7n.PxiwixmuSW7JTs920tJ3avcA0UUl6ssaHTPB1ka', null, 'client_credentials,refresh_token', null, '3600', '604800', null, null, 'all', null);

-- ----------------------------
-- Table structure for oauth_refresh_token
-- ----------------------------
DROP TABLE IF EXISTS `oauth_refresh_token`;
CREATE TABLE `oauth_refresh_token` (
  `token_id` varchar(256) DEFAULT NULL,
  `token` blob,
  `authentication` blob
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of oauth_refresh_token
-- ----------------------------
INSERT INTO `oauth_refresh_token` VALUES ('e726af88327bd1aab4181d93709d80c8', 0xACED00057372004C6F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E636F6D6D6F6E2E44656661756C744578706972696E674F417574683252656672657368546F6B656E2FDF47639DD0C9B70200014C000A65787069726174696F6E7400104C6A6176612F7574696C2F446174653B787200446F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E636F6D6D6F6E2E44656661756C744F417574683252656672657368546F6B656E73E10E0A6354D45E0200014C000576616C75657400124C6A6176612F6C616E672F537472696E673B787074002465323965623232352D383733382D346562632D393835612D3439373966333335613235647372000E6A6176612E7574696C2E44617465686A81014B597419030000787077080000017A5098A25378, 0xACED0005737200416F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E70726F76696465722E4F417574683241757468656E7469636174696F6EBD400B02166252130200024C000D73746F7265645265717565737474003C4C6F72672F737072696E676672616D65776F726B2F73656375726974792F6F61757468322F70726F76696465722F4F4175746832526571756573743B4C00127573657241757468656E7469636174696F6E7400324C6F72672F737072696E676672616D65776F726B2F73656375726974792F636F72652F41757468656E7469636174696F6E3B787200476F72672E737072696E676672616D65776F726B2E73656375726974792E61757468656E7469636174696F6E2E416273747261637441757468656E7469636174696F6E546F6B656ED3AA287E6E47640E0200035A000D61757468656E746963617465644C000B617574686F7269746965737400164C6A6176612F7574696C2F436F6C6C656374696F6E3B4C000764657461696C737400124C6A6176612F6C616E672F4F626A6563743B787000737200266A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C654C697374FC0F2531B5EC8E100200014C00046C6973747400104C6A6176612F7574696C2F4C6973743B7872002C6A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C65436F6C6C656374696F6E19420080CB5EF71E0200014C00016371007E00047870737200136A6176612E7574696C2E41727261794C6973747881D21D99C7619D03000149000473697A657870000000007704000000007871007E000C707372003A6F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E70726F76696465722E4F41757468325265717565737400000000000000010200075A0008617070726F7665644C000B617574686F72697469657371007E00044C000A657874656E73696F6E7374000F4C6A6176612F7574696C2F4D61703B4C000B72656469726563745572697400124C6A6176612F6C616E672F537472696E673B4C00077265667265736874003B4C6F72672F737072696E676672616D65776F726B2F73656375726974792F6F61757468322F70726F76696465722F546F6B656E526571756573743B4C000B7265736F7572636549647374000F4C6A6176612F7574696C2F5365743B4C000D726573706F6E7365547970657371007E0011787200386F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E70726F76696465722E426173655265717565737436287A3EA37169BD0200034C0008636C69656E74496471007E000F4C001172657175657374506172616D657465727371007E000E4C000573636F706571007E001178707400046A626F73737200256A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C654D6170F1A5A8FE74F507420200014C00016D71007E000E7870737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F400000000000067708000000080000000374000A6772616E745F74797065740012636C69656E745F63726564656E7469616C73740009636C69656E745F69647400046A626F7374000573636F7065740003616C6C78737200256A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C65536574801D92D18F9B80550200007871007E0009737200176A6176612E7574696C2E4C696E6B656448617368536574D86CD75A95DD2A1E020000787200116A6176612E7574696C2E48617368536574BA44859596B8B7340300007870770C000000103F4000000000000171007E001E78017371007E0022770C000000103F40000000000000787371007E00173F40000000000000770800000010000000007870707371007E0022770C000000103F40000000000000787371007E0022770C000000103F400000000000007870);
INSERT INTO `oauth_refresh_token` VALUES ('c4f88296941ea61eebf47a761f5471f8', 0xACED00057372004C6F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E636F6D6D6F6E2E44656661756C744578706972696E674F417574683252656672657368546F6B656E2FDF47639DD0C9B70200014C000A65787069726174696F6E7400104C6A6176612F7574696C2F446174653B787200446F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E636F6D6D6F6E2E44656661756C744F417574683252656672657368546F6B656E73E10E0A6354D45E0200014C000576616C75657400124C6A6176612F6C616E672F537472696E673B787074002439663635633730312D653566622D346133362D613630362D3665316665373238633165357372000E6A6176612E7574696C2E44617465686A81014B597419030000787077080000017A51B0EE8078, 0xACED0005737200416F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E70726F76696465722E4F417574683241757468656E7469636174696F6EBD400B02166252130200024C000D73746F7265645265717565737474003C4C6F72672F737072696E676672616D65776F726B2F73656375726974792F6F61757468322F70726F76696465722F4F4175746832526571756573743B4C00127573657241757468656E7469636174696F6E7400324C6F72672F737072696E676672616D65776F726B2F73656375726974792F636F72652F41757468656E7469636174696F6E3B787200476F72672E737072696E676672616D65776F726B2E73656375726974792E61757468656E7469636174696F6E2E416273747261637441757468656E7469636174696F6E546F6B656ED3AA287E6E47640E0200035A000D61757468656E746963617465644C000B617574686F7269746965737400164C6A6176612F7574696C2F436F6C6C656374696F6E3B4C000764657461696C737400124C6A6176612F6C616E672F4F626A6563743B787000737200266A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C654C697374FC0F2531B5EC8E100200014C00046C6973747400104C6A6176612F7574696C2F4C6973743B7872002C6A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C65436F6C6C656374696F6E19420080CB5EF71E0200014C00016371007E00047870737200136A6176612E7574696C2E41727261794C6973747881D21D99C7619D03000149000473697A657870000000007704000000007871007E000C707372003A6F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E70726F76696465722E4F41757468325265717565737400000000000000010200075A0008617070726F7665644C000B617574686F72697469657371007E00044C000A657874656E73696F6E7374000F4C6A6176612F7574696C2F4D61703B4C000B72656469726563745572697400124C6A6176612F6C616E672F537472696E673B4C00077265667265736874003B4C6F72672F737072696E676672616D65776F726B2F73656375726974792F6F61757468322F70726F76696465722F546F6B656E526571756573743B4C000B7265736F7572636549647374000F4C6A6176612F7574696C2F5365743B4C000D726573706F6E7365547970657371007E0011787200386F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E70726F76696465722E426173655265717565737436287A3EA37169BD0200034C0008636C69656E74496471007E000F4C001172657175657374506172616D657465727371007E000E4C000573636F706571007E001178707400046A626F73737200256A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C654D6170F1A5A8FE74F507420200014C00016D71007E000E7870737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F400000000000067708000000080000000374000A6772616E745F74797065740012636C69656E745F63726564656E7469616C73740009636C69656E745F69647400046A626F7374000573636F7065740003616C6C78737200256A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C65536574801D92D18F9B80550200007871007E0009737200176A6176612E7574696C2E4C696E6B656448617368536574D86CD75A95DD2A1E020000787200116A6176612E7574696C2E48617368536574BA44859596B8B7340300007870770C000000103F4000000000000171007E001E78017371007E0022770C000000103F40000000000000787371007E00173F40000000000000770800000010000000007870707371007E0022770C000000103F40000000000000787371007E0022770C000000103F400000000000007870);
INSERT INTO `oauth_refresh_token` VALUES ('cf32a5142bd9afc291c297c57eacf85d', 0xACED00057372004C6F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E636F6D6D6F6E2E44656661756C744578706972696E674F417574683252656672657368546F6B656E2FDF47639DD0C9B70200014C000A65787069726174696F6E7400104C6A6176612F7574696C2F446174653B787200446F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E636F6D6D6F6E2E44656661756C744F417574683252656672657368546F6B656E73E10E0A6354D45E0200014C000576616C75657400124C6A6176612F6C616E672F537472696E673B787074002435323131366362622D343939622D346533622D613565302D6632346566613762306163377372000E6A6176612E7574696C2E44617465686A81014B597419030000787077080000017A5650F66D78, 0xACED0005737200416F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E70726F76696465722E4F417574683241757468656E7469636174696F6EBD400B02166252130200024C000D73746F7265645265717565737474003C4C6F72672F737072696E676672616D65776F726B2F73656375726974792F6F61757468322F70726F76696465722F4F4175746832526571756573743B4C00127573657241757468656E7469636174696F6E7400324C6F72672F737072696E676672616D65776F726B2F73656375726974792F636F72652F41757468656E7469636174696F6E3B787200476F72672E737072696E676672616D65776F726B2E73656375726974792E61757468656E7469636174696F6E2E416273747261637441757468656E7469636174696F6E546F6B656ED3AA287E6E47640E0200035A000D61757468656E746963617465644C000B617574686F7269746965737400164C6A6176612F7574696C2F436F6C6C656374696F6E3B4C000764657461696C737400124C6A6176612F6C616E672F4F626A6563743B787000737200266A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C654C697374FC0F2531B5EC8E100200014C00046C6973747400104C6A6176612F7574696C2F4C6973743B7872002C6A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C65436F6C6C656374696F6E19420080CB5EF71E0200014C00016371007E00047870737200136A6176612E7574696C2E41727261794C6973747881D21D99C7619D03000149000473697A657870000000007704000000007871007E000C707372003A6F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E70726F76696465722E4F41757468325265717565737400000000000000010200075A0008617070726F7665644C000B617574686F72697469657371007E00044C000A657874656E73696F6E7374000F4C6A6176612F7574696C2F4D61703B4C000B72656469726563745572697400124C6A6176612F6C616E672F537472696E673B4C00077265667265736874003B4C6F72672F737072696E676672616D65776F726B2F73656375726974792F6F61757468322F70726F76696465722F546F6B656E526571756573743B4C000B7265736F7572636549647374000F4C6A6176612F7574696C2F5365743B4C000D726573706F6E7365547970657371007E0011787200386F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E70726F76696465722E426173655265717565737436287A3EA37169BD0200034C0008636C69656E74496471007E000F4C001172657175657374506172616D657465727371007E000E4C000573636F706571007E001178707400046A626F73737200256A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C654D6170F1A5A8FE74F507420200014C00016D71007E000E7870737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F400000000000067708000000080000000374000A6772616E745F74797065740012636C69656E745F63726564656E7469616C73740009636C69656E745F69647400046A626F7374000573636F7065740003616C6C78737200256A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C65536574801D92D18F9B80550200007871007E0009737200176A6176612E7574696C2E4C696E6B656448617368536574D86CD75A95DD2A1E020000787200116A6176612E7574696C2E48617368536574BA44859596B8B7340300007870770C000000103F4000000000000171007E001E78017371007E0022770C000000103F40000000000000787371007E00173F40000000000000770800000010000000007870707371007E0022770C000000103F40000000000000787371007E0022770C000000103F400000000000007870);
INSERT INTO `oauth_refresh_token` VALUES ('53997a606ba320b31a28746cc9c7c13b', 0xACED00057372004C6F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E636F6D6D6F6E2E44656661756C744578706972696E674F417574683252656672657368546F6B656E2FDF47639DD0C9B70200014C000A65787069726174696F6E7400104C6A6176612F7574696C2F446174653B787200446F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E636F6D6D6F6E2E44656661756C744F417574683252656672657368546F6B656E73E10E0A6354D45E0200014C000576616C75657400124C6A6176612F6C616E672F537472696E673B787074002464343463303837302D373733612D343632612D396362662D3236353864393336636130327372000E6A6176612E7574696C2E44617465686A81014B597419030000787077080000017A85417FA878, 0xACED0005737200416F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E70726F76696465722E4F417574683241757468656E7469636174696F6EBD400B02166252130200024C000D73746F7265645265717565737474003C4C6F72672F737072696E676672616D65776F726B2F73656375726974792F6F61757468322F70726F76696465722F4F4175746832526571756573743B4C00127573657241757468656E7469636174696F6E7400324C6F72672F737072696E676672616D65776F726B2F73656375726974792F636F72652F41757468656E7469636174696F6E3B787200476F72672E737072696E676672616D65776F726B2E73656375726974792E61757468656E7469636174696F6E2E416273747261637441757468656E7469636174696F6E546F6B656ED3AA287E6E47640E0200035A000D61757468656E746963617465644C000B617574686F7269746965737400164C6A6176612F7574696C2F436F6C6C656374696F6E3B4C000764657461696C737400124C6A6176612F6C616E672F4F626A6563743B787000737200266A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C654C697374FC0F2531B5EC8E100200014C00046C6973747400104C6A6176612F7574696C2F4C6973743B7872002C6A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C65436F6C6C656374696F6E19420080CB5EF71E0200014C00016371007E00047870737200136A6176612E7574696C2E41727261794C6973747881D21D99C7619D03000149000473697A657870000000007704000000007871007E000C707372003A6F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E70726F76696465722E4F41757468325265717565737400000000000000010200075A0008617070726F7665644C000B617574686F72697469657371007E00044C000A657874656E73696F6E7374000F4C6A6176612F7574696C2F4D61703B4C000B72656469726563745572697400124C6A6176612F6C616E672F537472696E673B4C00077265667265736874003B4C6F72672F737072696E676672616D65776F726B2F73656375726974792F6F61757468322F70726F76696465722F546F6B656E526571756573743B4C000B7265736F7572636549647374000F4C6A6176612F7574696C2F5365743B4C000D726573706F6E7365547970657371007E0011787200386F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E70726F76696465722E426173655265717565737436287A3EA37169BD0200034C0008636C69656E74496471007E000F4C001172657175657374506172616D657465727371007E000E4C000573636F706571007E001178707400056B30303931737200256A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C654D6170F1A5A8FE74F507420200014C00016D71007E000E7870737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F400000000000067708000000080000000374000A6772616E745F74797065740012636C69656E745F63726564656E7469616C73740009636C69656E745F69647400056B3030393174000573636F7065740003616C6C78737200256A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C65536574801D92D18F9B80550200007871007E0009737200176A6176612E7574696C2E4C696E6B656448617368536574D86CD75A95DD2A1E020000787200116A6176612E7574696C2E48617368536574BA44859596B8B7340300007870770C000000103F4000000000000171007E001E78017371007E0022770C000000103F40000000000000787371007E00173F40000000000000770800000010000000007870707371007E0022770C000000103F40000000000000787371007E0022770C000000103F400000000000007870);
INSERT INTO `oauth_refresh_token` VALUES ('727b448dcd09fc2645ca892c5e6db736', 0xACED00057372004C6F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E636F6D6D6F6E2E44656661756C744578706972696E674F417574683252656672657368546F6B656E2FDF47639DD0C9B70200014C000A65787069726174696F6E7400104C6A6176612F7574696C2F446174653B787200446F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E636F6D6D6F6E2E44656661756C744F417574683252656672657368546F6B656E73E10E0A6354D45E0200014C000576616C75657400124C6A6176612F6C616E672F537472696E673B787074002436643566663234352D393033622D343234302D393437632D3135636335333231343631357372000E6A6176612E7574696C2E44617465686A81014B597419030000787077080000017A7463D6C078, 0xACED0005737200416F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E70726F76696465722E4F417574683241757468656E7469636174696F6EBD400B02166252130200024C000D73746F7265645265717565737474003C4C6F72672F737072696E676672616D65776F726B2F73656375726974792F6F61757468322F70726F76696465722F4F4175746832526571756573743B4C00127573657241757468656E7469636174696F6E7400324C6F72672F737072696E676672616D65776F726B2F73656375726974792F636F72652F41757468656E7469636174696F6E3B787200476F72672E737072696E676672616D65776F726B2E73656375726974792E61757468656E7469636174696F6E2E416273747261637441757468656E7469636174696F6E546F6B656ED3AA287E6E47640E0200035A000D61757468656E746963617465644C000B617574686F7269746965737400164C6A6176612F7574696C2F436F6C6C656374696F6E3B4C000764657461696C737400124C6A6176612F6C616E672F4F626A6563743B787000737200266A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C654C697374FC0F2531B5EC8E100200014C00046C6973747400104C6A6176612F7574696C2F4C6973743B7872002C6A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C65436F6C6C656374696F6E19420080CB5EF71E0200014C00016371007E00047870737200136A6176612E7574696C2E41727261794C6973747881D21D99C7619D03000149000473697A657870000000007704000000007871007E000C707372003A6F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E70726F76696465722E4F41757468325265717565737400000000000000010200075A0008617070726F7665644C000B617574686F72697469657371007E00044C000A657874656E73696F6E7374000F4C6A6176612F7574696C2F4D61703B4C000B72656469726563745572697400124C6A6176612F6C616E672F537472696E673B4C00077265667265736874003B4C6F72672F737072696E676672616D65776F726B2F73656375726974792F6F61757468322F70726F76696465722F546F6B656E526571756573743B4C000B7265736F7572636549647374000F4C6A6176612F7574696C2F5365743B4C000D726573706F6E7365547970657371007E0011787200386F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E70726F76696465722E426173655265717565737436287A3EA37169BD0200034C0008636C69656E74496471007E000F4C001172657175657374506172616D657465727371007E000E4C000573636F706571007E001178707400046A626F73737200256A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C654D6170F1A5A8FE74F507420200014C00016D71007E000E7870737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F400000000000067708000000080000000374000A6772616E745F74797065740012636C69656E745F63726564656E7469616C73740009636C69656E745F69647400046A626F7374000573636F7065740003616C6C78737200256A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C65536574801D92D18F9B80550200007871007E0009737200176A6176612E7574696C2E4C696E6B656448617368536574D86CD75A95DD2A1E020000787200116A6176612E7574696C2E48617368536574BA44859596B8B7340300007870770C000000103F4000000000000171007E001E78017371007E0022770C000000103F40000000000000787371007E00173F40000000000000770800000010000000007870707371007E0022770C000000103F40000000000000787371007E0022770C000000103F400000000000007870);
INSERT INTO `oauth_refresh_token` VALUES ('dd4d56006f68e75de3aed3d4201173b3', 0xACED00057372004C6F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E636F6D6D6F6E2E44656661756C744578706972696E674F417574683252656672657368546F6B656E2FDF47639DD0C9B70200014C000A65787069726174696F6E7400104C6A6176612F7574696C2F446174653B787200446F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E636F6D6D6F6E2E44656661756C744F417574683252656672657368546F6B656E73E10E0A6354D45E0200014C000576616C75657400124C6A6176612F6C616E672F537472696E673B787074002436303934643336342D616163332D343533612D393364652D6337666564303063613933667372000E6A6176612E7574696C2E44617465686A81014B597419030000787077080000017A88C6B33078, 0xACED0005737200416F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E70726F76696465722E4F417574683241757468656E7469636174696F6EBD400B02166252130200024C000D73746F7265645265717565737474003C4C6F72672F737072696E676672616D65776F726B2F73656375726974792F6F61757468322F70726F76696465722F4F4175746832526571756573743B4C00127573657241757468656E7469636174696F6E7400324C6F72672F737072696E676672616D65776F726B2F73656375726974792F636F72652F41757468656E7469636174696F6E3B787200476F72672E737072696E676672616D65776F726B2E73656375726974792E61757468656E7469636174696F6E2E416273747261637441757468656E7469636174696F6E546F6B656ED3AA287E6E47640E0200035A000D61757468656E746963617465644C000B617574686F7269746965737400164C6A6176612F7574696C2F436F6C6C656374696F6E3B4C000764657461696C737400124C6A6176612F6C616E672F4F626A6563743B787000737200266A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C654C697374FC0F2531B5EC8E100200014C00046C6973747400104C6A6176612F7574696C2F4C6973743B7872002C6A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C65436F6C6C656374696F6E19420080CB5EF71E0200014C00016371007E00047870737200136A6176612E7574696C2E41727261794C6973747881D21D99C7619D03000149000473697A657870000000007704000000007871007E000C707372003A6F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E70726F76696465722E4F41757468325265717565737400000000000000010200075A0008617070726F7665644C000B617574686F72697469657371007E00044C000A657874656E73696F6E7374000F4C6A6176612F7574696C2F4D61703B4C000B72656469726563745572697400124C6A6176612F6C616E672F537472696E673B4C00077265667265736874003B4C6F72672F737072696E676672616D65776F726B2F73656375726974792F6F61757468322F70726F76696465722F546F6B656E526571756573743B4C000B7265736F7572636549647374000F4C6A6176612F7574696C2F5365743B4C000D726573706F6E7365547970657371007E0011787200386F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E70726F76696465722E426173655265717565737436287A3EA37169BD0200034C0008636C69656E74496471007E000F4C001172657175657374506172616D657465727371007E000E4C000573636F706571007E001178707400046A626F73737200256A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C654D6170F1A5A8FE74F507420200014C00016D71007E000E7870737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F400000000000067708000000080000000374000A6772616E745F74797065740012636C69656E745F63726564656E7469616C73740009636C69656E745F69647400046A626F7374000573636F7065740003616C6C78737200256A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C65536574801D92D18F9B80550200007871007E0009737200176A6176612E7574696C2E4C696E6B656448617368536574D86CD75A95DD2A1E020000787200116A6176612E7574696C2E48617368536574BA44859596B8B7340300007870770C000000103F4000000000000171007E001E78017371007E0022770C000000103F40000000000000787371007E00173F40000000000000770800000010000000007870707371007E0022770C000000103F40000000000000787371007E0022770C000000103F400000000000007870);
INSERT INTO `oauth_refresh_token` VALUES ('7094b2bf1a068b88a4863459350045f6', 0xACED00057372004C6F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E636F6D6D6F6E2E44656661756C744578706972696E674F417574683252656672657368546F6B656E2FDF47639DD0C9B70200014C000A65787069726174696F6E7400104C6A6176612F7574696C2F446174653B787200446F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E636F6D6D6F6E2E44656661756C744F417574683252656672657368546F6B656E73E10E0A6354D45E0200014C000576616C75657400124C6A6176612F6C616E672F537472696E673B787074002465633733646437612D306562612D343035322D393330622D6332333636316535333562317372000E6A6176612E7574696C2E44617465686A81014B597419030000787077080000017A8921CE2B78, 0xACED0005737200416F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E70726F76696465722E4F417574683241757468656E7469636174696F6EBD400B02166252130200024C000D73746F7265645265717565737474003C4C6F72672F737072696E676672616D65776F726B2F73656375726974792F6F61757468322F70726F76696465722F4F4175746832526571756573743B4C00127573657241757468656E7469636174696F6E7400324C6F72672F737072696E676672616D65776F726B2F73656375726974792F636F72652F41757468656E7469636174696F6E3B787200476F72672E737072696E676672616D65776F726B2E73656375726974792E61757468656E7469636174696F6E2E416273747261637441757468656E7469636174696F6E546F6B656ED3AA287E6E47640E0200035A000D61757468656E746963617465644C000B617574686F7269746965737400164C6A6176612F7574696C2F436F6C6C656374696F6E3B4C000764657461696C737400124C6A6176612F6C616E672F4F626A6563743B787000737200266A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C654C697374FC0F2531B5EC8E100200014C00046C6973747400104C6A6176612F7574696C2F4C6973743B7872002C6A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C65436F6C6C656374696F6E19420080CB5EF71E0200014C00016371007E00047870737200136A6176612E7574696C2E41727261794C6973747881D21D99C7619D03000149000473697A657870000000007704000000007871007E000C707372003A6F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E70726F76696465722E4F41757468325265717565737400000000000000010200075A0008617070726F7665644C000B617574686F72697469657371007E00044C000A657874656E73696F6E7374000F4C6A6176612F7574696C2F4D61703B4C000B72656469726563745572697400124C6A6176612F6C616E672F537472696E673B4C00077265667265736874003B4C6F72672F737072696E676672616D65776F726B2F73656375726974792F6F61757468322F70726F76696465722F546F6B656E526571756573743B4C000B7265736F7572636549647374000F4C6A6176612F7574696C2F5365743B4C000D726573706F6E7365547970657371007E0011787200386F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E70726F76696465722E426173655265717565737436287A3EA37169BD0200034C0008636C69656E74496471007E000F4C001172657175657374506172616D657465727371007E000E4C000573636F706571007E001178707400046A626F73737200256A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C654D6170F1A5A8FE74F507420200014C00016D71007E000E7870737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F400000000000067708000000080000000374000A6772616E745F74797065740012636C69656E745F63726564656E7469616C73740009636C69656E745F69647400046A626F7374000573636F7065740003616C6C78737200256A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C65536574801D92D18F9B80550200007871007E0009737200176A6176612E7574696C2E4C696E6B656448617368536574D86CD75A95DD2A1E020000787200116A6176612E7574696C2E48617368536574BA44859596B8B7340300007870770C000000103F4000000000000171007E001E78017371007E0022770C000000103F40000000000000787371007E00173F40000000000000770800000010000000007870707371007E0022770C000000103F40000000000000787371007E0022770C000000103F400000000000007870);
INSERT INTO `oauth_refresh_token` VALUES ('76ee9adf25e81b857eeb7dae5143b8bf', 0xACED00057372004C6F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E636F6D6D6F6E2E44656661756C744578706972696E674F417574683252656672657368546F6B656E2FDF47639DD0C9B70200014C000A65787069726174696F6E7400104C6A6176612F7574696C2F446174653B787200446F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E636F6D6D6F6E2E44656661756C744F417574683252656672657368546F6B656E73E10E0A6354D45E0200014C000576616C75657400124C6A6176612F6C616E672F537472696E673B787074002438306435663230322D393632312D346166632D396461372D3738333338396562373131347372000E6A6176612E7574696C2E44617465686A81014B597419030000787077080000017A89C309FC78, 0xACED0005737200416F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E70726F76696465722E4F417574683241757468656E7469636174696F6EBD400B02166252130200024C000D73746F7265645265717565737474003C4C6F72672F737072696E676672616D65776F726B2F73656375726974792F6F61757468322F70726F76696465722F4F4175746832526571756573743B4C00127573657241757468656E7469636174696F6E7400324C6F72672F737072696E676672616D65776F726B2F73656375726974792F636F72652F41757468656E7469636174696F6E3B787200476F72672E737072696E676672616D65776F726B2E73656375726974792E61757468656E7469636174696F6E2E416273747261637441757468656E7469636174696F6E546F6B656ED3AA287E6E47640E0200035A000D61757468656E746963617465644C000B617574686F7269746965737400164C6A6176612F7574696C2F436F6C6C656374696F6E3B4C000764657461696C737400124C6A6176612F6C616E672F4F626A6563743B787000737200266A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C654C697374FC0F2531B5EC8E100200014C00046C6973747400104C6A6176612F7574696C2F4C6973743B7872002C6A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C65436F6C6C656374696F6E19420080CB5EF71E0200014C00016371007E00047870737200136A6176612E7574696C2E41727261794C6973747881D21D99C7619D03000149000473697A657870000000007704000000007871007E000C707372003A6F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E70726F76696465722E4F41757468325265717565737400000000000000010200075A0008617070726F7665644C000B617574686F72697469657371007E00044C000A657874656E73696F6E7374000F4C6A6176612F7574696C2F4D61703B4C000B72656469726563745572697400124C6A6176612F6C616E672F537472696E673B4C00077265667265736874003B4C6F72672F737072696E676672616D65776F726B2F73656375726974792F6F61757468322F70726F76696465722F546F6B656E526571756573743B4C000B7265736F7572636549647374000F4C6A6176612F7574696C2F5365743B4C000D726573706F6E7365547970657371007E0011787200386F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E70726F76696465722E426173655265717565737436287A3EA37169BD0200034C0008636C69656E74496471007E000F4C001172657175657374506172616D657465727371007E000E4C000573636F706571007E001178707400046A626F73737200256A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C654D6170F1A5A8FE74F507420200014C00016D71007E000E7870737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F400000000000067708000000080000000374000A6772616E745F74797065740012636C69656E745F63726564656E7469616C73740009636C69656E745F69647400046A626F7374000573636F7065740003616C6C78737200256A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C65536574801D92D18F9B80550200007871007E0009737200176A6176612E7574696C2E4C696E6B656448617368536574D86CD75A95DD2A1E020000787200116A6176612E7574696C2E48617368536574BA44859596B8B7340300007870770C000000103F4000000000000171007E001E78017371007E0022770C000000103F40000000000000787371007E00173F40000000000000770800000010000000007870707371007E0022770C000000103F40000000000000787371007E0022770C000000103F400000000000007870);
INSERT INTO `oauth_refresh_token` VALUES ('3751dce74cfe6582c6d134294665d337', 0xACED00057372004C6F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E636F6D6D6F6E2E44656661756C744578706972696E674F417574683252656672657368546F6B656E2FDF47639DD0C9B70200014C000A65787069726174696F6E7400104C6A6176612F7574696C2F446174653B787200446F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E636F6D6D6F6E2E44656661756C744F417574683252656672657368546F6B656E73E10E0A6354D45E0200014C000576616C75657400124C6A6176612F6C616E672F537472696E673B787074002464633732666233312D393761312D346530352D623935352D3236613465616532643261327372000E6A6176612E7574696C2E44617465686A81014B597419030000787077080000017A8A070E8678, 0xACED0005737200416F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E70726F76696465722E4F417574683241757468656E7469636174696F6EBD400B02166252130200024C000D73746F7265645265717565737474003C4C6F72672F737072696E676672616D65776F726B2F73656375726974792F6F61757468322F70726F76696465722F4F4175746832526571756573743B4C00127573657241757468656E7469636174696F6E7400324C6F72672F737072696E676672616D65776F726B2F73656375726974792F636F72652F41757468656E7469636174696F6E3B787200476F72672E737072696E676672616D65776F726B2E73656375726974792E61757468656E7469636174696F6E2E416273747261637441757468656E7469636174696F6E546F6B656ED3AA287E6E47640E0200035A000D61757468656E746963617465644C000B617574686F7269746965737400164C6A6176612F7574696C2F436F6C6C656374696F6E3B4C000764657461696C737400124C6A6176612F6C616E672F4F626A6563743B787000737200266A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C654C697374FC0F2531B5EC8E100200014C00046C6973747400104C6A6176612F7574696C2F4C6973743B7872002C6A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C65436F6C6C656374696F6E19420080CB5EF71E0200014C00016371007E00047870737200136A6176612E7574696C2E41727261794C6973747881D21D99C7619D03000149000473697A657870000000007704000000007871007E000C707372003A6F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E70726F76696465722E4F41757468325265717565737400000000000000010200075A0008617070726F7665644C000B617574686F72697469657371007E00044C000A657874656E73696F6E7374000F4C6A6176612F7574696C2F4D61703B4C000B72656469726563745572697400124C6A6176612F6C616E672F537472696E673B4C00077265667265736874003B4C6F72672F737072696E676672616D65776F726B2F73656375726974792F6F61757468322F70726F76696465722F546F6B656E526571756573743B4C000B7265736F7572636549647374000F4C6A6176612F7574696C2F5365743B4C000D726573706F6E7365547970657371007E0011787200386F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E70726F76696465722E426173655265717565737436287A3EA37169BD0200034C0008636C69656E74496471007E000F4C001172657175657374506172616D657465727371007E000E4C000573636F706571007E001178707400046A626F73737200256A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C654D6170F1A5A8FE74F507420200014C00016D71007E000E7870737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F400000000000067708000000080000000374000A6772616E745F74797065740012636C69656E745F63726564656E7469616C73740009636C69656E745F69647400046A626F7374000573636F7065740003616C6C78737200256A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C65536574801D92D18F9B80550200007871007E0009737200176A6176612E7574696C2E4C696E6B656448617368536574D86CD75A95DD2A1E020000787200116A6176612E7574696C2E48617368536574BA44859596B8B7340300007870770C000000103F4000000000000171007E001E78017371007E0022770C000000103F40000000000000787371007E00173F40000000000000770800000010000000007870707371007E0022770C000000103F40000000000000787371007E0022770C000000103F400000000000007870);
INSERT INTO `oauth_refresh_token` VALUES ('9394bade3237d1a56d1cd6b396220f64', 0xACED00057372004C6F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E636F6D6D6F6E2E44656661756C744578706972696E674F417574683252656672657368546F6B656E2FDF47639DD0C9B70200014C000A65787069726174696F6E7400104C6A6176612F7574696C2F446174653B787200446F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E636F6D6D6F6E2E44656661756C744F417574683252656672657368546F6B656E73E10E0A6354D45E0200014C000576616C75657400124C6A6176612F6C616E672F537472696E673B787074002433666132326632652D316362312D343465312D396132662D3661386238646533353136377372000E6A6176612E7574696C2E44617465686A81014B597419030000787077080000017A8A3FAD9678, 0xACED0005737200416F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E70726F76696465722E4F417574683241757468656E7469636174696F6EBD400B02166252130200024C000D73746F7265645265717565737474003C4C6F72672F737072696E676672616D65776F726B2F73656375726974792F6F61757468322F70726F76696465722F4F4175746832526571756573743B4C00127573657241757468656E7469636174696F6E7400324C6F72672F737072696E676672616D65776F726B2F73656375726974792F636F72652F41757468656E7469636174696F6E3B787200476F72672E737072696E676672616D65776F726B2E73656375726974792E61757468656E7469636174696F6E2E416273747261637441757468656E7469636174696F6E546F6B656ED3AA287E6E47640E0200035A000D61757468656E746963617465644C000B617574686F7269746965737400164C6A6176612F7574696C2F436F6C6C656374696F6E3B4C000764657461696C737400124C6A6176612F6C616E672F4F626A6563743B787000737200266A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C654C697374FC0F2531B5EC8E100200014C00046C6973747400104C6A6176612F7574696C2F4C6973743B7872002C6A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C65436F6C6C656374696F6E19420080CB5EF71E0200014C00016371007E00047870737200136A6176612E7574696C2E41727261794C6973747881D21D99C7619D03000149000473697A657870000000007704000000007871007E000C707372003A6F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E70726F76696465722E4F41757468325265717565737400000000000000010200075A0008617070726F7665644C000B617574686F72697469657371007E00044C000A657874656E73696F6E7374000F4C6A6176612F7574696C2F4D61703B4C000B72656469726563745572697400124C6A6176612F6C616E672F537472696E673B4C00077265667265736874003B4C6F72672F737072696E676672616D65776F726B2F73656375726974792F6F61757468322F70726F76696465722F546F6B656E526571756573743B4C000B7265736F7572636549647374000F4C6A6176612F7574696C2F5365743B4C000D726573706F6E7365547970657371007E0011787200386F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E70726F76696465722E426173655265717565737436287A3EA37169BD0200034C0008636C69656E74496471007E000F4C001172657175657374506172616D657465727371007E000E4C000573636F706571007E001178707400046A626F73737200256A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C654D6170F1A5A8FE74F507420200014C00016D71007E000E7870737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F400000000000067708000000080000000374000A6772616E745F74797065740012636C69656E745F63726564656E7469616C73740009636C69656E745F69647400046A626F7374000573636F7065740003616C6C78737200256A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C65536574801D92D18F9B80550200007871007E0009737200176A6176612E7574696C2E4C696E6B656448617368536574D86CD75A95DD2A1E020000787200116A6176612E7574696C2E48617368536574BA44859596B8B7340300007870770C000000103F4000000000000171007E001E78017371007E0022770C000000103F40000000000000787371007E00173F40000000000000770800000010000000007870707371007E0022770C000000103F40000000000000787371007E0022770C000000103F400000000000007870);
INSERT INTO `oauth_refresh_token` VALUES ('e7d724e68a0866885dce1c6b6434a7bb', 0xACED00057372004C6F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E636F6D6D6F6E2E44656661756C744578706972696E674F417574683252656672657368546F6B656E2FDF47639DD0C9B70200014C000A65787069726174696F6E7400104C6A6176612F7574696C2F446174653B787200446F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E636F6D6D6F6E2E44656661756C744F417574683252656672657368546F6B656E73E10E0A6354D45E0200014C000576616C75657400124C6A6176612F6C616E672F537472696E673B787074002437633137646235302D633439642D343363392D613763342D6262343437303435353263357372000E6A6176612E7574696C2E44617465686A81014B597419030000787077080000017A989B1B3078, 0xACED0005737200416F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E70726F76696465722E4F417574683241757468656E7469636174696F6EBD400B02166252130200024C000D73746F7265645265717565737474003C4C6F72672F737072696E676672616D65776F726B2F73656375726974792F6F61757468322F70726F76696465722F4F4175746832526571756573743B4C00127573657241757468656E7469636174696F6E7400324C6F72672F737072696E676672616D65776F726B2F73656375726974792F636F72652F41757468656E7469636174696F6E3B787200476F72672E737072696E676672616D65776F726B2E73656375726974792E61757468656E7469636174696F6E2E416273747261637441757468656E7469636174696F6E546F6B656ED3AA287E6E47640E0200035A000D61757468656E746963617465644C000B617574686F7269746965737400164C6A6176612F7574696C2F436F6C6C656374696F6E3B4C000764657461696C737400124C6A6176612F6C616E672F4F626A6563743B787000737200266A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C654C697374FC0F2531B5EC8E100200014C00046C6973747400104C6A6176612F7574696C2F4C6973743B7872002C6A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C65436F6C6C656374696F6E19420080CB5EF71E0200014C00016371007E00047870737200136A6176612E7574696C2E41727261794C6973747881D21D99C7619D03000149000473697A657870000000007704000000007871007E000C707372003A6F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E70726F76696465722E4F41757468325265717565737400000000000000010200075A0008617070726F7665644C000B617574686F72697469657371007E00044C000A657874656E73696F6E7374000F4C6A6176612F7574696C2F4D61703B4C000B72656469726563745572697400124C6A6176612F6C616E672F537472696E673B4C00077265667265736874003B4C6F72672F737072696E676672616D65776F726B2F73656375726974792F6F61757468322F70726F76696465722F546F6B656E526571756573743B4C000B7265736F7572636549647374000F4C6A6176612F7574696C2F5365743B4C000D726573706F6E7365547970657371007E0011787200386F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E70726F76696465722E426173655265717565737436287A3EA37169BD0200034C0008636C69656E74496471007E000F4C001172657175657374506172616D657465727371007E000E4C000573636F706571007E001178707400046A626F73737200256A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C654D6170F1A5A8FE74F507420200014C00016D71007E000E7870737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F400000000000067708000000080000000374000A6772616E745F74797065740012636C69656E745F63726564656E7469616C73740009636C69656E745F69647400046A626F7374000573636F7065740003616C6C78737200256A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C65536574801D92D18F9B80550200007871007E0009737200176A6176612E7574696C2E4C696E6B656448617368536574D86CD75A95DD2A1E020000787200116A6176612E7574696C2E48617368536574BA44859596B8B7340300007870770C000000103F4000000000000171007E001E78017371007E0022770C000000103F40000000000000787371007E00173F40000000000000770800000010000000007870707371007E0022770C000000103F40000000000000787371007E0022770C000000103F400000000000007870);
INSERT INTO `oauth_refresh_token` VALUES ('037e309c3bb66e15c581f17bf1268bcf', 0xACED00057372004C6F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E636F6D6D6F6E2E44656661756C744578706972696E674F417574683252656672657368546F6B656E2FDF47639DD0C9B70200014C000A65787069726174696F6E7400104C6A6176612F7574696C2F446174653B787200446F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E636F6D6D6F6E2E44656661756C744F417574683252656672657368546F6B656E73E10E0A6354D45E0200014C000576616C75657400124C6A6176612F6C616E672F537472696E673B787074002465653630666534662D333832622D346431352D383632312D6461653963363164333163357372000E6A6176612E7574696C2E44617465686A81014B597419030000787077080000017AA8B09D3A78, 0xACED0005737200416F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E70726F76696465722E4F417574683241757468656E7469636174696F6EBD400B02166252130200024C000D73746F7265645265717565737474003C4C6F72672F737072696E676672616D65776F726B2F73656375726974792F6F61757468322F70726F76696465722F4F4175746832526571756573743B4C00127573657241757468656E7469636174696F6E7400324C6F72672F737072696E676672616D65776F726B2F73656375726974792F636F72652F41757468656E7469636174696F6E3B787200476F72672E737072696E676672616D65776F726B2E73656375726974792E61757468656E7469636174696F6E2E416273747261637441757468656E7469636174696F6E546F6B656ED3AA287E6E47640E0200035A000D61757468656E746963617465644C000B617574686F7269746965737400164C6A6176612F7574696C2F436F6C6C656374696F6E3B4C000764657461696C737400124C6A6176612F6C616E672F4F626A6563743B787000737200266A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C654C697374FC0F2531B5EC8E100200014C00046C6973747400104C6A6176612F7574696C2F4C6973743B7872002C6A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C65436F6C6C656374696F6E19420080CB5EF71E0200014C00016371007E00047870737200136A6176612E7574696C2E41727261794C6973747881D21D99C7619D03000149000473697A657870000000007704000000007871007E000C707372003A6F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E70726F76696465722E4F41757468325265717565737400000000000000010200075A0008617070726F7665644C000B617574686F72697469657371007E00044C000A657874656E73696F6E7374000F4C6A6176612F7574696C2F4D61703B4C000B72656469726563745572697400124C6A6176612F6C616E672F537472696E673B4C00077265667265736874003B4C6F72672F737072696E676672616D65776F726B2F73656375726974792F6F61757468322F70726F76696465722F546F6B656E526571756573743B4C000B7265736F7572636549647374000F4C6A6176612F7574696C2F5365743B4C000D726573706F6E7365547970657371007E0011787200386F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E70726F76696465722E426173655265717565737436287A3EA37169BD0200034C0008636C69656E74496471007E000F4C001172657175657374506172616D657465727371007E000E4C000573636F706571007E001178707400046A626F73737200256A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C654D6170F1A5A8FE74F507420200014C00016D71007E000E7870737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F400000000000067708000000080000000374000A6772616E745F74797065740012636C69656E745F63726564656E7469616C73740009636C69656E745F69647400046A626F7374000573636F7065740003616C6C78737200256A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C65536574801D92D18F9B80550200007871007E0009737200176A6176612E7574696C2E4C696E6B656448617368536574D86CD75A95DD2A1E020000787200116A6176612E7574696C2E48617368536574BA44859596B8B7340300007870770C000000103F4000000000000171007E001E78017371007E0022770C000000103F40000000000000787371007E00173F40000000000000770800000010000000007870707371007E0022770C000000103F40000000000000787371007E0022770C000000103F400000000000007870);
INSERT INTO `oauth_refresh_token` VALUES ('7f51ada080ef63e406caeff7da36873e', 0xACED00057372004C6F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E636F6D6D6F6E2E44656661756C744578706972696E674F417574683252656672657368546F6B656E2FDF47639DD0C9B70200014C000A65787069726174696F6E7400104C6A6176612F7574696C2F446174653B787200446F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E636F6D6D6F6E2E44656661756C744F417574683252656672657368546F6B656E73E10E0A6354D45E0200014C000576616C75657400124C6A6176612F6C616E672F537472696E673B787074002463376263343763352D656461302D343165352D393438342D3165333835323235616630637372000E6A6176612E7574696C2E44617465686A81014B597419030000787077080000017AAD109A3478, 0xACED0005737200416F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E70726F76696465722E4F417574683241757468656E7469636174696F6EBD400B02166252130200024C000D73746F7265645265717565737474003C4C6F72672F737072696E676672616D65776F726B2F73656375726974792F6F61757468322F70726F76696465722F4F4175746832526571756573743B4C00127573657241757468656E7469636174696F6E7400324C6F72672F737072696E676672616D65776F726B2F73656375726974792F636F72652F41757468656E7469636174696F6E3B787200476F72672E737072696E676672616D65776F726B2E73656375726974792E61757468656E7469636174696F6E2E416273747261637441757468656E7469636174696F6E546F6B656ED3AA287E6E47640E0200035A000D61757468656E746963617465644C000B617574686F7269746965737400164C6A6176612F7574696C2F436F6C6C656374696F6E3B4C000764657461696C737400124C6A6176612F6C616E672F4F626A6563743B787000737200266A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C654C697374FC0F2531B5EC8E100200014C00046C6973747400104C6A6176612F7574696C2F4C6973743B7872002C6A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C65436F6C6C656374696F6E19420080CB5EF71E0200014C00016371007E00047870737200136A6176612E7574696C2E41727261794C6973747881D21D99C7619D03000149000473697A657870000000007704000000007871007E000C707372003A6F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E70726F76696465722E4F41757468325265717565737400000000000000010200075A0008617070726F7665644C000B617574686F72697469657371007E00044C000A657874656E73696F6E7374000F4C6A6176612F7574696C2F4D61703B4C000B72656469726563745572697400124C6A6176612F6C616E672F537472696E673B4C00077265667265736874003B4C6F72672F737072696E676672616D65776F726B2F73656375726974792F6F61757468322F70726F76696465722F546F6B656E526571756573743B4C000B7265736F7572636549647374000F4C6A6176612F7574696C2F5365743B4C000D726573706F6E7365547970657371007E0011787200386F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E70726F76696465722E426173655265717565737436287A3EA37169BD0200034C0008636C69656E74496471007E000F4C001172657175657374506172616D657465727371007E000E4C000573636F706571007E001178707400046A626F73737200256A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C654D6170F1A5A8FE74F507420200014C00016D71007E000E7870737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F400000000000067708000000080000000374000A6772616E745F74797065740012636C69656E745F63726564656E7469616C73740009636C69656E745F69647400046A626F7374000573636F7065740003616C6C78737200256A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C65536574801D92D18F9B80550200007871007E0009737200176A6176612E7574696C2E4C696E6B656448617368536574D86CD75A95DD2A1E020000787200116A6176612E7574696C2E48617368536574BA44859596B8B7340300007870770C000000103F4000000000000171007E001E78017371007E0022770C000000103F40000000000000787371007E00173F40000000000000770800000010000000007870707371007E0022770C000000103F40000000000000787371007E0022770C000000103F400000000000007870);
INSERT INTO `oauth_refresh_token` VALUES ('04012737b7d948b5fd88963f9c29221f', 0xACED00057372004C6F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E636F6D6D6F6E2E44656661756C744578706972696E674F417574683252656672657368546F6B656E2FDF47639DD0C9B70200014C000A65787069726174696F6E7400104C6A6176612F7574696C2F446174653B787200446F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E636F6D6D6F6E2E44656661756C744F417574683252656672657368546F6B656E73E10E0A6354D45E0200014C000576616C75657400124C6A6176612F6C616E672F537472696E673B787074002464633938383135342D383535362D343666342D623239342D6663636239613865663965637372000E6A6176612E7574696C2E44617465686A81014B597419030000787077080000017AC795F8F078, 0xACED0005737200416F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E70726F76696465722E4F417574683241757468656E7469636174696F6EBD400B02166252130200024C000D73746F7265645265717565737474003C4C6F72672F737072696E676672616D65776F726B2F73656375726974792F6F61757468322F70726F76696465722F4F4175746832526571756573743B4C00127573657241757468656E7469636174696F6E7400324C6F72672F737072696E676672616D65776F726B2F73656375726974792F636F72652F41757468656E7469636174696F6E3B787200476F72672E737072696E676672616D65776F726B2E73656375726974792E61757468656E7469636174696F6E2E416273747261637441757468656E7469636174696F6E546F6B656ED3AA287E6E47640E0200035A000D61757468656E746963617465644C000B617574686F7269746965737400164C6A6176612F7574696C2F436F6C6C656374696F6E3B4C000764657461696C737400124C6A6176612F6C616E672F4F626A6563743B787000737200266A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C654C697374FC0F2531B5EC8E100200014C00046C6973747400104C6A6176612F7574696C2F4C6973743B7872002C6A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C65436F6C6C656374696F6E19420080CB5EF71E0200014C00016371007E00047870737200136A6176612E7574696C2E41727261794C6973747881D21D99C7619D03000149000473697A657870000000007704000000007871007E000C707372003A6F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E70726F76696465722E4F41757468325265717565737400000000000000010200075A0008617070726F7665644C000B617574686F72697469657371007E00044C000A657874656E73696F6E7374000F4C6A6176612F7574696C2F4D61703B4C000B72656469726563745572697400124C6A6176612F6C616E672F537472696E673B4C00077265667265736874003B4C6F72672F737072696E676672616D65776F726B2F73656375726974792F6F61757468322F70726F76696465722F546F6B656E526571756573743B4C000B7265736F7572636549647374000F4C6A6176612F7574696C2F5365743B4C000D726573706F6E7365547970657371007E0011787200386F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E70726F76696465722E426173655265717565737436287A3EA37169BD0200034C0008636C69656E74496471007E000F4C001172657175657374506172616D657465727371007E000E4C000573636F706571007E001178707400046A626F73737200256A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C654D6170F1A5A8FE74F507420200014C00016D71007E000E7870737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F400000000000067708000000080000000374000A6772616E745F74797065740012636C69656E745F63726564656E7469616C73740009636C69656E745F69647400046A626F7374000573636F7065740003616C6C78737200256A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C65536574801D92D18F9B80550200007871007E0009737200176A6176612E7574696C2E4C696E6B656448617368536574D86CD75A95DD2A1E020000787200116A6176612E7574696C2E48617368536574BA44859596B8B7340300007870770C000000103F4000000000000171007E001E78017371007E0022770C000000103F40000000000000787371007E00173F40000000000000770800000010000000007870707371007E0022770C000000103F40000000000000787371007E0022770C000000103F400000000000007870);
INSERT INTO `oauth_refresh_token` VALUES ('410e0ce223cf1864209ff2f86d2c98be', 0xACED00057372004C6F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E636F6D6D6F6E2E44656661756C744578706972696E674F417574683252656672657368546F6B656E2FDF47639DD0C9B70200014C000A65787069726174696F6E7400104C6A6176612F7574696C2F446174653B787200446F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E636F6D6D6F6E2E44656661756C744F417574683252656672657368546F6B656E73E10E0A6354D45E0200014C000576616C75657400124C6A6176612F6C616E672F537472696E673B787074002463316563653133312D623038382D343461342D623030662D3535646538346363353638357372000E6A6176612E7574696C2E44617465686A81014B597419030000787077080000017AC7CF60F578, 0xACED0005737200416F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E70726F76696465722E4F417574683241757468656E7469636174696F6EBD400B02166252130200024C000D73746F7265645265717565737474003C4C6F72672F737072696E676672616D65776F726B2F73656375726974792F6F61757468322F70726F76696465722F4F4175746832526571756573743B4C00127573657241757468656E7469636174696F6E7400324C6F72672F737072696E676672616D65776F726B2F73656375726974792F636F72652F41757468656E7469636174696F6E3B787200476F72672E737072696E676672616D65776F726B2E73656375726974792E61757468656E7469636174696F6E2E416273747261637441757468656E7469636174696F6E546F6B656ED3AA287E6E47640E0200035A000D61757468656E746963617465644C000B617574686F7269746965737400164C6A6176612F7574696C2F436F6C6C656374696F6E3B4C000764657461696C737400124C6A6176612F6C616E672F4F626A6563743B787000737200266A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C654C697374FC0F2531B5EC8E100200014C00046C6973747400104C6A6176612F7574696C2F4C6973743B7872002C6A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C65436F6C6C656374696F6E19420080CB5EF71E0200014C00016371007E00047870737200136A6176612E7574696C2E41727261794C6973747881D21D99C7619D03000149000473697A657870000000007704000000007871007E000C707372003A6F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E70726F76696465722E4F41757468325265717565737400000000000000010200075A0008617070726F7665644C000B617574686F72697469657371007E00044C000A657874656E73696F6E7374000F4C6A6176612F7574696C2F4D61703B4C000B72656469726563745572697400124C6A6176612F6C616E672F537472696E673B4C00077265667265736874003B4C6F72672F737072696E676672616D65776F726B2F73656375726974792F6F61757468322F70726F76696465722F546F6B656E526571756573743B4C000B7265736F7572636549647374000F4C6A6176612F7574696C2F5365743B4C000D726573706F6E7365547970657371007E0011787200386F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E70726F76696465722E426173655265717565737436287A3EA37169BD0200034C0008636C69656E74496471007E000F4C001172657175657374506172616D657465727371007E000E4C000573636F706571007E001178707400046A626F73737200256A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C654D6170F1A5A8FE74F507420200014C00016D71007E000E7870737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F400000000000067708000000080000000374000A6772616E745F74797065740012636C69656E745F63726564656E7469616C73740009636C69656E745F69647400046A626F7374000573636F7065740003616C6C78737200256A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C65536574801D92D18F9B80550200007871007E0009737200176A6176612E7574696C2E4C696E6B656448617368536574D86CD75A95DD2A1E020000787200116A6176612E7574696C2E48617368536574BA44859596B8B7340300007870770C000000103F4000000000000171007E001E78017371007E0022770C000000103F40000000000000787371007E00173F40000000000000770800000010000000007870707371007E0022770C000000103F40000000000000787371007E0022770C000000103F400000000000007870);
INSERT INTO `oauth_refresh_token` VALUES ('a186d8f1da27b7afdd9cdc339ae94256', 0xACED00057372004C6F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E636F6D6D6F6E2E44656661756C744578706972696E674F417574683252656672657368546F6B656E2FDF47639DD0C9B70200014C000A65787069726174696F6E7400104C6A6176612F7574696C2F446174653B787200446F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E636F6D6D6F6E2E44656661756C744F417574683252656672657368546F6B656E73E10E0A6354D45E0200014C000576616C75657400124C6A6176612F6C616E672F537472696E673B787074002463336663623438392D636437632D343538322D626230322D3661333061346263353566317372000E6A6176612E7574696C2E44617465686A81014B597419030000787077080000017AC80A21F578, 0xACED0005737200416F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E70726F76696465722E4F417574683241757468656E7469636174696F6EBD400B02166252130200024C000D73746F7265645265717565737474003C4C6F72672F737072696E676672616D65776F726B2F73656375726974792F6F61757468322F70726F76696465722F4F4175746832526571756573743B4C00127573657241757468656E7469636174696F6E7400324C6F72672F737072696E676672616D65776F726B2F73656375726974792F636F72652F41757468656E7469636174696F6E3B787200476F72672E737072696E676672616D65776F726B2E73656375726974792E61757468656E7469636174696F6E2E416273747261637441757468656E7469636174696F6E546F6B656ED3AA287E6E47640E0200035A000D61757468656E746963617465644C000B617574686F7269746965737400164C6A6176612F7574696C2F436F6C6C656374696F6E3B4C000764657461696C737400124C6A6176612F6C616E672F4F626A6563743B787000737200266A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C654C697374FC0F2531B5EC8E100200014C00046C6973747400104C6A6176612F7574696C2F4C6973743B7872002C6A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C65436F6C6C656374696F6E19420080CB5EF71E0200014C00016371007E00047870737200136A6176612E7574696C2E41727261794C6973747881D21D99C7619D03000149000473697A657870000000007704000000007871007E000C707372003A6F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E70726F76696465722E4F41757468325265717565737400000000000000010200075A0008617070726F7665644C000B617574686F72697469657371007E00044C000A657874656E73696F6E7374000F4C6A6176612F7574696C2F4D61703B4C000B72656469726563745572697400124C6A6176612F6C616E672F537472696E673B4C00077265667265736874003B4C6F72672F737072696E676672616D65776F726B2F73656375726974792F6F61757468322F70726F76696465722F546F6B656E526571756573743B4C000B7265736F7572636549647374000F4C6A6176612F7574696C2F5365743B4C000D726573706F6E7365547970657371007E0011787200386F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E70726F76696465722E426173655265717565737436287A3EA37169BD0200034C0008636C69656E74496471007E000F4C001172657175657374506172616D657465727371007E000E4C000573636F706571007E001178707400046A626F73737200256A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C654D6170F1A5A8FE74F507420200014C00016D71007E000E7870737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F400000000000067708000000080000000374000A6772616E745F74797065740012636C69656E745F63726564656E7469616C73740009636C69656E745F69647400046A626F7374000573636F7065740003616C6C78737200256A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C65536574801D92D18F9B80550200007871007E0009737200176A6176612E7574696C2E4C696E6B656448617368536574D86CD75A95DD2A1E020000787200116A6176612E7574696C2E48617368536574BA44859596B8B7340300007870770C000000103F4000000000000171007E001E78017371007E0022770C000000103F40000000000000787371007E00173F40000000000000770800000010000000007870707371007E0022770C000000103F40000000000000787371007E0022770C000000103F400000000000007870);
INSERT INTO `oauth_refresh_token` VALUES ('74e845f5029eca73bd36b648471da72a', 0xACED00057372004C6F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E636F6D6D6F6E2E44656661756C744578706972696E674F417574683252656672657368546F6B656E2FDF47639DD0C9B70200014C000A65787069726174696F6E7400104C6A6176612F7574696C2F446174653B787200446F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E636F6D6D6F6E2E44656661756C744F417574683252656672657368546F6B656E73E10E0A6354D45E0200014C000576616C75657400124C6A6176612F6C616E672F537472696E673B787074002463333838613330382D373637352D346566392D386336302D3233333539363432663531337372000E6A6176612E7574696C2E44617465686A81014B597419030000787077080000017ACC2BCE4D78, 0xACED0005737200416F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E70726F76696465722E4F417574683241757468656E7469636174696F6EBD400B02166252130200024C000D73746F7265645265717565737474003C4C6F72672F737072696E676672616D65776F726B2F73656375726974792F6F61757468322F70726F76696465722F4F4175746832526571756573743B4C00127573657241757468656E7469636174696F6E7400324C6F72672F737072696E676672616D65776F726B2F73656375726974792F636F72652F41757468656E7469636174696F6E3B787200476F72672E737072696E676672616D65776F726B2E73656375726974792E61757468656E7469636174696F6E2E416273747261637441757468656E7469636174696F6E546F6B656ED3AA287E6E47640E0200035A000D61757468656E746963617465644C000B617574686F7269746965737400164C6A6176612F7574696C2F436F6C6C656374696F6E3B4C000764657461696C737400124C6A6176612F6C616E672F4F626A6563743B787000737200266A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C654C697374FC0F2531B5EC8E100200014C00046C6973747400104C6A6176612F7574696C2F4C6973743B7872002C6A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C65436F6C6C656374696F6E19420080CB5EF71E0200014C00016371007E00047870737200136A6176612E7574696C2E41727261794C6973747881D21D99C7619D03000149000473697A657870000000007704000000007871007E000C707372003A6F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E70726F76696465722E4F41757468325265717565737400000000000000010200075A0008617070726F7665644C000B617574686F72697469657371007E00044C000A657874656E73696F6E7374000F4C6A6176612F7574696C2F4D61703B4C000B72656469726563745572697400124C6A6176612F6C616E672F537472696E673B4C00077265667265736874003B4C6F72672F737072696E676672616D65776F726B2F73656375726974792F6F61757468322F70726F76696465722F546F6B656E526571756573743B4C000B7265736F7572636549647374000F4C6A6176612F7574696C2F5365743B4C000D726573706F6E7365547970657371007E0011787200386F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E70726F76696465722E426173655265717565737436287A3EA37169BD0200034C0008636C69656E74496471007E000F4C001172657175657374506172616D657465727371007E000E4C000573636F706571007E001178707400046A626F73737200256A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C654D6170F1A5A8FE74F507420200014C00016D71007E000E7870737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F400000000000067708000000080000000374000A6772616E745F74797065740012636C69656E745F63726564656E7469616C73740009636C69656E745F69647400046A626F7374000573636F7065740003616C6C78737200256A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C65536574801D92D18F9B80550200007871007E0009737200176A6176612E7574696C2E4C696E6B656448617368536574D86CD75A95DD2A1E020000787200116A6176612E7574696C2E48617368536574BA44859596B8B7340300007870770C000000103F4000000000000171007E001E78017371007E0022770C000000103F40000000000000787371007E00173F40000000000000770800000010000000007870707371007E0022770C000000103F40000000000000787371007E0022770C000000103F400000000000007870);
INSERT INTO `oauth_refresh_token` VALUES ('5c2292f3ab9cd2908aac9081b4b660ec', 0xACED00057372004C6F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E636F6D6D6F6E2E44656661756C744578706972696E674F417574683252656672657368546F6B656E2FDF47639DD0C9B70200014C000A65787069726174696F6E7400104C6A6176612F7574696C2F446174653B787200446F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E636F6D6D6F6E2E44656661756C744F417574683252656672657368546F6B656E73E10E0A6354D45E0200014C000576616C75657400124C6A6176612F6C616E672F537472696E673B787074002463303133653231362D353235322D346163652D623234612D6330366362343962653235367372000E6A6176612E7574696C2E44617465686A81014B597419030000787077080000017ACD568F4878, 0xACED0005737200416F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E70726F76696465722E4F417574683241757468656E7469636174696F6EBD400B02166252130200024C000D73746F7265645265717565737474003C4C6F72672F737072696E676672616D65776F726B2F73656375726974792F6F61757468322F70726F76696465722F4F4175746832526571756573743B4C00127573657241757468656E7469636174696F6E7400324C6F72672F737072696E676672616D65776F726B2F73656375726974792F636F72652F41757468656E7469636174696F6E3B787200476F72672E737072696E676672616D65776F726B2E73656375726974792E61757468656E7469636174696F6E2E416273747261637441757468656E7469636174696F6E546F6B656ED3AA287E6E47640E0200035A000D61757468656E746963617465644C000B617574686F7269746965737400164C6A6176612F7574696C2F436F6C6C656374696F6E3B4C000764657461696C737400124C6A6176612F6C616E672F4F626A6563743B787000737200266A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C654C697374FC0F2531B5EC8E100200014C00046C6973747400104C6A6176612F7574696C2F4C6973743B7872002C6A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C65436F6C6C656374696F6E19420080CB5EF71E0200014C00016371007E00047870737200136A6176612E7574696C2E41727261794C6973747881D21D99C7619D03000149000473697A657870000000007704000000007871007E000C707372003A6F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E70726F76696465722E4F41757468325265717565737400000000000000010200075A0008617070726F7665644C000B617574686F72697469657371007E00044C000A657874656E73696F6E7374000F4C6A6176612F7574696C2F4D61703B4C000B72656469726563745572697400124C6A6176612F6C616E672F537472696E673B4C00077265667265736874003B4C6F72672F737072696E676672616D65776F726B2F73656375726974792F6F61757468322F70726F76696465722F546F6B656E526571756573743B4C000B7265736F7572636549647374000F4C6A6176612F7574696C2F5365743B4C000D726573706F6E7365547970657371007E0011787200386F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E70726F76696465722E426173655265717565737436287A3EA37169BD0200034C0008636C69656E74496471007E000F4C001172657175657374506172616D657465727371007E000E4C000573636F706571007E001178707400046A626F73737200256A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C654D6170F1A5A8FE74F507420200014C00016D71007E000E7870737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F400000000000067708000000080000000374000A6772616E745F74797065740012636C69656E745F63726564656E7469616C73740009636C69656E745F69647400046A626F7374000573636F7065740003616C6C78737200256A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C65536574801D92D18F9B80550200007871007E0009737200176A6176612E7574696C2E4C696E6B656448617368536574D86CD75A95DD2A1E020000787200116A6176612E7574696C2E48617368536574BA44859596B8B7340300007870770C000000103F4000000000000171007E001E78017371007E0022770C000000103F40000000000000787371007E00173F40000000000000770800000010000000007870707371007E0022770C000000103F40000000000000787371007E0022770C000000103F400000000000007870);
INSERT INTO `oauth_refresh_token` VALUES ('ca7115a28ab7c1706e54d65d9c4d2c35', 0xACED00057372004C6F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E636F6D6D6F6E2E44656661756C744578706972696E674F417574683252656672657368546F6B656E2FDF47639DD0C9B70200014C000A65787069726174696F6E7400104C6A6176612F7574696C2F446174653B787200446F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E636F6D6D6F6E2E44656661756C744F417574683252656672657368546F6B656E73E10E0A6354D45E0200014C000576616C75657400124C6A6176612F6C616E672F537472696E673B787074002462616134356264652D623335332D343734622D623766632D3266363636663066386631657372000E6A6176612E7574696C2E44617465686A81014B597419030000787077080000017AD214E16478, 0xACED0005737200416F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E70726F76696465722E4F417574683241757468656E7469636174696F6EBD400B02166252130200024C000D73746F7265645265717565737474003C4C6F72672F737072696E676672616D65776F726B2F73656375726974792F6F61757468322F70726F76696465722F4F4175746832526571756573743B4C00127573657241757468656E7469636174696F6E7400324C6F72672F737072696E676672616D65776F726B2F73656375726974792F636F72652F41757468656E7469636174696F6E3B787200476F72672E737072696E676672616D65776F726B2E73656375726974792E61757468656E7469636174696F6E2E416273747261637441757468656E7469636174696F6E546F6B656ED3AA287E6E47640E0200035A000D61757468656E746963617465644C000B617574686F7269746965737400164C6A6176612F7574696C2F436F6C6C656374696F6E3B4C000764657461696C737400124C6A6176612F6C616E672F4F626A6563743B787000737200266A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C654C697374FC0F2531B5EC8E100200014C00046C6973747400104C6A6176612F7574696C2F4C6973743B7872002C6A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C65436F6C6C656374696F6E19420080CB5EF71E0200014C00016371007E00047870737200136A6176612E7574696C2E41727261794C6973747881D21D99C7619D03000149000473697A657870000000007704000000007871007E000C707372003A6F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E70726F76696465722E4F41757468325265717565737400000000000000010200075A0008617070726F7665644C000B617574686F72697469657371007E00044C000A657874656E73696F6E7374000F4C6A6176612F7574696C2F4D61703B4C000B72656469726563745572697400124C6A6176612F6C616E672F537472696E673B4C00077265667265736874003B4C6F72672F737072696E676672616D65776F726B2F73656375726974792F6F61757468322F70726F76696465722F546F6B656E526571756573743B4C000B7265736F7572636549647374000F4C6A6176612F7574696C2F5365743B4C000D726573706F6E7365547970657371007E0011787200386F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E70726F76696465722E426173655265717565737436287A3EA37169BD0200034C0008636C69656E74496471007E000F4C001172657175657374506172616D657465727371007E000E4C000573636F706571007E001178707400046A626F73737200256A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C654D6170F1A5A8FE74F507420200014C00016D71007E000E7870737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F400000000000067708000000080000000374000A6772616E745F74797065740012636C69656E745F63726564656E7469616C73740009636C69656E745F69647400046A626F7374000573636F7065740003616C6C78737200256A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C65536574801D92D18F9B80550200007871007E0009737200176A6176612E7574696C2E4C696E6B656448617368536574D86CD75A95DD2A1E020000787200116A6176612E7574696C2E48617368536574BA44859596B8B7340300007870770C000000103F4000000000000171007E001E78017371007E0022770C000000103F40000000000000787371007E00173F40000000000000770800000010000000007870707371007E0022770C000000103F40000000000000787371007E0022770C000000103F400000000000007870);
INSERT INTO `oauth_refresh_token` VALUES ('5df1a03751b1f020d02d711eed1914d5', 0xACED00057372004C6F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E636F6D6D6F6E2E44656661756C744578706972696E674F417574683252656672657368546F6B656E2FDF47639DD0C9B70200014C000A65787069726174696F6E7400104C6A6176612F7574696C2F446174653B787200446F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E636F6D6D6F6E2E44656661756C744F417574683252656672657368546F6B656E73E10E0A6354D45E0200014C000576616C75657400124C6A6176612F6C616E672F537472696E673B787074011065794A68624763694F694A49557A49314E694973496E523563434936496B705856434A392E65794A7A593239775A53493657794A68624777695853776959585270496A6F695957526C5A4451344D6A63745A6A6733595330304D7A49354C57493259574D745932557859324E6D4F5463774E324935496977695A586877496A6F784E6A49344E7A55344F5445774C434A7164476B694F694978595749304D6A6335595331684D7A55784C5451774D7A41744F545134595330345A6A67334F545A685A446868596A45694C434A6A62476C6C626E5266615751694F694A71596D397A496E302E785937686761315A6E624354424146445672374B44522D73364751417A444C33312D6D434C386E537458497372000E6A6176612E7574696C2E44617465686A81014B597419030000787077080000017B3998A51078, 0xACED0005737200416F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E70726F76696465722E4F417574683241757468656E7469636174696F6EBD400B02166252130200024C000D73746F7265645265717565737474003C4C6F72672F737072696E676672616D65776F726B2F73656375726974792F6F61757468322F70726F76696465722F4F4175746832526571756573743B4C00127573657241757468656E7469636174696F6E7400324C6F72672F737072696E676672616D65776F726B2F73656375726974792F636F72652F41757468656E7469636174696F6E3B787200476F72672E737072696E676672616D65776F726B2E73656375726974792E61757468656E7469636174696F6E2E416273747261637441757468656E7469636174696F6E546F6B656ED3AA287E6E47640E0200035A000D61757468656E746963617465644C000B617574686F7269746965737400164C6A6176612F7574696C2F436F6C6C656374696F6E3B4C000764657461696C737400124C6A6176612F6C616E672F4F626A6563743B787000737200266A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C654C697374FC0F2531B5EC8E100200014C00046C6973747400104C6A6176612F7574696C2F4C6973743B7872002C6A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C65436F6C6C656374696F6E19420080CB5EF71E0200014C00016371007E00047870737200136A6176612E7574696C2E41727261794C6973747881D21D99C7619D03000149000473697A657870000000007704000000007871007E000C707372003A6F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E70726F76696465722E4F41757468325265717565737400000000000000010200075A0008617070726F7665644C000B617574686F72697469657371007E00044C000A657874656E73696F6E7374000F4C6A6176612F7574696C2F4D61703B4C000B72656469726563745572697400124C6A6176612F6C616E672F537472696E673B4C00077265667265736874003B4C6F72672F737072696E676672616D65776F726B2F73656375726974792F6F61757468322F70726F76696465722F546F6B656E526571756573743B4C000B7265736F7572636549647374000F4C6A6176612F7574696C2F5365743B4C000D726573706F6E7365547970657371007E0011787200386F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E70726F76696465722E426173655265717565737436287A3EA37169BD0200034C0008636C69656E74496471007E000F4C001172657175657374506172616D657465727371007E000E4C000573636F706571007E001178707400046A626F73737200256A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C654D6170F1A5A8FE74F507420200014C00016D71007E000E7870737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F400000000000067708000000080000000374000A6772616E745F74797065740012636C69656E745F63726564656E7469616C73740009636C69656E745F69647400046A626F7374000573636F7065740003616C6C78737200256A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C65536574801D92D18F9B80550200007871007E0009737200176A6176612E7574696C2E4C696E6B656448617368536574D86CD75A95DD2A1E020000787200116A6176612E7574696C2E48617368536574BA44859596B8B7340300007870770C000000103F4000000000000171007E001E78017371007E0022770C000000103F40000000000000787371007E00173F40000000000000770800000010000000007870707371007E0022770C000000103F40000000000000787371007E0022770C000000103F400000000000007870);
INSERT INTO `oauth_refresh_token` VALUES ('5de2148d4f2c92c7056b0c8d3ac2b0ac', 0xACED00057372004C6F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E636F6D6D6F6E2E44656661756C744578706972696E674F417574683252656672657368546F6B656E2FDF47639DD0C9B70200014C000A65787069726174696F6E7400104C6A6176612F7574696C2F446174653B787200446F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E636F6D6D6F6E2E44656661756C744F417574683252656672657368546F6B656E73E10E0A6354D45E0200014C000576616C75657400124C6A6176612F6C616E672F537472696E673B787074011065794A68624763694F694A49557A49314E694973496E523563434936496B705856434A392E65794A7A593239775A53493657794A68624777695853776959585270496A6F694D325268595459325A5749745A6D5A6C597930304F574A6B4C5745325A6A63744E4455304D5755794E6D4D345A444A6B496977695A586877496A6F784E6A49344F444D7A4E5445794C434A7164476B694F694A6B4F54637A595451784E43316A4F4445324C54526B596A5574595749774E5330354E7A59344F47526B4F44686D5A5467694C434A6A62476C6C626E5266615751694F694A71596D397A496E302E6E616D785F67396657706B77376B433178686B765A4A573148434F567A7757366462584E497472533573637372000E6A6176612E7574696C2E44617465686A81014B597419030000787077080000017B3E0AFC7478, 0xACED0005737200416F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E70726F76696465722E4F417574683241757468656E7469636174696F6EBD400B02166252130200024C000D73746F7265645265717565737474003C4C6F72672F737072696E676672616D65776F726B2F73656375726974792F6F61757468322F70726F76696465722F4F4175746832526571756573743B4C00127573657241757468656E7469636174696F6E7400324C6F72672F737072696E676672616D65776F726B2F73656375726974792F636F72652F41757468656E7469636174696F6E3B787200476F72672E737072696E676672616D65776F726B2E73656375726974792E61757468656E7469636174696F6E2E416273747261637441757468656E7469636174696F6E546F6B656ED3AA287E6E47640E0200035A000D61757468656E746963617465644C000B617574686F7269746965737400164C6A6176612F7574696C2F436F6C6C656374696F6E3B4C000764657461696C737400124C6A6176612F6C616E672F4F626A6563743B787000737200266A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C654C697374FC0F2531B5EC8E100200014C00046C6973747400104C6A6176612F7574696C2F4C6973743B7872002C6A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C65436F6C6C656374696F6E19420080CB5EF71E0200014C00016371007E00047870737200136A6176612E7574696C2E41727261794C6973747881D21D99C7619D03000149000473697A657870000000007704000000007871007E000C707372003A6F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E70726F76696465722E4F41757468325265717565737400000000000000010200075A0008617070726F7665644C000B617574686F72697469657371007E00044C000A657874656E73696F6E7374000F4C6A6176612F7574696C2F4D61703B4C000B72656469726563745572697400124C6A6176612F6C616E672F537472696E673B4C00077265667265736874003B4C6F72672F737072696E676672616D65776F726B2F73656375726974792F6F61757468322F70726F76696465722F546F6B656E526571756573743B4C000B7265736F7572636549647374000F4C6A6176612F7574696C2F5365743B4C000D726573706F6E7365547970657371007E0011787200386F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E70726F76696465722E426173655265717565737436287A3EA37169BD0200034C0008636C69656E74496471007E000F4C001172657175657374506172616D657465727371007E000E4C000573636F706571007E001178707400046A626F73737200256A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C654D6170F1A5A8FE74F507420200014C00016D71007E000E7870737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F400000000000067708000000080000000374000A6772616E745F74797065740012636C69656E745F63726564656E7469616C73740009636C69656E745F69647400046A626F7374000573636F7065740003616C6C78737200256A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C65536574801D92D18F9B80550200007871007E0009737200176A6176612E7574696C2E4C696E6B656448617368536574D86CD75A95DD2A1E020000787200116A6176612E7574696C2E48617368536574BA44859596B8B7340300007870770C000000103F4000000000000171007E001E78017371007E0022770C000000103F40000000000000787371007E00173F40000000000000770800000010000000007870707371007E0022770C000000103F40000000000000787371007E0022770C000000103F400000000000007870);

-- ----------------------------
-- Table structure for oauth_resource_auth
-- ----------------------------
DROP TABLE IF EXISTS `oauth_resource_auth`;
CREATE TABLE `oauth_resource_auth` (
  `CLIENT_ID` varchar(128) NOT NULL,
  `RESOURCE_ID` varchar(1024) NOT NULL,
  PRIMARY KEY (`CLIENT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of oauth_resource_auth
-- ----------------------------
INSERT INTO `oauth_resource_auth` VALUES ('jbos', '/openapi/support/getUserInfo');

-- ----------------------------
-- Table structure for om_order
-- ----------------------------
DROP TABLE IF EXISTS `om_order`;
CREATE TABLE `om_order` (
  `seq_id` varchar(128) NOT NULL COMMENT '序列号',
  `order_mno` varchar(8) DEFAULT NULL COMMENT '订单主号',
  `order_sno` varchar(64) DEFAULT NULL COMMENT '订单副号',
  `acct_seq_id` varchar(128) DEFAULT NULL COMMENT '账号ID',
  `payway` varchar(8) DEFAULT NULL COMMENT '支付方式(10：微信；20：支付宝；30：银行卡；40：信用卡)',
  `order_src` varchar(8) DEFAULT NULL COMMENT '订单来源(10：主站)',
  `order_type` varchar(8) DEFAULT NULL COMMENT '订单类型',
  `order_time` timestamp NULL DEFAULT NULL COMMENT '订单时间',
  `pay_time` timestamp NULL DEFAULT NULL COMMENT '支付时间',
  `trade_time` timestamp NULL DEFAULT NULL COMMENT '成交时间',
  `dstway` varchar(8) DEFAULT NULL COMMENT '配送方式(10：物流：20：自提)',
  `order_status` varchar(8) DEFAULT NULL COMMENT '订单状态(10：待支付；20：已支付；30：支付成功；40：支付失败；50：待发货；60：确认收货；70：完成评价；90：交易成功；99：交易关闭)',
  PRIMARY KEY (`seq_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单信息表';

-- ----------------------------
-- Records of om_order
-- ----------------------------

-- ----------------------------
-- Table structure for om_order_fees
-- ----------------------------
DROP TABLE IF EXISTS `om_order_fees`;
CREATE TABLE `om_order_fees` (
  `seq_id` varchar(128) NOT NULL COMMENT '序列号',
  `order_seq_id` varchar(128) DEFAULT NULL COMMENT '订单序列号',
  PRIMARY KEY (`seq_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of om_order_fees
-- ----------------------------

-- ----------------------------
-- Table structure for om_order_logistics
-- ----------------------------
DROP TABLE IF EXISTS `om_order_logistics`;
CREATE TABLE `om_order_logistics` (
  `seq_id` varchar(128) NOT NULL COMMENT '序列号',
  `order_seq_id` varchar(128) DEFAULT NULL COMMENT '订单序列号',
  `logistics_no` varchar(64) DEFAULT NULL COMMENT '物流单号',
  `logistics_name` varchar(128) DEFAULT NULL COMMENT '物流公司',
  `status` varchar(8) DEFAULT NULL COMMENT '状态(10：待发货；20：发货中：30：已签收；99：物流异常)',
  PRIMARY KEY (`seq_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='物流信息表';

-- ----------------------------
-- Records of om_order_logistics
-- ----------------------------

-- ----------------------------
-- Table structure for om_order_product
-- ----------------------------
DROP TABLE IF EXISTS `om_order_product`;
CREATE TABLE `om_order_product` (
  `seq_id` varchar(128) NOT NULL COMMENT '序列号',
  `order_seq_id` varchar(128) DEFAULT NULL COMMENT '订单序列号',
  `pic_seq_id` varchar(128) DEFAULT NULL COMMENT '商品图片',
  `product_name` varchar(256) DEFAULT NULL COMMENT '商品名称',
  `price` decimal(8,2) DEFAULT NULL COMMENT '价格',
  `amount` int DEFAULT NULL COMMENT '数量',
  `inventory` int DEFAULT NULL COMMENT '库存',
  `totalAmt` decimal(8,2) DEFAULT NULL COMMENT '总计',
  PRIMARY KEY (`seq_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单商品表';

-- ----------------------------
-- Records of om_order_product
-- ----------------------------

-- ----------------------------
-- Table structure for om_order_receipt
-- ----------------------------
DROP TABLE IF EXISTS `om_order_receipt`;
CREATE TABLE `om_order_receipt` (
  `seq_id` varchar(128) NOT NULL COMMENT '序列号',
  `order_seq_id` varchar(128) DEFAULT NULL COMMENT '订单序列号',
  `receipt_name` varchar(128) DEFAULT NULL COMMENT '收货人',
  `mobile_phone` varchar(32) DEFAULT NULL COMMENT '手机号码',
  `post_code` varchar(16) DEFAULT NULL COMMENT '邮政编码',
  `address` varchar(256) DEFAULT NULL COMMENT '详细地址',
  PRIMARY KEY (`seq_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='收货人信息表';

-- ----------------------------
-- Records of om_order_receipt
-- ----------------------------

-- ----------------------------
-- Table structure for pm_comment
-- ----------------------------
DROP TABLE IF EXISTS `pm_comment`;
CREATE TABLE `pm_comment` (
  `seq_id` varchar(128) NOT NULL COMMENT '序列号',
  `member_seq_id` varchar(128) DEFAULT NULL COMMENT '用户昵称',
  `product_seq_id` varchar(128) DEFAULT NULL COMMENT '商品名称',
  `comment` varchar(256) DEFAULT NULL COMMENT '评价内容',
  `ip` varchar(32) DEFAULT NULL COMMENT 'IP',
  `is_visble` int DEFAULT NULL COMMENT '是否显示(1：是；0：否)',
  `comment_time` timestamp NULL DEFAULT NULL COMMENT '评价时间',
  PRIMARY KEY (`seq_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品评价表';

-- ----------------------------
-- Records of pm_comment
-- ----------------------------

-- ----------------------------
-- Table structure for pm_comment_pic
-- ----------------------------
DROP TABLE IF EXISTS `pm_comment_pic`;
CREATE TABLE `pm_comment_pic` (
  `seq_id` varchar(128) NOT NULL COMMENT '序列号',
  `comment_seq_id` varchar(128) DEFAULT NULL COMMENT '评价ID',
  `pic_seq_id` varchar(128) DEFAULT NULL COMMENT '图片ID',
  PRIMARY KEY (`seq_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品评价图片表';

-- ----------------------------
-- Records of pm_comment_pic
-- ----------------------------

-- ----------------------------
-- Table structure for pm_product
-- ----------------------------
DROP TABLE IF EXISTS `pm_product`;
CREATE TABLE `pm_product` (
  `seq_id` varchar(128) NOT NULL COMMENT '序列号',
  `category_code` varchar(128) DEFAULT NULL COMMENT '商品分类',
  `product_code` varchar(64) DEFAULT NULL COMMENT '商品编码',
  `product_name` varchar(256) DEFAULT NULL COMMENT '商品名称',
  `title` varchar(128) DEFAULT NULL COMMENT '标题',
  `brand_seq_id` varchar(128) DEFAULT NULL COMMENT '商品品牌',
  `remark` varchar(512) DEFAULT NULL COMMENT '商品介绍',
  `freight_seq_id` varchar(128) DEFAULT NULL COMMENT '运费模板',
  `weight` decimal(8,2) DEFAULT NULL COMMENT '商品重量',
  `status` varchar(16) DEFAULT NULL COMMENT '状态(10：草稿中；20：已上架；30：待审核；40：未通过；50：已下架)',
  `is_valid` int DEFAULT '1' COMMENT '是否有效(1:有效;0:无效)',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`seq_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品信息表';

-- ----------------------------
-- Records of pm_product
-- ----------------------------
INSERT INTO `pm_product` VALUES ('1db07be5-dc9f-4d7a-ba6e-2b3e34030ae5', '100', '2021122410011110007', null, '毛巾 ', null, null, null, null, '50', '1', '2021-12-24 10:25:32', '2021-12-24 11:02:01');
INSERT INTO `pm_product` VALUES ('23b0be31-7924-4e8a-b387-570aaa238a6d', '100', '2021122410012120003', null, '洗脸盆', null, null, null, null, '50', '1', '2021-12-24 10:51:50', '2023-02-21 17:47:13');
INSERT INTO `pm_product` VALUES ('2df067d7-68e6-445c-970a-60c47ce663b0', '100101', '100027', null, '衣架', null, null, null, null, '99', '1', '2021-11-23 16:08:30', '2023-02-21 17:48:33');
INSERT INTO `pm_product` VALUES ('32ab32cc-6cae-44b6-9458-26bfc0a0c8b9', '100', '2021122410011110004', null, '垃圾袋', null, null, null, null, '50', '1', '2021-12-24 10:23:50', '2021-12-24 11:01:58');
INSERT INTO `pm_product` VALUES ('477bee67-c423-4e9e-9faf-6b4ef860c6e7', '100', '100029', null, '蓝子', null, null, null, null, '50', '1', '2021-11-23 16:21:05', '2023-02-21 17:48:30');
INSERT INTO `pm_product` VALUES ('501f1fd6-c7d3-4913-be1a-a9edff13276e', '100', '2021122410012120004', null, '洗菜蓝', null, null, null, null, '20', '1', '2021-12-24 10:52:56', '2023-02-22 10:26:29');
INSERT INTO `pm_product` VALUES ('9590a1ed-78fb-4a2d-82d1-68d3f33c49e7', '100', '2021122410012120002', null, '牙膏', null, null, null, null, '50', '1', '2021-12-24 10:50:36', '2021-12-24 11:01:59');
INSERT INTO `pm_product` VALUES ('b1f0bc26-f997-4deb-9b54-eb40d7f86587', '100', '2023022110017170013', null, 'dd', null, null, null, null, '99', '1', '2023-02-21 16:18:32', '2023-02-21 17:08:16');
INSERT INTO `pm_product` VALUES ('bba07948-43dd-43fa-a9b8-2be579be77d7', '100', '2021122410011110009', null, '茶杯', null, null, null, null, '50', '1', '2021-12-24 10:29:01', '2021-12-24 11:01:57');
INSERT INTO `pm_product` VALUES ('c527a6e6-fe21-4177-a0b0-7f580510474a', '200101', '20211125100330100', null, '小米电脑', null, null, null, null, '20', '1', '2021-11-25 14:20:19', '2023-02-21 17:47:10');
INSERT INTO `pm_product` VALUES ('db39938e-b2c2-4a93-8f56-1c310e640791', '300101', '20211125100330082', null, '休闲库', null, null, null, null, '50', '1', '2021-11-25 13:50:15', '2021-12-23 15:14:01');
INSERT INTO `pm_product` VALUES ('e49d72af-c356-4ba8-8415-9bb11e007ffb', '200102', '20211125100330096', null, '小米8', null, null, null, null, '20', '1', '2021-11-25 14:11:51', '2021-12-27 11:08:34');
INSERT INTO `pm_product` VALUES ('fab6e241-1a61-40d3-b901-ddf7590e4b84', '300', '2023022110017170004', null, 'dd', null, null, null, null, '99', '1', '2023-02-21 15:23:24', '2023-02-21 17:08:35');

-- ----------------------------
-- Table structure for pm_product_category
-- ----------------------------
DROP TABLE IF EXISTS `pm_product_category`;
CREATE TABLE `pm_product_category` (
  `seq_id` varchar(128) NOT NULL COMMENT '序列号',
  `code` varchar(64) DEFAULT NULL COMMENT '分类编码',
  `code_tree` varchar(128) DEFAULT NULL COMMENT '编码树',
  `parent_code` varchar(64) DEFAULT NULL COMMENT '上级分类',
  `name` varchar(128) DEFAULT NULL COMMENT '分类名称',
  `order_no` int DEFAULT NULL COMMENT '排序',
  `keywords` varchar(128) DEFAULT NULL COMMENT '关键字',
  `remark` varchar(256) DEFAULT NULL COMMENT '备注',
  `is_valid` int DEFAULT '1' COMMENT '是否有效(1:有效;0:无效)',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`seq_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品分类表';

-- ----------------------------
-- Records of pm_product_category
-- ----------------------------
INSERT INTO `pm_product_category` VALUES ('1', '100', null, '0', '家居生活', '1', '家居生活', null, '1', null, null);
INSERT INTO `pm_product_category` VALUES ('100101', '100101101', null, '100101', '毛巾/浴巾', '1', '毛巾/浴巾', null, '1', null, null);
INSERT INTO `pm_product_category` VALUES ('101', '100101', null, '100', '居家日用', '1', '居家日用', null, '1', null, null);
INSERT INTO `pm_product_category` VALUES ('2', '200', null, '0', '数码电器', '2', '数码电器', null, '1', null, null);
INSERT INTO `pm_product_category` VALUES ('201', '200101', null, '200', '电脑', '1', '电脑', null, '1', null, null);
INSERT INTO `pm_product_category` VALUES ('202', '200102', null, '200', '手机', '2', '手机', null, '1', null, null);
INSERT INTO `pm_product_category` VALUES ('3', '300', null, '0', '服饰箱包', '3', '服饰箱包', null, '1', null, null);
INSERT INTO `pm_product_category` VALUES ('301', '300101', null, '300', '男装', '1', '男装', null, '1', null, null);

-- ----------------------------
-- Table structure for pm_product_list
-- ----------------------------
DROP TABLE IF EXISTS `pm_product_list`;
CREATE TABLE `pm_product_list` (
  `seq_id` varchar(128) NOT NULL COMMENT '序列号',
  `product_seq_id` varchar(128) DEFAULT NULL COMMENT '商品ID',
  `price_scope` varchar(64) DEFAULT NULL COMMENT '售价范围',
  `amount` int DEFAULT NULL COMMENT '销售数量',
  `inventory` int DEFAULT NULL COMMENT '库存数量',
  `is_valid` int DEFAULT '1' COMMENT '是否有效(1:有效;0:无效)',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`seq_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品列表表';

-- ----------------------------
-- Records of pm_product_list
-- ----------------------------
INSERT INTO `pm_product_list` VALUES ('12206689-81db-41a3-94ae-e614dfb58329', '23b0be31-7924-4e8a-b387-570aaa238a6d', null, '0', '0', '1', '2021-12-24 10:51:50', '2021-12-24 10:55:27');
INSERT INTO `pm_product_list` VALUES ('304e9f26-c598-44c6-ac03-a9bfe4ab0b5a', '501f1fd6-c7d3-4913-be1a-a9edff13276e', '¥8.80-8.80', '0', '297', '1', '2021-12-24 10:53:13', '2023-02-22 10:26:29');
INSERT INTO `pm_product_list` VALUES ('5fecf2c0-3b3a-448c-ab80-8109b3a74190', 'bba07948-43dd-43fa-a9b8-2be579be77d7', null, '0', '0', '1', '2021-12-24 10:29:01', '2021-12-24 10:30:45');
INSERT INTO `pm_product_list` VALUES ('6beb0a8c-1dc1-4045-9267-3e286532e085', '32ab32cc-6cae-44b6-9458-26bfc0a0c8b9', null, '0', '0', '1', '2021-12-24 10:23:50', '2021-12-24 10:27:37');
INSERT INTO `pm_product_list` VALUES ('77758f8d-0917-4e71-b6b1-356070f12a6f', 'b1f0bc26-f997-4deb-9b54-eb40d7f86587', null, '0', '0', '1', '2023-02-21 16:18:32', null);
INSERT INTO `pm_product_list` VALUES ('7ac17825-5b8e-46b1-943c-291cb51f93c5', 'e49d72af-c356-4ba8-8415-9bb11e007ffb', '¥1999.00-3999.00', '0', '200', '1', '2021-11-25 14:11:51', '2021-12-24 15:32:55');
INSERT INTO `pm_product_list` VALUES ('7bb640de-3829-4636-b38f-60732e15d0dc', 'fab6e241-1a61-40d3-b901-ddf7590e4b84', null, '0', '0', '1', '2023-02-21 15:23:24', null);
INSERT INTO `pm_product_list` VALUES ('7cc2c907-94d6-4b61-bdf0-b56bb0400f7f', '9590a1ed-78fb-4a2d-82d1-68d3f33c49e7', null, '0', '0', '1', '2021-12-24 10:50:36', '2021-12-24 10:50:59');
INSERT INTO `pm_product_list` VALUES ('7fbd803f-af66-4fa7-ad13-055343d71103', '477bee67-c423-4e9e-9faf-6b4ef860c6e7', '¥0.00', '0', '0', '1', '2021-11-23 16:21:05', '2023-02-21 17:48:19');
INSERT INTO `pm_product_list` VALUES ('9a250883-7b92-4a33-a3e8-f69841fd0451', 'db39938e-b2c2-4a93-8f56-1c310e640791', null, '0', '0', '1', '2021-11-25 13:50:15', '2021-12-23 15:13:51');
INSERT INTO `pm_product_list` VALUES ('a1e476c6-495d-41ab-a3ab-2e110735db27', '1db07be5-dc9f-4d7a-ba6e-2b3e34030ae5', null, '0', '0', '1', '2021-12-24 10:25:32', '2021-12-24 10:26:46');
INSERT INTO `pm_product_list` VALUES ('e58cb2f6-ab86-4bf8-9723-63aa9c761296', '2df067d7-68e6-445c-970a-60c47ce663b0', null, '0', '0', '1', '2021-11-23 15:49:28', '2021-11-25 14:21:03');
INSERT INTO `pm_product_list` VALUES ('fbfafebf-a2d2-4fc9-8945-a79b1a961878', 'c527a6e6-fe21-4177-a0b0-7f580510474a', '¥3500.00-4999.00', '0', '20', '1', '2021-11-25 14:20:19', '2021-12-24 15:32:59');

-- ----------------------------
-- Table structure for pm_product_pic
-- ----------------------------
DROP TABLE IF EXISTS `pm_product_pic`;
CREATE TABLE `pm_product_pic` (
  `seq_id` varchar(128) NOT NULL COMMENT '序列号',
  `product_seq_id` varchar(128) DEFAULT NULL COMMENT '商品ID',
  `pic_seq_id` varchar(128) DEFAULT NULL COMMENT '图片ID',
  `order_no` int DEFAULT NULL,
  `is_valid` int DEFAULT '1' COMMENT '是否有效(1:有效;0:无效)',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`seq_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品主图表';

-- ----------------------------
-- Records of pm_product_pic
-- ----------------------------
INSERT INTO `pm_product_pic` VALUES ('1177277e-4975-42da-9ca9-17eab2d89a55', 'e49d72af-c356-4ba8-8415-9bb11e007ffb', '781bbbf6-9125-4236-978d-bad2fb7e8b29', null, '1', null, null);
INSERT INTO `pm_product_pic` VALUES ('1a57bf28-2970-4d1e-babd-37a386fcb72c', '501f1fd6-c7d3-4913-be1a-a9edff13276e', 'd67d2d18-8baf-4043-a1d3-6f67f6d899b4', '2', '1', null, null);
INSERT INTO `pm_product_pic` VALUES ('28c83158-c9c5-4e78-a248-72e9bd6d850d', '501f1fd6-c7d3-4913-be1a-a9edff13276e', '141a69c1-1171-4941-a97b-b748340da4c4', '4', '1', null, null);
INSERT INTO `pm_product_pic` VALUES ('3bbb93af-05f1-4bb1-84eb-98679017d5b9', '501f1fd6-c7d3-4913-be1a-a9edff13276e', '6ec911ca-3f52-4973-bad6-d2c6567209e3', '3', '1', null, null);
INSERT INTO `pm_product_pic` VALUES ('43493287-4219-4a97-bb4f-fbf6ee4b0e5f', '501f1fd6-c7d3-4913-be1a-a9edff13276e', 'afae050b-dea4-43d1-803d-28eb2a1db45e', '5', '1', null, null);
INSERT INTO `pm_product_pic` VALUES ('58aa5527-3c70-45fb-9f5f-776038c6e60a', 'e49d72af-c356-4ba8-8415-9bb11e007ffb', '7f764bb5-53c4-4f80-9b09-51431473357d', null, '1', null, null);
INSERT INTO `pm_product_pic` VALUES ('61432d8e-9a2a-4d2d-8d99-468af78fda9a', '501f1fd6-c7d3-4913-be1a-a9edff13276e', '73fa821d-fc04-4917-ae68-05af858d3657', '1', '1', null, null);
INSERT INTO `pm_product_pic` VALUES ('92577283-4263-44b5-901a-780657edc9f6', 'e49d72af-c356-4ba8-8415-9bb11e007ffb', 'c944580a-85df-4dfa-8361-a03e3aac42d1', null, '1', null, null);
INSERT INTO `pm_product_pic` VALUES ('a8b6eafd-63c7-41e0-8fe8-4b7f4e90f56c', 'e49d72af-c356-4ba8-8415-9bb11e007ffb', 'bcf09c87-d98c-4bb1-8a97-66cf3bc5272f', null, '1', null, null);

-- ----------------------------
-- Table structure for pm_product_sku
-- ----------------------------
DROP TABLE IF EXISTS `pm_product_sku`;
CREATE TABLE `pm_product_sku` (
  `seq_id` varchar(128) NOT NULL COMMENT '序列号',
  `product_seq_id` varchar(128) DEFAULT NULL COMMENT '商品ID',
  `specs_seq_id` varchar(128) DEFAULT NULL COMMENT '商品规格',
  `specs_name` varchar(128) DEFAULT NULL COMMENT '规格名称',
  `sell_price` decimal(8,2) DEFAULT NULL COMMENT '商品售价',
  `market_price` decimal(8,2) DEFAULT NULL COMMENT '市场售价',
  `inventory_amount` int DEFAULT NULL COMMENT '库存数量',
  `warn_amount` int DEFAULT NULL COMMENT '预警库存',
  `unit` varchar(32) DEFAULT NULL COMMENT '计量单位',
  `pic_seq_id` varchar(128) DEFAULT NULL COMMENT '图片ID',
  `order_no` int DEFAULT NULL COMMENT '排序',
  `is_valid` int DEFAULT '1' COMMENT '是否有效(1:有效;0:无效)',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`seq_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品SKU表';

-- ----------------------------
-- Records of pm_product_sku
-- ----------------------------
INSERT INTO `pm_product_sku` VALUES ('01fc257a-95b0-4b62-8f59-2cf476a24b9c', 'c527a6e6-fe21-4177-a0b0-7f580510474a', null, '笔计本I7', '4999.00', '0.00', '10', '0', null, null, '0', '1', null, null);
INSERT INTO `pm_product_sku` VALUES ('06f89362-0568-4155-a977-d48893f72f71', 'e49d72af-c356-4ba8-8415-9bb11e007ffb', null, '蓝色旗舰版', '3999.00', '0.00', '100', '0', null, null, '0', '1', null, null);
INSERT INTO `pm_product_sku` VALUES ('173a864b-4650-49c8-a1ab-ced211239864', '501f1fd6-c7d3-4913-be1a-a9edff13276e', null, '北欧紫', '8.80', '0.00', '99', '0', null, null, '2', '1', null, null);
INSERT INTO `pm_product_sku` VALUES ('4f36cf08-7f42-4f2f-93b3-984589e840d6', 'c527a6e6-fe21-4177-a0b0-7f580510474a', null, '笔计本I5', '3500.00', '0.00', '10', '0', null, null, '1', '1', null, null);
INSERT INTO `pm_product_sku` VALUES ('88f4581b-283d-4411-88ee-2d59f0c67d11', 'b1f0bc26-f997-4deb-9b54-eb40d7f86587', null, '', '0.00', '0.00', '1', '0', null, null, '0', '1', null, null);
INSERT INTO `pm_product_sku` VALUES ('8c2a3e05-b842-4242-9ff2-bc1320a7bb4e', 'e49d72af-c356-4ba8-8415-9bb11e007ffb', null, '黑色普通版', '1999.00', '0.00', '100', '0', null, null, '1', '1', null, null);
INSERT INTO `pm_product_sku` VALUES ('c8a5e69f-a437-4841-b00a-c38b87c79774', '501f1fd6-c7d3-4913-be1a-a9edff13276e', null, '北欧绿', '8.80', '0.00', '99', '0', null, null, '1', '1', null, null);
INSERT INTO `pm_product_sku` VALUES ('ce910fcf-6e46-46ab-9eff-c78e7e58fc31', '501f1fd6-c7d3-4913-be1a-a9edff13276e', null, '灰蓝色', '8.80', '0.00', '99', '0', null, null, '0', '1', null, null);

-- ----------------------------
-- Table structure for pm_product_specs
-- ----------------------------
DROP TABLE IF EXISTS `pm_product_specs`;
CREATE TABLE `pm_product_specs` (
  `seq_id` varchar(128) NOT NULL COMMENT '序列号',
  `product_seq_id` varchar(128) DEFAULT NULL COMMENT '商品ID',
  `code` varchar(16) DEFAULT NULL COMMENT '规格编码',
  `name` varchar(16) DEFAULT NULL COMMENT '规格名称',
  PRIMARY KEY (`seq_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品规格表';

-- ----------------------------
-- Records of pm_product_specs
-- ----------------------------

-- ----------------------------
-- Table structure for pm_product_type
-- ----------------------------
DROP TABLE IF EXISTS `pm_product_type`;
CREATE TABLE `pm_product_type` (
  `seq_id` varchar(128) NOT NULL COMMENT '序列号',
  `type_code` varchar(32) DEFAULT NULL COMMENT '类型编码',
  `type_name` varchar(32) DEFAULT NULL COMMENT '类型名称'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品类型表';

-- ----------------------------
-- Records of pm_product_type
-- ----------------------------

-- ----------------------------
-- Table structure for pm_product_type_property
-- ----------------------------
DROP TABLE IF EXISTS `pm_product_type_property`;
CREATE TABLE `pm_product_type_property` (
  `seq_id` varchar(128) NOT NULL COMMENT '序列号',
  `type_code` varchar(128) DEFAULT NULL COMMENT '商品类型',
  `property_code` varchar(128) DEFAULT NULL COMMENT '属性名称',
  `property_dom_tag` varchar(32) DEFAULT NULL COMMENT 'DOM标签(text,select,check)',
  `order_no` int DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`seq_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品类型属性表';

-- ----------------------------
-- Records of pm_product_type_property
-- ----------------------------

-- ----------------------------
-- Table structure for pm_property_item
-- ----------------------------
DROP TABLE IF EXISTS `pm_property_item`;
CREATE TABLE `pm_property_item` (
  `seq_id` varchar(128) NOT NULL COMMENT '序列号',
  `property_code` varchar(32) DEFAULT NULL COMMENT '属性编码',
  `item_code` varchar(32) DEFAULT NULL COMMENT '科目编码',
  `item_name` varchar(32) DEFAULT NULL COMMENT '科目名称'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='属性分项表';

-- ----------------------------
-- Records of pm_property_item
-- ----------------------------

-- ----------------------------
-- Table structure for pm_property_name
-- ----------------------------
DROP TABLE IF EXISTS `pm_property_name`;
CREATE TABLE `pm_property_name` (
  `seq_id` varchar(128) NOT NULL COMMENT '序列号',
  `property_code` varchar(32) DEFAULT NULL COMMENT '属性编码',
  `property_name` varchar(32) DEFAULT NULL COMMENT '属性名称'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='属性名称表';

-- ----------------------------
-- Records of pm_property_name
-- ----------------------------
