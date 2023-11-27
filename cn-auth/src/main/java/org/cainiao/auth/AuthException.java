package org.cainiao.auth;

import java.io.Serial;

/**
 * <br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
public class AuthException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 6287621029682564953L;

    final String status;

    /**
     *
     */
    public AuthException() {
        super();
        this.status = "DEFAULT";
    }

    /**
     *
     * @param message
     *            异常消息
     */
    public AuthException(String message) {
        super(message);
        this.status = "DEFAULT";
    }

    /**
     *
     * @param message
     *            异常消息
     * @param status
     *            状态
     */
    public AuthException(String message, String status) {
        super(message);
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

}
