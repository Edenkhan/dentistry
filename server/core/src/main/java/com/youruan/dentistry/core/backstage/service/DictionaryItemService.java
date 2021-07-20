
package com.youruan.dentistry.core.backstage.service;

import com.youruan.dentistry.core.base.query.Pagination;
import com.youruan.dentistry.core.backstage.domain.DictionaryItem;
import com.youruan.dentistry.core.backstage.query.DictionaryItemQuery;
import com.youruan.dentistry.core.backstage.vo.ExtendedDictionaryItem;

import java.util.List;

public interface DictionaryItemService {

    /**
     * 根据id，获取单条记录
     */
    public DictionaryItem get(Long id);
    /**
     * 根据条件，获取单条记录
     */
    public ExtendedDictionaryItem queryOne(DictionaryItemQuery qo);
    /**
     * 返回所有记录
     */
    public List<ExtendedDictionaryItem> listAll(DictionaryItemQuery qo);
    /**
     * 根据条件，查询列表
     */
    public Pagination<ExtendedDictionaryItem> query(DictionaryItemQuery qo);
    /**
     * 根据查询条件，返回记录条目
     */
    public int count(DictionaryItemQuery qo);
    /**
     * 添加
     */
    DictionaryItem create(String name, Boolean enabled, Long dictionaryId);
    /**
     * 修改
     */
    void update(DictionaryItem dictionaryItem, String name, Boolean enabled, Long dictionaryId);
    /**
     * 根据id集合，查询对应列表
     */
    List<? extends ExtendedDictionaryItem> listAll(Long[] dictionaryItemIds);
    /**
     * 返回所有记录
     */
    List<ExtendedDictionaryItem> listAll();

    /**
     * 根据名称获取字典详情
     */
    ExtendedDictionaryItem getByName(String dicItemName);

    /**
     * 改变状态
     */
    void changeEnabled(DictionaryItem dictionaryItem);
}
