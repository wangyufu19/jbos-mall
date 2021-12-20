package com.mall.product.application.service;
import com.mall.common.utils.DateUtils;
import com.mall.common.utils.StringUtils;
import com.mall.product.domain.entity.Product;
import com.mall.product.domain.entity.ProductList;
import com.mall.product.domain.entity.ProductPic;
import com.mall.product.infrastructure.repository.ProductPicRepo;
import com.mall.product.infrastructure.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * ProductService
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
     * @param parameterObject
     * @return
     */
    public List<ProductList> getProductList(Map<String,Object> parameterObject){
        if(StringUtils.isNUll(parameterObject.get("status"))){
            parameterObject.put("status",Product.PRODUCT_STATUS_SHELF);
        }
        return productRepo.getProductList(parameterObject);
    }

    /**
     * 得到商品信息
     * @param parameterObject
     * @return
     */
    public Product getProductInfo(Map<String,Object> parameterObject){
        return productRepo.getProductInfo(parameterObject);
    }

    /**
     * 得到商品主图
     * @param parameterObject
     * @return
     */
    public List<ProductPic> getProductMainPicList(Map<String,Object> parameterObject){
        parameterObject.put("endpointUrl",this.endpointUrl);
        return  productPicRepo.getProductMainPicList(parameterObject);
    }
    /**
     * 新增商品信息
     * @param productMap
     * @param skuList
     */
    @Transactional
    public void addProductInfo(Map<String,Object> productMap,List<Map<String,Object>> skuList){
        Product product=new Product();
        product.setSeqId(StringUtils.replaceNull(productMap.get("seqId")));
        product.setCategoryCode(StringUtils.replaceNull(productMap.get("categoryCode")));
        product.setProductCode(StringUtils.replaceNull(productMap.get("productCode")));
        product.setTitle(StringUtils.replaceNull(productMap.get("title")));
        product.setStatus(StringUtils.replaceNull(productMap.get("status")));
        product.setIsValid(1);
        product.setCreateTime(DateUtils.getCurrentDate());
        //新增商品信息
        this.productRepo.addProductInfo(product);
        //新增商品SKU
        skuService.addProductSku(StringUtils.replaceNull(productMap.get("seqId")),skuList);
        //新增商品列表信息
        ProductList productList=new ProductList();
        productList.setSeqId(StringUtils.getUUID());
        productList.setProductSeqId(product.getSeqId());
        productList.setCreateTime(DateUtils.getCurrentDate());
        this.productRepo.addProductList(productList);
    }

    /**
     * 更新商品信息
     * @param productMap
     * @param skuList
     */
    @Transactional
    public void updateProductInfo(Map<String,Object> productMap,List<Map<String,Object>> skuList){
        //更新商品信息
        Product product=new Product();
        product.setSeqId(StringUtils.replaceNull(productMap.get("seqId")));
        product.setCategoryCode(StringUtils.replaceNull(productMap.get("categoryCode")));
        product.setTitle(StringUtils.replaceNull(productMap.get("title")));
        product.setStatus(StringUtils.replaceNull(productMap.get("status")));
        product.setUpdateTime(DateUtils.getCurrentDate());
        this.productRepo.updateProductInfo(product);
        if(skuList!=null&&skuList.size()>0){
            //删除商品SKU
            skuService.deleteProductSku(StringUtils.replaceNull(productMap.get("seqId")));
            //新增商品SKU
            skuService.addProductSku(StringUtils.replaceNull(productMap.get("seqId")),skuList);
        }
        //更新商品列表信息
        ProductList productList=new ProductList();
        productList.setProductSeqId(product.getSeqId());
        productList.setUpdateTime(DateUtils.getCurrentDate());
        this.productRepo.updateProductList(productList);
    }

    /**
     * 下架一个商品
     * @param productMap
     */
    @Transactional
    public void offShelfOne(Map<String,Object> productMap){
        Product product=new Product();
        product.setSeqId(StringUtils.replaceNull(productMap.get("seqId")));
        product.setStatus(Product.PRODUCT_STATUS_OFF_SHELF);
        product.setUpdateTime(DateUtils.getCurrentDate());
        this.productRepo.updateProductInfo(product);
    }
    /**
     * 上架一个商品
     * @param productMap
     */
    @Transactional
    public void shelfOne(Map<String,Object> productMap){
        Product product=new Product();
        product.setSeqId(StringUtils.replaceNull(productMap.get("seqId")));
        product.setStatus(Product.PRODUCT_STATUS_SHELF);
        product.setUpdateTime(DateUtils.getCurrentDate());
        this.productRepo.updateProductInfo(product);
    }
}
