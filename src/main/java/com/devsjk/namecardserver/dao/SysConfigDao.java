package com.devsjk.namecardserver.dao;

import com.devsjk.namecardserver.model.SysConfig;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface SysConfigDao {

    SysConfig findByKey(String key);
}
