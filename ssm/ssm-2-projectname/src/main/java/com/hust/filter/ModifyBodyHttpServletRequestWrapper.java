package com.hust.filter;

import org.apache.commons.lang.StringUtils;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
     * 重写request，获取body数据的时候读取新的body
     **/
class ModifyBodyHttpServletRequestWrapper extends HttpServletRequestWrapper {

        // 重新赋值的body数据
        private String bodyJsonStr;

        public ModifyBodyHttpServletRequestWrapper(HttpServletRequest request, String bodyJsonStr) {
            super(request);
            this.bodyJsonStr = bodyJsonStr;
        }

        @Override
        public ServletInputStream getInputStream() throws IOException {
            if(StringUtils.isEmpty(bodyJsonStr)) {
                bodyJsonStr = "";
            }
            // 必须指定utf-8编码，否则json请求数据中如果包含中文，会出现异常
            final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bodyJsonStr.getBytes("utf-8"));
            ServletInputStream servletInputStream = new ServletInputStream() {
                @Override
                public int read() throws IOException {
                    return byteArrayInputStream.read();
                }
            };
            return servletInputStream;
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

