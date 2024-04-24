package com.hanshan.app.web;

import com.hanshan.api.result.Result;
import com.hanshan.app.model.query.Request;
import com.hanshan.app.service.impl.AllSqlService;
import com.hanshan.sqlbase.meta.ColumnMeta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("jdbc_base")
public class JdbcBaseRest {

    @Autowired
    private AllSqlService allSqlService;
    @PostMapping("showDatabase")
    public Result<Object> showDatabase(@RequestBody Request request){
        return allSqlService.showDatabase(request.getConnect());
    }

}
