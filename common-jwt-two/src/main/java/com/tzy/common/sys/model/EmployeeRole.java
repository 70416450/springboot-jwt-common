package com.tzy.common.sys.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "t_employee_role")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class EmployeeRole {
    /**
     * 员工id
     */
    @Id
    @Column(name = "employee_id")
    private Long employeeId;

    /**
     * 角色id
     */
    @Id
    @Column(name = "role_id")
    private Long roleId;
}