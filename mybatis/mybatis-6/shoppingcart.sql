DROP TABLE SHOPPINGCART;
CREATE TABLE IF NOT EXISTS SHOPPINGCART
(
  `product_id` BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  `product_name` VARCHAR(50) DEFAULT NULL,
  `number` INT(11) DEFAULT NULL, 
  `price` DOUBLE DEFAULT NULL, 
  `total_amount` DOUBLE DEFAULT NULL
);