1、pom.xml显示unknown
右键pom.xml点击maven->reimport

2、调整目录结构
其中applicationContext.xml和*-servlet.xml放在resources下
web.xml放在webapp/WEB-INF下
https://blog.csdn.net/lch520baby/article/details/89374819

3、修改*-servlet.xml路径
https://blog.csdn.net/weixin_34194702/article/details/92248296

        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>classpath:dispatcher-servlet.xml</param-value>
        </init-param>


4、jsp文件始终找不到，对比观察得知webapp文件夹上没有小蓝点
关于idea中新建web项目 webapp文件夹没有小蓝点 ，启动服务，访问不到解决方案

5、获取各种路径
        System.out.println(httpServletRequest.getContextPath());
        System.out.println(httpServletRequest.getServletPath());
        System.out.println(httpServletRequest.getRequestURI());
        System.out.println(httpServletRequest.getRealPath("/"));

