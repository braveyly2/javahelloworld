package com.hust.lw.model.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hust.lw.utils.PublicUtil;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class User {
    private Long id;

    private String mobile;

    private String nickName;

    private String email;

    private String userName;

    private String name;

    private String address;

    private String image;

    private String role;

    private String passwd;

    private Integer status;

    private LocalDateTime statusTime;

    private String statusLong;

    private String privilege;

    private LocalDateTime createTime;

    private LocalDateTime lastLoginTime;

    private String loginIp;

    private Integer loginFailedCount;

    private Integer onlineStatus;

    private LocalDateTime expireTime;

    private Integer terminalBindStatus;//终端绑定开启状态 1：开启  0：关闭

    @JsonProperty("createTime")
    private String getCreateTimeStr() {
        if (PublicUtil.isNotEmpty(createTime)) {
            return String.valueOf(PublicUtil.getUTCTimeMillis(createTime));
        }
        return null;
    }

    @JsonProperty("createTime")
    private void setCreateTimeStr(String createTime) {
        this.createTime = PublicUtil.getUTCDate(Long.parseLong(createTime));
    }

    @JsonProperty("lastLoginTime")
    private String getLastLoginTimeStr() {
        if (PublicUtil.isNotEmpty(lastLoginTime)) {
            return String.valueOf(PublicUtil.getUTCTimeMillis(lastLoginTime));
        }
        return null;
    }

    @JsonProperty("lastLoginTime")
    private void setLastLoginTimeStr(String lastLoginTime) {
        this.lastLoginTime = PublicUtil.getUTCDate(Long.parseLong(lastLoginTime));
    }

    @JsonProperty("expireTime")
    private String getExpireTimeStr() {
        if (PublicUtil.isNotEmpty(expireTime)) {
            return String.valueOf(PublicUtil.getUTCTimeMillis(expireTime));
        }
        return null;
    }

    @JsonProperty("expireTime")
    private void setExpireTimeStr(String expireTime) {
        this.expireTime = PublicUtil.getUTCDate(Long.parseLong(expireTime));
    }

    @JsonProperty("statusTime")
    private String getStatusTimeStr() {
        if (PublicUtil.isNotEmpty(statusTime)) {
            return String.valueOf(PublicUtil.getUTCTimeMillis(statusTime));
        }
        return null;
    }

    @JsonProperty("statusTime")
    private void setStatusTimeStr(String statusTime) {
        this.statusTime = PublicUtil.getUTCDate(Long.parseLong(statusTime));
    }
}