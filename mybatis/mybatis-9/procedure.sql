#1、创建表
CREATE TABLE IF NOT EXISTS USER
(
  id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  phone VARCHAR(255) CHARSET utf8 NULL,
  email VARCHAR(255) CHARSET utf8 NULL,
  vx_id VARCHAR(255) CHARSET utf8 NULL,
  vx_nickname VARCHAR(255) CHARSET utf8 NULL,
  PASSWORD VARCHAR(255) CHARSET utf8 NULL,
  mark VARCHAR(255) CHARSET utf8 NULL
);

#2、创建存储过程
DROP PROCEDURE myproc;
DELIMITER $$
CREATE PROCEDURE myproc(IN p_id INT)
BEGIN 
   SELECT id,phone,email FROM t_user WHERE id=p_id;
END$$
DELIMITER ;

#3、查看存储过程创建结果
show procedure status;

#4、导出存储过程
show create procedure myproc;

#5、MySQL客户端调用执行存储过程
set @select_type='0';
call myproc(@select_type);