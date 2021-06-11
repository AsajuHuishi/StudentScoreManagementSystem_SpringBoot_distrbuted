package indi.huishi.sms.interceptor;

/**
 * @Author: Huishi
 * @Date: 2021/4/28 0:17
 */

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 登录检查: 拦截器通过用户模块的Session判断是否登录。已经实现Session共享，可在公共模块实现
 * 1.配置拦截器
 * 2.把配置放在容器中
 */
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    /**
     * 方法执行前
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        HttpSession session = request.getSession();// 其他模块的session是无法和用户模块的session共享的
        // 调用用户模块查看session
        log.info("1preHandle: {}", request.getRequestURI());///student/show.action
        log.info("拦截器：session中user的值为 {}", request.getSession().getAttribute("user"));
        if (request.getSession().getAttribute("user") == null){
            log.info("未登录");
            request.getRequestDispatcher("/user/login").forward(request, response);
            return false;
        }else{
            return true;//放行
        }
    }
    /**
     * 方法执行后
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("1postHandle: {}", request.getRequestURI());
    }
    /**
     * 页面渲染后
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("1afterCompletion: {}", request.getRequestURI());
    }
}
