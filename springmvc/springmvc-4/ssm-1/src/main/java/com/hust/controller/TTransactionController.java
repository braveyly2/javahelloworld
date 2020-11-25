package com.hust.controller;

import com.hust.entity.Article;
import com.hust.entity.TTransaction;
import com.hust.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import org.springframework.web.servlet.view.xml.MappingJackson2XmlView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
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

    @RequestMapping(value="/transaction/testMultiLogicalView",method= RequestMethod.POST)
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
}
