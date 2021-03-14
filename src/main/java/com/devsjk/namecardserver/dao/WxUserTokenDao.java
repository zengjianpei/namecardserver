package com.devsjk.namecardserver.dao;

import com.devsjk.namecardserver.model.WxUser;
import com.devsjk.namecardserver.model.WxUserToken;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface WxUserTokenDao {

	int insertWxUserToken(WxUserToken wxUserToken);

	WxUserToken findWxUserTokenByAccessToken(String accessToken);

	int updateUserTokenVaild(WxUserToken wxUserToken);
}