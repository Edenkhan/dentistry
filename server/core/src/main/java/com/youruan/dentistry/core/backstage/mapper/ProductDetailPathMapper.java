
package com.youruan.dentistry.core.backstage.mapper;

import com.youruan.dentistry.core.backstage.domain.ProductDetailPath;
import com.youruan.dentistry.core.backstage.query.ProductDetailPathQuery;
import com.youruan.dentistry.core.backstage.vo.ExtendedProductDetailPath;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ProductDetailPathMapper {

    /**
     * 获取单条记录
     */
    public ProductDetailPath get(Long id);
    /**
     * 修改
     */
    public int update(ProductDetailPath productDetailPath);
    /**
     * 添加
     */
    public int add(@Param("productDetailPathList") List<ProductDetailPath> productDetailPathList);
    /**
     * 删除
     */
    public int delete(Long id);
    /**
     * 根据查询条件，返回记录条目
     */
    public int count(ProductDetailPathQuery qo);
    /**
     * 根据查询条件，返回记录列表
     */
    public List<ExtendedProductDetailPath> query(ProductDetailPathQuery qo);
    /**
     * 根据产品id删除详情轮播图
     */
    void deleteByProductId(Long id);
}
