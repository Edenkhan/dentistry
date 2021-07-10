
package com.youruan.dentistry.core.backstage.service;

import com.youruan.dentistry.core.base.query.Pagination;
import com.youruan.dentistry.core.backstage.domain.Dictionary;
import com.youruan.dentistry.core.backstage.query.DictionaryQuery;
import com.youruan.dentistry.core.backstage.vo.ExtendedDictionary;

import java.util.List;

public interface DictionaryService {

    /**
     * 根据id，获取单条记录
     */
    public Dictionary get(Long id);
    /**
     * 根据条件，获取单条记录
     */
    public ExtendedDictionary queryOne(DictionaryQuery qo);
    /**
     * 返回所有记录
     */
    public List<ExtendedDictionary> listAll(DictionaryQuery qo);
    /**
     * 根据条件，查询列表
     */
    public Pagination<ExtendedDictionary> query(DictionaryQuery qo);
    /**
     * 根据查询条件，返回记录条目
     */
    public int count(DictionaryQuery qo);
    /**
     * 添加
     */
    Dictionary create(String name, String logo);
    /**
     * 修改
     */
    void update(Dictionary dictionary, String name, String logo);
    /**
     * 根据id集合，查询对应列表
     */
    List<? extends Dictionary> listAll(Long[] dictionaryIds);
    /**
     * 返回所有记录
     */
    List<ExtendedDictionary> listAll();
}
