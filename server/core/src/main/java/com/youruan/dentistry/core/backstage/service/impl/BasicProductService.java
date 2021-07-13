
package com.youruan.dentistry.core.backstage.service.impl;

import com.youruan.dentistry.core.backstage.domain.Product;
import com.youruan.dentistry.core.backstage.domain.ProductDetailPath;
import com.youruan.dentistry.core.backstage.mapper.ProductMapper;
import com.youruan.dentistry.core.backstage.query.ProductDetailPathQuery;
import com.youruan.dentistry.core.backstage.query.ProductQuery;
import com.youruan.dentistry.core.backstage.service.ProductDetailPathService;
import com.youruan.dentistry.core.backstage.service.ProductService;
import com.youruan.dentistry.core.backstage.vo.ExtendedProduct;
import com.youruan.dentistry.core.backstage.vo.ExtendedProductDetailPath;
import com.youruan.dentistry.core.base.exception.OptimismLockingException;
import com.youruan.dentistry.core.base.query.Pagination;
import com.youruan.dentistry.core.base.storage.DiskFileStorage;
import com.youruan.dentistry.core.base.storage.UploadFile;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BasicProductService
        implements ProductService {

    private final ProductMapper productMapper;
    private final DiskFileStorage diskFileStorage;
    private final ProductDetailPathService productDetailPathService;

    public BasicProductService(ProductMapper productMapper, DiskFileStorage diskFileStorage, ProductDetailPathService productDetailPathService) {
        this.productMapper = productMapper;
        this.diskFileStorage = diskFileStorage;
        this.productDetailPathService = productDetailPathService;
    }

    @Override
    public Product get(Long id) {
        return productMapper.get(id);
    }

    protected void update(Product product) {
        int affected = productMapper.update(product);
        if (affected == 0) {
            throw new OptimismLockingException("version!!");
        }
        product.setVersion((product.getVersion() + 1));
    }

    protected Product add(Product product) {
        product.setCreatedDate(new Date());
        productMapper.add(product);
        return product;
    }

    @Override
    public List<ExtendedProduct> listAll(ProductQuery qo) {
        return productMapper.query(qo);
    }

    @Override
    public ExtendedProduct queryOne(ProductQuery qo) {
        qo.setPageSize(1);
        List<ExtendedProduct> datas = productMapper.query(qo);
        return (((datas == null) || datas.isEmpty()) ? null : datas.get(0));
    }

    @Override
    public Pagination<ExtendedProduct> query(ProductQuery qo) {
        int rows = productMapper.count(qo);
        List<ExtendedProduct> datas = ((rows == 0) ? new ArrayList<>() : productMapper.query(qo));
        return new Pagination<>(rows, datas);
    }

    @Override
    public int count(ProductQuery qo) {
        return productMapper.count(qo);
    }

    @Override
    public Product create(String name, String intro, Integer type, Integer userType, BigDecimal price, Integer totalAppointNum, Integer peopleNum, String iconPath, List<String> detailPathList, String description, Integer state) {
        // 远程产品
        if(Product.PRODUCT_TYPE_ONLINE.equals(type)) peopleNum = 1;
        // 团队
        if(Product.USER_TYPE_TEAM.equals(userType)) totalAppointNum = 1;
        this.checkAdd(name, intro, type, userType, price, totalAppointNum, peopleNum, iconPath, detailPathList, description, state);
        Product product = new Product();
        this.assign(product, name, intro, type, userType, price, totalAppointNum, peopleNum, iconPath, description, state);
        product = add(product);
        productDetailPathService.create(product.getId(),detailPathList);
        return product;
    }

    @Override
    @Transactional
    public void update(Product product, String name, String intro, Integer type, Integer userType, BigDecimal price, Integer totalAppointNum, Integer peopleNum, String iconPath, List<String> detailPathList, String description, Integer state) {
        this.checkUpdate(product, name, intro, type, userType, price, totalAppointNum, peopleNum, iconPath, detailPathList, description, state);
        this.assign(product, name, intro, type, userType, price, totalAppointNum, peopleNum, iconPath, description, state);
        update(product);
        productDetailPathService.deleteByProductId(product.getId());
        productDetailPathService.create(product.getId(),detailPathList);
    }

    /**
     * 修改产品校验
     */
    private void checkUpdate(Product product, String name, String intro, Integer type, Integer userType, BigDecimal price, Integer totalAppointNum, Integer peopleNum, String iconPath, List<String> detailPathList, String description, Integer state) {
        Assert.notNull(product, "必须提供产品");
        this.checkParam(name, intro, type, userType, price, totalAppointNum, peopleNum, iconPath, detailPathList, description, state);
        ProductQuery qo = new ProductQuery();
        qo.setName(name);
        int count = productMapper.count(qo);
        Assert.isTrue(product.getName().equalsIgnoreCase(name) || count == 0, "产品名称重复");
    }

    /**
     * 封装产品数据
     */
    private void assign(Product product, String name, String intro, Integer type, Integer userType, BigDecimal price, Integer totalAppointNum, Integer peopleNum, String iconPath, String description, Integer state) {
        product.setName(name);
        product.setIntro(intro);
        product.setType(type);
        product.setUserType(userType);
        product.setPrice(price);
        product.setTotalAppointNum(totalAppointNum);
        product.setPeopleNum(peopleNum);
        product.setIconPath(iconPath);
        product.setDescription(description);
        product.setState(state);
        product.setSales(0);
    }

    /**
     * 添加产品校验
     */
    private void checkAdd(String name, String intro, Integer type, Integer userType, BigDecimal price, Integer totalAppointNum, Integer peopleNum, String iconPath, List<String> detailPathList, String description, Integer state) {
        this.checkParam(name, intro, type, userType, price, totalAppointNum, peopleNum, iconPath, detailPathList, description, state);
        ProductQuery qo = new ProductQuery();
        qo.setName(name);
        int count = productMapper.count(qo);
        Assert.isTrue(count == 0, "产品名称重复");
    }

    private void checkParam(String name, String intro, Integer type, Integer userType, BigDecimal price, Integer totalAppointNum, Integer peopleNum, String iconPath, List<String> detailPathList, String description, Integer state) {
        Assert.notNull(name, "必须提供产品名称");
        Assert.notNull(intro, "必须提供产品简介");
        Assert.notNull(type, "必须提供产品类型");
        Assert.notNull(userType, "必须提供用户类型");
        Assert.notNull(price, "必须提供产品价格");
        Assert.notNull(totalAppointNum, "必须提供包含次数");
        Assert.notNull(peopleNum, "必须提供包含人数");
        Assert.notNull(iconPath, "必须提供产品列表主图");
        Assert.isTrue(!CollectionUtils.isEmpty(detailPathList), "必须提供产品详情主图");
        Assert.notNull(description, "必须提供产品详情内容");
        Assert.notNull(state, "必须提供产品状态");
    }

    @Override
    public List<? extends Product> listAll(Long[] productIds) {
        ProductQuery qo = new ProductQuery();
        qo.setIds(productIds);
        return listAll(qo);
    }

    @Override
    public List<ExtendedProduct> listAll() {
        ProductQuery qo = new ProductQuery();
        qo.setMaxPageSize();
        return listAll(qo);
    }

    @Override
    public List<String> upload(String directory, MultipartFile... files) {
        List<String> pathList = new ArrayList<>();
        Arrays.stream(files).forEach(file -> {
            try {
                UploadFile uploadFile = new UploadFile();
                BeanUtils.copyProperties(file, uploadFile);
                String path = diskFileStorage.store(uploadFile, directory);
                pathList.add(path);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return pathList;
    }

    @Override
    public ExtendedProduct handleData(Product product) {
        ProductDetailPathQuery qo = new ProductDetailPathQuery();
        qo.setProductId(product.getId());
        List<ExtendedProductDetailPath> productDetailList = productDetailPathService.listAll(qo);
        List<String> detailPathList = productDetailList.stream().map(ProductDetailPath::getDetailPath).collect(Collectors.toList());
        ExtendedProduct vo = new ExtendedProduct();
        BeanUtils.copyProperties(product,vo);
        vo.setDetailPathList(detailPathList);
        return vo;
    }

    @Override
    public void updateSales(Product product) {
        Assert.notNull(product,"必须提供产品");
        product.setSales(product.getSales() + 1);
        this.update(product);
    }

}
