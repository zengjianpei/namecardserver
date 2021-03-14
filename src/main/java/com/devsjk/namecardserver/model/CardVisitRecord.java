package com.devsjk.namecardserver.model;

import com.leshang.framework3.model.BaseModel;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class CardVisitRecord extends BaseModel {
    private Integer id;

    private Long companyId;

    private Long companyUserId;

    private String visitorWxId;

    private Integer times;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastVisitTime;

    private Integer gotoTop;

    private String companyName;

    private String companyUserName;

    private String companyUserMobile;

    private String companyUserPosition;

    private String companyUserAvatar;

    private String visitorMobile;

    private String visitorAvatar;

    private String visitorNickname;

    public CardVisitRecord(){

    }

    public CardVisitRecord(Long companyId,Long companyUserId,String visitorWxId,LocalDateTime lastVisitTime,Integer times){
        this.companyId=companyId;
        this.companyUserId=companyUserId;
        this.visitorWxId=visitorWxId;
        this.lastVisitTime=lastVisitTime;
        this.times=times;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTimes() {
        return times;
    }

    public void setTimes(Integer times) {
        this.times = times;
    }

    public Long getCompanyUserId() {
        return companyUserId;
    }

    public void setCompanyUserId(Long companyUserId) {
        this.companyUserId = companyUserId;
    }

    public String getVisitorWxId() {
        return visitorWxId;
    }

    public void setVisitorWxId(String visitorWxId) {
        this.visitorWxId = visitorWxId;
    }

    public LocalDateTime getLastVisitTime() {
        return lastVisitTime;
    }

    public void setLastVisitTime(LocalDateTime lastVisitTime) {
        this.lastVisitTime = lastVisitTime;
    }

    public Integer getGotoTop() {
        return gotoTop;
    }

    public void setGotoTop(Integer gotoTop) {
        this.gotoTop = gotoTop;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyUserName() {
        return companyUserName;
    }

    public void setCompanyUserName(String companyUserName) {
        this.companyUserName = companyUserName;
    }

    public String getCompanyUserMobile() {
        return companyUserMobile;
    }

    public void setCompanyUserMobile(String companyUserMobile) {
        this.companyUserMobile = companyUserMobile;
    }

    public String getCompanyUserPosition() {
        return companyUserPosition;
    }

    public void setCompanyUserPosition(String companyUserPosition) {
        this.companyUserPosition = companyUserPosition;
    }

    public String getCompanyUserAvatar() {
        return companyUserAvatar;
    }

    public void setCompanyUserAvatar(String companyUserAvatar) {
        this.companyUserAvatar = companyUserAvatar;
    }

    public String getVisitorMobile() {
        return visitorMobile;
    }

    public void setVisitorMobile(String visitorMobile) {
        this.visitorMobile = visitorMobile;
    }

    public String getVisitorAvatar() {
        return visitorAvatar;
    }

    public void setVisitorAvatar(String visitorAvatar) {
        this.visitorAvatar = visitorAvatar;
    }

    public String getVisitorNickname() {
        return visitorNickname;
    }

    public void setVisitorNickname(String visitorNickname) {
        this.visitorNickname = visitorNickname;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
}