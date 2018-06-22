package com.tzy.common.biz.service.impl;

import com.tzy.common.biz.dto.biz.CommonFileSelectDto;
import com.tzy.common.biz.mapper.EnclosureMapper;
import com.tzy.common.biz.model.Enclosure;
import com.tzy.common.biz.service.EnclosureService;
import com.tzy.common.exception.BizErrorCode;
import com.tzy.common.exception.BusinessException;
import com.tzy.common.util.BeanUtil;
import com.tzy.common.util.Validator;
import com.tzy.common.util.file.FileLoadUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@SuppressWarnings("all")
@Service
@Slf4j
public class EnclosureServiceImpl implements EnclosureService {

    @Autowired
    private EnclosureMapper enclosureMapper;

    /**
     * @param [enclosure]
     * @return void
     * @date 2018/5/21 16:48
     * @describe 上传文件并批量添加附件指向, 返回附件ID列表
     */
    @Override
    public ArrayList<Long> createList(MultipartFile[] multipartfiles, String fileName) {
        List<Enclosure> enclosures = new ArrayList<>();//用于插入数据库
        ArrayList<Long> ls = new ArrayList<>();//用于返回的ID集合
        List<FileLoadUtil.MyFileInfo> myFileInfos = FileLoadUtil.simpleUpLoad(fileName, multipartfiles);
        for (FileLoadUtil.MyFileInfo info : myFileInfos) {
            Enclosure enclosure = Enclosure.builder().isDel(false).build();
            BeanUtil.copyProperties(info, enclosure);
            ls.add(info.getEnclosureId());
            enclosures.add(enclosure);
        }
        enclosureMapper.insertEnclosureList(enclosures);
        return ls;
    }

    @Override
    public Enclosure createSingle(MultipartFile multipartfiles, String fileName) {
        MultipartFile[] m = {multipartfiles};
        List<FileLoadUtil.MyFileInfo> myFileInfos = FileLoadUtil.simpleUpLoad(fileName, m);
        FileLoadUtil.MyFileInfo myFileInfo = myFileInfos.get(0);
        Enclosure enclosure = Enclosure.builder().isDel(false).build();
        BeanUtil.copyProperties(myFileInfo, enclosure);
        return enclosure;
    }


    @Transactional
    @Override
    public void delete(CommonFileSelectDto commonFileSelectDto) {
        String type = commonFileSelectDto.getType();
        Example example = getExample(commonFileSelectDto.getIds());
        Enclosure enclosure = Enclosure.builder().isDel(true).build();
        enclosureMapper.updateByExampleSelective(enclosure, example);
        List<Enclosure> enclosures = enclosureMapper.selectByExample(example);
        List<Long> l = new ArrayList<>();
        for (Enclosure e : enclosures) {
            Long enclosureId = e.getEnclosureId();
            l.add(enclosureId);
        }
        Long[] ids = (Long[]) l.toArray();
//        switch (type) {
//            case "体检报告":
//                reportEnclosureService.delete(ids);
//                break;
//            case "丢失调查":
//                doseLoseEnclosureService.delete(ids);
//                break;
//            case "干预通知":
//                doseInterventionEnclosureService.delete(ids);
//                break;
//            case "异常通知":
//                doseAbnormalEnclosureService.delete(ids);
//                break;
//            case "计量档案":
//                personalArchivesEnclosureService.delete(ids);
//                break;
//            case "计量申报":
//                personalDeclareEnclosureService.delete(ids);
//                break;
//            case "计量信息":
//                doseInfoEnclosureService.delete(ids);
//                break;
//            case "计量对比":
//                doseInfoContrastEnclosureService.delete(ids);
//                break;
//            case "人员详情":
//                personnelInfoEnclosureService.delete(ids);
//                break;
//        }

    }

    @Override
    public void fileLoad(Long[] ids, HttpServletRequest request, HttpServletResponse response) {
        Example example = getExample(ids);
        List<Enclosure> enclosures = enclosureMapper.selectByExample(example);
        List<String> filePaths = new ArrayList<>();
        for (Enclosure e : enclosures) {
            String path = e.getEnclosurePath();
            filePaths.add(path);
        }
        HashSet h = new HashSet(filePaths);
        filePaths.clear();
        filePaths.addAll(h);
        FileLoadUtil.feedBackDirectMultiDownload(filePaths, request, response);
    }

    @Override
    public void fileOneLoad(Long id, HttpServletRequest request, HttpServletResponse response) {
        Enclosure enclosure = enclosureMapper.selectByPrimaryKey(id);
        FileLoadUtil.download(enclosure.getEnclosurePath(), response);
    }


    //条件 批量删除使用
    public Example getExample(Long[] ids) {
        if (!Validator.valid(ids)) {
            throw new BusinessException(BizErrorCode.ID_NULL);
        }
        List<Long> idss = Arrays.asList(ids);
        Example example = new Example(Enclosure.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("enclosureId", idss);
        return example;
    }

}
