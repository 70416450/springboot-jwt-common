package com.tzy.common.biz.controller;

import com.tzy.common.biz.dto.biz.CommonSelectDto;
import com.tzy.common.biz.dto.biz.StudentDto;
import com.tzy.common.biz.model.Enclosure;
import com.tzy.common.biz.model.Student;
import com.tzy.common.biz.service.EnclosureService;
import com.tzy.common.biz.service.StudentService;
import com.tzy.common.biz.vo.JoinVo.StudentJoinVo;
import com.tzy.common.config.JsonData;
import com.tzy.common.util.BeanUtil;
import com.tzy.common.util.FluentValidatorUtil;
import com.tzy.common.util.file.FilePDFUtil;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Heaton
 * @date 2018/6/11 21:49
 */
@SuppressWarnings("all")
@RestController
@RequestMapping("/student")
@Api(value = "API - StudentController", description = "学生接口")
public class StudentController {

    @Autowired
    private StudentService studentService;
    @Autowired
    private EnclosureService enclosureService;


    @Value("${message.data.file.path}")
    private String oldFilePath;

    @ApiOperation(value = "添加")
    @ApiImplicitParam(name = "studentDtos", value = "学生对象集合", dataType = "StudentDto", required = true, paramType = "body")
    @ApiResponses({@ApiResponse(code = 200, message = "操作成功"),
            @ApiResponse(code = 500, message = "服务器内部异常"),
            @ApiResponse(code = 999, message = "权限不足")})
    @PostMapping
    public JsonData create(@RequestBody List<StudentDto> studentDtos) {
        FluentValidatorUtil.resultAdd(studentDtos);
//        List<Student> students = new ArrayList<>();
//        for (StudentDto dto : studentDtos) {
//            Student student = Student.builder().build();
//            BeanUtil.copyProperties(dto, student);
//            students.add(student);
//        }
        List<Student> students = BeanUtil.copyList(studentDtos, Student.class);
        studentService.addList(students);
        return JsonData.ok();
    }

    /***
     * http://localhost/student/export?queryCriteria=1&ids=5471374410122240,5471376268166144&isExportAll=true
     * 这种因为设置为全表导出，故直接导出整个表的所有数据
     * http://localhost/student/export?queryCriteria=1&ids=5471374410122240,5471376268166144&isExportAll=false
     * 这种可以根据查询条件和id数组来下载需要的excel
     */
    @ApiOperation(value = "修改")
    @ApiImplicitParam(name = "studentDtos", value = "学生对象集合", dataType = "TclassDto", required = true, paramType = "body")
    @ApiResponses({@ApiResponse(code = 200, message = "操作成功"),
            @ApiResponse(code = 500, message = "服务器内部异常"),
            @ApiResponse(code = 999, message = "权限不足")})
    @PutMapping("/updata")
    public JsonData update(@RequestBody List<StudentDto> studentDtos) {
        FluentValidatorUtil.resultUpdate(studentDtos);
        List<Student> students = new ArrayList<>();
        for (StudentDto dto : studentDtos) {
            Student student = Student.builder().build();
            BeanUtil.copyProperties(dto, student);
            students.add(student);
        }
        studentService.updateList(students);
        return JsonData.ok();
    }

    @ApiOperation(value = "删除", notes = "根据url的id来指定删除对象")
    @ApiImplicitParam(name = "ids", value = "学生对象ID数组", dataType = "Long", required = true, paramType = "path")
    @ApiResponses({@ApiResponse(code = 200, message = "操作成功"),
            @ApiResponse(code = 500, message = "服务器内部异常"),
            @ApiResponse(code = 999, message = "权限不足")})
    @DeleteMapping("/{ids}")
    public JsonData delete(@PathVariable("ids") Long[] ids) {
        studentService.delete(ids);
        return JsonData.ok();
    }

    @ApiOperation(value = "模糊查询学生", notes = "不传为全查询")
    @ApiImplicitParam(name = "commonSelectDto", value = "公用对象", dataType = "CommonSelectDto", required = true, paramType = "body")
    @ApiResponses({@ApiResponse(code = 200, message = "操作成功"),
            @ApiResponse(code = 500, message = "服务器内部异常"),
            @ApiResponse(code = 999, message = "权限不足")})
    @PostMapping("/selectLike")
    public JsonData selectLike(@RequestBody CommonSelectDto commonSelectDto) {
        List<Student> students = studentService.findAll(commonSelectDto);
        return JsonData.ok(students);
    }

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
        studentService.export(queryCriteria, ids, isExportAll, response);
    }


    @ApiOperation(value = "单查询导出word", notes = "根据url的id来指定查询对象调查导出word")
    @ApiImplicitParam(name = "id", value = "学生ID", required = true, dataType = "Long", paramType = "path")
    @GetMapping("/word/{id}")
    public JsonData word(@PathVariable("id") Long id, HttpServletResponse httpServletResponse) {
        studentService.exportWord(id,httpServletResponse);
        return JsonData.ok();
    }

    @ApiOperation(value = "上传附件,多业务对应一个附件")
    @ApiImplicitParam(name = "infoIds", value = "业务id集合", paramType = "query")
    @PostMapping("/upload")
    public JsonData upload(@RequestParam("file") MultipartFile file, Long[] infoIds) {
        studentService.upload(file, infoIds);
        return JsonData.ok();
    }


    @ApiOperation(value = "上传单个附件")
    @PostMapping("/uploadSingle")
    public JsonData uploadSingle(@RequestParam("file") MultipartFile file) {
        Enclosure enclosure = studentService.uploadSingle(file);
        return JsonData.ok(enclosure);
    }

    @ApiOperation(value = "excel导入,拿到准备添加的数据")
    @PostMapping("/importExcel")
    public JsonData upload(@RequestParam("file") MultipartFile file) {
        List<StudentJoinVo> studentJoinVos = studentService.importExcel(file);
        return JsonData.ok(studentJoinVos);
    }

    @ApiOperation(value = "得到附件")
    @ApiImplicitParam(name = "infoId", value = "学生业务id", paramType = "path")
    @GetMapping("/getEnclosure/{infoId}")
    public JsonData selectDoseInfoEnclosure(@PathVariable("infoId") Long infoId) {
        List<Enclosure> infoEnclosures = studentService.selectInfoEnclosure(infoId);
        return JsonData.ok(infoEnclosures);
    }

    @ApiOperation(value = "删除附件")
    @ApiImplicitParam(name = "infoEnclosureId", value = "信息附件id", paramType = "path")
    @GetMapping("/deleteEnclosure/{infoEnclosureId}")
    public JsonData deleteEnclosure(@PathVariable("infoEnclosureId") Long infoEnclosureId) {
        studentService.deleteInfoEnclosure(infoEnclosureId);
        return JsonData.ok();
    }

    @ApiOperation(value = "批量打包下载")
    @ApiImplicitParam(name = "ids", value = "附件ID数组", dataType = "Long", required = true, paramType = "query")
    @GetMapping("/fileLoad")
    public void fileLoad(@RequestParam("ids") Long[] ids, HttpServletRequest request, HttpServletResponse response) {
        enclosureService.fileLoad(ids, request, response);
    }


    @ApiOperation(value = "单独下载")
    @ApiImplicitParam(name = "id", value = "附件ID", dataType = "Long", required = true, paramType = "query")
    @GetMapping("/fileOneLoad")
    public void fileOneLoad(@RequestParam("id") Long id, HttpServletRequest request, HttpServletResponse response) {
        enclosureService.fileOneLoad(id, request, response);
    }

    //http://localhost:8080/student/showEnclosure?enclosurePath=D:\templates\学生信息管理2018-06-20\5496051415352320_测试.pdf
    @ApiOperation(value = "预览附件PDF")
    @ApiImplicitParam(name = "enclosurePath", value = "路径", dataType = "String", required = true, paramType = "query")
    @GetMapping("/showEnclosure")
    public void showEnclosure(HttpServletResponse response, String enclosurePath) {
        FilePDFUtil.displayPDF(enclosurePath, response);
    }





}
