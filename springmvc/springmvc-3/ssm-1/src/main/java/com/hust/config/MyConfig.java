package com.hust.config;

import org.hibernate.validator.HibernateValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Properties;

@Configuration
public class MyConfig {
    /*方法1：完全在xml中配置
    <!-- 校验器，配置validator -->
    <bean id="validator" class="org.springframework.validation.beanvalidation.LocalValidatorFactoryBean">
        <property name="providerClass" value="org.hibernate.validator.HibernateValidator"></property>
        <property name="validationMessageSource" ref="validationMessageSource"></property>
    </bean>
    <!-- 配置validationMessageSource -->
    <bean id="validationMessageSource" class="org.springframework.context.support.ReloadableResourceBundleMessageSource">
        <!-- 指定校验信息的资源文件的基本文件名称，不包括后缀，后缀默认是properties -->
        <property name="basenames">
            <list>
                <value>classpath:validationMessageSource</value>
            </list>
        </property>
        <!-- 指定文件的编码 -->
        <property name="fileEncodings" value="utf8"></property>
        <!-- 对资源文件内容缓存的时间，单位秒 -->
        <property name="cacheSeconds" value="120"></property>
    </bean>
    * */

    /* //方法2：将xml配置转换为@configuration和@bean
    @Bean(name="validator")
    public LocalValidatorFactoryBean getLocalValidatorFactoryBean(){
        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.setProviderClass(org.hibernate.validator.HibernateValidator.class);
        localValidatorFactoryBean.setValidationMessageSource(getReloadableResourceBundleMessageSource());
        return localValidatorFactoryBean;
    }

    @Bean(name="validationMessageSource")
    public ReloadableResourceBundleMessageSource getReloadableResourceBundleMessageSource(){
        ReloadableResourceBundleMessageSource reloadableResourceBundleMessageSource = new ReloadableResourceBundleMessageSource();
        reloadableResourceBundleMessageSource.setBasenames("classpath:validationMessageSource");
        //reloadableResourceBundleMessageSource.setFileEncodings(new Properties("fileEncodings","utf-8"));
        reloadableResourceBundleMessageSource.setCacheSeconds(120);
        return reloadableResourceBundleMessageSource;
    }
    */

    /*方法3：另外一种生成validator方法
    * */
    @Bean
    public Validator validator() {
        ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class)
                .configure()
                // 快速失败模式
                .failFast(true)
                .addProperty( "hibernate.validator.fail_fast", "true" )
                .buildValidatorFactory();
        return validatorFactory.getValidator();
    }


    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        MethodValidationPostProcessor postProcessor = new MethodValidationPostProcessor();
        ///**设置validator模式为快速失败返回
        postProcessor.setValidator(validator());
        return postProcessor;
    }



}
