package com.hanshan.common.pojo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * view function trigger sequence produce展示类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VFTSPInfo {
    private String name;
    private String comment;
    private String tableName;
}
