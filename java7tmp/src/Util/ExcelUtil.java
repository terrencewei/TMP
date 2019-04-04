package Util;

import org.apache.poi.hssf.OldExcelFormatException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;

/**
 * Created by terrence on 2018/05/25.
 */
public class ExcelUtil {

    public static Workbook createWorkbookIfNotExist(String fileName) throws Exception {
        Workbook wb = null;

        if (fileName.endsWith(".xls")) {
            wb = new HSSFWorkbook();
        } else if (fileName.endsWith(".xlsx")) {
            wb = new XSSFWorkbook();
        } else {
            throw new Exception("文件类型错误！");
        }

        try {
            OutputStream output = new FileOutputStream(fileName);
            wb.write(output);
        } catch (FileNotFoundException e) {
            System.out.println("文件创建失败，失败原因为：" + e.getMessage());
            throw new FileNotFoundException();
        }
        System.out.println(fileName + "文件创建成功！");

        return wb;
    }



    //创建一个新的或者已存在的Excel文档的Workbook
    public static Workbook createWorkbook(String fileName) throws Exception {
        InputStream input = null;
        Workbook wb = null;

        try {
            input = new FileInputStream(fileName);
            wb = WorkbookFactory.create(input);
        } catch (FileNotFoundException e) {
            System.out.println("要打开的文件不存在，正试图创建该文件，请稍后……！");
            wb = createWorkbookIfNotExist(fileName);
        } catch (OldExcelFormatException e) {
            System.out.println("文件打开失败，原因：要打开的Excel文件版本过低！");
            throw new OldExcelFormatException("文件版本过低");
        } finally {
            if (input != null) {
                input.close();
            }
        }

        return wb;
    }



    //创建sheet
    public static Sheet createSheet(Workbook wb, String sheetName) {
        Sheet sheet = wb.getSheet(sheetName);

        if (sheet == null) {
            System.out.println("表单" + sheetName + "不存在，试图创建该sheet，请稍后……");
            sheet = wb.createSheet(sheetName);
            System.out.println("名为" + sheetName + "的sheet创建成功！");
        }

        return sheet;
    }



    //创建行row
    public static Row createRow(Sheet sheet, int rowNum) {
        Row row = sheet.getRow(rowNum);

        if (row == null) {
            System.out.println("行号为：" + rowNum + "的行不存在，正试图创建该行，请稍后……");
            row = sheet.createRow(rowNum);
            System.out.println("行号为：" + rowNum + "的行创建成功！");
        }

        return row;
    }



    //创建单元格cell
    public static Cell createCell(Row row, int cellNum) {
        Cell cell = row.getCell(cellNum);

        if (cell == null) {
            System.out.println("该单元格不存在，正在试图创建该单元格，请稍后……");
            cell = row.createCell(cellNum);
            System.out.println("该单元格创建成功！");
        }

        return cell;
    }



    public static void saveWorkbook(Workbook pWorkbook, String filePath) {
        try {
            pWorkbook.write(new FileOutputStream(filePath));
        } catch (IOException e) {
            System.out.println("文件保存失败，失败原因为：" + e.getMessage());
        }
    }

}