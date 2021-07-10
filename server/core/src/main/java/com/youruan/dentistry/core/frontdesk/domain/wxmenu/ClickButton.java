package com.youruan.dentistry.core.frontdesk.domain.wxmenu;

import lombok.Getter;
import lombok.Setter;

/**
 * 微信公众号菜单click类型按钮
 */
@Getter
@Setter
public class ClickButton extends BasicButton {
    private String key;
}
