package com.devsjk.namecardserver.admin.service.impl;

import com.devsjk.namecardserver.dao.IndustryDao;
import com.devsjk.namecardserver.model.Industry;
import com.devsjk.namecardserver.admin.service.IndustryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service("adminIndustryService")
public class IndustryServiceImpl implements IndustryService {

	@Autowired
	private IndustryDao industryDao;

	@Override
	public void insertIndustry(Industry industry) {
		if(null==industry.getStatus()){
			industry.setStatus(1);
		}
		industryDao.insertIndustry(industry);
	}

	@Override
	public void deleteIndustry(Industry industry) {
		industryDao.deleteIndustry(industry);
	}

	@Override
	public Industry findIndustry(Industry industry) {
		return industryDao.findIndustry(industry);
	}

	@Override
	public void updateIndustry(Industry industry) {
		industryDao.updateIndustry(industry);
	}

	@Override
	public List<Industry> listIndustry(Industry industry) {
		return industryDao.listIndustry(industry);
	}

	@Override
	public Long listIndustryCount(Industry industry) {
		return industryDao.listIndustryCount(industry);
	}
}