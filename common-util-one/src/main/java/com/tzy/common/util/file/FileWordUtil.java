package com.tzy.common.util.file;

import cn.afterturn.easypoi.word.WordExportUtil;
import com.tzy.common.util.DateUtil;
import com.tzy.common.util.id.IdUtil;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Created by Heaton on 2018/5/15.
 * @describe poi Word
 */
public class FileWordUtil {

    public void imageWordExport() {
       /* Map<String, Object> map = new HashMap<String, Object>();
        map.put("department", "Easypoi");
        map.put("person", "JueYue");
        map.put("time", DateUtil.timeStamp2Date(DateUtil.timeStamp(),null));
        WordImageEntity image = new WordImageEntity();
        image.setHeight(200);
        image.setWidth(500);
        image.setUrl("cn/afterturn/easypoi/test/word/img/testCode.png");
        image.setType(WordImageEntity.URL);
        map.put("testCode", image);
        try {
            XWPFDocument doc = WordExportUtil.exportWord07(
                    "cn/afterturn/easypoi/test/word/doc/Image.docx", map);
            FileOutputStream fos = new FileOutputStream("D:/excel/image.docx");
            doc.write(fos);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        System.out.println("1");
    }

    /**
     * @param [map模版需要字段的集合, templatesPath模版路径, targetPath下载路径,name 文件名字]
     * @return void
     * @date 2018/5/16 16:37
     * @describe 简单导出没有图片
     */
    public static void SimpleWordExport(Object obj, String templatesPath, String targetPath, String name) {
        Map<String, Object> map = BeanCollectionInterconversionUtil.transBean2Map(obj);
        String finalPath = targetPath + DateUtil.date2String(new Date(), "yyyy-MM-dd") + name + IdUtil.generateId() + ".docx";
        try (FileOutputStream fos = new FileOutputStream(finalPath)) {
            XWPFDocument doc = WordExportUtil.exportWord07(templatesPath, map);
            File file = new File(finalPath);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
            }
            doc.write(fos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void SimpleWordExport(HttpServletResponse httpServletResponse, Object obj, String templatesPath, String targetPath, String name) {
        Map<String, Object> map;
        if (obj instanceof Map) {
            map = (Map<String, Object>) obj;
        } else {
            map = BeanCollectionInterconversionUtil.transBean2Map(obj);
        }
        String finalPath = targetPath + DateUtil.date2String(new Date(), "yyyy-MM-dd") + name + IdUtil.generateId() + ".docx";
        File file = new File(finalPath);
        try (FileOutputStream fos = new FileOutputStream(finalPath)) {
            XWPFDocument doc = WordExportUtil.exportWord07(templatesPath, map);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
            }
            doc.write(fos);
            FileLoadUtil.download(finalPath, httpServletResponse);

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            file.delete();
        }
    }


    public static void main(String[] args) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("department", "Easypoi");
        map.put("person", "JueYue");
        map.put("time", DateUtil.timeStamp2Date(DateUtil.timeStamp(), null));
        map.put("me", "JueYue");
        map.put("date", "2015-01-03");
        String path = "C:\\Users\\70416\\Desktop\\abd\\asd\\simple.docx";
        int i = path.lastIndexOf(".");
        String overPath = path.substring(0, i) + IdUtil.generateId() + path.substring(i);
        try {
            XWPFDocument doc = WordExportUtil.exportWord07("C:\\Users\\70416\\Desktop\\testword.docx", map);
            File file = new File(overPath);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
            } else {

            }
            FileOutputStream fos = new FileOutputStream(overPath);

            doc.write(fos);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
