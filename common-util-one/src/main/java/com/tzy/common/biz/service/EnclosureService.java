package com.tzy.common.biz.service;

import com.tzy.common.biz.dto.biz.CommonFileSelectDto;
import com.tzy.common.biz.model.Enclosure;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

public interface EnclosureService {
    //添加附件集合 返回附件用于回显
    ArrayList<Long> createList(MultipartFile[] multipartfiles, String fileName);

    //添加单个附件 返回附件用于回显
    Enclosure createSingle(MultipartFile multipartfiles, String fileName);

    //删除
    void delete(CommonFileSelectDto commonFileSelectDto);

    //打包下载
    void fileLoad(Long[] ids, HttpServletRequest request, HttpServletResponse response);
    //单独下载
    void fileOneLoad(Long id, HttpServletRequest request, HttpServletResponse response);
}
