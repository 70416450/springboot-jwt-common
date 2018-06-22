package com.tzy.common.biz.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name = "info_enclosure")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InfoEnclosure {
    /**
     * 附件中间表id
     */
    @Id
    @Column(name = "biz_enclosure_id")
    private Long bizEnclosureId;

    /**
     * 附件id
     */
    @Column(name = "enclosure_id")
    private Long enclosureId;

    /**
     * 业务id
     */
    @Column(name = "info_id")
    private Long infoId;

    /**
     * 是否删除
     */
    @Column(name = "is_del")
    private Boolean isDel;

}