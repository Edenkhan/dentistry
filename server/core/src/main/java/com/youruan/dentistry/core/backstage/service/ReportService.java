
package com.youruan.dentistry.core.backstage.service;

import com.youruan.dentistry.core.backstage.domain.Report;
import com.youruan.dentistry.core.backstage.query.ReportQuery;
import com.youruan.dentistry.core.backstage.vo.ExtendedReport;
import com.youruan.dentistry.core.base.query.Pagination;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ReportService {

    /**
     * 根据id，获取单条记录
     */
    public Report get(Long id);
    /**
     * 根据条件，获取单条记录
     */
    public ExtendedReport queryOne(ReportQuery qo);
    /**
     * 返回所有记录
     */
    public List<ExtendedReport> listAll(ReportQuery qo);
    /**
     * 根据条件，查询列表
     */
    public Pagination<ExtendedReport> query(ReportQuery qo);
    /**
     * 根据查询条件，返回记录条目
     */
    public int count(ReportQuery qo);
    /**
     * 添加
     */
    Report create(Long userId, String path);
    /**
     * 修改
     */
    void update(Report report, String name, String logo);
    /**
     * 根据id集合，查询对应列表
     */
    List<? extends Report> listAll(Long[] reportIds);
    /**
     * 返回所有记录
     */
    List<ExtendedReport> listAll();

    /**
     * 上传报告
     */
    String upload(MultipartFile file);

}
