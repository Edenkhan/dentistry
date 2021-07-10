package com.youruan.dentistry.core.frontdesk.domain.wxmenu;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 公众号菜单按钮父类
 */
@Getter
@Setter
public class BasicButton {
    /**
     * 类型
     */
    private String type;
    /**
     * 名称
     */
    private String name;
    /**
     * 多级菜单
     */
    private List<BasicButton> sub_button;

}
