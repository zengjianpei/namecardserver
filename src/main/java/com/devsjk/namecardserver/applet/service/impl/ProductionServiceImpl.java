package com.devsjk.namecardserver.applet.service.impl;

import com.devsjk.namecardserver.applet.service.ProductionService;
import com.devsjk.namecardserver.dao.ProductionDao;
import com.devsjk.namecardserver.model.Production;
import com.leshang.framework3.Exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Auther: zjp
 * @Date: 2020/10/9 14:11
 * @Description:
 */
@Service("appletProductionService")
public class ProductionServiceImpl implements ProductionService {

    @Autowired
    private ProductionDao productionDao;

    @Override
    public List<Production> listByCompanyId(Production production) {
        return productionDao.listByCompanyId(production);
    }

    @Override
    public Long listCountByCompanyId(Production production) {
        return productionDao.listCountByCompanyId(production);
    }

    @Override
    @Transactional
    public void insertProduction(Production production) throws BusinessException {
        productionDao.insertProduction(production);
    }

    @Override
    public Production findProduction(Production production) {
        return productionDao.findProduction(production);
    }

    @Override
    @Transactional
    public void updateProduction(Production production) throws BusinessException {
        productionDao.updateProduction(production);
    }

    @Override
    @Transactional
    public void deleteProduction(Production production) {
        productionDao.deleteProductioin(production);
    }
}
