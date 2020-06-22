package com.hust.aspect;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


/**
 * @author Lyh
 * @Title: ConfigUtil
 * @Description: TODO
 * @date 2019/7/19 17:56
 */
@Component
public class ConfigUtil {

    private static String disabledApi;

    @Value("${disabledApi}")
    public void setDisabledApi(String str) {
        disabledApi = str;
    }

    public String getDisabledApi(){
        return disabledApi;
    }


    private static String httpPrefix;

    @Value("${httpPrefix}")
    public void setHttpPrefix(String str) {
        httpPrefix = str;
    }

    public String getHttpPrefix(){
        return httpPrefix;
    }
}
