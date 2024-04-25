package com.hanshan.app.web;

import com.hanshan.api.model.NameComment;
import com.hanshan.api.query.ConnectQuery;
import com.hanshan.api.result.Result;
import com.hanshan.app.AppContext;
import com.hanshan.app.exception.NoServerException;
import com.hanshan.app.exception.ParamErrorException;
import com.hanshan.app.model.query.ServerQuery;
import com.hanshan.app.model.request.ServerRequestEmpty;
import com.hanshan.app.service.impl.AllSqlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("jdbcBase")
public class JdbcBaseRest {

    @Autowired
    private AllSqlService allSqlService;

    @PostMapping("setServer")
    public Result setServer(@RequestBody ServerQuery server) {
        AppContext.ServerStoreMap.put(server.getServer().getServerId(), server);
        return Result.success();
    }

    @PostMapping("testConnect")
    public Result testConnect(@RequestBody ConnectQuery request) {
        return allSqlService.testConnect(request);

    }

    @PostMapping("closeConnect")
    public Result closeConnect(@RequestBody ConnectQuery request) {
        return allSqlService.closeConnect(request);

    }

    @PostMapping("showDatabase")
    public Result<List<String>> showDatabase(@RequestBody ServerRequestEmpty request) throws ParamErrorException, NoServerException {
        return allSqlService.showDatabase(request.getConnect());
    }

    @PostMapping("showSchema")
    public Result<List<String>> showSchema(@RequestBody ServerRequestEmpty request) throws ParamErrorException, NoServerException {
        return allSqlService.showSchema(request.getConnect());
    }

    @PostMapping("showTables")
    public Result<List<NameComment>> showTables(@RequestBody ServerRequestEmpty request) throws ParamErrorException, NoServerException {
        return allSqlService.showTables(request.getConnect());
    }

    @PostMapping("showViews")
    public Result<List<NameComment>> showViews(@RequestBody ServerRequestEmpty request) throws ParamErrorException, NoServerException {
        return allSqlService.showViews(request.getConnect());
    }

}
