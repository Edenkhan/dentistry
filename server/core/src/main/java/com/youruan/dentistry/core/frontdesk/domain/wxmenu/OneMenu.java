package com.youruan.dentistry.core.frontdesk.domain.wxmenu;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 一级菜单
 */
@Getter
@Setter
public class OneMenu {
    /**
     * 名称
     */
    private String name;
    /**
     * 二级菜单
     */
    private List<BasicButton> sub_button;
}
