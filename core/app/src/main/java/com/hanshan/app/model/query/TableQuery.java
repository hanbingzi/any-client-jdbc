package com.hanshan.app.model.query;

import com.hanshan.app.model.Page;
import lombok.Data;

@Data
public class TableQuery {

    private String table;

    private String filterParams;

    private Page page;



}
