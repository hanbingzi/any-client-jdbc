package com.hanshan;

import com.hanshan.api.model.DbInfo;
import com.hanshan.api.model.WhereParam;

import java.util.List;

public interface BaseSqlDialect {

    /*-----服务器操作-------------------------*/
    //pingDialect(database?: string | number): string;

    //String useDataBase(String database);

    //String useSchema(String schema);

    /*--------show-------------------------*/

    //showIndex(dbInfo: IDbInfo, table: string): string;

//    showDatabases(): string;

//    showDatabaseInfo(db: string): string;

//    showSchemas(): string;

//    showSchemaInfo(schema: string): string;

//    showTables(dbInfo: IDbInfo): string;

//    showColumns(dbInfo: IDbInfo, table: string): string;

//    showMultiTableColumns(dbInfo: IDbInfo, tables: string[]): string;

//    showViews(dbInfo: IDbInfo): string;

//    showViewSource(dbInfo: IDbInfo, table: string): string;

//    showUsers(): string;

//    showPrimary(dbInfo: IDbInfo, table: string): string;

//    showTriggers(dbInfo: IDbInfo): string;

//    showTrigger(dbInfo: IDbInfo, trigger: string): string;

//    showTriggerSource(dbInfo: IDbInfo, trigger: string): string;

//    showProcedures(dbInfo: IDbInfo): string;

//    showProcedure(dbInfo: IDbInfo, procedure: string): string;

//    showProcedureSource(dbInfo: IDbInfo, name: string): string;

//    showFunctions(dbInfo: IDbInfo): string;

//    showFunction(dbInfo: IDbInfo, _function: string): string;

//    showFunctionSource(dbInfo: IDbInfo, name: string): string;

//    showSequences(dbInfo: IDbInfo): string;

//    showSequence(dbInfo: IDbInfo, sequence: string): string;

//    showSequenceSource(dbInfo: IDbInfo, sequence: string): string;

//    showTriggerSource(dbInfo: IDbInfo, name: string): string;

//    showTableSource(dbInfo: IDbInfo, table: string): string;

//    showVariableList(): string;

//    showStatusList(): string;

//    showTableInfo(dbInfo: IDbInfo, tableName: string): string;

    /*-----------select-------------------------*/
//    selectSql(dbInfo: IDbInfo, table: string): string;

//    String selectByWhere(DbInfo dbInfo, String table, List<WhereParam> whereParams);

//    String selectSqlByPage(String sql,Integer page, Integer pageSize);

//    selectTableByPage(sql: string, page?: number, pageSize?: number): string;

    /*------create-------------------------*/
//    createDb(param: IDbDetail): string[];

//    createSchema(param: IDbDetail): string[];

//    createTable(dbInfo: IDbInfo, tableParam: CreateTableParam): string[];

//    createColumn(dbInfo: IDbInfo, table: string, createColumnParam: CreateColumnParam): string[];

//    createUser(): string;

//    createDatabase(database: string): string;

//    createIndex(createIndexParam: CreateIndexParam): string;

    /*-----------drop-------------------------*/

//    dropDatabase(database: string): string;

//    dropSchema(schema: string): string;

//    dropTable(dbInfo: IDbInfo, table: string): string;

//    dropView(dbInfo: IDbInfo, view: string): string;

//    dropFunction(dbInfo: IDbInfo, name: string): string;

//    dropSequence(dbInfo: IDbInfo, name: string): string;

//    dropProcedure(dbInfo: IDbInfo, name: string): string;

//    dropTrigger(dbInfo: IDbInfo, name: string, tableName?: string): string;

//    dropIndex(table: string, indexName: string): string;

//    dropTriggerTemplate(name: string): string;

    /*-----------rename-------------------------*/

    /*-----------count-------------------------*/

//    countTable(dbInfo: IDbInfo, table: string): string;

//    countBySql(sql: string): string;

//    countPrimary(dbInfo: IDbInfo, table: string): string;

    //查询当前库是否存在
//    countByDatabase(dbInfo: IDbInfo): string;

    //查询当前表是否存在
//    countByTable(dbInfo: IDbInfo, table: string): string;

    /*-----------alter-------------------------*/

//    alterDb(param: IDbDetail): string[];

//    alterSchema(param: IDbDetail): string[];

//    alterTable(dbInfo: IDbInfo, update: UpdateTableParam): string;

//    alterColumn(dbInfo: IDbInfo, table: string, column: string, type: string, comment: string, nullable: string): string;

//    alterColumnSql(dbInfo: IDbInfo, table: string, updateColumnParam: UpdateColumnParam): string[];

//    alterAutoIncrementSql(dbInfo: IDbInfo, table: string, updateColumnParam: UpdateColumnParam): string;

//    alterTableToRename(dbInfo: IDbInfo, oldName: string, newName: string): string;

//    alterColumnToSortSql(dbInfo: IDbInfo, table: string, sortColumnParam: SortColumnParam): string;

    /*-----------update-------------------------*/
//    updatePrimaryKey(dbInfo: IDbInfo, existPrimaryKeys: IPrimaryMeta[], table: string, primaryKeys?: string[]): string[];

    /*-----------delete-----------------------*/
//    deleteAllData(dbInfo: IDbInfo, table: string): string;

    //删除表格数据
//    delete(
//            dbInfo: IDbInfo,
//            table: string,
//            primaryKey: string,
//            ids: number[] | string[],
//            primaryType: DataInputEnum,
//            ): string;

//    deleteByCompositeKey(dbInfo: IDbInfo, table: string, compositePrimaryKeys: CompositeKeyParam[]): string;

//    deleteColumn(dbInfo: IDbInfo, table: string, columnName: string): string;

    /*-----------build-------------------------*/

//    buildUpdateData(dbInfo: IDbInfo, table: string, updateData: UpdateParam): string;

//    buildUpdateDataByCompositeKey(dbInfo: IDbInfo, table: string, updateData: UpdateCompositeKeyParam): string;

//    buildCreateTableSql(
//            dbInfo: IDbInfo,
//            tableInfo: ITableMeta,
//            columns: IColumnMeta[],
//            primaryKeys?: IPrimaryMeta[],
//            ): string;

    /*-----------example-------------------------*/

//    selectExample(tableName: string, columns: string[]): string;

//    insertExample(tableName: string, columns: IColumnMeta[]): string;

//    updateExample(tableName: string, columns: IColumnMeta[]): string;

//    deleteExample(tableName: string, primary: IPrimaryMeta[]): string;

    /*-----------template-------------------------*/

//    tableTemplate(): string;

//    viewTemplate(): string;

//    procedureTemplate(): string;

//    triggerTemplate(): string;

//    functionTemplate(): string;

//    truncateDatabase(dbInfo: IDbInfo): string;
}
