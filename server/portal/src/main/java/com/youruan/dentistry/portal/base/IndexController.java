package com.youruan.dentistry.portal.base;

import com.youruan.dentistry.core.user.domain.RegisteredUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author jin
 * @date 2020-06-06 11:18
 **/
@Controller
public class IndexController {

    @GetMapping("/index")
    public String index(RegisteredUser user, Model model) {
        model.addAttribute("user", user);
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/regist")
    public String register(){
        return "regist";
    }

    @GetMapping("/cert")
    public String cert(){
        return "cert";
    }

}
