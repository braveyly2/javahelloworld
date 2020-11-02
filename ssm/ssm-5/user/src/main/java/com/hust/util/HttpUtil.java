package com.hust.util;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

/**
 * Http请求封装类
 */
@Slf4j
public class HttpUtil {
    /**
     * 发送GET请求
     *
     * @param url 请求URL
     * @return 结果
     * @throws IOException
     */
    public static String sendGet(String url) throws IOException {
        URL oUrl;
        String result = "";
        BufferedReader in = null;
        try {
            oUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) oUrl.openConnection();
            conn.setUseCaches(false);
            conn.setRequestMethod("GET");
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            return result;
        } catch (MalformedURLException e) {
            log.error("HTTP发送异常", "ms-user", e);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                log.error("IO关闭异常", "ms-user", ex);
            }
        }
        return result;
    }

    /**
     * 发送POST请求
     *
     * @param url   请求url
     * @param param 参数
     * @return 结果
     * @throws IOException
     */
    public static String sendPost(String url, String param) throws IOException {
        return sendPost(url, param, null);
    }

    /**
     * 发送POST请求
     *
     * @param url     请求URL
     * @param param   参数
     * @param headers 头部信息
     * @return 结果
     * @throws IOException
     */
    public static String sendPost(String url, String param, Map<String, String> headers) throws IOException {
        URL oUrl;
        PrintWriter out = null;
        BufferedReader in = null;
        StringBuilder result = new StringBuilder();
        try {
            oUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) oUrl.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            if (headers != null) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    conn.setRequestProperty(entry.getKey(), entry.getValue());
                }
            }
            out = new PrintWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8"));
            out.print(param);
            out.flush();
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line).append("\r\n");
            }
        } catch (MalformedURLException e) {
            log.error("HTTP发送异常", "ms-user", e);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                log.error("IO关闭异常", "ms-user", ex);
            }
        }
        return result.toString();
    }
}