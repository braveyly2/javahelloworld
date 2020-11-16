CREATE TABLE IF NOT EXISTS t_employee
(
  id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  user_name VARCHAR(100) CHARSET utf8 NULL,
  gender INT(11),
  age INT(11) DEFAULT NULL
);

CREATE TABLE IF NOT EXISTS t_female_health_report
(
  id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  employee_id BIGINT,
  liver VARCHAR(64) NOT NULL,
  heart VARCHAR(64) NOT NULL,
  uterus VARCHAR(64) NOT NULL
);

CREATE TABLE IF NOT EXISTS t_male_health_report
(
  id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  employee_id BIGINT,
  liver VARCHAR(64) NOT NULL,
  heart VARCHAR(64) NOT NULL,
  prostate VARCHAR(64) NOT NULL
);