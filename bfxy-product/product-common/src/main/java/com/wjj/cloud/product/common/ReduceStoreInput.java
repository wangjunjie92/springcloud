package com.wjj.cloud.product.common;

import lombok.Data;

import java.util.Date;

/**
 * 减库存入参
 * Created by 廖师兄
 * 2018-01-20 22:50
 */
@Data
public class ReduceStoreInput {

    private int version;

    private String supplierId;

    private String goodsId;

    private String updateBy;

    private Date updateTime;

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}