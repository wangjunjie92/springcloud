package com.wjj.cloud.product.server.service;

import java.util.Date;

public interface StoreService {

    int selectVersion(String supplierId, String goodsId);

    int selectStoreCount(String supplierId, String goodsId);

    int updateStoreCountByVersion(int version, String supplierId, String goodsId, String updateBy,
                                  Date updateTime);
}
