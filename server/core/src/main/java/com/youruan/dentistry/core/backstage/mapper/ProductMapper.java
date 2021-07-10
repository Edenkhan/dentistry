
package com.youruan.dentistry.core.backstage.mapper;

import com.youruan.dentistry.core.backstage.domain.Product;
import com.youruan.dentistry.core.backstage.query.ProductQuery;
import com.youruan.dentistry.core.backstage.vo.ExtendedProduct;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ProductMapper {

    /**
     * 根据id，获取单条记录
     */
    public Product get(Long id);
    /**
     * 修改
     */
    public int update(Product product);
    /**
     * 添加
     */
    public int add(Product product);
    /**
     * 删除
     */
    public int delete(Long id);
    /**
     * 根据查询条件，返回记录条目
     */
    public int count(ProductQuery qo);
    /**
     * 根据查询条件，返回记录列表
     */
    public List<ExtendedProduct> query(ProductQuery qo);
}
