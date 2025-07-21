CREATE TABLE `fund` (
                        `id`	VARCHAR	NOT NULL,
                        `name`	VARCHAR	NULL,
                        `rate`	DECIMAL	NULL,
                        `fund_price`	FLOAT	NULL,
                        `fund_price_movement`	FLOAT	NULL,
                        `TER`	FLOAT	NULL,
                        `category`	VARCHAR	NULL,
                        `theme`	VARCHAR	NULL,
                        `investment_warning_grade`	VARCHAR	NULL,
                        `upfront_fee`	FLOAT	NULL,
                        `management_fee`	FLOAT	NULL,
                        `minimum_cost`	INT	NULL,
                        `target`	VARCHAR	NULL,
                        `investment_type`	VARCHAR	NULL,
                        `company`	VARCHAR	NULL,
                        `score`	INT	NULL,
                        `risk`	INT	NULL,
                        `reg_link`	VARCHAR	NULL,
                        `caution`	TEXT	NULL,
                        `view_cnt`	INT	NULL,
                        `scrap_cnt`	INT	NULL,
                        `regret_cnt`	INT	NULL,
                        `algo_code`	FLOAT	NULL
);



CREATE TABLE `deposit` (
                           `id`	VARCHAR	NOT NULL,
                           `name`	VARCHAR	NULL,
                           `rate`	DECIMAL	NULL,
                           `prime_rate`	DECIMAL	NULL,
                           `join_way`	VARCHAR	NULL,
                           `end_date`	DATE	NULL,
                           `max_limit`	BIGINT	NULL,
                           `join_member`	VARCHAR	NULL,
                           `join_deny`	INT	NULL,
                           `bonus_condition`	TEXT	NULL,
                           `save_term`	INT	NULL,
                           `minimum_cost`	INT	NULL,
                           `company`	VARCHAR	NULL,
                           `score`	INT	NULL,
                           `risk`	INT	NULL,
                           `reg_link`	VARCHAR	NULL,
                           `caution`	TEXT	NULL,
                           `view_cnt`	INT	NULL,
                           `scrap_cnt`	INT	NULL,
                           `regret_cnt`	INT	NULL,
                           `algo_code`	FLOAT	NULL
);

CREATE TABLE `forex` (
                         `id`	VARCHAR	NOT NULL,
                         `name`	VARCHAR	NULL,
                         `rate`	DECIMAL	NULL,
                         `description`	VARCHAR	NULL,
                         `currency`	VARCHAR	NULL,
                         `reg_fund`	BIGINT	NULL,
                         `reg_limit_date`	DATETIME	NULL,
                         `auto_renew`	VARCHAR	NULL,
                         `extra_deposit`	BOOLEAN	NULL,
                         `rate_give_way`	VARCHAR	NULL,
                         `tax_refund`	BOOLEAN	NULL,
                         `protect`	BOOLEAN	NULL,
                         `company`	VARCHAR	NULL,
                         `score`	INT	NULL,
                         `risk`	INT	NULL,
                         `reg_link`	VARCHAR	NULL,
                         `caution`	TEXT	NULL,
                         `view_cnt`	INT	NULL,
                         `scrap_cnt`	INT	NULL,
                         `regret_cnt`	INT	NULL,
                         `algo_code`	FLOAT	NULL
);

CREATE TABLE `pension` (
                           `id`	VARCHAR	NOT NULL,
                           `name`	VARCHAR	NULL,
                           `rate`	DECIMAL	NULL,
                           `pension_kind`	VARCHAR	NULL,
                           `pension_type`	VARCHAR	NULL,
                           `min_guarantee_rate`	DECIMAL	NULL,
                           `last_year_profit_rate`	DECIMAL	NULL,
                           `end_date`	DATE	NULL,
                           `company`	VARCHAR	NULL,
                           `score`	INT	NULL,
                           `risk`	INT	NULL,
                           `reg_link`	VARCHAR	NULL,
                           `caution`	TEXT	NULL,
                           `view_cnt`	INT	NULL,
                           `scrap_cnt`	INT	NULL,
                           `regret_cnt`	INT	NULL,
                           `algo_code`	FLOAT	NULL
);




CREATE TABLE `savings` (
                           `id`	VARCHAR	NOT NULL,
                           `name`	VARCHAR	NULL,
                           `rate`	DECIMAL	NULL,
                           `prime_rate`	DECIMAL	NULL,
                           `join_way`	VARCHAR	NULL,
                           `end_date`	DATE	NULL,
                           `max_limit`	BIGINT	NULL,
                           `join_member`	VARCHAR	NULL,
                           `join_deny`	INT	NULL,
                           `bonus_condition`	TEXT	NULL,
                           `save_term`	BIGINT	NULL,
                           `rate_type`	VARCHAR	NULL,
                           `save_type`	VARCHAR	NULL,
                           `company`	VARCHAR	NULL,
                           `score`	INT	NULL,
                           `risk`	INT	NULL,
                           `reg_link`	VARCHAR	NULL,
                           `caution`	TEXT	NULL,
                           `view_cnt`	INT	NULL,
                           `scrap_cnt`	INT	NULL,
                           `regret_cnt`	INT	NULL,
                           `algo_code`	FLOAT	NULL
);


CREATE TABLE `personalized_description` (
                                            `id` INT NOT NULL AUTO_INCREMENT,
                                            `algo_code` FLOAT NOT NULL,
                                            `match_score` ENUM('상', '중', '하') NULL,
                                            `advantage` VARCHAR(255) NULL,
                                            `disadvantage` VARCHAR(255) NULL,
                                            `product_id` VARCHAR(10) NOT NULL,
                                            PRIMARY KEY (`id`)
);


-- user

CREATE TABLE `users` (
                         `id`	INT	NOT NULL,
                         `email`	VARCHAR	NULL,
                         `name`	VARCHAR	NULL,
                         `password`	VARCHAR	NULL,
                         `phone`	VARCHAR	NULL,
                         `birth`	DATE	NULL,
                         `risk`	INT	NULL,
                         `user_klg`	INT	NULL,
                         `algo_code`	FLOAT	NULL,
                         `reg_date`	DATE	NULL,
                         `klg_renew_date`	DATE	NULL,
                         `risk_renew_date`	DATE	NULL,
                         `docu_renew_date`	DATE	NULL
);

CREATE TABLE `mydata` (
                          `id`	INT	NOT NULL,
                          `collect_date`	DATE	NULL,
                          `asset`	INT	NULL,
                          `user_id`	INT	NOT NULL
);

CREATE TABLE `myproduct` (
                             `id` INT NOT NULL AUTO_INCREMENT,
                             `mydata_id` INT NOT NULL,
                             `amount` INT,
                             `reg_date` DATE,
                             `end_date` DATE,
                             `product_id` VARCHAR(10) NOT NULL,
                             PRIMARY KEY (`id`),
                             FOREIGN KEY (`mydata_id`) REFERENCES `mydata` (`id`)
);
-- user 끝


-- action, scrap
CREATE TABLE `action` (
                          `user_id` INT NOT NULL,
                          `product_id` VARCHAR(10) NOT NULL,
                          `action` ENUM('가입', '관심', '조회'),
                          PRIMARY KEY (`user_id`, `product_id`),
                          FOREIGN KEY (`user_id`) REFERENCES `users`(`id`)
);

CREATE TABLE `scrap` (
                         `user_id` INT NOT NULL,
                         `product_id` VARCHAR(10) NOT NULL,
                         PRIMARY KEY (`user_id`, `product_id`),
                         FOREIGN KEY (`user_id`) REFERENCES `users`(`id`)
);
-- action, scrap 끝

-- 트래킹
CREATE TABLE `fund_track` (
                              `id` INT NOT NULL AUTO_INCREMENT,
                              `product_id` VARCHAR(10) NOT NULL,
                              `tracking_date` DATE,
                              `price` INT,
                              PRIMARY KEY (`id`),
                              FOREIGN KEY (`product_id`) REFERENCES `fund`(`id`)
);

CREATE TABLE `forex_track` (
                               `id` INT NOT NULL AUTO_INCREMENT,
                               `product_id` VARCHAR(10) NOT NULL,
                               `tracking_date` DATE,
                               `price` INT,
                               PRIMARY KEY (`id`),
                               FOREIGN KEY (`product_id`) REFERENCES `forex`(`id`)
);

CREATE TABLE `aggressive_pension_track` (
                                            `id` INT NOT NULL AUTO_INCREMENT,
                                            `product_id` VARCHAR(10) NOT NULL,
                                            `tracking_date` DATE,
                                            `price` INT,
                                            PRIMARY KEY (`id`),
                                            FOREIGN KEY (`product_id`) REFERENCES `pension`(`id`)
);

ALTER TABLE `fund_track` ADD CONSTRAINT `PK_FUND_TRACK` PRIMARY KEY (
                                                                     `id`
    );

ALTER TABLE `forex_track` ADD CONSTRAINT `PK_FOREX_TRACK` PRIMARY KEY (
                                                                       `id`
    );

ALTER TABLE `aggressive_pension_track` ADD CONSTRAINT `PK_AGGRESSIVE_PENSION_TRACK` PRIMARY KEY (
                                                                                                 `id`
    );

-- 트래킹 끝

CREATE TABLE `rec_item` (
                            `rec_id` INT NOT NULL AUTO_INCREMENT,
                            `user_id` INT NOT NULL,
                            `rec_date` DATE,
                            `product1_id` VARCHAR(10) NOT NULL,
                            `product1_status` ENUM('가입', '관심', '회고'),
                            `product1_survey` VARCHAR(255),
                            `product1_regret_score` INT,
                            `product1_miss_amount` INT,

                            `product2_id` VARCHAR(10) NOT NULL,
                            `product2_status` ENUM('가입', '관심', '회고'),
                            `product2_survey` VARCHAR(255),
                            `product2_regret_score` INT,
                            `product2_miss_amount` INT,

                            `product3_id` VARCHAR(10) NOT NULL,
                            `product3_status` ENUM('가입', '관심', '회고'),
                            `product3_survey` VARCHAR(255),
                            `product3_regret_score` INT,
                            `product3_miss_amount` INT,

                            `product4_id` VARCHAR(10) NOT NULL,
                            `product4_status` ENUM('가입', '관심', '회고'),
                            `product4_survey` VARCHAR(255),
                            `product4_regret_score` INT,
                            `product4_miss_amount` INT,

                            `product5_id` VARCHAR(10) NOT NULL,
                            `product5_status` ENUM('가입', '관심', '회고'),
                            `product5_survey` VARCHAR(255),
                            `product5_regret_score` INT,
                            `product5_miss_amount` INT,

                            `anlz_date` DATE,

                            PRIMARY KEY (`rec_id`, `user_id`),
                            FOREIGN KEY (`user_id`) REFERENCES `users`(`id`)
);




