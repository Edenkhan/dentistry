
package com.youruan.dentistry.core.backstage.mapper;

import com.youruan.dentistry.core.backstage.domain.Dictionary;
import com.youruan.dentistry.core.backstage.query.DictionaryQuery;
import com.youruan.dentistry.core.backstage.vo.ExtendedDictionary;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface DictionaryMapper {

    /**
     * 获取单条记录
     */
    public Dictionary get(Long id);
    /**
     * 修改
     */
    public int update(Dictionary dictionary);
    /**
     * 添加
     */
    public int add(Dictionary dictionary);
    /**
     * 删除
     */
    public int delete(Long id);
    /**
     * 根据查询条件，返回记录条目
     */
    public int count(DictionaryQuery qo);
    /**
     * 根据查询条件，返回记录列表
     */
    public List<ExtendedDictionary> query(DictionaryQuery qo);
}
