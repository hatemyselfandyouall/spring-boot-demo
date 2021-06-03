package com.wangxinenpu.springbootdemo.util.statuCheck;

import com.alibaba.fastjson.JSONObject;

import com.wangxinenpu.springbootdemo.dataobject.vo.LinkTransferTask.table.FieldInfo;
import com.wangxinenpu.springbootdemo.dataobject.vo.LinkTransferTask.table.TableInfo;
import com.wangxinenpu.springbootdemo.dataobject.vo.root.ResultVo;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.ArrayList;
import java.util.List;

public class ExcelUtil {



    public static ResultVo<List<TableInfo>> testConnect(String filePath) {
        Workbook wb = ExcelHelper.readExcel(filePath);
        Integer sheetNum = wb.getNumberOfSheets();
        List<TableInfo> tableInfos = new ArrayList<>();
        for (int i = 0; i < sheetNum; i++) {
            TableInfo tableInfo = new TableInfo();
            Sheet sheet = wb.getSheetAt(i);
            tableInfo.setTableName(sheet.getSheetName());
            tableInfo.setFieldInfos(getFields(sheet));
            tableInfo.setTableDatas(getTableDatas(sheet, tableInfo.getFieldInfos()));
            tableInfos.add(tableInfo);
        }
        ResultVo resultVo=new ResultVo();
        resultVo.setSuccess(true);
        resultVo.setResult(tableInfos);
        return resultVo;
    }

    public static List<FieldInfo> getFields(Sheet sheet) {
        List<FieldInfo> fieldInfos = new ArrayList<>();
        Row row = sheet.getRow(0);
        if (row==null){
            return null;
        }
        for (int i = 0; i < row.getLastCellNum(); i++) {
            FieldInfo fieldInfo = new FieldInfo();
            if (row.getCell(i)==null){
                continue;
            }
            fieldInfo.setName(row.getCell(i).getStringCellValue());
            fieldInfo.setType(row.getCell(i).getCellTypeEnum().name());
            fieldInfos.add(fieldInfo);
        }
        return fieldInfos;
    }

    public static List<JSONObject> getTableDatas(Sheet sheet, List<FieldInfo> fieldInfos) {
        List<JSONObject> jsonArray = new ArrayList<>();
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            JSONObject jsonObject = new JSONObject();
            for (int j = 0; j < row.getLastCellNum(); j++) {
                Cell cell = row.getCell(j);
                if (j < fieldInfos.size() && cell != null) {
                    //todo 此处要根据类型做筛选
                    jsonObject.put(fieldInfos.get(j).getName() != null ? fieldInfos.get(j).getName() : "null", ExcelHelper.getCellFormatValue(cell) != null ? ExcelHelper.getCellFormatValue(cell) : "null");
                }
            }
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }


}
