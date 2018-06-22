package com.tzy.common.biz.vo.biz;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "班级对象Dto")
public class TclassVo {
    /**
     * 班级ID
     */
    @ApiModelProperty(value = "班级ID")
    private Long id;

    /**
     * 名字
     */
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