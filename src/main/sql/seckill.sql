-- 存储过程
DELIMITER $$ #分号转换为

CREATE PROCEDURE `seckill`.`execute_seckill`
  (IN v_seckill_id bitint ,IN v_phone bigint,IN v_kill_time TIMESTAMP ,OUT r_result int);
  BEGIN ;
    DECLARE insert_count INT DEFAULT 0;
    START TRANSACTION ;
    INSERT ignore INTO success_killed
      (seckill_id,user_phone,create_time)
      VALUES (v_seckill_id,v_phone,v_kill_time);
    SELECT ROW_COUNT() INTO insert_count;
    IF (insert_count = 0)THEN
      ROLLBACK ;
      SET r_result = -1;
    ELSEIF(insert_count < 0) THEN
      ROLLBACK ;
      SET r_result = -2;
    ELSE
      UPDATE seckill
        SET number = number - 1
        WHERE seckill_id = v_seckill_id
          and end_time > v_kill_time
          and start_time < v_kill_time
          and number > 0;
      SELECT ROW_COUNT() INTO insert_count;
      IF(insert_count = 0) THEN
        ROLLBACK ;
        set r_result = 0;
      ELSEIF(insert_count < 0) THEN
        ROLLBACK ;
        SET r_result = 2;
      ELSE
        COMMIT ;
        set r_result = 1;
      END IF;
    END IF;
  END;
$$
DELIMITER ;
SET @r_result = -3;
CALL execute_seckill(1003,12123456342,now(),@r_result);

SELECT @r_result;


