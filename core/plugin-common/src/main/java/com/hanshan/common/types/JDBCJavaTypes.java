package com.hanshan.common.types;

import java.util.List;

//    | `boolean` | `BOOLEAN` (16) | `setBoolean(int parameterIndex, boolean x)` |
//            | `byte` | `TINYINT` (-6) | `setByte(int parameterIndex, byte x)` |
//            | `short` | `SMALLINT` (5) | `setShort(int parameterIndex, short x)` |
//            | `int` | `INTEGER` (4) | `setInt(int parameterIndex, int x)` |
//            | `long` | `BIGINT` (-5) | `setLong(int parameterIndex, long x)` |
//            | `float` | `REAL` (7) | `setFloat(int parameterIndex, float x)` |
//            | `double` | `DOUBLE` (8) | `setDouble(int parameterIndex, double x)` |
//            | `java.math.BigDecimal` | `NUMERIC` (2), `DECIMAL` (3) | `setBigDecimal(int parameterIndex, BigDecimal x)` |
//            | `String` | `CHAR` (1), `VARCHAR` (12), `LONGVARCHAR` (-1) | `setString(int parameterIndex, String x)` |
//            | `java.sql.Date` | `DATE` (91) | `setDate(int parameterIndex, Date x)` |
//            | `java.sql.Time` | `TIME` (92) | `setTime(int parameterIndex, Time x)` |
//            | `java.sql.Timestamp` | `TIMESTAMP` (93) | `setTimestamp(int parameterIndex, Timestamp x)` |
//            | `byte[]` | `BINARY` (-2), `VARBINARY` (-3), `LONGVARBINARY` (-4) | `setBytes(int parameterIndex, byte[] x)` |
//            | `java.sql.Blob` | `BLOB` (2004) | `setBlob(int parameterIndex, Blob x)` |
//            | `java.sql.Clob` | `CLOB` (2005) | `setClob(int parameterIndex, Clob x)` |
//            | `java.net.URL` | `DATALINK` (70) | `setURL(int parameterIndex, URL x)` |
//            | `Object` | `JAVA_OBJECT` (2000) | `setObject(int parameterIndex, Object x)` |
//            | `Object` | `OTHER` (1111) | `setObject(int parameterIndex, Object x, int targetSqlType)` |
public enum JDBCJavaTypes {
    //name需要保持与js一致，所有采用大小驼峰写法
    _BOOLEAN("boolean", List.of(16)),
    _BYTE("byte", List.of(-6)),
    _SHORT("short", List.of(5)),
    _INT("int", List.of(4)),
    _LONG("long", List.of(-5)),
    _FLOAT("float", List.of(7)),
    _DOUBLE("double", List.of(8)),
    _BIGDECIMAL("bigDecimal", List.of(2, 3)),
    //
    _STRING("string", List.of(1, 12, -1,-9)),
    _DATE("date", List.of(91)),
    _YEAR("year", List.of(91)),
    _TIME("time", List.of(92)),
    _TIMESTAMP("timestamp", List.of(93)),
    _BYTES("bytes", List.of(-2, -3, -4)),
    _BLOB("blob", List.of(2004)),
    _CLOB("clob", List.of(2005)),
    _URL("url", List.of(70)),
    _OBJECT("object", List.of(2000, 1111)),
    // bit 0或者1 方便前端操作，在java中通常转换为布尔值
    _BIT("bit", List.of(-7));

    private String name;
    private List<Integer> values;


    JDBCJavaTypes(String name, List<Integer> values) {
        this.name = name;
        this.values = values;
    }

    public String getName() {
        return name;
    }

    public List<Integer> getValues() {
        return values;
    }

    public static JDBCJavaTypes getTypeById(Integer id) {
        for (JDBCJavaTypes type : JDBCJavaTypes.values()) {
            if (type.values.contains(id))
                return type;
        }
        return _STRING;
    }

    public static JDBCJavaTypes getType(Integer id,String name) {
       //特殊类型的处理
        // Mysql Year处理
        if(name.equals("YEAR")){
            return _YEAR;
        }
        return getTypeById(id);
    }

    public static JDBCJavaTypes getTypeByName(String name) {
        for (JDBCJavaTypes type : JDBCJavaTypes.values()) {
            if (type.name.equals(name))
                return type;
        }
        return _STRING;
    }


}
