#1、insert user存储过程
DROP PROCEDURE user_insert;
DELIMITER $$
CREATE PROCEDURE user_insert(OUT u_id BIGINT,IN u_phone VARCHAR(255),IN u_email VARCHAR(255))
BEGIN 
   INSERT INTO t_user(phone,email) VALUES(u_phone,u_email);
   SET u_id=LAST_INSERT_ID();
END$$
DELIMITER ;

#2、delete user存储过程
DROP PROCEDURE user_delete;
DELIMITER $$
CREATE PROCEDURE user_delete(IN u_id BIGINT,OUT u_phone VARCHAR(255),OUT u_email VARCHAR(255))
BEGIN 
   SELECT phone,email FROM t_user WHERE id=u_id INTO u_phone,u_email;
   DELETE from t_user where id=u_id;
END$$
DELIMITER ;

#3、update user存储过程
DROP PROCEDURE user_update;
DELIMITER $$
CREATE PROCEDURE user_update(IN u_id BIGINT,INOUT u_phone VARCHAR(255),INOUT u_email VARCHAR(255))
BEGIN 
   UPDATE t_user set phone=u_phone,email=u_email where id=u_id;
   SELECT phone+"00",email+"00" FROM t_user WHERE id=u_id INTO u_phone,u_email;
END$$
DELIMITER ;

#4、select user 游标存储过程
DELIMITER $$
CREATE PROCEDURE user_select_cursor(IN u_vx_id VARCHAR(255),OUT user_cursor CURSOR)
BEGIN
  DECLARE user_cursor CURSOR FOR SELECT * FROM USER WHERE vx_id LIKE CONCAT('%',u_vx_id,'%')
END$$
DELIMITER ;

