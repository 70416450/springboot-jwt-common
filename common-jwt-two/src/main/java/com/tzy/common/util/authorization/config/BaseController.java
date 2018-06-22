package com.tzy.common.util.authorization.config;


import com.tzy.common.sys.model.Employee;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description: 需要用到的controller继承此类，然后于sql中添加需要的条件
 */
public class BaseController {

    protected Employee getLoginEmployee(HttpServletRequest request) {
//        return (Employee) request.getAttribute("employee");
        return Employee.builder().name("罗甜").build();
    }
}
