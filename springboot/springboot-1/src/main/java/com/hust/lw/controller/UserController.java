package com.hust.lw.controller;

import com.hust.lw.model.dto.UserIDDto;
import com.hust.lw.model.entity.User;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping(value="/users")
public class UserController {
    private static Map<Long, User> users = Collections.synchronizedMap(new HashMap<Long, User>());

    @RequestMapping(value="/add", method= RequestMethod.POST)
    Long add(@RequestBody User user){
        User userResult = users.put(user.getId(),user);
        if(null != userResult)
            return userResult.getId();
        else
            return new Long((long)0);
    }

    @RequestMapping(value="/delete", method= RequestMethod.POST)
    Long delete(@RequestBody UserIDDto user){
        User userResult = users.remove(user.getId());
        if(null != userResult)
            return userResult.getId();
        else
            return new Long((long)0);
    }

    @RequestMapping(value="/modify", method= RequestMethod.POST)
    Long modify(@RequestBody User user){
        User userResult = users.get(user.getId());
        if(null != userResult) {
            userResult.setUserName(user.getUserName());
            userResult.setPassword(user.getPassword());
            userResult.setAge(user.getAge());
            return userResult.getId();
        }
        else
            return new Long((long)0);
    }

    @RequestMapping(value="/get", method= RequestMethod.POST)
    User get(@RequestBody UserIDDto user){
        return users.get(user.getId());
    }

    @RequestMapping(value="/getall", method= RequestMethod.POST)
    List<User> getAll(){
        List<User> r = new ArrayList<User>(users.values());
        return r;
    }
}
