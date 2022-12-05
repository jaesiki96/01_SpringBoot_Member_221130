package com.example.member.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

// 로그인 여부 확인
// 로그인 하지 않은 상태라면 로그인 페이지로 보내고 로그인을 수행하면 직전에
// 요청한 주소로 보내줌.
// 로그인 상태라면 넘어감
public class LoginCheckInterceptor implements HandlerInterceptor {
    // implements 는 interface 구현
    // extends 는 상속
    // @Override = 재 정의
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        // 요청한 주소 값을 가져올 수 있다.
        String requestURL = request.getRequestURI();
        System.out.println("request = " + request + ", response = " + response + ", handler = " + handler);
        // 재 정의 시 부모 메서드가 HttpSession session 을 정의하지 않았으면, 자식도 HttpSession session 을 위에서 정의할 수 없기 때문에
        // 여기서 정의하는 것!
        HttpSession session = request.getSession();
        if (session.getAttribute("loginEmail") == null) {
            response.sendRedirect("/member/login?redirectURL=" + requestURL);
            return false;
        }
        return true;
    }
}
