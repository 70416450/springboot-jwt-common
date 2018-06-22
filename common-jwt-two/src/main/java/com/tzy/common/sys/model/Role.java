package com.tzy.common.sys.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "t_role")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Role {
    /**
     * 角色id
     */
    @Id
    @Column(name = "role_id")
    private Long roleId;

    /**
     * 角色名称
     */
    @Column(name = "role_name")
    private String roleName;

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