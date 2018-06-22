package com.tzy.common.biz.dto.biz;

import com.tzy.common.validGroup.AddProject;
import com.tzy.common.validGroup.UpdateProject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "班级对象Dto")
public class TclassDto {
    /**
     * 班级ID
     */
    @NotNull(message = "班级Id不能为空", groups = {UpdateProject.class})
    @ApiModelProperty(value = "班级ID")
    private Long id;

    /**
     * 名字
     */
    @NotNull(message = "班级名字不能为空", groups = {AddProject.class})
    @ApiModelProperty(value = "班级名字")
    private String userName;

    /**
     * 是否删除
     */
    @ApiModelProperty(value = "是否删除")
    private Boolean isDel;

    /**
     * 学生集合
     */
    @ApiModelProperty(value = "学生集合")
    private List students;
}