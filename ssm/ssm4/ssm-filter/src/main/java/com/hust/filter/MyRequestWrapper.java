package com.hust.filter;

import org.apache.commons.lang.StringUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MyRequestWrapper extends HttpServletRequestWrapper {
    private String bodyJsonStr;
    private ServletInputStream in;

    public MyRequestWrapper(HttpServletRequest request, String bodyJsonStr) {
        super(request);
        this.bodyJsonStr = bodyJsonStr;
        this.in = null;
    }

    class WrapperServletInputStream extends ServletInputStream  {
        private ByteArrayInputStream bis;

        public WrapperServletInputStream() throws IOException{
            if(StringUtils.isEmpty(bodyJsonStr)) {
                bodyJsonStr = "";
            }
            bis = new ByteArrayInputStream(bodyJsonStr.getBytes("utf-8"));
        }

        @Override
        public boolean isFinished() {
            return false;
        }

        @Override
        public boolean isReady() {
            return false;
        }

        @Override
        public void setReadListener(ReadListener readListener) {

        }

        @Override
        public int read() throws IOException {
            return bis.read();
        }
    };

    @Override
    public ServletInputStream getInputStream() throws IOException {
        in = new WrapperServletInputStream();
        return in;
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(this.getInputStream()));
    }

    public String getBodyJsonStr() {
        return bodyJsonStr;
    }

    public void setBodyJsonStr(String bodyJsonStr) {
        this.bodyJsonStr = bodyJsonStr;
    }
}
