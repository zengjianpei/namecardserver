package com.devsjk.namecardserver.dao;

import com.devsjk.namecardserver.model.CardVisitRecord;
import com.devsjk.namecardserver.model.CompanyUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CompanyUserDao {

    int insertCompanyUser(CompanyUser companyUser);

    CompanyUser findCompanyUser(CompanyUser companyUser);

    CompanyUser findCompanyUserByWxUserId(CompanyUser companyUser);

    int updateCompanyUser(CompanyUser companyUser);

    List<CardVisitRecord> getVisitors(CompanyUser companyUser);

    Long getCountVisitors(CompanyUser companyUser);

    List<CardVisitRecord> getCardPocket(CompanyUser companyUser);

    Long getCountCardPocket(CompanyUser companyUser);

    List<CompanyUser> listStaffManager(CompanyUser companyUser);

    Long listCountStaffManager(CompanyUser companyUser);

    Integer totalVisitors(CompanyUser companyUser);

    Integer visitTotalCount(CompanyUser companyUser);

    Integer todayVisitCount(CompanyUser companyUser);

    CardVisitRecord findCardVisitRecord(CardVisitRecord cardVisitRecord);

    int insertCardVisitRecord(CardVisitRecord cardVisitRecord);

    int updateCardVisitRecordTimes(CardVisitRecord cardVisitRecord);
}
