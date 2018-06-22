package com.tzy.common.sys.model.vo;

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
public class PermVo {
    /**
     * 权限id
     */
    private Long permId;

    /**
     * 上级id
     */
    private Long parentId;

    /**
     * 权限名称
     */
    @NotNull(message = "角色名称无效", groups = {UpdateGroup.class})
    private String permName;

    /**
     * 权限url
     */
    private String permUrl;

    /**
     * 访问方式
     */
    private String method;

    /**
     * 是否禁用
     */
    private Boolean isDisabled;

    /**
     * 是否删除
     */
    private Boolean isDel;
}
