package com.mall.business.application.product.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.NumberUtil;
import com.mall.common.response.ResponsePageResult;
import com.mall.business.domain.entity.product.Product;
import com.mall.business.domain.entity.product.ProductList;
import com.mall.business.domain.entity.product.ProductPic;
import com.mall.business.infrastructure.repository.product.ProductPicRepo;
import com.mall.business.infrastructure.repository.product.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * ProductService
 *
 * @author youfu.wang
 * @date 2021-08-19
 */
@Service
public class ProductService {
    @Value("${spring.servlet.imageio.endpoint-url}")
    private String endpointUrl;
    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private ProductPicRepo productPicRepo;
    @Autowired
    private SkuService skuService;

    /**
     * 查询商品列表
     *
     * @param parameterObject
     * @return
     */
    public ResponsePageResult getProductList(Map<String, Object> parameterObject) {
        if (StringUtils.isEmpty(parameterObject.get("status"))) {
            parameterObject.put("status", Product.PRODUCT_STATUS_SHELF);
        }
        List<ProductList> productListList = productRepo.getProductList(parameterObject);
        return ResponsePageResult.ok().setData(productListList);
    }

    /**
     * 得到商品信息
     *
     * @param parameterObject
     * @return
     */
    public Product getProductInfo(Map<String, Object> parameterObject) {
        return productRepo.getProductInfo(parameterObject);
    }

    /**
     * 得到商品主图
     *
     * @param parameterObject
     * @return
     */
    public List<ProductPic> getProductMainPicList(Map<String, Object> parameterObject) {
        parameterObject.put("endpointUrl", this.endpointUrl);
        return productPicRepo.getProductMainPicList(parameterObject);
    }

    /**
     * 新增商品信息
     *
     * @param productMap
     * @param skuList
     */
    @Transactional
    public void addProductInfo(Map<String, Object> productMap, List<Map<String, Object>> skuList) {
        Product product = new Product();
        product.setSeqId(String.valueOf(productMap.get("seqId")));
        product.setCategoryCode(String.valueOf(productMap.get("categoryCode")));
        product.setProductCode(String.valueOf(productMap.get("productCode")));
        product.setTitle(String.valueOf(productMap.get("title")));
        product.setStatus(String.valueOf(productMap.get("status")));
        product.setIsValid(1);
        product.setCreateTime(DateUtil.date());
        //新增商品信息
        this.productRepo.addProductInfo(product);
        //新增商品SKU
        skuService.addProductSku(String.valueOf(productMap.get("seqId")), skuList);
        //新增商品列表信息
        ProductList productList = new ProductList();
        productList.setSeqId(IdUtil.randomUUID());
        productList.setProductSeqId(product.getSeqId());
        productList.setCreateTime(DateUtil.date());
        this.productRepo.addProductList(productList);
    }

    /**
     * 更新商品信息
     *
     * @param productMap
     * @param skuList
     */
    @Transactional
    public void updateProductInfo(Map<String, Object> productMap, List<Map<String, Object>> skuList) {
        //更新商品信息
        Product product = new Product();
        product.setSeqId(String.valueOf(productMap.get("seqId")));
        product.setCategoryCode(String.valueOf(productMap.get("categoryCode")));
        product.setTitle(String.valueOf(productMap.get("title")));
        product.setStatus(String.valueOf(productMap.get("status")));
        product.setUpdateTime(DateUtil.date());
        this.productRepo.updateProductInfo(product);
        //SKU库存数量
        int inventoryAmount = 0;
        //SKU所有价格的集合
        List<Double> skuPriceList = new ArrayList<Double>();
        if (skuList != null && skuList.size() > 0) {
            //删除商品SKU
            skuService.deleteProductSku(String.valueOf(productMap.get("seqId")));
            //新增商品SKU
            skuService.addProductSku(String.valueOf(productMap.get("seqId")), skuList);
            for (Map<String, Object> skuMap : skuList) {
                inventoryAmount += Integer.parseInt(String.valueOf(skuMap.get("inventoryAmount")));
                if (!StringUtils.isEmpty(String.valueOf(skuMap.get("sellPrice")))) {
                    skuPriceList.add(Double.parseDouble(String.valueOf(skuMap.get("sellPrice"))));
                }
            }
        }
        //更新商品列表信息
        ProductList productList = new ProductList();
        productList.setProductSeqId(product.getSeqId());
        productList.setInventory(inventoryAmount);
        if (skuPriceList.size() > 0) {
            Double min = Collections.min(skuPriceList);
            Double max = Collections.max(skuPriceList);
            productList.setPriceScope("¥" + NumberUtil.roundStr(min, 2) + "-" + NumberUtil.roundStr(max, 2));
        } else {
            productList.setPriceScope("¥" + NumberUtil.roundStr(0, 2));
        }
        productList.setUpdateTime(DateUtil.date());
        this.productRepo.updateProductList(productList);
    }

    /**
     * 下架一个商品
     *
     * @param productMap
     */
    @Transactional
    public void offShelfOne(Map<String, Object> productMap) {
        Product product = new Product();
        product.setSeqId(String.valueOf(productMap.get("seqId")));
        product.setStatus(Product.PRODUCT_STATUS_OFF_SHELF);
        product.setUpdateTime(DateUtil.date());
        this.productRepo.updateProductInfo(product);
    }

    /**
     * 上架一个商品
     *
     * @param productMap
     */
    @Transactional
    public void shelfOne(Map<String, Object> productMap) {
        Product product = new Product();
        product.setSeqId(String.valueOf(productMap.get("seqId")));
        product.setStatus(Product.PRODUCT_STATUS_SHELF);
        product.setUpdateTime(DateUtil.date());
        this.productRepo.updateProductInfo(product);
    }

    /**
     * 删除一个商品列表
     *
     * @param productMap
     */
    @Transactional
    public void deleteOneProductList(Map<String, Object> productMap) {
        Product product = new Product();
        product.setSeqId(String.valueOf(productMap.get("productSeqId")));
        product.setStatus(Product.PRODUCT_STATUS_DELETED);
        product.setUpdateTime(DateUtil.date());
        this.productRepo.updateProductInfo(product);
    }

}
