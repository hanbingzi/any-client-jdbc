package com.hanshan.app.web;

import com.hanshan.api.query.ConnectQuery;
import com.hanshan.api.query.SqlQuery;
import com.hanshan.api.result.RunSqlResult;
import com.hanshan.app.exception.NoServerException;
import com.hanshan.app.exception.ParamErrorException;
import com.hanshan.app.model.request.ServerRequest;
import com.hanshan.app.service.impl.SqlRunService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("jdbcQuery")
public class JdbcQueryRest {

    @Autowired
    private SqlRunService sqlRunService;

    @PostMapping("query")
    public RunSqlResult<List<Map<String, Object>>> query(@RequestBody ServerRequest<SqlQuery> request) throws ParamErrorException, NoServerException {
        ConnectQuery connectQuery = request.getConnect();
        return sqlRunService.query(connectQuery,request.getData());
    }
}
