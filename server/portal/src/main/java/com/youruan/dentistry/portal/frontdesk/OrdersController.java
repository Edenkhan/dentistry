package com.youruan.dentistry.portal.frontdesk;

import com.google.common.collect.ImmutableMap;
import com.youruan.dentistry.core.backstage.domain.Dictionary;
import com.youruan.dentistry.core.backstage.domain.Product;
import com.youruan.dentistry.core.backstage.query.DictionaryItemQuery;
import com.youruan.dentistry.core.backstage.query.DictionaryQuery;
import com.youruan.dentistry.core.backstage.query.ShopQuery;
import com.youruan.dentistry.core.backstage.service.DictionaryItemService;
import com.youruan.dentistry.core.backstage.service.DictionaryService;
import com.youruan.dentistry.core.backstage.service.ShopService;
import com.youruan.dentistry.core.backstage.vo.ExtendedDictionary;
import com.youruan.dentistry.core.backstage.vo.ExtendedDictionaryItem;
import com.youruan.dentistry.core.backstage.vo.ExtendedShop;
import com.youruan.dentistry.core.backstage.vo.OrderRecordVo;
import com.youruan.dentistry.core.base.query.Pagination;
import com.youruan.dentistry.core.base.utils.BeanMapUtils;
import com.youruan.dentistry.core.base.utils.IPUtils;
import com.youruan.dentistry.core.base.utils.StreamUtils;
import com.youruan.dentistry.core.base.utils.wechat.WXPayUtil;
import com.youruan.dentistry.core.frontdesk.domain.Orders;
import com.youruan.dentistry.core.frontdesk.query.OrdersQuery;
import com.youruan.dentistry.core.frontdesk.service.OrdersService;
import com.youruan.dentistry.core.frontdesk.vo.ExtendedOrders;
import com.youruan.dentistry.core.user.domain.RegisteredUser;
import com.youruan.dentistry.portal.base.interceptor.RequiresAuthentication;
import com.youruan.dentistry.portal.frontdesk.form.OrdersAddForm;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/frontdesk/orders")
public class OrdersController {

    private final DictionaryService dictionaryService;
    private final DictionaryItemService dictionaryItemService;
    private final ShopService shopService;
    private final OrdersService ordersService;
    private final HttpServletRequest request;

    public OrdersController(DictionaryService dictionaryService, DictionaryItemService dictionaryItemService, ShopService shopService, OrdersService ordersService, HttpServletRequest request) {
        this.dictionaryService = dictionaryService;
        this.dictionaryItemService = dictionaryItemService;
        this.shopService = shopService;
        this.ordersService = ordersService;
        this.request = request;
    }

    @GetMapping("/get")
    @RequiresAuthentication
    public ResponseEntity<?> get(Long orderId) {
        Orders orders = ordersService.get(orderId);
        ExtendedOrders extendedOrders = ordersService.handleData(orders);
        return ResponseEntity.ok(extendedOrders);
    }

    /**
     * 查询用户可预约的订单
     */
    @GetMapping("/getByUser")
    @RequiresAuthentication
    public ResponseEntity<?> getByUser(RegisteredUser user) {
        OrdersQuery qo = new OrdersQuery();
        qo.setUserId(user.getId());
        qo.setPayStatus(Orders.PAY_STATUS_PAID);
        qo.setProductType(Product.PRODUCT_TYPE_OFFLINE);
        qo.setMaxPageSize();
        Pagination<OrderRecordVo> pagination = ordersService.record(qo);
        List<OrderRecordVo> data = ordersService.handleData(pagination.getData());
        return ResponseEntity.ok(ImmutableMap.builder()
                .put("data", data)
                .build());
    }

    @GetMapping("/list")
    @RequiresAuthentication
    public ResponseEntity<?> list(RegisteredUser user) {
        OrdersQuery qo = new OrdersQuery();
        qo.setUserId(user.getId());
        qo.setMaxPageSize();
        Pagination<OrderRecordVo> pagination = ordersService.record(qo);
        return ResponseEntity.ok(ImmutableMap.builder()
                .put("data",pagination.getData())
                .build());
    }

    @GetMapping("/getDoctor")
    @RequiresAuthentication
    public ResponseEntity<?> getDoctor() {
        DictionaryQuery qo = new DictionaryQuery();
        qo.setMark(Dictionary.MARK_DOCTOR);
        ExtendedDictionary dictionary = dictionaryService.queryOne(qo);
        DictionaryItemQuery qo2 = new DictionaryItemQuery();
        qo2.setDictionaryId(dictionary.getId());
        qo2.setEnabled(true);
        List<ExtendedDictionaryItem> voList = dictionaryItemService.listAll(qo2);
        return ResponseEntity.ok(BeanMapUtils.pick(voList,"id","name"));
    }

    @GetMapping("/getShop")
    @RequiresAuthentication
    public ResponseEntity<?> getShop() {
        ShopQuery qo = new ShopQuery();
        qo.setEnabled(true);
        List<ExtendedShop> shopList = shopService.listAll(qo);
        return ResponseEntity.ok(BeanMapUtils.pick(shopList,"id","name"));
    }

    @PostMapping("/add")
    @RequiresAuthentication
    public ResponseEntity<?> add(OrdersAddForm form) {
        Orders orders = ordersService.create(form.getPrice(),
                form.getUserId(),
                form.getProductId(),
                form.getShopId(),
                form.getDicItemId());
        return ResponseEntity.ok(ImmutableMap.builder()
                .put("id", orders.getId())
                .build());
    }

    @PostMapping("/toPay")
    @RequiresAuthentication
    public ResponseEntity<?> toPay(RegisteredUser user,Long orderId) throws Exception {
        String ip = IPUtils.getClientIp(request);
        Orders orders = ordersService.get(orderId);
        Map<String, String> resultMap = ordersService.placeOrder(user, orders, ip);
        return ResponseEntity.ok(ImmutableMap.builder()
                .put("id", orders.getId())
                .put("result", resultMap)
                .build());
    }

    /**
     * 微信支付回调地址 修改订单状态 增加产品销量
     */
    @PostMapping("/notify")
    public ResponseEntity<?> notify_(HttpServletRequest request) throws Exception {
        String xml = StreamUtils.readStream(request.getInputStream());
        Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);
        Orders orders = ordersService.getByOrderNo(resultMap.get("out_trade_no"));
        Assert.notNull(orders,"该订单已清除");
        if(Orders.PAY_STATUS_UNPAID.equals(orders.getPayStatus())) {
            ordersService.payCompleted(orders);
        }
        return ResponseEntity.ok("订单已支付");
    }
}
