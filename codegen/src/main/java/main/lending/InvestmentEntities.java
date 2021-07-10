package main.lending;

import main.AbstractEntities;
import mate.Const;
import mate.ConstGroup;
import mate.Entity;
import mate.Property;

import java.math.BigDecimal;
import java.util.Date;

public class InvestmentEntities extends AbstractEntities {

    static Entity InvestmentProduct;

    public InvestmentEntities() {
        addEntity(InvestmentProduct = InvestmentProduct());
        addEntity(InvestmentProductBid());
        addEntity(InvestmentProductBidInvalid());
    }

    private Entity InvestmentProduct() {
        Entity entity = new Entity("InvestmentProduct", "ip", "投资产品");
        entity.setProperties(
                new Property("String", "no", "编号").setUpdate(true).setLikeSearchable(true).setUniqueIndex(true).setSearchable(true),
                new Property("Long", "investmentManagerId", "直投经理").setUniqueIndex(true).setSearchable(true).setJoin(UserEntities.RegisteredUser, "id", "realName"),
                new Property(BigDecimal.class, "bidAmount", "总募集金额").setUpdate(true),
                new Property("Integer", "bidCount", "总募集次数").setUpdate(true),
                new Property(BigDecimal.class, "loanAmount", "总出借金额").setUpdate(true),
                new Property("Integer", "loanCount", "总出借次数").setUpdate(true),
                new Property(BigDecimal.class, "canLoanAmount", "可出借金额").setUpdate(true),
                new Property("Boolean", "locked", "是否已锁定").setUpdate(true).setSearchable(true));
        return entity;
    }

    private Entity InvestmentProductBid() {
        Entity entity = new Entity("InvestmentProductBid", "ipb", "投资产品募集记录");
        entity.setProperties(
                new Property("String", "no", "编号").setUpdate(true).setLikeSearchable(true).setUniqueIndex(true).setSearchable(true),
                new Property("Long", "investmentProductId", "投资产品").setIndex(true).setSearchable(true).setJoin(InvestmentProduct, "id", "no"),
                new Property("Long", "investmentManagerId", "直投经理").setIndex(true).setSearchable(true).setJoin(UserEntities.RegisteredUser, "id", "realName"),
                new Property("Long", "investorId", "委托出借人").setIndex(true).setSearchable(true).setJoin(UserEntities.RegisteredUser, "id", "realName"),
                new Property(Date.class, "bidDate", "投资时间").setUpdate(true).setIndex(true).setSortable(true).setLikeSearchable(true),
                new Property(Date.class, "pendingSettleDate", "申请结算时间").setUpdate(true),
                new Property(Date.class, "settledDate", "结算时间").setUpdate(true),
                new Property(BigDecimal.class, "bidAmount", "投资金额").setUpdate(true).setSortable(true).setIndex(true),
                new Property(BigDecimal.class, "apr", "年化率").setUpdate(true).setHighPrecision(true).setSearchable(true),
                new Property(BigDecimal.class, "interest", "已产生收益").setUpdate(true).setSortable(true),
                new Property("Integer", "calculationDays", "计息天数").setUpdate(true),
                new Property(Date.class, "lastCalculationDate", "最后计息时间").setUpdate(true).setIndex(true).setLikeSearchable(true));
        entity.setConstGroups(
                new ConstGroup(ConstGroup.METHOD_TYPE, "state", "状态",
                        new Const("INVESTING", "投资中"),
                        new Const("PENDING_SETTLE", "结算中"),
                        new Const("SETTLED", "已结算"),
                        new Const("INVALID", "已无效")).setIndex(true).setUpdate(true));
        return entity;
    }

    private Entity InvestmentProductBidInvalid() {
        Entity entity = new Entity("InvestmentProductBidInvalid", "ipbi", "投资产品募集无效记录");
        entity.setProperties(
                new Property("Long", "investmentProductBidId", "投资记录").setUniqueIndex(true).setSearchable(true),
                new Property("String", "note", "备注").setLarge(true),
                new Property("Long", "employeeId", "操作员工")
        );
        return entity;
    }

    @Override
    public String getModuleName() {
        return "investment";
    }
}
