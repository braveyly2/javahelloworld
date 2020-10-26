package com.hust.filter;
import com.alibaba.fastjson.JSON;
import com.hust.entity.domain.User;
import com.hust.accountcommon.util.IdWorker;
//import com.hust.accountcommon.util.LogUtil;
import com.hust.accountcommon.constant.GlobalConstant;
import com.hust.accountcommon.entity.dto.TokenDataDto;
import com.hust.accountcommon.util.apitemplate.BasicOutput;
import com.hust.accountcommon.util.apitemplate.TDRequest;
import com.hust.accountcommon.util.apitemplate.TDResponse;
import com.hust.accountcommon.util.token.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

@Component
@Slf4j
public class AccessPurviewFilter extends HttpServlet implements Filter{

    @Autowired
    TokenUtil tokenUtil;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        String requestPath = ((HttpServletRequest)servletRequest).getServletPath();
        if(requestPath.contains("/user/login") ||
                requestPath.contains("/user/sms-code/no-token/get") ||
                requestPath.contains("/user/register") ||
                requestPath.contains("hellocode") ||
                requestPath.contains("hello") ||
                requestPath.contains("hello2") ||
                requestPath.contains("/user/register1") ||
                requestPath.contains("/user/register2") ||
                requestPath.contains("/user/register3") ||
                requestPath.contains("/user/password/find")){
            filterChain.doFilter(servletRequest,servletResponse);
            return;
        }

        InputStream in = servletRequest.getInputStream();
        String body = StreamUtils.copyToString(in, Charset.forName(GlobalConstant.DEFAULT_CHARSET));
        log.info(String.format("body: < %s >", body), "AccessPurviewFilter");
        TDRequest requestInfo = JSON.parseObject(body, TDRequest.class);
        //token有效性校验
        TDResponse<TokenDataDto> tdResponse;
        BasicOutput basicOutput;

        try {
            TokenDataDto tokenDataDto = tokenUtil.verify(requestInfo.getBasic().getToken(),true,true);

            if(null != tokenDataDto){
                requestInfo.setTokenDataDto(tokenDataDto);
                ModifyBodyHttpServletRequestWrapper modifyBodyHttpServletRequestWrapper = new ModifyBodyHttpServletRequestWrapper((HttpServletRequest)servletRequest, JSON.toJSONString(requestInfo));
                filterChain.doFilter(modifyBodyHttpServletRequestWrapper,servletResponse);
            }else{
                User user = new User();
                user.setId(IdWorker.getInstance().getId());
                user.setMark("Token invalid");
                returnJson(servletResponse,user);
            }
        }
        catch (RuntimeException ex) {
            log.error("token验签失败");
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
