/*Table structure for table `blog_sys_user_role` */

DROP TABLE IF EXISTS `blog_sys_user_role`;

CREATE TABLE `blog_sys_user_role` (
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户和角色关联表';

/*Data for the table `blog_sys_user_role` */

insert  into `blog_sys_user_role`(`user_id`,`role_id`) values (1,1),(2,2),(5,2),(6,12);
