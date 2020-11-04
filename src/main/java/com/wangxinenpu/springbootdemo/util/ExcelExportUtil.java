package com.wangxinenpu.springbootdemo.util;

/**
 * Created by swy on 2019/5/14.
 */

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

public class ExcelExportUtil {
    private static Logger logger = LoggerFactory.getLogger(star.util.ExcelExportUtil.class);

    public static XSSFWorkbook exportExcel(List<String> titleList, List<Map<String, String>> list, String fileName) throws Exception {
        XSSFWorkbook workbook = new XSSFWorkbook();
        if(list!=null&&list.size()>0) {
            createSheets(workbook, titleList,list,fileName);
            OutputStream out = null;
            try {
                URLEncoder.encode(fileName, "UTF-8");
                File file = new File(fileName);
                out = new FileOutputStream(file);
                workbook.write(out);
                out.flush();
            } catch (UnsupportedEncodingException var13) {
                logger.info("文件名称转码错误");
                var13.printStackTrace();
                throw new Exception("文件名称转码错误");
            } catch (FileNotFoundException var14) {
                logger.info("获取输出流异常");
                var14.printStackTrace();
                throw new Exception("获取输出流异常");
            } catch (IOException var15) {
                logger.info("将导出数据写入到excel中异常");
                var15.printStackTrace();
                throw new Exception("将导出数据写入到excel中异常");
            } finally {
                if (out != null) {
                    out.close();
                }
            }
        }
        return workbook;
    }

    private static void createSheets(XSSFWorkbook workbook, List<String> titleList, List<Map<String, String>> list, String title) {
        createSheet(workbook,titleList,list,title,"sheet0");
    }

    private static void createSheet(XSSFWorkbook workbook, List<String> titleList, List<Map<String, String>> sheetBody, String title, String sheetName) {
        List<String> footExceptionMsgList = new ArrayList();
        XSSFSheet sheet = workbook.createSheet(sheetName);

        //设置标题位置
        sheet.addMergedRegion(new CellRangeAddress(
                0, //first row
                0, //last row
                0, //first column
                titleList.size()-1 //last column
        ));

        XSSFCellStyle titleStyle = genTitleStyle(workbook);//创建标题样式

        Row row = sheet.createRow(0);//第0行 放标题
        Cell cell;
        cell = row.createCell(0);//创建一列
        cell.setCellValue(title.substring(0,title.indexOf(".")));//标题
        cell.setCellStyle(titleStyle);//设置标题样式

        row = sheet.createRow(1);//第二行 放列
        for (int i=0;i<titleList.size();i++) {
            String headers = titleList.get(i);
            cell = row.createCell(i);
            cell.setCellValue(headers);
            cell.setCellStyle(createStyles(workbook).get("header"));
            sheet.setColumnWidth(i, headers.getBytes().length * 2 * 256);
        }

        for(int i = 0; i < sheetBody.size(); ++i) {
            row = sheet.createRow(i + 2);//第二行以后放值
            Map<String, String> customMap = sheetBody.get(i);
            for(int j = 0; j < titleList.size(); ++j) {
                cell = row.createCell(j);
                String field = titleList.get(j);
                if(StringUtils.isEmpty(field)) {
                    cell.setCellValue("");
                } else {
                    Object value = null;
                    if(customMap.containsKey(field)) {
                        value = customMap.get(field);
                    }
                    String resultValue = String.valueOf(value);
                    try {
                        resultValue = cellResult(field, value);
                    } catch (Exception var17) {
                        String exception = sheetName + "页第" + (i + 2) + "行" + field + "字段数据有异常。 异常信息：" + var17.getMessage();
                        footExceptionMsgList.add(exception);
                    }

                    resultValue = StringUtils.replace(resultValue, "null", "");
                    cell.setCellValue(resultValue);
                }
            }
        }

    }


    public static String cellResult(String field, Object value) {
        String cellResult = String.valueOf(value);
        if(StringUtils.contains(field, "-date") && value instanceof Date) {
            Date d = (Date)value;
            String pattern = StringUtils.contains(field, "-dateTime")?"yyyy-MM-dd HH:mm:ss":"yyyy-MM-dd";
            if(StringUtils.contains(field, "#")) {
                pattern = StringUtils.substringAfter(field, "#");
            }

            SimpleDateFormat simple = new SimpleDateFormat(pattern);
            cellResult = simple.format(d);
        } else if(StringUtils.contains(field, "-centimeter")) {
            BigDecimal thousand = new BigDecimal(1000);
            BigDecimal bigDecimalValue = new BigDecimal(cellResult);
            bigDecimalValue = bigDecimalValue.divide(thousand, 2, 4);
            cellResult = bigDecimalValue.toPlainString();
            if(StringUtils.contains(field, "#")) {
                cellResult = cellResult + StringUtils.substringAfter(field, "#");
            }
        } else if(StringUtils.contains(field, "-rate")) {
            cellResult = StringUtils.replace(cellResult, "null", "0.00%");
            cellResult = StringUtils.replace(cellResult, "", "0.00%");
            cellResult = cellResult.contains("%")?cellResult:cellResult + "%";
        } else if(StringUtils.contains(field, "-money")) {
            cellResult = StringUtils.replace(cellResult, "null", "0.00");
            cellResult = StringUtils.replace(cellResult, "", "0.00");
        }

        return cellResult;
    }


    public static Map<String, CellStyle> createStyles(Workbook wb) {
        Map<String, CellStyle> styles = new HashMap(100);
        DataFormat df = wb.createDataFormat();
        Font normalFont = wb.createFont();
        normalFont.setFontHeightInPoints((short)10);
        Font boldFont = wb.createFont();
        boldFont.setFontHeightInPoints((short)10);
        boldFont.setBoldweight((short)700);
        Font blueBoldFont = wb.createFont();
        blueBoldFont.setFontHeightInPoints((short)10);
        blueBoldFont.setBoldweight((short)700);
        blueBoldFont.setColor(IndexedColors.BLUE.getIndex());
        CellStyle headerStyle = wb.createCellStyle();
        headerStyle.setFont(boldFont);
        styles.put("header", headerStyle);
        CellStyle dateCellStyle = wb.createCellStyle();
        dateCellStyle.setFont(normalFont);
        dateCellStyle.setDataFormat(df.getFormat("yyyy"));
        setBorder(dateCellStyle);
        styles.put("dateCell", dateCellStyle);
        CellStyle numberCellStyle = wb.createCellStyle();
        numberCellStyle.setFillPattern((short)1);
        numberCellStyle.setFillBackgroundColor((short)48);
        numberCellStyle.setFont(normalFont);
        numberCellStyle.setDataFormat(df.getFormat("#,##0.00"));
        setBorder(numberCellStyle);
        styles.put("numberCell", numberCellStyle);
        CellStyle totalStyle = wb.createCellStyle();
        totalStyle.setFont(blueBoldFont);
        totalStyle.setWrapText(true);
        totalStyle.setAlignment((short)3);
        setBorder(totalStyle);
        styles.put("total", totalStyle);
        return styles;
    }

    private static void setBorder(CellStyle style) {
        style.setBorderRight((short)1);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft((short)1);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop((short)1);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderBottom((short)1);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
    }

    //生成标题样式
    public static XSSFCellStyle genTitleStyle(XSSFWorkbook workbook){

        XSSFCellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setWrapText(true);

        //标题居中，没有边框，所以这里没有设置边框，设置标题文字样式
        XSSFFont titleFont = workbook.createFont();
        titleFont.setBold(true);//加粗
        titleFont.setFontHeight((short)15);//文字尺寸
        titleFont.setFontHeightInPoints((short)15);
        style.setFont(titleFont);

        return style;
    }
}

