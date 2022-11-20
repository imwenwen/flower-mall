/*
 Navicat Premium Data Transfer

 Source Server         : aly
 Source Server Type    : MySQL
 Source Server Version : 50740
 Source Host           : 106.15.170.237:13306
 Source Schema         : flower-mall

 Target Server Type    : MySQL
 Target Server Version : 50740
 File Encoding         : 65001

 Date: 20/11/2022 23:06:57
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for mall_admin_user
-- ----------------------------
DROP TABLE IF EXISTS `mall_admin_user`;
CREATE TABLE `mall_admin_user` (
                                   `admin_user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '管理员id',
                                   `login_user_name` varchar(50) NOT NULL COMMENT '管理员登陆名称',
                                   `login_password` varchar(50) NOT NULL COMMENT '管理员登陆密码',
                                   `nick_name` varchar(50) NOT NULL COMMENT '管理员显示昵称',
                                   `locked` tinyint(4) DEFAULT '0' COMMENT '是否锁定 0未锁定 1已锁定无法登陆',
                                   PRIMARY KEY (`admin_user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of mall_admin_user
-- ----------------------------
BEGIN;
INSERT INTO `mall_admin_user` (`admin_user_id`, `login_user_name`, `login_password`, `nick_name`, `locked`) VALUES (1, 'admin', 'e10adc3949ba59abbe56e057f20f883e', '陈文伟', 0);
COMMIT;

-- ----------------------------
-- Table structure for mall_carousel
-- ----------------------------
DROP TABLE IF EXISTS `mall_carousel`;
CREATE TABLE `mall_carousel` (
                                 `carousel_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '首页轮播图主键id',
                                 `carousel_url` varchar(100) NOT NULL DEFAULT '' COMMENT '轮播图',
                                 `carousel_rank` int(11) NOT NULL DEFAULT '0' COMMENT '排序值(字段越大越靠前)',
                                 `deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标识字段(0-未删除 1-已删除)',
                                 `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                 `create_user` int(11) NOT NULL DEFAULT '0' COMMENT '创建者id',
                                 `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
                                 `update_user` int(11) NOT NULL DEFAULT '0' COMMENT '修改者id',
                                 PRIMARY KEY (`carousel_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of mall_carousel
-- ----------------------------
BEGIN;
INSERT INTO `mall_carousel` (`carousel_id`, `carousel_url`, `carousel_rank`, `deleted`, `create_time`, `create_user`, `update_time`, `update_user`) VALUES (22, 'http://106.15.170.237:8081/upload/20221116_21394222.jpg', 0, 0, '2022-11-16 21:39:46', 0, '2022-11-16 21:39:46', 0);
INSERT INTO `mall_carousel` (`carousel_id`, `carousel_url`, `carousel_rank`, `deleted`, `create_time`, `create_user`, `update_time`, `update_user`) VALUES (23, 'http://106.15.170.237:8081/upload/20221116_23425925.jpg', 0, 0, '2022-11-16 23:41:30', 0, '2022-11-16 15:43:01', 0);
INSERT INTO `mall_carousel` (`carousel_id`, `carousel_url`, `carousel_rank`, `deleted`, `create_time`, `create_user`, `update_time`, `update_user`) VALUES (24, 'http://106.15.170.237:8081/upload/20221116_23421184.jpg', 0, 0, '2022-11-16 23:42:12', 0, '2022-11-16 23:42:12', 0);
COMMIT;

-- ----------------------------
-- Table structure for mall_goods_category
-- ----------------------------
DROP TABLE IF EXISTS `mall_goods_category`;
CREATE TABLE `mall_goods_category` (
                                       `goods_category_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '分类id',
                                       `category_name` varchar(50) NOT NULL DEFAULT '' COMMENT '分类名称',
                                       PRIMARY KEY (`goods_category_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of mall_goods_category
-- ----------------------------
BEGIN;
INSERT INTO `mall_goods_category` (`goods_category_id`, `category_name`) VALUES (1, '热门花种');
INSERT INTO `mall_goods_category` (`goods_category_id`, `category_name`) VALUES (2, '新品上线');
INSERT INTO `mall_goods_category` (`goods_category_id`, `category_name`) VALUES (3, '为你推荐');
COMMIT;

-- ----------------------------
-- Table structure for mall_goods_info
-- ----------------------------
DROP TABLE IF EXISTS `mall_goods_info`;
CREATE TABLE `mall_goods_info` (
                                   `goods_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '商品表主键id',
                                   `goods_name` varchar(200) NOT NULL DEFAULT '' COMMENT '商品名',
                                   `goods_intro` varchar(200) NOT NULL DEFAULT '' COMMENT '商品简介',
                                   `goods_category_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '关联分类id',
                                   `goods_cover_img` varchar(200) NOT NULL DEFAULT '/admin/dist/img/no-img.png' COMMENT '商品主图',
                                   `goods_carousel` varchar(500) NOT NULL DEFAULT '/admin/dist/img/no-img.png' COMMENT '商品轮播图',
                                   `goods_detail_content` text NOT NULL COMMENT '商品详情',
                                   `original_price` int(11) NOT NULL DEFAULT '1' COMMENT '商品价格',
                                   `selling_price` int(11) NOT NULL DEFAULT '1' COMMENT '商品实际售价',
                                   `stock_num` int(11) NOT NULL DEFAULT '0' COMMENT '商品库存数量',
                                   `tag` varchar(20) NOT NULL DEFAULT '' COMMENT '商品标签',
                                   `goods_sell_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '商品上架状态 0-下架 1-上架',
                                   `create_user` int(11) NOT NULL DEFAULT '0' COMMENT '添加者主键id',
                                   `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '商品添加时间',
                                   `update_user` int(11) NOT NULL DEFAULT '0' COMMENT '修改者主键id',
                                   `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '商品修改时间',
                                   PRIMARY KEY (`goods_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10935 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of mall_goods_info
-- ----------------------------
BEGIN;
INSERT INTO `mall_goods_info` (`goods_id`, `goods_name`, `goods_intro`, `goods_category_id`, `goods_cover_img`, `goods_carousel`, `goods_detail_content`, `original_price`, `selling_price`, `stock_num`, `tag`, `goods_sell_status`, `create_user`, `create_time`, `update_user`, `update_time`) VALUES (10914, '粉玫瑰', '小熊形状', 1, 'http://106.15.170.237:8081/upload/20221116_23581077.jpg', 'http://106.15.170.237:8081/upload/20221116_23581077.jpg', '粉玫瑰的花语是永远的爱', 6, 6, 10, '1', 0, 0, '2022-11-17 00:18:08', 0, '2022-11-17 00:45:17');
INSERT INTO `mall_goods_info` (`goods_id`, `goods_name`, `goods_intro`, `goods_category_id`, `goods_cover_img`, `goods_carousel`, `goods_detail_content`, `original_price`, `selling_price`, `stock_num`, `tag`, `goods_sell_status`, `create_user`, `create_time`, `update_user`, `update_time`) VALUES (10915, '红玫瑰', '兔子形状', 1, 'http://106.15.170.237:8081/upload/20221117_00414524.jpg', 'http://106.15.170.237:8081/upload/20221117_00414524.jpg', '红玫瑰的花语是热烈的爱', 6, 6, 9, '1', 0, 0, '2022-11-17 00:42:54', 0, '2022-11-17 00:45:17');
INSERT INTO `mall_goods_info` (`goods_id`, `goods_name`, `goods_intro`, `goods_category_id`, `goods_cover_img`, `goods_carousel`, `goods_detail_content`, `original_price`, `selling_price`, `stock_num`, `tag`, `goods_sell_status`, `create_user`, `create_time`, `update_user`, `update_time`) VALUES (10916, '粉玫瑰', '花筒', 1, 'http://106.15.170.237:8081/upload/20221117_00433492.png', 'http://106.15.170.237:8081/upload/20221117_00433492.png', '粉玫瑰的花语是永远的爱', 6, 6, 10, '1', 0, 0, '2022-11-17 00:44:17', 0, '2022-11-17 00:45:17');
INSERT INTO `mall_goods_info` (`goods_id`, `goods_name`, `goods_intro`, `goods_category_id`, `goods_cover_img`, `goods_carousel`, `goods_detail_content`, `original_price`, `selling_price`, `stock_num`, `tag`, `goods_sell_status`, `create_user`, `create_time`, `update_user`, `update_time`) VALUES (10917, '红玫瑰', '爱心', 1, 'http://106.15.170.237:8081/upload/20221117_00444614.jpg', 'http://106.15.170.237:8081/upload/20221117_00444614.jpg', '红玫瑰的花语是热烈的爱', 6, 6, 10, '1', 0, 0, '2022-11-17 00:45:08', 0, '2022-11-17 00:45:17');
INSERT INTO `mall_goods_info` (`goods_id`, `goods_name`, `goods_intro`, `goods_category_id`, `goods_cover_img`, `goods_carousel`, `goods_detail_content`, `original_price`, `selling_price`, `stock_num`, `tag`, `goods_sell_status`, `create_user`, `create_time`, `update_user`, `update_time`) VALUES (10918, '红玫瑰', '爱心', 1, 'http://106.15.170.237:8081/upload/20221117_23250649.jpg', 'http://106.15.170.237:8081/upload/20221117_23250649.jpg', '红玫瑰的花语是热烈的爱', 6, 6, 0, '1', 0, 0, '2022-11-17 23:25:28', 0, '2022-11-17 23:25:28');
INSERT INTO `mall_goods_info` (`goods_id`, `goods_name`, `goods_intro`, `goods_category_id`, `goods_cover_img`, `goods_carousel`, `goods_detail_content`, `original_price`, `selling_price`, `stock_num`, `tag`, `goods_sell_status`, `create_user`, `create_time`, `update_user`, `update_time`) VALUES (10919, '渐变玫瑰', '兔子形状', 1, 'http://106.15.170.237:8081/upload/20221117_23255038.jpg', 'http://106.15.170.237:8081/upload/20221117_23255038.jpg', '红玫瑰的花语是热烈的爱', 6, 6, 10, '1', 1, 0, '2022-11-17 23:26:16', 0, '2022-11-18 00:30:08');
INSERT INTO `mall_goods_info` (`goods_id`, `goods_name`, `goods_intro`, `goods_category_id`, `goods_cover_img`, `goods_carousel`, `goods_detail_content`, `original_price`, `selling_price`, `stock_num`, `tag`, `goods_sell_status`, `create_user`, `create_time`, `update_user`, `update_time`) VALUES (10920, '红玫瑰', '花筒', 2, 'http://106.15.170.237:8081/upload/20221117_23263559.jpg', 'http://106.15.170.237:8081/upload/20221117_23263559.jpg', '红玫瑰的花语是热烈的爱', 6, 6, 10, '1', 0, 0, '2022-11-17 23:27:08', 0, '2022-11-17 23:27:08');
INSERT INTO `mall_goods_info` (`goods_id`, `goods_name`, `goods_intro`, `goods_category_id`, `goods_cover_img`, `goods_carousel`, `goods_detail_content`, `original_price`, `selling_price`, `stock_num`, `tag`, `goods_sell_status`, `create_user`, `create_time`, `update_user`, `update_time`) VALUES (10921, '红玫瑰', '零售鲜花', 1, 'http://106.15.170.237:8081/upload/20221117_2330340.png', 'http://106.15.170.237:8081/upload/20221117_2330340.png', '红玫瑰的花语是热烈的爱', 6, 6, 10, '1', 0, 0, '2022-11-17 23:33:08', 0, '2022-11-17 23:33:08');
INSERT INTO `mall_goods_info` (`goods_id`, `goods_name`, `goods_intro`, `goods_category_id`, `goods_cover_img`, `goods_carousel`, `goods_detail_content`, `original_price`, `selling_price`, `stock_num`, `tag`, `goods_sell_status`, `create_user`, `create_time`, `update_user`, `update_time`) VALUES (10922, '红玫瑰', '永生花', 1, 'http://106.15.170.237:8081/upload/20221117_23334890.jpg', 'http://106.15.170.237:8081/upload/20221117_23334890.jpg', '红玫瑰的花语是热烈的爱', 9, 9, 10, '1', 0, 0, '2022-11-17 23:34:12', 0, '2022-11-17 23:34:12');
INSERT INTO `mall_goods_info` (`goods_id`, `goods_name`, `goods_intro`, `goods_category_id`, `goods_cover_img`, `goods_carousel`, `goods_detail_content`, `original_price`, `selling_price`, `stock_num`, `tag`, `goods_sell_status`, `create_user`, `create_time`, `update_user`, `update_time`) VALUES (10923, '粉玫瑰', '零售鲜花', 1, 'http://106.15.170.237:8081/upload/20221117_23350415.png', 'http://106.15.170.237:8081/upload/20221117_23350415.png', '粉玫瑰的花语是永远的爱', 9, 9, 10, '1', 0, 0, '2022-11-17 23:36:21', 0, '2022-11-17 23:36:21');
INSERT INTO `mall_goods_info` (`goods_id`, `goods_name`, `goods_intro`, `goods_category_id`, `goods_cover_img`, `goods_carousel`, `goods_detail_content`, `original_price`, `selling_price`, `stock_num`, `tag`, `goods_sell_status`, `create_user`, `create_time`, `update_user`, `update_time`) VALUES (10924, '粉白玫瑰', '拼色', 1, 'http://106.15.170.237:8081/upload/20221117_23364151.png', 'http://106.15.170.237:8081/upload/20221117_23364151.png', '粉玫瑰的花语是永远的爱', 6, 6, 10, '1', 0, 0, '2022-11-17 23:38:02', 0, '2022-11-17 23:38:02');
INSERT INTO `mall_goods_info` (`goods_id`, `goods_name`, `goods_intro`, `goods_category_id`, `goods_cover_img`, `goods_carousel`, `goods_detail_content`, `original_price`, `selling_price`, `stock_num`, `tag`, `goods_sell_status`, `create_user`, `create_time`, `update_user`, `update_time`) VALUES (10925, '红玫瑰', '永生花', 1, 'http://106.15.170.237:8081/upload/20221117_23393357.png', 'http://106.15.170.237:8081/upload/20221117_23393357.png', '红玫瑰的花语是热烈的爱', 9, 9, 10, '1', 0, 0, '2022-11-17 23:40:57', 0, '2022-11-17 23:40:57');
INSERT INTO `mall_goods_info` (`goods_id`, `goods_name`, `goods_intro`, `goods_category_id`, `goods_cover_img`, `goods_carousel`, `goods_detail_content`, `original_price`, `selling_price`, `stock_num`, `tag`, `goods_sell_status`, `create_user`, `create_time`, `update_user`, `update_time`) VALUES (10926, '人鱼姬', '永生花', 1, 'http://106.15.170.237:8081/upload/20221117_23421650.jpg', 'http://106.15.170.237:8081/upload/20221117_23421650.jpg', '人鱼姬玫瑰的花语是一半是海洋一半是绮丽的梦', 10, 10, 10, '1', 0, 0, '2022-11-18 00:02:14', 0, '2022-11-18 00:02:14');
INSERT INTO `mall_goods_info` (`goods_id`, `goods_name`, `goods_intro`, `goods_category_id`, `goods_cover_img`, `goods_carousel`, `goods_detail_content`, `original_price`, `selling_price`, `stock_num`, `tag`, `goods_sell_status`, `create_user`, `create_time`, `update_user`, `update_time`) VALUES (10927, '红玫瑰', '老虎', 1, 'http://106.15.170.237:8081/upload/20221118_00025593.jpg', 'http://106.15.170.237:8081/upload/20221118_00025593.jpg', '红玫瑰的花语是热烈的爱', 6, 6, 10, '1', 1, 0, '2022-11-18 00:03:17', 0, '2022-11-18 00:25:46');
INSERT INTO `mall_goods_info` (`goods_id`, `goods_name`, `goods_intro`, `goods_category_id`, `goods_cover_img`, `goods_carousel`, `goods_detail_content`, `original_price`, `selling_price`, `stock_num`, `tag`, `goods_sell_status`, `create_user`, `create_time`, `update_user`, `update_time`) VALUES (10928, '红玫瑰', '零售鲜花', 1, 'http://106.15.170.237:8081/upload/20221118_00034272.png', 'http://106.15.170.237:8081/upload/20221118_00034272.png', '红玫瑰的花语是热烈的爱', 6, 6, 10, '1', 0, 0, '2022-11-18 00:04:02', 0, '2022-11-18 00:04:02');
INSERT INTO `mall_goods_info` (`goods_id`, `goods_name`, `goods_intro`, `goods_category_id`, `goods_cover_img`, `goods_carousel`, `goods_detail_content`, `original_price`, `selling_price`, `stock_num`, `tag`, `goods_sell_status`, `create_user`, `create_time`, `update_user`, `update_time`) VALUES (10929, '红玫瑰', '零售鲜花', 1, 'http://106.15.170.237:8081/upload/20221118_00043145.jpg', 'http://106.15.170.237:8081/upload/20221118_00043145.jpg', '红玫瑰的花语是热烈的爱', 6, 6, 10, '1', 0, 0, '2022-11-18 00:04:49', 0, '2022-11-18 00:04:49');
INSERT INTO `mall_goods_info` (`goods_id`, `goods_name`, `goods_intro`, `goods_category_id`, `goods_cover_img`, `goods_carousel`, `goods_detail_content`, `original_price`, `selling_price`, `stock_num`, `tag`, `goods_sell_status`, `create_user`, `create_time`, `update_user`, `update_time`) VALUES (10930, '红玫瑰', '爱心', 1, 'http://106.15.170.237:8081/upload/20221118_00050030.jpg', 'http://106.15.170.237:8081/upload/20221118_00050030.jpg', '红玫瑰的花语是热烈的爱', 6, 6, 10, '1', 0, 0, '2022-11-18 00:05:16', 0, '2022-11-18 00:05:16');
INSERT INTO `mall_goods_info` (`goods_id`, `goods_name`, `goods_intro`, `goods_category_id`, `goods_cover_img`, `goods_carousel`, `goods_detail_content`, `original_price`, `selling_price`, `stock_num`, `tag`, `goods_sell_status`, `create_user`, `create_time`, `update_user`, `update_time`) VALUES (10931, '鲜花999', '999', 1, 'http://106.15.170.237:8081/upload/20221118_0022337.jpg', 'http://106.15.170.237:8081/upload/20221118_0022337.jpg', '999', 180, 99, 100, '1', 1, 0, '2022-11-18 00:22:39', 0, '2022-11-18 00:25:38');
INSERT INTO `mall_goods_info` (`goods_id`, `goods_name`, `goods_intro`, `goods_category_id`, `goods_cover_img`, `goods_carousel`, `goods_detail_content`, `original_price`, `selling_price`, `stock_num`, `tag`, `goods_sell_status`, `create_user`, `create_time`, `update_user`, `update_time`) VALUES (10932, '花花1', '鲜花爱美人', 1, 'http://106.15.170.237:8081/upload/20221118_00262364.jpg', 'http://106.15.170.237:8081/upload/20221118_00262364.jpg', '1', 100, 50, 11, '1', 0, 0, '2022-11-18 00:26:26', 0, '2022-11-18 00:26:26');
INSERT INTO `mall_goods_info` (`goods_id`, `goods_name`, `goods_intro`, `goods_category_id`, `goods_cover_img`, `goods_carousel`, `goods_detail_content`, `original_price`, `selling_price`, `stock_num`, `tag`, `goods_sell_status`, `create_user`, `create_time`, `update_user`, `update_time`) VALUES (10933, '粉玫瑰', '微景观', 1, 'http://106.15.170.237:8081/upload/20221118_00264485.jpg', 'http://106.15.170.237:8081/upload/20221118_00264485.jpg', '粉玫瑰的花语是永远的爱', 45, 45, 100, '1', 0, 0, '2022-11-18 00:26:47', 0, '2022-11-17 16:32:26');
INSERT INTO `mall_goods_info` (`goods_id`, `goods_name`, `goods_intro`, `goods_category_id`, `goods_cover_img`, `goods_carousel`, `goods_detail_content`, `original_price`, `selling_price`, `stock_num`, `tag`, `goods_sell_status`, `create_user`, `create_time`, `update_user`, `update_time`) VALUES (10934, '粉白玫瑰花台', '永生花', 1, 'http://106.15.170.237:8081/upload/20221118_00270520.jpg', 'http://106.15.170.237:8081/upload/20221118_00270520.jpg', '粉玫瑰的花语是永远的爱', 50, 50, 100, '1', 0, 0, '2022-11-18 00:27:08', 0, '2022-11-17 16:31:07');
COMMIT;

-- ----------------------------
-- Table structure for mall_index_config
-- ----------------------------
DROP TABLE IF EXISTS `mall_index_config`;
CREATE TABLE `mall_index_config` (
                                     `config_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '首页配置项主键id',
                                     `config_name` varchar(50) NOT NULL DEFAULT '' COMMENT '显示字符(配置搜索时不可为空，其他可为空)',
                                     `config_type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '1-搜索框热搜 2-搜索下拉框热搜 3-(首页)热销商品 4-(首页)新品上线 5-(首页)为你推荐',
                                     `goods_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '商品id 默认为0',
                                     `config_rank` int(11) NOT NULL DEFAULT '0' COMMENT '排序值(字段越大越靠前)',
                                     `deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标识字段(0-未删除 1-已删除)',
                                     `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                     `create_user` int(11) NOT NULL DEFAULT '0' COMMENT '创建者id',
                                     `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最新修改时间',
                                     `update_user` int(11) DEFAULT '0' COMMENT '修改者id',
                                     PRIMARY KEY (`config_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of mall_index_config
-- ----------------------------
BEGIN;
INSERT INTO `mall_index_config` (`config_id`, `config_name`, `config_type`, `goods_id`, `config_rank`, `deleted`, `create_time`, `create_user`, `update_time`, `update_user`) VALUES (32, '红玫瑰', 3, 10917, 1, 0, '2022-11-17 00:47:52', 0, '2022-11-16 16:53:33', 0);
INSERT INTO `mall_index_config` (`config_id`, `config_name`, `config_type`, `goods_id`, `config_rank`, `deleted`, `create_time`, `create_user`, `update_time`, `update_user`) VALUES (33, '粉玫瑰', 3, 10916, 2, 0, '2022-11-17 00:49:59', 0, '2022-11-16 16:53:24', 0);
INSERT INTO `mall_index_config` (`config_id`, `config_name`, `config_type`, `goods_id`, `config_rank`, `deleted`, `create_time`, `create_user`, `update_time`, `update_user`) VALUES (34, '红玫瑰', 3, 10915, 3, 0, '2022-11-17 00:50:37', 0, '2022-11-16 16:53:12', 0);
INSERT INTO `mall_index_config` (`config_id`, `config_name`, `config_type`, `goods_id`, `config_rank`, `deleted`, `create_time`, `create_user`, `update_time`, `update_user`) VALUES (35, '粉玫瑰', 3, 10914, 4, 0, '2022-11-17 00:50:48', 0, '2022-11-16 16:52:58', 0);
INSERT INTO `mall_index_config` (`config_id`, `config_name`, `config_type`, `goods_id`, `config_rank`, `deleted`, `create_time`, `create_user`, `update_time`, `update_user`) VALUES (36, '红玫瑰', 4, 10930, 9, 0, '2022-11-18 00:06:12', 0, '2022-11-18 00:06:12', 0);
INSERT INTO `mall_index_config` (`config_id`, `config_name`, `config_type`, `goods_id`, `config_rank`, `deleted`, `create_time`, `create_user`, `update_time`, `update_user`) VALUES (37, '人鱼姬玫瑰', 4, 10926, 8, 0, '2022-11-18 00:06:31', 0, '2022-11-18 00:06:31', 0);
INSERT INTO `mall_index_config` (`config_id`, `config_name`, `config_type`, `goods_id`, `config_rank`, `deleted`, `create_time`, `create_user`, `update_time`, `update_user`) VALUES (38, '粉玫瑰', 4, 10923, 7, 0, '2022-11-18 00:07:01', 0, '2022-11-18 00:07:01', 0);
INSERT INTO `mall_index_config` (`config_id`, `config_name`, `config_type`, `goods_id`, `config_rank`, `deleted`, `create_time`, `create_user`, `update_time`, `update_user`) VALUES (39, '粉玫瑰', 4, 10929, 6, 0, '2022-11-18 00:08:57', 0, '2022-11-18 00:08:57', 0);
INSERT INTO `mall_index_config` (`config_id`, `config_name`, `config_type`, `goods_id`, `config_rank`, `deleted`, `create_time`, `create_user`, `update_time`, `update_user`) VALUES (40, '红玫瑰', 5, 10928, 0, 0, '2022-11-18 00:10:12', 0, '2022-11-18 00:10:12', 0);
INSERT INTO `mall_index_config` (`config_id`, `config_name`, `config_type`, `goods_id`, `config_rank`, `deleted`, `create_time`, `create_user`, `update_time`, `update_user`) VALUES (41, '红玫瑰', 5, 10927, 0, 1, '2022-11-18 00:10:21', 0, '2022-11-18 00:28:58', 0);
INSERT INTO `mall_index_config` (`config_id`, `config_name`, `config_type`, `goods_id`, `config_rank`, `deleted`, `create_time`, `create_user`, `update_time`, `update_user`) VALUES (42, '红玫瑰', 5, 10925, 0, 0, '2022-11-18 00:11:27', 0, '2022-11-18 00:11:27', 0);
INSERT INTO `mall_index_config` (`config_id`, `config_name`, `config_type`, `goods_id`, `config_rank`, `deleted`, `create_time`, `create_user`, `update_time`, `update_user`) VALUES (43, '红玫瑰', 5, 10924, 0, 0, '2022-11-18 00:11:37', 0, '2022-11-18 00:11:37', 0);
INSERT INTO `mall_index_config` (`config_id`, `config_name`, `config_type`, `goods_id`, `config_rank`, `deleted`, `create_time`, `create_user`, `update_time`, `update_user`) VALUES (44, '玫瑰', 5, 10922, 0, 0, '2022-11-18 00:13:05', 0, '2022-11-18 00:13:05', 0);
INSERT INTO `mall_index_config` (`config_id`, `config_name`, `config_type`, `goods_id`, `config_rank`, `deleted`, `create_time`, `create_user`, `update_time`, `update_user`) VALUES (45, '玫瑰', 5, 10921, 0, 0, '2022-11-18 00:13:16', 0, '2022-11-18 00:13:16', 0);
INSERT INTO `mall_index_config` (`config_id`, `config_name`, `config_type`, `goods_id`, `config_rank`, `deleted`, `create_time`, `create_user`, `update_time`, `update_user`) VALUES (46, '玫瑰', 5, 10920, 0, 0, '2022-11-18 00:13:29', 0, '2022-11-18 00:13:29', 0);
INSERT INTO `mall_index_config` (`config_id`, `config_name`, `config_type`, `goods_id`, `config_rank`, `deleted`, `create_time`, `create_user`, `update_time`, `update_user`) VALUES (47, '玫瑰', 5, 10918, 0, 0, '2022-11-18 00:13:40', 0, '2022-11-18 00:13:40', 0);
INSERT INTO `mall_index_config` (`config_id`, `config_name`, `config_type`, `goods_id`, `config_rank`, `deleted`, `create_time`, `create_user`, `update_time`, `update_user`) VALUES (48, '玫瑰', 5, 10919, 0, 0, '2022-11-18 00:14:04', 0, '2022-11-18 00:14:04', 0);
INSERT INTO `mall_index_config` (`config_id`, `config_name`, `config_type`, `goods_id`, `config_rank`, `deleted`, `create_time`, `create_user`, `update_time`, `update_user`) VALUES (49, '玫瑰', 5, 10917, 0, 1, '2022-11-18 00:14:34', 0, '2022-11-18 00:16:05', 0);
INSERT INTO `mall_index_config` (`config_id`, `config_name`, `config_type`, `goods_id`, `config_rank`, `deleted`, `create_time`, `create_user`, `update_time`, `update_user`) VALUES (50, '红玫瑰999', 5, 10931, 0, 1, '2022-11-18 00:23:01', 0, '2022-11-18 00:28:39', 0);
INSERT INTO `mall_index_config` (`config_id`, `config_name`, `config_type`, `goods_id`, `config_rank`, `deleted`, `create_time`, `create_user`, `update_time`, `update_user`) VALUES (51, '玫瑰', 5, 10932, 0, 0, '2022-11-18 00:26:46', 0, '2022-11-18 00:26:46', 0);
INSERT INTO `mall_index_config` (`config_id`, `config_name`, `config_type`, `goods_id`, `config_rank`, `deleted`, `create_time`, `create_user`, `update_time`, `update_user`) VALUES (52, '玫瑰', 5, 10933, 0, 0, '2022-11-18 00:27:26', 0, '2022-11-18 00:27:26', 0);
INSERT INTO `mall_index_config` (`config_id`, `config_name`, `config_type`, `goods_id`, `config_rank`, `deleted`, `create_time`, `create_user`, `update_time`, `update_user`) VALUES (53, '玫瑰', 5, 10934, 0, 0, '2022-11-18 00:27:59', 0, '2022-11-18 00:27:59', 0);
COMMIT;

-- ----------------------------
-- Table structure for mall_order
-- ----------------------------
DROP TABLE IF EXISTS `mall_order`;
CREATE TABLE `mall_order` (
                              `order_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '订单表主键id',
                              `order_no` varchar(20) NOT NULL DEFAULT '' COMMENT '订单号',
                              `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户主键id',
                              `total_price` int(11) NOT NULL DEFAULT '1' COMMENT '订单总价',
                              `pay_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '支付状态:0.未支付,1.支付成功,-1:支付失败',
                              `pay_type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0.无 1.支付宝支付 2.微信支付',
                              `pay_time` datetime DEFAULT NULL COMMENT '支付时间',
                              `order_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '订单状态:0.待支付 1.已支付 2.配货完成 3:出库成功 4.交易成功 -1.手动关闭 -2.超时关闭 -3.商家关闭',
                              `extra_info` varchar(100) NOT NULL DEFAULT '' COMMENT '订单body',
                              `user_name` varchar(30) NOT NULL DEFAULT '' COMMENT '收货人姓名',
                              `user_phone` varchar(11) NOT NULL DEFAULT '' COMMENT '收货人手机号',
                              `user_address` varchar(100) NOT NULL DEFAULT '' COMMENT '收货人收货地址',
                              `deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标识字段(0-未删除 1-已删除)',
                              `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                              `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最新修改时间',
                              PRIMARY KEY (`order_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of mall_order
-- ----------------------------
BEGIN;
INSERT INTO `mall_order` (`order_id`, `order_no`, `user_id`, `total_price`, `pay_status`, `pay_type`, `pay_time`, `order_status`, `extra_info`, `user_name`, `user_phone`, `user_address`, `deleted`, `create_time`, `update_time`) VALUES (1, '16687763263866005', 10, 6, 0, 0, NULL, 0, '', '', '', '福建省三明市三元区三明学院', 0, '2022-11-18 20:58:46', '2022-11-18 20:58:46');
INSERT INTO `mall_order` (`order_id`, `order_no`, `user_id`, `total_price`, `pay_status`, `pay_type`, `pay_time`, `order_status`, `extra_info`, `user_name`, `user_phone`, `user_address`, `deleted`, `create_time`, `update_time`) VALUES (2, '16687764974901811', 10, 9, 0, 0, NULL, -3, '', '', '', '福建省三明市三元区三明学院', 0, '2022-11-18 21:01:37', '2022-11-20 22:41:41');
INSERT INTO `mall_order` (`order_id`, `order_no`, `user_id`, `total_price`, `pay_status`, `pay_type`, `pay_time`, `order_status`, `extra_info`, `user_name`, `user_phone`, `user_address`, `deleted`, `create_time`, `update_time`) VALUES (3, '16689550575537804', 1, 6, 1, 1, '2022-11-20 22:39:59', -1, '', '', '', '111', 0, '2022-11-20 22:37:39', '2022-11-20 22:40:05');
COMMIT;

-- ----------------------------
-- Table structure for mall_order_item
-- ----------------------------
DROP TABLE IF EXISTS `mall_order_item`;
CREATE TABLE `mall_order_item` (
                                   `order_item_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '订单关联购物项主键id',
                                   `order_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '订单主键id',
                                   `goods_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '关联商品id',
                                   `goods_name` varchar(200) NOT NULL DEFAULT '' COMMENT '下单时商品的名称(订单快照)',
                                   `goods_cover_img` varchar(200) NOT NULL DEFAULT '' COMMENT '下单时商品的主图(订单快照)',
                                   `selling_price` int(11) NOT NULL DEFAULT '1' COMMENT '下单时商品的价格(订单快照)',
                                   `goods_count` int(11) NOT NULL DEFAULT '1' COMMENT '数量(订单快照)',
                                   `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                   PRIMARY KEY (`order_item_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of mall_order_item
-- ----------------------------
BEGIN;
INSERT INTO `mall_order_item` (`order_item_id`, `order_id`, `goods_id`, `goods_name`, `goods_cover_img`, `selling_price`, `goods_count`, `create_time`) VALUES (1, 1, 10915, '红玫瑰', 'http://106.15.170.237:8081/upload/20221117_00414524.jpg', 6, 1, '2022-11-18 20:58:46');
INSERT INTO `mall_order_item` (`order_item_id`, `order_id`, `goods_id`, `goods_name`, `goods_cover_img`, `selling_price`, `goods_count`, `create_time`) VALUES (2, 2, 10925, '红玫瑰', 'http://106.15.170.237:8081/upload/20221117_23393357.png', 9, 1, '2022-11-18 21:01:37');
INSERT INTO `mall_order_item` (`order_item_id`, `order_id`, `goods_id`, `goods_name`, `goods_cover_img`, `selling_price`, `goods_count`, `create_time`) VALUES (3, 3, 10915, '红玫瑰', 'http://106.15.170.237:8081/upload/20221117_00414524.jpg', 6, 1, '2022-11-20 22:37:39');
COMMIT;

-- ----------------------------
-- Table structure for mall_shopping_cart_item
-- ----------------------------
DROP TABLE IF EXISTS `mall_shopping_cart_item`;
CREATE TABLE `mall_shopping_cart_item` (
                                           `cart_item_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '购物项主键id',
                                           `user_id` bigint(20) NOT NULL COMMENT '用户主键id',
                                           `goods_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '关联商品id',
                                           `goods_count` int(11) NOT NULL DEFAULT '1' COMMENT '数量(最大为5)',
                                           `deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标识字段(0-未删除 1-已删除)',
                                           `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                           `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最新修改时间',
                                           PRIMARY KEY (`cart_item_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=80 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of mall_shopping_cart_item
-- ----------------------------
BEGIN;
INSERT INTO `mall_shopping_cart_item` (`cart_item_id`, `user_id`, `goods_id`, `goods_count`, `deleted`, `create_time`, `update_time`) VALUES (77, 10, 10915, 1, 1, '2022-11-18 20:57:54', '2022-11-18 20:57:54');
INSERT INTO `mall_shopping_cart_item` (`cart_item_id`, `user_id`, `goods_id`, `goods_count`, `deleted`, `create_time`, `update_time`) VALUES (78, 10, 10925, 1, 1, '2022-11-18 21:01:27', '2022-11-18 21:01:27');
INSERT INTO `mall_shopping_cart_item` (`cart_item_id`, `user_id`, `goods_id`, `goods_count`, `deleted`, `create_time`, `update_time`) VALUES (79, 1, 10915, 1, 1, '2022-11-20 22:35:15', '2022-11-20 22:35:15');
COMMIT;

-- ----------------------------
-- Table structure for mall_user
-- ----------------------------
DROP TABLE IF EXISTS `mall_user`;
CREATE TABLE `mall_user` (
                             `user_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户主键id',
                             `nick_name` varchar(50) NOT NULL DEFAULT '' COMMENT '用户昵称',
                             `login_name` varchar(11) NOT NULL DEFAULT '' COMMENT '登陆名称(默认为手机号)',
                             `password_md5` varchar(32) NOT NULL DEFAULT '' COMMENT 'MD5加密后的密码',
                             `introduce_sign` varchar(100) NOT NULL DEFAULT '' COMMENT '个性签名',
                             `address` varchar(100) NOT NULL DEFAULT '' COMMENT '收货地址',
                             `deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '注销标识字段(0-正常 1-已注销)',
                             `locked_flag` tinyint(4) NOT NULL DEFAULT '0' COMMENT '锁定标识字段(0-未锁定 1-已锁定)',
                             `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
                             PRIMARY KEY (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of mall_user
-- ----------------------------
BEGIN;
INSERT INTO `mall_user` (`user_id`, `nick_name`, `login_name`, `password_md5`, `introduce_sign`, `address`, `deleted`, `locked_flag`, `create_time`) VALUES (1, '陈文伟', '18806039828', 'e10adc3949ba59abbe56e057f20f883e', '', '111', 0, 0, '2022-10-10 21:44:59');
INSERT INTO `mall_user` (`user_id`, `nick_name`, `login_name`, `password_md5`, `introduce_sign`, `address`, `deleted`, `locked_flag`, `create_time`) VALUES (10, '18350884019', '18350884019', 'e10adc3949ba59abbe56e057f20f883e', '', '福建省三明市三元区三明学院', 0, 0, '2022-11-18 20:57:26');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
