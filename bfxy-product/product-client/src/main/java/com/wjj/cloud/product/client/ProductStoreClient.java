package com.wjj.cloud.product.client;


import com.wjj.cloud.product.common.ReduceStoreInput;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;


/**
 * @Author: wangjunjie 2019/3/12 11:19
 * @Description:
 * @Version: 1.0.0
 * @Modified By: xxx 2019/3/12 11:19
 */

@FeignClient(name = "product",fallback = ProductStoreClient.ProductClientFallBack.class)
public interface ProductStoreClient {

    @GetMapping("/product/store/count")
    int selectStoreCount(@RequestParam("supplierId") String supplierId,@RequestParam("goodsId") String goodsId);

    @PostMapping("/product/store/reduce")
    int reduceStoreCountByVersion(@RequestBody ReduceStoreInput reduceStoreInput);

    @GetMapping("/product/version")
    int selectVersion(@RequestParam("supplierId") String supplierId,@RequestParam("goodsId") String goodsId);

    @Component
    public static class ProductClientFallBack implements ProductStoreClient {

        @Override
        public int selectStoreCount(String supplierId, String goodsId) {
            System.out.println("Hystrix.....selectStoreCount");
            return 0;
        }

        @Override
        public int reduceStoreCountByVersion(ReduceStoreInput reduceStoreInput) {
            System.out.println("Hystrix.....reduceStoreCountByVersion");
            return 0;
        }

        @Override
        public int selectVersion(String supplierId, String goodsId) {
            System.out.println("Hystrix.....selectVersion......"+supplierId);
            return 0;
        }
    }
}
