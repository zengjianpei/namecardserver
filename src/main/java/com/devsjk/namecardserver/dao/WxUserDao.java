package com.devsjk.namecardserver.dao;

import com.devsjk.namecardserver.model.WxUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface WxUserDao {

	int insertWxUser(WxUser wxuser);

	int deleteWxUser(WxUser wxuser);

	WxUser findWxUser(WxUser wxuser);

	int updateWxUser(WxUser wxuser);

	List<WxUser> listWxUser(WxUser wxuser);

	Long listWxUserCount(WxUser wxuser);

	WxUser findWxUserByOpenId(String openid);


}