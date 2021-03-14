package com.devsjk.namecardserver.dao;

import com.devsjk.namecardserver.model.Industry;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface IndustryDao {

	int insertIndustry(Industry industry);

	int deleteIndustry(Industry industry);

	Industry findIndustry(Industry industry);

	int updateIndustry(Industry industry);

	List<Industry> listIndustry(Industry industry);

	Long listIndustryCount(Industry industry);
}