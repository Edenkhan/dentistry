
package com.youruan.dentistry.core.backstage.service.impl;

import com.youruan.dentistry.core.backstage.domain.Product;
import com.youruan.dentistry.core.backstage.domain.Report;
import com.youruan.dentistry.core.backstage.mapper.ReportMapper;
import com.youruan.dentistry.core.backstage.query.ReportQuery;
import com.youruan.dentistry.core.backstage.service.ProductService;
import com.youruan.dentistry.core.backstage.service.ReportService;
import com.youruan.dentistry.core.backstage.vo.AppointRecordVo;
import com.youruan.dentistry.core.backstage.vo.ExtendedReport;
import com.youruan.dentistry.core.backstage.vo.ReportRecordVo;
import com.youruan.dentistry.core.base.exception.OptimismLockingException;
import com.youruan.dentistry.core.base.query.Pagination;
import com.youruan.dentistry.core.base.storage.DiskFileStorage;
import com.youruan.dentistry.core.base.storage.UploadFile;
import com.youruan.dentistry.core.base.utils.SnowflakeIdWorker;
import com.youruan.dentistry.core.frontdesk.domain.Appointment;
import com.youruan.dentistry.core.frontdesk.service.AppointmentService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
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
    private final AppointmentService appointmentService;
    private final ProductService productService;

    public BasicReportService(ReportMapper reportMapper, DiskFileStorage diskFileStorage, AppointmentService appointmentService, ProductService productService) {
        this.reportMapper = reportMapper;
        this.diskFileStorage = diskFileStorage;
        this.appointmentService = appointmentService;
        this.productService = productService;
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
    public Report create(Integer peopleNum, Long userId, Long appointId, Long productId, List<String> pathList) {
        this.checkAdd(peopleNum, userId, appointId, productId, pathList);
        Report report = new Report();
        this.assign(report, userId, appointId, productId, pathList);
        return this.add(report);
    }

    /**
     * 封装数据
     */
    private void assign(Report report, Long userId, Long appointId, Long productId, List<String> pathList) {
        report.setUserId(userId);
        report.setAppointId(appointId);
        report.setProductId(productId);
        report.setPath(String.join(",", pathList));
        report.setSync(false);
        report.setReportNo(SnowflakeIdWorker.getIdWorker());
    }

    /**
     * 添加报告校验
     */
    private void checkAdd(Integer peopleNum, Long userId, Long appointId, Long productId, List<String> pathList) {
        Assert.notNull(peopleNum, "必须提供团队人数");
        Assert.notNull(userId, "必须提供用户id");
        Assert.notNull(appointId, "必须提供预约id");
        Assert.notNull(productId, "必须提供产品id");
        ReportQuery qo = new ReportQuery();
        qo.setAppointId(appointId);
        int count = this.count(qo);
        Assert.isTrue(count == 0, "该预约已有报告，请勿重复添加");
        Assert.notNull(pathList, "必须提供报告路径");
        Assert.isTrue(pathList.size() >= peopleNum, "报告数量少于团队人数");
    }

    @Override
    public void update(Report report, Boolean sync) {
        this.checkUpdate(report, sync);
        report.setSync(sync);
        this.update(report);
    }

    private void checkUpdate(Report report, Boolean sync) {
        Assert.notNull(report, "必须提供报告");
        Assert.notNull(sync, "必须提供同步状态");
        Appointment appointment = appointmentService.get(report.getAppointId());
        Assert.isTrue(Appointment.STATE_FINISH.equals(appointment.getState()),"预约未完成");
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
    public String upload(MultipartFile file, String directory) {
        try {
            UploadFile uploadFile = new UploadFile();
            uploadFile.setInputStream(file.getInputStream());
            uploadFile.setOriginalFilename(file.getOriginalFilename());
            return diskFileStorage.store(uploadFile, directory);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Pagination<ReportRecordVo> record(ReportQuery qo) {
        int rows = reportMapper.countRecord(qo);
        List<ReportRecordVo> data = (rows == 0) ? new ArrayList<>() : reportMapper.record(qo);
        return new Pagination<>(rows, data);
    }

    @Override
    public AppointRecordVo getAppoint(Report report) {
        Assert.notNull(report,"必须提供报告");
        return appointmentService.getInfo(report.getAppointId());
    }

    @Override
    public void reset(Report report, List<String> pathList) {
        this.checkReset(report, pathList);
        report.setPath(String.join(",", pathList));
        this.update(report);
    }

    /**
     * 重新上传校验
     */
    private void checkReset(Report report, List<String> pathList) {
        Assert.notNull(report,"必须提供报告");
        Assert.isTrue(!report.getSync(),"该报告已同步，无法上传");
        Assert.isTrue(!CollectionUtils.isEmpty(pathList),"必须提供文件");
        Product product = productService.get(report.getProductId());
        Assert.notNull(product,"必须提供产品");
        Assert.isTrue(pathList.size()>=product.getPeopleNum(),"上传报告少于团队人数");
    }
}
