package com.hust.listener;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;

public class MyServletRequestListener implements ServletRequestListener {
    @Override
    public void requestDestroyed(ServletRequestEvent arg0) {
        System.out.println("MyServletRequestListener Destory");

    }

    @Override
    public void requestInitialized(ServletRequestEvent arg0) {
        System.out.println("MyServletRequestListener Init");

        Integer count=null;//请求数量
        Object requestCount=arg0.getServletContext().getAttribute("requestCount");
        if(requestCount==null){
            count=0;
        }else{
            count=Integer.valueOf(requestCount.toString());
        }
        count++;
        System.out.println("current request number："+count.toString());
        arg0.getServletContext().setAttribute("requestCount", count);

        //serverName
        String serverName=arg0.getServletRequest().getServerName();
        System.out.println("serverName:"+serverName);

        //serverPort
        int serverPort=arg0.getServletRequest().getServerPort();
        System.out.println("serverPort:"+serverPort);

        HttpServletRequest servletRequest=(HttpServletRequest)arg0.getServletRequest();

        //requestURI
        String requestURI=servletRequest.getRequestURI();
        System.out.println("requestURI:"+requestURI);

        //requestURL
        String requestURL=servletRequest.getRequestURL().toString();
        System.out.println("requestURL:"+requestURL);

        //servletPath
        String servletPath=servletRequest.getServletPath();
        System.out.println("servletPath:"+servletPath);

        //queryString
        String queryString=servletRequest.getQueryString();
        System.out.println("queryString:"+queryString);

    }
}
