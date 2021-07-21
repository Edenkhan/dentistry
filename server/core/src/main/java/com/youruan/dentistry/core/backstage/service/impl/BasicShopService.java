
package com.youruan.dentistry.core.backstage.service.impl;

import com.youruan.dentistry.core.backstage.domain.Shop;
import com.youruan.dentistry.core.backstage.mapper.ShopMapper;
import com.youruan.dentistry.core.backstage.query.ShopQuery;
import com.youruan.dentistry.core.backstage.service.AppointManageService;
import com.youruan.dentistry.core.backstage.service.ShopService;
import com.youruan.dentistry.core.backstage.vo.ExtendedShop;
import com.youruan.dentistry.core.base.exception.OptimismLockingException;
import com.youruan.dentistry.core.base.query.Pagination;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BasicShopService
        implements ShopService {

    private final ShopMapper shopMapper;
    private final AppointManageService appointManageService;

    public BasicShopService(ShopMapper shopMapper, @Lazy AppointManageService appointManageService) {
        this.shopMapper = shopMapper;
        this.appointManageService = appointManageService;
    }

    @Override
    public Shop get(Long id) {
        return shopMapper.get(id);
    }

    protected void update(Shop shop) {
        int affected = shopMapper.update(shop);
        if (affected == 0) {
            throw new OptimismLockingException("version!!");
        }
        shop.setVersion((shop.getVersion() + 1));
    }

    protected Shop add(Shop shop) {
        shop.setCreatedDate(new Date());
        shopMapper.add(shop);
        return shop;
    }

    @Override
    public List<ExtendedShop> listAll(ShopQuery qo) {
        qo.setMaxPageSize();
        return shopMapper.query(qo);
    }

    @Override
    public ExtendedShop queryOne(ShopQuery qo) {
        qo.setPageSize(1);
        List<ExtendedShop> datas = shopMapper.query(qo);
        return (((datas == null) || datas.isEmpty()) ? null : datas.get(0));
    }

    @Override
    public Pagination<ExtendedShop> query(ShopQuery qo) {
        int rows = shopMapper.count(qo);
        List<ExtendedShop> datas = ((rows == 0) ? new ArrayList<>() : shopMapper.query(qo));
        return new Pagination<>(rows, datas);
    }

    @Override
    public int count(ShopQuery qo) {
        return shopMapper.count(qo);
    }

    @Override
    @Transactional
    public Shop create(String name, String address, String phone, Boolean enabled) {
        this.checkAdd(name,address,phone,enabled);
        Shop shop = new Shop();
        this.assign(shop, name,address,phone,enabled);
        shop = this.add(shop);
        // 為該門店生成7天預約管理數據
        appointManageService.generate7Days(shop.getId());
        return shop;
    }

    @Override
    public void update(Shop shop, String name, String address, String phone, Boolean enabled) {
        this.checkUpdate(shop, name,address,phone,enabled);
        this.assign(shop, name,address,phone,enabled);
        update(shop);
    }

    

    /**
     * 添加门店校验
     */
    private void checkAdd(String name, String address, String phone, Boolean enabled) {
        this.checkParam(name,address,phone,enabled);
        ShopQuery qo = new ShopQuery();
        qo.setName(name);
        int count = shopMapper.count(qo);
        Assert.isTrue(count == 0,"门店名称重复");
    }

    /**
     * 修改门店校验
     */
    private void checkUpdate(Shop shop, String name, String address, String phone, Boolean enabled) {
        Assert.notNull(shop,"必须提供门店");
        this.checkParam(name,address,phone,enabled);
        ShopQuery qo = new ShopQuery();
        qo.setName(name);
        int count = shopMapper.count(qo);
        Assert.isTrue(shop.getName().equalsIgnoreCase(name)||count == 0,"门店名称重复");
    }

    private void checkParam(String name, String address, String phone, Boolean enabled) {
        Assert.notNull(name, "必须提供门店名称");
        Assert.notNull(address, "必须提供门店地址");
        Assert.notNull(phone, "必须提供门店电话");
        Assert.notNull(enabled, "必须提供门店状态");
    }
    

    /**
     * 封装数据
     */
    private void assign(Shop shop, String name, String address, String phone, Boolean enabled) {
        shop.setName(name);
        shop.setAddress(address);
        shop.setPhone(phone);
        shop.setTotalNum(0);
        shop.setAppointNum(0);
        shop.setEnabled(enabled);
    }

    @Override
    public List<? extends Shop> listAll(Long[] shopIds) {
        ShopQuery qo = new ShopQuery();
        qo.setIds(shopIds);
        return listAll(qo);
    }

    @Override
    public List<ExtendedShop> listAll() {
        ShopQuery qo = new ShopQuery();
        qo.setMaxPageSize();
        return listAll(qo);
    }

    @Override
    public void updateTotalNum(Shop shop, Integer frequency) {
        Assert.notNull(shop,"必须提供门店");
        Assert.notNull(frequency,"必须提供次数");
        shop.setTotalNum(shop.getTotalNum() + frequency);
        this.update(shop);
    }

    @Override
    public void increAppointNum(Shop shop) {
        Assert.notNull(shop,"必须提供门店");
        shop.setAppointNum(shop.getAppointNum() + 1);
        this.update(shop);
    }

}
