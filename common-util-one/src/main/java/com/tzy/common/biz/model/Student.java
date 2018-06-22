package com.tzy.common.biz.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "t_student")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    /**
     * 学生ID
     */
    @Id
    @Column(name = "student_id")
    private Long studentId;

    /**
     * 班级ID
     */
    private Long id;

    /**
     * 名字
     */
    @Column(name = "user_name")
    private String userName;

    /**
     * 性别
     */
    private Integer gender;

    /**
     * 身份证号
     */
    @Column(name = "id_card")
    private String idCard;

    /**
     * 眼睛度数
     */
    @Column(name = "eyes_degree")
    private Double eyesDegree;

    /**
     * 是否删除
     */
    @Column(name = "is_del")
    private Boolean isDel;

    /**
     * 生日
     */
    private Date bd;

}