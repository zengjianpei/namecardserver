package com.devsjk.namecardserver.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @Auther: zjp
 * @Date: 2020/9/27 23:42
 * @Description:
 */
public class WxUserToken {

    private Long wxUserId;
    private String accessToken;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime valid;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private LocalDateTime curTime;

    private Integer active_mins=300;

    public WxUserToken(){

    }

    public WxUserToken(Long wxUserId,String accessToken){
        this.wxUserId=wxUserId;
        this.accessToken=accessToken;
    }

    public Long getWxUserId() {
        return wxUserId;
    }

    public void setWxUserId(Long wxUserId) {
        this.wxUserId = wxUserId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public LocalDateTime getValid() {
        return valid;
    }

    public void setValid(LocalDateTime valid) {
        this.valid = valid;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getActive_mins() {
        return active_mins;
    }

    public void setActive_mins(Integer active_mins) {
        this.active_mins = active_mins;
    }

    public LocalDateTime getCurTime() {
        return curTime;
    }

    public void setCurTime(LocalDateTime curTime) {
        this.curTime = curTime;
    }
}
