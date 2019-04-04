package com.wjj.cloud.product.server.service.impl;

import com.wjj.cloud.product.server.dao.StoreMapper;
import com.wjj.cloud.product.server.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Author: wangjunjie 2019/3/12 11:10
 * @Description:
 * @Version: 1.0.0
 * @Modified By: xxx 2019/3/12 11:10
 */

@Service
public class StoreServiceImpl implements StoreService {

    @Autowired
    private StoreMapper storeMapper;

    @Override
    public int selectVersion(String supplierId, String goodsId) {
        return storeMapper.selectVersion(supplierId, goodsId);
    }

    @Override
    public int updateStoreCountByVersion(int version, String supplierId, String goodsId, String updateBy,
                                         Date updateTime) {
        return storeMapper.updateStoreCountByVersion(version, supplierId, goodsId, updateBy, updateTime);
    }

    @Override
    public int selectStoreCount(String supplierId, String goodsId) {
        return storeMapper.selectStoreCount(supplierId, goodsId);
    }
}
