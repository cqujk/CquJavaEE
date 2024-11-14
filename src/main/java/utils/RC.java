package utils;

public enum RC {
    SUCCESS(0, "认证成功"),
    USER_NOT_FOUND(1, "用户不存在"),
    WRONG_PASSWORD(2, "密码不正确"),
    USER_DISABLED(3, "用户已被禁用"),
    AUTHENTICATION_FAILED(4, "认证失败"),
    USER_EXITS(5,"已存在相同用户名的用户" );


    private final int code;
    private final String message;
    RC(int code, String message) {
        this.code = code;
        this.message = message;
    }
    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
