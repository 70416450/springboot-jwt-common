package com.tzy.common.biz.service;

import com.tzy.common.biz.dto.biz.CommonSelectDto;
import com.tzy.common.biz.model.Enclosure;
import com.tzy.common.biz.model.Student;
import com.tzy.common.biz.vo.JoinVo.StudentJoinVo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author Heaton
 * @date 2018/6/11 21:40
 */
public interface StudentService {

    List<Student> findAll(CommonSelectDto commonSelectDto);

    void addList(List<Student> students);

    void updateList(List<Student> students);

    void delete(Long[] ids);

    //导出Excel
    void export(String queryCriteria, Long[] ids, Boolean isExportAll, HttpServletResponse response);

    //导入Excel
    List<StudentJoinVo> importExcel(MultipartFile file);

    //导出word
    void exportWord(Long id, HttpServletResponse httpServletResponse);

    //上传文件，多
    void upload(MultipartFile file, Long[] doseInfoIds);
    //上传文件，单 返回附件信息，用于回显
    Enclosure uploadSingle(MultipartFile file);

    //根据业务ID返回附件
    List<Enclosure> selectInfoEnclosure(Long infoId);

    //
    void deleteInfoEnclosure(Long infoEnclosureId);
}
