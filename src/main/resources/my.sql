/*
 Navicat Premium Data Transfer

 Source Server         : imwenwen
 Source Server Type    : MySQL
 Source Server Version : 50729
 Source Host           : localhost:3306
 Source Schema         : flower-mall

 Target Server Type    : MySQL
 Target Server Version : 50729
 File Encoding         : 65001

 Date: 01/11/2022 23:51:33
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for mall_admin_user
-- ----------------------------
DROP TABLE IF EXISTS `mall_admin_user`;
CREATE TABLE `mall_admin_user`  (
                                    `admin_user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '管理员id',
                                    `login_user_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '管理员登陆名称',
                                    `login_password` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '管理员登陆密码',
                                    `nick_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '管理员显示昵称',
                                    `locked` tinyint(4) NULL DEFAULT 0 COMMENT '是否锁定 0未锁定 1已锁定无法登陆',
                                    PRIMARY KEY (`admin_user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mall_admin_user
-- ----------------------------
INSERT INTO `mall_admin_user` VALUES (1, 'admin', 'e10adc3949ba59abbe56e057f20f883e', '十三', 0);
INSERT INTO `mall_admin_user` VALUES (2, 'newbee-admin1', 'e10adc3949ba59abbe56e057f20f883e', '新蜂01', 0);
INSERT INTO `mall_admin_user` VALUES (3, 'newbee-admin2', 'e10adc3949ba59abbe56e057f20f883e', '新蜂02', 0);

-- ----------------------------
-- Table structure for mall_carousel
-- ----------------------------
DROP TABLE IF EXISTS `mall_carousel`;
CREATE TABLE `mall_carousel`  (
                                  `carousel_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '首页轮播图主键id',
                                  `carousel_url` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '轮播图',
                                  `carousel_rank` int(11) NOT NULL DEFAULT 0 COMMENT '排序值(字段越大越靠前)',
                                  `deleted` tinyint(4) NOT NULL DEFAULT 0 COMMENT '删除标识字段(0-未删除 1-已删除)',
                                  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
                                  `create_user` int(11) NOT NULL DEFAULT 0 COMMENT '创建者id',
                                  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '修改时间',
                                  `update_user` int(11) NOT NULL DEFAULT 0 COMMENT '修改者id',
                                  PRIMARY KEY (`carousel_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mall_carousel
-- ----------------------------
INSERT INTO `mall_carousel` VALUES (9, 'http://localhost:8081/upload/20221031_22594355.jpg', 0, 1, '2022-10-31 22:59:46', 0, '2022-10-31 23:38:32', 0);
INSERT INTO `mall_carousel` VALUES (10, 'http://localhost:8081/admin/dist/img/img-upload.png', 1, 1, '2022-10-31 22:59:52', 0, '2022-10-31 23:00:13', 0);
INSERT INTO `mall_carousel` VALUES (11, 'http://localhost:8081/upload/20221031_23373478.jpg', 0, 1, '2022-10-31 23:37:35', 0, '2022-10-31 23:38:32', 0);
INSERT INTO `mall_carousel` VALUES (12, 'http://localhost:8081/upload/20221031_23374860.jpg', 1, 1, '2022-10-31 23:37:51', 0, '2022-10-31 23:38:32', 0);
INSERT INTO `mall_carousel` VALUES (13, 'http://localhost:8081/upload/20221031_23384194.jpg', 0, 0, '2022-10-31 23:38:42', 0, '2022-10-31 23:38:42', 0);
INSERT INTO `mall_carousel` VALUES (14, 'http://localhost:8081/upload/20221031_23385176.png', 0, 0, '2022-10-31 23:38:52', 0, '2022-10-31 23:38:52', 0);

-- ----------------------------
-- Table structure for mall_goods_category
-- ----------------------------
DROP TABLE IF EXISTS `mall_goods_category`;
CREATE TABLE `mall_goods_category`  (
                                        `category_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '分类id',
                                        `category_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '分类名称',
                                        PRIMARY KEY (`category_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 107 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mall_goods_category
-- ----------------------------
INSERT INTO `mall_goods_category` VALUES (1, '热门花种');
INSERT INTO `mall_goods_category` VALUES (2, '新品上线');
INSERT INTO `mall_goods_category` VALUES (3, '为你推荐');

-- ----------------------------
-- Table structure for mall_goods_info
-- ----------------------------
DROP TABLE IF EXISTS `mall_goods_info`;
CREATE TABLE `mall_goods_info`  (
                                    `goods_id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '商品表主键id',
                                    `goods_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '商品名',
                                    `goods_intro` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '商品简介',
                                    `goods_category_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '关联分类id',
                                    `goods_cover_img` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '/admin/dist/img/no-img.png' COMMENT '商品主图',
                                    `goods_carousel` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '/admin/dist/img/no-img.png' COMMENT '商品轮播图',
                                    `goods_detail_content` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商品详情',
                                    `original_price` int(11) NOT NULL DEFAULT 1 COMMENT '商品价格',
                                    `selling_price` int(11) NOT NULL DEFAULT 1 COMMENT '商品实际售价',
                                    `stock_num` int(11) NOT NULL DEFAULT 0 COMMENT '商品库存数量',
                                    `tag` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '商品标签',
                                    `goods_sell_status` tinyint(4) NOT NULL DEFAULT 0 COMMENT '商品上架状态 0-下架 1-上架',
                                    `create_user` int(11) NOT NULL DEFAULT 0 COMMENT '添加者主键id',
                                    `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '商品添加时间',
                                    `update_user` int(11) NOT NULL DEFAULT 0 COMMENT '修改者主键id',
                                    `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '商品修改时间',
                                    PRIMARY KEY (`goods_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10908 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mall_goods_info
-- ----------------------------

-- ----------------------------
-- Table structure for mall_index_config
-- ----------------------------
DROP TABLE IF EXISTS `mall_index_config`;
CREATE TABLE `mall_index_config`  (
                                      `config_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '首页配置项主键id',
                                      `config_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '显示字符(配置搜索时不可为空，其他可为空)',
                                      `config_type` tinyint(4) NOT NULL DEFAULT 0 COMMENT '1-搜索框热搜 2-搜索下拉框热搜 3-(首页)热销商品 4-(首页)新品上线 5-(首页)为你推荐',
                                      `goods_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '商品id 默认为0',
                                      `config_rank` int(11) NOT NULL DEFAULT 0 COMMENT '排序值(字段越大越靠前)',
                                      `deleted` tinyint(4) NOT NULL DEFAULT 0 COMMENT '删除标识字段(0-未删除 1-已删除)',
                                      `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
                                      `create_user` int(11) NOT NULL DEFAULT 0 COMMENT '创建者id',
                                      `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '最新修改时间',
                                      `update_user` int(11) NULL DEFAULT 0 COMMENT '修改者id',
                                      PRIMARY KEY (`config_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 25 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mall_index_config
-- ----------------------------
INSERT INTO `mall_index_config` VALUES (1, '热销商品 iPhone XR', 3, 10284, 10, 1, '2019-09-18 17:04:56', 0, '2022-10-31 23:39:38', 0);
INSERT INTO `mall_index_config` VALUES (2, '热销商品 华为 Mate20', 3, 10779, 100, 1, '2019-09-18 17:05:27', 0, '2022-10-31 23:39:38', 0);
INSERT INTO `mall_index_config` VALUES (3, '热销商品 荣耀8X', 3, 10700, 300, 1, '2019-09-18 17:08:02', 0, '2022-10-31 23:39:38', 0);
INSERT INTO `mall_index_config` VALUES (4, '热销商品 Apple AirPods', 3, 10159, 101, 1, '2019-09-18 17:08:56', 0, '2022-10-31 23:39:38', 0);
INSERT INTO `mall_index_config` VALUES (5, '新品上线 Macbook Pro', 4, 10269, 100, 0, '2019-09-18 17:10:36', 0, '2019-09-18 17:10:36', 0);
INSERT INTO `mall_index_config` VALUES (6, '新品上线 荣耀 9X Pro', 4, 10755, 100, 0, '2019-09-18 17:11:05', 0, '2019-09-18 17:11:05', 0);
INSERT INTO `mall_index_config` VALUES (7, '新品上线 iPhone 11', 4, 10283, 102, 0, '2019-09-18 17:11:44', 0, '2019-09-18 17:11:44', 0);
INSERT INTO `mall_index_config` VALUES (8, '新品上线 iPhone 11 Pro', 4, 10320, 101, 0, '2019-09-18 17:11:58', 0, '2019-09-18 17:11:58', 0);
INSERT INTO `mall_index_config` VALUES (9, '新品上线 华为无线耳机', 4, 10186, 100, 0, '2019-09-18 17:12:29', 0, '2019-09-18 17:12:29', 0);
INSERT INTO `mall_index_config` VALUES (10, '纪梵希高定香榭天鹅绒唇膏', 5, 10233, 98, 0, '2019-09-18 17:47:23', 0, '2019-09-18 17:47:23', 0);
INSERT INTO `mall_index_config` VALUES (11, 'MAC 磨砂系列', 5, 10237, 100, 0, '2019-09-18 17:47:44', 0, '2019-09-18 17:47:44', 0);
INSERT INTO `mall_index_config` VALUES (12, '索尼 WH-1000XM3', 5, 10195, 102, 0, '2019-09-18 17:48:00', 0, '2019-09-18 17:48:00', 0);
INSERT INTO `mall_index_config` VALUES (13, 'Apple AirPods', 5, 10180, 101, 0, '2019-09-18 17:49:11', 0, '2019-09-18 17:49:11', 0);
INSERT INTO `mall_index_config` VALUES (14, '小米 Redmi AirDots', 5, 10160, 100, 0, '2019-09-18 17:49:28', 0, '2019-09-18 17:49:28', 0);
INSERT INTO `mall_index_config` VALUES (15, '2019 MacBookAir 13', 5, 10254, 100, 0, '2019-09-18 17:50:18', 0, '2019-09-18 17:50:18', 0);
INSERT INTO `mall_index_config` VALUES (16, '女式粗棉线条纹长袖T恤', 5, 10158, 99, 0, '2019-09-18 17:52:03', 0, '2019-09-18 17:52:03', 0);
INSERT INTO `mall_index_config` VALUES (17, '塑料浴室座椅', 5, 10154, 100, 0, '2019-09-18 17:52:19', 0, '2019-09-18 17:52:19', 0);
INSERT INTO `mall_index_config` VALUES (18, '靠垫', 5, 10147, 101, 0, '2019-09-18 17:52:50', 0, '2019-09-18 17:52:50', 0);
INSERT INTO `mall_index_config` VALUES (19, '小型超声波香薰机', 5, 10113, 100, 0, '2019-09-18 17:54:07', 0, '2019-09-18 17:54:07', 0);
INSERT INTO `mall_index_config` VALUES (20, '11', 5, 1, 0, 1, '2019-09-19 08:31:11', 0, '2019-09-19 08:31:20', 0);
INSERT INTO `mall_index_config` VALUES (21, '热销商品 华为 P30', 3, 10742, 200, 1, '2019-09-19 23:23:38', 0, '2022-10-31 23:39:38', 0);
INSERT INTO `mall_index_config` VALUES (22, '新品上线 华为Mate30 Pro', 4, 10893, 200, 0, '2019-09-19 23:26:05', 0, '2019-09-19 23:26:05', 0);
INSERT INTO `mall_index_config` VALUES (23, '新品上线 华为 Mate 30', 4, 10895, 199, 0, '2019-09-19 23:26:32', 0, '2019-09-19 23:26:32', 0);
INSERT INTO `mall_index_config` VALUES (24, '华为 Mate 30 Pro', 5, 10894, 101, 0, '2019-09-19 23:27:00', 0, '2019-09-19 23:27:00', 0);

-- ----------------------------
-- Table structure for mall_order
-- ----------------------------
DROP TABLE IF EXISTS `mall_order`;
CREATE TABLE `mall_order`  (
                               `order_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '订单表主键id',
                               `order_no` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '订单号',
                               `user_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '用户主键id',
                               `total_price` int(11) NOT NULL DEFAULT 1 COMMENT '订单总价',
                               `pay_status` tinyint(4) NOT NULL DEFAULT 0 COMMENT '支付状态:0.未支付,1.支付成功,-1:支付失败',
                               `pay_type` tinyint(4) NOT NULL DEFAULT 0 COMMENT '0.无 1.支付宝支付 2.微信支付',
                               `pay_time` datetime(0) NULL DEFAULT NULL COMMENT '支付时间',
                               `order_status` tinyint(4) NOT NULL DEFAULT 0 COMMENT '订单状态:0.待支付 1.已支付 2.配货完成 3:出库成功 4.交易成功 -1.手动关闭 -2.超时关闭 -3.商家关闭',
                               `extra_info` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '订单body',
                               `user_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '收货人姓名',
                               `user_phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '收货人手机号',
                               `user_address` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '收货人收货地址',
                               `deleted` tinyint(4) NOT NULL DEFAULT 0 COMMENT '删除标识字段(0-未删除 1-已删除)',
                               `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
                               `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '最新修改时间',
                               PRIMARY KEY (`order_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 24 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mall_order
-- ----------------------------

-- ----------------------------
-- Table structure for mall_order_item
-- ----------------------------
DROP TABLE IF EXISTS `mall_order_item`;
CREATE TABLE `mall_order_item`  (
                                    `order_item_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '订单关联购物项主键id',
                                    `order_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '订单主键id',
                                    `goods_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '关联商品id',
                                    `goods_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '下单时商品的名称(订单快照)',
                                    `goods_cover_img` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '下单时商品的主图(订单快照)',
                                    `selling_price` int(11) NOT NULL DEFAULT 1 COMMENT '下单时商品的价格(订单快照)',
                                    `goods_count` int(11) NOT NULL DEFAULT 1 COMMENT '数量(订单快照)',
                                    `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
                                    PRIMARY KEY (`order_item_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 38 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mall_order_item
-- ----------------------------

-- ----------------------------
-- Table structure for mall_shopping_cart_item
-- ----------------------------
DROP TABLE IF EXISTS `mall_shopping_cart_item`;
CREATE TABLE `mall_shopping_cart_item`  (
                                            `cart_item_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '购物项主键id',
                                            `user_id` bigint(20) NOT NULL COMMENT '用户主键id',
                                            `goods_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '关联商品id',
                                            `goods_count` int(11) NOT NULL DEFAULT 1 COMMENT '数量(最大为5)',
                                            `deleted` tinyint(4) NOT NULL DEFAULT 0 COMMENT '删除标识字段(0-未删除 1-已删除)',
                                            `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
                                            `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '最新修改时间',
                                            PRIMARY KEY (`cart_item_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 73 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mall_shopping_cart_item
-- ----------------------------
INSERT INTO `mall_shopping_cart_item` VALUES (69, 9, 10154, 1, 1, '2022-10-19 22:45:30', '2022-10-19 22:45:30');
INSERT INTO `mall_shopping_cart_item` VALUES (70, 9, 10907, 1, 1, '2022-10-27 23:34:21', '2022-10-27 23:39:08');
INSERT INTO `mall_shopping_cart_item` VALUES (71, 9, 10700, 1, 1, '2022-10-29 23:44:05', '2022-10-29 23:44:05');
INSERT INTO `mall_shopping_cart_item` VALUES (72, 9, 10907, 1, 1, '2022-10-30 00:26:08', '2022-10-30 00:26:08');

-- ----------------------------
-- Table structure for mall_user
-- ----------------------------
DROP TABLE IF EXISTS `mall_user`;
CREATE TABLE `mall_user`  (
                              `user_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户主键id',
                              `nick_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '用户昵称',
                              `login_name` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '登陆名称(默认为手机号)',
                              `password_md5` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT 'MD5加密后的密码',
                              `introduce_sign` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '个性签名',
                              `address` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '收货地址',
                              `deleted` tinyint(4) NOT NULL DEFAULT 0 COMMENT '注销标识字段(0-正常 1-已注销)',
                              `locked_flag` tinyint(4) NOT NULL DEFAULT 0 COMMENT '锁定标识字段(0-未锁定 1-已锁定)',
                              `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '注册时间',
                              PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mall_user
-- ----------------------------
INSERT INTO `mall_user` VALUES (1, '十三', '13700002703', 'e10adc3949ba59abbe56e057f20f883e', '我不怕千万人阻挡，只怕自己投降', '杭州市西湖区xx小区x幢419 十三 137xxxx2703', 0, 0, '2019-09-22 08:44:57');
INSERT INTO `mall_user` VALUES (6, '测试用户1', '13711113333', 'dda01dc6d334badcd031102be6bee182', '测试用户1', '上海浦东新区XX路XX号 999 137xxxx7797', 0, 0, '2019-08-29 10:51:39');
INSERT INTO `mall_user` VALUES (7, '测试用户2测试用户2测试用户2测试用户2', '13811113333', 'dda01dc6d334badcd031102be6bee182', '测试用户2', '杭州市西湖区xx小区x幢419 十三 137xxxx2703', 0, 0, '2019-08-29 10:55:08');
INSERT INTO `mall_user` VALUES (8, '测试用户3', '13911113333', 'dda01dc6d334badcd031102be6bee182', '测试用户3', '杭州市西湖区xx小区x幢419 十三 137xxxx2703', 0, 0, '2019-08-29 10:55:16');
INSERT INTO `mall_user` VALUES (9, '18806039828', '18806039828', 'e10adc3949ba59abbe56e057f20f883e', '', '福建省龙岩市新罗区龙岩市中城团结解放路56号', 0, 0, '2022-10-10 21:44:59');

SET FOREIGN_KEY_CHECKS = 1;
