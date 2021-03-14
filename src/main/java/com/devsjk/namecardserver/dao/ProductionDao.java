package com.devsjk.namecardserver.dao;

import com.devsjk.namecardserver.model.Production;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ProductionDao {

    List<Production> listProductionByCategoryId(Production production);

    List<Production> listByCompanyId(Production production);

    Long listCountByCompanyId(Production production);

    int insertProduction(Production production);

    Production findProduction(Production production);

    int updateProduction(Production production);

    int deleteProductioin(Production production);

    List<Production> listOnsaleProduction(Long companyId);
}
