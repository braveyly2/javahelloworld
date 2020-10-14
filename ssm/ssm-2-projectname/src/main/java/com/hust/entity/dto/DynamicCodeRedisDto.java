package com.hust.entity.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Lyh
 * @Title: DynamicCodeRedisDto
 * @Description: 动态码缓存中的数据结构（通用）
 * @date 2018/9/1 13:45
 */
@Data
public class DynamicCodeRedisDto implements Serializable {
    private static final long serialVersionUID = 6919292775624934796L;

    /**
     * 目标号（手机或者邮箱）
     */
    private String target;
    /**
     * 动态码内容
     */
    private String code;
    /**
     * 验证失败次数
     */
    private int failCount;
}
