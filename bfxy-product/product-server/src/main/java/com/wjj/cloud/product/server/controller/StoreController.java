package com.wjj.cloud.product.server.controller;



import com.wjj.cloud.product.common.ReduceStoreInput;
import com.wjj.cloud.product.server.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: wangjunjie 2019/3/12 11:13
 * @Description:
 * @Version: 1.0.0
 * @Modified By: xxx 2019/3/12 11:13
 */

@RestController
@RequestMapping("/product")
public class StoreController {

    @Autowired
    private StoreService storeService;

    @GetMapping("/store/count")
    public int selectStoreCount(String supplierId, String goodsId) {
        return storeService.selectStoreCount(supplierId, goodsId);
    }

    @PostMapping("/store/reduce")
    public int reduceStoreCountByVersion(@RequestBody ReduceStoreInput reduceStoreInput) {
        return storeService.updateStoreCountByVersion(reduceStoreInput.getVersion(),
                reduceStoreInput.getSupplierId(),reduceStoreInput.getGoodsId(),
                reduceStoreInput.getUpdateBy(),reduceStoreInput.getUpdateTime());
    }



    @GetMapping("/version")
    public int selectVersion(String supplierId, String goodsId) throws InterruptedException {
        return storeService.selectVersion(supplierId, "1001");
    }

    /*public int testHystrix1Timeout(String supplierId, String goodsId) {
        System.out.println("hystrix..."+supplierId + "goodsId.."+goodsId+"太拥挤了, 请稍后再试~~");
        return 0;
    }*/


}
