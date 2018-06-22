package com.tzy.common.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tzy.common.config.JsonData;
import com.tzy.common.service.EmployeeService;
import com.tzy.common.service.PermService;
import com.tzy.common.sys.model.Employee;
import com.tzy.common.sys.model.Perm;
import com.tzy.common.util.authorization.jwt.JwtUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/")
public class LoginController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private PermService permService;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName",value = "用户名",paramType = "query"),
            @ApiImplicitParam(name = "password",value = "密码",paramType = "query")
    })
    @PostMapping("/login")
    public JsonData login(String userName, String password) {
        Employee employee = employeeService.login(userName, password);
        List<Perm> permList = permService.findPermByUserId(employee.getEmployeeId());
        String permListString = JSONArray.toJSONString(permList);
        System.out.println("permListString -----> " + permListString);
        String userString = JSONObject.toJSONString(employee);
        String jwt = null;
        try {
            jwt = JwtUtil.createJWT(userString, permListString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<Perm> perms = permService.selectUserPerm(employee.getEmployeeId());
        System.out.println(JSONArray.toJSONString(perms));
        Map<String, Object> map = new HashMap<>();
        map.put("perms", perms);
        map.put("jwt", jwt);
        map.put("employee", employee);
        System.out.println(map);
        return JsonData.ok(map);
    }
}
