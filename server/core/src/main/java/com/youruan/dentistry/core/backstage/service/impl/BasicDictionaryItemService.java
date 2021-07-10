
package com.youruan.dentistry.core.backstage.service.impl;

import com.youruan.dentistry.core.backstage.domain.DictionaryItem;
import com.youruan.dentistry.core.backstage.mapper.DictionaryItemMapper;
import com.youruan.dentistry.core.backstage.query.DictionaryItemQuery;
import com.youruan.dentistry.core.backstage.service.DictionaryItemService;
import com.youruan.dentistry.core.backstage.vo.DictionaryItemListVo;
import com.youruan.dentistry.core.base.exception.OptimismLockingException;
import com.youruan.dentistry.core.base.query.Pagination;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BasicDictionaryItemService
        implements DictionaryItemService {

    private final DictionaryItemMapper dictionaryItemMapper;

    public BasicDictionaryItemService(DictionaryItemMapper dictionaryItemMapper) {
        this.dictionaryItemMapper = dictionaryItemMapper;
    }

    @Override
    public DictionaryItem get(Long id) {
        return dictionaryItemMapper.get(id);
    }

    protected void update(DictionaryItem dictionaryItem) {
        int affected = dictionaryItemMapper.update(dictionaryItem);
        if (affected == 0) {
            throw new OptimismLockingException("version!!");
        }
        dictionaryItem.setVersion((dictionaryItem.getVersion() + 1));
    }

    protected DictionaryItem add(DictionaryItem dictionaryItem) {
        dictionaryItem.setCreatedDate(new Date());
        dictionaryItemMapper.add(dictionaryItem);
        return dictionaryItem;
    }

    @Override
    public List<DictionaryItemListVo> listAll(DictionaryItemQuery qo) {
        return dictionaryItemMapper.query(qo);
    }

    @Override
    public DictionaryItemListVo queryOne(DictionaryItemQuery qo) {
        qo.setPageSize(1);
        List<DictionaryItemListVo> datas = dictionaryItemMapper.query(qo);
        return (((datas == null) || datas.isEmpty()) ? null : datas.get(0));
    }

    @Override
    public Pagination<DictionaryItemListVo> query(DictionaryItemQuery qo) {
        int rows = dictionaryItemMapper.count(qo);
        List<DictionaryItemListVo> datas = ((rows == 0) ? new ArrayList<DictionaryItemListVo>() : dictionaryItemMapper.query(qo));
        return new Pagination<DictionaryItemListVo>(rows, datas);
    }

    @Override
    public int count(DictionaryItemQuery qo) {
        return dictionaryItemMapper.count(qo);
    }

    @Override
    public DictionaryItem create(String name, Boolean enabled, Long dictionaryId) {
        this.checkAdd(name,enabled,dictionaryId);
        DictionaryItem dictionaryItem = new DictionaryItem();
        this.assign(dictionaryItem,name,enabled,dictionaryId);
        return this.add(dictionaryItem);
    }


    /**
     * 添加字典详情校验
     */
    private void checkAdd(String name, Boolean enabled, Long dictionaryId) {
        this.checkParam(name, enabled, dictionaryId);
        DictionaryItemQuery qo = new DictionaryItemQuery();
        qo.setName(name);
        qo.setDictionaryId(dictionaryId);
        int count = dictionaryItemMapper.count(qo);
        Assert.isTrue(count == 0,"字典详情名称重复");
    }

    @Override
    @Transactional
    public void update(DictionaryItem dictionaryItem, String name, Boolean enabled, Long dictionaryId) {
        this.checkUpdate(dictionaryItem, name, enabled, dictionaryId);
        this.assign(dictionaryItem,name,enabled,dictionaryId);
        this.update(dictionaryItem);
    }

    /**
     * 赋值
     */
    private void assign(DictionaryItem dictionaryItem, String name, Boolean enabled, Long dictionaryId) {
        dictionaryItem.setName(name);
        dictionaryItem.setEnabled(enabled);
        dictionaryItem.setDictionaryId(dictionaryId);
    }

    /**
     * 修改字典详情校验
     */
    private void checkUpdate(DictionaryItem dictionaryItem, String name, Boolean enabled, Long dictionaryId) {
        Assert.notNull(dictionaryItem,"必须提供字典");
        this.checkParam(name, enabled, dictionaryId);
        DictionaryItemQuery qo = new DictionaryItemQuery();
        qo.setName(name);
        qo.setDictionaryId(dictionaryId);
        int count = this.count(qo);
        Assert.isTrue(dictionaryItem.getName().equalsIgnoreCase(name)
                ||count == 0,"字典详情名称重复");
    }

    /**
     * 公共校验
     */
    private void checkParam(String name, Boolean enabled, Long dictionaryId) {
        Assert.notNull(name,"必须提供字典详情名称");
        Assert.notNull(enabled,"必须提供状态");
        Assert.notNull(dictionaryId,"必须提供父字典id");
    }

    @Override
    public List<? extends DictionaryItemListVo> listAll(Long[] dictionaryItemIds) {
        DictionaryItemQuery qo = new DictionaryItemQuery();
        qo.setIds(dictionaryItemIds);
        return listAll(qo);
    }

    @Override
    public List<DictionaryItemListVo> listAll() {
        DictionaryItemQuery qo = new DictionaryItemQuery();
        qo.setMaxPageSize();
        return listAll(qo);
    }

}
