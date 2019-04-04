package Util;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by terrencewei on 2018/03/27.
 */
public class DyAdminRQLExcelParse {

    public static void main(String args[]) throws Exception {
        // XSSFWorkbook, File
        OPCPackage pkg = OPCPackage.open(new File("/home/terrence/Downloads/INPUT.xlsx"));
        XSSFWorkbook excel = new XSSFWorkbook(pkg);
        Sheet sheet = excel.getSheet("PROD_ORDER_2");

        List<Order> orders = new ArrayList<>();
        int rqlPropertyRowCount = 34;
        int itemArrayCount = sheet.getLastRowNum() / rqlPropertyRowCount;
        for (int i = 0; i < itemArrayCount + 1; i++) {
            for (int k = 1; k <= 11; k++) {
                Order order = new Order();
                String id = getText(sheet.getRow((i * rqlPropertyRowCount) - 1 + 1), k);
                if (id != null) {
                    order.setAtgId(id);
                }
                String jobId = getText(sheet.getRow((i * rqlPropertyRowCount) - 1 + 23), k);
                if (jobId != null) {
                    order.setJobNumber(jobId);
                }
                String jobAccount = getText(sheet.getRow((i * rqlPropertyRowCount) - 1 + 9), k);
                if (jobAccount != null) {
                    order.setJobName(jobAccount);
                }
                String orderNumber = getText(sheet.getRow((i * rqlPropertyRowCount) - 1 + 16), k);
                if (orderNumber != null) {
                    order.setMincronId(orderNumber);
                }
                String defaultBranchId = getText(sheet.getRow((i * rqlPropertyRowCount) - 1 + 17), k);
                if (defaultBranchId != null) {
                    order.setDefaultBranchId(defaultBranchId);
                }
                String shippingGroups = getText(sheet.getRow((i * rqlPropertyRowCount) - 1 + 7), k);
                if (shippingGroups != null) {
                    order.setShippingGroups(shippingGroups);
                }
                // add to orders
                if (order.getAtgId() != null) {
                    orders.add(order);
                }
            }
        }

        pkg.close();

        //        for (Order order : orders) {
        //            System.out.println(order);
        //        }
        //        System.out.println("total order count:" + orders.size());

        String outputFilePath = "/home/terrence/Downloads/order.xlsx";
        Workbook workbook = ExcelUtil.createWorkbook(outputFilePath);
        Sheet sheet1 = ExcelUtil.createSheet(workbook, "order");
        CellStyle style = workbook.createCellStyle();
        XSSFDataFormat format = (XSSFDataFormat) workbook.createDataFormat();
        style.setDataFormat(format.getFormat("TEXT"));
        for (int i = 0; i < orders.size() + 1; i++) {
            Row row = ExcelUtil.createRow(sheet1, i);
            if (i == 0) {
                setCell(style, row, 0, "Mincron ID");
                setCell(style, row, 1, "Job Name");
                setCell(style, row, 2, "Job Number");
                setCell(style, row, 3, "ATG Order ID");
                setCell(style, row, 4, "defaultBranchId");
                setCell(style, row, 5, "shippingGroups");
            } else {
                Order order = orders.get(i - 1);
                setCell(style, row, 0, order.getMincronId());
                setCell(style, row, 1, order.getJobName());
                setCell(style, row, 2, order.getJobNumber());
                setCell(style, row, 3, order.getAtgId());
                setCell(style, row, 4, order.getDefaultBranchId());
                setCell(style, row, 5, order.getShippingGroups());
            }
        }
        ExcelUtil.saveWorkbook(workbook, outputFilePath);
    }



    private static void setCell(CellStyle pStyle, Row pRow, int column, String cellText) {
        Cell cell = ExcelUtil.createCell(pRow, column);
        cell.setCellStyle(pStyle);
        cell.setCellValue(cellText);
    }



    private static String getText(Row pRow, int cell) {
        String text = "";
        try {
            text = pRow.getCell(cell).toString();
        } catch (Exception pE) {
        }
        if (text != null && text.length() > 0) {
            text = text.replaceAll("●", " ");
            if (text.endsWith(".0")) {
                text = text.substring(0, text.length() - 2);
            }
            if (text.equals("null")) {
                text = "";
            }
            return text;
        }
        return null;
    }



    public static class Order {
        private String atgId;
        private String mincronId;
        private String jobName;
        private String jobNumber;
        private String defaultBranchId;
        private String shippingGroups;



        public String getAtgId() {
            return atgId;
        }



        public void setAtgId(String pAtgId) {
            atgId = pAtgId;
        }



        public String getMincronId() {
            return mincronId;
        }



        public void setMincronId(String pMincronId) {
            mincronId = pMincronId;
        }



        public String getJobName() {
            return jobName;
        }



        public void setJobName(String pJobName) {
            jobName = pJobName;
        }



        public String getJobNumber() {
            return jobNumber;
        }



        public void setJobNumber(String pJobNumber) {
            jobNumber = pJobNumber;
        }



        public String getDefaultBranchId() {
            return defaultBranchId;
        }



        public void setDefaultBranchId(String pDefaultBranchId) {
            defaultBranchId = pDefaultBranchId;
        }



        public String getShippingGroups() {
            return shippingGroups;
        }



        public void setShippingGroups(String pShippingGroups) {
            shippingGroups = pShippingGroups;
        }



        @Override
        public String toString() {
            return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
        }

    }

}

