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
                        `minimum_cost`	VARCHAR(255)	NULL,
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
                        yieldScore FLOAT NULL,
                        risk_score FLOAT NULL,
                        cost_score FLOAT NULL,
                        liquidity_score FLOAT NULL,
                        complexity_score FLOAT NULL,
                        f_sector INT NULL,
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
                           `min_save_term` INT NULL,
                           `max_save_term` INT NULL,
                           yieldScore FLOAT NULL,
                           risk_score FLOAT NULL,
                           cost_score FLOAT NULL,
                           liquidity_score FLOAT NULL,
                           complexity_score FLOAT NULL,
                           f_sector INT NULL,
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
                         yieldScore FLOAT NULL,
                         risk_score FLOAT NULL,
                         cost_score FLOAT NULL,
                         liquidity_score FLOAT NULL,
                         complexity_score FLOAT NULL,
                         f_sector INT NULL,
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
                           yieldScore FLOAT NULL,
                           risk_score FLOAT NULL,
                           cost_score FLOAT NULL,
                           liquidity_score FLOAT NULL,
                           complexity_score FLOAT NULL,
                           f_sector INT NULL,
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
                           min_save_term INT NULL,
                           max_save_term INT NULL,
                           yieldScore FLOAT NULL,
                           risk_score FLOAT NULL,
                           cost_score FLOAT NULL,
                           liquidity_score FLOAT NULL,
                           complexity_score FLOAT NULL,
                           f_sector INT NULL,
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
                         `id`	INT	AUTO_INCREMENT NOT NULL,
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
                         yieldScore FLOAT NULL,
                         risk_score FLOAT NULL,
                         cost_score FLOAT NULL,
                         liquidity_score FLOAT NULL,
                         complexity_score FLOAT NULL,
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


INSERT INTO fund (id, name, rate, fund_price, fund_price_movement, TER, category, theme, investment_warning_grade, upfront_fee, management_fee, minimum_cost, target, investment_type, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code, f_sector) VALUES ('F0001', '교보 고배당 펀드', -2.74, 1170.38, -21.52, 0.32, '국내주식형', '미래기술테마', '5등급', 0.96, 0.39, 50000, '고액 자산가', '적립', '신한자산운용', NULL, NULL, 'https://www.한화.com/fund/5220', '※ 투자 전 투자설명서 및 약관을 반드시 확인하시기 바랍니다.', 8009, 489, 154, NULL,3);
INSERT INTO fund (id, name, rate, fund_price, fund_price_movement, TER, category, theme, investment_warning_grade, upfront_fee, management_fee, minimum_cost, target, investment_type, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code, f_sector) VALUES ('F0002', 'KB 그로스 인덱스 증권자투자신탁', 8.67, 1104.18, 38.71, 1.66, '해외채권형', 'ETF(국내채권)', '5등급', 0.22, 1.05, 10000, '개인 투자자', '거치', '한화자산운용', NULL, NULL, 'https://www.한화.com/fund/9615', '※ 펀드 수익률은 과거의 실적이며, 미래의 수익을 보장하지 않습니다.', 5227, 35, 289, NULL, 3);
INSERT INTO fund (id, name, rate, fund_price, fund_price_movement, TER, category, theme, investment_warning_grade, upfront_fee, management_fee, minimum_cost, target, investment_type, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code, f_sector) VALUES ('F0003', '한화 테크 펀드', 13.73, 1148.28, -20.4, 0.97, '해외주식형', 'ETF(해외주식)', '3등급', 0.89, 1.14, 10000, '개인 투자자', '거치', '미래에셋자산운용', NULL, NULL, 'https://www.교보.com/fund/8749', '※ 투자 전 투자설명서 및 약관을 반드시 확인하시기 바랍니다.', 19157, 185, 412, NULL, 3);
INSERT INTO fund (id, name, rate, fund_price, fund_price_movement, TER, category, theme, investment_warning_grade, upfront_fee, management_fee, minimum_cost, target, investment_type, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code, f_sector) VALUES ('F0004', '신한 ESG 증권자투자신탁', 6.0, 1033.84, 12.34, 1.65, '국내대체', 'ETF(해외주식)', '2등급', 0.4, 0.5, 50000, '고액 자산가', '거치', 'KB자산운용', NULL, NULL, 'https://www.미래에셋.com/fund/4146', '※ 펀드 수익률은 과거의 실적이며, 미래의 수익을 보장하지 않습니다.', 17612, 393, 339, NULL, 3);
INSERT INTO fund (id, name, rate, fund_price, fund_price_movement, TER, category, theme, investment_warning_grade, upfront_fee, management_fee, minimum_cost, target, investment_type, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code, f_sector) VALUES ('F0005', '한화 ESG 증권자투자신탁', 9.59, 1129.31, -17.7, 0.5, '해외채권형', 'ETF(해외주식)', '3등급', 0.16, 0.39, 100000, '고액 자산가', '거치', 'KB자산운용', NULL, NULL, 'https://www.삼성.com/fund/2154', '※ 투자 전 투자설명서 및 약관을 반드시 확인하시기 바랍니다.', 16185, 334, 330, NULL, 3);
INSERT INTO fund (id, name, rate, fund_price, fund_price_movement, TER, category, theme, investment_warning_grade, upfront_fee, management_fee, minimum_cost, target, investment_type, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code, f_sector) VALUES ('F0006', 'KB 글로벌 혁신 펀드', 14.15, 908.17, 48.61, 1.17, '해외주식형', '배당주펀드', '1등급', 0.36, 0.55, 100000, '고액 자산가', '거치', 'KB자산운용', NULL, NULL, 'https://www.교보.com/fund/3853', '※ 펀드 수익률은 과거의 실적이며, 미래의 수익을 보장하지 않습니다.', 12161, 743, 56, NULL, 3);
INSERT INTO fund (id, name, rate, fund_price, fund_price_movement, TER, category, theme, investment_warning_grade, upfront_fee, management_fee, minimum_cost, target, investment_type, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code, f_sector) VALUES ('F0007', '신한 테크 증권자투자신탁', 10.92, 1024.14, 49.76, 1.84, '국내대체', '미래기술테마', '6등급', 0.92, 0.7, 50000, '고액 자산가', '거치', 'KB자산운용', NULL, NULL, 'https://www.삼성.com/fund/3047', '※ 펀드 수익률은 과거의 실적이며, 미래의 수익을 보장하지 않습니다.', 16682, 652, 56, NULL, 3);
INSERT INTO fund (id, name, rate, fund_price, fund_price_movement, TER, category, theme, investment_warning_grade, upfront_fee, management_fee, minimum_cost, target, investment_type, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code, f_sector) VALUES ('F0008', 'KB 성장주 증권자투자신탁', -1.8, 981.53, 49.6, 1.26, '해외주식형', '미래기술테마', '1등급', 0.27, 1.15, 50000, '고액 자산가', '적립', '삼성자산운용', NULL, NULL, 'https://www.nh-amundi.com/fund/5491', '※ 펀드 수익률은 과거의 실적이며, 미래의 수익을 보장하지 않습니다.', 15888, 523, 313, NULL, 3);
INSERT INTO fund (id, name, rate, fund_price, fund_price_movement, TER, category, theme, investment_warning_grade, upfront_fee, management_fee, minimum_cost, target, investment_type, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code, f_sector) VALUES ('F0009', 'NH-Amundi ESG 혼합형펀드', 8.99, 988.01, -6.4, 0.5, 'MMF', 'ETF(해외주식)', '4등급', 0.0, 0.8, 50000, '개인 투자자', '거치', '교보자산운용', NULL, NULL, 'https://www.한화.com/fund/2750', '※ 펀드 수익률은 과거의 실적이며, 미래의 수익을 보장하지 않습니다.', 19365, 32, 269, NULL, 3);
INSERT INTO fund (id, name, rate, fund_price, fund_price_movement, TER, category, theme, investment_warning_grade, upfront_fee, management_fee, minimum_cost, target, investment_type, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code, f_sector) VALUES ('F0010', 'NH-Amundi 코리아 대표 증권자투자신탁', -1.14, 1103.3, 2.5, 0.95, '해외주식형', '배당주펀드', '3등급', 0.92, 1.2, 50000, '개인 투자자', '거치', '미래에셋자산운용', NULL, NULL, 'https://www.kb.com/fund/9900', '※ 펀드 수익률은 과거의 실적이며, 미래의 수익을 보장하지 않습니다.', 7138, 443, 47, NULL, 3);
INSERT INTO fund (id, name, rate, fund_price, fund_price_movement, TER, category, theme, investment_warning_grade, upfront_fee, management_fee, minimum_cost, target, investment_type, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code, f_sector) VALUES ('F0011', '교보 테크 증권자투자신탁', 6.0, 1017.95, -26.27, 0.77, '해외채권형', 'ETF(해외주식)', '2등급', 0.15, 0.42, 50000, '개인 투자자', '적립', 'NH-Amundi자산운용', NULL, NULL, 'https://www.kb.com/fund/3365', '※ 펀드 수익률은 과거의 실적이며, 미래의 수익을 보장하지 않습니다.', 12709, 735, 242, NULL, 3);
INSERT INTO fund (id, name, rate, fund_price, fund_price_movement, TER, category, theme, investment_warning_grade, upfront_fee, management_fee, minimum_cost, target, investment_type, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code, f_sector) VALUES ('F0012', '신한 고배당 혼합형펀드', -2.27, 1055.67, 39.92, 1.67, '해외채권형', '미래기술테마', '6등급', 0.52, 0.74, 50000, '고액 자산가', '적립', 'KB자산운용', NULL, NULL, 'https://www.미래에셋.com/fund/8574', '※ 펀드 수익률은 과거의 실적이며, 미래의 수익을 보장하지 않습니다.', 10695, 232, 276, NULL, 3);
INSERT INTO fund (id, name, rate, fund_price, fund_price_movement, TER, category, theme, investment_warning_grade, upfront_fee, management_fee, minimum_cost, target, investment_type, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code, f_sector) VALUES ('F0013', '삼성 고배당 펀드', 8.35, 1053.63, 42.76, 1.37, 'MMF', 'ETF(국내채권)', '3등급', 0.46, 0.88, 10000, '고액 자산가', '거치', '신한자산운용', NULL, NULL, 'https://www.신한.com/fund/8173', '※ 투자 전 투자설명서 및 약관을 반드시 확인하시기 바랍니다.', 14900, 454, 447, NULL, 3);
INSERT INTO fund (id, name, rate, fund_price, fund_price_movement, TER, category, theme, investment_warning_grade, upfront_fee, management_fee, minimum_cost, target, investment_type, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code, f_sector) VALUES ('F0014', '신한 성장주 혼합형펀드', 6.34, 1150.84, 49.84, 0.98, 'MMF', 'ETF(국내채권)', '6등급', 0.43, 1.14, 100000, '고액 자산가', '적립', 'KB자산운용', NULL, NULL, 'https://www.교보.com/fund/8886', '※ 펀드 수익률은 과거의 실적이며, 미래의 수익을 보장하지 않습니다.', 13871, 575, 311, NULL, 3);
INSERT INTO fund (id, name, rate, fund_price, fund_price_movement, TER, category, theme, investment_warning_grade, upfront_fee, management_fee, minimum_cost, target, investment_type, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code, f_sector) VALUES ('F0015', 'NH-Amundi 고정수익 혼합형펀드', 1.39, 1130.61, 36.73, 1.78, '해외주식형', 'ETF(해외주식)', '2등급', 0.62, 0.88, 100000, '개인 투자자', '적립', '삼성자산운용', NULL, NULL, 'https://www.미래에셋.com/fund/4850', '※ 펀드 수익률은 과거의 실적이며, 미래의 수익을 보장하지 않습니다.', 3201, 163, 210, NULL, 3);
INSERT INTO fund (id, name, rate, fund_price, fund_price_movement, TER, category, theme, investment_warning_grade, upfront_fee, management_fee, minimum_cost, target, investment_type, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code, f_sector) VALUES ('F0016', '신한 테크 증권자투자신탁', 8.81, 1167.7, -6.26, 2.0, '국내대체', '배당주펀드', '4등급', 0.69, 0.63, 10000, '고액 자산가', '거치', '삼성자산운용', NULL, NULL, 'https://www.nh-amundi.com/fund/7913', '※ 펀드 수익률은 과거의 실적이며, 미래의 수익을 보장하지 않습니다.', 6641, 464, 499, NULL, 3);
INSERT INTO fund (id, name, rate, fund_price, fund_price_movement, TER, category, theme, investment_warning_grade, upfront_fee, management_fee, minimum_cost, target, investment_type, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code, f_sector) VALUES ('F0017', '삼성 그로스 인덱스 증권자투자신탁', 0.47, 1133.35, 36.7, 1.53, '국내주식형', '배당주펀드', '6등급', 0.32, 0.82, 100000, '개인 투자자', '거치', 'NH-Amundi자산운용', NULL, NULL, 'https://www.nh-amundi.com/fund/6322', '※ 이 상품은 원금 손실이 발생할 수 있으며, 예금자 보호법에 의해 보호되지 않습니다.', 825, 52, 90, NULL, 3);
INSERT INTO fund (id, name, rate, fund_price, fund_price_movement, TER, category, theme, investment_warning_grade, upfront_fee, management_fee, minimum_cost, target, investment_type, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code, f_sector) VALUES ('F0018', '삼성 글로벌 혁신 펀드', 4.28, 1222.46, 5.53, 0.45, '해외채권형', '미래기술테마', '5등급', 0.0, 0.17, 100000, '고액 자산가', '거치', '교보자산운용', NULL, NULL, 'https://www.교보.com/fund/9575', '※ 펀드 수익률은 과거의 실적이며, 미래의 수익을 보장하지 않습니다.', 4579, 559, 450, NULL, 3);
INSERT INTO fund (id, name, rate, fund_price, fund_price_movement, TER, category, theme, investment_warning_grade, upfront_fee, management_fee, minimum_cost, target, investment_type, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code, f_sector) VALUES ('F0019', '신한 그로스 인덱스 펀드', 3.32, 1049.09, 19.55, 1.39, 'MMF', '미래기술테마', '5등급', 0.56, 0.73, 100000, '고액 자산가', '적립', '신한자산운용', NULL, NULL, 'https://www.한화.com/fund/7628', '※ 펀드 수익률은 과거의 실적이며, 미래의 수익을 보장하지 않습니다.', 4352, 926, 381, NULL, 3);
INSERT INTO fund (id, name, rate, fund_price, fund_price_movement, TER, category, theme, investment_warning_grade, upfront_fee, management_fee, minimum_cost, target, investment_type, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code, f_sector) VALUES ('F0020', '신한 성장주 증권자투자신탁', 10.42, 1256.72, 2.57, 0.41, '국내대체', 'ETF(해외주식)', '3등급', 0.93, 0.18, 100000, '고액 자산가', '적립', '교보자산운용', NULL, NULL, 'https://www.nh-amundi.com/fund/9878', '※ 이 상품은 원금 손실이 발생할 수 있으며, 예금자 보호법에 의해 보호되지 않습니다.', 1594, 962, 345, NULL, 3);
INSERT INTO fund (id, name, rate, fund_price, fund_price_movement, TER, category, theme, investment_warning_grade, upfront_fee, management_fee, minimum_cost, target, investment_type, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code, f_sector) VALUES ('F0021', '신한 중소형주 혼합형펀드', -1.5, 1082.87, -3.73, 1.25, '해외채권형', 'ETF(국내채권)', '6등급', 0.76, 0.76, 100000, '고액 자산가', '적립', 'KB자산운용', NULL, NULL, 'https://www.삼성.com/fund/9874', '※ 펀드 수익률은 과거의 실적이며, 미래의 수익을 보장하지 않습니다.', 6651, 770, 297, NULL, 3);
INSERT INTO fund (id, name, rate, fund_price, fund_price_movement, TER, category, theme, investment_warning_grade, upfront_fee, management_fee, minimum_cost, target, investment_type, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code, f_sector) VALUES ('F0022', '한화 친환경 펀드', 10.49, 1131.61, 33.8, 0.45, '해외채권형', 'ETF(해외주식)', '3등급', 0.49, 1.06, 10000, '고액 자산가', '적립', '한화자산운용', NULL, NULL, 'https://www.미래에셋.com/fund/6009', '※ 투자 전 투자설명서 및 약관을 반드시 확인하시기 바랍니다.', 7229, 538, 149, NULL, 3);
INSERT INTO fund (id, name, rate, fund_price, fund_price_movement, TER, category, theme, investment_warning_grade, upfront_fee, management_fee, minimum_cost, target, investment_type, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code, f_sector) VALUES ('F0023', '삼성 고배당 증권자투자신탁', 13.5, 1098.98, 20.42, 1.17, 'MMF', 'ETF(해외주식)', '5등급', 0.34, 0.47, 50000, '개인 투자자', '적립', '교보자산운용', NULL, NULL, 'https://www.nh-amundi.com/fund/6698', '※ 투자 전 투자설명서 및 약관을 반드시 확인하시기 바랍니다.', 10859, 85, 149, NULL, 3);
INSERT INTO fund (id, name, rate, fund_price, fund_price_movement, TER, category, theme, investment_warning_grade, upfront_fee, management_fee, minimum_cost, target, investment_type, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code, f_sector) VALUES ('F0024', '교보 테크 증권자투자신탁', 1.55, 988.23, 17.57, 0.38, '해외주식형', 'ETF(해외주식)', '2등급', 0.78, 0.84, 100000, '고액 자산가', '적립', '삼성자산운용', NULL, NULL, 'https://www.nh-amundi.com/fund/1750', '※ 펀드 수익률은 과거의 실적이며, 미래의 수익을 보장하지 않습니다.', 1822, 913, 93, NULL, 3);
INSERT INTO fund (id, name, rate, fund_price, fund_price_movement, TER, category, theme, investment_warning_grade, upfront_fee, management_fee, minimum_cost, target, investment_type, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code, f_sector) VALUES ('F0025', '한화 성장주 펀드', 12.43, 1184.07, -23.22, 1.07, 'MMF', '배당주펀드', '1등급', 0.8, 0.22, 100000, '개인 투자자', '적립', '교보자산운용', NULL, NULL, 'https://www.교보.com/fund/2343', '※ 이 상품은 원금 손실이 발생할 수 있으며, 예금자 보호법에 의해 보호되지 않습니다.', 186, 464, 248, NULL, 3);
INSERT INTO fund (id, name, rate, fund_price, fund_price_movement, TER, category, theme, investment_warning_grade, upfront_fee, management_fee, minimum_cost, target, investment_type, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code, f_sector) VALUES ('F0026', 'NH-Amundi 글로벌 혁신 펀드', 12.02, 1294.59, 9.83, 1.51, '국내주식형', 'ETF(해외주식)', '4등급', 0.07, 1.05, 100000, '개인 투자자', '적립', '삼성자산운용', NULL, NULL, 'https://www.신한.com/fund/2471', '※ 투자 전 투자설명서 및 약관을 반드시 확인하시기 바랍니다.', 4166, 584, 211, NULL, 3);
INSERT INTO fund (id, name, rate, fund_price, fund_price_movement, TER, category, theme, investment_warning_grade, upfront_fee, management_fee, minimum_cost, target, investment_type, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code, f_sector) VALUES ('F0027', 'KB 고배당 혼합형펀드', 11.25, 969.4, 13.77, 0.92, 'MMF', '미래기술테마', '5등급', 0.94, 0.22, 50000, '고액 자산가', '거치', 'KB자산운용', NULL, NULL, 'https://www.nh-amundi.com/fund/8584', '※ 이 상품은 원금 손실이 발생할 수 있으며, 예금자 보호법에 의해 보호되지 않습니다.', 16363, 132, 369, NULL, 3);
INSERT INTO fund (id, name, rate, fund_price, fund_price_movement, TER, category, theme, investment_warning_grade, upfront_fee, management_fee, minimum_cost, target, investment_type, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code, f_sector) VALUES ('F0028', 'NH-Amundi 성장주 혼합형펀드', 11.91, 944.86, 25.19, 0.72, '국내대체', '미래기술테마', '5등급', 0.43, 0.81, 10000, '고액 자산가', '적립', '한화자산운용', NULL, NULL, 'https://www.한화.com/fund/4359', '※ 투자 전 투자설명서 및 약관을 반드시 확인하시기 바랍니다.', 12675, 909, 58, NULL, 3);
INSERT INTO fund (id, name, rate, fund_price, fund_price_movement, TER, category, theme, investment_warning_grade, upfront_fee, management_fee, minimum_cost, target, investment_type, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code, f_sector) VALUES ('F0029', 'KB 테크 증권자투자신탁', 12.03, 1084.21, -19.18, 1.89, 'MMF', 'ETF(국내채권)', '2등급', 0.77, 0.96, 50000, '개인 투자자', '적립', '교보자산운용', NULL, NULL, 'https://www.신한.com/fund/5606', '※ 투자 전 투자설명서 및 약관을 반드시 확인하시기 바랍니다.', 19606, 686, 451, NULL, 3);
INSERT INTO fund (id, name, rate, fund_price, fund_price_movement, TER, category, theme, investment_warning_grade, upfront_fee, management_fee, minimum_cost, target, investment_type, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code, f_sector) VALUES ('F0030', '삼성 성장주 증권자투자신탁', 2.65, 949.63, 36.08, 1.07, '해외주식형', 'ETF(국내채권)', '6등급', 0.51, 1.06, 50000, '개인 투자자', '적립', '교보자산운용', NULL, NULL, 'https://www.미래에셋.com/fund/8705', '※ 이 상품은 원금 손실이 발생할 수 있으며, 예금자 보호법에 의해 보호되지 않습니다.', 3985, 229, 65, NULL, 3);
INSERT INTO fund (id, name, rate, fund_price, fund_price_movement, TER, category, theme, investment_warning_grade, upfront_fee, management_fee, minimum_cost, target, investment_type, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code, f_sector) VALUES ('F0031', '한화 글로벌 혁신 혼합형펀드', 7.62, 1077.96, 27.73, 0.82, '국내대체', 'ETF(국내채권)', '5등급', 0.37, 0.69, 50000, '개인 투자자', '거치', '삼성자산운용', NULL, NULL, 'https://www.kb.com/fund/5599', '※ 펀드 수익률은 과거의 실적이며, 미래의 수익을 보장하지 않습니다.', 11937, 894, 281, NULL, 3);
INSERT INTO fund (id, name, rate, fund_price, fund_price_movement, TER, category, theme, investment_warning_grade, upfront_fee, management_fee, minimum_cost, target, investment_type, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code, f_sector) VALUES ('F0032', '삼성 고정수익 혼합형펀드', 9.48, 1202.51, 49.22, 1.51, '국내주식형', '미래기술테마', '3등급', 0.95, 0.59, 10000, '개인 투자자', '적립', 'KB자산운용', NULL, NULL, 'https://www.신한.com/fund/7764', '※ 이 상품은 원금 손실이 발생할 수 있으며, 예금자 보호법에 의해 보호되지 않습니다.', 4917, 477, 413, NULL, 3);
INSERT INTO fund (id, name, rate, fund_price, fund_price_movement, TER, category, theme, investment_warning_grade, upfront_fee, management_fee, minimum_cost, target, investment_type, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code, f_sector) VALUES ('F0033', '삼성 글로벌 혁신 펀드', -1.32, 1270.77, -15.12, 1.9, 'MMF', '미래기술테마', '5등급', 0.25, 1.0, 50000, '고액 자산가', '적립', 'KB자산운용', NULL, NULL, 'https://www.kb.com/fund/8072', '※ 이 상품은 원금 손실이 발생할 수 있으며, 예금자 보호법에 의해 보호되지 않습니다.', 1685, 162, 192, NULL, 3);
INSERT INTO fund (id, name, rate, fund_price, fund_price_movement, TER, category, theme, investment_warning_grade, upfront_fee, management_fee, minimum_cost, target, investment_type, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code, f_sector) VALUES ('F0034', 'NH-Amundi 친환경 증권자투자신탁', 13.62, 1213.77, 6.85, 1.05, 'MMF', '배당주펀드', '3등급', 0.77, 1.04, 50000, '고액 자산가', '적립', '미래에셋자산운용', NULL, NULL, 'https://www.kb.com/fund/9243', '※ 이 상품은 원금 손실이 발생할 수 있으며, 예금자 보호법에 의해 보호되지 않습니다.', 11003, 791, 322, NULL, 3);
INSERT INTO fund (id, name, rate, fund_price, fund_price_movement, TER, category, theme, investment_warning_grade, upfront_fee, management_fee, minimum_cost, target, investment_type, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code, f_sector) VALUES ('F0035', 'NH-Amundi 고배당 펀드', 14.4, 943.28, 25.75, 0.34, '해외채권형', '미래기술테마', '2등급', 0.38, 0.45, 100000, '개인 투자자', '적립', 'NH-Amundi자산운용', NULL, NULL, 'https://www.신한.com/fund/3951', '※ 투자 전 투자설명서 및 약관을 반드시 확인하시기 바랍니다.', 14247, 358, 445, NULL, 3);
INSERT INTO fund (id, name, rate, fund_price, fund_price_movement, TER, category, theme, investment_warning_grade, upfront_fee, management_fee, minimum_cost, target, investment_type, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code, f_sector) VALUES ('F0036', 'KB 고정수익 펀드', -0.35, 910.16, 20.4, 1.59, '해외채권형', '미래기술테마', '6등급', 0.39, 0.36, 50000, '개인 투자자', '거치', '삼성자산운용', NULL, NULL, 'https://www.nh-amundi.com/fund/1924', '※ 이 상품은 원금 손실이 발생할 수 있으며, 예금자 보호법에 의해 보호되지 않습니다.', 19827, 758, 48, NULL, 3);
INSERT INTO fund (id, name, rate, fund_price, fund_price_movement, TER, category, theme, investment_warning_grade, upfront_fee, management_fee, minimum_cost, target, investment_type, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code, f_sector) VALUES ('F0037', 'NH-Amundi 고정수익 혼합형펀드', 14.1, 1295.06, 41.36, 0.63, '국내주식형', 'ETF(해외주식)', '1등급', 0.15, 0.91, 100000, '개인 투자자', '적립', '신한자산운용', NULL, NULL, 'https://www.삼성.com/fund/7318', '※ 펀드 수익률은 과거의 실적이며, 미래의 수익을 보장하지 않습니다.', 8154, 912, 123, NULL, 3);
INSERT INTO fund (id, name, rate, fund_price, fund_price_movement, TER, category, theme, investment_warning_grade, upfront_fee, management_fee, minimum_cost, target, investment_type, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code, f_sector) VALUES ('F0038', '교보 테크 증권자투자신탁', 4.6, 1087.98, 27.18, 0.61, '해외채권형', 'ETF(해외주식)', '3등급', 0.68, 0.29, 100000, '고액 자산가', '거치', '교보자산운용', NULL, NULL, 'https://www.삼성.com/fund/1471', '※ 펀드 수익률은 과거의 실적이며, 미래의 수익을 보장하지 않습니다.', 18122, 744, 208, NULL, 3);
INSERT INTO fund (id, name, rate, fund_price, fund_price_movement, TER, category, theme, investment_warning_grade, upfront_fee, management_fee, minimum_cost, target, investment_type, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code, f_sector) VALUES ('F0039', '한화 고배당 펀드', 8.91, 1052.13, 14.31, 0.71, '국내주식형', 'ETF(해외주식)', '2등급', 0.21, 0.79, 50000, '개인 투자자', '적립', '교보자산운용', NULL, NULL, 'https://www.삼성.com/fund/8255', '※ 펀드 수익률은 과거의 실적이며, 미래의 수익을 보장하지 않습니다.', 1987, 926, 430, NULL, 3);
INSERT INTO fund (id, name, rate, fund_price, fund_price_movement, TER, category, theme, investment_warning_grade, upfront_fee, management_fee, minimum_cost, target, investment_type, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code, f_sector) VALUES ('F0040', '한화 성장주 혼합형펀드', 9.8, 1112.93, 7.55, 1.52, '해외주식형', 'ETF(국내채권)', '1등급', 0.61, 0.88, 50000, '고액 자산가', '적립', '교보자산운용', NULL, NULL, 'https://www.신한.com/fund/6907', '※ 투자 전 투자설명서 및 약관을 반드시 확인하시기 바랍니다.', 12957, 787, 258, NULL, 3);
INSERT INTO fund (id, name, rate, fund_price, fund_price_movement, TER, category, theme, investment_warning_grade, upfront_fee, management_fee, minimum_cost, target, investment_type, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code, f_sector) VALUES ('F0041', '미래에셋 고정수익 펀드', -0.42, 1262.46, -2.34, 1.16, '해외주식형', '배당주펀드', '1등급', 0.75, 1.0, 10000, '고액 자산가', '거치', '교보자산운용', NULL, NULL, 'https://www.nh-amundi.com/fund/2698', '※ 투자 전 투자설명서 및 약관을 반드시 확인하시기 바랍니다.', 7529, 340, 214, NULL, 3);
INSERT INTO fund (id, name, rate, fund_price, fund_price_movement, TER, category, theme, investment_warning_grade, upfront_fee, management_fee, minimum_cost, target, investment_type, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code, f_sector) VALUES ('F0042', 'KB 글로벌 혁신 증권자투자신탁', 1.33, 1223.17, 10.65, 1.34, '국내대체', 'ETF(국내채권)', '4등급', 0.71, 0.48, 50000, '개인 투자자', '거치', '한화자산운용', NULL, NULL, 'https://www.신한.com/fund/7587', '※ 투자 전 투자설명서 및 약관을 반드시 확인하시기 바랍니다.', 14982, 288, 246, NULL, 3);
INSERT INTO fund (id, name, rate, fund_price, fund_price_movement, TER, category, theme, investment_warning_grade, upfront_fee, management_fee, minimum_cost, target, investment_type, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code, f_sector) VALUES ('F0043', '교보 코리아 대표 혼합형펀드', 11.24, 1076.81, -26.27, 0.62, '해외채권형', '미래기술테마', '3등급', 0.46, 0.18, 50000, '개인 투자자', '적립', '한화자산운용', NULL, NULL, 'https://www.한화.com/fund/7317', '※ 이 상품은 원금 손실이 발생할 수 있으며, 예금자 보호법에 의해 보호되지 않습니다.', 3226, 6, 361, NULL, 3);
INSERT INTO fund (id, name, rate, fund_price, fund_price_movement, TER, category, theme, investment_warning_grade, upfront_fee, management_fee, minimum_cost, target, investment_type, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code, f_sector) VALUES ('F0044', '미래에셋 코리아 대표 증권자투자신탁', 12.16, 981.45, 1.98, 1.4, '해외채권형', '미래기술테마', '5등급', 0.39, 0.59, 10000, '고액 자산가', '거치', 'KB자산운용', NULL, NULL, 'https://www.한화.com/fund/2130', '※ 투자 전 투자설명서 및 약관을 반드시 확인하시기 바랍니다.', 17157, 316, 50, NULL, 3);
INSERT INTO fund (id, name, rate, fund_price, fund_price_movement, TER, category, theme, investment_warning_grade, upfront_fee, management_fee, minimum_cost, target, investment_type, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code, f_sector) VALUES ('F0045', '미래에셋 코리아 대표 증권자투자신탁', 2.66, 917.97, 48.97, 0.8, '국내대체', '배당주펀드', '4등급', 0.97, 0.33, 50000, '고액 자산가', '거치', '미래에셋자산운용', NULL, NULL, 'https://www.신한.com/fund/1924', '※ 이 상품은 원금 손실이 발생할 수 있으며, 예금자 보호법에 의해 보호되지 않습니다.', 10056, 581, 281, NULL, 3);
INSERT INTO fund (id, name, rate, fund_price, fund_price_movement, TER, category, theme, investment_warning_grade, upfront_fee, management_fee, minimum_cost, target, investment_type, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code, f_sector) VALUES ('F0046', '교보 그로스 인덱스 펀드', 11.05, 1148.07, -0.92, 0.31, '해외주식형', '미래기술테마', '5등급', 0.76, 1.13, 100000, '고액 자산가', '거치', '삼성자산운용', NULL, NULL, 'https://www.교보.com/fund/4504', '※ 펀드 수익률은 과거의 실적이며, 미래의 수익을 보장하지 않습니다.', 8488, 548, 300, NULL, 3);
INSERT INTO fund (id, name, rate, fund_price, fund_price_movement, TER, category, theme, investment_warning_grade, upfront_fee, management_fee, minimum_cost, target, investment_type, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code, f_sector) VALUES ('F0047', 'NH-Amundi 고배당 증권자투자신탁', 10.62, 907.22, 19.99, 1.37, 'MMF', '미래기술테마', '4등급', 0.11, 0.35, 10000, '개인 투자자', '적립', '교보자산운용', NULL, NULL, 'https://www.신한.com/fund/2534', '※ 펀드 수익률은 과거의 실적이며, 미래의 수익을 보장하지 않습니다.', 12375, 333, 206, NULL, 3);
INSERT INTO fund (id, name, rate, fund_price, fund_price_movement, TER, category, theme, investment_warning_grade, upfront_fee, management_fee, minimum_cost, target, investment_type, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code, f_sector) VALUES ('F0048', '교보 글로벌 혁신 혼합형펀드', 7.03, 1161.26, 48.62, 1.79, '국내주식형', 'ETF(해외주식)', '4등급', 0.57, 0.27, 10000, '고액 자산가', '거치', '한화자산운용', NULL, NULL, 'https://www.kb.com/fund/5848', '※ 이 상품은 원금 손실이 발생할 수 있으며, 예금자 보호법에 의해 보호되지 않습니다.', 13607, 296, 124, NULL, 3);
INSERT INTO fund (id, name, rate, fund_price, fund_price_movement, TER, category, theme, investment_warning_grade, upfront_fee, management_fee, minimum_cost, target, investment_type, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code, f_sector) VALUES ('F0049', '미래에셋 고정수익 혼합형펀드', 3.2, 1232.27, 46.55, 1.16, '국내주식형', 'ETF(해외주식)', '3등급', 0.54, 0.55, 10000, '고액 자산가', '거치', '미래에셋자산운용', NULL, NULL, 'https://www.삼성.com/fund/7772', '※ 펀드 수익률은 과거의 실적이며, 미래의 수익을 보장하지 않습니다.', 12903, 455, 166, NULL, 3);

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

INSERT INTO deposit (id, name, rate, prime_rate, join_way, end_date, max_limit, join_member, join_deny, bonus_condition, save_term, minimum_cost, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code, min_save_term, max_save_term) VALUES ('D1', 'WON플러스예금', 2.5, 3.5, '인터넷,스마트폰,전화(텔레뱅킹)', NULL, NULL, '실명의 개인', 1, '해당사항 없음', 12, 10000, '우리은행', 85, 3, 'https://wooribank.com', '- 가입기간: 1~36개월
- 최소가입금액: 1만원 이상
- 만기일을 일,월 단위로 자유롭게 선택 가능
- 만기해지 시 신규일 당시 영업점과 인터넷 홈페이지에 고시된 계약기간별 금리 적용', 1200, 350, 50, 0.85, 1, 36);
INSERT INTO deposit (id, name, rate, prime_rate, join_way, end_date, max_limit, join_member, join_deny, bonus_condition, save_term, minimum_cost, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code, min_save_term, max_save_term) VALUES ('D10', '미즈월복리정기예금', 2.45, 3.45, '영업점,인터넷,스마트폰,기타', NULL, 50000000.0, '만18세이상 여성으로 실명의 개인 및 개인사업자', 1, '▶ 최고우대금리 0.2%
 ① 요구불평잔 : 0.2% -300만원이상 0.1%, 500만원이상 0.2%
 ② 신용(체크)카드결제실적 : 0.1% -전월결제금 300만원이상 0.05%, 500만원이상 0.1%', 12, 5000000, '광주은행', 84, 3, 'https://kjbank.com', '1. 가입기간 : 1년이상 3년제
2. 가입금액 : 5백만원이상 최고 50백만원', 1000, 300, 42, 0.84, 12, 36);
INSERT INTO deposit (id, name, rate, prime_rate, join_way, end_date, max_limit, join_member, join_deny, bonus_condition, save_term, minimum_cost, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code, min_save_term, max_save_term) VALUES ('D11', '스마트모아Dream정기예금', 2.35, 3.35, '인터넷,스마트폰', NULL, NULL, '개인 및 개인사업자', 1, '▶ 1천만원 이상 가입시 최대 0.2%우대', 12, 1000000, '광주은행', 81, 2, 'https://kjbank.com', '1. 가입기간 : 1개월이상 3년제
2. 최소가입금액 : 100만원이상', 980, 290, 40, 0.81, 1, 36);
INSERT INTO deposit (id, name, rate, prime_rate, join_way, end_date, max_limit, join_member, join_deny, bonus_condition, save_term, minimum_cost, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code, min_save_term, max_save_term) VALUES ('D12', '굿스타트예금', 3.0, 4.0, '스마트폰', NULL, 100000000.0, '개인 및 개인사업자', 1, '▶ 최고우대금리 0.5%
 ① 첫예금거래 : 0.4% -최근1년동안 정기예금 계좌 신규 또는 해지이력이 없는경우
 ② 개인(신용)정보 수집이용동의 : 0.1% -만기일전일까지 유지시', 12, 1000000, '광주은행', 95, 1, 'https://kjbank.com', '1. 가입기간 : 1년제
2. 가입금액 : 1백만원이상 최고 1억원(1인1계좌)', 1800, 500, 80, 0.95, 12, 12);
INSERT INTO deposit (id, name, rate, prime_rate, join_way, end_date, max_limit, join_member, join_deny, bonus_condition, save_term, minimum_cost, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code, min_save_term, max_save_term) VALUES ('D13', 'The플러스예금', 2.65, 3.65, '영업점,스마트폰', NULL, 1000000000.0, '개인 및 법인(단,국가 지자체 및 금융기관 제외)', 1, '▶ 해당사항없음', 6, 1000000, '광주은행', 89, 3, 'https://kjbank.com', '1. 가입기간 : 3개월,6개월,1년제
2. 가입금액 : 10백만원이상 고객당 10억원한도', 1100, 320, 47, 0.89, 3, 12);
INSERT INTO deposit (id, name, rate, prime_rate, join_way, end_date, max_limit, join_member, join_deny, bonus_condition, save_term, minimum_cost, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code, min_save_term, max_save_term) VALUES ('D14', '제주Dream
정기예금
(개인/만기
지급식)', 2.25, 3.25, '영업점,인터넷,스마트폰', NULL, NULL, '제한없음', 1, '최고 연 0.1%p(항목별 0.1%p)
①급여이체
②적립식예금 잔액 10만원 이상 보유
③탑스, 주거래 고객
④결제계좌(가맹점) 전월 입금액 10만원 이상
⑤비과세종합저축 대상 고객
⑥다자녀(3인이상 자녀)가정
⑦탐나는 J연금통장 가입고객
⑧국민연금안심통장 가입고객
⑨공무원연금안심통장 가입고객', 12, 1000000, '제주은행', 79, 4, 'https://jejuebank.co.kr', '가입금액 : 1백만원 이상', 800, 240, 32, 0.79, 12, 12);
INSERT INTO deposit (id, name, rate, prime_rate, join_way, end_date, max_limit, join_member, join_deny, bonus_condition, save_term, minimum_cost, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code, min_save_term, max_save_term) VALUES ('D15', 'J정기예금
(만기지급식)', 2.75, 3.75, '영업점,인터넷,스마트폰', NULL, NULL, '실명의
개인 및
개인사업자', 1, '- 아래의 우대요건 충족시 최고 0.5%p 추가 우대
①비대면 채널 가입시 0.3%제공(신규시제공)
(단, 이벤트시 디지털 채널에 고시한 우대금리를 추가 적용할 수 있음)
②신규일로부터 만기달 제외한 계약기간의 1/2이상 매월 Jbank로그인 시 0.2%제공(만기시제공)', 12, 300000, '제주은행', 91, 2, 'https://jejuebank.co.kr', '가입금액 : 30만원 이상', 1400, 410, 65, 0.91, 12, 12);
INSERT INTO deposit (id, name, rate, prime_rate, join_way, end_date, max_limit, join_member, join_deny, bonus_condition, save_term, minimum_cost, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code, min_save_term, max_save_term) VALUES ('D16', '스마일드림
정기예금
(개인/선이자
지급식)', 2.15, 3.15, '영업점,스마트폰', NULL, NULL, '실명의
개인 및
개인사업자', 1, '-아래의 우대요건 충족시 최고0.3% 추가우대(신규시제공)
①김만덕나눔적금 보유 또는 김만적 나눔적금 만기 해지고객 0.2%우대(가입시제공)
②예금가입시 탐나는전 체크카드 보유고객 0.1%우대(가입시제공)
(단, 이벤트시 영업점,디지털채널에 고시한 우대금리를 추가 적용할 수 있음)', 12, 1000000, '제주은행', 77, 3, 'https://jejuebank.co.kr', '가입금액 : 1백만원 이상', 750, 220, 30, 0.77, 12, 12);
INSERT INTO deposit (id, name, rate, prime_rate, join_way, end_date, max_limit, join_member, join_deny, bonus_condition, save_term, minimum_cost, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code, min_save_term, max_save_term) VALUES ('D17', 'JB 다이렉트예금통장
(만기일시지급식)', 2.85, 3.85, '인터넷,스마트폰', NULL, 1000000000.0, '실명의 개인(임의단체 제외', 1, '우대조건
없음', 12, 1000000, '전북은행', 93, 2, 'https://jbbank.co.kr', '가입금액 1계좌당 1백만원이상 10억원이하,
1인당  총 10억원 이하,
인터넷/스마트폰뱅킹 가입상품', 1700, 480, 75, 0.93, 12, 12);
INSERT INTO deposit (id, name, rate, prime_rate, join_way, end_date, max_limit, join_member, join_deny, bonus_condition, save_term, minimum_cost, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code, min_save_term, max_save_term) VALUES ('D18', 'JB 123 정기예금
 (만기일시지급식)', 2.95, 3.95, '인터넷,스마트폰', NULL, 500000000.0, '실명의 개인 또는 개인사업자 (1인 다계좌 가입 가능함)', 1, '자동재예치 우대이율
1회차 0.1%,
2회차 0.2%,
3회차 0.3%', 12, 1000000, '전북은행', 94, 1, 'https://jbbank.co.kr', '예금의 신규 : 인터넷뱅킹, 모바일뱅킹, 모바일웹, BDT
예금의 해지 : 인터넷뱅킹, 모바일뱅킹, 영업점
가입금액 최저 1백만원이상 고객별 5억원 이상 (다만 자동재예치시 이자 원가로 인한 5억원 초과는 가능), 계좌수 관계없이 가입가능', 1900, 520, 85, 0.94, 12, 12);
INSERT INTO deposit (id, name, rate, prime_rate, join_way, end_date, max_limit, join_member, join_deny, bonus_condition, save_term, minimum_cost, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code, min_save_term, max_save_term) VALUES ('D19', '내맘 쏙 정기예금', 2.7, 3.7, '인터넷,스마트폰', NULL, 999999999999.0, '실명의 개인 및 개인사업자', 1, '1. 모바일뱅킹 첫거래 고객 0.10%
2. 마케팅동의고객 0.10%
3. 입출금계좌 이용고객 0.10%', 12, 100000, '전북은행', 90, 2, 'https://jbbank.co.kr', '가입금액 : 계좌당 10만원 이상
예금의 신규 : 모바일뱅킹, 모바일Web
예금의 해지 : 모바일뱅킹, 인터넷뱅킹, 영업점
계약기간 : 1개월이상 12개월이내(월단위)', 1550, 430, 68, 0.9, 1, 12);
INSERT INTO deposit (id, name, rate, prime_rate, join_way, end_date, max_limit, join_member, join_deny, bonus_condition, save_term, minimum_cost, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code, min_save_term, max_save_term) VALUES ('D2', 'e-그린세이브예금', 2.8, 3.8, '인터넷,스마트폰', '9999-12-31', 1000000000.0, '개인(개인사업자 포함)', 1, '1.SC제일은행 최초 거래 신규고객에 대하여 우대 이율을 제공함 (보너스이율0.2%)                     2.SC제일마이백통장에서 출금하여 이 예금을 신규하는경우에 보너스이율을 제공함
(가입기간:1년제/ 보너스이율:0.1% / 만기해약하는 경우에 한해 보너스이율을 적용함)', 24, 1000000, '한국스탠다드차타드은행', 90, 2, 'https://standardchartered.co.kr', '디지털채널 전용상품 (인터넷, 모바일뱅킹)', 1500, 400, 60, 0.9, 12, 24);
INSERT INTO deposit (id, name, rate, prime_rate, join_way, end_date, max_limit, join_member, join_deny, bonus_condition, save_term, minimum_cost, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code, min_save_term, max_save_term) VALUES ('D20', 'BNK더조은정기예금', 2.6, 3.6, '인터넷,스마트폰', '9999-12-31', 500000000.0, '거래대상자는 제한을 두지 아니한다. 다만, 국가 및 지방자치단체는 이 예금을 거래할 수 없다.', 1, '①가입금액 20백만원 이상인 경우 0.20%
②이 예금 신규시 금리우대쿠폰 등록할 경우 0.20%
③ 경남은행 오픈뱅킹 서비스에 가입되어 있는 경우
(만기시까지 해당서비스 유지하는 경우) 0.10%
④ 자동재예치 신청 0.05%
(단 금리우대쿠폰과 중복적용 불가)', 12, 1000000, '경남은행', 88, 3, 'https://knbank.co.kr', '1. 이 예금의 계약기간은 3개월 이상 2년 이내 월단위로 한다.
2. 가입금액은 1인당 최소 100만원 이상 5억원 이하이다.', 1250, 360, 52, 0.88, 3, 24);
INSERT INTO deposit (id, name, rate, prime_rate, join_way, end_date, max_limit, join_member, join_deny, bonus_condition, save_term, minimum_cost, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code, min_save_term, max_save_term) VALUES ('D21', 'The든든예금(시즌2)', 2.9, 3.9, '스마트폰', '2025-12-31', 1000000000.0, '개인', 1, '①마케팅동의 및 모바일메세지 수신동의 0.05%
②신규고객 우대(최근 12개월 신규이력·해지이력 미보유) 0.10%
③이벤트금리(비대면금리) 최대 0.60%
   (3, 6개월 0.55% / 12개월 0.60%)', 12, 1000000, '경남은행', 93, 2, 'https://knbank.co.kr', '1. 이 예금의 계약기간은 3개월, 6개월, 12개월로 한다.
2. 가입좌수 제한없으며, 가입금액은 1인당 최소 100만원 이상 10억원 이하이다.', 1750, 490, 78, 0.93, 3, 12);
INSERT INTO deposit (id, name, rate, prime_rate, join_way, end_date, max_limit, join_member, join_deny, bonus_condition, save_term, minimum_cost, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code, min_save_term, max_save_term) VALUES ('D22', 'The파트너예금', 2.75, 3.75, '스마트폰', '9999-12-31', 1000000000.0, '개인', 1, '①경남은행 거래기간 5년이상 + 마케팅동의 고객 0.20%
②급여, 연금, 가맹점대금 입금 시 0.10%
③당행 카드 결제실적 보유 시 0.10%', 12, 1000000, '경남은행', 90, 3, 'https://knbank.co.kr', '1. 이 예금의 계약기간은 6개월, 12개월, 24개월로 한다.
2. 가입좌수 제한없으며, 가입금액은 1인당 최소 100만원 이상 10억원 이하이다.', 1450, 420, 63, 0.9, 6, 12);
INSERT INTO deposit (id, name, rate, prime_rate, join_way, end_date, max_limit, join_member, join_deny, bonus_condition, save_term, minimum_cost, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code, min_save_term, max_save_term) VALUES ('D23', 'IBK평생한가족통장(실세금리정기예금)', 2.5, 3.5, '영업점,인터넷,스마트폰', NULL, 100000000.0, '실명의 개인
(개인사업자 제외)', 1, '최고 연 0.20%p

-고객별 우대 : 최고 연 0.05%p
 1. 최초신규고객 : 연 0.05%p
 2. 재예치고객 : 연 0.05%p
 3. 장기거래고객 : 연 0.05%p

-주거래우대 : 연 0.15%p', 12, 1000000, '중소기업은행', 85, 3, 'https://ibk.co.kr', '계좌 수 제한 없으며, 최소 1백만원 이상 통합한도 1억원 이내 가입 가능', 1200, 350, 50, 0.85, 12, 12);
INSERT INTO deposit (id, name, rate, prime_rate, join_way, end_date, max_limit, join_member, join_deny, bonus_condition, save_term, minimum_cost, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code, min_save_term, max_save_term) VALUES ('D24', 'IBK더굴리기통장(실세금리정기예금)', 2.4, 3.4, '인터넷,스마트폰', NULL, NULL, '실명의 개인
(개인사업자 제외)', 1, '없음', 12, 1000000, '중소기업은행', 82, 2, 'https://ibk.co.kr', '계좌 수 제한 없으며, 최소 1백만원 이상 납입한도 제한 없음', 1000, 300, 45, 0.82, 12, 12);
INSERT INTO deposit (id, name, rate, prime_rate, join_way, end_date, max_limit, join_member, join_deny, bonus_condition, save_term, minimum_cost, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code, min_save_term, max_save_term) VALUES ('D25', 'IBK굴리기통장(정기예금)', 2.3, 3.3, '영업점,스마트폰', NULL, 300000000.0, '실명의 개인
(개인사업자 제외)', 1, '없음', 12, 1000000, '중소기업은행', 80, 3, 'https://ibk.co.kr', '계좌 수 제한 없으며, 최소 1백만원 이상 통합한도 3억원 이내 가입 가능', 950, 280, 40, 0.8, 12, 12);
INSERT INTO deposit (id, name, rate, prime_rate, join_way, end_date, max_limit, join_member, join_deny, bonus_condition, save_term, minimum_cost, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code, min_save_term, max_save_term) VALUES ('D26', 'KDB 정기예금', 2.6, 3.6, '영업점,인터넷,스마트폰', NULL, NULL, '제한없음', 1, '해당없음', 12, 0, '한국산업은행', 87, 2, 'https://kdb.co.kr', '해당없음', 1300, 380, 55, 0.87, 12, 12);
INSERT INTO deposit (id, name, rate, prime_rate, join_way, end_date, max_limit, join_member, join_deny, bonus_condition, save_term, minimum_cost, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code, min_save_term, max_save_term) VALUES ('D27', 'KB Star 정기예금', 2.7, 3.7, '인터넷,스마트폰', NULL, NULL, '실명의 개인 또는 개인사업자', 1, '해당무', 12, 1000000, '국민은행', 89, 2, 'https://kbstar.com', '- 가입금액 : 1백만원 이상', 1400, 400, 60, 0.89, 12, 12);
INSERT INTO deposit (id, name, rate, prime_rate, join_way, end_date, max_limit, join_member, join_deny, bonus_condition, save_term, minimum_cost, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code, min_save_term, max_save_term) VALUES ('D28', '쏠편한 정기예금', 2.55, 3.55, '인터넷,스마트폰', NULL, NULL, '만14세이상 개인고객', 1, '해당사항없음', 12, 10000, '신한은행', 86, 3, 'https://shinhan.com', '1. 가입한도 :
 1만원 이상', 1100, 320, 48, 0.86, 12, 12);
INSERT INTO deposit (id, name, rate, prime_rate, join_way, end_date, max_limit, join_member, join_deny, bonus_condition, save_term, minimum_cost, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code, min_save_term, max_save_term) VALUES ('D29', 'NH왈츠회전예금 II', 2.8, 3.8, '영업점,인터넷,스마트폰', NULL, NULL, '개인', 1, '1. 급여이체실적(50만원 이상)이 있는 경우 : 0.1%p
2. 트리플 회전 우대이율 :  4회전기간부터 0.1%p', 12, 100000, '농협은행주식회사', 91, 2, 'https://nonghyup.com', '※ 기본금리 및 최고 우대금리 항목은 가입기간이 아닌 ‘회전주기’기간별 금리를 공시
 - 회전주기는 1개월 이상 12개월 이내 월단위로 선택 가능
※ 전월취급평균금리는 본 홈페이지에 공시되지 않는 회전기간 6개월 미만의 계좌들도 포함하여 산출하기 때문에 회전기간별로 공시된 기본금리보다 낮게 보여질 수 있음', 1600, 450, 70, 0.91, 1, 12);
INSERT INTO deposit (id, name, rate, prime_rate, join_way, end_date, max_limit, join_member, join_deny, bonus_condition, save_term, minimum_cost, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code, min_save_term, max_save_term) VALUES ('D3', 'iM주거래우대예금(첫만남고객형)', 2.6, 3.6, '영업점,인터넷,스마트폰', NULL, NULL, '실명의 개인', 1, '* 최고우대금리 : 연0.65%p
 - 목돈굴리기예금 최초 가입시 : 연0.20%p
 - 상품 가입 전 최근 1개월 이내 신용(체크)카드 신규 발급 : 연0.20%p
 - 상품 가입 전 최근 1개월 이내 인터넷.폰.모바일앱뱅킹 가입 : 연0.20%p
 * 해당 상품을 인터넷/모바일뱅킹을 통해 가입 : 연0.05%p', 12, 1000000, '아이엠뱅크', 88, 3, 'https://imbank.com', '계좌당 가입 최저한도 : 100만원', 1100, 300, 45, 0.88, 12, 12);
INSERT INTO deposit (id, name, rate, prime_rate, join_way, end_date, max_limit, join_member, join_deny, bonus_condition, save_term, minimum_cost, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code, min_save_term, max_save_term) VALUES ('D30', 'NH내가Green초록세상예금', 2.9, 3.9, '영업점,인터넷,스마트폰', NULL, NULL, '개인', 1, '※ 우대금리 최대한도 : 0.4%p(연%, 세전)
1. 온실가스 줄이기 실천서약서 동의 : 0.1%p
2. 통장미발급 : 0.3%p
3. 손하나로인증 서비스 등록 : 0.2%p
4. 대중교통이용 : 0.2%p
5. NH내가Green초록세상예금 동시 보유 : 0.1%p', 12, 3000000, '농협은행주식회사', 94, 1, 'https://nonghyup.com', '1. 300만원이상 가입
2. 온실가스 줄이기 실천서약서 동의시 가입가능
3. 신규가입 계좌당 2천원씩 녹색환경기금 적립
※ 자세한 사항은 상품설명서 참조', 1800, 500, 80, 0.94, 12, 12);
INSERT INTO deposit (id, name, rate, prime_rate, join_way, end_date, max_limit, join_member, join_deny, bonus_condition, save_term, minimum_cost, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code, min_save_term, max_save_term) VALUES ('D4', 'iM행복파트너예금(일반형)', 2.4, 3.4, '영업점,인터넷,스마트폰', NULL, NULL, '만50세 이상 실명의 개인', 3, '* 최고우대금리 : 연0.45%p
- 지난달 당행 통장으로 연금 입금 실적 보유 : 연0.10%p
- 상품 가입 전 당행 신용(체크)카드 보유 : 연0.10%p
- 지난 3개월 예금 평잔 30만원 이상 : 연0.10%p
- iM행복파트너적금 동시 가입 및 만기 보유 : 연0.10%p
* 해당 상품을 인터넷/모바일뱅킹을 통해 가입 : 연0.05%p', 12, 1000000, '아이엠뱅크', 82, 4, 'https://imbank.com', '계좌당 가입 최저한도 : 100만원', 950, 280, 40, 0.82, 12, 12);
INSERT INTO deposit (id, name, rate, prime_rate, join_way, end_date, max_limit, join_member, join_deny, bonus_condition, save_term, minimum_cost, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code, min_save_term, max_save_term) VALUES ('D5', 'iM함께예금', 2.3, 3.3, '영업점,인터넷,스마트폰', NULL, NULL, '실명의 개인 및 개인사업자', 1, '* 최고우대금리 : 연0.45%p
- 전월 총 수신 평잔실적 또는 상품 가입 전 첫만남플러스 통장 보유시
- 당행 주택청약상품보유
-  iM함께적금" 동시 가입 및 만기 보유- 당행 오픈뱅킹에 다른 은행 계좌 등록시 각  연0.10%p * 해당 상품을 인터넷/모바일뱅킹을 통해 가입 시 : 연0.05%p”', 12, 1000000, '아이엠뱅크', 80, 3, 'https://imbank.com', '계좌당 가입 최저한도 : 100만원', 900, 270, 38, 0.8, 12, 12);
INSERT INTO deposit (id, name, rate, prime_rate, join_way, end_date, max_limit, join_member, join_deny, bonus_condition, save_term, minimum_cost, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code, min_save_term, max_save_term) VALUES ('D6', 'iM스마트예금', 2.2, 3.2, '인터넷,스마트폰', NULL, NULL, '실명의 개인', 1, '* 최고우대금리 : 연0.25%p
- 가입일(재예치일)로부터 3개월 이내 아래 1가지 이상 요건 충족시
① 당행 주택청약종합저축 보유
② 당행 신용(체크)카드 결제실적 보유(결제금액 출금기준)
* 해당 상품을 인터넷/모바일뱅킹을 통해 가입 : 연0.05%p', 12, 1000000, '아이엠뱅크', 78, 2, 'https://imbank.com', '계좌당 가입 최저한도 : 100만원', 850, 250, 35, 0.78, 12, 12);
INSERT INTO deposit (id, name, rate, prime_rate, join_way, end_date, max_limit, join_member, join_deny, bonus_condition, save_term, minimum_cost, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code, min_save_term, max_save_term) VALUES ('D7', 'LIVE정기예금', 2.7, 3.7, '영업점,인터넷', NULL, NULL, '제한없음', 1, '*우대이율
가. 3~5개월 특판우대이율 : 0.70%
나. 6~11개월 특판 우대이율: 0.60%
다. 12개월 특판 우대이율 : 0.45%', 12, 10000000, '부산은행', 87, 3, 'https://busanbank.co.kr', '1. 가입금액 :
   1천만원 이상
2. 가입기간 :
1개월 이상 60개월 이하(일단위)
3. 월이자지급식/만기일시지급식', 1300, 380, 55, 0.87, 1, 60);
INSERT INTO deposit (id, name, rate, prime_rate, join_way, end_date, max_limit, join_member, join_deny, bonus_condition, save_term, minimum_cost, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code, min_save_term, max_save_term) VALUES ('D8', '더(The) 특판 정기예금', 2.9, 3.9, '인터넷,스마트폰', NULL, NULL, '실명의 개인', 1, '* 우대이율 (최대 0.70%p)
가. 모바일뱅킹 금융정보 및 혜택알림 동의 우대이율 : 0.10%p
나. 이벤트 우대이율 : 최대 0.6%p
1) 더(The) 특판 정기예금 신규고객 우대이율 : 0.10%p
2) 특판 우대이율 : 0.50%p', 12, 1000000, '부산은행', 92, 2, 'https://busanbank.co.kr', '1. 가입금액 : 1백만원 이상 제한없음 (원단위)
2. 가입기간 : 1개월, 3개월, 6개월, 1년, 2년, 3년
3. 이자지급방식 : 만기일시지급식', 1600, 450, 70, 0.92, 1, 36);
INSERT INTO deposit (id, name, rate, prime_rate, join_way, end_date, max_limit, join_member, join_deny, bonus_condition, save_term, minimum_cost, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code, min_save_term, max_save_term) VALUES ('D9', '더(The) 레벨업 정기예금', 2.55, 3.55, '스마트폰', NULL, NULL, '실명의 개인', 1, '*우대이율(최대 0.20%p)
가. 모바일뱅킹 금융정보 및 혜택알림 동의 우대이율 : 0.10%p
나. 비대면 정기예금 재예치 우대이율  : 0.10%', 12, 1000000, '부산은행', 86, 3, 'https://busanbank.co.kr', '1. 가입금액 : 1백만원 이상 제한없음 (원단위)
2. 가입기간 : 6개월, 1년
3. 이자지급방식 : 만기일시지급식', 1150, 330, 48, 0.86, 6, 12);
INSERT INTO savings (id, name, rate, prime_rate, join_way, end_date, max_limit, join_member, join_deny, bonus_condition, save_term, rate_type, save_type, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code) VALUES
                                                                                                                                                                                                                                                   ('S1', '우리SUPER주거래적금', 2.20, 3.20, '영업점,인터넷,스마트폰,전화(텔레뱅킹)', NULL, NULL, '실명의 개인', 1, '1. 거래실적 인정기간 동안 우리은행 입출식 계좌에서 아래 각 항목별 실적이 있는 월 수가 계약기간의 1/2이상인 경우
가. 급여/연금 이체 : 연 0.7%p
나. 공과금 자동이체 출금 실적 : 0.3%p
다. 우리카드사 신용/체크카드 결제금액 10만원 이상 출금 : 연 0.3%p
2. 우리은행 상품서비스 마케팅 동의 항목 중 전화(휴대폰) 및 SMS항목을 모두 동의한 후 만기해지시점까지 유지 : 연 0.1%p
3. 이 상품 가입 시 금리우대쿠폰을 적용한 경우', 12, '변동금리', '정액적립식', '우리은행', 85, 3, 'https://wooribank.com', '1. 가입기간 : 1년/2년/3년
2. 가입금액 : 월 50만원 이내', 1200, 350, 50, 0.85),
                                                                                                                                                                                                                                                   ('S2', 'WON적금', 2.10, 3.10, '스마트폰,전화(텔레뱅킹)', NULL, NULL, '실명의 개인', 1, '1. 아래 각 항(가, 나)의 조건을 충족하는 경우 합산 최대 연 0.2%p 우대
가. 이 적금을 우리꿈통장, WON통장에 연결하여 가입하는 경우 : 0.1%p
나. 우리 오픈뱅킹 서비스에 타행계좌가 등록되어 있는 경우 : 연 0.1%p', 12, '변동금리', '자유적립식', '우리은행', 80, 2, 'https://wooribank.com', '1. 가입기간 : 1년
2. 가입금액 : 월 50만원 이내', 1100, 320, 45, 0.80),
                                                                                                                                                                                                                                                   ('S3', '퍼스트가계적금', 2.30, 3.30, '영업점,인터넷,스마트폰', '9999-12-31', 10000000, '개인(개인사업자 포함)', 1, '없음', 24, '변동금리', '정액적립식', '한국스탠다드차타드은행', 88, 3, 'https://standardchartered.co.kr', '해당없음', 1300, 380, 55, 0.88),
                                                                                                                                                                                                                                                   ('S4', '내손안에 적금', 2.50, 3.50, '스마트폰', NULL, 1000000, '실명의 개인', 1, '* 최고우대금리 : 연0.55%p
 - 당행 인터넷/모바일뱅킹을 통하여 최초로 적립식예금 가입 시 : 연0.20%p
 - 상품 가입 전 최근 3개월 이내 당행 인터넷/모바일뱅킹을 통한 이체거래 3회 이상 : 연0.10%p
- 상품 가입 전 수수료우대 통장 보유 고객 : 연0.20%p
* 해당 상품을 모바일뱅킹을 통해 가입 : 연0.05%p', 12, '변동금리', '자유적립식', '아이엠뱅크', 90, 2, 'https://imbank.com', '1계좌당 가입한도 : 월 100만원(최저 10만원 이상)', 1500, 400, 60, 0.90),
                                                                                                                                                                                                                                                   ('S5', '영플러스적금', 2.40, 3.40, '영업점,인터넷,스마트폰', NULL, 500000, '만 29세 이하 실명의 개인', 3, '* 최고우대금리 : 연0.55%p
- 상품 가입 전 영플러스통장 보유 또는 재예치일 기준 영플러스통장 전월 평잔 10만원 이상 : 연0.10%p
- 예금기간 중 입금횟수 10회 이상 : 연0.10%p
- 입학 및 졸업 축하금리 : 연0.30%p
* 해당 상품을 인터넷/모바일뱅킹을 통해 가입 : 연0.05%p', 12, '변동금리', '자유적립식', '아이엠뱅크', 87, 3, 'https://imbank.com', '1계좌당 가입한도 : 월 50만원(최저 1만원 이상)', 1400, 380, 55, 0.87),
                                                                                                                                                                                                                                                   ('S6', '내가만든 보너스적금', 2.80, 3.80, '영업점,인터넷,스마트폰', NULL, 200000, '실명의 개인', 1, '* 최고우대금리 : 연0.80%p
 - 인터넷/모바일뱅킹으로 신규 : 연0.20%p
 - 상품 가입 전 마케팅활용[개인(신용)정보수집이용동의] 전체 동의 : 연0.20%p
(계약기간 1년인 경우 만기일 기준)
 - 당행 계좌로 자동이체 8회 이상 입금 : 연0.20%p
 - 원금합계 100만원 이상 : 연0.10%p 또는 원금합계 200만원 이상 : 연0.20%p', 12, '변동금리', '자유적립식', '아이엠뱅크', 92, 1, 'https://imbank.com', '1계좌당 가입한도 : 월20만원
(최저 1만원 이상)', 1600, 450, 70, 0.92),
                                                                                                                                                                                                                                                   ('S7', 'iM스마트적금', 2.60, 3.60, '인터넷,스마트폰', NULL, 1000000, '실명의 개인', 1, '* 최고우대금리 : 연0.45%p
- 가입일로부터 3개월 이내 1가지 이상 요건 충족시 : 연0.20%p
① 당행 주택청약종합저축 보유
② 당행 신용(체크)카드 결제실적 보유(결제금액 출금기준)
- 전체 계약월수 중 1/2이상 당행 계좌에서 자동이체 납입 시 : 연0.20%p
* 해당 상품을 인터넷/모바일뱅킹을 통해 가입 : 연0.05%p', 12, '변동금리', '자유적립식', '아이엠뱅크', 89, 2, 'https://imbank.com', '1계좌당 가입한도 : 월 100만원(최저 5만원 이상)', 1450, 410, 65, 0.89),
                                                                                                                                                                                                                                                   ('S8', '펫 적금', 2.00, 3.00, '영업점,스마트폰', NULL, 500000, '실명의 개인고객(1인 1계좌)', 1, '*우대이율 6개월제 최대 0.55%, 12개월제 최대 0.90%', 12, '변동금리', '정기적립식', '부산은행', 75, 4, 'https://busanbank.co.kr', '1. 가입한도: 월 1만원 이상 50만원 이하 원단위
2. 정기적립식', 900, 280, 35, 0.75),
                                                                                                                                                                                                                                                   ('S9', '저탄소 실천
적금', 2.20, 3.20, '영업점,인터넷,스마트폰', NULL, 10000000, '제한없음', 1, '조건 달성여부에 따라 우대금리 적용
*개인형 우대이율: 최대0.5%
*기업형(개인사업자 및 법인)우대이율: 최대0.4%', 12, '변동금리', '자유적립식', '부산은행', 80, 3, 'https://busanbank.co.kr', '1. 가입한도: 월 1만원 이상 1천만원 이하 원단위 (월 1천만원 이하 불입 가능)
2. 자유적립식', 1000, 300, 40, 0.80),
                                                                                                                                                                                                                                                   ('S10', 'BNK내맘대로
적금', 2.10, 3.10, '영업점,인터넷,스마트폰', NULL, NULL, '제한없음', 1, '*우대이율 최대 0.2%
- 신규시 우대이율 0.05% 및 해지시 우대이율 최대 0.15%', 12, '변동금리', '자유적립식', '부산은행', 78, 2, 'https://busanbank.co.kr', '1. 가입금액 :
   1천원 이상
2. 가입기간 :
6개월 이상 60개월 이하(일단위)', 950, 290, 38, 0.78),
                                                                                                                                                                                                                                                   ('S11', '부산이라 좋다
Big적금', 3.50, 4.50, '스마트폰', NULL, 1000000, '실명의 개인고객(1인 1계좌)', 1, '*우대이율 6개월 미만 최대2.00%, 6개월 이상 2.20%', 6, '변동금리', '자유적립식', '부산은행', 95, 1, 'https://busanbank.co.kr', '1. 가입한도: 월 1천원 이상 100만원 이하 원단위
2. 자유적립식', 1800, 500, 80, 0.95),
                                                                                                                                                                                                                                                   ('S12', '해피라이프_여행스케치적금V', 2.70, 3.70, '영업점,스마트폰', NULL, 5000000, '제한없음(단,국가및지방자치단체제외)', 1, '▶ 최고우대금리 1.20%p
①해피라이프_여행스케치외화적금V 동일자가입 0.5%p
②해지원금 기준 500만원이상 : 최고0.2%p
③신용(체크)카드사용실적300만원이상:최고 0.3%p
④개인(신용)정보 동의: 0.2%p', 12, '변동금리', '정액적립식', '광주은행', 90, 2, 'https://kjbank.com', '1. 가입기간 : 6개월이상 3년제
2. 가입금액 : 월5만원 이상 5백만원 이하 (1인1계좌)', 1500, 420, 68, 0.90),
                                                                                                                                                                                                                                                   ('S13', '여행스케치_남도투어적금', 3.00, 4.00, '영업점,스마트폰', NULL, 1000000, '만14세이상 개인 및 개인사업자', 1, '▶ 최고우대금리 1.9%p
①예금가입일~만기일전일까지 당행이선정한 전라남도 관광지 방문 인증시 : 최고 1.5%p
②신용(체크)카드사용실적300만원이상:최고 0.3%p
③개인(신용)정보 동의: 0.1%p', 12, '변동금리', '정액적립식', '광주은행', 93, 1, 'https://kjbank.com', '1. 가입기간 :12개월제,18개월제
2. 가입금액 : 월 5만원 이상 1백만원 이하 (1인1계좌)
※ 18개월 정액식 기본금리 3.3%, 최고금리 5.2%', 1700, 480, 75, 0.93),
                                                                                                                                                                                                                                                   ('S14', 'VIP플러스적금', 2.50, 3.50, '영업점,인터넷,스마트폰', NULL, 5000000, '실명의 개인', 1, '▶ 최고우대금리 0.50%p
①이 예금가입시 VIP고객이거나 가입일 이후부터 만기일전일까지 VIP고객에 선정된 경험이 있는 경우 : 0.3%p
②이 예금 가입일에 정기예금 500만원이상(만기 1년이상) 가입하고 만기일 전일까지 유지한 경우 : 0.2%p', 12, '변동금리', '정액적립식', '광주은행', 88, 3, 'https://kjbank.com', '1. 가입기간 : 1년제
2. 가입금액 : 월10만원 이상 5백만원 이하 (1인1계좌)', 1300, 370, 52, 0.88),
                                                                                                                                                                                                                                                   ('S15', 'jbank 저금통적금', 3.20, 4.20, '인터넷,스마트폰', NULL, 500000, '개인 및 개인사업자', 1, '* 거래조건에 따라 최고 2.1%p 우대금리 적용
① 자투리 출금계좌 평잔 50만원 이상 유지 : 0.8%p
② 첫거래고객 or JBANK저금통적금 1개월 내 재신규 : 0.5%p
③ 신규가입 시점에서 적금 목표금액 최소 30만원 이상 설정하고, 적금 신규일로부터 3개월 내 잔액이 목표금액 이상인 경우 :0.5%p
④ 추천인 우대금리 : 0.30%p', 12, '변동금리', '자유적립식', '제주은행', 94, 1, 'https://jejuebank.co.kr', '월 납입한도 50만원 이하', 1850, 510, 82, 0.94),
                                                                                                                                                                                                                                                   ('S16', 'MZ 플랜적금', 2.80, 3.80, '영업점,인터넷,스마트폰', NULL, 300000, '개인 및 개인사업자', 1, '①매월 1회이상 지속적 납입시:1년제: 0.50%
② 목표 금액 달성:0.50%
③ 신용카드,체크카드 합산 사용액 월10만원 이상 사용: 0.50%
* 청년이니까응원합니다 이벤트 : 0.50%', 12, '변동금리', '자유적립식', '제주은행', 91, 2, 'https://jejuebank.co.kr', '월 납입한도 30만원 이하', 1650, 460, 72, 0.91),
                                                                                                                                                                                                                                                   ('S17', '탐이나요적금', 3.70, 4.70, '인터넷,스마트폰', NULL, 1000000, '개인 및 개인사업자', 1, '우대요건 충족시 최고 2.7%p 추가 우대(만기시 제공)매월 JBANK로그인 시 0.2%,한 가지라도 수행시 2.5% 제공①추천인 코드로 3명 이상 가입시 ②탐나는전 체크카드 월30만원 이상 사용 유지 ③탐나는전 선불카드 충전계좌를 당행 입출금계좌로 등록 후 평잔 50만원 이상 유지 시', 12, '변동금리', '자유적립식', '제주은행', 96, 1, 'https://jejuebank.co.kr', '월 납입한도 100만원 이하', 2000, 550, 90, 0.96),
                                                                                                                                                                                                                                                   ('S18', 'JB 다이렉트적금(자유적립식)', 2.40, 3.40, '인터넷,스마트폰', NULL, 10000000, '실명의 개인 및 개인사업자(임의단체 제외)', 1, '추가우대금리 :
당행 계좌간 자동이체를 통해 이 예금으로 자동이체 된 금액에 0.1% 금리 우대', 12, '변동금리', '자유적립식', '전북은행', 85, 3, 'https://jbbank.co.kr', '1. 초회불입금 1만원이상, 1인당 월별 최고 1천만원이내
2. 만기직전 1개월간 적립합계는 이전기간 적립금액을 초과할 수 없음
3. 인터넷뱅킹/스마트폰뱅킹 가입상품', 1250, 360, 52, 0.85),
                                                                                                                                                                                                                                                   ('S19', 'JB 다이렉트적금(정액적립식)', 2.30, 3.30, '인터넷,스마트폰', NULL, 5000000, '실명의 개인 및 개인사업자(임의단체 제외)', 1, '추가우대금리 :
당행 계좌간 자동이체를 통해 6회이상 입금한 경우
연 0.1% 금리우대', 12, '변동금리', '정액적립식', '전북은행', 82, 2, 'https://jbbank.co.kr', '1. 초회불입금 1만원이상, 1인당 월별 최고 5백만원이내
2. 인터넷뱅킹/스마트폰뱅킹 가입상품', 1150, 330, 48, 0.82),
                                                                                                                                                                                                                                                   ('S20', '행복 DREAM 적금', 2.60, 3.60, '영업점,인터넷,스마트폰', '9999-12-31', 999999999, '실명의 개인 및 개인사업자', 1, '①신규일로부터 6개월전까지 당행 적금 미보유 0.2%
②월부금이 50만원 이상인 경우 0.20%
③이 예금을 자동이체로 납입하는 경우 0.2%
④신규일로부터 익월 말일까지 신용(체크)카드를 최초로 발급하고 동 기간내에 10만원이상 이용실적이 있는경우 0.2%
⑤이 예금 가입전 경남은행 마케팅동의가 되어있는경우 0.2%', 12, '변동금리', '자유적립식', '경남은행', 89, 3, 'https://knbank.co.kr', '1. 계약기간은 1년 이상 3년 이하 월단위로 한다.
2. 적립금액은 5만원 이상, 최고금액은 제한없음', 1400, 400, 60, 0.89),
                                                                                                                                                                                                                                                   ('S21', 'BNK더조은자유적금', 2.50, 3.50, '인터넷,스마트폰', '9999-12-31', 5000000, '거래대상자는 제한을 두지 아니한다. 다만, 국가 및 지방자치단체는 이 예금을 거래할 수 없다.', 1, '①오픈뱅킹서비스 가입 0.20%(만기까지 유지)
②당행 입출금통장에서 자동이체시 입금건별 0.20%
③이 상품 신규시 금리우대쿠폰을 등록할 경우 0.30%', 12, '변동금리', '자유적립식', '경남은행', 87, 2, 'https://knbank.co.kr', '1. 계약기간은 6개월 이상 2년 이내 월단위로 한다.
2. 초입금 1만원 이상 월별 500만원 이내에서 자유롭게 저축
3. 최대 저축횟수는 999회 이내', 1300, 370, 55, 0.87),
                                                                                                                                                                                                                                                   ('S22', 'BNK 위더스자유적금', 3.00, 4.00, '영업점,인터넷,스마트폰,기타', '9999-12-31', 1000000, '실명의 개인 및 개인사업자', 1, '①ESG 실천 우대금리 1.00%
②신규고객 우대금리 1.00%
- 당행 1년 이내예적금(청약포함)신규해지 이력미보유
③마케팅동의우대금리 0.50%', 12, '변동금리', '자유적립식', '경남은행', 92, 1, 'https://knbank.co.kr', '1.계약기간은 1개월 이상 36개월 이하 월단위로 한다.
2..1인 1계좌로 가입가능
2.매월 최소 1만원 이상, 최고 월 100만원 이하 (천원 단위)', 1600, 450, 70, 0.92),
                                                                                                                                                                                                                                                   ('S23', '주거래 프리미엄 적금', 2.80, 3.80, '영업점,인터넷,스마트폰', '9999-12-31', 999999999, '실명의 개인 및 개인사업자', 1, '①주거래우대 0.50%
②공과금 자동이체 0.40~0.60%
③신규고객 0.20%
④주택청약종합저축 보유 0.10%
⑤ 전자명함을 통한 신규 시 0.2%', 12, '변동금리', '정액적립식', '경남은행', 90, 2, 'https://knbank.co.kr', '1.계악기간은 1년제, 2년제, 3년제로 한다.
2. 적립금액은 매월 1만원이상, 최고금액은 제한없음', 1500, 420, 65, 0.90),
                                                                                                                                                                                                                                                   ('S24', '오늘도세이브적금', 2.70, 3.70, '인터넷,스마트폰,기타', '9999-12-31', 100000, '실명의 개인 및 개인사업자', 1, '①마케팅동의 0.15%
②목돈마련 축하이율 0.30~1.00%
③친구 추천번호 0.30~0.60%', 6, '변동금리', '자유적립식', '경남은행', 88, 3, 'https://knbank.co.kr', '1. 계약기간은 1개월 이상 6개월 이내 월단위로 한다.
2. 초입금 일 1천원 이상 10만원 이하 자유롭게 저축
3. 최대 저축횟수는 999회 이내', 1350, 390, 58, 0.88),
                                                                                                                                                                                                                                                   ('S25', '사장님 걱정 ZERO 적금', 3.50, 4.50, '영업점,스마트폰', '9999-12-31', 3000000, '실명의 개인 및 개인사업자', 1, '①이 예금 가입전 경남은행 마케팅 동의 0.40%
②가입 전월 경남은행 계좌로 BC,신한,삼성 카드사 가맹점 대금 입금 실적이 모두 없으며, 가입일로부터 만기일 전일까지 BC,신한,삼성 카드사 가맹점 대금 입금실적이 발생한 경우 : 1.00%
③월별가맹점 대금실적이 있는경우
1종류 : 0.50%
2종류 1.00%
3종류 이상 : 1.80%', 12, '변동금리', '정액적립식', '경남은행', 95, 1, 'https://knbank.co.kr', '1.계악기간은 1년제로 한다.
2. 적립금액은 최소 1천원 이상 최대3백만원 이하 1원단위
3. 최대 저축횟수는 999회 이내', 1900, 520, 85, 0.95),
                                                                                                                                                                                                                                                   ('S26', 'IBK D-day적금(자유적립식)', 3.00, 4.00, '스마트폰', NULL, 600000, '실명의 개인
(개인사업자 제외)', 1, '최고 연 1.5%p
1. 목표달성 축하금리 : 연 1.0%p
  - 당행 입출금식 계좌에서 이 적금으로 자동이체를 통해 3회 이상 납입하고 만기일 전일까지 목표금액(신규 시 직접 설정) 이상 납입하는 경우
2. 최초거래고객 우대금리 : 연 0.5%p', 12, '변동금리', '자유적립식', '중소기업은행', 93, 1, 'https://ibk.co.kr', '1인당 3계좌 가입 가능하며, 계좌당 20만원 이내 납입 가능', 1700, 480, 75, 0.93),
                                                                                                                                                                                                                                                   ('S27', 'IBK탄소제로적금(자유적립식)', 3.20, 4.20, '스마트폰', NULL, 1000000, '실명의 개인
(개인사업자 제외)', 1, '최고 연 2.00%p
1. 에너지 절감 우대금리 : 최대 연 1.00%p
2. 최초거래고객 우대금리 : 연 0.50%p
3. 지로 또는 공과금 자동이체 우대금리 : 연 0.50%p', 12, '변동금리', '자유적립식', '중소기업은행', 94, 1, 'https://ibk.co.kr', '1인당 1계좌 가입 가능하며, 계좌당 최소 1만원 이상 1백만원까지 납입 가능', 1800, 500, 80, 0.94),
                                                                                                                                                                                                                                                   ('S28', 'IBK중기근로자우대적금
(자유적립식)', 3.40, 4.40, '영업점,스마트폰', NULL, 1000000, '중소기업에서 근무하는
실명의 개인
(개인사업자 제외)', 3, '최고 연 2.20%p
1. 가입시점 중소기업 근로자로 확인된 경우 : 재직기간에 따라 최고 연 1.2%p
2. 당행 급여이체 실적(월50만원 이상) 6개월 이상
   인 경우 : 연 1.0%p', 12, '변동금리', '자유적립식', '중소기업은행', 95, 1, 'https://ibk.co.kr', '1인당 1계좌 가입 가능하며, 계좌당 100만원까지 납입 가능', 1900, 520, 85, 0.95),
                                                                                                                                                                                                                                                   ('S29', 'IBK모으기통장(자유적립식)', 2.00, 3.00, '영업점,스마트폰', NULL, 15000000, '실명의 개인
(개인사업자 제외)', 1, '최고 연 0.20%p
1. 자동이체 우대금리 : 연 0.20%p
- 6개월 이상 12개월 미만 : 3회
 12개월 이상 24개월 미만 : 6회
 24개월 이상 36개월 미만 : 12회
 36개월 : 18회', 12, '변동금리', '자유적립식', '중소기업은행', 80, 3, 'https://ibk.co.kr', '1인당 5계좌 가입 가능하며, 계좌당 최소 1천원 이상 3백만원까지 납입 가능', 1000, 300, 40, 0.80),
                                                                                                                                                                                                                                                   ('S30', 'KDB 기업정기적금', 2.10, 3.10, '영업점,인터넷', NULL, NULL, '개인사업자, 조합(비영리법인 포함), 법인', 1, '해당없음', 12, '변동금리', '정액적립식', '한국산업은행', 82, 2, 'https://kdb.co.kr', '해당없음', 1100, 320, 45, 0.82);
INSERT INTO `pension` (id, name, rate, pension_kind, pension_type, min_guarantee_rate, last_year_profit_rate, end_date, company, score, risk, reg_link, caution, view_cnt, scrap_cnt, regret_cnt, algo_code) VALUES
                                                                                                                                                                                                                 ('C1', 'KB국민 안정형 확정급여 연금', 2.85, '확정급여형', 'TRUE', 1.8, 3.2, '2026-12-31', 'KB국민은행', 90, 5, 'https://obank.kbstar.com/ch_pension/ch021001/ch02100101/pension_irp_intro.jsp', '낮은 수익률에 유의하세요.', 12345, 876, 50, 0.25),
                                                                                                                                                                                                                 ('A1', '미래에셋 공격형 성장형 펀드 연금', 0.0, '연금저축펀드', 'FALSE', 0.0, 15.7, '2027-11-15', '미래에셋증권', 65, 2, 'https://securities.miraeasset.com/contents/retirement/irp/introduction.do', '원금 손실 가능성이 있습니다.', 25678, 1532, 280, 0.82),
                                                                                                                                                                                                                 ('C2', '삼성생명 평생 안정 연금보험', 2.1, '연금저축보험', 'TRUE', 1.5, 2.5, '2028-06-30', '삼성생명', 85, 4, 'https://www.samsunglife.com/pension/retirement-pension/pension-savings', '장기유지 시 유리합니다.', 9876, 543, 30, 0.38),
                                                                                                                                                                                                                 ('A2', '한국투자증권 글로벌 ETF 연금', 0.0, 'IRP', 'FALSE', 0.0, 22.1, '2027-09-01', '한국투자증권', 55, 1, 'https://www.koreainvest.com/main/customer/helpdesk/faq/views/20002166.jsp', '수익률 변동성이 매우 큽니다.', 31234, 1890, 450, 0.95),
                                                                                                                                                                                                                 ('C3', '신한은행 안전제일 확정급여', 3.1, '확정급여형', 'TRUE', 2.0, 3.5, '2029-03-20', '신한은행', 92, 6, 'https://sol.shinhan.com/con/pension/IRPInfo/index.jsp', '물가 상승률에 대비하세요.', 15432, 987, 60, 0.15),
                                                                                                                                                                                                                 ('A3', '유안타증권 고수익 주식형 연금', 0.0, '연금저축펀드', 'FALSE', 0.0, -8.5, '2026-10-01', '유안타증권', 48, 3, 'https://www.myasset.com/main/pension/overview.jsp', '손실 위험이 높으니 신중하세요.', 18765, 1120, 350, 0.78),
                                                                                                                                                                                                                 ('C4', '하나은행 예금형 연금저축', 2.3, '연금저축보험', 'TRUE', 1.0, 2.1, '2027-04-05', '하나은행', 80, 5, 'https://www.kebhana.com/cont/pension/pension02/pension0201/pension_irp.jsp', '장기적인 관점에서 접근하세요.', 7654, 321, 25, 0.45),
                                                                                                                                                                                                                 ('A4', 'NH투자증권 혁신기술 성장 연금', 0.0, 'DC', 'FALSE', 0.0, 30.2, '2028-01-25', 'NH투자증권', 70, 1, 'https://www.nhqv.com/investment/pension/irp_info.do', '시장 상황에 따라 변동성이 큽니다.', 29876, 1789, 520, 0.90),
                                                                                                                                                                                                                 ('C5', 'DB손해보험 안정형 IRP', 2.6, 'IRP', 'TRUE', 1.7, 3.0, '2029-09-10', 'DB손해보험', 88, 4, 'https://www.dbins.co.kr/company/retirement/retirement01.jsp', '수수료를 확인하세요.', 10500, 600, 40, 0.30),
                                                                                                                                                                                                                 ('A5', 'KDB생명 인덱스 투자 연금', 0.0, '연금저축보험', 'FALSE', 0.0, 18.0, '2027-03-01', 'KDB생명', 60, 2, 'https://www.kdblife.co.kr/Cmn.do?_SVC_ID=CPM1001000100', '시장 흐름에 민감합니다.', 22100, 1300, 200, 0.75),
                                                                                                                                                                                                                 ('C6', '교보생명 확정금리 연금저축', 2.9, '연금저축보험', 'TRUE', 1.9, 3.3, '2028-08-01', '교보생명', 91, 5, 'https://www.kyobolife.co.kr/FP/KLI/KLIFP0605V00.do', '금리 변동 시 불리할 수 있습니다.', 13200, 780, 55, 0.28),
                                                                                                                                                                                                                 ('A6', '키움증권 해외주식형 연금', 0.0, '연금저축펀드', 'FALSE', 0.0, 25.5, '2026-07-20', '키움증권', 58, 1, 'https://www.kiwoom.com/h/common/retirement', '환율 변동 위험이 있습니다.', 34500, 2100, 600, 0.98),
                                                                                                                                                                                                                 ('C7', '한화생명 변액연금 보험', 2.4, '연금저축보험', 'TRUE', 1.2, 2.7, '2029-01-10', '한화생명', 83, 4, 'https://www.hanwhalife.com/pr/irp', '투자 수익률이 낮을 수 있습니다.', 8800, 450, 35, 0.40),
                                                                                                                                                                                                                 ('A7', 'ABL생명 고성장 자산배분 연금', 0.0, 'IRP', 'FALSE', 0.0, 19.3, '2028-05-01', 'ABL생명', 62, 3, 'https://www.abllife.co.kr/ins/prd/P_L4104.jsp', '다양한 자산에 분산 투자됩니다.', 27000, 1600, 300, 0.85),
                                                                                                                                                                                                                 ('C8', '농협생명 이율보증 연금', 2.7, '연금저축보험', 'TRUE', 1.6, 3.1, '2027-10-25', '농협생명', 87, 5, 'https://www.nhlife.co.kr/retirement/pension/product/P02_01.jsp', '중도해지 시 환급금이 적을 수 있습니다.', 11500, 700, 48, 0.33),
                                                                                                                                                                                                                 ('A8', '메리츠증권 AI 투자 연금', 0.0, '연금저축펀드', 'FALSE', 0.0, 10.5, '2029-02-15', '메리츠증권', 68, 2, 'https://www.meritz.com/customer_center/center_info/guide_retirement.jsp', '알고리즘 오류 가능성도 존재합니다.', 20500, 1250, 180, 0.70),
                                                                                                                                                                                                                 ('C9', 'KDB산업은행 채권형 퇴직연금', 3.0, '확정급여형', 'TRUE', 2.1, 3.4, '2026-09-05', 'KDB산업은행', 93, 6, 'https://www.kdb.co.kr/ib/pr/retirePensionInfo.do', '안정적인 대신 고수익은 어렵습니다.', 14000, 850, 58, 0.10),
                                                                                                                                                                                                                 ('A9', '삼성증권 글로벌테마 연금', 0.0, 'IRP', 'FALSE', 0.0, 28.0, '2027-02-28', '삼성증권', 50, 1, 'https://www.samsungpop.com/fund/irp/index.jsp', '특정 테마에 집중 투자됩니다.', 33000, 1950, 550, 0.92),
                                                                                                                                                                                                                 ('C10', 'NH농협은행 장기안정 퇴직연금', 2.55, '확정급여형', 'TRUE', 1.75, 2.9, '2028-04-12', 'NH농협은행', 89, 5, 'https://banking.nonghyup.com/nhbank.html', '수익률이 낮지만 안정적입니다.', 11800, 680, 45, 0.20),
                                                                                                                                                                                                                 ('A10', '신한금융투자 고위험 대체투자 연금', 0.0, 'DC', 'FALSE', 0.0, -15.0, '2026-08-01', '신한금융투자', 45, 3, 'https://www.shinhaninvest.com/pension/retirement', '매우 높은 손실 위험이 있습니다.', 19500, 1050, 400, 0.72),
                                                                                                                                                                                                                 ('A11', '하나증권 테크주식 집중 연금', 0.0, '연금저축펀드', 'FALSE', 0.0, 28.5, '2027-09-01', '하나증권', 58, 1, 'https://www.hanaw.com/contents/pension/pension_main.cmd', '고위험 고수익 전략입니다.', 30123, 1850, 480, 0.93),
                                                                                                                                                                                                                 ('A12', 'KB증권 이머징마켓 투자 연금', 0.0, 'IRP', 'FALSE', 0.0, 17.2, '2028-03-10', 'KB증권', 60, 2, 'https://m.kbsec.com/go.do?w=269', '신흥국 시장의 높은 변동성.', 24567, 1450, 250, 0.80),
                                                                                                                                                                                                                 ('A13', '교보증권 테마형 성장 연금', 0.0, 'DC', 'FALSE', 0.0, -12.3, '2026-11-20', '교보증권', 47, 3, 'https://securities.iprovest.com/retirement/pension_irp.jsp', '특정 산업 편중 위험이 있습니다.', 16789, 980, 380, 0.76),
                                                                                                                                                                                                                 ('A14', '흥국생명 공격형 변액연금', 0.0, '연금저축보험', 'FALSE', 0.0, 11.8, '2027-05-05', '흥국생명', 63, 2, 'https://www.heungkuklife.co.kr/product/pension/variable_pension_list.do', '보험료 납입 중지 시 위험.', 20054, 1150, 190, 0.73),
                                                                                                                                                                                                                 ('A15', '신한라이프 글로벌 혁신 연금', 0.0, '연금저축펀드', 'FALSE', 0.0, 33.1, '2028-07-15', '신한라이프', 68, 1, 'https://www.shinhanlife.co.kr/hp/pnsn/prdt_irpv/prdt_irpv_intro.do', '해외 투자 위험이 있습니다.', 35000, 2200, 650, 0.97),
                                                                                                                                                                                                                 ('A16', 'DB금융투자 AI 자율주행 연금', 0.0, 'IRP', 'FALSE', 0.0, 9.7, '2026-10-01', 'DB금융투자', 55, 3, 'https://www.db-fi.com/IRP/IRP.html', '알고리즘 투자에 대한 이해 필요.', 17500, 1050, 220, 0.71),
                                                                                                                                                                                                                 ('A17', 'IBK기업은행 고위험 자산배분 연금', 0.0, 'DC', 'FALSE', 0.0, 20.0, '2029-04-20', 'IBK기업은행', 62, 2, 'https://www.ibk.co.kr/pension/pension.ibk', '다양한 고위험 자산에 투자됩니다.', 28000, 1700, 350, 0.88),
                                                                                                                                                                                                                 ('A18', '하나생명 글로벌 성장 펀드 연금', 0.0, '연금저축보험', 'FALSE', 0.0, 24.5, '2027-01-01', '하나생명', 59, 1, 'https://www.hanalife.co.kr/product/pension/pension_list.do', '환헤지 여부 확인이 필요합니다.', 32000, 2000, 580, 0.96),
                                                                                                                                                                                                                 ('A19', '케이프투자증권 메타버스 연금', 0.0, '연금저축펀드', 'FALSE', 0.0, -5.0, '2028-09-01', '케이프투자증권', 50, 3, 'https://www.capefn.com/front/html/customer/guide/pension_guide_irp.html', '신규 테마의 높은 투자 위험.', 15000, 850, 290, 0.79),
                                                                                                                                                                                                                 ('A20', '현대차증권 ESG 성장 연금', 0.0, 'IRP', 'FALSE', 0.0, 14.0, '2026-12-15', '현대차증권', 61, 2, 'https://www.hifund.com/irp/main', 'ESG 기준에 대한 이해 필요.', 21000, 1280, 210, 0.83),
                                                                                                                                                                                                                 ('A21', '유진투자증권 퀀트 전략 연금', 0.0, 'DC', 'FALSE', 0.0, 16.5, '2029-06-30', '유진투자증권', 66, 2, 'https://www.eugenefn.com/cms/pension/irp_info.html', '복잡한 투자 전략 이해 필요.', 26500, 1600, 270, 0.86),
                                                                                                                                                                                                                 ('A22', '푸르덴셜생명 글로벌 멀티에셋 연금', 0.0, '연금저축보험', 'FALSE', 0.0, 9.0, '2027-08-01', '푸르덴셜생명', 53, 3, 'https://www.prudential.co.kr/product/variable-pension', '다양한 자산의 운용 성과에 따라 달라짐.', 18000, 1000, 150, 0.74),
                                                                                                                                                                                                                 ('A23', 'BNK투자증권 베트남 성장 연금', 0.0, '연금저축펀드', 'FALSE', 0.0, 20.8, '2028-02-10', 'BNK투자증권', 57, 1, 'https://www.bnkst.com/investment/pension/irp.jsp', '신흥국 시장 특유의 높은 위험.', 29000, 1750, 420, 0.91),
                                                                                                                                                                                                                 ('A24', '하이투자증권 로봇산업 투자 연금', 0.0, 'IRP', 'FALSE', 0.0, 13.5, '2026-09-25', '하이투자증권', 60, 2, 'https://www.hi-ib.com/irp/irp01.do', '특정 산업의 리스크 집중.', 23000, 1380, 230, 0.84),
                                                                                                                                                                                                                 ('A25', '다올투자증권 대체투자 혼합 연금', 0.0, 'DC', 'FALSE', 0.0, -10.0, '2029-05-18', '다올투자증권', 49, 3, 'https://www.daolt.com/business/irp/main.do', '다양한 비전통 자산에 투자됩니다.', 16000, 920, 310, 0.77),
                                                                                                                                                                                                                 ('A26', '에셋플러스자산운용 성장주 연금', 0.0, '연금저축펀드', 'FALSE', 0.0, 27.0, '2027-03-01', '에셋플러스자산운용', 65, 1, 'https://www.assetplus.co.kr/product/fund_pension.html', '성장주 중심의 높은 변동성.', 31500, 1900, 510, 0.94),
                                                                                                                                                                                                                 ('A27', '신영증권 가치투자형 연금', 0.0, 'IRP', 'FALSE', 0.0, 8.2, '2028-10-05', '신영증권', 60, 2, 'https://www.shinyoung.com/retirement/pension_irp.jsp', '단기 성과보다 장기 가치 중시.', 22000, 1300, 180, 0.81),
                                                                                                                                                                                                                 ('A28', '한국투자신탁운용 액티브 연금', 0.0, 'DC', 'FALSE', 0.0, 19.5, '2026-07-25', '한국투자신탁운용', 64, 2, 'https://www.kim.co.kr/main/irp/product.jsp', '펀드매니저의 운용 역량에 따라 달라짐.', 28500, 1720, 360, 0.89),
                                                                                                                                                                                                                 ('A29', 'KB손해보험 액티브 자산 연금', 0.0, '연금저축보험', 'FALSE', 0.0, 15.0, '2029-01-20', 'KB손해보험', 56, 3, 'https://www.kbinsure.co.kr/CI/CGDCP00000.do', '보험사 상품이지만 투자 비중이 높습니다.', 19000, 1100, 240, 0.77),
                                                                                                                                                                                                                 ('A30', 'BNK경남은행 글로벌 성장 투자 연금', 0.0, 'IRP', 'FALSE', 0.0, 21.0, '2027-04-10', 'BNK경남은행', 59, 1, 'https://www.knbank.co.kr/customer/retirement_irp.jsp', '글로벌 시장의 동향에 민감합니다.', 30500, 1880, 500, 0.95),
                                                                                                                                                                                                                 ('A31', '한국투자밸류자산운용 가치성장 연금', 0.0, '연금저축펀드', 'FALSE', 0.0, 11.0, '2028-06-01', '한국투자밸류자산운용', 62, 2, 'https://www.koreainvestvalue.com/products/pension_fund.jsp', '장기적 관점의 가치 투자를 지향합니다.', 23500, 1400, 200, 0.84),
                                                                                                                                                                                                                 ('A32', 'SK증권 인공지능 자산운용 연금', 0.0, 'DC', 'FALSE', 0.0, 14.5, '2026-11-05', 'SK증권', 60, 2, 'https://www.sks.co.kr/pension/main.do', 'AI 시스템의 성과에 따라 달라집니다.', 25000, 1500, 260, 0.85),
                                                                                                                                                                                                                 ('A33', '푸본현대생명 공격형 변액연금', 0.0, '연금저축보험', 'FALSE', 0.0, -7.0, '2029-03-01', '푸본현대생명', 51, 3, 'https://www.fubonhyundai.com/product/pension/variable_pension.do', '시장 침체 시 손실이 커질 수 있습니다.', 17000, 950, 330, 0.76),
                                                                                                                                                                                                                 ('A34', '한화투자증권 글로벌 메가트렌드 연금', 0.0, 'IRP', 'FALSE', 0.0, 29.5, '2027-06-20', '한화투자증권', 67, 1, 'https://www.hanwhawm.com/cust/retirement/irp.cmd', '미래 성장 산업에 집중 투자됩니다.', 34000, 2150, 620, 0.98),
                                                                                                                                                                                                                 ('A35', '케이뱅크 디지털자산 투자 연금', 0.0, '연금저축펀드', 'FALSE', 0.0, 18.8, '2028-01-01', '케이뱅크', 55, 2, 'https://www.kbanknow.com/pension/irp.html', '디지털 자산의 높은 변동성.', 26000, 1550, 300, 0.87),
                                                                                                                                                                                                                 ('A36', '카카오페이증권 테마형 연금', 0.0, 'IRP', 'FALSE', 0.0, 23.0, '2026-10-10', '카카오페이증권', 60, 1, 'https://www.kakaopaysec.com/products/irp/intro', '유행에 민감한 테마에 투자됩니다.', 31000, 1900, 500, 0.90),
                                                                                                                                                                                                                 ('A37', 'NH농협생명 고위험 주식형 연금', 0.0, '연금저축보험', 'FALSE', 0.0, -9.5, '2029-07-05', 'NH농협생명', 48, 3, 'https://www.nhlife.co.kr/retirement/pension/product/P02_02.jsp', '주식 시장의 등락에 따라 손실 가능성.', 15500, 900, 370, 0.75),
                                                                                                                                                                                                                 ('A38', 'IBK투자증권 인컴 & 성장 연금', 0.0, 'DC', 'FALSE', 0.0, 12.0, '2027-02-20', 'IBK투자증권', 63, 2, 'https://www.ibks.com/personal/irp.html', '수익과 성장을 동시에 추구합니다.', 24000, 1450, 240, 0.82),
                                                                                                                                                                                                                 ('A39', '유진저축은행 P2P 연계 연금', 0.0, '연금저축펀드', 'FALSE', 0.0, 10.0, '2028-04-01', '유진저축은행', 52, 3, 'https://www.eugenelb.co.kr/loan/p2p.jsp', 'P2P 투자의 특성상 고위험.', 18500, 1080, 280, 0.78),
                                                                                                                                                                                                                 ('A40', '메리츠화재 고수익 투자형 연금', 0.0, 'IRP', 'FALSE', 0.0, 26.0, '2026-09-15', '메리츠화재', 57, 1, 'https://www.meritzfire.com/customer/retirement_pension/retirement_pension_irp.jsp', '보험사이지만 투자 비중이 매우 높습니다.', 32500, 2050, 600, 0.96),
                                                                                                                                                                                                                 ('A41', '신한자산운용 TDF 공격형', 0.0, '연금저축펀드', 'FALSE', 0.0, 17.5, '2027-01-01', '신한자산운용', 65, 2, 'https://www.shinhanbnpp.com/product/fund_list_pension.jsp', '목표 시점에 따라 위험 자산 비중이 높습니다.', 27800, 1700, 320, 0.88),
                                                                                                                                                                                                                 ('A42', '하이투자증권 해외 기술주 연금', 0.0, 'DC', 'FALSE', 0.0, 31.0, '2028-05-20', '하이투자증권', 68, 1, 'https://www.hi-ib.com/irp/irp01.do', '해외 특정 산업에 대한 높은 노출도.', 36000, 2300, 680, 0.99),
                                                                                                                                                                                                                 ('A43', '교보악사자산운용 글로벌 성장액티브 연금', 0.0, 'IRP', 'FALSE', 0.0, 10.8, '2026-08-15', '교보악사자산운용', 59, 3, 'https://www.kyoboaxa.com/fund/irp_fund.jsp', '펀드 매니저의 재량에 따라 수익률이 달라집니다.', 20000, 1150, 250, 0.79),
                                                                                                                                                                                                                 ('A44', '삼성화재 고수익 혼합형 연금', 0.0, '연금저축보험', 'FALSE', 0.0, 13.0, '2029-04-01', '삼성화재', 54, 2, 'https://www.samsungfire.com/personal/product/pension/retirement-pension-irp.do', '투자형 상품으로 원금 손실 가능성.', 21500, 1280, 280, 0.81),
                                                                                                                                                                                                                 ('A45', '신한투자증권 혁신성장형 연금', 0.0, '연금저축펀드', 'FALSE', 0.0, 24.0, '2027-07-10', '신한투자증권', 66, 1, 'https://www.shinhaninvest.com/pension/retirement', '파괴적 혁신 기업에 투자됩니다.', 33500, 2050, 580, 0.95),
                                                                                                                                                                                                                 ('A46', '우리은행 고위험 포트폴리오 연금', 0.0, 'IRP', 'FALSE', 0.0, 16.0, '2028-09-25', '우리은행', 58, 2, 'https://spot.wooribank.com/pot/pib/IRPView.act', '다양한 고위험 자산에 분산 투자됩니다.', 25500, 1500, 300, 0.86),
                                                                                                                                                                                                                 ('A47', '흥국화재 성장형 변액연금', 0.0, '연금저축보험', 'FALSE', 0.0, 8.5, '2026-12-01', '흥국화재', 50, 3, 'https://www.heungkukfire.co.kr/product/retirement/irp.do', '보험사 상품이지만 주식 비중이 높습니다.', 18000, 1000, 260, 0.77),
                                                                                                                                                                                                                 ('A48', 'IBK연금보험 글로벌 투자형 연금', 0.0, 'DC', 'FALSE', 0.0, 20.0, '2029-05-10', 'IBK연금보험', 62, 1, 'https://www.ibkpension.co.kr/product/variable_pension.jsp', '해외 시장의 큰 흐름에 따라 달라집니다.', 30000, 1850, 520, 0.92),
                                                                                                                                                                                                                 ('A49', '대신증권 중국 성장주 연금', 0.0, '연금저축펀드', 'FALSE', 0.0, 15.0, '2027-03-20', '대신증권', 57, 2, 'https://www.daishin.com/pension/main.do', '중국 시장의 정책 및 경제 변동성.', 24000, 1400, 300, 0.84),
                                                                                                                                                                                                                 ('A50', '유안타증권 AI기반 투자 연금', 0.0, 'IRP', 'FALSE', 0.0, 22.5, '2028-08-01', '유안타증권', 60, 1, 'https://www.myasset.com/main/pension/overview.jsp', 'AI 모델의 성과에 따라 위험 노출.', 31800, 1980, 550, 0.90),
                                                                                                                                                                                                                 ('C11', '우리은행 확정금리 안정 연금', 2.9, '확정급여형', 'TRUE', 1.9, 3.1, '2027-11-20', '우리은행', 90, 5, 'https://spot.wooribank.com/pot/pib/IRPView.act', '인플레이션 헤지가 필요할 수 있습니다.', 13000, 800, 40, 0.28),
                                                                                                                                                                                                                 ('C12', 'SC제일은행 평생 이율보증 연금', 2.2, '연금저축보험', 'TRUE', 1.0, 2.3, '2028-05-15', 'SC제일은행', 85, 4, 'https://www.standardchartered.co.kr/np/kr/RetirementPension.jsp', '복리 효과를 장기적으로 누리세요.', 9500, 500, 25, 0.40),
                                                                                                                                                                                                                 ('C13', '산업은행 채권형 안전 IRP', 3.0, 'IRP', 'TRUE', 2.0, 3.5, '2026-10-01', 'KDB산업은행', 92, 6, 'https://www.kdb.co.kr/ib/pr/retirePensionInfo.do', '안정성에 중점을 둔 상품입니다.', 14500, 900, 55, 0.18),
                                                                                                                                                                                                                 ('C14', '수협은행 확정금리 퇴직연금', 2.7, '확정급여형', 'TRUE', 1.8, 3.0, '2029-03-01', '수협은행', 88, 5, 'https://www.suhyup-bank.com/html/product/retirement/retirement_01.jsp', '원금 보장이 강점입니다.', 11000, 650, 35, 0.30),
                                                                                                                                                                                                                 ('C15', '우체국 예금형 연금저축', 2.0, '연금저축보험', 'TRUE', 1.0, 1.9, '2027-04-20', '우체국보험', 80, 6, 'https://www.epostbank.go.kr/pension/prod_01.jsp', '낮은 금리라도 세액공제 혜택이 있습니다.', 7000, 300, 20, 0.48),
                                                                                                                                                                                                                 ('C16', 'MG새마을금고 채권형 연금', 2.5, 'IRP', 'TRUE', 1.5, 2.8, '2028-08-01', 'MG새마을금고', 87, 4, 'https://www.kfcc.co.kr/irplanding/irplanding.jsp', '채권 금리 변동에 영향을 받을 수 있습니다.', 10800, 620, 38, 0.35),
                                                                                                                                                                                                                 ('C17', '신협 예금형 연금저축', 2.1, '연금저축보험', 'TRUE', 1.0, 2.0, '2029-01-05', '신협', 82, 5, 'https://www.cu.co.kr/product/retirement/retirement_01.do', '안정적인 노후 자금 마련에 적합합니다.', 8500, 480, 28, 0.42),
                                                                                                                                                                                                                 ('C18', '카카오뱅크 퇴직연금 예금', 2.3, '확정급여형', 'TRUE', 1.5, 2.5, '2027-06-10', '카카오뱅크', 89, 6, 'https://www.kakaobank.com/products/pension', '간편한 모바일 가입이 장점입니다.', 12000, 700, 45, 0.22),
                                                                                                                                                                                                                 ('C19', '케이뱅크 퇴직연금 예금', 2.4, '확정급여형', 'TRUE', 1.6, 2.6, '2028-10-01', '케이뱅크', 88, 5, 'https://www.kbanknow.com/pension/irp.html', '비대면으로 편리하게 가입 가능.', 11500, 680, 42, 0.27),
                                                                                                                                                                                                                 ('C20', '새마을금고 예금형 개인연금', 2.25, '연금저축보험', 'TRUE', 1.1, 2.15, '2029-02-28', 'MG새마을금고', 81, 4, 'https://www.kfcc.co.kr/irplanding/irplanding.jsp', '이자율은 변동될 수 있습니다.', 7800, 350, 22, 0.43),
                                                                                                                                                                                                                 ('C21', '한국씨티은행 확정금리 IRP', 2.7, 'IRP', 'TRUE', 1.7, 3.0, '2027-09-15', '한국씨티은행', 90, 5, 'https://www.citibank.co.kr/CrdPrdRetirementPnsn01.do', '외국계 은행의 안정성을 추구합니다.', 10500, 600, 30, 0.30),
                                                                                                                                                                                                                 ('C22', 'BNK부산은행 안정형 DB', 2.95, '확정급여형', 'TRUE', 1.95, 3.25, '2028-03-25', 'BNK부산은행', 91, 6, 'https://www.busanbank.co.kr/ib2019/retrieveRetirePensionList.act', '퇴직 시 안정적인 연금 수령이 가능합니다.', 12500, 750, 48, 0.20),
                                                                                                                                                                                                                 ('C23', 'DGB대구은행 이율보증형 연금', 2.6, '연금저축보험', 'TRUE', 1.6, 2.8, '2026-11-01', 'DGB대구은행', 86, 4, 'https://www.dgb.co.kr/product/retirement/pension_irp.jsp', '최저 보증 이율로 안심하고 가입하세요.', 9200, 520, 33, 0.36),
                                                                                                                                                                                                                 ('C24', '광주은행 확정금리 IRP', 2.8, 'IRP', 'TRUE', 1.8, 3.1, '2029-07-10', '광주은행', 89, 5, 'https://www.kjbank.com/bank/product/irp/irp_01.jsp', '세액 공제 혜택을 받을 수 있습니다.', 11300, 680, 45, 0.25),
                                                                                                                                                                                                                 ('C25', '전북은행 채권형 퇴직연금', 2.75, '확정급여형', 'TRUE', 1.75, 2.95, '2027-05-01', '전북은행', 87, 5, 'https://www.jbbank.co.kr/banking/retirement_pension.jsp', '채권형 자산으로 안정적 운용.', 10000, 550, 30, 0.32),
                                                                                                                                                                                                                 ('C26', '제주은행 이율 보증 연금', 2.4, '연금저축보험', 'TRUE', 1.4, 2.6, '2028-11-05', '제주은행', 84, 4, 'https://www.jejusafe.com/product/pension/main.do', '지역 은행의 안정성과 편리함.', 8000, 420, 20, 0.40),
                                                                                                                                                                                                                 ('C27', '새마을금고 확정금리 IRP', 2.65, 'IRP', 'TRUE', 1.65, 2.85, '2029-04-25', 'MG새마을금고', 88, 5, 'https://www.kfcc.co.kr/irplanding/irplanding.jsp', '세금 혜택을 고려한 연금 준비.', 11000, 650, 35, 0.29),
                                                                                                                                                                                                                 ('C28', '신협 예금형 퇴직연금', 2.5, '확정급여형', 'TRUE', 1.5, 2.7, '2027-02-10', '신협', 86, 6, 'https://www.cu.co.kr/product/retirement/retirement_01.do', '노후 자금의 안전한 운용.', 12000, 720, 40, 0.23),
                                                                                                                                                                                                                 ('C29', '케이뱅크 이율보증형 연금', 2.1, '연금저축보험', 'TRUE', 1.0, 2.0, '2028-06-01', '케이뱅크', 80, 4, 'https://www.kbanknow.com/pension/irp.html', '비대면으로 편리하게 관리.', 7500, 380, 20, 0.45),
                                                                                                                                                                                                                 ('C30', '카카오뱅크 예금형 개인연금', 2.05, '연금저축보험', 'TRUE', 1.05, 1.95, '2029-09-01', '카카오뱅크', 80, 5, 'https://www.kakaobank.com/products/pension', '간편한 가입 절차.', 7200, 340, 18, 0.46),
                                                                                                                                                                                                                 ('C31', 'OK저축은행 확정금리 연금', 3.2, '연금저축보험', 'TRUE', 2.2, 3.6, '2027-01-15', 'OK저축은행', 85, 4, 'https://www.okbank.co.kr/product/pension/main.jsp', '고정금리로 안정적인 수익.', 9800, 560, 32, 0.35),
                                                                                                                                                                                                                 ('C32', '웰컴저축은행 이율보증형 연금', 2.8, 'IRP', 'TRUE', 1.8, 3.0, '2028-04-01', '웰컴저축은행', 87, 5, 'https://www.welcomebank.co.kr/retirement/main.do', '최저 보증으로 원금 손실 위험 없음.', 10200, 610, 39, 0.28),
                                                                                                                                                                                                                 ('C33', 'JT친애저축은행 채권형 연금', 3.0, '확정급여형', 'TRUE', 2.0, 3.3, '2026-09-20', 'JT친애저축은행', 89, 6, 'https://www.jtchin.co.kr/product/pension/main.do', '안전한 자산에 집중 투자.', 11800, 700, 43, 0.19),
                                                                                                                                                                                                                 ('C34', 'SBI저축은행 예금형 연금', 2.9, '연금저축보험', 'TRUE', 1.9, 3.1, '2029-06-15', 'SBI저축은행', 86, 4, 'https://www.sbisavings.co.kr/retirement/irp/intro.do', '정기적인 이자 수익을 기대할 수 있습니다.', 9500, 530, 27, 0.34),
                                                                                                                                                                                                                 ('C35', '고려저축은행 이율보증형 연금', 2.7, 'IRP', 'TRUE', 1.7, 2.9, '2027-10-01', '고려저축은행', 84, 5, 'https://www.koryobank.com/irp/main.do', '목돈 마련에 유리합니다.', 9000, 480, 25, 0.38),
                                                                                                                                                                                                                 ('C36', '동원제일저축은행 확정금리 연금', 2.5, '확정급여형', 'TRUE', 1.5, 2.7, '2028-02-05', '동원제일저축은행', 88, 6, 'https://www.dongwonj.com/savings/irp.do', '안전한 노후를 위한 선택.', 10500, 600, 35, 0.26),
                                                                                                                                                                                                                 ('C37', '페퍼저축은행 예금형 연금', 2.3, '연금저축보험', 'TRUE', 1.3, 2.4, '2029-05-20', '페퍼저축은행', 82, 4, 'https://www.pepperbank.co.kr/product/pension/irp_intro.do', '납입금 보장에 초점을 둡니다.', 7700, 400, 22, 0.41),
                                                                                                                                                                                                                 ('C38', '대신저축은행 이율보증형 연금', 2.6, 'IRP', 'TRUE', 1.6, 2.8, '2027-03-01', '대신저축은행', 85, 5, 'https://www.daishin-s.com/customer/product_info/irp.html', '정해진 이율로 안정적인 운용.', 9300, 500, 28, 0.36),
                                                                                                                                                                                                                 ('C39', 'KB저축은행 채권형 개인연금', 2.9, '연금저축펀드', 'TRUE', 1.9, 3.2, '2028-07-25', 'KB저축은행', 87, 4, 'https://www.kbsb.co.kr/personal/pension/irp_info.do', '저축은행이지만 안정성 높은 채권형.', 10800, 650, 38, 0.31),
                                                                                                                                                                                                                 ('C40', '신한저축은행 확정금리 IRP', 3.1, 'IRP', 'TRUE', 2.1, 3.4, '2026-12-10', '신한저축은행', 90, 6, 'https://www.shinhan.co.kr/irb/product/irp_info.do', '최고 수준의 안전성을 자랑합니다.', 12200, 750, 45, 0.20),
                                                                                                                                                                                                                 ('C41', 'IBK저축은행 이율보증형 연금', 2.6, '연금저축보험', 'TRUE', 1.6, 2.8, '2029-01-01', 'IBK저축은행', 83, 4, 'https://www.ibksavings.co.kr/product/irp_info.jsp', '확정 이율로 변동성 없는 수익 추구.', 9000, 500, 29, 0.37),
                                                                                                                                                                                                                 ('C42', '다올저축은행 예금형 퇴직연금', 2.45, '확정급여형', 'TRUE', 1.45, 2.65, '2027-04-15', '다올저축은행', 85, 5, 'https://www.daolsb.co.kr/retirement/irp.do', '간편하고 안전한 노후 대비.', 10000, 580, 32, 0.34),
                                                                                                                                                                                                                 ('C43', 'JT저축은행 확정금리 개인연금', 3.05, '연금저축보험', 'TRUE', 2.05, 3.35, '2028-09-10', 'JT저축은행', 88, 4, 'https://www.jtsavingsbank.co.kr/product/pension/main.do', '높은 확정금리로 만족스러운 수익.', 11500, 680, 40, 0.27),
                                                                                                                                                                                                                 ('C44', '키움저축은행 채권형 IRP', 2.75, 'IRP', 'TRUE', 1.75, 2.95, '2026-10-20', '키움저축은행', 86, 5, 'https://www.kiwoomsb.co.kr/main/product/irp.do', '안정적인 채권 투자로 위험 최소화.', 9700, 550, 30, 0.33),
                                                                                                                                                                                                                 ('C45', '하나저축은행 이율보증형 연금', 2.35, '연금저축보험', 'TRUE', 1.35, 2.55, '2029-02-01', '하나저축은행', 81, 4, 'https://www.hanasb.co.kr/product/irp_info.do', '최저 수익률 보장으로 안심.', 7800, 420, 23, 0.42),
                                                                                                                                                                                                                 ('C46', 'NH저축은행 확정금리 퇴직연금', 2.95, '확정급여형', 'TRUE', 1.95, 3.25, '2027-07-05', 'NH저축은행', 89, 6, 'https://www.nhsavings.co.kr/product/irp_info.do', '농협의 신뢰와 함께하는 안정적인 연금.', 11200, 680, 38, 0.21),
                                                                                                                                                                                                                 ('C47', '유진저축은행 예금형 개인연금', 2.2, '연금저축보험', 'TRUE', 1.2, 2.3, '2028-11-15', '유진저축은행', 80, 5, 'https://www.eugenelb.co.kr/loan/p2p.jsp', '개인연금으로 세제 혜택과 안정성.', 7500, 400, 20, 0.44),
                                                                                                                                                                                                                 ('C48', 'JT친애저축은행 이율보증형 IRP', 2.85, 'IRP', 'TRUE', 1.85, 3.05, '2029-03-01', 'JT친애저축은행', 87, 4, 'https://www.jtchin.co.kr/product/pension/main.do', 'IRP로 노후 자금을 효율적으로 관리.', 9800, 550, 33, 0.30),
                                                                                                                                                                                                                 ('C49', 'SBI저축은행 확정금리 연금저축', 3.0, '연금저축보험', 'TRUE', 2.0, 3.2, '2027-05-25', 'SBI저축은행', 89, 5, 'https://www.sbisavings.co.kr/retirement/irp/intro.do', '안정적인 수익률과 세액공제 혜택.', 11000, 650, 37, 0.25),
                                                                                                                                                                                                                 ('C50', '대신저축은행 예금형 퇴직연금', 2.65, '확정급여형', 'TRUE', 1.65, 2.85, '2028-10-01', '대신저축은행', 88, 6, 'https://www.daishin-s.com/customer/product_info/irp.html', '원금 보장으로 안전한 퇴직연금.', 12000, 700, 42, 0.22);
UPDATE `savings` SET `min_save_term` = '1', `max_save_term` = '2' WHERE (`id` = 'S1');
UPDATE `savings` SET `min_save_term` = '3', `max_save_term` = '124' WHERE (`id` = 'S10');
UPDATE `savings` SET `min_save_term` = '12', `max_save_term` = '1245' WHERE (`id` = 'S11');
UPDATE `savings` SET `min_save_term` = '12', `max_save_term` = '123' WHERE (`id` = 'S12');
UPDATE `savings` SET `min_save_term` = '5', `max_save_term` = '35' WHERE (`id` = 'S13');
UPDATE `savings` SET `min_save_term` = '34', `max_save_term` = '765' WHERE (`id` = 'S14');
UPDATE `savings` SET `min_save_term` = '1', `max_save_term` = '2' WHERE (`id` = 'S15');
UPDATE `savings` SET `min_save_term` = '4', `max_save_term` = '76' WHERE (`id` = 'S16');
UPDATE `savings` SET `min_save_term` = '412', `max_save_term` = '2453' WHERE (`id` = 'S17');
UPDATE `savings` SET `min_save_term` = '12', `max_save_term` = '12' WHERE (`id` = 'S18');
UPDATE `savings` SET `min_save_term` = '3', `max_save_term` = '33' WHERE (`id` = 'S19');
UPDATE `savings` SET `min_save_term` = '12', `max_save_term` = '23' WHERE (`id` = 'S2');
UPDATE `savings` SET `min_save_term` = '54', `max_save_term` = '156' WHERE (`id` = 'S20');
UPDATE `savings` SET `min_save_term` = '12', `max_save_term` = '122' WHERE (`id` = 'S21');
UPDATE `savings` SET `min_save_term` = '12', `max_save_term` = '12' WHERE (`id` = 'S22');
UPDATE `savings` SET `min_save_term` = '12', `max_save_term` = '13' WHERE (`id` = 'S23');
UPDATE `savings` SET `min_save_term` = '14', `max_save_term` = '15' WHERE (`id` = 'S24');
UPDATE `savings` SET `min_save_term` = '16', `max_save_term` = '17' WHERE (`id` = 'S25');
UPDATE `savings` SET `min_save_term` = '18', `max_save_term` = '19' WHERE (`id` = 'S26');
UPDATE `savings` SET `min_save_term` = '12', `max_save_term` = '23' WHERE (`id` = 'S27');
UPDATE `savings` SET `min_save_term` = '12', `max_save_term` = '24' WHERE (`id` = 'S28');
UPDATE `savings` SET `min_save_term` = '12', `max_save_term` = '36' WHERE (`id` = 'S29');
UPDATE `savings` SET `min_save_term` = '3', `max_save_term` = '12' WHERE (`id` = 'S3');
UPDATE `savings` SET `min_save_term` = '1', `max_save_term` = '12' WHERE (`id` = 'S30');
UPDATE `savings` SET `min_save_term` = '12', `max_save_term` = '12' WHERE (`id` = 'S4');
UPDATE `savings` SET `min_save_term` = '3', `max_save_term` = '36' WHERE (`id` = 'S5');
UPDATE `savings` SET `min_save_term` = '2', `max_save_term` = '24' WHERE (`id` = 'S6');
UPDATE `savings` SET `min_save_term` = '2', `max_save_term` = '12' WHERE (`id` = 'S7');
UPDATE `savings` SET `min_save_term` = '2', `max_save_term` = '36' WHERE (`id` = 'S8');
UPDATE `savings` SET `min_save_term` = '1', `max_save_term` = '6' WHERE (`id` = 'S9');

select * from forex;
INSERT INTO users (`id`, `email`, `name`, `password`, `phone`, `birth`, `risk`, `user_klg`, `algo_code`, `reg_date`, `klg_renew_date`, `risk_renew_date`, `docu_renew_date`, yieldScore, risk_score, cost_score, liquidity_score, complexity_score) VALUES ('1', 'a@a.a', 'admin', '1234', '01012345678', '2000-03-04', '1', '3', '87', '2025-03-04', '2025-03-04', '2025-03-04', '2025-03-04', 0.3,0.3,0.3,0.3,0.3);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (1, '2024-07-24', 11902828, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (2, '2024-07-25', 11972764, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (3, '2024-07-26', 18745558, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (4, '2024-07-27', 19177486, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (5, '2024-07-28', 19509209, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (6, '2024-07-29', 18919077, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (7, '2024-07-30', 11049985, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (8, '2024-07-31', 17226235, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (9, '2024-08-01', 10968828, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (10, '2024-08-02', 16626346, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (11, '2024-08-03', 13874667, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (12, '2024-08-04', 16783256, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (13, '2024-08-05', 11665049, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (14, '2024-08-06', 19696835, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (15, '2024-08-07', 16903725, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (16, '2024-08-08', 10167547, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (17, '2024-08-09', 15018824, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (18, '2024-08-10', 13713763, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (19, '2024-08-11', 15475126, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (20, '2024-08-12', 16082440, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (21, '2024-08-13', 18960290, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (22, '2024-08-14', 10779715, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (23, '2024-08-15', 18173449, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (24, '2024-08-16', 11270180, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (25, '2024-08-17', 18359127, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (26, '2024-08-18', 19020021, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (27, '2024-08-19', 11574082, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (28, '2024-08-20', 18209278, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (29, '2024-08-21', 15686535, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (30, '2024-08-22', 13962795, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (31, '2024-08-23', 18835939, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (32, '2024-08-24', 10459034, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (33, '2024-08-25', 13596843, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (34, '2024-08-26', 15932409, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (35, '2024-08-27', 15876159, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (36, '2024-08-28', 11783696, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (37, '2024-08-29', 14786102, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (38, '2024-08-30', 19334682, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (39, '2024-08-31', 18451584, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (40, '2024-09-01', 13152625, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (41, '2024-09-02', 16583940, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (42, '2024-09-03', 17258417, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (43, '2024-09-04', 15817181, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (44, '2024-09-05', 13249806, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (45, '2024-09-06', 11053994, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (46, '2024-09-07', 12943828, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (47, '2024-09-08', 16018185, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (48, '2024-09-09', 11264009, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (49, '2024-09-10', 19849021, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (50, '2024-09-11', 15488201, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (51, '2024-09-12', 16421572, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (52, '2024-09-13', 11731715, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (53, '2024-09-14', 13245582, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (54, '2024-09-15', 11277656, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (55, '2024-09-16', 14004205, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (56, '2024-09-17', 10530361, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (57, '2024-09-18', 17760428, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (58, '2024-09-19', 14332464, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (59, '2024-09-20', 11837417, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (60, '2024-09-21', 15147381, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (61, '2024-09-22', 10030594, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (62, '2024-09-23', 19664262, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (63, '2024-09-24', 17542918, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (64, '2024-09-25', 10617054, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (65, '2024-09-26', 10293293, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (66, '2024-09-27', 11580147, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (67, '2024-09-28', 16767954, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (68, '2024-09-29', 14414626, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (69, '2024-09-30', 12345754, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (70, '2024-10-01', 11110614, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (71, '2024-10-02', 14300760, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (72, '2024-10-03', 15047808, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (73, '2024-10-04', 15351302, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (74, '2024-10-05', 13867364, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (75, '2024-10-06', 14206925, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (76, '2024-10-07', 13999704, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (77, '2024-10-08', 13930813, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (78, '2024-10-09', 10478832, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (79, '2024-10-10', 10631498, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (80, '2024-10-11', 15456613, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (81, '2024-10-12', 10096901, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (82, '2024-10-13', 15599264, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (83, '2024-10-14', 19434592, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (84, '2024-10-15', 18194670, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (85, '2024-10-16', 15390151, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (86, '2024-10-17', 17065090, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (87, '2024-10-18', 16204760, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (88, '2024-10-19', 17982735, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (89, '2024-10-20', 18713517, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (90, '2024-10-21', 10606230, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (91, '2024-10-22', 15442787, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (92, '2024-10-23', 13055225, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (93, '2024-10-24', 15160360, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (94, '2024-10-25', 17096140, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (95, '2024-10-26', 17262209, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (96, '2024-10-27', 14517774, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (97, '2024-10-28', 10519875, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (98, '2024-10-29', 14294359, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (99, '2024-10-30', 11658863, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (100, '2024-10-31', 12263492, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (101, '2024-11-01', 10103448, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (102, '2024-11-02', 14570430, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (103, '2024-11-03', 13873855, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (104, '2024-11-04', 19693358, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (105, '2024-11-05', 12144737, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (106, '2024-11-06', 18663692, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (107, '2024-11-07', 18360045, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (108, '2024-11-08', 13750951, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (109, '2024-11-09', 15996258, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (110, '2024-11-10', 14228456, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (111, '2024-11-11', 11406731, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (112, '2024-11-12', 13361110, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (113, '2024-11-13', 18937089, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (114, '2024-11-14', 18674465, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (115, '2024-11-15', 17643346, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (116, '2024-11-16', 16266697, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (117, '2024-11-17', 14495282, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (118, '2024-11-18', 15295630, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (119, '2024-11-19', 10438789, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (120, '2024-11-20', 10797405, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (121, '2024-11-21', 11301524, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (122, '2024-11-22', 16422282, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (123, '2024-11-23', 14208407, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (124, '2024-11-24', 15966984, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (125, '2024-11-25', 17139999, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (126, '2024-11-26', 19521360, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (127, '2024-11-27', 18192588, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (128, '2024-11-28', 19743786, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (129, '2024-11-29', 19164600, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (130, '2024-11-30', 17921184, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (131, '2024-12-01', 16078894, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (132, '2024-12-02', 11360545, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (133, '2024-12-03', 10115873, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (134, '2024-12-04', 16542141, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (135, '2024-12-05', 12302316, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (136, '2024-12-06', 17867046, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (137, '2024-12-07', 19384325, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (138, '2024-12-08', 12709683, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (139, '2024-12-09', 10005430, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (140, '2024-12-10', 13683003, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (141, '2024-12-11', 18606229, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (142, '2024-12-12', 14662703, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (143, '2024-12-13', 17256539, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (144, '2024-12-14', 15656978, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (145, '2024-12-15', 15468071, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (146, '2024-12-16', 19630443, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (147, '2024-12-17', 17863441, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (148, '2024-12-18', 17693323, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (149, '2024-12-19', 14146740, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (150, '2024-12-20', 13949990, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (151, '2024-12-21', 15472390, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (152, '2024-12-22', 16327791, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (153, '2024-12-23', 14483291, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (154, '2024-12-24', 13074509, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (155, '2024-12-25', 10740490, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (156, '2024-12-26', 13760799, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (157, '2024-12-27', 16317660, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (158, '2024-12-28', 15326577, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (159, '2024-12-29', 12236642, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (160, '2024-12-30', 10189367, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (161, '2024-12-31', 15285593, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (162, '2025-01-01', 12949005, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (163, '2025-01-02', 14724059, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (164, '2025-01-03', 10084886, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (165, '2025-01-04', 16053613, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (166, '2025-01-05', 13531634, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (167, '2025-01-06', 19006011, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (168, '2025-01-07', 15240936, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (169, '2025-01-08', 15969533, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (170, '2025-01-09', 18555400, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (171, '2025-01-10', 12781021, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (172, '2025-01-11', 10383842, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (173, '2025-01-12', 10762136, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (174, '2025-01-13', 13347202, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (175, '2025-01-14', 14532454, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (176, '2025-01-15', 11698589, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (177, '2025-01-16', 10364922, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (178, '2025-01-17', 18882116, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (179, '2025-01-18', 16351812, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (180, '2025-01-19', 13271091, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (181, '2025-01-20', 16486311, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (182, '2025-01-21', 14046933, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (183, '2025-01-22', 19751339, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (184, '2025-01-23', 17812321, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (185, '2025-01-24', 15926910, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (186, '2025-01-25', 16183201, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (187, '2025-01-26', 19615147, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (188, '2025-01-27', 18588481, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (189, '2025-01-28', 16417842, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (190, '2025-01-29', 16635788, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (191, '2025-01-30', 19561091, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (192, '2025-01-31', 12177850, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (193, '2025-02-01', 15810593, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (194, '2025-02-02', 17062080, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (195, '2025-02-03', 15205408, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (196, '2025-02-04', 17538635, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (197, '2025-02-05', 10317111, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (198, '2025-02-06', 11827540, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (199, '2025-02-07', 14461234, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (200, '2025-02-08', 15056503, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (201, '2025-02-09', 11216867, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (202, '2025-02-10', 17574224, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (203, '2025-02-11', 12029927, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (204, '2025-02-12', 18555874, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (205, '2025-02-13', 16323525, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (206, '2025-02-14', 13898652, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (207, '2025-02-15', 12865343, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (208, '2025-02-16', 10166141, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (209, '2025-02-17', 18316861, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (210, '2025-02-18', 13759870, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (211, '2025-02-19', 17824895, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (212, '2025-02-20', 18636230, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (213, '2025-02-21', 15488758, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (214, '2025-02-22', 13365060, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (215, '2025-02-23', 10157732, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (216, '2025-02-24', 15395511, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (217, '2025-02-25', 15608115, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (218, '2025-02-26', 12538495, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (219, '2025-02-27', 12772975, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (220, '2025-02-28', 11891668, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (221, '2025-03-01', 19198090, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (222, '2025-03-02', 19911177, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (223, '2025-03-03', 13422897, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (224, '2025-03-04', 16293189, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (225, '2025-03-05', 19949818, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (226, '2025-03-06', 16686443, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (227, '2025-03-07', 13519599, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (228, '2025-03-08', 12404167, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (229, '2025-03-09', 10246869, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (230, '2025-03-10', 14654066, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (231, '2025-03-11', 18002117, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (232, '2025-03-12', 17915113, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (233, '2025-03-13', 19099449, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (234, '2025-03-14', 12189759, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (235, '2025-03-15', 19390397, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (236, '2025-03-16', 13863911, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (237, '2025-03-17', 13899093, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (238, '2025-03-18', 16504562, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (239, '2025-03-19', 15525259, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (240, '2025-03-20', 15906198, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (241, '2025-03-21', 12697245, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (242, '2025-03-22', 16186928, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (243, '2025-03-23', 15531832, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (244, '2025-03-24', 19621520, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (245, '2025-03-25', 13661659, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (246, '2025-03-26', 19048598, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (247, '2025-03-27', 13696327, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (248, '2025-03-28', 11941709, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (249, '2025-03-29', 14874227, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (250, '2025-03-30', 19806629, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (251, '2025-03-31', 11703335, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (252, '2025-04-01', 12348890, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (253, '2025-04-02', 16874038, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (254, '2025-04-03', 16280723, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (255, '2025-04-04', 15514027, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (256, '2025-04-05', 13853009, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (257, '2025-04-06', 12432241, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (258, '2025-04-07', 14212863, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (259, '2025-04-08', 14294898, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (260, '2025-04-09', 12991673, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (261, '2025-04-10', 19729765, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (262, '2025-04-11', 13839839, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (263, '2025-04-12', 13404704, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (264, '2025-04-13', 19516558, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (265, '2025-04-14', 19388047, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (266, '2025-04-15', 14434757, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (267, '2025-04-16', 16164487, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (268, '2025-04-17', 14663110, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (269, '2025-04-18', 15689652, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (270, '2025-04-19', 13060615, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (271, '2025-04-20', 12713013, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (272, '2025-04-21', 13140071, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (273, '2025-04-22', 19037249, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (274, '2025-04-23', 14926376, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (275, '2025-04-24', 19116262, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (276, '2025-04-25', 15643574, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (277, '2025-04-26', 12691543, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (278, '2025-04-27', 11793447, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (279, '2025-04-28', 15553026, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (280, '2025-04-29', 19386950, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (281, '2025-04-30', 17043586, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (282, '2025-05-01', 17381973, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (283, '2025-05-02', 19535511, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (284, '2025-05-03', 12532626, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (285, '2025-05-04', 19249923, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (286, '2025-05-05', 12841948, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (287, '2025-05-06', 11352393, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (288, '2025-05-07', 11159448, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (289, '2025-05-08', 18501796, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (290, '2025-05-09', 11115020, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (291, '2025-05-10', 16812739, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (292, '2025-05-11', 15763504, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (293, '2025-05-12', 17203957, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (294, '2025-05-13', 12793658, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (295, '2025-05-14', 19586979, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (296, '2025-05-15', 12191362, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (297, '2025-05-16', 12017715, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (298, '2025-05-17', 12517667, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (299, '2025-05-18', 19694749, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (300, '2025-05-19', 17529612, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (301, '2025-05-20', 18087768, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (302, '2025-05-21', 14641286, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (303, '2025-05-22', 13672156, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (304, '2025-05-23', 19542962, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (305, '2025-05-24', 12196005, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (306, '2025-05-25', 13770237, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (307, '2025-05-26', 18908005, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (308, '2025-05-27', 10760153, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (309, '2025-05-28', 12825743, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (310, '2025-05-29', 16186197, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (311, '2025-05-30', 14249447, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (312, '2025-05-31', 16709190, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (313, '2025-06-01', 13802315, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (314, '2025-06-02', 14365065, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (315, '2025-06-03', 18078982, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (316, '2025-06-04', 10282961, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (317, '2025-06-05', 16536415, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (318, '2025-06-06', 16777715, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (319, '2025-06-07', 17780705, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (320, '2025-06-08', 12933118, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (321, '2025-06-09', 13826164, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (322, '2025-06-10', 19148654, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (323, '2025-06-11', 14821136, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (324, '2025-06-12', 15047379, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (325, '2025-06-13', 10914543, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (326, '2025-06-14', 13142984, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (327, '2025-06-15', 19547757, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (328, '2025-06-16', 18884954, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (329, '2025-06-17', 17847654, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (330, '2025-06-18', 11942947, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (331, '2025-06-19', 18125714, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (332, '2025-06-20', 11821744, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (333, '2025-06-21', 17015638, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (334, '2025-06-22', 14709338, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (335, '2025-06-23', 16636258, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (336, '2025-06-24', 11160191, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (337, '2025-06-25', 19295190, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (338, '2025-06-26', 15422734, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (339, '2025-06-27', 19443652, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (340, '2025-06-28', 10053506, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (341, '2025-06-29', 18439520, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (342, '2025-06-30', 13307459, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (343, '2025-07-01', 12022244, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (344, '2025-07-02', 19525903, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (345, '2025-07-03', 13095474, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (346, '2025-07-04', 13209050, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (347, '2025-07-05', 17547700, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (348, '2025-07-06', 15605340, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (349, '2025-07-07', 15032866, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (350, '2025-07-08', 15700689, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (351, '2025-07-09', 10429862, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (352, '2025-07-10', 11124594, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (353, '2025-07-11', 19617082, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (354, '2025-07-12', 16329223, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (355, '2025-07-13', 11496591, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (356, '2025-07-14', 18322220, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (357, '2025-07-15', 13135435, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (358, '2025-07-16', 12409834, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (359, '2025-07-17', 19957563, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (360, '2025-07-18', 11468426, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (361, '2025-07-19', 19467195, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (362, '2025-07-20', 17019476, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (363, '2025-07-21', 19664009, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (364, '2025-07-22', 16052566, 1);
INSERT INTO mydata (id, collect_date, asset, user_id) VALUES (365, '2025-07-23', 17083077, 1);
