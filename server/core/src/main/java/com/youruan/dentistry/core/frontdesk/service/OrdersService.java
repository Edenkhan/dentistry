package com.youruan.dentistry.core.frontdesk.service;


import com.youruan.dentistry.core.backstage.vo.OrderRecordVo;
import com.youruan.dentistry.core.base.query.Pagination;
import com.youruan.dentistry.core.frontdesk.domain.Orders;
import com.youruan.dentistry.core.frontdesk.query.OrdersQuery;
import com.youruan.dentistry.core.frontdesk.vo.ExtendedOrders;
import com.youruan.dentistry.core.user.domain.RegisteredUser;
import com.youruan.dentistry.core.user.vo.UserBoughtVo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface OrdersService {
    /**
     * 根据id，获取单条记录
     */
    public Orders get(Long id);
    /**
     * 根据条件，获取单条记录
     */
    public ExtendedOrders queryOne(OrdersQuery qo);
    /**
     * 返回所有记录
     */
    public List<ExtendedOrders> listAll(OrdersQuery qo);
    /**
     * 根据条件，查询列表
     */
    public Pagination<ExtendedOrders> query(OrdersQuery qo);
    /**
     * 根据查询条件，返回记录条目
     */
    public int count(OrdersQuery qo);
    /**
     * 添加
     */
    Orders create(BigDecimal price, Long userId, Long productId, Long shopId, Long dicItemId);

    /**
     * 删除
     */
    void delete(Long userId, Long productId, Long shopId, Long dicItemId);
    /**
     * 根据id集合，查询对应列表
     */
    List<? extends Orders> listAll(Long[] ordersIds);
    /**
     * 返回所有记录
     */
    List<ExtendedOrders> listAll();

    /**
     * 统一下单，返回JSAPI调起支付所需参数
     */
    Map<String, String> placeOrder(RegisteredUser user, Orders orders, String ip) throws Exception;

    /**
     * 返回JSAPI调起支付所需参数
     */
    Map<String, String> payHandle(String prepayId) throws Exception;

    /**
     * 查询by订单号
     */
    Orders getByOrderNo(String orderNo);

    /**
     * 查询订单对应的商品信息
     */
    ExtendedOrders handleData(Orders orders);

    /**
     * 增加订单已预约次数
     */
    void increAppointNum(Orders orders);


    /**
     * 查询订单记录
     */
    Pagination<OrderRecordVo> record(OrdersQuery qo);

    /**
     * 查询用户已购买
     */
    Pagination<UserBoughtVo> bought(OrdersQuery qo);
    /**
     * 预约完成，改变订单预约状态
     */
    void appointCompleted(Orders orders);

    /**
     * 支付成功
     */
    void payCompleted(Orders orders);

    /**
     * 兑换订单
     */
    Orders redeemOrders(BigDecimal price, Long userId, Long productId, Long shopId, Long dicItemId);

    /**
     * 订单 预约进行中
     */
    void appointProgress(Orders orders);

    /**
     * 过滤掉用完次数 并且已经完成预约的订单
     */
    List<OrderRecordVo> handleData(List<OrderRecordVo> data);
}
