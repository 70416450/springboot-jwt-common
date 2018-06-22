package com.tzy.common.sys.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;

@Table(name = "t_perm")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Perm {
    /**
     * 权限id
     */
    @Id
    @Column(name = "perm_id")
    private Long permId;

    /**
     * 上级id
     */
    @Column(name = "parent_id")
    private Long parentId;

    /**
     * 权限名称
     */
    @Column(name = "perm_name")
    private String permName;

    /**
     * 权限url
     */
    @Column(name = "perm_url")
    private String permUrl;

    /**
     * 访问方式
     */
    private String method;

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

    private List<Perm> permList;

}