# javahelloworld
1、环境搭建及controller与jsp互传数据（SpringMVC-1）
（1）新建SpringMVC工程

（2）部署Tomcat环境


（3）缺少Spring MVC相关类库引用


完成如上的3步后，则可以将这个空的SpringMVC工程跑起来了。

参考：
IDEA建立Spring MVC Hello World 详细入门教程
https://www.cnblogs.com/wormday/p/8435617.html

（4）创建controller类，找不到servlet
出现了问题： javax.servlet 包找不到
解决： 将本地 Tomcat 服务器的目录下【lib】文件夹下的 servlet-api.jar 包拷贝到工程【lib】文件夹下，添加依赖

这里面的“添加依赖”则出现了误会
参考：
cannot resolve symbol 'Servlet'
https://blog.csdn.net/ycfn97/article/details/101114915

（5）其他步骤，参考：
Spring MVC【入门】就这一篇！
https://www.jianshu.com/p/91a2d0a1e45a

说明：
A、 指定controller的方法，要么特定指定，要么泛指
    <bean id="simpleUrlHandlerMapping"
          class="org.springframework.web.servlet.handler.SimpleUrlHandlerMapping">
        <property name="mappings">
            <props>

                <!-- /hello 路径的请求交给 id 为 helloController 的控制器处理-->


            <prop key="/hello">helloController</prop>
        </props>
    </property>
</bean>
<bean id="helloController" class="com.hust.lw.controller.HelloController"></bean>


<!-- 扫描ccom.hust.lw.controller下的组件 -->
<context:component-scan base-package="com.hust.lw.controller"/>
如上只能选一个

B、在用模型传参时，模型中的set方法实现中，必须this.userName和this.password这样的格式

C、 jsp向后台controller传参一共有如下几种方法：

jnhs-SpringMVC jsp页面向controller传递参数的五种方式
https://www.cnblogs.com/jnhs/p/9961589.html

D、 Controller向前端jsp回传数据的方法：原生API完成、使用modelandview
、使用model、使用modelAttribute
