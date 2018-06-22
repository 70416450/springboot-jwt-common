package com.tzy.common.sys.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "t_role_perm")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class RolePerm {
    /**
     * 角色id
     */
    @Id
    @Column(name = "role_id")
    private Long roleId;

    /**
     * 权限id
     */
    @Id
    @Column(name = "perm_id")
    private Long permId;
}