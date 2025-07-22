drop database if exists my_db;
create database my_db;
use my_db;
CREATE TABLE `fund` (
                        `id`	VARCHAR(20)	NOT NULL,
                        `name`	VARCHAR(255)	NULL,
                        `rate`	FLOAT   NULL,
                        `fund_price`	FLOAT	NULL,
                        `fund_price_movement`	FLOAT	NULL,
                        `TER`	FLOAT	NULL,
                        `category`	VARCHAR(255)	NULL,
                        `theme`	VARCHAR(255)	NULL,
                        `investment_warning_grade`	VARCHAR(255)	NULL,
                        `upfront_fee`	FLOAT	NULL,
                        `management_fee`	FLOAT	NULL,
                        `minimum_cost`	INT	NULL,
                        `target`	VARCHAR(255)	NULL,
                        `investment_type`	VARCHAR(255)	NULL,
                        `company`	VARCHAR(255)	NULL,
                        `score`	INT	NULL,
                        `risk`	INT	NULL,
                        `reg_link`	VARCHAR(255)	NULL,
                        `caution`	TEXT	NULL,
                        `view_cnt`	INT	NULL,
                        `scrap_cnt`	INT	NULL,
                        `regret_cnt`	INT	NULL,
                        `algo_code`	FLOAT	NULL,
                        PRIMARY KEY (`id`)
);



CREATE TABLE `deposit` (
                           `id`	VARCHAR(20)	NOT NULL,
                           `name`	VARCHAR(255)	NULL,
                           `rate`	FLOAT	NULL,
                           `prime_rate`	FLOAT	NULL,
                           `join_way`	VARCHAR(255)	NULL,
                           `end_date`	DATE	NULL,
                           `max_limit`	BIGINT	NULL,
                           `join_member`	VARCHAR(255)	NULL,
                           `join_deny`	INT	NULL,
                           `bonus_condition`	TEXT	NULL,
                           `save_term`	INT	NULL,
                           `minimum_cost`	VARCHAR(255)	NULL,
                           `company`	VARCHAR(255)	NULL,
                           `score`	INT	NULL,
                           `risk`	INT	NULL,
                           `reg_link`	VARCHAR(255)	NULL,
                           `caution`	TEXT	NULL,
                           `view_cnt`	INT	NULL,
                           `scrap_cnt`	INT	NULL,
                           `regret_cnt`	INT	NULL,
                           `algo_code`	FLOAT	NULL,
                           PRIMARY KEY (`id`)
);

CREATE TABLE `forex` (
                         `id`	VARCHAR(20)	NOT NULL,
                         `name`	VARCHAR(255)	NULL,
                         `rate`	FLOAT	NULL,
                         `description`	VARCHAR(255)	NULL,
                         `currency`	VARCHAR(255)	NULL,
                         `reg_fund`	BIGINT	NULL,
                         `reg_limit_date`	DATETIME	NULL,
                         `auto_renew`	VARCHAR(255)	NULL,
                         `extra_deposit`	BOOLEAN	NULL,
                         `rate_give_way`	VARCHAR(255)	NULL,
                         `tax_refund`	BOOLEAN	NULL,
                         `protect`	BOOLEAN	NULL,
                         `company`	VARCHAR(255)	NULL,
                         `score`	INT	NULL,
                         `risk`	INT	NULL,
                         `reg_link`	VARCHAR(255)	NULL,
                         `caution`	TEXT	NULL,
                         `view_cnt`	INT	NULL,
                         `scrap_cnt`	INT	NULL,
                         `regret_cnt`	INT	NULL,
                         `algo_code`	FLOAT	NULL,
                         PRIMARY KEY (`id`)
);

CREATE TABLE `pension` (
                           `id`	VARCHAR(255)	NOT NULL,
                           `name`	VARCHAR(255)	NULL,
                           `rate`	FLOAT	NULL,
                           `pension_kind`	VARCHAR(255)	NULL,
                           `pension_type`	VARCHAR(255)	NULL,
                           `min_guarantee_rate`	FLOAT	NULL,
                           `last_year_profit_rate`	FLOAT	NULL,
                           `end_date`	DATE	NULL,
                           `company`	VARCHAR(255)	NULL,
                           `score`	INT	NULL,
                           `risk`	INT	NULL,
                           `reg_link`	VARCHAR(255)	NULL,
                           `caution`	TEXT	NULL,
                           `view_cnt`	INT	NULL,
                           `scrap_cnt`	INT	NULL,
                           `regret_cnt`	INT	NULL,
                           `algo_code`	FLOAT	NULL,
                           PRIMARY KEY (`id`)
);




CREATE TABLE `savings` (
                           `id`	VARCHAR(20)	NOT NULL,
                           `name`	VARCHAR(255)	NULL,
                           `rate`	FLOAT	NULL,
                           `prime_rate`	FLOAT	NULL,
                           `join_way`	VARCHAR(255)	NULL,
                           `end_date`	DATE	NULL,
                           `max_limit`	BIGINT	NULL,
                           `join_member`	VARCHAR(255)	NULL,
                           `join_deny`	INT	NULL,
                           `bonus_condition`	TEXT	NULL,
                           `save_term`	BIGINT	NULL,
                           `rate_type`	VARCHAR(255)	NULL,
                           `save_type`	VARCHAR(255)	NULL,
                           `company`	VARCHAR(255)	NULL,
                           `score`	INT	NULL,
                           `risk`	INT	NULL,
                           `reg_link`	VARCHAR(255)	NULL,
                           `caution`	TEXT	NULL,
                           `view_cnt`	INT	NULL,
                           `scrap_cnt`	INT	NULL,
                           `regret_cnt`	INT	NULL,
                           `algo_code`	FLOAT	NULL,
                           PRIMARY KEY (`id`)
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
                         `email`	VARCHAR(255)	NULL,
                         `name`	VARCHAR(255)	NULL,
                         `password`	VARCHAR(255)	NULL,
                         `phone`	VARCHAR(255)	NULL,
                         `birth`	DATE	NULL,
                         `risk`	INT	NULL,
                         `user_klg`	INT	NULL,
                         `algo_code`	FLOAT	NULL,
                         `reg_date`	DATE	NULL,
                         `klg_renew_date`	DATE	NULL,
                         `risk_renew_date`	DATE	NULL,
                         `docu_renew_date`	DATE	NULL,
                         PRIMARY KEY (`id`)
);

CREATE TABLE `mydata` (
                          `id`	INT	NOT NULL,
                          `collect_date`	DATE	NULL,
                          `asset`	INT	NULL,
                          `user_id`	INT	NOT NULL,
                          PRIMARY KEY (`id`)
);

CREATE TABLE `myproduct` (
                             `id` INT NOT NULL AUTO_INCREMENT,
                             `mydata_id` INT NOT NULL,
                             `amount` INT,
                             `reg_date` DATE,
                             `end_date` DATE,
                             `product_id` VARCHAR(20) NOT NULL,
                             PRIMARY KEY (`id`),
                             FOREIGN KEY (`mydata_id`) REFERENCES `mydata` (`id`)
);
-- user 끝


-- action, scrap
CREATE TABLE `action` (
                          `user_id` INT NOT NULL,
                          `product_id` VARCHAR(20) NOT NULL,
                          `action` ENUM('가입', '관심', '조회'),
                          PRIMARY KEY (`user_id`, `product_id`),
                          FOREIGN KEY (`user_id`) REFERENCES `users`(`id`)
);

CREATE TABLE `scrap` (
                         `user_id` INT NOT NULL,
                         `product_id` VARCHAR(20) NOT NULL,
                         PRIMARY KEY (`user_id`, `product_id`),
                         FOREIGN KEY (`user_id`) REFERENCES `users`(`id`)
);
-- action, scrap 끝

-- 트래킹
CREATE TABLE `fund_track` (
                              `id` INT NOT NULL AUTO_INCREMENT,
                              `product_id` VARCHAR(20) NOT NULL,
                              `tracking_date` DATE,
                              `price` FLOAT,
                              PRIMARY KEY (`id`),
                              FOREIGN KEY (`product_id`) REFERENCES `fund`(`id`)
);

CREATE TABLE `forex_track` (
                               `id` INT NOT NULL AUTO_INCREMENT,
                               `product_id` VARCHAR(20) NOT NULL,
                               `tracking_date` DATE,
                               `price` FLOAT,
                               PRIMARY KEY (`id`),
                               FOREIGN KEY (`product_id`) REFERENCES `forex`(`id`)
);

CREATE TABLE `aggressive_pension_track` (
                                            `id` INT NOT NULL AUTO_INCREMENT,
                                            `product_id` VARCHAR(20) NOT NULL,
                                            `tracking_date` DATE,
                                            `price` FLOAT,
                                            PRIMARY KEY (`id`),
                                            FOREIGN KEY (`product_id`) REFERENCES `pension`(`id`)
);


-- 트래킹 끝

CREATE TABLE `rec_item` (
                            `rec_id` INT NOT NULL AUTO_INCREMENT,
                            `user_id` INT NOT NULL,
                            `rec_date` DATE,
                            `product1_id` VARCHAR(20) NOT NULL,
                            `product1_status` ENUM('가입', '관심', '회고'),
                            `product1_survey` VARCHAR(255),
                            `product1_regret_score` INT,
                            `product1_miss_amount` INT,

                            `product2_id` VARCHAR(20) NOT NULL,
                            `product2_status` ENUM('가입', '관심', '회고'),
                            `product2_survey` VARCHAR(255),
                            `product2_regret_score` INT,
                            `product2_miss_amount` INT,

                            `product3_id` VARCHAR(20) NOT NULL,
                            `product3_status` ENUM('가입', '관심', '회고'),
                            `product3_survey` VARCHAR(255),
                            `product3_regret_score` INT,
                            `product3_miss_amount` INT,

                            `product4_id` VARCHAR(20) NOT NULL,
                            `product4_status` ENUM('가입', '관심', '회고'),
                            `product4_survey` VARCHAR(255),
                            `product4_regret_score` INT,
                            `product4_miss_amount` INT,

                            `product5_id` VARCHAR(20) NOT NULL,
                            `product5_status` ENUM('가입', '관심', '회고'),
                            `product5_survey` VARCHAR(255),
                            `product5_regret_score` INT,
                            `product5_miss_amount` INT,

                            `anlz_date` DATE,

                            PRIMARY KEY (`rec_id`, `user_id`),
                            FOREIGN KEY (`user_id`) REFERENCES `users`(`id`)
);


INSERT INTO fund (id, name, rate, fund_price, fund_price_movement, TER, category, theme, investment_warning_grade, upfront_fee, management_fee, minimum_cost, target, investment_type, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code) VALUES ('F0001', '교보 고배당 펀드', -2.74, 1170.38, -21.52, 0.32, '국내주식형', '미래기술테마', '5등급', 0.96, 0.39, 50000, '고액 자산가', '적립', '신한자산운용', NULL, NULL, 'https://www.한화.com/fund/5220', '※ 투자 전 투자설명서 및 약관을 반드시 확인하시기 바랍니다.', 8009, 489, 154, NULL);
INSERT INTO fund (id, name, rate, fund_price, fund_price_movement, TER, category, theme, investment_warning_grade, upfront_fee, management_fee, minimum_cost, target, investment_type, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code) VALUES ('F0002', 'KB 그로스 인덱스 증권자투자신탁', 8.67, 1104.18, 38.71, 1.66, '해외채권형', 'ETF(국내채권)', '5등급', 0.22, 1.05, 10000, '개인 투자자', '거치', '한화자산운용', NULL, NULL, 'https://www.한화.com/fund/9615', '※ 펀드 수익률은 과거의 실적이며, 미래의 수익을 보장하지 않습니다.', 5227, 35, 289, NULL);
INSERT INTO fund (id, name, rate, fund_price, fund_price_movement, TER, category, theme, investment_warning_grade, upfront_fee, management_fee, minimum_cost, target, investment_type, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code) VALUES ('F0003', '한화 테크 펀드', 13.73, 1148.28, -20.4, 0.97, '해외주식형', 'ETF(해외주식)', '3등급', 0.89, 1.14, 10000, '개인 투자자', '거치', '미래에셋자산운용', NULL, NULL, 'https://www.교보.com/fund/8749', '※ 투자 전 투자설명서 및 약관을 반드시 확인하시기 바랍니다.', 19157, 185, 412, NULL);
INSERT INTO fund (id, name, rate, fund_price, fund_price_movement, TER, category, theme, investment_warning_grade, upfront_fee, management_fee, minimum_cost, target, investment_type, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code) VALUES ('F0004', '신한 ESG 증권자투자신탁', 6.0, 1033.84, 12.34, 1.65, '국내대체', 'ETF(해외주식)', '2등급', 0.4, 0.5, 50000, '고액 자산가', '거치', 'KB자산운용', NULL, NULL, 'https://www.미래에셋.com/fund/4146', '※ 펀드 수익률은 과거의 실적이며, 미래의 수익을 보장하지 않습니다.', 17612, 393, 339, NULL);
INSERT INTO fund (id, name, rate, fund_price, fund_price_movement, TER, category, theme, investment_warning_grade, upfront_fee, management_fee, minimum_cost, target, investment_type, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code) VALUES ('F0005', '한화 ESG 증권자투자신탁', 9.59, 1129.31, -17.7, 0.5, '해외채권형', 'ETF(해외주식)', '3등급', 0.16, 0.39, 100000, '고액 자산가', '거치', 'KB자산운용', NULL, NULL, 'https://www.삼성.com/fund/2154', '※ 투자 전 투자설명서 및 약관을 반드시 확인하시기 바랍니다.', 16185, 334, 330, NULL);
INSERT INTO fund (id, name, rate, fund_price, fund_price_movement, TER, category, theme, investment_warning_grade, upfront_fee, management_fee, minimum_cost, target, investment_type, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code) VALUES ('F0006', 'KB 글로벌 혁신 펀드', 14.15, 908.17, 48.61, 1.17, '해외주식형', '배당주펀드', '1등급', 0.36, 0.55, 100000, '고액 자산가', '거치', 'KB자산운용', NULL, NULL, 'https://www.교보.com/fund/3853', '※ 펀드 수익률은 과거의 실적이며, 미래의 수익을 보장하지 않습니다.', 12161, 743, 56, NULL);
INSERT INTO fund (id, name, rate, fund_price, fund_price_movement, TER, category, theme, investment_warning_grade, upfront_fee, management_fee, minimum_cost, target, investment_type, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code) VALUES ('F0007', '신한 테크 증권자투자신탁', 10.92, 1024.14, 49.76, 1.84, '국내대체', '미래기술테마', '6등급', 0.92, 0.7, 50000, '고액 자산가', '거치', 'KB자산운용', NULL, NULL, 'https://www.삼성.com/fund/3047', '※ 펀드 수익률은 과거의 실적이며, 미래의 수익을 보장하지 않습니다.', 16682, 652, 56, NULL);
INSERT INTO fund (id, name, rate, fund_price, fund_price_movement, TER, category, theme, investment_warning_grade, upfront_fee, management_fee, minimum_cost, target, investment_type, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code) VALUES ('F0008', 'KB 성장주 증권자투자신탁', -1.8, 981.53, 49.6, 1.26, '해외주식형', '미래기술테마', '1등급', 0.27, 1.15, 50000, '고액 자산가', '적립', '삼성자산운용', NULL, NULL, 'https://www.nh-amundi.com/fund/5491', '※ 펀드 수익률은 과거의 실적이며, 미래의 수익을 보장하지 않습니다.', 15888, 523, 313, NULL);
INSERT INTO fund (id, name, rate, fund_price, fund_price_movement, TER, category, theme, investment_warning_grade, upfront_fee, management_fee, minimum_cost, target, investment_type, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code) VALUES ('F0009', 'NH-Amundi ESG 혼합형펀드', 8.99, 988.01, -6.4, 0.5, 'MMF', 'ETF(해외주식)', '4등급', 0.0, 0.8, 50000, '개인 투자자', '거치', '교보자산운용', NULL, NULL, 'https://www.한화.com/fund/2750', '※ 펀드 수익률은 과거의 실적이며, 미래의 수익을 보장하지 않습니다.', 19365, 32, 269, NULL);
INSERT INTO fund (id, name, rate, fund_price, fund_price_movement, TER, category, theme, investment_warning_grade, upfront_fee, management_fee, minimum_cost, target, investment_type, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code) VALUES ('F0010', 'NH-Amundi 코리아 대표 증권자투자신탁', -1.14, 1103.3, 2.5, 0.95, '해외주식형', '배당주펀드', '3등급', 0.92, 1.2, 50000, '개인 투자자', '거치', '미래에셋자산운용', NULL, NULL, 'https://www.kb.com/fund/9900', '※ 펀드 수익률은 과거의 실적이며, 미래의 수익을 보장하지 않습니다.', 7138, 443, 47, NULL);
INSERT INTO fund (id, name, rate, fund_price, fund_price_movement, TER, category, theme, investment_warning_grade, upfront_fee, management_fee, minimum_cost, target, investment_type, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code) VALUES ('F0011', '교보 테크 증권자투자신탁', 6.0, 1017.95, -26.27, 0.77, '해외채권형', 'ETF(해외주식)', '2등급', 0.15, 0.42, 50000, '개인 투자자', '적립', 'NH-Amundi자산운용', NULL, NULL, 'https://www.kb.com/fund/3365', '※ 펀드 수익률은 과거의 실적이며, 미래의 수익을 보장하지 않습니다.', 12709, 735, 242, NULL);
INSERT INTO fund (id, name, rate, fund_price, fund_price_movement, TER, category, theme, investment_warning_grade, upfront_fee, management_fee, minimum_cost, target, investment_type, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code) VALUES ('F0012', '신한 고배당 혼합형펀드', -2.27, 1055.67, 39.92, 1.67, '해외채권형', '미래기술테마', '6등급', 0.52, 0.74, 50000, '고액 자산가', '적립', 'KB자산운용', NULL, NULL, 'https://www.미래에셋.com/fund/8574', '※ 펀드 수익률은 과거의 실적이며, 미래의 수익을 보장하지 않습니다.', 10695, 232, 276, NULL);
INSERT INTO fund (id, name, rate, fund_price, fund_price_movement, TER, category, theme, investment_warning_grade, upfront_fee, management_fee, minimum_cost, target, investment_type, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code) VALUES ('F0013', '삼성 고배당 펀드', 8.35, 1053.63, 42.76, 1.37, 'MMF', 'ETF(국내채권)', '3등급', 0.46, 0.88, 10000, '고액 자산가', '거치', '신한자산운용', NULL, NULL, 'https://www.신한.com/fund/8173', '※ 투자 전 투자설명서 및 약관을 반드시 확인하시기 바랍니다.', 14900, 454, 447, NULL);
INSERT INTO fund (id, name, rate, fund_price, fund_price_movement, TER, category, theme, investment_warning_grade, upfront_fee, management_fee, minimum_cost, target, investment_type, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code) VALUES ('F0014', '신한 성장주 혼합형펀드', 6.34, 1150.84, 49.84, 0.98, 'MMF', 'ETF(국내채권)', '6등급', 0.43, 1.14, 100000, '고액 자산가', '적립', 'KB자산운용', NULL, NULL, 'https://www.교보.com/fund/8886', '※ 펀드 수익률은 과거의 실적이며, 미래의 수익을 보장하지 않습니다.', 13871, 575, 311, NULL);
INSERT INTO fund (id, name, rate, fund_price, fund_price_movement, TER, category, theme, investment_warning_grade, upfront_fee, management_fee, minimum_cost, target, investment_type, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code) VALUES ('F0015', 'NH-Amundi 고정수익 혼합형펀드', 1.39, 1130.61, 36.73, 1.78, '해외주식형', 'ETF(해외주식)', '2등급', 0.62, 0.88, 100000, '개인 투자자', '적립', '삼성자산운용', NULL, NULL, 'https://www.미래에셋.com/fund/4850', '※ 펀드 수익률은 과거의 실적이며, 미래의 수익을 보장하지 않습니다.', 3201, 163, 210, NULL);
INSERT INTO fund (id, name, rate, fund_price, fund_price_movement, TER, category, theme, investment_warning_grade, upfront_fee, management_fee, minimum_cost, target, investment_type, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code) VALUES ('F0016', '신한 테크 증권자투자신탁', 8.81, 1167.7, -6.26, 2.0, '국내대체', '배당주펀드', '4등급', 0.69, 0.63, 10000, '고액 자산가', '거치', '삼성자산운용', NULL, NULL, 'https://www.nh-amundi.com/fund/7913', '※ 펀드 수익률은 과거의 실적이며, 미래의 수익을 보장하지 않습니다.', 6641, 464, 499, NULL);
INSERT INTO fund (id, name, rate, fund_price, fund_price_movement, TER, category, theme, investment_warning_grade, upfront_fee, management_fee, minimum_cost, target, investment_type, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code) VALUES ('F0017', '삼성 그로스 인덱스 증권자투자신탁', 0.47, 1133.35, 36.7, 1.53, '국내주식형', '배당주펀드', '6등급', 0.32, 0.82, 100000, '개인 투자자', '거치', 'NH-Amundi자산운용', NULL, NULL, 'https://www.nh-amundi.com/fund/6322', '※ 이 상품은 원금 손실이 발생할 수 있으며, 예금자 보호법에 의해 보호되지 않습니다.', 825, 52, 90, NULL);
INSERT INTO fund (id, name, rate, fund_price, fund_price_movement, TER, category, theme, investment_warning_grade, upfront_fee, management_fee, minimum_cost, target, investment_type, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code) VALUES ('F0018', '삼성 글로벌 혁신 펀드', 4.28, 1222.46, 5.53, 0.45, '해외채권형', '미래기술테마', '5등급', 0.0, 0.17, 100000, '고액 자산가', '거치', '교보자산운용', NULL, NULL, 'https://www.교보.com/fund/9575', '※ 펀드 수익률은 과거의 실적이며, 미래의 수익을 보장하지 않습니다.', 4579, 559, 450, NULL);
INSERT INTO fund (id, name, rate, fund_price, fund_price_movement, TER, category, theme, investment_warning_grade, upfront_fee, management_fee, minimum_cost, target, investment_type, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code) VALUES ('F0019', '신한 그로스 인덱스 펀드', 3.32, 1049.09, 19.55, 1.39, 'MMF', '미래기술테마', '5등급', 0.56, 0.73, 100000, '고액 자산가', '적립', '신한자산운용', NULL, NULL, 'https://www.한화.com/fund/7628', '※ 펀드 수익률은 과거의 실적이며, 미래의 수익을 보장하지 않습니다.', 4352, 926, 381, NULL);
INSERT INTO fund (id, name, rate, fund_price, fund_price_movement, TER, category, theme, investment_warning_grade, upfront_fee, management_fee, minimum_cost, target, investment_type, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code) VALUES ('F0020', '신한 성장주 증권자투자신탁', 10.42, 1256.72, 2.57, 0.41, '국내대체', 'ETF(해외주식)', '3등급', 0.93, 0.18, 100000, '고액 자산가', '적립', '교보자산운용', NULL, NULL, 'https://www.nh-amundi.com/fund/9878', '※ 이 상품은 원금 손실이 발생할 수 있으며, 예금자 보호법에 의해 보호되지 않습니다.', 1594, 962, 345, NULL);
INSERT INTO fund (id, name, rate, fund_price, fund_price_movement, TER, category, theme, investment_warning_grade, upfront_fee, management_fee, minimum_cost, target, investment_type, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code) VALUES ('F0021', '신한 중소형주 혼합형펀드', -1.5, 1082.87, -3.73, 1.25, '해외채권형', 'ETF(국내채권)', '6등급', 0.76, 0.76, 100000, '고액 자산가', '적립', 'KB자산운용', NULL, NULL, 'https://www.삼성.com/fund/9874', '※ 펀드 수익률은 과거의 실적이며, 미래의 수익을 보장하지 않습니다.', 6651, 770, 297, NULL);
INSERT INTO fund (id, name, rate, fund_price, fund_price_movement, TER, category, theme, investment_warning_grade, upfront_fee, management_fee, minimum_cost, target, investment_type, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code) VALUES ('F0022', '한화 친환경 펀드', 10.49, 1131.61, 33.8, 0.45, '해외채권형', 'ETF(해외주식)', '3등급', 0.49, 1.06, 10000, '고액 자산가', '적립', '한화자산운용', NULL, NULL, 'https://www.미래에셋.com/fund/6009', '※ 투자 전 투자설명서 및 약관을 반드시 확인하시기 바랍니다.', 7229, 538, 149, NULL);
INSERT INTO fund (id, name, rate, fund_price, fund_price_movement, TER, category, theme, investment_warning_grade, upfront_fee, management_fee, minimum_cost, target, investment_type, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code) VALUES ('F0023', '삼성 고배당 증권자투자신탁', 13.5, 1098.98, 20.42, 1.17, 'MMF', 'ETF(해외주식)', '5등급', 0.34, 0.47, 50000, '개인 투자자', '적립', '교보자산운용', NULL, NULL, 'https://www.nh-amundi.com/fund/6698', '※ 투자 전 투자설명서 및 약관을 반드시 확인하시기 바랍니다.', 10859, 85, 149, NULL);
INSERT INTO fund (id, name, rate, fund_price, fund_price_movement, TER, category, theme, investment_warning_grade, upfront_fee, management_fee, minimum_cost, target, investment_type, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code) VALUES ('F0024', '교보 테크 증권자투자신탁', 1.55, 988.23, 17.57, 0.38, '해외주식형', 'ETF(해외주식)', '2등급', 0.78, 0.84, 100000, '고액 자산가', '적립', '삼성자산운용', NULL, NULL, 'https://www.nh-amundi.com/fund/1750', '※ 펀드 수익률은 과거의 실적이며, 미래의 수익을 보장하지 않습니다.', 1822, 913, 93, NULL);
INSERT INTO fund (id, name, rate, fund_price, fund_price_movement, TER, category, theme, investment_warning_grade, upfront_fee, management_fee, minimum_cost, target, investment_type, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code) VALUES ('F0025', '한화 성장주 펀드', 12.43, 1184.07, -23.22, 1.07, 'MMF', '배당주펀드', '1등급', 0.8, 0.22, 100000, '개인 투자자', '적립', '교보자산운용', NULL, NULL, 'https://www.교보.com/fund/2343', '※ 이 상품은 원금 손실이 발생할 수 있으며, 예금자 보호법에 의해 보호되지 않습니다.', 186, 464, 248, NULL);
INSERT INTO fund (id, name, rate, fund_price, fund_price_movement, TER, category, theme, investment_warning_grade, upfront_fee, management_fee, minimum_cost, target, investment_type, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code) VALUES ('F0026', 'NH-Amundi 글로벌 혁신 펀드', 12.02, 1294.59, 9.83, 1.51, '국내주식형', 'ETF(해외주식)', '4등급', 0.07, 1.05, 100000, '개인 투자자', '적립', '삼성자산운용', NULL, NULL, 'https://www.신한.com/fund/2471', '※ 투자 전 투자설명서 및 약관을 반드시 확인하시기 바랍니다.', 4166, 584, 211, NULL);
INSERT INTO fund (id, name, rate, fund_price, fund_price_movement, TER, category, theme, investment_warning_grade, upfront_fee, management_fee, minimum_cost, target, investment_type, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code) VALUES ('F0027', 'KB 고배당 혼합형펀드', 11.25, 969.4, 13.77, 0.92, 'MMF', '미래기술테마', '5등급', 0.94, 0.22, 50000, '고액 자산가', '거치', 'KB자산운용', NULL, NULL, 'https://www.nh-amundi.com/fund/8584', '※ 이 상품은 원금 손실이 발생할 수 있으며, 예금자 보호법에 의해 보호되지 않습니다.', 16363, 132, 369, NULL);
INSERT INTO fund (id, name, rate, fund_price, fund_price_movement, TER, category, theme, investment_warning_grade, upfront_fee, management_fee, minimum_cost, target, investment_type, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code) VALUES ('F0028', 'NH-Amundi 성장주 혼합형펀드', 11.91, 944.86, 25.19, 0.72, '국내대체', '미래기술테마', '5등급', 0.43, 0.81, 10000, '고액 자산가', '적립', '한화자산운용', NULL, NULL, 'https://www.한화.com/fund/4359', '※ 투자 전 투자설명서 및 약관을 반드시 확인하시기 바랍니다.', 12675, 909, 58, NULL);
INSERT INTO fund (id, name, rate, fund_price, fund_price_movement, TER, category, theme, investment_warning_grade, upfront_fee, management_fee, minimum_cost, target, investment_type, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code) VALUES ('F0029', 'KB 테크 증권자투자신탁', 12.03, 1084.21, -19.18, 1.89, 'MMF', 'ETF(국내채권)', '2등급', 0.77, 0.96, 50000, '개인 투자자', '적립', '교보자산운용', NULL, NULL, 'https://www.신한.com/fund/5606', '※ 투자 전 투자설명서 및 약관을 반드시 확인하시기 바랍니다.', 19606, 686, 451, NULL);
INSERT INTO fund (id, name, rate, fund_price, fund_price_movement, TER, category, theme, investment_warning_grade, upfront_fee, management_fee, minimum_cost, target, investment_type, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code) VALUES ('F0030', '삼성 성장주 증권자투자신탁', 2.65, 949.63, 36.08, 1.07, '해외주식형', 'ETF(국내채권)', '6등급', 0.51, 1.06, 50000, '개인 투자자', '적립', '교보자산운용', NULL, NULL, 'https://www.미래에셋.com/fund/8705', '※ 이 상품은 원금 손실이 발생할 수 있으며, 예금자 보호법에 의해 보호되지 않습니다.', 3985, 229, 65, NULL);
INSERT INTO fund (id, name, rate, fund_price, fund_price_movement, TER, category, theme, investment_warning_grade, upfront_fee, management_fee, minimum_cost, target, investment_type, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code) VALUES ('F0031', '한화 글로벌 혁신 혼합형펀드', 7.62, 1077.96, 27.73, 0.82, '국내대체', 'ETF(국내채권)', '5등급', 0.37, 0.69, 50000, '개인 투자자', '거치', '삼성자산운용', NULL, NULL, 'https://www.kb.com/fund/5599', '※ 펀드 수익률은 과거의 실적이며, 미래의 수익을 보장하지 않습니다.', 11937, 894, 281, NULL);
INSERT INTO fund (id, name, rate, fund_price, fund_price_movement, TER, category, theme, investment_warning_grade, upfront_fee, management_fee, minimum_cost, target, investment_type, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code) VALUES ('F0032', '삼성 고정수익 혼합형펀드', 9.48, 1202.51, 49.22, 1.51, '국내주식형', '미래기술테마', '3등급', 0.95, 0.59, 10000, '개인 투자자', '적립', 'KB자산운용', NULL, NULL, 'https://www.신한.com/fund/7764', '※ 이 상품은 원금 손실이 발생할 수 있으며, 예금자 보호법에 의해 보호되지 않습니다.', 4917, 477, 413, NULL);
INSERT INTO fund (id, name, rate, fund_price, fund_price_movement, TER, category, theme, investment_warning_grade, upfront_fee, management_fee, minimum_cost, target, investment_type, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code) VALUES ('F0033', '삼성 글로벌 혁신 펀드', -1.32, 1270.77, -15.12, 1.9, 'MMF', '미래기술테마', '5등급', 0.25, 1.0, 50000, '고액 자산가', '적립', 'KB자산운용', NULL, NULL, 'https://www.kb.com/fund/8072', '※ 이 상품은 원금 손실이 발생할 수 있으며, 예금자 보호법에 의해 보호되지 않습니다.', 1685, 162, 192, NULL);
INSERT INTO fund (id, name, rate, fund_price, fund_price_movement, TER, category, theme, investment_warning_grade, upfront_fee, management_fee, minimum_cost, target, investment_type, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code) VALUES ('F0034', 'NH-Amundi 친환경 증권자투자신탁', 13.62, 1213.77, 6.85, 1.05, 'MMF', '배당주펀드', '3등급', 0.77, 1.04, 50000, '고액 자산가', '적립', '미래에셋자산운용', NULL, NULL, 'https://www.kb.com/fund/9243', '※ 이 상품은 원금 손실이 발생할 수 있으며, 예금자 보호법에 의해 보호되지 않습니다.', 11003, 791, 322, NULL);
INSERT INTO fund (id, name, rate, fund_price, fund_price_movement, TER, category, theme, investment_warning_grade, upfront_fee, management_fee, minimum_cost, target, investment_type, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code) VALUES ('F0035', 'NH-Amundi 고배당 펀드', 14.4, 943.28, 25.75, 0.34, '해외채권형', '미래기술테마', '2등급', 0.38, 0.45, 100000, '개인 투자자', '적립', 'NH-Amundi자산운용', NULL, NULL, 'https://www.신한.com/fund/3951', '※ 투자 전 투자설명서 및 약관을 반드시 확인하시기 바랍니다.', 14247, 358, 445, NULL);
INSERT INTO fund (id, name, rate, fund_price, fund_price_movement, TER, category, theme, investment_warning_grade, upfront_fee, management_fee, minimum_cost, target, investment_type, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code) VALUES ('F0036', 'KB 고정수익 펀드', -0.35, 910.16, 20.4, 1.59, '해외채권형', '미래기술테마', '6등급', 0.39, 0.36, 50000, '개인 투자자', '거치', '삼성자산운용', NULL, NULL, 'https://www.nh-amundi.com/fund/1924', '※ 이 상품은 원금 손실이 발생할 수 있으며, 예금자 보호법에 의해 보호되지 않습니다.', 19827, 758, 48, NULL);
INSERT INTO fund (id, name, rate, fund_price, fund_price_movement, TER, category, theme, investment_warning_grade, upfront_fee, management_fee, minimum_cost, target, investment_type, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code) VALUES ('F0037', 'NH-Amundi 고정수익 혼합형펀드', 14.1, 1295.06, 41.36, 0.63, '국내주식형', 'ETF(해외주식)', '1등급', 0.15, 0.91, 100000, '개인 투자자', '적립', '신한자산운용', NULL, NULL, 'https://www.삼성.com/fund/7318', '※ 펀드 수익률은 과거의 실적이며, 미래의 수익을 보장하지 않습니다.', 8154, 912, 123, NULL);
INSERT INTO fund (id, name, rate, fund_price, fund_price_movement, TER, category, theme, investment_warning_grade, upfront_fee, management_fee, minimum_cost, target, investment_type, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code) VALUES ('F0038', '교보 테크 증권자투자신탁', 4.6, 1087.98, 27.18, 0.61, '해외채권형', 'ETF(해외주식)', '3등급', 0.68, 0.29, 100000, '고액 자산가', '거치', '교보자산운용', NULL, NULL, 'https://www.삼성.com/fund/1471', '※ 펀드 수익률은 과거의 실적이며, 미래의 수익을 보장하지 않습니다.', 18122, 744, 208, NULL);
INSERT INTO fund (id, name, rate, fund_price, fund_price_movement, TER, category, theme, investment_warning_grade, upfront_fee, management_fee, minimum_cost, target, investment_type, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code) VALUES ('F0039', '한화 고배당 펀드', 8.91, 1052.13, 14.31, 0.71, '국내주식형', 'ETF(해외주식)', '2등급', 0.21, 0.79, 50000, '개인 투자자', '적립', '교보자산운용', NULL, NULL, 'https://www.삼성.com/fund/8255', '※ 펀드 수익률은 과거의 실적이며, 미래의 수익을 보장하지 않습니다.', 1987, 926, 430, NULL);
INSERT INTO fund (id, name, rate, fund_price, fund_price_movement, TER, category, theme, investment_warning_grade, upfront_fee, management_fee, minimum_cost, target, investment_type, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code) VALUES ('F0040', '한화 성장주 혼합형펀드', 9.8, 1112.93, 7.55, 1.52, '해외주식형', 'ETF(국내채권)', '1등급', 0.61, 0.88, 50000, '고액 자산가', '적립', '교보자산운용', NULL, NULL, 'https://www.신한.com/fund/6907', '※ 투자 전 투자설명서 및 약관을 반드시 확인하시기 바랍니다.', 12957, 787, 258, NULL);
INSERT INTO fund (id, name, rate, fund_price, fund_price_movement, TER, category, theme, investment_warning_grade, upfront_fee, management_fee, minimum_cost, target, investment_type, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code) VALUES ('F0041', '미래에셋 고정수익 펀드', -0.42, 1262.46, -2.34, 1.16, '해외주식형', '배당주펀드', '1등급', 0.75, 1.0, 10000, '고액 자산가', '거치', '교보자산운용', NULL, NULL, 'https://www.nh-amundi.com/fund/2698', '※ 투자 전 투자설명서 및 약관을 반드시 확인하시기 바랍니다.', 7529, 340, 214, NULL);
INSERT INTO fund (id, name, rate, fund_price, fund_price_movement, TER, category, theme, investment_warning_grade, upfront_fee, management_fee, minimum_cost, target, investment_type, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code) VALUES ('F0042', 'KB 글로벌 혁신 증권자투자신탁', 1.33, 1223.17, 10.65, 1.34, '국내대체', 'ETF(국내채권)', '4등급', 0.71, 0.48, 50000, '개인 투자자', '거치', '한화자산운용', NULL, NULL, 'https://www.신한.com/fund/7587', '※ 투자 전 투자설명서 및 약관을 반드시 확인하시기 바랍니다.', 14982, 288, 246, NULL);
INSERT INTO fund (id, name, rate, fund_price, fund_price_movement, TER, category, theme, investment_warning_grade, upfront_fee, management_fee, minimum_cost, target, investment_type, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code) VALUES ('F0043', '교보 코리아 대표 혼합형펀드', 11.24, 1076.81, -26.27, 0.62, '해외채권형', '미래기술테마', '3등급', 0.46, 0.18, 50000, '개인 투자자', '적립', '한화자산운용', NULL, NULL, 'https://www.한화.com/fund/7317', '※ 이 상품은 원금 손실이 발생할 수 있으며, 예금자 보호법에 의해 보호되지 않습니다.', 3226, 6, 361, NULL);
INSERT INTO fund (id, name, rate, fund_price, fund_price_movement, TER, category, theme, investment_warning_grade, upfront_fee, management_fee, minimum_cost, target, investment_type, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code) VALUES ('F0044', '미래에셋 코리아 대표 증권자투자신탁', 12.16, 981.45, 1.98, 1.4, '해외채권형', '미래기술테마', '5등급', 0.39, 0.59, 10000, '고액 자산가', '거치', 'KB자산운용', NULL, NULL, 'https://www.한화.com/fund/2130', '※ 투자 전 투자설명서 및 약관을 반드시 확인하시기 바랍니다.', 17157, 316, 50, NULL);
INSERT INTO fund (id, name, rate, fund_price, fund_price_movement, TER, category, theme, investment_warning_grade, upfront_fee, management_fee, minimum_cost, target, investment_type, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code) VALUES ('F0045', '미래에셋 코리아 대표 증권자투자신탁', 2.66, 917.97, 48.97, 0.8, '국내대체', '배당주펀드', '4등급', 0.97, 0.33, 50000, '고액 자산가', '거치', '미래에셋자산운용', NULL, NULL, 'https://www.신한.com/fund/1924', '※ 이 상품은 원금 손실이 발생할 수 있으며, 예금자 보호법에 의해 보호되지 않습니다.', 10056, 581, 281, NULL);
INSERT INTO fund (id, name, rate, fund_price, fund_price_movement, TER, category, theme, investment_warning_grade, upfront_fee, management_fee, minimum_cost, target, investment_type, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code) VALUES ('F0046', '교보 그로스 인덱스 펀드', 11.05, 1148.07, -0.92, 0.31, '해외주식형', '미래기술테마', '5등급', 0.76, 1.13, 100000, '고액 자산가', '거치', '삼성자산운용', NULL, NULL, 'https://www.교보.com/fund/4504', '※ 펀드 수익률은 과거의 실적이며, 미래의 수익을 보장하지 않습니다.', 8488, 548, 300, NULL);
INSERT INTO fund (id, name, rate, fund_price, fund_price_movement, TER, category, theme, investment_warning_grade, upfront_fee, management_fee, minimum_cost, target, investment_type, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code) VALUES ('F0047', 'NH-Amundi 고배당 증권자투자신탁', 10.62, 907.22, 19.99, 1.37, 'MMF', '미래기술테마', '4등급', 0.11, 0.35, 10000, '개인 투자자', '적립', '교보자산운용', NULL, NULL, 'https://www.신한.com/fund/2534', '※ 펀드 수익률은 과거의 실적이며, 미래의 수익을 보장하지 않습니다.', 12375, 333, 206, NULL);
INSERT INTO fund (id, name, rate, fund_price, fund_price_movement, TER, category, theme, investment_warning_grade, upfront_fee, management_fee, minimum_cost, target, investment_type, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code) VALUES ('F0048', '교보 글로벌 혁신 혼합형펀드', 7.03, 1161.26, 48.62, 1.79, '국내주식형', 'ETF(해외주식)', '4등급', 0.57, 0.27, 10000, '고액 자산가', '거치', '한화자산운용', NULL, NULL, 'https://www.kb.com/fund/5848', '※ 이 상품은 원금 손실이 발생할 수 있으며, 예금자 보호법에 의해 보호되지 않습니다.', 13607, 296, 124, NULL);
INSERT INTO fund (id, name, rate, fund_price, fund_price_movement, TER, category, theme, investment_warning_grade, upfront_fee, management_fee, minimum_cost, target, investment_type, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code) VALUES ('F0049', '미래에셋 고정수익 혼합형펀드', 3.2, 1232.27, 46.55, 1.16, '국내주식형', 'ETF(해외주식)', '3등급', 0.54, 0.55, 10000, '고액 자산가', '거치', '미래에셋자산운용', NULL, NULL, 'https://www.삼성.com/fund/7772', '※ 펀드 수익률은 과거의 실적이며, 미래의 수익을 보장하지 않습니다.', 12903, 455, 166, NULL);

INSERT INTO forex (
    `id`, `name`, `rate`, `description`, `currency`, `reg_fund`, `reg_limit_date`, `auto_renew`,
    `extra_deposit`, `rate_give_way`, `tax_refund`, `protect`, `company`, `score`, `risk`,
    `reg_link`, `caution`, `view_cnt`, `scrap_cnt`, `regret_cnt`, `algo_code`
) VALUES
      ('X1', 'Sol트래블 외화예금', 3.1, '여행자용 외화예금', 'USD', 100000, '2027-03-12', 'Y', FALSE, '이자 지급', TRUE, TRUE, '신한은행', 5, 2, 'https://bank.shinhan.com/index.jsp#020504030000', '원금 손실 가능성 있음. 환율 변동 주의.', 25432, 1453, 210, 6.37),
      ('X2', '외화 체인지업 예금', 2.75, '환율 변동 활용 상품', 'EUR', 100000, '2026-11-01', 'Y', TRUE, '이자 지급', TRUE, TRUE, '신한은행', 4, 2, 'https://bank.shinhan.com/index.jsp#020504030000', '중도 해지 시 이자 손실 가능.', 13102, 790, 115, 1.29),
      ('X3', 'Someday(썸데이) 외화적금', 3.5, 'USD 적립식 적금', 'USD', 50000, '2027-07-19', 'N', TRUE, '이자 지급', TRUE, TRUE, '신한은행', 5, 2, 'https://bank.shinhan.com/index.jsp#020504030000', '적립 기간 준수 필요. 환율 리스크 있음.', 30987, 1323, 87, 5.03),
      ('X4', 'Value-up 외화적립예금', 3.2, 'JPY 적립식 적금', 'JPY', 50000, '2025-10-30', 'N', TRUE, '이자 지급', TRUE, TRUE, '신한은행', 5, 2, 'https://bank.shinhan.com/index.jsp#020504030000', '해지 시 이자율 변동 가능성 있음.', 22034, 1975, 312, 3.47),
      ('X5', 'More 환테크 적립예금', 3.0, 'GBP 적립식 적금', 'GBP', 50000, '2026-12-15', 'N', TRUE, '이자 지급', TRUE, TRUE, '신한은행', 4, 2, 'https://bank.shinhan.com/index.jsp#020504030000', '투자 위험에 유의하세요.', 18912, 1520, 47, 7.81),
      ('X6', 'KB TWO테크 외화정기예금', 3.0, 'USD 정기예금', 'USD', 100000, '2027-01-08', 'Y', FALSE, '이자 지급', TRUE, TRUE, '국민은행', 4, 2, 'https://obank.kbstar.com/quics?page=C101501&cc=b102293:b103845&QSL&브랜드상품코드=FD01000970&브랜드상품명=KB%20TWO테크%20외화정기예금', '예금자 보호법 적용 상품 아님.', 12765, 623, 98, 9.05),
      ('X7', 'KB두근두근외화적금', 3.6, 'EUR 적립식 적금', 'EUR', 50000, '2025-11-23', 'N', TRUE, '이자 지급', TRUE, TRUE, '국민은행', 5, 2, 'https://obank.kbstar.com/quics?page=C101501&cc=b102293:b103845&QSL&브랜드상품코드=FD01000972&브랜드상품명=KB두근두근외화적금', '이자 변동 가능성 있음.', 34210, 1800, 399, 0.72),
      ('X8', 'KB국민UP 외화정기예금', 3.1, 'JPY 정기예금', 'JPY', 100000, '2027-06-05', 'Y', FALSE, '이자 지급', TRUE, TRUE, '국민은행', 5, 2, 'https://obank.kbstar.com/quics?page=C101501&cc=b102293:b103845&QSL&브랜드상품코드=FD01000954&브랜드상품명=KB국민UP%20외화정기예금', '중도 해지 시 페널티 부과.', 32005, 1523, 201, 4.66),
      ('X9', 'KB WISE 외화정기예금', 2.9, 'GBP 정기예금', 'GBP', 100000, '2026-08-10', 'Y', FALSE, '이자 지급', TRUE, TRUE, '국민은행', 4, 2, 'https://obank.kbstar.com/quics?page=C101501&cc=b102293:b103845&QSL&브랜드상품코드=FD01000955&브랜드상품명=KB%20WISE%20외화정기예금', '환율 변동 주의.', 14932, 954, 78, 1.11),
      ('X10', 'KB 적립식 외화정기예금', 3.4, 'USD 적립식 적금', 'USD', 50000, '2025-12-20', 'N', TRUE, '이자 지급', TRUE, TRUE, '국민은행', 5, 2, 'https://obank.kbstar.com/quics?page=C101501&cc=b102293:b103845&QSL&브랜드상품코드=FD01000953&브랜드상품명=KB%20적립식%20외화정기예금', '적립 기간 중 중도 해지 불이익 있음.', 27891, 1478, 225, 2.90),
      ('X11', '우리 외화바로예금', 2.8, 'USD 입출금 가능한 외화예금', 'USD', 100000, '2027-02-25', 'Y', FALSE, '이자 지급', TRUE, TRUE, '우리은행', 4, 2, 'https://spot.wooribank.com/pot/Dream?withyou=FXDEP0002&cc=c004253:c009166;c012263:c012399&PRD_CD=P030000074&PLM_PDCD=P030000074', '환율 하락 시 원금 손실 가능.', 20234, 1390, 49, 3.22),
      ('X12', '우리ONE 회전식 복리 외화예금', 3.15, 'EUR 복리 회전식 예금', 'EUR', 100000, '2025-10-18', 'Y', FALSE, '복리 이자 지급', TRUE, TRUE, '우리은행', 5, 2, 'https://spot.wooribank.com/pot/Dream?withyou=FXDEP0002&cc=c004253:c009166;c012263:c012399&PRD_CD=P030000019&PLM_PDCD=P030000019', '복리 이자 지급, 중도 해지 시 불이익.', 31100, 1977, 270, 8.48),
      ('X13', '환율CARE 외화적립예금', 3.3, 'JPY 적립식 예금', 'JPY', 50000, '2026-09-15', 'N', TRUE, '이자 지급', TRUE, TRUE, '우리은행', 5, 2, 'https://spot.wooribank.com/pot/Dream?withyou=FXDEP0002&cc=c004253:c009166;c012263:c012399&PRD_CD=P030000017&PLM_PDCD=P030000017', '해지 시 환율 변동에 따른 손실 가능.', 25789, 1210, 112, 5.11),
      ('X14', '해외로 외화적립예금', 3.0, 'GBP 적립식 예금', 'GBP', 50000, '2027-04-01', 'N', TRUE, '이자 지급', TRUE, TRUE, '우리은행', 4, 2, 'https://spot.wooribank.com/pot/Dream?withyou=FXDEP0002&cc=c004253:c009166;c012263:c012399&PRD_CD=P030000015&PLM_PDCD=P030000015', '투자 원금 보장 안됨.', 32854, 1587, 310, 7.24),
      ('X15', '외화MMDA PLUS', 2.95, 'USD 단기 MMDA 상품', 'USD', 100000, '2026-06-20', 'Y', FALSE, '이자 지급', TRUE, TRUE, '우리은행', 4, 2, 'https://spot.wooribank.com/pot/Dream?withyou=FXDEP0002&cc=c004253:c009166;c012263:c012399&PRD_CD=P030000014&PLM_PDCD=P030000014', '중도 해지 시 이자율 변동.', 11842, 1124, 42, 4.00),
      ('X16', '외화정기예금', 3.1, 'USD 외화 정기예금', 'USD', 100000, '2027-05-25', 'Y', FALSE, '이자 지급', TRUE, TRUE, '우리은행', 4, 2, 'https://spot.wooribank.com/pot/Dream?withyou=FXDEP0002&cc=c004253:c009166;c012263:c012399&PRD_CD=P030000013&PLM_PDCD=P030000013', '환율 변동 리스크 있음.', 30342, 960, 172, 9.92),
      ('X17', '외화보통예금', 2.7, 'USD 입출금 자유로운 외화보통예금', 'USD', 100000, '2026-12-29', 'Y', FALSE, '이자 지급', TRUE, TRUE, '우리은행', 4, 2, 'https://spot.wooribank.com/pot/Dream?withyou=FXDEP0002&cc=c004253:c009166;c012263:c012399&PRD_CD=P030000003&PLM_PDCD=P030000003', '원금 손실 가능성 주의.', 20745, 1388, 405, 1.07),
      ('X18', '위비트래블 외화예금', 3.2, '여행자용 USD 외화예금', 'USD', 100000, '2027-03-28', 'Y', FALSE, '이자 지급', TRUE, TRUE, '우리은행', 5, 2, 'https://spot.wooribank.com/pot/Dream?withyou=FXDEP0002&cc=c004253:c009166;c012263:c012399&PRD_CD=P030000093&PLM_PDCD=P030000093', '여행자용으로 중도 해지 가능.', 25291, 1776, 118, 0.91),
      ('X19', '우리 더(The)달러 외화적립예금', 3.4, 'USD 적립식 외화적금', 'USD', 50000, '2027-01-11', 'N', TRUE, '이자 지급', TRUE, TRUE, '우리은행', 5, 2, 'https://spot.wooribank.com/pot/Dream?withyou=FXDEP0002&cc=c004253:c009166;c012263:c012399&PRD_CD=P030000082&PLM_PDCD=P030000082', '적립 기간 중 해지 시 불이익.', 33104, 1042, 236, 8.16),
      ('X20', 'NH환테크 외화회전예금I', 2.85, 'USD 회전식 예금', 'USD', 100000, '2026-07-06', 'Y', FALSE, '이자 지급', TRUE, TRUE, '농협은행', 4, 2, 'https://smartmarket.nonghyup.com/servlet/BFDCW2001R.view', '회전식 예금 특성에 따른 유의사항.', 25367, 870, 134, 3.08),
      ('X21', 'NH환테크 외화회전예금Ⅱ', 2.75, 'EUR 회전식 예금', 'EUR', 100000, '2026-10-14', 'Y', FALSE, '이자 지급', TRUE, TRUE, '농협은행', 4, 2, 'https://smartmarket.nonghyup.com/servlet/BFDCW2001R.view', '환율 변동 주의 요망.', 31642, 1912, 399, 7.12),
      ('X22', 'Multi-one 외화정기예금', 3.2, 'JPY 정기예금', 'JPY', 100000, '2027-02-28', 'Y', FALSE, '이자 지급', TRUE, TRUE, '농협은행', 5, 2, 'https://smartmarket.nonghyup.com/servlet/BFDCW2001R.view', '중도 해지 시 페널티 발생.', 28671, 1103, 276, 5.19),
      ('X23', 'NH주거래우대외화적립예금', 3.35, 'USD 적립식 예금', 'USD', 50000, '2026-05-22', 'N', TRUE, '이자 지급', TRUE, TRUE, '농협은행', 5, 2, 'https://smartmarket.nonghyup.com/servlet/BFDCW2001R.view', '적립 기간 중 해지 불이익.', 18840, 1288, 92, 2.56),
      ('X24', '다통화 월복리 외화적립예금', 3.4, '다통화 적립식 월복리 예금', 'USD, EUR', 50000, '2027-04-18', 'N', TRUE, '복리 이자 지급', TRUE, TRUE, '농협은행', 5, 2, 'https://smartmarket.nonghyup.com/servlet/BFDCW2001R.view', '복리 효과 및 환율 변동 위험.', 17650, 1471, 317, 9.87),
      ('X25', '트리플외화자유적립예금', 3.3, 'USD, EUR, JPY 3개 통화 자유적립 적금', 'USD, EUR, JPY', 50000, '2027-06-27', 'N', TRUE, '이자 지급', TRUE, TRUE, '농협은행', 5, 2, 'https://smartmarket.nonghyup.com/servlet/BFDCW2001R.view', '여러 통화 환율 변동 유의.', 20123, 1994, 388, 0.66),
      ('X26', '하나로외화자유적립예금', 3.1, '다양한 통화 자유롭게 적립 가능한 적금', 'USD, EUR, GBP', 50000, '2027-03-07', 'N', TRUE, '이자 지급', TRUE, TRUE, '농협은행', 5, 2, 'https://smartmarket.nonghyup.com/servlet/BFDCW2001R.view', '자유적립 조건 및 환율 리스크.', 23654, 1403, 153, 1.04),
      ('X27', '위안화적립식정기예금', 3.25, 'CNY 위안화 적립식 정기예금', 'CNY', 50000, '2026-08-29', 'N', TRUE, '이자 지급', TRUE, TRUE, '농협은행', 5, 2, 'https://smartmarket.nonghyup.com/servlet/BFDCW2001R.view', '중도 해지 시 불이익 주의.', 17213, 1389, 225, 6.58),
      ('X28', '외화정기예금', 3.0, 'USD 외화 정기예금', 'USD', 100000, '2027-01-19', 'Y', FALSE, '이자 지급', TRUE, TRUE, '농협은행', 4, 2, 'https://smartmarket.nonghyup.com/servlet/BFDCW2001R.view', '정기예금 조건 준수 필요.', 24111, 870, 95, 7.77),
      ('X29', 'IBK 외화통장', 2.9, '중소기업 대상 외화통장', 'USD', 100000, '2027-07-02', 'Y', FALSE, '이자 지급', TRUE, TRUE, '기업은행', 4, 2, 'https://mybank.ibk.co.kr/uib/jsp/index.jsp', '중소기업 대상 상품입니다.', 30122, 1876, 416, 2.28),
      ('X30', '원화외화 내맘대로 통장', 2.85, '원화 및 외화 혼합 통장', 'USD, EUR', 100000, '2026-09-11', 'Y', TRUE, '이자 지급', TRUE, TRUE, '기업은행', 4, 2, 'https://mybank.ibk.co.kr/uib/jsp/index.jsp', '환율 변동에 따른 손실 가능성 있음.', 21874, 1671, 234, 4.11);

INSERT INTO deposit (id, name, rate, prime_rate, join_way, end_date, max_limit, join_member, join_deny, bonus_condition, save_term, minimum_cost, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code) VALUES
                                                                                                                                                                                                                                           ('D1', 'WON플러스예금', 3.50, 2.50, '인터넷,스마트폰,전화(텔레뱅킹)', NULL, NULL, '실명의 개인', 1, '해당사항 없음', 12, '1만원 이상', '우리은행', 85, 3, 'https://wooribank.com', '- 가입기간: 1~36개월\n- 최소가입금액: 1만원 이상\n- 만기일을 일,월 단위로 자유롭게 선택 가능\n- 만기해지 시 신규일 당시 영업점과 인터넷 홈페이지에 고시된 계약기간별 금리 적용', 1200, 350, 50, 0.85),
                                                                                                                                                                                                                                           ('D2', 'e-그린세이브예금', 3.80, 2.80, '인터넷,스마트폰', '9999-12-31', 1000000000, '개인(개인사업자 포함)', 1, '1.SC제일은행 최초 거래 신규고객에 대하여 우대 이율을 제공함 (보너스이율0.2%)                     2.SC제일마이백통장에서 출금하여 이 예금을 신규하는경우에 보너스이율을 제공함\n(가입기간:1년제/ 보너스이율:0.1% / 만기해약하는 경우에 한해 보너스이율을 적용함)', 24, '1백만원 이상', '한국스탠다드차타드은행', 90, 2, 'https://standardchartered.co.kr', '디지털채널 전용상품 (인터넷, 모바일뱅킹)', 1500, 400, 60, 0.90),
                                                                                                                                                                                                                                           ('D3', 'iM주거래우대예금(첫만남고객형)', 3.60, 2.60, '영업점,인터넷,스마트폰', NULL, NULL, '실명의 개인', 1, '* 최고우대금리 : 연0.65%p              \n - 목돈굴리기예금 최초 가입시 : 연0.20%p\n - 상품 가입 전 최근 1개월 이내 신용(체크)카드 신규 발급 : 연0.20%p\n - 상품 가입 전 최근 1개월 이내 인터넷.폰.모바일앱뱅킹 가입 : 연0.20%p\n * 해당 상품을 인터넷/모바일뱅킹을 통해 가입 : 연0.05%p', 12, '100만원', '아이엠뱅크', 88, 3, 'https://imbank.com', '계좌당 가입 최저한도 : 100만원', 1100, 300, 45, 0.88),
                                                                                                                                                                                                                                           ('D4', 'iM행복파트너예금(일반형)', 3.40, 2.40, '영업점,인터넷,스마트폰', NULL, NULL, '만50세 이상 실명의 개인', 3, '* 최고우대금리 : 연0.45%p\n- 지난달 당행 통장으로 연금 입금 실적 보유 : 연0.10%p\n- 상품 가입 전 당행 신용(체크)카드 보유 : 연0.10%p\n- 지난 3개월 예금 평잔 30만원 이상 : 연0.10%p\n- iM행복파트너적금 동시 가입 및 만기 보유 : 연0.10%p\n* 해당 상품을 인터넷/모바일뱅킹을 통해 가입 : 연0.05%p', 12, '100만원', '아이엠뱅크', 82, 4, 'https://imbank.com', '계좌당 가입 최저한도 : 100만원', 950, 280, 40, 0.82),
                                                                                                                                                                                                                                           ('D5', 'iM함께예금', 3.30, 2.30, '영업점,인터넷,스마트폰', NULL, NULL, '실명의 개인 및 개인사업자', 1, '* 최고우대금리 : 연0.45%p\n- 전월 총 수신 평잔실적 또는 상품 가입 전 첫만남플러스 통장 보유시 \n- 당행 주택청약상품보유 \n-  "iM함께적금" 동시 가입 및 만기 보유 \n-당행 오픈뱅킹에 다른 은행 계좌 등록시 각  연0.10%p                       \n* 해당 상품을 인터넷/모바일뱅킹을 통해 가입 시 : 연0.05%p', 12, '100만원', '아이엠뱅크', 80, 3, 'https://imbank.com', '계좌당 가입 최저한도 : 100만원', 900, 270, 38, 0.80),
                                                                                                                                                                                                                                           ('D6', 'iM스마트예금', 3.20, 2.20, '인터넷,스마트폰', NULL, NULL, '실명의 개인', 1, '* 최고우대금리 : 연0.25%p\n- 가입일(재예치일)로부터 3개월 이내 아래 1가지 이상 요건 충족시\n① 당행 주택청약종합저축 보유\n② 당행 신용(체크)카드 결제실적 보유(결제금액 출금기준)\n* 해당 상품을 인터넷/모바일뱅킹을 통해 가입 : 연0.05%p', 12, '100만원', '아이엠뱅크', 78, 2, 'https://imbank.com', '계좌당 가입 최저한도 : 100만원', 850, 250, 35, 0.78),
                                                                                                                                                                                                                                           ('D7', 'LIVE정기예금', 3.70, 2.70, '영업점,인터넷', NULL, NULL, '제한없음', 1, '*우대이율\n가. 3~5개월 특판우대이율 : 0.70%\n나. 6~11개월 특판 우대이율: 0.60%\n다. 12개월 특판 우대이율 : 0.45%', 12, '1천만원 이상', '부산은행', 87, 3, 'https://busanbank.co.kr', '1. 가입금액 :\n   1천만원 이상\n2. 가입기간 : \n1개월 이상 60개월 이하(일단위)\n3. 월이자지급식/만기일시지급식', 1300, 380, 55, 0.87),
                                                                                                                                                                                                                                           ('D8', '더(The) 특판 정기예금', 3.90, 2.90, '인터넷,스마트폰', NULL, NULL, '실명의 개인', 1, '* 우대이율 (최대 0.70%p)\n가. 모바일뱅킹 금융정보 및 혜택알림 동의 우대이율 : 0.10%p\n나. 이벤트 우대이율 : 최대 0.6%p \n1) 더(The) 특판 정기예금 신규고객 우대이율 : 0.10%p\n2) 특판 우대이율 : 0.50%p', 12, '1백만원 이상', '부산은행', 92, 2, 'https://busanbank.co.kr', '1. 가입금액 : 1백만원 이상 제한없음 (원단위)\n2. 가입기간 : 1개월, 3개월, 6개월, 1년, 2년, 3년\n3. 이자지급방식 : 만기일시지급식', 1600, 450, 70, 0.92),
                                                                                                                                                                                                                                           ('D9', '더(The) 레벨업 정기예금', 3.55, 2.55, '스마트폰', NULL, NULL, '실명의 개인', 1, '*우대이율(최대 0.20%p)\n가. 모바일뱅킹 금융정보 및 혜택알림 동의 우대이율 : 0.10%p\n나. 비대면 정기예금 재예치 우대이율  : 0.10%', 12, '1백만원 이상', '부산은행', 86, 3, 'https://busanbank.co.kr', '1. 가입금액 : 1백만원 이상 제한없음 (원단위)\n2. 가입기간 : 6개월, 1년\n3. 이자지급방식 : 만기일시지급식', 1150, 330, 48, 0.86),
                                                                                                                                                                                                                                           ('D10', '미즈월복리정기예금', 3.45, 2.45, '영업점,인터넷,스마트폰,기타', NULL, 50000000, '만18세이상 여성으로 실명의 개인 및 개인사업자', 1, '▶ 최고우대금리 0.2% \n ① 요구불평잔 : 0.2% -300만원이상 0.1%, 500만원이상 0.2%\n ② 신용(체크)카드결제실적 : 0.1% -전월결제금 300만원이상 0.05%, 500만원이상 0.1%', 12, '5백만원이상', '광주은행', 84, 3, 'https://kjbank.com', '1. 가입기간 : 1년이상 3년제\n2. 가입금액 : 5백만원이상 최고 50백만원', 1000, 300, 42, 0.84),
                                                                                                                                                                                                                                           ('D11', '스마트모아Dream정기예금', 3.35, 2.35, '인터넷,스마트폰', NULL, NULL, '개인 및 개인사업자', 1, '▶ 1천만원 이상 가입시 최대 0.2%우대', 12, '100만원이상', '광주은행', 81, 2, 'https://kjbank.com', '1. 가입기간 : 1개월이상 3년제\n2. 최소가입금액 : 100만원이상', 980, 290, 40, 0.81),
                                                                                                                                                                                                                                           ('D12', '굿스타트예금', 4.00, 3.00, '스마트폰', NULL, 100000000, '개인 및 개인사업자', 1, '▶ 최고우대금리 0.5% \n ① 첫예금거래 : 0.4% -최근1년동안 정기예금 계좌 신규 또는 해지이력이 없는경우\n ② 개인(신용)정보 수집이용동의 : 0.1% -만기일전일까지 유지시', 12, '1백만원이상', '광주은행', 95, 1, 'https://kjbank.com', '1. 가입기간 : 1년제\n2. 가입금액 : 1백만원이상 최고 1억원(1인1계좌)', 1800, 500, 80, 0.95),
                                                                                                                                                                                                                                           ('D13', 'The플러스예금', 3.65, 2.65, '영업점,스마트폰', NULL, 1000000000, '개인 및 법인(단,국가 지자체 및 금융기관 제외)', 1, '▶ 해당사항없음', 6, '10백만원이상', '광주은행', 89, 3, 'https://kjbank.com', '1. 가입기간 : 3개월,6개월,1년제\n2. 가입금액 : 10백만원이상 고객당 10억원한도', 1100, 320, 47, 0.89),
                                                                                                                                                                                                                                           ('D14', '제주Dream\n정기예금\n(개인/만기\n지급식)', 3.25, 2.25, '영업점,인터넷,스마트폰', NULL, NULL, '제한없음', 1, '최고 연 0.1%p(항목별 0.1%p)\n①급여이체\n②적립식예금 잔액 10만원 이상 보유\n③탑스, 주거래 고객\n④결제계좌(가맹점) 전월 입금액 10만원 이상 \n⑤비과세종합저축 대상 고객\n⑥다자녀(3인이상 자녀)가정\n⑦탐나는 J연금통장 가입고객\n⑧국민연금안심통장 가입고객\n⑨공무원연금안심통장 가입고객', 12, '1백만원 이상', '제주은행', 79, 4, 'https://jejuebank.co.kr', '가입금액 : 1백만원 이상', 800, 240, 32, 0.79),
                                                                                                                                                                                                                                           ('D15', 'J정기예금\n(만기지급식)', 3.75, 2.75, '영업점,인터넷,스마트폰', NULL, NULL, '실명의 \n개인 및 \n개인사업자', 1, '- 아래의 우대요건 충족시 최고 0.5%p 추가 우대 \n①비대면 채널 가입시 0.3%제공(신규시제공)  \n(단, 이벤트시 디지털 채널에 고시한 우대금리를 추가 적용할 수 있음)\n②신규일로부터 만기달 제외한 계약기간의 1/2이상 매월 Jbank로그인 시 0.2%제공(만기시제공)', 12, '30만원 이상', '제주은행', 91, 2, 'https://jejuebank.co.kr', '가입금액 : 30만원 이상', 1400, 410, 65, 0.91),
                                                                                                                                                                                                                                           ('D16', '스마일드림 \n정기예금\n(개인/선이자\n지급식)', 3.15, 2.15, '영업점,스마트폰', NULL, NULL, '실명의\n개인 및 \n개인사업자', 1, '-아래의 우대요건 충족시 최고0.3% 추가우대(신규시제공)\n①김만덕나눔적금 보유 또는 김만적 나눔적금 만기 해지고객 0.2%우대(가입시제공)\n②예금가입시 탐나는전 체크카드 보유고객 0.1%우대(가입시제공)\n(단, 이벤트시 영업점,디지털채널에 고시한 우대금리를 추가 적용할 수 있음)', 12, '1백만원 이상', '제주은행', 77, 3, 'https://jejuebank.co.kr', '가입금액 : 1백만원 이상', 750, 220, 30, 0.77),
                                                                                                                                                                                                                                           ('D17', 'JB 다이렉트예금통장\n(만기일시지급식)', 3.85, 2.85, '인터넷,스마트폰', NULL, 1000000000, '실명의 개인(임의단체 제외', 1, '우대조건\n없음', 12, '1백만원이상', '전북은행', 93, 2, 'https://jbbank.co.kr', '가입금액 1계좌당 1백만원이상 10억원이하,\n1인당  총 10억원 이하,\n인터넷/스마트폰뱅킹 가입상품', 1700, 480, 75, 0.93),
                                                                                                                                                                                                                                           ('D18', 'JB 123 정기예금\n (만기일시지급식)', 3.95, 2.95, '인터넷,스마트폰', NULL, 500000000, '실명의 개인 또는 개인사업자 (1인 다계좌 가입 가능함)', 1, '자동재예치 우대이율\n1회차 0.1%,\n2회차 0.2%,\n3회차 0.3%', 12, '1백만원이상', '전북은행', 94, 1, 'https://jbbank.co.kr', '예금의 신규 : 인터넷뱅킹, 모바일뱅킹, 모바일웹, BDT\n예금의 해지 : 인터넷뱅킹, 모바일뱅킹, 영업점\n가입금액 최저 1백만원이상 고객별 5억원 이상 (다만 자동재예치시 이자 원가로 인한 5억원 초과는 가능), 계좌수 관계없이 가입가능', 1900, 520, 85, 0.94),
                                                                                                                                                                                                                                           ('D19', '내맘 쏙 정기예금', 3.70, 2.70, '인터넷,스마트폰', NULL, 999999999999, '실명의 개인 및 개인사업자', 1, '1. 모바일뱅킹 첫거래 고객 0.10%\n2. 마케팅동의고객 0.10%\n3. 입출금계좌 이용고객 0.10%', 12, '10만원 이상', '전북은행', 90, 2, 'https://jbbank.co.kr', '가입금액 : 계좌당 10만원 이상\n예금의 신규 : 모바일뱅킹, 모바일Web\n예금의 해지 : 모바일뱅킹, 인터넷뱅킹, 영업점\n계약기간 : 1개월이상 12개월이내(월단위)', 1550, 430, 68, 0.90),
                                                                                                                                                                                                                                           ('D20', 'BNK더조은정기예금', 3.60, 2.60, '인터넷,스마트폰', '9999-12-31', 500000000, '거래대상자는 제한을 두지 아니한다. 다만, 국가 및 지방자치단체는 이 예금을 거래할 수 없다.', 1, '①가입금액 20백만원 이상인 경우 0.20%\n②이 예금 신규시 금리우대쿠폰 등록할 경우 0.20% \n③ 경남은행 오픈뱅킹 서비스에 가입되어 있는 경우\n(만기시까지 해당서비스 유지하는 경우) 0.10%\n④ 자동재예치 신청 0.05%\n(단 금리우대쿠폰과 중복적용 불가)', 12, '100만원 이상', '경남은행', 88, 3, 'https://knbank.co.kr', '1. 이 예금의 계약기간은 3개월 이상 2년 이내 월단위로 한다.\n2. 가입금액은 1인당 최소 100만원 이상 5억원 이하이다.', 1250, 360, 52, 0.88),
                                                                                                                                                                                                                                           ('D21', 'The든든예금(시즌2)', 3.90, 2.90, '스마트폰', '2025-12-31', 1000000000, '개인', 1, '①마케팅동의 및 모바일메세지 수신동의 0.05%\n②신규고객 우대(최근 12개월 신규이력·해지이력 미보유) 0.10%\n③이벤트금리(비대면금리) 최대 0.60%\n   (3, 6개월 0.55% / 12개월 0.60%)', 12, '100만원 이상', '경남은행', 93, 2, 'https://knbank.co.kr', '1. 이 예금의 계약기간은 3개월, 6개월, 12개월로 한다.\n2. 가입좌수 제한없으며, 가입금액은 1인당 최소 100만원 이상 10억원 이하이다.', 1750, 490, 78, 0.93),
                                                                                                                                                                                                                                           ('D22', 'The파트너예금', 3.75, 2.75, '스마트폰', '9999-12-31', 1000000000, '개인', 1, '①경남은행 거래기간 5년이상 + 마케팅동의 고객 0.20%\n②급여, 연금, 가맹점대금 입금 시 0.10%\n③당행 카드 결제실적 보유 시 0.10%', 12, '100만원 이상', '경남은행', 90, 3, 'https://knbank.co.kr', '1. 이 예금의 계약기간은 6개월, 12개월, 24개월로 한다.\n2. 가입좌수 제한없으며, 가입금액은 1인당 최소 100만원 이상 10억원 이하이다.', 1450, 420, 63, 0.90),
                                                                                                                                                                                                                                           ('D23', 'IBK평생한가족통장(실세금리정기예금)', 3.50, 2.50, '영업점,인터넷,스마트폰', NULL, 100000000, '실명의 개인\n(개인사업자 제외)', 1, '최고 연 0.20%p\n\n-고객별 우대 : 최고 연 0.05%p\n 1. 최초신규고객 : 연 0.05%p\n 2. 재예치고객 : 연 0.05%p\n 3. 장기거래고객 : 연 0.05%p\n\n-주거래우대 : 연 0.15%p', 12, '1백만원 이상', '중소기업은행', 85, 3, 'https://ibk.co.kr', '계좌 수 제한 없으며, 최소 1백만원 이상 통합한도 1억원 이내 가입 가능', 1200, 350, 50, 0.85),
                                                                                                                                                                                                                                           ('D24', 'IBK더굴리기통장(실세금리정기예금)', 3.40, 2.40, '인터넷,스마트폰', NULL, NULL, '실명의 개인\n(개인사업자 제외)', 1, '없음', 12, '1백만원 이상', '중소기업은행', 82, 2, 'https://ibk.co.kr', '계좌 수 제한 없으며, 최소 1백만원 이상 납입한도 제한 없음', 1000, 300, 45, 0.82),
                                                                                                                                                                                                                                           ('D25', 'IBK굴리기통장(정기예금)', 3.30, 2.30, '영업점,스마트폰', NULL, 300000000, '실명의 개인\n(개인사업자 제외)', 1, '없음', 12, '1백만원 이상', '중소기업은행', 80, 3, 'https://ibk.co.kr', '계좌 수 제한 없으며, 최소 1백만원 이상 통합한도 3억원 이내 가입 가능', 950, 280, 40, 0.80),
                                                                                                                                                                                                                                           ('D26', 'KDB 정기예금', 3.60, 2.60, '영업점,인터넷,스마트폰', NULL, NULL, '제한없음', 1, '해당없음', 12, '해당없음', '한국산업은행', 87, 2, 'https://kdb.co.kr', '해당없음', 1300, 380, 55, 0.87),
                                                                                                                                                                                                                                           ('D27', 'KB Star 정기예금', 3.70, 2.70, '인터넷,스마트폰', NULL, NULL, '실명의 개인 또는 개인사업자', 1, '해당무', 12, '1백만원 이상', '국민은행', 89, 2, 'https://kbstar.com', '- 가입금액 : 1백만원 이상', 1400, 400, 60, 0.89),
                                                                                                                                                                                                                                           ('D28', '쏠편한 정기예금', 3.55, 2.55, '인터넷,스마트폰', NULL, NULL, '만14세이상 개인고객', 1, '해당사항없음', 12, '1만원 이상', '신한은행', 86, 3, 'https://shinhan.com', '1. 가입한도 :\n 1만원 이상', 1100, 320, 48, 0.86),
                                                                                                                                                                                                                                           ('D29', 'NH왈츠회전예금 II', 3.80, 2.80, '영업점,인터넷,스마트폰', NULL, NULL, '개인', 1, '1. 급여이체실적(50만원 이상)이 있는 경우 : 0.1%p\n2. 트리플 회전 우대이율 :  4회전기간부터 0.1%p', 12, '10만원 이상', '농협은행주식회사', 91, 2, 'https://nonghyup.com', '※ 기본금리 및 최고 우대금리 항목은 가입기간이 아닌 ‘회전주기’기간별 금리를 공시\n - 회전주기는 1개월 이상 12개월 이내 월단위로 선택 가능\n※ 전월취급평균금리는 본 홈페이지에 공시되지 않는 회전기간 6개월 미만의 계좌들도 포함하여 산출하기 때문에 회전기간별로 공시된 기본금리보다 낮게 보여질 수 있음', 1600, 450, 70, 0.91),
                                                                                                                                                                                                                                           ('D30', 'NH내가Green초록세상예금', 3.90, 2.90, '영업점,인터넷,스마트폰', NULL, NULL, '개인', 1, '※ 우대금리 최대한도 : 0.4%p(연%, 세전)\n1. 온실가스 줄이기 실천서약서 동의 : 0.1%p\n2. 통장미발급 : 0.1%p\n3. 손하나로인증 서비스 등록 : 0.1%p\n4. NH내가Green초록세상적금 동시 보유 : 0.1%p', 12, '300만원이상', '농협은행주식회사', 94, 1, 'https://nonghyup.com', '1. 300만원이상 가입\n2. 온실가스 줄이기 실천서약서 동의시 가입가능\n3. 신규가입 계좌당 2천원씩 녹색환경기금 적립\n※ 자세한 사항은 상품설명서 참조', 1800, 500, 80, 0.94);

select * from forex;
