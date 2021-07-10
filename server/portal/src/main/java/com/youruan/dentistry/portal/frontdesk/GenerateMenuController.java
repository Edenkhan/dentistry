package com.youruan.dentistry.portal.frontdesk;

import com.youruan.dentistry.core.frontdesk.service.GenerateMenuService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/generateMenu")
public class GenerateMenuController {

    private final GenerateMenuService generateMenuService;

    public GenerateMenuController(GenerateMenuService generateMenuService) {
        this.generateMenuService = generateMenuService;
    }

    /**
     * 自定义公众号菜单
     */
    @GetMapping("/define")
    public void define() {
        generateMenuService.define();
    }

}
