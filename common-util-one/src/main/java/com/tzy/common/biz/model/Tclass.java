package com.tzy.common.biz.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;

@Table(name = "t_tclass")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Tclass {
    /**
     * 班级ID
     */
    @Id
    private Long id;

    /**
     * 名字
     */
    @Column(name = "user_name")
    private String userName;

    /**
     * 是否删除
     */
    @Column(name = "is_del")
    private Boolean isDel;

    /**
     * 学生集合
     */
    private List<Student> students;
}