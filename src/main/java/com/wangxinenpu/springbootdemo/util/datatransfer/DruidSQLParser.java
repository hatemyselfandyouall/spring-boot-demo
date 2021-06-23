package com.wangxinenpu.springbootdemo.util.datatransfer;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.statement.SQLExprTableSource;
import com.alibaba.druid.sql.ast.statement.SQLSelect;
import com.alibaba.druid.sql.ast.statement.SQLSelectQueryBlock;
import com.alibaba.druid.sql.ast.statement.SQLSelectStatement;

import java.sql.SQLSyntaxErrorException;
import java.util.List;

public class DruidSQLParser {

    public static void main(String[] args) throws SQLSyntaxErrorException {
        testSqlParser();
    }
    public static void testSqlParser() throws SQLSyntaxErrorException {
        String sql = "select t.name, t.id, (select p.name from post p where p.id = t.post_id)" +
                "from acct t where t.id = 10 and exists (select r.id from role r where r.id = t.role_id) ";
        String dbType = "mysql";
        System.out.println("原始SQL 为 ： " + sql);
        String result = SQLUtils.format(sql, dbType);
        System.out.println(result);
        SQLSelectStatement statement = (SQLSelectStatement) parser(sql, dbType);
        SQLSelect select = statement.getSelect();
        SQLSelectQueryBlock query = (SQLSelectQueryBlock) select.getQuery();

        // 这里新增的条件，如果语法不正确会报错。如果条件不正确，需要执行了sql后才会报错。
//        query.addSelectItem("name like 'admin%'");

        SQLExprTableSource tableSource = (SQLExprTableSource) query.getFrom();
        String tableName = tableSource.getExpr().toString();
        System.out.println("获取的表名为  tableName ：" + tableName);
        //修改表名为acct_1
//        tableSource.setExpr("acct_1");
        System.out.println("修改表名后的SQL 为 ： [" + statement.toString() + "]");
    }

    public static SQLStatement parser(String sql, String dbType) throws SQLSyntaxErrorException {
        List<SQLStatement> list = SQLUtils.parseStatements(sql, dbType);
        if (list.size() > 1) {
            throw new SQLSyntaxErrorException("MultiQueries is not supported,use single query instead ");
        }
        return list.get(0);
    }
}
