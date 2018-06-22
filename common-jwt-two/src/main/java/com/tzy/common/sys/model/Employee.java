package com.tzy.common.sys.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "t_employee")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Employee {
    /**
     * 员工id
     */
    @Id
    @Column(name = "employee_id")
    private Long employeeId;

    /**
     * 景区id
     */
    @Column(name = "scenic_area_id")
    private Long scenicAreaId;

    /**
     * 姓名
     */
    private String name;

    /**
     * 身份证号码
     */
    @Column(name = "id_card_number")
    private String idCardNumber;

    /**
     * 电话号码
     */
    @Column(name = "phone_number")
    private String phoneNumber;

    /**
     * 家庭住址
     */
    private String address;

    /**
     * 工号
     */
    @Column(name = "job_number")
    private String jobNumber;

    /**
     * 用户名
     */
    @Column(name = "user_name")
    private String userName;

    /**
     * 密码
     */
    @JsonIgnore
    private String password;

    /**
     * 秘钥
     */
    @JsonIgnore
    private String salt;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 是否禁用
     */
    @Column(name = "is_disabled")
    private Boolean isDisabled;

    /**
     * 是否删除
     */
    @Column(name = "is_del")
    private Boolean isDel;
}