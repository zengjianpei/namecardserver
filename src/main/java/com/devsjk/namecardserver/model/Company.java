package com.devsjk.namecardserver.model;

import com.leshang.framework3.model.BaseModel;
import java.time.LocalDateTime;
import java.util.List;

public class Company extends BaseModel {
    private Long id;

    private String name;

    private String provinceCode;

    private String cityCode;

    private String areaCode;

    private String address;

    private Long adminId;

    private Long industryId;

    private String industryDetailName;

    private Integer status;

    private LocalDateTime signUpTime;

    private LocalDateTime tryDeadline;

    private LocalDateTime deadline;

    private Integer peopleNum;

    private Integer comeInNum;

    private String invCode;

    private CompanyUser adminCompanyUser;

    private List<CompanyUser> companyUserList;

    private String provinceName;

    private String cityName;

    private String areaName;

    private String wholeAddress;

    private Integer remainDays;

    public Company(){

    }

    public Company(Long id){
        this.id=id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    public Long getIndustryId() {
        return industryId;
    }

    public void setIndustryId(Long industryId) {
        this.industryId = industryId;
    }

    public String getIndustryDetailName() {
        return industryDetailName;
    }

    public void setIndustryDetailName(String industryDetailName) {
        this.industryDetailName = industryDetailName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public LocalDateTime getTryDeadline() {
        return tryDeadline;
    }

    public void setTryDeadline(LocalDateTime tryDeadline) {
        this.tryDeadline = tryDeadline;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public CompanyUser getAdminCompanyUser() {
        return adminCompanyUser;
    }

    public void setAdminCompanyUser(CompanyUser adminCompanyUser) {
        this.adminCompanyUser = adminCompanyUser;
    }

    public List<CompanyUser> getCompanyUserList() {
        return companyUserList;
    }

    public void setCompanyUserList(List<CompanyUser> companyUserList) {
        this.companyUserList = companyUserList;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getWholeAddress() {
        return wholeAddress;
    }

    public void setWholeAddress(String wholeAddress) {
        this.wholeAddress = wholeAddress;
    }

    public LocalDateTime getSignUpTime() {
        return signUpTime;
    }

    public void setSignUpTime(LocalDateTime signUpTime) {
        this.signUpTime = signUpTime;
    }

    public Integer getPeopleNum() {
        return peopleNum;
    }

    public void setPeopleNum(Integer peopleNum) {
        this.peopleNum = peopleNum;
    }

    public String getInvCode() {
        return invCode;
    }

    public void setInvCode(String invCode) {
        this.invCode = invCode;
    }

    public Integer getComeInNum() {
        return comeInNum;
    }

    public void setComeInNum(Integer comeInNum) {
        this.comeInNum = comeInNum;
    }

    public Integer getRemainDays() {
        return remainDays;
    }

    public void setRemainDays(Integer remainDays) {
        this.remainDays = remainDays;
    }
}