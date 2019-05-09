package com.newbie.dao;

import com.newbie.domain.UserStock;
import com.newbie.domain.UserStockExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserStockMapper {
    long countByExample(UserStockExample example);

    int deleteByExample(UserStockExample example);

    int deleteByPrimaryKey(String stockId);

    int insert(UserStock record);

    int insertSelective(UserStock record);

    List<UserStock> selectByExample(UserStockExample example);

    UserStock selectByPrimaryKey(String stockId);

    int updateByExampleSelective(@Param("record") UserStock record, @Param("example") UserStockExample example);

    int updateByExample(@Param("record") UserStock record, @Param("example") UserStockExample example);

    int updateByPrimaryKeySelective(UserStock record);

    int updateByPrimaryKey(UserStock record);
}