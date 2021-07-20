
package com.youruan.dentistry.core.backstage.mapper;

import com.youruan.dentistry.core.backstage.domain.Report;
import com.youruan.dentistry.core.backstage.query.ReportQuery;
import com.youruan.dentistry.core.backstage.vo.ExtendedReport;
import com.youruan.dentistry.core.backstage.vo.ReportRecordVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ReportMapper {

    /**
     * 获取单条记录
     */
    public Report get(Long id);
    /**
     * 修改
     */
    public int update(Report report);
    /**
     * 添加
     */
    public int add(Report report);
    /**
     * 删除
     */
    public int delete(Long id);
    /**
     * 根据查询条件，返回记录条目
     */
    public int count(ReportQuery qo);
    /**
     * 根据查询条件，返回记录列表
     */
    public List<ExtendedReport> query(ReportQuery qo);

    /**
     * 查询用户报告记录数
     */
    int countRecord(ReportQuery qo);

    /**
     * 返回用户报告数据
     */
    List<ReportRecordVo> record(ReportQuery qo);

    /**
     * 批量添加报告
     */
    void batchAdd(List<Report> reportList);
}
