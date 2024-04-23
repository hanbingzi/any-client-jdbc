package com.hanshan.app.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestVo {
    private Integer id;
    private String name;
    private List<String> dbs;
    private List<String> schemas;
    private Object meta;
    private Object data;
}
