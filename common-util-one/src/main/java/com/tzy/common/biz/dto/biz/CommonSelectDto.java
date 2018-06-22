package com.tzy.common.biz.dto.biz;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@ApiModel(description = "公用查询对象")
public class CommonSelectDto {
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
}
