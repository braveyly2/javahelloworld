1��pom.xml��ʾunknown
�Ҽ�pom.xml���maven->reimport

2������Ŀ¼�ṹ
����applicationContext.xml��*-servlet.xml����resources��
web.xml����webapp/WEB-INF��
https://blog.csdn.net/lch520baby/article/details/89374819

3���޸�*-servlet.xml·��
https://blog.csdn.net/weixin_34194702/article/details/92248296

        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>classpath:dispatcher-servlet.xml</param-value>
        </init-param>


4��jsp�ļ�ʼ���Ҳ������Աȹ۲��֪webapp�ļ�����û��С����
����idea���½�web��Ŀ webapp�ļ���û��С���� ���������񣬷��ʲ����������

5����ȡ����·��
        System.out.println(httpServletRequest.getContextPath());
        System.out.println(httpServletRequest.getServletPath());
        System.out.println(httpServletRequest.getRequestURI());
        System.out.println(httpServletRequest.getRealPath("/"));

