package com.tzy.common.sys.model.vo;

import com.tzy.common.validGroup.UpdateGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EmployeeRoleVO {
    /**
     * 员工id
     */
    @NotNull(message = "员工id无效", groups = {UpdateGroup.class})
    private Long employeeId;

    /**
     * 角色id
     */
    private List<String> roleList;
}
