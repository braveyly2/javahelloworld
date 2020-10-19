package com.hust.aspect;

import com.hust.annotation.TDAuth;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.aspectj.lang.reflect.SourceLocation;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import com.hust.annotation.TDAuth;

@Aspect
@Component
@Slf4j
public class AspectAuth {
    /**
     * 定义一个切入点表达式,用来确定哪些类需要代理
     * execution(* com.lq.demo.demo.mode.aop.UserController.*(..)代表UserController下所有方法都会被代理
     */
    @Pointcut("execution(* com.hust.controller..*(..))")
    public void enhanceMethod() {
    }

    /**
     * 前置方法,在目标方法执行前执行
     *
     * @param joinPoint 封装了代理方法信息的对象,若用不到则可以忽略不写
     */
    @Before("enhanceMethod()")
    public void beforeMethod(JoinPoint joinPoint) {
        String targetMethodName = joinPoint.getSignature().getName();
        log.info("AspectAuth: target method name: " + targetMethodName);
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        RequestMapping annotation = method.getAnnotation(RequestMapping.class);
        Annotation[] declaredAnnotations = method.getDeclaredAnnotations();
        TDAuth auth = method.getAnnotation(TDAuth.class);
        if(auth==null){
            throw new RuntimeException("no  auth");
        }

        String[] value = annotation.value();
        String[] params = annotation.params();

        for (String s : params) {
            log.info("params:" + s);
        }

        for (String s : value) {
            log.info("annotationvalue:" + s);
        }
        Class<? extends Annotation> annotationType = annotation.annotationType();
        String name = annotation.name();
        Class clazz = joinPoint.getSignature().getDeclaringType();
        log.info("AspectAuth: target method attribute: ：" + targetMethodName);
        String type = Modifier.toString(joinPoint.getSignature().getModifiers());
        log.info("AspectAuth: target method type: ：" + type);

        Object[] args = joinPoint.getArgs();
        for (int i = 0; i < args.length; i++) {
            log.info("AspectAuth: the " + (i + 1) + " parameter is ：" + (String) args[i]);
        }
        Object target = joinPoint.getTarget();
        log.info("AspectAuth: the proxyed object is " + target);
        Object aThis = joinPoint.getThis();
        log.info("AspectAuth: the proxyed object self " + aThis);
        SourceLocation sourceLocation = joinPoint.getSourceLocation();
        log.info("sourceLocation:" + sourceLocation);

        String kind = joinPoint.getKind();
        log.info("kind:" + kind);

    }
    //
    @After("enhanceMethod()")
    public void after() {
        log.info("after");
    }

    @AfterReturning(pointcut = "enhanceMethod()",returning = "rs")
    public void afterReturning(Object rs) {
        log.info("afterreturning"+rs);
    }

    @AfterThrowing(pointcut = "enhanceMethod()",throwing = "e")
    public void afterThrowing(JoinPoint joinPoint,Throwable e) {

        log.info("afterthrowing e:"+e.getMessage() );
    }

    /**
     * 环绕方法,可自定义目标方法执行的时机
     *
     * @param pjd JoinPoint的子接口,添加了
     *            Object proceed() throws Throwable 执行目标方法
     *            Object proceed(Object[] var1) throws Throwable 传入的新的参数去执行目标方法
     *            两个方法
     * @return 此方法需要返回值, 返回值视为目标方法的返回值
     */
    @Around("enhanceMethod()")
    public Object aroundMethod(ProceedingJoinPoint pjd) throws Throwable {
        Object result = null;

        //前置通知
        System.out.println("AspectAuth: target method run before ...");
        //执行目标方法
        //result = pjd.proceed();
        //用新的参数值执行目标方法
        String o = (String) pjd.getArgs()[0];
        result = pjd.proceed(new Object[]{"newid" + o});
        //返回通知
        System.out.println("AspectAuth: target method return result after...");

        //后置通知
        System.out.println("AspectAuth: target method run after...");

        return result;
    }

    @Around("@annotation(tdAuth)")
    public Object aroundMethod2(ProceedingJoinPoint pjd, TDAuth tdAuth) throws Throwable {
        Object obj = null;
        String o = (String) pjd.getArgs()[0];
        System.out.println("tdAuth.isCheckToken() is " + tdAuth.isCheckToken());
        System.out.println("tdAuth.value() is " + tdAuth.value());
        obj = pjd.proceed(new Object[]{"newid" + o});
        return obj;
    }
}
/*
AspectAuth: target method run before ...
 - AspectAuth: target method name: authannotest
 - annotationvalue:/authannotest
 - AspectAuth: target method attribute: ��authannotest
 - AspectAuth: target method type: ��public
 - AspectAuth: the 1 parameter is ��newidliwei
 - AspectAuth: the proxyed object is com.hust.controller.UserController@4c80b16e
 - AspectAuth: the proxyed object self com.hust.controller.UserController@4c80b16e
 - sourceLocation:org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint$SourceLocationImpl@79cddead
 - kind:method-execution
AspectAuth: target method return result after...
AspectAuth: target method run after...
 - after
 - afterreturninglogin
*/