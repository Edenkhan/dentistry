
package com.youruan.dentistry.core.backstage.mapper;

import com.youruan.dentistry.core.backstage.domain.AppointManage;
import com.youruan.dentistry.core.backstage.query.AppointManageQuery;
import com.youruan.dentistry.core.backstage.vo.ExtendedAppointManage;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AppointManageMapper {

    /**
     * 获取单条记录
     */
    public AppointManage get(Long id);
    /**
     * 修改
     */
    public int update(AppointManage appointManage);
    /**
     * 添加
     */
    public int add(AppointManage appointManage);
    /**
     * 删除
     */
    public int delete(Long id);
    /**
     * 根据查询条件，返回记录条目
     */
    public int count(AppointManageQuery qo);
    /**
     * 根据查询条件，返回记录列表
     */
    public List<ExtendedAppointManage> query(AppointManageQuery qo);

    /**
     * 批量添加
     */
    void addBatch(List<AppointManage> list);

    /**
     * 修改预约上限
     */
    int updateTopLimit(AppointManage appointManage);


}
