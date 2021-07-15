
package com.youruan.dentistry.core.backstage.mapper;

import com.youruan.dentistry.core.backstage.domain.RedeemCode;
import com.youruan.dentistry.core.backstage.query.RedeemCodeQuery;
import com.youruan.dentistry.core.backstage.vo.ExtendedRedeemCode;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RedeemCodeMapper {

    /**
     * 根据id，获取单条记录
     */
    public RedeemCode get(Long id);
    /**
     * 修改
     */
    public int update(RedeemCode dictionary);
    /**
     * 添加
     */
    public int add(RedeemCode dictionary);
    /**
     * 删除
     */
    public int delete(Long id);
    /**
     * 根据查询条件，返回记录条目
     */
    public int count(RedeemCodeQuery qo);
    /**
     * 根据查询条件，返回记录列表
     */
    public List<ExtendedRedeemCode> query(RedeemCodeQuery qo);

    /**
     * 批量添加兑换码
     */
    void batchAdd(List<RedeemCode> redeemCodeList);
}
