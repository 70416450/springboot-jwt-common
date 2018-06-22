package com.tzy.common.biz.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Created by Heaton on 2018/6/20.
 * @describe X
 */
@Controller
public class indexController {
    @RequestMapping("index")
    public String index() {
        return "templates/index.html";
    }

}
