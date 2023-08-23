package CordJg.Diary.exception;

import lombok.Getter;

public enum ExceptionCode {

    MEMBER_NOT_FOUND(404, "회원을 찾을 수 없습니다"),
    DATE_EXISTS(409, "해당 날짜의 자료가 존재합니다"),
    DATE_NOT_FOUND(404, "해당 날짜의 자료가 존재하지 않습니다"),
    DIARY_NOT_FOUND(404, "다이어리가 존재하지 않습니다"),
    JWT_TOKEN_EXPIRED(404, "토큰 기한이 만료되었습니다"),
    MEMBER_IS_DELETED(404, "정지 회원입니다"),
    MEMBER_EXISTS(404, "회원이 존재 합니다"),
    NO_PERMISSION_EDITING(404, "권한이 없습니다");

    @Getter
    private int status;

    @Getter
    private String message;

    ExceptionCode(int code, String message) {
        this.status = code;
        this.message = message;
    }
}
