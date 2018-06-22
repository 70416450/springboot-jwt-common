package com.tzy.common.biz.dto.biz;

import com.tzy.common.validGroup.AddProject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@ApiModel(description = "公用查询对象")
public class CommonFileSelectDto {
    @ApiModelProperty(value = "页码")
    private Integer pageNum;
    @ApiModelProperty(value = "每页条数")
    private Integer pageSize;
    @ApiModelProperty(value = "排序字段")
    private String sort;
    @ApiModelProperty(value = "排序方式")
    private String order;
    @ApiModelProperty(value = "查询条件")
    private String queryCriteria;
    @ApiModelProperty(value = "类型:体检报告,丢失调查,干预通知,异常通知,计量档案,计量申报,计量信息,计量对比,人员详情")
    @NotNull(message = "类型必须传递", groups = AddProject.class)
    private String type;
    @ApiModelProperty(value = "id数组")
    private Long[] ids;
}
