package com.devsjk.namecardserver.admin.service;

import com.devsjk.namecardserver.model.Industry;
import java.util.List;

public interface IndustryService {

	void insertIndustry(Industry industry);

	void deleteIndustry(Industry industry);

	Industry findIndustry(Industry industry);

	void updateIndustry(Industry industry);

	List<Industry> listIndustry(Industry industry);

	Long listIndustryCount(Industry industry);
}