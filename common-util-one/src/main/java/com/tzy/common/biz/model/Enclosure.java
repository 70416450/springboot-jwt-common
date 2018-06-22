package com.tzy.common.biz.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "t_enclosure")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Enclosure {
    /**
     * 附件id
     */
    @Id
    @Column(name = "enclosure_id")
    private Long enclosureId;

    /**
     * 上传时间
     */
    @Column(name = "upload_time")
    private Date uploadTime;

    /**
     * 文件编号
     */
    @Column(name = "file_number")
    private String fileNumber;

    /**
     * 文件名称
     */
    @Column(name = "file_name")
    private String fileName;

    /**
     * 文件存储名称
     */
    @Column(name = "file_storage_name")
    private String fileStorageName;

    /**
     * 文件大小
     */
    @Column(name = "file_size")
    private Double fileSize;

    /**
     * 附件存放路径
     */
    @Column(name = "enclosure_path")
    private String enclosurePath;

    /**
     * 文件来源
     */
    @Column(name = "file_source")
    private String fileSource;

    /**
     * 上传人
     */
    @Column(name = "upload_person")
    private String uploadPerson;

    /**
     * 是否删除
     */
    @Column(name = "is_del")
    private Boolean isDel;

}