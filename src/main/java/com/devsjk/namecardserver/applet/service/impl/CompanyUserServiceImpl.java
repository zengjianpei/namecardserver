package com.devsjk.namecardserver.applet.service.impl;

import com.devsjk.namecardserver.applet.service.CompanyUserService;
import com.devsjk.namecardserver.aspect.UserTokenVerify;
import com.devsjk.namecardserver.bean.WxBean;
import com.devsjk.namecardserver.dao.CompanyDao;
import com.devsjk.namecardserver.dao.CompanyUserDao;
import com.devsjk.namecardserver.dao.ProductionDao;
import com.devsjk.namecardserver.dao.WxUserDao;
import com.devsjk.namecardserver.model.*;
import com.devsjk.namecardserver.utils.CustomQrCodeUtils;
import com.devsjk.namecardserver.utils.tencentUtils.OssUtils;
import com.devsjk.namecardserver.utils.wxUtils.WxConfig;
import com.devsjk.namecardserver.utils.wxUtils.WxRequestUtil;
import com.devsjk.namecardserver.utils.wxUtils.WxUtils;
import com.leshang.framework3.Exception.BusinessException;
import com.leshang.framework3.constant.StatusConstant;
import com.leshang.framework3.model.StatusPair;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: zjp
 * @Date: 2020/9/29 17:12
 * @Description:
 */
@Service("appletCompanyUserService")
public class CompanyUserServiceImpl implements CompanyUserService {

    @Autowired
    private CompanyUserDao companyUserDao;

    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private WxBean wxBean;

    @Autowired
    private OssUtils ossUtils;

    @Autowired
    private ProductionDao productionDao;

    @Autowired
    private WxUserDao wxUserDao;

    @Override
    public CompanyUser findCompanyUser(CompanyUser companyUser) {
        companyUser.setWxUserId(Long.valueOf(companyUser.getVerifyUserId()));
        CompanyUser cu=companyUserDao.findCompanyUserByWxUserId(companyUser);
        if(null!=cu){
            Company company=companyDao.findCompany(new Company(cu.getCompanyId()));
            Duration duration = Duration.between(LocalDateTime.now(),company.getDeadline());
            long days = duration.toDays(); //相差的天数
            company.setRemainDays(days<=0?0:(int)days);
            cu.setCompany(company);
        }
        return cu;
    }

    @Override
    public CompanyUser findCompanyUserById(CompanyUser companyUser) {
        CompanyUser cu=companyUserDao.findCompanyUser(companyUser);
        if(null!=cu){
            Company company=companyDao.findCompany(new Company(cu.getCompanyId()));
            Duration duration = Duration.between(LocalDateTime.now(),company.getDeadline());
            long days = duration.toDays(); //相差的天数
            company.setRemainDays(days<=0?0:(int)days);
            cu.setCompany(company);
        }
        return cu;
    }

    @Override
    public CompanyUser findCompanyUserAllById(CompanyUser companyUser) {
        CompanyUser cu=companyUserDao.findCompanyUser(companyUser);
        if(null!=cu){
            Company company=companyDao.findCompany(new Company(cu.getCompanyId()));
            cu.setCompany(company);
            if(null!=company){
                List<Production> productionList=productionDao.listOnsaleProduction(company.getId());
                cu.setProductionList(productionList);
            }
            String loginStr= WxRequestUtil.makeGetRequest(MessageFormat.format(WxConfig.jscode2session,wxBean.getAppId(),wxBean.getAppSecret(),companyUser.getCode()));
            JSONObject loginJson=new JSONObject(loginStr);
            //WxUser wxUser=new WxUser(loginJson.getString("openid"),loginJson.getString("session_key"),loginJson.has("unionid")?loginJson.getString("unionid"):"");
            WxUser w=wxUserDao.findWxUserByOpenId(loginJson.getString("openid"));
            Long wxId=null;
            if(null==w){
                cu.setVisitorRegister(0);
                WxUser wxUser=new WxUser(loginJson.getString("openid"),loginJson.getString("session_key"),loginJson.has("unionid")?loginJson.getString("unionid"):"",0);
                wxUserDao.insertWxUser(wxUser);
                wxId=wxUser.getId();
            }else{
                CompanyUser c1=new CompanyUser();
                c1.setWxUserId(w.getId());
                CompanyUser c=companyUserDao.findCompanyUserByWxUserId(c1);
                if(null==c){
                    cu.setVisitorRegister(0);
                }else{
                    cu.setVisitorRegister(1);
                }
                wxId=w.getId();
            }
            //记录访问记录
            if(wxId.longValue()!=cu.getWxUserId()){
                CardVisitRecord c=new CardVisitRecord(cu.getCompanyId(),cu.getId(),String.valueOf(wxId),LocalDateTime.now(),0);
                CardVisitRecord cardVisitRecord=companyUserDao.findCardVisitRecord(c);
                if(null==cardVisitRecord){
                    companyUserDao.insertCardVisitRecord(c);
                }else{
                    companyUserDao.updateCardVisitRecordTimes(c);
                }
            }

        }
        return cu;
    }

    @Override
    @Transactional
    public void updateCompanyUser(CompanyUser companyUser) throws BusinessException {
        companyUserDao.updateCompanyUser(companyUser);
        companyDao.updateCompany(companyUser.getCompany());
    }

    @Override
    public CompanyUser getQcode(CompanyUser companyUser) throws BusinessException, IOException {
        CompanyUser cu=companyUserDao.findCompanyUser(companyUser);
        if(null!=cu){
            if(StringUtils.isBlank(cu.getQcode())){
                String ac= WxUtils.getServerAccessToken(wxBean);
                byte[] result=WxUtils.getAppletQcodebyte(ac,"pages/cardDetail/cardDetail?id="+companyUser.getId());
                String qCodeUrl1=ossUtils.uploadFile(new ByteArrayInputStream(result));
                BufferedImage b=CustomQrCodeUtils.qrCode(qCodeUrl1,cu.getAvatarUrl());
                String qCodeUrl=ossUtils.uploadFile(bufferedImageToInputStream(b));
                cu.setQcode(qCodeUrl);
                companyUserDao.updateCompanyUser(cu);
            }
            Company company=companyDao.findCompany(new Company(cu.getCompanyId()));
            cu.setCompany(company);
        }
        return cu;
    }

    @Override
    public List<CardVisitRecord> getVisitors(CompanyUser companyUser) {
        return companyUserDao.getVisitors(companyUser);
    }

    @Override
    public Long getCountVisitors(CompanyUser companyUser) {
        return companyUserDao.getCountVisitors(companyUser);
    }

    @Override
    public List<CardVisitRecord> getCardPocket(CompanyUser companyUser) {
        companyUser.setWxUserId(Long.valueOf(companyUser.getVerifyUserId()));
        return companyUserDao.getCardPocket(companyUser);
    }

    @Override
    public Long getCountCardPocket(CompanyUser companyUser) {
        companyUser.setWxUserId(Long.valueOf(companyUser.getVerifyUserId()));
        return companyUserDao.getCountCardPocket(companyUser);
    }

    @Override
    public void inviteStaff(CompanyUser companyUser) throws BusinessException {
        companyUser.setWxUserId(Long.valueOf(companyUser.getVerifyUserId()));
        CompanyUser cu=companyUserDao.findCompanyUserByWxUserId(companyUser);
        if(null!=cu){
            throw new BusinessException(new StatusPair(11100,"你已有归属企业，不可再加入"));
        }
        companyUser.setCompanyOwner(0);
        companyUserDao.insertCompanyUser(companyUser);
    }

    @Override
    public List<CompanyUser> listStaffManager(CompanyUser companyUser) {
        CompanyUser cu=companyUserDao.findCompanyUserByWxUserId(new CompanyUser(Long.valueOf(companyUser.getVerifyUserId())));
        List<CompanyUser> staffList=companyUserDao.listStaffManager(cu);
        for(CompanyUser c:staffList){
            c.setPageview(0);
            c.setVisitors(0);
        }
        return staffList;
    }

    @Override
    public Long listCountStaffManager(CompanyUser companyUser) {
        CompanyUser cu=companyUserDao.findCompanyUserByWxUserId(new CompanyUser(Long.valueOf(companyUser.getVerifyUserId())));
        return companyUserDao.listCountStaffManager(cu);
    }

    @Override
    public Map<String, Integer> getVisitorsStatistics(CompanyUser companyUser) {
        Map<String,Integer> result=new HashMap<String,Integer>();
        Integer totalVisitors=companyUserDao.totalVisitors(companyUser);
        Integer visitTotalCount=companyUserDao.visitTotalCount(companyUser);
        Integer todayVisitCount=companyUserDao.todayVisitCount(companyUser);
        result.put("totalVisitors",totalVisitors);
        result.put("visitTotalCount",visitTotalCount);
        result.put("todayVisitCount",todayVisitCount);
        return result;
    }

    /**
     * 将BufferedImage转换为InputStream
     * @param image
     * @return
     */
    private InputStream bufferedImageToInputStream(BufferedImage image){
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "png", os);
            InputStream input = new ByteArrayInputStream(os.toByteArray());
            return input;
        } catch (IOException e) {
            //logger.error("提示:",e);
        }
        return null;
    }
}
