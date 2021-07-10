package com.youruan.dentistry.core.frontdesk.mapper;


import com.youruan.dentistry.core.backstage.vo.OrderRecordVo;
import com.youruan.dentistry.core.frontdesk.domain.Orders;
import com.youruan.dentistry.core.frontdesk.query.OrdersQuery;
import com.youruan.dentistry.core.frontdesk.vo.ExtendedOrders;
import com.youruan.dentistry.core.user.vo.UserBoughtVo;

import java.util.List;


public interface OrdersMapper {
    /**
     * 获取单条记录
     */
    public Orders get(Long id);
    /**
     * 修改
     */
    public int update(Orders orders);
    /**
     * 添加
     */
    public int add(Orders orders);
    /**
     * 删除
     * @param id
     */
    public int delete(Long id);
    public int deleteByCondition(Orders orders);
    /**
     * 根据查询条件，返回记录条目
     */
    public int count(OrdersQuery qo);
    /**
     * 根据查询条件，返回记录列表
     */
    public List<ExtendedOrders> query(OrdersQuery qo);

    /**
     * 联表查询订单记录数
     */
    int countRecord(OrdersQuery qo);

    /**
     * 条件查询 订单记录
     */
    List<OrderRecordVo> record(OrdersQuery qo);

    /**
     * 查询已购买订单记录数
     */
    int countBought(OrdersQuery qo);

    /**
     * 查询已购买订单数据
     */
    List<UserBoughtVo> bought(OrdersQuery qo);
}
