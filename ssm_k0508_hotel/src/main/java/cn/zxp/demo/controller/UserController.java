package cn.zxp.demo.controller;

import cn.zxp.demo.pojo.User;
import cn.zxp.demo.service.UserService;
import cn.zxp.demo.utils.MD5;
import cn.zxp.demo.utils.VerifyCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequestMapping("user")
public class UserController extends BaseController<User> {
    @Autowired
    private UserService userService;

    //生成验证码，返回到页面显示验证码图片
    @RequestMapping("getVerifyCode")
    public void getVerifyCode(HttpServletResponse response, HttpSession session){
        //response: 通过响应流把生成的验证图片，响应到页面上
        //session: 使用session来存储服务器生成的验证码

        //1.生成5位数的验证码
        String verifyCode = VerifyCodeUtils.generateVerifyCode(5);
        //2.把验证码转换为小写放入到session中
        session.setAttribute("verifyCode",verifyCode.toLowerCase());
        //3.验证码图片响应到页面上显示
        try {
            VerifyCodeUtils.outputImage(220,35,response.getOutputStream(),verifyCode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //实现验证码服务器验证
    @RequestMapping("checkVerifyCode")
    public @ResponseBody String checkVerifyCode(String yzm, HttpSession session){
        //1.获取服务器上session保存的验证码
        String verifyCode = (String) session.getAttribute("verifyCode");
        //2.把服务器上session保存的验证码跟前端传递过来的验证码进行对比
        //yzm.toLowerCase() : 需要转换为小写
        if(verifyCode.equals(yzm.toLowerCase())){
            return "success"; // 验证成功
        }else{
            return "fail"; //验证失败
        }
    }

    //根据用户名和密码验证是否登录成功
    @RequestMapping("checkLogin")
    public @ResponseBody String checkLogin(User user, HttpSession session){
        //将用户输入的密码（123456）进行MD5加密（e10adc3949ba59abbe56e057f20f883e），再到数据库进行条件查询登陆
        user.setPwd(MD5.md5crypt(user.getPwd()));
        try {
            User loginUser = userService.findTByParams(user);
            if(loginUser!=null){  //判断能查询到系统用户数据
                //将登录查询出的用户数据放入到session容器中
                session.setAttribute("loginUser",loginUser);
                return "success";
            }else {
                return "fail";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    //用户退出
    @RequestMapping("/exitUser")
    public @ResponseBody String exitUser(HttpSession session){
        try {
            //1.将session容器中的用户数据删除掉
            session.removeAttribute("loginUser");
            return "success";
        }catch (Exception e){
            e.printStackTrace();
            return "error";
        }
    }
}
