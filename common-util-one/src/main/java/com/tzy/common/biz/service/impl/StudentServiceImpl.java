package com.tzy.common.biz.service.impl;

import com.tzy.common.biz.dto.biz.CommonSelectDto;
import com.tzy.common.biz.mapper.StudentMapper;
import com.tzy.common.biz.model.Enclosure;
import com.tzy.common.biz.model.Student;
import com.tzy.common.biz.service.EnclosureService;
import com.tzy.common.biz.service.InfoEnclosureService;
import com.tzy.common.biz.service.StudentService;
import com.tzy.common.biz.vo.JoinVo.StudentJoinVo;
import com.tzy.common.exception.BizErrorCode;
import com.tzy.common.exception.BusinessException;
import com.tzy.common.util.BeanUtil;
import com.tzy.common.util.DateUtil;
import com.tzy.common.util.PojoPageHandler;
import com.tzy.common.util.Validator;
import com.tzy.common.util.file.FileExcleUtil;
import com.tzy.common.util.file.FileWordUtil;
import com.tzy.common.util.id.IdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @author Heaton
 * @date 2018/6/11 21:42
 */
@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private EnclosureService enclosureService;

    @Autowired
    private InfoEnclosureService infoEnclosureService;

    @Value("${message.data.file.path}")
    private String oldFilePath;

    @Override
    public List<Student> findAll(CommonSelectDto commonSelectDto) {
        PojoPageHandler.doPagingAndSorting(commonSelectDto);
        List<Student> allLike = studentMapper.findAllLike(commonSelectDto);
        return allLike;
    }

    @Override
    public void addList(List<Student> students) {
        for (Student student : students) {
            student.setStudentId(IdUtil.generateId());
            student.setIsDel(false);
        }
        studentMapper.addList(students);
    }

    @Override
    public void updateList(List<Student> students) {
        studentMapper.updateList(students);
    }

    @Override
    public void delete(Long[] ids) {
        Example example = getExample(ids);
        Student student = Student.builder().isDel(true).build();
        studentMapper.updateByExampleSelective(student, example);

    }

    //查询符合条件的
    private Example getExample(Long[] idss) {
        if (!Validator.valid(idss)) {
            throw new BusinessException(BizErrorCode.ID_NULL);
        }
        List<Long> ids = Arrays.asList(idss);
        Example example = new Example(Student.class);
        example.createCriteria().andIn("studentId", ids);
        return example;
    }

    @Override
    public void export(String queryCriteria, Long[] ids, Boolean isExportAll, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<>();
        if (!isExportAll) {
            if (Validator.valid(queryCriteria)) {
                map.put("queryCriteria", queryCriteria);
            }
            if (Validator.valid(ids)) {
                map.put("ids", ids);
            }
        }
        List<StudentJoinVo> studentJoinVos = studentMapper.selectInfoEnclosureList(map);
        FileExcleUtil.exportExcel(studentJoinVos, "学生管理", "学生信息", StudentJoinVo.class, "学生管理" + DateUtil.timeStamp2Date(DateUtil.timeStamp(), "yyyy-MM-dd") + ".xls", response);
    }

    @Override
    public void exportWord(Long id, HttpServletResponse httpServletResponse) {
        Student student = studentMapper.selectByPrimaryKey(id);
        StudentJoinVo studentJoinVo = StudentJoinVo.builder().build();
        BeanUtil.copyProperties(student, studentJoinVo);
        FileWordUtil.SimpleWordExport(httpServletResponse, studentJoinVo, "templates/easytemplates/学生信息.docx", oldFilePath, "学生信息");
    }


    @Transactional
    @Override
    public void upload(MultipartFile file, Long[] infoIds) {
        MultipartFile[] multipartFiles = {file};
        ArrayList<Long> list = enclosureService.createList(multipartFiles, oldFilePath + "学生信息管理");
        Long enclosureId = list.isEmpty() ? null : list.get(0);
        infoEnclosureService.create(enclosureId, infoIds);
    }

    @Override
    public Enclosure uploadSingle(MultipartFile file) {
        return enclosureService.createSingle(file, oldFilePath + "学生信息管理");
    }

    @Override
    public List<StudentJoinVo> importExcel(MultipartFile file) {
        List<StudentJoinVo> studentJoinVos = FileExcleUtil.importExcel(file, 1, 1, StudentJoinVo.class);
        return studentJoinVos;
    }

    @Override
    public List<Enclosure> selectInfoEnclosure(Long infoId) {
        return infoEnclosureService.selectInfoEnclosure(infoId);
    }

    @Override
    public void deleteInfoEnclosure(Long infoEnclosureId) {
        infoEnclosureService.delete(infoEnclosureId);
    }
}
