package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.impl.LoginService;
import cn.itcast.travel.service.impl.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

@WebServlet("/user/*")
public class UserServlet extends BaseServlet {
    //3调用service完成注册
    private UserService userService = new UserService();
    public void registe(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取数据
        Map<String, String[]> map = request.getParameterMap();
        //1.1判断验证码
        String check = request.getParameter("check");
        HttpSession session = request.getSession();
        String  checkcode_server = (String)session.getAttribute("CHECKCODE_SERVER");

        session.removeAttribute("CHECKCODE_SERVER");
        ResultInfo info = new ResultInfo();
        if (checkcode_server == null||!checkcode_server.equalsIgnoreCase(check)){
            info.setFlag(false);
            info.setErrorMsg("验证码错误！请刷新验证码");
            //将info对象序列话为json
            writeValueAsString(info,response);
            return;
        }
        //2\分装对象
        User user = new User();

        try {
            BeanUtils.populate(user,map);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //3调用service完成注册
        //UserService userService = new UserService();
        boolean flag = false;
        try {
            flag = userService.registe(user);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //4.响应页面
        if (flag){
            //注册成功
            info.setFlag(true);
        }else{
            info.setFlag(false);
            info.setErrorMsg("注册失败！账户已存在");
        }
       writeValueAsString(info,response);
    }

    public void active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取code
        String code = request.getParameter("code");


        if(code != null){
            //调用service
            User user = new User();
            user.setCode(code);
           // UserService service = new UserService();
            boolean active = userService.active(user);
            String msg = null;
            if(active){
                //激活成功
                msg = "激活成功，请<a href='login.html'>登录</a>";
            }else {
                //注册失败
                msg = "激活失败！授权码不存在";

            }
            //设置content-type
            response.setContentType("text/html;charset:utf-8");
            response.getWriter().write(msg);
        }
    }
    public void findName(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Object user = request.getSession().getAttribute("user");
        if(!"null".equals(user)){
            writeValueAsString(user,response);
        }
    }
    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取数据
        Map<String, String[]> map = request.getParameterMap();
        //1.1判断验证码
        String check = request.getParameter("check");
        HttpSession session = request.getSession();
        String checkcode_server = (String)session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");
        ResultInfo info = new ResultInfo();
        if (checkcode_server == null||!checkcode_server.equalsIgnoreCase(check)){
            info.setFlag(false);
            info.setErrorMsg("验证码错误！请刷新验证码");
            //将info对象序列话为json
           writeValueAsString(info,response);
            return;
        }
        //2\分装对象
        User user = new User();
        try {
            BeanUtils.populate(user,map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //3调用service完成登录
        User login = null;
        LoginService loginService = new LoginService();
        try {
            login = loginService.login(user);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //4.响应页面
        if (login != null){
            //账户存在
            if ("Y".equals(login.getStatus())){
                info.setFlag(true);
                info.setData(login);
                session.setAttribute("user",info.getData());
            }else {
                info.setFlag(false);
                info.setErrorMsg("登录失败，账户未激活，请查看邮箱并激活！");
            }

        }else{
            info.setFlag(false);
            info.setErrorMsg("登录失败，用户名或密码错误！");
        }
        writeValue(info,response);
    }
    public void exit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.invalidate();
        response.sendRedirect(request.getContextPath()+"/login.html");
    }
}
