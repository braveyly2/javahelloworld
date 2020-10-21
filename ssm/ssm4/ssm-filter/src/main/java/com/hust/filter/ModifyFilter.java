package com.hust.filter;

import com.hust.entity.User;
import org.springframework.util.StreamUtils;
import com.alibaba.fastjson.JSON;
import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.ResponseWrapper;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.charset.Charset;

public class ModifyFilter extends HttpServlet implements Filter{

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException
    {
        String requestPath = ((HttpServletRequest)request).getServletPath();

        if(!requestPath.contains("/modifyuser")){
            filterChain.doFilter(request,response);
            return;
        }

        InputStream in = request.getInputStream();
        String bodyStr = StreamUtils.copyToString(in, Charset.forName("UTF-8"));
        User user = JSON.parseObject(bodyStr, User.class);
        user.setMark("i am a student");
        MyRequestWrapper myRequestWrapper = new MyRequestWrapper((HttpServletRequest) request, JSON.toJSONString(user));
        MyResponseWrapper myResponseWrapper = new MyResponseWrapper((HttpServletResponse) response);

        filterChain.doFilter(myRequestWrapper, myResponseWrapper);

        byte[] responseBytes = myResponseWrapper.getContent();
        String responseStr = new String(responseBytes);

        //String responseStr2 = myResponseWrapper.getContent().toString();//此方法有问题
        User responseUser = JSON.parseObject(responseStr, User.class);
        responseUser.setName("mynameisnick");

        //ServletOutputStream out = myResponseWrapper.getOutputStream();
        //byte[] responseBytes2 = JSON.toJSONString(responseUser).getBytes();
        //out.write(JSON.toJSONString(responseUser).getBytes());
        //out.flush();
        PrintWriter writer = null;//返回json对象用PrintWriter
        //myResponseWrapper.setCharacterEncoding("UTF-8");
        //myResponseWrapper.setContentType("text/html; charset=utf-8");
        writer = myResponseWrapper.getWriter();
        writer.print(JSON.toJSONString(responseUser));
    }

    @Override
    public void init(FilterConfig arg0)
            throws ServletException
    {

    }

    @Override
    public void destroy()
    {

    }
}
