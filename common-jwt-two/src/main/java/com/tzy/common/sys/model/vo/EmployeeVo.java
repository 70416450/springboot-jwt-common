package com.tzy.common.sys.model.vo;

import com.tzy.common.validGroup.AddGroup;
import com.tzy.common.validGroup.UpdateGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EmployeeVo {
    /**
     * 员工id
     */
    @NotNull(message = "员工id无效", groups = {UpdateGroup.class})
    private Long employeeId;

    /**
     * 景区id
     */
    @NotNull(message = "景区id无效", groups = {AddGroup.class})
    private Long scenicAreaId;

    /**
     * 姓名
     */
    @NotNull(message = "姓名", groups = {AddGroup.class})
    private String name;

    /**
     * 身份证号码
     */
    @NotNull(message = "身份证号码无效", groups = {AddGroup.class})
    private String idCardNumber;

    /**
     * 电话号码
     */
    @NotNull(message = "电话号码无效", groups = {AddGroup.class})
    private String phoneNumber;

    /**
     * 家庭住址
     */
    private String address;

    /**
     * 工号
     */
    private String jobNumber;

    /**
     * 用户名
     */
    @NotNull(message = "用户名无效", groups = {AddGroup.class})
    private String userName;

    /**
     * 密码
     */
    @NotNull(message = "密码无效", groups = {AddGroup.class})
    private String password;

    /**
     * 秘钥
     */
    private String salt;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 是否禁用
     */
    private Boolean isDisabled;

    /**
     * 是否删除
     */
    private Boolean isDel;
}
