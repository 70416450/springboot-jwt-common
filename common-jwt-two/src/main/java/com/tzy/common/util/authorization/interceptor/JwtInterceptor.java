package com.tzy.common.util.authorization.interceptor;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.interfaces.Claim;
import com.tzy.common.exception.BusinessException;
import com.tzy.common.exception.CommonErrorCode;
import com.tzy.common.util.authorization.jwt.JwtUtil;
import com.tzy.common.util.authorization.jwt.UrlUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/24.
 */
public class JwtInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        String method = request.getMethod();
        String requestURI = request.getRequestURI();
        System.out.println("method --> "+ method);
        System.out.println("requestURI --> "+ requestURI);
        boolean hasPerm = false;
        if (!method.equals("OPTIONS")) {
            try {
                String token = request.getHeader("Token");
                Map<String, Claim> result = JwtUtil.parseJWT(token);
                Claim perm = result.get("perm");
                String s = perm.asString();
                JSONArray objects = JSONArray.parseArray(s);
                for (int i = 0; i < objects.size(); i++) {
                    JSONObject jsonObject = objects.getJSONObject(i);
                    String dbMethod = jsonObject.getString("method");
                    String url = jsonObject.getString("permUrl");
                    if (dbMethod.equalsIgnoreCase(method) && UrlUtils.isLike(requestURI, url)) {
                        hasPerm = true;
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (e instanceof NullPointerException) {
                    throw new BusinessException(CommonErrorCode.TOKEN_INVALID);
                }
            }
        }

        if (!method.equals("OPTIONS") && !hasPerm) {
            throw new BusinessException(CommonErrorCode.NO_SESSION);
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
