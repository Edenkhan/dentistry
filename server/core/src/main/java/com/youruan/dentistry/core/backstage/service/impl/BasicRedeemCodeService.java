
package com.youruan.dentistry.core.backstage.service.impl;

import com.youruan.dentistry.core.backstage.domain.Product;
import com.youruan.dentistry.core.backstage.domain.RedeemCode;
import com.youruan.dentistry.core.backstage.domain.Shop;
import com.youruan.dentistry.core.backstage.mapper.RedeemCodeMapper;
import com.youruan.dentistry.core.backstage.query.RedeemCodeQuery;
import com.youruan.dentistry.core.backstage.service.DictionaryItemService;
import com.youruan.dentistry.core.backstage.service.ProductService;
import com.youruan.dentistry.core.backstage.service.RedeemCodeService;
import com.youruan.dentistry.core.backstage.service.ShopService;
import com.youruan.dentistry.core.backstage.vo.ExtendedDictionaryItem;
import com.youruan.dentistry.core.backstage.vo.ExtendedRedeemCode;
import com.youruan.dentistry.core.base.exception.OptimismLockingException;
import com.youruan.dentistry.core.base.query.Pagination;
import com.youruan.dentistry.core.base.utils.RandomStringUtils;
import com.youruan.dentistry.core.frontdesk.domain.Orders;
import com.youruan.dentistry.core.frontdesk.service.OrdersService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BasicRedeemCodeService
        implements RedeemCodeService {

    private final RedeemCodeMapper redeemCodeMapper;
    private final ProductService productService;
    private final OrdersService ordersService;
    private final DictionaryItemService dictionaryItemService;
    private final ShopService shopService;

    public BasicRedeemCodeService(RedeemCodeMapper redeemCodeMapper, ProductService productService,
                                  OrdersService ordersService, DictionaryItemService dictionaryItemService, ShopService shopService) {
        this.redeemCodeMapper = redeemCodeMapper;
        this.productService = productService;
        this.ordersService = ordersService;
        this.dictionaryItemService = dictionaryItemService;
        this.shopService = shopService;
    }

    @Override
    public RedeemCode get(Long id) {
        return redeemCodeMapper.get(id);
    }

    protected void update(RedeemCode redeemCode) {
        int affected = redeemCodeMapper.update(redeemCode);
        if (affected == 0) {
            throw new OptimismLockingException("version!!");
        }
        redeemCode.setVersion((redeemCode.getVersion() + 1));
    }

    protected RedeemCode add(RedeemCode redeemCode) {
        redeemCode.setCreatedDate(new Date());
        redeemCodeMapper.add(redeemCode);
        return redeemCode;
    }

    @Override
    public List<ExtendedRedeemCode> listAll(RedeemCodeQuery qo) {
        return redeemCodeMapper.query(qo);
    }

    @Override
    public ExtendedRedeemCode queryOne(RedeemCodeQuery qo) {
        qo.setPageSize(1);
        List<ExtendedRedeemCode> datas = redeemCodeMapper.query(qo);
        return (((datas == null) || datas.isEmpty()) ? null : datas.get(0));
    }

    @Override
    public Pagination<ExtendedRedeemCode> query(RedeemCodeQuery qo) {
        int rows = redeemCodeMapper.count(qo);
        List<ExtendedRedeemCode> datas = ((rows == 0) ? new ArrayList<>() : redeemCodeMapper.query(qo));
        return new Pagination<>(rows, datas);
    }

    @Override
    public int count(RedeemCodeQuery qo) {
        return redeemCodeMapper.count(qo);
    }

    @Override
    public void create(Long productId, Long shopId, Integer amount) {
        this.checkAdd(productId, shopId, amount);
        List<RedeemCode> redeemCodeList = this.createData(productId, shopId, amount);
        this.batchAdd(redeemCodeList);
    }

    /**
     * 批量生成兑换码
     */
    private List<RedeemCode> createData(Long productId, Long shopId, Integer amount) {
        List<RedeemCode> redeemCodeList = new ArrayList<>();
        RedeemCode redeemCode;
        for (int i = 0; i < amount; i++) {
            redeemCode = new RedeemCode();
            this.assign(redeemCode, productId, shopId);
            redeemCodeList.add(redeemCode);
        }
        return redeemCodeList;
    }

    /**
     * 批量添加兑换码
     */
    private void batchAdd(List<RedeemCode> redeemCodeList) {
        redeemCodeMapper.batchAdd(redeemCodeList);
    }

    /**
     * 封装数据
     */
    private void assign(RedeemCode redeemCode, Long productId, Long shopId) {
        redeemCode.setCreatedDate(new Date());
        redeemCode.setCode(RandomStringUtils.random(6));
        redeemCode.setBound(false);
        redeemCode.setUsed(false);
        redeemCode.setProductId(productId);
        redeemCode.setShopId(shopId);
    }

    /**
     * 添加兑换码校验
     */
    private void checkAdd(Long productId, Long shopId, Integer amount) {
        Assert.notNull(productId, "必须提供产品id");
        Assert.notNull(amount, "必须提供生成数量");
        Product product = productService.get(productId);
        if (Product.PRODUCT_TYPE_OFFLINE.equals(product.getType())) {
            Assert.notNull(shopId, "必须提供门店id");
        }
    }

    /**
     * 修改兑换码校验
     */
    private void checkUpdate(RedeemCode redeemCode, String name, String logo) {

    }

    @Override
    public List<? extends RedeemCode> listAll(Long[] redeemCodeIds) {
        RedeemCodeQuery qo = new RedeemCodeQuery();
        qo.setIds(redeemCodeIds);
        return listAll(qo);
    }

    @Override
    public List<ExtendedRedeemCode> listAll() {
        RedeemCodeQuery qo = new RedeemCodeQuery();
        qo.setMaxPageSize();
        return listAll(qo);
    }

    @Override
    public RedeemCode getByCode(String code) {
        RedeemCodeQuery qo = new RedeemCodeQuery();
        qo.setCode(code);
        ExtendedRedeemCode vo = this.queryOne(qo);
        RedeemCode redeemCode = new RedeemCode();
        BeanUtils.copyProperties(vo, redeemCode);
        return redeemCode;
    }

    @Override
    @Transactional
    public void bindUser(RedeemCode redeemCode, Long userId, String dicItemName) {
        Assert.notNull(redeemCode, "必须提供兑换码");
        Assert.notNull(userId, "必须提供用户id");
        Assert.isTrue(!redeemCode.getBound(), "该兑换码已兑换");
        // 查看門店是否啓用
        Shop shop = shopService.get(redeemCode.getShopId());
        Assert.notNull(shop,"必須提供門店");
        Assert.isTrue(shop.getEnabled(),"當前門店已禁用，無法兌換");
        // 添加订单
        redeemCode.setUserId(userId);
        Orders orders = this.addOrders(redeemCode, dicItemName);
        redeemCode.setOrderId(orders.getId());
        redeemCode.setBound(true);
        // 更新兑换码信息
        this.update(redeemCode);
    }

    /**
     * 添加订单
     */
    private Orders addOrders(RedeemCode redeemCode, String dicItemName) {
        Product product = productService.get(redeemCode.getProductId());
        Assert.notNull(product, "必须提供产品");
        Long dicItemId = null;
        if(Product.PRODUCT_TYPE_ONLINE.equals(product.getType())) {
            ExtendedDictionaryItem dictionaryItem = dictionaryItemService.getByName(dicItemName);
            Assert.notNull(dictionaryItem,"必须提供字典详情");
            dicItemId = dictionaryItem.getId();
        }
        // 兑换码生成订单
        return ordersService.redeemOrders(product.getPrice(),
                redeemCode.getUserId(),
                product.getId(),
                redeemCode.getShopId(),
                dicItemId);

    }

    @Override
    public void redeemCompleted(ExtendedRedeemCode redeemCode) {
        Assert.notNull(redeemCode,"必须提供兑换码");
        redeemCode.setUsed(true);
        this.update(redeemCode);
    }


}
