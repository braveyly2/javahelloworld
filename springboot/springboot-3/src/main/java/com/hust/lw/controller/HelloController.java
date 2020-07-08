package com.hust.lw.controller;

import com.hust.lw.model.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Api(tags = "Hello管理")
@RestController
public class HelloController {

    @RequestMapping(value="/hello", method= RequestMethod.POST)
    @ResponseBody
    public User Hello(){
        User user = new User();
        user.setId(new Long((long)1));
        return user;
    }

    @RequestMapping(value="/hello1", method= RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value="获取hello")
    public User Hello1(@RequestBody @Valid User user){
        User userResult = new User();
        userResult.setId(user.getId());
        userResult.setUserName(user.getUserName());
        userResult.setPassword(user.getPassword());
        userResult.setEmail(user.getEmail());
        return userResult;
    }

}
