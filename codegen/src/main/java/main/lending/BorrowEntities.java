package main.lending;

import main.AbstractEntities;
import mate.Const;
import mate.ConstGroup;
import mate.Entity;
import mate.Property;

import java.math.BigDecimal;
import java.util.Date;

public class BorrowEntities extends AbstractEntities {

    static Entity PaymentMethod;
    static Entity Borrow;

    public BorrowEntities() {
        PaymentMethod = PaymentMethod();
        Borrow = Borrow();
        addEntity(PaymentMethod);
        addEntity(Borrow);
        addEntity(UserBorrow());
        addEntity(PaymentSchedule());
        Entity BorrowRequestFile = BorrowRequestFile();
        addEntity(BorrowRequestFile);
        addEntity(BorrowRequest(BorrowRequestFile));
        addEntity(BorrowTelephoneReview());
    }

    public Entity UserBorrow() {
        Entity entity = new Entity("UserBorrow", "ub", "借款用户");
        entity.setProperties(
                new Property("Long", "userId", "用户").setSearchable(true).setUniqueIndex(true),
                new Property(BigDecimal.class, "remainderPrincipal", "剩余待还本金").setUpdate(true));
        return entity;
    }

    public Entity PaymentMethod() {
        Entity entity = new Entity("PaymentMethod", "pm", "还款方式");
        entity.setProperties(
                new Property("String", "name", "名称").setUpdate(true).setSearchable(true).setLikeSearchable(true),
                new Property(BigDecimal.class, "apr", "年化率").setUpdate(true).setHighPrecision(true),
                new Property("Integer", "term", "还款周期").setUpdate(true),
                new Property(BigDecimal.class, "lowerRate", "低额还款比例").setUpdate(true).setHighPrecision(true),
                new Property("Integer", "lowerMonths", "低额还款总期数").setUpdate(true),
                new Property("Integer", "highMonths", "正常还款总期数").setUpdate(true));
        entity.setConstGroups(
                new ConstGroup(ConstGroup.METHOD_TYPE, "state", "状态",
                        new Const("PENDING_REVIEW", "待发布"),
                        new Const("AVAILABLE", "可用")).setIndex(true).setUpdate(true));
        return entity;
    }

    private Entity BorrowRequestFile() {
        Entity entity = new Entity("BorrowRequestFile", "brf", "借款申请文件");
        entity.setProperties(
                new Property("String", "phoneNumber", "上传者"),
                new Property("String", "name", "文件名").setSearchable(true),
                new Property("String", "extension", "扩展名").setSearchable(true),
                new Property("Long", "size", "大小"),
                new Property("String", "path", "路径"));
        entity.setConstGroups(
                new ConstGroup(ConstGroup.METHOD_TYPE, "type", "类型",
                        new Const("ID_CARD_FRONT", "身份证正面照片"),
                        new Const("ID_CARD_BACK", "身份证反面照片"),
                        new Const("ID_CARD_IN_HAND", "手持身份证照片")),
                new ConstGroup(ConstGroup.METHOD_MOD, "flags", "标识",
                        new Const("REFERENCED", "已被引用")).setUpdate(true));
        return entity;
    }

    public Entity BorrowRequest(Entity BorrowRequestFile) {
        Entity entity = new Entity("BorrowRequest", "br", "借款申请");
        entity.setProperties(
                new Property("String", "phoneNumber", "手机号").setSearchable(true).setIndex(true),
                new Property("String", "realName", "姓名").setSearchable(true).setUpdate(true).setUpdate(true),
                new Property("String", "idCardNo", "身份证号").setSearchable(true).setIndex(true).setUpdate(true).setUpdate(true),
                new Property("Long", "idCardFrontId", "身份证正面").setUpdate(true).setJoin(BorrowRequestFile, "id", "path"),
                new Property("Long", "idCardBackId", "身份证反面").setUpdate(true).setJoin(BorrowRequestFile, "id", "path"),
                new Property("Long", "idCardInHandId", "手持身份证").setUpdate(true).setJoin(BorrowRequestFile, "id", "path"),
                new Property("String", "nativePlace", "籍贯住址").setUpdate(true),
                new Property("String", "currentAddress", "现居住址").setUpdate(true),
                new Property("String", "weChatNo", "微信号").setUpdate(true),
                new Property("String", "qqNo", "QQ 号").setUpdate(true),
                new Property("String", "educationBackground", "学历").setUpdate(true),
                new Property("String", "graduateSchool", "毕业院校名称").setUpdate(true),
                new Property("String", "incomeStatus", "收入情况").setUpdate(true),
                new Property("String", "indebtedStatus", "负债情况").setUpdate(true),
                new Property("String", "firstContactRelation", "第一联系人关系").setUpdate(true),
                new Property("String", "firstContactRealName", "第一联系人姓名").setUpdate(true),
                new Property("String", "firstContactPhoneNumber", "第一联系人联系电话").setUpdate(true),
                new Property("String", "firstContactAddress", "第一联系人长期居住住址").setUpdate(true),
                new Property("String", "secondContactRelation", "第二联系人关系").setUpdate(true),
                new Property("String", "secondContactRealName", "第二联系人姓名").setUpdate(true),
                new Property("String", "secondContactPhoneNumber", "第二联系人联系电话").setUpdate(true),
                new Property("Long", "institutionId", "机构").setUpdate(true).setJoin(UserEntities.Institution, "id", "name"),
                new Property("String", "courseName", "课程名称").setUpdate(true),
                new Property(Date.class, "courseStartDate", "开课时间").setUpdate(true),
                new Property("Long", "paymentMethodId", "还款方案").setUpdate(true).setJoin(PaymentMethod, "id", "name"),
                new Property(BigDecimal.class, "principal", "借款金额").setUpdate(true),
                new Property("Long", "reviewerId", "审核人").setUpdate(true).setJoin(PlatformEntities.Employee, "id", "realName"),
                new Property(Date.class, "reviewDate", "审核时间").setUpdate(true),
                new Property("String", "note", "备注").setUpdate(true)
        );
        entity.setConstGroups(
                new ConstGroup(ConstGroup.METHOD_TYPE, "state", "状态",
                        new Const("PENDING_REVIEW", "待审核"),
                        new Const("PASSED", "审核通过"),
                        new Const("REJECTED", "审核拒绝")).setIndex(true).setUpdate(true));
        return entity;
    }

    public Entity Borrow() {
        Entity entity = new Entity("Borrow", "b", "借款");
        entity.setProperties(
                new Property("String", "no", "编号").setSearchable(true).setUniqueIndex(true).setUpdate(true),
                new Property("Long", "investmentManagerId", "直投经理").setSearchable(true).setIndex(true).setUpdate(true).setJoin(UserEntities.RegisteredUser, "id", "realName"),
                new Property("Long", "borrowerId", "借款人").setUpdate(true).setSearchable(true).setIndex(true).setJoin(UserEntities.RegisteredUser, "id", "realName"),
                new Property("Long", "employeeId", "创建人").setSearchable(true).setIndex(true).setJoin(PlatformEntities.Employee, "id", "username", "realName"),
                new Property("Long", "institutionId", "机构").setUpdate(true).setSearchable(true).setIndex(true).setJoin(UserEntities.Institution, "id", "name"),
                new Property("Long", "paymentMethodId", "还款方案").setUpdate(true).setSearchable(true).setIndex(true).setJoin(PaymentMethod, "id", "name"),
                new Property(BigDecimal.class, "principal", "本金").setUpdate(true).setSortable(true),
                new Property("Integer", "term", "期限").setUpdate(true),
                new Property(BigDecimal.class, "apr", "年利率").setUpdate(true),
                new Property("Boolean", "currentOverdue", "当前有逾期").setUpdate(true).setSearchable(true),
                new Property(Date.class, "requestDate", "申请借款时间").setUpdate(true).setLikeSearchable(true),
                new Property(Date.class, "confirmDate", "确认放款时间").setUpdate(true).setLikeSearchable(true),
                new Property("String", "confirmNote", "放款备注").setUpdate(true).setLarge(true),
                new Property("Long", "confirmerId", "放款员工").setUpdate(true).setJoin(PlatformEntities.Employee, "id", "username", "realName"),
                new Property(Date.class, "startDate", "放款时间").setUpdate(true).setIndex(true).setLikeSearchable(true).setSortable(true),
                new Property("Long", "reviewerId", "审核员工").setUpdate(true).setJoin(PlatformEntities.Employee, "id", "username", "realName"),
                new Property(Date.class, "reviewDate", "审核时间").setUpdate(true),
                new Property("String", "reviewNote", "审核备注").setUpdate(true).setLarge(true),
                new Property("Long", "riskReviewerId", "审核员工").setUpdate(true).setJoin(PlatformEntities.Employee, "id", "username", "realName"),
                new Property(Date.class, "riskReviewDate", "审核时间").setUpdate(true),
                new Property("String", "riskReviewNote", "审核备注").setUpdate(true).setLarge(true),
                new Property(Date.class, "invalidDate", "无效操作时间").setUpdate(true),
                new Property("Long", "invalidOperatorId", "无效操作人").setUpdate(true).setJoin(PlatformEntities.Employee, "id", "username", "realName"),
                new Property("String", "invalidNote", "无效备注").setUpdate(true),
                new Property("String", "allPayOffNote", "一次性还清备注").setUpdate(true));
        entity.setConstGroups(
                new ConstGroup(ConstGroup.METHOD_TYPE, "state", "状态",
                        new Const("PENDING_REVIEW", "待电话审核"),
                        new Const("PENDING_RETURN", "还款中"),
                        new Const("RETURNED", "已还清"),
                        new Const("CANCELLED", "已取消"),
                        new Const("PENDING_CONFIRM", "待放款"),
                        new Const("INVALID", "已无效"),
                        new Const("PENDING_RISK_REVIEW", "待风控审核")).setIndex(true).setUpdate(true),
                new ConstGroup(ConstGroup.METHOD_MOD, "flags", "标识",
                        new Const("FROM_HXJX", "鸿学转"),
                        new Const("TELEPHONE_REVIEW_PASS", "电话审核通过"),
                        new Const("TELEPHONE_REVIEW_REJECT", "电话审核拒绝"),
                        new Const("RISK_REVIEW_PASS", "风控审核通过"),
                        new Const("RISK_REVIEW_REJECT", "风控审核通过"),
                        new Const("ALL_PAY_OFF", "一次性还清")).setUpdate(true));
        return entity;
    }

    public Entity BorrowTelephoneReview() {
        Entity entity = new Entity("BorrowTelephoneReview", "btr", "借款电话审核");
        entity.setProperties(
                new Property("Long", "borrowId", "借款").setUpdate(true).setSearchable(true).setIndex(true),
                new Property("String", "manner", "态度"),
                new Property("String", "knowBorrow", "借款信息清楚情况"),
                new Property("String", "knowObligation", "责任与义务清楚情况"),
                new Property("String", "note", "审核员备注").setLarge(true),
                new Property("Long", "reviewerId", "审核人").setUpdate(true).setJoin(PlatformEntities.Employee, "id", "realName")
        );
        return entity;
    }

    public Entity PaymentSchedule() {
        Entity entity = new Entity("PaymentSchedule", "ps", "还款计划");
        entity.setBatchInsert(true);
        entity.setProperties(
                new Property("Long", "borrowId", "借款").setSearchable(true).setIndex(true).setJoin(Borrow, "id", "no"),
                new Property("Long", "borrowerId", "借款人").setSearchable(true).setIndex(true).setJoin(UserEntities.RegisteredUser, "id", "realName"),
                new Property("Long", "investmentManagerId", "直投经理").setSearchable(true).setIndex(true).setJoin(UserEntities.RegisteredUser, "id", "realName"),
                new Property("Integer", "termIndex", "期数").setSortable(true).setSearchable(true),
                new Property(Date.class, "deadline", "还款截止日").setIndex(true, "overdueLastCheckDate").setSortable(true).setLikeSearchable(true),
                new Property(BigDecimal.class, "principal", "本金"),
                new Property(BigDecimal.class, "interest", "利息"),
                new Property(BigDecimal.class, "lateFee", "滞纳金").setUpdate(true),
                new Property(BigDecimal.class, "returnedTotal", "已还总额").setUpdate(true),
                new Property(Date.class, "returnedDate", "还款时间").setUpdate(true).setIndex(true).setLikeSearchable(true).setSortable(true),
                new Property("Boolean", "overdue", "逾期").setIndex(true).setSearchable(true).setUpdate(true),
                new Property("Integer", "overdueDays", "逾期天数").setUpdate(true),
                new Property(Date.class, "overdueLastCheckDate", "最后检测时间").setUpdate(true).setLikeSearchable(true));
        entity.setConstGroups(
                new ConstGroup(ConstGroup.METHOD_TYPE, "state", "状态",
                        new Const("PENDING_RETURN", "待还"),
                        new Const("RETURNED", "已还"),
                        new Const("INVALID", "无效")).setIndex(true).setUpdate(true),
                new ConstGroup(ConstGroup.METHOD_MOD, "flags", "标识",
                        new Const("RETURNED_BY_MANAGER", "由直投经理标记已还"),
                        new Const("ALL_PAY_OFF", "一次性还清")).setUpdate(true));
        return entity;
    }

    @Override
    public String getModuleName() {
        return "borrow";
    }
}
