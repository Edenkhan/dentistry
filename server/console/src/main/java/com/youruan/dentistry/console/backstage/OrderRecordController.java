package com.youruan.dentistry.console.backstage;

import com.google.common.collect.ImmutableMap;
import com.youruan.dentistry.console.backstage.form.OrderRecordListForm;
import com.youruan.dentistry.console.base.interceptor.RequiresPermission;
import com.youruan.dentistry.core.backstage.vo.OrderRecordVo;
import com.youruan.dentistry.core.base.query.Pagination;
import com.youruan.dentistry.core.frontdesk.query.OrdersQuery;
import com.youruan.dentistry.core.frontdesk.service.OrdersService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 订单记录
 */
@RestController
@RequestMapping("/backstage/orderRecord")
public class OrderRecordController {

    private final OrdersService ordersService;

    public OrderRecordController(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    @GetMapping("/list")
    @RequiresPermission(value = "backstage.orderRecord.list", description = "订单记录-列表")
    public ResponseEntity<?> list(OrderRecordListForm form) {
        OrdersQuery qo = form.buildQuery();
        Pagination<OrderRecordVo> pagination = ordersService.record(qo);
        return ResponseEntity.ok(ImmutableMap.builder()
                .put("data", pagination.getData())
                .put("rows", pagination.getRows())
                .build());
    }
    
}
