package com.devsjk.namecardserver.applet.service.impl;

import com.devsjk.namecardserver.applet.service.IndustryService;
import com.devsjk.namecardserver.dao.IndustryDao;
import com.devsjk.namecardserver.model.Industry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: zjp
 * @Date: 2020/9/5 16:31
 * @Description:
 */
@Service("appletIndustryService")
public class IndustryServiceImpl implements IndustryService {

    @Autowired
    private IndustryDao industryDao;

    @Override
    public List<Industry> selectOptions(Industry industry) {
        return industryDao.listIndustry(industry);
    }

}
