package com.hust.controller;

import com.hust.entity.Article;
import com.hust.entity.TTransaction;
import com.hust.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import org.springframework.web.servlet.view.xml.MappingJackson2XmlView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class TTransactionController {


    @RequestMapping(value="/transaction/testvalidator",method= RequestMethod.POST)
    @ResponseBody
    public String testValidator(@Valid @RequestBody TTransaction transaction, BindingResult bindingResult){
        System.out.println(transaction);
        if(bindingResult.hasErrors()){
            for (ObjectError error : bindingResult.getAllErrors()) {
                System.out.println(error.getDefaultMessage());
            }
        }
        return "userInfo";
    }

    @RequestMapping(value="/transaction/testMappingJackson2JsonView",method= RequestMethod.POST)
    public ModelAndView testMappingJackson2JsonView(){
        ModelAndView mv = new ModelAndView();
        mv.setView(new MappingJackson2JsonView());
        //mv.setView(new MappingJackson2XmlView());
        TTransaction tTransaction = new TTransaction();
        tTransaction.setUserId(1L);
        tTransaction.setProductId(1L);
        tTransaction.setQuantity(100);
        tTransaction.setNote("liwei123");
        tTransaction.setPrice(1.0);
        tTransaction.setAmount(2.0);
        tTransaction.setEmail("111@111.com");
        //tTransaction.setDate("");
        mv.addObject("tTransaction",tTransaction);
        return mv;
    }

    @RequestMapping(value="/transaction/testMultiLogicalView",method= RequestMethod.GET)
    public ModelAndView testMultiLogicalView(){
        //ModelAndView mv = new ModelAndView();
        String name="liwei";
        //mv.addObject("name",name);
        return new ModelAndView("123","name",name);
    }

    @RequestMapping(value="/transaction/testExcelView",method= RequestMethod.GET)
    public String testExcelView(ModelMap map){
        List<Article> articles = new ArrayList<Article>();
        for(int i = 0 ; i < 5; i ++){
            Article article = new Article();
            article.setTitle("title" +i);
            article.setContent("content" + i);
            articles.add(article);
        }
        map.addAttribute("articles",articles);
        return "excel";
    }

    @RequestMapping(value="/transaction/testXmlView",method= RequestMethod.GET)
    public String testXmlView(ModelMap map){
        List<Article> articles = new ArrayList<Article>();
        for(int i = 0 ; i < 5; i ++){
            Article article = new Article();
            article.setTitle("title" +i);
            article.setContent("content" + i);
            articles.add(article);
        }
        map.addAttribute("articles",articles);
        return "myXmlView";
    }

    @RequestMapping("/file-upload")
    public ModelAndView upload(@RequestParam(value = "file", required = false) MultipartFile file,
                               HttpServletRequest request, HttpSession session) {
        // 文件不为空
        if(!file.isEmpty()) {
            // 文件存放路径
            //String path = request.getServletContext().getRealPath("/");
            String path="D:\\";
            // 文件名称
            String name = String.valueOf(new Date().getTime()+"_"+file.getOriginalFilename());
            File destFile = new File(path,name);
            // 转存文件
            try {
                file.transferTo(destFile);
            } catch (IllegalStateException | IOException e) {
                e.printStackTrace();
            }
            // 访问的url
            String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/" + name;
        }
        ModelAndView mv = new ModelAndView();
        mv.setViewName("123");
        return mv;
    }
}
