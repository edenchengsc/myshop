
DROP TABLE IF EXISTS `activity`;
CREATE TABLE `activity`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'activiy ID',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'activity name',
  `commodity_id` bigint NOT NULL,
  `original_price` decimal(10, 2) NOT NULL COMMENT 'original price',
  `onsale_price` decimal(10, 2) NOT NULL COMMENT 'big sale price',
  `activity_status` int NOT NULL DEFAULT 0 COMMENT 'status: 0: unlisted, 1: online',
  `start_time` datetime(0) NULL DEFAULT NULL COMMENT 'activity start time',
  `end_time` datetime(0) NULL DEFAULT NULL COMMENT 'activity end time',
  `total_stock` bigint UNSIGNED NOT NULL COMMENT 'total stock',
  `available_stock` int NOT NULL COMMENT 'available stock',
  `lock_stock` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT 'lock stock',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `id`(`id`, `name`, `commodity_id`) USING BTREE,
  INDEX `name`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 27 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;
