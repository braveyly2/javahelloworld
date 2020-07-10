package com.hust.lw.controller;

import com.hust.lw.model.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
public class UserController {

    @RequestMapping(value="/add", method= RequestMethod.POST)
    @ResponseBody
    User add(@RequestBody User user){
        if(null == user.getUserName() || user.getUserName().length() <=0){
            log.debug("username[debug] is null");
            log.info("username[info] is null");
            log.debug("username[error] is null");
        }
        return user;
    }
}
