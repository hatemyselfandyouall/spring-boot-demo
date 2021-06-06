package com.wangxinenpu.springbootdemo.util.dataSource.sqlParse;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.*;
import net.sf.jsqlparser.statement.update.Update;
import net.sf.jsqlparser.util.TablesNamesFinder;
import org.apache.ibatis.annotations.DeleteProvider;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class SqlParseUtil {
    //三个 sql语句 测试例子，select，insert，update
    //由已知的 sql语句 分别得到对应的 List<String> 或 String

    public static void main(String[] args) throws JSQLParserException {
        String deleteTest="delete from \"EMPQUERY\".\"AC20\" where \"AAZ157\" = '3010000007109499' and \"AAZ159\" = '3010000006944720' and \"AAA027\" = '330100' and \"AAB301\" = '330108' and \"AAE140\" = '210' and \"AAC001\" = '3010001071908729' and \"AAB001\" = '3011000106267879' and \"AAB033\" = '2' and \"AAC013\" IS NULL and \"AAC031\" = '2' and \"AAC066\" = '100' and \"AAC040\" = '3500' and \"AAE180\" = '3500' and \"AAE030\" = '20200401' and \"AAE031\" = '20210601' and \"AAZ165\" IS NULL and \"AAZ289\" = '33010021010607' and \"AAZ113\" IS NULL and \"AAZ003\" IS NULL and \"AJC050\" = '20160801' and \"PRSENO\" = '3010000036247517' and \"CREATE_TIME\" = TO_DATE('01-4月 -20', 'DD-MON-RR') and \"MODIFY_TIME\" = TO_DATE('04-6月 -21', 'DD-MON-RR') and \"ACA111\" IS NULL and ROWID = 'AAAedQAGuAAAvVvAAi'";
//        System.out.println(deleteTest);
//        String updateTest="update \"EMPQUERY\".\"AC02\" set \"AAZ159\" = '4000000010795644', \"AAA027\" = '330122', \"AAE140\" = '110', \"AAC001\" = '4000000010516123', \"AAC008\" = '1', \"AAC049\" = '202104', \"AAE030\" = '20210401', \"AAE031\" = NULL, \"AAE200\" = NULL, \"AAE206\" = NULL, \"AAZ165\" = '40717737', \"AAE100\" = '1', \"PRSENO\" = '163999899937377013', \"CREATE_TIME\" = TO_DATE('20-4月 -21', 'DD-MON-RR'), \"MODIFY_TIME\" = TO_DATE('04-6月 -21', 'DD-MON-RR') where \"AAZ159\" = '4000000010795644' and \"AAA027\" = '330122' and \"AAE140\" = '110' and \"AAC001\" = '4000000010516123' and \"AAC008\" = '1' and \"AAC049\" = '202104' and \"AAE030\" = '20210401' and \"AAE031\" IS NULL and \"AAE200\" IS NULL and \"AAE206\" IS NULL and \"AAZ165\" = '40717737' and \"AAE100\" = '1' and \"PRSENO\" = '163999899937377013' and \"CREATE_TIME\" = TO_DATE('20-4月 -21', 'DD-MON-RR') and \"MODIFY_TIME\" = TO_DATE('20-4月 -21', 'DD-MON-RR') and ROWID = 'AAAehtAGxAAA8AcAAn'";
//        UpdateSQLParseDTO updateSQLParseDTO=test_update(updateTest);
        System.out.println(deleteTest.replaceAll("and ROWID =.*",""));
//        System.out.println(updateSQLParseDTO);
    }

    public static void test_select(String sql) throws JSQLParserException {
        // *********select body items内容
        List<String> str_items = SqlParseUtil.test_select_items(sql);

        // **********select table
        List<String> tableList = SqlParseUtil.test_select_table(sql);

        // **********select table with join
        List<String> tablewithjoin = SqlParseUtil.test_select_join(sql);

        // // *******select where
        String str = SqlParseUtil.test_select_where(sql);

        // // ******select group by
//        List<String> str_groupby = SqlParseUtil.test_select_groupby(sql);

        // //**************select order by
        List<String> str_orderby = SqlParseUtil.test_select_orderby(sql);

    }
    public static InsertSQLParseDTO test_insert(String sql) throws JSQLParserException {
        // ****insert table
        String string_tablename = SqlParseUtil.test_insert_table(sql);

        // ********* insert table column
        List<String> str_column = test_insert_column(sql);

        // ********Insert values ExpressionList强制转换，参见InsertTest.java
        List<String> str_values = test_insert_values(sql);
        InsertSQLParseDTO insertSQLParseDTO=new InsertSQLParseDTO();
        insertSQLParseDTO.setColumns(str_column);
        insertSQLParseDTO.setTableName(string_tablename);
        insertSQLParseDTO.setValues(str_values);
        return insertSQLParseDTO;
    }
    public static UpdateSQLParseDTO test_update(String sql) throws JSQLParserException {

        // *********update table name
//        List<String> str_table = test_update_table(sql);
        // *********update column
        List<String> str_column = test_update_column(sql);
        // *********update values
        List<String> str_values = test_update_values(sql);
        // *******uodate where
        String str_where = test_update_where(sql);
        UpdateSQLParseDTO insertSQLParseDTO=new UpdateSQLParseDTO();
        insertSQLParseDTO.setColumns(str_column);
        insertSQLParseDTO.setValues(str_values);
        insertSQLParseDTO.setWhere(str_where);
        return insertSQLParseDTO;
    }

    public static DeleteSQLParseDTO test_delete(String sql) throws JSQLParserException {

        // *********update table name
//        List<String> str_table = test_update_table(sql);
        // *********update column
        String str_column = test_delete_column(sql);
        // *********update values
        // *******uodate where
//        String str_where = test_update_where(sql);
        DeleteSQLParseDTO deleteSQLParseDTO=new DeleteSQLParseDTO();
//        insertSQLParseDTO.setColumns(str_column);
        deleteSQLParseDTO.setWhere(str_column);
        return deleteSQLParseDTO;
    }

    // *********select body items内容
    public static List<String> test_select_items(String sql)
            throws JSQLParserException {
        CCJSqlParserManager parserManager = new CCJSqlParserManager();
        Select select = (Select) parserManager.parse(new StringReader(sql));
        PlainSelect plain = (PlainSelect) select.getSelectBody();
        List<SelectItem> selectitems = plain.getSelectItems();
        List<String> str_items = new ArrayList<String>();
        if (selectitems != null) {
            for (int i = 0; i < selectitems.size(); i++) {
                str_items.add(selectitems.get(i).toString());
            }
        }
        return str_items;
    }

    // **********select table
    // **********TablesNamesFinder:Find all used tables within an select
    public static List<String> test_select_table(String sql)
            throws JSQLParserException {
        Statement statement = (Statement) CCJSqlParserUtil.parse(sql);
        Select selectStatement = (Select) statement;
        TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();
        List<String> tableList = tablesNamesFinder
                .getTableList(selectStatement);
        return tableList;
    }

    //******************* select join
    public static List<String> test_select_join(String sql)
            throws JSQLParserException {
        Statement statement = (Statement) CCJSqlParserUtil.parse(sql);
        Select selectStatement = (Select) statement;
        PlainSelect plain = (PlainSelect) selectStatement.getSelectBody();
        List<Join> joinList = plain.getJoins();
        List<String> tablewithjoin = new ArrayList<String>();
        if (joinList != null) {
            for (int i = 0; i < joinList.size(); i++) {
                tablewithjoin.add(joinList.get(i).toString());
                //注意 ， leftjoin rightjoin 等等的to string()区别
            }
        }
        return tablewithjoin;
    }

    // *******select where
    public static String test_select_where(String sql)
            throws JSQLParserException {
        CCJSqlParserManager parserManager = new CCJSqlParserManager();
        Select select = (Select) parserManager.parse(new StringReader(sql));
        PlainSelect plain = (PlainSelect) select.getSelectBody();
        Expression where_expression = plain.getWhere();
        String str = where_expression.toString();
        return str;
    }

//    // ******select group by
//    public static List<String> test_select_groupby(String sql)
//            throws JSQLParserException {
//        CCJSqlParserManager parserManager = new CCJSqlParserManager();
//        Select select = (Select) parserManager.parse(new StringReader(sql));
//        PlainSelect plain = (PlainSelect) select.getSelectBody();
//        List<Expression> GroupByColumnReferences = plain
//                .getGroupBy();
//        List<String> str_groupby = new ArrayList<String>();
//        if (GroupByColumnReferences != null) {
//            for (int i = 0; i < GroupByColumnReferences.size(); i++) {
//                str_groupby.add(GroupByColumnReferences.get(i).toString());
//            }
//        }
//        return str_groupby;
//    }

    // **************select order by
    public static List<String> test_select_orderby(String sql)
            throws JSQLParserException {
        CCJSqlParserManager parserManager = new CCJSqlParserManager();
        Select select = (Select) parserManager.parse(new StringReader(sql));
        PlainSelect plain = (PlainSelect) select.getSelectBody();
        List<OrderByElement> OrderByElements = plain.getOrderByElements();
        List<String> str_orderby = new ArrayList<String>();
        if (OrderByElements != null) {
            for (int i = 0; i < OrderByElements.size(); i++) {
                str_orderby.add(OrderByElements.get(i).toString());
            }
        }
        return str_orderby;
    }



    // ****insert table
    public static String test_insert_table(String sql)
            throws JSQLParserException {
        Statement statement = CCJSqlParserUtil.parse(sql);
        Insert insertStatement = (Insert) statement;
        String string_tablename = insertStatement.getTable().getName();
        return string_tablename;
    }

    // ********* insert table column
    public static List<String> test_insert_column(String sql)
            throws JSQLParserException {
        Statement statement = CCJSqlParserUtil.parse(sql);
        Insert insertStatement = (Insert) statement;
        List<Column> table_column = insertStatement.getColumns();
        List<String> str_column = new ArrayList<String>();
        for (int i = 0; i < table_column.size(); i++) {
            str_column.add(table_column.get(i).toString().replaceAll("\"","").replaceAll("'",""));
        }
        return str_column;
    }

    // ********* Insert values ExpressionList
    public static List<String> test_insert_values(String sql)
            throws JSQLParserException {
        Statement statement = CCJSqlParserUtil.parse(sql);
        Insert insertStatement = (Insert) statement;
        List<Expression> insert_values_expression = ((ExpressionList) insertStatement
                .getItemsList()).getExpressions();
        List<String> str_values = new ArrayList<String>();
        for (int i = 0; i < insert_values_expression.size(); i++) {
            str_values.add(insert_values_expression.get(i).toString().replaceAll("\"","").replaceAll("'",""));
        }
        return str_values;
    }

    // *********update table name
    public static String test_update_table(String sql)
            throws JSQLParserException {
//        Statement statement = CCJSqlParserUtil.parse(sql);
//        Update updateStatement = (Update) statement;
////        Table update_table = updateStatement.getTable();
//        List<String> str_table = new ArrayList<String>();
//        if (update_table != null) {
//            for (int i = 0; i < update_table.size(); i++) {
//                str_table.add(update_table.get(i).toString());
//            }
//        }
//        return update_table.getName();
        return null;
    }

    // *********update column
    public static String test_delete_column(String sql)
            throws JSQLParserException {
        Statement statement = CCJSqlParserUtil.parse(sql);
        Delete updateStatement = (Delete) statement;
        String str = updateStatement.getWhere().toString();
//        return str;
//        List<String> str_column = new ArrayList<String>();
//        if (update_column != null) {
//            for (int i = 0; i < update_column.size(); i++) {
//                str_column.add(update_column.get(i).toString().replaceAll("\"","").replaceAll("'",""));
//            }
//
//        }
        System.out.println(str);
        return str;

    }

    // *********update column
    public static List<String> test_update_column(String sql)
            throws JSQLParserException {
        Statement statement = CCJSqlParserUtil.parse(sql);
        Update updateStatement = (Update) statement;
        List<Column> update_column = updateStatement.getColumns();
        List<String> str_column = new ArrayList<String>();
        if (update_column != null) {
            for (int i = 0; i < update_column.size(); i++) {
                str_column.add(update_column.get(i).toString().replaceAll("\"","").replaceAll("'",""));
            }

        }
        return str_column;

    }

    // *********update values
    public static List<String> test_update_values(String sql)
            throws JSQLParserException {
        Statement statement = CCJSqlParserUtil.parse(sql);
        Update updateStatement = (Update) statement;
        List<Expression> update_values = updateStatement.getExpressions();
        List<String> str_values = new ArrayList<String>();
        if (update_values != null) {
            for (int i = 0; i < update_values.size(); i++) {
                str_values.add(update_values.get(i).toString().replaceAll("\"","").replaceAll("'",""));
            }
        }
        return str_values;

    }

    // *******update where
    public static String test_update_where(String sql)
            throws JSQLParserException {
        Statement statement = CCJSqlParserUtil.parse(sql);
        Update updateStatement = (Update) statement;
        Expression where_expression = updateStatement.getWhere();
        String str = where_expression.toString();
        return str;
    }


    public static void select_fromtxt() throws Exception {
        try(BufferedReader reader = new BufferedReader(
                new FileReader(
                        "D:\\java：eclipse\\workspace for javaSE\\JSql-test\\src\\test\\select_simple.txt"));) {

            List<String> statement_list = new ArrayList<String>();
            while (true) { // 输出所有语句
                String line = reader.readLine();
                if (line == null) {
                    break;
                } else
                    statement_list.add(line);
            }
            for (int i = 0; i < 1; i++) {
                SqlParseUtil.test_select(statement_list.get(i));
            }
        }catch (Exception e){
            throw e;
        }
    }

    public static void insert_fromtxt() throws Exception {
        try (BufferedReader reader = new BufferedReader(
                new FileReader(
                        "D:\\java：eclipse\\workspace for javaSE\\JSql-test\\src\\test\\select_simple.txt"));){

        List<String> statement_list = new ArrayList<String>();
        while (true) { // 输出所有语句
            String line = reader.readLine();
            if (line == null) {
                break;
            } else
                statement_list.add(line);
        }
        for (int i = 0; i < 1; i++) {
            SqlParseUtil.test_insert(statement_list.get(i));
        }
    }catch (Exception e){
            throw e;
        }
    }

    public static void update_fromtxt() throws Exception {
        try (BufferedReader reader = new BufferedReader(
                new FileReader(
                        "D:\\java：eclipse\\workspace for javaSE\\JSql-test\\src\\test\\select_simple.txt"));) {

            List<String> statement_list = new ArrayList<String>();
            while (true) { // 输出所有语句
                String line = reader.readLine();
                if (line == null) {
                    break;
                } else
                    statement_list.add(line);
            }
            for (int i = 0; i < 1; i++) {
                SqlParseUtil.test_update(statement_list.get(i));
            }
        }catch (Exception e){
            throw e;
        }
    }

    public static String getROWIDfromWhere(String where){
//        if (!where.contains(column)){
//            return null;
//        }
        where=where.substring(where.indexOf("AND ROWID = '"));
        where=where.replace("AND ROWID = '","").replaceAll("'","");
        return where;
    }

    public static String getRowIdFromSQL(String redoSQL, String opeartion) throws JSQLParserException {
        if ("UPDATE".equals(opeartion)){
            SQLParseDTO sqlParseDTO=test_update(redoSQL);
            return getROWIDfromWhere(sqlParseDTO.getWhere());
        }
        if ("DELETE".equals(opeartion)){
            SQLParseDTO sqlParseDTO=test_delete(redoSQL);
            return getROWIDfromWhere(sqlParseDTO.getWhere());
        }
        return null;
    }
}
