package com.tzy.common.controller;


import com.tzy.common.config.JsonData;
import com.tzy.common.service.EmployeeService;
import com.tzy.common.sys.model.Employee;
import com.tzy.common.sys.model.vo.EmployeeVo;
import com.tzy.common.util.BeanUtil;
import com.tzy.common.validGroup.AddGroup;
import com.tzy.common.validGroup.UpdateGroup;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    public JsonData create(@RequestBody @Validated(AddGroup.class) EmployeeVo employeeVo) {
        Employee employee = new Employee();
        BeanUtil.copyProperties(employeeVo, employee);
        employee = employeeService.create(employee);
        return JsonData.ok(employee);
    }

    @PutMapping
    public JsonData update(@RequestBody @Validated(UpdateGroup.class) EmployeeVo employeeVo) {
        Employee employee = new Employee();
        BeanUtil.copyProperties(employeeVo, employee);
        employeeService.update(employee);
        return JsonData.ok();
    }

    @GetMapping("/{id}")
    public JsonData selectByKey(@PathVariable("id") Long employeeId) {
        Employee employee = employeeService.selectByKey(employeeId);
        return JsonData.ok(employee);
    }

    @GetMapping("/scenicId")
    public JsonData selectByScenicId(@RequestParam Long scenicId) {
        List<Employee> employees = employeeService.selectByScenicId(scenicId);
        return JsonData.ok(employees);
    }

    /**
     * 修改密码
     * @return
     */
    @PutMapping("/modifyPassword")
    @ApiOperation(value = "修改密码",
            notes = "{\"employeeId\":\"员工Id\",\"oldPassword\":\"旧密码\",\"newPassword\":\"新密码\"}")
    public JsonData modifyPassword (@RequestBody Map map){
        return employeeService.modifyPassword(
                (Long)map.get("employeeId"),
                (String) map.get("oldPassword"),
                (String) map.get("newPassword")
        );
    }

}
