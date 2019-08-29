package com.x3110.learningcommunity.result;


public enum ResultCode {
    /**
     * 成功
     */
    SUCCESS(200),
    /**
     * 已存在
     */
    HaveExist(201),
    /**
     * 已点赞
     */
    HavaLiked(202),

    /**
     * 未点赞
     */
    HaventLiked(203),
    /**
     * 未存在
     */
    NotExist(300),
    /**
     * 不合法的邮箱
     */
    INVALID_EMAIL_ADDRESS(305),
    /**
     * 邮箱已注册
     */
    EMAILOCCUPIED(306),
    /**
     * 失败
     */
    FAIL(400),

    /**
     * 未认证（签名错误）
     */
    UNAUTHORIZED(401),

    /**
     * 密码无效
     */
    INVALID_PASSWORD(402),

    /**
     * 接口不存在
     */
    NOT_FOUND(404),

    /**
     * 服务器内部错误
     */
    INTERNAL_SERVER_ERROR(500);



    /**
     * 状态码
     */
    public int code;

    ResultCode(int code) {
        this.code = code;
    }

}

