package com.hust.filter;
import com.alibaba.fastjson.JSON;
import com.hust.constant.ErrorCodeEnum;
import com.hust.constant.GlobalConstant;
import com.hust.entity.domain.User;
import com.hust.entity.dto.TokenDataDto;
import com.hust.service.TokenService;
import com.hust.service.UserService;
import com.hust.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class AccessPurviewFilter extends HttpServlet implements Filter{

    @Autowired
    TokenService tokenService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {


        String requestPath = ((HttpServletRequest)servletRequest).getServletPath();
        //filterChain.doFilter(servletRequest,servletResponse);//just for debug TDRESULT
        if(requestPath.contains("/user/login") || requestPath.contains("/user/sms-code/no-token/get") || requestPath.contains("/user/register")){
            filterChain.doFilter(servletRequest,servletResponse);
            return;
        }

        InputStream in = servletRequest.getInputStream();
        LogUtil.info("body123: < before >", "AccessPurviewFilter");
        String body = StreamUtils.copyToString(in, Charset.forName(GlobalConstant.DEFAULT_CHARSET));
        LogUtil.info(String.format("body: < %s >", body), "AccessPurviewFilter");
        TDRequest requestInfo = JSON.parseObject(body, TDRequest.class);
        //token有效性校验
        TDResponse<TokenDataDto> tdResponse;
        BasicOutput basicOutput;

        try {
            tdResponse = tokenService.isTokenValid(requestInfo);
            if(tdResponse.getBasic().getCode() == ErrorCodeEnum.TD200.code()){
                requestInfo.setTokenDataDto(tdResponse.getData());
                ModifyBodyHttpServletRequestWrapper modifyBodyHttpServletRequestWrapper = new ModifyBodyHttpServletRequestWrapper((HttpServletRequest)servletRequest, JSON.toJSONString(requestInfo));
                filterChain.doFilter(modifyBodyHttpServletRequestWrapper,servletResponse);
            }else{
                User user = new User();
                user.setId(111);
                user.setName("rrrr");
                user.setPassword("rtttt");
                user.setMark("this is rrrr");
                returnJson(servletResponse,user);
            }
        }
        catch (RuntimeException ex) {

        }
    }

    private void returnJson(ServletResponse response, User user){
        String json = JSON.toJSONString(user);
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=utf-8");
        try {
            writer = response.getWriter();
            writer.print(json);

        } catch (IOException e) {
            System.out.println("e  is " + e);
        } finally {
            if (writer != null)
                writer.close();
        }
    }
}
