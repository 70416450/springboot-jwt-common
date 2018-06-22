package com.tzy.common.service;

import com.tzy.common.config.JsonData;
import com.tzy.common.sys.model.Employee;

import java.util.List;

public interface EmployeeService {

    Employee login(String userName, String password);

    Employee create(Employee employee);

    void update(Employee employee);

    Employee selectByKey(Long employeeId);

    List<Employee> selectByScenicId(Long scenicId);

    JsonData modifyPassword(Long employeeId, String oldPassword, String newPassword);
}
