package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.impl.RouteService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {
    private RouteService routeService = new RouteService();
    public void pageQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.接收参数
        String currentPage_str = request.getParameter("currentPage");
        String pageSize_str = request.getParameter("pageSize");
        //解决tomcat7中文获取参数乱码
        //2接收搜索参数
        String rname  = "%";
        String rname1 = request.getParameter("rname");
        if(rname1 !=  null || rname1.length() > 0){
        rname = new String(rname1.getBytes("iso-8859-1"),"utf-8");}
        String cid_str = request.getParameter("cid");
        //2.处理参数

        int currentPage = 0;//当前页面标号，默认1
        if (currentPage_str != null && currentPage_str.length() > 0){
            currentPage = Integer.parseInt(currentPage_str);
        }else{
            currentPage = 1;
        }
        int pageSize = 0;//页面显示最大值,默认5

        if (pageSize_str != null && pageSize_str.length() > 0){

            pageSize = Integer.parseInt(pageSize_str);
        }else {
            pageSize = 5;
        }
        int cid = 0;//类别id
        if (cid_str != null && cid_str.length() > 0 && !"null".equals(cid_str)){
            cid = Integer.parseInt(cid_str);
        }

        //查询

        PageBean<Route> routePageBean = routeService.pageQuery(currentPage, pageSize, cid,rname);

        //序列化json
        writeValue(routePageBean,response);
    }
    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            //1.接收rid
        String rid = request.getParameter("rid");
        //2调用service查询
       Route route = routeService.findOne(rid);
        //3转换为json返回
        writeValue(route,response);
    }
    public void findFa(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //接收参数
        //2. 获取当前登录的用户 user
        User user = (User) request.getSession().getAttribute("user");
        int uid;//用户id
        if(user == null){
            //用户尚未登录
            uid = 0;
        }else{
            //用户已经登录
            uid = user.getUid();
        }
        String rid_str = request.getParameter("rid");
        int rid = Integer.parseInt(rid_str);
        //是否收藏
        boolean flag = routeService.findFa(uid, rid);
//        写回客户端
        writeValue(flag,response);
    }
    public void findFaCount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String rid_str = request.getParameter("rid");
        int rid = Integer.parseInt(rid_str);
        int faCout = routeService.findFaCout(rid);
        writeValue(faCout,response);
    }
    public void addFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //接收参数
        //2. 获取当前登录的用户 user
        User user = (User) request.getSession().getAttribute("user");
        int uid;//用户id
        if(user == null){
            //用户尚未登录
            uid = 0;
        }else{
            //用户已经登录
            uid = user.getUid();
        }
        String rid_str = request.getParameter("rid");
        int rid = Integer.parseInt(rid_str);
        Integer faCout = routeService.findFaCout(rid);
        int count = faCout + 1;
        routeService.addFavorite(rid,uid,count);
    }
}
