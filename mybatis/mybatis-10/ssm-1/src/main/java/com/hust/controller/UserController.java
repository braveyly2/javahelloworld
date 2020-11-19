package com.hust.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hust.ResultHandler.UserResultHandler;
import com.hust.entity.User;
import com.hust.service.UserService;
import net.sf.json.JSON;
import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.batch.MyBatisCursorItemReader;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Iterator;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private MyBatisCursorItemReader myBatisCursorItemReader;

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    @RequestMapping(value="login",method= RequestMethod.GET)
    public String login(){
        return "login";
    }

    @RequestMapping(value="userInfo",method= RequestMethod.GET)
    public String userinfo(){
        return "userInfo";
    }

    @RequestMapping(value="register",method= RequestMethod.GET)
    public String register(){
        return "register";
    }

    @RequestMapping(value="/user/login",method=RequestMethod.POST)
    public String login(HttpSession session, Model model, @RequestParam("userName") String name, @RequestParam("password") String password){
        User user = userService.selectByName(name);
        if(null == user){
            model.addAttribute("status","1");
        }
        else{
            if(user.getName().equals(name) && user.getPassword().equals(password)) {
                model.addAttribute("status", "0");
                session.setAttribute("user", user);
                return "userInfo";
            }
            else if(user.getName().equals(name) && !user.getPassword().equals(password)) {
                model.addAttribute("status", "2");
                session.setAttribute("user", user);
            }
        }

        return "login";
    }

    @RequestMapping(value="/user/sessionInfo",method=RequestMethod.GET)
    public String sessionInfo(HttpSession session){
        System.out.println("session.id=" + session.getId());
        User user = (User) session.getAttribute("user");
        System.out.println("username =" + (String) user.getName());
        return "userInfo";
    }
    @RequestMapping(value="/user/logout",method=RequestMethod.GET)
    public String logout(HttpSession session){
        session.invalidate();
        return "login";
    }

    @RequestMapping(value="/user/register",method=RequestMethod.POST)
    public String register(Model model, @RequestParam("userName") String name, @RequestParam("password") String password){
        User user = userService.selectByName(name);
        //cannot register for the same username
        if(null != user){
            model.addAttribute("status","1");
            return "register";
        }

        User userAdd = new User();
        userAdd.setName(name);
        userAdd.setPassword(password);
        userAdd.setMark("thisisforregister");

        userService.insert(userAdd);
       // model.addAttribute("status","0");
        return "register";
    }

    @RequestMapping(value="/user/getallbystream",method=RequestMethod.POST)
    public String getallbystream(@RequestParam("userName") String name, @RequestParam("password") String password){
        //User user = userService.selectByName(name);
        myBatisCursorItemReader.open(new ExecutionContext());
        Long count = 0L;
        User user = new User();
        try{
        while ((user = (User)myBatisCursorItemReader.read()) != null) {

            System.out.println(user);
            ++count;
            System.out.println(count);

        }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

        return "login";
    }

    @RequestMapping(value="/user/getresulthandler",method=RequestMethod.POST)
    public String getresulthandler(@RequestParam("userName") String name, @RequestParam("password") String password){
        //User user = userService.selectByName(name);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        sqlSession.select("com.hust.dao.UserMapper.selectAllByStream",null,new ResultHandler() {
            @Override
            public void handleResult(ResultContext resultContext) {
                User user = (User) resultContext.getResultObject();
                System.out.println(resultContext.getResultCount());
                System.out.println(user);
            }
        });
        return "login";
    }

    @RequestMapping(value="/user/getselfresulthandler",method=RequestMethod.POST)
    public String getselfresulthandler(@RequestParam("userName") String name, @RequestParam("password") String password){
        //User user = userService.selectByName(name);
        UserResultHandler userResultHandler = new UserResultHandler();
        sqlSessionTemplate.select("com.hust.dao.UserMapper.selectAllByStream",userResultHandler);
        List<User> userList = userResultHandler.getResultInfoList();
        return "login";
    }

    @RequestMapping(value="/user/getsqlsessionselectcursor",method=RequestMethod.POST)
    public String getsqlsessionselectcursor(@RequestParam("userName") String name, @RequestParam("password") String password){
        //User user = userService.selectByName(name);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        Cursor<User> users = sqlSession.selectCursor("selectAllByStream");
        Iterator<User> iter = users.iterator();
        while(iter.hasNext()){
            System.out.println(iter.next());
        }
        return "login";
    }

        @RequestMapping(value="/user/getpagehelper",method=RequestMethod.POST)
        @ResponseBody
        public PageInfo<User> getpageinfouser(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize, @RequestParam("userName") String userName){
        //User user = userService.selectByName(name);
        PageHelper.startPage(pageNum,pageSize);
        List<User> userList = userService.selectAllByName(userName);
        PageInfo<User> pageInfo = new PageInfo<User>(userList);
        return pageInfo;
    }
}
