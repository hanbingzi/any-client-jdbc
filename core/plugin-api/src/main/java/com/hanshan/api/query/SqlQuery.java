package com.hanshan.api.query;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SqlQuery extends ConnectQuery {

    private String sql;

}
