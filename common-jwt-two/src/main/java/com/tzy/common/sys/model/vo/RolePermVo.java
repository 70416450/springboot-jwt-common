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
public class RolePermVo {
    /**
     * 角色id
     */
    @NotNull(message = "角色id无效", groups = {UpdateGroup.class})
    private Long roleId;

    /**
     * 权限id
     */
    private List<String> permList;
}
