package com.tzy.common.biz.vo.JoinVo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
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
@ExcelTarget("TclassJoinVo")
public class TclassJoinVo {
    /**
     * 班级ID
     */
    @Id
    @Excel(name = "班级ID", width = 25, needMerge = true)
    private Long id;

    /**
     * 名字
     */
    @Column(name = "user_name")
    @Excel(name = "名字", needMerge = true)
    private String userName;

    /**
     * 是否删除
     */
    @Column(name = "is_del")
    private Boolean isDel;

    /**
     * 学生集合
     */
    @ExcelCollection(name = "学生集合")
    private List<StudentJoinVo> studentJoinVos;

    //添加字段
    /**
     * 附件数量
     */
    @Excel(name = "附件数量", needMerge = true)
    private Integer enclosureCount;
}