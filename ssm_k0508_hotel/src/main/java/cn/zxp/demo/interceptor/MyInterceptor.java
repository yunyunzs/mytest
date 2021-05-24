package cn.zxp.demo.interceptor;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *   自定义拦截器
 */
public class MyInterceptor extends HandlerInterceptorAdapter {

    //拦截器的核心方法
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("拦截器的核心方法..");
        //1.从session容器中取出登陆的用户对象
        Object loginUser = request.getSession().getAttribute("loginUser");
        if(loginUser!=null){  //存在
            return true;  //直接放行
        }else{
            //装入拦截提示
            request.setAttribute("loginUIMsg","loginUIMsg");
            //直接有服务器转发到登陆页面
            request.getRequestDispatcher("/model/toLoginUI").forward(request,response);
            return false;  //将此请求拦截下来
        }

    }

    //拦截之前的执行的方法
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("拦截之前执行的方法..");
    }

    //拦截之后执行的方法
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e) throws Exception {
        System.out.println("拦截之后执行的方法..");
    }
}
