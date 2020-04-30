package cn.itcast.travel.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class BaseServlet extends HttpServlet {
    @Override
    public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //完成方法分发
        //1获取请求路径
        String uri = req.getRequestURI();
        //2获取方法名称
        String methondName = uri.substring(uri.lastIndexOf('/') + 1);

        //3获取方法对象
        try {
            Method method = this.getClass().getMethod(methondName, HttpServletRequest.class, HttpServletResponse.class);
            //4执行方法
            try {
                method.invoke(this,req,resp);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
            }
        } catch (NoSuchMethodException e) {
        }

    }

    /**
     * 序列化json
     * @param object
     * @param response
     * @throws IOException
     */
    public void writeValue(Object object,HttpServletResponse response) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset:utf-8");
        mapper.writeValue(response.getOutputStream(),object);
    }
    public void writeValueAsString(Object object,HttpServletResponse response) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(object);

        //将JSON数据写回前端
        //设置content-type
        response.setContentType("application/json;charset:utf-8");
        response.getWriter().write(json);
    }
}
