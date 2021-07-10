
package com.youruan.dentistry.core.backstage.service.impl;

import com.youruan.dentistry.core.backstage.domain.Report;
import com.youruan.dentistry.core.backstage.mapper.ReportMapper;
import com.youruan.dentistry.core.backstage.query.ReportQuery;
import com.youruan.dentistry.core.backstage.service.ReportService;
import com.youruan.dentistry.core.backstage.vo.ExtendedReport;
import com.youruan.dentistry.core.base.exception.OptimismLockingException;
import com.youruan.dentistry.core.base.query.Pagination;
import com.youruan.dentistry.core.base.storage.DiskFileStorage;
import com.youruan.dentistry.core.base.storage.UploadFile;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BasicReportService
        implements ReportService {

    private final ReportMapper reportMapper;
    private final DiskFileStorage diskFileStorage;

    public BasicReportService(ReportMapper reportMapper, DiskFileStorage diskFileStorage) {
        this.reportMapper = reportMapper;
        this.diskFileStorage = diskFileStorage;
    }

    @Override
    public Report get(Long id) {
        return reportMapper.get(id);
    }

    protected void update(Report report) {
        int affected = reportMapper.update(report);
        if (affected == 0) {
            throw new OptimismLockingException("version!!");
        }
        report.setVersion((report.getVersion() + 1));
    }

    protected Report add(Report report) {
        report.setCreatedDate(new Date());
        reportMapper.add(report);
        return report;
    }

    @Override
    public List<ExtendedReport> listAll(ReportQuery qo) {
        return reportMapper.query(qo);
    }

    @Override
    public ExtendedReport queryOne(ReportQuery qo) {
        qo.setPageSize(1);
        List<ExtendedReport> datas = reportMapper.query(qo);
        return (((datas == null) || datas.isEmpty()) ? null : datas.get(0));
    }

    @Override
    public Pagination<ExtendedReport> query(ReportQuery qo) {
        int rows = reportMapper.count(qo);
        List<ExtendedReport> datas = ((rows == 0) ? new ArrayList<>() : reportMapper.query(qo));
        return new Pagination<>(rows, datas);
    }

    @Override
    public int count(ReportQuery qo) {
        return reportMapper.count(qo);
    }

    @Override
    public Report create(Long userId, String path) {
        Assert.notNull(userId,"必须提供用户id");
        Report report = new Report();
        return add(report);
    }

    @Override
    public void update(Report report, String name, String mark) {
        update(report);
    }


    @Override
    public List<? extends Report> listAll(Long[] reportIds) {
        ReportQuery qo = new ReportQuery();
        qo.setIds(reportIds);
        return listAll(qo);
    }

    @Override
    public List<ExtendedReport> listAll() {
        ReportQuery qo = new ReportQuery();
        qo.setMaxPageSize();
        return listAll(qo);
    }

    @Override
    public String upload(MultipartFile file) {
        try {
            UploadFile uploadFile = new UploadFile();
            uploadFile.setInputStream(file.getInputStream());
            uploadFile.setOriginalFilename(file.getOriginalFilename());
            return diskFileStorage.store(uploadFile,"report");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
