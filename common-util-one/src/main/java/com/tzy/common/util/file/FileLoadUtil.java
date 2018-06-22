package com.tzy.common.util.file;

import com.tzy.common.exception.BusinessException;
import com.tzy.common.exception.CommonErrorCode;
import com.tzy.common.util.DateUtil;
import com.tzy.common.util.MathUtil;
import com.tzy.common.util.id.IdUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @Created by Heaton on 2018/5/15.
 * @describe 上传下载
 */
@Slf4j
@NoArgsConstructor
public class FileLoadUtil {

    public static List<MyFileInfo> simpleUpLoad(MultipartFile[] multipartfiles) {
        return upLoad(null, multipartfiles);
    }

    public static List<MyFileInfo> simpleUpLoad(String filePath, MultipartFile[] multipartfiles) {
        return upLoad(filePath, multipartfiles);
    }


    /**
     * @param [filePath, request]
     * @return void
     * @date 2018/5/15 15:23
     * @describe 文件上传
     */
    public static List<MyFileInfo> upLoad(String filePath, MultipartFile[] multipartfiles) {
        // 定义上传路径
        String s = DateUtil.date2String(new Date(), "yyyy-MM-dd");
        String path;
        List<MyFileInfo> list = new ArrayList<>();
        path = filePath + s + "\\";
        try {
            if (null != multipartfiles && multipartfiles.length > 0) {
                //遍历并保存文件
                for (MultipartFile fileUp : multipartfiles) {
                    String oldName = fileUp.getOriginalFilename();
                    if ("".equals(oldName)) {
                        continue;
                    }
                    long id = IdUtil.generateId();  //sonwFlake算法
                    String newName = id + "_" + oldName;
                    File fileEx = new File(path + newName);
                    int i = filePath.lastIndexOf("\\");
                    String source = filePath.substring(i + 1, filePath.length());
                    MyFileInfo fileInfo = MyFileInfo.builder().enclosureId(id).uploadTime(new Date()).fileNumber("" + id)
                            .fileName(oldName).fileStorageName(newName)
                            .fileSize(MathUtil.div(Double.parseDouble((fileUp.getSize() + "")), MathUtil.mul(1024D, 1024D)))
                            .enclosurePath(path + newName).fileSource(source).build();
                    list.add(fileInfo);
                    if (!fileEx.exists()) {
                        // 先得到文件的上级目录，并创建上级目录，在创建文件,只有一级
                        fileEx.getParentFile().mkdirs();
                        fileUp.transferTo(fileEx);
                    } else {
                        fileUp.transferTo(fileEx);//就算有也输出
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            log.error("文件上传异常");
            throw new BusinessException(CommonErrorCode.FILE_UPLOAD_ERROR);
        }
        return list;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MyFileInfo {
        /**
         * 附件id
         */
        private Long enclosureId;
        /**
         * 上传时间
         */
        private Date uploadTime;
        /**
         * 文件编号
         */
        private String fileNumber;
        /**
         * 文件老名称
         */
        private String fileName;
        /**
         * 文件存储新名称
         */
        private String fileStorageName;
        /**
         * 文件大小
         */
        private Double fileSize;
        /**
         * 附件存放路径
         */
        private String enclosurePath;
        /**
         * 文件来源(那个业务)
         */
        private String fileSource;
    }

    //文件下载(单独)
    public static void download(String path, HttpServletResponse response) {
        try {

            File file = new File(path);
            // 取得文件名。
            String filename = file.getName();
            filename = URLEncoder.encode(filename, "UTF-8");
            // 以流的形式下载文件。
            InputStream fis = new BufferedInputStream(new FileInputStream(path));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            response.reset();
            // 设置response的Header
            //设置文件名
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes()));
            //设置文件大小
            response.addHeader("Content-Length", "" + file.length());
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    //批量下载
    public static Map<String, Object> feedBackDirectMultiDownload(List<String> filePaths, HttpServletRequest request, HttpServletResponse response) {
        try {
            //压缩文件初始设置
            String path = "D:\\templates";//压缩文件想要放置的路径
            String base_name = "测试文件";//压缩文件名字
            String fileZip = base_name + ".zip"; // 拼接zip文件
            String filePath = path + "\\" + fileZip;//之后用来生成zip文件

            //文件集合
            File[] files = new File[filePaths.size()];//
            for (int i = 0; i < filePaths.size(); i++) {
                files[i] = new File(filePaths.get(i));//获取所有需要下载的文件
            }

            // 创建临时压缩文件

            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
            ZipOutputStream zos = new ZipOutputStream(bos);
            ZipEntry ze = null;
            for (int i = 0; i < files.length; i++) {//将所有需要下载的文件都写入临时zip文件
                BufferedInputStream bis = new BufferedInputStream(new FileInputStream(files[i]));
                ze = new ZipEntry(files[i].getName());
                zos.putNextEntry(ze);
                int s = -1;
                while ((s = bis.read()) != -1) {
                    zos.write(s);
                }
                bis.close();
            }
            zos.flush();
            zos.close();

            //以上，临时压缩文件创建完成

            //进行浏览器下载
            //获得浏览器代理信息
            final String userAgent = request.getHeader("USER-AGENT");
            //判断浏览器代理并分别设置响应给浏览器的编码格式
            String finalFileName = null;
            if (StringUtils.contains(userAgent, "MSIE") || StringUtils.contains(userAgent, "Trident")) {//IE浏览器
                finalFileName = URLEncoder.encode(fileZip, "UTF8");
                System.out.println("IE浏览器");
            } else if (StringUtils.contains(userAgent, "Mozilla")) {//google,火狐浏览器
                finalFileName = new String(fileZip.getBytes(), "ISO8859-1");
            } else {
                finalFileName = URLEncoder.encode(fileZip, "UTF8");//其他浏览器
            }
            response.setContentType("application/x-download");//告知浏览器下载文件，而不是直接打开，浏览器默认为打开
            response.setHeader("Content-Disposition", "attachment;filename=\"" + finalFileName + "\"");//下载文件的名称

            ServletOutputStream servletOutputStream = response.getOutputStream();
            DataOutputStream temps = new DataOutputStream(
                    servletOutputStream);

            DataInputStream in = new DataInputStream(
                    new FileInputStream(filePath));//浏览器下载文件的路径
            byte[] b = new byte[2048];
            File reportZip = new File(filePath);//之后用来删除临时压缩文件
            try {
                while ((in.read(b)) != -1) {
                    temps.write(b);
                }
                temps.flush();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (temps != null) temps.close();
                if (in != null) in.close();
                if (reportZip != null) reportZip.delete();//删除服务器本地产生的临时压缩文件
                servletOutputStream.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //压缩包批量下载
    public static Map<String, Object> listDownload(List<String> filePaths, HttpServletRequest request, HttpServletResponse response) {
        try {
            //压缩文件初始设置
            String path = "C:";//压缩文件想要放置的路径
            String base_name = "公司文件";//压缩文件名字
            String fileZip = base_name + ".zip"; // 拼接zip文件
            String filePath = path + "\\" + fileZip;//之后用来生成zip文件

            //文件集合
            File[] files = new File[filePaths.size()];//
            for (int i = 0; i < filePaths.size(); i++) {
                files[i] = new File(filePaths.get(i));//获取所有需要下载的文件
            }

            // 创建临时压缩文件

            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
            ZipOutputStream zos = new ZipOutputStream(bos);
            ZipEntry ze = null;
            for (int i = 0; i < files.length; i++) {//将所有需要下载的文件都写入临时zip文件
                BufferedInputStream bis = new BufferedInputStream(new FileInputStream(files[i]));
                ze = new ZipEntry(files[i].getName());
                zos.putNextEntry(ze);
                int s = -1;
                while ((s = bis.read()) != -1) {
                    zos.write(s);
                }
                bis.close();
            }
            zos.flush();
            zos.close();

            //以上，临时压缩文件创建完成

            //进行浏览器下载
            //获得浏览器代理信息
            final String userAgent = request.getHeader("USER-AGENT");
            //判断浏览器代理并分别设置响应给浏览器的编码格式
            String finalFileName = null;
            if (StringUtils.contains(userAgent, "MSIE") || StringUtils.contains(userAgent, "Trident")) {//IE浏览器
                finalFileName = URLEncoder.encode(fileZip, "UTF8");
                System.out.println("IE浏览器");
            } else if (StringUtils.contains(userAgent, "Mozilla")) {//google,火狐浏览器
                finalFileName = new String(fileZip.getBytes(), "ISO8859-1");
            } else {
                finalFileName = URLEncoder.encode(fileZip, "UTF8");//其他浏览器
            }
            response.setContentType("application/x-download");//告知浏览器下载文件，而不是直接打开，浏览器默认为打开
            response.setHeader("Content-Disposition", "attachment;filename=\"" + finalFileName + "\"");//下载文件的名称

            ServletOutputStream servletOutputStream = response.getOutputStream();
            DataOutputStream temps = new DataOutputStream(
                    servletOutputStream);

            DataInputStream in = new DataInputStream(
                    new FileInputStream(filePath));//浏览器下载文件的路径
            byte[] b = new byte[2048];
            File reportZip = new File(filePath);//之后用来删除临时压缩文件
            try {
                while ((in.read(b)) != -1) {
                    temps.write(b);
                }
                temps.flush();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (temps != null) temps.close();
                if (in != null) in.close();
                if (reportZip != null) reportZip.delete();//删除服务器本地产生的临时压缩文件
                servletOutputStream.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
