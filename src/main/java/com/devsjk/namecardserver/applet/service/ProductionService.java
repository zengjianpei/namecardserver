package com.devsjk.namecardserver.applet.service;

import com.devsjk.namecardserver.model.Production;
import com.leshang.framework3.Exception.BusinessException;

import java.util.List;

public interface ProductionService {

    List<Production> listByCompanyId(Production production);

    Long listCountByCompanyId(Production production);

    void insertProduction(Production production) throws BusinessException;

    Production findProduction(Production production);

    void updateProduction(Production production) throws BusinessException;

    void deleteProduction(Production production);
}
