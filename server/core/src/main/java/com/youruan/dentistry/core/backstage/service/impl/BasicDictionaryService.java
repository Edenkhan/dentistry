
package com.youruan.dentistry.core.backstage.service.impl;

import com.youruan.dentistry.core.backstage.domain.Dictionary;
import com.youruan.dentistry.core.backstage.mapper.DictionaryMapper;
import com.youruan.dentistry.core.backstage.query.DictionaryQuery;
import com.youruan.dentistry.core.backstage.service.DictionaryService;
import com.youruan.dentistry.core.backstage.vo.ExtendedDictionary;
import com.youruan.dentistry.core.base.exception.OptimismLockingException;
import com.youruan.dentistry.core.base.query.Pagination;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BasicDictionaryService
        implements DictionaryService {

    private final DictionaryMapper dictionaryMapper;

    public BasicDictionaryService(DictionaryMapper dictionaryMapper) {
        this.dictionaryMapper = dictionaryMapper;
    }

    @Override
    public Dictionary get(Long id) {
        return dictionaryMapper.get(id);
    }

    protected void update(Dictionary dictionary) {
        int affected = dictionaryMapper.update(dictionary);
        if (affected == 0) {
            throw new OptimismLockingException("version!!");
        }
        dictionary.setVersion((dictionary.getVersion() + 1));
    }

    protected Dictionary add(Dictionary dictionary) {
        dictionary.setCreatedDate(new Date());
        dictionaryMapper.add(dictionary);
        return dictionary;
    }

    @Override
    public List<ExtendedDictionary> listAll(DictionaryQuery qo) {
        return dictionaryMapper.query(qo);
    }

    @Override
    public ExtendedDictionary queryOne(DictionaryQuery qo) {
        qo.setPageSize(1);
        List<ExtendedDictionary> datas = dictionaryMapper.query(qo);
        return (((datas == null) || datas.isEmpty()) ? null : datas.get(0));
    }

    @Override
    public Pagination<ExtendedDictionary> query(DictionaryQuery qo) {
        int rows = dictionaryMapper.count(qo);
        List<ExtendedDictionary> datas = ((rows == 0) ? new ArrayList<>() : dictionaryMapper.query(qo));
        return new Pagination<>(rows, datas);
    }

    @Override
    public int count(DictionaryQuery qo) {
        return dictionaryMapper.count(qo);
    }

    @Override
    public Dictionary create(String name, String mark) {
        this.checkAdd(name,mark);
        Dictionary dictionary = new Dictionary();
        this.assign(dictionary, name, mark);
        return add(dictionary);
    }

    @Override
    public void update(Dictionary dictionary, String name, String mark) {
        this.checkUpdate(dictionary, name, mark);
        this.assign(dictionary, name, mark);
        update(dictionary);
    }

    /**
     * 添加字典校验
     */
    private void checkAdd(String name, String mark) {
        this.checkParam(name, mark);
        DictionaryQuery qo = new DictionaryQuery();
        qo.setName(name);
        int count = dictionaryMapper.count(qo);
        Assert.isTrue(count == 0,"字典名称重复");
        qo = new DictionaryQuery();
        qo.setMark(mark);
        count = dictionaryMapper.count(qo);
        Assert.isTrue(count == 0,"字典标识重复");
    }

    /**
     * 修改字典校验
     */
    private void checkUpdate(Dictionary dictionary, String name, String mark) {
        Assert.notNull(dictionary,"必须提供字典");
        this.checkParam(name, mark);
        DictionaryQuery qo = new DictionaryQuery();
        qo.setName(name);
        int count = dictionaryMapper.count(qo);
        Assert.isTrue(dictionary.getName().equalsIgnoreCase(name)||count == 0,"字典名称重复");
        qo = new DictionaryQuery();
        qo.setMark(mark);
        count = dictionaryMapper.count(qo);
        Assert.isTrue(dictionary.getMark().equalsIgnoreCase(mark)||count == 0,"字典标识重复");
    }

    private void checkParam(String name, String mark) {
        Assert.notNull(name, "必须提供字典名称");
        Assert.notNull(mark, "必须提供字典标识");
    }

    /**
     * 封装数据
     */
    private void assign(Dictionary dictionary, String name, String mark) {
        dictionary.setName(name);
        dictionary.setMark(mark);
    }

    @Override
    public List<? extends Dictionary> listAll(Long[] dictionaryIds) {
        DictionaryQuery qo = new DictionaryQuery();
        qo.setIds(dictionaryIds);
        return listAll(qo);
    }

    @Override
    public List<ExtendedDictionary> listAll() {
        DictionaryQuery qo = new DictionaryQuery();
        qo.setMaxPageSize();
        return listAll(qo);
    }
}
