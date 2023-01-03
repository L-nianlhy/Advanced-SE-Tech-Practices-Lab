DROP DATABASE IF EXISTS seciii;
CREATE DATABASE seciii DEFAULT CHARACTER SET utf8;
USE seciii;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

--用户表
--用户id 用户名 密码 角色{employee employer admin} 用户签名 封禁标志{0：封禁中 1：未封禁}
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
                              `id` int(11) NOT NULL AUTO_INCREMENT,
                              `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
                              `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
                              `user_role` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
                              `user_signature` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci,
                              `login_flag` int(11) NOT NULL,
                              PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

--项目表
--项目id 发包方id 项目名称 项目简介 子任务数
--项目是否结束{0：已结束 1：未结束}
DROP TABLE IF EXISTS `project`;
CREATE TABLE `project`  (
                         `id` int(11) NOT NULL AUTO_INCREMENT,
                         `employer_id` int(11) NOT NULL,
                         `project_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
                         `introduction` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
                         `subtasks_num` int(11) NULL DEFAULT NULL,
                         `finished` int(11) NULL DEFAULT NULL,
                         PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

--测试任务表
--任务id 发包方id 任务名称 任务简介 需要工人数 测试设备{Linux设备 android设备 鸿蒙设备 mac设备 windows设备}
--开始时间 结束时间 任务类型{功能测试 性能测试 其他} 文件路径：属于该任务的测试文件所在文件夹
--已报名人数 任务是否结束{0：已结束 1：未结束}
DROP TABLE IF EXISTS `task`;
CREATE TABLE `task`  (
                         `id` int(11) NOT NULL AUTO_INCREMENT,
                         `project_id` int(11) NOT NULL,
                         `employer_id` int(11) NOT NULL,
                         `task_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
                         `introduction` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
                         `need_workers` int(11) NOT NULL,
                         `test_device` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
                         `start_time` datetime(0) NULL DEFAULT NULL,
                         `end_time` datetime(0) NULL DEFAULT NULL,
                         `task_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
                         `file_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
                         `signed_num` int(11) NULL DEFAULT NULL,
                         `available` int(11) NULL DEFAULT NULL,
                         PRIMARY KEY (`id`) USING BTREE,
                         INDEX `fk_user_task`(`employer_id`) USING BTREE,
                         CONSTRAINT `fk_user_task` FOREIGN KEY (`employer_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

--日志表 记录登录信息
--日志id 用户id 用户某次登录时间
DROP TABLE IF EXISTS `diary`;
CREATE TABLE `diary`  (
                         `id` int(11) NOT NULL AUTO_INCREMENT,
                         `uid` int(11) NULL DEFAULT NULL,
                         `login_time` datetime(0) NULL DEFAULT NULL,
                         PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

--测试文件表
--文件id 任务id 文件名 文件类型{testFile} 文件路径：某个测试文件的路径
DROP TABLE IF EXISTS `file`;
CREATE TABLE `file`  (
                         `id` int(11) NOT NULL AUTO_INCREMENT,
                         `task_id` int(11) NOT NULL,
                         `file_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
                         `file_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
                         `file_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
                         PRIMARY KEY (`id`) USING BTREE,
                         INDEX `fk_task_file`(`task_id`) USING BTREE,
                         CONSTRAINT `fk_task_file` FOREIGN KEY (`task_id`) REFERENCES `task` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

--测试报告表 某个用户对某个任务只能提交一个有效报告，但可以有多个无效报告
--报告id 提交报告工人id 任务id 报告名 缺陷描述 复现步骤 设备类型{Android Windows IOS MacOS}
--截图路径：属于这个报告的截图所在文件夹 提交时间 平均分数 是否为有效报告{0：无效报告 1：有效报告}
DROP TABLE IF EXISTS `report`;
CREATE TABLE `report`  (
                         `id` int(11) NOT NULL AUTO_INCREMENT,
                         `employee_id` int(11) NOT NULL,
                         `task_id` int(11) NOT NULL,
                         `report_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
                         `defect_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
                         `recurrence_steps` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
                         `device_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
                         `screenshots_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
                         `submit_time` datetime(0) NULL DEFAULT NULL,
                         `avg_score` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
                         `finish` int(11) NOT NULL,
                         PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

--测试截图表
--截图id 报告id 截图名 截图路径；该截图的路径
DROP TABLE IF EXISTS `screenshots`;
CREATE TABLE `screenshots`  (
                         `id` int(11) NOT NULL AUTO_INCREMENT,
                         `report_id` int(11) NOT NULL,
                         `screenshots_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
                         `screenshots_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
                         PRIMARY KEY (`id`) USING BTREE,
                         INDEX `fk_report_screenshots`(`report_id`) USING BTREE,
                         CONSTRAINT `fk_report_screenshots` FOREIGN KEY (`report_id`) REFERENCES `report` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

--报名表 记录报名信息
--报名id 任务id 工人id 这个工人对本次任务是否提交过有效报告{0：未提交 1：已提交}
DROP TABLE IF EXISTS `application`;
CREATE TABLE `application`  (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `task_id` int(11) NOT NULL,
    `employee_id` int(11) NOT NULL,
    `submit` int(11) NOT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

--评论表
--评论id 用户id：名字起错了，可以是发包方和工人 报告id 评论内容 评论时间
DROP TABLE IF EXISTS `discription`;
CREATE TABLE `discription`  (
                         `id` int(11) NOT NULL AUTO_INCREMENT,
                         `employee_id` int(11) NOT NULL,
                         `report_id` int(11) NOT NULL,
                         `discription` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
                         `time`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
                         PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

--老版本的评论表 目前没有使用
DROP TABLE IF EXISTS `evaluation`;
CREATE TABLE `evaluation`  (
                                `id` int(11) NOT NULL AUTO_INCREMENT,
                                `employee_id` int(11) NOT NULL,
                                `report_id` int(11) NOT NULL,
                                `score` int(11) NOT NULL,
                                `discription` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
                                PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

--打分表
--打分id 工人id 报告id 分数
DROP TABLE IF EXISTS `score`;
CREATE TABLE `score`  (
                               `id` int(11) NOT NULL AUTO_INCREMENT,
                               `employee_id` int(11) NOT NULL,
                               `report_id` int(11) NOT NULL,
                               `score` int(11) NOT NULL,
                               PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

--工人表
--工人id 专业能力 任务偏好 设备类型 活跃度 提交报告平均分 完成率（他提交过的报告/他的所有报告） 补充报告的数量 评估能力：体现他打分的质量
--报告相似度 总得分
CREATE TABLE `employee`(
        `employee_id`  int(11) NOT NULL,
        `professional_ability` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
        `task_preference` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
        `test_device` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
        `activity_level` int(11) NULL DEFAULT 0,
        `avg_score` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
        `finish_rate` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
        `supp_num` int(11) NULL DEFAULT 0,
        `review_ability` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
        `report_similarity` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
        `total_score` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
        PRIMARY KEY (`employee_id`) USING BTREE
)ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

--管理员表 只能有一个管理员
--管理员id 推荐策略
CREATE TABLE `admin`(
        `admin_id`  int(11) NOT NULL,
        `rank_rule` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
        PRIMARY KEY (`admin_id`) USING BTREE
)ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

--补充报告表
--补充报告id 工人id 报告id 补充报告名  缺陷描述 复现步骤 设备类型 截图路径
DROP TABLE IF EXISTS `supplement`;
CREATE TABLE `supplement`  (
                         `id` int(11) NOT NULL AUTO_INCREMENT,
                         `employee_id` int(11) NOT NULL,
                         `report_id` int(11) NOT NULL,
                         `supplement_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
                         `defect_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
                         `recurrence_steps` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
                         `device_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
                         `screenshots_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
                         PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

--排名表 从高到低排
--排名id 工人id 工人名 工人分数
DROP TABLE IF EXISTS `rankl`;
CREATE TABLE `rankl`  (
                               `id` int(11) NOT NULL AUTO_INCREMENT,
                               `employee_id` int(11) NOT NULL,
                               `employee_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
                               `score` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
                               PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;



INSERT INTO `user` VALUES (1,'xyf','123456','admin',null,1);
INSERT INTO `admin` VALUES (1,'default');
INSERT INTO `user` VALUES (2,'yd','123456','employer',null,1);
INSERT INTO `user` VALUES (3,'yh','123456','employee',null,1);
INSERT INTO `employee` values (3,'团队测试,业务知识,项目管理,测试能力' ,'性能测试','Linux设备',0,'0','0',0,'0','0','0');
INSERT INTO `user` VALUES (4,'yh1','123456','employee',null,1);
INSERT INTO `employee` values (4,'团队测试,业务知识,项目管理,测试能力' ,'性能测试','Linux设备',0,'0','0',0,'0','0','0');
INSERT INTO `user` VALUES (5,'yh2','123456','employee',null,1);
INSERT INTO `employee` values (5,'团队测试,业务知识,项目管理,测试能力' ,'性能测试','Linux设备',0,'0','0',0,'0','0','0');






SET FOREIGN_KEY_CHECKS = 1;