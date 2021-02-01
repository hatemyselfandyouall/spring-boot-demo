package com.wangxinenpu.springbootdemo.util;

import jxl.Cell;
import jxl.DateCell;
import jxl.Sheet;
import jxl.Workbook;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;
import star.util.DateUtil;
import star.util.vo.ExcelVo;

import java.io.*;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExcelUtil
{
  private static final short LENGTH_DATE = (short)(DateUtil.dateFormate(new Date(), "yyyy-MM-dd HH:mm").length() *
    260);

  public static List<Cell[]> getSheet(String filepath, int index)
  {
    Workbook workbook = null;
    try    {
      workbook = Workbook.getWorkbook(new File(filepath));
    }    catch (Exception e)    {
      return null;
    }
    Sheet s = workbook.getSheet(index);
    int rows = s.getRows();
    ArrayList<Cell[]> l = new ArrayList<>();

    for (int i = 1; i < rows; i++)    {
      Cell[] cells = s.getRow(i);
      if (cells.length >= 1) {


        l.add(cells);
      }    }
    return l;
  }

  public static List<Cell[]> getSheet(String filepath)
  {
    return getSheet(filepath, 0);
  }

  public static void excel2File(ExcelVo vo, String filePath)
  {
    FileOutputStream fileOut = null;
    try    {
      fileOut = new FileOutputStream(filePath);
      excel2File(vo, fileOut);
    }    catch (FileNotFoundException e)    {
      e.printStackTrace();
    }
  }


  public static void excel2File(ExcelVo vo, OutputStream fileOut) {
      try {
         createExcleFile(vo).write(fileOut);
      } catch (IOException var11) {
         var11.printStackTrace();
      } finally {
         if (fileOut != null) {
            try {
               fileOut.flush();
               fileOut.close();
            } catch (IOException var10) {
               var10.printStackTrace();
            }         }
      }

   }


  public static HSSFWorkbook createExcleFile(ExcelVo vo)
  {
    HSSFWorkbook workbook = new HSSFWorkbook();
    
    HSSFSheet sheet = workbook.createSheet();
    String sheetName = vo.getSheetName();
    if (StringUtils.isEmpty(sheetName)) {
      sheetName = "firstSheet";
    }
    workbook.setSheetName(0, sheetName);
    
    int s = 0;
    if ((vo.getFields() != null) && (vo.getFields().length > 0))    {
      s = 1;
      
      HSSFRow row = sheet.createRow(0);
      for (int i = 0; i < vo.getFields().length; i++)      {
        String field = vo.getFields()[i];
        HSSFCell cell0 = row.createCell(i);
        cell0.setCellValue(field);
        cell0.setCellStyle(getFieldNameStyle(workbook));

      }
    }
    if ((vo.getRows() != null) && (vo.getRows().size() > 0))    {
      HSSFCellStyle dateStyle = getDateStyle(workbook);
      int l = vo.getRows().size();
      int x = 0;
      boolean cellSizeInited = false;

      for (int i = s; i < s + l; i++)      {
        if (i % 500 == 0) {
          System.err.print(".");
        }
        Object[] os = (Object[])vo.getRows().get(x);
        x++;
        HSSFRow row = sheet.createRow(i);
        for (int j = 0; j < os.length; j++)        {
          Object o = os[j];
          if (o == null) {
            o = "";
          }
          HSSFCell cell = row.createCell(j);
          if (((o instanceof Integer)) || ((o instanceof Float)) || ((o instanceof Double)))          {
            cell.setCellType(CellType.NUMERIC);
            Double d = new Double(o.toString());
            cell.setCellValue(d.doubleValue());          }
          else if ((o instanceof String))          {
            cell.setCellType(CellType.STRING);
            cell.setCellValue((String)o);
            if (!cellSizeInited) {
              sheet.setColumnWidth(j, 2600);
            }
          }
          else if ((o instanceof Date))          {
            Date d = (Date)o;
            cell.setCellStyle(dateStyle);
            cell.setCellValue(d);
            if (!cellSizeInited) {
              sheet.setColumnWidth(j, LENGTH_DATE);
            }          }          else
          {
            cell.setCellType(CellType.STRING);
            cell.setCellValue(o.toString());
            if (!cellSizeInited) {
              sheet.setColumnWidth(j, 2600);

            }
          }
        }
        if (!cellSizeInited) {
          cellSizeInited = true;
        }
      }
    }
    return workbook;
  }
  private static HSSFCellStyle getFieldNameStyle(HSSFWorkbook workbook)
  {
    HSSFCellStyle cs = workbook.createCellStyle();
    HSSFFont f = workbook.createFont();
    f.setColor((short)12);
    f.setBold(true);
    cs.setFont(f);
    cs.setBorderBottom(BorderStyle.THIN);
    return cs;
  }

  private static HSSFCellStyle getDateStyle(HSSFWorkbook workbook)
  {
    HSSFCellStyle cellStyle = workbook.createCellStyle();
    
    cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy h:mm"));
    return cellStyle;
  }
  public static String getCellStringValue(Cell[] cells, int cellIndex)
  {
    String str = null;
    if (cells.length > cellIndex) {
      try      {
        str = cells[cellIndex].getContents();
        if (str != null)        {
          str = str.trim();
          str = str.replaceAll("\n", "");
        }
      }
      catch (Exception localException) {}
    }
    return str;
  }
  public static Date getCellDateValue(Cell[] cells, int cellIndex)
  {
    Date date = null;
    if (cells.length > cellIndex) {
      try      {
        DateCell datec11 = (DateCell)cells[cellIndex];
        date = datec11.getDate();
      }      catch (Exception e)      {
        String con = getCellStringValue(cells, cellIndex);
        date = DateUtil.getDateDf(con, "yyyy年MM月dd日");
        if (date == null) {
          date = DateUtil.getDateDf(con, "yyyy年MM月dd号");
        }
      }
    }
    return date;
  }

  public static List<String[][]> getData(MultipartFile file, int ignoreRows)
    throws IOException
  {
    return readExcelToArrayList(file.getOriginalFilename(), file.getInputStream(), ignoreRows);
  }


  public static List<String[][]> getRemoteExcelData(String fileUrl, int ignoreRows)
    throws IOException
  {
    URL url = new URL(fileUrl);
    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
    conn.setRequestMethod("GET");
    conn.setConnectTimeout(300000);
    InputStream inputStream = conn.getInputStream();
    return readExcelToArrayList(fileUrl, inputStream, ignoreRows);
  }
  private static List<String[][]> readExcelToArrayList(String fileName, InputStream inputStream, int ignoreRows)
    throws IOException
  {
    if (isExcel2003(fileName)) {
      return readExcel2003ToArrayList(inputStream, ignoreRows);    }
    if (isExcel2007(fileName)) {
      return readExcel2007ToArrayList(inputStream, ignoreRows);
    }
    return null;
  }

  private static List<String[][]> readExcel2003ToArrayList(InputStream inputStream, int ignoreRows)
    throws IOException
  {
    List<String[][]> result = new ArrayList<>();
    


    HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
    HSSFCell cell = null;
    workbook.getNumberOfSheets();
    for (int i = 0; i < workbook.getNumberOfSheets(); i++)    {
      HSSFSheet sheet = workbook.getSheetAt(i);
      if(ignoreRows>sheet.getLastRowNum()){
        ignoreRows=sheet.getLastRowNum();
      }
      int newRowNum = sheet.getLastRowNum()+1-ignoreRows;
      String[][] rowResult = new String[newRowNum][1];
      int newRowIndex=0;
      for (int rownumber = ignoreRows; rownumber <= sheet.getLastRowNum(); rownumber++)
      {
        HSSFRow row = sheet.getRow(rownumber);
        if (row != null)


        {
          String[] colResult = new String[row.getLastCellNum()];


          for (int colnumber = 0; colnumber < row.getLastCellNum(); colnumber++)          {
            String value = "";
            cell = row.getCell(colnumber);

            if (cell != null) {
              switch (cell.getCellType())
              {              case 1: 
                value = cell.getStringCellValue();
                break;
              case 0: 
                if (HSSFDateUtil.isCellDateFormatted(cell))                {
                  Date date = cell.getDateCellValue();
                  if (date != null) {
                    value = new SimpleDateFormat("yyyy-MM-dd").format(date);
                  } else {
                    value = "";
                  }                }                else
                {
                  value = new BigDecimal(String.valueOf(cell.getNumericCellValue())).toString();
                }
                break;

              case 2: 
                if (!"".equals(cell.getStringCellValue())) {
                  value = cell.getStringCellValue();
                } else {
                  value = String.valueOf(cell.getNumericCellValue());
                }
                break;
              case 3: 
                break;
              case 5: 
                value = "";
                break;
              case 4: 
                value = cell.getBooleanCellValue() ? "Y" : "N";
                break;
              default: 
                value = "";
              }
            }
            if (colnumber == 0) {
              value.trim();
            }
            colResult[colnumber] = rightTrim(value);
          }
          rowResult[newRowIndex] = colResult;
          newRowIndex++;
        }      }
      if (rowResult != null) {
        result.add(rowResult);
      }
    }
    inputStream.close();
    return result;
  }

  private static List<String[][]> readExcel2007ToArrayList(InputStream inputStream, int ignoreRows)
    throws IOException
  {
    List<String[][]> result = new ArrayList<>();
    
    XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
    XSSFCell cell = null;
    workbook.getNumberOfSheets();
    for (int i = 0; i < workbook.getNumberOfSheets(); i++)    {
      XSSFSheet sheet = workbook.getSheetAt(i);
      if(ignoreRows>sheet.getLastRowNum()){
        ignoreRows=sheet.getLastRowNum();
      }
      int newRowNum = sheet.getLastRowNum()+1-ignoreRows;
      String[][] rowResult = new String[newRowNum][1];
      int newRowIndex=0;

      for (int rownumber = ignoreRows; rownumber <= sheet.getLastRowNum(); rownumber++)      {

        XSSFRow row = sheet.getRow(rownumber);
        if (row != null)

        {
          String[] colResult = new String[row.getLastCellNum()];
          for (int colnumber = 0; colnumber < row.getLastCellNum(); colnumber++)          {
            String value = "";
            cell = row.getCell(colnumber);
            if (cell != null) {
              switch (cell.getCellType())
              {              case 1: 
                value = cell.getStringCellValue();
                break;
              case 0: 
                if (HSSFDateUtil.isCellDateFormatted(cell))                {
                  Date date = cell.getDateCellValue();
                  if (date != null) {
                    value = new SimpleDateFormat("yyyy-MM-dd").format(date);
                  } else {
                    value = "";
                  }                }                else
                {
                  value = new BigDecimal(String.valueOf(cell.getNumericCellValue())).toString();
                }
                break;
              case 2: 
                if (!"".equals(cell.getStringCellValue())) {
                  value = cell.getStringCellValue();
                } else {
                  value = String.valueOf(cell.getNumericCellValue());
                }
                break;
              case 3: 
                break;
              case 5: 
                value = "";
                break;
              case 4: 
                value = cell.getBooleanCellValue() ? "Y" : "N";
                break;
              default: 
                value = "";
              }
            }
            if (colnumber == 0) {
              //"".equals(value.trim());
              value.trim();
            }
            colResult[colnumber] = rightTrim(value);
          }
          rowResult[newRowIndex] = colResult;
          newRowIndex++;
        }      }
      if (rowResult != null) {
        result.add(rowResult);
      }
    }
    inputStream.close();
    return result;
  }

  private static String rightTrim(String str)
  {
    if (str == null) {
      return "";
    }
    int length = str.length();
    for (int i = length - 1; i >= 0; i--)    {
      if (str.charAt(i) != ' ') {
        break;
      }
      length--;
    }
    return str.substring(0, length);
  }
  public static boolean isExcel2003(String fileName)
  {
    return fileName.matches("^.+\\.(?i)(xls)$");
  }
  public static boolean isExcel2007(String fileName)
  {
    return fileName.matches("^.+\\.(?i)(xlsx)$");
  }

}
