package com.tzy.common.biz.controller;

import com.tzy.common.biz.dto.biz.CommonSelectDto;
import com.tzy.common.biz.dto.biz.TclassDto;
import com.tzy.common.biz.model.Tclass;
import com.tzy.common.biz.service.TclassService;
import com.tzy.common.biz.vo.biz.TclassVo;
import com.tzy.common.config.JsonData;
import com.tzy.common.util.BeanUtil;
import com.tzy.common.util.FluentValidatorUtil;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Heaton
 * @date 2018/6/10 23:18
 */
@SuppressWarnings("all")
@RestController
@RequestMapping("/tclass")
@Api(value = "API - TclassController", description = "班级接口")
public class TclassController {

    @Autowired
    private TclassService tclassService;

    @ApiOperation(value = "添加")
    @ApiImplicitParam(name = "tclassDtos", value = "班级对象集合", dataType = "TclassDto", required = true, paramType = "body")
    @ApiResponses({@ApiResponse(code = 200, message = "操作成功"),
            @ApiResponse(code = 500, message = "服务器内部异常"),
            @ApiResponse(code = 999, message = "权限不足")})
    @PostMapping
    public JsonData create(@RequestBody List<TclassDto> tclassDtos) {
        FluentValidatorUtil.resultAdd(tclassDtos);
        List<Tclass> tclasses = new ArrayList<>();
        for (TclassDto dto : tclassDtos) {
            Tclass tclass = Tclass.builder().build();
            BeanUtil.copyProperties(dto, tclass);
            tclasses.add(tclass);
        }
        tclassService.addList(tclasses);
        return JsonData.ok();
    }

    @ApiOperation(value = "修改")
    @ApiImplicitParam(name = "tclassDtos", value = "班级对象集合", dataType = "TclassDto", required = true, paramType = "body")
    @ApiResponses({@ApiResponse(code = 200, message = "操作成功"),
            @ApiResponse(code = 500, message = "服务器内部异常"),
            @ApiResponse(code = 999, message = "权限不足")})
    @PutMapping("/updata")
    public JsonData update(@RequestBody List<TclassDto> tclassDtos) {
        FluentValidatorUtil.resultUpdate(tclassDtos);
        List<Tclass> tclasses = new ArrayList<>();
        for (TclassDto dto : tclassDtos) {
            Tclass tclass = Tclass.builder().build();
            BeanUtil.copyProperties(dto, tclass);
            tclasses.add(tclass);
        }
        tclassService.updateList(tclasses);
        return JsonData.ok();
    }

    @ApiOperation(value = "删除", notes = "根据url的id来指定删除对象")
    @ApiImplicitParam(name = "ids", value = "班级对象ID数组", dataType = "Long", required = true, paramType = "path")
    @ApiResponses({@ApiResponse(code = 200, message = "操作成功"),
            @ApiResponse(code = 500, message = "服务器内部异常"),
            @ApiResponse(code = 999, message = "权限不足")})
    @DeleteMapping("/{ids}")
    public JsonData delete(@PathVariable("ids") Long[] classIds) {
        tclassService.delete(classIds);
        return JsonData.ok();
    }

    @ApiOperation(value = "模糊查询班级", notes = "不传为全查询")
    @ApiImplicitParam(name = "commonSelectDto", value = "公用对象", dataType = "CommonSelectDto", required = true, paramType = "body")
    @ApiResponses({@ApiResponse(code = 200, message = "操作成功"),
            @ApiResponse(code = 500, message = "服务器内部异常"),
            @ApiResponse(code = 999, message = "权限不足")})
    @PostMapping("/selectLike")
    public JsonData selectLike(@RequestBody CommonSelectDto commonSelectDto) {
        List<Tclass> tclasses = tclassService.findAll(commonSelectDto);
        return JsonData.ok(tclasses);
    }

    @ApiOperation(value = "模糊查询班级及其学生信息", notes = "不传为全查询")
    @ApiImplicitParam(name = "commonSelectDto", value = "公用对象", dataType = "CommonSelectDto", required = true, paramType = "body")
    @ApiResponses({@ApiResponse(code = 200, message = "操作成功"),
            @ApiResponse(code = 500, message = "服务器内部异常"),
            @ApiResponse(code = 999, message = "权限不足")})
    @PostMapping("/selectLikeJoin")
    public JsonData findAllLikeJoinStudent(@RequestBody CommonSelectDto commonSelectDto) {
        List<Tclass> tclasses = tclassService.findAllLikeJoinStudent(commonSelectDto);
        List<TclassVo> tclassVos = BeanUtil.copyList(tclasses, TclassVo.class);
        return JsonData.ok(tclassVos);
    }

    /***
     *http://localhost/tclass/export?queryCriteria=班&ids=5468888365663232,5468895679251456&isExportAll=true
     * 这种因为设置为全表导出，故直接导出整个表的所有数据
     * http://localhost/tclass/export?queryCriteria=班&ids=5468888365663232,5468895679251456&isExportAll=false
     * 这种可以根据查询条件和id数组来下载需要的excel
     */
    @ApiOperation(value = "导出Excel")
    @ApiResponses({@ApiResponse(code = 200, message = "操作成功"),
            @ApiResponse(code = 500, message = "服务器内部异常"),
            @ApiResponse(code = 999, message = "权限不足")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "queryCriteria", value = "查询条件", paramType = "query"),
            @ApiImplicitParam(name = "ids", value = "id数组", paramType = "query"),
            @ApiImplicitParam(name = "isExportAll", value = "是否全部导出", defaultValue = "false", dataType = "boolean", paramType = "query")
    })
    @GetMapping("/export")
    public void export(HttpServletRequest request, HttpServletResponse response, String queryCriteria, Long[] ids, @RequestParam(defaultValue = "false") Boolean isExportAll) {
        tclassService.export(queryCriteria, ids, isExportAll, response);
    }


    /***
     * http://localhost/tclass/export?queryCriteria=班&ids=5468888365663232,5468895679251456&isExportAll=true
     * 这种因为设置为全表导出，故直接导出整个表的所有数据
     * http://localhost/tclass/exportJoinChild?queryCriteria=班&ids=5468888365663232,5468895679251456&isExportAll=false
     * 这种可以根据查询条件和id数组来下载需要的excel
     */
    @ApiOperation(value = "导出和班级相关学生的Excel")
    @ApiResponses({@ApiResponse(code = 200, message = "操作成功"),
            @ApiResponse(code = 500, message = "服务器内部异常"),
            @ApiResponse(code = 999, message = "权限不足")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "queryCriteria", value = "查询条件", paramType = "query"),
            @ApiImplicitParam(name = "ids", value = "id数组", paramType = "query"),
            @ApiImplicitParam(name = "isExportAll", value = "是否全部导出", defaultValue = "false", dataType = "boolean", paramType = "query")
    })
    @GetMapping("/exportJoinChild")
    public void exportJoinChild(HttpServletRequest request, HttpServletResponse response, String queryCriteria, Long[] ids, @RequestParam(defaultValue = "false") Boolean isExportAll) {
        tclassService.exportJoinChild(queryCriteria, ids, isExportAll, response);
    }



    @ApiOperation(value = "导出和某个班级相关学生的Excel（按照模板）")
    @ApiResponses({@ApiResponse(code = 200, message = "操作成功"),
            @ApiResponse(code = 500, message = "服务器内部异常"),
            @ApiResponse(code = 999, message = "权限不足")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "queryCriteria", value = "查询条件", paramType = "query"),
            @ApiImplicitParam(name = "id", value = "班级id", paramType = "query",required = true)
    })
    @GetMapping("/exportExcleTemplate")
    public void exportExcleTemplate(HttpServletRequest request, HttpServletResponse response, String queryCriteria, Long id) {
        Long[] ids = {id};
        tclassService.exportExcleTemplate(queryCriteria, ids, response);
    }

}
