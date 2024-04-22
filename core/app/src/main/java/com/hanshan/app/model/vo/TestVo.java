package com.hanshan.app.model.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TestVo {
    private Integer id;
    private String name;
    private Integer age;
    private String sex;
}
