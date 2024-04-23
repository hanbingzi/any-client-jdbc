package com.hanshan.app.service.impl;

import com.hanshan.app.model.vo.TestVo;
import com.hanshan.app.service.ITestService;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TestService implements ITestService {

    private static final Map<String, URLClassLoader> loaderMap = new HashMap<>();
    public static final String selectFirst = "SELECT * FROM test.students where id=3";
    public static final String selectAll = "";
    public static final String update = "";
    public static final String delete = "";

    private static final Logger logger = LoggerFactory.getLogger(TestService.class);

    public TestVo test() {
        TestVo test = new TestVo();
        test.setName("test");
        String mysqlDriver = "com.mysql.cj.jdbc.Driver";
        checkClass(mysqlDriver);
        logger.info("test");
        // 2. 创建 HikariConfig 对象
        HikariConfig config = new HikariConfig();
        // 3. 设置数据库驱动类名
        config.setDriverClassName(mysqlDriver);
        // 4. 设置数据库连接 URL
        config.setJdbcUrl("jdbc:mysql://111.67.196.4:3306");
        // 5. 设置数据库用户名和密码
        config.setUsername("root");
        config.setPassword("oracle123iop");
        // 6. 设置 HikariCP 连接池属性
        config.setMaximumPoolSize(10);
        config.setMinimumIdle(5);
        // 7. 创建 HikariCP 数据源
        HikariDataSource dataSource = new HikariDataSource(config);
        // 8. 使用数据源进行数据库操作
        // 例如，执行查询操作
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet dbResultSet = null;
        ResultSet schemasSet = null;
        ResultSet dataResultSet = null;
        try {
            connection = dataSource.getConnection();
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            //databaseMetaData.getTables()
            dbResultSet = databaseMetaData.getCatalogs();
            schemasSet = databaseMetaData.getSchemas();
            ArrayList<String> dbs = new ArrayList<>();
            while (dbResultSet.next()) {
                String catalog = dbResultSet.getString(1);
                dbs.add(catalog);
            }
            ArrayList<String> schemas = new ArrayList<>();
            while (schemasSet.next()) {
                schemas.add(schemasSet.getString(1));
            }

            test.setDbs(dbs);
            test.setSchemas(schemas);
            statement = connection.prepareStatement(selectFirst);
            dataResultSet = statement.executeQuery();
            ResultSetMetaData metaData = dataResultSet.getMetaData();
            test.setMeta(metaData);
            int columnCount = metaData.getColumnCount();
            //test.setData(resultSet.g);
            // test.setData(resultSet.getMetaData().getCatalogName());
            // 处理结果集
            List<List<String>> data = new ArrayList<>();
            while (dataResultSet.next()) {
                List<String> item = new ArrayList<>();
                // 遍历每行数据的各个列
                for (int i = 1; i <= columnCount; i++) {
                    // 获取列名和数据类型
                    String columnName = metaData.getColumnName(i);
                    int columnType = metaData.getColumnType(i);
                    // 根据数据类型获取对应的字段值
                    Object columnValue = null;
                    switch (columnType) {
                        case Types.INTEGER:
                            columnValue = dataResultSet.getInt(i);
                            break;
                        case Types.VARCHAR:
                            columnValue = dataResultSet.getString(i);
                            break;
                        default:
                            columnValue = dataResultSet.getObject(i);
                            // 其他数据类型的处理...
                    }
                    // 处理字段值...
                    item.add(columnName + ": " + columnValue);
                }
                data.add(item);
            }
            test.setData(data);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (dataResultSet != null) {
                    dataResultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        return test;
    }

    private void checkClass(String driver) {
        if (!loaderMap.containsKey(driver)) {
            this.loadDriver();
        }

    }

    /**
     * 手动加载驱动jar包
     */
    @SneakyThrows
//    @PostConstruct
    private void loadDriver() {
        String driver = "com.mysql.cj.jdbc.Driver";
        String DriverJarPath = "/Users/yanqi/devData/myDevGitHub/level5/any-client-jdbc/plugins/Mysql/mysql-connector-java-8.0.28.jar";
        //String DriverJarPath = "/Users/yanqi/devData/myDevGitHub/level5/any-client-jdbc/plugins/Mysql/mysql-connector-j-8.0.33.jar";
        if (!new File(DriverJarPath).exists()) {
            throw new RuntimeException("Driver " + DriverJarPath + " not exists!");
        }
        URL u = new URL("jar:file://" + DriverJarPath + "!/");
        URLClassLoader classLoader = new URLClassLoader(new URL[]{u});
        // 设置线程上下文类加载器
        //Thread.currentThread().setContextClassLoader(classLoader);
        //Driver driverClass = (Driver) Class.forName(driver, true, classLoader).newInstance();
        Class<?> driverClass = classLoader.loadClass(driver);
        //DriverManager.registerDriver(new DriverShim(d));
        DriverManager.registerDriver((Driver) driverClass.getDeclaredConstructor().newInstance());
        // 设置加载本地 JAR 文件的路径
        // 创建 URLClassLoader 并加载本地 JAR 文件
        //URLClassLoader classLoader = new URLClassLoader(new URL[]{new URL("file://" + DriverJarPath)});
        //Thread.currentThread().setContextClassLoader(classLoader);
        // 加载驱动类
        //Class<?> driverClass = classLoader.loadClass(driver);
        // 注册驱动
        //DriverManager.registerDriver((java.sql.Driver) driverClass.newInstance());
        loaderMap.put(driver, classLoader);
        classLoader.close();
    }
}
