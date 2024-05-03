package com.hanshan.app.web;

import com.hanshan.api.model.NameComment;
import com.hanshan.api.query.ConnectQuery;
import com.hanshan.api.result.Result;
import com.hanshan.app.AppContext;
import com.hanshan.app.exception.NoServerException;
import com.hanshan.app.exception.ParamErrorException;
import com.hanshan.app.model.query.ServerQuery;
import com.hanshan.app.model.request.ServerRequestEmpty;
import com.hanshan.app.service.impl.AllSqlBaseService;
import com.hanshan.common.types.ResponseEnum;
import org.apache.commons.lang3.StringUtils;
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
    private AllSqlBaseService allSqlService;

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
        ConnectQuery connectQuery = request.getConnect();
        if (StringUtils.isNotEmpty(connectQuery.getDb()) || StringUtils.isNotEmpty(connectQuery.getSchema())) {
            return Result.error(ResponseEnum.PARAM_ERROR);
        }
        return allSqlService.showDatabase(connectQuery);
    }

    @PostMapping("showSchema")
    public Result<List<String>> showSchema(@RequestBody ServerRequestEmpty request) throws ParamErrorException, NoServerException {
        ConnectQuery connectQuery = request.getConnect();
        if (StringUtils.isEmpty(connectQuery.getDb())) {
            return Result.error(ResponseEnum.PARAM_ERROR);
        }
        return allSqlService.showSchema(connectQuery);
    }

    @PostMapping("showTables")
    public Result<List<NameComment>> showTables(@RequestBody ServerRequestEmpty request) throws ParamErrorException, NoServerException {
        ConnectQuery connectQuery = request.getConnect();
        if (StringUtils.isEmpty(connectQuery.getDb())) {
            return Result.error(ResponseEnum.PARAM_ERROR);
        }
        return allSqlService.showTables(request.getConnect());
    }

    @PostMapping("showViews")
    public Result<List<NameComment>> showViews(@RequestBody ServerRequestEmpty request) throws ParamErrorException, NoServerException {
        return allSqlService.showViews(request.getConnect());
    }
    //table 数据查询

    //table 数据删除

    //table 数据更新

    //table 数据新增


}
