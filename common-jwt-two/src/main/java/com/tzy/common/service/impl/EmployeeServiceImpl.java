package com.tzy.common.service.impl;


import com.tzy.common.config.JsonData;
import com.tzy.common.exception.BusinessException;
import com.tzy.common.exception.CommonErrorCode;
import com.tzy.common.service.EmployeeService;
import com.tzy.common.sys.mapper.EmployeeMapper;
import com.tzy.common.sys.model.Employee;
import com.tzy.common.util.PasswordUtil;
import com.tzy.common.util.Validator;
import com.tzy.common.util.id.IdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import java.util.Map;

@SuppressWarnings("all")
@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;


    @Override
    public Employee login(String userName, String password) {
        if (!Validator.valid(userName)) {
            throw new BusinessException(CommonErrorCode.ILLEGAL_PARAM);
        }
        if (!Validator.valid(password)) {
            throw new BusinessException(CommonErrorCode.ILLEGAL_PARAM);
        }
        Example example = new Example(Employee.class);
        example.createCriteria().andEqualTo("userName", userName);
        List<Employee> employees = employeeMapper.selectByExample(example);
        if (!Validator.valid(employees)) {
            throw new BusinessException(CommonErrorCode.OBJECT_IS_NULL);
        }
        Employee employee = employees.get(0);
        boolean b = PasswordUtil.genPassword(password, employee.getPassword(), employee.getSalt());
        if (!b) {
            throw new BusinessException(CommonErrorCode.USERNAME_OR_PASSWORD_ERROR);
        }
        return employee;
    }

    @Override
    public Employee create(Employee employee) {
        employee.setEmployeeId(IdUtil.generateId());
        employee.setCreateTime(new Date());
        employee.setIsDel(false);
        employee.setIsDisabled(false);

        Map<String, String> map = PasswordUtil.password(employee.getPassword());
        employee.setPassword(map.get("password"));
        employee.setSalt(map.get("salt"));

        employeeMapper.insert(employee);
        return employee;
    }

    @Override
    public void update(Employee employee) {
        Map<String, String> map = PasswordUtil.password(employee.getPassword());
        employee.setPassword(map.get("password"));
        employee.setSalt(map.get("salt"));
        employeeMapper.updateByPrimaryKeySelective(employee);
    }

    @Override
    public Employee selectByKey(Long employeeId) {
        return employeeMapper.selectByPrimaryKey(employeeId);
    }

    @Override
    public List<Employee> selectByScenicId(Long scenicId) {
        Example example = new Example(Employee.class);
        example.createCriteria().
                andEqualTo("scenicAreaId",scenicId);
        List<Employee> employees = employeeMapper.selectByExample(example);
        return employees;
    }

    @Override
    public JsonData modifyPassword(Long employeeId, String oldPassword, String newPassword) {
        Employee employee = employeeMapper.selectByPrimaryKey(employeeId);
        boolean b = PasswordUtil.genPassword(oldPassword, employee.getPassword(), employee.getSalt());
        if (!b) {
            return JsonData.ok("密码错误");
        }
        employee.setPassword(newPassword);
        update(employee);
        return JsonData.ok("密码修改成功");
    }
}
