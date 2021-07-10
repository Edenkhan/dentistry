package main.live;

import main.AbstractEntities;
import mate.Const;
import mate.ConstGroup;
import mate.Entity;
import mate.Property;

public class UserEntities extends AbstractEntities {

    static Entity RegisteredUser;

    public UserEntities() {
        RegisteredUser = RegisteredUser();
        addEntity(RegisteredUser);
    }

    public Entity RegisteredUser() {
        Entity entity = new Entity("RegisteredUser", "ru", "注册的用户");
        entity.setProperties(
                new Property("String", "no", "用户编号").setLikeSearchable(true).setUniqueIndex(true).setUpdate(true).setSearchable(true),
                new Property("String", "phoneNumber", "手机号").setSearchable(true).setUpdate(true).setLikeSearchable(true),
                new Property("String", "avatar", "头像").setUpdate(true),
                new Property("Boolean", "visitor", "是否访客").setUpdate(true),
                new Property("Boolean", "counselor", "是否咨询师").setUpdate(true),
                new Property("Boolean", "locked", "是否已锁定").setSearchable(true).setUpdate(true));
        entity.setConstGroups(new ConstGroup(ConstGroup.METHOD_TYPE, "gender", "性别",
                new Const("MALE", "男"),
                new Const("FEMALE", "女")).setUpdate(true));
        return entity;
    }

    @Override
    public String getModuleName() {
        return "user";
    }
}
