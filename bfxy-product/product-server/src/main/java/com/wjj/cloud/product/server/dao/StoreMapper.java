package com.wjj.cloud.product.server.dao;


import com.wjj.cloud.product.server.entity.Store;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

public interface StoreMapper {
    int deleteByPrimaryKey(String storeId);

    int insert(Store record);

    int insertSelective(Store record);

    Store selectByPrimaryKey(String storeId);

    int updateByPrimaryKeySelective(Store record);

    int updateByPrimaryKey(Store record);

	int selectVersion(@Param("supplierId") String supplierId, @Param("goodsId") String goodsId);

	int updateStoreCountByVersion(@Param("version") int version, @Param("supplierId") String supplierId, @Param("goodsId") String goodsId, @Param("updateBy") String updateBy, @Param("updateTime") Date updateTime);

	int selectStoreCount(@Param("supplierId") String supplierId, @Param("goodsId") String goodsId);

}