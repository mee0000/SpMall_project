package com.example.spmall.interceptor;

import com.example.spmall.pojo.Result;
import com.example.spmall.utils.JwtUtils;
import com.google.gson.Gson;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * LoginCheckInterceptor
 *
 * @author toy aa@nfda
 * @version 2023/10/14 11:12 AM
 * @since JDK11
 **/
@Component
@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {

    /**
     * login true, 진행 아니면 loginForm 이동
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String url = request.getRequestURI().toString();
        log.info("청구한 url: {}", url);

        //url중 login 포함되는지 확인
        if (url.contains("login")) {
            log.info("로그인...");
            return true;
        }

        // header..token포함
        String jwt = request.getHeader("token");
        log.info("token: {}", jwt);
        //token 존재하는지 확인
        if (!StringUtils.hasLength(jwt)) {
            log.info("token이 존재하자않는다, loinForm으로 이동");
            Result error = Result.error("NOT_LOGIN");
            // 수동으로 json 전환
            Gson gsonObj = new Gson();
            String notLogin = gsonObj.toJson(error);
            log.info("notLogin");
            response.getWriter().write(notLogin);
            return false;
        }

        //token 존재할 경우 , token 해석 ,false NOT_LOGIN true 진행
        try {
            Claims claims = JwtUtils.parseJWT(jwt);

        } catch (Exception e) {
            e.printStackTrace();
            log.info("token유호하지 않음, 로그인 필요");
            Result error = Result.error("NOT_LOGIN");
            Gson gsonObj = new Gson();
            String notLogin = gsonObj.toJson(error);
            System.out.println(notLogin);
            response.getWriter().write(notLogin);
            return false;
        }
        log.info("token유효, 정상진행");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
