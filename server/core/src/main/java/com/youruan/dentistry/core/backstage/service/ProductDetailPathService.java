
package com.youruan.dentistry.core.backstage.service;

import com.youruan.dentistry.core.backstage.domain.ProductDetailPath;
import com.youruan.dentistry.core.backstage.query.ProductDetailPathQuery;
import com.youruan.dentistry.core.backstage.vo.ExtendedProductDetailPath;
import com.youruan.dentistry.core.base.query.Pagination;

import java.util.List;

public interface ProductDetailPathService {

    /**
     * 根据id，获取单条记录
     */
    public ProductDetailPath get(Long id);
    /**
     * 根据条件，获取单条记录
     */
    public ExtendedProductDetailPath queryOne(ProductDetailPathQuery qo);
    /**
     * 返回所有记录
     */
    public List<ExtendedProductDetailPath> listAll(ProductDetailPathQuery qo);
    /**
     * 根据条件，查询列表
     */
    public Pagination<ExtendedProductDetailPath> query(ProductDetailPathQuery qo);
    /**
     * 根据查询条件，返回记录条目
     */
    public int count(ProductDetailPathQuery qo);
    /**
     * 添加
     */
    void create(Long productId, List<String> detailPaths);
    /**
     * 修改
     */
    void update(ProductDetailPath productDetailPath, Long productId, List<String> detailPaths);
    /**
     * 根据id集合，查询对应列表
     */
    List<? extends ProductDetailPath> listAll(Long[] productDetailPathIds);
    /**
     * 返回所有记录
     */
    List<ExtendedProductDetailPath> listAll();
    /**
     * 根据产品id删除详情轮播图
     */
    void deleteByProductId(Long id);

}
