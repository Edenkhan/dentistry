package com.youruan.dentistry.core.frontdesk.service.impl;


import com.youruan.dentistry.core.backstage.domain.Product;
import com.youruan.dentistry.core.backstage.service.ProductService;
import com.youruan.dentistry.core.backstage.vo.OrderRecordVo;
import com.youruan.dentistry.core.base.constant.WechatConstant;
import com.youruan.dentistry.core.base.exception.OptimismLockingException;
import com.youruan.dentistry.core.base.query.Pagination;
import com.youruan.dentistry.core.base.utils.HttpClientUtils;
import com.youruan.dentistry.core.base.utils.SnowflakeIdWorker;
import com.youruan.dentistry.core.base.utils.wechat.WXPayUtil;
import com.youruan.dentistry.core.frontdesk.WechatPayProperties;
import com.youruan.dentistry.core.frontdesk.domain.Orders;
import com.youruan.dentistry.core.frontdesk.mapper.OrdersMapper;
import com.youruan.dentistry.core.frontdesk.query.OrdersQuery;
import com.youruan.dentistry.core.frontdesk.service.OrdersService;
import com.youruan.dentistry.core.frontdesk.vo.ExtendedOrders;
import com.youruan.dentistry.core.user.domain.RegisteredUser;
import com.youruan.dentistry.core.user.vo.UserBoughtVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.*;

@Service
public class BasicOrdersService implements OrdersService {
    private final OrdersMapper ordersMapper;
    private final WechatPayProperties wechatPayProperties;
    private final ProductService productService;

    public BasicOrdersService(OrdersMapper ordersMapper, WechatPayProperties wechatPayProperties, ProductService productService) {
        this.ordersMapper = ordersMapper;
        this.wechatPayProperties = wechatPayProperties;
        this.productService = productService;
    }

    @Override
    public Orders get(Long id) {
        return ordersMapper.get(id);
    }

    protected void update(Orders orders) {
        int affected = ordersMapper.update(orders);
        if (affected == 0) {
            throw new OptimismLockingException("version!!");
        }
        orders.setVersion((orders.getVersion() + 1));
    }

    protected Orders add(Orders orders) {
        orders.setCreatedDate(new Date());
        ordersMapper.add(orders);
        return orders;
    }

    @Override
    public List<ExtendedOrders> listAll(OrdersQuery qo) {
        return ordersMapper.query(qo);
    }

    @Override
    public ExtendedOrders queryOne(OrdersQuery qo) {
        qo.setPageSize(1);
        List<ExtendedOrders> datas = ordersMapper.query(qo);
        return (((datas == null) || datas.isEmpty()) ? null : datas.get(0));
    }

    @Override
    public Pagination<ExtendedOrders> query(OrdersQuery qo) {
        int rows = ordersMapper.count(qo);
        List<ExtendedOrders> datas = ((rows == 0) ? new ArrayList<>() : ordersMapper.query(qo));
        return new Pagination<>(rows, datas);
    }

    @Override
    public int count(OrdersQuery qo) {
        return ordersMapper.count(qo);
    }

    @Override
    @Transactional
    public Orders create(BigDecimal price, Long userId, Long productId, Long shopId, Long dicItemId) {
        this.checkAdd(price, userId, productId);
        this.delete(userId, productId, shopId, dicItemId);
        Product product = productService.get(productId);
        Orders orders = new Orders();
        this.assign(orders, price, product.getTotalAppointNum(), userId, productId, shopId, dicItemId, false);
        return add(orders);
    }

    @Override
    public void delete(Long userId, Long productId, Long shopId, Long dicItemId) {
        Orders orders = new Orders();
        orders.setUserId(userId);
        orders.setProductId(productId);
        orders.setShopId(shopId);
        orders.setDicItemId(dicItemId);
        orders.setPayStatus(Orders.PAY_STATUS_UNPAID);
        ordersMapper.deleteByCondition(orders);
    }

    /**
     * 添加订单校验
     */
    private void checkAdd(BigDecimal price, Long userId, Long productId) {
        this.checkParam(price, userId, productId);
    }

    /**
     * 修改订单校验
     */
    private void checkUpdate(Orders orders, String name, String mark) {
//        Assert.notNull(orders,"必须提供字典");
//        this.checkParam(name, mark);
//        OrdersQuery qo = new OrdersQuery();
//        qo.setName(name);
//        int count = ordersMapper.count(qo);
//        Assert.isTrue(orders.getName().equalsIgnoreCase(name)||count == 0,"字典名称重复");
//        qo = new OrdersQuery();
//        qo.setMark(mark);
//        count = ordersMapper.count(qo);
//        Assert.isTrue(orders.getMark().equalsIgnoreCase(mark)||count == 0,"字典标识重复");
    }

    private void checkParam(BigDecimal price, Long userId, Long productId) {
        Assert.notNull(price, "必须提供订单价格");
        Assert.notNull(userId, "必须提供用户id");
        Assert.notNull(productId, "必须提供商品id");
    }

    /**
     * 封装数据
     */
    private void assign(Orders orders, BigDecimal price, Integer totalNum, Long userId, Long productId, Long shopId, Long dicItemId, Boolean isRedeemOrder) {
        orders.setOrderNo(SnowflakeIdWorker.getIdWorker());
        orders.setPayStatus(Orders.PAY_STATUS_UNPAID);
        orders.setAppointStatus(Orders.APPOINT_STATUS_NOT);
        orders.setPrice(price);
        orders.setTotalNum(totalNum);
        orders.setAppointNum(0);
        orders.setUserId(userId);
        orders.setProductId(productId);
        orders.setShopId(shopId);
        orders.setDicItemId(dicItemId);
        orders.setIsRedeemOrder(isRedeemOrder);
        if(isRedeemOrder) {
            // 兑换码订单 状态已支付
            orders.setPayStatus(Orders.PAY_STATUS_PAID);
            // 支付时间
            orders.setBoughtTime(new Date());
        }
    }

    @Override
    public List<? extends Orders> listAll(Long[] ordersIds) {
        OrdersQuery qo = new OrdersQuery();
        qo.setIds(ordersIds);
        return listAll(qo);
    }

    @Override
    public List<ExtendedOrders> listAll() {
        OrdersQuery qo = new OrdersQuery();
        qo.setMaxPageSize();
        return listAll(qo);
    }

    @Override
    public String placeOrder(RegisteredUser user, Orders orders, String ip) {
        Assert.notNull(user, "必须提供用户");
        Assert.notNull(orders, "必须提供订单信息");
        Assert.notNull(ip, "必须提供用户ip");
        try {
            Map<String, String> paramMap = new HashMap<>();
            paramMap.put("appid", wechatPayProperties.getAppId());
            paramMap.put("mch_id", wechatPayProperties.getMchid());
            paramMap.put("nonce_str", WXPayUtil.generateNonceStr());
            paramMap.put("sign", WXPayUtil.generateSignature(paramMap, wechatPayProperties.getPrivateKey()));
            paramMap.put("body", "大苏打");
            paramMap.put("out_trade_no", orders.getOrderNo());
            paramMap.put("total_fee", "1");
            paramMap.put("spbill_create_ip", ip);
            paramMap.put("notify_url", wechatPayProperties.getNotifyUrl());
            System.out.println("notify_url:::" + wechatPayProperties.getNotifyUrl());
            paramMap.put("trade_type", "JSAPI");
            paramMap.put("openid", user.getOpenid());
            String xml = HttpClientUtils.doPostXml(WechatConstant.UNIFIED_ORDER_URL,
                    WXPayUtil.generateSignedXml(paramMap, wechatPayProperties.getPrivateKey()));
            Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);
            System.out.println("resultMap:" + resultMap);
            return resultMap.get("prepay_id");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Map<String, String> payHandle(String prepayId) {
        try {
            Map<String, String> resultMap = new HashMap<>();
            resultMap.put("appId", wechatPayProperties.getAppId());
            resultMap.put("timeStamp", String.valueOf(System.currentTimeMillis() / 1000));
            resultMap.put("signType", "MD5");
            resultMap.put("nonceStr", WXPayUtil.generateNonceStr());
            resultMap.put("package", "prepay_id=" + prepayId);
            resultMap.put("paySign", WXPayUtil.generateSignature(resultMap, wechatPayProperties.getPrivateKey()));
            System.out.println(resultMap);
            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Orders getByOrderNo(String orderNo) {
        OrdersQuery qo = new OrdersQuery();
        qo.setOrderNo(orderNo);
        return this.queryOne(qo);
    }

    @Override
    public ExtendedOrders handleData(Orders orders) {
        ExtendedOrders extendedOrders = new ExtendedOrders();
        Product product = productService.get(orders.getProductId());
        BeanUtils.copyProperties(orders, extendedOrders);
        extendedOrders.setPeopleNum(product.getPeopleNum());
        extendedOrders.setProductName(product.getName());
        extendedOrders.setUserType(product.getUserType());
        extendedOrders.setProductType(product.getType());
        extendedOrders.setDescription(product.getDescription());
        extendedOrders.setIconPath(product.getIconPath());
        return extendedOrders;
    }

    @Override
    public void updateAppointNum(Orders orders) {
        Assert.notNull(orders, "必须提供订单");
        Assert.isTrue(Orders.APPOINT_STATUS_NOT.equals(orders.getAppointStatus()),"当前订单状态不是待预约");
        orders.setAppointNum(orders.getAppointNum() + 1);
        orders.setAppointStatus(Orders.APPOINT_STATUS_OK);
        this.update(orders);
    }

    @Override
    public Pagination<OrderRecordVo> record(OrdersQuery qo) {
        int rows = ordersMapper.countRecord(qo);
        List<OrderRecordVo> datas = ((rows == 0) ? new ArrayList<>() : ordersMapper.record(qo));
        return new Pagination<>(rows, datas);
    }

    @Override
    public Pagination<UserBoughtVo> bought(OrdersQuery qo) {
        int rows = ordersMapper.countBought(qo);
        List<UserBoughtVo> datas = (rows == 0) ? new ArrayList<>() : ordersMapper.bought(qo);
        return new Pagination<>(rows, datas);
    }

    @Override
    public void appointCompleted(Orders orders) {
        Assert.notNull(orders, "必须提供订单");
        Assert.isTrue(Orders.APPOINT_STATUS_OK.equals(orders.getAppointStatus()),"当前订单状态不是已完成");
        orders.setAppointStatus(Orders.APPOINT_STATUS_NOT);
        this.update(orders);
    }

    @Override
    @Transactional
    public void payCompleted(Orders orders) {
        Assert.notNull(orders, "必须提供订单");
        Assert.isTrue(Orders.PAY_STATUS_UNPAID.equals(orders.getPayStatus()),"当前订单状态不是未支付");
        orders.setBoughtTime(new Date());
        orders.setPayStatus(Orders.PAY_STATUS_PAID);
        this.update(orders);
        // 增加产品销量
        Product product = productService.get(orders.getProductId());
        productService.updateSales(product);
    }

    @Override
    public Orders redeemOrders(BigDecimal price, Long userId, Long productId, Long shopId, Long dicItemId) {
        this.checkAdd(price, userId, productId);
        Product product = productService.get(productId);
        Assert.notNull(product,"必须提供商品");
        Orders orders = new Orders();
        this.assign(orders, price, product.getTotalAppointNum(), userId, productId, shopId, dicItemId, true);
        return this.add(orders);
    }


}
