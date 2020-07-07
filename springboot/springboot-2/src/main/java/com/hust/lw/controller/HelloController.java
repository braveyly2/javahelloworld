package com.hust.lw.controller;

import com.hust.lw.model.entity.PostInfo;
import com.hust.lw.model.entity.User;
import com.hust.lw.utils.FooProperties;
import com.hust.lw.utils.GlobalVariable;
import com.hust.lw.utils.UserConfig;
import javafx.application.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class HelloController {
    @Value("${test.user.username}")
    private String name;

    @Value("${test.user.desc}")
    private String password;


    @Autowired
    UserConfig userConfig;

    @RequestMapping(value="/hello", method= RequestMethod.POST)
    @ResponseBody
    public User Hello(){
        User user = new User();
        user.setId(new Long((long)1));
        user.setUserName(name);
        user.setPassword(password);
        return user;
    }

    @RequestMapping(value="/hello1", method= RequestMethod.POST)
    @ResponseBody
    public Map<String,String> Hello1(){
        return userConfig.getPerson();
    }

    @RequestMapping(value="/hello2", method= RequestMethod.POST)
    @ResponseBody
    public List<String> Hello2(){
        return userConfig.getList();
    }

    @RequestMapping(value="/hello3", method= RequestMethod.POST)
    public String Hello3(){
        // ∞Û∂®ºÚµ•≈‰÷√
        FooProperties foo = GlobalVariable.binder.bind("com.didispace", Bindable.of(FooProperties.class)).get();
        return foo.getFoo();
    }

    @RequestMapping(value="/hello4", method= RequestMethod.POST)
    public List<String> Hello4(){
        // ∞Û∂®ºÚµ•≈‰÷√
        List<String> post = GlobalVariable.binder.bind("com.didispace.post", Bindable.listOf(String.class)).get();

        return post;
    }

    @RequestMapping(value="/hello5", method= RequestMethod.POST)
    @ResponseBody
    public List<PostInfo> Hello5(){

        // ∞Û∂®ºÚµ•≈‰÷√
        List<PostInfo> posts = GlobalVariable.binder.bind("com.didispace.posts", Bindable.listOf(PostInfo.class)).get();

        return posts;
    }
}
