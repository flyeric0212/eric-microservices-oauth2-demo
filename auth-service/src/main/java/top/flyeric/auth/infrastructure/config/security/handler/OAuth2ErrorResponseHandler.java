package top.flyeric.auth.infrastructure.config.security.handler;

import com.alibaba.fastjson2.JSONObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;
import java.util.HashMap;

public class OAuth2ErrorResponseHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String errMsg;
        if (exception instanceof OAuth2AuthenticationException auth2AuthenticationException) {
            OAuth2Error error = auth2AuthenticationException.getError();
            errMsg = "认证信息错误：" + error.getErrorCode() + " / "  + error.getDescription();
        } else {
            errMsg = exception.getMessage();
        }

        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        HashMap<String, Object> resultWrapper = new HashMap<>();
        resultWrapper.put("errCode", HttpStatus.UNAUTHORIZED.value());
        resultWrapper.put("errMessage", errMsg);

        response.getWriter().write(JSONObject.toJSONString(resultWrapper));
        response.getWriter().flush();
    }
}
