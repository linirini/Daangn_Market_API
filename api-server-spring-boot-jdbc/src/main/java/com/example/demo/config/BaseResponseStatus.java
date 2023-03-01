package com.example.demo.config;

import lombok.Getter;

/**
 * 에러 코드 관리
 */
@Getter
public enum BaseResponseStatus {
    /**
     * 1000 : 요청 성공
     */
    SUCCESS(true, 1000, "요청에 성공하였습니다."),


    /**
     * 2000 : Request 오류
     */
    // Common
    REQUEST_ERROR(false, 2000, "입력값을 확인해주세요."),
    EMPTY_JWT(false, 2001, "JWT를 입력해주세요."),
    INVALID_JWT(false, 2002, "유효하지 않은 JWT입니다."),
    INVALID_USER_JWT(false, 2003, "권한이 없는 유저의 접근입니다."),

    // users

    // [POST] /users
    POST_USERS_EMPTY_PHONENUMBER(false, 2015, "전화번호를 입력해주세요."),
    POST_USERS_EMPTY_NICK_NAME(false, 2070," 닉네임을 입력해주세요."),
    POST_USERS_INVALID_PHONENUMBER(false, 2016, "전화번호 형식을 확인해주세요."),
    POST_USERS_INVALID_EMAIL_ADDRESS(false,2080,"이메일 양식을 확인해주세요."),
    POST_USERS_EMPTY_EMAIL_ADDRESS(false,2081,"이메일을 입력해주세요"),

    // [GET] /users/{user-id}
    GET_USERS_UNKNOWN_USER_ID(false, 2034, "존재하지 않는 유저입니다."),


    // [PATCH] /users/{user-id}
    PATCH_USERS_EMPTY_NICK_NAME(false, 2018, "유저 닉네임을 입력해주세요."),

    // [DELETE] /users/{user-id}
    DELETE_USERS_UNKNOWN_USER_ID(false, 2020, "존재하지 않는 유저입니다."),
    DELETE_USERS_ALREADY_DELETED(false, 2021, "이미 탈퇴한 유저입니다."),

    //posts

    //[POST] /posts
    POST_POST_EMPTY_TITLE(false, 2022, "제목을 입력해주세요."),
    POST_POST_EMPTY_CONTENT(false, 2023, "내용을 입력해주세요."),
    POST_POST_EMPTY_PRICE(false,2035,"가격을 입력해주세요"),
    POST_POST_EMPTY_IMG_URLS(false,2036,"제품 사진을 넣어주세요."),

    //[GET] /posts?address=
    GET_POST_EMPTY_ADDRESS(false, 2024, "내 동네를 입력해주세요."),

    //[GET] /posts/:postId
    GET_POST_UNKNOWN_POST_ID(false, 2040, "존재하지 않는 게시물입니다."),

    //[PATCH] /posts/status/:postId
    DELETE_POST_UNKNOWN_POST_ID(false, 2041, "존재하지 않는 게시물입니다."),


    //Interest

    //[POST] /interests
    POST_INTEREST_ALREADY_EXIST(false, 2026, "이미 관심 테이블에 등록되어 있습니다."),
    POST_INTEREST_EMPTY_POST_ID(false, 2027, "게시물 식별자를 넣어주세요."),
    POST_INTEREST_EMPTY_USER_ID(false, 2028, "유저의 식별자를 넣어주세요."),
    POST_INTEREST_OWN_POST(false,2042,"본인의 게시글을 관심 목록에 추가할 수 없습니다."),

    //[PATCH] /interests/:interestId
    DELETE_INTERESTS_UNKNOWN_INTEREST_ID(false,2029, "관심 목록에 존재하지 않습니다."),

    //Badge

    //[POST] /badges
    POST_BADGES_EMPTY_BADGE_TAG_ID(false,2031,"배지 태그의 식별자를 넣어주세요."),
    POST_BADGES_EMPTY_USER_ID(false,2032,"유저의 식별자를 넣어주세요."),
    POST_BADGE_ALREADY_EXIST(false,2033,"이미 획득한 배지입니다."),

    ///[PATCH] /badges/representative/:badge-id
    PATCH_BADGES_EMPTY_BADGE_ID(false,2045,"뱃지 식별자를 입력해주세요."),
    PATCH_BADGE_UNKNOWN_BADGE_ID(false,2046,"존재하지 않는 뱃지입니다."),
    PATCH_BADGES_EMPTY_USER_ID(false,2047,"유저 식별자를 입력해주세요."),

    //[GET] /badges/:badges
    GET_BADGES_EMPTY_BADGE_ID(false,2048,"뱃지 식별자를 넣어주세요."),
    GET_BADGE_UNKNOWN_BADGE_ID(false,2049,"존재하지 않는 뱃지입니다."),
    GET_BADGES_EMPTY_USER_ID(false,2050,"유저 식별자를 넣어주세요."),

    //Address

    //[POST] /addresses
    POST_ADDRESS_EMPTY_USER_ID(false,2051,"유저 식별자를 넣어주세요."),
    POST_ADDRESS_EMPTY_LATITUDE(false,2052,"위도 값을 넣어주세요."),
    POST_ADDRESS_EMPTY_LONGITUDE(false, 2053, "경도 값을 넣어주세요."),
    POST_ADDRESS_EMPTY_ADDRESS(false,2054, "주소를 넣어주세요."),

    //[GET] /addresses/:address-id
    GET_ADDRESS_UNKNOWN_ADDRESS_ID(false,2057,"존재하지 않는 주소입니다."),
    GET_ADDRESS_EMPTY_ADDRESS_ID(false,2058,"주소 식별자를 넣어주세요."),

    //[PATCH] /addresses/:address-id?user-id=
    PATCH_ADDRESS_EMPTY_ADDRESS_ID(false,2055,"주소 식별자를 넣어주세요."),
    PATCH_ADDRESS_EMPTY_USER_ID(false,2056,"유저 식별자를 넣어주세요."),


    /**
     * 3000 : Response 오류
     */
    // Common
    RESPONSE_ERROR(false, 3000, "값을 불러오는데 실패하였습니다."),

    // [POST] /users
    DUPLICATED_EMAIL(false, 3013, "중복된 이메일입니다."),
    POST_USERS_EXISTS_PHONENUMBER(false, 3017, "이미 가입된 전화번호입니다."),
    PATCH_USERS_EXISTS_NICK_NAME(false, 3019, "이미 사용중인 닉네임입니다."),
    FAILED_TO_LOGIN(false, 3014, "로그인에 실패하였습니다."),

    /**
     * 4000 : Database, Server 오류
     */
    DATABASE_ERROR(false, 4000, "데이터베이스 연결에 실패하였습니다."),
    SERVER_ERROR(false, 4001, "서버와의 연결에 실패하였습니다."),

    //User

    //[PATCH] /users/{userIdx}
    MODIFY_FAIL_USER(false, 4014, "유저 정보 수정에 실패하였습니다."),

    //Post

    //[PATCH] /posts/:postId
    MODIFY_FAIL_POST(false, 4018, "게시글 수정에 실패하였습니다."),

    //Interest

    //[PATCH] /interests/status/:interest-id
    MODIFY_FAIL_INTEREST(false, 4017, "관심 목록 수정에 싪패하였습니다."),

    //Badge

    //[PATCH] /badges/:badge-id?user-id=
    MODIFY_FAIL_BADGE(false,4016,"뱃지 정보 수정에 실패하였습니다."),

    //Address

    //[PATCH] /addresses/:address-id?user-id=
    //[PATCH] /addresses/:address-id
    //[PATCH] /addresses/status/:address-id
    MODIFY_FAIL_ADDRESS(false,4015,"주소 정보 수정에 실패하였습니다."),

    PHONENUMBER_ENCRYPTION_ERROR(false, 4011, "전화번호 암호화에 실패하였습니다."),
    PHONENUMBER_DECRYPTION_ERROR(false, 4012, "전화번호 복호화에 실패하였습니다.");


    // 5000 : 필요시 만들어서 쓰세요
    // 6000 : 필요시 만들어서 쓰세요


    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
