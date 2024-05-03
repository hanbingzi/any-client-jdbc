package com.hanshan.app.model;

import lombok.Data;

@Data
public class Page {
    //当前页码
   private Integer page;

    //当前页多少条
  private Integer  pageCount;

    //总共多少条
   private Integer total;

    //每页默认多少条
  private Integer  pageSize;
}
