package com.tzy.common.util.file;

import org.apache.commons.io.IOUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

/**
 * @Created by Heaton on 2018/5/15.
 * @describe PDF在线阅览工具
 */
public class FilePDFUtil {

    public static void displayPDF(String filePath, HttpServletResponse response) {
        try {
            File file = new File(filePath);
            FileInputStream fileInputStream = new FileInputStream(file);
//            response.setHeader("Content-Disposition", "attachment;fileName=test.pdf");
//            response.setContentType("multipart/form-data");
            OutputStream outputStream = response.getOutputStream();
            IOUtils.write(IOUtils.toByteArray(fileInputStream), outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
