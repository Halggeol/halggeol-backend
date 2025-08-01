DROP DATABASE IF EXISTS my_db;
CREATE DATABASE my_db;

USE my_db;

-- Table: users
CREATE TABLE `users` (
                         `id` INT AUTO_INCREMENT PRIMARY KEY, # 자동생성 ID
                         `email` VARCHAR(255) NOT NULL UNIQUE, # 사용자 이메일, 고유값
                         `name` VARCHAR(255) NOT NULL, # 사용자 이름
                         `password` VARCHAR(255) NOT NULL, # 사용자 비밀번호, 해시값으로 저장
                         `phone` VARCHAR(255) NOT NULL, # 사용자 전화번호
                         `birth` DATETIME, # 사용자 생년월일
                         `risk` INT, # 사용자 위험성향, 1~5 사이의 정수로 표현
                         `user_klg` INT, # 사용자 KLG 점수, 1~5 사이의 정수로 표현
                         `algo_code` VARCHAR(255) NULL, # 이거 양식이 11011 로 각 score의 고저를 나타냄
                         `reg_date` DATETIME, # 사용자 가입일
                         `klg_renew_date` DATETIME, # KLG 점수 갱신일
                         `risk_renew_date` DATETIME, # 위험성향 갱신일
                         `docu_renew_date` DATETIME, # 개인정보 갱신일
                         `yield_score` DOUBLE, # 수익성 점수 (0~1 사이의 실수 1에 가까울 수록 고수익 성향)
                         `risk_score` DOUBLE, # 위험성 점수 (0~1 사이의 실수 1에 가까울 수록 고위험 성향)
                         `cost_score` DOUBLE, # 비용 점수 (0~1 사이의 실수 1에 가까울 수록 저비용 성향)
                         `liquidity_score` DOUBLE, # 유동성 점수 (0~1 사이의 실수 1에 가까울 수록 고유동성 성향)
                         `complexity_score` DOUBLE, # 복잡성 점수 (0~1 사이의 실수 1에 가까울 수록 고복잡성 성향)
                         `insight_cycle` VARCHAR(255) DEFAULT '0 0 0 1 * *' # cron 형식으로, 0 0 0 1 * * 은 매월 1일 0시 0분에 실행됨
);

CREATE TABLE `mydata` (
                          `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY, # 자동생성 ID
                          `collect_date` DATETIME, # 데이터 수집일
                          `asset` INT, # 자산 총액
                          `user_id` INT NOT NULL # 사용자 ID, users 테이블의 id를 참조
);

CREATE TABLE `myproduct` (
                             `id` INT NOT NULL AUTO_INCREMENT, # 자동생성 ID
                             `mydata_id` INT NOT NULL, # mydata 테이블의 id를 참조
                             `amount` INT, # 수집일 기준 투자 평가 금액
                             `reg_date` DATE, # 가입일
                             `end_date` DATE, # 만기일
                             `product_id` VARCHAR(20) NOT NULL, # 제품 ID, products 테이블의 id를 참조
                             PRIMARY KEY (`id`),
                             FOREIGN KEY (`mydata_id`) REFERENCES `mydata` (`id`)
);

-- Table: products

CREATE TABLE `deposit` (
                           `id`	VARCHAR(20)	NOT NULL PRIMARY KEY ,          # 자동생성 ID
                           `name`	VARCHAR(255)	NOT NULL,               # 상품 이름
                           `rate`	FLOAT	NOT NULL,                       # 기본 금리
                           `prime_rate`	FLOAT	NOT NULL,                   # 우대 금리
                           `join_way`	VARCHAR(255)	NULL,               # 가입 방법
                           `end_date`	DATE	NULL, # 만기일
                           `max_limit`	BIGINT	NULL, # 최대 한도
                           `join_member`	VARCHAR(255)	NULL, # 가입 가능 회원
                           `join_deny`	INT	NULL, # 가입 제한 여부 (1: 제한 없음, 2: 약한 제한, 3: 강한 제한)
                           `bonus_condition`	TEXT	NULL, # 보너스 조건
                           `rate_type` VARCHAR(255) NULL, # 금리 유형 (예: 단리, 복리 등)
                           `min_limit`	BIGINT	NULL, # 최소 한도
                           `company`	VARCHAR(255)	NULL, # 금융 회사 이름
                           `score`	INT	NULL, # 상품 점수 아직 정의되지 않음
                           `risk`	INT	NULL, # 위험 등급 (1~6 사이의 정수로 표현)
                           `reg_link`	VARCHAR(255)	NULL, # 상품 등록 링크
                           `caution`	TEXT	NULL, # 주의 사항
                           `view_cnt`	INT	NULL, # 조회 수
                           `scrap_cnt`	INT	NULL, # 스크랩 수
                           `regret_cnt`	INT	NULL, # 회고 수
                           `algo_code`	FLOAT	NULL, # 이거 어따 쓸 지 아직 모름
                           `min_save_term` INT NULL, # 최소 예치 기간
                           `max_save_term` INT NULL, # 최대 예치 기간
                           yield_score FLOAT NULL, # 수익성 점수 (0~1 사이의 실수, 1에 가까울수록 고수익 성향)
                           risk_score FLOAT NULL, # 위험성 점수 (0~1 사이의 실수, 1에 가까울수록 고위험 성향)
                           cost_score FLOAT NULL, # 비용 점수 (0~1 사이의 실수, 1에 가까울수록 저비용 성향)
                           liquidity_score FLOAT NULL, # 유동성 점수 (0~1 사이의 실수, 1에 가까울수록 고유동성 성향)
                           complexity_score FLOAT NULL, # 복잡성 점수 (0~1 사이의 실수, 1에 가까울수록 고복잡성 성향)
                           f_sector INT NULL, # 금융 분야 (예: 1: 1금융권, 2: 2금융권)
                           `description` TEXT NULL, # 상품 설명
                           `extra_deposit` BOOLEAN NULL # 추가 입금 가능 여부
);

CREATE TABLE `savings` (
                           `id`	VARCHAR(20)	NOT NULL PRIMARY KEY, # 자동생성 ID
                           `name`	VARCHAR(255)	NULL, # 상품 이름
                           `rate`	FLOAT	NULL, # 기본 금리
                           `prime_rate`	FLOAT	NULL, # 우대 금리
                           `join_way`	VARCHAR(255)	NULL, # 가입 방법
                           `end_date`	DATE	NULL, # 만기일
                           `max_limit`	BIGINT	NULL, # 최대 한도
                           `join_member`	VARCHAR(255)	NULL, # 가입 가능 회원
                           `join_deny`	INT	NULL, # 가입 제한 여부 (1: 제한 없음, 2: 약한 제한, 3: 강한 제한)
                           `bonus_condition`	TEXT	NULL, # 보너스 조건
                           `min_limit`	BIGINT	NULL, # 최소 한도
                           `rate_type`	VARCHAR(255)	NULL, # 금리 유형 (예: 단리, 복리 등)
                           `save_type`	VARCHAR(255)	NULL, # 예치 유형 (예: 정기, 자유 등)
                           `company`	VARCHAR(255)	NULL, # 금융 회사 이름
                           `score`	INT	NULL, # 상품 점수 아직 정의되지 않음
                           `risk`	INT	NULL, # 위험 등급 (1~6 사이의 정수로 표현)
                           `reg_link`	VARCHAR(255)	NULL, # 상품 등록 링크
                           `caution`	TEXT	NULL, # 주의 사항
                           `view_cnt`	INT	NULL, # 조회 수
                           `scrap_cnt`	INT	NULL, # 스크랩 수
                           `regret_cnt`	INT	NULL, # 회고 수
                           `algo_code`	FLOAT	NULL, # 이거 어따 쓸 지 아직 모름
                           min_save_term INT NULL, # 최소 예치 기간
                           max_save_term INT NULL, # 최대 예치 기간
                           yield_score FLOAT NULL, # 수익성 점수 (0~1 사이의 실수, 1에 가까울수록 고수익 성향)
                           risk_score FLOAT NULL, # 위험성 점수 (0~1 사이의 실수, 1에 가까울수록 고위험 성향)
                           cost_score FLOAT NULL, # 비용 점수 (0~1 사이의 실수, 1에 가까울수록 저비용 성향)
                           liquidity_score FLOAT NULL, # 유동성 점수 (0~1 사이의 실수, 1에 가까울수록 고유동성 성향)
                           complexity_score FLOAT NULL, # 복잡성 점수 (0~1 사이의 실수, 1에 가까울수록 고복잡성 성향)
                           f_sector INT NULL, # 금융 분야 (예: 1: 1금융권, 2: 2금융권)
                           `description` TEXT NULL # 상품 설명
);

CREATE TABLE `pension` (
                           `id`	VARCHAR(255)	NOT NULL PRIMARY KEY, # 자동생성 ID
                           `name`	VARCHAR(255)	NULL, # 상품 이름
                           `rate`	FLOAT	NULL, # 기본 금리
                           `pension_price_movement`	FLOAT	NULL, # 연금 가격 변동률
                           `pension_kind`	VARCHAR(255)	NULL, # 연금 종류 (예: 개인연금, 퇴직연금 등)
                           `pension_type`	VARCHAR(255)	NULL, # 연금 유형 (예: 확정형, 변동형 등)
                           `min_guarantee_rate`	FLOAT	NULL, # 최소 보장 금리
                           `end_date`	DATE	NULL, # 만기일
                           `company`	VARCHAR(255)	NULL, # 금융 회사 이름
                           `score`	INT	NULL, # 상품 점수 아직 정의되지 않음
                           `risk`	INT	NULL, # 위험 등급 (1~6 사이의 정수로 표현)
                           `reg_link`	VARCHAR(255)	NULL, # 상품 등록 링크
                           `caution`	TEXT	NULL, # 주의 사항
                           `view_cnt`	INT	NULL, # 조회 수
                           `scrap_cnt`	INT	NULL, # 스크랩 수
                           `regret_cnt`	INT	NULL, # 회고 수
                           `algo_code`	FLOAT	NULL, # 이거 어따 쓸 지 아직 모름
                           yield_score FLOAT NULL, # 수익성 점수 (0~1 사이의 실수, 1에 가까울수록 고수익 성향)
                           risk_score FLOAT NULL, # 위험성 점수 (0~1 사이의 실수, 1에 가까울수록 고위험 성향)
                           cost_score FLOAT NULL, # 비용 점수 (0~1 사이의 실수, 1에 가까울수록 저비용 성향)
                           liquidity_score FLOAT NULL, # 유동성 점수 (0~1 사이의 실수, 1에 가까울수록 고유동성 성향)
                           complexity_score FLOAT NULL, # 복잡성 점수 (0~1 사이의 실수, 1에 가까울수록 고복잡성 성향)
                           f_sector INT NULL, # 금융 분야 (예: 1: 1금융권, 2: 2금융권)
                           `description` TEXT NULL, # 상품 설명
                           `save_term` INT NULL, # 예치 기간 (월 단위)
                           `rate_type` VARCHAR(255) NULL, # 금리 유형 (예: 단리, 복리 등)
                           `min_limit` BIGINT NULL, # 최소 한도
                           `max_limit` BIGINT NULL, # 최대 한도
                           `TER` FLOAT NULL # 총 보수 비율 (Total Expense Ratio)
);

CREATE TABLE `fund` (
                        `id`	VARCHAR(20)	NOT NULL PRIMARY KEY, # 자동생성 ID
                        `name`	VARCHAR(255)	NULL, # 상품 이름
                        `rate`	FLOAT   NULL, # 기본 금리
                        `fund_price`	FLOAT	NULL, # 펀드 가격
                        `fund_price_movement`	FLOAT	NULL, # 펀드 가격 변동률
                        `TER`	FLOAT	NULL, # 총 보수 비율 (Total Expense Ratio)
                        `category`	VARCHAR(255)	NULL, # 카테고리 (예: 주식형, 채권형 등)
                        `theme`	VARCHAR(255)	NULL, # 테마 (예: ESG, 기술주 등)
                        `investment_warning_grade`	VARCHAR(255)	NULL, # 투자 경고 등급 (예: 고위험, 중위험, 없음 등 딱히 정의된 건 없음)
                        `upfront_fee`	FLOAT	NULL, # 선취 수수료
                        `management_fee`	FLOAT	NULL, # 운용 수수료
                        `min_limit`	BIGINT	NULL, # 최소 한도
                        `target`	VARCHAR(255)	NULL, # 투자 대상 (예: 국내 주식, 해외 채권 등)
                        `investment_type`	VARCHAR(255)	NULL, # 투자 유형 (예: 액티브, 패시브 등)
                        `company`	VARCHAR(255)	NULL, # 금융 회사 이름
                        `score`	INT	NULL, # 상품 점수 아직 정의되지 않음
                        `risk`	INT	NULL, # 위험 등급 (1~6 사이의 정수로 표현)
                        `reg_link`	VARCHAR(255)	NULL, # 상품 등록 링크
                        `caution`	TEXT	NULL, # 주의 사항
                        `view_cnt`	INT	NULL, # 조회 수
                        `scrap_cnt`	INT	NULL, # 스크랩 수
                        `regret_cnt`	INT	NULL, # 회고 수
                        `algo_code`	FLOAT	NULL, # 이거 어따 쓸 지 아직 모름
                        yield_score FLOAT NULL, # 수익성 점수 (0~1 사이의 실수, 1에 가까울수록 고수익 성향)
                        risk_score FLOAT NULL, # 위험성 점수 (0~1 사이의 실수, 1에 가까울수록 고위험 성향)
                        cost_score FLOAT NULL, # 비용 점수 (0~1 사이의 실수, 1에 가까울수록 저비용 성향)
                        liquidity_score FLOAT NULL, # 유동성 점수 (0~1 사이의 실수, 1에 가까울수록 고유동성 성향)
                        complexity_score FLOAT NULL, # 복잡성 점수 (0~1 사이의 실수, 1에 가까울수록 고복잡성 성향)
                        f_sector INT NULL, # 금융 분야 (예: 1: 1금융권, 2: 2금융권)
                        `description` TEXT NULL # 상품 설명
);

CREATE TABLE `forex` (
                         `id`	VARCHAR(20)	NOT NULL PRIMARY KEY, # 자동생성 ID
                         `name`	VARCHAR(255)	NULL, # 상품 이름
                         `rate`	FLOAT	NULL, # 기본 금리
                         `description`	VARCHAR(255)	NULL, # 상품 설명
                         `currency`	VARCHAR(255)	NULL, # 통화 종류 (예: USD, EUR 등)
                         `min_limit`	BIGINT	NULL, # 최소 한도
                         `max_limit`	BIGINT	NULL, # 최대 한도
                         `reg_limit_date`	DATETIME	NULL, # 등록 가능 기간
                         `auto_renew`	VARCHAR(255)	NULL, # 자동 갱신 여부 (예: 'Y', 'N')
                         `extra_deposit`	BOOLEAN	NULL, # 추가 입금 가능 여부
                         `rate_give_way`	VARCHAR(255)	NULL, # 금리 제공 방식 (예: '고정', '변동')
                         `tax_refund`	BOOLEAN	NULL, # 세금 환급 여부
                         `protect`	BOOLEAN	NULL, # 보호 여부 (예: 'true', 'false')
                         `company`	VARCHAR(255)	NULL, # 금융 회사 이름
                         `score`	INT	NULL, # 상품 점수 아직 정의되지 않음
                         `risk`	INT	NULL, # 위험 등급 (1~6 사이의 정수로 표현)
                         `reg_link`	VARCHAR(255)	NULL, # 상품 등록 링크
                         `caution`	TEXT	NULL, # 주의 사항
                         `view_cnt`	INT	NULL, # 조회 수
                         `scrap_cnt`	INT	NULL, # 스크랩 수
                         `regret_cnt`	INT	NULL, # 회고 수
                         `algo_code`	FLOAT	NULL, # 이거 어따 쓸 지 아직 모름
                         yield_score FLOAT NULL, # 수익성 점수 (0~1 사이의 실수, 1에 가까울수록 고수익 성향)
                         risk_score FLOAT NULL, # 위험성 점수 (0~1 사이의 실수, 1에 가까울수록 고위험 성향)
                         cost_score FLOAT NULL, # 비용 점수 (0~1 사이의 실수, 1에 가까울수록 저비용 성향)
                         liquidity_score FLOAT NULL, # 유동성 점수 (0~1 사이의 실수, 1에 가까울수록 고유동성 성향)
                         complexity_score FLOAT NULL, # 복잡성 점수 (0~1 사이의 실수, 1에 가까울수록 고복잡성 성향)
                         f_sector INT NULL, # 금융 분야 (예: 1: 1금융권, 2: 2금융권)
                         `rate_type` VARCHAR(255) NULL, # 금리 유형 (예: 단리, 복리 등)
                         `min_save_term` INT NULL, # 최소 예치 기간 (월 단위)
                         `max_save_term` INT NULL # 최대 예치 기간 (월 단위)
);

CREATE TABLE `rec_item` (
                            `rec_id` INT NOT NULL AUTO_INCREMENT, # 추천 아이템 ID, 자동 생성
                            `user_id` INT NOT NULL, # 사용자 ID, users 테이블의 id를 참조
                            `rec_date` DATE NOT NULL, # 추천 날짜
                            `product1_id` VARCHAR(20) NOT NULL, # 추천된 첫 번째 상품 ID
                            `product1_status` ENUM('가입', '관심', '회고') NULL, # 첫 번째 상품 상태
                            `product1_survey` VARCHAR(255) NULL, # 첫 번째 상품에 대한 설문 응답(예: null이면 설문 미응답, 응답 시에는 설문 내용)
                            `product1_regret_score` INT NULL, # 첫 번째 상품에 대한 회고 점수 (0~100 사이의 정수로 표현)
                            `product1_miss_amount` INT NULL, # 첫 번째 상품에 대한 회고 시 놓친 금액 (원 단위)

                            `product2_id` VARCHAR(20) NOT NULL, # 추천된 두 번째 상품 ID
                            `product2_status` ENUM('가입', '관심', '회고') NULL, # 두 번째 상품 상태
                            `product2_survey` VARCHAR(255) NULL, # 두 번째 상품에 대한 설문 응답(예: null이면 설문 미응답, 응답 시에는 설문 내용)
                            `product2_regret_score` INT NULL, # 두 번째 상품에 대한 회고 점수 (0~100 사이의 정수로 표현)
                            `product2_miss_amount` INT NULL, # 두 번째 상품에 대한 회고 시 놓친 금액 (원 단위)

                            `product3_id` VARCHAR(20) NOT NULL, # 추천된 세 번째 상품 ID
                            `product3_status` ENUM('가입', '관심', '회고') NULL, # 세 번째 상품 상태
                            `product3_survey` VARCHAR(255) NULL, # 세 번째 상품에 대한 설문 응답(예: null이면 설문 미응답, 응답 시에는 설문 내용)
                            `product3_regret_score` INT NULL, # 세 번째 상품에 대한 회고 점수 (0~100 사이의 정수로 표현)
                            `product3_miss_amount` INT NULL, # 세 번째 상품에 대한 회고 시 놓친 금액 (원 단위)

                            `product4_id` VARCHAR(20) NOT NULL, # 추천된 네 번째 상품 ID
                            `product4_status` ENUM('가입', '관심', '회고') NULL, # 네 번째 상품 상태
                            `product4_survey` VARCHAR(255) NULL, # 네 번째 상품에 대한 설문 응답(예: null이면 설문 미응답, 응답 시에는 설문 내용)
                            `product4_regret_score` INT NULL, # 네 번째 상품에 대한 회고 점수 (0~100 사이의 정수로 표현)
                            `product4_miss_amount` INT NULL, # 네 번째 상품에 대한 회고 시 놓친 금액 (원 단위)

                            `product5_id` VARCHAR(20) NOT NULL, # 추천된 다섯 번째 상품 ID
                            `product5_status` ENUM('가입', '관심', '회고') NULL, # 다섯 번째 상품 상태
                            `product5_survey` VARCHAR(255) NULL, # 다섯 번째 상품에 대한 설문 응답(예: null이면 설문 미응답, 응답 시에는 설문 내용)
                            `product5_regret_score` INT NULL, # 다섯 번째 상품에 대한 회고 점수 (0~100 사이의 정수로 표현)
                            `product5_miss_amount` INT NULL, # 다섯 번째 상품에 대한 회고 시 놓친 금액 (원 단위)

                            `anlz_date` DATE NOT NULL, # 분석 날짜
                            `round` INT NULL # 추천 회차 (1, 2, 3 등)

                            PRIMARY KEY (`rec_id`, `user_id`),
                            FOREIGN KEY (`user_id`) REFERENCES `users`(`id`)
);

-- 트래킹
CREATE TABLE `fund_track` (
                              `id` INT NOT NULL AUTO_INCREMENT, # 자동생성 ID
                              `product_id` VARCHAR(20) NOT NULL, # 상품 ID, fund 테이블의 id를 참조
                              `tracking_date` DATE, # 트래킹 날짜
                              `price` FLOAT, # 펀드 가격
                              `profit` FLOAT, # 수익률
                              PRIMARY KEY (`id`),
                              FOREIGN KEY (`product_id`) REFERENCES `fund`(`id`)
);

CREATE TABLE `forex_track` (
                               `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY, # 자동생성 ID
                               `product_id` VARCHAR(20) NOT NULL, # 상품 ID, forex 테이블의 id를 참조
                               `tracking_date` DATE, # 트래킹 날짜
                               `price` FLOAT, # 외환 가격
                               `currency` VARCHAR(255) NULL, # 통화 종류 (예: USD, EUR 등)
                               FOREIGN KEY (`product_id`) REFERENCES `forex`(`id`)
);

CREATE TABLE `aggressive_pension_track` (
                                            `id` INT NOT NULL AUTO_INCREMENT, # 자동생성 ID
                                            `product_id` VARCHAR(20) NOT NULL, # 상품 ID, pension 테이블의 id를 참조
                                            `tracking_date` DATE, # 트래킹 날짜
                                            `price` FLOAT, # 연금 가격
                                            `profit` FLOAT, # 수익률
                                            PRIMARY KEY (`id`),
                                            FOREIGN KEY (`product_id`) REFERENCES `pension`(`id`)
);

CREATE TABLE `personalized_description` (
                                            `id` INT NOT NULL AUTO_INCREMENT, # 자동생성 ID
                                            `algo_code` VARCHAR(255) NOT NULL, # 알고리즘 코드, 예: '11011'
                                            `advantage` VARCHAR(255) NULL, # 장점 설명
                                            `disadvantage` VARCHAR(255) NULL, # 단점 설명
                                            `description` TEXT NULL, # 상세 설명
                                            `product_id` VARCHAR(10) NOT NULL, # 상품 ID, products 테이블의 id를 참조
                                            PRIMARY KEY (`id`)
);

CREATE TABLE `scrap` (
                         `user_id` INT NOT NULL, # 사용자 ID, users 테이블의 id를 참조
                         `product_id` VARCHAR(20) NOT NULL, # 상품 ID, products 테이블의 id를 참조
                         PRIMARY KEY (`user_id`, `product_id`),
                         FOREIGN KEY (`user_id`) REFERENCES `users`(`id`)
);