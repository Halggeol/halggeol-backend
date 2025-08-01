package com.halggeol.backend.security.util;

public class RegexConstants {
    // 01012345678
    public static final String PHONE_PATTERN = "^01[016789]\\d{7,8}$";

    // 최소 8자 이상의 영문 대/소문자, 숫자, 특수문자로 구성
    public static final String PASSWORD_PATTERN
        = "^[a-zA-Z\\d!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]{8,}$";

    // 최소 2자 이상의 한글로 구성
    public static final String NAME_PATTERN = "^[가-힣]{2,}$";
}
