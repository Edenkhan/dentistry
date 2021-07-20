
package com.youruan.dentistry.core.backstage.service;

import com.youruan.dentistry.core.backstage.domain.Shop;
import com.youruan.dentistry.core.backstage.query.ShopQuery;
import com.youruan.dentistry.core.backstage.vo.ExtendedShop;
import com.youruan.dentistry.core.base.query.Pagination;

import java.util.List;

public interface ShopService {

    /**
     * 根据id，获取单条记录
     */
    public Shop get(Long id);
    /**
     * 根据条件，获取单条记录
     */
    public ExtendedShop queryOne(ShopQuery qo);
    /**
     * 返回所有记录
     */
    public List<ExtendedShop> listAll(ShopQuery qo);
    /**
     * 根据条件，查询列表
     */
    public Pagination<ExtendedShop> query(ShopQuery qo);
    /**
     * 根据查询条件，返回记录条目
     */
    public int count(ShopQuery qo);
    /**
     * 添加
     */
    Shop create(String name, String address, String phone, Boolean enabled);
    /**
     * 修改
     */
    void update(Shop shop, String name, String address, String phone, Boolean enabled);
    /**
     * 根据id集合，查询对应列表
     */
    List<? extends Shop> listAll(Long[] shopIds);
    /**
     * 返回所有记录
     */
    List<ExtendedShop> listAll();

    /**
     * 修改可预约次数
     */
    void updateValidNum(Shop shop, Integer validNum);

    /**
     * 增加门店预约次数
     */
    void increAppointNum(Shop shop);

}
