
package com.youruan.dentistry.core.backstage.mapper;

import com.youruan.dentistry.core.backstage.domain.DictionaryItem;
import com.youruan.dentistry.core.backstage.query.DictionaryItemQuery;
import com.youruan.dentistry.core.backstage.vo.ExtendedDictionaryItem;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface DictionaryItemMapper {

    /**
     * 获取单条记录
     */
    public DictionaryItem get(Long id);
    /**
     * 修改
     */
    public int update(DictionaryItem dictionaryItem);
    /**
     * 添加
     */
    public int add(DictionaryItem dictionaryItem);
    /**
     * 删除
     */
    public int delete(Long id);
    /**
     * 根据查询条件，返回记录条目
     */
    public int count(DictionaryItemQuery qo);
    /**
     * 根据查询条件，返回记录列表
     */
    public List<ExtendedDictionaryItem> query(DictionaryItemQuery qo);

}
