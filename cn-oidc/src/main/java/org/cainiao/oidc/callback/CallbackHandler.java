package org.cainiao.oidc.callback;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

public interface CallbackHandler {

    Logger LOGGER = LoggerFactory.getLogger(CallbackHandler.class);

    void handle(HttpServletRequest request, HttpServletResponse response);

    /**
     * 获取code参数
     *
     * @return String
     */
    default String code(HttpServletRequest request, HttpServletResponse response) {
        String code = request.getParameter("code");
        if (Strings.isBlank(code)) {
            LOGGER.debug("BAD_REQUEST code is null or empty");
            response.setStatus(HttpStatus.BAD_REQUEST.value());
        }
        return code;
    }

    /**
     * 获取state参数
     *
     * @return String
     */
    default String state(HttpServletRequest request, HttpServletResponse response) {
        String state = request.getParameter("state");
        if (Strings.isBlank(state)) {
            LOGGER.debug("BAD_REQUEST state is null or empty");
            response.setStatus(HttpStatus.BAD_REQUEST.value());
        }
        return state;
    }

}
