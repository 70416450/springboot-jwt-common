package com.tzy.common.biz.vo.biz;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tzy.common.validGroup.AddProject;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentVo {
    /**
     * 学生ID
     */
    @ApiModelProperty(value = "学生ID")
    private Long studentId;

    /**
     * 班级ID
     */
    @ApiModelProperty(value = "班级名字")
    private Long id;

    /**
     * 名字
     */
    @ApiModelProperty(value = "班级名字")
    private String userName;

    /**
     * 性别
     */
    @ApiModelProperty(value = "性别")
    private Integer gender;

    /**
     * 身份证号
     */
    @ApiModelProperty(value = "身份证号码")
    @Pattern(regexp = "^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$",
            message = "身份证号码不正确",
            groups = {AddProject.class})
    private String idCard;

    /**
     * 眼睛度数
     */
    @ApiModelProperty(value = "眼睛度数")
    private Double eyesDegree;

    /**
     * 是否删除
     */
    @ApiModelProperty(value = "是否删除 true删除，false有效")
    private Boolean isDel;

    /**
     * 生日
     */
    @ApiModelProperty(value = "生日")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date bd;
}