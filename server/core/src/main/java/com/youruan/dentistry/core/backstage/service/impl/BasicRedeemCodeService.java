
package com.youruan.dentistry.core.backstage.service.impl;

import com.youruan.dentistry.core.base.exception.OptimismLockingException;
import com.youruan.dentistry.core.base.query.Pagination;
import com.youruan.dentistry.core.backstage.domain.RedeemCode;
import com.youruan.dentistry.core.backstage.mapper.RedeemCodeMapper;
import com.youruan.dentistry.core.backstage.query.RedeemCodeQuery;
import com.youruan.dentistry.core.backstage.service.RedeemCodeService;
import com.youruan.dentistry.core.backstage.vo.ExtendedRedeemCode;
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

    public BasicRedeemCodeService(RedeemCodeMapper redeemCodeMapper) {
        this.redeemCodeMapper = redeemCodeMapper;
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
        List<ExtendedRedeemCode> datas = ((rows == 0) ? new ArrayList<ExtendedRedeemCode>() : redeemCodeMapper.query(qo));
        return new Pagination<ExtendedRedeemCode>(rows, datas);
    }

    @Override
    public int count(RedeemCodeQuery qo) {
        return redeemCodeMapper.count(qo);
    }

    @Override
    public RedeemCode create(String name, String logo) {
        this.checkAdd(name,logo);
        RedeemCode redeemCode = new RedeemCode();
//        redeemCode.setName(name);
//        redeemCode.setLogo(logo);
        return add(redeemCode);
    }

    /**
     * 添加字典校验
     */
    private void checkAdd(String name, String logo) {
        this.checkParam(name, logo);
        RedeemCodeQuery qo = new RedeemCodeQuery();
//        qo.setName(name);
        int count = redeemCodeMapper.count(qo);
        Assert.isTrue(count == 0,"字典名称重复");
        qo = new RedeemCodeQuery();
//        qo.setLogo(logo);
        count = redeemCodeMapper.count(qo);
        Assert.isTrue(count == 0,"字典标识重复");
    }

    private void checkParam(String name, String logo) {
        Assert.notNull(name, "必须提供字典名称");
        Assert.notNull(logo, "必须提供字典标识");
    }

    @Override
    @Transactional
    public void update(RedeemCode redeemCode, String name, String logo) {
        this.checkUpdate(redeemCode, name, logo);
//        redeemCode.setName(name);
//        redeemCode.setLogo(logo);
        update(redeemCode);
    }

    /**
     * 修改字典校验
     */
    private void checkUpdate(RedeemCode redeemCode, String name, String logo) {
        Assert.notNull(redeemCode,"必须提供字典");
        this.checkParam(name, logo);
        RedeemCodeQuery qo = new RedeemCodeQuery();
//        qo.setName(name);
        int count = redeemCodeMapper.count(qo);
//        Assert.isTrue(redeemCode.getName().equals(name)||count == 0,"字典名称重复");
        qo = new RedeemCodeQuery();
//        qo.setLogo(logo);
        count = redeemCodeMapper.count(qo);
//        Assert.isTrue(redeemCode.getLogo().equals(logo)||count == 0,"字典标识重复");
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
