package com.tzy.common.sys.model.vo;

import com.tzy.common.validGroup.AddGroup;
import com.tzy.common.validGroup.UpdateGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RoleVo {

    /**
     * 角色id
     */
    @NotNull(message = "角色id无效", groups = {UpdateGroup.class})
    private Long roleId;

    /**
     * 角色名称
     */
    @NotNull(message = "角色名称无效", groups = {AddGroup.class})
    private String roleName;

    /**
     * 是否禁用
     */
    private Boolean isDisabled;

    /**
     * 是否删除
     */
    private Boolean isDel;
}
