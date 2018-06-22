package com.tzy.common.biz.vo.JoinVo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.tzy.common.validGroup.AddProject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Table(name = "t_student")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentJoinVo {
    /**
     * 学生ID
     */
    @Id
    @Column(name = "student_id")
    @Excel(name = "学生ID", width = 25)
    private Long studentId;

    /**
     * 班级ID
     */
    private Long id;

    /**
     * 名字
     */
    @Column(name = "user_name")
    @Excel(name = "学生名字")
    private String userName;

    /**
     * 性别
     */
    @Excel(name = "性别", replace = {"男_1", "女_2"})
    private Integer gender;

    /**
     * 身份证号
     */
    @Column(name = "id_card")
    @Excel(name = "身份证号", width = 25)
    @Pattern(regexp = "^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$",
            message = "身份证号码不正确",
            groups = {AddProject.class})
    private String idCard;

    /**
     * 眼睛度数
     */
    @Column(name = "eyes_degree")
    @Excel(name = "眼睛度数")
    private Double eyesDegree;

    /**
     * 是否删除
     */
    @Column(name = "is_del")
    private Boolean isDel;

    /**
     * 生日
     */
    @JsonFormat(pattern = "yyyy-MM")
    @Excel(name = "生日", exportFormat = "yyyy-MM-dd", width = 20)
    private Date bd;

    //添加字段
    /**
     * 附件数量
     */
    @Excel(name = "附件数量")
    private Integer enclosureCount;
}