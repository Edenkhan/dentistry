
package com.youruan.dentistry.core.backstage.mapper;

import com.youruan.dentistry.core.backstage.domain.Shop;
import com.youruan.dentistry.core.backstage.query.ShopQuery;
import com.youruan.dentistry.core.backstage.vo.ExtendedShop;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ShopMapper {

    /**
     * 获取单条记录
     */
    public Shop get(Long id);
    /**
     * 修改
     */
    public int update(Shop shop);
    /**
     * 添加
     */
    public int add(Shop shop);
    /**
     * 删除
     */
    public int delete(Long id);
    /**
     * 根据查询条件，返回记录条目
     */
    public int count(ShopQuery qo);
    /**
     * 根据查询条件，返回记录列表
     */
    public List<ExtendedShop> query(ShopQuery qo);
}
