
package com.youruan.dentistry.core.backstage.service.impl;

import com.youruan.dentistry.core.backstage.domain.Product;
import com.youruan.dentistry.core.backstage.domain.RedeemCode;
import com.youruan.dentistry.core.backstage.mapper.RedeemCodeMapper;
import com.youruan.dentistry.core.backstage.query.RedeemCodeQuery;
import com.youruan.dentistry.core.backstage.service.ProductService;
import com.youruan.dentistry.core.backstage.service.RedeemCodeService;
import com.youruan.dentistry.core.backstage.vo.ExtendedRedeemCode;
import com.youruan.dentistry.core.base.exception.OptimismLockingException;
import com.youruan.dentistry.core.base.query.Pagination;
import com.youruan.dentistry.core.base.utils.RandomStringUtils;
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

    public BasicRedeemCodeService(RedeemCodeMapper redeemCodeMapper, ProductService productService) {
        this.redeemCodeMapper = redeemCodeMapper;
        this.productService = productService;
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
    public void create(Long productId, Long shopId, Integer codeNum) {
        this.checkAdd(productId, shopId, codeNum);
        List<RedeemCode> redeemCodeList = this.createData(productId, shopId, codeNum);
        this.batchAdd(redeemCodeList);
    }

    /**
     * 批量生成兑换码
     */
    private List<RedeemCode> createData(Long productId, Long shopId, Integer codeNum) {
        List<RedeemCode> redeemCodeList = new ArrayList<>();
        RedeemCode redeemCode;
        for (int i = 0; i < codeNum; i++) {
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

    private void assign(RedeemCode redeemCode, Long productId, Long shopId) {
        redeemCode.setCreatedDate(new Date());
        System.out.println("随机字符串："+ RandomStringUtils.random(6));
        redeemCode.setCode(RandomStringUtils.random(6));
        redeemCode.setBound(false);
        redeemCode.setUsed(false);
        redeemCode.setProductId(productId);
        redeemCode.setShopId(shopId);
    }

    /**
     * 添加兑换码校验
     */
    private void checkAdd(Long productId, Long shopId, Integer codeNum) {
        Assert.notNull(productId, "必须提供产品id");
        Assert.notNull(codeNum, "必须提供生成数量");
        Product product = productService.get(productId);
        if (Product.PRODUCT_TYPE_OFFLINE.equals(product.getType())) {
            Assert.notNull(shopId, "必须提供门店id");
        }
    }

    @Override
    @Transactional
    public void update(RedeemCode redeemCode, String name, String logo) {

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


}
