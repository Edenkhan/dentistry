
package com.youruan.dentistry.core.backstage.service;

import com.youruan.dentistry.core.backstage.domain.Product;
import com.youruan.dentistry.core.backstage.query.ProductQuery;
import com.youruan.dentistry.core.backstage.vo.ExtendedProduct;
import com.youruan.dentistry.core.base.query.Pagination;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {

    /**
     * 根据id，获取单条记录
     */
    public Product get(Long id);

    /**
     * 根据条件，获取单条记录
     */
    public ExtendedProduct queryOne(ProductQuery qo);

    /**
     * 返回所有记录
     */
    public List<ExtendedProduct> listAll(ProductQuery qo);

    /**
     * 根据条件，查询列表
     */
    public Pagination<ExtendedProduct> query(ProductQuery qo);

    /**
     * 根据查询条件，返回记录条目
     */
    public int count(ProductQuery qo);

    /**
     * 添加
     */
    Product create(String name, String intro, Integer type, Integer userType,
                   BigDecimal price, Integer totalAppointNum, Integer peopleNum,
                   String iconPath, List<String> pathList, String description);

    /**
     * 修改
     */
    void update(Product product, String name, String intro, Integer type,
                Integer userType, BigDecimal price, Integer totalAppointNum,
                Integer peopleNum, String iconPath, List<String> pathList, String description);

    /**
     * 根据id集合，查询对应列表
     */
    List<? extends Product> listAll(Long[] dictionaryIds);

    /**
     * 返回所有记录
     */
    List<ExtendedProduct> listAll();

    /**
     * 上传产品图片
     */
    String upload(String directory, MultipartFile file);

    /**
     * 给商品绑定详情图片
     */
    ExtendedProduct handleData(Product product);

    /**
     * 增加销量
     */
    void updateSales(Product product);

    /**
     * 改变产品状态
     */
    void changeState(Product product);
}
