package com.youruan.dentistry.core.frontdesk.domain.wxmenu;

import lombok.Getter;
import lombok.Setter;

/**
 * 微信公众号菜单view类型按钮
 */
@Getter
@Setter
public class ViewButton extends BasicButton {
    private String url;
}
