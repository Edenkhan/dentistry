package main.lending;

import main.AbstractEntities;
import mate.Const;
import mate.ConstGroup;
import mate.Entity;
import mate.Property;

public class UserEntities extends AbstractEntities {

    static Entity RegisteredUser;
    static Entity Institution;

    public UserEntities() {
        RegisteredUser = RegisteredUser();
        Institution = Institution();
        addEntity(RegisteredUser);
        addEntity(UserContact());
        addEntity(WeChatUser());
        addEntity(Institution);
        addEntity(UserAccount(RegisteredUser));
        addEntity(InstitutionCourse());
    }

    public Entity UserContact() {
        Entity entity = new Entity("UserContact", "uc", "用户联系信息");
        entity.setProperties(
                new Property("Long", "userId", "用户").setSearchable(true).setUniqueIndex(true),
                new Property("String", "firstRealName", "第一联系人姓名").setUpdate(true),
                new Property("String", "firstPhoneNumber", "第一联系人电话").setUpdate(true),
                new Property("String", "firstAddress", "第一联系人住址").setUpdate(true),
                new Property("String", "secondRealName", "第二联系人姓名").setUpdate(true),
                new Property("String", "secondPhoneNumber", "第二联系人电话").setUpdate(true),
                new Property("String", "secondAddress", "第二联系人住址").setUpdate(true)
        );
        return entity;
    }

    public Entity WeChatUser() {
        Entity entity = new Entity("WeChatUser", "wcu", "微信用户");
        entity.setProperties(
                new Property("String", "openId", "OpenId").setUniqueIndex(true).setSearchable(true),
                new Property("String", "unionId", "UnionId").setUniqueIndex(true).setSearchable(true),
                new Property("Long", "userId", "用户").setUniqueIndex(true).setUpdate(true).setSearchable(true).setJoin(RegisteredUser, "id", "avatar"),
                new Property("String", "avatar", "头像").setUpdate(true),
                new Property("String", "nickname", "昵称").setUpdate(true),
                new Property("Integer", "sex", "性别").setUpdate(true)
        );
        return entity;
    }

    private Entity UserAccount(Entity RegisteredUser) {
        Entity entity = new Entity("UserBank", "ua", "用户银行卡信息");
        entity.setProperties(
                new Property("Long", "userId", "用户").setSearchable(true).setUniqueIndex(true).setJoin(RegisteredUser, "id", "avatar"),
                new Property("String", "bankName", "银行名").setSearchable(true).setUpdate(true),
                new Property("String", "bankAbbr", "银行简称").setSearchable(true).setUpdate(true),
                new Property("String", "bankAccount", "银行账号").setSearchable(true).setUpdate(true),
                new Property("String", "bankBranch", "银行支行").setUpdate(true));
        return entity;
    }

    public Entity RegisteredUser() {
        Entity entity = new Entity("RegisteredUser", "ru", "注册的用户");
        entity.setProperties(
                new Property("String", "no", "用户编号").setLikeSearchable(true).setUniqueIndex(true).setUpdate(true).setSearchable(true),
                new Property("String", "phoneNumber", "手机号").setSearchable(true).setUpdate(true).setLikeSearchable(true),
                new Property("String", "realName", "姓名").setUpdate(true).setSearchable(true).setIndex(true).setLikeSearchable(true),
                new Property("String", "avatar", "头像").setUpdate(true),
                new Property("String", "idCardNumber", "身份证号码").setUpdate(true).setSearchable(true).setIndex(true).setLikeSearchable(true),
                new Property("String", "weChatNo", "微信号").setUpdate(true),
                new Property("String", "qqNo", "QQ号").setUpdate(true),
                new Property("String", "address", "住址").setUpdate(true),
                new Property("String", "note", "备注").setUpdate(true).setLarge(true),
                new Property("Boolean", "locked", "是否已锁定").setSearchable(true).setUpdate(true));
        entity.setConstGroups(
                new ConstGroup(ConstGroup.METHOD_TYPE, "type", "用户类型",
                        new Const("INVESTMENT_MANAGER", "直投经理"),
                        new Const("INVESTOR", "委托出借人"),
                        new Const("BORROWER", "借款用户")).setIndex(true).setUpdate(true),
                new ConstGroup(ConstGroup.METHOD_TYPE, "gender", "性别",
                        new Const("MALE", "男"),
                        new Const("FEMALE", "女")).setUpdate(true));
        return entity;
    }

    public Entity InstitutionCourse() {
        Entity entity = new Entity("InstitutionCourse", "ic", "机构课程");
        entity.setProperties(
                new Property("Long", "institutionId", "机构").setUpdate(true),
                new Property("String", "name", "名称").setUpdate(true));
        return entity;
    }

    public Entity Institution() {
        Entity entity = new Entity("Institution", "i", "机构");
        entity.setProperties(
                new Property("String", "code", "机构代码").setUniqueIndex(true).setUpdate(true).setLikeSearchable(true).setSearchable(true),
                new Property("String", "name", "名称").setUpdate(true).setLikeSearchable(true).setLikeSearchable(true),
                new Property("String", "contactPhoneNumber", "联系人手机号").setUpdate(true).setIndex(true).setLikeSearchable(true),
                new Property("String", "contactRealName", "联系人姓名").setUpdate(true).setLikeSearchable(true).setLikeSearchable(true),
                new Property("String", "bankName", "银行名").setUpdate(true),
                new Property("String", "bankAbbr", "银行简称").setUpdate(true),
                new Property("String", "bankAccountName", "银行账户名").setUpdate(true),
                new Property("String", "bankAccount", "银行账号").setSearchable(true).setUpdate(true));
        return entity;
    }

    @Override
    public String getModuleName() {
        return "user";
    }
}
