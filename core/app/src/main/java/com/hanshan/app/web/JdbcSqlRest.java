package com.hanshan.app.web;

import com.hanshan.app.AppContext;
import com.hanshan.app.exception.NoServerException;
import com.hanshan.app.exception.ParamErrorException;
import com.hanshan.app.model.query.ServerQuery;
import com.hanshan.app.model.query.TableQuery;
import com.hanshan.app.model.request.ServerRequest;
import com.hanshan.app.model.request.ServerRequestEmpty;
import com.hanshan.app.model.vo.TableVo;
import com.hanshan.app.service.impl.AllSqlBaseService;
import com.hanshan.app.service.impl.SqlRunService;
import com.hanshan.common.pojo.model.ColumnMeta;
import com.hanshan.common.pojo.model.PrimaryInfo;
import com.hanshan.common.pojo.model.ServerInfo;
import com.hanshan.common.pojo.model.VFTSPInfo;
import com.hanshan.common.pojo.query.BatchSqlQuery;
import com.hanshan.common.pojo.query.ConnectQuery;
import com.hanshan.common.pojo.query.SqlQuery;
import com.hanshan.common.pojo.result.Result;
import com.hanshan.common.pojo.result.RunSqlResult;
import com.hanshan.common.types.ResponseEnum;
import com.hanshan.common.utils.DecryptUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("sql")
public class JdbcSqlRest {

    @Autowired
    private AllSqlBaseService allSqlService;


    @Autowired
    private SqlRunService sqlRunService;

    public static final Logger log = LoggerFactory.getLogger(JdbcSqlRest.class);
    @PostMapping("setServer")
    public Result setServer(@RequestBody ServerQuery server) throws Exception {
        String password = server.getServer().getPassword();
        if (StringUtils.isNotEmpty(password)) {
            password = DecryptUtils.decryptData(password);
            server.getServer().setPassword(password);
        }
        AppContext.ServerStoreMap.put(server.getServer().getServerId(), server);
        return Result.success();
    }


    @PostMapping("clearServer/{serverId}")
    public Result clearServer(@PathVariable("serverId")String serverId) throws Exception {
        if (StringUtils.isEmpty(serverId)) {
            return Result.runSqlError(ResponseEnum.PARAM_ERROR);
        }
        AppContext.ServerStoreMap.remove(serverId);
        return Result.success();
    }

    @PostMapping("testConnect")
    public Result testConnect(@RequestBody ConnectQuery request) throws Exception {
        ServerInfo server = request.getServer();
        if (request.getOriginPassword() == null || !request.getOriginPassword()) {
            server.setPassword(DecryptUtils.decryptData(server.getPassword()));
        }
        return allSqlService.testConnect(request);

    }



    @PostMapping("closeConnect")
    public Result closeConnect(@RequestBody ConnectQuery request) {
        log.info("关闭连接:库:{},schema:{}",request.getDb(),request.getSchema());
        return allSqlService.closeConnect(request);
    }

    @PostMapping("closeAllConnect")
    public Result closeConnect() {
        return allSqlService.closeAllConnect();
    }

    @PostMapping("tableQuery")
    public RunSqlResult<List<Map<String, Object>>> tableQuery(@RequestBody ServerRequest<SqlQuery> request) throws Exception {
        ConnectQuery connectQuery = request.getConnect();
        return sqlRunService.tableQuery(connectQuery, request.getData());
    }

    @PostMapping("query")
    public RunSqlResult<List<Map<String, Object>>> query(@RequestBody ServerRequest<SqlQuery> request) throws Exception {
        ConnectQuery connectQuery = request.getConnect();
        SqlQuery sqlQuery = request.getData();
        if (StringUtils.isEmpty(sqlQuery.getSql())) {
            return Result.runSqlError(ResponseEnum.PARAM_ERROR);
        }
        return sqlRunService.query(connectQuery, request.getData());
    }

    @PostMapping("exec")
    public RunSqlResult exec(@RequestBody ServerRequest<SqlQuery> request) throws Exception {
        ConnectQuery connectQuery = request.getConnect();
        return sqlRunService.exec(connectQuery, request.getData());
    }

    @PostMapping("multiExec")
    public List<RunSqlResult> multiExec(@RequestBody ServerRequest<BatchSqlQuery> request) throws Exception {
        ConnectQuery connectQuery = request.getConnect();
        return sqlRunService.multiExec(connectQuery, request.getData());
    }

    @PostMapping("runSql")
    public RunSqlResult runSql(@RequestBody ServerRequest<String> request) throws Exception {
        ConnectQuery connectQuery = request.getConnect();
        if (StringUtils.isEmpty(request.getData())) {
            return Result.runSqlError(ResponseEnum.PARAM_ERROR);
        }
        return sqlRunService.runSql(connectQuery, request.getData());
    }

    @PostMapping("runBatch")
    public Result<List<RunSqlResult>> runBatch(@RequestBody ServerRequest<BatchSqlQuery> request) throws Exception {
        ConnectQuery connectQuery = request.getConnect();

        if (request.getData().getBatchSql().isEmpty()) {
            return Result.runSqlError(ResponseEnum.PARAM_ERROR);
        }
        return Result.success(sqlRunService.runBatch(connectQuery, request.getData()));
    }

    @PostMapping("showDatabases")
    public Result<List<String>> showDatabase(@RequestBody ServerRequestEmpty request) throws Exception {
        ConnectQuery connectQuery = request.getConnect();
        if (StringUtils.isNotEmpty(connectQuery.getDb()) || StringUtils.isNotEmpty(connectQuery.getSchema())) {
            return Result.error(ResponseEnum.PARAM_ERROR);
        }
        return allSqlService.showDatabase(connectQuery);
    }

    @PostMapping("showSchemas")
    public Result<List<String>> showSchema(@RequestBody ServerRequestEmpty request) throws Exception {
        ConnectQuery connectQuery = request.getConnect();
        if (StringUtils.isEmpty(connectQuery.getDb())) {
            return Result.error(ResponseEnum.PARAM_ERROR);
        }
        return allSqlService.showSchema(connectQuery);
    }

    @PostMapping("showTables")
    public Result<List<VFTSPInfo>> showTables(@RequestBody ServerRequestEmpty request) throws Exception {
        ConnectQuery connectQuery = request.getConnect();
        if (StringUtils.isEmpty(connectQuery.getDb())) {
            return Result.error(ResponseEnum.PARAM_ERROR);
        }
        return allSqlService.showTables(request.getConnect());
    }

    @PostMapping("showViews")
    public Result<List<VFTSPInfo>> showViews(@RequestBody ServerRequestEmpty request) throws Exception {
        return allSqlService.showViews(request.getConnect());
    }

    @PostMapping("showFunctions")
    public Result<List<VFTSPInfo>> showFunctions(@RequestBody ServerRequestEmpty request) throws Exception {
        return allSqlService.showFunctions(request.getConnect());
    }

    @PostMapping("showProcedures")
    public Result<List<VFTSPInfo>> showProcedures(@RequestBody ServerRequestEmpty request) throws Exception {
        return allSqlService.showProduces(request.getConnect());
    }

    @PostMapping("showPrimary")
    public Result<List<PrimaryInfo>> showPrimary(@RequestBody ServerRequest<String> request) throws Exception {
        ConnectQuery connectQuery = request.getConnect();
        if (StringUtils.isEmpty(request.getData())) {
            return Result.runSqlError(ResponseEnum.PARAM_ERROR);
        }
        return allSqlService.showPrimary(connectQuery, request.getData());
    }

    @PostMapping("showColumns")
    public Result<List<ColumnMeta>> showColumn(@RequestBody ServerRequest<String> request) throws Exception {
        ConnectQuery connectQuery = request.getConnect();
        if (StringUtils.isEmpty(request.getData())) {
            return Result.runSqlError(ResponseEnum.PARAM_ERROR);
        }
        return allSqlService.showColumn(connectQuery, request.getData());
    }




    //table 数据查询
    public Result<TableVo> selectTable(@RequestBody ServerRequest<TableQuery> request) throws ParamErrorException, NoServerException {
        ConnectQuery connectQuery = request.getConnect();


        return null;
    }


    //table 数据删除

    //table 数据更新

    //table 数据新增


}
