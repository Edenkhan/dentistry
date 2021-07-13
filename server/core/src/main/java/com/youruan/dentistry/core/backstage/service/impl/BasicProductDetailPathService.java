
package com.youruan.dentistry.core.backstage.service.impl;

import com.youruan.dentistry.core.backstage.domain.ProductDetailPath;
import com.youruan.dentistry.core.backstage.mapper.ProductDetailPathMapper;
import com.youruan.dentistry.core.backstage.query.ProductDetailPathQuery;
import com.youruan.dentistry.core.backstage.service.ProductDetailPathService;
import com.youruan.dentistry.core.backstage.vo.ExtendedProductDetailPath;
import com.youruan.dentistry.core.base.exception.OptimismLockingException;
import com.youruan.dentistry.core.base.query.Pagination;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BasicProductDetailPathService
        implements ProductDetailPathService {

    private final ProductDetailPathMapper productDetailPathMapper;

    public BasicProductDetailPathService(ProductDetailPathMapper productDetailPathMapper) {
        this.productDetailPathMapper = productDetailPathMapper;
    }

    @Override
    public ProductDetailPath get(Long id) {
        return productDetailPathMapper.get(id);
    }

    protected void update(ProductDetailPath productDetailPath) {
        int affected = productDetailPathMapper.update(productDetailPath);
        if (affected == 0) {
            throw new OptimismLockingException("version!!");
        }
        productDetailPath.setVersion((productDetailPath.getVersion() + 1));
    }

    protected int add(List<ProductDetailPath> productDetailPathList) {
        return productDetailPathMapper.add(productDetailPathList);
    }

    @Override
    public List<ExtendedProductDetailPath> listAll(ProductDetailPathQuery qo) {
        return productDetailPathMapper.query(qo);
    }

    @Override
    public ExtendedProductDetailPath queryOne(ProductDetailPathQuery qo) {
        qo.setPageSize(1);
        List<ExtendedProductDetailPath> datas = productDetailPathMapper.query(qo);
        return (((datas == null) || datas.isEmpty()) ? null : datas.get(0));
    }

    @Override
    public Pagination<ExtendedProductDetailPath> query(ProductDetailPathQuery qo) {
        int rows = productDetailPathMapper.count(qo);
        List<ExtendedProductDetailPath> datas = ((rows == 0) ? new ArrayList<>() : productDetailPathMapper.query(qo));
        return new Pagination<>(rows, datas);
    }

    @Override
    public int count(ProductDetailPathQuery qo) {
        return productDetailPathMapper.count(qo);
    }

    @Override
    public void create(Long productId, List<String> detailPathList) {
        this.checkAdd(productId,detailPathList);
        List<ProductDetailPath> list = new ArrayList<>();
        detailPathList.forEach(item -> {
            ProductDetailPath productDetailPath = new ProductDetailPath();
            productDetailPath.setCreatedDate(new Date());
            productDetailPath.setProductId(productId);
            productDetailPath.setDetailPath(item);
            list.add(productDetailPath);
        });
        add(list);
    }
    

    /**
     * 添加产品详情图片校验
     */
    private void checkAdd(Long productId, List<String> detailPathList) {
        this.checkParam(productId, detailPathList);
//        ProductDetailPathQuery qo = new ProductDetailPathQuery();
//        qo.setName(name);
//        int count = productDetailPathMapper.count(qo);
//        Assert.isTrue(count == 0,"产品详情图片名称重复");
//        qo = new ProductDetailPathQuery();
//        qo.setMark(mark);
//        count = productDetailPathMapper.count(qo);
//        Assert.isTrue(count == 0,"产品详情图片标识重复");
    }

    private void checkParam(Long productId, List<String> detailPathList) {
        Assert.notNull(productId, "必须提供产品id");
        Assert.notNull(detailPathList, "必须提供产品详情图片");
    }

    @Override
    @Transactional
    public void update(ProductDetailPath productDetailPath, Long productId, List<String> detailPathList) {
//        this.checkUpdate(productDetailPath, productId, detailPathList);
//        this.assign(productDetailPath, productId, detailPathList);
//        update(productDetailPath);
    }


    /**
     * 修改产品详情图片校验
     */
    private void checkUpdate(ProductDetailPath productDetailPath, Long productId, List<String> detailPathList) {
        Assert.notNull(productDetailPath,"必须提供产品详情图片");
        this.checkParam(productId, detailPathList);
//        ProductDetailPathQuery qo = new ProductDetailPathQuery();
//        qo.setName(name);
//        int count = productDetailPathMapper.count(qo);
//        Assert.isTrue(productDetailPath.getName().equalsIgnoreCase(name)||count == 0,"产品详情图片名称重复");
//        qo = new ProductDetailPathQuery();
//        qo.setMark(mark);
//        count = productDetailPathMapper.count(qo);
//        Assert.isTrue(productDetailPath.getMark().equalsIgnoreCase(mark)||count == 0,"产品详情图片标识重复");
    }

    @Override
    public List<? extends ProductDetailPath> listAll(Long[] productDetailPathIds) {
        ProductDetailPathQuery qo = new ProductDetailPathQuery();
        qo.setIds(productDetailPathIds);
        return listAll(qo);
    }

    @Override
    public List<ExtendedProductDetailPath> listAll() {
        ProductDetailPathQuery qo = new ProductDetailPathQuery();
        qo.setMaxPageSize();
        return listAll(qo);
    }

    @Override
    public void deleteByProductId(Long id) {
        productDetailPathMapper.deleteByProductId(id);
    }
}
